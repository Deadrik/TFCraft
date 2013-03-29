package TFC.Items;

import net.minecraft.item.ItemStack;

public class ItemOre3 extends ItemTerraBlock
{
	public ItemOre3(int i) 
	{
		super(i);
		setHasSubtypes(true);
		this.MetaNames = new String[]{"Borax", "Olivine", "Lapis Lazuli"};
		this.setFolder("ore/");
	}

	@Override
	public String getItemDisplayName(ItemStack itemstack) 
	{
    	if(MetaNames != null)
    		return new StringBuilder().append(super.getItemDisplayName(itemstack)).append(".").append(MetaNames[itemstack.getItemDamage()]).toString();
		return super.getItemDisplayName(itemstack);
	}
}
