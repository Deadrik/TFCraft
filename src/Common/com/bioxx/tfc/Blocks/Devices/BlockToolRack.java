package com.bioxx.tfc.Blocks.Devices;

import java.util.ArrayList;
import java.util.List;

import com.bioxx.tfc.TFCBlocks;
import com.bioxx.tfc.Blocks.BlockTerraContainer;
import com.bioxx.tfc.Items.Tools.ItemProPick;
import com.bioxx.tfc.Items.Tools.ItemSpindle;
import com.bioxx.tfc.Items.Tools.ItemWeapon;
import com.bioxx.tfc.TileEntities.TileEntityToolRack;
import com.bioxx.tfc.api.IMultipleBlock;
import com.bioxx.tfc.api.Constant.Global;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockToolRack extends BlockTerraContainer implements IMultipleBlock
{
	String[] woodNames;
	public BlockToolRack()
	{
		super(Material.wood);
		this.setCreativeTab(CreativeTabs.tabDecorations);
		woodNames = new String[16];
		System.arraycopy(Global.WOOD_ALL, 0, woodNames, 0,16);
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public boolean getBlocksMovement(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		return true;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	{
		return null;
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	public IIcon getIcon(int woodType)
	{
		return getBlockTypeForRender().getIcon(0, woodType);
	}

	@Override
	public int getRenderType()
	{
		return TFCBlocks.toolRackRenderId;
	}

	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float hitX, float hitY, float hitZ)
	{
		if(!world.isRemote)
		{
			TileEntity te = world.getTileEntity(i, j, k);
			if(te != null && te instanceof TileEntityToolRack)
			{
				TileEntityToolRack tet = (TileEntityToolRack) te;
				int dir = world.getBlockMetadata(i, j, k);
				if(dir == 0)
				{
					if(hitX < 0.5 && hitY > 0.5)
						handleArea(world, i, j, k, entityplayer, tet, 0, 0);
					else if(hitX > 0.5 && hitY > 0.5)
						handleArea(world, i, j, k, entityplayer, tet, 1, 0);
					else if(hitX < 0.5)
						handleArea(world, i, j, k, entityplayer, tet, 2, 0);
					else if(hitX > 0.5)
						handleArea(world, i, j, k, entityplayer, tet, 3, 0);
				}
				else if(dir == 1)
				{
					if(hitZ < 0.5 && hitY > 0.5)
						handleArea(world, i, j, k, entityplayer, tet, 0, 1);
					else if(hitZ > 0.5 && hitY > 0.5)
						handleArea(world, i, j, k, entityplayer, tet, 1, 1);
					else if(hitZ < 0.5)
						handleArea(world, i, j, k, entityplayer, tet, 2, 1);
					else if(hitZ > 0.5)
						handleArea(world, i, j, k, entityplayer, tet, 3, 1);
				}
				else if(dir == 2)
				{
					if(hitX < 0.5 && hitY > 0.5)
						handleArea(world, i, j, k, entityplayer, tet, 0, 2);
					else if(hitX > 0.5 && hitY > 0.5)
						handleArea(world, i, j, k, entityplayer, tet, 1, 2);
					else if(hitX < 0.5)
						handleArea(world, i, j, k, entityplayer, tet, 2, 2);
					else if(hitX > 0.5)
						handleArea(world, i, j, k, entityplayer, tet, 3, 2);
				}
				else if(dir == 3)
				{
					if(hitZ < 0.5 && hitY > 0.5)
						handleArea(world, i, j, k, entityplayer, tet, 0, 3);
					else if(hitZ > 0.5 && hitY > 0.5)
						handleArea(world, i, j, k, entityplayer, tet, 1, 3);
					else if(hitZ < 0.5)
						handleArea(world, i, j, k, entityplayer, tet, 2, 3);
					else if(hitZ > 0.5)
						handleArea(world, i, j, k, entityplayer, tet, 3, 3);
				}
				world.markBlockForUpdate(i, j, k);
				//tet.broadcastPacketInRange(tet.createUpdatePacket());
				return true;
			}
		}
		return false;
	}

	private void handleArea(World world, int i, int j, int k,EntityPlayer entityplayer, TileEntityToolRack te, int slot, int dir) 
	{
		boolean hasToolInHand = entityplayer.getCurrentEquippedItem() != null && (
				entityplayer.getCurrentEquippedItem().getItem() instanceof ItemTool || entityplayer.getCurrentEquippedItem().getItem() instanceof ItemWeapon || 
				entityplayer.getCurrentEquippedItem().getItem() instanceof ItemHoe || entityplayer.getCurrentEquippedItem().getItem() instanceof ItemProPick || 
				entityplayer.getCurrentEquippedItem().getItem() instanceof ItemBow || entityplayer.getCurrentEquippedItem().getItem() instanceof ItemSword || 
				entityplayer.getCurrentEquippedItem().getItem() instanceof ItemAxe || entityplayer.getCurrentEquippedItem().getItem() instanceof ItemSpade || 
				entityplayer.getCurrentEquippedItem().getItem() instanceof ItemShears || entityplayer.getCurrentEquippedItem().getItem() instanceof ItemSpindle);
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
			if((te != null) && (te instanceof TileEntityToolRack))
			{
				TileEntityToolRack rack = (TileEntityToolRack) te;
				dropBlockAsItem(world, x, y, z, new ItemStack(TFCBlocks.ToolRack, 1, rack.woodType));
			}
		}
		return super.removedByPlayer(world, player, x, y, z);
	}

	@Override
	public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int meta)
	{
		// don't drop here, we dropped in removedByPlayer instead
	}

	// this method should end up being used when the block is dropped by an explosion
	// note that the block can still sometimes be destroyed and this won't be called
	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune)
	{
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		TileEntity te = world.getTileEntity(x, y, z);
		if((te != null) && (te instanceof TileEntityToolRack))
		{
			TileEntityToolRack rack = (TileEntityToolRack) te;
			ret.add(new ItemStack(this, 1, rack.woodType));
		}
		return ret;
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityToolRack();
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess access, int i, int j, int k) 
	{
		int dir = access.getBlockMetadata(i, j, k);
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
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int i, int j, int k)
	{
		int dir = world.getBlockMetadata(i, j, k);
		if(dir == 0)
			return AxisAlignedBB.getBoundingBox(i+0.0F, j+0F, k+0.85F, i+1F, j+1F, k+1F);
		else if(dir == 1)
			return AxisAlignedBB.getBoundingBox(i+0.0F, j+0F, k+0.0F, i+0.15F, j+1F, k+1F);
		else if(dir == 2)
			return AxisAlignedBB.getBoundingBox(i+0.0F, j+0F, k+0.00F, i+1F, j+1F, k+0.15F);
		else if(dir == 3)
			return AxisAlignedBB.getBoundingBox(i+0.85F, j+0F, k+0.0F, i+1F, j+1F, k+1F);

		return AxisAlignedBB.getBoundingBox(i, j, k, i+1, j+1, k+1);
	}

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, Block block) 
	{
		int dir = world.getBlockMetadata(i, j, k);

		if(dir == 0)
			if(!world.getBlock(i, j, k+1).isOpaqueCube())
				removedByPlayer(world, null, i, j, k);
		else if(dir == 1)
			if(!world.getBlock(i-1, j, k).isOpaqueCube())
				removedByPlayer(world, null, i, j, k);
		else if(dir == 2)
			if(!world.getBlock(i, j, k-1).isOpaqueCube())
				removedByPlayer(world, null, i, j, k);
		else if(dir == 3)
			if(!world.getBlock(i+1, j, k).isOpaqueCube())
				removedByPlayer(world, null, i, j, k);
	}

	@Override
	public int onBlockPlaced(World world, int i, int j, int k, int side, float hitX, float hitY, float hitZ, int meta)
	{
		if(side == 4) return 3;
		if(side == 5) return 1;
		if(side == 2) return 0;
		if(side == 3) return 2;

		return 5;
	}

	@Override
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLivingBase entity, ItemStack is)
	{
		if(!world.isRemote)
		{
			TileEntity te = world.getTileEntity(i, j, k);
			if(te != null && te instanceof TileEntityToolRack)
			{
				TileEntityToolRack rack = (TileEntityToolRack) te;
				rack.woodType = (byte)is.getItemDamage();
				world.markBlockForUpdate(i, j, k);
				//rack.broadcastPacketInRange(rack.createUpdatePacket());
			}
		}
	}

	@Override
	public boolean canPlaceBlockOnSide(World world, int i, int j, int k, int side)
	{
		if(this.canPlaceBlockAt(world, i, j, k))
		{
			if(side == 5 && world.getBlock(i-1, j, k).isNormalCube())
				return true;
			if(side == 4 && world.getBlock(i+1, j, k).isNormalCube())
				return true;
			if(side == 2 && world.getBlock(i, j, k+1).isNormalCube())
				return true;
			if(side == 3 && world.getBlock(i, j, k-1).isNormalCube())
				return true;
		}
		return false;
	}

	@SideOnly(Side.CLIENT)
	@Override
	/**
	 * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
	 */
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		for(int i = 0; i < woodNames.length; i++)
			par3List.add(new ItemStack(par1, 1, i));
	}

	@Override
	public IIcon getIcon(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
	{
		return getBlockTypeForRender().getIcon(par1IBlockAccess, par2, par3, par4, par5);
	}

	@Override
	public IIcon getIcon(int par1, int par2)
	{
		return getBlockTypeForRender().getIcon(par1, par2);
	}

	@Override
	public void registerBlockIcons(IIconRegister iconRegisterer)
	{
		//Empty On Purpose
	}
	
	@Override
	public Block getBlockTypeForRender()
	{
		return TFCBlocks.WoodSupportH;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess bAccess, int x, int y, int z, int side)
	{
		return true;
	}

}
