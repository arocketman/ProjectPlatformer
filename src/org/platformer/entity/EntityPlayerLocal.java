package org.platformer.entity;

import org.platformer.data.PlayerConfiguration;
import org.platformer.register.RegisterKeybinds;
import org.platformer.utils.MiscUtils;
import org.platformer.world.World;
import org.platformer.world.WorldClient;

public class EntityPlayerLocal extends EntityPlayer
{

	public EntityPlayerLocal(World world, String hash)
	{
		super(world, hash);
	}

	/**
	 * Constructor with PlayerConfiguration added.
	 *
	 * @param world
	 * @param hash
	 * @param config
	 */
	public EntityPlayerLocal(World world, String hash, PlayerConfiguration config)
	{

		super(world, hash, config);

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

		mouseAngle = MiscUtils.pointTowardsPosition(posX, posY-20, ((WorldClient)world).getMouseInWorld()[0], ((WorldClient)world).getMouseInWorld()[1]);
		super.update();
	}
}
