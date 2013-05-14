package TFC.Items;

import net.minecraft.client.renderer.texture.IconRegister;

public class ItemCustomLeaves extends ItemTerraBlock
{
	public ItemCustomLeaves(int i)
	{
		super(i);
		setMaxDamage(0);
		setHasSubtypes(true);
		MetaNames = new String[]{"Oak","Aspen","Birch","Chestnut","Douglas Fir","Hickory","Maple","Ash","Pine",
				"Sequoia","Spruce","Sycamore","White Cedar","White Elm","Willow","Kapok"};
	}
	
	@Override
	public void registerIcons(IconRegister registerer)
    {

    }
}
