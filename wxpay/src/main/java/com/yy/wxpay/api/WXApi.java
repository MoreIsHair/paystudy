package com.yy.wxpay.api;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Console;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.http.HttpUtil;
import com.github.wxpay.sdk.WXPayUtil;
import com.yy.common.event.AbstractPayNotifyEvent;
import com.yy.common.util.QRCodeUtils;
import com.yy.common.util.XMLUtils;
import com.yy.wxpay.config.WxPayConfig;
import com.yy.wxpay.constant.enums.PayFlag;
import com.yy.wxpay.event.WXPayNotifyEvent;
import com.yy.wxpay.exception.WXPayException;
import com.yy.wxpay.model.WXPrePayParam;
import com.yy.wxpay.service.WxPayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author YY
 * @date 2019/12/2
 * @description
 */
@Api(tags = "微信支付API")
@RestController
@RequestMapping("/wx")
public class WXApi {

    @Autowired
    WxPayService wxPayService;

    @Autowired
    private ApplicationContext applicationContext;

    @PostMapping("/notify")
    @ApiOperation("微信回调接口")
    public void notify(HttpServletRequest request, HttpServletResponse response) {
        Console.log("微信支付成功,微信发送的callback信息,请注意修改订单信息");
        try (InputStream is = request.getInputStream();
             PrintWriter writer = response.getWriter()) {
            // 获取请求的流信息(这里是微信发的xml格式所有只能使用流来读)
            String xml = HttpUtil.getString(is, CharsetUtil.CHARSET_UTF_8, true);
            // 将微信发的xml转map
            Map<String, String> notifyMap = WXPayUtil.xmlToMap(xml);
            Console.log("微信返回给回调函数的信息为：" + xml);

            if (PayFlag.SUCCESS.name().equals(notifyMap.get("result_code"))) {
                // 商户订单号
                String ordersSn = notifyMap.get("out_trade_no");
                // 实际支付的订单金额:单位 分
                String amountpaid = notifyMap.get("total_fee");
                // 将分转换成元-实际支付金额:元
                BigDecimal amountPay = (new BigDecimal(amountpaid).divide(new BigDecimal("100"))).setScale(2);
                // 业务处理--推送事件，可通过监听去完成业务处理
                Console.log("===notify===回调方法已经被调");
                AbstractPayNotifyEvent abstractPayNotifyEvent = new WXPayNotifyEvent(this, notifyMap);
                applicationContext.publishEvent(abstractPayNotifyEvent);
            }
            // 告诉微信服务器收到信息了，不需要继续回调，回复微信服务器信息用流发送一个xml即可
            writer.write("<xml><return_code><![CDATA[SUCCESS]]></return_code></xml>");
        } catch (Exception e) {
            Console.error(e.getMessage());
            e.printStackTrace();
        }
    }

    @ApiOperation("微信支付")
    @PostMapping("/wxpay")
    public void wxPay(HttpServletRequest request, HttpServletResponse response, long money) {

        WxPayConfig config = wxPayService.getConfig();
        // 返回前端页面的数据
        Map<String, String> payMap = new HashMap<>(6);
        // 获取请求ip地址
        String ip = getIp(request);
        // 构建对象
        WXPrePayParam wxPrePayParam = new WXPrePayParam();
        // 商家平台ID
        wxPrePayParam.setAppid(config.getAppId());
        // 商家名称-销售商品类目、String(128)
        wxPrePayParam.setBody("商家名称-销售商品类目");
        // 商户ID
        wxPrePayParam.setMch_id(config.getMchId());
        // UUID
        wxPrePayParam.setNonce_str(WXPayUtil.generateNonceStr());
        wxPrePayParam.setOpenid(config.getOpenId());
        // 订单号,每次都不同
        wxPrePayParam.setOut_trade_no(UUID.randomUUID().toString().replaceAll("-", ""));
        wxPrePayParam.setSpbill_create_ip(ip);
        // 支付金额，单位分
        wxPrePayParam.setTotal_fee(String.valueOf(money));
        // 支付类型 此处指定为扫码支付
        wxPrePayParam.setTrade_type(config.getTradeType());
        // 回调地址
        wxPrePayParam.setNotify_url(config.getNotifyUrl());
        Map convert = Convert.convert(new TypeReference<Map<String, String>>() {
        }, wxPrePayParam);
        try {
            String sign = WXPayUtil.generateSignature(convert, config.getMchKey());
            wxPrePayParam.setSign(sign);
            String xml = XMLUtils.object2XMLString(wxPrePayParam, null);
            Map convertSign = Convert.convert(new TypeReference<Map<String, String>>() {
            }, wxPrePayParam);
            Console.log("xml为：" + xml);
            // 调用微信统一下单接口（生成预订单）
            String xmlStr = HttpUtil.post(WxPayConfig.UNIFIED_ORDER_URL, xml);
            Console.log("xmlStr为：" + xmlStr);
            if (xmlStr.contains(PayFlag.SUCCESS.name())) {
                Map<String, String> map = WXPayUtil.xmlToMap(xmlStr);
                String urlCode = map.get("code_url");
                // 生成二维码写回浏览器
                QRCodeUtils.qrCode2Response(response, urlCode);
            }
        } catch (Exception e) {
            Console.error(e.getMessage());
            e.printStackTrace();
            throw new WXPayException(e.getMessage());
        }
    }

    /**
     * 将这个6个参数传给前端
     * 返回待支付信息给前段？
     *
     * @param prepay_id 预支付ID
     */
    private void buildPayInfo(WxPayConfig config, Map<String, String> payMap, String prepay_id) throws Exception {
        payMap.put("appId", config.getAppId());
        payMap.put("timeStamp", Convert.toStr(DateUtil.current(true)));
        payMap.put("nonceStr", WXPayUtil.generateNonceStr());
        payMap.put("signType", "MD5");
        payMap.put("package", "prepay_id=" + prepay_id);
        String paySign = WXPayUtil.generateSignature(payMap, config.getMchKey());
        payMap.put("paySign", paySign);
    }

    /**
     * 获取IP
     */
    private String getIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || PayFlag.UNKNOWN.name().equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || PayFlag.UNKNOWN.name().equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || PayFlag.UNKNOWN.name().equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip.contains(",")) {
            String[] ips = ip.split(",");
            ip = ips[0].trim();
        }
        return ip;
    }
}
