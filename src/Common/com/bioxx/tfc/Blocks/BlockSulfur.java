package com.bioxx.tfc.Blocks;

import java.util.Arrays;
import java.util.Random;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.TFCBlocks;
import com.bioxx.tfc.TFCItems;
import com.bioxx.tfc.api.Constant.Global;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockSulfur extends BlockTerra
{
	int itemMeta = Arrays.asList(Global.POWDER).indexOf("Sulfur Powder");
	IIcon[] icons = new IIcon[4];

	public BlockSulfur(Material material)
	{
		super(material);
	}

	@Override
	public IIcon getIcon(int i, int j) 
	{
		return icons[j];
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k)
	{
		return null;
	}

	@Override
	public void registerBlockIcons(IIconRegister registerer)
	{
		for(int i = 0; i < 4; i++)
			icons[i] = registerer.registerIcon(Reference.ModID + ":" + "ores/Sulfur"+i);
	}

	@Override
	public int getRenderType()
	{
		return TFCBlocks.sulfurRenderId;
	}

	@Override
	public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int l)
	{
		//super.harvestBlock(world, entityplayer, i, j, k, l);
		dropBlockAsItem(world, i, j, k, new ItemStack(TFCItems.Powder, quantityDropped(new Random()), itemMeta));
	}

	@Override
	public Item getItemDropped(int par1, Random par2Random, int par3) {
		return TFCItems.Powder;
	}

	@Override
	public boolean isBlockNormalCube()
	{
		return false;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, Block l)
	{
		int num = 0;
		if(world.getBlock(i, j, k+1).isBlockNormalCube())
			num++;
		if(world.getBlock(i, j, k-1).isBlockNormalCube())
			num++;
		if(world.getBlock(i+1, j, k).isBlockNormalCube())
			num++;
		if(world.getBlock(i-1, j, k).isBlockNormalCube())
			num++;
		if(world.getBlock(i, j+1, k).isBlockNormalCube())
			num++;
		if(world.getBlock(i, j-1, k).isBlockNormalCube())
			num++;
		if(num == 0)
		{
			world.setBlockToAir(i, j, k);
			dropBlockAsItem(world, i, j, k, new ItemStack(TFCItems.Powder, quantityDropped(new Random()), itemMeta));
		}
	}

	@Override
	public int quantityDropped(Random random)
	{
		return 1 + random.nextInt(5);
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess iblockaccess, int i, int j, int k)
	{
		int num = 0;
		if(iblockaccess.getBlock(i, j, k+1).isBlockNormalCube())
		{
			setBlockBounds(0.0F, 0.0F, 0.99F, 1.0F, 1.0F, 1.0F);
			num++;
		}
		if(iblockaccess.getBlock(i, j, k-1).isBlockNormalCube())
		{
			setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.01F);
			num++;
		}
		if(iblockaccess.getBlock(i+1, j, k).isBlockNormalCube())
		{
			setBlockBounds(0.99F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
			num++;
		}
		if(iblockaccess.getBlock(i-1, j, k).isBlockNormalCube())
		{
			setBlockBounds(0.0F, 0.0F, 0.0F, 0.01F, 1.0F, 1.0F);
			num++;
		}
		if(iblockaccess.getBlock(i, j+1, k).isBlockNormalCube())
		{
			setBlockBounds(0.0F, 0.99F, 0.0F, 1.0F, 1.0F, 1.0F);
			num++;
		}
		if(iblockaccess.getBlock(i, j-1, k).isBlockNormalCube())
		{
			setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.01F, 1.0F);
			num++;
		}
		if(num > 1)
		{
			setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		}
	}
}
