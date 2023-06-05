package com.appsec.action;


import java.util.List;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.appsec.model.StudentDAO;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class SQLAction extends CommonAction{
	/*protected ActionForward doAction(ActionContext ac)
                     throws ActionException {
		System.out.println("SQL Action Success");
		return this.getForwardPage("success", ac);
	}*/
	private static int csrfToken = 0;
	public ActionForward sqlInput(ActionMapping map,
			 ActionForm fm,
			 HttpServletRequest req,
			 HttpServletResponse resp) throws Exception {
		ActionContext ac = getAC(map,fm,req,resp);
		csrfToken++;
		ac.setDataBean("csrfToken","" + csrfToken);
			System.out.println("*****SQLAction Success " + csrfToken);
		return this.getForwardPage("sqlInput", ac);
   }
	public ActionForward sqlAttack(ActionMapping map,
			 ActionForm fm,
			 HttpServletRequest req,
			 HttpServletResponse resp) throws Exception {
		System.out.println("******SQLAttack called");
		ActionContext ac = getAC(map,fm,req,resp);
		
		String last = ac.getInputParam("lastName");
		if( last == null ){
			last = "";
		}
		String sort = ac.getInputParam("sort");
		StudentDAO sdao = new StudentDAO();
		List lst = sdao.findStudentList(last, null, sort, false);
		ac.setDataBean("studentList", lst);
			System.out.println("******SQLAttackn Success");
		return this.getForwardPage("sqlResult", ac);
  }
	public ActionForward sqlMapAttack(ActionMapping map,
			 ActionForm fm,
			 HttpServletRequest req,
			 HttpServletResponse resp) throws Exception {
		ActionContext ac = getAC(map,fm,req,resp);
			System.out.println("SQLMap Success");
		return this.getForwardPage("sqlInput", ac);
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
