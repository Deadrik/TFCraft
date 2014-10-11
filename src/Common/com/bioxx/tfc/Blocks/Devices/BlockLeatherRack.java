package com.bioxx.tfc.Blocks.Devices;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.TFCBlocks;
import com.bioxx.tfc.Blocks.BlockTerraContainer;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.TileEntities.TELeatherRack;
import com.bioxx.tfc.api.Tools.IKnife;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockLeatherRack extends BlockTerraContainer
{
	public IIcon scrapedTex;

	public BlockLeatherRack()
	{
		super(Material.wood);
		this.setCreativeTab(TFCTabs.TFCDevices);
		this.setBlockBounds(0, 0, 0, 1, 0.001f, 1);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityplayer, int side, float hitX, float hitY, float hitZ)
	{
		if(!world.isRemote)
		{
			TELeatherRack te = (TELeatherRack)world.getTileEntity(x, y, z);
			if(te.workedArea != -1 && entityplayer.getCurrentEquippedItem() != null && 
					entityplayer.getCurrentEquippedItem().getItem() instanceof IKnife)
			{
				int coord = (int)Math.floor(hitX/0.25f)+((int)Math.floor(hitZ/0.25f)*4);
				te.workArea(coord);
				NBTTagCompound nbt = new NBTTagCompound();
				nbt.setShort("workedArea", te.workedArea);
				te.broadcastPacketInRange(te.createDataPacket(nbt));
				entityplayer.getCurrentEquippedItem().damageItem(1, entityplayer);
			}
			else if(te.leatherItem != null)
			{
				EntityItem ei = new EntityItem(world, x, y, z, te.leatherItem);
				ei.motionX = 0; ei.motionZ = 0;
				world.spawnEntityInWorld(ei);
				world.setBlockToAir(x, y, z);
			}
		}
		return false;
	}

	@Override
	public void onBlockPreDestroy(World world, int i, int j, int k, int meta) 
	{
		if(!world.isRemote)
		{
			TELeatherRack te = (TELeatherRack)world.getTileEntity(i, j, k);
			EntityItem ei = new EntityItem(world, i, j, k, te.leatherItem);
			world.spawnEntityInWorld(ei);
		}
	}

	@Override
	public Item getItemDropped(int metadata, Random rand, int fortune)
	{
		return null;
	}

	@Override
	public boolean canBeReplacedByLeaves(IBlockAccess w, int x, int y, int z)
	{
		return false;
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
		return TFCBlocks.leatherRackRenderId;
	}

	@Override
	public void registerBlockIcons(IIconRegister iconRegisterer)
	{
		this.blockIcon = iconRegisterer.registerIcon(Reference.ModID + ":" + "Soaked Hide");
		scrapedTex = iconRegisterer.registerIcon(Reference.ModID + ":" + "Scraped Hide");
	}

	/*@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess access, int i, int j, int k, int meta)
	{
		return blockIcon;
	}*/

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess access, int x, int y, int z, int side)
	{
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int var2)
	{
		return new TELeatherRack();
	}
}
