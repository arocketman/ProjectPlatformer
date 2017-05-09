package org.platformer.entity;

import org.platformer.animation.Animation;
import org.platformer.animation.AnimationEntity;
import org.platformer.animation.anims.AnimPlayerArms;
import org.platformer.animation.anims.AnimPlayerLegs;
import org.platformer.utils.AABB;
import org.platformer.world.World;
import org.platformer.world.chunk.Chunk;
import org.platformer.input.Mouse;
import org.platformer.register.RegisterBlocks;
import org.platformer.world.chunk.Chunk;
import org.platformer.utils.Handler;

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
		if(animationEntity != null)
			animationEntity.update();
		checkUserInput();
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
		float[] mousePos = Mouse.getMouseLocation();
		int chunkX = (int) Math.floor(mousePos[0]/(16*32));
		int chunkY = (int) Math.floor(mousePos[1]/(16*32));
		int x = (int) Math.floor((mousePos[0])/16f)-(chunkX*(32));
		int y = (int) Math.floor((mousePos[1])/16f)-(chunkY*(32));
		Chunk chunk = Handler.getWorld().getChunk(chunkX, chunkY);
		
		if (isWithinRange()) {
			if(chunk == null)
				return;
			
			chunk.placeBlock(x, y, RegisterBlocks.dirt);
		}
	}
	
	private boolean isWithinRange() {
		if (Mouse.getMouseLocation()[0] > this.posX + 20 && Mouse.getMouseLocation()[1] > this.posY + 20 ||
				Mouse.getMouseLocation()[0] > this.posX + 20 && Mouse.getMouseLocation()[1] < this.posY + 20 ||
				Mouse.getMouseLocation()[0] < this.posX + 20 && Mouse.getMouseLocation()[1] > this.posY + 20 ||
				Mouse.getMouseLocation()[0] < this.posX + 20 && Mouse.getMouseLocation()[1] < this.posY + 20)
			return true;
		else
			return false;
	}
}