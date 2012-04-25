package net.minecraft.src.TFC_Core;

import net.minecraft.src.ItemBlock;
import net.minecraft.src.ItemStack;
import net.minecraft.src.forge.ITextureProvider;

public class ItemTerraPlanks extends ItemBlock implements ITextureProvider
{
	String[] Names = {"Oak","Aspen","Birch","Chestnut","Douglas Fir","Hickory","Maple","Ash","Pine",
			"Sequoia","Spruce","Sycamore","White Cedar","White Elm","Willow","Kapok"};


	public ItemTerraPlanks(int i) 
	{
		super(i);
		setHasSubtypes(true);
	}

	@Override
	public String getItemNameIS(ItemStack itemstack) 
	{
		String s = new StringBuilder().append(super.getItemName()).append(".").append(Names[itemstack.getItemDamage()]).toString();
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
		return "/bioxx/terrablocks.png";
	}
}
