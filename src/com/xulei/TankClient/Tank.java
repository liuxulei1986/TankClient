package com.xulei.TankClient;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

public class Tank {
	int x, y;
	public static final int XSPEED=5;
	public static final int YSPEED=5;
	
	
	private boolean bL = false,  
					bU = false,
					bR = false,
					bD = false;
	
	enum Direction{L,LU,U,RU,R,RD,D,LD,S};
	
	private Direction dir  = Direction.S;

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Tank(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	public void draw(Graphics g)
	{
		Color c = g.getColor();
		g.setColor(Color.RED);
		g.fillOval(x, y, 30,30);
		g.setColor(c);
		move();
	}
	
	void move(){
		switch(dir){
		case L:
			x -= XSPEED; 
			break;
		case LU:
			x -= XSPEED;
			y -= YSPEED;
			break;
		case U:
			y -= YSPEED;
			break;
		case RU:
			x += XSPEED;
			y -= YSPEED;
			break;
		case R:
			x += XSPEED; 
			break;
		case RD:
			x += XSPEED; 
			y += YSPEED;
			break;
		case D:
			y += YSPEED;
			break;
		case LD:
			x -= XSPEED;
			y += YSPEED;
			break;
		case S:
			x = 0;
			y = 0;
			break;
		}
	}
	
	public void keyPressed(KeyEvent e)
	{
		int key = e.getKeyCode();
		switch(key){
		case KeyEvent.VK_LEFT: bL=true; break;
		case KeyEvent.VK_UP:   bU=true; break;
		case KeyEvent.VK_RIGHT:bR=true; break;
		case KeyEvent.VK_DOWN: bD=true; break;
		}
		locateDirection();

	}
	
	public void keyReleased(KeyEvent e)
	{
		int key = e.getKeyCode();
		switch(key){
		case KeyEvent.VK_LEFT: bL=false; break;
		case KeyEvent.VK_UP:   bU=false; break;
		case KeyEvent.VK_RIGHT:bR=false; break;
		case KeyEvent.VK_DOWN: bD=false; break;
		}
		locateDirection();

	}
	
	void locateDirection(){
		if(bL && !bU && !bR && !bD) dir = Direction.L;
		else if(bL && bU && !bR && !bD) dir = Direction.LU;
		else if(!bL && bU && !bR && !bD) dir = Direction.U;
		else if(!bL && bU && bR && !bD) dir = Direction.RU;
		else if(!bL && !bU && bR && !bD) dir = Direction.R;
		else if(!bL && !bU && bR && bD) dir = Direction.RD;
		else if(!bL && !bU && !bR && bD) dir = Direction.D;
		else if(bL && !bU && !bR && bD) dir = Direction.LD;
		else if(!bL && !bU && !bR && !bD) dir = Direction.S;
		else dir = Direction.S;
	}
	

}
