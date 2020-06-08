package com.yy.platform.component.starter.util;

/**
 * @Auther: zhanqingqi
 * @Date: 2020/1/3 14:57
 * @Description: 字符串工具类
 */
public class StringUtil {

    private StringUtil(){}

    /**
     * 字符串模板的拼接
     * @param str
     * @param values
     * @return
     */
    public static String strAssemble(String str, Object... values){
        String strTemp = str.replace("{}", "%s");
        return String.format(strTemp, values);
    }

}
