package org.platformer.entity;

import org.platformer.register.RegisterKeybinds;
import org.platformer.world.World;

public class EntityPlayerLocal extends EntityPlayer
{

	public EntityPlayerLocal(World world, String hash)
	{
		super(world, hash);
	}

	@Override
	public void update()
	{
		if(RegisterKeybinds.move_left.isPressed()) motionX = -4f;
		if(RegisterKeybinds.move_right.isPressed()) motionX = 4f;
		
		if(onGround)
		{
			if(RegisterKeybinds.move_up.isPressed()) motionY = -4.2f;
		}
		
		super.update();
	}
}
