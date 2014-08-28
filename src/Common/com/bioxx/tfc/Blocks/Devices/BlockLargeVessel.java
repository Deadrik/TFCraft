package com.bioxx.tfc.Blocks.Devices;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidContainerRegistry;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.TFCBlocks;
import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.TileEntities.TEBarrel;
import com.bioxx.tfc.TileEntities.TEVessel;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockLargeVessel extends BlockBarrel
{
	IIcon clayIcon;
	public BlockLargeVessel()
	{
		super();
		this.setCreativeTab(TFCTabs.TFCDevices);
		this.setBlockBounds(0.1f, 0, 0.1f, 0.9f, 1, 0.9f);

	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List) 
	{
		par3List.add(new ItemStack(this, 1, 0));
		par3List.add(new ItemStack(this, 1, 1));
	}

	@Override
	public void registerBlockIcons(IIconRegister iconRegisterer)
	{
		this.blockIcon = iconRegisterer.registerIcon(Reference.ModID + ":" + "clay/Ceramic");
		clayIcon = iconRegisterer.registerIcon(Reference.ModID + ":" + "clay/Clay");
	}

	@Override
	public IIcon getIcon(int side, int meta)
	{
		if(meta == 1)
			return blockIcon;
		return clayIcon;
	}

	@Override
	public IIcon getIcon(IBlockAccess access, int x, int y, int z, int side)
	{
		if(access.getBlockMetadata(x, y, z) == 1)
			return blockIcon;
		return clayIcon;
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
		return TFCBlocks.vesselRenderId;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		if (world.isRemote)
		{
			world.markBlockForUpdate(x, y, z);
			return true;
		}
		else
		{
			if (player.isSneaking())
			{
				return false;
			}

			if(world.getTileEntity(x, y, z) != null)
			{
				TEBarrel te = (TEBarrel)(world.getTileEntity(x, y, z));

				if (!te.getSealed()) {
					ItemStack equippedItem = player.getCurrentEquippedItem();
					if(FluidContainerRegistry.isFilledContainer(equippedItem) && !te.getSealed())
					{
						ItemStack is = te.addLiquid(player.getCurrentEquippedItem());
						player.inventory.setInventorySlotContents(player.inventory.currentItem, is);
						return true;
					}
					else if(FluidContainerRegistry.isEmptyContainer(equippedItem))
					{
						ItemStack is = te.removeLiquid(player.getCurrentEquippedItem());
						player.inventory.setInventorySlotContents(player.inventory.currentItem, is);
						return true;
					}
				}

				if(te.getInvCount() == 0)
					player.openGui(TerraFirmaCraft.instance, 46, world, x, y, z);
				else
					player.openGui(TerraFirmaCraft.instance, 47, world, x, y, z);
				return true;
			}
		}
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2)
	{
		return new TEVessel();
	}
}
