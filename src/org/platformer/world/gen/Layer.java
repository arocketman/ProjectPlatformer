package org.platformer.world.gen;

import org.platformer.block.Block;

public class Layer
{
	public final int range;
	public final Block block;
	
	public Layer(int range, Block block)
	{
		this.range = range;
		this.block = block;
	}
}
