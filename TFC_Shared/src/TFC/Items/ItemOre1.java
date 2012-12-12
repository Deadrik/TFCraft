package TFC.Items;

import TFC.Core.TFC_Textures;
import net.minecraft.src.ItemBlock;
import net.minecraft.src.ItemStack;

public class ItemOre1 extends ItemBlock
{
	public static String[] blockNames = {"Native Copper", "Native Gold", "Native Platinum", "Hematite", "Native Silver", "Cassiterite", "Galena", "Bismuthinite", "Garnierite", 
		"Malachite", "Magnetite", "Limonite", "Sphalerite", "Tetrahedrite", 
		"Bituminous Coal", "Lignite"};


	public ItemOre1(int i) 
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
		return TFC_Textures.RockSheet;
	}
}
