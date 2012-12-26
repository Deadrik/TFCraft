package TFC.Handlers;

import TFC.Chunkdata.ChunkData;
import TFC.Chunkdata.ChunkDataManager;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.crash.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.packet.*;
import net.minecraft.pathfinding.*;
import net.minecraft.potion.*;
import net.minecraft.server.*;
import net.minecraft.stats.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.village.*;
import net.minecraft.world.*;
import net.minecraft.world.biome.*;
import net.minecraft.world.chunk.*;
import net.minecraft.world.gen.feature.*;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.world.ChunkDataEvent;

public class ChunkDataEventHandler 
{
	@ForgeSubscribe
	public void onDataLoad(ChunkDataEvent.Load event) 
	{
		NBTTagCompound eventTag = event.getData();

		if(eventTag.hasKey("Spawn Protection"))
		{
			NBTTagCompound spawnProtectionTag = eventTag.getCompoundTag("Spawn Protection");
			ChunkData data = new ChunkData(spawnProtectionTag);
			String key = data.chunkX + "," + data.chunkZ;
			ChunkDataManager.chunkmap.put(key, data);
		}
		else
		{
			NBTTagCompound levelTag = eventTag.getCompoundTag("Level");
			ChunkData data = new ChunkData().CreateNew(levelTag.getInteger("xPos"), levelTag.getInteger("zPos"));
			String key = data.chunkX + "," + data.chunkZ;
			ChunkDataManager.chunkmap.put(key, data);
		}
	}

	@ForgeSubscribe
	public void onDataSave(ChunkDataEvent.Save event) 
	{
		NBTTagCompound eventTag = event.getData();
		NBTTagCompound levelTag = eventTag.getCompoundTag("Level");

		int x = levelTag.getInteger("xPos");
		int z = levelTag.getInteger("zPos");
		String key = x + "," + z;
		if(ChunkDataManager.chunkmap.containsKey(key))
		{
			NBTTagCompound spawnProtectionTag = ((ChunkData)ChunkDataManager.chunkmap.get(key)).getTag();
			if(spawnProtectionTag != null)
			{
				eventTag.setCompoundTag("Spawn Protection", spawnProtectionTag);
			}
		}

	}
}
