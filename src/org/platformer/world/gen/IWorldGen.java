package org.platformer.world.gen;

import java.util.ArrayList;

import org.platformer.world.World;

public interface IWorldGen
{
	public void generate(World world);
	public void getLayerData(ArrayList<Layer> list);
}
