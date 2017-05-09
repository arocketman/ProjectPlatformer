package org.platformer.gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.platformer.GameInstance;
import org.platformer.Main;
import org.platformer.lang.Localization;
import org.platformer.utils.GuiUtils.ALIGN;

public class GuiMainMenu extends GuiScreen implements IGuiButtonListener
{

	@Override
	public void init()
	{	
		int w = Main.displayWidth/2;
		int h = Main.displayHeight/2;
		
		guiPart.add(new GuiButton(this, 0, w-50, h-10, 100, 20, Localization.get("gui.mainmenu.play"), Color.red, ALIGN.CENTER));
		guiPart.add(new GuiButton(this, 1, w-50, (h-10)+25, 100, 20, Localization.get("gui.mainmenu.languages"), Color.red, ALIGN.CENTER));
		
		GameInstance.get().world = null;
		
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
		super.render(g);
	}

	@Override
	public void buttonClicked(GuiButton button)
	{
		if(button.id == 0)
		{
			GameInstance.get().displayGui(null);
			GameInstance.get().startWorld();
		}
		
		if(button.id == 1)
		{
			GameInstance.get().displayGui(new GuiLanguage());
		}
	}

	@Override
	public boolean pausesGame()
	{
		return true;
	}
}
