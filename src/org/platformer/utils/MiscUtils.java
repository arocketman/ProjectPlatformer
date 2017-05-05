package org.platformer.utils;


public class MiscUtils
{
	
	/**
	 * Returns the angle from Point A to Point B.
	 * @param x - Point A
	 * @param y - Point A
	 * @param x2 - Point B
	 * @param y2 - Point B
	 * @return Angle
	 */
	public static float pointTowardsPosition(float x, float y, float x2, float y2)
	{
		float deltaX = x - x2;
		float deltaY = y - y2;
		return (float) (360 + Math.toDegrees(Math.atan2(deltaY, deltaX))) % 360;
	}

	/**
	 * Increments a number towards another number smoothly, with a buffer to avoid 'seeking'
	 * @param target - The target value
	 * @param current - Current value
	 * @param speed - Speed to increment at
	 * @param buffer - Stop incrementing if the difference between target and current is within the buffer value
	 * @return New Incremented value
	 */
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

	/**
	 * Used to rotate towards an angle in the least distance, also fixes 360 wrap around (eg. Going from 340 to 30)
	 * @param targetAngle
	 * @param currentAngle
	 * @param speed - Speed to turn at
	 * @return New Angle
	 */
	public static float rotateTowards(float targetAngle, float currentAngle, float speed)
	{
		float wdir = targetAngle;
		float turnspeed = speed;
		if(Math.abs(wdir-currentAngle) > 180)
		{
			if(wdir > 180)
			{
				float tempdir = wdir - 360;
				if(Math.abs(tempdir-currentAngle) > turnspeed)
				{
					currentAngle -= turnspeed;
				}
				else
				{
					currentAngle = wdir;
				}
			}
			else
			{
				float tempdir = wdir + 360;
				if(Math.abs(tempdir-currentAngle) > turnspeed)
				{
					currentAngle += turnspeed;
				}
				else
				{
					currentAngle = wdir;
				}
			}
		}
		else
		{
			if(Math.abs(wdir - currentAngle) > turnspeed)
			{
				if(wdir > currentAngle)
				{
					currentAngle += turnspeed;
				}
				else
				{
					currentAngle -= turnspeed;
				}
			}
			else
			{
				currentAngle = wdir;
			}
		}
		return currentAngle;
	}
	
	/**
	 * Get the angle difference between 2 angles, while keeping it in the 0-360 range.
	 * @param angle1
	 * @param angle2
	 * @return Angle difference
	 */
	public static float angleDifference(float angle1, float angle2)
	{
		float phi = Math.abs(angle2 - angle1) % 360;
		float distance = phi > 180 ? 360 - phi : phi;
        return distance;
    }

	/**
	 * Get the distance between two points.
	 * @param x1 - Point A
	 * @param y1 - Point A
	 * @param x2 - Point B
	 * @param y2 - Point B
	 * @return Distance
	 */
	public static float distanceBetween(float x1, float y1, float x2, float y2)
	{
		return (float) Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
	}
}
