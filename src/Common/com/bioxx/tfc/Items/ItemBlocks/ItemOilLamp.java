package com.bioxx.tfc.Items.ItemBlocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidContainerItem;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.api.Metal;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.TFCFluids;
import com.bioxx.tfc.api.TFCOptions;
import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Enums.EnumWeight;
import com.bioxx.tfc.api.Interfaces.ISmeltable;
import com.bioxx.tfc.api.Util.Helper;

public class ItemOilLamp extends ItemTerraBlock implements ISmeltable, IFluidContainerItem
{
	public ItemOilLamp(Block par1)
	{
		super(par1);
		this.metaNames = new String[]{"Gold", "Platinum", "RoseGold", "Silver", "SterlingSilver", "BlueSteel"};
	}

	@Override
	public int getDisplayDamage(ItemStack is)
	{
		FluidStack fuel = FluidStack.loadFluidStackFromNBT(is.getTagCompound());
		int amt = fuel != null ? fuel.amount : 0;
		return getMaxDamage(is) - amt;
	}

	@Override
	public boolean isDamaged(ItemStack is)
	{
		return is.hasTagCompound();
	}

	@Override
	public int getMaxDamage(ItemStack is)
	{
		return 250;
	}

	@Override
	public EnumSize getSize(ItemStack is) {
		return EnumSize.SMALL;
	}

	@Override
	public EnumWeight getWeight(ItemStack is) {
		return EnumWeight.LIGHT;
	}

	@Override
	public int getItemStackLimit(ItemStack is)
	{
		return 1;
	}

	@Override
	public short getMetalReturnAmount(ItemStack is) {

		return 100;
	}

	@Override
	public boolean isSmeltable(ItemStack is) {
		FluidStack fuel = FluidStack.loadFluidStackFromNBT(is.getTagCompound());
		return fuel == null || fuel.amount == 0;
	}

	@Override
	public EnumTier getSmeltTier(ItemStack is) {
		// TODO Auto-generated method stub
		return EnumTier.TierI;
	}

	@Override
	public Metal getMetalType(ItemStack is) 
	{
		int meta = is.getItemDamage();
		switch(meta)
		{
		case 0: return Global.GOLD;
		case 1: return Global.PLATINUM;
		case 2: return Global.ROSEGOLD;
		case 3: return Global.SILVER;
		case 4: return Global.STERLINGSILVER;
		case 5: return Global.BLUESTEEL;
		default : return Global.UNKNOWN;
		}
	}

	@Override
	public boolean onItemUse(ItemStack is, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		MovingObjectPosition mop = this.getMovingObjectPositionFromPlayer(world, player, !is.hasTagCompound());
		if (mop != null && is.getItemDamage() == 5 && world.getBlock(mop.blockX, mop.blockY, mop.blockZ) == TFCBlocks.lavaStationary)
		{
			if(!is.hasTagCompound())
			{
				FluidStack fs = new FluidStack(TFCFluids.LAVA, 250);
				is.setTagCompound(fs.writeToNBT(new NBTTagCompound()));
			}
			return false;
		}

		int yCoord = y;
		if (side == 0)
			--yCoord;
		else if (side == 1)
			++yCoord;
		else
			return false;

		int xCoord = x;
		int zCoord = z;
		//Block block = world.getBlock(xCoord, yCoord, zCoord);
		if(world.isAirBlock(xCoord, yCoord, zCoord))
		{
			return super.onItemUse(is, player, world, xCoord, yCoord, zCoord, side, hitX, hitY, hitZ);
		}

		return false;
	}

	@Override
	public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata)
	{
		FluidStack fs = FluidStack.loadFluidStackFromNBT(stack.getTagCompound());
		if (fs == null || fs.getFluid() != TFCFluids.OLIVEOIL && fs.getFluid() != TFCFluids.LAVA)
		{
			metadata += 8;
		}

		return super.placeBlockAt(stack, player, world, x, y, z, side, hitX, hitY, hitZ, metadata);
	}

	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag)
	{
		super.addInformation(is, player, arraylist, flag);
		if(is.hasTagCompound())
		{
			FluidStack fs = FluidStack.loadFluidStackFromNBT(is.getTagCompound());
			if(fs != null && fs.getFluid() == TFCFluids.OLIVEOIL)
				arraylist.add(((fs.amount) * TFCOptions.oilLampFuelMult) + " " + TFC_Core.translate("gui.hoursRemaining") + " (" + Helper.roundNumber((fs.amount / 250f) * 100f, 10) + "%)");
			if(fs != null && fs.getFluid() == TFCFluids.LAVA)
				arraylist.add(TFC_Core.translate("gui.infinite") + " " + TFC_Core.translate("gui.hoursRemaining"));
		}
	}

	public static ItemStack getFullLamp(int meta)
	{
		ItemStack is = new ItemStack(TFCBlocks.oilLamp, 1, meta);
		FluidStack fs = new FluidStack(TFCFluids.OLIVEOIL, 250);
		is.setTagCompound(fs.writeToNBT(new NBTTagCompound()));
		return is;
	}

	@Override
	public FluidStack getFluid(ItemStack container) 
	{
		return FluidStack.loadFluidStackFromNBT(container.getTagCompound());
	}

	@Override
	public int getCapacity(ItemStack container) 
	{
		return 250;
	}

	@Override
	public int fill(ItemStack container, FluidStack resource, boolean doSim) 
	{
		FluidStack fs = getFluid(container);
		int inAmt = 0;
		if(fs != null)
		{
			int max = getCapacity(container) - fs.amount;
			if(max > 0 && fs.isFluidEqual(resource))
			{
				inAmt = Math.min(max, resource.amount);
				if(doSim)
				{
					fs.amount += inAmt;
					if(container.getTagCompound() == null)
						container.setTagCompound(new NBTTagCompound());
					fs.writeToNBT(container.getTagCompound());
				}
			}
		}
		else 
		{
			inAmt = Math.min(getCapacity(container), resource.amount);
			if(doSim)
			{
				fs = resource.copy();
				fs.amount = inAmt;
				if(container.getTagCompound() == null)
					container.setTagCompound(new NBTTagCompound());
				fs.writeToNBT(container.getTagCompound());
			}
		}
		return inAmt;
	}

	@Override
	public FluidStack drain(ItemStack container, int maxDrain, boolean doSim) 
	{
		FluidStack fs = getFluid(container);
		FluidStack fsOut = fs.copy();
		fsOut.amount = Math.min(maxDrain, fs.amount);

		if (doSim)
		{
			if(fs.amount - fsOut.amount <= 0)
			{
				container.stackTagCompound = null;
			}
			else
			{
				fs.amount -= fsOut.amount;
				if(container.getTagCompound() == null)
					container.setTagCompound(new NBTTagCompound());
				fs.writeToNBT(container.getTagCompound());
			}
		}
		return fsOut;
	}
}
