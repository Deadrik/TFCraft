package TFC.Items;

import net.minecraft.src.ItemBlock;
import net.minecraft.src.ItemStack;
import net.minecraft.src.forge.ITextureProvider;

public class ItemTerraDirt extends ItemBlock implements ITextureProvider
{
	public static String[] blockNames = {"Granite", "Diorite", "Gabbro", 
		"Siltstone", "Mudstone", "Shale", "Claystone", "Rock Salt", "Limestone", "Conglomerate", "Dolomite", "Chert", 
		"Chalk", "Rhyolite", "Basalt", "Andesite", "Dacite", 
		"Quartzite", "Slate", "Phyllite", "Schist", "Gneiss", "Marble"};


	public ItemTerraDirt(int i) 
	{
		super(i);
		setHasSubtypes(true);
	}

	@Override
	public String getItemNameIS(ItemStack itemstack) 
	{
		String s = new StringBuilder().append(super.getItemName()).append(".").append(blockNames[itemstack.getItemDamage()]).toString();
		return s;
	}
	@Override
	public int getMetadata(int i) 
	{		
		return i;
	}

	@Override
	public String getTextureFile()
	{
		return "/bioxx/terrablocks2.png";
	}
}
