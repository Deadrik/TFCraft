package TFC.TileEntities;

import java.util.Random;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import TFC.API.TFCOptions;
import TFC.API.Constant.Global;
import TFC.Core.TFC_Achievements;
import TFC.Core.TFC_Climate;
import TFC.Core.TFC_Core;
import TFC.Core.TFC_Time;
import TFC.Food.CropIndex;
import TFC.Food.CropManager;

public class TECrop extends TileEntity
{
	public float growth;
	public int cropId;
	private long growthTimer;//Tracks the time since the plant was planted
	private long plantedTime;//Tracks the time when the plant was planted
	private byte sunLevel;
	public int tendingLevel;

	public TECrop()
	{
		growth = 0.1f;
		plantedTime = TFC_Time.getTotalTicks();
		growthTimer = TFC_Time.getTotalTicks();
		sunLevel = 5;
	}

	private boolean checkedSun = false;
	@Override
	public void updateEntity()
	{
		Random R = new Random();
		if(!worldObj.isRemote)
		{
			float timeMultiplier = 360/TFC_Time.daysInYear;
			CropIndex crop = CropManager.getInstance().getCropFromId(cropId);
			long time = TFC_Time.getTotalTicks();

			if(growthTimer < time && sunLevel > 0)
			{
				sunLevel--;
				if(crop.needsSunlight && (worldObj.getBlockLightValue(xCoord, yCoord, zCoord) > 11 || worldObj.canBlockSeeTheSky(xCoord, yCoord, zCoord)))
				{
					sunLevel++;
					if(sunLevel > 30)
						sunLevel = 30;
				}

				TileEntityFarmland tef = null;
				TileEntity te = worldObj.getTileEntity(xCoord, yCoord-1, zCoord);
				if(te != null && te instanceof TileEntityFarmland)
					tef = (TileEntityFarmland) te;

				float ambientTemp = TFC_Climate.getHeightAdjustedTempSpecificDay(TFC_Time.getDayOfYearFromTick(growthTimer), xCoord, yCoord, zCoord);
				float tempAdded = 0;

				if(!crop.dormantInFrost && ambientTemp < crop.minGrowthTemp)
					tempAdded = -0.03f * (crop.minGrowthTemp - ambientTemp);
				else if(crop.dormantInFrost && ambientTemp < crop.minGrowthTemp)
				{
					if(growth > 1)
						tempAdded = -0.03f * (crop.minGrowthTemp - ambientTemp);
				}
				else if(ambientTemp < 28)
					tempAdded = ambientTemp* 0.00035f;
				else if(ambientTemp < 37)
					tempAdded = (28-(ambientTemp-28))* 0.0003f;

				if(!crop.dormantInFrost && ambientTemp < crop.minAliveTemp)
					worldObj.setBlockToAir(xCoord, yCoord, zCoord);
				else if(crop.dormantInFrost && ambientTemp < crop.minAliveTemp)
					if(growth > 1)
						worldObj.setBlockToAir(xCoord, yCoord, zCoord);

				int nutriType = crop.cycleType;
				int nutri = tef != null ? tef.nutrients[nutriType] : 18000;
				int fert = tef != null ? tef.nutrients[3] : 0;
				int soilMax = tef != null ? tef.getSoilMax() : 18000;
				//waterBoost only helps if you are playing on a longer than default year length.
				float waterBoost = TFC.Blocks.BlockFarmland.isWaterNearby(worldObj, xCoord, yCoord, zCoord) ? 0.1f : 0;

				//Allow the fertilizer to make up for lost nutrients
				nutri = Math.min(nutri+fert, (int)(soilMax*1.25f));

				float nutriMult = (0.2f + ((float)nutri/(float)soilMax) * 0.5f) + waterBoost;

				if(tef != null)
				{
					if(tef.nutrients[nutriType] > 0)
						tef.DrainNutrients(nutriType, crop.nutrientUsageMult);
					//Drain Fertilizer
					if(tef.nutrients[3] > 0)
						tef.DrainNutrients(3, crop.nutrientUsageMult);
				}

				float growthRate = (((crop.numGrowthStages/(crop.growthTime*TFC_Time.timeRatio96))+tempAdded)*nutriMult) * timeMultiplier;

				int oldGrowth = (int) Math.floor(growth);

				growth += growthRate;

				if(oldGrowth < (int) Math.floor(growth))
					worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
					//this.broadcastPacketInRange(createCropUpdatePacket());

				if((TFCOptions.enableCropsDie && (crop.maxLifespan == -1 && growth > crop.numGrowthStages+((float)crop.numGrowthStages/2))) || growth < 0)
					worldObj.setBlockToAir(xCoord, yCoord, zCoord);

				growthTimer += (R.nextInt(2)+23)*TFC_Time.hourLength;
			}
			else if(crop.needsSunlight && sunLevel <= 0)
				worldObj.setBlockToAir(xCoord, yCoord, zCoord);

			if(worldObj.isRaining() && TFC_Climate.getHeightAdjustedTemp(xCoord, yCoord, zCoord) < 0)
				if(!crop.dormantInFrost || growth > 1)
					worldObj.setBlockToAir(xCoord, yCoord, zCoord);
		}
	}

	public float getEstimatedGrowth(CropIndex crop)
	{
		return ((float)crop.numGrowthStages/(growthTimer-plantedTime/TFC_Time.dayLength))*1.5f;
	}

	public void onHarvest(World world, EntityPlayer player)
	{
		if(!world.isRemote)
		{
			CropIndex crop = CropManager.getInstance().getCropFromId(cropId);
			if(crop != null && growth >= crop.numGrowthStages-1)
			{
				ItemStack is1 = crop.getOutput1(this);
				ItemStack is2 = crop.getOutput2(this);

				if(is1 != null)
					world.spawnEntityInWorld(new EntityItem(world, xCoord+0.5, yCoord+0.5, zCoord+0.5, is1));

				if(is2 != null)
					world.spawnEntityInWorld(new EntityItem(world, xCoord+0.5, yCoord+0.5, zCoord+0.5, is2));

				ItemStack is = crop.getSeed();
				if(is != null)
					world.spawnEntityInWorld(new EntityItem(world, xCoord+0.5, yCoord+0.5, zCoord+0.5, is));

				TFC_Core.getSkillStats(player).increaseSkill(Global.SKILL_AGRICULTURE, 1);
				
				if(TFC_Core.isSoil(world.getBlock(xCoord, yCoord - 1, zCoord)))
					player.addStat(TFC_Achievements.achWildVegetable, 1);
			}
			else if (crop != null)
			{
				ItemStack is = crop.getSeed();
				is.stackSize = 1+(world.rand.nextInt(1+(20-(int)(20*TFC_Core.getSkillStats(player).getSkillMultiplier(Global.SKILL_AGRICULTURE)) == 0 ? 1 : 0)));

				if(is != null)
					world.spawnEntityInWorld(new EntityItem(world, xCoord+0.5, yCoord+0.5, zCoord+0.5, is));
			}
		}
	}

	/**
	 * Reads a tile entity from NBT.
	 */
	@Override
	public void readFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readFromNBT(par1NBTTagCompound);
		growth = par1NBTTagCompound.getFloat("growth");
		cropId = par1NBTTagCompound.getInteger("cropId");
		growthTimer = par1NBTTagCompound.getLong("growthTimer");
		plantedTime = par1NBTTagCompound.getLong("plantedTime");
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
	}

	@Override
	public Packet getDescriptionPacket()
	{
		NBTTagCompound nbt = new NBTTagCompound();
		writeToNBT(nbt);
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, nbt);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
	{
		readFromNBT(pkt.func_148857_g());
	}

}
