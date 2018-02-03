package com.lxy.livedata.utils;

import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

public class TimeUtil {

    private static String[] num = {"一", "二", "三", "四", "五", "六", "七", "八", "九"};
    private static HashMap<String, String> stringHashMap = new HashMap<>();


    /**
     * 从时间(毫秒)中提取出时间(时:分)
     * 时间格式:  时:分
     *
     * @param millisecond
     * @return
     */
    public static String getTimeFromMillisecond(Long millisecond) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        TimeZone tz = TimeZone.getDefault();
        simpleDateFormat.setTimeZone(tz);
        Date date = new Date(millisecond);
        String timeStr = simpleDateFormat.format(date);
        return timeStr;
    }

    /**
     * 将毫秒转化成固定格式的时间 年月日时分秒
     * 时间格式: yyyy-MM-dd HH:mm:ss
     *
     * @param millisecond
     * @return
     */
    public static String getDateTimeFromMillisecond(Long millisecond) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(millisecond);
        String dateStr = simpleDateFormat.format(date);
        return dateStr;
    }

    /**
     * 将毫秒转化成固定格式的时间 年月日
     * 时间格式: yyyy-MM-dd
     * 带时区
     * @param millisecond
     * @return
     */
    public static String fromartyyyyMMdd(Long millisecond) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        TimeZone tz = TimeZone.getDefault();
        simpleDateFormat.setTimeZone(tz);
        Date date = new Date(millisecond);
        String dateStr = simpleDateFormat.format(date);
        return dateStr;
    }
    /**
     * 将毫秒转化成固定格式的时间 年月日
     * 时间格式: yyyy-MM-dd HH:mm
     * 带时区
     * @param millisecond
     * @return
     */
    public static String fromartyyyyMMddHHmm(Long millisecond) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        TimeZone tz = TimeZone.getDefault();
        simpleDateFormat.setTimeZone(tz);
        Date date = new Date(millisecond);
        String dateStr = simpleDateFormat.format(date);
        return dateStr;
    }

    /**
     * 将毫秒转化成固定格式的时间 年月日时分
     * 时间格式: yyyy-MM-dd HH:mm
     *
     * @param millisecond
     * @return
     */
    public static String getDateTimeFromMilli(Long millisecond) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");
        Date date = new Date(millisecond);
        String dateStr = simpleDateFormat.format(date);
        return dateStr;
    }

    /**
     * 将时间转化成毫秒
     * 时间格式: yyyy-MM-dd HH:mm:ss
     *
     * @param time
     * @return
     */
    public static Long timeStrToSecond(String time) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Long second = format.parse(time).getTime();
            return second;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1L;
    }

    /**
     * 将时间转化成毫秒
     * 时间格式: yyyy-MM-ddTHH:mm:ssZ
     *
     * @param time
     * @return
     */
    public static Long timeTZStrToSecond(String time) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-ddTHH:mm:ssZ");
            Long second = format.parse(time).getTime();
            return second;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1L;
    }

    /**
     * 获取数字对应的中文名称
     */
    public static String getText4Num(int i) {
        if (i < 10) {
            return num[i - 1];
        } else if (i == 10) {
            return "十";
        } else if (i <= 19) {
            return "十" + num[i % 10 - 1];
        } else if (i > 19 && i < 99) {
            return num[i / 10 - 1] + "十" + (i % 10 == 0 ? "" : num[i % 10 - 1]);
        }
        return "";
    }


    public static String timeTZStrToChinese(String time) {
        stringHashMap.put("0", "零");
        stringHashMap.put("1", "一");
        stringHashMap.put("2", "二");
        stringHashMap.put("3", "三");
        stringHashMap.put("4", "四");
        stringHashMap.put("5", "五");
        stringHashMap.put("6", "六");
        stringHashMap.put("7", "七");
        stringHashMap.put("8", "八");
        stringHashMap.put("9", "九");
        String toString = "";
        int year = time.indexOf("-");
        int month = time.lastIndexOf("-");
        int day = time.indexOf("T");
        String yearstr = time.substring(2, year);
        String monstr = time.substring(year + 1, month);
        String daystr = time.substring(month + 1, day);
        char[] chars = yearstr.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            toString = toString + stringHashMap.get(String.valueOf(chars[i]));
        }
        String monthString = getText4Num(Integer.parseInt(getInt(monstr)));
        String dayString = getText4Num(Integer.parseInt(getInt(daystr)));
        return toString + "年" + monthString + "月" + dayString + "日";
    }

    public static String getInt(String s) {
        String substring = s.substring(0, 1);
        if (TextUtils.equals(substring, "0")) {
            String s1 = s.substring(1, 2);
            return s1;
        } else {
            return s;
        }
    }

    /**
     * 将时间转化成毫秒
     * 时间格式: yyyy-MM-ddTHH:mm:ssZ
     *
     * @param time
     * @return
     */
    public static Long timeTZStr2Second(String time) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            format.setTimeZone(TimeZone.getTimeZone("UTC"));
            Long second = format.parse(time).getTime();
            return second;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1L;
    }
}