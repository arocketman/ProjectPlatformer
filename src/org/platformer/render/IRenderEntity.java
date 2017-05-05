package org.platformer.render;

import org.newdawn.slick.Graphics;
import org.platformer.entity.Entity;

public interface IRenderEntity
{
	public void render(Entity ent, Graphics g);
}
