package TFC.Items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import TFC.Core.TFC_Core;
import TFC.TileEntities.TECrop;
import TFC.TileEntities.TileEntityFarmland;

public class ItemFertilizer extends ItemTerra
{
	public ItemFertilizer(int i) 
	{
		super(i);
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		if(!world.isRemote)
		{
			if(TFC_Core.isFarmland(world.getBlockId(x, y, z)))
			{
				TileEntityFarmland tef = (TileEntityFarmland)world.getBlockTileEntity(x, y, z);
				if (tef.nutrients[3] != tef.getSoilMax())
				{
					tef.nutrients[3] = tef.getSoilMax();
					--itemstack.stackSize;
					return true;
				}
			} 
			else if(world.getBlockTileEntity(x, y, z) instanceof TECrop && TFC_Core.isFarmland(world.getBlockId(x, y-1, z)))
			{
				TileEntityFarmland tef = (TileEntityFarmland)world.getBlockTileEntity(x, y-1, z);
				if (tef.nutrients[3] != tef.getSoilMax())
				{
					tef.nutrients[3] = tef.getSoilMax();
					--itemstack.stackSize;
					return true;
				}
			}
		}
		return false;
	}
}
