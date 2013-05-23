package TFC.Items.ItemBlocks;

import TFC.API.BlockTypes;
import net.minecraft.client.renderer.texture.IconRegister;

public class ItemMM extends ItemTerraBlock
{	
	public ItemMM(int i) 
	{
		super(i);
		MetaNames = BlockTypes.STONE_MM;
	}
	
	@Override
	public void registerIcons(IconRegister registerer)
    {

    }
}
