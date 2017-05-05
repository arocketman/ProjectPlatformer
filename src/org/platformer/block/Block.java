package org.platformer.block;

import org.platformer.utils.AABB;

public class Block
{
	private String texture = "missingtexture";
	private final int blockID;
	private AABB aabb;
	
	public Block(int i, String texture)
	{
		this.blockID = i;
	}
	
	public Block setCollision(AABB aabb)
	{
		this.aabb = aabb;
		return this;
	}
	
	public String getTexture()
	{
		return texture;
	}

	public int getID()
	{
		return blockID;
	}

	public AABB getCollisionBox()
	{
		return aabb;
	}
	
	public boolean hasCollision()
	{
		return (getCollisionBox() != null);
	}
}
