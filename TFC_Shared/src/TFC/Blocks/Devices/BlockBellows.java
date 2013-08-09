package TFC.Blocks.Devices;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import TFC.Reference;
import TFC.TFCBlocks;
import TFC.TerraFirmaCraft;
import TFC.Blocks.BlockTerraContainer;
import TFC.Core.TFC_Sounds;
import TFC.TileEntities.TileEntityBellows;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockBellows extends BlockTerraContainer {

	public static int getDirectionFromMetadata(int i) {
		return i & 3;
	}

	public BlockBellows(int i, Material material) {
		super(i, material);
		this.setCreativeTab(CreativeTabs.tabRedstone);
	}

	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int par6, float par7, float par8, float par9) {
		super.onBlockActivated(world, i, j, k, entityplayer, par6, par7, par8, par9);
		TileEntityBellows te = (TileEntityBellows) world.getBlockTileEntity(i, j, k);
		if (!world.isRemote) {
			if (!te.shouldBlow) {
				te.shouldBlow = true;
				TerraFirmaCraft.proxy.sendCustomPacketToPlayersInRange(i, j, k, te.createUpdatePacket(), 160);
				world.playSoundEffect(i, j, k, TFC_Sounds.BELLOWS, 0.3F, 1);
			}
		}
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess par1iBlockAccess, int par2, int par3, int par4, int par5) {
		return true;
	}

	@Override
	public Icon getIcon(int i, int j) {
		if (i == 0)// bottom
		{
			if (j == 0) { return Sides[0]; }
			else if (j == 1) { return Sides[1]; }
			else if (j == 2) { return Sides[3]; }
			else if (j == 3) { return Sides[2]; }
		}
		else if (i == 1)// top
		{
			if (j == 0) { return Sides[0]; }
			else if (j == 1) { return Sides[1]; }
			else if (j == 2) { return Sides[3]; }
			else if (j == 3) { return Sides[2]; }
		}
		else if (i == 2)// -x
		{
			if (j == 0) { return BellowsBack; }
			else if (j == 1) { return Sides[2]; } // -z
			else if (j == 2) { return BellowsBack; }
			else if (j == 3) { return Sides[1]; } // -z
		}
		else if (i == 3)// -z
		{
			if (j == 0) { return BellowsBack; }
			else if (j == 1) { return Sides[1]; } // -z
			else if (j == 2) { return BellowsBack; }
			else if (j == 3) { return Sides[2]; } // -z
		}
		else if (i == 4)// +x
		{
			if (j == 0) { return Sides[2]; }
			else if (j == 1) { return BellowsBack; } // -z
			else if (j == 2) { return Sides[1]; }
			else if (j == 3) { return BellowsBack; } // -z
		}
		else if (i == 5)// -z
		{
			if (j == 0) { return Sides[1]; }
			else if (j == 1) { return BellowsBack; } // -z
			else if (j == 2) { return Sides[2]; }
			else if (j == 3) { return BellowsBack; } // -z
		}
		else
		{
			return Sides[1];
		}
		return Sides[0];
	}

	public static Icon[] Sides = new Icon[4];
	public static Icon BellowsFront;
	public static Icon BellowsBack;

	@Override
	public void registerIcons(IconRegister registerer) {
		Sides[0] = registerer.registerIcon(Reference.ModID + ":" + "devices/Bellows82");
		Sides[1] = registerer.registerIcon(Reference.ModID + ":" + "devices/Bellows83");
		Sides[2] = registerer.registerIcon(Reference.ModID + ":" + "devices/Bellows84");
		Sides[3] = registerer.registerIcon(Reference.ModID + ":" + "devices/Bellows85");
		BellowsFront = registerer.registerIcon(Reference.ModID + ":" + "devices/Bellows Front");
		BellowsBack = registerer.registerIcon(Reference.ModID + ":" + "devices/Bellows Back");
	}

	@Override
	public int getRenderType() {
		return TFCBlocks.BellowsRenderId;
	}

	/*@Override
	public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int l) {
		dropBlockAsItem_do(world, i, j, k, new ItemStack(TFCItems.BellowsItem, 1));
	}*/

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLivingBase entityliving, ItemStack is) {
		super.onBlockPlacedBy(world, i, j, k, entityliving, is);
		int l = MathHelper.floor_double(entityliving.rotationYaw * 4F / 360F + 0.5D) & 3;
		world.setBlockMetadataWithNotify(i, j, k, l, 0x2);
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World var1) {
		return new TileEntityBellows();
	}
}
