package TFC.Items.ItemBlocks;

import net.minecraft.client.renderer.texture.IconRegister;
import TFC.Blocks.Flora.BlockBerryBush;

public class ItemBerryBush extends ItemTerraBlock
{
	public ItemBerryBush(int i) 
	{
		super(i);
		MetaNames = BlockBerryBush.MetaNames;
	}

	@Override
	public void registerIcons(IconRegister registerer)
	{

	}
}
