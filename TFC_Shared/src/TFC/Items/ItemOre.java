package TFC.Items;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import TFC.Reference;
import TFC.API.IMadeOfMetal;
import TFC.API.Metal;
import TFC.API.Constant.Global;
import TFC.API.Enums.EnumSize;
import TFC.API.Enums.EnumWeight;

public class ItemOre extends ItemTerra implements IMadeOfMetal
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
				/*32*/"Borax", "Olivine", "Lapis Lazuli", "GalenaPartial", "TetrahedritePartial", "MagnetitePartial"};
		setFolder("ore/");
	}
	
	@Override
	public EnumSize getSize() {
		return EnumSize.SMALL;
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
	public void registerIcons(IconRegister registerer)
    {
		for(int i = 0; i < MetaNames.length-3; i++)
			icons[i] = registerer.registerIcon(Reference.ModID + ":" + textureFolder+MetaNames[i]+" Ore");
    }
	
	@Override
    public String getUnlocalizedName(ItemStack itemstack)
    {
    	if(MetaNames != null)
    		return getUnlocalizedName().concat("."+ MetaNames[itemstack.getItemDamage()]);
    	return super.getUnlocalizedName(itemstack);
    }

	@Override
	public Metal GetMetalType(ItemStack is) 
	{
		int dam = is.getItemDamage();
		switch(dam)
		{
		case 0: return Global.COPPER;
		case 1: return Global.GOLD;
		case 2: return Global.PLATINUM;
		case 3: return Global.PIGIRON;
		case 4: return Global.SILVER;
		case 5: return Global.TIN;
		case 6: return Global.LEAD;
		case 7: return Global.BISMUTH;
		case 8: return Global.NICKEL;
		case 9: return Global.COPPER;
		case 10: return Global.PIGIRON;
		case 11: return Global.PIGIRON;
		case 12: return Global.ZINC;
		case 13: return Global.COPPER;
		}
		return null;
	}

	@Override
	public int GetMetalReturnAmount(ItemStack is) 
	{
		int dam = is.getItemDamage();
		switch(dam)
		{
		case 0:
		case 1:
		case 2:
		case 3:
		case 4:
		case 5:
		case 6:
		case 7:
		case 8:
		case 9:
		case 10:
		case 11:
		case 12:
		case 13:
			return 25;
		}
		return 0;
	}
	
    /*public static String getItemNameDamage(int d) 
    {
        String s = blockNames[d];
        return s;
    }*/

}
