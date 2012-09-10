package TFC.Items;

import java.util.List;

import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

public class ItemOre extends ItemTerra
{
	public static String[] blockNames = {"Native Copper", "Native Gold", "Native Platinum", "Hematite", "Native Silver", "Cassiterite", "Galena", "Bismuthinite", "Garnierite", 
		"Malachite", "Magnetite", "Limonite", "Sphalerite", "Tetrahedrite", 
		"Bituminous Coal", "Lignite", "Kaolinite", "Gypsum", "Satinspar", "Selenite", "Graphite", "Kimberlite", 
		/*22*/"Petrified Wood", "Sulfur", "Jet", "Microcline", "Pitchblende", "Cinnabar", "Cryolite", "Saltpeter", "Serpentine", "Sylvite", 
		/*32*/"Borax", "Olivine", "LapisLazuli", "GalenaPartial", "TetrahedritePartial", "MagnetitePartial"};


	public ItemOre(int i) 
	{
		super(i);
		setMaxDamage(0);
		setHasSubtypes(true);
		this.maxStackSize = 8;
	}

	@Override
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List list)
	{
		for(int i = 0; i < blockNames.length; i++) {
			list.add(new ItemStack(this,1,i));
		}
	}

	public int getIconFromDamage(int par1)
	{
	    if(par1 == 35)
	        return this.iconIndex+6;
	    else if(par1 == 36)
            return this.iconIndex+13;
	    else if(par1 == 37)
            return this.iconIndex+10;
	    
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
