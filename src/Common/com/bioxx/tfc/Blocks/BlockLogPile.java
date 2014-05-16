package com.bioxx.tfc.Blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.TFCItems;
import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.TileEntities.TELogPile;

public class BlockLogPile extends BlockTerraContainer
{
	IIcon[] icons = new IIcon[3];

	public BlockLogPile()
	{
		super(Material.wood);
	}

	public static int getDirectionFromMetadata(int i)
	{
		return i & 3;
	}

	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int par6, float par7, float par8, float par9)
	{
		if (world.isRemote)
		{
			return true;
		}
		else
		{
			if((TELogPile)world.getTileEntity(i, j, k)!=null)
			{
				TELogPile te;
				te = (TELogPile)world.getTileEntity(i, j, k);
				ItemStack is = entityplayer.getCurrentEquippedItem();

				if(is != null && is.getItem() == TFCItems.Logs)
				{
					return false;
				}
				else
				{
					entityplayer.openGui(TerraFirmaCraft.instance, 0, world, i, j, k);
				}
				return true;
			} else {
				return false;
			}

		}

	}

	@Override
	public IIcon getIcon(int i, int j)
	{
		if(j == 0 || j == 2)//+z
		{
			if(i == 0) {
				return icons[1];
			} else if(i == 1) {
				return icons[1];
			} else if(i == 2) {
				return icons[2];
			} else if(i == 3) {
				return icons[2];
			} else if(i == 4) {
				return icons[0];
			} else if(i == 5) {
				return icons[0];
			}
		}
		else if(j == 1 || j == 3)//-x
		{
			if(i == 0) {
				return icons[0];
			} else if(i == 1) {
				return icons[0];
			} else if(i == 2) {
				return icons[0];
			} else if(i == 3) {
				return icons[0];
			} else if(i == 4) {
				return icons[2];
			} else if(i == 5) {
				return icons[2];
			}
		}

		return icons[0];

	}

	@Override
	public void registerBlockIcons(IIconRegister iconRegisterer)
	{
		icons[0] = iconRegisterer.registerIcon(Reference.ModID + ":" + "devices/Log Pile Side 0");
		icons[1] = iconRegisterer.registerIcon(Reference.ModID + ":" + "devices/Log Pile Side 1");
		icons[2] = iconRegisterer.registerIcon(Reference.ModID + ":" + "devices/Log Pile End");
	}

	public void Eject(World world, int x, int y, int z)
	{
		if(!world.isRemote && (TELogPile)world.getTileEntity(x, y, z) != null)
		{
			TELogPile TELogPile;
			TELogPile = (TELogPile)world.getTileEntity(x, y, z);
			TELogPile.ejectContents();
			world.removeTileEntity(x, y, z);
		}
	}

	@Override
	public Item getItemDropped(int par1, Random random, int par3)
	{
		return Item.getItemById(0);
	}

	@Override
	public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int l)
	{
		Eject(world, i, j, k);
	}

	@Override
	public void onBlockDestroyedByExplosion(World world, int x, int y, int z, Explosion ex)
	{
		Eject(world, x, y, z);
	}

	@Override
	public void onBlockDestroyedByPlayer(World world, int x, int y, int z, int i)
	{
		Eject(world, x, y, z);
	}

	@Override
	public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z)
	{
		Eject(world, x, y, z);
		return super.removedByPlayer(world, player, x, y, z);
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2)
	{
		return new TELogPile();
	}

	@Override
	public void onNeighborBlockChange(World par1World, int x, int y, int z, Block block)
	{
		if(!par1World.isRemote)
		{
			TELogPile teLogPile = (TELogPile)par1World.getTileEntity(x, y, z);
			if(teLogPile != null)
				teLogPile.neighborChanged();
		}
	}
}
