package com.bioxx.tfc.Blocks.Terrain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.bioxx.tfc.Blocks.BlockTerra;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Reference;
import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.TFCItems;

public class BlockClay extends BlockTerra
{
	protected IIcon[] DirtTexture;
	protected int textureOffset = 0;

	public BlockClay(int texOff)
	{
		super(Material.clay);
		this.setCreativeTab(TFCTabs.TFCBuilding);
		textureOffset = texOff;
	}

	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list)
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

	@Override
	public void registerBlockIcons(IIconRegister registerer)
	{
		int count = (textureOffset == 0 ? 16 : Global.STONE_ALL.length - 16);
		DirtTexture = new IIcon[count];
		for(int i = 0; i < count; i++)
			DirtTexture[i] = registerer.registerIcon(Reference.ModID + ":" + "clay/Clay " + Global.STONE_ALL[i + textureOffset]);
	}

	/**
	 * Retrieves the block texture to use based on the display side. Args: iBlockAccess, x, y, z, side
	 */
	@Override
	public IIcon getIcon(IBlockAccess bAccess, int x, int y, int z, int side)
	{
		int meta = bAccess.getBlockMetadata(x, y, z);
		if(meta >= DirtTexture.length) return DirtTexture[DirtTexture.length - 1];
		return DirtTexture[meta];
	}

	/**
	 * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
	 */
	@Override
	public IIcon getIcon(int side, int meta)
	{
		if(meta >= DirtTexture.length) return DirtTexture[DirtTexture.length - 1];
		return DirtTexture[meta];
	}

	/**
	 * Returns the items to drop on destruction.
	 */
	@Override
	public Item getItemDropped(int metadata, Random rand, int fortune)
	{
		return TFCItems.ClayBall;
	}

	/**
	 * Returns the quantity of items to drop on block destruction.
	 */
	@Override
	public int quantityDropped(Random rand)
	{
		return rand.nextInt(3) + 1;
	}

	@Override
	public int damageDropped(int dmg)
	{
		return dmg;
	}

	/**
	 * The reason for overriding getDrops is because we only want to drop the clay item with meta 0,
	 * but also need damageDropped to return the correct meta for localization purposes.
	 */
	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune)
	{
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		int count = this.quantityDropped(world.rand);
		Item item = getItemDropped(metadata, world.rand, fortune);
		for(int i = 0; i < count; i++)
			ret.add(new ItemStack(item, 1, 0));
		return ret;
	}

	/*public void getCollidingBoundingBoxes(World world, int i, int j, int k, AxisAlignedBB par5AxisAlignedBB, ArrayList par6ArrayList)
	{
		if((!world.isBlockOpaqueCube(i+1, j, k) || !world.isBlockOpaqueCube(i-1, j, k) || 
				!world.isBlockOpaqueCube(i, j, k+1) || !world.isBlockOpaqueCube(i, j, k-1)) && 
				!world.isBlockOpaqueCube(i, j+1, k))
		{
			par6ArrayList.add(AxisAlignedBB.getBoundingBoxFromPool(i, j, k,i +1,j + 0.5f,k + 1));

			double minX = 0.25;
			double minZ = 0.25;
			double maxX = 0.75;
			double maxZ = 0.75;

			if(!world.isBlockOpaqueCube(i+1, j, k))
				maxX = 0.5;
			if(!world.isBlockOpaqueCube(i-1, j, k))
				minX = 0.5;
			if(!world.isBlockOpaqueCube(i, j, k+1))
				maxZ = 0.5;
			if(!world.isBlockOpaqueCube(i, j, k-1))
				minZ = 0.5;

			par6ArrayList.add(AxisAlignedBB.getBoundingBoxFromPool(i + minX, j + 0.5, k + minZ, i + maxX, j + 1, k + maxZ));

		}
		else
			par6ArrayList.add(AxisAlignedBB.getBoundingBoxFromPool(i, j, k,i + 1,j + 1,k +1));
	}*/
}
