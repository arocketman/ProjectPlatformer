package org.platformer.utils;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;

public class GuiUtils
{
	public enum ALIGN{LEFT,RIGHT,CENTER}
	
	public static void drawString(Graphics g, Font font, String string, float x, float y, Color color, ALIGN align)
	{
		if(font == null)return;
		
		g.setFont(font);
		g.setColor(color);
		float posx2 = x + 2;
		float posy2 = y + 2;
		if(align == ALIGN.CENTER)
		{
			posx2 = (x)-(GuiUtils.stringWidth(g,string)/2);
		}

		if(align == ALIGN.RIGHT)
		{
			posx2 = (x)-(GuiUtils.stringWidth(g,string));
			posx2 -= 2;
		}
		g.drawString(string, posx2, posy2);
		g.setColor(Color.white);
	}

	public static int stringWidth(Graphics g, String text)
	{
		return g.getFont().getWidth(text);
	}
}
