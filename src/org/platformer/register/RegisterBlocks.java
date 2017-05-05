package org.platformer.register;

import java.util.HashMap;

import org.platformer.block.Block;

public class RegisterBlocks
{
	public static HashMap<Integer,Block> blocks = new HashMap<Integer,Block>();
	public static Block dirt = new Block(0,"dirt");
	public static Block stone = new Block(1,"stone");

	public static void init()
	{
		register(dirt);
		register(stone);
	}

	private static void register(Block block)
	{
		blocks.put(block.getID(), block);
	}
	
	public static Block get(int i)
	{
		return blocks.get(i);
	}
}
