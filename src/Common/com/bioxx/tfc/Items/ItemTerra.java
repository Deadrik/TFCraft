package com.bioxx.tfc.Items;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.TFCItems;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.api.HeatIndex;
import com.bioxx.tfc.api.HeatRegistry;
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

	public String[] MetaNames;
	public IIcon[] MetaIcons;
	public String textureFolder;

	private int craftingXP = 1;

	public ItemTerra()
	{
		super();
		this.setCreativeTab(TFCTabs.TFCMisc);
		textureFolder = "";
		setNoRepair();
	}

	public ItemTerra setMetaNames(String[] metanames)
	{
		MetaNames = metanames;
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

	@Override
	public void getSubItems(Item item, CreativeTabs tabs, List list)
	{
		if(MetaNames != null)
		{
			for(int i = 0; i < MetaNames.length; i++)
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
		if(this.MetaNames == null)
		{
			if(this.iconString != null)
				this.itemIcon = registerer.registerIcon(Reference.ModID + ":" + this.textureFolder + this.getIconString());
			else
				this.itemIcon = registerer.registerIcon(Reference.ModID + ":" + this.textureFolder + this.getUnlocalizedName().replace("item.", ""));
		}
		else
		{
			MetaIcons = new IIcon[MetaNames.length];
			for(int i = 0; i < MetaNames.length; i++)
			{
				MetaIcons[i] = registerer.registerIcon(Reference.ModID + ":" + this.textureFolder + MetaNames[i]);
			}
			
			//This will prevent NullPointerException errors with other mods like NEI
			this.itemIcon = MetaIcons[0];
		}
	}

	@Override
	public IIcon getIconFromDamage(int i)
	{
		if(MetaNames != null && i < MetaNames.length)
			return MetaIcons[i];
		else
			return this.itemIcon;
	}

	@Override
	public String getUnlocalizedName(ItemStack itemstack)
	{
		if(MetaNames != null && itemstack.getItemDamage() < MetaNames.length)
			return getUnlocalizedName().concat("." + MetaNames[itemstack.getItemDamage()]);
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

	public static void addSizeInformation(ItemStack object, List arraylist)
	{
		if(((ISize)object.getItem()).getSize(object)!= null && ((ISize)object.getItem()).getWeight(object) != null && ((ISize)object.getItem()).getReach(object)!= null)
			arraylist.add("\u2696" + StatCollector.translateToLocal("gui.Weight." + ((ISize)object.getItem()).getWeight(object).getName()) + " \u21F2" + 
					StatCollector.translateToLocal("gui.Size." + ((ISize)object.getItem()).getSize(object).getName().replace(" ", "")));
		if(object.getItem() instanceof IEquipable)
		{
			if(((IEquipable)object.getItem()).getEquipType(object) == IEquipable.EquipType.BACK)
			{
				arraylist.add(EnumChatFormatting.LIGHT_PURPLE.toString()+StatCollector.translateToLocal("gui.slot")+ 
						EnumChatFormatting.GRAY.toString()+": " + 
						EnumChatFormatting.WHITE.toString() + StatCollector.translateToLocal("gui.slot.back"));
			}
		}
	}

	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag)
	{
		//Minecraft.getMinecraft().gameSettings.advancedItemTooltips = false;
		ItemTerra.addSizeInformation(is, arraylist);

		addHeatInformation(is, arraylist);

		if (is.hasTagCompound())
		{
			if(is.getTagCompound().hasKey("itemCraftingValue") && is.getTagCompound().getShort("itemCraftingValue") != 0)
				arraylist.add("This Item Has Been Worked");
		}

		addItemInformation(is, player, arraylist);
		addExtraInformation(is, player, arraylist);
	}

	public void addItemInformation(ItemStack is, EntityPlayer player, List arraylist)
	{
		if(	is.getItem() instanceof ItemIngot ||
				is.getItem() instanceof ItemMetalSheet ||
				is.getItem() instanceof ItemUnfinishedArmor ||
				is.getItem() instanceof ItemBloom)
		{
			if(TFC_ItemHeat.HasTemp(is))
			{
				String s = "";
				if(HeatRegistry.getInstance().isTemperatureDanger(is))
				{
					s += EnumChatFormatting.WHITE + StatCollector.translateToLocal("gui.ingot.danger") + " | ";
				}

				if(HeatRegistry.getInstance().isTemperatureWeldable(is))
				{
					s += EnumChatFormatting.WHITE + StatCollector.translateToLocal("gui.ingot.weldable") + " | ";
				}

				if(HeatRegistry.getInstance().isTemperatureWorkable(is))
				{
					s += EnumChatFormatting.WHITE + StatCollector.translateToLocal("gui.ingot.workable");
				}

				if(!s.equals(""))
					arraylist.add(s);
			}
		}
	}

	public static void addHeatInformation(ItemStack is, List arraylist)
	{
		if (is.hasTagCompound())
		{
			if(TFC_ItemHeat.HasTemp(is))
			{
				float temp = TFC_ItemHeat.GetTemp(is);
				float meltTemp = -1;
				HeatIndex hi = HeatRegistry.getInstance().findMatchingIndex(is);
				if(hi != null)
					meltTemp = hi.meltTemp;

				if(meltTemp != -1)
				{
					if(is.getItem() == TFCItems.Stick)
						arraylist.add(TFC_ItemHeat.getHeatColorTorch(temp, meltTemp));
					else
						arraylist.add(TFC_ItemHeat.getHeatColor(temp, meltTemp));
				}
			}
		}
	}

	public void addExtraInformation(ItemStack is, EntityPlayer player, List arraylist)
	{
	}

	@Override
	public Multimap getItemAttributeModifiers()
	{
		Multimap multimap = HashMultimap.create();
		return multimap;
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
