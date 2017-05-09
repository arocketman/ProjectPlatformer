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
	
	public boolean isLeftClicked() {
		return org.lwjgl.input.Mouse.isButtonDown(0);
	}
	
	public boolean isRightClicked() {
		return org.lwjgl.input.Mouse.isButtonDown(1);
	}
	
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
		consoleOutput();
	}
	
	private void consoleOutput() {
		//System.out.printf("X-Coord: %s\nY-Coord: %s\n\n", relativeCoordinates[0], relativeCoordinates[1]);
	}

	/**
	 * Gets the x coordinate relative to the world
	 * @return
     */
	public float getRelativeX(){
		return relativeCoordinates[0];
	}

	/**
	 * Gets the y coordinate relative to the world
	 * @return
     */
	public float getRelativeY(){
		return relativeCoordinates[1];
	}
}
