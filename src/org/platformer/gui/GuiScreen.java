package org.platformer.gui;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;

public abstract class GuiScreen implements IGui
{
	public ArrayList<IGui> guiPart = new ArrayList<IGui>();
	
	@Override
	public void init()
	{
		for(IGui part : guiPart)
		{
			part.init();
		}
	}
	
	@Override
	public void update()
	{
		for(IGui part : guiPart)
		{
			part.update();
		}
	}

	@Override
	public void render(Graphics g)
	{
		for(IGui part : guiPart)
		{
			part.render(g);
		}
	}

}
