package com.appsec.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.appsec.util.ConnectionPool;
import com.appsec.util.StringUtility;

public class StudentDAO {
	public List findStudentList(String last,String first, String sort,boolean mitigate) throws StudentDAOException {
		String ln = last;
		if( StringUtility.isNull(last)) {
			ln = "";
		}
		String orderBy = "";
		if( sort != null && !sort.equalsIgnoreCase("none")){
			orderBy = "order by " + sort;
		}
		System.out.println("=== orderBy:" + orderBy);
		String sql0 = "select first_name,last_name,state,city from member where last_name like '%" + 
			ln + "%' " + orderBy;
		Connection con = null;
		List lst = new ArrayList();
		try{
			con = ConnectionPool.instance().getConnection();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql0);
			int cnt = 1;
			System.out.println("executing sql " + sql0);
			
			while (rs.next()) {
				StudentData sd = new StudentData();
				sd.setFirstName(rs.getString("first_name"));
				sd.setLastName(rs.getString("last_name"));
				sd.setCity(rs.getString("city"));
				sd.setState(rs.getString("state"));
				lst.add(sd);
				//System.out.println(" User:"+rs.getString("first_name")+ ",last: " + rs.getString("last_name")+ ",city: " +rs.getString("city") + ",state: " + rs.getString("state"));
			}
			rs.close();
			st.close();
		}catch( Exception e ){
			throw new StudentDAOException(e);
		}
		finally {
			if (con!=null) try {con.close();}catch (Exception ignore) {}
		}
		return lst;
	}	
}
