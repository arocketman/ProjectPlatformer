package org.platformer.animation.anims;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.platformer.animation.Animation;
import org.platformer.animation.Line;
import org.platformer.register.RegisterTextures;
import org.platformer.utils.MiscUtils;
import org.platformer.utils.other.Vec2;

public class AnimPlayerLegs extends Animation
{
	private int tickTimer = 0;
	private float lowestY = 0;
	public Line[] lines = new Line[4];
	
	public AnimPlayerLegs()
	{
		lines[0] = new Line(RegisterTextures.getTexture("player_leftthigh"),new Vec2(0,0), new Vec2(0,16));
		lines[1] = new Line(RegisterTextures.getTexture("player_leftleg"),new Vec2(0,0), new Vec2(0,16));
		lines[2] = new Line(RegisterTextures.getTexture("player_rightthigh"),new Vec2(0,0), new Vec2(0,16));
		lines[3] = new Line(RegisterTextures.getTexture("player_rightleg"),new Vec2(0,0), new Vec2(0,16));
	}

	@Override
	public void update()
	{
		if(tickTimer < 100){tickTimer+=2;}else{tickTimer = 0;}
		if(entity.moveSpeed == 0)
		{
			tickTimer = 10;
		}
		
		boolean flipped = entity.walkingLeft;
		
		float walkSpeed = (Math.abs(entity.moveSpeed)+1)/7f;

		Line legLeft = lines[0];
		Line kneeLeft = lines[1];
		Line legRight = lines[2];
		Line kneeRight = lines[3];
		
		float rotationAmount = 50f*walkSpeed;
		float rotationOffset = -15f;
		legLeft.angle = getAngle(20, rotationAmount, rotationOffset, flipped);
		legLeft.calculateEnd();
		legLeft.transitionAnimation();
		
		rotationAmount = 50f*walkSpeed;
		rotationOffset = -15f;
		legRight.angle = getAngle(70, rotationAmount, rotationOffset, flipped);
		legRight.calculateEnd();
		legRight.transitionAnimation();
		
		rotationAmount = 80f*walkSpeed;
		rotationOffset = 20f;
		kneeLeft.angle = getAngle(10,rotationAmount, rotationOffset, flipped);
		kneeLeft.start.x = legLeft.visEnd.x;
		kneeLeft.start.y = legLeft.visEnd.y;
		kneeLeft.calculateEnd();
		kneeLeft.transitionAnimation();
		
		rotationAmount = 80f*walkSpeed;
		rotationOffset = 30f;
		kneeRight.angle = getAngle(60,rotationAmount, rotationOffset, flipped);
		kneeRight.start.x = legRight.visEnd.x;
		kneeRight.start.y = legRight.visEnd.y;
		kneeRight.calculateEnd();
		kneeRight.transitionAnimation();
	}

	private float getAngle(int tickOffset, float rotationAmount, float rotationOffset, boolean flipped)
	{
		int tt = tickTimer+tickOffset;
		if(tt > 100){tt -=100;}
		if(flipped)
		{
			tt = 100 - tt;
			return (-rotationOffset)+(float)Math.sin(((float)Math.toRadians(((tt)/10f)*36)))*(rotationAmount);
		}
		return rotationOffset+(float)Math.sin(((float)Math.toRadians(((tt)/10f)*36)))*rotationAmount;
	}

	@Override
	public void render(Graphics g)
	{
		lowestY = 0f;
		for(Line line : lines)
		{
			if(line.visEnd.y > lowestY)
			{
				lowestY = line.visEnd.y;
			}
		}
		float yDif = Math.abs(29.93f-lowestY)/2f;
		g.translate(0f, -yDif);
		
		boolean flipped = entity.walkingLeft;
		
		for(int i=0;i<lines.length;i++)
		{
			Line line = lines[i];
			
			if(flipped){line = lines[(lines.length-1)-i];};
			
			if(line != null)
			{
				Image img = line.image;
				if(flipped){img = line.image.getFlippedCopy(true, false);}
				
				img.setCenterOfRotation(2,0);
				float angle2 = MiscUtils.pointTowardsPosition(line.start.x, line.start.y, line.visEnd.x, line.visEnd.y)+90f;
				img.setRotation(angle2);
				img.draw(line.visStart.x, line.visStart.y, 4, line.end.y+2f);
				img.setRotation(0);
			}
		}
	}

}
