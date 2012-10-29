package TFC.Items;

import TFC.*;
import net.minecraft.src.ItemBlock;
import net.minecraft.src.ItemStack;

public class ItemSapling extends ItemBlock
{
	String[] Names = {"Oak","Aspen","Birch","Chestnut","Douglas Fir","Hickory","Maple","Ash","Pine",
			"Sequoia","Spruce","Sycamore","White Cedar","White Elm","Willow","Kapok"};

	public ItemSapling(int i)
	{
		super(i);
		setMaxDamage(0);
		setHasSubtypes(true);
	}

	public int getIconFromDamage(int i)
	{
		return 160+i;
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

	@Override
	public String getTextureFile()
	{
		return "/bioxx/terrablocks.png";
	}
}
