package TFC.Blocks.Devices;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import TFC.TFCBlocks;
import TFC.TerraFirmaCraft;
import TFC.Blocks.BlockTerraContainer;
import TFC.Core.TFC_Textures;
import TFC.TileEntities.TENestBox;

public class BlockNestBox extends BlockTerraContainer
{
	private final Random random = new Random();

	public BlockNestBox(int par1)
	{
		super(par1, Material.wood);
		this.setCreativeTab(CreativeTabs.tabDecorations);
		this.setBlockBounds(0.1f, 0, 0.1f, 0.9f, 0.4f, 0.9f);
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
		return TFCBlocks.NestBoxRenderId;
	}

	@Override
	public void registerIcons(IconRegister iconRegisterer)
	{
		this.blockIcon = TFC_Textures.InvisibleTexture;//iconRegisterer.registerIcon(Reference.ModID + ":" + "plants/Thatch");
	}

	@Override
	public boolean getBlocksMovement(IBlockAccess par1IBlockAccess, int i, int j, int k)
	{
		return true;
	}

	@Override
	public Icon getIcon(int side, int meta)
	{
		return this.blockIcon;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityplayer, int side, float hitX, float hitY, float hitZ)
	{
		if (!world.isRemote)
		{
			//((NetworkTileEntity)world.getBlockTileEntity(x,y,z)).validate();
			entityplayer.openGui(TerraFirmaCraft.instance, 41, world, x, y, z);
			return true;
		}
		return false;

	}

	@Override
	public TileEntity createNewTileEntity(World var1) {
		// TODO Auto-generated method stub
		return new TENestBox();
	}
}
