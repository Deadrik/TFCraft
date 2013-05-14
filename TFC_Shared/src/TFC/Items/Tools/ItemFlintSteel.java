package TFC.Items.Tools;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import TFC.TFCBlocks;
import TFC.Blocks.BlockSlab;
import TFC.Core.Helper;
import TFC.Core.TFCTabs;
import TFC.Core.TFC_Core;
import TFC.TileEntities.TileEntityPartial;
import net.minecraft.item.ItemFlintAndSteel;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.crash.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.packet.*;
import net.minecraft.pathfinding.*;
import net.minecraft.potion.*;
import net.minecraft.server.*;
import net.minecraft.stats.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.village.*;
import net.minecraft.world.*;
import net.minecraft.world.biome.*;
import net.minecraft.world.chunk.*;
import net.minecraft.world.gen.feature.*;

public class ItemFlintSteel extends ItemFlintAndSteel{

	public ItemFlintSteel(int par1) {
		super(par1);
		setCreativeTab(TFCTabs.TFCTools);
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
						if(entity.getEntityItem().itemID == Item.paper.itemID)
						{
							hasPaper = 20;
						}
						else if(entity.getEntityItem().itemID == Item.stick.itemID)
						{
							numsticks+=entity.getEntityItem().stackSize;
						}
						else if(entity.getEntityItem().itemID == Item.coal.itemID)
						{
							numcoal+=entity.getEntityItem().stackSize;
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
						if(entity.getEntityItem().itemID == Item.stick.itemID)
						{
							entity.setDead();
						}
						if(entity.getEntityItem().itemID == Item.paper.itemID)
						{
							entity.setDead();
						}
					}
					world.setBlock(x, y+1, z, TFCBlocks.Firepit.blockID, 1, 0x2);
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
						if(entity.getEntityItem().itemID == Item.stick.itemID)
						{
							entity.setDead();
						}
						if(entity.getEntityItem().itemID == Item.coal.itemID)
						{
							entity.setDead();
						}
					}
					world.setBlock(x, y+1, z, TFCBlocks.Forge.blockID, 1, 0x2);
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
					world.setBlock(x, y, z, TFCBlocks.Forge.blockID, 1, 0x2);
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
			if(BlockSlab.getEastChiselLevel(te.extraData) != 0)
			{
				return false;
			}
		}
		if(world.getBlockId(x+1, y, z) == TFCBlocks.stoneSlabs.blockID)
		{
			TileEntityPartial te = (TileEntityPartial) world.getBlockTileEntity(x+1, y, z);
			if(BlockSlab.getWestChiselLevel(te.extraData) != 0)
			{
				return false;
			}
		}
		if(world.getBlockId(x, y, z-1) == TFCBlocks.stoneSlabs.blockID)
		{
			TileEntityPartial te = (TileEntityPartial) world.getBlockTileEntity(x, y, z-1);
			if(BlockSlab.getSouthChiselLevel(te.extraData) != 0)
			{
				return false;
			}
		}
		if(world.getBlockId(x, y, z-1) == TFCBlocks.stoneSlabs.blockID)
		{
			TileEntityPartial te = (TileEntityPartial) world.getBlockTileEntity(x, y, z-1);
			if(BlockSlab.getNorthChiselLevel(te.extraData) != 0)
			{
				return false;
			}
		}
		return true;
	}
}
