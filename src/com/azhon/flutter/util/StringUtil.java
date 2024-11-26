package com.azhon.flutter.util;

/**
 * createDate: 2022/11/18 on 14:25
 * desc:
 *
 * @author azhon
 */


public class StringUtil {

    /**
     * 将下划线格式转换成驼峰命名并首字母大写
     */
    public static String toCamelCase(String str, boolean upCase) {
        final char underLine = '_';
        StringBuilder sb = new StringBuilder(str.length());
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == underLine) {
                if (++i < str.length()) {
                    sb.append(Character.toUpperCase(str.charAt(i)));
                }
            } else {
                sb.append(c);
            }
        }
        //首字母大写
        if (upCase) {
            char[] chars = sb.toString().toCharArray();
            chars[0] -= 32;
            return String.valueOf(chars);
        } else {
            return firstLowCase(sb.toString());
        }
    }

    /**
     * 首字母小写
     */
    public static String firstLowCase(String str) {
        String s = str.substring(0, 1).toLowerCase();
        return s + str.substring(1);
    }

    /**
     * 去除文件格式
     */
    public static String removeSuffix(String name) {
        int index = name.indexOf(".");
        return name.substring(0, index == -1 ? name.length() : index);
    }
}
