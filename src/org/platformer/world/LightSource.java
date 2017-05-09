package org.platformer.world;

public class LightSource
{
	public int posX = 0;
	public int posY = 0;
	public float color_r = 1f;
	public float color_g = 1f;
	public float color_b = 1f;
	public float lightLevel = 1f;
	public float range = 1f;
	
	public LightSource(int x, int y, float r, float g, float b, float level, float range)
	{
		posX = x;
		posY = y;
		color_r = r;
		color_g = g;
		color_b = b;
		lightLevel = level;
		this.range = range;
	}
}
