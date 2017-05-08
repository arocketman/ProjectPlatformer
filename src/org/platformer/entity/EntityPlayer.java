package org.platformer.entity;

import org.platformer.animation.Animation;
import org.platformer.animation.AnimationEntity;
import org.platformer.animation.anims.AnimPlayerArms;
import org.platformer.animation.anims.AnimPlayerLegs;
import org.platformer.utils.AABB;
import org.platformer.world.World;
import org.platformer.world.chunk.Chunk;
import org.platformer.input.Mouse;
import org.platformer.world.chunk.Chunk;

public class EntityPlayer extends Entity
{
	public AnimationEntity animationEntity;
	public float mouseAngle;
	
	public EntityPlayer(World world, String hash)
	{
		super(world, hash);
		colBox = new AABB(15.925f,62.925f);
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
		if(animationEntity != null)animationEntity.update();
		super.update();
	}
	
	public void checkUserInput() {
		if (Mouse.isLeftClicked())
			breakBlock();
		else if (Mouse.isRightClicked())
			placeBlock();
	}
	
	private void breakBlock() {
		
	}
	
	private void placeBlock() {
		if (isWithinRange()) {
			int chunkX = (int) Math.floor(mouseX/(16*32));
			int chunkY = (int) Math.floor(mouseY/(16*32));
			int x = (int) Math.floor((mouseX)/16f)-(chunkX*(32));
			int y = (int) Math.floor((mouseY)/16f)-(chunkY*(32));
			Chunk chunk = Chunk.getChunk(chunkX,chunkY);
		} else
			return;
	}
	
	private boolean isWithinRange() {
		if (Mouse.getMouseLocation()[0] > Math.abs(this.posX) + 20 &&
				Mouse.getMouseLocation()[1] > Math.abs(this.posY) + 20)
			return true;
		else
			return false;
	}
}