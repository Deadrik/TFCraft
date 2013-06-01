package TFC.Items.ItemBlocks;

import net.minecraft.client.renderer.texture.IconRegister;
import TFC.API.Constant.Global;
import TFC.API.Enums.EnumSize;

public class ItemPlankBlock extends ItemTerraBlock
{
	public ItemPlankBlock(int i) 
	{
		super(i);
		MetaNames = Global.WOOD_ALL;
	}
	
	@Override
	public void registerIcons(IconRegister registerer)
    {

    }
	
	@Override
	public EnumSize getSize() {
		return EnumSize.MEDIUM;
	}
}
