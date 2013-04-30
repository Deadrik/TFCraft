package TFC.Items.ItemBlocks;

import net.minecraft.client.renderer.texture.IconRegister;
import TFC.Enums.EnumSize;

public class ItemPlankBlock extends ItemTerraBlock
{
	public ItemPlankBlock(int i) 
	{
		super(i);
		MetaNames = new String[]{"Oak","Aspen","Birch","Chestnut","Douglas Fir","Hickory","Maple","Ash","Pine",
				"Sequoia","Spruce","Sycamore","White Cedar","White Elm","Willow","Kapok"};
	}
	
	@Override
	public void updateIcons(IconRegister registerer)
    {

    }
	
	@Override
	public EnumSize getSize() {
		return EnumSize.MEDIUM;
	}
}
