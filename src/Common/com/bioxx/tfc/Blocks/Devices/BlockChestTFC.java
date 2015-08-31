package com.bioxx.tfc.Blocks.Devices;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryLargeChest;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import net.minecraftforge.common.util.ForgeDirection;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Blocks.BlockTerraContainer;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.TileEntities.TEChest;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.Constant.Global;

import static net.minecraftforge.common.util.ForgeDirection.DOWN;

public class BlockChestTFC extends BlockTerraContainer
{
	private String[] woodNames;
	
	public BlockChestTFC()
	{
		super(Material.wood);
		this.setCreativeTab(TFCTabs.TFC_DECORATION);
		this.setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
		woodNames = Global.WOOD_ALL;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int var2)
	{
		return new TEChest();
	}

	@Override
	public int getDamageValue(World world, int x, int y, int z)
	{
		TileEntity te = world.getTileEntity(x, y, z);
		if (te instanceof TEChest)
			return ((TEChest)te).type;
		return 0;
	}

	/*@Override
	public void onBlockHarvested(World world, int x, int y, int z, int metadata, EntityPlayer player)
	{
		Item item = getItemDropped(metadata, world.rand, 0);
		dropBlockAsItem(world, x, y, z, new ItemStack(item, 1, getDamageValue(world, x, y, z)));

	}*/

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune)
	{
		return new ArrayList<ItemStack>();
	}

	@Override
	public Item getItemDropped(int metadata, Random rand, int fortune)
	{
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List) 
	{
		for(int i = 0; i < woodNames.length; i++)
			par3List.add(new ItemStack(this, 1, i));
	}
	
	@Override
	public void onBlockPreDestroy(World world, int i, int j, int k, int meta) 
	{
		if(!world.isRemote && world.getGameRules().getGameRuleBooleanValue("doTileDrops"))
		{
			int damageValue = getDamageValue(world, i, j, k);
			EntityItem ei = new EntityItem(world, i, j, k, new ItemStack(TFCBlocks.chest, 1, damageValue));
			world.spawnEntityInWorld(ei);
		}
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9)
	{
		if (world.isRemote)
		{
			return true;
		}
		else
		{
			IInventory iinventory = this.getInventory(world, x, y, z);

			if (iinventory != null)
				player.openGui(TerraFirmaCraft.instance, 29, world, x, y, z);

			return true;
		}
	}

	/**
	 * Called when the block is placed in the world.
	 */
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemStack)
	{
		byte chestSide = 0;
		int facingDir    = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5) & 3;
		int secFacingDir = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F ) & 3;
		//TerraFirmaCraft.log.info( facingDir );
		final int facingN  = 0;
		final int facingE  = 1;
		final int facingS  = 2;
		final int facingW  = 3;

		final byte sideN = 2;
		final byte sideS = 3;
		final byte sideE = 5;
		final byte sideW = 4;

		if (facingDir == facingN ) chestSide = sideN;
		if (facingDir == facingE ) chestSide = sideE;
		if (facingDir == facingS ) chestSide = sideS;
		if (facingDir == facingW ) chestSide = sideW;

		ForgeDirection adjDirection = getAdjacentChestDirection(world, x, y, z, itemStack.getItemDamage());
		if( adjDirection == ForgeDirection.UNKNOWN )
		{
			world.setBlockMetadataWithNotify(x, y, z, chestSide, 3);
		}
		else
		{
			switch( adjDirection )
			{
			case NORTH:
			case SOUTH:
				if( chestSide == sideN || chestSide == sideS )
				{
					if( secFacingDir == facingE || secFacingDir == facingN ) chestSide = sideE;
					if( secFacingDir == facingW || secFacingDir == facingS ) chestSide = sideW;
					//TerraFirmaCraft.log.info( secFacingDir );
				}
				break;
			default:
			case EAST:
			case WEST:
				if( chestSide == sideE || chestSide == sideW )
				{
					chestSide = sideN;
					if( secFacingDir == facingN || secFacingDir == facingW ) chestSide = sideN;
					if( secFacingDir == facingS || secFacingDir == facingE ) chestSide = sideS;
					//TerraFirmaCraft.log.info( secFacingDir );
				}
				break;
			}

			world.setBlockMetadataWithNotify(x, y, z, chestSide, 3);
			world.setBlockMetadataWithNotify(x + adjDirection.offsetX, y, z + adjDirection.offsetZ, chestSide, 3);
		}

		if (itemStack.hasDisplayName())
			((TEChest)world.getTileEntity(x, y, z)).func_145976_a(itemStack.getDisplayName());
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
	public int getRenderType()
	{
		return TFCBlocks.chestRenderId;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess access, int x, int y, int z)
	{

		TEChest chest = (TEChest)access.getTileEntity(x, y, z);
		if (chest != null)
		{
			ForgeDirection adjDirection = getAdjacentChestDirection(access, x, y, z, chest.type);
			switch(adjDirection)
			{
			case NORTH:
				this.setBlockBounds(0.0625F, 0.0F, 0.0F, 0.9375F, 0.875F, 0.9375F);
				break;
			case SOUTH:
				this.setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 1.0F);
				break;
			case EAST:
				this.setBlockBounds(0.0625F, 0.0F, 0.0625F, 1.0F, 0.875F, 0.9375F);
				break;
			case WEST:
				this.setBlockBounds(0.0F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
				break;
			default:
			case UNKNOWN:
				this.setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
				break;
			}
		}
	}

	@Override
	public void onBlockAdded(World world, int x, int y, int z)
	{
		super.onBlockAdded(world, x, y, z);
	}

	public void unifyAdjacentChests(World world, int x, int y, int z)
	{
		//Doesn't do anything?
	}
	private boolean isChestOfType( IBlockAccess world, int x, int y, int z, int type )
	{
		if (world.getBlock(x, y, z) == this)
		{
			TEChest chest = (TEChest) world.getTileEntity(x, y, z);
			return chest.type == type;
		}
		return false;
	}

	private ForgeDirection getAdjacentChestDirection( IBlockAccess world, int x, int y, int z, int type)
	{
		ForgeDirection[] dirs = { ForgeDirection.NORTH, ForgeDirection.SOUTH, ForgeDirection.EAST, ForgeDirection.WEST };
		for( ForgeDirection dir : dirs )
		{
			if( isChestOfType(world, x + dir.offsetX, y, z + dir.offsetZ, type) )
				return dir;
		}
		return ForgeDirection.UNKNOWN;
	}

	/**
	 * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
	 */
	@Override
	public boolean canReplace(World world, int x, int y, int z, int side, ItemStack itemStack )
	{
		//itemStack is ItemChest
		int type = itemStack.getItemDamage();

		ForgeDirection adjDirection = ForgeDirection.UNKNOWN;
		ForgeDirection[] dirs = { ForgeDirection.NORTH, ForgeDirection.SOUTH, ForgeDirection.EAST, ForgeDirection.WEST };
		for( ForgeDirection dir : dirs )
		{
			if( isChestOfType(world, x + dir.offsetX, y, z + dir.offsetZ, type) )
			{
				if( adjDirection != ForgeDirection.UNKNOWN)
					return false;

				adjDirection  = dir;
			}
		}
		if( adjDirection == ForgeDirection.UNKNOWN)
		{
			//Single chest!
			return true;

		}

		//Have neighboring chest, make sure it isn't a double chest!
		ForgeDirection doubleDirection = getAdjacentChestDirection(world, x + adjDirection.offsetX, y, z+adjDirection.offsetZ, type);
		//If IT has a neighbor, then it is a double chest
		return doubleDirection == ForgeDirection.UNKNOWN; 
	}

	public IInventory getInventory(World world, int x, int y, int z)
	{
		TEChest chest = (TEChest)world.getTileEntity(x, y, z);

		if (chest == null)
		{
			//Ooops?
			return null;
		}
		else if (world.isSideSolid(x, y + 1, z, DOWN))
		{
			//Check if top is blocked
			return null;
		}

		ForgeDirection adjDirection = getAdjacentChestDirection(world, x, y, z, chest.type);
		if( adjDirection == ForgeDirection.UNKNOWN )
		{
			//No surrounding chests
			return chest;
		}
		else
		{
			IInventory inv1, inv2;
			TEChest adjChest = (TEChest)world.getTileEntity(x + adjDirection.offsetX, y, z + adjDirection.offsetZ );
			switch(adjDirection)
			{
			case NORTH:
				inv1 = chest;
				inv2 = adjChest;
				break;
			case SOUTH:
				inv2 = chest;
				inv1 = adjChest;
				break;
			case EAST:
				inv2 = chest;
				inv1 = adjChest;
				break;
			default:
			case WEST:
				inv1 = chest;
				inv2 = adjChest;
				break;
			}
			return new InventoryLargeChest("container.chestDouble", inv1, inv2);

		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register)
	{
		this.blockIcon = register.registerIcon("planks_oak");
	}
}
