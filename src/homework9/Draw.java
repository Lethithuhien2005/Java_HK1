package homework9;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Draw extends JFrame implements MouseListener, ActionListener {
	private Vector vec;
	private boolean circle;
	private boolean rectangle;
	private MyPoint point;
	
	public Draw() {
		circle = false;
		rectangle = false;
		this.setTitle("Draw");
		this.setSize(300, 400);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addMouseListener(this);
		JButton jButton_cir = new JButton("Circle");
		jButton_cir.addActionListener(this);
		JButton jButton_rec = new JButton("Rectangle");
		jButton_rec.addActionListener(this);
		
		
		this.add(jButton_cir);
		this.add(jButton_rec);
		this.setLayout(new FlowLayout());
		this.add(jButton_cir);
		this.add(jButton_rec);
		this.setVisible(true);
		vec = new Vector();

		this.addWindowListener(new WindowAdapter() {
			public void WindowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		circle = e.getActionCommand().equals("Circle");
		rectangle = e.getActionCommand().equals("Rectangle");
	}
	public void paint(Graphics g) {
		for (int i=0; i<vec.size(); i++) {
			point = (MyPoint)vec.get(i);
			if (circle) {
				g.drawOval(point.getX(), point.getY(), 20, 20);
			}
			if (rectangle) {
				g.drawRect(point.getX(), point.getY(), 20, 40);
			}
		}
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		vec.add(new MyPoint(e.getX(), e.getY()));
		repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}
	

	public static void main(String[] args) {
		new Draw();
	}
}
