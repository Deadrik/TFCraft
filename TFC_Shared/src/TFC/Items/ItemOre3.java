package TFC.Items;

import net.minecraft.src.ItemBlock;
import net.minecraft.src.ItemStack;

public class ItemOre3 extends ItemBlock
{
	public static String[] blockNames = {"Borax", "Olivine", "LapisLazuli"};


	public ItemOre3(int i) 
	{
		super(i);
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

	@Override
	public String getTextureFile()
	{
		return "/bioxx/terraRock.png";
	}
}
