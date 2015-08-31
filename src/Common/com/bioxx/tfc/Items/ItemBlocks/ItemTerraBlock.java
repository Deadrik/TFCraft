package com.bioxx.tfc.Items.ItemBlocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Items.ItemTerra;
import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.TFC_ItemHeat;
import com.bioxx.tfc.api.Enums.EnumItemReach;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Enums.EnumWeight;
import com.bioxx.tfc.api.Interfaces.ISize;

public class ItemTerraBlock extends ItemBlock implements ISize
{
	public String[] metaNames;
	public IIcon[] icons;
	public String folder;

	public ItemTerraBlock(Block b)
	{
		super(b);
		this.setHasSubtypes(true);
		this.folder = "";
	}

	public ItemTerraBlock setFolder(String f)
	{
		folder = f;
		return this;
	}

	@Override
	public String getUnlocalizedName(ItemStack is)
	{
		try
		{
			if(metaNames != null && is.getItemDamage() < metaNames.length)
				return getUnlocalizedName().concat("." + metaNames[is.getItemDamage()]);
		}
		catch(Exception ex)
		{
			TerraFirmaCraft.LOG.error(ex.getLocalizedMessage());
		}

		return super.getUnlocalizedName(is);
	}

	/**
	 * This is called by inventories in the world to tick things such as temperature and food decay. Override this and 
	 * return true if you want the item to be handled differently than the standard code. True will stop he standard TFC code from running.
	 */
	public boolean onUpdate(ItemStack is, World world, int x, int y, int z)
	{
		return false;
	}

	@Override
	public int getMetadata(int i)
	{
		return i;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag)
	{
		ItemTerra.addSizeInformation(is, arraylist);

		if (is.hasTagCompound())
		{
			if(TFC_ItemHeat.hasTemp(is))
			{
				float temp = TFC_ItemHeat.getTemp(is);
				float meltTemp = TFC_ItemHeat.isCookable(is);

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

	@Override
	public boolean getShareTag()
	{
		return true;
	}

	@Override
	public int getItemStackLimit(ItemStack is)
	{
		if(canStack())
			return this.getSize(null).stackSize * getWeight(null).multiplier;
		else
			return 1;
	}

	@Override
	public EnumSize getSize(ItemStack is)
	{
		return EnumSize.VERYSMALL;
	}

	@Override
	public boolean canStack()
	{
		return true;
	}

	@Override
	public EnumWeight getWeight(ItemStack is)
	{
		return EnumWeight.HEAVY;
	}

	@Override
	public void registerIcons(IIconRegister registerer)
	{
	}

	@Override
	public EnumItemReach getReach(ItemStack is)
	{
		return EnumItemReach.SHORT;
	}
}