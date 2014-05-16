package com.bioxx.tfc.Blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import com.bioxx.tfc.TileEntities.TEPartial;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockPartial extends BlockTerraContainer
{
	public BlockPartial(Material m)
	{
		super(m);
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
	@SideOnly(Side.CLIENT)
	public boolean addDestroyEffects(World world, int x, int y, int z, int meta, EffectRenderer effectRenderer)
	{
		// TODO Include particle spawning logic, or replace this with a functional getBlockTextureFromSideAndMetadata 
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean addHitEffects(World worldObj, MovingObjectPosition target, EffectRenderer effectRenderer)
	{
		// TODO Include particle spawning logic, or replace this with a functional getBlockTextureFromSideAndMetadata 
		return true;
	}

	@Override
	public void onBlockAdded(World world, int par2, int par3, int par4)
	{
		super.onBlockAdded(world, par2, par3, par4);
		world.markBlockForUpdate(par2, par3, par4);
	}

	@Override
	public void onBlockDestroyedByExplosion(World world, int i, int j, int k, Explosion ex)
	{
		if(!world.isRemote)
		{
		}
	}

	@Override
	public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int l)
	{
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2)
	{
		return new TEPartial();
	}

	@Override
	public int getFlammability(IBlockAccess world, int x, int y, int z, ForgeDirection face)
	{
		TEPartial te = (TEPartial) world.getTileEntity(x, y, z);
		if(te.TypeID >= 0)
			return Blocks.fire.getFlammability(Block.getBlockById(te.TypeID));
		else return 0;
	}

	@Override
	public int getFireSpreadSpeed(IBlockAccess world, int x, int y, int z, ForgeDirection face)
	{
		TEPartial te = (TEPartial) world.getTileEntity(x, y, z);
		if(te.TypeID >= 0)
			return Blocks.fire.getEncouragement(Block.getBlockById(te.TypeID));
		else return 0;
	}
}
