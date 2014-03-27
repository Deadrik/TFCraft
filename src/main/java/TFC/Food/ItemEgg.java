package TFC.Food;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import TFC.Core.Util.StringUtil;
import TFC.Items.ItemTerra;

public class ItemEgg extends ItemTerra
{
	public ItemEgg()
	{
		super();
	}

	@Override
	public void addItemInformation(ItemStack is, EntityPlayer player, List arraylist)
	{
		if(is.hasTagCompound())
			if(is.getTagCompound().hasKey("Fertilized"))
				arraylist.add(EnumChatFormatting.GOLD + StringUtil.localize("gui.fertilized"));
	}

	@Override
	public void onUpdate(ItemStack is, World world, Entity entity, int i, boolean isSelected) 
	{
		super.onUpdate(is, world, entity, i, isSelected);
		if (is.hasTagCompound())
		{
			if(is.getTagCompound().hasKey("Fertilized"))
			{
				is.stackTagCompound.removeTag("Fertilized");
				is.stackTagCompound.removeTag("Genes");
			}
			if(!is.getTagCompound().hasNoTags())
			{
				is.stackTagCompound = null;
			}
		}
	}
}
