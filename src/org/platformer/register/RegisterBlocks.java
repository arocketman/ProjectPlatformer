package org.platformer.register;

import java.util.HashMap;

import org.platformer.block.Block;
import org.platformer.utils.AABB;

import static org.platformer.utils.Constants.*;

public class RegisterBlocks
{
	public static final AABB DEFAULT_AABB = new AABB(16f,16f);
	public static final AABB SLOPE_AABB = new AABB(-8f,0f,8f,8f);
	public static HashMap<Integer,Block> blocks = new HashMap<Integer,Block>();
	
	/** Block Parameters: BlockID, textureName*/
	
	public static Block air = new Block(-1,"missingtexture", 0);
	public static Block dirt = new Block(0,"dirt", BLOCK_HEALTH_DIRT).setCollision(DEFAULT_AABB);
	public static Block stone = new Block(1,"stone", BLOCK_HEALTH_STONE).setCollision(DEFAULT_AABB);
	public static Block grass = new Block(2,"grass", BLOCK_HEALTH_GRASS).setCollision(DEFAULT_AABB);
	public static Block granite = new Block(3,"granite", BLOCK_HEALTH_GRANITE).setCollision(DEFAULT_AABB);
	
	public static void init()
	{
		register(air);
		register(dirt);
		register(stone);
		register(grass);
		register(granite);
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
