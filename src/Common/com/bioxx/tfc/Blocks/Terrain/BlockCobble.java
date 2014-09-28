package com.bioxx.tfc.Blocks.Terrain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Blocks.BlockTerra;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Core.TFC_Sounds;
import com.bioxx.tfc.Items.Tools.ItemHammer;
import com.bioxx.tfc.api.Tools.IToolChisel;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockCobble extends BlockTerra
{
	protected BlockCobble(Material material)
	{
		super(material);
		this.setCreativeTab(TFCTabs.TFCBuilding);
	}

	protected String[] names;
	protected IIcon[] icons;

	@SideOnly(Side.CLIENT)
	@Override
	/**
	 * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
	 */
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List list)
	{
		for(int i = 0; i < names.length; i++)
			list.add(new ItemStack(this,1,i));
	}

	@Override
	protected void dropBlockAsItem(World par1World, int par2, int par3, int par4, ItemStack is)
	{
		if(is.getItemDamage() < 8)
			super.dropBlockAsItem(par1World, par2, par3, par4, is);
	}

	/*
	 * Mapping from metadata value to damage value
	 */
	@Override
	public int damageDropped(int i)
	{
		return i & 7;
	}

	@Override
	public IIcon getIcon(int i, int j)
	{
		return icons[j & 7];
	}

	@Override
	public void registerBlockIcons(IIconRegister iconRegisterer)
	{
		for(int i = 0; i < names.length; i++)
			icons[i] = iconRegisterer.registerIcon(Reference.ModID + ":" + "rocks/"+names[i]+" Cobble");
	}

	@Override
	public void onBlockAdded(World world, int i, int j, int k)
	{
		world.scheduleBlockUpdate(i, j, k, this, tickRate(world));
	}

	@Override
	public void onBlockDestroyedByPlayer(World world, int i, int j, int k, int l)
	{
	}

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, Block l)
	{
		world.scheduleBlockUpdate(i, j, k, this, tickRate(world));
	}

	public boolean canFallBelow(World world, int i, int j, int k)
	{
		Block l = world.getBlock(i, j, k);
		if (world.isAirBlock(i, j, k))
			return true;
		if (l == Blocks.fire)
			return true;
		Material material = l.getMaterial();
		if (material == Material.water)
			return true;
		return material == Material.lava;
	}

	/**
	 * Called when the block is clicked by a player. Args: x, y, z, entityPlayer
	 */
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityplayer, int side, float par7, float par8, float par9) 
	{
		boolean hasHammer = false;
		for(int i = 0; i < 9;i++)
		{
			if(entityplayer.inventory.mainInventory[i] != null && entityplayer.inventory.mainInventory[i].getItem() instanceof ItemHammer)
				hasHammer = true;
		}
		if(entityplayer.getCurrentEquippedItem() != null && entityplayer.getCurrentEquippedItem().getItem() instanceof IToolChisel && 
				hasHammer && !world.isRemote && ((IToolChisel)entityplayer.getCurrentEquippedItem().getItem()).canChisel(entityplayer, x, y, z))
		{
			Block id = world.getBlock(x, y, z);
			byte meta = (byte) world.getBlockMetadata(x, y, z);

			return ((IToolChisel)entityplayer.getCurrentEquippedItem().getItem()).onUsed(world, entityplayer, x, y, z, id, meta, side, par7, par8, par9);
		}
		return false;
	}

	private void tryToFall(World world, int x, int y, int z)
	{
		int meta = world.getBlockMetadata(x, y, z);
		if (!BlockCollapsable.isNearSupport(world, x, y, z, 4, 0) && BlockCollapsable.canFallBelow(world, x, y - 1, z) && y >= 0)
		{
			byte byte0 = 32;
			if (!BlockCollapsable.fallInstantly && world.checkChunksExist(x - byte0, y - byte0, z - byte0, x + byte0, y + byte0, z + byte0))
			{
				if (!world.isRemote)
				{
					//world.setBlockToAir(x, y, z);
					EntityFallingBlock entityfallingblock = new EntityFallingBlock(world, (double)((float)x + 0.5F), (double)((float)y + 0.5F), (double)((float)z + 0.5F), this, meta);
					//this.func_149829_a(entityfallingblock);
					world.spawnEntityInWorld(entityfallingblock);
					world.playSoundAtEntity(entityfallingblock, TFC_Sounds.FALLININGROCKSHORT, 1.0F, 0.8F + (world.rand.nextFloat()/2));
				}
			}
			else
			{
				world.setBlockToAir(x, y, z);

				while (BlockCollapsable.canFallBelow(world, x, y - 1, z) && y > 0)
				{
					--y;
				}

				if (y > 0)
				{
					world.setBlock(x, y, z, this, meta, 0x2);
				}
			}
		}
	}

	@Override
	public void updateTick(World world, int i, int j, int k, Random random)
	{
		if(!world.isRemote)
		{
			int meta = world.getBlockMetadata(i, j, k);

			boolean isBelowAir = world.isAirBlock(i, j-1, k);
			byte count = 0;
			List sides = new ArrayList<Integer>();

			if(world.isAirBlock(i+1, j, k))
			{
				count++;
				if(world.isAirBlock(i+1, j-1, k))
					sides.add(0);
			}
			if(world.isAirBlock(i, j, k+1))
			{
				count++;
				if(world.isAirBlock(i, j-1, k+1))
					sides.add(1);
			}
			if(world.isAirBlock(i-1, j, k))
			{
				count++;
				if(world.isAirBlock(i-1, j-1, k))
					sides.add(2);
			}
			if(world.isAirBlock(i, j, k-1))
			{
				count++;
				if(world.isAirBlock(i, j-1, k-1))
					sides.add(3);
			}

			if(!isBelowAir && (count > 2) && sides.size() >= 1)
			{
				switch((Integer)sides.get(random.nextInt(sides.size())))
				{
				case 0:
				{
					world.setBlockToAir(i, j, k);
					world.setBlock(i+1, j, k, this, meta, 0x2);
					tryToFall(world, i+1, j, k);
					break;
				}
				case 1:
				{
					world.setBlockToAir(i, j, k);
					world.setBlock(i, j, k+1, this, meta, 0x2);
					tryToFall(world, i, j, k+1);
					break;
				}
				case 2:
				{
					world.setBlockToAir(i, j, k);
					world.setBlock(i-1, j, k, this, meta, 0x2);
					tryToFall(world, i-1, j, k);
					break;
				}
				case 3:
				{
					world.setBlockToAir(i, j, k);
					world.setBlock(i, j, k-1, this, meta, 0x2);
					tryToFall(world, i, j, k-1);
					break;
				}
				}
			}
			else if(isBelowAir)
			{
				tryToFall(world, i, j, k);
			}
		}
	}
}
