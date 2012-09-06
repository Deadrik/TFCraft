package TFC.Items;

import TFC.Core.Helper;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MathHelper;
import net.minecraft.src.MovingObjectPosition;
import net.minecraft.src.TFCBlocks;
import net.minecraft.src.World;

public class ItemTerraBellows extends Item
{
	public ItemTerraBellows(int i) 
	{
		super(i);
		maxStackSize = 1;
		this.setTabToDisplayOn(CreativeTabs.tabMisc);
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

	public boolean tryPlaceIntoWorld(ItemStack itemstack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		if(!world.isRemote)
		{
			int l = MathHelper.floor_double((double)(player.rotationYaw * 4F / 360F) + 0.5D) & 3;
			if(side == 1 && world.isBlockNormalCube(x, y, z) && world.isBlockOpaqueCube(x, y, z) && 
					world.getBlockId(x, y+1, z) == 0)
			{
				world.setBlockAndMetadataWithNotify( x, y+1, z, TFCBlocks.terraBellows.blockID, l);
				player.inventory.mainInventory[player.inventory.currentItem] = null;
				return true;
			}
		}

		return false;
	}
}
