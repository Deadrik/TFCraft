package TFC.Items;

import TFC.*;
import TFC.Blocks.BlockSluice;
import TFC.Enums.EnumSize;
import TFC.Enums.EnumWeight;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MathHelper;
import net.minecraft.src.World;

public class ItemSluice extends ItemTerra
{

	public ItemSluice(int i)
	{
		super(i);
	}

	@Override
	public String getItemNameIS(ItemStack itemstack) 
	{
		return super.getItemName();
	}

	public int getPlacedBlockMetadata(int i) {

		//System.out.println(new StringBuilder().append(this.getItemDisplayName(new ItemStack(this,1,i))).toString());

		return i;
	}

	@Override
	public String getTextureFile()
	{
		return "/bioxx/terratools.png";
	}

	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int side, float hitX, float hitY, float hitZ)
	{
		if(!world.isRemote)
		{
			int xCoord = i;
			int yCoord = j;
			int zCoord = k;
			int r = MathHelper.floor_double((double)(entityplayer.rotationYaw * 4F / 360F) + 0.5D) & 3;
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
				world.setBlockAndMetadata(i, j+1, k, TFCBlocks.Sluice.blockID, r);
				if(world.getBlockId(i, j+1, k) == TFCBlocks.Sluice.blockID)
				{
					world.setBlockAndMetadataWithNotify(i + byte0, j+1, k + byte1, TFCBlocks.Sluice.blockID, r + 8);
					entityplayer.inventory.decrStackSize(entityplayer.inventory.currentItem, 1);
				}
				return true;
			}
		}
		return false;
	}

	public EnumSize getSize()
	{
		return EnumSize.HUGE;
	}
	@Override
	public EnumWeight getWeight() 
	{
		return EnumWeight.HEAVY;
	}
}
