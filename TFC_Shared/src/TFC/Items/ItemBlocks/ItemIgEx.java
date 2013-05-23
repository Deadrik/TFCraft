package TFC.Items.ItemBlocks;

import TFC.API.BlockTypes;
import net.minecraft.client.renderer.texture.IconRegister;

public class ItemIgEx extends ItemTerraBlock
{
	public ItemIgEx(int i) 
	{
		super(i);
		MetaNames = BlockTypes.STONE_IGEX;
	}
	
	@Override
	public void registerIcons(IconRegister registerer)
    {

    }
}
