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

	/*@Override
	public void create()
	{
		super.create();
	}*/

	public FluidStack getFuel()
	{
		if(fuel == null)
			return null;
		FluidStack f = fuel.copy();
		f.amount /= TFCOptions.oilLampFuelMult;
		return f;
	}

	/**
	 *  Updates the fuel timer of the lamp. Set burn to false to update the timer without consuming fuel, for when the lamp was off.
	 * @param burn
	 */
	public void updateLampFuel(Boolean burn)
	{
		if((int)TFC_Time.getTotalHours() - TFCOptions.oilLampFuelMult >= hourPlaced)
		{
			int diff = burn ? (int) TFC_Time.getTotalHours() - this.hourPlaced : 0; // Don't burn any fuel if set to false.
			this.hourPlaced = (int)TFC_Time.getTotalHours();

			if(fuel != null && getFuel().getFluid() != TFCFluids.LAVA && this.getFuelAmount() > 0)
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

	public boolean isFuelValid()
	{
		int meta = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
		if(getFuel() != null && getFuel().getFluid() == TFCFluids.OLIVEOIL)
		{
			return true;
		}
		else if((meta & 7) == 5 && getFuel() != null && getFuel().getFluid() == TFCFluids.LAVA)
		{
			return true;
		}
		return false;
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
