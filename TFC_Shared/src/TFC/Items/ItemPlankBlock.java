package TFC.Items;

import TFC.Enums.EnumSize;
import net.minecraft.src.ItemBlock;
import net.minecraft.src.ItemStack;

public class ItemPlankBlock extends ItemTerraBlock
{
	{blockNames = new String[]{"Oak","Aspen","Birch","Chestnut","Douglas Fir","Hickory","Maple","Ash","Pine",
			"Sequoia","Spruce","Sycamore","White Cedar","White Elm","Willow","Kapok"};}


	public ItemPlankBlock(int i) 
	{
		super(i);
	}
	
	@Override
	public EnumSize getSize() {
		return EnumSize.MEDIUM;
	}

	@Override
	public String getTextureFile()
	{
		return "/bioxx/terrablocks.png";
	}
}
