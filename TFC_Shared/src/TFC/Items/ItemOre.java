package TFC.Items;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import TFC.Enums.EnumSize;
import TFC.Enums.EnumWeight;

public class ItemOre extends ItemTerra
{	
	public Icon[] icons = new Icon[35];


	public ItemOre(int i) 
	{
		super(i);
		setMaxDamage(0);
		setHasSubtypes(true);
		MetaNames = new String[]{"Native Copper", "Native Gold", "Native Platinum", "Hematite", "Native Silver", "Cassiterite", "Galena", "Bismuthinite", "Garnierite", 
				"Malachite", "Magnetite", "Limonite", "Sphalerite", "Tetrahedrite", 
				"Bituminous Coal", "Lignite", "Kaolinite", "Gypsum", "Satinspar", "Selenite", "Graphite", "Kimberlite", 
				/*22*/"Petrified Wood", "Sulfur", "Jet", "Microcline", "Pitchblende", "Cinnabar", "Cryolite", "Saltpeter", "Serpentine", "Sylvite", 
				/*32*/"Borax", "Olivine", "LapisLazuli", "GalenaPartial", "TetrahedritePartial", "MagnetitePartial"};
	}
	
	@Override
	public EnumSize getSize() {
		return EnumSize.MEDIUM;
	}
	
	@Override
	public EnumWeight getWeight() {
		// TODO Auto-generated method stub
		return EnumWeight.HEAVY;
	}

	@Override
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List list)
	{
		for(int i = 0; i < MetaNames.length; i++) {
			list.add(new ItemStack(this,1,i));
		}
	}
	
	@Override
	public Icon getIconFromDamage(int meta)
	{
	    if(meta == 35)
	        return icons[6];
	    else if(meta == 36)
            return icons[13];
	    else if(meta == 37)
            return icons[10];
	    
		return icons[meta];
	}
	
	@Override
	public void updateIcons(IconRegister registerer)
    {
		for(int i = 0; i < MetaNames.length-3; i++)
			icons[i] = registerer.registerIcon(textureFolder+MetaNames[i]+" Ore");
    }

	/*@Override
	public String getItemDisplayName(ItemStack itemstack) 
	{
    	if(blockNames != null)
    		return new StringBuilder().append(super.getItemDisplayName(itemstack)).append(".").append(blockNames[itemstack.getItemDamage()]).toString();
		return super.getItemDisplayName(itemstack);
	}*/
	
    /*public static String getItemNameDamage(int d) 
    {
        String s = blockNames[d];
        return s;
    }*/

}
