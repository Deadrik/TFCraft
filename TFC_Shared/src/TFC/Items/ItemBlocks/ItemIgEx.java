package TFC.Items.ItemBlocks;

import net.minecraft.client.renderer.texture.IconRegister;

public class ItemIgEx extends ItemTerraBlock
{
	public ItemIgEx(int i) 
	{
		super(i);
		MetaNames = new String[]{"Rhyolite", "Basalt", "Andesite", "Dacite"};
	}
	
	@Override
	public void registerIcons(IconRegister registerer)
    {

    }
}
