package net.minecraft.src.TFC_Core;

import net.minecraft.src.ItemBlock;
import net.minecraft.src.ItemStack;
import net.minecraft.src.forge.ITextureProvider;

public class ItemTerraRockCobble extends ItemBlock implements ITextureProvider
{
	public static String[] blockNames = {"Granite", "Diorite", "Gabbro", 
		"Siltstone", "Mudstone", "Shale", "Claystone", "Rock Salt", "Limestone", "Conglomerate", "Dolomite", "Chert", 
		"Chalk", "Rhyolite", "Basalt", "Andesite", "Dacite", 
		"Quartzite", "Slate", "Phyllite", "Schist", "Gneiss", "Marble", 
		"Native Copper", "Native Gold", "Native Platinum", "Hematite", "Native Silver", "Cassiterite", "Galena", "Bismuthinite", "Garnierite", 
		"Malachite", "Magnetite", "Limonite", "Sphalerite", "Tetrahedrite", 
		"Bituminous Coal", "Lignite", "Kaolinite", "Gypsum", "Satinspar", "Selenite", "Graphite", "Kimberlite", 
		"Petrified Wood", "Sulfur", "Jet", "Microcline", "Pitchblende", "Cinnabar", "Cryolite", "Saltpeter", "Serpentine", "Sylvite", 
		"Borax", "Olivine", "LapisLazuli"};


	public ItemTerraRockCobble(int i) 
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
