package TFC.Items.ItemBlocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import TFC.API.Constant.Global;

public class ItemCustomLeaves extends ItemTerraBlock
{
	public ItemCustomLeaves(Block par1)
	{
		super(par1);
		setMaxDamage(0);
		setHasSubtypes(true);
		MetaNames = Global.WOOD_ALL;
	}
	
	@Override
	public void registerIcons(IIconRegister registerer)
    {

    }
}
