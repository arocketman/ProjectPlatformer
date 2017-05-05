package org.platformer.entity;

import java.util.HashMap;

public class EntityTracker
{
	public enum Map{ENTITIES,PLAYERS}

	private static HashMap<String,Entity> entityMap = new HashMap<String,Entity>();
	private static HashMap<String,Entity> playerMap = new HashMap<String,Entity>();

	public static void addEntityTracker(Entity ent)
	{
		entityMap.put(ent.getHash(), ent);
	}

	public static void addPlayer(Entity ent)
	{
		entityMap.put(ent.getHash(), ent);
		playerMap.put(ent.getHash(), ent);
	}

	public static void removePlayer(Entity ent)
	{
		playerMap.remove(ent.getHash());
		entityMap.remove(ent.getHash());
		ent = null;
	}

	public static HashMap<String,Entity> getEntityMap(Map mapType)
	{
		if(mapType == Map.PLAYERS)
		{
			return playerMap;
		}
		return entityMap;
	}

	public static Entity getEntityFromHash(Map maptype, String hash)
	{
		if(maptype.equals(Map.PLAYERS))
		{
			return playerMap.get(hash);
		}
		if(maptype.equals(Map.ENTITIES))
		{
			return entityMap.get(hash);
		}
		return null;
	}
}
