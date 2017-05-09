package org.platformer.entity;

import org.platformer.Data.PlayerConfiguration;
import org.platformer.animation.Animation;
import org.platformer.animation.AnimationEntity;
import org.platformer.animation.anims.AnimPlayerArms;
import org.platformer.animation.anims.AnimPlayerLegs;
import org.platformer.utils.AABB;
import org.platformer.world.World;

public class EntityPlayer extends Entity
{
	public AnimationEntity animationEntity;
	public float mouseAngle;

	//Nomitso add
	private PlayerConfiguration config;

	public EntityPlayer(World world, String hash)
	{
		super(world, hash);
		colBox = new AABB(15.925f,62.925f);
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
		colBox = new AABB(15.925f,62.925f);
		this.config = config;
		findWorldSpawn();
		teleportToSpawn();
		setupAnimations();
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

	@Override
	public void update()
	{
		if(animationEntity != null)animationEntity.update();
		super.update();
	}
}