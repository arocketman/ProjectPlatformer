package org.platformer.world.gen;

import java.util.ArrayList;
import java.util.Random;

import org.platformer.register.RegisterBlocks;
import org.platformer.utils.Noise;
import org.platformer.world.World;
import org.platformer.world.chunk.Chunk;

public class WorldGenPlanet0 implements IWorldGen
{
	@Override
	public void getLayerData(ArrayList<Layer> list)
	{
		Layer grass = new Layer(1,RegisterBlocks.grass);
		list.add(grass);
		Layer dirt = new Layer(10,RegisterBlocks.dirt);
		list.add(dirt);
		Layer stone = new Layer(25,RegisterBlocks.stone);
		list.add(stone);
		Layer granite = new Layer(20,RegisterBlocks.granite);
		list.add(granite);
	}

	@Override
	public void generate(World world)
	{
		Random random = world.getRandom();
		ArrayList<Layer> layers = new ArrayList<Layer>();
		getLayerData(layers);

		int maxLayer = 0;
		for(Layer layer : layers)
		{
			maxLayer+=layer.range;
		}

		int offset = 30-(int)Math.floor(maxLayer/32);
		Chunk chk = world.getChunk(random.nextInt(32), offset);
		world.setSpawnChunk(chk);

		int maxWidth = 32;
		int maxHeight = 32;
		int targetOffset = 0;
		int offsetHeight2 = 0;
		
		float caveSize = 0.45f; //0 = biggest, 0.9 = smallest
		int frequency = 128; 
		int roughness = 8; 

		for(int i=0;i<maxWidth;i++)
		{
			int chunkX = i;
			for(int j=0;j<maxHeight;j++)
			{
				int chunkY = j;
				for(int x=0;x<32;x++)
				{
					if(offsetHeight2 == targetOffset)targetOffset = random.nextInt(roughness+1)-(roughness/2);
					if(offsetHeight2 < targetOffset)offsetHeight2+=random.nextInt(4);
					if(offsetHeight2 > targetOffset)offsetHeight2-=random.nextInt(4);
					for(int y=0;y<32;y++)
					{
						int newX = x;
						int newY = y;
						int worldPosX = (chunkX*(32*16))+(newX*16);
						int worldPosY = ((chunkY)*(32*16))+((newY)*16);
						float h = (float) Noise.noise((float) worldPosX / (frequency), (float) worldPosY / frequency); 
						Chunk chunk = world.getChunkWorldPos(worldPosX, worldPosY);
						if(chunk != null)
						{
							int blockID = -2;

							int rangeY = (worldPosY/16)+offsetHeight2;
							int last = 0;
							int index = 0;
							for(Layer layer : layers)
							{
								int check1 = (((maxHeight*32)-maxLayer)+last);
								int rand = (random.nextInt(5));
								if(index <= 1)rand = 0;
								if(rangeY > check1-rand && rangeY <= check1+layer.range+rand)
								{
									blockID = layer.block.getID();
									break;
								}
								
								index++;
								last+=layer.range;
							}

							if(!(h > caveSize && h <= 1))
							{
								if(blockID != -2)
								{
									int xx = (newX % 32);
									int yy = (newY % 32);
									if(xx < 0)xx = 32+xx;
									if(yy < 0)yy = 32+yy;

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
