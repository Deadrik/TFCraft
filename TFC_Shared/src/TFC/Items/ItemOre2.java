package TFC.Items;

import net.minecraft.item.ItemStack;

public class ItemOre2 extends ItemTerraBlock
{
	public ItemOre2(int i) 
	{
		super(i);
		setHasSubtypes(true);
		this.MetaNames = new String[]{"Kaolinite", "Gypsum", "Satinspar", "Selenite", "Graphite", "Kimberlite", 
		        "Petrified Wood", "Sulfur", "Jet", "Microcline", "Pitchblende", "Cinnabar", "Cryolite", "Saltpeter", "Serpentine", "Sylvite"};
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
