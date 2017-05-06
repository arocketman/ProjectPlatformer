package org.platformer.world.gen;

import java.util.ArrayList;
import java.util.Random;

import org.platformer.register.RegisterBlocks;
import org.platformer.utils.Log;
import org.platformer.world.World;
import org.platformer.world.chunk.Chunk;

public class WorldGenPlanet0 implements IWorldGen
{
	@Override
	public void generate(World world)
	{
		Random random = world.getRandom();
		ArrayList<Layer> layers = new ArrayList<Layer>();
		getLayerData(layers);
		
		int minLayer = 999999;
		int maxLayer = 0;
		
		for(Layer layer : layers)
		{
			if(minLayer > layer.rangeStart)minLayer = layer.rangeStart;
			if(maxLayer < layer.rangeEnd)maxLayer = layer.rangeEnd;
		}
		
		int offset = 31-(int)Math.floor(maxLayer/32);
		int offset2 = 31-(int)Math.floor(minLayer/32);
		Chunk chk = world.getChunk(random.nextInt(32), offset2);
		Log.out(offset2);
		world.setSpawnChunk(chk);
		
		int maxWidth = 32;
		for(int i=0;i<maxWidth;i++)
		{
			int chunkX = i;
			for(Layer layer : layers)
			{
				Chunk chunk = null;
				int start = layer.rangeStart+(offset*32);
				int end = layer.rangeEnd+(offset*32);
				for(int a=start;a<end;a++)
				{
					int chunkY = (int)Math.floor(a/32);
					Chunk chunkTest = world.getChunk(chunkX, chunkY);
					if(chunk != chunkTest)
					{
						chunk = chunkTest;
						generateChunk(chunk, layer);
					}
				}
			}
		}
	}

	private void generateChunk(Chunk chunk, Layer layer)
	{
		for(int i=0;i<32;i++)
		{
			for(int a=layer.rangeStart;a<layer.rangeEnd;a++)
			{
				chunk.placeBlock(i, a, layer.block);
			}
		}
	}

	@Override
	public void getLayerData(ArrayList<Layer> list)
	{
		Layer dirt = new Layer(0,20,RegisterBlocks.dirt);
		list.add(dirt);
		Layer stone = new Layer(21,30,RegisterBlocks.stone);
		list.add(stone);
	}
}
