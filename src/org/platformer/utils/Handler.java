package org.platformer.utils;

import org.platformer.GameInstance;
import org.platformer.world.WorldClient;
import org.platformer.entity.EntityPlayer;
import org.platformer.world.chunk.Chunk;

public class Handler {
	private static GameInstance gameInstance;
	private static WorldClient world;
	private static EntityPlayer player;
	private static Chunk[] chunks;
	
	public static GameInstance getGameInstance() {
		return gameInstance;
	}
	
	public static WorldClient getWorld() {
		return world;
	}
	
	public static EntityPlayer getPlayer() {
		return player;
	}
	
	public static Chunk[] getChunks() {
		return chunks;
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
	
	public static void setChunks(Chunk[] p_Chunks) {
		chunks = p_Chunks;
	}
}
