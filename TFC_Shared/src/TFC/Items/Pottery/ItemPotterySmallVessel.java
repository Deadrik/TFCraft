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
import TFC.API.IBag;
import TFC.API.ISmeltable;
import TFC.API.Metal;
import TFC.API.Enums.EnumSize;
import TFC.API.Enums.EnumWeight;
import TFC.Core.TFC_Core;
import TFC.Core.TFC_Time;
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
		this.setMaxStackSize(1);
	}

	@Override
	public boolean canStack()
	{
		return false;
	}

	@Override
	public void onDoneCooking(World world, ItemStack is, Alloy.EnumTier furnaceTier)
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

		if(is.getItemDamage() == 2)
		{
			NBTTagCompound tag = is.stackTagCompound;
			long totalH =  TFC_Time.getTotalHours();
			tag.setLong("TempTimer", totalH);
		}

		if(canCookAlloy)
		{
			Metal[] types = new Metal[4];
			int[] metalAmounts = new int[4];

			if(bag[0] != null)
			{
				types[0] = ((ISmeltable)bag[0].getItem()).GetMetalType(bag[0]);
				metalAmounts[0] = ((ISmeltable)bag[0].getItem()).GetMetalReturnAmount(bag[0]) * bag[0].stackSize;
			}

			if(bag[1] != null)
			{
				types[1] = ((ISmeltable)bag[1].getItem()).GetMetalType(bag[1]);
				metalAmounts[1] = ((ISmeltable)bag[1].getItem()).GetMetalReturnAmount(bag[1]) * bag[1].stackSize;

				if(mergeMetals(types[0], types[1], metalAmounts[0], metalAmounts[1]) != metalAmounts[0])
				{
					metalAmounts[0] = mergeMetals(types[0], types[1], metalAmounts[0], metalAmounts[1]);
					types[1] = null;
					metalAmounts[1] = 0;
				}
			}

			if(bag[2] != null)
			{
				types[2] = ((ISmeltable)bag[2].getItem()).GetMetalType(bag[2]);
				metalAmounts[2] = ((ISmeltable)bag[2].getItem()).GetMetalReturnAmount(bag[2]) * bag[2].stackSize;

				if(mergeMetals(types[0], types[2], metalAmounts[0], metalAmounts[2]) != metalAmounts[0])
				{
					metalAmounts[0] = mergeMetals(types[0], types[2], metalAmounts[0], metalAmounts[2]);
					types[2] = null;
					metalAmounts[2] = 0;
				}
				if(mergeMetals(types[1], types[2], metalAmounts[1], metalAmounts[2]) != metalAmounts[1])
				{
					metalAmounts[1] = mergeMetals(types[1], types[2], metalAmounts[1], metalAmounts[2]);
					types[2] = null;
					metalAmounts[2] = 0;
				}
			}
			if(bag[3] != null)
			{
				types[3] = ((ISmeltable)bag[3].getItem()).GetMetalType(bag[3]);
				metalAmounts[3] = ((ISmeltable)bag[3].getItem()).GetMetalReturnAmount(bag[3]) * bag[3].stackSize;

				if(mergeMetals(types[0], types[3], metalAmounts[0], metalAmounts[3]) != metalAmounts[0])
				{
					metalAmounts[0] = mergeMetals(types[0], types[3], metalAmounts[0], metalAmounts[3]);
					types[3] = null;
					metalAmounts[3] = 0;
				}
				if(mergeMetals(types[1], types[3], metalAmounts[1], metalAmounts[3]) != metalAmounts[1])
				{
					metalAmounts[1] = mergeMetals(types[1], types[3], metalAmounts[1], metalAmounts[3]);
					types[3] = null;
					metalAmounts[3] = 0;
				}
				if(mergeMetals(types[2], types[3], metalAmounts[2], metalAmounts[3]) != metalAmounts[2])
				{
					metalAmounts[2] = mergeMetals(types[2], types[3], metalAmounts[2], metalAmounts[3]);
					types[3] = null;
					metalAmounts[3] = 0;
				}
			}
			int total = metalAmounts[0] + metalAmounts[1] + metalAmounts[2] + metalAmounts[3];
			int numMetals = 0;
			if(metalAmounts[0] > 0) {
				numMetals++;
			}
			if(metalAmounts[1] > 0) {
				numMetals++;
			}
			if(metalAmounts[2] > 0) {
				numMetals++;
			}
			if(metalAmounts[3] > 0) {
				numMetals++;
			}

			if(total > 0)
			{
				float[] metalPercent = new float[4];
				metalPercent[0] = ((float)metalAmounts[0] / (float)total) * 100f;
				metalPercent[1] = ((float)metalAmounts[1] / (float)total) * 100f;
				metalPercent[2] = ((float)metalAmounts[2] / (float)total) * 100f;
				metalPercent[3] = ((float)metalAmounts[3] / (float)total) * 100f;

				List<AlloyMetal> a = new ArrayList<AlloyMetal>();
				if(types[0] != null) {
					a.add(new AlloyMetal(types[0], metalPercent[0]));
				}
				if(types[1] != null) {
					a.add(new AlloyMetal(types[1], metalPercent[1]));
				}
				if(types[2] != null) {
					a.add(new AlloyMetal(types[2], metalPercent[2]));
				}
				if(types[3] != null) {
					a.add(new AlloyMetal(types[3], metalPercent[3]));
				}

				Metal match = AlloyManager.instance.matchesAlloy(a, furnaceTier);
				if(match != null)
				{
					Alloy output = new Alloy(match, total); 
					NBTTagCompound tag = is.stackTagCompound;
					tag.setString("MetalType", output.outputType.Name);
					tag.setInteger("MetalAmount", output.outputAmount);
					long totalH =  TFC_Time.getTotalHours();
					tag.setLong("TempTimer", totalH);
					is.getTagCompound().removeTag("Items");
					is.setItemDamage(2);
				}
			}
		}
	}

	private int mergeMetals(Metal mt0, Metal mt1, int m0, int m1)
	{
		if(mt0 != null && mt1 != null && m0 > 0)
		{
			if(mt0.Name.equals(mt1.Name))
			{
				return m0 + m1;
			}
		}
		return m0;
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
		if(!entityplayer.isSneaking())
		{
			NBTTagCompound nbt = itemstack.getTagCompound();

			if(itemstack.getItemDamage() == 2) 
			{
				if(nbt.hasKey("TempTimer"))
				{
					long temp = nbt.getLong("TempTimer");
					if(TFC_Time.getTotalHours() - temp < 11) 
					{
						entityplayer.openGui(TerraFirmaCraft.instance, 19, entityplayer.worldObj, (int)entityplayer.posX, (int)entityplayer.posY, (int)entityplayer.posZ);
					}
				}
			} 
			else if(itemstack.getItemDamage() == 1) {
				entityplayer.openGui(TerraFirmaCraft.instance, 39, entityplayer.worldObj, (int)entityplayer.posX, (int)entityplayer.posY, (int)entityplayer.posZ);
			}
		}
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
	public void addItemInformation(ItemStack is, EntityPlayer player, List arraylist)
	{
		NBTTagCompound tag = is.stackTagCompound;
		if(tag != null)
		{
			if(tag.hasKey("MetalType"))
			{
				String name = tag.getString("MetalType");
				name = name.replace(" ", "");
				arraylist.add(EnumChatFormatting.DARK_GREEN + StringUtil.localize("gui.metal."+name));
			}

			if(tag.hasKey("TempTimer"))
			{
				long total = TFC_Time.getTotalHours();
				long temp = tag.getLong("TempTimer");
				if(total - temp < 11) 
				{
					arraylist.add(EnumChatFormatting.WHITE + StringUtil.localize("gui.ItemHeat.Liquid"));
				}
			}

			if(tag.hasKey("Items"))
			{
				NBTTagList nbttaglist = tag.getTagList("Items");
				for(int i = 0; i < nbttaglist.tagCount(); i++)
				{
					NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.tagAt(i);
					byte byte0 = nbttagcompound1.getByte("Slot");
					if(byte0 >= 0 && byte0 < 4)
					{
						ItemStack itemstack = ItemStack.loadItemStackFromNBT(nbttagcompound1);
						arraylist.add(EnumChatFormatting.GOLD + "" + itemstack.stackSize + "x " + itemstack.getItem().getItemDisplayName(itemstack));
					}
				}
			}
		}
	}

	@Override
	public void addExtraInformation(ItemStack is, EntityPlayer player, List arraylist)
	{
		if (TFC_Core.showExtraInformation()) 
		{
			arraylist.add(StringUtil.localize("gui.Help"));
			arraylist.add(StringUtil.localize("gui.PotteryBase.Inst0"));
			arraylist.add(StringUtil.localize("gui.PotteryVesselSmall.Inst0"));
		}
		else
		{
			arraylist.add(StringUtil.localize("gui.ShowHelp"));
		}
	}

}
