package TFC.Handlers.Client;

import java.util.EnumSet;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import TFC.Core.TFC_Time;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class ClientTickHandler implements ITickHandler
{

	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) 
	{

	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) 
	{
		if(type.contains(TickType.PLAYER))
		{
			EntityPlayer player = (EntityPlayer)tickData[0];
			World world = player.worldObj;

			//Allow the client to increment time
			TFC_Time.UpdateTime(world);
		}
	}

	@Override
	public EnumSet<TickType> ticks() 
	{
		return EnumSet.of(TickType.PLAYER, TickType.RENDER);
	}

	@Override
	public String getLabel()
	{
		return "TFC Client";
	}

}
