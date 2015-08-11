package com.bioxx.tfc.Blocks.Devices;

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

import com.bioxx.tfc.Blocks.BlockTerraContainer;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Core.TFC_Achievements;
import com.bioxx.tfc.Core.TFC_Sounds;
import com.bioxx.tfc.Reference;
import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.TileEntities.TEQuern;
import com.bioxx.tfc.api.TFCBlocks;

public class BlockQuern extends BlockTerraContainer
{
	private IIcon QuernBase;
	private IIcon QuernTop1;
	private IIcon QuernTop2;

	public BlockQuern()
	{
		super(Material.rock);
		this.setCreativeTab(TFCTabs.TFCDevices);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityplayer, int side, float hitX, float hitY, float hitZ)
	{
		super.onBlockActivated(world, x, y, z, entityplayer, side, hitX, hitY, hitZ);
		TEQuern te = (TEQuern) world.getTileEntity(x, y, z);

		//TerraFirmaCraft.log.info("----X:"+hitX+" Z:"+hitZ+" Y:"+hitY+" Side:"+side); //Nice way to find out where the mouse is pointing at
		Boolean hit = (side == 1 && hitX >= 0.70 && hitX <= 90 && hitZ >= 0.70 && hitZ <= 0.90) ||
				(side == 5 && hitZ >= 0.8 && hitZ <= 0.99 && hitY >= 0.85);

		if(!world.isRemote)
		{
			if(!te.shouldRotate && hit && te.storage[2] != null)
			{
				te.shouldRotate = true;
				world.markBlockForUpdate(te.xCoord, te.yCoord, te.zCoord);
				world.playSoundEffect(x, y, z, TFC_Sounds.STONEDRAG, 1, 1);
				entityplayer.triggerAchievement(TFC_Achievements.achQuern);
			}
			else if((!te.shouldRotate && !hit) || te.storage[2] == null)
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
		if(i == 0 || (i == 1 && j == 1))
			return QuernTop1;
		else if (i == 1 && j == 0)
			return QuernTop2;
		return QuernBase;
	}

	@Override
	public void registerBlockIcons(IIconRegister registerer)
	{
		QuernBase = registerer.registerIcon(Reference.ModID + ":" + "devices/Quern Base");
		QuernTop1 = registerer.registerIcon(Reference.ModID + ":" + "devices/Quern Top 1");
		QuernTop2 = registerer.registerIcon(Reference.ModID + ":" + "devices/Quern Top 2");
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
