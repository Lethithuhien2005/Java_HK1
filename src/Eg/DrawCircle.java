package Eg;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Vector;

import javax.swing.JFrame;

import homework9.MyPoint;

public class DrawCircle extends JFrame implements MouseListener{
	private Vector vec;
	private MyPoint myPoint;
	
	public DrawCircle() {
		this.setTitle("Draw");
		this.setSize(300, 400);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		vec = new Vector();
		this.addMouseListener(this);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		this.setVisible(true);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		vec.add(new MyPoint(e.getX(), e.getY()));
		repaint();
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void paint(Graphics g) {
		for (int i=0; i<vec.size(); i++) {
			myPoint = (MyPoint)vec.get(i);
			g.drawOval(myPoint.getX(), myPoint.getY(), 20, 20);
		}
	}
	public static void main(String[] args) {
		new DrawCircle();
	}
}
