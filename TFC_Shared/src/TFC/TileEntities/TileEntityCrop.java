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
    //temp


    //important
    public float growth;
    public int cropId;
    private long growthTimer;
    private byte sunLevel;

    public TileEntityCrop()
    {
        growth = 0;
        growthTimer = TFCSeasons.totalHours();
        sunLevel = 5;
    }

    @Override
    public void updateEntity()
    {
        Random R = new Random();
        if(!worldObj.isRemote)
        {
            long hours = TFCSeasons.totalHours();
            
            if(TFCSeasons.getHour() == 12)
            {
                if(worldObj.getBlockLightValue(xCoord, yCoord, zCoord) < 15)
                    sunLevel--;
                else
                    sunLevel++;
            }
            
            if(growthTimer < hours && sunLevel > 0)
            {
                CropIndex crop = CropManager.getInstance().getCropFromId(cropId);
                TileEntityFarmland tef = (TileEntityFarmland) worldObj.getBlockTileEntity(xCoord, yCoord-1, zCoord);
                
                float ambientTemp = ((TFCBiome)worldObj.getBiomeGenForCoords(xCoord, zCoord)).getHeightAdjustedTemperature(yCoord);
                float tempAdded = 0;

                if(ambientTemp < crop.minGrowthTemp)
                {
                    tempAdded = -0.03f * (crop.minGrowthTemp - ambientTemp);
                }
                else if(ambientTemp < 25)
                {
                    tempAdded = ambientTemp* 0.0003f;
                }
                else if(ambientTemp < 37)
                {
                    tempAdded = (25-(ambientTemp-25))* 0.0003f;
                }

                if(ambientTemp < crop.minAliveTemp)
                {
                    worldObj.setBlock(xCoord, yCoord, zCoord, 0);
                }
                
                int nutriType = crop.cycleType;
                int nutri = tef.nutrients[nutriType];
                float nutriMult = (float)nutri/10000 > 1 ? 1 : (float)nutri/10000;
                tef.DrainNutrients(nutriType, crop.nutrientUsage);
                tef.waterSaturation--;
                


                float growthRate = (((float)crop.numGrowthStages/(float)crop.growthTime)+tempAdded)*nutriMult;

                growth += growthRate;
                
                if(growth > crop.numGrowthStages+((float)crop.numGrowthStages/2) || tef.waterSaturation == -1)
                    worldObj.setBlock(xCoord, yCoord, zCoord, 0);
                
                growthTimer += R.nextInt(2)+23;
            }
            else if(sunLevel <= 0)
            {
                worldObj.setBlock(xCoord, yCoord, zCoord, 0);
            }
        }
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
