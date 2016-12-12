package com.bioxx.tfc.Blocks.Devices;

import com.bioxx.tfc.api.TFCOptions;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Blocks.BlockTerraContainer;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Core.TFC_Achievements;
import com.bioxx.tfc.Core.TFC_Sounds;
import com.bioxx.tfc.TileEntities.TEQuern;
import com.bioxx.tfc.api.TFCBlocks;

public class BlockQuern extends BlockTerraContainer
{
	private IIcon quernBase;
	private IIcon quernTop1;
	private IIcon quernTop2;

	public BlockQuern()
	{
		super(Material.rock);
		this.setCreativeTab(TFCTabs.TFC_DEVICES);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityplayer, int side, float hitX, float hitY, float hitZ)
	{
		super.onBlockActivated(world, x, y, z, entityplayer, side, hitX, hitY, hitZ);
		TEQuern te = (TEQuern) world.getTileEntity(x, y, z);

		if(TFCOptions.enableDebugMode)
		{
			TerraFirmaCraft.LOG.info("----X:"+hitX+" Z:"+hitZ+" Y:"+hitY+" Side:"+side); //Nice way to find out where the mouse is pointing at
		}
		Boolean hit = side == 1 && hitX >= 0.68 && hitX <= 1.0 && hitZ >= 0.68 && hitZ <= 1.0 ||

				side == 2 && hitX >= 0.67 && hitX <= 0.85 && hitY >= 0.83 ||
				side == 3 && hitX >= 0.67 && hitX <= 0.85 && hitY >= 0.83 ||
				side == 4 && hitZ >= 0.67 && hitZ <= 0.85 && hitY >= 0.83 ||
				side == 5 && hitZ >= 0.67 && hitZ <= 0.85 && hitY >= 0.83;

		if(!world.isRemote)
		{
			if(!te.shouldRotate && hit && te.storage[2] != null)
			{
				te.shouldRotate = true;
				world.markBlockForUpdate(te.xCoord, te.yCoord, te.zCoord);
				world.playSoundEffect(x, y, z, TFC_Sounds.STONEDRAG, 1, 1);
				entityplayer.triggerAchievement(TFC_Achievements.achQuern);
			}
			else if (!te.shouldRotate && !hit || te.storage[2] == null)
			{
				entityplayer.openGui(TerraFirmaCraft.instance, 33, world, x, y, z);
			}
		}
		else if(!te.shouldRotate && hit && te.hasQuern)
		{
			te.shouldRotate = true;
		}
		return true;
	}

	@Override
	public IIcon getIcon(int i, int j)
	{
		if (i == 0 || i == 1 && j == 1)
			return quernTop1;
		else if (i == 1 && j == 0)
			return quernTop2;
		return quernBase;
	}

	@Override
	public void registerBlockIcons(IIconRegister registerer)
	{
		quernBase = registerer.registerIcon(Reference.MOD_ID + ":" + "devices/Quern Base");
		quernTop1 = registerer.registerIcon(Reference.MOD_ID + ":" + "devices/Quern Top 1");
		quernTop2 = registerer.registerIcon(Reference.MOD_ID + ":" + "devices/Quern Top 2");
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
		return TFCBlocks.quernRenderId;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess access, int x, int y, int z, int side)
	{
		return true;
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
	{
		return AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y + 0.825, z + 1);
	}
	
	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z)
	{
		return AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y + 0.825, z + 1);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int var2)
	{
		return new TEQuern();
	}
}
