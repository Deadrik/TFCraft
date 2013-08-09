package TFC.Items;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import TFC.Reference;
import TFC.API.HeatIndex;
import TFC.API.HeatRegistry;
import TFC.API.ISize;
import TFC.API.TFCTabs;
import TFC.API.Enums.EnumSize;
import TFC.API.Enums.EnumWeight;
import TFC.Core.TFC_ItemHeat;
import TFC.Core.Util.StringUtil;

public class ItemTerra extends Item implements ISize
{
	protected boolean stackable = true;
	public String[] MetaNames;
	public Icon[] MetaIcons;
	public String textureFolder;

	public ItemTerra(int id) 
	{
		super(id);
		this.setCreativeTab(TFCTabs.TFCMaterials);
		textureFolder = "";
	}

	public ItemTerra setMetaNames(String[] metanames)
	{
		MetaNames = metanames;
		return this;
	}

	@Override
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List list)
	{
		if(MetaNames != null)
		{
			for(int i = 0; i < MetaNames.length; i++) 
			{
				list.add(new ItemStack(this,1,i));
			}
		}
		else
		{
			list.add(new ItemStack(this,1));
		}
	}

	@Override
	public int getItemStackLimit()
	{
		if(canStack())
			return this.getSize().stackSize * getWeight().multiplier <= 64 ? this.getSize().stackSize * getWeight().multiplier : 64;
			else
				return 1;
	}

	public ItemTerra setFolder(String s)
	{
		this.textureFolder = s;
		return this;
	}

	@Override
	public void registerIcons(IconRegister registerer)
	{
		if(this.MetaNames == null)
			this.itemIcon = registerer.registerIcon(Reference.ModID + ":" + textureFolder + this.getUnlocalizedName().replace("item.", ""));
		else
		{
			MetaIcons = new Icon[MetaNames.length];
			for(int i = 0; i < MetaNames.length; i++) 
			{
				MetaIcons[i] = registerer.registerIcon(Reference.ModID + ":" + this.textureFolder + MetaNames[i]);
			}
		}
	}
	
	@Override
	public Icon getIconFromDamage(int i)
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
			return getUnlocalizedName().concat("."+ MetaNames[itemstack.getItemDamage()]);
		return super.getUnlocalizedName(itemstack);
	}

	@Override
	public String getItemDisplayName(ItemStack itemstack) 
	{
		return StringUtil.localize(getUnlocalizedName(itemstack).replace(" ", ""));
	}

	@Override
	public boolean getShareTag()
	{
		return true;
	}

	@Override
	public void onUpdate(ItemStack is, World world, Entity entity, int i, boolean isSelected) 
	{
		if(is.stackSize == 0)
			is.stackSize = 1;
		if (!world.isRemote && is.hasTagCompound())
		{
			NBTTagCompound stackTagCompound = is.getTagCompound();

			if(stackTagCompound.hasKey("temperature"))
			{
				TFC_ItemHeat.HandleItemHeat(is, (int)entity.posX, (int)entity.posY, (int)entity.posZ);
			}
		}
	}

	public static void addSizeInformation(ISize object, List arraylist)
	{
		if(object.getSize()!= null && object.getWeight() != null)
			arraylist.add("\u2696" + StringUtil.localize("gui.Weight." + object.getWeight().getName()) + " \u21F2" + StringUtil.localize("gui.Size." + object.getSize().getName().replace(" ", "")));
	}
	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag) 
	{
		//Minecraft.getMinecraft().gameSettings.advancedItemTooltips = false;
		ItemTerra.addSizeInformation(this, arraylist);

		addHeatInformation(is, arraylist);


		if (is.hasTagCompound())
		{
			if(is.getTagCompound().hasKey("itemCraftingValue") && is.getTagCompound().getByte("itemCraftingValue") != 0)
			{
				arraylist.add("This Item Has Been Worked");
			}
		}

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
				{
					meltTemp = hi.meltTemp;
				}

				if(meltTemp != -1)
				{
					if(is.itemID == Item.stick.itemID)
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

	public static String getRuleFromId(int id)
	{
		switch(id)
		{
		case 0:
			return "Hit";
		case 1:
			return "Draw";
		case 2:
			return "Quench";
		case 3:
			return "Punch";
		case 4:
			return "Bend";
		case 5:
			return "Upset";
		case 6:
			return "Shrink";
		case 7:
			return "Weld";
		default:
			return "";
		}
	}

	@Override
	public boolean canStack() 
	{
		return stackable;
	}

	@Override
	public EnumSize getSize() 
	{
		return EnumSize.TINY;
	}
	@Override
	public EnumWeight getWeight() 
	{
		return EnumWeight.MEDIUM;
	}

}
