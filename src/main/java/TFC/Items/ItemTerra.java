package TFC.Items;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import TFC.Reference;
import TFC.API.HeatIndex;
import TFC.API.HeatRegistry;
import TFC.API.ISize;
import TFC.API.Enums.EnumSize;
import TFC.API.Enums.EnumWeight;
import TFC.Core.TFCTabs;
import TFC.Core.TFC_ItemHeat;

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
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List list)
	{
		if(MetaNames != null)
			for(int i = 0; i < MetaNames.length; i++)
				list.add(new ItemStack(this,1,i));
		else
			list.add(new ItemStack(this,1));
	}

	@Override
	public int getItemStackLimit()
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
			this.itemIcon = registerer.registerIcon(Reference.ModID + ":" + textureFolder + this.getUnlocalizedName().replace("item.", ""));
		else
		{
			MetaIcons = new IIcon[MetaNames.length];
			for(int i = 0; i < MetaNames.length; i++)
				MetaIcons[i] = registerer.registerIcon(Reference.ModID + ":" + this.textureFolder + MetaNames[i]);
		}
	}

	@Override
	public IIcon getIconFromDamage(int i)
	{
		if(MetaNames != null)
			return MetaIcons[i];
		else
			return this.itemIcon;
	}

	@Override
	public String getUnlocalizedName(ItemStack itemstack)
	{
		if(MetaNames != null && itemstack.getItemDamage() < MetaNames.length)
			return getUnlocalizedName().concat("."+ MetaNames[itemstack.getItemDamage()] + ".name");
		return super.getUnlocalizedName(itemstack);
	}

	@Override
	public boolean getShareTag()
	{
		return true;
	}

	@Override
	public void onUpdate(ItemStack is, World world, Entity entity, int i, boolean isSelected) 
	{
		/*if (is.hasTagCompound())
		{
			NBTTagCompound stackTagCompound = is.getTagCompound();

			if(stackTagCompound.hasKey("temperature"))
				TFC_ItemHeat.HandleItemHeat(is);
			if(is.stackSize <= 0)
				is = null;
		}*/
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
		if(((ISize)object.getItem()).getSize(object)!= null && ((ISize)object.getItem()).getWeight(object) != null)
			arraylist.add("\u2696" + StatCollector.translateToLocal("gui.Weight." + ((ISize)object.getItem()).getWeight(object).getName()) + " \u21F2" + 
					StatCollector.translateToLocal("gui.Size." + ((ISize)object.getItem()).getSize(object).getName().replace(" ", "")));
	}
	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag) 
	{
		//Minecraft.getMinecraft().gameSettings.advancedItemTooltips = false;
		ItemTerra.addSizeInformation(is, arraylist);

		addHeatInformation(is, arraylist);

		if (is.hasTagCompound())
			if(is.getTagCompound().hasKey("itemCraftingValue") && is.getTagCompound().getShort("itemCraftingValue") != 0)
				arraylist.add("This Item Has Been Worked");

		addItemInformation(is, player, arraylist);
		addExtraInformation(is, player, arraylist);
	}

	public void addItemInformation(ItemStack is, EntityPlayer player, List arraylist)
	{
	}

	public static void addHeatInformation(ItemStack is, List arraylist)
	{
		if (is.hasTagCompound())
		{
			NBTTagCompound stackTagCompound = is.getTagCompound();

			if(stackTagCompound.hasKey("temperature"))
			{
				float temp = stackTagCompound.getFloat("temperature");
				float meltTemp = -1;
				HeatIndex hi = HeatRegistry.getInstance().findMatchingIndex(is);
				if(hi != null)
					meltTemp = hi.meltTemp;

				if(meltTemp != -1)
					if(is.getItem() == Items.stick)
						arraylist.add(TFC_ItemHeat.getHeatColorTorch(temp, meltTemp));
					else
						arraylist.add(TFC_ItemHeat.getHeatColor(temp, meltTemp));
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
}
