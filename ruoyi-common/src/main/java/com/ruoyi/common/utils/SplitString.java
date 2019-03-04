package com.ruoyi.common.utils;

import java.util.ArrayList;

import java.util.List;

public class SplitString {
    static List<String> list = new ArrayList<String>();

    /**
     *      *
     *      * 根据分割符<h4>('.',':','/')</h4>,分割指定字符串
     *      * @param str 被分割的字符串
     *      * @param div  分割符
     *      * @return  list 被分割后的字符集合
     *     
     */
    public static List<String> diveString(String str, String div) {
        int index = str.indexOf(",");
        list.add(str.substring(0, index));
        String next = str.substring(index + 1);
        if (next.contains(",")) {
            diveString(next, div);
        }
        return list;
    }

}
