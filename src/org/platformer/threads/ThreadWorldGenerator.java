package org.platformer.threads;

import org.platformer.register.RegisterWorldGenerators;
import org.platformer.register.RegisterWorldGenerators.WorldType;
import org.platformer.world.World;
import org.platformer.world.gen.IWorldGen;

public class ThreadWorldGenerator extends Thread
{
	private IWorldGen worldGen;
	private World world;
	
	public ThreadWorldGenerator(World world, WorldType worldType)
	{
		this.world = world;
		this.worldGen = RegisterWorldGenerators.get(worldType);
	}

    public void run()
    {
    	worldGen.generate(world);
    	world.postWorldGenerator();
    }
}