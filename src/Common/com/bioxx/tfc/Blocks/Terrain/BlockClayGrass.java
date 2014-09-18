package com.bioxx.tfc.Blocks.Terrain;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.bioxx.tfc.TFCBlocks;
import com.bioxx.tfc.TFCItems;
import com.bioxx.tfc.Core.TFC_Core;

public class BlockClayGrass extends BlockGrass
{
	public BlockClayGrass(int texOff)
	{
		super(texOff);
	}

	@Override
	public int getRenderType()
	{
		return TFCBlocks.clayGrassRenderId;
	}

	@Override
	public int damageDropped(int dmg)
	{
		return dmg;
	}

	@Override
	public Item getItemDropped(int metadata, Random rand, int fortune)
	{
		return TFCItems.ClayBall;
	}

	@Override
	public int quantityDropped(Random rand)
	{
		return rand.nextInt(4);
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

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block b)
	{

	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand)
	{
		if (world.getBlockLightValue(x, y + 1, z) < 4 && world.getBlock(x, y + 1, z).getLightOpacity() > 2)
			world.setBlock(x, y, z, TFC_Core.getTypeForClay(world.getBlockMetadata(x, y, z) + textureOffset), world.getBlockMetadata(x, y, z), 0x2);
		else if (world.getBlockLightValue(x, y + 1, z) >= 4)
		{
			spreadGrass(world, x, y, z, rand);
		}

		world.markBlockForUpdate(x, y, z);
	}
}
