package org.platformer.input;

import org.platformer.Main;
import org.platformer.world.WorldClient;

public class Mouse {
	private static WorldClient world;
	private static float xPos = 0, yPos = 0;
	
	public static void setWorld(WorldClient p_World) {
		world = p_World;
	}
	
	public static void update() {
		xPos = org.lwjgl.input.Mouse.getX();
		yPos = org.lwjgl.input.Mouse.getY();
	}
	
	public static float[] getMouseInWorld() {
		int height = Main.displayHeight;
		return world.getWorldCoordinates(xPos, height-yPos);
	}
	
	public static boolean isLeftClicked() {
		return false;
	}
	
	public static boolean isRightClicked() {
		return false;
	}

}
