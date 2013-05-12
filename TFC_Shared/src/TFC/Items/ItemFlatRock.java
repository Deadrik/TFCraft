package TFC.Items;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

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
        this.setCreativeTab(null);
    }
    
    @Override
    public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5) 
    {
        //if(par1ItemStack.stackSize == 0)
        ((EntityPlayer)par3Entity).inventory.setInventorySlotContents(par4, null);
    }
    
    @Override
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
	public void registerIcons(IconRegister registerer)
    {
		for(int i = 0; i < MetaNames.length; i++)
			icons[i] = registerer.registerIcon("rocks/flatrocks/"+MetaNames[i]+" Raw");
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
