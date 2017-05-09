package org.platformer.entity;

import org.platformer.animation.Animation;
import org.platformer.animation.AnimationEntity;
import org.platformer.animation.anims.AnimPlayerArms;
import org.platformer.animation.anims.AnimPlayerLegs;
import org.platformer.data.PlayerConfiguration;
import org.platformer.utils.AABB;
import org.platformer.world.World;
import org.platformer.world.WorldClient;
import org.platformer.world.chunk.Chunk;
import org.platformer.input.Mouse;
import org.platformer.register.RegisterBlocks;

public class EntityPlayer extends Entity
{
	public float playerSize_w = 12.925f;
	public float playerSize_h = 47.925f;
	public AnimationEntity animationEntity;
	public float mouseAngle;
	
	private Mouse playerMouse;

	//Nomitso add
	private PlayerConfiguration config;

	public EntityPlayer(World world, String hash)
	{
		super(world, hash);
		colBox = new AABB(playerSize_w,playerSize_h);
		findWorldSpawn();
		teleportToSpawn();
		setupAnimations();
	}

	/**
	 * Constructor with PlayerConfiguration added.
	 *
	 * @param world
	 * @param hash
	 * @param config
	 */
	public EntityPlayer(World world, String hash, PlayerConfiguration config)
	{
		super(world, hash);
		colBox = new AABB(playerSize_w,playerSize_h);
		this.config = config;
		findWorldSpawn();
		teleportToSpawn();
		setupAnimations();
		createMouse();
	}
	
	private void createMouse() {
		playerMouse = new Mouse();
	}

	/**
	 * Sets the player's configuration.
	 *
	 * @param config the player's configuration
	 * @see PlayerConfiguration
	 */
	public void setPlayerConfiguration(PlayerConfiguration config) {

		this.config = config;

	}

	/**
	 * Gets the player's configuration
	 *
	 * @return the player's configuration
	 * @see PlayerConfiguration
	 */
	public PlayerConfiguration getPlayerConfiguration() {

		return config;

	}

	private void setupAnimations()
	{
		animationEntity = new AnimationEntity(this);
		animationEntity.addAnimation("anim_player", new Animation[]{new AnimPlayerLegs(),new AnimPlayerArms()});
		animationEntity.remapIDToExisting("anim_player", 1);
	}
	
	// Testing
	private void checkInput() {
		int chunkX = (int) Math.floor(playerMouse.getRelativeLocation()[0]/(16*32));
		int chunkY = (int) Math.floor(playerMouse.getRelativeLocation()[1]/(16*32));
		int x = (int) Math.floor((playerMouse.getRelativeLocation()[0])/16f)-(chunkX*(32));
		int y = (int) Math.floor((playerMouse.getRelativeLocation()[1])/16f)-(chunkY*(32));
		
		Chunk chunk = WorldClient.getWorld().getChunk(chunkX,chunkY);
		if (chunk == null)
			return;
		
		if (playerMouse.isLeftClicked()) {
			//Hitting the block if it's nearby
			if(chunk.getBlockAABB(x,y) != null && colBox.isNearby(chunk.getBlockAABB(x,y)))
				chunk.hitBlock(x,y);
		}
		if (playerMouse.isRightClicked()){
			//Placing the block if the mouse is near to the player
			if(colBox.isNearby(playerMouse.getRelativeX(),playerMouse.getRelativeY()))
				chunk.placeBlock(x, y, RegisterBlocks.dirt, false);
		}
	}
	
	@Override
	public void update()
	{
		if(animationEntity != null)animationEntity.update();
		playerMouse.update();
		super.update();
		
		checkInput();
	}
}