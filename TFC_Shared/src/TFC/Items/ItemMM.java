package TFC.Items;

import net.minecraft.client.renderer.texture.IconRegister;

public class ItemMM extends ItemTerraBlock
{	
	public ItemMM(int i) 
	{
		super(i);
		MetaNames = new String[]{ "Quartzite", "Slate", "Phyllite", "Schist", "Gneiss", "Marble"};
	}
	
	@Override
	public void registerIcons(IconRegister registerer)
    {

    }
}
