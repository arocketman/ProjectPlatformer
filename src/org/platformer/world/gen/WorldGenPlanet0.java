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
	public void getLayerData(ArrayList<Layer> list)
	{
		Layer grass = new Layer(0,0,RegisterBlocks.grass);
		list.add(grass);
		Layer dirt = new Layer(0,20,RegisterBlocks.dirt);
		list.add(dirt);
		Layer stone = new Layer(21,30,RegisterBlocks.stone);
		list.add(stone);
		Layer granite = new Layer(31,64,RegisterBlocks.granite);
		list.add(granite);
	}
	
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
		Log.out(offset);
		world.setSpawnChunk(chk);

		int maxWidth = 32;
		int maxHeight = 32;
		int offsetHeightTarget = (random.nextInt(32)-16);
		int offsetHeight = offsetHeightTarget;
		for(int i=0;i<maxWidth;i++)
		{
			if(offsetHeight == offsetHeightTarget)offsetHeightTarget = (random.nextInt(32)-16);
			int chunkX = i;
			for(int j=0;j<maxHeight;j++)
			{
				int chunkY = j;
				for(int x=0;x<32;x++)
				{
					if(offsetHeight < offsetHeightTarget)offsetHeight+=random.nextInt(3);
					if(offsetHeight > offsetHeightTarget)offsetHeight-=random.nextInt(3);
					
					for(int y=0;y<32;y++)
					{
						int newX = x;
						int newY = y+offsetHeight;
						int worldPosX = (chunkX*(32*16))+(newX*16);
						int worldPosY = ((chunkY+offset2)*(32*16))+(newY*16);
						Chunk chunk = world.getChunkWorldPos(worldPosX, worldPosY);
						if(chunk != null)
						{
							int blockID = -2;
							for(Layer layer : layers)
							{
								if((((chunkY+offset2)*32)+y) >= (layer.rangeStart+(offset2*32)) && (((chunkY+offset2)*32)+y) < (layer.rangeEnd+(offset2*32)))
								{
									blockID = layer.block.getID();
									break;
								}
							}
							
							if(blockID != -2)
							{
								int xx = (newX % 32);
								int yy = (newY % 32);
								if(xx >= 0 && yy >= 0)
								{
									chunk.placeBlock(xx, yy, RegisterBlocks.get(blockID));
								}
							}
						}
					}
				}
			}
		}
	}
}
