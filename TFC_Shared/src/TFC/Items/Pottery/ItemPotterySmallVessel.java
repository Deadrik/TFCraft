package TFC.Items.Pottery;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import TFC.TerraFirmaCraft;
import TFC.API.HeatIndex;
import TFC.API.HeatRegistry;
import TFC.API.IBag;
import TFC.API.IMadeOfMetal;
import TFC.API.Metal;
import TFC.API.Enums.EnumSize;
import TFC.API.Enums.EnumWeight;
import TFC.Core.TFC_Core;
import TFC.Core.Metal.Alloy;
import TFC.Core.Metal.AlloyManager;
import TFC.Core.Metal.AlloyMetal;
import TFC.Core.Util.StringUtil;
import TFC.Items.ItemOre;
import TFC.Items.ItemOreSmall;

public class ItemPotterySmallVessel extends ItemPotteryBase implements IBag
{

	public ItemPotterySmallVessel(int id) 
	{
		super(id);
		this.MetaNames = new String[]{"Clay Vessel", "Ceramic Vessel", "Ceramic Vessel"};
	}

	@Override
	public void onDoneCooking(World world, ItemStack is)
	{
		ItemStack[] bag = loadBagInventory(is);
		boolean canCookAlloy = true;
		for(int i = 0; i < 4; i++)
		{
			if(bag[i] != null)
			{
				if(!(bag[i].getItem() instanceof ItemOreSmall) && !(bag[i].getItem() instanceof ItemOre))
				{
					canCookAlloy = false;
				}
			}
		}

		if(canCookAlloy)
		{
			HeatIndex heatIndex0 = HeatRegistry.getInstance().findMatchingIndex(bag[0]);
			HeatIndex heatIndex1 = HeatRegistry.getInstance().findMatchingIndex(bag[1]);
			HeatIndex heatIndex2 = HeatRegistry.getInstance().findMatchingIndex(bag[2]);
			HeatIndex heatIndex3 = HeatRegistry.getInstance().findMatchingIndex(bag[3]);
			ItemStack is0 = heatIndex0 != null ? heatIndex0.getOutput(world.rand) : null;
			ItemStack is1 = heatIndex1 != null ? heatIndex1.getOutput(world.rand) : null;
			ItemStack is2 = heatIndex2 != null ? heatIndex2.getOutput(world.rand) : null;
			ItemStack is3 = heatIndex3 != null ? heatIndex3.getOutput(world.rand) : null;

			Metal[] types = new Metal[4];
			int[] metalAmounts = new int[4];

			if(bag[0] != null)
			{
				types[0] = ((IMadeOfMetal)bag[0].getItem()).GetMetalType(bag[0]);
				metalAmounts[0] = ((IMadeOfMetal)bag[0].getItem()).GetMetalReturnAmount(bag[0]) * bag[0].stackSize;
			}

			if(bag[1] != null)
			{
				types[1] = ((IMadeOfMetal)bag[1].getItem()).GetMetalType(bag[1]);
				metalAmounts[1] = ((IMadeOfMetal)bag[1].getItem()).GetMetalReturnAmount(bag[1]) * bag[1].stackSize;

				if(mergeMetals(types[0], types[1], metalAmounts[0], metalAmounts[1]))
					types[1] = null;
			}

			if(bag[2] != null)
			{
				types[2] = ((IMadeOfMetal)bag[2].getItem()).GetMetalType(bag[2]);
				metalAmounts[2] = ((IMadeOfMetal)bag[2].getItem()).GetMetalReturnAmount(bag[2]) * bag[2].stackSize;

				if(mergeMetals(types[0], types[2], metalAmounts[0], metalAmounts[2]))
					types[2] = null;
				if(mergeMetals(types[1], types[2], metalAmounts[1], metalAmounts[2]))
					types[2] = null;
			}
			if(bag[3] != null)
			{
				types[3] = ((IMadeOfMetal)bag[3].getItem()).GetMetalType(bag[3]);
				metalAmounts[3] = ((IMadeOfMetal)bag[3].getItem()).GetMetalReturnAmount(bag[3]) * bag[3].stackSize;

				if(mergeMetals(types[0], types[3], metalAmounts[0], metalAmounts[3]))
					types[3] = null;
				if(mergeMetals(types[1], types[3], metalAmounts[1], metalAmounts[3]))
					types[3] = null;
				if(mergeMetals(types[2], types[3], metalAmounts[2], metalAmounts[3]))
					types[3] = null;
			}
			int total = metalAmounts[0] + metalAmounts[1] + metalAmounts[2] + metalAmounts[3];
			int numMetals = 0;
			if(metalAmounts[0] > 0) numMetals++;
			if(metalAmounts[1] > 0) numMetals++;
			if(metalAmounts[2] > 0) numMetals++;
			if(metalAmounts[3] > 0) numMetals++;

			if(total > 0 && numMetals > 1)
			{
				int[] metalPercent = new int[4];
				metalPercent[0] = (metalAmounts[0] / total) * 100;
				metalPercent[1] = (metalAmounts[1] / total) * 100;
				metalPercent[2] = (metalAmounts[2] / total) * 100;
				metalPercent[3] = (metalAmounts[3] / total) * 100;

				List<AlloyMetal> a = new ArrayList<AlloyMetal>();
				if(types[0] != null) a.add(new AlloyMetal(types[0], metalPercent[0]));
				if(types[1] != null) a.add(new AlloyMetal(types[1], metalPercent[1]));
				if(types[2] != null) a.add(new AlloyMetal(types[2], metalPercent[2]));
				if(types[3] != null) a.add(new AlloyMetal(types[3], metalPercent[3]));

				Alloy output = new Alloy(AlloyManager.instance.matchesAlloy(a), total); 
				if(output != null)
				{
					NBTTagCompound tag = is.stackTagCompound;
					tag.setString("MetalType", output.outputType.Name);
					tag.setInteger("MetalAmount", output.outputAmount);
				}
				is.getTagCompound().removeTag("Items");
				is.setItemDamage(2);
			}
			else if(total > 0 && numMetals == 1)
			{
				Metal m = null;
				if(types[0] != null) m = types[0];
				else if(types[1] != null) m = types[0];
				else if(types[2] != null) m = types[0];
				else if(types[3] != null) m = types[0];
				
				if(m != null)
				{
					NBTTagCompound tag = is.stackTagCompound;
					tag.setString("MetalType", m.Name);
					tag.setInteger("MetalAmount", total);
				}
				is.getTagCompound().removeTag("Items");
				is.setItemDamage(2);
			}
		}
	}

	private boolean mergeMetals(Metal mt0, Metal mt1, float m0, float m1)
	{
		if(mt0 != null && mt1 != null && m0 > 0)
		{
			if(mt0.Name.equals(mt1.Name))
			{
				m0 += m1;
				return true;
			}
		}
		return false;
	}

	public ItemStack[] loadBagInventory(ItemStack is)
	{
		ItemStack[] bag = new ItemStack[4];

		if(is != null && is.hasTagCompound())
		{
			NBTTagList nbttaglist = is.getTagCompound().getTagList("Items");

			for(int i = 0; i < nbttaglist.tagCount(); i++)
			{
				NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.tagAt(i);
				byte byte0 = nbttagcompound1.getByte("Slot");
				if(byte0 >= 0 && byte0 < 4)
				{
					bag[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
				}
			}
		}

		return bag;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
	{
		if(itemstack.getItemDamage() == 2)
			entityplayer.openGui(TerraFirmaCraft.instance, 40, entityplayer.worldObj, (int)entityplayer.posX, (int)entityplayer.posY, (int)entityplayer.posZ);
		else if(itemstack.getItemDamage() == 1)
			entityplayer.openGui(TerraFirmaCraft.instance, 39, entityplayer.worldObj, (int)entityplayer.posX, (int)entityplayer.posY, (int)entityplayer.posZ);
		return itemstack;
	}

	@Override
	public EnumSize getSize() 
	{
		return EnumSize.SMALL;
	}
	@Override
	public EnumWeight getWeight() 
	{
		return EnumWeight.MEDIUM;
	}

	@Override
	public void addExtraInformation(ItemStack is, EntityPlayer player, List arraylist)
	{
		if (TFC_Core.showExtraInformation()) 
		{
			arraylist.add(EnumChatFormatting.DARK_GRAY + StringUtil.localize("gui.Help") + ":");
			arraylist.add(EnumChatFormatting.WHITE + StringUtil.localize("gui.PotteryBase.Inst0") + " " + 
					EnumChatFormatting.AQUA + StringUtil.localize("gui.RightClick") + " " + 
					EnumChatFormatting.WHITE + StringUtil.localize("gui.PotteryBase.Inst1"));
			arraylist.add( 
					EnumChatFormatting.WHITE + StringUtil.localize("gui.PotteryVesselSmall.Inst0") + " " + 
							EnumChatFormatting.AQUA + StringUtil.localize("gui.RightClick") + " " + 
							EnumChatFormatting.WHITE + StringUtil.localize("gui.PotteryVesselSmall.Inst1"));
		}
		else
		{
			arraylist.add(
					EnumChatFormatting.DARK_GRAY + StringUtil.localize("gui.Help") + ": (" + StringUtil.localize("gui.Armor.Hold") + " " + 
							EnumChatFormatting.GRAY + StringUtil.localize("gui.Armor.Shift") + 
							EnumChatFormatting.DARK_GRAY + ")");
		}
	}

}
