package org.platformer.gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.platformer.GameInstance;
import org.platformer.Main;
import org.platformer.lang.Localization;
import org.platformer.register.RegisterTextures;
import org.platformer.utils.GuiUtils;
import org.platformer.utils.GuiUtils.ALIGN;

public class GuiMessage extends GuiScreen implements IGuiButtonListener
{
	private String message = "";
	
	public GuiMessage(String msg)
	{
		message = msg;
	}

	@Override
	public void init()
	{	
		int w = Main.displayWidth/2;
		int h = Main.displayHeight/2;
		
		guiPart.add(new GuiButton(this, 0, w-50, h+32, 100, 20, Localization.get("gui.back"), Color.red, ALIGN.CENTER));

		super.init();
	}

	@Override
	public void update()
	{
		super.update();
	}

	@Override
	public void render(Graphics g)
	{
		int w = Main.displayWidth;
		int h = Main.displayHeight;
		GuiUtils.drawString(g, RegisterTextures.font_0, message, w/2, h/2, Color.red, ALIGN.CENTER);
		super.render(g);
	}

	@Override
	public void buttonClicked(GuiButton button)
	{
		if(button.id == 0)
		{
			GameInstance.get().displayGui(new GuiMainMenu());
		}
	}

	@Override
	public boolean pausesGame()
	{
		return true;
	}
}
