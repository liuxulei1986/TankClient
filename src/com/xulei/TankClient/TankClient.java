package com.xulei.TankClient;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;

import com.xulei.TankClient.Tank.Direction;

public class TankClient extends Frame{
	public static final int GAME_WIDTH = 800;
	public static final int GAME_HEIGHT = 600;
	
	Tank myTank = new Tank(50,50, true ,this);
	//Tank enemyTank = new Tank(100,100,false,this);
	
	Wall w1 = new Wall(100,200,20,150, this);
	Wall w2 = new Wall(300,100,300,20, this);
	
	
	List<Bullet> bullets = new ArrayList<Bullet>();
	List<Explode> explodes = new ArrayList<Explode>();
	List<Tank> enemyTanks = new ArrayList<Tank>();
	
	

	
	Image offScreenImage = null;//double buffer
	@Override
	public void paint(Graphics g) {
		g.drawString("bullets count: "+bullets.size(), 10, 50);
		g.drawString("explodes count: "+explodes.size(), 10, 70);
		g.drawString("enemyTanks count: "+enemyTanks.size(), 10, 90);
		myTank.draw(g);
		w1.draw(g);
		w2.draw(g);
//		enemyTank.draw(g);

		for(Tank t : enemyTanks)
		{
			t.draw(g);
			t.collidesWithWall(w1);
			t.collidesWithWall(w2);
			t.collodesWithTank(this.enemyTanks);
		}
		
		for(Bullet b : bullets)
		{
//			b.hitTank(enemyTank);
			b.hitTanks(enemyTanks);
			b.hitTank(myTank);
			b.draw(g);
			b.hitWall(w1);
			b.hitWall(w2);
			
			
		
		//	else
		//		bullets.remove(b);
		}
		
		for(Explode e: explodes)
		{
			e.draw(g);
		}
	}
	
	public void update(Graphics g){
		//super.update(g);
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
		for(int i=0; i <10 ;i++)
		{
			this.enemyTanks.add(new Tank(50+40*(i+1),50,false,Tank.Direction.D,this));
		}
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
