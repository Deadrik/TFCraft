package com.bioxx.tfc.Blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.TFCBlocks;
import com.bioxx.tfc.TFCItems;
import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Blocks.Terrain.BlockCollapsable;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Food.ItemFoodMeat;
import com.bioxx.tfc.TileEntities.TESmokeRack;
import com.bioxx.tfc.api.Food;
import com.bioxx.tfc.api.TFCOptions;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockSmokeRack extends BlockTerraContainer
{
	public BlockSmokeRack()
	{
		super(Material.circuits);
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) 
	{
		if((world.getBlockMetadata(x, y, z) & 1) == 0)
		{
			this.setBlockBounds(0.45f, 0.45f, 0, 0.55f, 0.55f, 1);
		}
		else
		{
			this.setBlockBounds(0f, 0.45f, 0.45f, 1, 0.55f, 0.55f);
		}
	}

	@Override
	public boolean isReplaceable(IBlockAccess world, int x, int y, int z)
	{
		return true;
	}

	@Override
	public boolean getBlocksMovement(IBlockAccess bAccess, int x, int y, int z)
	{
		return true;
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z)
	{
		return new ItemStack(getCreatedWithItem());
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityplayer, int side, float hitX, float hitY, float hitZ)
	{
		boolean flag = false;
		if(!world.isRemote)
		{
			int meta = world.getBlockMetadata(x, y, z);
			TESmokeRack te = (TESmokeRack) world.getTileEntity(x, y, z);
			ItemStack yarn = TFC_Core.getItemInInventory(getCreatedWithItem(), entityplayer.inventory);
			if((meta & 1) == 0 && hitZ < 0.5)
			{
				if(te.getStackInSlot(0) == null && yarn != null && isItemValid(entityplayer.inventory.getCurrentItem()))
				{
					te.setInventorySlotContents(0, entityplayer.inventory.getCurrentItem().copy());
					entityplayer.inventory.getCurrentItem().stackSize--;
					entityplayer.inventory.consumeInventoryItem(getCreatedWithItem());
					flag = true;
				}
				else if(te.getStackInSlot(0) != null && entityplayer.isSneaking())
				{
					TFC_Core.giveItemToPlayer(te.removeStackInSlot(0), entityplayer);
					flag = true;
				}
			}
			else if((meta & 1) == 0 && hitZ >= 0.5)
			{ 
				if(te.getStackInSlot(1) == null && yarn != null && isItemValid(entityplayer.inventory.getCurrentItem()))
				{
					te.setInventorySlotContents(1, entityplayer.inventory.getCurrentItem().copy());
					entityplayer.inventory.getCurrentItem().stackSize--;
					entityplayer.inventory.consumeInventoryItem(getCreatedWithItem());
					flag = true;
				}
				else if(te.getStackInSlot(1) != null && entityplayer.isSneaking())
				{
					TFC_Core.giveItemToPlayer(te.removeStackInSlot(1), entityplayer);
					flag = true;
				}
			}
			else if((meta & 1) == 1 && hitX < 0.5)
			{
				if(te.getStackInSlot(0) == null && yarn != null && isItemValid(entityplayer.inventory.getCurrentItem()))
				{
					te.setInventorySlotContents(0, entityplayer.inventory.getCurrentItem().copy());
					entityplayer.inventory.getCurrentItem().stackSize--;
					entityplayer.inventory.consumeInventoryItem(getCreatedWithItem());
					flag = true;
				}
				else if(te.getStackInSlot(0) != null && entityplayer.isSneaking())
				{
					TFC_Core.giveItemToPlayer(te.removeStackInSlot(0), entityplayer);
					flag = true;
				}
			}
			else if((meta & 1) == 1 && hitX >= 0.5)
			{ 
				if(te.getStackInSlot(1) == null && yarn != null && isItemValid(entityplayer.inventory.getCurrentItem()))
				{
					te.setInventorySlotContents(1, entityplayer.inventory.getCurrentItem().copy());
					entityplayer.inventory.getCurrentItem().stackSize--;
					entityplayer.inventory.consumeInventoryItem(getCreatedWithItem());
					return true;
				}
				else if(te.getStackInSlot(1) != null && entityplayer.isSneaking())
				{
					TFC_Core.giveItemToPlayer(te.removeStackInSlot(1), entityplayer);
					flag = true;
				}
			}
			
			if (flag == false)
			{
				entityplayer.openGui(TerraFirmaCraft.instance, 99, world, x, y, z);
				flag = true;
			}
		}
		return flag;
	}

	private boolean isItemValid(ItemStack is)
	{
		if(is == null)
			return false;
		if(is.getItem() instanceof ItemFoodMeat)
		{
			if(!Food.isCooked(is) && Food.isBrined(is))
				return true;
		}

		return false;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public int getRenderType()
	{
		return TFCBlocks.smokeRackRenderId;
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
	{
		int meta = world.getBlockMetadata(x, y, z);
		if(!world.isRemote)
		{
			if((meta & 1) == 0)
			{
				if(!isValidNeighbor(world, x, y, z-1, ForgeDirection.NORTH) || !isValidNeighbor(world, x, y, z+1, ForgeDirection.SOUTH))
				{
					TFC_Core.DestroyBlock(world, x, y, z);
				}
			}
			else
			{
				if(!isValidNeighbor(world, x-1, y, z, ForgeDirection.WEST) || !isValidNeighbor(world, x+1, y, z, ForgeDirection.EAST))
				{
					TFC_Core.DestroyBlock(world, x, y, z);
				}
			}

			if(world.getBlock(x, y+1, z) instanceof BlockCollapsable)
			{
				TFC_Core.DestroyBlock(world, x, y, z);
			}
		}
	}

	private boolean isValidNeighbor(World world, int x, int y, int z, ForgeDirection dir)
	{
		Block b = world.getBlock(x, y, z);
		if((b == this || b.isSideSolid(world, x, y, z, dir.getOpposite())))
			return true;
		return false;
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k)
	{
		return null;
	}

	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z)
	{
		if((world.getBlockMetadata(x, y, z) & 1) == 0)
		{
			return AxisAlignedBB.getBoundingBox(x+0.45, y+0.45, z, x + 0.55, y + 0.55, z + 1);
		}
		else
		{
			return AxisAlignedBB.getBoundingBox(x, y+0.45, z+0.45, x + 1, y + 0.55, z +0.55);
		}
	}

	@Override
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
	{
		return getCreatedWithItem();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg)
	{
		this.blockIcon = reg.registerIcon(Reference.ModID + ":String"); // This gets registered in BlockGrass
	}

	@Override
	public TileEntity createTileEntity(World world, int meta)
	{
		return new TESmokeRack();
	}

	public static Item getCreatedWithItem()
	{
		if(TFCOptions.useJuteFibreForSmokeRack)
			return TFCItems.JuteFibre;
		else
			return TFCItems.WoolYarn;
	}
}
