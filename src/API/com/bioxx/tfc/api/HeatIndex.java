package com.bioxx.tfc.api;

import java.util.Random;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

public class HeatIndex
{
	public float specificHeat;
	public float meltTemp;
	public boolean keepNBT;

	private ItemStack output;
	private int outputMin;
	private int outputMax;

	private ItemStack morph;
	public ItemStack input;

	public HeatIndex(ItemStack in, double sh, double melt, ItemStack out)
	{
		input = in;
		specificHeat = (float)sh;
		meltTemp = (float)melt;
		output = out;
	}

	public HeatIndex(ItemStack in, HeatRaw raw)
	{
		input = in;
		specificHeat = raw.specificHeat;
		meltTemp = raw.meltTemp;
	}

	public HeatIndex(ItemStack in, HeatRaw raw, ItemStack out)
	{
		this(in, raw);
		output = out;
	}

	public HeatIndex setKeepNBT(boolean k)
	{
		keepNBT = k;
		return this;
	}

	public boolean hasOutput(){
		return output != null;
	}

	public Item getOutputItem()
	{
		if(output!= null)
			return output.getItem();
		else return null;
	}

	public int getOutputDamage()
	{
		if(output!= null)
			return output.getItemDamage();
		else return 0;
	}

	public HeatIndex setMinMax(int min, int max)
	{
		outputMin = min;
		outputMax = max;
		return this;
	}

	public HeatIndex setMinMax(int amt)
	{
		outputMin = amt;
		outputMax = amt;
		return this;
	}

	public HeatIndex setMorph(ItemStack is)
	{
		morph = is;
		return this;
	}

	public ItemStack getMorph()
	{
		return morph;
	}

	public ItemStack getOutput(Random r)
	{
		if(getOutputItem() == null)
			return null;
		int rand = 0;
		if(outputMax - outputMin > 0) 
		{
			rand = outputMin + r.nextInt(outputMax - outputMin);
			return new ItemStack(getOutputItem(),output.stackSize, 100-rand);
		}
		else 
		{
			return new ItemStack(getOutputItem(),output.stackSize, outputMin);
		}
	}

	public ItemStack getOutput(ItemStack in, Random r)
	{
		ItemStack is = getOutput(r);
		if(is != null && this.keepNBT)
		{
			if(is.hasTagCompound())
			{
				NBTTagCompound nbt = is.getTagCompound();
				for(Object o : in.getTagCompound().func_150296_c())
				{
					NBTBase n = (NBTBase)o;
					if(nbt.hasKey(n.toString()))
						nbt.removeTag(n.toString());
					nbt.func_150296_c().add(o);
				}
			}
			else
			{
				is.setTagCompound(in.stackTagCompound);
				if(TFC_ItemHeat.hasTemp(is))
					TFC_ItemHeat.setTemp(is, TFC_ItemHeat.getTemp(is)*0.9f);
			}
		}
		return is;
	}

	public boolean matches(ItemStack is)
	{
		if(is != null)
		{
			boolean b = is.getItem().getHasSubtypes();
			if(is.getItem() != input.getItem())
				return false;
			else if (b &&input.getItemDamage() != 32767 &&
						is.getItemDamage() != input.getItemDamage())
				return false;
		}
		else return false;
		return true;
	}
}
