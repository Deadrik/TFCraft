package com.bioxx.tfc.TileEntities;

import java.util.Random;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

import com.bioxx.tfc.Blocks.BlockFarmland;
import com.bioxx.tfc.Core.TFC_Achievements;
import com.bioxx.tfc.Core.TFC_Climate;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.Food.CropIndex;
import com.bioxx.tfc.Food.CropManager;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.TFCOptions;
import com.bioxx.tfc.api.Constant.Global;

public class TECrop extends NetworkTileEntity
{
	public float growth;
	public int cropId;
	private long growthTimer;//Tracks the time since the plant was planted
	private long plantedTime;//Tracks the time when the plant was planted
	private byte sunLevel;
	public int tendingLevel;
	private int killLevel;//We use this to make crop killing more and more likely if its cold

	public TECrop()
	{
		growth = 0.1f;
		plantedTime = TFC_Time.getTotalTicks();
		growthTimer = TFC_Time.getTotalTicks();
		sunLevel = 1;
	}

	//private boolean checkedSun = false;
	@Override
	public void updateEntity()
	{
		Random r = new Random();
		if(!worldObj.isRemote)
		{
			float timeMultiplier = 360f / TFC_Time.daysInYear;
			CropIndex crop = CropManager.getInstance().getCropFromId(cropId);
			long time = TFC_Time.getTotalTicks();
			//ChunkData cd = TFC_Core.getCDM(worldObj).getData(xCoord >> 4, zCoord >> 4);
			if(crop != null && growthTimer < time && sunLevel > 0)
			{
				sunLevel--;
				if(crop.needsSunlight && hasSunlight(worldObj, xCoord, yCoord, zCoord))
				{
					sunLevel++;
					if(sunLevel > 30)
						sunLevel = 30;
				}

				TEFarmland tef = null;
				TileEntity te = worldObj.getTileEntity(xCoord, yCoord - 1, zCoord);
				if (te instanceof TEFarmland)
					tef = (TEFarmland) te;

				float ambientTemp = TFC_Climate.getHeightAdjustedTempSpecificDay(worldObj, TFC_Time.getDayOfYearFromTick(growthTimer), xCoord, yCoord, zCoord);
				float tempAdded = 0;
				boolean isDormant = false;

				//Attempt to start an infestation
				/*if(cd != null)
				{
					//Works but removed until this feature can be more clearly defined
					if(worldObj.rand.nextInt(2000) == 0)
					{
						cd.infest();
					}
					if(cd.cropInfestation > 0)
					{
						if(worldObj.rand.nextInt(40/cd.cropInfestation) == 0)
						{
							if(tef != null)
								tef.infest();
							worldObj.markBlockForUpdate(xCoord, yCoord-1, zCoord);
						}
					}
				}*/
				//End Infestation Code

				if(!crop.dormantInFrost && ambientTemp < crop.minGrowthTemp)
					tempAdded = -0.03f * (crop.minGrowthTemp - ambientTemp);
				else if(crop.dormantInFrost && ambientTemp < crop.minGrowthTemp)
				{
					if(growth > 1)
						tempAdded = -0.03f * (crop.minGrowthTemp - ambientTemp);
					isDormant = true;
				}
				else if(ambientTemp < 28)
					tempAdded = ambientTemp * 0.00035f;
				else if(ambientTemp < 37)
					tempAdded = (28 - (ambientTemp-28)) * 0.0003f;

				if(!crop.dormantInFrost && ambientTemp < crop.minAliveTemp)
				{
					int baseKillChance = 6;
					if(this.worldObj.rand.nextInt(baseKillChance-this.killLevel) == 0)
						killCrop(crop);
					else
					{
						if(killLevel < baseKillChance-1)
							this.killLevel++;
					}
				}
				else if(crop.dormantInFrost && ambientTemp < crop.minAliveTemp)
				{
					if(growth > 1)
					{
						int baseKillChance = 6;
						if(this.worldObj.rand.nextInt(baseKillChance-this.killLevel) == 0)
							killCrop(crop);
						else
						{
							if(killLevel < baseKillChance-1)
								this.killLevel++;
						}
					}
				}
				else
				{
					this.killLevel = 0;
				}

				int nutriType = crop.cycleType;
				int nutri = tef != null ? tef.nutrients[nutriType] : 18000;
				int fert = tef != null ? tef.nutrients[3] : 0;
				int soilMax = tef != null ? tef.getSoilMax() : 18000;
				//waterBoost only helps if you are playing on a longer than default year length.
				float waterBoost = BlockFarmland.isFreshWaterNearby(worldObj, xCoord, yCoord - 1, zCoord) ? 0.1f : 0;

				//Allow the fertilizer to make up for lost nutrients
				nutri = Math.min(nutri + fert, (int)(soilMax * 1.25f));

				float nutriMult = 0.2f + ((float) nutri / (float) soilMax) * 0.5f + waterBoost;

				if(tef != null && !isDormant)
				{
					if(tef.nutrients[nutriType] > 0)
						tef.drainNutrients(nutriType, crop.nutrientUsageMult);
					//Drain Fertilizer
					if(tef.nutrients[3] > 0)
						tef.drainNutrients(3, crop.nutrientUsageMult);
				}

				float growthRate = Math.max(0.0f, (((crop.numGrowthStages / (crop.growthTime * TFC_Time.timeRatio96) + tempAdded) * nutriMult) * timeMultiplier) * TFCOptions.cropGrowthMultiplier);
				if(tef!= null && tef.isInfested)
					growthRate /= 2;
				int oldGrowth = (int) Math.floor(growth);

				if(!isDormant)
					growth += growthRate;

				if(oldGrowth < (int) Math.floor(growth))
				{
					//TerraFirmaCraft.log.info(xCoord+","+yCoord+","+zCoord);
					worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
				}

				// Wild crops will always die of old age, regardless of the config setting
				if ((TFCOptions.enableCropsDie || !TFC_Core.isFarmland(worldObj.getBlock(xCoord, yCoord - 1, zCoord))) &&
					(crop.maxLifespan == -1 && growth > crop.numGrowthStages + ((float) crop.numGrowthStages / 2)) || growth < 0)
				{
					killCrop(crop);
				}

				growthTimer += (r.nextInt(2) + 23) * TFC_Time.HOUR_LENGTH;
			}
			// Not enough sunlight
			else if(crop != null && crop.needsSunlight && sunLevel <= 0)
			{
				killCrop(crop);
			}

			// Snowing
			if (TFC_Core.isExposedToRain(worldObj, xCoord, yCoord, zCoord) && TFC_Climate.getHeightAdjustedTemp(worldObj, xCoord, yCoord, zCoord) < 0)
			{
				if(crop != null && !crop.dormantInFrost || growth > 1)
				{
					killCrop(crop);
				}
			}
		}
	}

	public static boolean hasSunlight(World world, int x, int y, int z)
	{
		Chunk chunk = world.getChunkFromBlockCoords(x, z);
		int skylight = chunk.getSavedLightValue(EnumSkyBlock.Sky, x & 15, y, z & 15);
		boolean sky = world.canBlockSeeTheSky(x, y + 1, z);
		return sky || skylight > 13;
	}

	public float getEstimatedGrowth(CropIndex crop)
	{
		return ((float)crop.numGrowthStages / (growthTimer - plantedTime / TFC_Time.DAY_LENGTH)) * 1.5f;
	}

	public void onHarvest(World world, EntityPlayer player, boolean isBreaking)
	{
		if(!world.isRemote)
		{
			CropIndex crop = CropManager.getInstance().getCropFromId(cropId);

			if (crop != null && growth >= crop.numGrowthStages - 1)
			{
				ItemStack is1 = crop.getOutput1(this);
				ItemStack is2 = crop.getOutput2(this);
				ItemStack seedStack = crop.getSeed();

				if(is1 != null)
					world.spawnEntityInWorld(new EntityItem(world, xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, is1));

				if(is2 != null)
					world.spawnEntityInWorld(new EntityItem(world, xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, is2));

				int skill = 20 - (int) (20 * TFC_Core.getSkillStats(player).getSkillMultiplier(Global.SKILL_AGRICULTURE));

				seedStack.stackSize = 1 + (world.rand.nextInt(1 + skill) == 0 ? 1 : 0);
				if (isBreaking)
					world.spawnEntityInWorld(new EntityItem(world, xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, seedStack));

				TFC_Core.getSkillStats(player).increaseSkill(Global.SKILL_AGRICULTURE, 1);

				if (TFC_Core.isSoil(world.getBlock(xCoord, yCoord - 1, zCoord)))
					player.addStat(TFC_Achievements.achWildVegetable, 1);
			}
			else if (crop != null)
			{
				ItemStack is = crop.getSeed();
				is.stackSize = 1;
				world.spawnEntityInWorld(new EntityItem(world, xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, is));
			}
		}
	}

	public void killCrop(CropIndex crop)
	{
		ItemStack is = crop.getSeed();
		is.stackSize = 1;
		if (TFC_Core.isFarmland(worldObj.getBlock(xCoord, yCoord - 1, zCoord)) && TFCOptions.enableSeedDrops)
		{
			if(worldObj.setBlock(xCoord, yCoord, zCoord, TFCBlocks.worldItem))
			{
				TEWorldItem te = (TEWorldItem) worldObj.getTileEntity(xCoord, yCoord, zCoord);
				te.storage[0] = is;
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			}				
		}
		else
		{
			worldObj.setBlockToAir(xCoord, yCoord, zCoord);
		}
	}

	/**
	 * Reads a tile entity from NBT.
	 */
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		growth = nbt.getFloat("growth");
		cropId = nbt.getInteger("cropId");
		growthTimer = nbt.getLong("growthTimer");
		plantedTime = nbt.getLong("plantedTime");
		killLevel = nbt.getInteger("killLevel");
		sunLevel = nbt.getByte("sunLevel");
	}

	/**
	 * Writes a tile entity to NBT.
	 */
	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		nbt.setFloat("growth", growth);
		nbt.setInteger("cropId", cropId);
		nbt.setLong("growthTimer", growthTimer);
		nbt.setLong("plantedTime", plantedTime);
		nbt.setInteger("killLevel", killLevel);
		nbt.setByte("sunLevel", sunLevel);
	}

	@Override
	public void handleInitPacket(NBTTagCompound nbt) {
		growth = nbt.getFloat("growth");
		cropId = nbt.getInteger("cropId");
		worldObj.func_147479_m(xCoord, yCoord, zCoord);
	}

	@Override
	public void handleDataPacket(NBTTagCompound nbt) 
	{
		growth = nbt.getFloat("growth");
		cropId = nbt.getInteger("cropId");
		worldObj.func_147479_m(xCoord, yCoord, zCoord);
	}

	@Override
	public void createDataNBT(NBTTagCompound nbt) {
		nbt.setFloat("growth", growth);
		nbt.setInteger("cropId", cropId);
	}

	@Override
	public void createInitNBT(NBTTagCompound nbt) {
		nbt.setFloat("growth", growth);
		nbt.setInteger("cropId", cropId);
	}

}
