package TFC.Items;

import TFC.TFCBlocks;
import TFC.Core.Helper;
import TFC.Enums.EnumSize;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MathHelper;
import net.minecraft.src.MovingObjectPosition;
import net.minecraft.src.World;

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
