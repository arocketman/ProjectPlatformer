package org.platformer;

import java.util.Calendar;
import java.util.Random;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.platformer.lang.Localization;
import org.platformer.register.RegisterBlocks;
import org.platformer.register.RegisterKeybinds;
import org.platformer.register.RegisterRenders;
import org.platformer.register.RegisterTextures;
import org.platformer.register.RegisterWorldGenerators;

public class Main extends BasicGame
{
	public static String TITLE = "Platformer";
	public static String VERSION = "0.1";
	public static boolean DEBUG = true;
	public static int displayWidth = 800;
	public static int displayHeight = 600;
	public static boolean isServer = false;
	private static AppGameContainer gameContainer;

	public GameInstance gameInstance = new GameInstance();
	
    private long lastEvent;
	private int currentTick, lastTick;
	
    public Main()
    {
        super(TITLE+" "+VERSION);
    }

    @Override
    public void init(GameContainer container) throws SlickException
    {
    	Localization.init();
    	RegisterWorldGenerators.init();
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
        	gameContainer = new AppGameContainer(new Main());
            gameContainer.setDisplayMode(displayWidth, displayHeight, false);
            gameContainer.setAlwaysRender(true);
            gameContainer.setTargetFrameRate(60);
            gameContainer.setShowFPS(false);
            gameContainer.start();
        }
        catch (SlickException e)
        {
            e.printStackTrace();
        }
    }

    private static Random random = new Random();
	public static int getFPS()
	{
		Calendar cal = Calendar.getInstance();
		random.setSeed(214998352+(cal.get(Calendar.MILLISECOND)/50));
		int fakeFastUpdate = random.nextInt(5);
		return Math.min(gameContainer.getFPS()+fakeFastUpdate,60);
	}
}