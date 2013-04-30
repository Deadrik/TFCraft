package TFC.Blocks;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import TFC.TFCBlocks;
import TFC.TerraFirmaCraft;
import TFC.Core.TFC_Sounds;
import TFC.TileEntities.TileEntityQuern;

public class BlockQuern extends BlockTerraContainer {

	public BlockQuern(int par1) {
		super(par1, Material.rock);
		this.setCreativeTab(CreativeTabs.tabDecorations);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityplayer, int side, float hitX, float hitY, float hitZ)  
	{
		super.onBlockActivated(world, x, y, z, entityplayer, side, hitX, hitY, hitZ);
		TileEntityQuern te = (TileEntityQuern) world.getBlockTileEntity(x, y, z);
		if(!world.isRemote)
		{
			if(!te.shouldRotate && hitX >= 0.65 && hitZ >= 0.65 && te.storage[2] != null)
			{
				te.shouldRotate = true;
				world.playSoundEffect(x, y, z, TFC_Sounds.STONEDRAG, 1, 1);
			}	
			else if((!te.shouldRotate && (hitX < 0.65 || hitZ < 0.65)) || te.storage[2] == null)
			{
				entityplayer.openGui(TerraFirmaCraft.instance, 33, world, x, y, z);
			}
		}
		else if(!te.shouldRotate && hitX >= 0.65 && hitZ >= 0.65 && te.hasQuern)
		{
			te.shouldRotate = true;
		}	

		return true;
	}


	@Override
	public Icon getIcon(int i, int j) 
	{
		if(i == 0 || (i == 1 && j == 1))
			return QuernTop1;
		else if (i == 1 && j == 0)
			return QuernTop2;
		return QuernBase;
	}

	Icon QuernBase;
	Icon QuernTop1;
	Icon QuernTop2;

	@Override
	public void registerIcons(IconRegister registerer)
	{
		QuernBase = registerer.registerIcon("devices/Quern Base");
		QuernTop1 = registerer.registerIcon("devices/Quern Top 1");
		QuernTop2 = registerer.registerIcon("devices/Quern Top 2");
	}

	@Override
	public boolean canBeReplacedByLeaves(World w, int x, int y, int z)
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
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int i, int j, int k)
	{
		return AxisAlignedBB.getBoundingBox(i, j, k, i+1, j+0.825, k+1);
	}
	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World par1World, int i, int j, int k)
	{
		return AxisAlignedBB.getBoundingBox(i, j, k, i+1, j+0.825, k+1);
	}

	@Override
	public TileEntity createNewTileEntity(World var1) {
		return new TileEntityQuern();
	}
}
