package org.platformer;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.platformer.register.RegisterBlocks;
import org.platformer.register.RegisterKeybinds;
import org.platformer.register.RegisterRenders;
import org.platformer.register.RegisterTextures;

public class Main extends BasicGame
{
	public static String TITLE = "Platformer";
	public static String VERSION = "0.1";
	public static int displayWidth = 800;
	public static int displayHeight = 600;
	public static boolean isServer = false;
	public static Main mainInstance;
	
	private GameInstance gameInstance = new GameInstance();
	
    private long lastEvent;
	private int currentTick, lastTick;
	
    public Main()
    {
        super(TITLE+" "+VERSION);
        mainInstance = this;
    }
	
	public static Main getMain() {
		return mainInstance;
	}
	
	public GameInstance getGameInstance() {
		return gameInstance;
	}

    @Override
    public void init(GameContainer container) throws SlickException
    {
    	RegisterTextures.init();
    	RegisterKeybinds.init();
    	RegisterRenders.init();
    	RegisterBlocks.init();
    	gameInstance.init();
    }
 
    @Override
    public void update(GameContainer container, int delta) throws SlickException
    {
		if(System.currentTimeMillis() > lastEvent + 16)
		{
			lastEvent = System.currentTimeMillis();
			currentTick++;
		}

		if(lastTick != currentTick)
		{
			lastTick = currentTick;
			tick();
		}
    }
 
    private void tick()
    {
    	gameInstance.update();
	}

	@Override
    public void render(GameContainer container, Graphics g) throws SlickException
    {
    	gameInstance.render(g);
    }
    
    public static void main(String[] arguments)
    {
        try
        {
            AppGameContainer app = new AppGameContainer(new Main());
            app.setDisplayMode(displayWidth, displayHeight, false);
            app.setAlwaysRender(true);
            app.setTargetFrameRate(60);
            app.start();
        }
        catch (SlickException e)
        {
            e.printStackTrace();
        }
    }
}