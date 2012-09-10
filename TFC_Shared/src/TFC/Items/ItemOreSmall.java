package TFC.Items;

import java.util.List;

import net.minecraft.src.CreativeTabs;
import net.minecraft.src.ItemStack;

public class ItemOreSmall extends ItemOre
{
    public static String[] blockNames = {"Native Copper", "Native Gold", "Native Platinum", "Hematite", "Native Silver", "Cassiterite", "Galena", "Bismuthinite", "Garnierite", 
        "Malachite", "Magnetite", "Limonite", "Sphalerite", "Tetrahedrite", 
        "Bituminous Coal", "Lignite", "Kaolinite", "Gypsum", "Satinspar", "Selenite", "Graphite", "Kimberlite", 
        /*22*/"Petrified Wood", "Sulfur", "Jet", "Microcline", "Pitchblende", "Cinnabar", "Cryolite", "Saltpeter", "Serpentine", "Sylvite", 
        /*32*/"Borax", "Olivine", "LapisLazuli", "GalenaPartial", "TetrahedritePartial", "MagnetitePartial"};

    public ItemOreSmall(int i)
    {
        super(i);
    }

    @Override
    public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List list)
    {
        for(int i = 0; i < 14; i++) {
            list.add(new ItemStack(this,1,i));}
            
            list.add(new ItemStack(this,1,35));
            list.add(new ItemStack(this,1,36));
            list.add(new ItemStack(this,1,37));
    }
}
