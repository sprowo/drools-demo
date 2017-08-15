package com.prowo;

import org.drools.core.util.StringUtils;

public class StringTest {
    public static void main(String[] args) {
        String str = "a\b\bcde\b";
        System.err.println(strRepace1(str));
        System.err.println(strRepace2(str));
    }

    private static String strRepace1(String str) {
        if (StringUtils.isEmpty(str)) {
            return StringUtils.EMPTY;
        }
        int index = str.indexOf("\b");
        if (index == -1) {
            return str;
        }
        return index <= 1 ? str.substring(index + 1) : str.substring(0, index - 1) + str.substring(index + 1);
    }

    private static String strRepace2(String str) {
        if (StringUtils.isEmpty(str)) {
            return StringUtils.EMPTY;
        }
        int count = 0;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) != '\b') {
                sb.append(str.charAt(i));
                count++;
            } else {
                if (count > 0) {
                    sb.deleteCharAt(count - 1);
                    count--;
                }
            }
        }
        return sb.toString();
    }

}
