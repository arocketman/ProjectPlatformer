package org.platformer.block;

public class Block
{
	private int blockID = 0;
	private String texture = "dirt";
	
	public Block(int i, String texture)
	{
		blockID = i;
	}
	
	public String getTexture()
	{
		return texture;
	}

	public int getID()
	{
		return blockID;
	}
}
