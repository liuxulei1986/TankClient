package com.xulei.TankClient;

import java.awt.*;
import java.awt.event.*;
import java.io.OutputStream;

public class TankClient extends Frame{
	public static final int GAME_WIDTH = 800;
	public static final int GAME_HEIGHT = 600;
	
	Tank myTank = new Tank(50,50);
	
	Image offScreenImage = null;//double buffer
	@Override
	public void paint(Graphics g) {
		myTank.draw(g);
	}
	
	public void update(Graphics g){
		super.update(g);
		if(offScreenImage == null)
		{
			offScreenImage = this.createImage(GAME_WIDTH,GAME_HEIGHT);
		}
		Graphics gOffScreen = offScreenImage.getGraphics();
		Color c = gOffScreen.getColor();
		gOffScreen.setColor(Color.GREEN);//refresh;
		gOffScreen.fillRect(0,0,GAME_WIDTH,GAME_HEIGHT);
		gOffScreen.setColor(c);
		paint(gOffScreen);
		g.drawImage(offScreenImage, 0, 0, null);
	}

	public void launchFrame()
	{
		this.setLocation(400,300);
		this.setSize(GAME_WIDTH,GAME_HEIGHT);
		this.setTitle("TankClient");
		this.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}
		});
		this.setResizable(false);
		this.setBackground(Color.GREEN);
		
		this.addKeyListener(new KeyMonitor());
		
		this.setVisible(true);//display begin
		
		new Thread (new PaintThread()).start();
	}
	
	public static void main(String[] args){
		TankClient tc = new TankClient();
		tc.launchFrame();
	}
	
	private class PaintThread implements Runnable{
		public void run(){
			while(true)
			{
				repaint();//call update()-> paint();
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private class KeyMonitor extends KeyAdapter{
		
/*
 * inherit only need to implement methods that you interested in. However, interface need to implement all of them.(non-Javadoc)
 * that's why use inherit here.
 * @see java.awt.event.KeyAdapter#keyPressed(java.awt.event.KeyEvent)
 */

		@Override
		public void keyPressed(KeyEvent e) {
			// test  System.out.println("OK");
			myTank.keyPressed(e);
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			
			myTank.keyReleased(e);
		} 
		
		
		
	}

}
