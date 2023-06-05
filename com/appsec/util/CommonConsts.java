package com.appsec.util;


public class CommonConsts {
  public static boolean TEST_MODE = false;
  public static String CALENDAR_FORMAT = "";
  public static String CALENDAR_DATE_TIME_FORMAT = "";
  public static String CALENDAR_SETUP_FORMAT="";
 

  public final static String SYS_PROP_APPSERVER = "AppServer";
  public final static String SYS_PROP_APPSERVER_USE = "AppServerInUse";
  public final static String SYS_PROP_MSGSERVER_USE = "MsgServerInUse";
  public final static String SYS_PROP_APP_PATH_BASE = "AppPathBase";
  public  static String HTTP_DOC_FILE_BASE = null;
  public final static String PROD_BASE_PROP_NAME = "baseProduct";
  public final static String SESSION_USER_ID = "userId";
  public static void initProps() {
      TEST_MODE = Boolean.getBoolean("testMode");
      CALENDAR_DATE_TIME_FORMAT = System.getProperty("formeng.date.time.format","yyyy-MM-dd hh:mm:ss");
     // CALENDAR_SETUP_FORMAT = System.getProperty("formeng.calendar.setup.time.format","%Y-%m-%d %H:%M:%S");     
      CALENDAR_FORMAT = System.getProperty("formeng.time.format","yyyy-MM-dd");
      
      CALENDAR_SETUP_FORMAT = System.getProperty("formeng.calendar.setup.time.format","%Y-%m-%d");
  }
  
 // public final static String REQUEST_USER_ID = "$userId";
}
