package Eg;

import java.sql.*;
import java.util.*;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

public class ViewStudents {
	public static void main(String[] args) {
		Vector vData = null, vTitle = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/studentmanagement", "Hien",
					"04121992");
			Statement stm = conn.createStatement();
			ResultSet rs = stm.executeQuery("Select * from students");
			ResultSetMetaData rstmeta = rs.getMetaData();
			int col_num = rstmeta.getColumnCount();
			vTitle = new Vector(col_num);
			for (int i = 1; i <= col_num; i++) {
				vTitle.add(rstmeta.getColumnLabel(i));
			}
			vData = new Vector(10, 10);
			while (rs.next()) {
				Vector row = new Vector(col_num);
				for (int i = 1; i <= col_num; i++) {
					row.add(rs.getString(i));
				}
				vData.add(row);
			}
			rs.close();
			stm.close();
			conn.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		JScrollPane tableResult = new JScrollPane(new JTable(vData, vTitle));
		JFrame f = new JFrame();
		f.setSize(600, 400);  
		f.setContentPane(tableResult);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}
}
