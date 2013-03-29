package TFC.Items;

import net.minecraft.item.ItemStack;

public class ItemOre1 extends ItemTerraBlock
{
	public ItemOre1(int i) 
	{
		super(i);
		setHasSubtypes(true);
		this.MetaNames = new String[]{"Native Copper", "Native Gold", "Native Platinum", "Hematite", "Native Silver", "Cassiterite", "Galena", "Bismuthinite", "Garnierite", 
				"Malachite", "Magnetite", "Limonite", "Sphalerite", "Tetrahedrite", 
				"Bituminous Coal", "Lignite"};
		this.setFolder("ore/");
	}

	@Override
	public String getItemDisplayName(ItemStack itemstack) 
	{
    	if(MetaNames != null)
    		return new StringBuilder().append(super.getItemDisplayName(itemstack)).append(".").append(MetaNames[itemstack.getItemDamage()]).toString();
		return super.getItemDisplayName(itemstack);
	}
	
	@Override
	public int getMetadata(int i) 
	{		
		return i;
	}
}
