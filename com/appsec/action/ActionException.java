package com.appsec.action;
//import com.chipdata.share.BaseException;

/**
 *   This exception class is designed to be thrown from Action related classes.
 */
public class ActionException extends Exception
{
  /**
  *    Constructor to be used for re-throwing
  *    @param Exception - previous exception
  */
  public ActionException( Exception e )
  {
      super( e );
  }
  /**
  *    Constructor to be used for re-throwing
  *    @param msg - additional exception message
  *    @param Exception - previous exception
  */
  public ActionException( String msg,Exception e )
  {
      super( msg,e );
  }
  /**
  *    Constructor to be used to throw source exception
  *    @param msg - exception message
  */
  public ActionException( String msg )
  {
      super( msg );
  }
}
