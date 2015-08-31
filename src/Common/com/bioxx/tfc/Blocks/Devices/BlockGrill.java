package com.bioxx.tfc.Blocks.Devices;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Blocks.BlockTerraContainer;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Core.TFC_Textures;
import com.bioxx.tfc.Items.ItemBlocks.ItemMetalTrapDoor;
import com.bioxx.tfc.TileEntities.TEGrill;
import com.bioxx.tfc.api.TFCBlocks;

public class BlockGrill extends BlockTerraContainer
{
	public BlockGrill()
	{
		super(Material.iron);
		this.setBlockBounds(0, 0, 0, 1, 0.05f, 1);
		this.setCreativeTab(TFCTabs.TFC_DEVICES);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityplayer, int side, float hitX, float hitY, float hitZ)
	{
		super.onBlockActivated(world, x, y, z, entityplayer, side, hitX, hitY, hitZ);
		if(entityplayer.inventory.getCurrentItem() != null && entityplayer.inventory.getCurrentItem().getItem() instanceof ItemMetalTrapDoor)
			return false;

		TEGrill te = (TEGrill)world.getTileEntity(x, y, z);
		int meta = world.getBlockMetadata(x, y, z);

		if (isGrillOpen(meta) || entityplayer.isSneaking() && te.isEmpty())
		{
			world.setBlockMetadataWithNotify(x, y, z, meta ^ 4, 2);
			world.playAuxSFXAtEntity(entityplayer, 1003, x, y, z, 0);
			return true;
		}
		else
		{
			entityplayer.openGui(TerraFirmaCraft.instance, 43, world, x, y, z);
			return true;
		}
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2)
	{
		return new TEGrill();
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public boolean isReplaceable(IBlockAccess world, int x, int y, int z)
	{
		return false;
	}

	/**
	 * Returns a bounding box from the pool of bounding boxes (this means this
	 * box can change after the pool has been cleared to be reused)
	 */
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
	{
		this.setBlockBoundsBasedOnState(world, x, y, z);
		return super.getCollisionBoundingBoxFromPool(world, x, y, z);
	}

	/**
	 * Ray traces through the blocks collision from start vector to end vector
	 * returning a ray trace hit. Args: world, x, y, z, startVec, endVec
	 */
	@Override
	public MovingObjectPosition collisionRayTrace(World par1World, int par2, int par3, int par4, Vec3 par5Vec3, Vec3 par6Vec3)
	{
		this.setBlockBoundsBasedOnState(par1World, par2, par3, par4);
		return super.collisionRayTrace(par1World, par2, par3, par4, par5Vec3, par6Vec3);
	}

	/**
	 * Updates the blocks bounds based on its current state. Args: world, x, y,
	 * z
	 */
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess access, int x, int y, int z)
	{
		if (access.getTileEntity(x, y, z) != null && access.getTileEntity(x, y, z) instanceof TEGrill)
			this.setBlockBoundsForBlockRender(access.getBlockMetadata(x, y, z), ((TEGrill) access.getTileEntity(x, y, z)).data);
	}

	@Override
	public boolean getBlocksMovement(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		return !isGrillOpen(par1IBlockAccess.getBlockMetadata(par2, par3, par4));
	}

	public boolean isGrillOpen(int meta)
	{
		return (meta & 4) != 0;
	}

	public void setBlockBoundsForBlockRender(int meta, int data)
	{
		float f = 0.05F;
		int side = data & 7;
		int hinge = data >> 4;
		float fx = 0, fy = 0, fz = 0, fx2 = 1, fy2 = 1, fz2 = 1;

		if (isGrillOpen(meta))
		{
			if (hinge == 0)
			{
				switch (side)
				{
				case 0:
				case 1:
				case 2:
				case 3:
				{
					fx2 = f;
					break;
				}
				case 4:
				case 5:
				{
					fy2 = f;
					break;
				}
				default:
					fx2 = f;
					break;
				}
			}
			else if (hinge == 1)
			{
				switch (side)
				{
				case 2:
				case 3:
				{
					fy2 = f;
					break;
				}
				default:
					fz2 = f;
					break;
				}
			}
			else if (hinge == 2)
			{
				switch (side)
				{
				case 4:
				case 5:
				{
					fy = 1 - f;
					break;
				}
				default:
					fx = 1 - f;
					break;
				}
			}
			else if (hinge == 3)
			{
				switch (side)
				{
				case 2:
				case 3:
				{
					fy = 1 - f;
					break;
				}
				case 4:
				case 5:
				{
					fz = 1 - f;
					break;
				}
				default:
					fz = 1 - f;
					break;
				}
			}

			this.setBlockBounds(fx, fy, fz, fx2, fy2, fz2);
		}
		else
			this.setBlockBounds(0, 0, 0, 1, 0.05f, 1);
	}

	/***********************************************************************************
	 * 
	 * Client Only Code Below This Point
	 * 
	 ***********************************************************************************/
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegisterer)
	{
		this.blockIcon = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "devices/Grill Wrought Iron");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		if (meta == 0)
		{
			if (/*TFCOptions.use2DGrill && */side == 0 || side == 1)
			{
				return blockIcon;
			}
			return TFC_Textures.sheetWroughtIron;
		}
		return blockIcon;
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderType()
	{
		return TFCBlocks.grillRenderId;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess par1iBlockAccess, int par2, int par3, int par4, int par5) {
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	/**
	 * Returns the bounding box of the wired rectangular prism to render.
	 */
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z)
	{
		this.setBlockBoundsBasedOnState(world, x, y, z);
		return super.getSelectedBoundingBoxFromPool(world, x, y, z);
	}
}
