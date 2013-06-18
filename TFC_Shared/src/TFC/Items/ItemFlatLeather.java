package TFC.Items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemFlatLeather extends ItemTerra
{

    public ItemFlatLeather(int id) 
    {
        super(id);
        this.hasSubtypes = false;
        this.setMaxDamage(0);
        this.maxStackSize = 25;
    }
    
    public ItemFlatLeather(int id, String tex) 
    {
        super(id);
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
}
