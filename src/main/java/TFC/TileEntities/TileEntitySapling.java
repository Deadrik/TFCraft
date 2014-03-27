package TFC.TileEntities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

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
