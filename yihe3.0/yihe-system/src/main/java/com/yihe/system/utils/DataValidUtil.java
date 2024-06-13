package com.yihe.system.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * 
 *  
 * @author terryhuang
 * @date 2017年6月2日 上午9:23:53
 */
public final class DataValidUtil {
    private static final BigDecimal MIN_DAILYTPUT = new BigDecimal("0.00");
    private static final String DECIMAL_FORMAT = "#,###,###.##";

    public static final String DOUBLE_FORMAT  ="####0.000";
    public static final int MAX_YEAR = 9999;
    public static final int MIN_YEAR = 1753; 
    public static final int YEAR = 0;
    public static final int MONTH = 0;
    public static final int DAY = 0;

    public static String convertDateStr(String pDate, String FromFormat,
                                        String toFormat) throws Exception {
        if (!DataValidUtil.validateStrToDate(pDate, FromFormat)) {
            //throw new Exception(eXpMessage.I_DATEFORMAT);
        }
        Date d = setStrToDate(pDate, FromFormat);
        String datestr = setDateToStr(d, toFormat);
        return datestr;
    }

    // Validate date to string.
    //
    public static boolean validateDateToStr(Date date, String timeZone) {
        boolean bValid = false;

        try {
            String dateString = setDateToStr(date, timeZone);
            if (dateString != null) {
                Date tmpDate = setStrToDate(dateString, timeZone);
                String tmpDateString = setDateToStr(tmpDate, timeZone);
                if (dateString.equals(tmpDateString)) {
                    bValid = true;
                }
            }
        } catch (Exception e) {
        }

        return bValid;
    }

    //
    // Validate string to date.
    //
    public static boolean validateStrToDate(String dateString) {
        return validateStrToDate(dateString, DateTimeUtil.DATE_FORMAT_DAY);
    }

    public static boolean validateStrToDate(String dateString, String patten) {
        return validateStrToDate(dateString, patten,
                                 TimeZone.getDefault().getID());
    }

    public static boolean validateStrToDate(String dateString, String patten,
                                            String timeZone) {
        boolean bValid = false;

        try {
            Date date = setStrToDate(dateString, patten, timeZone);
            String tmpDateString = setDateToStr(date, patten, timeZone);
            if (dateString.equals(tmpDateString)) {
                bValid = true;
            }
        } catch (Exception e) {
        }

        return bValid;
    }

    public static String setDateToStr(Date date) {
        return setDateToStr(date, DateTimeUtil.DATE_FORMAT_DAY);
    }

    public static String setDateToStr(Date date, String patten) {
        return setDateToStr(date, patten, TimeZone.getDefault().getID());
    }

    //
    // Set date to string. (Any date format can be passed.)
    public static String setDateToStr(Date date, String pDateFormat,
                                      String timeZone) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pDateFormat);
        dateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
        String dateString = dateFormat.format(date);
        return dateString;
    }

    /**
     * 把串转化为日期
     * @param dateString String
     * @return Date
     */
    public static Date setStrToDate(String dateString) {
        return setStrToDate(dateString, DateTimeUtil.DATE_FORMAT_DAY);
    }

    public static Date setStrToDate(String dateString, String patten) {
        return setStrToDate(dateString, patten, TimeZone.getDefault().getID());
    }

    public static Date setStrToDate(String dateString, String patten,
                                    String timeZone) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(patten);
        dateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
        dateFormat.setLenient(false);
        ParsePosition pos = new ParsePosition(0);
        Date date = dateFormat.parse(dateString, pos);

        return date;
    }

    //
    // Set integer to string.
    //
    public static String setIntToStr(int i) {
        String str = "";

        String s = Integer.toString(i);
        if (s != null) {
            str = s;
        }

        return str;
    }

    //
    // Set float to integer.
    //
    public static int setFloatToInt(float f) {
        int i = 0;

        int j = Float.floatToIntBits(f);
        i = j;

        return i;
    }

    public static String setLongToStr(long l) {
        String str = "";

        String s = Long.toString(l);
        if (s != null) {
            str = s;
        }

        return str;
    }

    //
    // Set string to integer.
    //
    public static int setStrToInt(String s) {
        int i = 0;

        int j = Integer.parseInt(s);
        i = j;

        return i;

    }

    //
    // Set string to float.
    //
    public static float setStrToFloat(String s) {
        float i = 0;

        float j = Float.valueOf(s).floatValue();
        i = j;

        return i;

    }

    //
    // Set string to long.
    //
    public static long setStrToLong(String s) {
        long i = 0;

        long j = Long.valueOf(s).longValue();
        i = j;

        return i;

    }

    public static String formatDouble(double number ,String pattern){
        return new DecimalFormat(pattern).format(number);
    }

    private String formatDecimal(BigDecimal decimal, int scale) {
        String formatStr = "";
        String returnStr = "";

        if (decimal == null) {
        } else {
            if (scale > 0) {
                formatStr = "#,###,###.";
                for (int i = 0; i < scale; i++)
                    formatStr = formatStr + "#";
            } else
                formatStr = "#,###,###";
            DecimalFormat dFormat = new DecimalFormat(formatStr);
            try {
                if (decimal.doubleValue() == 0)
                    returnStr = decimal.toString();
                else
                    returnStr = dFormat.format(decimal.doubleValue()).toString();
            } catch (IllegalArgumentException e) {

            }
            if (returnStr.trim().equals("")) {
                returnStr = " ";
            } else {
                if (scale > 0) {
                    returnStr = "0.";
                    for (int i = 0; i < scale; i++)
                        returnStr = returnStr + "0";
                } else
                    returnStr = "0";
                if (returnStr.trim().indexOf(".") == 0)
                    returnStr = "0" + returnStr;
            }
        }
        return returnStr;
    }

    public static long getDBTimeStamp() {
        Date aDate = new Date();
        return aDate.getTime();
    }

    public static final boolean valiDate(String day, String month, String year) {
        SimpleDateFormat formatter = new SimpleDateFormat(DateTimeUtil.DATE_FORMAT_DAY);
        try {
            String testDate = day.trim() + "/" + month.trim() + "/" + year.trim();
            formatter.setLenient(false);
            Date date = formatter.parse(testDate);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static final Date getFormatDate(String date) { //throws eXpExceptionHandle
        SimpleDateFormat formatter = new SimpleDateFormat(DateTimeUtil.DATE_FORMAT_DAY);
        Date date1 = null;
        try {
            formatter.setLenient(false);
            date1 = formatter.parse(date);
        } catch (Exception e) {
            //throw new eXpExceptionHandle(e.toString());
        }
        return date1;
    }

    //For DATE_FORMAT = "dd/MM/yyyy HH:mm";
    public static final Date getDateFormatDate(Date date) { //throws eXpExceptionHandle
        String strDate = setDateToStr(date,
                                      DateTimeUtil.DATE_FORMAT);

        return setStrToDate(strDate, DateTimeUtil.DATE_FORMAT);
    }

    //For DATE_FORMAT_DAY = "dd/MM/yyyy";
    public static final Date getDateFormatDayDate(Date date) { //throws eXpExceptionHandle
        String strDate = setDateToStr(date,
                                      DateTimeUtil.DATE_FORMAT_DAY);

        return setStrToDate(strDate,
                            DateTimeUtil.DATE_FORMAT_DAY);
    }

//    //For DATE_FORMAT_WITH_SECOND = "dd/MM/yyyy HH:mm:ss",no useful,direct use new java.util.Date();
//    public static final Date getDateFormatWithSecondDate(Date date) { //throws eXpExceptionHandle
//        String strDate = setDateToStr(date,
//                                      DateTimeUtil.DATE_FORMAT_WITH_SECOND);
//
//        return setStrToDate(strDate,
//                            DateTimeUtil.DATE_FORMAT_WITH_SECOND);
//    }


    public static final boolean valiDate(String month, String year) {
        SimpleDateFormat formatter = new SimpleDateFormat(DateTimeUtil.DATE_FORMAT_DAY);
        try {
            String testDate = "01/" + month.trim() + "/" + year.trim();
            formatter.setLenient(false);
            Date date = formatter.parse(testDate);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static final boolean valiDate(String year) {
        SimpleDateFormat formatter = new SimpleDateFormat(DateTimeUtil.DATE_FORMAT_DAY);
        try {
            String testDate = "01/01/" + year.trim();
            formatter.setLenient(false);
            Date date = formatter.parse(testDate);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static boolean validInt(String intStr) {
        int intNum;

        if (intStr.trim().equals(""))
            return false;
        else {
            try {
                intNum = Integer.valueOf(intStr).intValue();
            } catch (Exception e) {
                return false;
            }
            return true;
        }
    }

    public static String replace(String content, String placeholder, String s) {
        if (placeholder == null)
            return content;
        if (content == null)
            return content;
        int i = content.indexOf(placeholder);
        if (i < 0)
            return content;
        return content.substring(0, i) + ( (s == null) ? "" : s) +
                content.substring(i + placeholder.length());
    }

    public static String replaceAll(String content, String placeholder,
                                    String s) {
        if (placeholder == null)
            return content;
        if (content == null)
            return content;
        int i = content.indexOf(placeholder);
        while (i != -1) {
            content = content.substring(0, i) + ( (s == null) ? "" : s) +
                    content.substring(i + placeholder.length());
            i = content.indexOf(placeholder);
        }
        return content;
    }
    
    /**
     * 可以处理浏览器中带AM,PM字样
	 * 处理为可用日期，terry20160829added
	 * @param jsDateStr
	 * @return
	 */
	public static String handleJsStr(String jsDateStr){
		String result1=jsDateStr;
		if(jsDateStr!=null && jsDateStr.indexOf(" AM")!=-1){
			result1=jsDateStr.substring(0,jsDateStr.indexOf(" AM"));
		}else if(jsDateStr!=null && jsDateStr.indexOf(" PM")!=-1){
			result1=jsDateStr.substring(0,jsDateStr.indexOf(" PM"));
		}else if(jsDateStr!=null && jsDateStr.indexOf(" am")!=-1){
			result1=jsDateStr.substring(0,jsDateStr.indexOf(" am"));
		}else if(jsDateStr!=null && jsDateStr.indexOf(" pm")!=-1){
			result1=jsDateStr.substring(0,jsDateStr.indexOf(" pm"));
		}
		
		String news="";
		String all="0123456789- :";
		if(result1!=null && !"".equals(result1))
		for(int i=0;i<result1.length();i++){
			if(all.indexOf(result1.charAt(i))!=-1){
				news+=result1.charAt(i);
			}
		}
		
		if(jsDateStr!=null && (jsDateStr.indexOf(" PM")!=-1 || jsDateStr.indexOf(" pm")!=-1)){
			Date pmdate=DataValidUtil.setStrToDate(news,DateTimeUtil.AISAT_DATE_FORMAT_WITH_SECOND);
			Calendar cal = Calendar.getInstance();   
	        cal.setTime(pmdate);   
	        cal.add(Calendar.HOUR, 12);// 24小时制   
	        pmdate = cal.getTime();  
	        
	        news = DataValidUtil.setDateToStr(pmdate, DateTimeUtil.AISAT_DATE_FORMAT_WITH_SECOND);
		}
		
		return news;
	}

    public static boolean validF4Date(String dd, String mm, String yyyy,
                                      String hhmi) {
        if (dd == null || dd.trim().equals("") || mm == null ||
            mm.trim().equals("") ||
            yyyy == null || yyyy.trim().equals("") || hhmi == null ||
            hhmi.trim().equals("")) {
            return false;
        }
        //2002-11-20
        try {
            int iDay = Integer.parseInt(dd);
            int iMonth = Integer.parseInt(mm);
            int iYear = Integer.parseInt(yyyy);
            int iTime = Integer.parseInt(hhmi);
        } catch (Exception ex) {
            return false;
        }
        String dateFormat = DateTimeUtil.DATE_FORMAT_DAY + "HHmm";
        return validateStrToDate(dd + "/" + mm + "/" + yyyy + hhmi, dateFormat);
    }
    
	public static int getMaxDayByYearMonth(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year - 1);
		calendar.set(Calendar.MONTH, month);
		return calendar.getActualMaximum(Calendar.DATE);
	}
	
	public static int getMaxDayByYearMonth(String year,String month) {
		int y=1970;
		int m=1;
		try {
			y = Integer.parseInt(year);
			m = Integer.parseInt(month);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		
		return getMaxDayByYearMonth(y,m);
	}

    public static void main(String[] args){
      //String d = "2006-06-13 23:59:59";
      //String d="2006-06-13 14‎:‎27‎:48";//不显示 
      String d="2006-06-13 16‎:‎23‎:11"; //能显示
      //String d="2006-06-13 14:27:48";//能显示
      if(DataValidUtil.validateStrToDate(d,DateTimeUtil.AISAT_DATE_FORMAT_WITH_SECOND)){
        Date date = DataValidUtil.setStrToDate(d, DateTimeUtil.AISAT_DATE_FORMAT_WITH_SECOND);

        System.out.println("Test date : " + date);
      }
    }
}