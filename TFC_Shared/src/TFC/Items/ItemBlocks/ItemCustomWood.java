package TFC.Items.ItemBlocks;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.ItemStack;

public class ItemCustomWood extends ItemTerraBlock
{
	String[] Names = {"Oak Log","Aspen Log","Birch Log","Chestnut Log","Douglas Fir Log","Hickory Log","Maple Log","Ash Log","Pine Log",
			"Sequoia Log","Spruce Log","Sycamore Log","White Cedar Log","White Elm Log","Willow Log","Kapok Log"};
	public ItemCustomWood(int i)
	{
		super(i);
		setMaxDamage(0);
		setHasSubtypes(true);
		setFolder("wood/trees");
	}
	@Override
	public String getItemDisplayName(ItemStack itemstack) 
	{
		String s = new StringBuilder().append(super.getItemDisplayName(itemstack)).append(".").append(Names[itemstack.getItemDamage()]).toString();
		return s;
	}

	@Override
	public int getMetadata(int i)
	{
		return i;
	}

	@Override
	public void registerIcons(IconRegister registerer)
    {

    }
}
