package TFC.Items.Pottery;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import TFC.API.Enums.EnumSize;
import TFC.API.Enums.EnumWeight;

public class ItemPotteryPot extends ItemPotteryBase
{

	public ItemPotteryPot() 
	{
		super();
		this.MetaNames = new String[]{"Clay Pot", "Ceramic Pot"};
		this.setWeight(EnumWeight.LIGHT);
		this.setSize(EnumSize.SMALL);
	}

	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag) 
	{
		super.addInformation(is, player, arraylist, flag);
		if(is.hasTagCompound() && is.stackTagCompound.hasKey("LiquidType"))
		{
			arraylist.add(is.stackTagCompound.getString("LiquidType"));
		}
	}
}
