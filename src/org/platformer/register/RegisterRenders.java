package org.platformer.register;

import java.util.HashMap;

import org.platformer.entity.Entity;
import org.platformer.entity.EntityPlayerLocal;
import org.platformer.render.RenderEntity;
import org.platformer.render.RenderPlayer;

public class RegisterRenders
{
	private static HashMap<Class<? extends Entity>,RenderEntity> entityRenderMap = new HashMap<Class<? extends Entity>,RenderEntity>();
	
	public static void init()
	{
		entityRenderMap.put(EntityPlayerLocal.class, new RenderPlayer());
		}
	
	public static RenderEntity getRenderer(Class<? extends Entity> clzz)
	{
		return entityRenderMap.get(clzz);
	}
}
