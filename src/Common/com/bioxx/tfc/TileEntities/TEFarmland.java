package com.bioxx.tfc.TileEntities;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import com.bioxx.tfc.Chunkdata.ChunkData;
import com.bioxx.tfc.Core.TFC_Climate;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.Food.CropIndex;
import com.bioxx.tfc.Food.CropManager;
import com.bioxx.tfc.api.TFCBlocks;

public class TEFarmland extends NetworkTileEntity
{
	public long nutrientTimer = -1;
	public int[] nutrients =
	{ getSoilMax(), getSoilMax(), getSoilMax(), 0 };
	public boolean isInfested;

	/**
	 * Client only
	 * */
	public long timeSinceUpdate;

	public TEFarmland()
	{
		this.shouldSendInitData = true;
	}

	@Override
	public void updateEntity()
	{
		if(!worldObj.isRemote)
		{
			if(nutrientTimer <= 0)
				nutrientTimer = TFC_Time.getTotalHours();

			if(nutrientTimer < TFC_Time.getTotalHours())
			{
				CropIndex crop = null;
				int soilMax = getSoilMax();
				int restoreAmount = 139;

				if (worldObj.getBlock(xCoord, yCoord + 1, zCoord) == TFCBlocks.crops)
				{
					crop = CropManager.getInstance().getCropFromId(((TECrop)worldObj.getTileEntity(xCoord, yCoord + 1, zCoord)).cropId);

					if (crop.cycleType != 0)
					{
						if(nutrients[0] < soilMax)
							nutrients[0] += restoreAmount + crop.nutrientExtraRestore[0];
					}

					if (crop.cycleType != 1)
					{
						if(nutrients[1] < soilMax)
							nutrients[1] += restoreAmount + crop.nutrientExtraRestore[1];
					}

					if (crop.cycleType != 2)
					{
						if(nutrients[2] < soilMax)
							nutrients[2] += restoreAmount + crop.nutrientExtraRestore[2];
					}
				}
				else
				{
					if(nutrients[0] < soilMax)
						nutrients[0] += restoreAmount;
					if(nutrients[1] < soilMax)
						nutrients[1] += restoreAmount;
					if(nutrients[2] < soilMax)
						nutrients[2] += restoreAmount;
				}

				if(nutrients[0] > soilMax)
					nutrients[0] = soilMax;
				if(nutrients[1] > soilMax)
					nutrients[1] = soilMax;
				if(nutrients[2] > soilMax)
					nutrients[2] = soilMax;

				nutrientTimer+=24;

				if(isInfested)
				{
					float temp = TFC_Climate.getHeightAdjustedTempSpecificDay(worldObj, TFC_Time.getDayFromTotalHours(this.nutrientTimer), xCoord, yCoord, zCoord);
					if(temp > 10 && worldObj.rand.nextInt(10) == 0)
					{
						TileEntity te = worldObj.getTileEntity(xCoord, yCoord, zCoord);
						if(te instanceof TEFarmland)
						{
							((TEFarmland)te).infest();
						}
					}
					else if(temp <= 10)
					{
						if(worldObj.rand.nextInt(5) == 0)
							uninfest();
					}
				}

				//                if(BlockFarmland.isWaterNearby(worldObj, xCoord, yCoord, zCoord))
				//                {
				//                    waterSaturation += 1;
				//                    if(waterSaturation > 30)
				//                        waterSaturation = 30;
				//                }
				//                else if((worldObj.getBlockId(xCoord, yCoord+1, zCoord) == Block.crops.blockID) && crop != null)
				//                {
				//                    waterSaturation -= 1*crop.waterUsageMult;
				//                }
				//                
				//                if(worldObj.isRaining() && worldObj.canBlockSeeTheSky(xCoord, yCoord+1, zCoord))
				//                {
				//                    waterSaturation += 3;
				//                    if(waterSaturation > 30)
				//                        waterSaturation = 30;
				//                }
			}
		}
	}

	public void infest()
	{
		isInfested = true;
		ChunkData cd = TFC_Core.getCDM(worldObj).getData(xCoord >> 4, zCoord >> 4);
		if(cd != null && cd.cropInfestation == 0)
			cd.infest();
	}

	public void uninfest()
	{
		isInfested = false;
		ChunkData cd = TFC_Core.getCDM(worldObj).getData(xCoord >> 4, zCoord >> 4);
		if(cd != null && cd.cropInfestation > 0)
			cd.uninfest();
	}

	public int getSoilMax()
	{
		float timeMultiplier = TFC_Time.daysInYear / 360f;
		return (int) (25000 * timeMultiplier);
	}

	public void drainNutrients(int type, float multiplier)
	{
		float timeMultiplier = 360f / TFC_Time.daysInYear;
		nutrients[type] -= (100 * multiplier) * timeMultiplier;

		if (nutrients[type] < 0)
			nutrients[type] = 0;
	}

	public boolean fertilize(ItemStack is, boolean isOrganic)
	{
		nutrients[3] = getSoilMax();
		--is.stackSize;
		return true;
	}

	/**
	 * Reads a tile entity from NBT.
	 */
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		nutrients = nbt.getIntArray("nutrients");
		nutrientTimer = nbt.getLong("nutrientTimer");
		isInfested = nbt.getBoolean("isInfested");
	}

	/**
	 * Writes a tile entity to NBT.
	 */
	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		nbt.setIntArray("nutrients", nutrients);
		nbt.setLong("nutrientTimer", nutrientTimer); 
		nbt.setBoolean("isInfested", isInfested);
	}

	public void requestNutrientData()
	{
		if(TFC_Time.getTotalTicks() > timeSinceUpdate + 1000)
		{
			timeSinceUpdate = TFC_Time.getTotalTicks();
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			this.broadcastPacketInRange();
		}
	}

	@Override
	public void handleInitPacket(NBTTagCompound nbt) {
		nutrients = nbt.getIntArray("nutrients");
		isInfested = nbt.getBoolean("isInfested");		
	}

	@Override
	public void handleDataPacket(NBTTagCompound nbt) 
	{
		if(worldObj.isRemote)
			nutrients = nbt.getIntArray("nutrients");
		else
			broadcastPacketInRange();
	}

	@Override
	public void createDataNBT(NBTTagCompound nbt) 
	{
		if(!worldObj.isRemote)
		{
			nbt.setIntArray("nutrients", nutrients);
		}
	}

	@Override
	public void createInitNBT(NBTTagCompound nbt) {
		nbt.setIntArray("nutrients", nutrients);
		nbt.setBoolean("isInfested", isInfested);
	}
}
