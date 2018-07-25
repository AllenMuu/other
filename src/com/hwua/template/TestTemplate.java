package com.hwua.template;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import com.hwua.domain.Account;
import com.hwua.tools.DBHelper;

public class TestTemplate<T> {
	
	public static void main(String[] args) {
		TestTemplate<List<Account>> tt = new TestTemplate<>();
		//-- 测试插入语句
		//-- 双引号中不可以使用双引号.如果想用改成单引号
		//tt.updateSQL("insert into t_account values(?,?,?)", 1,"张三",888);
		//-- 执行更新语句
		//tt.updateSQL("update t_account set t_money = t_money + ? where t_id = ?", 1000,5);
		//-- 测试删除语句
		//tt.updateSQL("delete from t_account where t_id = ?", 1,2,3,4,5);
		
		List<Account> list = tt.testQuery("select * from t_account", new ResultSetHandler<List<Account>>() {

			@Override
			public List<Account> handler(ResultSet rSet) {
				
				List<Account> list = new ArrayList<>();
				try {
					while (rSet.next()) {
						Account a = new Account();
						a.setAccountId(rSet.getInt("t_id"));
						a.setAccountName(rSet.getString("t_name"));
						a.setAccountBalance(rSet.getInt("t_money"));
						list.add(a);
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return list;
			}
				
		});
		
		if (list != null) {
			for (Account account : list) {
				System.out.println(account);
			}
		}
	}

	/**
	 * 测试写修改:
	 * 		增加
	 * 		删除
	 * 		更新
	 * 
	 * Bug:如果可变参,写多了或少了.会出事.
	 * 		使用元数据.根据sql语句中的问号来执行替换参数问题
	 * Java中有三种元数据.这里我们使用参数元数据
	 * 			
	 * 
	 */

	public void updateSQL(String sql,Object...params) {
		try {
			//-- 获取Connection 
			Connection conn = DBHelper.getInstance().getConnection();
			//-- 设置自动提交
			conn.setAutoCommit(true);
			//-- 通过链接获取PreparedStatement;
			PreparedStatement pStatement = conn.prepareStatement(sql);
			//-- 替换占位符
			/*
			for (int i = 0; i < params.length; i++) {
				pStatement.setObject(i+1, params[i]);
			}*/
			
			//-- 获取SQL语句中占位符的数量
			int count = pStatement.getParameterMetaData().getParameterCount();
			for (int i = 0; i < count; i++) {
				pStatement.setObject(i+1, params[i]);
			}

			//-- 执行sql语句
			pStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	/***
	 * 通过内部接口来提供一个结果集的持有者.
	 * @author Administrator
	 *
	 * @param <T>
	 */
	interface ResultSetHandler<T>{
		T handler(ResultSet rSet);
	}
	
	
	public T testQuery(String sql,ResultSetHandler<T> handler,Object...params) {
		try {
			//--  获取链接
			Connection conn = DBHelper.getInstance().getConnection();
			//-- 获取预处理对象PreparedStatement对象
			PreparedStatement pStatement = conn.prepareStatement(sql);
			//-- 好事成双
			int count = pStatement.getParameterMetaData().getParameterCount();
			for (int i = 0; i < count; i++) {
				pStatement.setObject(i+1, params[i]);
			}
			ResultSet rSet= pStatement.executeQuery();			
			//-- 甩锅.甩给调用该方法的人
			return handler.handler(rSet);
					
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
}

