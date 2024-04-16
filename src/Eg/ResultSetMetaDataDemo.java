package Eg;

import java.sql.*;

public class ResultSetMetaDataDemo {
	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/studentmanagement", "Hien",
					"04121992");
			Statement sm = conn.createStatement();
			ResultSet rs = sm.executeQuery("Select * from students");
			ResultSetMetaData rsm = rs.getMetaData();
			int col_num = rsm.getColumnCount();
			for (int i = 1; i <= col_num; i++) {
				System.out.print(rsm.getColumnLabel(i) + "   ");
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
