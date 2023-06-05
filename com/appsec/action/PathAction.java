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

public class PathAction extends CommonAction{
	/*protected ActionForward doAction(ActionContext ac)
                     throws ActionException {
		System.out.println("Path Action Success");
		return this.getForwardPage("success", ac);
	}*/
	public ActionForward pathInput(ActionMapping map,
			 ActionForm fm,
			 HttpServletRequest req,
			 HttpServletResponse resp) throws Exception {
		ActionContext ac = getAC(map,fm,req,resp);
			System.out.println("Path Action Success");
		return this.getForwardPage("success", ac);
   }
	public ActionForward pathAttack(ActionMapping map,
			 ActionForm fm,
			 HttpServletRequest req,
			 HttpServletResponse resp) throws Exception {
		ActionContext ac = getAC(map,fm,req,resp);
			System.out.println("Path Attack Success");
		return this.getForwardPage("success", ac);
 }
	
	public ActionForward showCode(ActionMapping map,
			 ActionForm fm,
			 HttpServletRequest req,
			 HttpServletResponse resp) throws Exception {
		ActionContext ac = getAC(map,fm,req,resp);
			System.out.println("Action Success");
		return this.getForwardPage("success", ac);
    }
}
