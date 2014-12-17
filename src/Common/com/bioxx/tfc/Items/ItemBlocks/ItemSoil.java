package com.bioxx.tfc.Items.ItemBlocks;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import com.bioxx.tfc.TFCBlocks;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.api.Constant.Global;

public class ItemSoil extends ItemTerraBlock
{
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
		int dam = is.getItemDamage();
		if (b == TFCBlocks.Dirt2
				|| b == TFCBlocks.Sand2
				|| b == TFCBlocks.Clay2
				|| TFC_Core.isGrassType2(b)
				|| b == TFCBlocks.tilledSoil2
				|| b == TFCBlocks.Gravel2)
		{
			dam += 16;
		}

		if (dam < Global.STONE_ALL.length)
			arraylist.add(EnumChatFormatting.DARK_GRAY + Global.STONE_ALL[dam]);
		else
			arraylist.add(EnumChatFormatting.DARK_RED + "Unknown");
	}
}
