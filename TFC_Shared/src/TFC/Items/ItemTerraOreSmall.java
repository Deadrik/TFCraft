package TFC.Items;

import net.minecraft.src.ItemStack;

public class ItemTerraOreSmall extends ItemTerraOre
{
    public static String[] blockNames = {"Native Copper", "Native Gold", "Native Platinum", "Hematite", "Native Silver", "Cassiterite", "Galena", "Bismuthinite", "Garnierite", 
        "Malachite", "Magnetite", "Limonite", "Sphalerite", "Tetrahedrite", 
        "Bituminous Coal", "Lignite", "Kaolinite", "Gypsum", "Satinspar", "Selenite", "Graphite", "Kimberlite", 
        /*22*/"Petrified Wood", "Sulfur", "Jet", "Microcline", "Pitchblende", "Cinnabar", "Cryolite", "Saltpeter", "Serpentine", "Sylvite", 
        /*32*/"Borax", "Olivine", "LapisLazuli", "GalenaPartial", "TetrahedritePartial", "MagnetitePartial"};

    public ItemTerraOreSmall(int i)
    {
        super(i);
    }

    @Override
    public void addCreativeItems(java.util.ArrayList list)
    {
        for(int i = 0; i < 14; i++) {
            list.add(new ItemStack(this,1,i));}
            
            list.add(new ItemStack(this,1,35));
            list.add(new ItemStack(this,1,36));
            list.add(new ItemStack(this,1,37));
    }
}
