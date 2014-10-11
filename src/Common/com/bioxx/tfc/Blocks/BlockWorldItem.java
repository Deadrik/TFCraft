package com.bioxx.tfc.Blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import com.bioxx.tfc.Core.TFC_Textures;
import com.bioxx.tfc.TileEntities.TEWorldItem;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockWorldItem extends BlockTerraContainer
{
	public BlockWorldItem()
	{
		super(Material.circuits);
		this.setBlockBounds(0F, 0.00F, 0F, 1F, 0.05F, 1F);
	}

	@Override
	public boolean getBlocksMovement(IBlockAccess bAccess, int x, int y, int z)
	{
		return true;
	}

	/*@Override
	public void harvestBlock(World world, EntityPlayer entityplayer, int x, int y, int z, int l)
	{
	}*/

	@Override
	public void onBlockPreDestroy(World world, int i, int j, int k, int meta) 
	{
		if(!world.isRemote)
		{
			TEWorldItem te = (TEWorldItem)world.getTileEntity(i, j, k);
			EntityItem ei = new EntityItem(world, i, j, k, te.storage[0]);
			world.spawnEntityInWorld(ei);
		}
	}

	@Override
	public Item getItemDropped(int metadata, Random rand, int fortune)
	{
		return null;
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z)
	{
		return null;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityplayer, int side, float hitX, float hitY, float hitZ)
	{
		if(!world.isRemote)
			return world.setBlockToAir(x, y, z);
		return false;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
	{
		if (world.isAirBlock(x, y - 1, z))
		{
			world.setBlockToAir(x, y, z);
			return;
		}
		if (!world.getBlock(x, y - 1, z).isSideSolid(world, x, y, z, ForgeDirection.UP))
		{
			world.setBlockToAir(x, y, z);
			return;
		}
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	public boolean isReplaceable(IBlockAccess world, int x, int y, int z)
	{
		return false;
	}

	@Override
	public boolean canBeReplacedByLeaves(IBlockAccess world, int x, int y, int z)
	{
		return false;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k)
	{
		return null;
	}

	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z)
	{
		return AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y + 0.25, z + 1);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg)
	{
		this.blockIcon = TFC_Textures.InvisibleTexture; // This gets registered in BlockGrass
	}

	@Override
	public TileEntity createTileEntity(World world, int meta)
	{
		return new TEWorldItem();
	}
}
