package com.bioxx.tfc.Blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import net.minecraftforge.common.util.ForgeDirection;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import com.bioxx.tfc.Items.Tools.ItemHammer;
import com.bioxx.tfc.TileEntities.TEPartial;
import com.bioxx.tfc.api.Tools.IToolChisel;

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
		// Do Nothing
	}

	/**
	 * Called when the block is clicked by a player. Args: x, y, z, entityPlayer
	 */
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityplayer, int side, float hitX, float hitY, float hitZ) {
		boolean hasHammer = false;
		for (int i = 0; i < 9; i++)
			if (entityplayer.inventory.mainInventory[i] != null && entityplayer.inventory.mainInventory[i].getItem() instanceof ItemHammer)
				hasHammer = true;

		if (entityplayer.getCurrentEquippedItem() != null && entityplayer.getCurrentEquippedItem().getItem() instanceof IToolChisel &&
				hasHammer && !world.isRemote && ((IToolChisel) entityplayer.getCurrentEquippedItem().getItem()).canChisel(entityplayer, x, y, z)) {
			Block id = world.getBlock(x, y, z);
			byte meta = (byte) world.getBlockMetadata(x, y, z);
			return ((IToolChisel) entityplayer.getCurrentEquippedItem().getItem()).onUsed(world, entityplayer, x, y, z, id, meta, side, hitX, hitY, hitZ);
		}
		return false;
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
		if(te.typeID >= 0)
			return Blocks.fire.getFlammability(Block.getBlockById(te.typeID));
		else return 0;
	}

	@Override
	public int getFireSpreadSpeed(IBlockAccess world, int x, int y, int z, ForgeDirection face)
	{
		TEPartial te = (TEPartial) world.getTileEntity(x, y, z);
		if(te.typeID >= 0)
			return Blocks.fire.getEncouragement(Block.getBlockById(te.typeID));
		else return 0;
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z)
	{
		return null;
	}
}
