package TFC.TileEntities;

import TFC.TFCBlocks;
import TFC.Chunkdata.ChunkDataManager;
import TFC.Core.TFC_Time;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;
import net.minecraft.src.TileEntity;

public class TileEntitySapling extends TileEntity
{
	public long growTime;
	
	public TileEntitySapling()
	{

	}
	
	public boolean canUpdate()
    {
        return false;
    }
	
	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);

		growTime = nbttagcompound.getLong("growTime");
	}

	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);

		nbttagcompound.setLong("growTime", growTime);
	}
}
