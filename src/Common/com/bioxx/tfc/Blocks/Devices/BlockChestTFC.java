package com.bioxx.tfc.Blocks.Devices;

import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.TileEntities.TileEntityChestTFC;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockChestTFC extends BlockChest
{
	public BlockChestTFC(int par1)
	{
		super(par1);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int var2)
	{
		return new TileEntityChestTFC();
	}

	@Override
	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
	{
		if (par1World.isRemote)
		{
			return true;
		}
		else
		{
			IInventory iinventory = this.func_149951_m(par1World, par2, par3, par4);

			if (iinventory != null)
				par5EntityPlayer.openGui(TerraFirmaCraft.instance, 29, par1World, par2, par3, par4);

			return true;
		}
	}

	/**
	 * Called when the block is placed in the world.
	 */
	@Override
	public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack)
	{
		Block l = par1World.getBlock(par2, par3, par4 - 1);
		Block i1 = par1World.getBlock(par2, par3, par4 + 1);
		Block j1 = par1World.getBlock(par2 - 1, par3, par4);
		Block k1 = par1World.getBlock(par2 + 1, par3, par4);
		byte b0 = 0;
		int l1 = MathHelper.floor_double(par5EntityLivingBase.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;

		if (l1 == 0) b0 = 2;
		if (l1 == 1) b0 = 5;
		if (l1 == 2) b0 = 3;
		if (l1 == 3) b0 = 4;

		if (l != this && i1 != this && j1 != this && k1 != this)
		{
			par1World.setBlockMetadataWithNotify(par2, par3, par4, b0, 3);
		}
		else
		{
			if ((l == this || i1 == this) && (b0 == 4 || b0 == 5))
			{
				if (l == this)
					par1World.setBlockMetadataWithNotify(par2, par3, par4 - 1, b0, 3);
				else
					par1World.setBlockMetadataWithNotify(par2, par3, par4 + 1, b0, 3);

				par1World.setBlockMetadataWithNotify(par2, par3, par4, b0, 3);
			}

			if ((j1 == this || k1 == this) && (b0 == 2 || b0 == 3))
			{
				if (j1 == this)
					par1World.setBlockMetadataWithNotify(par2 - 1, par3, par4, b0, 3);
				else
					par1World.setBlockMetadataWithNotify(par2 + 1, par3, par4, b0, 3);

				par1World.setBlockMetadataWithNotify(par2, par3, par4, b0, 3);
			}
		}

		if (par6ItemStack.hasDisplayName())
			((TileEntityChestTFC)par1World.getTileEntity(par2, par3, par4)).func_145976_a(par6ItemStack.getDisplayName());
	}
}
