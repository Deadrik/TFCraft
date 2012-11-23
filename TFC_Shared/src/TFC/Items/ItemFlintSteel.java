package TFC.Items;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import TFC.TFCBlocks;
import TFC.Blocks.BlockSlab;
import TFC.Core.Helper;
import TFC.TileEntities.TileEntityPartial;
import net.minecraft.src.*;

public class ItemFlintSteel extends ItemFlintAndSteel{

	public ItemFlintSteel(int par1) {
		super(par1);
		this.setCreativeTab(CreativeTabs.tabTools);
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		if(!world.isRemote)
		{
			MovingObjectPosition objectMouseOver = Helper.getMouseOverObject(entityplayer, world);
			if(objectMouseOver == null) {
				return false;
			}       

			if(side == 1 && world.isBlockNormalCube(x, y, z) && world.isBlockOpaqueCube(x, y, z) && 
					world.getBlockMaterial(x, y, z) != Material.wood && world.getBlockMaterial(x, y, z) != Material.cloth &&
					world.getBlockId(x, y+1, z) == 0 && world.getBlockId(x, y, z) != TFCBlocks.Charcoal.blockID)
			{

				List list = world.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(x, y+1, z, x+1, y+2, z+1));
				int numsticks = 0;
				int hasPaper = 0;
				int numcoal = 0;

				if (list != null && !list.isEmpty())
				{
					for (Iterator iterator = list.iterator(); iterator.hasNext();)
					{
						EntityItem entity = (EntityItem)iterator.next();
						if(entity.item.itemID == Item.paper.shiftedIndex)
						{
							hasPaper = 20;
						}
						else if(entity.item.itemID == Item.stick.shiftedIndex)
						{
							numsticks+=entity.item.stackSize;
						}
						else if(entity.item.itemID == Item.coal.shiftedIndex)
						{
							numcoal+=entity.item.stackSize;
						}
					}
				}

				itemstack.setItemDamage(itemstack.getItemDamage()+1);

				if(itemstack.getItemDamage() >= itemstack.getMaxDamage())
					itemstack.stackSize = 0;


				if(numsticks >= 3)
				{
					for (Iterator iterator = list.iterator(); iterator.hasNext();)
					{
						EntityItem entity = (EntityItem)iterator.next();
						if(entity.item.itemID == Item.stick.shiftedIndex)
						{
							entity.setDead();
						}
						if(entity.item.itemID == Item.paper.shiftedIndex)
						{
							entity.setDead();
						}
					}
					world.setBlockAndMetadataWithNotify(x, y+1, z, TFCBlocks.Firepit.blockID, 1);
					if(world.isRemote)
						world.markBlockForUpdate(x, y+1, z);
				}
				else if(numcoal >= 7 && world.getBlockMaterial(x, y, z) == Material.rock && 
						world.getBlockMaterial(x+1, y+1, z) == Material.rock && world.getBlockMaterial(x-1, y+1, z) == Material.rock && 
						world.getBlockMaterial(x, y+1, z+1) == Material.rock && world.getBlockMaterial(x, y+1, z-1) == Material.rock &&
						world.isBlockNormalCube(x, y, z) && ((world.isBlockNormalCube(x+1, y+1, z) && world.isBlockNormalCube(x-1, y+1, z) && 
								world.isBlockNormalCube(x, y+1, z+1) && world.isBlockNormalCube(x, y+1, z-1)) || (checkSlabsAround(world, x, y+1, z))))
				{
					for (Iterator iterator = list.iterator(); iterator.hasNext();)
					{
						EntityItem entity = (EntityItem)iterator.next();
						if(entity.item.itemID == Item.stick.shiftedIndex)
						{
							entity.setDead();
						}
						if(entity.item.itemID == Item.coal.shiftedIndex)
						{
							entity.setDead();
						}
					}
					world.setBlockAndMetadataWithNotify(x, y+1, z, TFCBlocks.Forge.blockID, 1);
					if(world.isRemote)
						world.markBlockForUpdate(x, y+1, z);
				}

				return true;
			}
			else if(world.getBlockId(x, y, z) == TFCBlocks.Charcoal.blockID && world.getBlockMetadata(x, y, z) > 6)
			{
				if(world.getBlockMaterial(x, y-1, z) == Material.rock && 
						world.getBlockMaterial(x+1, y, z) == Material.rock && world.getBlockMaterial(x-1, y, z) == Material.rock && 
						world.getBlockMaterial(x, y, z+1) == Material.rock && world.getBlockMaterial(x, y, z-1) == Material.rock &&
						world.isBlockNormalCube(x, y-1, z) && ((world.isBlockNormalCube(x+1, y, z) && world.isBlockNormalCube(x-1, y, z) && 
								world.isBlockNormalCube(x, y, z+1) && world.isBlockNormalCube(x, y, z-1)) || (checkSlabsAround(world, x, y, z))))
				{

					world.setBlockAndMetadataWithNotify(x, y, z, TFCBlocks.Forge.blockID, 1);
					world.markBlockForUpdate(x, y, z);

				}
			}
			else
			{
				super.onItemUse(itemstack, entityplayer, world, x, y, z, side, hitX, hitY, hitZ);
			}
			return false;
		}
		return false;
	}

	public static boolean checkSlabsAround(World world, int x, int y, int z)
	{
		if(world.getBlockId(x-1, y, z) == TFCBlocks.stoneSlabs.blockID)
		{
			TileEntityPartial te = (TileEntityPartial) world.getBlockTileEntity(x-1, y, z);
			if(BlockSlab.getPosXChiselLevel(te.extraData) != 0)
			{
				return false;
			}
		}
		if(world.getBlockId(x+1, y, z) == TFCBlocks.stoneSlabs.blockID)
		{
			TileEntityPartial te = (TileEntityPartial) world.getBlockTileEntity(x+1, y, z);
			if(BlockSlab.getNegXChiselLevel(te.extraData) != 0)
			{
				return false;
			}
		}
		if(world.getBlockId(x, y, z-1) == TFCBlocks.stoneSlabs.blockID)
		{
			TileEntityPartial te = (TileEntityPartial) world.getBlockTileEntity(x, y, z-1);
			if(BlockSlab.getPosZChiselLevel(te.extraData) != 0)
			{
				return false;
			}
		}
		if(world.getBlockId(x, y, z-1) == TFCBlocks.stoneSlabs.blockID)
		{
			TileEntityPartial te = (TileEntityPartial) world.getBlockTileEntity(x, y, z-1);
			if(BlockSlab.getNegZChiselLevel(te.extraData) != 0)
			{
				return false;
			}
		}
		return true;
	}
}
