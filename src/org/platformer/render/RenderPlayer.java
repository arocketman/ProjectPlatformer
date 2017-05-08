package org.platformer.render;

import org.newdawn.slick.Graphics;
import org.platformer.entity.Entity;
import org.platformer.entity.EntityPlayer;

public class RenderPlayer extends RenderEntity
{
	@Override
	public void render(Entity ent, Graphics g)
	{
		//Image tex = RegisterTextures.getTexture("player");
		//tex.draw(ent.posX-(ent.colBox.getWidth()/2), ent.posY-(ent.colBox.getHeight()/2),ent.colBox.getWidth(),ent.colBox.getHeight());
		g.pushTransform();
		g.translate(ent.posX, ent.posY);
		((EntityPlayer) ent).animationEntity.render(g);
		g.popTransform();
	}
}
