package org.platformer.gui;

import java.util.Locale;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.platformer.GameInstance;
import org.platformer.Main;
import org.platformer.lang.Localization;
import org.platformer.utils.GuiUtils.ALIGN;

public class GuiLanguage extends GuiScreen implements IGuiButtonListener
{

	@Override
	public void init()
	{	
		int w = Main.displayWidth/2;
		
		int i=0;
		for(Locale locale : Localization.localizationTypes)
		{
			int width = 170;
			int x = i % 4;
			int y = (i/4);
			guiPart.add(new GuiButton(this, i, ((w-50)-(2*(width+1)))+(x*(width+1)), (12)+(y*22), width, 20, locale.getDisplayName(), Color.red, ALIGN.CENTER));
			i++;
		}
		
		guiPart.add(new GuiButton(this, 999, w-50, (Main.displayHeight-30), 100, 20, Localization.get("gui.back"), Color.red, ALIGN.CENTER));
		
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
		if(button.id == 999)
		{
			GameInstance.get().displayGui(new GuiMainMenu());
		}
		else
		{
			Localization.loadLanguage(Localization.localizationTypes.get(button.id).toString());
			GameInstance.get().displayGui(new GuiLanguage());
		}
	}

	@Override
	public boolean pausesGame()
	{
		return true;
	}
}
