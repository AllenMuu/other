package com.hwua.tools;

import java.sql.Connection;
import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbcp2.BasicDataSourceFactory;

public class DBHelper {

	private DBHelper() {
	};

	public static DBHelper getInstance() {
		return Singleton.ENUM_INSTANCE.getInstance();
	}

	/**
	 * 利用枚举来实现单例.并解决线程安全为题.
	 * 
	 * @author Administrator
	 *
	 */
	private static enum Singleton {
		// -- 当前枚举的实例
		ENUM_INSTANCE;
		// -- DBHelper的实例
		private DBHelper instance;

		// -- JVM会帮我们保证该方法只会被执行一次.
		private Singleton() {
			instance = new DBHelper();
		}

		public DBHelper getInstance() {
			return instance;
		}
	}

	/**
	 * 获取链接的
	 * 
	 * @return
	 */
	public Connection getConnection() {
		Connection mConnection = null;
		try {
			if (mConnection == null) {
				Properties pro = new Properties();
				pro.load(this.getClass().getClassLoader().getResourceAsStream("db_config.properties"));
				BasicDataSource dataSource = BasicDataSourceFactory.createDataSource(pro);
				mConnection = dataSource.getConnection();
			}
		} catch (Exception e) {
			System.out.println("获取链接失败!");
		}
		return mConnection;
	}

}
