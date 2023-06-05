package com.appsec.util;

import java.io.*;


import java.sql.Timestamp;import java.lang.reflect.*;
import java.util.*;
import java.net.*;
import java.util.regex.*;
import javax.xml.bind.*;
import sun.misc.*;
import java.text.SimpleDateFormat;


/**
 *  Common utility class.  This class is composed of
 *  a set of static generic helper methods.
 */

public class Utility {

  private static Date _date = null;
  /* private static SimpleDateFormat sdf = new SimpleDateFormat( "yyyy/MM/dd hh:mm:ss");
  public static String date2Str( java.util.Date date ) {
     return sdf.format(date);
  }
  public static Date str2Date( String v ) throws UtilityException {
    try{
      return sdf.parse(v);
    }catch( Exception e ) {
      throw new UtilityException( e );
    }
  }*/

  /**
   *    Read a resource file located within the classpath.
   *
   *    @param o - A class loader loading the class of this object is used
   *               to load the resource.
   *    @param name - resource name
   *    @return  InputStream object for retrieving resource content.
   */
  public static InputStream readFromResource(Object o, String name) throws
      UtilityException {
    try {
     // InputStreamReader reader = null;
      String resPath = name;
      // using this class's ClassLoader to load the resource.
      //Object obj = new Object();
      return o.getClass().getResourceAsStream(resPath);
    }
    catch (Exception e) {
      throw new UtilityException(e);
    }
 }
  /**
   *    Read a lsit of proerties  from a resource file.
   *
   *    @param o - An object whose ClassLoader object will be retrieved
   *    @param  name - name of the resource in the jar.
   *    @return Properties object read from the resource.
   */
  public static Properties readPropsFromResource(Object o, String name) throws
      UtilityException {
    Properties props = null;
    try {
      //InputStreamReader reader = null;
      String resPath = "/META-INF/" + name;
      // using this class's ClassLoader to load the resource.
      //Object obj = new Object();
      InputStream is = o.getClass().getResourceAsStream(resPath);
      props = new Properties();
      props.load(is);
      is.close();
    }
    catch (Exception e) {
      throw new UtilityException(e);
    }
    return props;
  }
  public static Properties readPropsFromFile(String name) throws
      UtilityException {
    Properties props = null;
    try {
      InputStream is = new FileInputStream( name );
      props = new Properties();
      props.load(is);
      is.close();
    }
    catch (Exception e) {
      throw new UtilityException(e);
    }
    return props;
  }

  public static Properties readPropsFromResourceRoot( Object o,String name ) throws Exception
 {
              Properties props = null;
        try
        {
              // InputStreamReader reader = null;
               String resPath = name;
       // using this class's ClassLoader to load the resource.
       //Object obj = new Object();
               InputStream is = o.getClass().getResourceAsStream( resPath );
               props = new Properties();
               props.load( is );
               is.close();
     }
     catch( Exception e )
     {
         throw e;
     }
       return props;
 }



  /**
   *   read an InputStream into a byte array.
   *
   *   @param  in - Inputstream object.
   *   @return  byte[] - byte array containing the data read from in.
   */
  public static byte[] getBytesToEndOfStream(InputStream in) throws IOException {
    final int chunkSize = 4096;
    byte[] buf = new byte[chunkSize];
    ByteArrayOutputStream byteStream
        = new ByteArrayOutputStream(chunkSize);
    int count;

    while ( (count = in.read(buf)) != -1) {
      byteStream.write(buf, 0, count);

    }
    byte[] dataArray = byteStream.toByteArray();
    byteStream.close();
    return dataArray;
  }

  /**
   *   Read an InputStream into a String.  This is a different version of
   *   binaryStreamToText().
   *
   *   @param  in - input Inputstream.
   *   @return  String - String containing the data read from in.
   */
  public static String getStringToEndOfStream(InputStream in) throws
      IOException {
    final int chunkSize = 4096;
    byte[] buf = new byte[chunkSize];
    ByteArrayOutputStream byteStream
        = new ByteArrayOutputStream(chunkSize);
    int count;

    while ( (count = in.read(buf)) != -1) {
      byteStream.write(buf, 0, count);

    }
    return byteStream.toString();
  }
  public static String getStringFromFile( String path ) throws IOException {
      InputStream is = new FileInputStream( path );
      return getStringToEndOfStream( is );
  }
  public static byte[] getBytesFromFile( String path ) throws IOException {
        InputStream is = new FileInputStream( path );
        return getBytesToEndOfStream( is );
    }

  /**
   *    Convenient function to invoke a method without parameters
   *
   *    @param o - Object whose method will be invoked.
   *    @param methodName - method name string.
   *    @return objet value returned from the invoked method.
   */

  public static Object callMethod(Object o, String methodName) throws Exception {
    Class cls = o.getClass();
    Method m = cls.getMethod(methodName, new Class[0]);
    return m.invoke(o, new Object[0]);
  }

  public static Object callMethodWithTypes(String className, String methodName,
                                           Class[] types, Object[] params) throws
      Exception {
    Class cls = Class.forName(className);
    Object o = cls.newInstance();
    return callMethod(o, methodName, types, params);
  }

  /**
   *    Convenient function to invoke a method with parameters
   *
   *    @param className - class name for an object to be instantiated first and then invoked.
   *    @param methodName - method name string.
   *    @param params - array of parameter objects corresponding to the method arguments.
   *    @return objet value returned from the invoked method.
   */
  public static Object callMethod(String className, String methodName,
                                  Object[] params) throws Exception {
    Class cls = Class.forName(className);
    Object o = cls.newInstance();
    return callMethod(o, methodName, params);
  }

  /**
   *    Convenient function to invoke a method with parameters
   *
   *    @param o - Object whose method will be invoked.
   *    @param methodName - method name string.
   *    @param params - array of parameter objects corresponding to the method arguments.
   *    @return objet value returned from the invoked method.
   */
  public static Object callMethod(Object o, String methodName, Object[] params) throws
      Exception {
    return callMethod(o, methodName, null, params);
  }

  /**
   *    Convenient function to invoke a stateless service methofd.
   *    @param className - class Name of a service interface
   *    @param methodName - method name string
   *    @param params - array of parameter objects corresponding to the method arguments.
   *    @return objet value returned from the invoked method.
   *
   */
 /* public static Object callServiceMethod(String interfaceName,
                                         String methodName, Object[] params) throws
      Exception {
    Class cls = Class.forName(interfaceName);
    Object so = ServiceObjectFactory.create(cls);
    return callMethod(so, methodName, params);
  }*/

  /**
   *    Convenient function to invoke a stateless service method.
   *    @param className - class Name of a service interface
   *    @param methodName - method name string
   *    @return objet value returned from the invoked method.
   *
   */
 /* public static Object callServiceMethod(String interfaceName,
                                         String methodName) throws Exception {
    Class cls = Class.forName(interfaceName);
    Object so = ServiceObjectFactory.create(cls);
    return callMethod(so, methodName);
  }*/

  /**
   *   Convenient function to invoke a method with parameters
   *
   *    @param o - object whose method will be invoked.
   *    @param methodName - method name string.
   *    @param paramTypes - array of parameter class objects  corresponding to the types of method arguments.
   *    @param params - array of parameter objects corresponding to the method arguments.
   *    @return return value from the invoked method.
   */
  public static Object callMethod(Object o, String methodName,
                                  Class[] paramTypes, Object[] params) throws
      Exception {
    Class cls = o.getClass();
    Class[] paramClasses = null;
    Object retObj = null;
    try {
      if (paramTypes == null) {
        paramClasses = new Class[params.length];
        for (int i = 0; i < paramClasses.length; i++) {
          paramClasses[i] = params[i].getClass();
        }
      }
      else {
        paramClasses = paramTypes;

      }
      Object[] paramObjs = new Object[params.length];
      for (int i2 = 0; i2 < paramObjs.length; i2++) {
        paramObjs[i2] = params[i2];
      }
      Method m = cls.getMethod(methodName, paramClasses);
      retObj = m.invoke(o, paramObjs);
    }
    catch (InvocationTargetException e) {
      Throwable t = e.getTargetException();
      /*
      String paramClassStr = "";
      for (int i = 0; paramClasses != null && i < paramClasses.length; i++) {
        paramClassStr += (paramClasses[i] + "***");
      }*/
      if (t instanceof Throwable) {
        t.printStackTrace();
        throw new Exception("Failed to call " + methodName + "() from object " +
                            cls, t);
      }
      else {
        throw new Exception("Failed to call " + methodName + "() from object " +
                            cls + ", exception:" + t.toString());
      }
    }
    catch (Throwable e) {
      throw new Exception( e);
    }
    return retObj;
  }

  /**
   *   Convenient function to construct an object with a constructor and parameters
   *
   *    @param o - object that will be constructed
   *    @param params - parameters to be passed to the constructor
   *    @return return value from the invoked method.
   */
  public static Object constructObject(String className, Object[] params) throws
      Exception {
    Class cls = Class.forName(className);
    Class[] clsArray = new Class[params.length];
    for (int i = 0; i < params.length; i++) {
      clsArray[i] = params[i].getClass();
    }
    Constructor con = cls.getConstructor(clsArray);
    Object obj = con.newInstance(params);
    return obj;
  }

  /**
   *   get the exception object that initiate the exception chaining.
   *
   *   @param  current Throwable object
   *   @return source Throwable object
   */
  public static Exception getSourceException(Throwable e) {
    Throwable causeE = e;
    Throwable newE = null;
    while (true) {
      newE = causeE.getCause();
      if (newE == null) { // source E does not have cuase.
        break;
      }
      causeE = newE;
    //  System.out.println("causeE=" + causeE.toString() + ",," + causeE.getMessage() );
    }
    if( !(causeE instanceof Exception) ) {
        causeE = new Exception( causeE.getMessage() );
    }

    return (Exception)causeE;
  }

  /**
   *    A utility method that substitute all the occurance of oldStr for a new string
   *    in the source string
   *
   *    @param source - source string
   *    @param oldStr - old string to be replaced.
   *    @param newStr - new string to replace.
   *    @return  - the result string after replacement
   */
  public static String replaceString(String source, String oldStr,
                                     String newStr) {
    if (source == null) {
      return null;
    }
    int startIdx = 0;
    int endIdx = 0;
    String result = "";
    int oldStrLength = oldStr.length();
    while (true) {
      endIdx = source.indexOf(oldStr, startIdx);
      if (endIdx == -1) {
        result += source.substring(startIdx);
        break;
      }
      result += (source.substring(startIdx, endIdx) + newStr);
      startIdx = endIdx + oldStrLength;
    }

    return result;
  }
  public static String getLocalAddress() throws UtilityException{
   try{
     return java.net.InetAddress.getLocalHost().getHostAddress();
  }catch (Exception e) {
     throw new UtilityException( e );
   }


  }
  /**
   *   create a globally unique 24 character-base64 ID.
   */
  /*public static String createUUID() {
    String ip = null;
    try {
      ip = java.net.InetAddress.getLocalHost().getHostAddress();
    }
    catch (Exception e) {
      e.printStackTrace();
    } // this should never be thrown
    byte[] uuidArr = new byte[12];
    StringTokenizer st = new StringTokenizer(ip, ".");
    // convert ip format from 12.34.56.78 to for bytes array
    for (int i = 0; st.hasMoreTokens(); i++) {
      String ipNum = st.nextToken();
      uuidArr[i] = (byte) Integer.valueOf(ipNum).intValue();
    }
    // timestamp in ms
    Date d = new Date();
    long time = d.getTime();
    int endTimeIdx = uuidArr.length - 1;
    for (int timeIdx = 4; timeIdx <= endTimeIdx; timeIdx++) {
      int shift = (endTimeIdx - timeIdx) * 8;
      uuidArr[timeIdx] = (byte) (time >>> shift);
    }
    // base64 encoding
    BASE64Encoder base64 = new BASE64Encoder(); // Sun's Base64 Encoder
    String encodedUuid = base64.encode(uuidArr);

    return encodedUuid;
  }*/

  private static String appBasePath;
  static {
	if( appBasePath == null ) {
       appBasePath = System.getProperty("AppPathBase");
	}
  }
  public static void setAppPathBase( String v ){
	  appBasePath = v;
  }
  public static String getAppPathBase() {

    return appBasePath;
  }

  public static String nullToEmpty(String v) {
    if (v == null) {
      v = "";
    }
    return v;
  }

  public static String toQuotedText(String v) {
    if (v == null) {
      return v;
    }
    return "\"" + v + "\"";
  }

  /**
   *   Serialize an object to data
   * @param o Object
   * @throws IOException
   * @return byte[]
   */
  public static byte[] getObjectData(Object o) throws IOException {
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    ObjectOutputStream os = new ObjectOutputStream(bos);
    os.writeObject(o);
    return bos.toByteArray();
  }

  /**
   *   Deserialize data to an object
   * @param data byte[]
   * @throws IOException
   * @throws ClassNotFoundException
   * @return Object
   */
  public static Object getObjectFromData(byte[] data) throws IOException,
      ClassNotFoundException {
    ByteArrayInputStream bis = new ByteArrayInputStream(data);
    ObjectInputStream ois = new ObjectInputStream(bis);
    return ois.readObject();
  }

  public static String replace(String oldStr, String oldToken, String newToken) {
  Pattern p = Pattern.compile(oldToken);
    Matcher m = p.matcher(oldStr);
    StringBuffer sb = new StringBuffer();
    while (m.find()) {
      m.appendReplacement(sb, newToken);
    }
    m.appendTail(sb);
    return sb.toString();

  }
  public static String replaceSingleStr( String oldStr, String oldToken, String newToken) throws UtilityException {
     int idx1 = oldStr.indexOf(oldToken);
     if( idx1 == -1 ) {
       return oldStr;
     }
     StringBuffer buf = new StringBuffer();
     buf.append(oldStr.substring(0,idx1)).append(newToken).append(oldStr.substring(idx1+oldToken.length()));
     return buf.toString();
  }

  public static Object callBean(Object o, String objChain) throws Exception {
    StringTokenizer st = new StringTokenizer(objChain, ".");
    while (st.hasMoreTokens()) {
      String token = st.nextToken();
      String methodName = "get" + token;
      o = callMethod(o, methodName);
    }
    return o;
  }

  public static void callSetBean(Object o, Object val, String objChain) throws
      Exception {
    StringTokenizer st = new StringTokenizer(objChain, ".");
    int tokenCount = st.countTokens();
    for (int i = 0; i < tokenCount; i++) {
      String token = st.nextToken();
      if (i == (tokenCount - 1)) { // last object of the chain
        // set the val object to the nested bean
        String methodName = "set" + token;
        Object[] params = {
            val};
        o = callMethod(o, methodName, params);
        return;
      }
      String methodName = "get" + token;
      o = callMethod(o, methodName);
    }
    throw new Exception("Invalid call to Utility.callSetBean: " + o.getClass() +
                        "," + objChain);
  }

  public static void logTime2(String msg) {
    if (_date == null) {
      _date = new Date();
    }
    System.out.println("----- time " + msg + "," +
                       (new Date().getTime() - _date.getTime()));
  }
  public static Object unmarshalXML(String path,Class cls ) throws UtilityException {
    try{
      InputStream is = new FileInputStream(path);
      return unmarshalXML(is, cls.getPackage().getName());
     }catch( Exception e ) {
      throw new UtilityException( e );
   }

  }
  public static Object unmarshalXMLStr(String xml,Class cls ) throws UtilityException {
    try{
      InputStream is = new ByteArrayInputStream(xml.getBytes("UTF-8"));
      return unmarshalXML(is, cls.getPackage().getName());
     }catch( Exception e ) {
      throw new UtilityException( e );
   }

  }

  public static Object unmarshalXML(InputStream is, String packageName) throws
      Exception {
    JAXBContext jc0 = JAXBContext.newInstance(
        packageName );
    Unmarshaller u0 = jc0.createUnmarshaller();
    return u0.unmarshal(is);
  }
  public static String marshalXML(Object obj,Class cls ) throws UtilityException {
     return marshalXML(obj, cls.getPackage().getName(),true) ;
  }
  public static String marshalXML(Object obj, String packageName, boolean format) throws UtilityException {
      try{
        JAXBContext jc0 = JAXBContext.newInstance(
            packageName);
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        Marshaller m0 = jc0.createMarshaller();
        m0.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        m0.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, new Boolean( format ));
        m0.marshal(obj, bao);
        String str=  new String(bao.toByteArray(),"UTF-8");
        //System.out.println(str);
        return str;
      }catch( Exception e ) {
         throw new UtilityException( e );
      }
  }
  public static String marshalXML(Object obj, String packageName) throws
     UtilityException {
    return marshalXML( obj,packageName,false );
 }
 public static String getHostName() throws UtilityException{
   try{
     return java.net.InetAddress.getLocalHost().getHostName();
   }catch( Exception e ) {
     throw new UtilityException( e );
   }
 }
 public static String getHostIP() throws UtilityException{
  try{
    return java.net.InetAddress.getLocalHost().getHostAddress();
  }catch( Exception e ) {
    throw new UtilityException( e );
  }
}


 public static boolean dateAfter( Date curDate, Date expDate ) {
   return curDate.after(expDate);
 }

 /*public static String getAdapterAddress() throws UtilityException {
   try{
     String cmd = PropertiesMap.instance().getProperty(
         "license.adapterAddrCmd");
     if (cmd == null) {
       throw new UtilityException("Adapter address command for this OS is not defined in system property: adapterAddrCmd");
     }
     String token1 = PropertiesMap.instance().getProperty(
         "license.adapterAddrToken1");
     if (token1 == null) {
       throw new UtilityException("Adapter address token 1 for this OS is not defined in system property: license.adapterAddrToken1");
     }
     String token2 = PropertiesMap.instance().getProperty(
         "license.adapterAddrToken2");
     if (token2 == null) {
       throw new UtilityException("Adapter address token 2 for this OS is not defined in system property: license.adapterAddrToken2");
     }
     Process proc = Runtime.getRuntime().exec(cmd);
     byte[] data = new byte[10000];
     proc.getInputStream().read(data);
     String dataStr = new String( data );  // assume local encoding may need to adjust later
     int idx1 = dataStr.indexOf( token1 );
     if( idx1 == -1 ) {
         throw new UtilityException( "Command " + cmd + " does not contain token for adapter address: " + token1 );
     }
     String maxStr0 = dataStr.substring(idx1 + token1.length());
     int idx2 = maxStr0.indexOf( token2 );
     if( idx1 == -1 ) {
        throw new UtilityException( "Command " + cmd + " does not contain token after adapter address: " + token2 );
    }
    String mac = maxStr0.substring(0,idx2);
    return mac.trim();
   }catch( Exception e ) {
     throw new UtilityException( e );
   }
 }*/
 public static void printChars( String inp ) {
   System.out.println("char==>" );
   for( int i = 0; inp != null && i < inp.length(); i++ ) {
          int ch = (int)inp.charAt(i);
       System.out.println(Integer.toHexString(ch) + "," );
   }
 }
 public static Set StrArr2Set( String[] v ) {
    Set set = new HashSet();
    if( v == null ) {
      return set;
    }
    for( int i = 0; i < v.length; i++ ) {
        set.add(v[i]);
    }
    return set;
 }
 public static Timestamp getCurrentTimestamp() {
    Date curDate = new Date();
    Timestamp ts = new Timestamp( curDate.getTime() );
    return ts;
 }
 public static ClassLoader getClassLoader( Object parentObj, String classPath ) throws UtilityException{
   try{
     if ( StringUtility.isNull(classPath)) {
       throw new UtilityException( "Class path must be defined to load the class" );
     }
     URL[] urls = new URL[1];
     urls[0] = new URL(classPath);
     URLClassLoader ucl = null;
     ucl = new URLClassLoader(urls, parentObj.getClass().getClassLoader());
     return ucl;
   }catch( Exception e ) {
     throw new UtilityException( e);
   }
 }
 public static Class loadClass( Object o,String classPath,String className ) throws UtilityException{
   try{
       ClassLoader ucl = getClassLoader( o, classPath );
       return ucl.loadClass(className);


   }catch( Exception e ) {
     throw new UtilityException( e );
   }
 }
 public static int random() {
   return random(1000);
 }
 public static int random(int max) {
  return (int)(java.lang.Math.random() * max);
}
 public static List mapVal2List( Map map ) {
   List lst = new ArrayList();
   Iterator it = map.values().iterator();
   while( it.hasNext() ) {
     Object o = it.next();
     lst.add(o);
   }
   return lst;
 }


}
