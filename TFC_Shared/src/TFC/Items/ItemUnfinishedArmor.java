package TFC.Items;

import TFC.Enums.EnumSize;
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

public class ItemUnfinishedArmor extends ItemTerra
{
	String texture;

	public ItemUnfinishedArmor(int id) 
	{
		super(id);
		this.hasSubtypes = true;
		this.setMaxDamage(0);
		this.setCreativeTab(CreativeTabs.tabMisc);
	}
	public ItemUnfinishedArmor(int id, String tex) 
	{
		super(id);
		texture = tex;
	}

	@Override
	public String getTextureFile()
	{
		return texture;
	}

	public ItemUnfinishedArmor setTexturePath(String t)
	{
		texture = t;
		return this;
	}
	
	@Override
    public String getItemNameIS(ItemStack itemstack) 
    {
	    String s = "";
	    
	    if(itemstack.getItemDamage() == 1)
	        s = new StringBuilder().append(super.getItemName()).append("2").toString();
	    else
	        s = new StringBuilder().append(super.getItemName()).toString();
	    
        return s;
    }
	
	public int getItemStackLimit()
    {
    	if(canStack())
    		return this.getSize().stackSize;
    	else
    		return 1;
    }

	@Override
	public EnumSize getSize() {
		return EnumSize.LARGE;
	}
	
	@Override
	public boolean canStack() 
	{
		return true;
	}

}
