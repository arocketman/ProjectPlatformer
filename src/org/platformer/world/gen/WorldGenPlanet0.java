package org.platformer.world.gen;

import java.util.ArrayList;
import java.util.Random;

import org.platformer.block.Block;
import org.platformer.register.RegisterBlocks;
import org.platformer.utils.Noise;
import org.platformer.world.World;
import org.platformer.world.chunk.Chunk;

public class WorldGenPlanet0 implements IWorldGen
{
	/**
	 * Setup the layers this world will generate, these layers stack
	 */
	@Override
	public void getLayerData(ArrayList<Layer> list)
	{
		Layer grass = new Layer(1,RegisterBlocks.grass);
		list.add(grass);
		Layer dirt = new Layer(30,RegisterBlocks.dirt);
		list.add(dirt);
		Layer stone = new Layer(100,RegisterBlocks.stone);
		list.add(stone);
		Layer granite = new Layer(50,RegisterBlocks.granite);
		list.add(granite);
		Layer granite2 = new Layer(1,RegisterBlocks.granite);
		list.add(granite2);
	}

	@Override
	public void generate(World world)
	{
		/** Gets the random from the world, which has a seed, thus generating the same thing everytime*/
		Random random = world.getRandom();
		
		/** Create the layer data */
		ArrayList<Layer> layers = new ArrayList<Layer>();
		getLayerData(layers);

		/** Get the max amount of layers */
		int maxLayer = 0;
		for(Layer layer : layers)
		{
			maxLayer+=layer.range;
		}

		/** Create offset so the world is at the bottom instead of generating at the top*/
		int offset = 30-(int)Math.floor(maxLayer/32);
		
		/** Set spawn chunk while we're at it*/
		Chunk chk = world.getChunk(random.nextInt(32), offset);
		world.setSpawnChunk(chk);

		int maxWidth = 32;
		int maxHeight = 32;
		int targetOffset = 0;
		int offsetHeight2 = 0;
		
		float caveSize = 0.1f; //0 = biggest, 0.9 = smallest
		int frequency = 256+128; //How 'smooth / big' the generated noise is.
		int roughness = 16; //terrain roughness

		for(int i=0;i<maxWidth;i++) //go through all chunks x axis
		{
			int chunkX = i;
			for(int j=0;j<maxHeight;j++) //go through all chunks y axis (and x)
			{
				int chunkY = j;
				for(int x=0;x<32;x++) //go through all blocks in the chunk x axis
				{
					/** Set target offset if offsetHeight2 equals*/
					if((x & 4) == 0 && offsetHeight2 == targetOffset)targetOffset = random.nextInt(roughness+1)-(roughness/2);
					
					/** Increment towards targetOffset, this creates the bumpy terrain */
					if(offsetHeight2 < targetOffset)offsetHeight2+=random.nextInt(4);
					if(offsetHeight2 > targetOffset)offsetHeight2-=random.nextInt(4);
					
					for(int y=0;y<32;y++) //go through all blocks in the chunk y axis
					{
						int newX = x;
						int newY = y;
						int worldPosX = (chunkX*(32*16))+(newX*16);
						int worldPosY = ((chunkY)*(32*16))+((newY)*16);
						
						/** Generate noise to generate caves*/
						float h = (float) Noise.noise((float) worldPosX / (frequency*1.5f), (float) worldPosY / (frequency*0.75f)); 
						float h2 = (float) Noise.noise((float) worldPosX / (frequency*0.95), (float) worldPosY / (frequency*0.5)); 
						float h3 = (float) Noise.noise((float) worldPosX / (frequency*0.75), (float) worldPosY / (frequency*1.5)); 
						Chunk chunk = world.getChunkWorldPos(worldPosX, worldPosY);
						if(chunk != null)
						{
							int blockID = -2;

							/** Get the block actual position without pixel position*/
							int rangeY = (worldPosY/16)+offsetHeight2;
							int last = 0;
							int index = 0;
							for(Layer layer : layers) //Go through this worlds layers
							{
								int check1 = (((maxHeight*32)-maxLayer)+last);
								int rand = (random.nextInt(5));
								if(index <= 1)rand = 0;
								
								/** Check if our current block location matches this world's layer location*/
								if(rangeY > check1-rand && rangeY <= check1+layer.range+rand)
								{
									//Set block type to the layers block type
									blockID = layer.block.getID();
									break;
								}
								
								index++;
								last+=layer.range;
							}
							
							/** Make caves more common / increase size the further down the world you go*/
							float gen1 = h-(1.25f-(rangeY/(float)(maxWidth*maxHeight)));
							float gen2 = h2-(1.25f-(rangeY/(float)(maxWidth*maxHeight)));
							float gen3 = h3-(1.25f-(rangeY/(float)(maxWidth*maxHeight)));

							/** Check if the noise size is within our cave max and min size*/
							boolean flag = !(gen1 > caveSize && gen1 <= 1) && !(gen2 > caveSize && gen2 <= 1) && !(gen3 > caveSize && gen3 <= 1);
							
							if(i == 0)
							{
								if(x == 0)flag = true;
							}
							
							if(i == maxWidth-1)
							{
								if(x == 31)flag = true;
							}
							
							if(blockID != -2)
							{
								chunk.placeBlock(x, y, RegisterBlocks.get(blockID), true);
							}
							
							if(flag)
							{
								if(blockID != -2)
								{
									int xx = (newX % 32);
									int yy = (newY % 32);
									if(xx < 0)xx = 32+xx;
									if(yy < 0)yy = 32+yy;

									chunk.placeBlock(xx, yy, RegisterBlocks.get(blockID), false);
								}
							}
							
							/** Set Bottom block of the last chunk*/
							if(j == maxHeight-1)
							{
								if(y == 31)
								{
									Block block = layers.get(layers.size()-1).block; //Get last layer
									chunk.placeBlock(x, y, block, false);
								}
							}
						}	
					}
				}
			}
		}
	}
}
