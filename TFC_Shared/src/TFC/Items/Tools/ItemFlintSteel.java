package TFC.Items.Tools;

import java.util.Iterator;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFlintAndSteel;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import TFC.TFCBlocks;
import TFC.API.ISize;
import TFC.API.Enums.EnumItemReach;
import TFC.API.Enums.EnumSize;
import TFC.API.Enums.EnumWeight;
import TFC.Core.TFCTabs;
import TFC.Core.TFC_Core;
import TFC.TileEntities.TileEntityPottery;

public class ItemFlintSteel extends ItemFlintAndSteel implements ISize
{

	public ItemFlintSteel(int par1) {
		super(par1);
		setCreativeTab(TFCTabs.TFCTools);
	}

	@Override
	public boolean onItemUseFirst(ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		if(!world.isRemote)
		{ 

			boolean surroundSolids = TFC_Core.isNorthFaceSolid(world, x, y, z-1) && TFC_Core.isSouthFaceSolid(world, x, y, z+1) && 
					TFC_Core.isEastFaceSolid(world, x-1, y, z) && TFC_Core.isWestFaceSolid(world, x+1, y, z);

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
							hasPaper = 20;
						}
						else if(entity.getEntityItem().itemID == Item.stick.itemID)
						{
							numsticks+=entity.getEntityItem().stackSize;
						}
					}
				}

				itemstack.setItemDamage(itemstack.getItemDamage()+1);

				if(itemstack.getItemDamage() >= itemstack.getMaxDamage()) {
					itemstack.stackSize = 0;
				}


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
					itemstack.damageItem(1, entityplayer);
					world.setBlock(x, y+1, z, TFCBlocks.Firepit.blockID, 1, 0x2);
					if(world.isRemote) {
						world.markBlockForUpdate(x, y+1, z);
					}
					return true;
				}

				return true;
			}
			else if((world.getBlockId(x, y, z) == TFCBlocks.Charcoal.blockID && world.getBlockMetadata(x, y, z) > 6) ||
					world.getBlockId(x, y, z) == Block.coalBlock.blockID)
			{
				if(world.getBlockMaterial(x, y-1, z) == Material.rock && 
						world.getBlockMaterial(x+1, y, z) == Material.rock && world.getBlockMaterial(x-1, y, z) == Material.rock && 
						world.getBlockMaterial(x, y, z+1) == Material.rock && world.getBlockMaterial(x, y, z-1) == Material.rock &&
						surroundSolids)
				{
					itemstack.damageItem(1, entityplayer);
					world.setBlock(x, y, z, TFCBlocks.Forge.blockID, 1, 0x2);
					return true;
				}
			}
			else if(world.getBlockId(x, y, z) == TFCBlocks.Pottery.blockID && surroundSolids)
			{
				TileEntityPottery te = (TileEntityPottery) world.getBlockTileEntity(x, y, z);
				te.StartPitFire();					
				itemstack.damageItem(1, entityplayer);
				return true;
			}
			else
			{
				super.onItemUse(itemstack, entityplayer, world, x, y, z, side, hitX, hitY, hitZ);
			}
			return false;
		}
		return false;
	}

	@Override
	public EnumSize getSize(ItemStack is) {
		// TODO Auto-generated method stub
		return EnumSize.VERYSMALL;
	}

	@Override
	public EnumWeight getWeight(ItemStack is) {
		// TODO Auto-generated method stub
		return EnumWeight.LIGHT;
	}

	@Override
	public boolean canStack() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public EnumItemReach getReach(ItemStack is) {
		return EnumItemReach.SHORT;
	}
}
