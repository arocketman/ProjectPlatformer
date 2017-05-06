package org.platformer.world;

import org.newdawn.slick.Graphics;
import org.platformer.register.RegisterWorldGenerators.WorldType;
import org.platformer.threads.ThreadWorldGenerator;

public class WorldServer extends World
{
	public WorldServer(Long seed)
	{
		super(seed);
	}
	
	@Override
	public void init()
	{
		ThreadWorldGenerator threadWorldGen = new ThreadWorldGenerator(this,WorldType.PLANET0);
		threadWorldGen.setName("WorldGenerator");
		threadWorldGen.start();
	}

	@Override
	public void update()
	{
		updateEntities();
	}

	/**
	 * Unused.
	 */
	@Override
	public void render(Graphics g){}
}