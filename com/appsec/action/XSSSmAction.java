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
import org.owasp.esapi.*;
import org.owasp.esapi.reference.DefaultEncoder;

import com.appsec.util.StringUtility;

public class XSSSmAction extends CommonAction{
	/*protected ActionForward doAction(ActionContext ac)
                     throws ActionException {
		System.out.println("Action Success");
		return this.getForwardPage("success", ac);
	}*/
	public ActionForward xssInput(ActionMapping map,
			 ActionForm fm,
			 HttpServletRequest req,
			 HttpServletResponse resp) throws Exception {
		ActionContext ac = getAC(map,fm,req,resp);
			System.out.println("XSSInput Action Success");
			System.out.println("===== getParamNames called== ");
			 Enumeration e2 = req.getParameterNames();
			 while( e2.hasMoreElements()){
				 System.out.println("== get name: " + e2.nextElement()); 
			 }
			 
		return this.getForwardPage("xssInput", ac);
    }
	public ActionForward xssAttack(ActionMapping map,
			 ActionForm fm,
			 HttpServletRequest req,
			 HttpServletResponse resp) throws Exception {
		ActionContext ac = getAC(map,fm,req,resp);
		boolean isPersist = "persisted".equals(ac.getInputParam("xssType"));
		String name = ac.getInputParam("name");		
		String region = ac.getInputParam("region");
		String desc = ac.getInputParam("description");
		String comment = ac.getInputParam("comment");
		//System.out.println(System.getProperty("user.home"));
		boolean isMitigated = "yes".equals(ac.getInputParam("mitigated"));
		if( isMitigated ){
			name = DefaultEncoder.getInstance().encodeForHTML(name);
			comment = DefaultEncoder.getInstance().encodeForJavaScript(comment);
			region = DefaultEncoder.getInstance().encodeForHTML(region);
			String desc0 = desc;
			desc = DefaultEncoder.getInstance().encodeForHTML(desc);
			System.out.println(desc + "++++ desc2=" + DefaultEncoder.getInstance().encodeForHTMLAttribute(desc0) + "+++url desc=" + DefaultEncoder.getInstance().encodeForURL(desc0)+"+++js desc="
					+DefaultEncoder.getInstance().encodeForJavaScript(desc0) 
					   );
		}
		
		if( isPersist ){
			ac.setSessionValue("comment", comment);
			ac.setSessionValue("name", name);
			ac.setSessionValue("region",region );
			ac.setSessionValue("description",desc );
		}else{
			ac.setSessionValue("name", null);
			ac.setSessionValue("comment", null);
			ac.setSessionValue("region",null );
			ac.setSessionValue("description",null );
		}
		ac.setDataBean("xssName",name);
		ac.setDataBean("xssComment",comment);
		ac.setDataBean("xssRegion",region);
		ac.setDataBean("xssDesc",desc);
			System.out.println("======= XSSAttack Success!!!!" + ac.getDataBean("name") + ",isPersist=" + isPersist + ",mitigate=" + isMitigated);
		return this.getForwardPage("xssResult", ac);
   }
	public ActionForward xssShowPersistResult(ActionMapping map,
			 ActionForm fm,
			 HttpServletRequest req,
			 HttpServletResponse resp) throws Exception {
		ActionContext ac = getAC(map,fm,req,resp);
		String name = StringUtility.nullToEmpty(ac.getSessionValue("name"));		
		String region = StringUtility.nullToEmpty(ac.getSessionValue("region"));
		String desc = StringUtility.nullToEmpty(ac.getSessionValue("description"));
		ac.setDataBean("xssName",name);
		ac.setDataBean("xssRegion",region);
		ac.setDataBean("xssDesc",desc);
			System.out.println("ShowPersistResult Success name=" + name);
		return this.getForwardPage("xssResult", ac);
  }
	public ActionForward showCode(ActionMapping map,
			 ActionForm fm,
			 HttpServletRequest req,
			 HttpServletResponse resp) throws Exception {
		ActionContext ac = getAC(map,fm,req,resp);
			System.out.println("ShowCode Success");
		return this.getForwardPage("xssShow", ac);
   }
    public ActionForward clearPersist(ActionMapping map,
			 ActionForm fm,
			 HttpServletRequest req,
			 HttpServletResponse resp) throws Exception {
		ActionContext ac = getAC(map,fm,req,resp);
		ac.getSession().removeAttribute("name");
		ac.getSession().removeAttribute("comment");
		ac.getSession().removeAttribute("region" );
		ac.getSession().removeAttribute("description");
			System.out.println("ShowCode Success");
		return this.getForwardPage("xssShow", ac);
  }
}
