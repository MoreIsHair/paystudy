package com.yy;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Console;
import cn.hutool.core.map.MapUtil;
import com.yy.common.util.XMLUtils;
import com.yy.wxpay.model.WXPrePayResult;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class RunApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Test
    public void xmlUtilTest() {
        String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<o>\n" +
                "     <a class=\"array\">\n" +
                "          <e type=\"number\">1</e>\n" +
                "          <e type=\"number\">5</e>\n" +
                "     </a>\n" +
                "     <b type=\"string\">2</b>\n" +
                "     <c type=\"string\">3</c>\n" +
                "</o>";

        try {
            WXPrePayResult wxPrePayResult = XMLUtils.xml2Object(xmlString, WXPrePayResult.class);
            System.out.println(wxPrePayResult.getA().getClass());
            Console.log(wxPrePayResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void object2XMLStringTest() {
        WXPrePayResult wxPrePayResult = new WXPrePayResult();
        ArrayList<Integer> strings = CollUtil.newArrayList(1, 2);
        wxPrePayResult.setA(strings);
        wxPrePayResult.setB("2");
        wxPrePayResult.setC("3");
        Map<String, Integer> i = MapUtil.of("i", 4);
        i.put("i", 5);
        i.put("i", 6);
        wxPrePayResult.setD(i);
        HashMap<String, Object> g = MapUtil.of("g", 7);
        HashMap<String, String> y = MapUtil.of("y", "8");
        g.put("h", y);
        wxPrePayResult.setF(g);
        String s = XMLUtils.object2XMLString(wxPrePayResult, null);
        Console.log(s);
    }


}
