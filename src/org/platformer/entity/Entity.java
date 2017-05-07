package org.platformer.entity;

import java.util.Random;

import org.platformer.utils.AABB;
import org.platformer.utils.ITrackable;
import org.platformer.world.World;
import org.platformer.world.chunk.Chunk;
import org.platformer.utils.Handler;

public class Entity implements ITrackable
{
	public final String hash;
	public float posX, posY, motionX, motionY, spawnX, spawnY;
	public AABB colBox;
	public World world;
	public boolean onGround;
	public boolean isFalling;
	public int currentAnimation;
	public int moveSpeed;
	public boolean walkingLeft;
	
	public Entity(World world, String hash)
	{
		this.world = world;
		this.hash = hash;
		if(this instanceof EntityPlayer)
		{
			EntityTracker.addPlayer(this);
			Handler.setPlayer((EntityPlayer) this);
		}
		else
		{
			EntityTracker.addEntityTracker(this);
		}
	}

	public void update()
	{
		doCollision();
	}

	private void doCollision()
	{
		if(colBox == null)return;
		motionX*=0.5f;
		if(Math.abs(motionX) < 0.001f)motionX = 0f;
		moveSpeed = (int)(motionX*2);
		if(motionX != 0f)walkingLeft = (motionX < 0f);
		
		colBox.applyMotion(this,motionX,motionY);
		
		if(posY == colBox.posY || onGround){motionY = 0f;}
		posX = colBox.posX;
		posY = colBox.posY;
		
		isFalling = (motionY > 0);
		
		motionY+=0.3f;
		motionY*=1.005f;
		
		motionY=Math.min(motionY, 15f);
	}
	

	protected void findWorldSpawn()
	{
		Chunk chunk = world.getSpawnChunk();
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
		spawnX = worldPos[0]+(x*32);
		spawnY = worldPos[1]+(y*32)-colBox.getHeight()/2f;
	}

	protected void teleportToSpawn()
	{
		if(colBox != null)
		{
			colBox.posX = spawnX;
			colBox.posY = spawnY;
		}
		else
		{
			posX = spawnX;
			posY = spawnY;
		}
	}

	@Override
	public String getHash()
	{
		return this.hash;
	}

}