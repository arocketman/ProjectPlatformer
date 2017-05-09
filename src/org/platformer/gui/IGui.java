package org.platformer.gui;

import org.newdawn.slick.Graphics;

public interface IGui
{
	public boolean pausesGame();
	public void init();
	public void update();
	public void render(Graphics g);
}
