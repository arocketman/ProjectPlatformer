package org.platformer.register;

import java.util.HashMap;

import org.platformer.block.Block;
import org.platformer.utils.AABB;

public class RegisterBlocks
{
	private static final AABB DEFAULT_AABB = new AABB(16f,16f);
	public static HashMap<Integer,Block> blocks = new HashMap<Integer,Block>();
	
	/** Block Parameters: BlockID, textureName*/
	
	public static Block dirt = new Block(0,"dirt").setCollision(DEFAULT_AABB);
	public static Block stone = new Block(1,"stone").setCollision(DEFAULT_AABB);

	public static void init()
	{
		register(dirt);
		register(stone);
	}

	/**
	 * Add new block to registry
	 * @param block
	 */
	private static void register(Block block)
	{
		blocks.put(block.getID(), block);
	}
	
	/**
	 * Get block from registry using its block ID
	 * @param id - Block ID
	 * @return Block
	 */
	public static Block get(int id)
	{
		return blocks.get(id);
	}
}
