package org.platformer.input;

import org.platformer.utils.Handler;

public class Mouse {
	/** index 0 = x-position; index 1 = y-position */
	private static float[] position = new float[2];
	
	public static float[] getMouseLocation() {
		position[0] = org.lwjgl.input.Mouse.getX();
		position[1] = org.lwjgl.input.Mouse.getY();
		
		position = Handler.getWorld().getWorldCoordinates(position[0], position[1]);
		
		return position;
	}
	
	public static boolean isLeftClicked() {
		return org.lwjgl.input.Mouse.isButtonDown(0);
	}
	
	public static boolean isRightClicked() {
		return org.lwjgl.input.Mouse.isButtonDown(1);
	}
	
	public static void dummyClass() {
		System.out.println(getMouseLocation()[0]);
	}
}
