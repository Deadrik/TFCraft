package TFC.Handlers;

import java.util.EnumSet;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.World;
import TFC.Core.TFC_Core;
import TFC.Core.TFC_ItemHeat;
import TFC.Core.TFC_Time;
import TFC.WorldGen.TFCProvider;

import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class ServerTickHandler implements ITickHandler
{

	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) 
	{
		if(type.contains(TickType.WORLDLOAD))
		{
			World world = (World)tickData[0];
			if(world.provider.worldType == 0)
			{
				((TFCProvider)world.provider).createSpawnPosition();
				TFC_Core.SetupWorld(world);
			}
		}

		if (type.contains(TickType.PLAYER))
		{
			World world;
			world = ((EntityPlayer)tickData[0]).worldObj;

			//Allow the server to increment time
			TFC_Time.UpdateTime(world);
			TFC_ItemHeat.HandleContainerHeat(world, ((EntityPlayer)tickData[0]).inventory.mainInventory, 
					(int)((EntityPlayer)tickData[0]).posX, (int)((EntityPlayer)tickData[0]).posY, (int)((EntityPlayer)tickData[0]).posZ);

		}
	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {
		// TODO Auto-generated method stub

	}

	@Override
	public EnumSet<TickType> ticks() {
		// TODO Auto-generated method stub
		return EnumSet.of(TickType.WORLD, TickType.WORLDLOAD, TickType.PLAYER);
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return "TFC Server";
	}

}
