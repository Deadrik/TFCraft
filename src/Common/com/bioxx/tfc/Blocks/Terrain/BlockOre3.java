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
	public BlockOre3(Material material)
	{
		super(material);
		blockNames = Global.ORE_MINERAL2;
	}

	@Override
	public int damageDropped(int dmg)
	{
		return dmg + Global.ORE_METAL.length+Global.ORE_MINERAL.length;
	}

	@Override
	public int quantityDropped(int meta, int fortune, Random random)
	{
		return 1;
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune)
	{
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();

		int count = quantityDropped(metadata, fortune, world.rand);
		for (int i = 0; i < count; i++)
		{
			ItemStack itemstack = new ItemStack(TFCItems.oreChunk, 1, damageDropped(metadata));
			ret.add(itemstack);
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
				ItemStack itemstack = new ItemStack(TFCItems.oreChunk, 1, damageDropped(meta));
				dropBlockAsItem(world, x, y, z, itemstack);
			}
		}
		return world.setBlockToAir(x, y, z);
	}

	/*public boolean removeBlockByPlayer(World world, EntityPlayer player, int i, int j, int k) 
	{
		if(player != null)
		{
			player.addStat(StatList.mineBlockStatArray[blockID], 1);
			player.addExhaustion(0.075F);
		}

		MovingObjectPosition objectMouseOver = Helper.getMouseOverObject(player, world);
		if(objectMouseOver == null) {
			return false;
		}
		int side = objectMouseOver.sideHit;
		int sub = objectMouseOver.subHit;


		if(true)
		{

			ItemChisel.CreateSlab(world, i, j, k, this.blockID, (byte) world.getBlockMetadata(i, j, k), side, mod_TFC_Core.stoneMinedSlabs.blockID);
			TileEntityPartial te = (TileEntityPartial) world.getTileEntity(i,j,k);
			int id = te.TypeID;
			int meta = te.MetaID;
			ItemChisel.CreateSlab(world, i, j, k, te.TypeID, te.MetaID, side, mod_TFC_Core.stoneMinedSlabs.blockID);
			te = (TileEntityPartial) world.getTileEntity(i, j, k);
			Block.blocksList[id].harvestBlock(world, player, i, j, k, meta);
			if(te != null)
			{
				long extraX = (te.extraData) & 0xf;
				long extraY = (te.extraData >> 4) & 0xf;
				long extraZ = (te.extraData >> 8) & 0xf;
				long extraX2 = (te.extraData >> 12) & 0xf;
				long extraY2 = (te.extraData >> 16) & 0xf;
				long extraZ2 = (te.extraData >> 20) & 0xf;

				if(extraX+extraY+extraZ+extraX2+extraY2+extraZ2 > 8)
					return world.setBlock(i, j, k, 0);
			}
			else
				return world.setBlock(i, j, k, 0);
		}

		return false;
	}*/

	@Override
	public void onBlockExploded(World world, int x, int y, int z, Explosion exp)
	{
		ItemStack itemstack;
		int meta = world.getBlockMetadata(x, y, z);
		itemstack  = new ItemStack(TFCItems.oreChunk, 1, meta + 32);
		dropBlockAsItem(world, x, y, z, itemstack);
		onBlockDestroyedByExplosion(world, x, y, z, exp);
	}
}
