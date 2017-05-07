package org.platformer.animation;

import org.newdawn.slick.Image;
import org.platformer.utils.other.Vec2;

public class Line
{
	public float length;
	public float angle;
	public Vec2 start;
	public Vec2 end;
	public Vec2 endLoc;

	public Vec2 visStart;
	public Vec2 visEnd;
	
	public Image image;

	public Line(Image img, Vec2 start, Vec2 end)
	{
		this.image = img;
		this.start = start;
		this.end = end;
		this.endLoc = new Vec2();
		this.visStart = new Vec2(start);
		this.visEnd = new Vec2(end);
	}

	public void calculateEnd()
	{
		float ang = (float) Math.toRadians(angle);

		endLoc.x = (float) (start.x + (end.x * Math.cos(ang) - end.y * Math.sin(ang)));
		endLoc.y = (float) (start.y + (end.x * Math.sin(ang) + end.y * Math.cos(ang)));
	}

	public void transitionAnimation()
	{
		visStart.x = start.x;
		visStart.y = start.y;

		float sDifX = Math.abs(visEnd.x-endLoc.x);
		float sDifY = Math.abs(visEnd.y-endLoc.y);
		
		if(visEnd.x < endLoc.x)
		{
			visEnd.x+=(sDifX/10f);
		}
		if(visEnd.x > endLoc.x)
		{
			visEnd.x-=(sDifX/10f);
		}
		if(visEnd.y < endLoc.y)
		{
			visEnd.y+=(sDifY/10f);
		}
		if(visEnd.y > endLoc.y)
		{
			visEnd.y-=(sDifY/10f);
		}
	}
}
