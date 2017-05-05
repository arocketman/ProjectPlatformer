package org.platformer.world.chunk;

import org.platformer.block.Block;
import org.platformer.utils.AABB;

public class Chunk
{
	public int chunkX = 0;
	public int chunkY = 0;
	public AABB[] aabbPool = new AABB[1024];
	public int[] blocks = new int[1024];
	public boolean needsUpdate = true;
	
	public Chunk(int x, int y)
	{
		chunkX = x;
		chunkY = y;
		for(int i=0;i<blocks.length;i++)
		{
			blocks[i] = -1;
		}
	}
	
	public void placeBlock(int x, int y, Block block)
	{
		int i = (y * 32) + x;
		blocks[i] = block.getID();
		needsUpdate = true;
	}

	public void removeBlock(int x, int y)
	{
		int i = (y * 32) + x;
		blocks[i] = -1;
		needsUpdate = true;
	}
	
	public void onUpdate()
	{
		needsUpdate = false;
		updateAABB();
	}
	
	public void updateAABB()
	{
		int cX = ((chunkX*32)*16);
		int cY = ((chunkY*32)*16);
		
		for(int i=0;i<blocks.length;i++)
		{
			int by = i/32;
			int bx = (i)-(by*32);
			float blockX = cX+(bx*16);
			float blockY = cY+(by*16);
			if(blocks[i] != -1)
			{
				aabbPool[i] = new AABB(16f,16f);
				aabbPool[i].posX = blockX+8f;
				aabbPool[i].posY = blockY+8f;
			}
			else
			{
				aabbPool[i] = null;
			}
		}
	}
}
