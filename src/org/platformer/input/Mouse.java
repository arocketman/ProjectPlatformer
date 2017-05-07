package org.platformer.input;

public class Mouse {
	/** index 0 = x-position; index 1 = y-position */
	private static float[] position = new float[2];
	
	public static float[] getMouseLocation() {
		position[0] = org.lwjgl.input.Mouse.getX();
		position[1] = org.lwjgl.input.Mouse.getY();
		
		return position;
	}
	
	public static boolean isLeftClicked() {
		return org.lwjgl.input.Mouse.isButtonDown(0);
	}
	
	public static boolean isRightClicked() {
		return org.lwjgl.input.Mouse.isButtonDown(1);
	}
}
