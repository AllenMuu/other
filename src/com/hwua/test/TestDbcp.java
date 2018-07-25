package com.hwua.test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbcp2.BasicDataSourceFactory;
import org.junit.Test;

public class TestDbcp {
	
	
	@Test
	public void testDbcp() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName("oracle.jdbc.OracleDriver");
		dataSource.setUrl("jdbc:oracle:thin:@localhost:1521:xe");
		dataSource.setUsername("TC31");
		dataSource.setPassword("123456");
		
		try {
			Connection conn = dataSource.getConnection();
			if (!conn.isClosed()) {
				conn.close();
				System.out.println("链接已经关闭");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				dataSource.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	@Test
	public void testDbcp2() {
		//-- 目前只学过Properties配置.但是BasicDataSource中没有和Properteis有关的方法.需要换一个
		//BasicDataSource dataSource = new BasicDataSource();
		Properties properties = new Properties();
		InputStream is = this.getClass().getClassLoader().getResourceAsStream("db_config.properties");
		BasicDataSource dataSource = null;
		try {
			properties.load(is);
			dataSource = BasicDataSourceFactory.createDataSource(properties);
			Connection conn = dataSource.getConnection();
			if (!conn.isClosed()) {
				conn.close();
				System.out.println("使用配置文件来获取链接.链接已经成功关闭");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if (!dataSource.isClosed()) {
					dataSource.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		
	}
}
