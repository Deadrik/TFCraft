package TFC.Blocks;

import net.minecraft.src.Material;

public class BlockFarmland extends BlockPartial
{
    protected BlockFarmland(int par1)
    {
        super(par1, Material.ground);
    }
    
    public int getRenderType()
    {
        return 0;
    }
    
    @Override
    public String getTextureFile()
    {
        return "/bioxx/terrablocks2.png";
    }
}
