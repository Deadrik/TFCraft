package com.bioxx.tfc.Items.ItemBlocks;

import java.util.List;

import com.bioxx.tfc.TFCBlocks;
import com.bioxx.tfc.api.Constant.Global;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class ItemSand extends ItemTerraBlock
{
	public ItemSand(Block par1)
	{
		super(par1);
	}

	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag)
	{
		super.addInformation(is, player, arraylist, flag);
		int dam = is.getItemDamage();
		if (Block.getBlockFromItem(is.getItem()) == TFCBlocks.Sand2)
			dam += 16;
		arraylist.add(EnumChatFormatting.DARK_GRAY + Global.STONE_ALL[dam]);
	}
}