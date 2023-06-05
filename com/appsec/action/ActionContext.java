package com.appsec.action;

import java.io.*;

import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.struts.action.*;
import org.apache.struts.util.*;
import com.appsec.util.*;

/**
 *   The instance of this class contains action associated objects for an action thread.
 *   An action class is thread unsafe and, therefore, cannot hold per
 *   thread data. All the thread data must be stored in AcitonContext object by calling setOnActionProperty().
 *   The ActionContext object is passed as a parameter to
 *   onAction and onError in ActionAbstract derived classes to allow a concrete acion class to access action
 *   thread data.
 *   For classes derived from ServletActionAbstract, ServletActionContext is passed to the corresponding
 *   methods to expose servlet specific information.
 *
 */
public class ActionContext implements Serializable
{
  private String _forwardCode;
  private ActionMapping _mapping;
  private String _userErrorMsg;
  private String _userErrorResMsg;
  private ActionForm _form;
  private HttpServletRequest _request;
  private HttpServletResponse _response;
  private ServletContext _servletContext;
  private Action _action;
 // private UIErrors _uiErrs;
  private byte[] _binData;
  private static final String PDF_DONE_FLAG = "PDFDone";
  private Hashtable _props = new Hashtable();

  private boolean _doForward = true;

  public final static int SCOPE_REQUEST = 0;
  public final static int SCOPE_SESSION = 1;
  public final static int SCOPE_APPLICATION = 2;

  private final static int ACTION_EXCEPTION = 1;
  private final static int INPUT_VALIDATION = 2;

  protected ActionContext(Action act) {
    _action = act;
  }

  /**
   *   set forward code that is mapped to a forward URL path defined in
   *   ui-config.xml
   *
   *   @param forward code string
   */
  public void setForwardCode(String v) {
    _forwardCode = v;
  }

  protected String getForwardCode() {
    return _forwardCode;
  }

  protected void setActionMapping(ActionMapping v) {
    _mapping = v;
  }

  public ActionMapping getActionMapping() {
    return _mapping;
  }

  public void changeForwardPath(String name, String path) {
    ActionForward fw = _mapping.findForward(name);
    if (fw != null) {
      fw.setPath(path);
    }
  }

  protected void setActionForm(ActionForm v) {
    _form = v;
  }

  /**
   *    get ActionForm object that contains initialized input parameters.
   *    @return ActionForm object.
   */
  public ActionForm getActionForm() {
    return _form;
  }

  protected HttpSession getSession() {
    return _request.getSession();
  }

  protected void setServletContext(ServletContext v) {
    _servletContext = v;
  }

  protected ServletContext getServletContext() {
    return _servletContext;
  }

  protected void setRequest(HttpServletRequest v) {
    _request = v;
  }

  public HttpServletRequest getRequest() {
    return _request;
  }

  protected void setResponse(HttpServletResponse v) {
    _response = v;
  }

  public HttpServletResponse getResponse() {
    return _response;
  }

  /**
   *   Set user error message string to be displayed.
   *   Note, if the error message is not set, the exception message will be
   *   used for the error display.
   *   @param msg  - user error message string
   */
  public void setErrorMsg(String v) {
    _userErrorMsg = v;
  }

 /* protected UIErrors getUIErrors() {
    return _uiErrs;
  }

  protected void setUIErrors(UIErrors v) {
    _uiErrs = v;
  }*/

  /**
   *   Get the error message set by <code>setErrorMsg</code>to be displayed
   *   @return  - user error message string
   */
  public String getErrorMsg() {
    return _userErrorMsg;
  }

  /**
   *   This method is the samme as <code>setErrorMsg</code> exception it sets error message id
   *   instead of error text.
   *   @param msg  - user error message string
   */
  public void setErrorResMsg(String msg) {
    _userErrorResMsg = msg;
  }

  /**
   *   This method is the samme as <code>getErrorMsg</code> exception it gets error message id
   *   instead of error text.
   *   @return  - user error message string
   */
  public String getErrorResMsg() {
    return _userErrorResMsg;
  }

  /**
   *    set a data bean to request, session or application context beased on scope parameter
   *
   *    @param name of the bean
   *    @param o bean object to be set.
   *    @param scope to indicate where the object is stored. The scoep constants are define as static
   *           constatants with SCOPE_ prefix in this class.
   */
  public void setBean(String name, Object o, int scope) throws ActionException {
    if (scope == SCOPE_REQUEST)
      _request.setAttribute(name, o);
    else if (scope == SCOPE_SESSION) {
      HttpSession session = _request.getSession();
      session.setAttribute(name, o);
    }
    else if (scope == SCOPE_APPLICATION) {
      ServletContext servletContext = _action.getServlet().getServletContext();
      servletContext.setAttribute(name, o);
    }
    throw new ActionException("Invalid scope: " + scope);
  }

  /**
   *    get a data bean from request, session or application context beased on scope parameter
   *
   *    @param name of the bean
   *    @param scope to indicate where the object is stored. The scoep constants are define as static
   *           constatants with SCOPE_ prefix in this class.
   *
   *    @return data bean stored with a given scope.
   */
  public Object getBean(String name, int scope) throws ActionException {
    if (scope == SCOPE_REQUEST)
      return _request.getAttribute(name);
    else if (scope == SCOPE_SESSION) {
      HttpSession session = _request.getSession();
      return session.getAttribute(name);
    }
    else if (scope == SCOPE_APPLICATION) {
      ServletContext servletContext = _action.getServlet().getServletContext();
      return servletContext.getAttribute(name);
    }
    throw new ActionException("Invalid scope: " + scope);
  }

  /**
   *   get a value object stored in the user session identified by its name.
   *   @param name - name used to identify the value object stored in a session.
   */
  public Object getSessionValue(String name) {
    HttpSession session = _request.getSession();
    return session.getAttribute(name);
  }

  /**
   *   get a value object stored in the user session identified by the object's
   *   class name. This session value is stored with call setSessionValue( Object ).
   *   @param name - name used to identify the value object stored in a session.
   */
  public Object getSessionValue(Class cls) {
    String name = getClassName(cls);
    return getSessionValue(name);
  }

  public Enumeration getSessionNames() {
    HttpSession session = _request.getSession();
    return session.getAttributeNames();
  }

  /**
   *   Set a value object identified by name parameter to this user session.
   *
   *   @param name used to identify the value object stored in session.
   *   @param o  value object set in session.
   */
  public void setSessionValue(String name, Object o) {
    HttpSession session = _request.getSession();
    session.setAttribute(name, o);
  }

  /**
   *    Remove a value object identified by its property name. in the user session.
   *    This value object was set by setSessionValue( name,Object )
   *
   *    @param name - name of session property.
   */
  public void removeSessionValue(String name) {
    HttpSession session = _request.getSession();
    session.removeAttribute(name);
  }

  /**
   *    Remove a value object identified by its class name. in the user session.
   *    This value object was set by setSessionValue( Object )
   *
   *    @param cls Class which is the type of value object.
   */
  public void removeSessionValue(Class cls) {
    HttpSession session = _request.getSession();
    String name = getClassName(cls);
    session.removeAttribute(name);
  }

  /**
   *   Set a value object identified by its class to a user session. This call assumes only one
   *   instance of a class is stored in the session, otherwise call setSessionValue( name,o ). The value object
   *   stored this way
   */
  public void setSessionValue(Object o) {
    String name = getClassName(o);
    setSessionValue(name, o);
  }

  /**
   *   get attributes  from request scope
   *
   *   @param name - name used to identify the attribute stored in request scope.
   */
  public Object getDataBean(String name) {
    return _request.getAttribute(name);
  }

  public Enumeration getDataBeanNames() {
    return _request.getAttributeNames();
  }

  public void removeDataBean(String name) {
    _request.removeAttribute(name);
  }

  /**
   *   get names of avaialble parameters for this request
   *
   *   @return - array of parameter names
   */
  public Enumeration getInputParamNames() {
    Enumeration enumAttr = _request.getAttributeNames();
    Enumeration enumParam = _request.getParameterNames();
    Vector paramV = new Vector();
    while (enumAttr.hasMoreElements()) {
      paramV.add(enumAttr.nextElement());
    }
    while (enumParam.hasMoreElements()) {
      paramV.add(enumParam.nextElement());
    }
    return paramV.elements();
    // return _request.getParameterNames();
  }

  /**
   *   get parameter  from request scope
   *
   *   @param name - name used to identify the parameter stored in request scope.
   *   @return - parameter value
   */
  public String getInputParam(String name) {
    String inputParam = _request.getParameter(name);
    if ( StringUtility.isNull(inputParam)) {
      Object o = _request.getAttribute(name);
      if (o != null) {
        inputParam = o.toString();
      }
    }
    return inputParam;
  }

  /**
   *   get parameter values from request scope for a multi-value parameter
   *
   *   @param name - name used to identify the parameter stored in request scope.
   *   @return array of string values for the parameter.
   */
  public String[] getInputParams(String name) {
    return _request.getParameterValues(name);
  }

  /**
   *   get data bean object from request scope. The class name of the object is used as
   *   the bean's name.
   *
   *   @param o bean object whose name is identified by the object's class
   *            name.
   *   @return object bean stored in request scope.
   */
  public Object getDataBeanByObject(Object o) {
    String name = getClassName(o);
    return getDataBean(name);
  }

  /**
   *  set data bean in request scope using data object's class name as
   *  bean' name.
   *
   *  @param o - object to be set in request scope.
   */
  public void setDataBean(Object o) {
    String name = getClassName(o);
    setDataBean(name, o);
  }

  /**
   *   set data bean in request scope
   *
   *   @praram name of data bean
   *   @param object value of the data bean
   */
  public void setDataBean(String name, Object o) {
    _request.setAttribute(name, o);
  }

  /**
   *   call this method to directly send pdf document to a request client
   *
   *   @param byte array containing PDF data.
   */
  public void setBinData(byte[] data) {
    _binData = data;
  }

  public byte[] getBinData() {
    return _binData;
  }

  private String getClassName(Object o) {
    return getClassName(o.getClass());
  }

  private String getClassName(Class cls) {
    String clsName = cls.getName();
    int idx = clsName.lastIndexOf(".");
    if (idx == -1)
      return clsName;
    return clsName.substring(idx + 1);
  }

  /**
   *   Read input binary data result ( for example, from a PDF request ).
   *   @return byte array that contains binary data sent from a client
   */
  public byte[] readInputBytes() throws ActionException {
    try {
      return Utility.getBytesToEndOfStream(_request.getInputStream());
    }
    catch (IOException e) {
       throw new ActionException(e);
    }
  }

  /**
   *    Directly read input string from request
   *
   *    @return  input string sent by client.
   */
  public String readInputString() throws ActionException {
    try {
      return Utility.getStringToEndOfStream(_request.getInputStream());
      /*
       BufferedInputStream buf = new BufferedInputStream( _request.getInputStream() );
                   StringWriter writer = new StringWriter();
                   int blockSize = 4098;
                   byte[] data = new byte[ blockSize ];
                   while ( true )
                   {
          int readSize = buf.read(data,0,blockSize);
          if( readSize == -1 )
              break;
          writer.write(data,0,readSize);
                   }
                   return writer.toString();
       */
    }
    catch (IOException e) {
      throw new ActionException(e);
    }
  }

  /**
   *   Directly send string to the request client
   *
   *   @param outputStr - String to be sent to the request client.
   */
  public void writeOutputString(String outputStr) throws ActionException {
    _response.setContentType("text/xml");
    PrintWriter out = null;
    _doForward = false;
    try {
      out = _response.getWriter();
      out.println(outputStr);
      out.flush();
    }
    catch (IOException e) {
      throw new ActionException(e);
    }
    finally {
      if (out != null)
        out.close();
    }
  }

  public void setPDFComplete(boolean v) {
    HttpSession session = _request.getSession();
    if (v) {
      setSessionValue(PDF_DONE_FLAG, "true");
    }
    else {
      removeSessionValue(PDF_DONE_FLAG);
    }
  }

  public boolean isPDFInProgress() {
    String pdfDoneFlag = (String) getSessionValue(PDF_DONE_FLAG);
    if (pdfDoneFlag == null)
      return false;
    return true;
  }

  /**
   *   Directly return binary data to the client.
   *
   *   @param data byte array of binary data to be written to output stream
   *   @param imageType (image/gif, image/jpeg etc..)
   */
  public void writeOutputImg(byte[] data, String imageType) throws
      ActionException {
    // _response.setContentType( "image/gif" );
    _response.setContentType(imageType);
    ServletOutputStream out = null;
    try {
      out = _response.getOutputStream();
      out.write(data);
      out.flush();
      out.close();
    }
    catch (IOException e) {
      throw new ActionException(e);
    }
    finally {
      if (out != null) {
        try {
          out.close();
        }
        catch (Exception e) {
          e.printStackTrace();
        }
      }
    }

  }

  /**
   *   Directly return binary data to the client.
   *
   *   @param data byte array of binary data to be written to output stream.
   */
  public void writeOutputBytes(byte[] data) throws ActionException {
    writeOutputData(data, "binary");
  }

  /**
   *   Directly return  data to the client.
   *
   *   @param data byte array of binary data to be written to output stream.
   *   @param type context type
   */
  public void writeOutputData(byte[] data, String type) throws ActionException {
    OutputStream out = null;
    _doForward = false;
    try {
      _response.setContentType(type);
      out = _response.getOutputStream();
      out.write(data);
      out.flush();
    }
    catch (IOException e) {
      throw new ActionException(e);
    }
    finally {
      if (out != null) {
        try {
          out.close();
        }
        catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
  }

  /**
   *    Get an application level action property that is set by calling
   *    setActionProperty().
   *
   *    @param name  String to identify the property.
   *    @return property object
   */
  public Object getActionProperty(String name) {
    return _props.get(name);
  }

  /**
   *    Set an application level action property to be later read by getActionProperty()
   */
  public void setActionProperty(String name, Object v) {
    _props.put(name, v);
  }

  /**
   *    Encode url. Any URL with possible URL rewrite must be encoded by calling this
   *    method.
   *f
   *    @param oldURL url string to be encoded.
   *    @return encoded URL string
   */
  public String encodeURL(String oldURL) {
    return _response.encodeURL(oldURL);
  }
  public void setRequireFoward(boolean v) {
      _doForward=v;
  }
  public boolean requireFoward() {
    return _doForward;
  }

  public String getContextPath() {
    return _request.getContextPath();
  }

  public Hashtable getParamMap() {
    Hashtable map = new Hashtable();
    Enumeration enum0 = getInputParamNames();
    while (enum0.hasMoreElements()) {
      String name = (String) enum0.nextElement();
      Object valueObj = null;
      String[] values = getInputParams(name);
      if (values != null) {
        if (values.length == 1) {
          valueObj = values[0];
        }
        else {
          valueObj = values;
        }
      }
      else {
        valueObj = getInputParam(name);
      }
      if (valueObj != null) {
        map.put(name, valueObj);
      }
    }
    Enumeration enumAttrs = getRequest().getAttributeNames();
    while (enumAttrs.hasMoreElements()) {
      String en = (String) enumAttrs.nextElement();
      map.put(en, getDataBean(en));
    }
    return map;
  }
  public void sendBinData() throws ActionException{
    try{
         if( "true".equals((String)getDataBean("docRetrieved") )) {
             return;
         }
         HttpServletResponse response = getResponse();
         //response.setHeader("Accept-Ranges", "bytes");
          byte[] data = getBinData();
          if( data == null ) {
            throw new ActionException( "Data for file download has not been prepared" );
          }
         response.setContentLength(data.length);
         String ct = (String)getDataBean( "binDataContextType");
         if( ct == null ) {
           throw new ActionException( "action context for binary data is not defined" );
         }

         //response.setHeader("Connection", "close");
         response.setContentType(ct);
         ServletOutputStream out = response.getOutputStream();
         try{
           out.write(data);
           setDataBean("docRetrieved","true");
         }catch( IllegalStateException e ) {
            // ignore
         }
           out.flush();
           // write the pdf data to the output stream
           /*
         int flag = 0;
         int blockLength = 10000;
         for( int offset = 0; offset < data.length; offset += blockLength )
         {
               out.write(data,offset,blockLength);
                     out.flush();
         }
         */
                 out.close();
        }catch( Exception e )
        {
           throw new ActionException( e );
        }

  }
}
