package org.platformer.entity;

import org.platformer.utils.AABB;
import org.platformer.utils.ITrackable;
import org.platformer.world.World;

public class Entity implements ITrackable
{
	public final String hash;
	public float posX, posY, motionX, motionY;
	public AABB colBox;
	public World world;
	public boolean onGround;
	
	public Entity(World world, String hash)
	{
		this.world = world;
		this.hash = hash;
		if(this instanceof EntityPlayer)
		{
			EntityTracker.addPlayer(this);
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
		colBox.applyMotion(this,motionX,motionY);
		
		if(posY == colBox.posY || onGround){motionY = 0f;}
		posX = colBox.posX;
		posY = colBox.posY;
		
		motionY+=0.3f;
		motionY*=1.005f;
		
		motionY=Math.min(motionY, 15f);
	}

	@Override
	public String getHash()
	{
		return this.hash;
	}

}