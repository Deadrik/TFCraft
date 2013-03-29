package TFC.Items;

import net.minecraft.client.renderer.texture.IconRegister;

public class ItemIgIn extends ItemTerraBlock
{
	public ItemIgIn(int i) 
	{
		super(i);
		MetaNames = new String[]{"Granite", "Diorite", "Gabbro"};
	}
	
	@Override
	public void updateIcons(IconRegister registerer)
    {

    }
}
