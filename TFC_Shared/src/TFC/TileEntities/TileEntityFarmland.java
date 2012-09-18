package TFC.TileEntities;

import TFC.Blocks.BlockFarmland;
import TFC.Core.CropIndex;
import TFC.Core.CropManager;
import TFC.Core.TFC_Time;
import TFC.Handlers.PacketHandler;
import TFC.Items.ItemOre;
import net.minecraft.src.*;

public class TileEntityFarmland extends TileEntity
{
    public long nutrientTimer = -1;
    public int[] nutrients = {85000,85000,85000};
    public float waterSaturation = 10;

    public TileEntityFarmland()
    {
    }

    @Override
    public void updateEntity()
    {
        if(!worldObj.isRemote)
        {
            if(nutrientTimer <= 0)
                nutrientTimer = TFC_Time.totalHours();
            
            if(nutrientTimer < TFC_Time.totalHours())
            {
                CropIndex crop = null;
                if((worldObj.getBlockId(xCoord, yCoord+1, zCoord) == Block.crops.blockID))
                {
                    crop = CropManager.getInstance().getCropFromId(((TileEntityCrop)worldObj.getBlockTileEntity(xCoord, yCoord+1, zCoord)).cropId);
                    
                    if((crop.cycleType != 0))
                    {
                        if(nutrients[0] < 120000)
                            nutrients[0] += 80 + crop.nutrientExtraRestore[0];
                    }
                    if((crop.cycleType != 1))
                    {
                        if(nutrients[1] < 120000)
                            nutrients[1] += 80 + crop.nutrientExtraRestore[1];
                    }
                    if((crop.cycleType != 2))
                    {
                        if(nutrients[2] < 120000)
                            nutrients[2] += 80 + crop.nutrientExtraRestore[2];
                    }
                }
                else
                {
                    if(nutrients[0] < 120000)
                        nutrients[0]+=120;
                    if(nutrients[1] < 120000)
                        nutrients[1]+=120;
                    if(nutrients[2] < 120000)
                        nutrients[2]+=120;
                }

                nutrientTimer+=24;
                
                if(BlockFarmland.isWaterNearby(worldObj, xCoord, yCoord, zCoord))
                {
                    waterSaturation += 1;
                    if(waterSaturation > 30)
                        waterSaturation = 30;
                }
                else if((worldObj.getBlockId(xCoord, yCoord+1, zCoord) == Block.crops.blockID) && crop != null)
                {
                    waterSaturation -= 1*crop.waterUsageMult;
                }
                
                if(worldObj.isRaining() && worldObj.canBlockSeeTheSky(xCoord, yCoord+1, zCoord))
                {
                    waterSaturation += 3;
                    if(waterSaturation > 30)
                        waterSaturation = 30;
                }
            }
        }
    }

    public void DrainNutrients(int type, float multiplier)
    {
        nutrients[type] -= 250*multiplier;
    }

    /**
     * Reads a tile entity from NBT.
     */
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);
        
        nutrients = par1NBTTagCompound.getIntArray("nutrients");
        nutrientTimer = par1NBTTagCompound.getLong("nutrientTimer");
        waterSaturation = par1NBTTagCompound.getFloat("waterSaturation");
    }

    /**
     * Writes a tile entity to NBT.
     */
    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setIntArray("nutrients", nutrients);
        par1NBTTagCompound.setLong("nutrientTimer", nutrientTimer);
        par1NBTTagCompound.setFloat("waterSaturation", waterSaturation);        
    }
}
