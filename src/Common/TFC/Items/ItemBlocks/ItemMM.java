package TFC.Items.ItemBlocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import TFC.API.Constant.Global;

public class ItemMM extends ItemTerraBlock
{	
	public ItemMM(Block par1) 
	{
		super(par1);
		MetaNames = Global.STONE_MM;
	}
	
	@Override
	public void registerIcons(IIconRegister registerer)
    {

    }
}
