package com.bioxx.tfc.Core.Player;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import com.bioxx.tfc.Core.TFC_Climate;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.Interfaces.IClothing;
import com.bioxx.tfc.api.TileEntities.TEFireEntity;

public class BodyTempStats
{
	private int temperatureLevel;
	private int prevTemperatureLevel;
	private int extraFoodConsumed;
	private int extraWaterConsumed;
	private int heatStorage;
	private long tempTimer;

	private Random rand = new Random();
	private ItemStack itemHead, itemChest, itemLegs, itemFeet;

	public void onUpdate(EntityPlayer player)
	{
		if (TFC_Time.getTotalTicks() - this.tempTimer >= 20)
		{
			this.tempTimer += 20;
			if(heatStorage > temperatureLevel)
				heatStorage--;
			//player.addChatMessage("HS: "+heatStorage);
			if(!player.capabilities.isCreativeMode)
				applyTemperature(player);
		}
	}

	private void applyTemperature(EntityPlayer player)
	{
		FoodStatsTFC food = TFC_Core.getPlayerFoodStats(player);

		//Player's basic body functions.
		prevTemperatureLevel = temperatureLevel;
		if(rand.nextInt(2000-getBaseBodyTempMod(player))<100 && food.stomachLevel >= 500)
			temperatureLevel++;
		if((player.isSprinting() || player.swingProgress != 0) && rand.nextInt(1000 - (getBaseBodyTempMod(player)) / 2) < 100)
			temperatureLevel++;
		//if(rand.nextInt(1500 - (player.isInWater()?1000:0))<10 && food.waterLevel >= 500)
		//	temperatureLevel--;

		temperatureLevel += applyTemperatureFromEnvironment(player);

		//If we are warm then add it to heat reserves
		heatStorage = Math.max(Math.min(temperatureLevel+heatStorage, 14), 0);

		extraFoodConsumed = 0; //(temperatureLevel <0 && heatStorage <= 0 && rand.nextInt(350)<100)?temperatureLevel/-10:0;
		extraWaterConsumed = 0; //(temperatureLevel >0 && heatStorage <= 0 && rand.nextInt(350)<100)?temperatureLevel/10:0;

		if(temperatureLevel != prevTemperatureLevel &&
				!(prevTemperatureLevel >=-10 && prevTemperatureLevel <=10 &&
						temperatureLevel >=-10 && temperatureLevel <=10))
		{
			tellPlayerMessage(player);
		}
		prevTemperatureLevel = temperatureLevel;
		if(temperatureLevel >= -10 && temperatureLevel <= 10)
		{
			extraFoodConsumed = 0;
			extraWaterConsumed = 0;
		}
	}

	public int applyTemperatureFromEnvironment(EntityPlayer player)
	{
		int x = (int)(player.posX);
		int y = (int)(player.posY);
		int z = (int)(player.posZ);
		float temperature = TFC_Climate.getHeightAdjustedTemp(player.worldObj, x, y, z);
		temperature += applyTemperatureFromHeatSources(player);

		//if it's cold
		if(temperature <= 10)
		{
			int modifier = (int)((temperature - 10) * 15);
			if(rand.nextInt(1200 + modifier) < 10)
				return -1;
		}
		//if it's warm
		else if(temperature >= 30)
		{
			int modifier = Math.min(1199, (int)((temperature - 30) * 15));
			if(rand.nextInt(1200 - modifier) < 10)
				return 1;
		}
		else if(temperature <20)
		{
			if(temperatureLevel <= -1)
			{
				if(rand.nextInt(1200 -  Math.min(1199,(int)(temperature - 10)*15))<100)
					return 1;
			}
			else if(temperature >= 1)
			{
				if(rand.nextInt(1200 -  Math.min(1199,(int)(temperature - 10) * 15)) < 100)
					return -1;
			}
		}
		else if(temperature > 20)
		{
			if(temperatureLevel <= 1)
			{
				if(rand.nextInt(1200 -  Math.min(1199,(int)(temperature - 20)*10))<100)
					return 1;
			}
			else if(temperature > 1)
			{
				if(rand.nextInt(1200 -  Math.min(1199,(int)(temperature - 20)*10))<100)
					return -1;
			}
		}
		return 0;
	}

	public static float applyTemperatureFromHeatSources(EntityPlayer player)
	{
		int x = (int)(player.posX);
		int y = (int)(player.posY);
		int z = (int)(player.posZ);
		float temperatureMod = 0;

		for(int i = x - 7; i<x +7;i++)
		{
			for(int j = y-3;j<y+3;j++)
			{
				for(int k = z-7;k<z+7;k++)
				{
					if (player.worldObj.blockExists(i, j, k))
					{
						TileEntity te = player.worldObj.getTileEntity(i, j, k);
						if (player.worldObj.getBlock(i, j, k) == Blocks.lava || player.worldObj.getBlock(i, j, k) == TFCBlocks.lava || te instanceof TEFireEntity)
						{
							//returnAmount += (rand.nextInt(2000 - 198*(10-( (int)player.getDistance(i, j, k) )) )<10?1:0);
							//Lava averages 700-1200 C = 950 C, assume source is lava.
							double tempValue = 950;

							//if there is a firepit, use it's heat instead.
							if (te instanceof TEFireEntity)
								tempValue = ((TEFireEntity) te).fireTemp;

							//Just to make sure it's not 0
							double distanceSq = player.getDistanceSq(i, j, k) + 0.05;

							//radiation isn't perfect, so I don't know what a good numerator is, but it decreases with the square of the distance.
							//We can assume that the temperature of the actual heat source is when the player is directly touching it, which we have assigned to
							//a distanceSq of 0.05, therefore, the heat from such a heat source is = to the heat value * 0.05 divided by the distance squared
							tempValue *= (0.05) / distanceSq;
							temperatureMod += tempValue;
						}
					}
				}
			}
		}
		return temperatureMod;
	}

	public int getBaseBodyTempMod(EntityPlayer player)
	{
		itemHead = player.inventory.armorItemInSlot(3);
		itemChest = player.inventory.armorItemInSlot(2);
		itemLegs = player.inventory.armorItemInSlot(1);
		itemFeet = player.inventory.armorItemInSlot(0);
		int returnAmount = 0;
		if(itemHead != null && itemHead.getItem() instanceof IClothing)
			returnAmount += ((IClothing)itemHead.getItem()).getThermal();
		if(itemChest != null && itemChest.getItem() instanceof IClothing)
			returnAmount += ((IClothing)itemChest.getItem()).getThermal();
		if(itemLegs != null && itemLegs.getItem() instanceof IClothing)
			returnAmount += ((IClothing)itemLegs.getItem()).getThermal();
		if(itemFeet != null && itemFeet.getItem() instanceof IClothing)
			returnAmount += ((IClothing)itemFeet.getItem()).getThermal();
		return returnAmount;
	}

	private void tellPlayerMessage(EntityPlayer player)
	{
		/*		switch(temperatureLevel/10)
		{
		case -1:
		case 0:
		case 1: player.addChatMessage(new ChatComponentText("You feel comfortable"));break;
		case -2: player.addChatMessage(new ChatComponentText("You feel cool."));break;
		case -3: player.addChatMessage(new ChatComponentText("You feel chilled."));break;
		case -4: player.addChatMessage(new ChatComponentText("You feel cold."));break;
		case -5: player.addChatMessage(new ChatComponentText("You feel numb."));break;
		case -6: player.addChatMessage(new ChatComponentText("You feel freezing!"));break;
		case -7: killPlayer(player);break;
		case 2: player.addChatMessage(new ChatComponentText("You feel warm."));break;
		case 3: player.addChatMessage(new ChatComponentText("You feel very warm."));break;
		case 4: player.addChatMessage(new ChatComponentText("You feel hot."));break;
		case 5: player.addChatMessage(new ChatComponentText("You feel very hot."));break;
		case 6: player.addChatMessage(new ChatComponentText("You feel extremely hot!"));break;
		case 7: killPlayer(player);break;
		}
		 */
	}

	public void readNBT(NBTTagCompound nbt)
	{
		if (nbt.hasKey("tempCompound"))
		{
			NBTTagCompound tempCompound = nbt.getCompoundTag("tempCompound");
			this.temperatureLevel = tempCompound.getInteger("tempLev");
			this.extraWaterConsumed = tempCompound.getInteger("waterExtra");
			this.extraFoodConsumed = tempCompound.getInteger("foodExtra");
			this.heatStorage = tempCompound.getInteger("heatStorage");
			this.tempTimer = tempCompound.getLong("tempTimer");
		}
	}

	/**
	 * Writes food stats to an NBT object.
	 */
	public void writeNBT(NBTTagCompound nbt)
	{
		NBTTagCompound tempCompound = new NBTTagCompound();
		tempCompound.setInteger("tempLev",this.temperatureLevel);
		tempCompound.setInteger("waterExtra",this.extraWaterConsumed);
		tempCompound.setInteger("foodExtra",this.extraFoodConsumed);
		tempCompound.setInteger("heatStorage",this.heatStorage);
		tempCompound.setLong("tempTimer",this.tempTimer);
		nbt.setTag("tempCompound", tempCompound);
	}

	public int getExtraFood()
	{
		return extraFoodConsumed;
	}

	public int getExtraWater()
	{
		return extraWaterConsumed;
	}
}
