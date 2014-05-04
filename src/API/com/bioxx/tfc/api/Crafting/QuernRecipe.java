package com.bioxx.tfc.api.Crafting;

import net.minecraft.item.Item;

public class QuernRecipe
{
	private Item inItem;
	private int inItemDmg;
	private Item outItem;
	private int outItemDmg;
	private int outStackSize;

	public QuernRecipe(Item inputItem, int inputItemDamage, Item outputItem, int outputItemDamage, int outpuStackSize)
	{
		this.inItem = inputItem;
		this.inItemDmg = inputItemDamage;
		this.outItem = outputItem;
		this.outItemDmg = outputItemDamage;
		this.outStackSize = outpuStackSize;
	}

	public Boolean isInItem(Item item)
	{
		return inItem == item;
	}

	public Item getInItem()
	{
		return inItem;
	}

	public int getInItemDmg()
	{
		return inItemDmg;
	}

	public Item getOutItem()
	{
		return outItem;
	}

	public int getOutItemDmg()
	{
		return outItemDmg;
	}

	public int getOutStackSize()
	{
		return outStackSize;
	}

}
