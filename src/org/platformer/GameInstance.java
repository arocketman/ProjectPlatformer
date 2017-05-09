package org.platformer;

import org.newdawn.slick.Graphics;
import org.platformer.gui.GuiMainMenu;
import org.platformer.gui.GuiOverlay;
import org.platformer.gui.IGui;
import org.platformer.utils.IDefaultGame;
import org.platformer.world.World;
import org.platformer.world.WorldClient;
import org.platformer.world.WorldServer;

public class GameInstance implements IDefaultGame
{
	public World world;
	public IGui currentScreen;

	@Override
	public void init()
	{
<<<<<<< HEAD
		long seed = 1234567891L;
		world = Main.isServer? new WorldServer(seed) : new WorldClient(seed);
		
		world.init();
		
=======
>>>>>>> refs/remotes/origin/master
		GameInstanceHandler.setGameInstance(this);
		displayGui(new GuiMainMenu());
	}

	@Override
	public void update()
	{
		if(world != null)
		{
			world.update();
		}
		
		if(currentScreen != null)
		{
			currentScreen.update();
		}
	}

	@Override
	public void render(Graphics g)
	{
		if(world != null)
		{
			world.render(g);
		}
		
		if(currentScreen != null)
		{
			currentScreen.render(g);
		}
	}
	
	public void startWorld()
	{
		long seed = 1234567891L;
		displayGui(new GuiOverlay());
		world = Main.isServer? new WorldServer(seed) : new WorldClient(seed);
		world.init();
	}

	public void displayGui(IGui gui)
	{
		currentScreen = gui;
		
		if(gui != null)
		{
			gui.init();
		}
	}

	public static GameInstance get()
	{
		return GameInstanceHandler.getGameInstance();
	}
}

class GameInstanceHandler {
	private static GameInstance gameInstance;
	
	public static void setGameInstance(GameInstance p_GameInstance) {
		gameInstance = p_GameInstance;
	}
	
	public static GameInstance getGameInstance() {
		return gameInstance;
	}
}