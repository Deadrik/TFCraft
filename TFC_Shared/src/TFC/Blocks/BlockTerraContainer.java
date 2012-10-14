package TFC.Blocks;

import TFC.Core.TFC_Settings;
import TFC.Core.TFC_Core;
import TFC.TileEntities.TileEntityTerraMetallurgy;
import net.minecraft.src.*;

public abstract class BlockTerraContainer extends BlockContainer
{
	protected BlockTerraContainer(int par1) 
	{
		super(par1, Material.rock);
	}
	protected BlockTerraContainer(int par1,int par2, Material material) 
	{
		super(par1,par2, material);
	}

	protected BlockTerraContainer(int par1, Material material) 
	{
		super(par1, material);
	}


	@Override
	public String getTextureFile()
	{
		return "/bioxx/terrablocks.png";
	}

	@Override
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLiving entityliving) 
	{
		//TODO: Debug Message should go here if debug is toggled on
		if(TFC_Settings.enableDebugMode && world.isRemote)
		{
			int metadata = world.getBlockMetadata(i, j, k);
			System.out.println("Meta="+(new StringBuilder()).append(getBlockName()).append(":").append(metadata).toString());
		}
	}
	
	@Override
	public boolean canBeReplacedByLeaves(World w, int x, int y, int z)
	{
		return false;
	}
	
	@Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityplayer, int par6, float par7, float par8, float par9)  
    {
		if(TFC_Settings.enableDebugMode && world.isRemote)
		{
			int metadata = world.getBlockMetadata(x, y, z);
			System.out.println("Meta = "+(new StringBuilder()).append(getBlockName()).append(":").append(metadata).toString());
		}
		return false;
    }
	
	@Override
	public TileEntity createNewTileEntity(World var1) {
		return null;
	}
}
