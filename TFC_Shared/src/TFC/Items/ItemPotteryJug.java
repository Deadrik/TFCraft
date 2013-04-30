package TFC.Items;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import TFC.Core.TFC_ItemHeat;
import TFC.Enums.EnumSize;
import TFC.Enums.EnumWeight;

public class ItemPotteryJug extends ItemPotteryBase
{

    public ItemPotteryJug(int id) 
    {
        super(id);
        this.MetaNames = new String[]{"Clay Jug", "Ceramic Jug"};
    }


    @Override
    public void onUpdate(ItemStack is, World world, Entity entity, int i, boolean isSelected) 
    {
        if (!world.isRemote && is.hasTagCompound())
        {
            NBTTagCompound stackTagCompound = is.getTagCompound();

            if(stackTagCompound.hasKey("temperature"))
            {
                TFC_ItemHeat.HandleItemHeat(is, (int)entity.posX, (int)entity.posY, (int)entity.posZ);
            }
        }
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
