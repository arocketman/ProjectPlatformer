package org.platformer.entity;

import org.platformer.utils.AABB;
import org.platformer.world.World;

public class EntityPlayer extends Entity
{
	public EntityPlayer(World world, String hash)
	{
		super(world, hash);
		colBox = new AABB(15.925f,31.925f);
		findWorldSpawn();
		teleportToSpawn();
	}
}