package org.platformer.world.chunk;

import org.platformer.block.Block;
import org.platformer.register.RegisterBlocks;
import org.platformer.utils.AABB;

public class Chunk
{
	public int chunkX = 0;
	public int chunkY = 0;
	public AABB[] aabbPool = new AABB[1024];
	public int[] blocks = new int[1024];
	public boolean needsUpdate = true;
	
	public Chunk(int chunkX, int chunkY)
	{
		this.chunkX = chunkX;
		this.chunkY = chunkY;
		for(int i=0;i<blocks.length;i++)
		{
			blocks[i] = -1;
		}
	}
	
	/**
	 * Place a block within the chunk using relative position
	 * @param x - X pos range: 0-31
	 * @param y - Y pos range: 0-31
	 * @param block - Block that we are placing
	 */
	public void placeBlock(int x, int y, Block block)
	{
		int i = (y * 32) + x;
		blocks[i] = block.getID();
		needsUpdate = true;
	}
	
	/**
	 * Remove a block within the chunk using relative position
	 * @param x - X pos range: 0-31
	 * @param y - Y pos range: 0-31
	 */
	public void removeBlock(int x, int y)
	{
		int i = (y * 32) + x;
		blocks[i] = -1;
		needsUpdate = true;
	}
	
	public int getBlock(int x, int y)
	{
		int i = (y * 32) + x;
		return blocks[i];
	}
	
	public void onUpdate()
	{
		needsUpdate = false;
		updateAABB();
	}
	
	/**
	 * Updates the collision box and adds it to the aabbPool array
	 */
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
				Block block = RegisterBlocks.get(blocks[i]);
				aabbPool[i] = block.getCollisionBox().duplicate();
				aabbPool[i].posX = blockX+8f;
				aabbPool[i].posY = blockY+8f;
			}
			else
			{
				aabbPool[i] = null;
			}
		}
	}

	public int[] getWorldPosition()
	{
		return new int[]{chunkX*(32*16),chunkY*(32*16)};
	}
}
