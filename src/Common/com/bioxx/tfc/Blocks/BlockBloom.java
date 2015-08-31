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
import com.bioxx.tfc.TileEntities.TEBloom;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.TFCItems;

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
		this.blockIcon = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "devices/Iron Bloom");
	}

	@Override
	public TileEntity createNewTileEntity(World w, int meta)
	{
		return new TEBloom();
	}

	@Override
	public Item getItemDropped(int i, Random rand, int j)
	{
		return null;
	}

	@Override
	public void onBlockPreDestroy(World world, int i, int j, int k, int meta) 
	{
		TEBloom te = (TEBloom)world.getTileEntity(i, j, k);
		EntityItem ei = new EntityItem(world, i, j, k, new ItemStack(TFCItems.rawBloom, 1, te.size));

		int[] pos = getBloomery(world, i, j, k);
		ei.motionX = 0; ei.motionY = 0; ei.motionZ = 0;
		ei.setPosition(i + 0.5 + pos[0], j + 0.5, k + 0.5 + pos[1]);
		ei.delayBeforeCanPickup = 0;
		world.spawnEntityInWorld(ei);
	}

	public int[] getBloomery(World world, int x, int y, int z)
	{
		if(world.getBlock(x + 1, y, z) == TFCBlocks.bloomery)
			return new int[]{2, 0};
		if(world.getBlock(x - 1, y, z) == TFCBlocks.bloomery)
			return new int[]{-2, 0};
		if(world.getBlock(x, y, z + 1) == TFCBlocks.bloomery)
			return new int[]{0, 2};
		if(world.getBlock(x, y, z - 1) == TFCBlocks.bloomery)
			return new int[]{0, -2};

		return new int[]{0, 0};
	}
}
