package com.bioxx.tfc.Blocks;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.TFCBlocks;
import com.bioxx.tfc.TFCItems;
import com.bioxx.tfc.TileEntities.TileEntityBloom;

public class BlockBloom extends BlockTerraContainer
{
	public BlockBloom()
	{
		super(Material.iron);
	}

	@Override
	public boolean isOpaqueCube()
	{
		return true;
	}

	@Override
	public int damageDropped(int j)
	{
		return j;
	}

	@Override
	public void registerBlockIcons(IIconRegister iconRegisterer)
	{
		this.blockIcon = iconRegisterer.registerIcon(Reference.ModID + ":" + "devices/Iron Bloom");
	}

	@Override
	public TileEntity createNewTileEntity(World w, int meta)
	{
		return new TileEntityBloom();
	}

	@Override
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
	{
		return null;
	}

	@Override
	public void onBlockPreDestroy(World world, int i, int j, int k, int meta) 
	{
		TileEntityBloom te = (TileEntityBloom)world.getTileEntity(i, j, k);
		EntityItem ei = new EntityItem(world, i, j, k, new ItemStack(TFCItems.RawBloom, 1, te.size));

		int[] pos = getBloomery(world, i, j, k);
		ei.motionX = 0; ei.motionY = 0; ei.motionZ = 0;
		ei.setPosition(i + 0.5 + pos[0], j + 0.5, k + 0.5 + pos[1]);
		ei.delayBeforeCanPickup = 0;
		world.spawnEntityInWorld(ei);
	}

	public int[] getBloomery(World world, int x, int y, int z)
	{
		if(world.getBlock(x + 1, y, z) == TFCBlocks.EarlyBloomery)
			return new int[]{2, 0};
		if(world.getBlock(x - 1, y, z) == TFCBlocks.EarlyBloomery)
			return new int[]{-2, 0};
		if(world.getBlock(x, y, z + 1) == TFCBlocks.EarlyBloomery)
			return new int[]{0, 2};
		if(world.getBlock(x, y, z - 1) == TFCBlocks.EarlyBloomery)
			return new int[]{0, -2};

		return new int[]{0, 0};
	}
}
