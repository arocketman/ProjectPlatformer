package org.platformer.utils;

import org.newdawn.slick.Graphics;

public interface IDefaultGame
{
	/** This interface implements the 3 basic methods of a game*/
	
	public void init();
	public void update();
	public void render(Graphics g);
}
