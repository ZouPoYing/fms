package com.graduation.fms.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

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

    /**
     *根据 combineField字段 合并 money字段 money为BigDecimal类型
     * @param
     * @param dataList 原始数据
     * @return
     */
    public static List<Map<String, Object>> combineMap(List<Map<String, Object>> dataList, String combineField,  String money){
        List<Map<String, Object>> countList = new ArrayList<Map<String,Object>>();
        for (int i = 0; i < dataList.size(); i++) {
            String oldCombineField = String.valueOf(dataList.get(i).get(combineField));
            int flag = 0;
            for (int j = 0; j < countList.size(); j++) {
                String newCombineField = String.valueOf(countList.get(j).get(combineField));
                if (oldCombineField.equals(newCombineField)) {
                    BigDecimal afterSaleProfit = new BigDecimal(dataList.get(i).get(money).toString()).add(new BigDecimal(countList.get(j).get(money).toString()));
                    countList.get(j).put(money, afterSaleProfit);
                    flag = 1;
                    break;
                }
            }
            if (flag == 0) {
                countList.add(dataList.get(i));
            }
        }
        return countList;
    }
}
