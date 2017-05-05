package org.platformer.world;

import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.platformer.Main;
import org.platformer.block.Block;
import org.platformer.entity.Entity;
import org.platformer.entity.EntityPlayer;
import org.platformer.entity.EntityTracker;
import org.platformer.entity.EntityTracker.Map;
import org.platformer.register.RegisterBlocks;
import org.platformer.register.RegisterRenders;
import org.platformer.register.RegisterTextures;
import org.platformer.render.Camera;
import org.platformer.render.RenderEntity;
import org.platformer.utils.AABB;
import org.platformer.utils.IDefaultGame;
import org.platformer.utils.RenderUtils;
import org.platformer.world.chunk.Chunk;

public class World implements IDefaultGame
{
	private Image terrain = RegisterTextures.getTexture("terrain");
	
	private Camera camera;
	private EntityPlayer localPlayer;
	private boolean isServer = false;

	public ArrayList<AABB> aabbPool = new ArrayList<AABB>();
	public Chunk[] chunks = new Chunk[1024];
	private int glGenList = -99;
	
	private Random random;
	
	public World(boolean isServer)
	{
		this.isServer = isServer;
	}

	@Override
	public void init()
	{
		random = new Random(12483235124l);
		
		camera = new Camera(4f,0.05f);
		camera.translateX = 0;
		camera.translateY = 0;
		
		for(int i=0;i<chunks.length;i++)
		{
			int cy = i/32;
			int cx = (i)-(cy*32);
			chunks[i] = new Chunk(cx,cy);
			for(int a=0;a<chunks[i].blocks.length;a++)
			{
				chunks[i].blocks[a] = random.nextInt(2);
			}
		}
		
		if(!isServer)
		{
			localPlayer = new EntityPlayer(this,"test");
		}
	}

	@Override
	public void update()
	{
		for(Entity ent : EntityTracker.getEntityMap(Map.ENTITIES).values())
		{
			ent.update();
		}
		if(Mouse.isButtonDown(0))
		{
			clickMouse(0);
		}
		
		if(Mouse.isButtonDown(1))
		{
			clickMouse(1);
		}
	}

	private void clickMouse(int i)
	{
		float[] mouse = getMouseInWorld();
		float mouseX = mouse[0];
		float mouseY = mouse[1];
		int chunkX = (int) Math.floor(mouseX/(16*32));
		int chunkY = (int) Math.floor(mouseY/(16*32));
		int x = (int) Math.floor((mouseX)/16f)-(chunkX*(32));
		int y = (int) Math.floor((mouseY)/16f)-(chunkY*(32));
		Chunk chunk = getChunk(chunkX,chunkY);
		if(chunk == null)return;
		
		if(i == 1)
		{
			chunk.placeBlock(x, y, RegisterBlocks.dirt);
		}
		else
		{
			chunk.removeBlock(x, y);
		}
	}
	
	public Chunk getChunkWorldPos(int x, int y)
	{
		return getChunk((int)Math.floor(x/(16*32)),(int)Math.floor(y/(16*32)));
	}
	
	public Chunk getChunk(int x, int y)
	{
		int i = (y * 32) + x;
		if(i < 0 || i >= chunks.length)
		{
			return null;
		}
		return chunks[i];
	}

	@Override
	public void render(Graphics g)
	{
		renderBackground(g);
		transformCamera(g);
		renderEntities(g);
		renderChunks(g);
		g.resetTransform();
	}
	
	private void renderChunks(Graphics g)
	{
		g.setAntiAlias(false);
		if(glGenList == -99)
		{
			glGenList = glGenLists(1024);
		}
		boolean firstClear = true;
		for(int i=0;i<chunks.length;i++)
		{
			if(chunks[i] != null)
			{
				int cy = i/32;
				int cx = (i)-(cy*32);
				int chunkX = ((cx*32)*16);
				int chunkY = ((cy*32)*16);
				if(insideCameraView(chunkX,chunkY,(32*16),(32*16),false))
				{
					if(chunks[i].needsUpdate)
					{
						if(firstClear)
						{
							aabbPool.clear();
							firstClear = false;
						}
						glNewList(glGenList+i, GL11.GL_COMPILE_AND_EXECUTE);
						terrain.bind();
						for(int a=0;a<chunks[i].blocks.length;a++)
						{
							int by = a/32;
							int bx = (a)-(by*32);
							int id = chunks[i].blocks[a];
							if(id != -1)
							{
								Block block = RegisterBlocks.get(id);
								float blockX = chunkX+(bx*16);
								float blockY = chunkY+(by*16);
								
								int bid = block.getID();
								int rowX = bid % 16;
								int rowY = bid / 16;
								float uvOffset = (1f/16f);
								float uvMinX = (rowX*uvOffset)+0.0125f;
								float uvMinY = (rowY*uvOffset)+0.0125f;
								float uvMaxX = ((rowX+1)*uvOffset)-0.0125f;
								float uvMaxY = ((rowY+1)*uvOffset)-0.0125f;
								glEnable(GL_TEXTURE_2D);
								RenderUtils.drawTexturedQuad(new float[]{blockX-0.0125f, blockY-0.0125f, blockX+16+0.0125f, blockY+16+0.0125f}, new float[]{uvMinX,uvMinY,uvMaxX,uvMaxY});
							}
							
							AABB box = chunks[i].aabbPool[a];
							if(box != null)
							{
								aabbPool.add(box);
							}
						}
						glEndList();
						chunks[i].onUpdate();
					}
					else
					{
						terrain.bind();
						glCallList(glGenList+i);
					}
					
					for(int a=0;a<chunks[i].blocks.length;a++)
					{
						AABB box = chunks[i].aabbPool[a];
						if(box != null)
						{
							//g.drawRect(box.posX-(box.getWidth()/2f), box.posY-(box.getHeight()/2f), box.getWidth(), box.getHeight());
						}
					}
				}
				else
				{
					if(!chunks[i].needsUpdate)
					{
						chunks[i].needsUpdate = true;
					}
				}
			}
		}
		
		float[] mouse = getMouseInWorld();
		float mouseX = mouse[0]-8;
		float mouseY = mouse[1]-8;
		int chunkX = (int) Math.floor(mouseX/(16*32));
		int chunkY = (int) Math.floor(mouseY/(16*32));
		g.drawRect(chunkX*(16*32), chunkY*(16*32), (16*32), (16*32));
	}

	public void transformCamera(Graphics g)
	{
		if(localPlayer != null)
		{
			int halfWidth = Main.displayWidth/2;
			int halfHeight = Main.displayHeight/2;
			camera.translateX = localPlayer.posX-halfWidth;
			camera.translateY = localPlayer.posY-halfHeight;
		}
		camera.transform(g);
	}
	
	private void renderEntities(Graphics g)
	{
		for(Object o : EntityTracker.getEntityMap(Map.ENTITIES).values().toArray())
		{
			Entity ent = (Entity) o;
			RenderEntity re = RegisterRenders.getRenderer(ent.getClass());
			if(re != null){re.render(ent, g);}
		}
	}

	private void renderBackground(Graphics g)
	{
		RegisterTextures.getTexture("sky").draw(0, 0, 800, 600);
	}

	public float[] getWorldCoordinates(float x, float y)
	{
		int halfWidth = Main.displayWidth/2;
		int halfHeight = Main.displayHeight/2;
		float scale = 1f/camera.scaleSmooth;
		float[] coord = new float[2];
		coord[0] = ((x-halfWidth)*scale)+(camera.translateXSmooth+halfWidth);
		coord[1] = ((y-halfHeight)*scale)+(camera.translateYSmooth+halfHeight);
		return coord;
	}

	public float[] getMouseInWorld()
	{
		int height = Main.displayHeight;
		return getWorldCoordinates(Mouse.getX(),height-Mouse.getY());
	}
	
	private boolean insideCameraView(float posX, float posY, float width, float height, boolean centered)
	{
		//if(isServer)return true;
		float scale = 1f/camera.scaleSmooth;
		
		int halfWidth = Main.displayWidth/2;
		int halfHeight = Main.displayHeight/2;
		float[] topleft = getWorldCoordinates(-halfWidth, -halfHeight);
		float[] bottomright = getWorldCoordinates(halfWidth, halfHeight);
		topleft[0]=camera.translateXSmooth-(halfWidth*scale);
		topleft[1]=camera.translateYSmooth-(halfHeight*scale);
		bottomright[0]=camera.translateXSmooth+((halfWidth)*scale)+halfWidth;
		bottomright[1]=camera.translateYSmooth+((halfHeight)*scale)+halfHeight;
		
		float[] posTopLeft = new float[]{posX+(width),posY+(height)};
		float[] posBottomRight = new float[]{posX,posY};
		if(centered)
		{
			posTopLeft = new float[]{posX+(width/2),posY+(height/2f)};
			posBottomRight =new float[]{posX-(width/2),posY-(height/2f)};
		}
		
		return (posTopLeft[0] > topleft[0] && posTopLeft[1] > topleft[1] && posBottomRight[0] < bottomright[0] && posBottomRight[1] < bottomright[1]);
	}
}
