package com.bioxx.tfc.Blocks.Devices;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import net.minecraftforge.common.util.ForgeDirection;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import com.bioxx.tfc.Blocks.BlockTerraContainer;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Items.Tools.ItemProPick;
import com.bioxx.tfc.Items.Tools.ItemSpindle;
import com.bioxx.tfc.Items.Tools.ItemWeapon;
import com.bioxx.tfc.TileEntities.TEToolRack;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.Constant.Global;

public class BlockToolRack extends BlockTerraContainer
{
	protected String[] woodNames;

	public BlockToolRack()
	{
		super(Material.wood);
		this.setCreativeTab(TFCTabs.TFC_DECORATION);
		this.woodNames = Global.WOOD_ALL;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public boolean getBlocksMovement(IBlockAccess bAccess, int x, int y, int z)
	{
		return true;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
	{
		return null;
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	public int getRenderType()
	{
		return TFCBlocks.toolRackRenderId;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityplayer, int side, float hitX, float hitY, float hitZ)
	{
		if(!world.isRemote)
		{
			TileEntity te = world.getTileEntity(x, y, z);
			if (te instanceof TEToolRack)
			{
				TEToolRack tet = (TEToolRack) te;
				int dir = world.getBlockMetadata(x, y, z);
				if(dir == 0)
				{
					if(hitX < 0.5 && hitY > 0.5)
						handleArea(world, x, y, z, entityplayer, tet, 0, 0);
					else if(hitX > 0.5 && hitY > 0.5)
						handleArea(world, x, y, z, entityplayer, tet, 1, 0);
					else if(hitX < 0.5)
						handleArea(world, x, y, z, entityplayer, tet, 2, 0);
					else if(hitX > 0.5)
						handleArea(world, x, y, z, entityplayer, tet, 3, 0);
				}
				else if(dir == 1)
				{
					if(hitZ < 0.5 && hitY > 0.5)
						handleArea(world, x, y, z, entityplayer, tet, 0, 1);
					else if(hitZ > 0.5 && hitY > 0.5)
						handleArea(world, x, y, z, entityplayer, tet, 1, 1);
					else if(hitZ < 0.5)
						handleArea(world, x, y, z, entityplayer, tet, 2, 1);
					else if(hitZ > 0.5)
						handleArea(world, x, y, z, entityplayer, tet, 3, 1);
				}
				else if(dir == 2)
				{
					if(hitX < 0.5 && hitY > 0.5)
						handleArea(world, x, y, z, entityplayer, tet, 0, 2);
					else if(hitX > 0.5 && hitY > 0.5)
						handleArea(world, x, y, z, entityplayer, tet, 1, 2);
					else if(hitX < 0.5)
						handleArea(world, x, y, z, entityplayer, tet, 2, 2);
					else if(hitX > 0.5)
						handleArea(world, x, y, z, entityplayer, tet, 3, 2);
				}
				else if(dir == 3)
				{
					if(hitZ < 0.5 && hitY > 0.5)
						handleArea(world, x, y, z, entityplayer, tet, 0, 3);
					else if(hitZ > 0.5 && hitY > 0.5)
						handleArea(world, x, y, z, entityplayer, tet, 1, 3);
					else if(hitZ < 0.5)
						handleArea(world, x, y, z, entityplayer, tet, 2, 3);
					else if(hitZ > 0.5)
						handleArea(world, x, y, z, entityplayer, tet, 3, 3);
				}
				world.markBlockForUpdate(x, y, z);
				//tet.broadcastPacketInRange(tet.createUpdatePacket());
				return true;
			}
		}
		return false;
	}

	private void handleArea(World world, int x, int y, int z,EntityPlayer entityplayer, TEToolRack te, int slot, int dir)
	{
		boolean hasToolInHand = entityplayer.getCurrentEquippedItem() != null && (
				entityplayer.getCurrentEquippedItem().getItem() instanceof ItemTool ||
				entityplayer.getCurrentEquippedItem().getItem() instanceof ItemWeapon ||
				entityplayer.getCurrentEquippedItem().getItem() instanceof ItemHoe ||
				entityplayer.getCurrentEquippedItem().getItem() instanceof ItemProPick ||
				entityplayer.getCurrentEquippedItem().getItem() instanceof ItemBow ||
				entityplayer.getCurrentEquippedItem().getItem() instanceof ItemSword ||
				entityplayer.getCurrentEquippedItem().getItem() instanceof ItemAxe ||
				entityplayer.getCurrentEquippedItem().getItem() instanceof ItemSpade ||
				entityplayer.getCurrentEquippedItem().getItem() instanceof ItemShears ||
				entityplayer.getCurrentEquippedItem().getItem() instanceof ItemSpindle);
		if(te.storage[slot] == null && hasToolInHand)
		{
			te.storage[slot] = entityplayer.getCurrentEquippedItem().copy();
			entityplayer.inventory.mainInventory[entityplayer.inventory.currentItem] = null;
		}
		else if(te.storage[slot] != null)
		{
			te.ejectItem(slot, dir);
			te.storage[slot] = null;
		}
	}

	@Override
	public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z)
	{
		if(!world.isRemote)
		{
			// tile entity should still be valid at this point, so get the wood type and drop the rack
			TileEntity te = world.getTileEntity(x, y, z);
			if (te instanceof TEToolRack)
			{
				TEToolRack rack = (TEToolRack) te;
				dropBlockAsItem(world, x, y, z, new ItemStack(TFCBlocks.toolRack, 1, rack.woodType));
			}
		}
		return world.setBlockToAir(x, y, z); // super.removedByPlayer is deprecated, and causes a loop.
	}

	@Override
	public void harvestBlock(World world, EntityPlayer entityplayer, int x, int y, int z, int meta)
	{
		// don't drop here, we dropped in removedByPlayer instead
	}

	// this method should end up being used when the block is dropped by an explosion
	// note that the block can still sometimes be destroyed and this won't be called
	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune)
	{
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		
		int damageValue = getDamageValue(world, x, y, z);
		ret.add(new ItemStack(this, 1, damageValue));
		
		return ret;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TEToolRack();
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess access, int x, int y, int z)
	{
		int dir = access.getBlockMetadata(x, y, z);
		if(dir == 0)
			this.setBlockBounds(0.0F, 0F, 0.85F, 1F, 1F, 1F);
		else if(dir == 1)
			this.setBlockBounds(0.0F, 0F, 0.0F, 0.15F, 1F, 1F);
		else if(dir == 2)
			this.setBlockBounds(0.0F, 0F, 0.00F, 1F, 1F, 0.15F);
		else if(dir == 3)
			this.setBlockBounds(0.85F, 0F, 0.0F, 1F, 1F, 1F);
	}

	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z)
	{
		int dir = world.getBlockMetadata(x, y, z);
		if(dir == 0)
			return AxisAlignedBB.getBoundingBox(x + 0.0F, y + 0F, z + 0.85F, x + 1F, y + 1F, z + 1F);
		else if(dir == 1)
			return AxisAlignedBB.getBoundingBox(x + 0.0F, y + 0F, z + 0.0F, x + 0.15F, y + 1F, z + 1F);
		else if(dir == 2)
			return AxisAlignedBB.getBoundingBox(x + 0.0F, y + 0F, z + 0.00F, x + 1F, y + 1F, z + 0.15F);
		else if(dir == 3)
			return AxisAlignedBB.getBoundingBox(x + 0.85F, y + 0F, z + 0.0F, x + 1F, y + 1F, z + 1F);

		return AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y + 1, z + 1);
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
	{
		int dir = world.getBlockMetadata(x, y, z);

		if(dir == 0)
		{
			if (!world.getBlock(x, y, z + 1).isSideSolid(world, x, y, z + 1, ForgeDirection.NORTH))
				removedByPlayer(world, null, x, y, z);
		}
		else if(dir == 1)
		{
			if (!world.getBlock(x - 1, y, z).isSideSolid(world, x - 1, y, z, ForgeDirection.EAST))
				removedByPlayer(world, null, x, y, z);
		}
		else if(dir == 2)
		{
			if (!world.getBlock(x, y, z - 1).isSideSolid(world, x, y, z - 1, ForgeDirection.SOUTH))
				removedByPlayer(world, null, x, y, z);
		}
		else if(dir == 3)
		{
			if (!world.getBlock(x + 1, y, z).isSideSolid(world, x + 1, y, z, ForgeDirection.WEST))
				removedByPlayer(world, null, x, y, z);
		}
	}

	@Override
	public int onBlockPlaced(World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int meta)
	{
		if(side == 4) return 3;
		if(side == 5) return 1;
		if(side == 2) return 0;
		if(side == 3) return 2;

		return 5;
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack is)
	{
		TileEntity te = world.getTileEntity(x, y, z);
		if (te instanceof TEToolRack)
		{
			TEToolRack rack = (TEToolRack) te;
			rack.woodType = (byte)is.getItemDamage();
			world.markBlockForUpdate(x, y, z);
		}
	}

	@Override
	public boolean canPlaceBlockOnSide(World world, int x, int y, int z, int side)
	{
		if(this.canPlaceBlockAt(world, x, y, z))
		{
			if (side == 5 && world.getBlock(x - 1, y, z).isSideSolid(world, x - 1, y, z, ForgeDirection.EAST))
				return true;
			if (side == 4 && world.getBlock(x + 1, y, z).isSideSolid(world, x + 1, y, z, ForgeDirection.WEST))
				return true;
			if (side == 2 && world.getBlock(x, y, z + 1).isSideSolid(world, x, y, z + 1, ForgeDirection.NORTH))
				return true;
			if (side == 3 && world.getBlock(x, y, z - 1).isSideSolid(world, x, y, z - 1, ForgeDirection.SOUTH))
				return true;
		}
		return false;
	}

	@SideOnly(Side.CLIENT)
	@Override
	/**
	 * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
	 */
	public void getSubBlocks(Item item, CreativeTabs tabs, List list)
	{
		for(int i = 0; i < woodNames.length; i++)
			list.add(new ItemStack(item, 1, i));
	}

	@Override
	public IIcon getIcon(IBlockAccess bAccess, int x, int y, int z, int side)
	{
		TEToolRack te = (TEToolRack) bAccess.getTileEntity(x, y, z);

		if(te.woodType > 15)
			return TFCBlocks.woodSupportV2.getIcon(side, te.woodType);
		return TFCBlocks.woodSupportV.getIcon(side, te.woodType);
	}

	@Override
	public IIcon getIcon(int side, int meta)
	{
		if(meta > 15)
			return TFCBlocks.woodSupportV2.getIcon(side, meta);
		return TFCBlocks.woodSupportV.getIcon(side, meta);
	}

	@Override
	public int damageDropped(int dmg)
	{
		return dmg;
	}

	@Override
	public void registerBlockIcons(IIconRegister registerer)
	{
		//Empty On Purpose
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess bAccess, int x, int y, int z, int side)
	{
		return true;
	}
	
    /**
     * Get the block's damage value (for use with pick block).
     */
    @Override
	public int getDamageValue(World world, int x, int y, int z)
    {
		TileEntity te = world.getTileEntity(x, y, z);
		if (te instanceof TEToolRack)
			return ((TEToolRack)te).woodType;
		return 0;
    }
}
