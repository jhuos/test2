package com.appsec.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

public class ConnectionPool {
	private static ConnectionPool cp;
	private PoolProperties p = new PoolProperties();
	
	public static ConnectionPool instance() {
		if( cp == null ){
			cp = new ConnectionPool();
			try{
				cp.init();
			}catch( Exception e){
				e.printStackTrace();
				return null;
			}
		}
		return cp;
	}
    public void init() throws UtilityException {
        
        p.setUrl("jdbc:mysql://localhost:3306/test?autoReconnect=true");
        p.setDriverClassName("com.mysql.jdbc.Driver");
        p.setUsername("root");
        p.setPassword("");
        p.setJmxEnabled(true);
        p.setTestWhileIdle(false);
        p.setTestOnBorrow(true);
        p.setValidationQuery("SELECT 1");
        p.setTestOnReturn(false);
        p.setValidationInterval(30000);
        p.setTimeBetweenEvictionRunsMillis(30000);
        p.setMaxActive(100);
        p.setInitialSize(10);
        p.setMaxWait(10000);
        p.setRemoveAbandonedTimeout(60);
        p.setMinEvictableIdleTimeMillis(30000);
        p.setMinIdle(10);
        p.setLogAbandoned(true);
        p.setRemoveAbandoned(true);
        p.setJdbcInterceptors("org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer");
      }
      public Connection getConnection() throws UtilityException{  
        DataSource datasource = new DataSource();
        datasource.setPoolProperties(p);         
        Connection con = null;
        try {            
          con = datasource.getConnection();
        }catch( Exception e ) {
        	throw new UtilityException(e);
        }
        return con;
    }
}
