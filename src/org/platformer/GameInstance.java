package org.platformer;

import org.newdawn.slick.Graphics;
import org.platformer.utils.IDefaultGame;
import org.platformer.world.World;

public class GameInstance implements IDefaultGame
{
	public World world;
	
	@Override
	public void init()
	{
		world = new World(Main.isServer);
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
}