package Eg;

import java.sql.*;

public class JDBCDemo {
	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/studentmanagement", "Hien",
					"04121992");
			Statement sm = conn.createStatement();
			sm.executeUpdate("Insert into students values (9, \"NVQuan\", 8,7,9,(8+7+9)/3)");
			sm.executeUpdate("Insert into students values (10, \"PVThuan\", 8,5,9, (8+5+9)/3)");
			ResultSet rs = sm.executeQuery("Select * from students");
			ResultSetMetaData rsm = rs.getMetaData();
			int col_num = rsm.getColumnCount();
			for (int i = 1; i <= col_num; i++) {
				System.out.print(rsm.getColumnLabel(i) + "\t");
				}
			System.out.println("");
			while (rs.next()) {
				for (int i = 1; i < col_num; i++) {
					System.out.print(rs.getString(i) + "\t");
				}
				System.out.println("");
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
