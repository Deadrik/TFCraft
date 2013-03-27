package TFC.Items;

import java.util.List;

import TFC.TerraFirmaCraft;
import TFC.Core.Helper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.client.renderer.texture.IconRegister;
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

public class ItemFlatRock extends ItemTerra
{
	Icon[] icons;
    public ItemFlatRock(int id) 
    {
        super(id);
        this.hasSubtypes = true;
        this.setMaxDamage(0);
        this.maxStackSize = 25;
        MetaNames = new String[]{"Granite", "Diorite", "Gabbro", 
                "Siltstone", "Mudstone", "Shale", "Claystone", "Rock Salt", "Limestone", "Conglomerate", "Dolomite", "Chert", 
                "Chalk", "Rhyolite", "Basalt", "Andesite", "Dacite", 
                "Quartzite", "Slate", "Phyllite", "Schist", "Gneiss", "Marble"};
        icons = new Icon[MetaNames.length];
    }
    
    @Override
    public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5) 
    {
        //if(par1ItemStack.stackSize == 0)
        ((EntityPlayer)par3Entity).inventory.setInventorySlotContents(par4, null);
    }
    
    public boolean onDroppedByPlayer(ItemStack item, EntityPlayer player)
    {
        return false;
    }
    
    @Override
	public Icon getIconFromDamage(int meta)
	{        
		return icons[meta];
	}
	
	
	@Override
	public void registerIcon(IconRegister registerer)
    {
		for(int i = 0; i < MetaNames.length; i++)
			registerer.func_94245_a("../blocks/rocks/"+MetaNames[i]+" Raw");
    }

	@Override
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List list)
	{
		for(int i = 0; i < MetaNames.length; i++) {
			list.add(new ItemStack(this,1,i));
		}
	}

    /*public Icon getIconFromDamage(int i)
    {
        switch(i)
        {
            case 0:
            case 1:
            case 2:
                return i;//igin
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
                return 64+(i-3);//sed
            case 13:
            case 14:
            case 15:
            case 16:
                return 3+(i-13);//igex
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
                return 74+(i-17);//mm
        }
        return i;
    }*/
}
