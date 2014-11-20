package com.bioxx.tfc.api.TileEntities;

import java.util.Random;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import com.bioxx.tfc.TileEntities.NetworkTileEntity;
import com.bioxx.tfc.api.Food;
import com.bioxx.tfc.api.TFC_ItemHeat;
import com.bioxx.tfc.api.Enums.EnumFuelMaterial;
import com.bioxx.tfc.api.Interfaces.ICookableFood;

public class TEFireEntity extends NetworkTileEntity
{
	public int airFromBellows = 0;
	public float fireTemp = 0;
	public int maxFireTempScale;
	public int fuelTimeLeft = 0;
	public int fuelBurnTemp = 0;
	public int fuelTasteProfile = 0;

	public static final int AIRTOADD = 200;

	public TEFireEntity()
	{
	}

	public void careForInventorySlot(ItemStack is)
	{
		if(is != null)
		{
			float temp = TFC_ItemHeat.GetTemp(is);
			if(fuelTimeLeft > 0 && is.getItem() instanceof ICookableFood)
			{
				float inc = Food.getCooked(is)+Math.min((fireTemp/700), 2f);
				Food.setCooked(is, inc);
				temp = inc;
				if(Food.isCooked(is))
				{
					int[] cookedTasteProfile = new int[] {0,0,0,0,0};
					Random R = new Random(((ICookableFood)is.getItem()).getFoodID()+(((int)Food.getCooked(is)-600)/120));
					cookedTasteProfile[0] = R.nextInt(30)-15;
					cookedTasteProfile[1] = R.nextInt(30)-15;
					cookedTasteProfile[2] = R.nextInt(30)-15;
					cookedTasteProfile[3] = R.nextInt(30)-15;
					cookedTasteProfile[4] = R.nextInt(30)-15;
					Food.setCookedProfile(is, cookedTasteProfile);
					Food.setFuelProfile(is, EnumFuelMaterial.getFuelProfile(fuelTasteProfile));
				}
			}
			else if(fireTemp > temp)
			{
				temp += TFC_ItemHeat.getTempIncrease(is);
			}
			else
				temp -= TFC_ItemHeat.getTempDecrease(is);
			TFC_ItemHeat.SetTemp(is, temp);
		}
	}

	public void receiveAirFromBellows()
	{
		if(airFromBellows < AIRTOADD * 3)
			airFromBellows += AIRTOADD;
		if(airFromBellows > AIRTOADD * 3)
			airFromBellows = AIRTOADD * 3;
	}

	public void keepTempToRange()
	{
		if(fireTemp > getMaxTemp())
			fireTemp = getMaxTemp();
		else if(fireTemp < 0)
			fireTemp = 0;
	}

	public int getMaxTemp()
	{
		return fuelBurnTemp + airFromBellows;
	}

	public int getTemperatureScaled(int s)
	{
		return (int)(fireTemp * s / maxFireTempScale);
	}

	protected float handleTemp()
	{
		if(fuelTimeLeft > 0)
		{
			fuelTimeLeft--;
			if(airFromBellows > 0)
				fuelTimeLeft--;
		}
		else if(fuelTimeLeft < 0)
			fuelTimeLeft = 0;

		if(fuelTimeLeft > 0)
			return fuelBurnTemp + airFromBellows;
		else
			return 0;
	}

	public void handleAirReduction()
	{
		if(airFromBellows > 0)
			airFromBellows--;
	}

	public void handleTempFlux(float desiredTemp)
	{
		if(fireTemp < desiredTemp)
		{
			if(airFromBellows == 0)
				fireTemp++;
			else
				fireTemp += 2;
		}
		else if(fireTemp > desiredTemp)
		{
			if(desiredTemp == 0)
			{
				if(airFromBellows == 0)
					fireTemp -= 1;
				else
					fireTemp -= 0.5;
			}
		}
		this.keepTempToRange();
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		nbt.setFloat("temperature", fireTemp);
		nbt.setInteger("fuelTime", fuelTimeLeft);
		nbt.setInteger("fuelTemp", fuelBurnTemp);
		nbt.setInteger("bellowsAir", airFromBellows);
		nbt.setInteger("fuelTasteProfile", fuelTasteProfile);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		fireTemp = nbt.getFloat("temperature");
		fuelTimeLeft = nbt.getInteger("fuelTime");
		fuelBurnTemp = nbt.getInteger("fuelTemp");
		airFromBellows = nbt.getInteger("airBellows");
		fuelTasteProfile = nbt.getInteger("fuelTasteProfile");
	}

	@Override
	public void handleInitPacket(NBTTagCompound nbt)
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void handleDataPacket(NBTTagCompound nbt)
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void createDataNBT(NBTTagCompound nbt)
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void createInitNBT(NBTTagCompound nbt)
	{
		// TODO Auto-generated method stub
	}
}
