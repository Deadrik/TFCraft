package TFC.Items;

import TFC.Core.TFC_Textures;
import net.minecraft.src.ItemBlock;
import net.minecraft.src.ItemStack;

public class ItemOre2 extends ItemBlock
{
	public static String[] blockNames = {"Kaolinite", "Gypsum", "Satinspar", "Selenite", "Graphite", "Kimberlite", 
        "Petrified Wood", "Sulfur", "Jet", "Microcline", "Pitchblende", "Cinnabar", "Cryolite", "Saltpeter", "Serpentine", "Sylvite"};


	public ItemOre2(int i) 
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
		return TFC_Textures.RockSheet;
	}
}
