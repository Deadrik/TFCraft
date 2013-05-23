package TFC.Items.ItemBlocks;

import TFC.API.BlockTypes;
import net.minecraft.client.renderer.texture.IconRegister;

public class ItemSed extends ItemTerraBlock
{
	public ItemSed(int i) 
	{
		super(i);
		MetaNames = BlockTypes.STONE_SED;
	}
	@Override
	public void registerIcons(IconRegister registerer)
    {

    }
}
