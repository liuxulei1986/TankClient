package com.xulei.TankClient;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Random;

public class Tank {

	public static final int XSPEED=5;
	public static final int YSPEED=5;
	
	public static final int WIDTH = 30;
	public static final int HEIGHT = 30;
	
	
	private int x, y;
	private int oldx,oldy;
	
	private TankClient tc = null;
	
	private boolean live = true;
	
	private boolean good;
	
	public boolean isGood() {
		return good;
	}

	private boolean bL = false,  
					bU = false,
					bR = false,
					bD = false;
	private static Random r = new Random();
	
	enum Direction{L,LU,U,RU,R,RD,D,LD,S};
	
	private Direction dir  = Direction.S;
	private Direction BarrelDir = Direction.D;
	
	
	private int step = r.nextInt(12) + 3;  //For enemyTank

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

	public Tank(int x, int y, boolean good ) {
		super();
		this.x = x;
		this.y = y;
		this.oldx = x;
		this.oldy = y;
		this.good = good;
	}
	
	public Tank(int x, int y,boolean good, TankClient tc) {
		this(x,y,good);
		this.tc =tc;
	}
	
	public Tank(int x, int y,boolean good, Direction dir, TankClient tc) {
		this(x,y,good,tc);
		this.dir =dir;
	}
	
	public void draw(Graphics g)
	{
		if(!live) {
			if(!good)
			{
				tc.enemyTanks.remove(this);
			return;
			}else
				return;
		}
		Color c = g.getColor();
		if(good)
		{
		g.setColor(Color.RED);
		g.fillOval(x, y, WIDTH,HEIGHT);
		}else
		{
		g.setColor(Color.BLUE);
		g.fillOval(x, y, WIDTH,HEIGHT);
		}
		g.setColor(c);
		
		switch(BarrelDir){
		case L:
			g.drawLine(x + Tank.WIDTH/2, y+Tank.HEIGHT/2, x, y+Tank.HEIGHT/2);
			break;
		case LU:
			g.drawLine(x + Tank.WIDTH/2, y+Tank.HEIGHT/2, x, y);
			break;
		case U:
			g.drawLine(x + Tank.WIDTH/2, y+Tank.HEIGHT/2, x+Tank.WIDTH/2, y);
			break;
		case RU:
			g.drawLine(x + Tank.WIDTH/2, y+Tank.HEIGHT/2, x+Tank.WIDTH, y);
			break;
		case R:
			g.drawLine(x + Tank.WIDTH/2, y+Tank.HEIGHT/2, x+Tank.WIDTH, y+Tank.HEIGHT/2); 
			break;
		case RD:
			g.drawLine(x + Tank.WIDTH/2, y+Tank.HEIGHT/2, x+Tank.WIDTH, y+Tank.HEIGHT);
			break;
		case D:
			g.drawLine(x + Tank.WIDTH/2, y+Tank.HEIGHT/2, x+Tank.WIDTH/2, y+Tank.HEIGHT);
			break;
		case LD:
			g.drawLine(x + Tank.WIDTH/2, y+Tank.HEIGHT/2, x, y+Tank.HEIGHT/2);
			break;
		case S:
			break;
		}
		
		
		move();
	}
	
	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	void move(){
		// record this previous location
		this.oldx = x;
		this.oldy = y;
		
		
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
			break;
		}
		
		if(this.dir != Direction.S)
		{
			this.BarrelDir = this.dir;
		}
		
		if( x<0 ) x = 0;
		if( y<25 ) y = 25;//title height
		if( x + Tank.WIDTH > TankClient.GAME_WIDTH) x = TankClient.GAME_WIDTH-Tank.WIDTH;
		if( y + Tank.HEIGHT > TankClient.GAME_HEIGHT) y = TankClient.GAME_HEIGHT-Tank.HEIGHT;
		
		if(!good)
		{
			Direction[] dirs = Direction.values();
			if(step == 0){
				int randomNumber = r.nextInt(dirs.length);
				dir = dirs[randomNumber];
				step = r.nextInt(12)+3;
			}
			
			
			
			
			step--;
			
			if(r.nextInt(40)>38)this.fire();
			

		}
	}
	
	private void stay()
	{
		x = oldx;
		y = oldy;
		
	}
	
	
	public void keyPressed(KeyEvent e)
	{
		int key = e.getKeyCode();
		switch(key){
			//tc.b = this.fire() ;break;
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
		case KeyEvent.VK_CONTROL : fire();break; 
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
	
	public Bullet fire(){
		if(!live) return null;
		Bullet b = new Bullet(this.tc,x+Tank.WIDTH/2-Bullet.WIDTH/2,y+Tank.HEIGHT/2-Bullet.HEIGHT/2,good,this.BarrelDir);
		tc.bullets.add(b);
		return b;
	}
	
	
	
	
	public Rectangle getRect(){
		return new Rectangle(x,y,Tank.WIDTH,Tank.HEIGHT);
	}
	
	public boolean collidesWithWall(Wall w)
	{
		if (this.live && this.getRect().intersects(w.getRect()))
		{
			this.stay();
			//this.dir = Direction.S;
			return true;
		}
		return false;
	}
	
	public boolean collodesWithTank(List<Tank> tanks)
	{
		for(Tank t: tanks)
		{
			if (this== t) continue;
			
			if (this.live && t.isLive()&&this.getRect().intersects(t.getRect()))
			{
				this.stay();
				t.stay();
				return true;
			}
		}
		return false;
	}
	

}
