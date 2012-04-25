package net.minecraft.src.TFC_Core;

import net.minecraft.src.*;

public class ItemCustomLeaves extends ItemBlock
{
	String[] Names = {"Oak","Aspen","Birch","Chestnut","Douglas Fir","Hickory","Maple","Ash","Pine",
			"Sequoia","Spruce","Sycamore","White Cedar","White Elm","Willow","Kapok"};

	public ItemCustomLeaves(int i)
	{
		super(i);
		setMaxDamage(0);
		setHasSubtypes(true);
	}

	@Override
	public String getItemNameIS(ItemStack itemstack) 
	{
		String s = new StringBuilder().append(super.getItemName()).append(".").append(Names[itemstack.getItemDamage()]).toString();
		return s;
	}

	public int getMetadata(int i)
	{
		return i;
	}

	//    public int getColorFromDamage(int i, int j)
	//    {
	//        if (i == 12 || i == 7)
	//        {
	//            return ColorizerFoliage.getFoliageColorPine();
	//        }
	//        if (i == 2)
	//        {
	//            return ColorizerFoliage.getFoliageColorBirch();
	//        }
	//        else
	//        {
	//            return ColorizerFoliage.getFoliageColorBasic();
	//        }
	//    }
}
