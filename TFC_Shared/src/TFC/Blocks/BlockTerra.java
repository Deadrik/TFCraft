package TFC.Blocks;

import TFC.Core.TFCSettings;
import TFC.Core.TFC_Core;
import net.minecraft.src.*;

public abstract class BlockTerra extends Block
{

    protected BlockTerra(int par1) 
    {
        super(par1, Material.rock);
    }
    protected BlockTerra(int par1,int par2, Material material) 
    {
        super(par1,par2, material);
    }

    protected BlockTerra(int par1, Material material) 
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
        if(TFCSettings.enableDebugMode && world.isRemote)
        {
            int metadata = world.getBlockMetadata(i, j, k);
            System.out.println("Meta="+(new StringBuilder()).append(getBlockName()).append(":").append(metadata).toString());
        }
    }
}
