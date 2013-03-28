package TFC.Render.Blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
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
import net.minecraft.src.ModLoader;
import net.minecraft.stats.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.village.*;
import net.minecraft.world.*;

public class RenderFluids 
{
	public static boolean render(Block par1Block, int par2, int par3, int par4, RenderBlocks renderblocks)
    {
		Tessellator tessellator = Tessellator.instance;
        int l = par1Block.colorMultiplier(renderblocks.blockAccess, par2, par3, par4);
        float f = (float)(l >> 16 & 255) / 255.0F;
        float f1 = (float)(l >> 8 & 255) / 255.0F;
        float f2 = (float)(l & 255) / 255.0F;
        boolean flag = par1Block.shouldSideBeRendered(renderblocks.blockAccess, par2, par3 + 1, par4, 1);
        boolean flag1 = par1Block.shouldSideBeRendered(renderblocks.blockAccess, par2, par3 - 1, par4, 0);
        boolean[] aboolean = new boolean[] {par1Block.shouldSideBeRendered(renderblocks.blockAccess, par2, par3, par4 - 1, 2), par1Block.shouldSideBeRendered(renderblocks.blockAccess, par2, par3, par4 + 1, 3), par1Block.shouldSideBeRendered(renderblocks.blockAccess, par2 - 1, par3, par4, 4), par1Block.shouldSideBeRendered(renderblocks.blockAccess, par2 + 1, par3, par4, 5)};

        if (!flag && !flag1 && !aboolean[0] && !aboolean[1] && !aboolean[2] && !aboolean[3])
        {
            return false;
        }
        else
        {
            boolean flag2 = false;
            float f3 = 0.5F;
            float f4 = 1.0F;
            float f5 = 0.8F;
            float f6 = 0.6F;
            double d0 = 0.0D;
            double d1 = 1.0D;
            Material material = par1Block.blockMaterial;
            int i1 = renderblocks.blockAccess.getBlockMetadata(par2, par3, par4);
            double d2 = (double)renderblocks.getFluidHeight(par2, par3, par4, material);
            double d3 = (double)renderblocks.getFluidHeight(par2, par3, par4 + 1, material);
            double d4 = (double)renderblocks.getFluidHeight(par2 + 1, par3, par4 + 1, material);
            double d5 = (double)renderblocks.getFluidHeight(par2 + 1, par3, par4, material);
            double d6 = 0.0010000000474974513D;
            float f7;

            if (renderblocks.renderAllFaces || flag)
            {
                flag2 = true;
                Icon icon = renderblocks.getBlockIconFromSideAndMetadata(par1Block, 1, i1);
                float f8 = (float)BlockFluid.getFlowDirection(renderblocks.blockAccess, par2, par3, par4, material);

                if (f8 > -999.0F)
                {
                    icon = renderblocks.getBlockIconFromSideAndMetadata(par1Block, 2, i1);
                }

                d2 -= d6;
                d3 -= d6;
                d4 -= d6;
                d5 -= d6;
                double d7;
                double d8;
                double d9;
                double d10;
                double d11;
                double d12;
                double d13;
                double d14;

                if (f8 < -999.0F)
                {
                    d8 = (double)icon.getInterpolatedU(0.0D);
                    d12 = (double)icon.getInterpolatedV(0.0D);
                    d7 = d8;
                    d11 = (double)icon.getInterpolatedV(16.0D);
                    d10 = (double)icon.getInterpolatedU(16.0D);
                    d14 = d11;
                    d9 = d10;
                    d13 = d12;
                }
                else
                {
                    f7 = MathHelper.sin(f8) * 0.25F;
                    float f9 = MathHelper.cos(f8) * 0.25F;
                    d8 = (double)icon.getInterpolatedU((double)(8.0F + (-f9 - f7) * 16.0F));
                    d12 = (double)icon.getInterpolatedV((double)(8.0F + (-f9 + f7) * 16.0F));
                    d7 = (double)icon.getInterpolatedU((double)(8.0F + (-f9 + f7) * 16.0F));
                    d11 = (double)icon.getInterpolatedV((double)(8.0F + (f9 + f7) * 16.0F));
                    d10 = (double)icon.getInterpolatedU((double)(8.0F + (f9 + f7) * 16.0F));
                    d14 = (double)icon.getInterpolatedV((double)(8.0F + (f9 - f7) * 16.0F));
                    d9 = (double)icon.getInterpolatedU((double)(8.0F + (f9 - f7) * 16.0F));
                    d13 = (double)icon.getInterpolatedV((double)(8.0F + (-f9 - f7) * 16.0F));
                }

                tessellator.setBrightness(par1Block.getMixedBrightnessForBlock(renderblocks.blockAccess, par2, par3, par4));
                f7 = 1.0F;
                tessellator.setColorOpaque_F(f4 * f7 * f, f4 * f7 * f1, f4 * f7 * f2);
                tessellator.addVertexWithUV((double)(par2 + 0), (double)par3 + d2, (double)(par4 + 0), d8, d12);
                tessellator.addVertexWithUV((double)(par2 + 0), (double)par3 + d3, (double)(par4 + 1), d7, d11);
                tessellator.addVertexWithUV((double)(par2 + 1), (double)par3 + d4, (double)(par4 + 1), d10, d14);
                tessellator.addVertexWithUV((double)(par2 + 1), (double)par3 + d5, (double)(par4 + 0), d9, d13);
            }

            if (renderblocks.renderAllFaces || flag1)
            {
                tessellator.setBrightness(par1Block.getMixedBrightnessForBlock(renderblocks.blockAccess, par2, par3 - 1, par4));
                float f10 = 1.0F;
                tessellator.setColorOpaque_F(f3 * f10, f3 * f10, f3 * f10);
                renderblocks.renderBottomFace(par1Block, (double)par2, (double)par3 + d6, (double)par4, renderblocks.getBlockIconFromSide(par1Block, 0));
                flag2 = true;
            }

            for (int j1 = 0; j1 < 4; ++j1)
            {
                int k1 = par2;
                int l1 = par4;

                if (j1 == 0)
                {
                    l1 = par4 - 1;
                }

                if (j1 == 1)
                {
                    ++l1;
                }

                if (j1 == 2)
                {
                    k1 = par2 - 1;
                }

                if (j1 == 3)
                {
                    ++k1;
                }

                Icon icon1 = renderblocks.getBlockIconFromSideAndMetadata(par1Block, j1 + 2, i1);

                if (renderblocks.renderAllFaces || aboolean[j1])
                {
                    double d15;
                    double d16;
                    double d17;
                    double d18;
                    double d19;
                    double d20;

                    if (j1 == 0)
                    {
                        d15 = d2;
                        d17 = d5;
                        d16 = (double)par2;
                        d18 = (double)(par2 + 1);
                        d19 = (double)par4 + d6;
                        d20 = (double)par4 + d6;
                    }
                    else if (j1 == 1)
                    {
                        d15 = d4;
                        d17 = d3;
                        d16 = (double)(par2 + 1);
                        d18 = (double)par2;
                        d19 = (double)(par4 + 1) - d6;
                        d20 = (double)(par4 + 1) - d6;
                    }
                    else if (j1 == 2)
                    {
                        d15 = d3;
                        d17 = d2;
                        d16 = (double)par2 + d6;
                        d18 = (double)par2 + d6;
                        d19 = (double)(par4 + 1);
                        d20 = (double)par4;
                    }
                    else
                    {
                        d15 = d5;
                        d17 = d4;
                        d16 = (double)(par2 + 1) - d6;
                        d18 = (double)(par2 + 1) - d6;
                        d19 = (double)par4;
                        d20 = (double)(par4 + 1);
                    }

                    flag2 = true;
                    float f11 = icon1.getInterpolatedU(0.0D);
                    f7 = icon1.getInterpolatedU(8.0D);
                    float f12 = icon1.getInterpolatedV((1.0D - d15) * 16.0D * 0.5D);
                    float f13 = icon1.getInterpolatedV((1.0D - d17) * 16.0D * 0.5D);
                    float f14 = icon1.getInterpolatedV(8.0D);
                    tessellator.setBrightness(par1Block.getMixedBrightnessForBlock(renderblocks.blockAccess, k1, par3, l1));
                    float f15 = 1.0F;

                    if (j1 < 2)
                    {
                        f15 *= f5;
                    }
                    else
                    {
                        f15 *= f6;
                    }

                    tessellator.setColorOpaque_F(f4 * f15 * f, f4 * f15 * f1, f4 * f15 * f2);
                    tessellator.addVertexWithUV(d16, (double)par3 + d15, d19, (double)f11, (double)f12);
                    tessellator.addVertexWithUV(d18, (double)par3 + d17, d20, (double)f7, (double)f13);
                    tessellator.addVertexWithUV(d18, (double)(par3 + 0), d20, (double)f7, (double)f14);
                    tessellator.addVertexWithUV(d16, (double)(par3 + 0), d19, (double)f11, (double)f14);
                }
            }

            renderblocks.renderMinY = d0;
            renderblocks.renderMaxY = d1;
            return flag2;
        }
    }

    /**
     * Get fluid height
     */
    public static float getFluidHeight(int par1, int par2, int par3, Material par4Material, IBlockAccess blockaccess)
    {
        int var5 = 0;
        float var6 = 0.0F;

        for (int var7 = 0; var7 < 4; ++var7)
        {
            int var8 = par1 - (var7 & 1);
            int var10 = par3 - (var7 >> 1 & 1);

            if (blockaccess.getBlockMaterial(var8, par2 + 1, var10) == par4Material)
            {
                return 1.0F;
            }

            Material var11 = blockaccess.getBlockMaterial(var8, par2, var10);

            if (var11 == par4Material)
            {
                int var12 = blockaccess.getBlockMetadata(var8, par2, var10);

                if (var12 >= 8 || var12 == 0)
                {
                    var6 += BlockFluid.getFluidHeightPercent(var12) * 10.0F;
                    var5 += 10;
                }

                var6 += BlockFluid.getFluidHeightPercent(var12);
                ++var5;
            }
            else if (!var11.isSolid())
            {
                ++var6;
                ++var5;
            }
        }

        return 1.0F - var6 / (float)var5;
    }
}
