package com.bioxx.tfc.Blocks;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.TileEntities.TEFarmland;
import com.bioxx.tfc.api.Constant.Global;

public class BlockFarmland extends BlockContainer
{
	private Block dirtBlock;
	private IIcon[] dirtTexture;
	private int textureOffset;

	public BlockFarmland(Block block, int tex)
	{
		super(Material.ground);
		this.setTickRandomly(true);
		this.dirtBlock = block;
		this.textureOffset = tex;
		this.setCreativeTab(TFCTabs.TFC_BUILDING);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister registerer)
	{
		int count = (textureOffset == 0 ? 16 : Global.STONE_ALL.length - 16);
		dirtTexture = new IIcon[count];
		for(int i = 0; i < count; i++)
			dirtTexture[i] = registerer.registerIcon(Reference.MOD_ID + ":" + "farmland/Farmland " + Global.STONE_ALL[i + textureOffset]);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@SideOnly(Side.CLIENT)
	@Override
	/**
	 * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
	 */
	public void getSubBlocks(Item item, CreativeTabs tabs, List list)
	{
		// Change to false if this block should not be added to the creative tab
		Boolean addToCreative = true;

		if(addToCreative)
		{
			int count;
			if(textureOffset == 0) count = 16;
			else count = Global.STONE_ALL.length - 16;

			for(int i = 0; i < count; i++)
				list.add(new ItemStack(item, 1, i));
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(IBlockAccess access, int x, int y, int z, int side)
	{
		int meta = access.getBlockMetadata(x, y, z);
		if (meta < 0 || meta >= dirtTexture.length)
			meta = 0;
		if (side == 1)//top
			return dirtTexture[meta];
		else
			return this.dirtBlock.getIcon(side, meta);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int side, int meta)
	{
		if (meta < 0 || meta >= dirtTexture.length)
			meta = 0;
		if (side == ForgeDirection.UP.ordinal())
			return dirtTexture[meta];
		else
			return this.dirtBlock.getIcon(0, meta);
	}

	@Override
	public int damageDropped(int dmg)
	{
		return dmg;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
	{
		return AxisAlignedBB.getBoundingBox(x + 0, y + 0, z + 0, x + 1, y + 1, z + 1);
	}

	@Override
	public Item getItemDropped(int metadata, Random rand, int fortune)
	{
		return Item.getItemById(0);
	}

	/**
	 * returns true if there is at least one cropblock nearby (x-1 to x+1, y+1, z-1 to z+1)
	 */
	/*private boolean isCropsNearby(World world, int x, int y, int z)
	{
		byte var5 = 0;
		for (int var6 = x - var5; var6 <= x + var5; ++var6)
		{
			for (int var7 = z - var5; var7 <= z + var5; ++var7)
			{
				Block var8 = world.getBlock(var6, y + 1, var7);
				if (var8 instanceof IPlantable && canSustainPlant(world, x, y, z, ForgeDirection.UP, (IPlantable)var8))
					return true;
			}
		}
		return false;
	}*/

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
					if (world.blockExists(x, y, z))
					{
						Block b = world.getBlock(x, y, z);
						if (TFC_Core.isFreshWater(b))
							return true;
					}
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
					if (TFC_Core.isSaltWater(b))
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
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TEFarmland();
	}

	@Override
	public boolean canSustainPlant(IBlockAccess world, int x, int y, int z, ForgeDirection direction, IPlantable plantable)
	{
		Block plant = plantable.getPlant(world, x, y + 1, z);
		if (plant == Blocks.pumpkin_stem || plant == Blocks.melon_stem)
			return false;

		EnumPlantType plantType = plantable.getPlantType(world, x, y + 1, z);
		if (plantType == EnumPlantType.Crop)
			return true;

		return super.canSustainPlant(world, x, y, z, direction, plantable);
	}
}
