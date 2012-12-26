package TFC.Items;

import java.util.List;

import TFC.*;
import TFC.Entities.EntityCustomMinecart;
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
