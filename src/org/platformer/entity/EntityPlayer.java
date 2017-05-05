package org.platformer.entity;

import org.platformer.utils.AABB;
import org.platformer.world.World;

public class EntityPlayer extends Entity
{

	public EntityPlayer(World world, String hash)
	{
		super(world, hash);
		colBox = new AABB(15.5f,31.5f);
		colBox.posX = 100;
		colBox.posY = -50;
	}
}