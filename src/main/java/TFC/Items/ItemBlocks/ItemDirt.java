package TFC.Items.ItemBlocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import TFC.TFCBlocks;
import TFC.API.Constant.Global;

public class ItemDirt extends ItemTerraBlock
{
	public ItemDirt(Block par1) 
	{
		super(par1);
	}

	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag)
	{
		super.addInformation(is, player, arraylist, flag);
		int dam = is.getItemDamage();
		if (Block.getBlockFromItem(is.getItem()) == TFCBlocks.Dirt2)
			dam += 16;
		if (dam < 21)
			arraylist.add(EnumChatFormatting.DARK_GRAY + Global.STONE_ALL[dam]);
		else
			arraylist.add(EnumChatFormatting.DARK_RED + "Unknown");
	}
}