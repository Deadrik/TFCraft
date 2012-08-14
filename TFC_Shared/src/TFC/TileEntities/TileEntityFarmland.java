package TFC.TileEntities;

import TFC.Blocks.BlockTerraFarmland;
import TFC.Core.CropIndex;
import TFC.Core.CropManager;
import TFC.Core.PacketHandler;
import TFC.Core.TFCSeasons;
import TFC.Items.ItemTerraOre;
import net.minecraft.src.*;

public class TileEntityFarmland extends TileEntity
{
    public long nutrientTimer = -1;
    public int[] nutrients = {8500,8500,8500};
    public byte waterSaturation = 1;

    public TileEntityFarmland()
    {
        nutrients = new int[]{8500,8500,8500};
    }

    @Override
    public void updateEntity()
    {
        if(!worldObj.isRemote)
        {
            if(nutrientTimer <= 0)
                nutrientTimer = TFCSeasons.totalHours();
            
            if(nutrientTimer < TFCSeasons.totalHours())
            {
                CropIndex crop = null;
                if((worldObj.getBlockId(xCoord, yCoord+1, zCoord) == Block.crops.blockID))
                {
                    crop = CropManager.getInstance().getCropFromId(((TileEntityCrop)worldObj.getBlockTileEntity(xCoord, yCoord+1, zCoord)).cropId);
                }
                if(worldObj.getBlockId(xCoord, yCoord+1, zCoord) == 0 || (crop != null && crop.cycleType != 0))
                {
                    if(nutrients[0] < 12000)
                        nutrients[0]+=8;
                }
                if(worldObj.getBlockId(xCoord, yCoord+1, zCoord) == 0 || (crop != null && crop.cycleType != 1))
                {
                    if(nutrients[1] < 12000)
                        nutrients[1]+=8;
                }
                if(worldObj.getBlockId(xCoord, yCoord+1, zCoord) == 0 || (crop != null && crop.cycleType != 2))
                {
                    if(nutrients[2] < 12000)
                        nutrients[2]+=8;
                }

                nutrientTimer+=24;
                
                if(BlockTerraFarmland.isWaterNearby(worldObj, xCoord, yCoord, zCoord))
                {
                    waterSaturation += 1;
                    if(waterSaturation > 7)
                        waterSaturation = 7;
                }
                else
                {
                    waterSaturation--;
                }
                
                if(worldObj.isRaining() && worldObj.canBlockSeeTheSky(xCoord, yCoord+1, zCoord))
                {
                    waterSaturation += 1;
                    if(waterSaturation > 7)
                        waterSaturation = 7;
                }
            }
        }
    }

    public void DrainNutrients(int type, float multiplier)
    {
        nutrients[type] -= 10*multiplier;
    }

    /**
     * Reads a tile entity from NBT.
     */
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);
        
        nutrients = par1NBTTagCompound.getIntArray("nutrients");
        nutrientTimer = par1NBTTagCompound.getLong("nutrientTimer");
        waterSaturation = par1NBTTagCompound.getByte("waterSaturation");
    }

    /**
     * Writes a tile entity to NBT.
     */
    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setIntArray("nutrients", nutrients);
        par1NBTTagCompound.setLong("nutrientTimer", nutrientTimer);
        par1NBTTagCompound.setByte("waterSaturation", waterSaturation);        
    }
}
