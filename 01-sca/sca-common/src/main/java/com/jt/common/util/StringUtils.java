package com.jt.common.util;

public class StringUtils {
    /**
     * 通过此方法判定传入的字符串是否为空
     * @param str
     * @return
     */
    public static boolean isEmpty(String str){
        return str==null||"".equals(str);
    }
}
