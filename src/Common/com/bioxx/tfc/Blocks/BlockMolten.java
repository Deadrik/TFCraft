package com.bioxx.tfc.Blocks;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.bioxx.tfc.Reference;
public class BlockMolten extends BlockTerra
{
	private IIcon moltenOff;
	public BlockMolten()
	{
		super(Material.iron);
	}

	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z)
	{
		int meta = world.getBlockMetadata(x, y, z);
		if((meta & 8) > 0)
			return 15;
		return 0;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess access, int i, int j, int k)
	{
		int meta = access.getBlockMetadata(i, j, k) & 7;
		float f = 0.125F;
		setBlockBounds(0.0F, 0.0F, 0.0f, 1f, f+(f*meta), 1F);
	}

	@Override
	public IIcon getIcon(int side, int meta)
	{
		if((meta & 8) == 0)
			return this.moltenOff;
		return this.blockIcon;
	}

	@Override
	public void registerBlockIcons(IIconRegister iconRegisterer)
	{
		this.blockIcon = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "devices/Molten Rock");
		moltenOff = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "devices/Molten Rock Off");
	}

	@Override
	public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int l)
	{
		//super.harvestBlock(world, entityplayer, i, j, k, l);
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
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z)
	{
		return null;
	}

	@Override
	public Item getItemDropped(int metadata, Random rand, int fortune)
	{
		return null;
	}
}
