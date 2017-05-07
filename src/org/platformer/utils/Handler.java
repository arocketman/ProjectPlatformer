package org.platformer.utils;

import org.platformer.GameInstance;
import org.platformer.world.WorldClient;
import org.platformer.entity.EntityPlayer;

public class Handler {
	private static GameInstance gameInstance;
	private static WorldClient world;
	private static EntityPlayer player;
	
	public static GameInstance getGameInstance() {
		return gameInstance;
	}
	
	public static WorldClient getWorld() {
		return world;
	}
	
	public static EntityPlayer getPlayer() {
		return player;
	}
	
	public static void setGameInstance(GameInstance p_GameInstance) {
		gameInstance = p_GameInstance;
	}
	
	public static void setWorld(WorldClient p_World) {
		world = p_World;
	}
	
	public static void setPlayer(EntityPlayer p_Player) {
		player = p_Player;
	}
}
