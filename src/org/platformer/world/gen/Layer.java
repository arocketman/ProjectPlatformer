package org.platformer.world.gen;

import org.platformer.block.Block;

public class Layer
{
	public final int rangeStart;
	public final int rangeEnd;
	public final Block block;
	
	public Layer(int rangeStart, int rangeEnd, Block block)
	{
		this.rangeStart = rangeStart; //inclusive
		this.rangeEnd = rangeEnd+1; //exclusive
		this.block = block;
	}
}
