package net.minecraft.src.TFC_Core;

import net.minecraft.src.*;

public class TileEntityPartial extends TileEntity
{
    public int typeID;
    public int metaID;
    public int direction;
    
    public TileEntityPartial()
    {
        
    }
    
    /**
     * Reads a tile entity from NBT.
     */
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);
    }

    /**
     * Writes a tile entity to NBT.
     */
    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
    }
}
