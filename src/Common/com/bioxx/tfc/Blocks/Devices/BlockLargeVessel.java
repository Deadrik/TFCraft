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

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.TileEntities.TEVessel;
import com.bioxx.tfc.api.TFCBlocks;

public class BlockLargeVessel extends BlockBarrel
{
	private IIcon[] clayIcons;
	private IIcon[] ceramicIcons;
	public BlockLargeVessel()
	{
		super();
		this.setCreativeTab(TFCTabs.TFC_DEVICES);
		this.setBlockBounds(0.2f, 0, 0.2f, 0.8f, 0.7f, 0.8f);

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
		ceramicIcons = new IIcon[3];
		clayIcons = new IIcon[3];
		ceramicIcons[0] = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "clay/Ceramic Vessel Top");
		ceramicIcons[1] = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "clay/Ceramic Vessel Side");
		ceramicIcons[2] = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "clay/Ceramic Vessel Bottom");
		clayIcons[0] = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "clay/Clay Vessel Top");
		clayIcons[1] = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "clay/Clay Vessel Side");
		clayIcons[2] = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "clay/Clay Vessel Bottom");
	}

	@Override
	public IIcon getIcon(int side, int meta)
	{
		if(meta == 1)
		{
			if(side == 1)
				return ceramicIcons[0];
			else if(side == 0)
				return ceramicIcons[2];
			else
				return ceramicIcons[1];
		}
		if(side == 1)
			return clayIcons[0];
		else if(side == 0)
			return clayIcons[2];
		else
			return clayIcons[1];
	}

	@Override
	public IIcon getIcon(IBlockAccess access, int x, int y, int z, int side)
	{
		int meta= access.getBlockMetadata(x, y, z);
		if(meta == 1)
		{
			if(side == 1)
				return ceramicIcons[0];
			else if(side == 0)
				return ceramicIcons[2];
			else
				return ceramicIcons[1];
		}
		if(side == 1)
			return clayIcons[0];
		else if(side == 0)
			return clayIcons[2];
		else
			return clayIcons[1];

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
				TEVessel te = (TEVessel)(world.getTileEntity(x, y, z));

				if(!handleInteraction(player, te))
				{
					if(te.getInvCount() == 0)
						player.openGui(TerraFirmaCraft.instance, 46, world, x, y, z);
					else
						player.openGui(TerraFirmaCraft.instance, 47, world, x, y, z);
					return true;
				}
				else return true;
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
