package org.platformer.animation.anims;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.platformer.animation.Animation;
import org.platformer.animation.Line;
import org.platformer.entity.EntityPlayer;
import org.platformer.register.RegisterTextures;
import org.platformer.utils.Log;
import org.platformer.utils.MiscUtils;
import org.platformer.utils.other.Vec2;

public class AnimPlayerArms extends Animation
{
	private int tickTimer = 0;
	public Line[] lines = new Line[6];
	
	public AnimPlayerArms()
	{
		lines[0] = new Line(RegisterTextures.getTexture("player_leftshoulder"),new Vec2(0,0), new Vec2(0,12));
		lines[1] = new Line(RegisterTextures.getTexture("player_leftarm"),new Vec2(0,0), new Vec2(0,12));
		lines[2] = new Line(RegisterTextures.getTexture("player_torso"),new Vec2(0,0), new Vec2(0,-20));
		lines[3] = new Line(RegisterTextures.getTexture("player_head"),new Vec2(0,0), new Vec2(6,8));
		lines[4] = new Line(RegisterTextures.getTexture("player_rightshoulder"),new Vec2(0,0), new Vec2(0,12));
		lines[5] = new Line(RegisterTextures.getTexture("player_rightarm"),new Vec2(0,0), new Vec2(0,12));
	}

	@Override
	public void update()
	{
		if(tickTimer < 100){tickTimer+=2;}else{tickTimer = 0;}
		if(entity.moveSpeed == 0)
		{
			if(tickTimer < 5){tickTimer++;}else{tickTimer = 0;}
		}
		
		boolean flipped = entity.walkingLeft;
		boolean handsMouseControl = (entity.currentAnimation == 1);
		
		float walkSpeed = (Math.abs(entity.moveSpeed)+1)/7f;

		Line shoulderLeft = lines[0];
		Line armLeft = lines[1];
		Line body = lines[2];
		Line head = lines[3];
		Line shoulderRight = lines[4];
		Line armRight = lines[5];
		
		float rotationAmount = 0f;
		float rotationOffset = 0f;
		
		rotationAmount = 3f*walkSpeed;
		rotationOffset = 4f;
		body.angle = getAngle(0,rotationAmount, rotationOffset, flipped);
		body.calculateEnd();
		body.transitionAnimation();
		
		rotationAmount = 1;
		rotationOffset = 205f;
		if(!flipped)
		{
			head.angle = (25)+rotationOffset;
		}else{
			head.angle = rotationOffset;
		}
		
		EntityPlayer player = (EntityPlayer) entity;
		
		if(entity.moveSpeed == 0)
		{
			head.angle = player.mouseAngle+rotationOffset;
			
			if(player.mouseAngle > 90f && player.mouseAngle < 270f && entity.walkingLeft)
			{
				entity.walkingLeft = false;
			}
			
			if((player.mouseAngle < 90f || (player.mouseAngle < 360f && player.mouseAngle > 270f)) && !entity.walkingLeft)
			{
				entity.walkingLeft = true;
			}
			if(!flipped)
			{
				head.angle = (180+25+player.mouseAngle)+rotationOffset;
			}else{
				head.angle = player.mouseAngle+rotationOffset;
			}
		}
		head.start.x = body.visEnd.x-(head.end.x/2);
		head.start.y = body.visEnd.y;
		head.calculateEnd();
		head.transitionAnimation();
		
		rotationAmount = 50f*walkSpeed;
		rotationOffset = 5f;
		shoulderLeft.angle = getAngle(60,rotationAmount, rotationOffset, flipped);
		shoulderLeft.start.x = body.visEnd.x;
		shoulderLeft.start.y = body.visEnd.y;
		shoulderLeft.calculateEnd();
		shoulderLeft.transitionAnimation();
		
		rotationAmount = 80f*walkSpeed;
		rotationOffset = -15f;
		armLeft.angle = getAngle(60,rotationAmount, rotationOffset, flipped);
		armLeft.start.x = shoulderLeft.visEnd.x;
		armLeft.start.y = shoulderLeft.visEnd.y;
		armLeft.calculateEnd();
		armLeft.transitionAnimation();
		
		rotationAmount = 50f*walkSpeed;
		rotationOffset = 5f;
		if(handsMouseControl)
		{
			shoulderRight.angle = 90f+player.mouseAngle;
			if(entity.moveSpeed == 0)
			{
				if(shoulderRight.angle > 180f && shoulderRight.angle < 360f && entity.walkingLeft)
				{
					entity.walkingLeft = false;
				}
				
				if(shoulderRight.angle < 180f && !entity.walkingLeft)
				{
					entity.walkingLeft = true;
				}
			}
		}
		else
		{
			shoulderRight.angle = getAngle(10,rotationAmount, rotationOffset, flipped);
		}
		
		shoulderRight.start.x = body.visEnd.x;
		shoulderRight.start.y = body.visEnd.y;
		shoulderRight.calculateEnd();
		shoulderRight.transitionAnimation();
		
		rotationAmount = 80f*walkSpeed;
		rotationOffset = -25f;
		if(handsMouseControl)
		{
			armRight.angle = 90f+player.mouseAngle;
			armRight.visEnd.x = armRight.endLoc.x;
			armRight.visEnd.y = armRight.endLoc.y;
		}
		else
		{
			armRight.angle = getAngle(10,rotationAmount, rotationOffset, flipped);
		}
		armRight.start.x = shoulderRight.visEnd.x;
		armRight.start.y = shoulderRight.visEnd.y;
		armRight.calculateEnd();
		armRight.transitionAnimation();
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
		boolean flipped = entity.walkingLeft;
		
		for(int i=0;i<lines.length;i++)
		{
			Line line = lines[i];
			
			if(flipped){line = lines[(lines.length-1)-i];};
			
			if(line != null)
			{
				Image img = line.image;
				if(flipped){img = line.image.getFlippedCopy(true, false);}
				if(line == lines[3]){img = line.image.getFlippedCopy(flipped, true);}
				
				img.setCenterOfRotation(((line.end.x+4)/2),0);
				float angle2 = MiscUtils.pointTowardsPosition(line.start.x, line.start.y, line.visEnd.x, line.visEnd.y)+90f;
				if(line == lines[2]){angle2+=180f;}
				img.setRotation(angle2);
				img.draw(line.visStart.x, line.visStart.y, 4+line.end.x, line.end.y+1f);
				img.setRotation(0);
			}
		}
	}

}
