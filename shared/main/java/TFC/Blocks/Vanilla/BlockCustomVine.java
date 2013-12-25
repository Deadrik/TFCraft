package TFC.Blocks.Vanilla;

import net.minecraft.block.BlockVine;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.IShearable;
import TFC.TerraFirmaCraft;
import TFC.Core.ColorizerFoliageTFC;

public class BlockCustomVine extends BlockVine implements IShearable
{
    public BlockCustomVine(int par1)
    {
        super(par1);
    }

    @Override
    public int getBlockColor()
    {
        return ColorizerFoliageTFC.getFoliageColorBasic();
    }

    @Override
    public int getRenderColor(int par1)
    {
        return ColorizerFoliageTFC.getFoliageColorBasic();
    }

    @Override
    public int colorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        return TerraFirmaCraft.proxy.foliageColorMultiplier(par1IBlockAccess, par2, par3, par4);
    }
}
