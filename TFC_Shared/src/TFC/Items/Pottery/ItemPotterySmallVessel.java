package TFC.Items.Pottery;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import TFC.Enums.EnumSize;
import TFC.Enums.EnumWeight;

public class ItemPotterySmallVessel extends ItemPotteryBase
{

    public ItemPotterySmallVessel(int id) 
    {
        super(id);
        this.MetaNames = new String[]{"Clay Vessel", "Ceramic Vessel"};
    }

    @Override
    public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag) 
    {
    	super.addInformation(is, player, arraylist, flag);
    }

    	
	@Override
	public EnumSize getSize() 
	{
		return EnumSize.SMALL;
	}
	@Override
	public EnumWeight getWeight() 
	{
		return EnumWeight.LIGHT;
	}

}
