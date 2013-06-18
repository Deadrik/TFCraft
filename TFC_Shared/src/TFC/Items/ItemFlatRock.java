package TFC.Items;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import TFC.Reference;
import TFC.API.Constant.Global;

public class ItemFlatRock extends ItemTerra
{
	Icon[] icons;
    public ItemFlatRock(int id) 
    {
        super(id);
        this.hasSubtypes = true;
        this.setMaxDamage(0);
        this.maxStackSize = 25;
		this.MetaNames = Global.STONE_ALL;
        this.icons = new Icon[MetaNames.length];
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
			icons[i] = registerer.registerIcon(Reference.ModID + ":" + this.textureFolder + MetaNames[i] + " Raw");
    }
}
