package org.platformer.animation;

import java.util.ArrayList;
import java.util.HashMap;

import org.newdawn.slick.Graphics;
import org.platformer.entity.Entity;

public class AnimationEntity
{
	public Entity entity;
	private HashMap<String,Animation[]> animations = new HashMap<String,Animation[]>();
	
	public AnimationEntity(Entity entity)
	{
		this.entity = entity;
	}
	
	public void addAnimation(String name, Animation[] animation)
	{
		for(Animation anim : animation)
		{
			anim.entity = entity;
		}
		animations.put(name, animation);
	}

	public void update()
	{
		int animid = entity.currentAnimation;
		
		ArrayList<Animation[]> anims = new ArrayList<Animation[]>();
		anims.addAll(animations.values());
		if(animid < anims.size())
		{
			for(Animation anim2 : anims.get(animid))
			{
				anim2.update();
			}
		}
	}
	
	public void render(Graphics g)
	{
		int animid = entity.currentAnimation;
		
		ArrayList<Animation[]> anims = new ArrayList<Animation[]>();
		anims.addAll(animations.values());
		if(animid < anims.size())
		{
			for(Animation anim2 : anims.get(animid))
			{
				anim2.render(g);
			}
		}
	}

	public void remapIDToExisting(String name, int i)
	{
		Animation[] value = animations.get(name);
		animations.put(name+"_"+i, value);
	}
}
