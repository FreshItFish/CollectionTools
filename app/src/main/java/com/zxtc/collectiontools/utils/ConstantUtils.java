package com.zxtc.collectiontools.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 作者：KY
 * 创建时间：2018/4/9 10:50
 * 描述: 公共方法
 */

public class ConstantUtils {

    /**
     * 获取 资源文件数据
     * @param arrays
     * @return
     */
    public static List<String> getDate(String[] arrays) {
        List<String> date = new ArrayList<>();
        for (int i = 0; i < arrays.length; i++) {
            String array = arrays[i];
            date.add(array);
        }
        return date;
    }

    /**
     * 去除list集合中的重复元素
     * @param list 要去重的 list
     * @return 返回去重后的 list
     */
    public static List deleteRepeat(List list){
        List newList = new ArrayList<>();
        Set set = new HashSet();
        for (Object obj : list) {
            if(set.add(obj)){
                newList.add(obj);
            }
        }
        return newList;
    }
}
