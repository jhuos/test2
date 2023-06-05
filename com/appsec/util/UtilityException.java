package com.appsec.util;

public class UtilityException
    extends Exception {
  public UtilityException(String msg) {
    super(msg);
  }

  public UtilityException(String msg, Exception e) {
    super(msg, e);
  }

  public UtilityException(Exception e) {
    super(e);
  }
}
