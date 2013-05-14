package TFC.Items.ItemBlocks;

import net.minecraft.client.renderer.texture.IconRegister;

public class ItemSed extends ItemTerraBlock
{
	public ItemSed(int i) 
	{
		super(i);
		MetaNames = new String[]{"Siltstone", "Mudstone", "Shale", "Claystone", "Rock Salt", "Limestone", "Conglomerate", "Dolomite", "Chert", "Chalk"};
	}
	@Override
	public void registerIcons(IconRegister registerer)
    {

    }
}
