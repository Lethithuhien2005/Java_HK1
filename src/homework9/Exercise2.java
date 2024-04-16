package homework9;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.*;

public class Exercise2 extends JFrame {
	private JTextField usernameField;
	private JTextField passwordField;
	private JTextField urlField;
	private JTextField driverField;
	private JComboBox<String> tableComboBox;
	private JTable table;

	public Exercise2() {
		super("Configuration Table");

		// Create the text fields
		usernameField = new JTextField(20);
		passwordField = new JTextField(20);
		urlField = new JTextField(20);
		driverField = new JTextField(20);

		// Create the button
		JButton configButton = new JButton("Config");
		configButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					// Save the configuration to a file
					PrintWriter writer = new PrintWriter("config.txt");
					writer.println(usernameField.getText());
					writer.println(passwordField.getText());
					writer.println(urlField.getText());
					writer.println(driverField.getText());
					writer.close();

					// Connect to the database
					Connection conn = DriverManager.getConnection(urlField.getText(), usernameField.getText(),
							passwordField.getText());

					// Get a list of all tables in the database
					DatabaseMetaData md = conn.getMetaData();
					ResultSet rs = md.getTables(null, null, "%", null);

					// Add the table names to a combo box model
					DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
					while (rs.next()) {
						model.addElement(rs.getString(3));
					}

					// Create a combo box and add it to the frame
					tableComboBox = new JComboBox<String>(model);
					tableComboBox.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							String tableName = (String) tableComboBox.getSelectedItem();
							try {
								Statement stmt = conn.createStatement();
								ResultSet tableData = stmt.executeQuery("SELECT * FROM " + tableName);
								table = new JTable(buildTableModel(tableData));
								JOptionPane.showMessageDialog(null, new JScrollPane(table));
							} catch (SQLException ex) {
								ex.printStackTrace();
							}
						}
					});
					add(tableComboBox, BorderLayout.NORTH);
					pack();
				} catch (IOException ex) {
					ex.printStackTrace();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		});

		// Add the components to the frame
		JPanel panel = new JPanel(new GridLayout(5, 2));
		panel.add(new JLabel("Username:"));
		panel.add(usernameField);
		panel.add(new JLabel("Password:"));
		panel.add(passwordField);
		panel.add(new JLabel("Database URL:"));
		panel.add(urlField);
		panel.add(new JLabel("Driver:"));
		panel.add(driverField);
		panel.add(new JLabel(""));
		panel.add(configButton);
		add(panel);

		// Set the frame properties
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
		pack();
	}

	public static DefaultTableModel buildTableModel(ResultSet rs) throws SQLException {
		ResultSetMetaData metaData = rs.getMetaData();

		// Get column names
		int columnCount = metaData.getColumnCount();
		String[] columnNames = new String[columnCount];
		for (int i = 1; i <= columnCount; i++) {
			columnNames[i - 1] = metaData.getColumnName(i);
		}

		// Get row data
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
