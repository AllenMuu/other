package com.hwua.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.sql.DataSource;

import com.hwua.tools.DBHelper;

/**
 * 自定义的连接池. 池子就是容器 Connection[] conns = new Connection[10];
 * ArrayList<Connection> list = new ArrayList<>();
 * 
 * 
 * 问题: 1.单例. 2.安全吗->线程共享安全. 3.是否可以面向对象 面向接口
 * 
 * 
 * DataSource ds = new MyConnectionPool();
 * 
 * 
 * 
 * @author Administrator
 * 
 * 
 *
 */
public class MyConnectionPool implements DataSource {

	private List<Connection> connList;
	private static int count;

	public MyConnectionPool() {

		connList = new ArrayList<>(10);
		// -- 还是0
		System.out.println(connList.size());

		// -- 向容器中放入链接
		for (int i = 0; i < 10; i++) {
			try {
				Class.forName("oracle.jdbc.OracleDriver");
				Connection co = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "TC31", "123456");
				connList.add(co);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	/**
	 * 当链接总数到上限时有两个选择: * 分布式 云 * 阻塞队列.让后续想要链接的请求等待.等待已经在用链接的人把链接释放.
	 * 
	 * @return
	 * @throws SQLException
	 */
	@Override
	public Connection getConnection() throws SQLException {

		Connection conn = new MyConnectionWrap(connList.remove(0), connList);
		return conn;
	}

	@Override
	public PrintWriter getLogWriter() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLogWriter(PrintWriter out) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setLoginTimeout(int seconds) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public int getLoginTimeout() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Connection getConnection(String username, String password) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
