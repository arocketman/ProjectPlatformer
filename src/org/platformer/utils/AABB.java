package org.platformer.utils;

import org.platformer.entity.Entity;
import org.platformer.world.chunk.Chunk;

public class AABB
{

	private static final float NEARBY_DISTANCE = 64f;
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
	
	/**
	 * Check if this AABB is intersecting the other AABB
	 * @param other - Other AABB
	 * @return true if intersects
	 */
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
	
	/**
	 * Check if this AABB plus its motion is intersecting the other AABB
	 * @param other - Other AABB
	 * @return true if intersects if it had motion
	 */
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
	
	/**
	 * Get the full width of this AABB
	 * @return Full Width
	 */
	public float getWidth()
	{
		return Math.abs(minX)+maxX;
	}
	
	/**
	 * Get the full height of this AABB
	 * @return Full Height
	 */
	public float getHeight()
	{
		return Math.abs(minY)+maxY;
	}
	
	/**
	 * Get the minX with the aabb's position added to it
	 * @return
	 */
	public float getMinXWP()
	{
		return posX+minX;
	}
	
	/**
	 * Get the minY with the aabb's position added to it
	 * @return
	 */
	public float getMinYWP()
	{
		return posY+minY;
	}
	
	/**
	 * Get the maxX with the aabb's position added to it
	 * @return
	 */
	public float getMaxXWP()
	{
		return posX+maxX;
	}
	
	/**
	 * Get the maxY with the aabb's position added to it
	 * @return
	 */
	public float getMaxYWP()
	{
		return posY+maxY;
	}

	/**
	 * Detects Collision before applying the motion
	 * @param entity - Entity
	 * @param motionX - Motion X
	 * @param motionY - Motion Y
	 */
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
							float difY = Math.abs(other.getMinYWP()-getMaxYWP());
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
								
								if(difY <= 16f && !intersectsMotion(other, motionX, -difY))
								{
									motionY -= Math.abs(motionX)+0.5f;
									motionX*=1.25f;
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
				posX+=((posX-otherCol.posX)/64f);
				posY+=((posY-otherCol.posY)/64f)-0.275f;
			}
		}
	}

	/**
	 * Makes a new duplicate instance of this AABB
	 * @return Duplicate AABB
	 */
	public AABB duplicate()
	{
		AABB aabb = new AABB(minX,minY,maxX,maxY);
		aabb.posX = posX;
		aabb.posY = posY;
		return aabb;
	}

	/**
	 * Checks wheter or not this AABB is in range of the passed AABB.
	 * As of right now the threshhold distance is specified in a constant defined in this same class.
	 * @param blockAABB the AABB you want to check is nearby
	 * @return true if the two AABB are nearby, false otherwise.
     */
	public boolean isNearby(AABB blockAABB) {
		return Math.abs(blockAABB.posX - this.posX) <= NEARBY_DISTANCE
				&& Math.abs(blockAABB.posY - this.posY) <= NEARBY_DISTANCE;
	}

	/**
	 * Refer to {@link #isNearby(AABB)}.
	 * @param posX the X coordinate of the passed object to check against
	 * @param posY the Y coordinate of the passed object to check against
	 * @return true if the two objects are nearby, false otherwise.
     */
	public boolean isNearby(float posX , float posY) {
		return Math.abs(posX - this.posX) <= NEARBY_DISTANCE
				&& Math.abs(posY - this.posY) <= NEARBY_DISTANCE;
	}
}
