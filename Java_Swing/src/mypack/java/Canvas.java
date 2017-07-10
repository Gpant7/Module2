package mypack.java;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;

public class Canvas extends JLabel {
	
	//private static final Color preserveBackgroundColor = Color.black;
	//private int x;
	//private int y;
	//private int rad = 50;
	//private int i = 0;
	private int j = 0;
	private int p = 0;
	private int t = 0;
	private int [] xs = new int[100];
	private int [] ys = new int[100];
	public int  cc;
	private int [] lenght= new int[100];
	public int limit=0;


	public Canvas() {
		super();
		setPreferredSize(new Dimension(500,500));
		//setBackground(Color.BLACK);
		//setBackground(preserveBackgroundColor);
       
	}
	
	public void paint(Graphics g){
		for (j=0; j<limit; j++)
		{
			System.out.println("Skataaaaaaaaaaa");
			System.out.println(cc);
			if (cc ==1)
				g.setColor(Color.RED);
			else
				g.setColor(Color.GREEN);
		g.drawRect(xs[j]-lenght[j]/2,ys[j]-lenght[j]/2, lenght[j], lenght[j]);
		}
	}
	public void create(int x,int y) {
		int i=0;
		for(p=0;p<limit;p++)
		{
			if ((x < (xs[p]+lenght[p]/2)) && (x > (xs[p]-lenght[p]/2)) && (y < (ys[p]+lenght[p]/2)) && (y > (ys[p]-lenght[p]/2)))
			{
				System.out.println("Mphkaaaaaaaaaaaaaaaaa");
				 xs[p]=-900;
				 ys[p]=-900;
				 i=1; 
				repaint();
				
			}
		}
		if (i==0)
		{	
			limit++;
			lenght[limit-1]=(int) (Math.random() * 500) ;	;
			xs[limit-1]=x;
			ys[limit-1]=y;
			repaint();
		}
	}

	public void change() {
		if (t==0){
			t=1;
			cc=0;
			repaint();
		}
		else{
			t=0;
			cc=1;
			repaint();
		}	
		
	}
	

}
