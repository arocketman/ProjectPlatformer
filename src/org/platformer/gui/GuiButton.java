package org.platformer.gui;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.platformer.Main;
import org.platformer.register.RegisterTextures;
import org.platformer.utils.GuiUtils;
import org.platformer.utils.GuiUtils.ALIGN;

public class GuiButton implements IGui
{
	public int id = 0;
	public int posX = 0;
	public int posY = 0;
	public int width = 0;
	public int height = 0;
	public String text = "";
	public Color color;
	public ALIGN align;
	public GuiScreen parent;
	public boolean enabled = true;
	
	private int timer = 0;
	private boolean mouseClicked = false;

	public GuiButton(GuiScreen p, int i, int x, int y, int w, int h, String t, Color c, ALIGN a)
	{
		this.parent = p;
		this.id = i;
		this.posX = x;
		this.posY = y;
		this.text = t;
		this.color = c;
		this.width = w;
		this.height = h;
		this.align = a;
	}

	public GuiButton setEnabled(boolean b)
	{
		this.enabled = b;
		return this;
	}

	@Override
	public void init(){}

	@Override
	public void update()
	{
		timer++;
		if(timer < 10)return;
		if(parent instanceof IGuiButtonListener)
		{
			if(isMouseOver())
			{
				if(Mouse.isButtonDown(0) && !mouseClicked)
				{
					mouseClicked = true;
					((IGuiButtonListener) parent).buttonClicked(this);
				}
				
				if(!Mouse.isButtonDown(0) && mouseClicked)
				{
					mouseClicked = false;
				}
			}
			else
			{
				mouseClicked = false;
			}
		}
	}
	
	public boolean isMouseOver()
	{
		int mx = Mouse.getX();
		int my = Main.displayHeight-Mouse.getY();
		return (mx > posX && my > posY && mx < posX+width && my < posY+height);
	}

	@Override
	public void render(Graphics g)
	{
		Color color2 = (isMouseOver())? Color.orange : color;

		if(!this.enabled)
		{
			color2 = Color.darkGray;
		}

		g.setColor(color2);
		g.drawRect(posX,posY,width,height);
		g.setColor(Color.white);
		
		GuiUtils.drawString(g, RegisterTextures.font_0, text, posX+(width/2), posY, color2, align);
	}

	@Override
	public boolean pausesGame()
	{
		return false;
	}
}
