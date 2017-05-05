package org.platformer.render;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Graphics;
import org.platformer.Main;
import org.platformer.register.RegisterKeybinds;
import org.platformer.utils.MiscUtils;

public class Camera
{
	public float translateX;
	public float translateY;
	public float scale = 1f;
	private float maxScale = 6f;
	private float minScale = 0.5f;
	public boolean canZoom = true;
	public boolean canMove = true;
	public boolean smoothMovement = false;
	public float scaleSmooth;
	public float translateXSmooth;
	public float translateYSmooth;
	
	public Camera(float maxScale, float minScale)
	{
		this.maxScale = maxScale;
		this.minScale = minScale;
	}
	
	public void transform(Graphics g)
	{
		if(smoothMovement)
		{
			scaleSmooth = MiscUtils.incrementSmoothly(scale, scaleSmooth, 0.01f, 1f);
			translateXSmooth = MiscUtils.incrementSmoothly(translateX, translateXSmooth, 0.1f, 1f);
			translateYSmooth = MiscUtils.incrementSmoothly(translateY, translateYSmooth, 0.1f, 1f);
		}
		else
		{
			scaleSmooth = scale;
			translateXSmooth = translateX;
			translateYSmooth = translateY;
		}
		
		if(canMove)
		{
			if(RegisterKeybinds.move_up.isPressed())
			{
				translateY-=15*(1F/scale);
			}
			
			if(RegisterKeybinds.move_down.isPressed())
			{
				translateY+=15*(1F/scale);
			}
			
			if(RegisterKeybinds.move_left.isPressed())
			{
				translateX-=15*(1F/scale);
			}
			
			if(RegisterKeybinds.move_right.isPressed())
			{
				translateX+=15*(1F/scale);
			}
		
		}
		if(canZoom)
		{
			int dwheel = Mouse.getDWheel();
			if(dwheel > 0)
			{
				scale+=0.01f;
				scale*=1.1f;
			}
			if(dwheel < 0)
			{
				scale-=0.01f;
				scale*=0.9f;
			}
		}
		int halfWidth = Main.displayWidth/2;
		int halfHeight = Main.displayHeight/2;
		
		scale = Math.max(Math.min(scale, maxScale), minScale);

		/** Translate the camera to the middle, scale it from the middle, and translate it back*/
		g.translate(halfWidth, halfHeight);
		g.scale(scaleSmooth, scaleSmooth);
		g.translate(-halfWidth, -halfHeight);
		
		/** Translate the camera position to 'move' the world*/
		g.translate(-translateXSmooth, -translateYSmooth);
	}
}
