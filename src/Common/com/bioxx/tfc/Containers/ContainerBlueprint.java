package com.bioxx.tfc.Containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class ContainerBlueprint extends ContainerTFC
{
	//private World worldObj;
	//private EntityPlayer player;

	public ContainerBlueprint(EntityPlayer p, World world, int x, int y, int z)
	{
		//this.worldObj = world;
		//this.player = p;
	}

	@Override
	public boolean canInteractWith(EntityPlayer var1)
	{
		return true;
	}
}
