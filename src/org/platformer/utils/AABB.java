package org.platformer.utils;

import org.platformer.entity.Entity;
import org.platformer.world.chunk.Chunk;

public class AABB
{
	public float minX, minY, maxX, maxY, posX, posY;
	
	public AABB(float minX, float minY, float maxX, float maxY)
	{
		this.minX = minX;
		this.minY = minY;
		this.maxX = maxX;
		this.maxY = maxY;
	}
	
	public AABB(float width, float height)
	{
		this.minX = -(width/2f);
		this.minY = -(height/2f);
		this.maxX = (width/2f);
		this.maxY = (height/2f);
	}
	
	public boolean intersects(AABB other)
	{
		float sizeX = getWidth();
		float sizeY = getHeight();
		float sizeXother = other.getWidth();
		float sizeYother = other.getHeight();
		if(Math.abs(posX - other.posX) < ((sizeX/2f) + (sizeXother/2f)))
		{
			if(Math.abs(posY - other.posY) < ((sizeY/2f) + (sizeYother/2f)))
			{
				return true;
			}
		}
		return false;
	}
	
	public boolean intersectsMotion(AABB other, float motionX, float motionY)
	{
		float sizeX = getWidth();
		float sizeY = getHeight();
		float sizeXother = other.getWidth();
		float sizeYother = other.getHeight();
		if(Math.abs((posX+motionX) - other.posX) < ((sizeX/2f) + (sizeXother/2f)))
		{
			if(Math.abs((posY+motionY) - other.posY) < ((sizeY/2f) + (sizeYother/2f)))
			{
				return true;
			}
		}
		return false;
	}
	
	public float getWidth()
	{
		return Math.abs(minX)+maxX;
	}
	
	public float getHeight()
	{
		return Math.abs(minY)+maxY;
	}
	
	public float getMinXWP()
	{
		return posX+minX;
	}
	
	public float getMinYWP()
	{
		return posY+minY;
	}
	
	public float getMaxXWP()
	{
		return posX+maxX;
	}
	
	public float getMaxYWP()
	{
		return posY+maxY;
	}

	public void applyMotion(Entity entity, float motionX, float motionY)
	{
		entity.onGround = false;
		boolean intersects = false;
		boolean intersectsX = false;
		boolean intersectsY = false;
		
		Chunk[] chunkArray = new Chunk[5];
		chunkArray[0] = entity.world.getChunkWorldPos((int)(entity.posX), (int)(entity.posY));
		chunkArray[1] = entity.world.getChunkWorldPos((int)(entity.posX), (int)(entity.posY+getHeight()));
		chunkArray[2] = entity.world.getChunkWorldPos((int)(entity.posX-getWidth()), (int)(entity.posY));
		chunkArray[3] = entity.world.getChunkWorldPos((int)(entity.posX+getWidth()), (int)(entity.posY));
		chunkArray[4] = entity.world.getChunkWorldPos((int)(entity.posX), (int)(entity.posY-getHeight()));
		
		AABB otherCol = null;
		both:
		for(Chunk chunk : chunkArray)
		{
			if(chunk != null)
			{
				for(AABB other : chunk.aabbPool)
				{
					if(other != null)
					{
						if(intersects(other))
						{
							intersects = true;
							otherCol = other;
							break both;
						}
						else
						{
							if(intersectsMotion(other, 0f, motionY))
							{
								intersectsY = true;
							}
							
							if(intersectsMotion(other, motionX, 0f))
							{
								intersectsX = true;
							}
							
							while(intersectsY)
							{
								motionY/=2f;
								if(intersectsMotion(other, 0f, motionY))
								{
									intersectsY = true;
									if(motionY >= 0)
									{
										entity.onGround = true;
									}
								}
								else
								{
									intersectsY = false;
								}
							}
							
							while(intersectsX)
							{
								motionX/=2f;
								if(intersectsMotion(other, motionX, 0f))
								{
									intersectsX = true;
								}
								else
								{
									intersectsX = false;
								}
							}
						}
					}
				}
			}
		}
		if(!intersects)
		{
			if(!intersectsX)
			{
				posX+=motionX;
			}
			
			if(!intersectsY)
			{
				posY+=motionY;
			}

		}
		else
		{
			if(otherCol != null)
			{
				posX+=((posX-otherCol.posX)/32f);
				posY+=((posY-otherCol.posY)/32f)-0.25f;
			}
		}
	}
}