package com.yihe.system.utils;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * <p>Title: Eaton Timesheet</p>
 *
 * <p>Description: Timesheet -- Eaton日志系统</p>
 *
 * <p>Copyright: Copyright (c) 2007 eaton.com</p>
 *
 * <p>Company: 山特电子（深圳）有限公司</p>
 *
 * @author Terry Aug 11, 2009
 * @version 1.0
 */
public class DateTimeUtil {
	  public final static String AISAT_DATE_FORMAT_DAY = "yyyy-MM-dd";
	  public final static String AISAT_DATE_FORMAT_WITH_SECOND ="yyyy-MM-dd HH:mm:ss";

	  public final static String DATE_FORMAT = "dd/MM/yyyy HH:mm";
	  public final static String DATE_FORMAT_DAY = "dd/MM/yyyy";
	  public final static String DATE_FORMAT_WITH_SECOND = "dd/MM/yyyy HH:mm:ss";
	  public final static String SANTAK_DATE_FORMAT_WITH_SECOND = "yyyy-MM-dd HH:mm:ss";
	  public final static String DBBACKUP_DATE_FORMAT_WITH_SECOND = "yyyyMMdd_HH_mm_ss";
	  public final static String WORKLOG_DATE_FORMAT_DAY = "yyyyMMdd";
	  
//	  final static String DATE_FORMAT_STR = "yyyyMMddHHmmss";
//	  final static String DATE_FORMAT_DISP_STR = "dd/MM/yyyy hh24:mi";
//	  final static String DATE_FORMAT_SQL_STR = "yyyy/mm/ddhh24miss";
//	  final static String DEFAULT_TIME_DISP_STR = "hh24:mi:ss";
	  private GregorianCalendar objCalendar = null;
	  private final int DAY_OF_DATE_IS_NULL = 1;
	  private final int MONTH_OF_DATE_IS_NULL = 2;
	  private final int YEAR_OF_DATE_IS_NULL = 3;
	  private final int DAY_OF_DATE_INCORRECT = 4;
	  private final int MONTH_OF_DATE_INCORRECT = 5;
	  private final int YEAR_OF_DATE_LEN_INCORRECT = 6;
	  private final int TIME_OF_DATE_INCORRECT = 7;
	  private final int NULL_OF_DATE_VALUE = 100;
	  private boolean isNull = false;
	  private String strLoggerHeader = new String();
//	    private final Log log = LogFactory.getLog(getClass());


	  /**
	   * Default constructor.
	   */
	  public DateTimeUtil() {
	    strLoggerHeader = "[" + getClass().getName() + "] ";
	    Locale.setDefault(Locale.getDefault());
	    objCalendar = new GregorianCalendar();
	    objCalendar.setTime(new Date());
	  }

	  /**
	   * Add some days to current GregorianCalendar's object. If the number value
	   * less than zero. The GregorianCalendar's object will return the previous
	   * date object. If the number value more than zero. The GregorianCalendar's
	   * object will return the later date object.
	   * @param
	   * @return void
	   * @throws
	   */
	  public void addSomeDays(int nDays) {
	    try {
	      objCalendar.add(GregorianCalendar.DAY_OF_MONTH, nDays);
	    }
	    catch (Exception ex) {
//	          log.error(strLoggerHeader +
//	                             "[addSomeDays] The error is : \r\n" + ex);
	    }
	  }

	  /**
	   * 现在时间上加上nSeconds秒
	   * @param nSeconds int
	   */
	  public void addSomeSeconds(int nSeconds) {
	    try {
	      objCalendar.add(GregorianCalendar.SECOND, nSeconds);
	    }
	    catch (Exception ex) {
//	          log.error(strLoggerHeader +
//	                             "[addSomeDays] The error is : \r\n" + ex);
	    }
	  }

	  /**
	   * Get GregorianCalendar instance.
	   * @param
	   * @return GregorianCalendar
	   * @throws
	   */
	  public void setDateTime(Date objDate) {
	    Locale.setDefault(Locale.getDefault());
	    objCalendar = new GregorianCalendar();
	    objCalendar.setTime(objDate);
	  }

	  /**
	   * Get GregorianCalendar instance.
	   * @param
	   * @return GregorianCalendar
	   * @throws
	   */
	  public GregorianCalendar getCalendar() {
	    return objCalendar;
	  }

	  /**
	   * Judge whether of two GregorianCalendar is more later.
	   * @param objFromCalendar, the first GregorianCalendar's object.
	   * @param objToCalendar, the second GregorianCalendar's object.
	   * @return boolean
	   * @throws
	   */
	  public boolean isAfter(GregorianCalendar objFromCalendar,
	                         GregorianCalendar objToCalendar) {
	    return objToCalendar.after(objFromCalendar);
	  }

	  /**
	   * Get the minus value of two Date object.
	   * @param objFromCalendar, the first GregorianCalendar's object.
	   * @param objToCalendar, the second GregorianCalendar's object.
	   * @return int
	   * @throws
	   */
	  public int getTwoDateMinusABSNumber(GregorianCalendar objFromCalendar,
	                                      GregorianCalendar objToCalendar) {
	    long lResult = 0;
	    if (objFromCalendar.after(objToCalendar)) {
	      lResult = objFromCalendar.getTime().getTime() -
	          objToCalendar.getTime().getTime();
	    }
	    else {
	      lResult = objToCalendar.getTime().getTime() -
	          objFromCalendar.getTime().getTime();
	    }
	    lResult /= 1 * 24 * 60 * 60 * 1000;

	    return (int) lResult;
	  }

	  /**
	   * set date value with unit value.
	   * @param strYear, year value for date.
	   * @param strMonth, month value for date.
	   * @param strDay, day value for date.
	   * @param strHour, hour value for date.
	   * @param strMinute, minute value for date.
	   * @param strSecond, second value for date.
	   * @return int
	   * @return int
	   * @throws
	   */
	  public int setDateTimeValue(String strYear, String strMonth, String strDate,
	                              String strHour, String strMinute,String strSecond) {
	    int iResult = validateDateValue(strYear, strMonth, strDate,
	                                    strHour, strMinute, strSecond);
	    if (0 == iResult) {
	      try {
	        objCalendar.set(Integer.parseInt(strYear),
	                        Integer.parseInt(strMonth) - 1,
	                        Integer.parseInt(strDate),
	                        Integer.parseInt(strHour),
	                        Integer.parseInt(strMinute),
	                        Integer.parseInt(strSecond));
	      }
	      catch (Exception ex) {
//	              log.error(strLoggerHeader +
//	                                 "[setDateTimeValue] The error is : \r\n" + ex);
	      }
	    }

	    return iResult;
	  }

	  /**
	   * set date value with unit value.
	   * @param strYear, year value for date.
	   * @param strMonth, month value for date.
	   * @param strDay, day value for date.
	   * @param strHour, hour value for date.
	   * @param strMinute, minute value for date.
	   * @return int
	   * @return int
	   * @throws
	   */
	  public int setDateTimeValue(String strYear, String strMonth, String strDay,
	                              String strHour, String strMinute) {
	    int iResult = validateDateValue(strYear, strMonth, strDay, strHour,
	                                    strMinute, "00");
	    if (0 == iResult) {
	      try {
	        objCalendar.set(Integer.parseInt(strYear),
	                        Integer.parseInt(strMonth) - 1,
	                        Integer.parseInt(strDay),
	                        Integer.parseInt(strHour),
	                        Integer.parseInt(strMinute));
	      }
	      catch (Exception ex) {
//	              log.error(strLoggerHeader +
//	                                 "[setDateTimeValue] The error is : \r\n" + ex);
	      }
	    }

	    return iResult;
	  }

	  /**
	   * set date value with unit value.
	   * @param strYear, year value for date.
	   * @param strMonth, month value for date.
	   * @param strDay, day value for date.
	   * @return int
	   * @throws
	   */
	  public int setDateTimeValue(String strYear, String strMonth, String strDay) {
	    int iResult = validateDateValue(strYear, strMonth, strDay, "0000");
	    if (0 == iResult) {
	      try {
	        objCalendar.set(Integer.parseInt(strYear),
	                        Integer.parseInt(strMonth) - 1,
	                        Integer.parseInt(strDay), 0, 0);
	      }
	      catch (Exception ex) {
//	              log.error(strLoggerHeader +
//	                                 "[setDateTimeValue] The error is : \r\n" + ex);
	      }
	    }

	    return iResult;
	  }

	  /**
	   * set date value with unit value.
	   * @param strYear, year value r date.
	   * @param strMonth, month value for date.
	   * @param strDay, day value for date.
	   * @param strTime, time value for date.
	   * @return int
	   * @throws
	   */
	  public int setDateTimeValue(String strYear, String strMonth, String strDay,
	                              String strTime) {
	    int iResult = validateDateValue(strYear, strMonth, strDay, strTime);
	    if (0 == iResult) {
	      try {
	        if (!nullOrBlank(strTime)) {
	          objCalendar.set(Integer.parseInt(strYear),
	                          Integer.parseInt(strMonth) - 1,
	                          Integer.parseInt(strDay),
	                          Integer.parseInt(strTime.substring(0, 2)),
	                          Integer.parseInt(strTime.substring(2)));
	        }
	        else {
	          objCalendar.set(Integer.parseInt(strYear),
	                          Integer.parseInt(strMonth) - 1,
	                          Integer.parseInt(strDay), 0, 0);
	        }
	      }
	      catch (Exception ex) {
//	              log.error(strLoggerHeader +
//	                                 "[setDateTimeValue] The error is : \r\n" + ex);
	      }
	    }

	    return iResult;
	  }

	  /**
	   * Get the date and time value in String.
	   * @param
	   * @return String
	   * @throws
	   */
	  public String getDateTimeStr() {
	    String strResult = new String();
	    SimpleDateFormat objDateFormat = new SimpleDateFormat(DATE_FORMAT);
	    objDateFormat.setTimeZone(TimeZone.getTimeZone(TimeZone.getDefault().
	        getID()));
	    if (!isNull) {
	      strResult = objDateFormat.format(objCalendar.getTime());
	    }

	    return strResult;
	  }

	  public Date getDateTime() {
	    return objCalendar.getTime();
	  }

	  /**
	   * Get the date value only in String.
	   * @param strFormat String, the format for datetime.
	   * @return String
	   * @throws
	   */
	  public String getDateTimeWithPattern(String strFormat) {
	    try {
	      String strResult = new String();
	      SimpleDateFormat objDateFormat = new SimpleDateFormat(strFormat);
	      objDateFormat.setTimeZone(TimeZone.getTimeZone(TimeZone.getDefault().
	          getID()));
	      if (!isNull) {
	        strResult = objDateFormat.format(objCalendar.getTime());
	      }

	      return strResult;
	    }
	    catch (Exception ex) {
//	          log.error(strLoggerHeader +
//	                             "[getDateTimeWithPattern] The error is : \r\n" + ex);
	      return null;
	    }
	  }

	  /**
	   * Get the date value only in String.
	   * @param
	   * @return String
	   * @throws
	   */
	  public String getDateWithoutTime() {
	    try {
	      String strResult = new String();
	      SimpleDateFormat objDateFormat = new SimpleDateFormat(
	          DATE_FORMAT_DAY);
	      objDateFormat.setTimeZone(TimeZone.getTimeZone(TimeZone.getDefault().
	          getID()));
	      if (!isNull) {
	        strResult = objDateFormat.format(objCalendar.getTime());
	      }

	      return strResult;
	    }
	    catch (Exception ex) {
//	          log.error(strLoggerHeader +
//	                             "[getDateWithoutTime] The error is : \r\n" + ex);
	      return null;
	    }
	  }

	  /**
	   * Get the hour and minutes. If return the hour from 0 to 24, pls use
	   * GregorianCalendar.HOUR_OF_DAY. If return the hour from 0 to 12, pls use
	   * GregorianCalendar.HOUR.
	   * @param
	   * @return String
	   * @throws
	   */
	  public String getTime() {
	    try {
	      String strResult = new String();
	      DecimalFormat objTimeFormat = new DecimalFormat("00");
	      if (!isNull) {
	        strResult = objTimeFormat.format(objCalendar.get(
	            GregorianCalendar.
	            HOUR_OF_DAY))
	            +
	            objTimeFormat.format(objCalendar.get(GregorianCalendar.
	                                                 MINUTE));
	      }

	      return strResult;
	    }
	    catch (Exception ex) {
//	          log.error(strLoggerHeader +
//	                             "[getTime] The error is : \r\n" + ex);
	      return null;
	    }
	  }

	  /**
	   * Get the hour and minutes. If return the hour from 0 to 24, pls use
	   * GregorianCalendar.HOUR_OF_DAY. If return the hour from 0 to 12, pls use
	   * GregorianCalendar.HOUR.
	   * @param
	   * @return String
	   * @throws
	   */
	  public String getDisplayTime() {
	    try {
	      String strResult = new String();
	      DecimalFormat objTimeFormat = new DecimalFormat("00");
	      if (!isNull) {
	        strResult = objTimeFormat.format(objCalendar.get(
	            GregorianCalendar.
	            HOUR_OF_DAY)) + ":"
	            +
	            objTimeFormat.format(objCalendar.get(GregorianCalendar.
	                                                 MINUTE)) +
	            ":" +
	            objTimeFormat.format(objCalendar.get(GregorianCalendar.
	                                                 SECOND));
	      }

	      return strResult;
	    }
	    catch (Exception ex) {
//	          log.error(strLoggerHeader +
//	                             "[getDisplayTime] The error is : \r\n" + ex);
	      return null;
	    }
	  }

	  /**
	   * Get the hour and minutes. If return the hour from 0 to 24, pls use
	   * GregorianCalendar.HOUR_OF_DAY. If return the hour from 0 to 12, pls use
	   * GregorianCalendar.HOUR.
	   * @param
	   * @return String
	   * @throws
	   */
	  public String getDisplayMinuteTime() {
	    try {
	      String strResult = new String();
	      DecimalFormat objTimeFormat = new DecimalFormat("00");
	      if (!isNull) {
	        strResult = objTimeFormat.format(objCalendar.get(
	            GregorianCalendar.
	            HOUR_OF_DAY)) + ":"
	            +
	            objTimeFormat.format(objCalendar.get(GregorianCalendar.
	                                                 MINUTE));
	      }

	      return strResult;
	    }
	    catch (Exception ex) {
//	          log.error(strLoggerHeader +
//	                             "[getDisplayMinuteTime] The error is : \r\n" + ex);
	      return null;
	    }
	  }

	  /**
	   * Get the year value by String.
	   * @param
	   * @return String
	   * @throws
	   */
	  public String getYear() {
	    return objCalendar.get(GregorianCalendar.YEAR) + "";
	  }

	  /**
	   * Get the day value by String. If the day is one digit day. Use the DecimalFormat
	   * with two dogit format to return the value.
	   * @param
	   * @return String
	   * @throws
	   */
	  public String getDay() {
	    DecimalFormat objDayFormat = new DecimalFormat("00");
	    return objDayFormat.format(objCalendar.get(GregorianCalendar.
	                                               DAY_OF_MONTH));
	  }

	  /**
	   * Get the month value by String. If the day is one digit day. Use the DecimalFormat
	   * with two dogit format to return the value.
	   * @param
	   * @return String
	   * @throws
	   */
	  public String getMonth() {
	    DecimalFormat objMonthFormat = new DecimalFormat("00");
	    return objMonthFormat.format(objCalendar.get(GregorianCalendar.MONTH) +
	                                 1);
	  }

	  /**
	   * validate the date value.
	   * @param strYear, year value for date.
	   * @param strMonth, month value for date.
	   * @param strDay, day value for date.
	   * @param strHour, hour value for date.
	   * @param strMinute, minute value for date.
	   * @param strSecond, second value for date.
	   * @return int
	   * @throws
	   */
	  private int validateDateValue(String strYear, String strMonth,String strDay,
	                                String strHour, String strMinute,String strSecond) {
	    //TBC,Terry Mar-9
//	        if (nullOrBlank(strDay) &&
//	            nullOrBlank(strMonth) && nullOrBlank(strYear) &&
//	            nullOrBlank(strHour) && nullOrBlank(strMinute) &&
//	            nullOrBlankstrSecond)){
//	            return NULL_OF_DATE_VALUE;
//	        }
	    if (nullOrBlank(strDay) &&
	        (!nullOrBlank(strMonth) || !nullOrBlank(strYear))) {
	      return DAY_OF_DATE_IS_NULL;
	    }
	    if (nullOrBlank(strMonth) &&
	        (!nullOrBlank(strDay) || !nullOrBlank(strYear))) {
	      return MONTH_OF_DATE_IS_NULL;
	    }
	    if (nullOrBlank(strYear) &&
	        (!nullOrBlank(strDay) || !nullOrBlank(strMonth))) {
	      return YEAR_OF_DATE_IS_NULL;
	    }
	    if (!nullOrBlank(strDay) &&
	        (2 != strDay.length() || (0 >= Integer.parseInt(strDay)) ||
	         31 < Integer.parseInt(strDay))) {
	      return DAY_OF_DATE_INCORRECT;
	    }
	    if (!nullOrBlank(strMonth) &&
	        (2 != strMonth.length() || (0 >= Integer.parseInt(strMonth)) ||
	         12 < Integer.parseInt(strMonth))) {
	      return MONTH_OF_DATE_INCORRECT;
	    }
	    if (!nullOrBlank(strYear) && (4 != strYear.length()) ||
	        (0 >= Integer.parseInt(strYear))) {
	      return YEAR_OF_DATE_LEN_INCORRECT;
	    }
	    if (!nullOrBlank(strHour) && (0 > Integer.parseInt(strHour) ||
	                                  23 < Integer.parseInt(strHour))) {
	      return TIME_OF_DATE_INCORRECT;
	    }
	    if (!nullOrBlank(strMinute) && (0 > Integer.parseInt(strMinute) ||
	                                    59 < Integer.parseInt(strMinute))) {
	      return TIME_OF_DATE_INCORRECT;
	    }

	    return 0;
	  }

	  /**
	   * validate the date value.
	   * @param strYear, year value for date.
	   * @param strMonth, month value for date.
	   * @param strDay, day value for date.
	   * @param strHour, hour value for date.
	   * @param strTime, time value for date.
	   * @return int
	   * @throws
	   */
	  private int validateDateValue(String strYear, String strMonth,
	                                String strDay,String strTime) {
	    if (nullOrBlank(strDay) &&
	        nullOrBlank(strMonth) && nullOrBlank(strYear) &&
	        nullOrBlank(strTime)) {
	      return NULL_OF_DATE_VALUE;
	    }
	    if (nullOrBlank(strDay) &&
	        (!nullOrBlank(strMonth) || !nullOrBlank(strYear) ||
	         !nullOrBlank(strTime))) {
	      return DAY_OF_DATE_IS_NULL;
	    }
	    if (nullOrBlank(strMonth) &&
	        (!nullOrBlank(strDay) || !nullOrBlank(strYear) ||
	         !nullOrBlank(strTime))) {
	      return MONTH_OF_DATE_IS_NULL;
	    }
	    if (nullOrBlank(strYear) &&
	        (!nullOrBlank(strDay) || !nullOrBlank(strMonth) ||
	         !nullOrBlank(strTime))) {
	      return YEAR_OF_DATE_IS_NULL;
	    }
	    if (!nullOrBlank(strDay) &&
	        (!isDigits(strDay) || 2 != strDay.length() ||
	         0 >= Integer.parseInt(strDay) ||
	         31 < Integer.parseInt(strDay))) {
	      return DAY_OF_DATE_INCORRECT;
	    }
	    if (!nullOrBlank(strMonth) &&
	        (!isDigits(strMonth) || 2 != strMonth.length() ||
	         0 >= Integer.parseInt(strMonth) ||
	         12 < Integer.parseInt(strMonth))) {
	      return MONTH_OF_DATE_INCORRECT;
	    }
	    if (!nullOrBlank(strYear) && (!isDigits(strYear) || 4 != strYear.length()) ||
	        (0 >= Integer.parseInt(strYear))) {
	      return YEAR_OF_DATE_LEN_INCORRECT;
	    }
	    if (!nullOrBlank(strTime) &&
	        (!isDigits(strTime) || 4 != strTime.length() ||
	         0 > Integer.parseInt(strTime.substring(0, 2)) ||
	         23 < Integer.parseInt(strTime.substring(0, 2)) ||
	         0 > Integer.parseInt(strTime.substring(2)) ||
	         59 < Integer.parseInt(strTime.substring(2)))) {
	      return TIME_OF_DATE_INCORRECT;
	    }

	    return 0;
	  }

	  /**
	   * If input string is null or black, this function will return true. Otherwise,
	   * return false.
	   * @param strInput, judge the input string whether null or not
	   * @return boolean
	   * @throws
	   */
	  private boolean nullOrBlank(String strInput) {
	    if (null == strInput || "".equals(strInput.trim())) {
	      return true;
	    }

	    return false;
	  }

	  /**
	   * If input string contains non-number, return false. Otherwise, return true.
	   * @param strInput, input string to be judgment.
	   * @return boolean
	   * @throws
	   */
	  private boolean isDigits(String strInput) {
	    if (!nullOrBlank(strInput)) {
	      String strDigit = "0123456789";
	      for (int i = 0; i < strInput.length(); i++) {
	        if ( -1 == strDigit.indexOf(strInput.charAt(i) + "")) {
	          return false;
	        }
	      }
	    }
	    else {
	      return false;
	    }

	    return true;
	  }

	  /**
	   * 将某字符串按要求的格式转换为日期类型
	   * @param stringDate String 需要转换的字符串
	   * @param formatDate String 要求转换的格式
	   * @return Date 如果出现异常，返回null
	   */
	  public Date parseDate(String stringDate, String formatDate) {
	    Date resultDate;
	    DateFormat df = new SimpleDateFormat(formatDate);
	    try {
	      resultDate = df.parse(stringDate);
	    }
	    catch (ParseException ex) {
	      return null;
	    }
	    return resultDate;
	  }

	  /**
	   * 判断传入的日期是否为闰月
	   * @param toDate Date 需要判断的日期
	   * @return boolean 如果是闰月，返回true，否则返回false
	   */
	  public boolean isSleekMonth(Date toDate) {
	    DateFormat df = new SimpleDateFormat(this.AISAT_DATE_FORMAT_DAY);
	    String dateString = df.format(toDate);
	    String month = dateString.substring(5, 7);
	    if ("01".equals(month) || "03".equals(month) || "05".equals(month) ||
	        "07".equals(month) || "08".equals(month) || "10".equals(month) ||
	        "12".equals(month)) {
	      return true;
	    }
	    else {
	      return false;
	    }
	  }

	  /**
	   * 判断传入的日期为哪个季度
	   * @param toDate Date
	   * @return int 一季度返回1，二季度返回2，三季度返回3，四季度返回4
	   */
	  public int getQuarter(Date toDate) {
	    DateFormat df = new SimpleDateFormat(this.AISAT_DATE_FORMAT_DAY);
	    String dateString = df.format(toDate);
	    String month = dateString.substring(5, 7);
	    if ("01".equals(month) || "02".equals(month) || "03".equals(month)) {
	      return 1;
	    }
	    if ("04".equals(month) || "05".equals(month) || "06".equals(month)) {
	      return 2;
	    }
	    if ("07".equals(month) || "08".equals(month) || "09".equals(month)) {
	      return 3;
	    }
	    if ("10".equals(month) || "11".equals(month) || "12".equals(month)) {
	      return 4;
	    }
	    return 0;
	  }
	}