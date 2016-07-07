package com.bioxx.tfc.Items;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.TileEntities.TEAnvil;
import com.bioxx.tfc.api.HeatIndex;
import com.bioxx.tfc.api.HeatRegistry;
import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.TFC_ItemHeat;
import com.bioxx.tfc.api.Enums.EnumItemReach;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Enums.EnumWeight;
import com.bioxx.tfc.api.Interfaces.IEquipable;
import com.bioxx.tfc.api.Interfaces.ISize;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

public class ItemTerra extends Item implements ISize
{
	protected boolean stackable = true;
	protected EnumSize size = EnumSize.TINY;
	protected EnumWeight weight = EnumWeight.LIGHT;

	public String[] metaNames;
	public IIcon[] metaIcons;
	public String textureFolder;

	private int craftingXP = 1;

	public ItemTerra()
	{
		super();
		this.setCreativeTab(TFCTabs.TFC_MISC);
		textureFolder = "";
		setNoRepair();
	}

	public ItemTerra setMetaNames(String[] metanames)
	{
		metaNames = metanames.clone();
		this.hasSubtypes = true;
		return this;
	}

	public ItemTerra setCraftingXP(int m)
	{
		craftingXP = m;
		return this;
	}

	public int getCraftingXP()
	{
		return this.craftingXP;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void getSubItems(Item item, CreativeTabs tabs, List list)
	{
		if(metaNames != null)
		{
			for(int i = 0; i < metaNames.length; i++)
			{
				list.add(new ItemStack(this, 1, i));
			}
		}
		else
		{
			list.add(new ItemStack(this, 1));
		}
	}

	@Override
	public int getItemStackLimit(ItemStack is)
	{
		if(canStack())
			return this.getSize(null).stackSize * getWeight(null).multiplier <= 64 ? this.getSize(null).stackSize * getWeight(null).multiplier : 64;
			else
				return 1;
	}

	public ItemTerra setFolder(String s)
	{
		this.textureFolder = s;
		return this;
	}

	@Override
	public void registerIcons(IIconRegister registerer)
	{
		if(this.metaNames == null)
		{
			if(this.iconString != null)
				this.itemIcon = registerer.registerIcon(Reference.MOD_ID + ":" + this.textureFolder + this.getIconString());
			else
				this.itemIcon = registerer.registerIcon(Reference.MOD_ID + ":" + this.textureFolder + this.getUnlocalizedName().replace("item.", ""));
		}
		else
		{
			metaIcons = new IIcon[metaNames.length];
			for(int i = 0; i < metaNames.length; i++)
			{
				metaIcons[i] = registerer.registerIcon(Reference.MOD_ID + ":" + this.textureFolder + metaNames[i]);
			}
			
			//This will prevent NullPointerException errors with other mods like NEI
			this.itemIcon = metaIcons[0];
		}
	}

	@Override
	public IIcon getIconFromDamage(int i)
	{
		if(metaNames != null && i < metaNames.length)
			return metaIcons[i];
		else
			return this.itemIcon;
	}

	@Override
	public String getUnlocalizedName(ItemStack itemstack)
	{
		if(metaNames != null && itemstack.getItemDamage() < metaNames.length)
			return getUnlocalizedName().concat("." + metaNames[itemstack.getItemDamage()]);
		return super.getUnlocalizedName(itemstack);
	}

	@Override
	public boolean getShareTag()
	{
		return true;
	}

	/**
	 * This is called by inventories in the world to tick things such as temperature and food decay. Override this and 
	 * return true if you want the item to be handled differently than the standard code. True will stop he standard TFC code from running.
	 */
	public boolean onUpdate(ItemStack is, World world, int x, int y, int z)
	{
		return false;
	}

	public static void addSizeInformation(ItemStack object, List<String> arraylist)
	{
		if(((ISize)object.getItem()).getSize(object)!= null && ((ISize)object.getItem()).getWeight(object) != null && ((ISize)object.getItem()).getReach(object)!= null)
			arraylist.add("\u2696" + TFC_Core.translate("gui.Weight." + ((ISize)object.getItem()).getWeight(object).getName()) + " \u21F2" + 
					TFC_Core.translate("gui.Size." + ((ISize)object.getItem()).getSize(object).getName().replace(" ", "")));
		if(object.getItem() instanceof IEquipable)
		{
			if(((IEquipable)object.getItem()).getEquipType(object) == IEquipable.EquipType.BACK)
			{
				arraylist.add(EnumChatFormatting.LIGHT_PURPLE.toString()+TFC_Core.translate("gui.slot")+ 
						EnumChatFormatting.GRAY.toString()+": " + 
						EnumChatFormatting.WHITE.toString() + TFC_Core.translate("gui.slot.back"));
			}
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag)
	{
		//Minecraft.getMinecraft().gameSettings.advancedItemTooltips = false;
		ItemTerra.addSizeInformation(is, arraylist);

		ItemTerra.addHeatInformation(is, arraylist);

		if (is.hasTagCompound())
		{
			NBTTagCompound tag = is.getTagCompound();
			// Use either tag as a failsafe to display the tooltip
			if (tag.hasKey(TEAnvil.ITEM_CRAFTING_VALUE_TAG) || tag.hasKey(TEAnvil.ITEM_CRAFTING_RULE_1_TAG))
				arraylist.add(TFC_Core.translate("gui.ItemWorked"));
		}

		addItemInformation(is, player, arraylist);
		addExtraInformation(is, player, arraylist);
	}

	public void addItemInformation(ItemStack is, EntityPlayer player, List<String> arraylist)
	{
		if(	is.getItem() instanceof ItemIngot ||
				is.getItem() instanceof ItemMetalSheet ||
				is.getItem() instanceof ItemUnfinishedArmor ||
				is.getItem() instanceof ItemBloom ||
				is.getItem() == TFCItems.wroughtIronKnifeHead)
		{
			if(TFC_ItemHeat.hasTemp(is))
			{
				String s = "";
				if(HeatRegistry.getInstance().isTemperatureDanger(is))
				{
					s += EnumChatFormatting.WHITE + TFC_Core.translate("gui.ingot.danger") + " | ";
				}

				if(HeatRegistry.getInstance().isTemperatureWeldable(is))
				{
					s += EnumChatFormatting.WHITE + TFC_Core.translate("gui.ingot.weldable") + " | ";
				}

				if(HeatRegistry.getInstance().isTemperatureWorkable(is))
				{
					s += EnumChatFormatting.WHITE + TFC_Core.translate("gui.ingot.workable");
				}

				if (!"".equals(s))
					arraylist.add(s);
			}
		}
	}

	public static void addHeatInformation(ItemStack is, List<String> arraylist)
	{
		if (is.hasTagCompound())
		{
			if(TFC_ItemHeat.hasTemp(is))
			{
				float temp = TFC_ItemHeat.getTemp(is);
				float meltTemp = -1;
				HeatIndex hi = HeatRegistry.getInstance().findMatchingIndex(is);
				if(hi != null)
					meltTemp = hi.meltTemp;

				if(meltTemp != -1)
				{
					if(is.getItem() == TFCItems.stick)
						arraylist.add(TFC_ItemHeat.getHeatColorTorch(temp, meltTemp));
					else
						arraylist.add(TFC_ItemHeat.getHeatColor(temp, meltTemp));
				}
			}
		}
	}

	public void addExtraInformation(ItemStack is, EntityPlayer player, List<String> arraylist)
	{
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Multimap getItemAttributeModifiers()
	{
		return HashMultimap.create();
	}

	@Override
	public boolean canStack()
	{
		return stackable;
	}

	@Override
	public EnumSize getSize(ItemStack is)
	{
		return size;
	}
	@Override
	public EnumWeight getWeight(ItemStack is)
	{
		return weight;
	}

	public ItemTerra setSize(EnumSize e)
	{
		size = e;
		return this;
	}

	public ItemTerra setWeight(EnumWeight e)
	{
		weight = e;
		return this;
	}

	@Override
	public EnumItemReach getReach(ItemStack is)
	{
		return EnumItemReach.SHORT;
	}
}
