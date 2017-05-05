package org.platformer.world;

import org.newdawn.slick.Graphics;
import org.platformer.world.chunk.Chunk;

public class WorldServer extends World
{
	public WorldServer(Long seed)
	{
		super(seed);
	}
	
	@Override
	public void init()
	{
		initChunks();
	}
	
	/**
	 * Initialize the chunks, this will later get moved to a WorldGenerator class.
	 */
	private void initChunks()
	{
		for(int i=0;i<chunks.length;i++)
		{
			int cy = i/32;
			int cx = (i)-(cy*32);
			chunks[i] = new Chunk(cx,cy);
			for(int a=0;a<chunks[i].blocks.length;a++)
			{
				chunks[i].blocks[a] = random.nextInt(2);
			}
		}
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