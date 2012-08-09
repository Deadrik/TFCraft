package TFC.TileEntities;

import TFC.Core.PacketHandler;
import TFC.Items.ItemTerraOre;
import net.minecraft.src.*;

public class TileEntityFarmland extends TileEntityPartial
{
    private int waterCheckTimer = 400;
    public TileEntityFarmland()
    {
        
    }
    
    @Override
    public void updateEntity()
    {
        waterCheckTimer++;
        if(waterCheckTimer >= 400)
        {
            waterCheckTimer = 0;
            long water = extraData & 0x1;
//            if(water == 0 && TFC.Blocks.BlockFarmland.isWaterNearby(worldObj,xCoord,yCoord,zCoord))
//            {
//                extraData = extraData + 1;
//                this.broadcast();
//            }
//            else if(water == 1 && !TFC.Blocks.BlockFarmland.isWaterNearby(worldObj,xCoord,yCoord,zCoord))
//            {
//                extraData = extraData - 1;
//                this.broadcast();
//            }
        }
    }
    
    @Override
    public boolean canUpdate()
    {
        return true;
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
