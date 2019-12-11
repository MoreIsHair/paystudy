package com.yy.common.util;

import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.lang.Console;
import cn.hutool.extra.qrcode.QrCodeUtil;
import com.yy.common.exception.BasePayException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

/**
 * @author YY
 * @date 2019/12/9
 * @description 二维码生成
 */
public class QRCodeUtils {
    /**
     * 默认宽度
     */
    public static final int DEFAULT_WIDTH = 300;
    /**
     * 默认高度
     */
    public static final int DEFAULT_HEIGHT = 300;

    /**
     * 生成二维码
     */
    public static void qrCode2Response(HttpServletResponse response, String url, int width, int height) {
        try (ServletOutputStream outputStream = response.getOutputStream()) {
            QrCodeUtil.generate(url, width, height, ImgUtil.IMAGE_TYPE_PNG, outputStream);
        } catch (Exception e) {
            Console.error(e.getMessage());
            throw new BasePayException("二维码生成错误" + e.getMessage(), e);
        }
    }

    /**
     * 生成二维码
     */
    public static void qrCode2Response(HttpServletResponse response, String url) {
        QRCodeUtils.qrCode2Response(response, url, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }
}
