package TFC.Items;

import java.util.List;

import TFC.Entities.EntityCustomMinecart;
import net.minecraft.src.*;

public class ItemCustomMinecart extends Item
{
    public int minecartType;

    public ItemCustomMinecart(int par1, int par2)
    {
        super(par1);
        this.maxStackSize = 1;
        this.minecartType = par2;
        this.setTabToDisplayOn(CreativeTabs.tabTransport);
    }

    public boolean tryPlaceIntoWorld(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
        int var11 = par3World.getBlockId(par4, par5, par6);

        if (BlockRail.isRailBlock(var11))
        {
            if (!par3World.isRemote)
            {
                par3World.spawnEntityInWorld(new EntityCustomMinecart(par3World, (double)((float)par4 + 0.5F), (double)((float)par5 + 0.5F), (double)((float)par6 + 0.5F), this.minecartType));
            }

            --par1ItemStack.stackSize;
            return true;
        }
        else
        {
            return false;
        }
    }
    
    public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        par3List.add(new ItemStack(par1, 1, 0));
    }
}
