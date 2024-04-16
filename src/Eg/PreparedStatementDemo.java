package Eg;

import java.sql.*;

public class PreparedStatementDemo {
	public static void main(String[] args) {
		int[] id = { 13, 14, 15 };
		String[] names = { "Tran C", "Le D", "Ha E" };
		float[] Math = { 7, 8, 7 };
		float[] Phys = { 8, 9, 8 };
		float[] Chem = { 6, 7, 9 };
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/studentmanagement", "Hien",
					"04121992");
			PreparedStatement ps = conn.prepareStatement("Insert into students values(?,?,?,?,?,?)");
			for (int i = 0; i < names.length; i++) {
				ps.setInt(1, id[i]);
				ps.setString(2, names[i]);
				ps.setFloat(3, Math[i]);
				ps.setFloat(4, Phys[i]);
				ps.setFloat(5, Chem[i]);
				ps.setFloat(6, (Math[i] + Phys[i] + Chem[i]) / 3);
				ps.executeUpdate();
			}
			// Thuc hien gui den CSDL
			conn.commit();
			
			ResultSet rs = ps.executeQuery("Select * from students");
			ResultSetMetaData rsm = rs.getMetaData();
			int col_num = rsm.getColumnCount();
			for (int i = 1; i <= col_num; i++) {
				System.out.print(rsm.getColumnLabel(i) + "\t");
			}
			System.out.println();
			while (rs.next()) {
				for (int i = 1; i <= col_num; i++) {
					System.out.print(rs.getString(i) + "\t");
				}
				System.out.println();
			}
			ps.close();
			conn.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
