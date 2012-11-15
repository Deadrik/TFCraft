package TFC.Handlers;

import java.util.EnumSet;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ModLoader;
import net.minecraft.src.SpawnerAnimals;
import net.minecraft.src.World;
import net.minecraft.src.WorldServer;
import TFC.Chunkdata.ChunkDataManager;
import TFC.Core.TFC_Core;
import TFC.Core.TFC_ItemHeat;
import TFC.Core.TFC_Settings;
import TFC.Core.TFC_Time;
import TFC.WorldGen.SpawnerAnimalsTFC;
import TFC.WorldGen.TFCProvider;

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
				((TFCProvider)world.provider).createSpawnPosition();
				TFC_Core.SetupWorld(world);
			}
		}
		
		if(type.contains(TickType.WORLD))
		{
			WorldServer world = (WorldServer)tickData[0];
			world.setAllowedSpawnTypes(false, false);
			if (world.getGameRules().getGameRuleBooleanValue("doMobSpawning"))
	        {
				SpawnerAnimalsTFC.findChunksForSpawning(world, true, world.getWorldInfo().getWorldTime() % 400L == 0L);
	        }
			
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
