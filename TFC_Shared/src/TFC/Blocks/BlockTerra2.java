package TFC.Blocks;

import TFC.Core.TFC_Settings;
import TFC.Core.TFC_Core;
import net.minecraft.src.*;

public abstract class BlockTerra2 extends Block
{
	protected BlockTerra2(int par1) 
	{
		super(par1, Material.rock);
	}
	protected BlockTerra2(int par1,int par2, Material material) 
	{
		super(par1,par2, material);
	}

	protected BlockTerra2(int par1, Material material) 
	{
		super(par1, material);
	}

	@Override
	public String getTextureFile()
	{
		return "/bioxx/terrablocks2.png";
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
}
