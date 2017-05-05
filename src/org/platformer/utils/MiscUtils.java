package org.platformer.utils;


public class MiscUtils
{
	public static float pointTowardsPosition(float x, float y, float x2, float y2)
	{
		float deltaX = x - x2;
		float deltaY = y - y2;

		return (float) (360 + Math.toDegrees(Math.atan2(deltaY, deltaX))) % 360;
	}

	public static float incrementSmoothly(float target, float current, float speed, float buffer)
	{
		float dif = Math.abs(current-target);
		
		if(current < target)
		{
			current+=(dif/2f)*speed;
		}
		if(current > target)
		{
			current-=(dif/2f)*speed;
		}
		
		if(dif <= buffer)
		{
			current = target;
		}

		return current;
	}

	public static float incrementWrap360(float targetAngle, float currentAngle, float speed)
	{
		float wdir = targetAngle;
		float turnspeed = speed;
		if (Math.abs(wdir-currentAngle) > 180) {
			if (wdir > 180) {
				float tempdir = wdir - 360;
				if (Math.abs(tempdir-currentAngle) > turnspeed) {
					currentAngle -= turnspeed;
				} else {
					currentAngle = wdir;
				}
			} else {
				float tempdir = wdir + 360;
				if (Math.abs(tempdir-currentAngle) > turnspeed) {
					currentAngle += turnspeed;
				} else {
					currentAngle = wdir;
				}
			}
		} else {
			if (Math.abs(wdir - currentAngle) > turnspeed) {
				if (wdir > currentAngle) {
					currentAngle += turnspeed;
				} else {
					currentAngle -= turnspeed;
				}
			} else {
				currentAngle = wdir;
			}
		}
		return currentAngle;
	}
	
	public static float angleDifference(float angle1, float angle2)
	{
		float phi = Math.abs(angle2 - angle1) % 360;
		float distance = phi > 180 ? 360 - phi : phi;
        return distance;
    }

	public static float distanceBetween(float x1, float y1, float x2, float y2)
	{
		return (float) Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
	}
}
