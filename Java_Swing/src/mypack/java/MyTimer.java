package mypack.java;

public class MyTimer extends Thread {
	
	public final Canvas canvas1;
	public int cc;

	public MyTimer(Canvas canvas) {
		this.canvas1 = canvas;
		this.setDaemon(true);
	}
	
	public void run(){
		while (true){
			System.out.println("going to sleep..");
			try {
				sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("woke up");

			canvas1.change();
			//canvas1.repaint();
		}
	}
	
}