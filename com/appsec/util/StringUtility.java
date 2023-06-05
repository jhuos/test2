package com.appsec.util;

import java.math.BigDecimal;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Set;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class StringUtility {
    private static Pattern listIdPt;
    static {
	listIdPt = Pattern.compile("[0-9A-Za-z,\t]*");
    }

    /**
         * return the first word and remove the first word from StringBuffer
         * 
         * @param sBuf
         *                StringBuffer
         * @return String
         */
    public static String scanFirstWord(StringBuffer sBuf) {
	int idx = sBuf.indexOf(" ");
	if (idx == -1) {
	    return sBuf.toString();
	}
	String firstWord = sBuf.substring(0, idx);
	String newStr = sBuf.substring(idx);
	sBuf.delete(0, firstWord.length());
	return firstWord;
    }

    /**
         * return a string up to the first occurance of the token. replace the
         * input StringBuffer with the returned string without token
         * 
         * @param sBuf
         *                StringBuffer
         * @param token
         *                String
         * @return String
         */
    public static String scanTokenUpTo(StringBuffer sBuf, String token) {
	int idx = sBuf.indexOf(token);
	if (idx == -1) {
	    return sBuf.toString();
	}
	String returnStr = sBuf.substring(0, idx);
	sBuf.delete(0, idx + token.length());
	return returnStr;
    }

    public static String scanTokenAt(StringBuffer sBuf, String token) {
	scanTokenUpTo(sBuf, token);
	return sBuf.toString();
    }

    /**
         * replace the input StringBuffer with token removed
         * 
         * @param sBuf
         *                StringBuffer
         * @param token
         *                String
         */
    public static String skipToken(StringBuffer sBuf, String token) {
	int idx = sBuf.indexOf(token);
	if (idx == -1) {
	    return sBuf.toString();
	}
	sBuf.delete(0, idx + token.length());
	return sBuf.toString();
    }

    public static String skipToken(String str, String token) {
	StringBuffer sBuf = new StringBuffer(str);
	int idx = sBuf.indexOf(token);
	if (idx == -1) {
	    return sBuf.toString();
	}
	sBuf.delete(0, idx + token.length());
	return sBuf.toString();
    }

    public static boolean isNullObj(Object v) {
	if (v == null) {
	    return true;
	}
	return isNull(v.toString());
    }

    public static boolean checkListIdStr(String str) {
	Matcher m = listIdPt.matcher(str);
	return m.matches();
    }
    public static boolean isStrNull(String v) {
	return v == null 
		|| v.trim().length() == 0;
    }
    public static boolean isNull(String v) {
	return v == null || v.trim().equalsIgnoreCase("null")
		|| v.trim().length() == 0;
    }

    public static String map2Str(Map map) {
	Iterator it = map.keySet().iterator();
	StringBuffer buf = new StringBuffer();
	boolean first = true;
	while (it.hasNext()) {
	    String key = (String) it.next();
	    Object val = map.get(key);
	    if (first) {
		first = false;
	    } else {
		buf.append(",");
	    }
	    buf.append(key).append("=").append(val);
	}
	return buf.toString();
    }

    public static Map parseParams(String v) {
	Map map = new HashMap();
	if (v == null) {
	    return map;
	}
	String[] params = v.split(",");
	for (int i = 0; i < params.length; i++) {
	    String[] nameVals = params[i].split("=");
	    if (nameVals.length > 1) {
		map.put(nameVals[0], nameVals[1]);
	    }
	}
	return map;
    }

    /**
         * e.g. a,b are added to the Set with two entries
         * 
         * @param str
         *                String
         * @return Map
         */
    public static Set getCommaSepParamsSet(String str, boolean toUpper) {
	Set set = new HashSet();
	if (str == null) {
	    return set;
	}
	String[] params = str.split(",");
	for (int i = 0; i < params.length; i++) {
	    if (toUpper) {
		params[i] = params[i].toUpperCase();
	    }
	    set.add(params[i]);
	}
	return set;
    }

    public static List getCommaSepParamsList(String str, boolean toUpper) {
	List lst = new ArrayList();
	if (str == null) {
	    return lst;
	}
	String[] params = str.split(",");
	for (int i = 0; i < params.length; i++) {
	    if (toUpper) {
		params[i] = params[i].toUpperCase();
	    }
	    lst.add(params[i]);
	}
	return lst;
    }

    public static String set2Str(Set set) {
	return it2Str(set.iterator());
    }

    /**
         * return a string with comma separated tokens which are passed in as
         * array of tokens
         * 
         * @param arr
         * @return
         */
    public static String array2Str(Object[] arr) {
	if (arr == null || arr.length == 0) {
	    return "";
	}
	StringBuffer buf = new StringBuffer();
	boolean first = true;
	for (int i = 0; i < arr.length; i++) {
	    Object val = arr[i];
	    if( val == null){
		return "";
	    }
	    if (!first) {
		buf.append(",");
	    }
	    buf.append(val.toString());
	    if (first) {
		first = false;
	    }
	}
	return buf.toString();

    }

    public static String it2Str(Iterator it) {
	StringBuffer buf = new StringBuffer();
	boolean first = true;
	while (it.hasNext()) {
	    Object val = it.next();
	    if (!first) {
		buf.append(",");
	    }
	    buf.append(val.toString());
	    if (first) {
		first = false;
	    }
	}
	String str = buf.toString();
	if (str.trim().length() == 0) {
	    str = "-1";
	}
	return str;

    }

    public static String list2Str(List lst) {
	Iterator it = lst.iterator();
	return it2Str(it);
    }

    /**
         * e.g. a=1,b=2 are added to the Map with two entries
         * 
         * @param str
         *                String
         * @return Map
         */
    public static Map getCommaSepParams(String str) {
	Map map = new HashMap();
	if (str == null) {
	    return map;
	}
	String[] params = str.split(",");
	for (int i = 0; i < params.length; i++) {
	    String[] nameVal = params[i].split("=");
	    if (nameVal.length == 2) {
		map.put(nameVal[0].trim(), nameVal[1].trim());
	    }
	}
	return map;
    }

    public static String replaceParams(String str0, Map map,
	    boolean noReplaceIfNotMatch) throws UtilityException {
	try {
	    return replaceParams(str0, map, "{[", "]}", "", -1,
		    noReplaceIfNotMatch);
	} catch (Exception ex) {
	    return null;
	}
    }

    public static String replaceParams2(String str0, Map map,
	    boolean noReplaceIfNotMatch) throws UtilityException {
	try {
	    return replaceParams(str0, map, "{(", ")}", "", -1,
		    noReplaceIfNotMatch);
	} catch (Exception ex) {
	    return null;
	}
    }

    public static String replaceParams(String str0, Map map, String leftDel,
	    String rightDel, String quote, int arrayIdx)
	    throws UtilityException {
	return replaceParams(str0, map, leftDel, rightDel, quote, arrayIdx,
		false);

    }

    public static String replaceParams(String str0, Map map, String leftDel,
	    String rightDel, String quote, int arrayIdx,
	    boolean noReplaceIfNoExist) throws UtilityException {
	int fromIndex = 0;
	if (str0 == null) {
	    return str0;
	}
	str0 = str0.trim();
	int leftDelLength = leftDel.length();
	int rightDelLength = rightDel.length();
	StringBuffer resultBuf = new StringBuffer();
	while (true) {
	    int leftTokenIdx = str0.indexOf(leftDel, fromIndex);
	    int rightTokenIdx = str0.indexOf(rightDel, leftTokenIdx);
	    if (leftTokenIdx < 0) { // no more token left
		resultBuf.append(str0);
		break;
	    }
	    if (rightTokenIdx < 0) {
		throw new UtilityException(
			"Invalid ( unpaired ) variable token for " + str0);
	    }
	    String params = str0.substring(leftTokenIdx + leftDelLength,
		    rightTokenIdx); // param enclosed by tokens
	    Object tmpParam = map.get(params);
	    String paramStr = "null";
	    if (arrayIdx == -1) {
		if (tmpParam != null && tmpParam.getClass().isArray()) {
		    paramStr = arrayToCommaDelStr((Object[]) tmpParam);
		} else {
		    paramStr = (tmpParam == null) ? "" : tmpParam.toString();
		}
	    } else if (tmpParam != null) {
		if (tmpParam.getClass().isArray()) { // param is an array,
                                                        // only get array at
                                                        // arrayIdx
		    Object[] arrayParam = (Object[]) tmpParam;
		    if (arrayIdx < arrayParam.length) {
			paramStr = (arrayParam[arrayIdx] == null) ? ""
				: arrayParam[arrayIdx].toString();
		    }
		} else { // not array
		    paramStr = tmpParam.toString();
		}
	    }
	    if (paramStr != null) {
		paramStr = paramStr.trim();
		if (paramStr.length() == 0) {
		    if (quote.length() == 0) {
			if (noReplaceIfNoExist) {
			    paramStr = str0.substring(leftTokenIdx,
				    rightTokenIdx + rightDelLength);
			} else {
			    paramStr = "";
			}
		    } else {
			paramStr = "null";
		    }
		} else {
		    paramStr = quote + paramStr + quote;
		}
	    } else {
		if (quote.length() == 0) {
		    if (noReplaceIfNoExist) {
			paramStr = str0.substring(leftTokenIdx, rightTokenIdx
				+ rightDelLength);
		    } else {
			paramStr = "";
		    }
		} else {
		    paramStr = "null";
		}
	    }
	    resultBuf.append(str0.substring(fromIndex, leftTokenIdx)).append(
		    paramStr);
	    str0 = str0.substring(rightTokenIdx + rightDelLength);
	}
	return resultBuf.toString();
    }

    public static String replaceTokens(String str0, String repLeft,
	    String repRight, String leftDel, String rightDel)
	    throws UtilityException {
	int fromIndex = 0;
	if (str0 == null) {
	    return str0;
	}
	str0 = str0.trim();
	int leftDelLength = leftDel.length();
	int rightDelLength = rightDel.length();
	StringBuffer resultBuf = new StringBuffer();
	while (true) {
	    int leftTokenIdx = str0.indexOf(leftDel, fromIndex);
	    int rightTokenIdx = str0.indexOf(rightDel, leftTokenIdx);
	    if (leftTokenIdx < 0) { // no more token left
		resultBuf.append(str0);
		break;
	    }
	    if (rightTokenIdx < 0) {
		throw new UtilityException(
			"Invalid ( unpaired ) variable token for " + str0);
	    }
	    String paramStr = str0.substring(leftTokenIdx + leftDelLength,
		    rightTokenIdx); // param enclosed by tokens
	    // Object tmpParam = map.get(params);
	    /*
                 * if (arrayIdx == -1) { if (tmpParam != null &&
                 * tmpParam.getClass().isArray()) { paramStr =
                 * arrayToCommaDelStr( (Object[]) tmpParam); } else { paramStr =
                 * (tmpParam==null)? "" : tmpParam.toString(); } } else if
                 * (tmpParam != null) { if (tmpParam.getClass().isArray()) { //
                 * param is an array, only get array at arrayIdx Object[]
                 * arrayParam = (Object[]) tmpParam; if (arrayIdx <
                 * arrayParam.length) { paramStr = (arrayParam[arrayIdx] == null )? "" :
                 * arrayParam[arrayIdx].toString(); } } else { // not array
                 * paramStr = tmpParam.toString(); } } if (paramStr != null) {
                 */
	    paramStr = paramStr.trim();
	    /*
                 * if (paramStr.length() == 0) { if (quote.length() == 0) { if(
                 * noReplaceIfNoExist ) { paramStr =
                 * str0.substring(leftTokenIdx, rightTokenIdx + rightDelLength);
                 * }else{ paramStr = ""; } } else { paramStr = "null"; } } else {
                 */
	    paramStr = repLeft + paramStr + repRight; // quote + paramStr +
                                                        // quote;
	    // }
	    /*
                 * } else { if (quote.length() == 0) { if( noReplaceIfNoExist ) {
                 * paramStr = str0.substring(leftTokenIdx, rightTokenIdx +
                 * rightDelLength); }else{ paramStr = ""; } } else { paramStr =
                 * "null"; } }
                 */
	    resultBuf.append(str0.substring(fromIndex, leftTokenIdx)).append(
		    paramStr);
	    str0 = str0.substring(rightTokenIdx + rightDelLength);
	}
	return resultBuf.toString();
    }

    private static String arrayToCommaDelStr(Object[] param) {
	StringBuffer buf = new StringBuffer();
	boolean isFirst = true;
	for (int i = 0; i < param.length; i++) {
	    if (isFirst) {
		isFirst = false;
	    } else {
		buf.append(",");
	    }
	    buf.append(param[i]);
	}
	return buf.toString();
    }

    public static String removeExceptionClassText(String e) {
	if (e == null) {
	    return "null error";
	}
	String ex = "Exception:";
	int idx = e.indexOf(ex);
	if (idx == -1) {
	    return e;
	}
	return e.substring(idx + ex.length());
    }

    public static int toInt(Object o) {
	if (o==null) {
	    return -1;
	}
	String i = o.toString();
	int v = -1;
	try {
	    v = Integer.parseInt(i);
	} catch (Exception e) {
	    try{
		v = (int)Float.parseFloat(o.toString());
	    }catch( Exception e2) {
		    
	    }
	}

	return v;
    }
    public static float toFloat(Object o) {
	if (o == null) {
	    return -1;
	}
	String i = o.toString();
	float v = -1;
	try {
	    v = Float.valueOf(i).floatValue();
	} catch (Exception e) {
	}
	;
	return v;
    }
    public static Integer toIntObj(Object o) {
	if (o == null) {
	    return null;
	}	
	return new Integer(toInt(o));
    }

    /**
         * if string is null or "null" return empty string
         * 
         * @param v
         *                String
         * @return String
         */
    public static String nullToEmpty(Object v) {
	if (v == null) {
	    return "";
	}
	String str = v.toString();
	if (isNull(str)) {
	    return "";
	}
	return str;
    }

    public static String getStrConstValue(String v) {
	if (isNull(v)) {
	    return "null";
	}
	return "\"" + v + "\"";
    }

    /*
         * public static Timestamp parseTimestamp( String v )throws
         * UtilityException { try{ SimpleDateFormat sdf = new
         * SimpleDateFormat(WfConstants.TIME_FORMAT); Date timeoutTime =
         * sdf.parse(v); return new Timestamp(timeoutTime.getTime()); }catch(
         * Exception e){ throw new UtilityException(e); } }
         */
    public static Timestamp parseCalendarTimestamp(String v)
	    throws UtilityException {
	try {
	    if (StringUtility.isNull(v)) {
		return null;
	    }
	    SimpleDateFormat sdf = new SimpleDateFormat(
		    CommonConsts.CALENDAR_FORMAT);
	    Date timeoutTime = sdf.parse(v);
	    return new Timestamp(timeoutTime.getTime());
	} catch (Exception e) {
	    throw new UtilityException(e);
	}
    }

    public static String date2Str(java.util.Date date) {
	SimpleDateFormat sdf = new SimpleDateFormat(
		CommonConsts.CALENDAR_FORMAT);
	return sdf.format(date);
    }

    public static String tsDate2Str(Timestamp ts) {
	if( ts == null){
	    return "";
	}
	SimpleDateFormat sdf = new SimpleDateFormat(
		CommonConsts.CALENDAR_FORMAT);
	Date d = new Date(ts.getTime());
	return sdf.format(d);
    }

    public static String dateTime2Str(java.util.Date date) {
	SimpleDateFormat sdf = new SimpleDateFormat(
		CommonConsts.CALENDAR_DATE_TIME_FORMAT);
	return sdf.format(date);
    }

    public static String getCurDate() {
	return date2Str(new Date());
    }

    public static String getCurDateTime() {
	return dateTime2Str(new Date());
    }

    public static String HTMLEncode(String s) {
	StringBuffer buf = new StringBuffer();
	int len = (s == null ? -1 : s.length());

	for (int i = 0; i < len; i++) {
	    char c = s.charAt(i);
	    if (c == '<' || c == '>') {
		buf.append("&#" + (int) c + ";");
	    } else {
		buf.append(c);
	    }
	    /*
                 * if ( c>='a' && c<='z' || c>='A' && c<='Z' || c>='0' && c<='9' ||
                 * c==' ' ) { buf.append( c ); } else { buf.append( "&#" +
                 * (int)c + ";" ); }
                 */
	}
	return buf.toString();
    }
   /* public static String getCode( String s ){
	String r2 = new String(Base64.decode(s));
	return StringUtility.xor( r2, StringUtility.getToken());    
    }*/
    public static String xor(String s1, String s2) {
	char[] a = s1.toCharArray();
	char[] b = s2.toCharArray();
	int length = Math.min(a.length, b.length);
	char[] result = new char[length];
	for (int i = 0; i < length; i++) {
	    // ^ is the xor operator
	    // see http://mindprod.com/jgloss/xor.html
	    result[i] = (char) (a[i] ^ b[i]);
	}
	return new String(result);
    }

    public static String getToken() {
	return "d29zk32;y>-3r$d2;edebnds32'-=#43kds@1(idsadsewxcgrertfopsdf;"; //"d29zk32;y>-3r$d2;e";
    }

  /*  public static BigDecimal str2BigDec(String v){
	Double d = Double.valueOf(v);	
      return BigDecimal.valueOf(d.doubleValue());      
  }*/
    public static BigInteger str2BigInt(String v){
	Integer i = Integer.valueOf(v);	
      return BigInteger.valueOf(i.intValue());      
  }
    public static BigInteger int2BigtInt( int v){
	return BigInteger.valueOf(v);
    }
    public static Integer BigInt2Int( BigInteger v ){
	int ret = -1;
	if( v != null){
	    ret = v.intValue();
	}
	return new Integer( ret );
    }
    public static String[] objToStrArr( Object o){
	if( o == null ){
	    return new String[0];
	}
	if( !o.getClass().isArray()){
	    String[] arr = new String[1];
	    arr[0] = (String)o;
	    return arr;
	}
	return (String[])o;
    }
    public static String escapeSingleQuotes( String v ){
	String ret = v.replaceAll("'", "\\\\'");
	return ret;
    }
}
