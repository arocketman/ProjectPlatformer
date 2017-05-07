package org.platformer.entity;

import org.platformer.animation.Animation;
import org.platformer.animation.AnimationEntity;
import org.platformer.animation.anims.AnimPlayerArms;
import org.platformer.animation.anims.AnimPlayerLegs;
import org.platformer.register.RegisterKeybinds;
import org.platformer.utils.AABB;
import org.platformer.utils.MiscUtils;
import org.platformer.world.World;
import org.platformer.world.WorldClient;

public class EntityPlayer extends Entity
{
	public AnimationEntity animationEntity;
	public float mouseAngle;
	
	public EntityPlayer(World world, String hash)
	{
		super(world, hash);
		colBox = new AABB(15.925f,31.925f);
		findWorldSpawn();
		teleportToSpawn();
		setupAnimations();
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
		animationEntity.update();
		super.update();
	}
}