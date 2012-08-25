package TFC.TileEntities;

import java.util.Random;

import TFC.Core.CropIndex;
import TFC.Core.CropManager;
import TFC.Core.PacketHandler;
import TFC.Core.TFCSeasons;
import TFC.Items.ItemTerraOre;
import TFC.WorldGen.TFCBiome;
import net.minecraft.src.*;

public class TileEntityCrop extends TileEntity
{
    public float growth;
    public int cropId;
    private long growthTimer;//Tracks the time since the plant was planted
    private byte sunLevel;

    public TileEntityCrop()
    {
        growth = 0.1f;
        growthTimer = TFCSeasons.totalHours();
        sunLevel = 5;
    }

    private boolean checkedSun = false;
    @Override
    public void updateEntity()
    {
        Random R = new Random();
        if(!worldObj.isRemote && worldObj.getBlockMetadata(xCoord, yCoord, zCoord) == 0)
        {
            CropIndex crop = CropManager.getInstance().getCropFromId(cropId);
            
            long hours = TFCSeasons.totalHours();

            if(!checkedSun && TFCSeasons.getHour() == 12)
            {
                if(crop.needsSunlight && worldObj.getBlockLightValue(xCoord, yCoord, zCoord) < 15)
                    sunLevel--;
                else if(worldObj.canBlockSeeTheSky(xCoord, yCoord, zCoord))
                    sunLevel++;

                checkedSun = true;
            }

            if(growthTimer < hours && sunLevel > 0)
            {
                checkedSun = false;
                
                TileEntityFarmland tef = (TileEntityFarmland) worldObj.getBlockTileEntity(xCoord, yCoord-1, zCoord);

                float ambientTemp = ((TFCBiome)worldObj.getBiomeGenForCoords(xCoord, zCoord)).getHeightAdjustedTemperature(yCoord);
                float tempAdded = 0;

                if(!crop.dormantInFrost && ambientTemp < crop.minGrowthTemp)
                {
                    tempAdded = -0.03f * (crop.minGrowthTemp - ambientTemp);
                }
                else if(crop.dormantInFrost && ambientTemp < crop.minGrowthTemp)
                {
                    if(growth > 1)
                        tempAdded = -0.03f * (crop.minGrowthTemp - ambientTemp);
                    
                }
                else if(ambientTemp < 28)
                {
                    tempAdded = ambientTemp* 0.0003f;
                }
                else if(ambientTemp < 37)
                {
                    tempAdded = (28-(ambientTemp-28))* 0.0003f;
                }

                if(!crop.dormantInFrost && ambientTemp < crop.minAliveTemp)
                {
                    worldObj.setBlock(xCoord, yCoord, zCoord, 0);
                    worldObj.markBlockNeedsUpdate(xCoord, yCoord, zCoord);
                }
                else if(crop.dormantInFrost && ambientTemp < crop.minAliveTemp)
                {
                    if(growth > 1)
                    {
                        worldObj.setBlock(xCoord, yCoord, zCoord, 0);
                        worldObj.markBlockNeedsUpdate(xCoord, yCoord, zCoord);
                    }
                }

                int nutriType = crop.cycleType;
                int nutri = tef != null ? tef.nutrients[nutriType] : 85000;

                float nutriMult = (float)nutri/100000 > 1 ? 1 : (float)nutri/100000;

                if(tef != null)
                {
                    tef.DrainNutrients(nutriType, crop.nutrientUsageMult);
                    tef.waterSaturation--;
                }



                float growthRate = (((float)crop.numGrowthStages/(float)crop.growthTime)+tempAdded)*nutriMult;

                int oldGrowth = (int) Math.floor(growth);
                
                growth += growthRate;
                
                if(oldGrowth < (int) Math.floor(growth))
                {
                    broadcast();
                }

                if(crop.maxLifespan == -1 && growth > crop.numGrowthStages+((float)crop.numGrowthStages/2) || (tef != null && tef.waterSaturation <= -1) || growth < 0)
                {
                    worldObj.setBlock(xCoord, yCoord, zCoord, 0);
                    worldObj.markBlockNeedsUpdate(xCoord, yCoord, zCoord);
                }

                growthTimer += R.nextInt(2)+23;
            }
            else if(crop.needsSunlight && sunLevel <= 0)
            {
                worldObj.setBlock(xCoord, yCoord, zCoord, 0);
                worldObj.markBlockNeedsUpdate(xCoord, yCoord, zCoord);
            }
        }
    }

    public float getEstimatedGrowth(CropIndex crop)
    {
        return ((float)crop.numGrowthStages/(growthTimer/TFCSeasons.dayLength))*1.5f;
    }


    public void initialize()
    {
        if (this.worldObj.isRemote)
        {
            PacketHandler.requestInitialData(this);
        }
    }

    public void broadcast()
    {
        if (!this.worldObj.isRemote)
        {
            PacketHandler.broadcastCropData(this);
        }
    }

    public void handlePacketData(int i, float g)
    {
        this.cropId = i;
        this.growth = g;
        worldObj.markBlockNeedsUpdate(xCoord, yCoord, zCoord);
    }

    /**
     * Reads a tile entity from NBT.
     */
     public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
         super.readFromNBT(par1NBTTagCompound);
         growth = par1NBTTagCompound.getFloat("growth");
         cropId = par1NBTTagCompound.getInteger("cropId");
         growthTimer = par1NBTTagCompound.getLong("growthTimer");
    }

     /**
      * Writes a tile entity to NBT.
      */
     public void writeToNBT(NBTTagCompound nbt)
     {
         super.writeToNBT(nbt);
         nbt.setFloat("growth", growth);
         nbt.setInteger("cropId", cropId);
         nbt.setLong("growthTimer", growthTimer);
     }
}
