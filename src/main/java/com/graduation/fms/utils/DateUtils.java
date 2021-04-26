package com.graduation.fms.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    /**
     * Date转年月日格式
     *
     * @param Date
     * @return String
     * @author
     */
    public static String D2NYR(Date date) {
        SimpleDateFormat sj = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        return sj.format(date);
    }
}
