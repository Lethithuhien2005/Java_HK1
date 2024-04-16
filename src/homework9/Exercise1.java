package homework9;

import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Exercise1 {

	public static void main(String[] args) {
		try {

			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/studentmanagement", "Hien",
					"04121992");

			DatabaseMetaData md = conn.getMetaData();
			ResultSet rs = md.getTables(null, null, "%", null);

			DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
			while (rs.next()) {
				model.addElement(rs.getString(3));
			}

			JComboBox<String> comboBox = new JComboBox<String>(model);
			JFrame frame = new JFrame();
			frame.add(comboBox);
			frame.setSize(600, 600);
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);

			comboBox.addActionListener(e -> {
				String tableName = (String) comboBox.getSelectedItem();
				try {
					Statement stmt = conn.createStatement();
					ResultSet tableData = stmt.executeQuery("SELECT * FROM " + tableName);
					JTable table = new JTable(buildTableModel(tableData));
					JOptionPane.showMessageDialog(null, new JScrollPane(table));
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			});
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static DefaultTableModel buildTableModel(ResultSet rs) throws SQLException {
		ResultSetMetaData metaData = rs.getMetaData();

		int columnCount = metaData.getColumnCount();
		String[] columnNames = new String[columnCount];
		for (int i = 1; i <= columnCount; i++) {
			columnNames[i - 1] = metaData.getColumnName(i);
		}

		Object[][] data = new Object[100][columnCount];
		int rowCount = 0;
		while (rs.next()) {
			for (int i = 1; i <= columnCount; i++) {
				data[rowCount][i - 1] = rs.getObject(i);
			}
			rowCount++;
		}

		return new DefaultTableModel(data, columnNames);
	}
}
