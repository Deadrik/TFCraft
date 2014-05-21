package com.bioxx.tfc.Blocks;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.TFCBlocks;
import com.bioxx.tfc.TFCItems;
import com.bioxx.tfc.Core.TFC_Achievements;
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
	public void harvestBlock(World world, EntityPlayer entityplayer, int x, int y, int z, int side)
	{
		//Eject(world, x, y, z);
	}

	@Override
	public void onBlockDestroyedByExplosion(World world, int x, int y, int z, Explosion exp)
	{
		Eject(world, x, y, z);
	}

	@Override
	public void onBlockDestroyedByPlayer(World world, int x, int y, int z, int meta)
	{
		Eject(world, x, y, z);
	}

	@Override
	public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z)
	{
		if(!world.isRemote)
		{
			Eject(world, x, y, z);
			player.triggerAchievement(TFC_Achievements.achIronAge);
		}
		return super.removedByPlayer(world, player, x, y, z);
	}

	public void Eject(World world, int x, int y, int z)
	{
		TileEntityBloom te = (TileEntityBloom)world.getTileEntity(x, y, z);
		if(te != null)
		{
			int[] b = getBloomery(world, x, y, z);
			te = (TileEntityBloom)world.getTileEntity(x, y, z);
			dropBlockAsItem(world, x + b[0], y, z + b[1], new ItemStack(TFCItems.RawBloom, 1, te.size));
			world.removeTileEntity(x, y, z);
		}
	}

	public int[] getBloomery(World world, int x, int y, int z)
	{
		if(world.getBlock(x + 1, y, z) == TFCBlocks.EarlyBloomery)
			return new int[]{1, 0};
		if(world.getBlock(x - 1, y, z) == TFCBlocks.EarlyBloomery)
			return new int[]{-1, 0};
		if(world.getBlock(x, y, z + 1) == TFCBlocks.EarlyBloomery)
			return new int[]{0, 1};
		if(world.getBlock(x, y, z - 1) == TFCBlocks.EarlyBloomery)
			return new int[]{0, -1};

		return new int[]{0, 0};
	}
}
