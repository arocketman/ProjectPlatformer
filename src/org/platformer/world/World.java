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
	
	public World(Long seed)
	{
		random = new Random(seed);
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
}
