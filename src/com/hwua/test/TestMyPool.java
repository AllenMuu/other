package com.hwua.test;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Test;

public class TestMyPool {
	
	@Test
	public void tt() {
		//-- 编译看左边.运行看右边.
		DataSource ds = new MyConnectionPool();
		//-- 无法面向接口
		
	}
	
	@Test
	public void test() {
		MyConnectionPool mvp = new MyConnectionPool();
		for (int i = 0; i < 5; i++) {
			try {
				Connection con1 = mvp.getConnection();
				//-- 才是我们重写过的close方法.
				con1.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
