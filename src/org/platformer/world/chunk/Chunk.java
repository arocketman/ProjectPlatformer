package org.platformer.world.chunk;

import org.platformer.block.Block;
import org.platformer.entity.Entity;
import org.platformer.register.RegisterBlocks;
import org.platformer.utils.AABB;
import org.platformer.world.World;

import java.util.LinkedList;
import java.util.List;

public class Chunk
{
	public int chunkX = 0;
	public int chunkY = 0;
	public AABB[] aabbPool = new AABB[1024];
	public int[] blocks = new int[1024];
	public boolean needsUpdate = true;


	private List<Entity> entities;		// used to keep track of each entities in the chunk

	public Chunk(int chunkX, int chunkY)
	{
		this.chunkX = chunkX;
		this.chunkY = chunkY;

		for(int i=0;i<blocks.length;i++)
		{
			blocks[i] = -1;
		}

		entities = new LinkedList<>();

	}

	/**
	 * Places an entity to the chunk
	 * @param ent entity to be placed
	 */
	public void placeEntity(Entity ent) {

		entities.add(ent);

	}

	/**
	 * Removes an entity from the chunk
	 * @param ent entity to be removed
	 */
	public void removeEntity(Entity ent) {

		entities.remove(ent);

	}

	/**
	 * Gets a list of entities that are currently within the chunk.
	 *
	 * @return a list of entities that are currently within the chunk
	 */
	public List<Entity> getEntities() {

		return entities;

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
		//Make sure we're not placing the block on an existing one.
		if(blocks[i] != -1)
			return;
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
		if(i >= blocks.length)return -1;
		if(i < 0)return -1;
		return blocks[i];
	}

	public void onUpdate(World world)
	{
		needsUpdate = false;
		updateAABB(world);
	}

	/**
	 * Updates the collision box and adds it to the aabbPool array
	 * @param world
	 */
	public void updateAABB(World world)
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
				boolean[] blocksAt = new boolean[4]; //up, down, left, right
				blocksAt[0] = (world.getBlock((int)blockX, (int)blockY, bx,by-1) != -1);
				blocksAt[1] = (world.getBlock((int)blockX, (int)blockY, bx,by+1) != -1);
				blocksAt[2] = (world.getBlock((int)blockX, (int)blockY, bx-1,by) != -1);
				blocksAt[3] = (world.getBlock((int)blockX, (int)blockY, bx+1,by) != -1);
				boolean flag = false;

				if(!flag && (blocksAt[0] && blocksAt[1] && blocksAt[2] && blocksAt[3]))flag = true;
				if(!flag && (blocksAt[0] && blocksAt[1] && blocksAt[2] && !blocksAt[3]))flag = true;
				if(!flag && (blocksAt[0] && blocksAt[1] && !blocksAt[2] && blocksAt[3]))flag = true;
				if(!flag && (blocksAt[0] && !blocksAt[1] && blocksAt[2] && blocksAt[3]))flag = true;
				if(!flag && (!blocksAt[0] && blocksAt[1] && blocksAt[2] && blocksAt[3]))flag = true;
				if(!flag && (blocksAt[0] && blocksAt[1] && !blocksAt[2] && !blocksAt[3]))flag = true;
				if(!flag && (!blocksAt[0] && !blocksAt[1] && blocksAt[2] && blocksAt[3]))flag = true;
				if(!flag && (!blocksAt[0] && !blocksAt[1] && !blocksAt[2] && blocksAt[3]))flag = true;
				if(!flag && (!blocksAt[0] && !blocksAt[1] && blocksAt[2] && !blocksAt[3]))flag = true;
				if(!flag && (!blocksAt[0] && blocksAt[1] && !blocksAt[2] && !blocksAt[3]))flag = true;
				if(!flag && (blocksAt[0] && !blocksAt[1] && !blocksAt[2] && !blocksAt[3]))flag = true;
				if(!flag && (!blocksAt[0] && !blocksAt[1] && !blocksAt[2] && !blocksAt[3]))flag = true;

				Block block = RegisterBlocks.get(blocks[i]);
				AABB colBox = block.getCollisionBox().duplicate();
				aabbPool[i] = colBox;
				if(aabbPool[i] != null)
				{
					aabbPool[i].posX = blockX+8f;
					aabbPool[i].posY = blockY+8f;
				}
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
