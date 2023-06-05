package com.appsec.action;


import javax.servlet.*;
import javax.security.cert.*;
import javax.servlet.http.*;
import java.net.*;
import java.util.*;
import java.io.*;
import org.apache.struts.action.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class InjectionAction  extends CommonAction{
	protected ActionForward doAction(ActionContext ac)
                     throws ActionException {
		System.out.println("Injection Action Success");
		return this.getForwardPage("success", ac);
	}
}
