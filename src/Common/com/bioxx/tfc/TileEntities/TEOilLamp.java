package com.bioxx.tfc.TileEntities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;

import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.api.TFCFluids;
import com.bioxx.tfc.api.TFCOptions;

public class TEOilLamp extends TELightEmitter
{
	private FluidStack fuel;

	public TEOilLamp()
	{
	}

	@Override
	public void create()
	{
		super.create();
	}

	public FluidStack getFuel()
	{
		FluidStack f = fuel.copy();
		f.amount /= TFCOptions.oilLampFuelMult;
		return f;
	}

	public void updateLampFuel()
	{
		if((int)TFC_Time.getTotalHours() - TFCOptions.oilLampFuelMult >= hourPlaced)
		{
			int diff = (int)TFC_Time.getTotalHours() - this.hourPlaced;
			this.hourPlaced = (int)TFC_Time.getTotalHours();

			if(fuel != null && fuel.fluidID != TFCFluids.LAVA.getID() && this.getFuelAmount() > 0)
			{
				fuel.amount -= diff;
				if(fuel.amount <= 0)
					fuel = null;
			}
		}
	}

	public void setFuelFromStack(FluidStack fs)
	{
		fuel = fs;
		fuel.amount *= TFCOptions.oilLampFuelMult;
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
		float perc = f/250f;
		return (int)((TFC_Time.daysInYear*24)*perc);
	}
}
