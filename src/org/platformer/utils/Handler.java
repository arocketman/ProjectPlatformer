package org.platformer.utils;

import org.platformer.GameInstance;
import org.platformer.world.World;

public class Handler {
	private static GameInstance gameInstance;
	private static World world;
	
	public static GameInstance getGameInstance() {
		return gameInstance;
	}
	
	public static World getWorld() {
		return world;
	}
	
	public static void setGameInstance(GameInstance p_GameInstance) {
		gameInstance = p_GameInstance;
	}
	
	public static void setWorld(World p_World) {
		world = p_World;
	}
}
