package com.project.mgjandroid.utils;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 类说明： 日期时间工具类
 */
@SuppressLint("SimpleDateFormat")
public class DateUtils {
    /**
     * 默认日期格式
     */
    private static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";

    /**
     * @param pubtime 样例：2011-06-20T17:23:11Z
     * @return 样例：05-10 17:11
     */
    public static String getFormatTime(String pubtime) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        Date date = null;
        try {
            date = df.parse(pubtime.replace("Z", ""));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return (null == date) ? null : (new SimpleDateFormat("MM-dd HH:mm"))
                .format(date);
    }

    /**
     * @param pubtime 样例：2014-08-08 18:30:40
     * @return 样例：05-10 17:11
     */
    public static String getFormatTime2(String pubtime) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date date = null;
        try {
            date = df.parse(pubtime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return (null == date) ? null : (new SimpleDateFormat("MM-dd HH:mm"))
                .format(date);
    }

    /**
     * @param pubtime 样例：2011-06-20T17:23:11Z
     * @param format
     * @return
     */
    public static String getFormatTime(String pubtime, String format) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        Date date = null;
        try {
            date = df.parse(pubtime.replace("Z", ""));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return (null == date) ? null : (new SimpleDateFormat(format))
                .format(date);
    }

    /**
     * @param pubtime 样例：2011-06-20 17:23:11Z
     * @param format
     * @return
     */
    public static String getFormatTime1(String pubtime, String format) {
        if (TextUtils.isEmpty(pubtime)) {
            return "";
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date date = null;
        try {
            date = df.parse(pubtime.replace("Z", ""));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return (null == date) ? null : (new SimpleDateFormat(format))
                .format(date);
    }

    /**
     * 字符串转换时间戳
     *
     * @param str
     * @return
     */
    public static Timestamp str2Timestamp(String str, String fromat) {
        Date date = str2Date(str, fromat);
        return new Timestamp(date.getTime());
    }

    /**
     * 比较字符串时间是否在指定时间之后
     */
    public static boolean compareTimeBefore(String str, Long time) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.parse(str).getTime() > time;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //1525968000  2018/5/11 之后
    public static boolean compareTimeBefore(String str) {
        return compareTimeBefore(str, 1525968000000L);
    }

    //1525968000  2018/5/11
    public static boolean compareTimeBefore(Date date) {
        return date.getTime() > 1525968000000L;
    }


    /**
     * 获取两个时间戳之间差值,并且返回小时
     */
    public static int subTimeStamp2Hour(Timestamp one, Timestamp two) {
        int minute = subTimeStamp2Minute(one, two);
        return minute / 60;
    }

    /**
     * 获取两个时间戳之间差值,并且返回分钟
     */
    public static int subTimeStamp2Minute(Timestamp one, Timestamp two) {
        return (int) (two.getTime() - one.getTime()) / (1000 * 60);
    }

    /**
     * 获取两个时间戳之间差值,并且返回秒
     */
    public static int subTimeStamp2Second(Timestamp one, Timestamp two) {
        int minute = subTimeStamp2Minute(one, two);
        return minute * 60;
    }

    /**
     * Date转Timestamp
     *
     * @param pubtime
     * @return
     */
    public static Timestamp string2Timestamp(String pubtime) {
        Timestamp timestamp = null;

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        Date date = null;
        try {
            date = df.parse(pubtime.replace("Z", ""));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (null != date) {
            SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = df1.format(date);
            timestamp = Timestamp.valueOf(time);
        }

        return timestamp;
    }

    /**
     * 获取用于显示的时间
     *
     * @param pubtime
     * @return
     */
    public static String getTime(String pubtime) {
        String displayTime = getFormatTime(pubtime);
        Timestamp current = new Timestamp(System.currentTimeMillis());
        Timestamp pubTimestamp = DateUtils.string2Timestamp(pubtime);

        if (null != pubTimestamp) {
            int second = subTimeStamp2Second(pubTimestamp, current);
            if (second < 60 && second > 0) {
                displayTime = second + "秒前";
            } else {
                int minute = subTimeStamp2Minute(pubTimestamp, current);
                if (minute < 60 && minute > 0) {
                    displayTime = minute + "分钟前";
                } else {
                    int hour = subTimeStamp2Hour(pubTimestamp, current);

                    if (hour < 24 && hour > 0) {
                        displayTime = hour + "小时前";
                    }
                }
            }
        }

        return displayTime;
    }

    /**
     * 判断当前时间是否属于营业时间
     *
     * @param currentTime like "08:08"
     * @param workTime    like "8:00-10:00,12:00-14:00"
     * @return
     */
    public static boolean isBusinessTime(String currentTime, String workTime) {
        String works[] = workTime.split(",");
        if (works.length == 1) {
            String times[] = works[0].split("-");
            return isIn(currentTime, times);
        } else {
            for (String s : works) {
                String times[] = s.split("-");
                if (isIn(currentTime, times)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 当前时间是否在times范围内
     *
     * @param current
     * @param times
     * @return
     */
    private static boolean isIn(String current, String[] times) {
        String currents[] = current.split(":");
        String start[] = times[0].split(":");
        String end[] = times[1].split(":");
        if (Integer.parseInt(currents[0]) < Integer.parseInt(start[0]) || Integer.parseInt(currents[0]) > Integer.parseInt(end[0])) {
            return false;
        } else if (Integer.parseInt(currents[0]) == Integer.parseInt(start[0]) && Integer.parseInt(currents[1]) < Integer.parseInt(start[1])) {
            return false;
        } else if (Integer.parseInt(currents[0]) == Integer.parseInt(end[0]) && Integer.parseInt(currents[1]) > Integer.parseInt(end[1])) {
            return false;
        }
        return true;
    }

    /**
     * 字符串转换成日期 如果转换格式为空，则利用默认格式进行转换操作
     *
     * @param str    字符串
     * @param format 日期格式
     * @return 日期
     * @throws ParseException
     */
    public static Date str2Date(String str, String format) {
        Date date = null;

        // 如果没有指定字符串转换的格式，则用默认格式进行转换
        if (null == format || "".equals(format)) {
            format = DEFAULT_FORMAT;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            date = sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    /**
     * 时间字符串转换为指定格式
     *
     * @param date
     * @param format
     * @return
     */
    public static String strToStrByFormat(String date, String format) {
        String dateString = "";
        if (null == format || "".equals(format)) {
            format = DEFAULT_FORMAT;
        }
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(format);
            Date mDate = str2Date(date, format);
            dateString = formatter.format(mDate);
        } catch (Exception e) {
            e.getMessage();
        }
        return dateString;
    }

    /**
     * 两个格林时间差
     *
     * @param date1 当前时间
     * @param date2 过去时间
     * @return
     */
    public static String compressData(long date1, long date2) {
        String strDate = "";
        if (date1 == 0 || date2 == 0) {
            // strDate = String.valueOf(0)+" 秒钟前";
            strDate = "正在";
        } else {
            long cmDate = date1 - date2;
            long mDate = cmDate / 1000; // 将毫秒转为 秒
            if (mDate >= 60) {
                long minDate = mDate / 60;// 将秒转化为分钟
                if (minDate >= 60) {
                    long hourDate = minDate / 60;// 将分钟转化为小时
                    if (hourDate >= 24) {
                        long dayDate = hourDate / 24;
                        if (dayDate == 0) {
                            dayDate = 1;
                        }
                        strDate = String.valueOf(dayDate) + " 天前";
                    } else {
                        if (hourDate == 0) {
                            hourDate = 1;
                        }
                        strDate = String.valueOf(hourDate) + " 小时前";
                    }
                } else {
                    if (minDate == 0) {
                        minDate = 1;
                    }
                    strDate = String.valueOf(minDate) + " 分钟前";
                }
            } else {
                if (mDate == 0) {
                    mDate = 1;
                }
                strDate = String.valueOf(mDate) + " 秒钟前";
            }
        }
        return strDate;
    }

    /**
     * 显示时间为 几秒前，几分钟前，几小时前，几天前，几月前，几年前的实现
     *
     * @param date
     * @return
     */
    private static final long ONE_MINUTE = 60000L;

    private static final long ONE_HOUR = 3600000L;

    private static final long ONE_DAY = 86400000L;

    private static final long ONE_WEEK = 604800000L;

    private static final String ONE_SECOND_AGO = "秒前";

    private static final String ONE_MINUTE_AGO = "分钟前";

    private static final String ONE_HOUR_AGO = "小时前";

    private static final String ONE_DAY_AGO = "天前";

    private static final String ONE_MONTH_AGO = "月前";

    private static final String ONE_YEAR_AGO = "年前";

    public static String format(Date date, Date serviceTime) {

        long delta = serviceTime.getTime() - date.getTime();

        if (delta < 1L * ONE_MINUTE) {

            long seconds = toSeconds(delta);

            return (seconds <= 0 ? 1 : seconds) + ONE_SECOND_AGO;

        }

        if (delta < 45L * ONE_MINUTE) {

            long minutes = toMinutes(delta);

            return (minutes <= 0 ? 1 : minutes) + ONE_MINUTE_AGO;

        }

        if (delta < 24L * ONE_HOUR) {

            long hours = toHours(delta);

            return (hours <= 0 ? 1 : hours) + ONE_HOUR_AGO;

        }

        if (delta < 7L * ONE_DAY) {

            long days = toDays(delta);

            return (days <= 0 ? 1 : days) + ONE_DAY_AGO;

//		}
//
//		if (delta < 12L * 4L * ONE_WEEK) {
//
//			long months = toMonths(delta);
//
//			return (months <= 0 ? 1 : months) + ONE_MONTH_AGO;

        } else {

//			long years = toYears(delta);
//
//			return (years <= 0 ? 1 : years) + ONE_YEAR_AGO;
            return CommonUtils.formatTime(date.getTime(), "yyyy-MM-dd");

        }

    }

    private static long toSeconds(long date) {

        return date / 1000L;

    }

    private static long toMinutes(long date) {

        return toSeconds(date) / 60L;

    }

    private static long toHours(long date) {

        return toMinutes(date) / 60L;

    }

    private static long toDays(long date) {

        return toHours(date) / 24L;

    }

    private static long toMonths(long date) {

        return toDays(date) / 30L;

    }

    private static long toYears(long date) {

        return toMonths(date) / 365L;

    }

    public static String transRelativeTime(String publishtimeStr) {
        SimpleDateFormat format = new SimpleDateFormat(
                "yyyy-MM-dd'T'hh:mm:ss'Z'");
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date;
        String tempTime = "";
        try {
            if (publishtimeStr.contains("T") && publishtimeStr.endsWith("Z")) {
                date = format.parse(publishtimeStr);
            } else {
                date = format2.parse(publishtimeStr);
            }
            tempTime = DateUtils.compressData(new Date().getTime(),
                    date.getTime());
            if (tempTime != null && tempTime.contains("天前")) {
                int t = Integer.parseInt(String.valueOf(
                        tempTime.substring(0, tempTime.indexOf("天"))).trim());
                if (t > 7) {
                    tempTime = getFormatTime(publishtimeStr, "yyyy-MM-dd");
                }
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return tempTime;
    }
}
