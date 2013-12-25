package TFC.Items.ItemBlocks;

import net.minecraft.client.renderer.texture.IconRegister;

public class ItemFlora extends ItemTerraBlock
{
	public ItemFlora(int i) 
	{
		super(i);
		MetaNames = new String[]{"Golden Rod", "Cat Tails"};
	}

	@Override
	public void registerIcons(IconRegister registerer)
	{

	}
}
