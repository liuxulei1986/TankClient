package com.xulei.TankClient;

import java.awt.Color;
import java.awt.Graphics;

public class Explode {
	
	int x, y;
	private boolean live = true;
	
	private TankClient tc;
	
	
	public Explode(int x, int y, TankClient tc) {
		super();
		this.x = x;
		this.y = y;
		this.tc = tc;
	}




	int[] diameter ={4,7,12,18,26,32,49,30,14,6};//diameter of Explode
	/*
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	
	
	private static Image[] imgs = {
		tk.getImage(Explode.class.getClassLoader().getResource("image/0.git")),
		
		
	};
	
	private static boolean init = false; 
	*/
	
	int step = 0;
	

	
	
	public void draw(Graphics g)
	{
		/*if(false == init)
		{
			for (int i = 0; i < imgs.length; i++) {
				g.drawImage(imgs[i], -100, -100, null);
			}
			init = true;
		}*/
		if(!live) {
			tc.explodes.remove(this);
			return;
		}
		if(step == diameter.length)
		{
			live = false;
			step = 0;
			return;
		}
		Color c  = g.getColor();
		g.setColor(Color.GRAY);
		g.fillOval(x,y,diameter[step],diameter[step]);
		g.setColor(c);
		step ++;
	}

}
