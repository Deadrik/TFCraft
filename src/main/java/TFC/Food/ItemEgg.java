package TFC.Food;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import TFC.API.IFood;
import TFC.API.Enums.EnumFoodGroup;
import TFC.Items.ItemTerra;

public class ItemEgg extends ItemFoodTFC implements IFood
{
	public ItemEgg()
	{
		super(-1, EnumFoodGroup.Protein);
	}

	@Override
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List list)
	{
		list.add(createTag(new ItemStack(this, 1), 2));
	}

	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag) 
	{
		ItemTerra.addSizeInformation(is, arraylist);
		arraylist.add(getFoodGroupName(this.getFoodGroup()));
		addHeatInformation(is, arraylist);

		if(is.hasTagCompound())
		{
			if(is.getTagCompound().hasKey("Fertilized"))
				arraylist.add(EnumChatFormatting.GOLD + StatCollector.translateToLocal("gui.fertilized"));
			else
				ItemFoodTFC.addFoodInformation(is, player, arraylist);
		}
	}

	@Override
	public boolean onUpdate(ItemStack is, World world, int x, int y, int z)
	{
		if (is.hasTagCompound())
		{
			if(is.getTagCompound().hasKey("Fertilized"))
			{
				is.stackTagCompound.removeTag("Fertilized");
				is.stackTagCompound.removeTag("Genes");
			}
			if(is.getTagCompound().hasKey("Fertilized"))
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public float getDecayRate()
	{
		return 0.5f;
	}
}
