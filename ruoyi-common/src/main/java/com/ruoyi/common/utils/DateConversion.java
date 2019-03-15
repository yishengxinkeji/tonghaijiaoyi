package com.ruoyi.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConversion {

    public static void main(String[] args) throws Exception {
        Date date = stringToDate("2019-03-15 10:16:39", "yyyy-MM-dd HH:mm:ss");

        String s = dateToString(new Date(), "yyyy-MM-dd HH:mm:ss");
        String substring = s.substring(0, 8);
        String s1 = substring + "23:59:59";
        System.out.println(s1);
        System.out.println(s);
        System.out.println(date);
    }
    /**
     * 字符串转Date方法
     * @param dateStr
     * @param format 如yyyy-MM-dd HH:mm:ss等
     * @return
     * @throws Exception
     *
     *
     */
    public static Date stringToDate(String dateStr, String format) throws Exception{
        if(dateStr==null||"".equals(dateStr)){
            throw new Exception("stringToDate:要转换的日期参数为空！");
        }
        Date date = null;
        try{
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            date = sdf.parse(dateStr);
        }catch(Exception e){
            e.printStackTrace();
        }

        return date;
    }
    /**
     * Date转字符串方法
     * @param date
     * @param format 如yyyy-MM-dd HH:mm:ss等
     * @return
     * @throws Exception
     */
    public static String dateToString(Date date,String format) throws Exception{
        if(date==null){
            throw new Exception("dateToString:要转换的日期参数为空！");
        }
        String dateStr = "";
        try{
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            dateStr = sdf.format(date);
        }catch(Exception e){
            e.printStackTrace();
        }

        return dateStr;
    }
}
