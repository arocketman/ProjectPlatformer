package org.platformer.world.chunk;

import java.util.LinkedList;
import java.util.List;

import org.platformer.block.Block;
import org.platformer.entity.Entity;
import org.platformer.register.RegisterBlocks;
import org.platformer.utils.AABB;
import org.platformer.utils.Log;
import org.platformer.world.World;

public class Chunk
{
	public int chunkX = 0;
	public int chunkY = 0;
	public AABB[] aabbPool = new AABB[1024];
	public Block[] blocks = new Block[1024];
	public int[] backgroundBlocks = new int[1024];
	public int[] light = new int[1024];
	public boolean needsUpdate = true;

	private List<Entity> entities;		// used to keep track of each entities in the chunk

	public Chunk(int chunkX, int chunkY)
	{
		this.chunkX = chunkX;
		this.chunkY = chunkY;

		for(int i=0;i<blocks.length;i++)
		{
			blocks[i] = new Block(RegisterBlocks.air);
			backgroundBlocks[i] = -1;
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
	public void placeBlock(int x, int y, Block block, boolean background)
	{
		int i = (y * 32) + x;
		//Make sure we're not placing the block on an existing one.
		if(!background && blocks[i].getID() != -1)
			return;
		if(background && backgroundBlocks[i] != -1)
			return;
		
		if(background)
			backgroundBlocks[i] = block.getID();
		else
			blocks[i] = new Block(RegisterBlocks.get(block.getID()));
		needsUpdate = true;
	}

	/**
	 * Remove a block within the chunk using relative position
	 * @param x - X pos range: 0-31
	 * @param y - Y pos range: 0-31
	 */
	public void removeBlock(int x, int y, boolean background)
	{
		int i = (y * 32) + x;
		if(background)
			backgroundBlocks[i] = -1;
		else
			blocks[i].setBlockID(-1);
		needsUpdate = true;
	}

	public int getBlock(int x, int y, boolean background)
	{
		int i = (y * 32) + x;
		if(background && i >= blocks.length)return -1;
		if(!background && i >= backgroundBlocks.length)return -1;
		if(i < 0)return -1;
		return (background)? backgroundBlocks[i] : blocks[i].getID();
	}

	public AABB getBlockAABB(int x, int y){
		int i = (y * 32) + x;
		if(i >= aabbPool.length)return null;
		if(i < 0)return null;
		return aabbPool[i];
	}

	public int getLight(int x, int y)
	{
		int i = (y * 32) + x;
		if(i >= light.length)return 0;
		if(i < 0)return 0;
		return light[i];
	}

	public void onUpdate(World world)
	{
		needsUpdate = false;
		updateAABB(world);
		updateLighting(world);
	}

	public boolean isEmpty()
	{
		for(int x=0;x<32;x++)
		{
			for(int y=0;y<32;y++)
			{
				int blockid = getBlock(x,y,false);
				int blockidbg = getBlock(x,y,true);

				if(blockid != -1 || blockidbg != -1)return false;
			}
		}

		return true;
	}

	private void updateLighting(World world)
	{
		for(int i=0;i<light.length;i++)
		{
			light[i] = 0;
		}

		Chunk chk = null; //first chunk on the y axis that contains blocks
		for(int y=0;y<32;y++)
		{
			chk = world.getChunk(chunkX, y);
			if(!chk.isEmpty())
			{
				break;
			}
		}

		if(!this.isEmpty() && chk == this)//if first chunk is ours, then populate all air with light until it hits a block
		{
			for(int x=0;x<32;x++)
			{
				boolean first = true;
				for(int y=0;y<32;y++)
				{
					int i = (y * 32) + x;

					int blockid = getBlock(x,y,false);

					if(blockid != -1)
					{
						if(first)
						{
							first = false;
						}
					}
					else
					{
						if(first)
						{
							light[i]=300;
						}
					}
				}
			}
		}

		if(!this.isEmpty())
		{
			for(int p=0;p<4;p++) //how many passes / iterations
			{
				for(int x=0;x<32;x++)
				{
					for(int y=0;y<32;y++)
					{
						int i = (y * 32) + x;
						int l_up = getLight(x,y-1);
						int l_down = getLight(x,y+1);
						int l_left = getLight(x-1,y);
						int l_right = getLight(x+1,y);
						int total = (int)((l_up + l_down + l_left + l_right)*1.3f);
						int remove = 0;
						if(l_up == 0)remove++;
						if(l_down == 0)remove++;
						if(l_left == 0)remove++;
						if(l_right == 0)remove++;
						light[i] = Math.min(total/(5-remove),255);
					}
				}
			}
		}
		else//if empty chunk, populate fully with light
		{
			for(int i=0;i<light.length;i++)
			{
				light[i]=300;
			}
		}
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
			if(blocks[i].getID() != -1)
			{
				boolean[] blocksAt = new boolean[4]; //up, down, left, right
				blocksAt[0] = (world.getBlock((int)blockX, (int)blockY, bx,by-1, false) != -1);
				blocksAt[1] = (world.getBlock((int)blockX, (int)blockY, bx,by+1, false) != -1);
				blocksAt[2] = (world.getBlock((int)blockX, (int)blockY, bx-1,by, false) != -1);
				blocksAt[3] = (world.getBlock((int)blockX, (int)blockY, bx+1,by, false) != -1);
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

				Block block = RegisterBlocks.get(blocks[i].getID());
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

	public void hitBlock(int x, int y) {
		int i = (y*32)+x;
		blocks[i].removeHealth(1);
		if(blocks[i].getHealth()<=0)
			removeBlock(x,y,false);

	}
}
