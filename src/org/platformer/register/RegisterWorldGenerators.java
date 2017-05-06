package org.platformer.register;

import java.util.HashMap;

import org.platformer.world.gen.IWorldGen;
import org.platformer.world.gen.WorldGenPlanet0;

public class RegisterWorldGenerators
{
	public enum WorldType{PLANET0}
	private static HashMap<WorldType,IWorldGen> worldGenerators = new HashMap<WorldType,IWorldGen>();
	
	public static void init()
	{
		register(WorldType.PLANET0,new WorldGenPlanet0());
	}

	private static void register(WorldType worldType, IWorldGen worldGen)
	{
		worldGenerators.put(worldType, worldGen);
	}

	public static IWorldGen get(WorldType worldType)
	{
		return worldGenerators.get(worldType);
	}
}
