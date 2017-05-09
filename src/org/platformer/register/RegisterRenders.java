package org.platformer.register;

import java.util.HashMap;

import org.platformer.entity.Entity;
import org.platformer.entity.EntityItem;
import org.platformer.entity.EntityPlayerLocal;
import org.platformer.render.RenderEntity;
import org.platformer.render.RenderItem;
import org.platformer.render.RenderPlayer;

public class RegisterRenders
{
	private static HashMap<Class<? extends Entity>,RenderEntity> entityRenderMap = new HashMap<Class<? extends Entity>,RenderEntity>();

	public static void init()
	{
		entityRenderMap.put(EntityPlayerLocal.class, new RenderPlayer());
	}

	/**
	 * Adds an entity to the entityRenderMap
	 *
	 * @param ent entity to be added
	 */
	public static void addRenderEntity(Entity ent) {

		if(ent instanceof EntityItem) {

			EntityItem entItem = (EntityItem)ent;
			entityRenderMap.put(ent.getClass(), new RenderItem());

		}

	}

	/**
	 * Removes an entity from the entityRenderMap
	 *
	 * @param ent entity to be removed
	 */
	public static void removeRenderEntity(Entity ent)
	{

		entityRenderMap.remove(ent.getClass());

	}

	public static RenderEntity getRenderer(Class<? extends Entity> clzz)
	{
		return entityRenderMap.get(clzz);
	}
}
