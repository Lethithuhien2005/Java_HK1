package Eg;

import java.util.*;
import java.sql.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;

public class StudentManagement extends JFrame implements ActionListener, MouseListener {
	Connection conn;
	Statement stm;
	ResultSet rst;

	Vector vData = new Vector();
	Vector vTitle = new Vector();
	JScrollPane tableResult;
	DefaultTableModel model;
	JTable tb = new JTable();

	JButton edit, delete, insert;
	int selectedrow = 0;

	public StudentManagement(String s) {
		super(s);
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/studentmanagement", "Hien", "04121992");
			stm = conn.createStatement();

			JPanel p = new JPanel();

			edit = new JButton("Edit");
			edit.addActionListener(this);
			delete = new JButton("Delete");
			delete.addActionListener(this);
			insert = new JButton("Insert");
			insert.addActionListener(this);

			p.add(edit);
			p.add(delete);
			p.add(insert);
			this.add(p, "South");

			reload();

			model = new DefaultTableModel(vData, vTitle);
			tb = new JTable(model);
			tb.addMouseListener(this);
			tableResult = new JScrollPane(tb);
			this.getContentPane().add(tableResult, "North");
			this.setSize(400, 300);
			this.setLocation(200, 100);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.setVisible(true);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void reload() {
		try {
			vTitle.clear();
			vData.clear();

			ResultSet rst = stm.executeQuery("Select * from students");
			ResultSetMetaData rstmeta = rst.getMetaData();
			
			int num_column = rstmeta.getColumnCount();
			for (int i = 1; i <= num_column; i++) {
				vTitle.add(rstmeta.getColumnLabel(i));
			}
			while (rst.next()) {
				Vector row = new Vector(num_column);
				for (int i = 1; i <= num_column; i++) {
					row.add(rst.getString(i));
				}
				vData.add(row);
			}
			rst.close();
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void delete() {
		try {
			Vector st = (Vector) vData.elementAt(selectedrow);
			String sql = "Delete from students where id =\"" + st.elementAt(0) + "\"";
			stm.executeUpdate(sql);

			vData.remove(selectedrow);

			model.fireTableDataChanged();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Delete")) {
			delete();
		}
		if (e.getActionCommand().equals("Insert")) {
			new UpdateForm("Insert formm", this, "", "", "0", "0", "0");
		}
		if (e.getActionCommand().equals("Edit")) {
			Vector st = (Vector)vData.elementAt(selectedrow);
			new UpdateForm("Edit form", this, (String) st.elementAt(0), (String) st.elementAt(1),
					(String) st.elementAt(2), (String) st.elementAt(3), (String) st.elementAt(4));
		}
	}

	public void mouseClicked(MouseEvent e) {
		selectedrow = tb.getSelectedRow();
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	public static void main(String[] args) {
		new StudentManagement("Student Management");
	}
}

class UpdateForm extends JFrame implements ActionListener {

	JLabel namelb;
	JTextField name;
	JLabel mathlb;
	JTextField math;
	JLabel physlb;
	JTextField phys;
	JLabel chemlb;
	JTextField chem;

	JLabel errorlb;
	JLabel errordetails;

	JLabel ok;
	JLabel cancel;

	StudentManagement mst;
	String id;

	public UpdateForm(String s, StudentManagement st, String i, String na, String m, String ph, String ch) {
		super(s);
		mst = st;
		Container cont = this.getContentPane();
		cont.setLayout(new GridLayout(6, 2));
		namelb = new JLabel("Name");
		name = new JTextField(na);
		cont.add(namelb);
		cont.add(name);
		mathlb = new JLabel("Math");
		math = new JTextField(m);
		cont.add(mathlb);
		cont.add(math);
		physlb = new JLabel("Physics");
		phys = new JTextField(ph);
		cont.add(physlb);
		cont.add(phys);
		chemlb = new JLabel("Chemistry");
		chem = new JTextField(ch);
		cont.add(chemlb);
		cont.add(chem);

		errorlb = new JLabel("");
		errordetails = new JLabel("");
		errorlb.setVisible(false);
		errordetails.setVisible(false);
		cont.add(errorlb);
		cont.add(errordetails);

		JButton ok = new JButton("Ok");
		JButton cancel = new JButton("Cancel");
		cont.add(ok);
		cont.add(cancel);
		ok.addActionListener(this);
		cancel.addActionListener(this);

		this.setSize(230, 200);
		this.setLocation(250, 100);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setVisible(true);
		id = i;
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Ok")) {
			insertDB();
		} else
			this.dispose();
	}

	public void insertDB() {
		if (name.getText().equals("") || math.getText().equals("") || phys.getText().equals("")
				|| chem.getText().equals("")) {
			errorlb.setText("Error");
			errordetails.setText("empty value");
			errorlb.setForeground(Color.RED);
			errordetails.setForeground(Color.RED);
			errorlb.setVisible(true);
			errordetails.setVisible(true);
		} else {
			try {
				String na = name.getText();
				float m = Float.parseFloat(math.getText());
				float ph = Float.parseFloat(phys.getText());
				float ch = Float.parseFloat(chem.getText());
				String sql = "";

				if (this.getTitle().equals("Insert form")) {
					sql = "Insert into students(Name, Math, Phys, Chem, Aver) values (\"" + name + "\"," + m + "," + ph
							+ "," + ch + "," + (m + ph + ch) / 3 + ")";
				} else {
					sql = "Update students set Name =\"" + na + "\",Math=" + m + ",Phys=" + ph + ",Chem=" + ch
							+ ",Aver=" + (m + ph + ch) / 3 + "where ID=\"" + id + "";
					mst.stm.executeUpdate(sql);
					mst.reload();
					mst.model.fireTableDataChanged();
					this.dispose();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
}

