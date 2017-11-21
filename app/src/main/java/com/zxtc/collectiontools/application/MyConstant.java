package com.zxtc.collectiontools.application;

import java.util.ArrayList;
import java.util.List;

/**
 * 全局函数
 */

public class MyConstant {

    public static String net_address = "http://192.168.51.55:8080/";

    public static List<String> getDate(String[] arrays) {
        List<String> date = new ArrayList<>();
        for (int i = 0; i < arrays.length; i++) {
            String array = arrays[i];
            date.add(array);
        }
        return date;
    }

}
