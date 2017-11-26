package com.shao.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.PreparedStatement;
import com.shao.model.user;



public class ExecuteSQL {
	protected static String dbClassName = "com.mysql.jdbc.Driver";
	protected static String dbUrl = "jdbc:mysql://localhost:3306/atm";
	protected static String dbUser = "root";
	protected static String dbPwd = "root";
	private static Connection conn = null;
	
	private ExecuteSQL() {
		try {
			if (conn == null) {
				Class.forName(dbClassName).newInstance();
				conn = DriverManager.getConnection(dbUrl, dbUser, dbPwd);
			}
			else
				return;
		} catch (Exception ee) {
			ee.printStackTrace();
		}

	}
	
	//重写executeQuer方法
	//返回ResultSet结果集
	private static ResultSet executeQuery(String sql) {
		try {
			if(conn==null)
			new ExecuteSQL();
			return conn.createStatement().executeQuery(sql);//ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
		}
	}
	
	//重写executeUpdate方法
	private static int executeUpdate(String sql) {	
		try {
			if(conn==null)
				new ExecuteSQL();
			return conn.createStatement().executeUpdate(sql);
		} catch (SQLException e) {
			System.out.println(e.getMessage());			
			return -1;
		} finally {
		}
	}
	
	public static void close() {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			conn = null;
		}
	}
	
	//登录测试账号 密码
	public static user check(String name,String password){
		int i = 0;
		user u = new user();
		String sql = "select name, password from bank where name = '"+name+"'";
		ResultSet rs = ExecuteSQL.executeQuery(sql);
		try {
			while(rs.next()){				
				u.setName(rs.getString("name"));
				u.setPassword(rs.getString("password"));	
			//	u.setbalance(rs.getFloat("balance"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ExecuteSQL.close();
		return u;
	}
	
	//注册，添加用户信息
	public static int addUser(String name,String password){
		int i = 0;
		String sql = "insert into bank(name,password,balance)"
				+ "values('"+name+"','"+password+"','"+ 0 +"')";
		i = ExecuteSQL.executeUpdate(sql);
		ExecuteSQL.close();
		return i;
		
	}
	
	//查询用户信息
	public static user query(String name){
		user u = new user();
		String sql = "select name,balance from bank where name = '"+name+"'";
		ResultSet rs =  ExecuteSQL.executeQuery(sql);
		try {
			while(rs.next()){
				u.setName(rs.getString("name"));
				u.setbalance(rs.getDouble("balance"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ExecuteSQL.close();
		return u;
	}
	
	// 修改账户的余额
	public static int  modifyMoney(String name, double balance) {
		user u = new user();
		String sql = "update  bank set balance = '" + balance + "' where name ='" + name + "'";
		int i  =  ExecuteSQL.executeUpdate(sql);
		ExecuteSQL.close();
		return i;
	}	
}
