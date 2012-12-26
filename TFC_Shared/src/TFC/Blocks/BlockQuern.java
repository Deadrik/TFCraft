package TFC.Blocks;

import TFC.TFCBlocks;
import TFC.TerraFirmaCraft;
import TFC.Core.TFC_Settings;
import TFC.Core.TFC_Sounds;
import TFC.TileEntities.TileEntityQuern;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.crash.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.packet.*;
import net.minecraft.pathfinding.*;
import net.minecraft.potion.*;
import net.minecraft.server.*;
import net.minecraft.stats.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.village.*;
import net.minecraft.world.*;
import net.minecraft.world.biome.*;
import net.minecraft.world.chunk.*;
import net.minecraft.world.gen.feature.*;

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
	public int getBlockTextureFromSideAndMetadata(int i, int j) 
	{
		if(i == 0 || (i == 1 && j == 1))
			return 72;
		else if (i == 1 && j == 0)
			return 70;
		return 71;
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
