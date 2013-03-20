package TFC.Blocks;

import TFC.*;
import TFC.Core.TFC_Core;
import TFC.Core.TFC_Settings;
import TFC.Items.ItemCustomShovel;
import TFC.TileEntities.TileEntityPartial;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.client.renderer.texture.IconRegister;
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

public class BlockCharcoal extends BlockTerra {

	public BlockCharcoal(int par1) 
	{
		super(par1, Material.ground);
	}

	@Override
	public Icon getBlockTextureFromSideAndMetadata(int i, int j) 
	{
		return field_94336_cN;
	}
	@Override
	public void registerIcon(IconRegister iconRegisterer)
    {
		this.field_94336_cN = iconRegisterer.func_94245_a("/devices/Charcoal");
    }

	@Override
	public boolean canBeReplacedByLeaves(World w, int x, int y, int z)
	{
		return false;
	}
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}
	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}
	@Override
	public void onBlockHarvested(World world, int i, int j, int k, int l, EntityPlayer entityplayer) 
	{
		if(!world.isRemote)
		{
			//we need to make sure the player has the correct tool out
			boolean isShovel = false;
			ItemStack equip = entityplayer.getCurrentEquippedItem();
			if(equip!=null)
			{
				if(equip.getItem() instanceof ItemCustomShovel)
				{
					isShovel = true;
				}
			}
			if(isShovel)
			{
				int top = 0;
				while(world.getBlockId(i, j+top+1, k) == blockID)
					++top;
				
				dropBlockAsItem_do(world, i, j, k, new ItemStack(Item.coal,1,1));
				if(l-1 > 0)
				{
					if(world.getBlockId(i, j+1, k) == blockID)
					{
						int m1 = world.getBlockMetadata(i, j+top, k);
						if(m1-1 > 0)
						{
							world.setBlockMetadataWithNotify(i, j+top, k, m1-1, 3);
						}
						else
							world.setBlock(i, j+top, k, 0);

						world.setBlockAndMetadataWithNotify(i, j, k, blockID, 8, 3);
					}
					else
					{
						world.setBlockAndMetadataWithNotify(i, j, k, blockID, l-1, 3);
					}

					world.markBlockForUpdate(i, j, k);
					world.markBlockForUpdate(i, j+top, k);
				}
				else
					world.setBlock(i, j, k, 0);
			}
			else
			{
				world.setBlockAndMetadataWithNotify(i, j, k, blockID, l, 3);
			}

			if(l == 0)
				world.setBlock(i, j, k, 0);
		}
	}
	
	@Override
	public boolean removeBlockByPlayer(World world, EntityPlayer player, int x, int y, int z) 
	{
		if(world.getBlockMetadata(x, y, z) > 0)
			return false;

		return world.setBlock(x, y, z, 0);
	}

	public void combineCharcoalDown(World world, int i, int j, int k)
	{
		int meta = world.getBlockMetadata(i, j, k);
		int bottomMeta = world.getBlockMetadata(i, j-1, k);
		if(bottomMeta < 8)
		{
			bottomMeta = bottomMeta + meta;
			int m2 = 0;
			if(bottomMeta > 8)
			{
				m2 = bottomMeta - 8;
				bottomMeta = 8;
			}


			if(m2 > 0)
				world.setBlockAndMetadataWithNotify(i, j, k, blockID, m2, 0);
			else
				world.setBlock(i, j, k, 0);

			world.setBlockAndMetadataWithNotify(i, j-1, k, blockID, bottomMeta, 0);
		}
	}

	public void combineCharcoalUp(World world, int i, int j, int k)
	{
		int meta = world.getBlockMetadata(i, j+1, k);
		int bottomMeta = world.getBlockMetadata(i, j, k);
		if(bottomMeta < 8)
		{
			bottomMeta = bottomMeta + meta;
			int m2 = 0;
			if(bottomMeta > 8)
			{
				m2 = bottomMeta - 8;
				bottomMeta = 8;
			}


			if(m2 > 0)
				world.setBlockAndMetadataWithNotify(i, j+1, k, blockID, m2, 0);
			else
				world.setBlock(i, j+1, k, 0);

			world.setBlockAndMetadataWithNotify(i, j, k, blockID, bottomMeta, 0);
		}
	}

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, int id)
	{
		if(!world.isRemote)
		{
			if(world.getBlockId(i, j-1, k) == 0)
			{
				int meta = world.getBlockMetadata(i, j, k);
				world.setBlockAndMetadataWithNotify(i, j-1, k, blockID, meta, 0);
				world.setBlock(i, j, k, 0);
			}
			else
			{
				if(world.getBlockId(i, j-1, k) == this.blockID)
				{
					combineCharcoalDown(world, i, j, k);
				}

				if(world.getBlockId(i, j+1, k) == this.blockID)
				{
					combineCharcoalUp(world, i, j, k);
				}
			}
		}
	}

	/**
	 * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
	 * cleared to be reused)
	 */
	    @Override
	    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k)
	    {
	        int md = world.getBlockMetadata(i, j, k);
	
	        if (md == 8)
	        	return AxisAlignedBB.getBoundingBox(i, j, k, i + 1, j + 1, k + 1);
	
	        return AxisAlignedBB.getBoundingBox(i, j, k, i + 1, j + (0.125f * md), k + 1);
	    }

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int i, int j, int k) 
	{
		int meta = par1IBlockAccess.getBlockMetadata(i, j, k);
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, (0.125f * meta), 1.0F);
	}
	
	@Override
	public void onBlockDestroyedByExplosion(World world, int i, int j, int k, Explosion ex) 
	{
		if(!world.isRemote)
		{
		}
	}
}
