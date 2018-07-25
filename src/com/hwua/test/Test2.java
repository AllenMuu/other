package com.hwua.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.hwua.tools.DBHelper;

public class Test2 {
	public static void main(String[] args) {
		DBHelper helper = DBHelper.getInstance();
		Connection conn = helper.getConnection();
		// -- 如果没有设置自动提交为false
		try {
			conn.setAutoCommit(false);
		} catch (SQLException e) {
			System.out.println("重置自动提交.");
		}

		try {
			try {
				String sql1 = "update t_account set t_money = t_money - 100 where t_id = 1";
				// -- 先执行账户1扣款
				PreparedStatement pStatement1 = conn.prepareStatement(sql1);
				// -- 执行sql
				pStatement1.executeUpdate();
				// int i = 1/0;
				// -- 再执行账户2入账
				String sql2 = "update t_account set t_money = t_money + 100 where t_id = 2";
				PreparedStatement pStatement2 = conn.prepareStatement(sql2);
				pStatement2.executeUpdate();
				// -- commit;
				conn.commit();
			} finally {
				conn.rollback();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}

}
