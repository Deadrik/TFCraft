package com.bioxx.tfc.api.TileEntities;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import com.bioxx.tfc.TileEntities.NetworkTileEntity;
import com.bioxx.tfc.api.HeatIndex;
import com.bioxx.tfc.api.HeatRegistry;
import com.bioxx.tfc.api.TFC_ItemHeat;

public class TEFireEntity extends NetworkTileEntity
{
	public int airFromBellows;
	public float fireTemp;
	public int maxFireTempScale = 2000; // Fixes NPE
	public int fuelTimeLeft;
	public int fuelBurnTemp;
	public int fuelTasteProfile;

	public static final int AIRTOADD = 200;

	public TEFireEntity()
	{
	}

	public void careForInventorySlot(ItemStack is)
	{
		if(is != null)
		{
			HeatRegistry manager = HeatRegistry.getInstance();
			HeatIndex index = manager.findMatchingIndex(is);

			if (index != null && index.hasOutput())
			{
				float temp = TFC_ItemHeat.getTemp(is);
				if (fireTemp > temp)
				{
					temp += TFC_ItemHeat.getTempIncrease(is);
				}
				else
					temp -= TFC_ItemHeat.getTempDecrease(is);
				TFC_ItemHeat.setTemp(is, temp);
			}
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
