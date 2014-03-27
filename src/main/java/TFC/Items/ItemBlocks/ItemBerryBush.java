package TFC.Items.ItemBlocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import TFC.Blocks.Flora.BlockBerryBush;

public class ItemBerryBush extends ItemTerraBlock
{
	public ItemBerryBush(Block par1) 
	{
		super(par1);
		MetaNames = BlockBerryBush.MetaNames;
	}

	@Override
	public void registerIcons(IIconRegister registerer)
	{

	}
}
