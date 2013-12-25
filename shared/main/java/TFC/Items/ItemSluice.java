package TFC.Items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import TFC.TFCBlocks;
import TFC.API.Enums.EnumSize;
import TFC.API.Enums.EnumWeight;
import TFC.Blocks.Devices.BlockSluice;

public class ItemSluice extends ItemTerra
{

	public ItemSluice(int i)
	{
		super(i);
		this.setWeight(EnumWeight.HEAVY);
		this.setSize(EnumSize.HUGE);
	}


	public int getPlacedBlockMetadata(int i) {

		//System.out.println(new StringBuilder().append(this.getItemDisplayName(new ItemStack(this,1,i))).toString());

		return i;
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int side, float hitX, float hitY, float hitZ)
	{
		if(!world.isRemote)
		{
			int xCoord = i;
			int yCoord = j;
			int zCoord = k;
			int r = MathHelper.floor_double(entityplayer.rotationYaw * 4F / 360F + 0.5D) & 3;
			byte byte0 = 0;
			byte byte1 = 0;
			if(r == 0)//+z
			{
				byte1 = 1;
			}
			if(r == 1)//-x
			{
				byte0 = -1;
			}
			if(r == 2)//-z
			{
				byte1 = -1;
			}
			if(r == 3)//+x
			{
				byte0 = 1;
			}
			if(((BlockSluice)TFCBlocks.Sluice).canPlace(world, i, j+1, k,r))
			{
				world.setBlock(i, j+1, k, TFCBlocks.Sluice.blockID, r, 0x2);
				if(world.getBlockId(i, j+1, k) == TFCBlocks.Sluice.blockID)
				{
					world.setBlock(i + byte0, j+1, k + byte1, TFCBlocks.Sluice.blockID, r + 8, 0x2);
					entityplayer.inventory.decrStackSize(entityplayer.inventory.currentItem, 1);
				}
				return true;
			}
		}
		return false;
	}
}
