package org.platformer.gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.platformer.GameInstance;
import org.platformer.Main;
import org.platformer.entity.EntityPlayer;
import org.platformer.entity.EntityPlayerLocal;
import org.platformer.entity.EntityTracker;
import org.platformer.entity.EntityTracker.Map;
import org.platformer.register.RegisterTextures;
import org.platformer.utils.GuiUtils;
import org.platformer.utils.GuiUtils.ALIGN;
import org.platformer.world.WorldClient;

public class GuiOverlay extends GuiScreen implements IGuiButtonListener
{
	public GuiOverlay()
	{
	}

	@Override
	public void init()
	{	
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
		if(Main.DEBUG)
		{
			GameInstance gi = GameInstance.get();
			GuiUtils.drawString(g, RegisterTextures.font_0, "FPS: "+Main.getFPS(), 4, 2, Color.red, ALIGN.LEFT);

			if(gi.world != null)
			{
				GuiUtils.drawString(g, RegisterTextures.font_0, "Entities: "+EntityTracker.getEntityMap(Map.ENTITIES).size(), 4, 2+(14), Color.red, ALIGN.LEFT);
				int lc = gi.world.chunks.length;
				for(int i=0;i<gi.world.chunks.length;i++)
				{
					if(gi.world.chunks[i] != null && gi.world.chunks[i].needsUpdate)lc--;
				}
				GuiUtils.drawString(g, RegisterTextures.font_0, "Loaded Chunks: "+lc, 4, 2+(14*2), Color.red, ALIGN.LEFT);
				EntityPlayer player = ((WorldClient)gi.world).localPlayer;
				if(player != null)
				{
					GuiUtils.drawString(g, RegisterTextures.font_0, "Player X: "+(int)player.posX, 4, 2+(14*3), Color.red, ALIGN.LEFT);
					GuiUtils.drawString(g, RegisterTextures.font_0, "Player Y: "+(int)player.posY, 4, 2+(14*4), Color.red, ALIGN.LEFT);
				}
			}
		}
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
		return false;
	}
}
