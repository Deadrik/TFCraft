package TFC.Items.ItemBlocks;

import TFC.API.BlockTypes;
import net.minecraft.client.renderer.texture.IconRegister;

public class ItemIgIn extends ItemTerraBlock
{
	public ItemIgIn(int i) 
	{
		super(i);
		MetaNames = BlockTypes.STONE_IGIN;
	}
	
	@Override
	public void registerIcons(IconRegister registerer)
    {

    }
}
