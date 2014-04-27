package TFC.Items.Tools;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import TFC.TFCBlocks;
import TFC.API.Enums.EnumItemReach;
import TFC.API.Enums.EnumSize;
import TFC.API.Util.Helper;
import TFC.Core.TFCTabs;
import TFC.Core.TFC_Core;
import TFC.Items.ItemTerra;
import TFC.TileEntities.TileEntityForge;
import TFC.TileEntities.TileEntityPottery;

public class ItemFirestarter extends ItemTerra
{

	public ItemFirestarter(int i)
	{
		super(i);
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

			boolean surroundSolids = TFC_Core.isNorthFaceSolid(world, i, j, k-1) && TFC_Core.isSouthFaceSolid(world, i, j, k+1) && 
					TFC_Core.isEastFaceSolid(world, i-1, j, k) && TFC_Core.isWestFaceSolid(world, i+1, j, k);

			if(side == 1 && world.isBlockNormalCube(x, y, z) && world.isBlockOpaqueCube(x, y, z) && 
					world.getBlockMaterial(x, y, z) != Material.wood && world.getBlockMaterial(x, y, z) != Material.cloth &&
					world.getBlockId(x, y+1, z) == 0 && world.getBlockId(x, y, z) != TFCBlocks.Charcoal.blockID &&
					world.getBlockId(x, y, z) != Block.coalBlock.blockID)
			{

				List list = world.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(x, y+1, z, x+1, y+2, z+1));
				int numsticks = 0;
				int hasPaper = 0;

				if (list != null && !list.isEmpty())
				{
					for (Iterator iterator = list.iterator(); iterator.hasNext();)
					{
						EntityItem entity = (EntityItem)iterator.next();
						if(entity.getEntityItem().itemID == Item.paper.itemID)
						{
							hasPaper = 40;
						}
						else if(entity.getEntityItem().itemID == Item.stick.itemID)
						{
							numsticks+=entity.getEntityItem().stackSize;
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
							if(entity.getEntityItem().itemID == Item.stick.itemID)
							{
								entity.setDead();
							}
							if(entity.getEntityItem().itemID == Item.paper.itemID)
							{
								entity.setDead();
							}
						}
						world.setBlock(x, y+1, z, TFCBlocks.Firepit.blockID, 1, 2);
					}
					return true;
				}
			}
			else if((world.getBlockId(x, y, z) == TFCBlocks.Charcoal.blockID && world.getBlockMetadata(x, y, z) > 6) ||
					world.getBlockId(x, y, z) == Block.coalBlock.blockID)
			{
				if(world.getBlockMaterial(x, y-1, z) == Material.rock && 
						world.getBlockMaterial(x+1, y, z) == Material.rock && world.getBlockMaterial(x-1, y, z) == Material.rock && 
						world.getBlockMaterial(x, y, z+1) == Material.rock && world.getBlockMaterial(x, y, z-1) == Material.rock &&
						world.isBlockNormalCube(x, y-1, z) && surroundSolids)
				{
					int chance = new Random().nextInt(100);
					if(chance > 70)
					{
						world.setBlock(x, y, z, TFCBlocks.Forge.blockID, 1, 2);
						TileEntityForge te = (TileEntityForge)world.getBlockTileEntity(x, y, z);
						te.fuelBurnTemp = 200;
						te.fuelTimeLeft = 200;
					}
					itemstack.damageItem(1, entityplayer);
					return true;
				}
			}
			else if(world.getBlockId(x, y, z) == TFCBlocks.Pottery.blockID && surroundSolids)
			{
				int chance = new Random().nextInt(100);
				if(chance > 70)
				{
					TileEntityPottery te = (TileEntityPottery) world.getBlockTileEntity(x, y, z);
					te.StartPitFire();
				}
				itemstack.damageItem(1, entityplayer);
				return true;
			}
			return false;
		}
		return false;
	}

	@Override
	public EnumItemReach getReach(ItemStack is){
		return EnumItemReach.SHORT;
	}
}
