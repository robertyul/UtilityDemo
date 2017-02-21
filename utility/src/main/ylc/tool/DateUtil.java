package ylc.tool;


import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by robertpicyu on 2017/2/21.
 * 主要用法：(提取日期中的特定段时间 、 特定格式输出时间)
 * 1、String --> String
 * 2、String --> int
 */
public class DateUtil {
    public static final String YYMMDD = "yyMMdd";
    public static final String YYYYMMDD = "yyyyMMdd";
    public static final String YYMM = "yyMM";
    public static final String YYYYMM = "yyyyMM";
    public static final int ONE_DAY_MS = 24 * 3600 * 1000;

    public static Calendar toCalendar(Date date) {
        if (date == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c;
    }

    public static String toString(Calendar c, String format) {
        return toString(c.getTime(),format);

    }

    public static String toString(Date date, String format) {
        return getDateFormat(format).format(date);

    }

    public static DateFormat getDateFormat(String format) {
        return new SimpleDateFormat(format);
    }

    public static Date toDate(String date, String format) throws ParseException {
        if (date == null || date.trim().equals("")) {
            return null;
        }
        return getDateFormat(format).parse(date);
    }

    public static Date addDuration(Date date, int type, int amount) {
        if (date == null) {
            return null;
        }
        Calendar c = toCalendar(date);
        c.add(type, amount);
        return c.getTime();
    }

    public static Calendar toCalendar(String date, String format) throws ParseException {
        return toCalendar(toDate(date, format));
    }

    public static long getTotalDays(String startDate, String endDate) throws ParseException {
        Calendar start = toCalendar(startDate, YYMMDD);
        Calendar end = toCalendar(endDate, YYMMDD);
        return getTotalDays(start, end);
    }

    public static long getTotalDays(Calendar start, Calendar end) {
        return Math.abs((end.getTimeInMillis() - start.getTimeInMillis()) / ONE_DAY_MS) + 1;
    }


    public static Date getlastday(String yearandmonth) {
        String year = yearandmonth.substring(0, 4);
        String month = yearandmonth.substring(4);


        Calendar cal = Calendar.getInstance();
        // 不加下面2行，就是取当前时间前一个月的第一天及最后一天
        cal.set(Calendar.YEAR,Integer.parseInt(year));
        cal.set(Calendar.MONTH, Integer.parseInt(month));
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        Date lastDate = cal.getTime();
        return lastDate;
    }



    public static String getnextmonth(String yearandmonth, String format) {
        if(format.equals(YYMM)) {
            yearandmonth = "20"+yearandmonth;
        }
        String year = yearandmonth.substring(0, 4);
        String month = yearandmonth.substring(4);


        Calendar cal = Calendar.getInstance();

        cal.set(Calendar.YEAR,Integer.parseInt(year));
        cal.set(Calendar.MONTH, Integer.parseInt(month)-1);
        cal.add(Calendar.MONTH, 1);
        Date nextmonth = cal.getTime();
        return new SimpleDateFormat(format).format(nextmonth);
    }

    public static String getnextmonth(String yearandmonth) {
        String year = yearandmonth.substring(0, 4);
        String month = yearandmonth.substring(4);


        Calendar cal = Calendar.getInstance();

        cal.set(Calendar.YEAR,Integer.parseInt(year));
        cal.set(Calendar.MONTH, Integer.parseInt(month)-1);
        cal.add(Calendar.MONTH, 1);
        Date nextmonth = cal.getTime();
        return new SimpleDateFormat("yyyyMM").format(nextmonth);
    }

    public static int getWeekDay(String date) throws ParseException {
        Calendar start = DateUtil.toCalendar(date, "yyMMdd");
        return (start.get(Calendar.DAY_OF_WEEK)-1) == 0 ? 7 : start.get(Calendar.DAY_OF_WEEK)-1;
    }

    public static int getWeekDay(long time) throws ParseException {
        Calendar start = Calendar.getInstance();
        start.setTimeInMillis(time);
        return (start.get(Calendar.DAY_OF_WEEK)-1) == 0 ? 7 : start.get(Calendar.DAY_OF_WEEK)-1;
    }

    public static int getWeekDay(Date date) throws ParseException {
        Calendar start = Calendar.getInstance();
        start.setTime(date);
        return (start.get(Calendar.DAY_OF_WEEK)-1) == 0 ? 7 : start.get(Calendar.DAY_OF_WEEK)-1;
    }


    public static String getmonth(String date, String format) {
        if(format.equals(YYMMDD)) {
            return date.substring(0,4);
        } else {
            return date.substring(0,6);
        }
    }

    /*获得当月最后一天*/
    public static String getmonthendday(String date, String format) throws ParseException {
        Calendar calendar = toCalendar(date,format);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        return toString(calendar,format);
    }

    public static String getyesterday(String date, String format) {



        Calendar calendar = null;
        try {
            calendar = toCalendar(date,format);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        calendar.add(Calendar.DAY_OF_MONTH, -1);

        return toString(calendar,format);
    }

    public static String getnextday(String day, String format) {
        Date dateday = null;
        try {
            dateday = DateUtil.toDate(day, format);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateday);

        cal.add(Calendar.DAY_OF_MONTH, 1);
        String dd = DateUtil.toString(cal.getTime(),format);
        return dd;
    }

    public static Date getToday(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static Date getYesterday(){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, -1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static Long getTimeStampFromDateStr(String dateTime) {
        try {
            Timestamp timestamp = Timestamp.valueOf(dateTime);
            return timestamp.getTime();

        } catch (Exception e) {
            return 0L;
        }
    }

    public static Integer getDaysOfMonth(String month) throws ParseException {
        Date date = toDate(month, YYYYMM);
        return getDaysOfMonth(date);
    }

    public static Integer getDaysOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.getActualMaximum(Calendar.DATE);
    }

    /**
     * 获取两个输入日期之间的所有月份
     * @param minDate 开始日期如：201601
     * @param maxDate  结束如期 如：201603
     * @return 返回[201601,201602,201603]
     * @throws ParseException
     */

    public static List<String> getMonthBetween(String minDate, String maxDate) throws ParseException {
        List<String> result = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");//格式化为年月

        Calendar min = Calendar.getInstance();
        Calendar max = Calendar.getInstance();

        min.setTime(sdf.parse(minDate));
        min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);

        max.setTime(sdf.parse(maxDate));
        max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);

        Calendar curr = min;
        while (curr.before(max)) {
            result.add(sdf.format(curr.getTime()));
            curr.add(Calendar.MONTH, 1);
        }

        return result;
    }

    public static void main(String[] args)  {

    }
}
