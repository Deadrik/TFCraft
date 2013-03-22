package TFC.Items;

import java.util.List;

import TFC.Enums.EnumSize;
import TFC.Enums.EnumWeight;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.crash.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.packet.*;
import net.minecraft.pathfinding.*;
import net.minecraft.potion.*;
import net.minecraft.server.*;
import net.minecraft.stats.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.village.*;
import net.minecraft.world.*;
import net.minecraft.world.biome.*;
import net.minecraft.world.chunk.*;
import net.minecraft.world.gen.*;
import net.minecraft.world.gen.feature.*;

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
	        return icons[meta + 6];
	    else if(meta == 36)
            return icons[meta + 13];
	    else if(meta == 37)
            return icons[meta + 10];
	    
		return icons[meta];
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
