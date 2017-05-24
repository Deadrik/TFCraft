package com.bioxx.tfc.Items.Pottery;

import java.util.ArrayList;
import java.util.List;

import com.bioxx.tfc.Items.ItemNugget;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.Core.Metal.Alloy;
import com.bioxx.tfc.Core.Metal.AlloyManager;
import com.bioxx.tfc.Core.Metal.AlloyMetal;
import com.bioxx.tfc.Food.ItemFoodTFC;
import com.bioxx.tfc.Items.ItemOre;
import com.bioxx.tfc.Items.ItemOreSmall;
import com.bioxx.tfc.api.Food;
import com.bioxx.tfc.api.Metal;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Enums.EnumWeight;
import com.bioxx.tfc.api.Interfaces.IBag;
import com.bioxx.tfc.api.Interfaces.ISmeltable;
import com.bioxx.tfc.api.Util.Helper;

public class ItemPotterySmallVessel extends ItemPotteryBase implements IBag
{
	@SideOnly(Side.CLIENT)
	private IIcon overlayIcon;

	public ItemPotterySmallVessel()
	{
		super();
		this.metaNames = new String[]{"Clay Vessel", "Ceramic Vessel", "Ceramic Vessel"};
		this.setMaxStackSize(1);
		this.setWeight(EnumWeight.MEDIUM);
		this.setSize(EnumSize.SMALL);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses()
	{
		return true;
	}

	@Override
	public IIcon getIcon(ItemStack stack, int pass)
	{
		if (pass == 1 && stack.getTagCompound() != null && stack.getTagCompound().hasKey("color"))
			return overlayIcon;

		return super.getIcon(stack, pass);
	}

	@Override
	public void registerIcons(IIconRegister registerer)
	{
		super.registerIcons(registerer);
		this.overlayIcon = registerer.registerIcon(Reference.MOD_ID + ":" + textureFolder + "Ceramic Vessel Overlay");
	}

	@Override
	public void getSubItems(Item item, CreativeTabs tabs, List list)
	{
		list.add(new ItemStack(this, 1, 0));
		list.add(new ItemStack(this, 1, 1));
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
		for(int i = 0; bag != null && i < 4; i++)
		{
			if(bag[i] != null)
			{
				if(!(bag[i].getItem() instanceof ItemOreSmall) && !(bag[i].getItem() instanceof ItemOre) && !(bag[i].getItem() instanceof ItemNugget))
					canCookAlloy = false;
			}
		}

		if(is.getItemDamage() == 2)
		{
			NBTTagCompound tag = is.stackTagCompound;
			long totalH =  TFC_Time.getTotalHours();
			tag.setLong("TempTimer", totalH);
		}

		if(canCookAlloy && bag != null)
		{
			Metal[] types = new Metal[4];
			int[] metalAmounts = new int[4];

			if(bag[0] != null)
			{
				types[0] = ((ISmeltable)bag[0].getItem()).getMetalType(bag[0]);
				metalAmounts[0] = ((ISmeltable)bag[0].getItem()).getMetalReturnAmount(bag[0]) * bag[0].stackSize;
			}

			if(bag[1] != null)
			{
				types[1] = ((ISmeltable)bag[1].getItem()).getMetalType(bag[1]);
				metalAmounts[1] = ((ISmeltable)bag[1].getItem()).getMetalReturnAmount(bag[1]) * bag[1].stackSize;

				if(mergeMetals(types[0], types[1], metalAmounts[0], metalAmounts[1]) != metalAmounts[0])
				{
					metalAmounts[0] = mergeMetals(types[0], types[1], metalAmounts[0], metalAmounts[1]);
					types[1] = null;
					metalAmounts[1] = 0;
				}
			}

			if(bag[2] != null)
			{
				types[2] = ((ISmeltable)bag[2].getItem()).getMetalType(bag[2]);
				metalAmounts[2] = ((ISmeltable)bag[2].getItem()).getMetalReturnAmount(bag[2]) * bag[2].stackSize;

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
				types[3] = ((ISmeltable)bag[3].getItem()).getMetalType(bag[3]);
				metalAmounts[3] = ((ISmeltable)bag[3].getItem()).getMetalReturnAmount(bag[3]) * bag[3].stackSize;

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
			/*int numMetals = 0;
			if(metalAmounts[0] > 0)
				numMetals++;
			if(metalAmounts[1] > 0)
				numMetals++;
			if(metalAmounts[2] > 0)
				numMetals++;
			if(metalAmounts[3] > 0)
				numMetals++;*/

			if(total > 0)
			{
				float[] metalPercent = new float[4];
				metalPercent[0] = ((float)metalAmounts[0] / (float)total) * 100f;
				metalPercent[1] = ((float)metalAmounts[1] / (float)total) * 100f;
				metalPercent[2] = ((float)metalAmounts[2] / (float)total) * 100f;
				metalPercent[3] = ((float)metalAmounts[3] / (float)total) * 100f;

				List<AlloyMetal> a = new ArrayList<AlloyMetal>();
				if(types[0] != null)
					a.add(new AlloyMetal(types[0], metalPercent[0]));
				if(types[1] != null)
					a.add(new AlloyMetal(types[1], metalPercent[1]));
				if(types[2] != null)
					a.add(new AlloyMetal(types[2], metalPercent[2]));
				if(types[3] != null)
					a.add(new AlloyMetal(types[3], metalPercent[3]));

				Metal match = AlloyManager.INSTANCE.matchesAlloy(a, furnaceTier);
				if(match != null)
				{
					Alloy output = new Alloy(match, total); 
					NBTTagCompound tag = is.stackTagCompound;
					tag.setString("MetalType", output.outputType.name);
					tag.setInteger("MetalAmount", (int)output.outputAmount);
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
			if(mt0.name.equals(mt1.name))
				return m0 + m1;
		}
		return m0;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack is, int pass)
	{
		if (pass != 1)
		{
			return 0xFFFFFF; // White multiplier does not change the color.
		}
		else
		{
			int j = this.getColor(is);

			if (j < 0) // No NBT flag for color
			{
				return 0xFFFFFF; // White multiplier does not change the color.
			}

			if (is.getItemDamage() == 0) // Unfired Vessels
			{
				/*
				 * Increase each color value by 0x60 so the overlay on unfired vessels is lighter.
				 * Make sure that none of the color values go above 0xFF or else it bleeds into
				 * the other colors.
				 */
				int r = Math.min((j >> 16) + 0x60, 0xFF);
				int b = Math.min(((j >> 8) & 0x00FF) + 0x60, 0xFF); //NOPMD
				int g = Math.min((j & 0x0000FF) + 0x60, 0xFF); //NOPMD
				return (r << 16) | (b << 8) | g;
			}
			else
				return j;
		}
	}

	public int getColor(ItemStack is)
	{
		if (!is.hasTagCompound() || !is.getTagCompound().hasKey("color"))
			return -1;
		else
			return is.getTagCompound().getInteger("color");
	}

	@Override
	public ItemStack[] loadBagInventory(ItemStack is)
	{
		ItemStack[] bag = new ItemStack[4];
		if(is != null && is.hasTagCompound() && is.getTagCompound().hasKey("Items"))
		{
			NBTTagList nbttaglist = is.getTagCompound().getTagList("Items", 10);
			for(int i = 0; i < nbttaglist.tagCount(); i++)
			{
				NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
				byte byte0 = nbttagcompound1.getByte("Slot");
				if(byte0 >= 0 && byte0 < 4)
					bag[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
			}
		}
		else
			return null;

		return bag;
	}

	@Override
	public boolean onUpdate(ItemStack is, World world, int x, int y, int z)
	{
		ItemStack[] bag = loadBagInventory(is);
		if(bag != null)
		{
			TFC_Core.handleItemTicking(bag, world, x, y, z, 0.5f);
			for(ItemStack i : bag)
			{
				if(i != null && i.stackSize == 0)
					i = null;
			}
			saveContents(is, bag);
		}
		return true;
	}

	public void saveContents(ItemStack vessel, ItemStack[] bag) 
	{
		NBTTagList nbttaglist = new NBTTagList();
		for(int i = 0; i < 4; i++)
		{
			if(bag[i] != null)
			{
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte)i);
				bag[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}
		if(vessel != null)
		{
			if(!vessel.hasTagCompound())
				vessel.setTagCompound(new NBTTagCompound());
			vessel.getTagCompound().setTag("Items", nbttaglist);
		}
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
	{
		if(!entityplayer.isSneaking())
		{
			if (itemstack.getItemDamage() == 2)
			{
				NBTTagCompound nbt = itemstack.getTagCompound();
				if (nbt == null)
				{
					itemstack.setItemDamage(1);
					if (!world.isRemote) // Prevent double logging.
					{
						String error = TFC_Core.translate("error.error") + " " + itemstack.getUnlocalizedName() + " " +
										TFC_Core.translate("error.NBT") + " " + TFC_Core.translate("error.Contact");
						TerraFirmaCraft.LOG.error(error);
						TFC_Core.sendInfoMessage(entityplayer, new ChatComponentText(error));
					}
				}
				else if (nbt.hasKey("TempTimer"))
				{
					long temp = nbt.getLong("TempTimer");
					if(TFC_Time.getTotalHours() - temp < 11)
						entityplayer.openGui(TerraFirmaCraft.instance, 19, entityplayer.worldObj, (int)entityplayer.posX, (int)entityplayer.posY, (int)entityplayer.posZ);
				}
			}
			else if (itemstack.getItemDamage() == 1)
			{
				entityplayer.openGui(TerraFirmaCraft.instance, 39, entityplayer.worldObj, (int)entityplayer.posX, (int)entityplayer.posY, (int)entityplayer.posZ);
			}
		}
		return itemstack;
	}

	@Override
	public void addItemInformation(ItemStack is, EntityPlayer player, List<String> arraylist)
	{
		NBTTagCompound tag = is.stackTagCompound;
		if(tag != null)
		{
			if(tag.hasKey("MetalType"))
			{
				String name = tag.getString("MetalType");
				name = name.replace(" ", "");
				name = TFC_Core.translate("gui.metal." + name);

				// check if the vessel contains an amount of metal.
				if(tag.hasKey("MetalAmount"))
				{
					// suffix the amount of metal to the metal name.
					name += " (" + tag.getInteger("MetalAmount")+" "+TFC_Core.translate("gui.units")+")";
				}

				arraylist.add(EnumChatFormatting.DARK_GREEN + name);
			}

			if(tag.hasKey("TempTimer"))
			{
				long total = TFC_Time.getTotalHours();
				long temp = tag.getLong("TempTimer");
				if(total - temp < 11)
					arraylist.add(EnumChatFormatting.WHITE + TFC_Core.translate("gui.ItemHeat.Liquid"));
				else
					arraylist.add(EnumChatFormatting.WHITE + TFC_Core.translate("gui.ItemHeat.Solidified"));
			}

			if(tag.hasKey("Items"))
			{
				NBTTagList nbttaglist = tag.getTagList("Items", 10);
				for(int i = 0; i < nbttaglist.tagCount(); i++)
				{
					NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
					byte byte0 = nbttagcompound1.getByte("Slot");
					if(byte0 >= 0 && byte0 < 4)
					{
						ItemStack itemstack = ItemStack.loadItemStackFromNBT(nbttagcompound1);
						if(itemstack.stackSize > 0)
						{
							if(itemstack.getItem() instanceof ItemFoodTFC)
							{
								float decay = Food.getDecay(itemstack);
								float weight = Helper.roundNumber(Food.getWeight(itemstack), 100);

								String ds = " " + EnumChatFormatting.DARK_GRAY + Helper.roundNumber(decay / weight * 100, 10) + "%";
								if (decay <= 0)
									ds = "";

								arraylist.add(EnumChatFormatting.GOLD.toString() + itemstack.getItem().getItemStackDisplayName(itemstack) + " " + EnumChatFormatting.WHITE+weight + "oz" + ds);
							}
							else
								arraylist.add(EnumChatFormatting.GOLD.toString() + itemstack.stackSize + "x " + itemstack.getItem().getItemStackDisplayName(itemstack));
						}
					}
				}
			}
		}
	}

	@Override
	public void addExtraInformation(ItemStack is, EntityPlayer player, List<String> arraylist)
	{
		if (TFC_Core.showShiftInformation())
		{
			arraylist.add(TFC_Core.translate("gui.Help"));
			arraylist.add(TFC_Core.translate("gui.PotteryBase.Inst0"));

			NBTTagCompound tag = is.stackTagCompound;
			if (tag != null && tag.hasKey("TempTimer"))
			{
				long total = TFC_Time.getTotalHours();
				long temp = tag.getLong("TempTimer");
				if (total - temp < 11)
					arraylist.add(TFC_Core.translate("gui.PotteryVesselSmall.Inst0"));
			}
			else
				arraylist.add(TFC_Core.translate("gui.PotteryVesselSmall.Inst0"));
		}
		else
		{
			arraylist.add(TFC_Core.translate("gui.ShowHelp"));
		}
	}

}
