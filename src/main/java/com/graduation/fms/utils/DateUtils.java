package com.graduation.fms.utils;

import java.text.SimpleDateFormat;
import java.util.*;

public class DateUtils {

    /**
     * Date转年月日格式
     *
     * @param Date
     * @return String
     * @author
     */
    public static String D2NYR(Date date) throws Exception {
        SimpleDateFormat sj = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        return sj.format(date);
    }

    /**
     * Date转年月日格式
     *
     * @param Date
     * @return String
     * @author
     */
    public static String D2S(Date date) throws Exception {
        SimpleDateFormat sj = new SimpleDateFormat("yyyy-MM-dd");
        return sj.format(date);
    }

    /**
     * String转Date年月日格式
     *
     * @param String
     * @return Date
     * @author
     */
    public static Date S2D(String date) throws Exception {
        SimpleDateFormat sj = new SimpleDateFormat("yyyy-MM-dd");
        return sj.parse(date);
    }

    /**
     * Date转年月日格式
     *
     * @param Object
     * @return String
     * @author
     */
    public static String D2NYR(Object date) throws Exception {
        SimpleDateFormat sj = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        return sj.format(date);
    }

    /**
     * DateList转年月日格式 转string
     *
     * @param List<Date>
     * @return List<String>
     * @author
     */
    public static List<String> DL2SLNYR(List<Date> list) throws Exception {
        if (list.size()<=0) {
            return null;
        }
        ArrayList<String> sList = new ArrayList<>();
        for (Date date : list) {
            sList.add(D2S(date));
        }
        return sList;
    }

    /**
     * 获取从某天开始到昨天的list
     *
     * @param Date
     * @return List
     * @author
     */
    public static List<Date> getListD2Y(Date date) throws Exception {
        SimpleDateFormat sj = new SimpleDateFormat("yyyy-MM-dd");
        String D = sj.format(date);
        Date dateD = sj.parse(D);
        String N = sj.format(new Date());
        Date dateN = sj.parse(N);
        if (dateN.equals(dateD) || dateN.before(dateD)) {
            return null;
        } else {
            List<Date> list = new ArrayList<>();
            Calendar cal = Calendar.getInstance();
            cal.setTime(dateD);
            for (;cal.getTime().before(dateN);cal.add(Calendar.DATE, 1)) {
                list.add(cal.getTime());
            }
            return list;
        }
    }

    /**
     * 排序
     *
     * @param List<Date>
     * @return List<Date>
     * @author
     */
    public static void listSort(List<Date> list, int left, int right) throws Exception {
        if(left>right){
            return;
        }
        //设置最左边的元素为基准值
        Date key=list.get(left);
        //数组中比key小的放在左边，比key大的放在右边，key值下标为i
        int i=left;
        int j=right;
        while(i<j){
            //j向左移，直到遇到比key小的值
            while(key.compareTo(list.get(j))<=0 && i<j){
                j--;
            }
            //i向右移，直到遇到比key大的值
            while(key.compareTo(list.get(i))>=0 && i<j){
                i++;
            }
            //i和j指向的元素交换
            if(i<j){
                Date t = list.get(j);
                list.set(j,list.get(i));
                list.set(i,t);
            }
        }
        //最后将基准为与i和j相等位置的数字交换
        list.set(left,list.get(i));
        list.set(i,key);
        listSort(list, left, j-1);
        listSort(list, j+1, right);
    }
}
