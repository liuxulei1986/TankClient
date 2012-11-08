package com.xulei.TankClient;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Buffer {
	
	int x,y,w,h;
	TankClient tc;
	int step = 0;
	private boolean life = true;
	
	public boolean isLife() {
		return life;
	}


	public void setLife(boolean life) {
		this.life = life;
	}

	//Moving path of Buffer. 
	private int[][] pos={
						{350,300},{360,300},{375,275},{400,200},{360,270},{365,290},{350,340},{340,280}
					};
	
	public Buffer(){
		x=pos[0][0];
		y=pos[0][1];
		w = h = 15;
	}
	
	
	public void draw(Graphics g)
	{
		if(!life) return;
		Color c = g.getColor();
		g.setColor(Color.MAGENTA);
		g.fillRect(x,y,w,h);
		g.setColor(c);
		this.move();
	}
	
	private void move(){
		step++;
		
		if(step == pos.length){
			step = 0;
		}
		
		x=this.pos[step][0];
		y=this.pos[step][1];
	}
	
	public Rectangle getRect(){
		return new Rectangle(x, y, w, h);
	}
	

	

}
