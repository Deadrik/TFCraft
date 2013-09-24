package TFC.Items.ItemBlocks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumChatFormatting;
import TFC.API.ISize;
import TFC.API.Metal;
import TFC.API.Constant.Global;
import TFC.API.Enums.EnumSize;
import TFC.API.Enums.EnumWeight;
import TFC.Core.Metal.Alloy;
import TFC.Core.Metal.AlloyManager;
import TFC.Core.Metal.AlloyMetal;
import TFC.Core.Metal.MetalPair;
import TFC.Core.Metal.MetalRegistry;
import TFC.Items.ItemTerra;

public class ItemCrucible extends ItemTerraBlock implements ISize
{
	public HashMap metals = new HashMap();
	private Alloy currentAlloy; 
	public ItemCrucible(int par1) 
	{
		super(par1);
	}

	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag) 
	{
		ItemTerra.addSizeInformation(this, arraylist);

		readFromItemNBT(is.getTagCompound(), arraylist);
	}

	public void readFromItemNBT(NBTTagCompound nbt, List arraylist)
	{
		currentAlloy = null;
		metals = new HashMap();
		if(nbt != null && nbt.hasKey("Metals"))
		{
			NBTTagList nbttaglist = nbt.getTagList("Metals");

			for(int i = 0; i < nbttaglist.tagCount(); i++)
			{
				NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.tagAt(i);
				int id = nbttagcompound1.getInteger("ID");
				float amount = nbttagcompound1.getFloat("AmountF");

				Metal m = MetalRegistry.instance.getMetalFromItem(Item.itemsList[id]);
				addMetal(m, amount);

			}
		}

		if(currentAlloy != null)
		{
			for(int c = 0; c < currentAlloy.AlloyIngred.size(); c++)
			{
				double m = currentAlloy.AlloyIngred.get(c).metal;
				m = Math.round(m * 100d)/100d;
				if(currentAlloy.AlloyIngred.get(c).metalType != null) {
					arraylist.add(EnumChatFormatting.DARK_GRAY + currentAlloy.AlloyIngred.get(c).metalType.Name + " " + EnumChatFormatting.DARK_GREEN + m + "%");
				}
			}
		}
	}

	public boolean addMetal(Metal m, float amt)
	{
		if(getTotalMetal()+amt <= 3000 && m.Name != "Unknown")
		{
			if(metals.containsKey(m.Name)) {
				((MetalPair)metals.get(m.Name)).amount += amt;
			} else {
				metals.put(m.Name, new MetalPair(m, amt));
			}

			updateCurrentAlloy();

			return true;
		}
		return false;
	}

	public int getTotalMetal()
	{
		Iterator iter = metals.values().iterator();
		int totalAmount = 0;
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
		int totalAmount = getTotalMetal();

		iter = metals.values().iterator();
		while(iter.hasNext())
		{
			MetalPair m = (MetalPair) iter.next();
			if(m != null)
			{
				a.add(new AlloyMetal(m.type, (m.amount/totalAmount) * 100f));
			}
		}

		Metal match = AlloyManager.instance.matchesAlloy(a, Alloy.EnumTier.TierV);
		if(match != null)
		{
			currentAlloy = new Alloy(match, totalAmount); 
			currentAlloy.AlloyIngred = a;
		}
		else 
		{
			currentAlloy = new Alloy(Global.UNKNOWN, totalAmount);
			currentAlloy.AlloyIngred = a;
		}
	}


	@Override
	public EnumSize getSize() {
		// TODO Auto-generated method stub
		return EnumSize.HUGE;
	}

	@Override
	public boolean canStack() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public EnumWeight getWeight() {
		// TODO Auto-generated method stub
		return EnumWeight.HEAVY;
	}

}