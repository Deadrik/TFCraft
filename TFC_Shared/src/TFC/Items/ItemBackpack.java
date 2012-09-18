package TFC.Items;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;
import TFC.Enums.EnumSize;

public class ItemBackpack extends ItemTerra
{
	public ItemBackpack(int id) 
	{
		super(id, "/bioxx/terrasprites.png");
	}
	
	@Override
	public void onCreated(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) 
	{
		//Add the itemstacks for the inventory.
	}
	
	@Override
	public EnumSize getSize() {
		return EnumSize.HUGE;
	}

}
