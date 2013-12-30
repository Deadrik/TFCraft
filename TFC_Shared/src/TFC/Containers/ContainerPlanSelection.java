package TFC.Containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import TFC.TileEntities.TileEntityAnvil;

public class ContainerPlanSelection extends ContainerTFC
{
	TileEntityAnvil anvil;
	World world;
	EntityPlayer player;
	String plan = "";
	public ContainerPlanSelection(EntityPlayer p, TileEntityAnvil a, World w, int x, int y, int z)
	{
		anvil = a;
		world = w;
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
