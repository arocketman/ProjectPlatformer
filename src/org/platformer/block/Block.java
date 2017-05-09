package org.platformer.block;

import org.platformer.utils.AABB;

public class Block
{
	private String texture = "missingtexture";
	
	private int blockID;
	private int health;
	private AABB aabb;
	private int id;

	public Block(int i, String texture, int health)
	{
		this.blockID = i;
		this.health = health;
	}

	public Block(Block b){
		this.blockID = b.blockID;
		this.health = b.health;
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

	public void setBlockID(int blockID) {
		this.blockID = blockID;
	}

	/**
	 * Removes health from the block by a quantity specified as the parameter
	 * @param i
     */
	public void removeHealth(int i) {
		this.health -= i;
	}

	public int getHealth() {
		return health;
	}

}
