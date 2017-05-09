package org.platformer.input;

import org.platformer.Main;
import org.platformer.world.WorldClient;

public class Mouse {
	/** 
	 * The coordinates of the mouse relative to the screen <br>
	 * index 0 : x-coordinate <br>
	 * index 1 : y-coordinate
	 * */
	private int[] actualCoordinates = new int[2];
	/** 
	 * The coordinates relative to the world <br>
	 * index 0 : x-coordinate <br>
	 * index 1 : y-coordinate
	 * */
	private float[] relativeCoordinates = new float[2];
	
	public int[] getActualCoordinates() {
		return actualCoordinates;
	}
	
	public float[] getRelativeLocation() {
		return relativeCoordinates;
	}
	
	
	
	public void update() {
		actualCoordinates[0] = org.lwjgl.input.Mouse.getX();
		actualCoordinates[1] = org.lwjgl.input.Mouse.getY();
		relativeCoordinates = WorldClient.getWorld().getWorldCoordinates(actualCoordinates[0], Main.displayHeight - actualCoordinates[1]);
	}
}
