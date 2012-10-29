package TFC.Items;

import java.util.List;

import TFC.*;
import TFC.Entities.EntityCustomMinecart;
import TFC.Enums.EnumSize;
import net.minecraft.src.BlockRail;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public class ItemCustomMinecart extends ItemTerra
{
    public int minecartType;

    public ItemCustomMinecart(int par1, int par2)
    {
        super(par1);
        this.maxStackSize = 1;
        this.minecartType = par2;
        this.setCreativeTab(CreativeTabs.tabTransport);
    }

    @Override
    public boolean onItemUse(ItemStack itemstack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
        int var11 = world.getBlockId(x, y, z);

        if (BlockRail.isRailBlock(var11))
        {
            if (!world.isRemote)
            {
                world.spawnEntityInWorld(new EntityCustomMinecart(world, (double)((float)x + 0.5F), (double)((float)y + 0.5F), (double)((float)z + 0.5F), this.minecartType));
            }

            --itemstack.stackSize;
            return true;
        }
        else
        {
            return false;
        }
    }
    
    @Override
    public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        par3List.add(new ItemStack(par1, 1, 0));
    }
    
    @Override
	public EnumSize getSize() {
		// TODO Auto-generated method stub
		return EnumSize.MEDIUM;
	}

	@Override
	public boolean canStack() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public String getTextureFile() {
		return "/gui/items.png";
	}
}
