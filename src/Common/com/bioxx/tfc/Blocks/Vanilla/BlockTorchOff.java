package com.bioxx.tfc.Blocks.Vanilla;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.TileEntities.TELightEmitter;
import com.bioxx.tfc.api.TFCBlocks;

public class BlockTorchOff extends BlockTorch
{

	public BlockTorchOff()
	{
		super();
		this.setCreativeTab(null);
		this.setTickRandomly(false);
		setLightLevel(0.0F);
	}

	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z)
	{
		return 0;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		return offIcon;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		if (!world.isRemote)
		{
			if (player.inventory.getCurrentItem() != null &&
				player.inventory.getCurrentItem().getItem() == Item.getItemFromBlock(TFCBlocks.torch))
			{
				int meta = world.getBlockMetadata(x, y, z);
				world.setBlock(x, y, z, TFCBlocks.torch, meta, 3);
				if (world.getTileEntity(x, y, z) instanceof TELightEmitter)
				{
					TELightEmitter te = (TELightEmitter) world.getTileEntity(x, y, z);
					te.hourPlaced = (int) TFC_Time.getTotalHours();
				}
			}
		}
		return true;
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune)
	{
		// Burned out torches drop nothing.
		return new ArrayList<ItemStack>();
	}

	/**
	 * A randomly called display update to be able to add particles or other items for display
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random rand)
	{
		// Burned out torches have no particles
	}

}
