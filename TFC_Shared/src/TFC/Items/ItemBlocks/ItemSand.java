package TFC.Items.ItemBlocks;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import TFC.TFCBlocks;
import TFC.API.Constant.Global;

public class ItemSand extends ItemTerraBlock
{
	public ItemSand(int i) 
	{
		super(i);
	}
	
	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag)
	{
		super.addInformation(is, player, arraylist, flag);
		int dam = is.getItemDamage();
		if (is.itemID == TFCBlocks.Sand2.blockID)
			dam += 16;
		arraylist.add(EnumChatFormatting.DARK_GRAY + Global.STONE_ALL[dam]);
	}
}
