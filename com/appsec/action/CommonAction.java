package com.appsec.action;

import javax.servlet.*;

import javax.security.cert.*;
import javax.servlet.http.*;

import java.net.*;
import java.util.*;
import java.io.*;

import org.apache.struts.action.*;
import org.apache.struts.actions.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class CommonAction extends DispatchAction{
	/*public ActionForward execute(ActionMapping mapping,
			 ActionForm form,
			 HttpServletRequest request,
			 HttpServletResponse response)
                    throws IOException, ServletException {
		try{
			ActionContext ac = getAC(mapping,form,request,response);
			return this.doAction( ac );
		}catch( Exception e ){
			// add code here to never throw exception 
			throw new ServletException(e);
		}
	}
	abstract protected ActionForward doAction( ActionContext ac)throws ActionException;
	*/
	protected ActionForward getForwardPage( String forwardName2,ActionContext ac ) 
    {
        ActionForward actForward = null;
        String forwardCode = ac.getForwardCode();
        ActionMapping mapping = ac.getActionMapping();
        if( forwardCode != null ) {
             actForward = mapping.findForward( forwardCode);       
        }else{
        	actForward = mapping.findForward( forwardName2);
        	System.out.println("====actForward=" + actForward );
        }
        return actForward;
    }
	protected ActionContext getAC( ActionMapping mapping,
			 ActionForm form,
			 HttpServletRequest request,
			 HttpServletResponse response ){
		ActionContext ac = new ActionContext(this);
		ac.setRequest(request);
		ac.setResponse(response);
		ac.setActionMapping(mapping);
		System.out.println("==== paramNames: " + request.getParameterMap() );
		return ac;
	}
}
