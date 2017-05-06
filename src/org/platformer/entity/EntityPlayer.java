package org.platformer.entity;

import java.util.Random;

import org.platformer.utils.AABB;
import org.platformer.utils.Log;
import org.platformer.world.World;
import org.platformer.world.chunk.Chunk;

public class EntityPlayer extends Entity
{
	public EntityPlayer(World world, String hash)
	{
		super(world, hash);
		Chunk chunk = world.getSpawnChunk();
		colBox = new AABB(15.925f,31.925f);
		teleportToSpawn(chunk);
	}

	private void teleportToSpawn(Chunk chunk)
	{
		if(chunk == null)return;
		int[] worldPos = chunk.getWorldPosition();
		Random random = new Random();
		int x = random.nextInt(32);
		int y = 0;
		int blockID = chunk.getBlock(x,31);
		if(blockID != -1)
		{
			for(int i=0;i<32;i++)
			{
				blockID = chunk.getBlock(x,31-i);
				if(blockID == -1)
				{
					y = 31-i;
					break;
				}
			}
		}
		colBox.posX = worldPos[0]+(x*32);
		colBox.posY = worldPos[1]+(y*32)-colBox.getHeight()/2f;
		Log.out(worldPos[1]);
	}
}