package com.bioxx.tfc.Items.ItemBlocks;

import java.util.*;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumChatFormatting;

import com.bioxx.tfc.Core.Metal.*;
import com.bioxx.tfc.Items.ItemTerra;
import com.bioxx.tfc.api.Metal;
import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Enums.EnumWeight;
import com.bioxx.tfc.api.Interfaces.ISize;

public class ItemCrucible extends ItemTerraBlock implements ISize
{
	public Map<String, MetalPair> metals = new HashMap<String, MetalPair>();
	private Alloy currentAlloy; 
	public ItemCrucible(Block par1) 
	{
		super(par1);
	}

	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag) 
	{
		ItemTerra.addSizeInformation(is, arraylist);

		readFromItemNBT(is.getTagCompound(), arraylist);
	}

	public void readFromItemNBT(NBTTagCompound nbt, List<String> arraylist)
	{
		currentAlloy = null;
		metals = new HashMap<String, MetalPair>();
		if(nbt != null && nbt.hasKey("Metals"))
		{
			NBTTagList nbttaglist = nbt.getTagList("Metals", 9);

			for(int i = 0; i < nbttaglist.tagCount(); i++)
			{
				NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
				int id = nbttagcompound1.getInteger("ID");
				float amount = nbttagcompound1.getFloat("AmountF");

				Metal m = MetalRegistry.instance.getMetalFromItem(Item.getItemById(id));
				addMetal(m, amount);

			}
		}

		if(currentAlloy != null)
		{
			for(int c = 0; c < currentAlloy.alloyIngred.size(); c++)
			{
				double m = currentAlloy.alloyIngred.get(c).metal;
				m = Math.round(m * 100d)/100d;
				if(currentAlloy.alloyIngred.get(c).metalType != null)
				{
					arraylist.add(EnumChatFormatting.DARK_GRAY + currentAlloy.alloyIngred.get(c).metalType.name + " " + EnumChatFormatting.DARK_GREEN + m + "%");
				}
			}
		}
	}

	public boolean addMetal(Metal m, float amt)
	{
		if (getTotalMetal() + amt <= 3000 && !"Unknown".equals(m.name))
		{
			if(metals.containsKey(m.name))
			{
				metals.get(m.name).amount += amt;
			}
			else
			{
				metals.put(m.name, new MetalPair(m, amt));
			}

			updateCurrentAlloy();

			return true;
		}
		return false;
	}

	public float getTotalMetal()
	{
		Iterator iter = metals.values().iterator();
		float totalAmount = 0;
		while(iter.hasNext())
		{
			MetalPair m = (MetalPair) iter.next();
			if(m != null)
			{
				totalAmount += m.amount;
			}
		}
		return totalAmount;
	}

	private void updateCurrentAlloy()
	{
		List<AlloyMetal> a = new ArrayList<AlloyMetal>();

		Iterator iter = metals.values().iterator();
		float totalAmount = getTotalMetal();

		iter = metals.values().iterator();
		while(iter.hasNext())
		{
			MetalPair m = (MetalPair) iter.next();
			if(m != null)
			{
				a.add(new AlloyMetal(m.type, (m.amount/totalAmount) * 100f));
			}
		}

		Metal match = AlloyManager.INSTANCE.matchesAlloy(a, Alloy.EnumTier.TierV);
		if(match != null)
		{
			currentAlloy = new Alloy(match, totalAmount); 
			currentAlloy.alloyIngred = a;
		}
		else 
		{
			currentAlloy = new Alloy(Global.UNKNOWN, totalAmount);
			currentAlloy.alloyIngred = a;
		}
	}


	@Override
	public EnumSize getSize(ItemStack is)
	{
		return EnumSize.HUGE;
	}

	@Override
	public boolean canStack()
	{
		return false;
	}

	@Override
	public EnumWeight getWeight(ItemStack is)
	{
		return EnumWeight.HEAVY;
	}

}