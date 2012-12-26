package TFC.Items;

import TFC.TFCBlocks;
import TFC.Core.Helper;
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

public class ItemBellows extends ItemTerra
{
	public ItemBellows(int i) 
	{
		super(i);
		this.setCreativeTab(CreativeTabs.tabMisc);
	}
	
	@Override
	public EnumSize getSize() {
		return EnumSize.HUGE;
	}

	@Override
	public int getMetadata(int i) 
	{		
		return i;
	}

	@Override
	public String getTextureFile()
	{
		return "/bioxx/terratools.png";
	}
	
	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		if(!world.isRemote)
		{
			int l = MathHelper.floor_double((double)(player.rotationYaw * 4F / 360F) + 0.5D) & 3;
			if(side == 1 && world.isBlockNormalCube(x, y, z) && world.isBlockOpaqueCube(x, y, z) && 
					world.getBlockId(x, y+1, z) == 0)
			{
				world.setBlockAndMetadataWithNotify( x, y+1, z, TFCBlocks.Bellows.blockID, l);
				player.inventory.mainInventory[player.inventory.currentItem].stackSize--;
				return true;
			}
		}

		return false;
	}
}
