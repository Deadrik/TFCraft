package com.bioxx.tfc.Containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import com.bioxx.tfc.TileEntities.TEAnvil;

public class ContainerPlanSelection extends ContainerTFC
{
	private TEAnvil anvil;
	/*private World world;
	private EntityPlayer player;*/
	private String plan = "";
	public ContainerPlanSelection(EntityPlayer p, TEAnvil a, World w, int x, int y, int z)
	{
		anvil = a;
		//world = w;
		player = p;
	}

	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();
		if(anvil.craftingPlan != plan)
			plan = anvil.craftingPlan;
	}
}
