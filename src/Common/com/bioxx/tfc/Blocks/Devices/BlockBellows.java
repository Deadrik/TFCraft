package com.bioxx.tfc.Blocks.Devices;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Blocks.BlockTerraContainer;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Core.TFC_Sounds;
import com.bioxx.tfc.TileEntities.TEBellows;
import com.bioxx.tfc.api.TFCBlocks;

public class BlockBellows extends BlockTerraContainer
{
	public static IIcon[] sides = new IIcon[4];
	public static IIcon bellowsFront;
	public static IIcon bellowsBack;

	public static int getDirectionFromMetadata(int i)
	{
		return i & 3;
	}

	public BlockBellows(Material material)
	{
		super(material);
		this.setCreativeTab(TFCTabs.TFC_DEVICES);
	}

	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int par6, float par7, float par8, float par9)
	{
		super.onBlockActivated(world, i, j, k, entityplayer, par6, par7, par8, par9);
		TEBellows teb = null;
		TileEntity te = world.getTileEntity(i, j, k);
		if (!world.isRemote && te != null && te instanceof TEBellows)
		{
			teb = (TEBellows) te;
			if (!teb.shouldBlow)
			{
				teb.shouldBlow = true;
				world.markBlockForUpdate(i, j, k);
				//TerraFirmaCraft.proxy.sendCustomPacketToPlayersInRange(i, j, k, te.createUpdatePacket(), 160);
				world.playSoundEffect(i, j, k, TFC_Sounds.BELLOWS, 0.4F, 1);
			}
		}
		return true;
	}

	// Needed only if RenderBellows is being used.
//	@Override
//	@SideOnly(Side.CLIENT)
//	public boolean shouldSideBeRendered(IBlockAccess par1iBlockAccess, int par2, int par3, int par4, int par5) {
//		return true;
//	}

	@Override
	public IIcon getIcon(int i, int j) {
		if (i == 0)// bottom
		{
			if (j == 0) { return sides[0]; }
			else if (j == 1) { return sides[1]; }
			else if (j == 2) { return sides[3]; }
			else if (j == 3) { return sides[2]; }
		}
		else if (i == 1)// top
		{
			if (j == 0) { return sides[0]; }
			else if (j == 1) { return sides[1]; }
			else if (j == 2) { return sides[3]; }
			else if (j == 3) { return sides[2]; }
		}
		else if (i == 2)// -x
		{
			if (j == 0) { return bellowsBack; }
			else if (j == 1) { return sides[2]; } // -z
			else if (j == 2) { return bellowsBack; }
			else if (j == 3) { return sides[1]; } // -z
		}
		else if (i == 3)// -z
		{
			if (j == 0) { return bellowsBack; }
			else if (j == 1) { return sides[1]; } // -z
			else if (j == 2) { return bellowsBack; }
			else if (j == 3) { return sides[2]; } // -z
		}
		else if (i == 4)// +x
		{
			if (j == 0) { return sides[2]; }
			else if (j == 1) { return bellowsBack; } // -z
			else if (j == 2) { return sides[1]; }
			else if (j == 3) { return bellowsBack; } // -z
		}
		else if (i == 5)// -z
		{
			if (j == 0) { return sides[1]; }
			else if (j == 1) { return bellowsBack; } // -z
			else if (j == 2) { return sides[2]; }
			else if (j == 3) { return bellowsBack; } // -z
		}
		else
		{
			return sides[1];
		}
		return sides[0];
	}

	@Override
	public void registerBlockIcons(IIconRegister registerer)
	{
		sides[0] = registerer.registerIcon(Reference.MOD_ID + ":" + "devices/Bellows82");
		sides[1] = registerer.registerIcon(Reference.MOD_ID + ":" + "devices/Bellows83");
		sides[2] = registerer.registerIcon(Reference.MOD_ID + ":" + "devices/Bellows84");
		sides[3] = registerer.registerIcon(Reference.MOD_ID + ":" + "devices/Bellows85");
		bellowsFront = registerer.registerIcon(Reference.MOD_ID + ":" + "devices/Bellows Front");
		bellowsBack = registerer.registerIcon(Reference.MOD_ID + ":" + "devices/Bellows Back");
	}

	@Override
	public int getRenderType()
	{
		return TFCBlocks.bellowsRenderId;
	}

	/*@Override
	public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int l) {
		dropBlockAsItem(world, i, j, k, new ItemStack(TFCItems.BellowsItem, 1));
	}*/

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLivingBase entityliving, ItemStack is)
	{
		super.onBlockPlacedBy(world, i, j, k, entityliving, is);
		int l = MathHelper.floor_double(entityliving.rotationYaw * 4F / 360F + 0.5D) & 3;
		world.setBlockMetadataWithNotify(i, j, k, l, 0x2);
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2)
	{
		return new TEBellows();
	}
}
