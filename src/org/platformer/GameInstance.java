package org.platformer;

import org.newdawn.slick.Graphics;
import org.platformer.utils.IDefaultGame;
import org.platformer.world.World;
import org.platformer.world.WorldClient;
import org.platformer.world.WorldServer;

public class GameInstance implements IDefaultGame
{
	private World world;
	
	@Override
	public void init()
	{
		long seed = 1234567891L;
		world = Main.isServer? new WorldServer(seed) : new WorldClient(seed);
		
		world.init();
	}

	@Override
	public void update()
	{
		if(world == null)return;
		world.update();
	}

	@Override
	public void render(Graphics g)
	{
		if(world == null)return;
		world.render(g);
	}
	
	public World getWorld() {
		return world;
	}
}