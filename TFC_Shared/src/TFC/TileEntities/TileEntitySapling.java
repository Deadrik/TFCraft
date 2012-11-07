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
		int meta = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
		
		float growSpeed = 1;
		if(meta == 1 || meta == 8 || meta == 11) {
			growSpeed = 1.2f;
		} else if(meta == 5 || meta == 15 || meta == 0 || meta == 13) {
			growSpeed = 1.4f;
		} else if(meta == 9 || meta == 14) {
			growSpeed = 1.6f;
		}
		
		growTime = (long) ((TFC_Time.getTotalTicks() + (TFC_Time.dayLength * 7) * growSpeed)+(worldObj.rand.nextFloat()*TFC_Time.dayLength));
	}
	
	public boolean canUpdate()
    {
        return false;
    }
	
	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);

		growTime = nbttagcompound.getLong("birthTime");
	}

	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);

		nbttagcompound.setLong("birthTime", growTime);
	}
}
