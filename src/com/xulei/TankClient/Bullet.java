package com.xulei.TankClient;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.List;

import com.xulei.TankClient.Tank.Direction;

public class Bullet {
	public static final int XSPEED	 = 10;
	public static final int YSPEED	 = 10;
	
	public static final int WIDTH = 10;
	public static final int HEIGHT = 10;
	
	private TankClient tc;
	
	private boolean good;
	


	int x, y;
	Tank.Direction dir;
	private boolean Live = true;
	
	public boolean isLive() {
		return Live;
	}


	public Bullet(int x, int y, Direction dir) {
		super();
		this.x = x;
		this.y = y;
		this.dir = dir;
	}
	
	public Bullet(TankClient tc, int x, int y, Direction dir) {
		this(x,y,dir);
		this.tc = tc;
	}
	
	public Bullet(TankClient tc, int x, int y, boolean good, Direction dir) {
		this(tc,x,y,dir);
		this.good = good;
	}

	public void draw(Graphics g)
	{
		if(!this.Live) {
			tc.bullets.remove(this);
			return;
			}
		Color c = g.getColor();
		g.setColor(Color.BLACK);
		g.fillOval(x, y, WIDTH, HEIGHT);
		g.setColor(c);
		
		move();
	}

	private void move() {
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
			default:
				break;
			}
			
			if(x<0||y<0||x>TankClient.GAME_WIDTH||y>TankClient.GAME_HEIGHT){
				Live =false;
				tc.bullets.remove(this);
			}
			
		}
	
	
	public Rectangle getRect(){
		return new Rectangle(x,y,Bullet.WIDTH,Bullet.HEIGHT);
	}
	
	public boolean hitTank(Tank t){//collision detection

		if(
				this.Live&&
				this.getRect().intersects(t.getRect())&&
				t.isLive()==true&&
				(this.good != t.isGood())
			){
			t.setLive(false);
			this.Live=false;
			Explode e = new Explode(this.x, this.y, this.tc);
			tc.explodes.add(e);
			return true;		
		}
		return false;
	}
	
	public boolean hitTanks(List<Tank> Tanks)
	{
		for(Tank t: Tanks)
		{
			if(hitTank(t)){
				return true;
			}
		}
		return false;
	}
	
	public boolean hitWall(Wall w)
	{
		if (this.Live && this.getRect().intersects(w.getRect()))
		{
			this.Live = false;
			return true;
		}
		return false;
	}
	
	
}
