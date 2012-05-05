package net.minecraft.src.TFC_Core;

import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.forge.ITextureProvider;

public class ItemTerraSmallOre extends Item implements ITextureProvider
{
	public static String[] blockNames = {"Native Copper", "Native Gold", "Native Platinum", "Hematite", "Native Silver", "Cassiterite", "Galena", "Bismuthinite", "Garnierite", 
		"Malachite", "Magnetite", "Limonite", "Sphalerite", "Tetrahedrite", 
		"Bituminous Coal", "Lignite", "Kaolinite", "Gypsum", "Satinspar", "Selenite", "Graphite", "Kimberlite", 
		"Petrified Wood", "Sulfur", "Jet", "Microcline", "Pitchblende", "Cinnabar", "Cryolite", "Saltpeter", "Serpentine", "Sylvite", 
		"Borax", "Olivine", "LapisLazuli"};


	public ItemTerraSmallOre(int i) 
	{
		super(i);
		setMaxDamage(0);
		setHasSubtypes(true);
		this.maxStackSize = 8;
	}

	@Override
	public void addCreativeItems(java.util.ArrayList list)
	{
		for(int i = 0; i < blockNames.length; i++) {
			list.add(new ItemStack(this,1,i));
		}
	}

	public int getIconFromDamage(int par1)
	{
		return this.iconIndex+par1;
	}

	@Override
	public String getItemNameIS(ItemStack itemstack) 
	{
		String s = new StringBuilder().append(super.getItemName()).append(".").append(blockNames[itemstack.getItemDamage()]).toString();
		return s;
	}
	@Override
	public String getTextureFile()
	{
		return "/bioxx/terrasprites2.png";
	}
	
    public static String getItemNameDamage(int d) 
    {
        String s = blockNames[d];
        return s;
    }

}
