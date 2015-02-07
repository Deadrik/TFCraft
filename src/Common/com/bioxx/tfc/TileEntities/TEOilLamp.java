package com.bioxx.tfc.TileEntities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;

import com.bioxx.tfc.Core.TFC_Time;

public class TEOilLamp extends TELightEmitter
{
	public FluidStack fuel;
	public TEOilLamp()
	{
	}

	@Override
	public void create()
	{
		super.create();
	}

	public void updateLampFuel()
	{
		int diff = (int)TFC_Time.getTotalHours() - this.hourPlaced;
		this.hourPlaced = (int)TFC_Time.getTotalHours();

		if(fuel!= null && this.getFuelAmount() > 0)
		{
			fuel.amount -= (diff*(1000/TFC_Time.daysInYear));
			if(fuel.amount <= 0)
				fuel = null;
		}
	}

	@Override
	public boolean canUpdate()
	{
		return false;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		if(nbt.hasKey("Fuel"))
			this.fuel = FluidStack.loadFluidStackFromNBT(nbt.getCompoundTag("Fuel"));
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		if(fuel != null)
			nbt.setTag("Fuel", fuel.writeToNBT(new NBTTagCompound()));
	}

	public int getFuelAmount()
	{
		if(fuel == null)
			return 0;
		else return fuel.amount;
	}

	public int getFuelTimeLeft()
	{
		int f = getFuelAmount();
		float perc = f/1000f;
		return (int)((TFC_Time.daysInYear*24)*perc);
	}
}
