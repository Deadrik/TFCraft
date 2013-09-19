package TFC.Items.ItemBlocks;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import TFC.TFCBlocks;
import TFC.API.Enums.EnumSize;
import TFC.Core.TFCTabs;

public class ItemBellows extends ItemTerraBlock
{
	public ItemBellows(int par1)
	{
		super(par1);
		this.setCreativeTab(TFCTabs.TFCTools);
	}
	
	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		if(!world.isRemote)
		{
			int l = MathHelper.floor_double(player.rotationYaw * 4F / 360F + 0.5D) & 3;
			if(side == 1 && world.isBlockNormalCube(x, y, z) && world.isBlockOpaqueCube(x, y, z) && 
					world.getBlockId(x, y+1, z) == 0)
			{
				world.setBlock( x, y+1, z, TFCBlocks.Bellows.blockID, l, 0x2);
				player.inventory.mainInventory[player.inventory.currentItem].stackSize--;
				return true;
			}
		}
		return false;
	}
	
	@Override
	public EnumSize getSize() {
		return EnumSize.HUGE;
	}
}
