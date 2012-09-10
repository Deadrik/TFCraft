package TFC.Items;

import TFC.Enums.EnumSize;
import net.minecraft.src.*;

public class ItemTerraBlock extends ItemBlock
{
	public String[] blockNames;
	public EnumSize size;
	
	public ItemTerraBlock(int par1) 
	{
		super(par1);
		size = EnumSize.MEDIUM;
		setHasSubtypes(true);
	}
	
	@Override
	public String getItemNameIS(ItemStack itemstack) 
	{
		String s = new StringBuilder().append(super.getItemName()).append(".").append(blockNames[itemstack.getItemDamage()]).toString();
		return s;
	}
	
	@Override
	public int getMetadata(int i) 
	{		
		return i;
	}
}