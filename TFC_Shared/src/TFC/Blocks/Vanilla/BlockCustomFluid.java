package TFC.Blocks.Vanilla;

import java.util.Random;

import net.minecraft.block.BlockFluid;
import net.minecraft.block.material.Material;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import TFC.TerraFirmaCraft;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class BlockCustomFluid extends BlockFluid
{
    protected BlockCustomFluid(int par1, Material par2Material)
    {
        super(par1, par2Material);
    }

    @Override
	@SideOnly(Side.CLIENT)
    public int getBlockColor()
    {
        return 16777215;
    }

    @Override
	@SideOnly(Side.CLIENT)
    /**
     * Returns a integer with hex for 0xrrggbb with this color multiplied against the blocks color. Note only called
     * when first determining what to render.
     */
    public int colorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        if (this.blockMaterial != Material.water)
        {
            return 16777215;
        }
        else
        {
            return TerraFirmaCraft.proxy.waterColorMultiplier(par1IBlockAccess, par2, par3, par4);
        }
    }

    
    /**
     * Returns the percentage of the fluid block that is air, based on the given flow decay of the fluid.
     */
    public static float getFluidHeightPercent(int par0)
    {
        if (par0 >= 8)
        {
            par0 = 0;
        }

        float var1 = (par0 + 1) / 9.0F;
        return var1;
    }

    /**
     * Returns the amount of fluid decay at the coordinates, or -1 if the block at the coordinates is not the same
     * material as the fluid.
     */
    @Override
	protected int getFlowDecay(World par1World, int par2, int par3, int par4)
    {
        return par1World.getBlockMaterial(par2, par3, par4) != this.blockMaterial ? -1 : par1World.getBlockMetadata(par2, par3, par4);
    }

    /**
     * Returns the flow decay but converts values indicating falling liquid (values >=8) to their effective source block
     * value of zero.
     */
    @Override
	protected int getEffectiveFlowDecay(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        if (par1IBlockAccess.getBlockMaterial(par2, par3, par4) != this.blockMaterial)
        {
            return -1;
        }
        else
        {
            int var5 = par1IBlockAccess.getBlockMetadata(par2, par3, par4);

            if (var5 >= 8)
            {
                var5 = 0;
            }

            return var5;
        }
    }

    /**
     * The type of render function that is called for this block
     */
    /*public int getRenderType()
    {
        return TFCBlocks.fluidRenderId;
    }*/
    
    @Override
	public int tickRate(World world)
    {
        return this.blockMaterial == Material.water ? 5 : (this.blockMaterial == Material.lava ? 30 : 0);
    }

    /**
     * Goes straight to getLightBrightnessForSkyBlocks for Blocks, does some fancy computing for Fluids
     */
    @Override
	public int getMixedBrightnessForBlock(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        int var5 = par1IBlockAccess.getLightBrightnessForSkyBlocks(par2, par3, par4, 0);
        int var6 = par1IBlockAccess.getLightBrightnessForSkyBlocks(par2, par3+1, par4, 0);
        int var7 = var5 & 255;
        int var8 = var6 & 255;
        int var9 = var5 >> 16 & 255;
        int var10 = var6 >> 16 & 255;
        return (var7 > var8 ? var7 : var8) | (var9 > var10 ? var9 : var10) << 16;
    }

    /**
     * How bright to render this block based on the light its receiving. Args: iBlockAccess, x, y, z
     */
    @Override
	public float getBlockBrightness(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        float var5 = par1IBlockAccess.getLightBrightness(par2, par3, par4);
        float var6 = par1IBlockAccess.getLightBrightness(par2, par3+1, par4);
        return var5 > var6 ? var5 : var6;
    }

    /**
     * Ticks the block if it's been scheduled
     */
    @Override
	public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        super.updateTick(par1World, par2, par3, par4, par5Random);
    }
}
