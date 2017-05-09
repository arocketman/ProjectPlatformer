package org.platformer.world;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glCallList;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEndList;
import static org.lwjgl.opengl.GL11.glGenLists;
import static org.lwjgl.opengl.GL11.glNewList;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.platformer.Main;
import org.platformer.block.Block;
import org.platformer.data.Inventory;
import org.platformer.data.Item;
import org.platformer.data.PlayerConfiguration;
import org.platformer.entity.*;
import org.platformer.entity.EntityTracker.Map;
import org.platformer.register.RegisterBlocks;
import org.platformer.register.RegisterRenders;
import org.platformer.register.RegisterTextures;
import org.platformer.render.Camera;
import org.platformer.render.RenderEntity;
import org.platformer.utils.AABB;
import org.platformer.utils.RenderUtils;
import org.platformer.world.chunk.Chunk;

import java.util.Iterator;

public class WorldClient extends WorldServer
{
	private Image terrain = RegisterTextures.getTexture("terrain");
	private EntityPlayer localPlayer;
	private Camera camera;

	private int glGenList = -99;

	public WorldClient(Long seed)
	{
		super(seed);
	}

	@Override
	public void init()
	{
		camera = new Camera(4f,0.005f);
		camera.translateX = 0;
		camera.translateY = 0;
		camera.smoothMovement = true;
		super.init();
		
		WorldClientHandler.setWorldClient(this);
	}

	@Override
	public void postWorldGenerator()
	{
		//localPlayer = new EntityPlayerLocal(this,"Username");

		// testing the add of a player with a PlayerConfiguration
		PlayerConfiguration config = new PlayerConfiguration(5, 5, new Inventory(2));
		localPlayer = new EntityPlayerLocal(this,"Username", config);

		// testing the add of an item to the world
		EntityItem item = new EntityItem(this, "item", new Item("item", "missingtexture"));
		EntityItem item2 = new EntityItem(this, "item2", new Item("item2", "missingtexture"));
		EntityItem item3 = new EntityItem(this, "item3", new Item("item3", "missingtexture"));
	}

	@Override
	public void update()
	{
		super.update();

		if(Mouse.isButtonDown(0)) clickMouse(0);
		if(Mouse.isButtonDown(1)) clickMouse(1);
		if(Mouse.isButtonDown(2)) clickMouse(2); // testing the add of an item to the inventory
	}

	/**
	 * Temporary method to test block placing / removal
	 * @param i - Button Index Pressed
	 */
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
		else if(i == 0)
		{
			chunk.removeBlock(x, y);
		}
		// testing the add of an item to the player's inventory
		// on click, removes the item and puts into the player's inventory
		else if(i == 2)
		{

			AABB mouseAABB = new AABB(1,1);
			mouseAABB.posX = mouseX;
			mouseAABB.posY = mouseY;

			Iterator<Entity> entIterator = chunk.getEntities().iterator();

			while(entIterator.hasNext()) {

				Entity entity = entIterator.next();

				if(mouseAABB.intersects(entity.colBox))
				{

					if (entity instanceof EntityItem)
					{

						EntityItem entItem = (EntityItem) entity;

						if(localPlayer.getPlayerConfiguration().getInventory().isFull())
						{

							System.out.println("Player's inventory is full, couldn't add item: " + entItem.getItem() + " to the inventory.");

						}
						else
						{

							localPlayer.getPlayerConfiguration().getInventory().addItem(entItem.getItem());
							entIterator.remove();
							EntityTracker.removeEntity(entity);
							System.out.println("\nThe item " + entItem.getItem() + " has been added to the player's inventory.");
							System.out.println("Player's inventory: " + localPlayer.getPlayerConfiguration().getInventory().getItems());

						}
					}
				}
			}
		}
	}

	@Override
	public void render(Graphics g)
	{
		renderBackground(g);
		transformCamera(g);
		renderChunks(g);
		renderEntities(g);
		g.resetTransform();
		super.render(g);
	}

	/**
	 * Render all chunks
	 * @param g - Graphics
	 */
	private void renderChunks(Graphics g)
	{
		if(glGenList == -99)glGenList = glGenLists(1024);

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
							int id2 = backgroundChunks[i].blocks[a];
							int id = chunks[i].blocks[a];
							if(id2 != -1 && id == -1)
							{
								Block block = RegisterBlocks.get(id2);
								float blockX = chunkX+(bx*16);
								float blockY = chunkY+(by*16);

								int bid = block.getID();
								int rowX = bid % 16;
								int rowY = bid / 16;
								float uvOffset = (1f/16f);
								float uvMinX = (rowX*uvOffset)+0.000125f;
								float uvMinY = (rowY*uvOffset)+0.000125f;
								float uvMaxX = (((rowX)*uvOffset)+uvOffset)-0.000125f;
								float uvMaxY = (((rowY)*uvOffset)+uvOffset)-0.000125f;
								glEnable(GL_TEXTURE_2D);
								float shade = 0.65f;
								GL11.glColor4f(shade, shade, shade, 1f);
								RenderUtils.renderBlock(new float[]{blockX-0.0125f, blockY-0.0125f, blockX+16+0.0125f, blockY+16+0.0125f}, new float[]{uvMinX,uvMinY,uvMaxX,uvMaxY},block);
								GL11.glColor4f(1f, 1f, 1f, 1f);
							}
							if(id != -1)
							{
								Block block = RegisterBlocks.get(id);
								float blockX = chunkX+(bx*16);
								float blockY = chunkY+(by*16);

								int bid = block.getID();
								int rowX = bid % 16;
								int rowY = bid / 16;
								float uvOffset = (1f/16f);
								float uvMinX = (rowX*uvOffset)+0.000125f;
								float uvMinY = (rowY*uvOffset)+0.000125f;
								float uvMaxX = (((rowX)*uvOffset)+uvOffset)-0.000125f;
								float uvMaxY = (((rowY)*uvOffset)+uvOffset)-0.000125f;
								glEnable(GL_TEXTURE_2D);
								RenderUtils.renderBlock(new float[]{blockX-0.0125f, blockY-0.0125f, blockX+16+0.0125f, blockY+16+0.0125f}, new float[]{uvMinX,uvMinY,uvMaxX,uvMaxY},block);
								GL11.glColor4f(1f, 1f, 1f, 1f);
							}

							AABB box = chunks[i].aabbPool[a];
							if(box != null)
							{
								aabbPool.add(box);
							}
						}
						glEndList();
						chunks[i].onUpdate(this);
						backgroundChunks[i].onUpdate(this);
					}
					else
					{
						terrain.bind();
						glCallList(glGenList+i);
					}
				}
				else
				{
					if(!chunks[i].needsUpdate)chunks[i].needsUpdate = true;;
					if(!backgroundChunks[i].needsUpdate)backgroundChunks[i].needsUpdate = true;;
				}
			}
		}
		/**
		 float[] mouse = getMouseInWorld();
		 float mouseX = mouse[0]-8;
		 float mouseY = mouse[1]-8;
		 int chunkX = (int) Math.floor(mouseX/(16*32));
		 int chunkY = (int) Math.floor(mouseY/(16*32));
		 g.drawRect(chunkX*(16*32), chunkY*(16*32), (16*32), (16*32));
		 */
	}

	/**
	 * Applies the camera transforms, and centers it to the local player(if it exists)
	 * @param g - Graphics
	 */
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

	/**
	 * Render all entities by getting the mapped Renderer for them.
	 * @param g - Graphics
	 */
	private void renderEntities(Graphics g)
	{
		for(Object o : EntityTracker.getEntityMap(Map.ENTITIES).values().toArray())
		{
			Entity ent = (Entity) o;
			RenderEntity re = RegisterRenders.getRenderer(ent.getClass());
			if(re != null)
			{
				re.render(ent, g);
			}
		}
	}

	/**
	 * Render world background
	 * @param g - Graphics
	 */
	private void renderBackground(Graphics g)
	{
		RegisterTextures.getTexture("sky").draw(0, 0, 800, 600);
	}

	/**
	 * Useful conversion to convert screen coordinates to world coordinates, applies camera transformation
	 * @param x - Screen Coordinate
	 * @param y - Screen Coordinate
	 * @return Float[2]{x,y} - World coordinates
	 */
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

	/**
	 * Converts the mouse coordinates to world coordinates.
	 * @return Float[2]{x,y} - World coordinates
	 */
	public float[] getMouseInWorld()
	{
		int height = Main.displayHeight;
		return getWorldCoordinates(Mouse.getX(),height-Mouse.getY());
	}

	/**
	 * NOTE: Not properly working with scale - TODO Fix insideCameraView
	 * @param posX - object position
	 * @param posY - object position
	 * @param width - object width
	 * @param height - object height
	 * @param centered - is object centered
	 * @return is visible to camera view
	 */
	private boolean insideCameraView(float posX, float posY, float width, float height, boolean centered)
	{
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

class WorldClientHandler {
	private static WorldClient worldClient;
	
	public static void setWorldClient(WorldClient p_WorldClient) {
		worldClient = p_WorldClient;
	}
	
	public static WorldClient getWorldClient() {
		return worldClient;
	}
}