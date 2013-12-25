package TFC.Handlers;

import java.util.EnumSet;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import TFC.Core.TFC_Core;
import TFC.Core.TFC_Time;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class ServerTickHandler implements ITickHandler
{
	private int chunkPruneTimer = 0;
	
	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) 
	{
		if(type.contains(TickType.WORLDLOAD))
		{
			World world = (World)tickData[0];
			if(world.provider.dimensionId == 0)
			{
				TFC_Core.SetupWorld(world);
			}
		}
		
		if(type.contains(TickType.WORLD))
		{
			WorldServer world = (WorldServer)tickData[0];
			//Allow the server to increment time
			TFC_Time.UpdateTime(world);
		}

		if (type.contains(TickType.PLAYER))
		{
			World world;
			EntityPlayer player = (EntityPlayer)tickData[0];
			world = player.worldObj;
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
