package com.bioxx.tfc.Blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.TFCBlocks;
import com.bioxx.tfc.TileEntities.TEFarmland;
import com.bioxx.tfc.api.Constant.Global;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockFarmland extends BlockContainer
{
	private Block dirtBlock;
	private IIcon[] DirtTexture = new IIcon[Global.STONE_ALL.length];
	private int textureOffset = 0;

	public BlockFarmland(Block block, int tex)
	{
		super(Material.ground);
		this.setTickRandomly(true);
		this.dirtBlock = block;
		this.textureOffset = tex;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister registerer)
	{
		for(int i = textureOffset; i < (textureOffset == 0 ? 16 : Global.STONE_ALL.length); i++)
			DirtTexture[i] = registerer.registerIcon(Reference.ModID + ":" + "farmland/Farmland " + Global.STONE_ALL[i]);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(IBlockAccess access, int xCoord, int yCoord, int zCoord, int side)
	{
		int meta = access.getBlockMetadata(xCoord, yCoord, zCoord);
		if (side == 1)//top
			return DirtTexture[meta + textureOffset];
		else
			return this.dirtBlock.getIcon(side, meta);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int side, int meta)
	{
		if (side == ForgeDirection.UP.ordinal())
			return DirtTexture[meta + textureOffset];
		else
			return this.dirtBlock.getIcon(0, meta);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	{
		return AxisAlignedBB.getBoundingBox(par2 + 0, par3 + 0, par4 + 0, par2 + 1, par3 + 1, par4 + 1);
	}

	@Override
	public Item getItemDropped(int par1, Random par2Random, int par3)
	{
		return Item.getItemById(0);
	}

	/**
	 * returns true if there is at least one cropblock nearby (x-1 to x+1, y+1, z-1 to z+1)
	 */
	private boolean isCropsNearby(World par1World, int par2, int par3, int par4)
	{
		byte var5 = 0;
		for (int var6 = par2 - var5; var6 <= par2 + var5; ++var6)
		{
			for (int var7 = par4 - var5; var7 <= par4 + var5; ++var7)
			{
				Block var8 = par1World.getBlock(var6, par3 + 1, var7);
				if (var8 instanceof IPlantable && canSustainPlant(par1World, par2, par3, par4, ForgeDirection.UP, (IPlantable)var8))
					return true;
			}
		}
		return false;
	}

	/**
	 * returns true if there's water nearby (x-4 to x+4, y to y+1, k-4 to k+4)
	 */
	public static boolean isFreshWaterNearby(World world, int i, int j, int k)
	{
		for (int x = i - 4; x <= i + 4; ++x)
		{
			for (int y = j; y <= j + 1; ++y)
			{
				for (int z = k - 4; z <= k + 4; ++z)
				{
					Block b = world.getBlock(x, y, z);
					if (b == TFCBlocks.FreshWater)
						return true;
				}
			}
		}
		return false;
	}

	public static boolean isSaltWaterNearby(World world, int i, int j, int k)
	{
		for (int x = i - 4; x <= i + 4; ++x)
		{
			for (int y = j; y <= j + 1; ++y)
			{
				for (int z = k - 4; z <= k + 4; ++z)
				{
					Block b = world.getBlock(x, y, z);
					if (b == TFCBlocks.SaltWater)
						return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2)
	{
		return new TEFarmland();
	}
}
