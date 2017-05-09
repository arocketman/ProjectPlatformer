package org.platformer.entity;

import java.util.HashMap;

public class EntityTracker
{
	public enum Map{ENTITIES,PLAYERS}

	private static HashMap<String,Entity> entityMap = new HashMap<String,Entity>();
	private static HashMap<String,Entity> playerMap = new HashMap<String,Entity>();

	/**
	 * Adds entity to entity tracker map
	 * @param ent - Entity to track
	 */
	public static void addEntityTracker(Entity ent)
	{

		entityMap.put(ent.getHash(), ent);

	}

	/**
	 * Adds entity(Player) to entity and player tracker map
	 * @param ent - Entity(Player) to track
	 */
	public static void addPlayer(Entity ent)
	{
		entityMap.put(ent.getHash(), ent);
		playerMap.put(ent.getHash(), ent);
	}

	/**
	 * Remove entity(Player) from entity and player tracker map
	 * @param ent
	 */
	public static void removePlayer(Entity ent)
	{
		playerMap.remove(ent.getHash());
		entityMap.remove(ent.getHash());
		ent = null;
	}

	/**
	 * Remove entity from entity tracker map
	 * @param ent
	 */
	public static void removeEntity(Entity ent)
	{
		if(playerMap.containsKey(ent.getHash()))
		{
			playerMap.remove(ent.getHash());
		}
		entityMap.remove(ent.getHash());
		ent = null;
	}

	/**
	 * Get an EntityTracker hashmap
	 * @param mapType - Map Type (ENTITIES or PLAYERS)
	 * @return
	 */
	public static HashMap<String,Entity> getEntityMap(Map mapType)
	{
		if(mapType == Map.PLAYERS)
		{
			return playerMap;
		}
		return entityMap;
	}

	/**
	 * Get an entity by its mapped hash
	 * @param maptype - Map Type (ENTITIES or PLAYERS)
	 * @param hash - Entities Trackable Hash
	 * @return
	 */
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
