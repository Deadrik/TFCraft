package com.bioxx.tfc.TileEntities;

import com.bioxx.tfc.api.TFC_ItemHeat;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TEFireEntity extends TileEntity
{
	public int airFromBellows = 0;
	public float fireTemp = 0;
	public int maxFireTempScale;
	public int fuelTimeLeft = 0;
	public int fuelBurnTemp = 0;

	public static final int AIRTOADD = 200;

	public TEFireEntity()
	{
	}

	public void careForInventorySlot(ItemStack is)
	{
		if(is != null)
		{
			float temp = TFC_ItemHeat.GetTemp(is);
			if(fireTemp > temp)
			{
				temp += TFC_ItemHeat.getTempIncrease(is);
			}
			else
			{
				temp -= TFC_ItemHeat.getTempDecrease(is);
			}
			TFC_ItemHeat.SetTemp(is, temp);
		}
	}

	public void receiveAirFromBellows()
	{
		if(airFromBellows < AIRTOADD*3)
			airFromBellows += AIRTOADD;
		if(airFromBellows > AIRTOADD*3)
			airFromBellows = AIRTOADD*3;
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
			if(airFromBellows == 0)
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
		{
			airFromBellows--;
		}
	}

	public void handleTempFlux(float desiredTemp)
	{
		if(fireTemp < desiredTemp)
		{
			if(airFromBellows == 0)
				fireTemp++;
			else
				fireTemp+=2;
		}
		else if(fireTemp > desiredTemp)
		{
			if(desiredTemp == 0)
			{
				if(airFromBellows == 0) 
					fireTemp-=1;
				else 
					fireTemp-=0.5;
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
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		fireTemp = nbt.getFloat("temperature");
		fuelTimeLeft = nbt.getInteger("fuelTime");
		fuelBurnTemp = nbt.getInteger("fuelTemp");
		airFromBellows = nbt.getInteger("airBellows");
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
