package org.platformer.world;

import java.util.ArrayList;
import java.util.Random;

import org.platformer.entity.Entity;
import org.platformer.entity.EntityTracker;
import org.platformer.entity.EntityTracker.Map;
import org.platformer.utils.AABB;
import org.platformer.utils.IDefaultGame;
import org.platformer.world.chunk.Chunk;

public abstract class World implements IDefaultGame
{
	public ArrayList<AABB> aabbPool = new ArrayList<AABB>();
	public Chunk[] chunks = new Chunk[1024];
	
	protected Random random;
	private Chunk spawnChunk;

	public World(Long seed)
	{
		random = new Random(seed);
		initChunks();
	}

	/**
	 * Create chunk instances, 32 x 32 = 1024 chunks (1048576 blocks)
	 */
	private void initChunks()
	{
		for(int i=0;i<32;i++)
		{
			for(int a=0;a<32;a++)
			{
				int i2 = (a * 32) + i;
				chunks[i2] = new Chunk(i,a);
			}
		}
	}
	
	/**
	 * Executes after the world is finished generating
	 */
	public void postWorldGenerator()
	{
	}

	/**
	 * Get a chunk with world positions
	 * @param x - World Position
	 * @param y - World Position
	 * @return Chunk
	 */
	public Chunk getChunkWorldPos(int x, int y)
	{
		return getChunk((int)Math.floor(x/(16*32)),(int)Math.floor(y/(16*32)));
	}
	
	/**
	 * Get a chunk with chunk positions
	 * @param chunkX - Chunk Position
	 * @param chunkY - Chunk Position
	 * @return Chunk
	 */
	public Chunk getChunk(int chunkX, int chunkY)
	{
		int i = (chunkY * 32) + chunkX;
		if(i < 0 || i >= chunks.length)
		{
			return null;
		}
		return chunks[i];
	}
	
	/**
	 * Updates / Ticks all entities
	 */
	public void updateEntities()
	{
		for(Entity ent : EntityTracker.getEntityMap(Map.ENTITIES).values())
		{
			ent.update();
		}
	}

	public Random getRandom() 
	{
		return random;
	}

	public void setSpawnChunk(Chunk chunk)
	{
		this.spawnChunk = chunk;
	}
	
	public Chunk getSpawnChunk()
	{
		return spawnChunk;
	}
}