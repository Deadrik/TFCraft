package TFC.Items.Tools;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import TFC.TFCBlocks;
import TFC.API.Enums.EnumSize;
import TFC.API.Util.Helper;
import TFC.Core.TFCTabs;
import TFC.Core.TFC_Core;
import TFC.Items.ItemTerra;
import TFC.TileEntities.TileEntityPottery;

public class ItemFirestarter extends ItemTerra
{
	public ItemFirestarter()
	{
		super();
		this.setMaxDamage(8);
		this.hasSubtypes = false;
		setCreativeTab(TFCTabs.TFCTools);
	}

	@Override
	public EnumSize getSize(ItemStack is) {
		return EnumSize.SMALL;
	}

	public int getPlacedBlockMetadata(int i)
	{
		return i;
	}

	@Override
	public boolean canStack() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean onItemUseFirst(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l, float hitX, float hitY, float hitZ)
	{
		if(!world.isRemote)
		{
			MovingObjectPosition objectMouseOver = Helper.getMouseOverObject(entityplayer, world);
			if(objectMouseOver == null) {
				return false;
			}       
			int x = objectMouseOver.blockX;
			int y = objectMouseOver.blockY;
			int z = objectMouseOver.blockZ;
			int side = objectMouseOver.sideHit;

			boolean surroundSolids = TFC_Core.isNorthSolid(world, x, y, z) && TFC_Core.isSouthSolid(world, x, y, z) && 
					TFC_Core.isEastSolid(world, x, y, z) && TFC_Core.isWestSolid(world, x, y, z);
			boolean surroundSolidsUp1 = TFC_Core.isNorthSolid(world, x, y+1, z) && TFC_Core.isSouthSolid(world, x, y+1, z) && 
					TFC_Core.isEastSolid(world, x, y+1, z) && TFC_Core.isWestSolid(world, x, y+1, z);
			boolean surroundSolids2 = TFC_Core.isNorthSolid(world, x, y-1, z) && TFC_Core.isSouthSolid(world, x, y-1, z) && 
					TFC_Core.isEastSolid(world, x, y-1, z) && TFC_Core.isWestSolid(world, x, y-1, z);

			if(side == 1 && world.getBlock(x, y, z).isNormalCube() && world.getBlock(x, y, z).isOpaqueCube() && 
					world.getBlock(x, y, z).getMaterial() != Material.wood && world.getBlock(x, y, z).getMaterial() != Material.cloth &&
					world.isAirBlock(x, y+1, z) && world.getBlock(x, y, z) != TFCBlocks.Charcoal)
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
						if(entity.getEntityItem().getItem() == Items.paper)
						{
							hasPaper = 40;
						}
						else if(entity.getEntityItem().getItem() == Items.stick)
						{
							numsticks+=entity.getEntityItem().stackSize;
						}
						else if(entity.getEntityItem().getItem() == Items.coal)
						{
							numcoal+=entity.getEntityItem().stackSize;
						}
					}
				}

				itemstack.damageItem(1, entityplayer);

				if(itemstack.getItemDamage() >= itemstack.getMaxDamage()) {
					itemstack.stackSize = 0;
				}

				int chance = new Random().nextInt(100);
				if(chance > 70-hasPaper)
				{
					if(numsticks >= 3)
					{
						for (Iterator iterator = list.iterator(); iterator.hasNext();)
						{
							EntityItem entity = (EntityItem)iterator.next();
							if(entity.getEntityItem().getItem() == Items.stick)
							{
								entity.setDead();
							}
							if(entity.getEntityItem().getItem() == Items.paper)
							{
								entity.setDead();
							}
						}
						world.setBlock(x, y+1, z, TFCBlocks.Firepit, 1, 2);
					}
					else if(numcoal >= 7 && world.getBlock(x, y, z).getMaterial() == Material.rock && 
							world.getBlock(x+1, y+1, z).getMaterial() == Material.rock && world.getBlock(x-1, y+1, z).getMaterial() == Material.rock && 
							world.getBlock(x, y+1, z+1).getMaterial() == Material.rock && world.getBlock(x, y+1, z-1).getMaterial() == Material.rock &&
							world.getBlock(x, y, z).isNormalCube() && surroundSolidsUp1)
					{
						for (Iterator iterator = list.iterator(); iterator.hasNext();)
						{
							EntityItem entity = (EntityItem)iterator.next();
							if(entity.getEntityItem().getItem() == Items.stick)
							{
								entity.setDead();
							}
							if(entity.getEntityItem().getItem() == Items.coal)
							{
								entity.setDead();
							}
						}
						itemstack.damageItem(1, entityplayer);
						world.setBlock(x, y+1, z, TFCBlocks.Forge, 1, 2);
					}

					return true;
				}
			}
			else if(world.getBlock(x, y, z) == TFCBlocks.Charcoal && world.getBlockMetadata(x, y, z) > 6)
			{
				if(world.getBlock(x, y-1, z).getMaterial() == Material.rock && 
						world.getBlock(x+1, y, z).getMaterial() == Material.rock && world.getBlock(x-1, y, z).getMaterial() == Material.rock && 
						world.getBlock(x, y, z+1).getMaterial() == Material.rock && world.getBlock(x, y, z-1).getMaterial() == Material.rock &&
						world.getBlock(x, y-1, z).isNormalCube() && surroundSolids)
				{
					int chance = new Random().nextInt(100);
					if(chance > 70)
					{
						world.setBlock(x, y, z, TFCBlocks.Forge, 1, 2);
					}
					itemstack.damageItem(1, entityplayer);
					return true;
				}
			}
			else if(world.getBlock(x, y, z) == TFCBlocks.LogPile)
			{
				if(world.getBlock(x, y-1, z) == TFCBlocks.Pottery && surroundSolids && surroundSolids2)
				{
					int chance = new Random().nextInt(100);
					if(chance > 70)
					{
						TileEntityPottery te = (TileEntityPottery) world.getTileEntity(x, y-1, z);
						te.StartPitFire();
					}
					itemstack.damageItem(1, entityplayer);
					return true;
				}
			}
			return false;
		}
		return false;
	}
}
