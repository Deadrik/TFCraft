package com.bioxx.tfc.Blocks.Devices;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import net.minecraftforge.common.util.ForgeDirection;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Blocks.BlockTerraContainer;
import com.bioxx.tfc.Core.TFC_Textures;
import com.bioxx.tfc.TileEntities.TEFoodPrep;

public class BlockFoodPrep extends BlockTerraContainer
{
	public BlockFoodPrep()
	{
		super();
		this.setBlockBounds(0, 0, 0, 1, 0.15f, 1);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityplayer, int side, float hitX, float hitY, float hitZ)
	{
		if(!world.isRemote)
		{
			TEFoodPrep te = (TEFoodPrep) world.getTileEntity(x, y, z);
			te.openGui(entityplayer);
		}
		return true;
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
	public void registerBlockIcons(IIconRegister iconRegisterer)
	{
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess access, int i, int j, int k, int meta)
	{
		return TFC_Textures.invisibleTexture;
	}

	@Override
	public boolean getBlocksMovement(IBlockAccess par1IBlockAccess, int i, int j, int k)
	{
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2)
	{
		return new TEFoodPrep();
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune)
	{
		return new ArrayList<ItemStack>();
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	{
		return null;
	}

	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World par1World, int i, int j, int k)
	{
		return AxisAlignedBB.getBoundingBox(i, j, k, i+1, j+0.15, k+1);
	}

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, Block block)
	{
		if(!world.isRemote)
		{
			if(!world.isSideSolid(i, j - 1, k, ForgeDirection.UP))
			{
				((TEFoodPrep)world.getTileEntity(i, j, k)).ejectContents();
				world.setBlockToAir(i, j, k);
				return;
			}
		}
	}

	@Override
	public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int l)
	{
		eject(world,i,j,k);
	}

	@Override
	public void onBlockDestroyedByExplosion(World par1World, int par2, int par3, int par4, Explosion ex)
	{
		eject(par1World,par2,par3,par4);
	}

	@Override
	public void onBlockDestroyedByPlayer(World par1World, int par2, int par3, int par4, int par5)
	{
		eject(par1World,par2,par3,par4);
	}

	@Override
	public void breakBlock(World par1World, int par2, int par3, int par4, Block par5, int par6)
	{
		eject(par1World,par2,par3,par4);
	}

	//public void onBlockRemoval(World par1World, int par2, int par3, int par4) {Eject(par1World,par2,par3,par4);}

	public void eject(World par1World, int par2, int par3, int par4)
	{
		if (par1World.getTileEntity(par2, par3, par4) instanceof TEFoodPrep)
		{
			TEFoodPrep te = (TEFoodPrep)par1World.getTileEntity(par2, par3, par4);
			te.ejectContents();
			par1World.removeTileEntity(par2, par3, par4);
		}
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z)
	{
		return null;
	}

	/**
	 * Displays a flat icon image for an ItemStack containing the block, instead of a render. Using primarily for WAILA HUD.
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public String getItemIconName()
	{
		return Reference.MOD_ID + ":" + "devices/foodprep";
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean addHitEffects(World worldObj, MovingObjectPosition target, EffectRenderer effectRenderer)
	{
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean addDestroyEffects(World world, int x, int y, int z, int meta, EffectRenderer effectRenderer)
	{
		return world.getBlock(x, y, z) == this;
	}
}
