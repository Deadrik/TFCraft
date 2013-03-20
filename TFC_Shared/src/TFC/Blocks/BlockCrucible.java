package TFC.Blocks;

import TFC.TFCBlocks;
import TFC.TileEntities.TileEntityToolRack;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class BlockCrucible extends BlockTerraContainer
{
	Icon textureTop;
	Icon textureSide;
	
	public BlockCrucible(int par1) 
	{
		super(par1, Material.rock);
		this.setBlockBounds(0.0625f, 0f, 0.0625f, 0.9375f, 0.9375f, 0.9375f);
		this.setUnlocalizedName("Crucible");
		this.setCreativeTab(CreativeTabs.tabRedstone);
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
	
//	@Override
//	public int getRenderType()
//	{
//		return TFCBlocks.toolRackRenderId;
//	}
	
	@Override
	public Icon getBlockTextureFromSideAndMetadata(int i, int j)
	{
		if(i == 0 || i == 1)
			return textureTop;
		
		return textureSide;
	}
	
	@Override
    public void registerIcon(IconRegister registerer)
    {
		textureTop = registerer.func_94245_a("/devices/Crucible Top");
		textureSide = registerer.func_94245_a("/devices/Crucible Side");
    }
	
	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float hitX, float hitY, float hitZ)
	{
		if(!world.isRemote)
		{

		}
		return false;
	}
	
	@Override
	public TileEntity createNewTileEntity(World var1)
	{
		return null;
	}
	
}
