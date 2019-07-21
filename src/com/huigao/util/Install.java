package com.huigao.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class Install {
	
	public static List<String> readSql(String fileName) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(
				new FileInputStream(fileName), "UTF-8"));
		List<String> sqlList = new ArrayList<String>();
		StringBuilder sqlSb = new StringBuilder();
		String s = null;
		while ((s = br.readLine()) != null) {
			if (s.startsWith("/*") || s.startsWith("#") || s.trim().equals("")) { 
				continue;
			}
			if (s.endsWith(";")) {
				sqlSb.append(s);
				sqlSb.setLength(sqlSb.length() - 1);
				sqlList.add(sqlSb.toString());
				sqlSb.setLength(0);
			} else {
				sqlSb.append(s);
			}
		}
		br.close();
		return sqlList;
	}
	
	
	
	public static void createDb(String dbHost, String dbPort, String dbName,
			String dbUser, String dbPassword) throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
		String connStr = "jdbc:mysql://" + dbHost + ":" + dbPort + "?user="
				+ dbUser + "&password=" + dbPassword + "&characterEncoding=GBK";
		Connection conn = DriverManager.getConnection(connStr);
		Statement stat = conn.createStatement();
		String sql = "drop database if exists " + dbName;
		stat.execute(sql);
		sql = "create database " + dbName
				+ " CHARACTER SET UTF8";
		stat.execute(sql);
		stat.close();
		conn.close();
	}
	
	public static void changeDbCharset(String dbHost, String dbPort,
			String dbName, String dbUser, String dbPassword) throws Exception {
		Connection conn = getConn(dbHost, dbPort, dbName, dbUser, dbPassword);
		Statement stat = conn.createStatement();
		String sql = "ALTER DATABASE " + dbName
				+ " CHARACTER SET UTF8";
		stat.execute(sql);
		stat.close();
		conn.close();
	}
	
	
	public static Connection getConn(String dbHost, String dbPort,
			String dbName, String dbUser, String dbPassword) throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
		String connStr = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName
				+ "?user=" + dbUser + "&password=" + dbPassword
				+ "&characterEncoding=UTF8";
		Connection conn = DriverManager.getConnection(connStr);
		return conn;
	}
	
	
	public static void createTable(String dbHost, String dbPort, String dbName,
			String dbUser, String dbPassword, List<String> sqlList) throws Exception {
		Connection conn = getConn(dbHost, dbPort, dbName, dbUser, dbPassword);
		Statement stat = conn.createStatement();
		for (String sql : sqlList) stat.addBatch(sql);
		stat.executeBatch();
		stat.close();
		conn.close();
	}
	
	public static void createProcedure(String dbHost, String dbPort, String dbName,
			String dbUser, String dbPassword, String sql) throws Exception {
		Connection conn = getConn(dbHost, dbPort, dbName, dbUser, dbPassword);
		Statement stat = conn.createStatement();
		stat.execute(sql);
		stat.close();
		conn.close();
	}
	
	public static String readProc(String fileName) throws Exception{ 
		return FileUtils.readFileToString(new File(fileName));
	}
	
	public static void updateDbConf(String fileName, String dbHost, String dbPort, String dbName, String dbUser, String dbPassword) throws Exception {
		// --------- 更新 jdbc.properties --------
		String s = FileUtils.readFileToString(new File(fileName));
		String url = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName + "?useUnicode=true&amp;characterEncoding=UTF-8";
		s = s.replaceFirst("XIETONG_URL", url); 
		s = s.replaceFirst("XIETONG_USER", dbUser);
		s = s.replaceFirst("XIETONG_PASSWORD", dbPassword);
		FileUtils.writeStringToFile(new File(fileName), s);
	}
	
	public static void updateWebConf(String fromFile, String toFile) throws Exception {
		// --------- 更新 web.xml --------
		FileUtils.copyFile(new File(fromFile), new File(toFile));
	}
	
	public static void main(String[] args) throws Exception {
//		Install.createDb("localhost", "3306", "xietong_db", "root", "admin");
//		List<String> list = Install.readSql("C:/xietong_db.sql");
//		Install.createTable("localhost", "3306", "xietong_db", "root", "admin", list);
//		System.out.println("---------------------------------------------");
//		Install.createProcedure("localhost", "3306", "xietong_db", "root", "admin", Install.readProc("c:/statistic_proc.sql"));
//		System.out.println("ok");
	}
}
