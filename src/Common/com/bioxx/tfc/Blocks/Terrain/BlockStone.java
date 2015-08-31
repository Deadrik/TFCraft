package com.bioxx.tfc.Blocks.Terrain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Items.Tools.ItemHammer;
import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.Tools.IToolChisel;

public class BlockStone extends BlockCollapsible
{
	public BlockStone(Material material)
	{
		super(material);
	}

	protected String[] names;
	public IIcon[] icons;
	protected int looseStart;
	protected int gemChance;

	@SideOnly(Side.CLIENT)
	@Override
	/**
	 * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
	 */
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		for(int i = 0; i < names.length; i++)
			par3List.add(new ItemStack(par1, 1, i));
	}

	/*
	 * Mapping from metadata value to damage value
	 */
	@Override
	public int damageDropped(int i)
	{
		return i;
	}

	@Override
	public IIcon getIcon(int i, int j)
	{
		if((j&7)>=icons.length)
			return icons[0];
		return icons[j & 7];
	}

	@Override
	public void registerBlockIcons(IIconRegister iconRegisterer)
	{
		for(int i = 0; i < names.length; i++)
			icons[i] = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "rocks/"+names[i]+" Raw");
	}

	@Override
	public Item getItemDropped(int i, Random random, int j)
	{
		return TFCItems.looseRock;
	}

	/**
	 * Returns the quantity of items to drop on block destruction.
	 */
	@Override
	public int quantityDropped(Random rand)
	{
		return rand.nextInt(2) + 1;
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune)
	{
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		int meta = looseStart + metadata;

		int count = this.quantityDropped(world.rand);
		for (int i = 0; i < count; i++)
		{
			Item item = getItemDropped(meta, world.rand, fortune);
			if (item != null)
			{
				ret.add(new ItemStack(item, 1, damageDropped(meta)));
			}
		}

		ItemStack gemStack = TFC_Core.randomGem(world.rand, gemChance);
		if (gemStack != null)
			ret.add(gemStack);

		return ret;
	}

	@Override
	public void onBlockDestroyedByExplosion(World world, int i, int j, int k, Explosion ex) 
	{
		if(!world.isRemote)
		{
			//super.onBlockDestroyedByExplosion(world, i, j, k, ex);
			Random random = new Random();

			ItemStack is = null;

			is = TFC_Core.randomGem(random, 0);

			if(is != null)
			{
				EntityItem item = new EntityItem(world, i, j, k, is);
				world.spawnEntityInWorld(item);
			}
		}
	}

	@Override
	public void onBlockExploded(World world, int x, int y, int z, Explosion explosion)
	{
		// 30% chance to turn to cobble instead of being completely destroyed.
		if (world.rand.nextInt(100) < 30)
		{
			// +8 Metadata for natural cobblestone that drops rocks instead of the block
			world.setBlock(x, y, z, dropBlock, world.getBlockMetadata(x, y, z) + 8, 0x2);
		}
		else
		{
			super.onBlockExploded(world, x, y, z, explosion);
		}
	}

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, Block l)
	{
		dropCarvedStone(world, i, j, k);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityplayer, int side, float par7, float par8, float par9) 
	{
		boolean hasHammer = false;
		for(int i = 0; i < 9; i++)
			if(entityplayer.inventory.mainInventory[i] != null && entityplayer.inventory.mainInventory[i].getItem() instanceof ItemHammer)
				hasHammer = true;

		if(entityplayer.getCurrentEquippedItem() != null && entityplayer.getCurrentEquippedItem().getItem() instanceof IToolChisel && 
				hasHammer && !world.isRemote && ((IToolChisel)entityplayer.getCurrentEquippedItem().getItem()).canChisel(entityplayer, x, y, z))
		{
			Block id = world.getBlock(x, y, z);
			byte meta = (byte) world.getBlockMetadata(x, y, z);
			return ((IToolChisel)entityplayer.getCurrentEquippedItem().getItem()).onUsed(world, entityplayer, x, y, z, id, meta, side, par7, par8, par9);
		}
		return false;
	}

	@Override
	public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int l)
	{
		dropBlockAsItem(world, i, j, k, new ItemStack(TFCItems.looseRock, world.rand.nextInt(2) + 1, l + looseStart));

		super.harvestBlock(world, entityplayer, i, j, k, l);
	}

	@Override
	public void onBlockDestroyedByPlayer(World world, int i, int j, int k, int l)
	{
		if(!world.isRemote)
		{
			Random random = new Random();
			ItemStack is = null;

			is = TFC_Core.randomGem(random, gemChance);

			if(is != null)
			{
				EntityItem item = new EntityItem(world, i, j, k, is);
				world.spawnEntityInWorld(item);
			}
		}
	}
}
