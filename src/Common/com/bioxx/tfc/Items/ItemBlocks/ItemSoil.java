package com.bioxx.tfc.Items.ItemBlocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import com.bioxx.tfc.Blocks.Terrain.BlockPeat;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.Constant.Global;

public class ItemSoil extends ItemTerraBlock
{
	private IIcon icon;

	public ItemSoil(Block b)
	{
		super(b);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag)
	{
		super.addInformation(is, player, arraylist, flag);

		Block b = Block.getBlockFromItem(is.getItem());
		if (TFC_Core.isPeat(b) || TFC_Core.isPeatGrass(b))
			return;

		int dam = is.getItemDamage();
		if (b == TFCBlocks.dirt2
				|| b == TFCBlocks.sand2
				|| b == TFCBlocks.clay2
				|| TFC_Core.isGrassType2(b)
				|| b == TFCBlocks.tilledSoil2
				|| b == TFCBlocks.gravel2)
		{
			dam += 16;
		}

		if (dam < Global.STONE_ALL.length)
			arraylist.add(EnumChatFormatting.DARK_GRAY + Global.STONE_ALL[dam]);
		else
			arraylist.add(EnumChatFormatting.DARK_RED + "Unknown");
	}

	@Override
	public void registerIcons(IIconRegister registerer)
	{
		if (this.field_150939_a/*Block*/ instanceof BlockPeat)
		{
			String s = this.field_150939_a.getItemIconName();

			if (s != null)
			{
				icon = registerer.registerIcon(s);
			}

		}
	}

	/**
	 * Gets an icon index based on an item's damage value
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int damage)
	{
		if (this.field_150939_a/*Block*/instanceof BlockPeat)
		{
			return icon != null ? icon : this.field_150939_a.getBlockTextureFromSide(1);
		}
		else
			return super.getIconFromDamage(damage);
	}

}
