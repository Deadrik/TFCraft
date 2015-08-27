package com.bioxx.tfc.api.Interfaces;

import java.util.List;

import net.minecraft.world.World;

public interface ICustomCollision
{
	void addCollisionBoxesToList(World world, int i, int j, int k, List boxlist);
}
