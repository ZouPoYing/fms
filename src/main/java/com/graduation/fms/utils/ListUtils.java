package com.graduation.fms.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ListUtils {

    /**
     * 移除list1中含有list2的元素
     *
     * @param List<T>
     * @return List<T>
     * @author
     */
    public static <T> List<T> listrem(List<T> listA, List<T> listB){
        HashSet hs1 = new HashSet(listA);
        HashSet hs2 = new HashSet(listB);
        hs1.removeAll(hs2);
        List<T> listC = new ArrayList<T>();
        listC.addAll(hs1);
        return listC;
    }
}
