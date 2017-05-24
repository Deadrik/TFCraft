package com.bioxx.tfc.Blocks.Terrain;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.Constant.Global;

public class BlockOre3 extends BlockOre
{
	public BlockOre3(Material mat)
	{
		super(mat);
		blockNames = Global.ORE_MINERAL;
	}

	@Override
	public int damageDropped(int dmg)
	{
		return dmg;
	}

	@Override
	public int quantityDropped(int meta, int fortune, Random random)
	{
		if (meta == 13) // saltpeter
			return 1 + random.nextInt(3);
		return 1;
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune)
	{
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();

		int count = quantityDropped(metadata, fortune, world.rand);
		for (int i = 0; i < count; i++)
		{
			ItemStack itemstack = new ItemStack(TFCItems.oreMineralChunk, 1, damageDropped(metadata));

			if (metadata == 5)
				itemstack = kimberliteGemSpawn(); //Drop diamonds
			else if (metadata == 13)
				itemstack = new ItemStack(TFCItems.powder, 1, 4);

			if (itemstack != null)
			{
				ret.add(itemstack);
			}
		}
		return ret;
	}

	@Override
	public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z)
	{
		if(!world.isRemote)
		{
			boolean dropOres = false;
			if(player != null)
			{
				player.addStat(StatList.mineBlockStatArray[getIdFromBlock(this)], 1);
				player.addExhaustion(0.075F);
				dropOres = player.canHarvestBlock(this);
			}
			if (player == null || dropOres)
			{
				int meta = world.getBlockMetadata(x, y, z);
				Random random = new Random();

				ItemStack itemstack = new ItemStack(TFCItems.oreMineralChunk, 1 , damageDropped(meta));

				if(meta == 5)
					itemstack = kimberliteGemSpawn(); //Drop diamonds
				else if (meta == 13) // Saltpeter
					itemstack = new ItemStack(TFCItems.powder, 1 + random.nextInt(3), 4);

				if (itemstack != null)
					dropBlockAsItem(world, x, y, z, itemstack);
			}

		}
		return world.setBlockToAir(x, y, z);
	}

	public ItemStack kimberliteGemSpawn()
	{
		int quality = 0; // Chipped by default
		Random random = new Random();
		if(random.nextInt(50) == 0)
			quality = 1;
		else if (random.nextInt(75) == 0)
			quality = 2;
		else if (random.nextInt(150) == 0)
			quality = 3;
		else if (random.nextInt(300) == 0)
			quality = 4;

		return new ItemStack(TFCItems.gemDiamond, 1, quality);
	}


	@Override
	public void onBlockExploded(World world, int x, int y, int z, Explosion exp)
	{
		Random random = new Random();
		ItemStack itemstack;
		int meta = world.getBlockMetadata(x, y, z);
		itemstack  = new ItemStack(TFCItems.oreMineralChunk, 1, meta + 16);

		if(meta == 5)
			itemstack = kimberliteGemSpawn();
		else if (meta == 13)
			itemstack = new ItemStack(TFCItems.powder, 1 + random.nextInt(3), 4);

		if (itemstack != null)
			dropBlockAsItem(world, x, y, z, itemstack);

		onBlockDestroyedByExplosion(world, x, y, z, exp);
	}
}
