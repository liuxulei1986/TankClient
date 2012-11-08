package com.xulei.TankClient;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;

/**
 * 
 * Class TankClient is the main windows of game.
 * Instruction of this game 
 * Ctrl----Fire
 * A ------Super Fire
 * F2 -----New Life
 * up -----Move Up
 * Down ---Move Down
 * Left----Move Left
 * Right---Move Right
 * @author Xulei
 *
 */
@SuppressWarnings("serial")
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
	
	Buffer bb = new Buffer();

	
	Image offScreenImage = null;//double buffer
	@Override
	public void paint(Graphics g) {
		/*
		 * create and show info in game
		 */
		g.drawString("bullets count: "+bullets.size(), 10, 50);
		g.drawString("explodes count: "+explodes.size(), 10, 70);
		g.drawString("enemyTanks count: "+enemyTanks.size(), 10, 90);
		g.drawString("Tank Life: "+this.myTank.getLife(), 10, 110);
		
		if(this.enemyTanks.size()<=0)
		{
			for(int i=0; i <5 ;i++)
			{
				this.enemyTanks.add(new Tank(50+40*(i+1),50,false,Direction.D,this));
			}
		}
		
		
		
		myTank.draw(g);
		myTank.eat(bb);
		w1.draw(g);
		w2.draw(g);
		bb.draw(g);
//		enemyTank.draw(g);

		for(int i = 0; i < enemyTanks.size() ; i++)
		//for(Tank t : enemyTanks)
		{
			Tank t = enemyTanks.get(i);
			t.draw(g);
			t.collidesWithWall(w1);
			t.collidesWithWall(w2);
			t.collodesWithTank(this.enemyTanks);
		}
		
		for(int i = 0; i < bullets.size() ; i++)
		{
			Bullet b = bullets.get(i);
			b.draw(g);
			b.hitTanks(enemyTanks);
			b.hitTank(myTank);
			b.hitWall(w1);
			b.hitWall(w2);
			
			
			
		
		//	else
		//		bullets.remove(b);
		}
		for(int i = 0; i < explodes.size() ; i++)
		//for(Explode e: explodes)
		{
			Explode e = explodes.get(i);
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
	/**
	 * show the Frame in game
	 */

	public void launchFrame()
	{
		
		int initTankCount = 10;
		
		
		
		initTankCount = Integer.parseInt(PropertyManager.getProperty("initTankCount"));
		
		
		for(int i=0; i <initTankCount ;i++)
		{
			this.enemyTanks.add(new Tank(50+40*(i+1),50,false,Direction.D,this));
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
