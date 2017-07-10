package mypack.java;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class MyFrame extends JFrame implements  MouseListener 
{
	private Canvas canvas = new Canvas();
	private MyTimer myThread = new MyTimer(canvas);
	//private int value;

public MyFrame() throws HeadlessException {
	super("PANTERIS");
	setLayout(new BorderLayout());
	add(canvas, BorderLayout.CENTER);
	
	canvas.addMouseListener(this);
	
	setVisible(true);
	setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	
	myThread.start(); 
	pack();
	
	}

public static void main(String [] args){
	new MyFrame();
	}

@Override
public void mouseClicked(MouseEvent e) {
	int x=e.getX();
    int y=e.getY();
	canvas.create(x,y);
	
}

@Override
public void mouseEntered(MouseEvent e) {
	// TODO Auto-generated method stub
	
}

@Override
public void mouseExited(MouseEvent e) {
	// TODO Auto-generated method stub
	
}

@Override
public void mousePressed(MouseEvent e) {
	// TODO Auto-generated method stub
	
}

@Override
public void mouseReleased(MouseEvent e) {
	// TODO Auto-generated method stub
	
}




}