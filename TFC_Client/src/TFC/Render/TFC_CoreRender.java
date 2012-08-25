package TFC.Render;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import bioxx.importers.WavefrontObject;

import TFC.Blocks.BlockFiniteWater;
import TFC.Blocks.BlockFruitLeaves;
import TFC.Blocks.BlockTerraAnvil;
import TFC.Blocks.BlockTerraBellows;
import TFC.Blocks.BlockTerraSluice;
import TFC.Core.AnvilReq;
import TFC.Core.CropIndex;
import TFC.Core.CropManager;
import TFC.Core.FloraIndex;
import TFC.Core.FloraManager;
import TFC.Core.TFCSeasons;
import TFC.Core.TFCSettings;
import TFC.Core.TFC_Core.Direction;
import TFC.TileEntities.TileEntityCrop;
import TFC.TileEntities.TileEntityFruitTreeWood;
import TFC.TileEntities.TileEntityPartial;
import TFC.TileEntities.TileEntityTerraAnvil;
import TFC.WorldGen.TFCBiome;

import net.minecraft.client.Minecraft;
import net.minecraft.src.BiomeGenBase;
import net.minecraft.src.Block;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Material;
import net.minecraft.src.MathHelper;
import net.minecraft.src.ModLoader;
import net.minecraft.src.RenderBlocks;
import net.minecraft.src.Tessellator;
import net.minecraft.src.World;
import net.minecraft.src.mod_TFC;
import net.minecraft.src.forge.MinecraftForgeClient;

public class TFC_CoreRender 
{
    public static boolean renderBlockSlab(Block par1Block, int par2, int par3, int par4, RenderBlocks renderblocks)
    {
        TileEntityPartial te = (TileEntityPartial) renderblocks.blockAccess.getBlockTileEntity(par2, par3, par4);
        int md = renderblocks.blockAccess.getBlockMetadata(par2, par3, par4);

        if(te.TypeID <= 0) return false;

        int type = te.TypeID;
        int meta = te.MetaID;
        int tex = Block.blocksList[type].getBlockTextureFromSideAndMetadata(0, meta);
        long extraX = (te.extraData) & 0xf;
        long extraY = (te.extraData >> 4) & 0xf;
        long extraZ = (te.extraData >> 8) & 0xf;
        long extraX2 = (te.extraData >> 12) & 0xf;
        long extraY2 = (te.extraData >> 16) & 0xf;
        long extraZ2 = (te.extraData >> 20) & 0xf;

        par1Block.setBlockBounds(0.0F+ (0.1F * extraX), 0.0F+ (0.1F * extraY), 0.0F+ (0.1F * extraZ), 1.0F-(0.1F * extraX2), 1-(0.1F * extraY2), 1.0F-(0.1F * extraZ2));

        int over = renderblocks.overrideBlockTexture;
        if(over == -1 && (type == mod_TFC.terraOre.blockID || type == mod_TFC.terraOre2.blockID || type == mod_TFC.terraOre3.blockID))
        {
            BiomeGenBase biome = renderblocks.blockAccess.getBiomeGenForCoords(par2, par4);
            renderblocks.overrideBlockTexture = getRockTexture(par3,(TFCBiome)biome);
            renderblocks.renderStandardBlock(par1Block, par2, par3, par4);
            renderblocks.overrideBlockTexture = over;
        }


        if(over == -1)
            renderblocks.overrideBlockTexture = tex;

        renderblocks.renderStandardBlock(par1Block, par2, par3, par4);

        renderblocks.overrideBlockTexture = over;



        return true;
    }
    /**
     * Renders a stair block at the given coordinates
     */
    public static boolean renderBlockStairs(Block par1Block, int par2, int par3, int par4, RenderBlocks renderblocks)
    {
        int var5 = renderblocks.blockAccess.getBlockMetadata(par2, par3, par4);
        int var6 = var5 & 3;
        float var7 = 0.0F;
        float var8 = 0.5F;
        float var9 = 0.5F;
        float var10 = 1.0F;

        if ((var5 & 4) != 0)
        {
            var7 = 0.5F;
            var8 = 1.0F;
            var9 = 0.0F;
            var10 = 0.5F;
        }
        TileEntityPartial te = (TileEntityPartial) renderblocks.blockAccess.getBlockTileEntity(par2, par3, par4);
        if(te.TypeID <= 0) return false;

        int type = te.TypeID;
        int meta = te.MetaID;
        int tex = Block.blocksList[type].getBlockTextureFromSideAndMetadata(0, meta);
        renderblocks.overrideBlockTexture = tex;
        par1Block.setBlockBounds(0.0F, var7, 0.0F, 1.0F, var8, 1.0F);
        renderblocks.renderStandardBlock(par1Block, par2, par3, par4);

        if (var6 == 0)
        {
            par1Block.setBlockBounds(0.5F, var9, 0.0F, 1.0F, var10, 1.0F);
            renderblocks.renderStandardBlock(par1Block, par2, par3, par4);
        }
        else if (var6 == 1)
        {
            par1Block.setBlockBounds(0.0F, var9, 0.0F, 0.5F, var10, 1.0F);
            renderblocks.renderStandardBlock(par1Block, par2, par3, par4);
        }
        else if (var6 == 2)
        {
            par1Block.setBlockBounds(0.0F, var9, 0.5F, 1.0F, var10, 1.0F);
            renderblocks.renderStandardBlock(par1Block, par2, par3, par4);
        }
        else if (var6 == 3)
        {
            par1Block.setBlockBounds(0.0F, var9, 0.0F, 1.0F, var10, 0.5F);
            renderblocks.renderStandardBlock(par1Block, par2, par3, par4);
        }
        renderblocks.overrideBlockTexture = -1;
        par1Block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        return true;
    }

    public static boolean RenderGrass(Block block, int par2, int par3, int par4, 
            float par5, float par6, float par7, RenderBlocks renderblocks)
    {
        renderblocks.enableAO = true;
        boolean var8 = false;
        float var9 = renderblocks.lightValueOwn;
        float var10 = renderblocks.lightValueOwn;
        float var11 = renderblocks.lightValueOwn;
        float var12 = renderblocks.lightValueOwn;
        boolean var13 = true;
        boolean var14 = true;
        boolean var15 = true;
        boolean var16 = true;
        boolean var17 = true;
        boolean var18 = true;
        renderblocks.lightValueOwn = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, par2, par3, par4);
        renderblocks.aoLightValueXNeg = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, par2 - 1, par3, par4);
        renderblocks.aoLightValueYNeg = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, par2, par3 - 1, par4);
        renderblocks.aoLightValueZNeg = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, par2, par3, par4 - 1);
        renderblocks.aoLightValueXPos = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, par2 + 1, par3, par4);
        renderblocks.aoLightValueYPos = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, par2, par3 + 1, par4);
        renderblocks.aoLightValueZPos = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, par2, par3, par4 + 1);
        int var19 = block.getMixedBrightnessForBlock(renderblocks.blockAccess, par2, par3, par4);
        int var20 = var19;
        int var21 = var19;
        int var22 = var19;
        int var23 = var19;
        int var24 = var19;
        int var25 = var19;

        int BGTex = renderblocks.blockAccess.getBlockId(par2, par3+1, par4) == Block.snow.blockID ? 253 : 255;

        if (block.minY <= 0.0D)
        {
            var21 = block.getMixedBrightnessForBlock(renderblocks.blockAccess, par2, par3 - 1, par4);
        }

        if (block.maxY >= 1.0D)
        {
            var24 = block.getMixedBrightnessForBlock(renderblocks.blockAccess, par2, par3 + 1, par4);
        }

        if (block.minX <= 0.0D)
        {
            var20 = block.getMixedBrightnessForBlock(renderblocks.blockAccess, par2 - 1, par3, par4);
        }

        if (block.maxX >= 1.0D)
        {
            var23 = block.getMixedBrightnessForBlock(renderblocks.blockAccess, par2 + 1, par3, par4);
        }

        if (block.minZ <= 0.0D)
        {
            var22 = block.getMixedBrightnessForBlock(renderblocks.blockAccess, par2, par3, par4 - 1);
        }

        if (block.maxZ >= 1.0D)
        {
            var25 = block.getMixedBrightnessForBlock(renderblocks.blockAccess, par2, par3, par4 + 1);
        }

        Tessellator tesselator = Tessellator.instance;
        tesselator.setBrightness(983055);
        renderblocks.aoGrassXYZPPC = Block.canBlockGrass[renderblocks.blockAccess.getBlockId(par2 + 1, par3 + 1, par4)];
        renderblocks.aoGrassXYZPNC = Block.canBlockGrass[renderblocks.blockAccess.getBlockId(par2 + 1, par3 - 1, par4)];
        renderblocks.aoGrassXYZPCP = Block.canBlockGrass[renderblocks.blockAccess.getBlockId(par2 + 1, par3, par4 + 1)];
        renderblocks.aoGrassXYZPCN = Block.canBlockGrass[renderblocks.blockAccess.getBlockId(par2 + 1, par3, par4 - 1)];
        renderblocks.aoGrassXYZNPC = Block.canBlockGrass[renderblocks.blockAccess.getBlockId(par2 - 1, par3 + 1, par4)];
        renderblocks.aoGrassXYZNNC = Block.canBlockGrass[renderblocks.blockAccess.getBlockId(par2 - 1, par3 - 1, par4)];
        renderblocks.aoGrassXYZNCN = Block.canBlockGrass[renderblocks.blockAccess.getBlockId(par2 - 1, par3, par4 - 1)];
        renderblocks.aoGrassXYZNCP = Block.canBlockGrass[renderblocks.blockAccess.getBlockId(par2 - 1, par3, par4 + 1)];
        renderblocks.aoGrassXYZCPP = Block.canBlockGrass[renderblocks.blockAccess.getBlockId(par2, par3 + 1, par4 + 1)];
        renderblocks.aoGrassXYZCPN = Block.canBlockGrass[renderblocks.blockAccess.getBlockId(par2, par3 + 1, par4 - 1)];
        renderblocks.aoGrassXYZCNP = Block.canBlockGrass[renderblocks.blockAccess.getBlockId(par2, par3 - 1, par4 + 1)];
        renderblocks.aoGrassXYZCNN = Block.canBlockGrass[renderblocks.blockAccess.getBlockId(par2, par3 - 1, par4 - 1)];

        if (block.blockIndexInTexture != 255)
        {
            var18 = false;
            var17 = false;
            var16 = false;
            var15 = false;
            var13 = false;
        }
        else
        {
            var18 = true;
            var17 = true;
            var16 = true;
            var15 = true;
            var13 = true;
        }

        if (renderblocks.overrideBlockTexture >= 0)
        {
            var18 = false;
            var17 = false;
            var16 = false;
            var15 = false;
            var13 = false;
        }

        if (renderblocks.renderAllFaces || block.shouldSideBeRendered(renderblocks.blockAccess, par2, par3 - 1, par4, 0))
        {
            if (renderblocks.aoType > 0)
            {
                if (block.minY <= 0.0D)
                {
                    --par3;
                }

                renderblocks.aoBrightnessXYNN = block.getMixedBrightnessForBlock(renderblocks.blockAccess, par2 - 1, par3, par4);
                renderblocks.aoBrightnessYZNN = block.getMixedBrightnessForBlock(renderblocks.blockAccess, par2, par3, par4 - 1);
                renderblocks.aoBrightnessYZNP = block.getMixedBrightnessForBlock(renderblocks.blockAccess, par2, par3, par4 + 1);
                renderblocks.aoBrightnessXYPN = block.getMixedBrightnessForBlock(renderblocks.blockAccess, par2 + 1, par3, par4);
                renderblocks.aoLightValueScratchXYNN = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, par2 - 1, par3, par4);
                renderblocks.aoLightValueScratchYZNN = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, par2, par3, par4 - 1);
                renderblocks.aoLightValueScratchYZNP = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, par2, par3, par4 + 1);
                renderblocks.aoLightValueScratchXYPN = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, par2 + 1, par3, par4);

                if (!renderblocks.aoGrassXYZCNN && !renderblocks.aoGrassXYZNNC)
                {
                    renderblocks.aoLightValueScratchXYZNNN = renderblocks.aoLightValueScratchXYNN;
                    renderblocks.aoBrightnessXYZNNN = renderblocks.aoBrightnessXYNN;
                }
                else
                {
                    renderblocks.aoLightValueScratchXYZNNN = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, par2 - 1, par3, par4 - 1);
                    renderblocks.aoBrightnessXYZNNN = block.getMixedBrightnessForBlock(renderblocks.blockAccess, par2 - 1, par3, par4 - 1);
                }

                if (!renderblocks.aoGrassXYZCNP && !renderblocks.aoGrassXYZNNC)
                {
                    renderblocks.aoLightValueScratchXYZNNP = renderblocks.aoLightValueScratchXYNN;
                    renderblocks.aoBrightnessXYZNNP = renderblocks.aoBrightnessXYNN;
                }
                else
                {
                    renderblocks.aoLightValueScratchXYZNNP = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, par2 - 1, par3, par4 + 1);
                    renderblocks.aoBrightnessXYZNNP = block.getMixedBrightnessForBlock(renderblocks.blockAccess, par2 - 1, par3, par4 + 1);
                }

                if (!renderblocks.aoGrassXYZCNN && !renderblocks.aoGrassXYZPNC)
                {
                    renderblocks.aoLightValueScratchXYZPNN = renderblocks.aoLightValueScratchXYPN;
                    renderblocks.aoBrightnessXYZPNN = renderblocks.aoBrightnessXYPN;
                }
                else
                {
                    renderblocks.aoLightValueScratchXYZPNN = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, par2 + 1, par3, par4 - 1);
                    renderblocks.aoBrightnessXYZPNN = block.getMixedBrightnessForBlock(renderblocks.blockAccess, par2 + 1, par3, par4 - 1);
                }

                if (!renderblocks.aoGrassXYZCNP && !renderblocks.aoGrassXYZPNC)
                {
                    renderblocks.aoLightValueScratchXYZPNP = renderblocks.aoLightValueScratchXYPN;
                    renderblocks.aoBrightnessXYZPNP = renderblocks.aoBrightnessXYPN;
                }
                else
                {
                    renderblocks.aoLightValueScratchXYZPNP = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, par2 + 1, par3, par4 + 1);
                    renderblocks.aoBrightnessXYZPNP = block.getMixedBrightnessForBlock(renderblocks.blockAccess, par2 + 1, par3, par4 + 1);
                }

                if (block.minY <= 0.0D)
                {
                    ++par3;
                }

                var9 = (renderblocks.aoLightValueScratchXYZNNP + renderblocks.aoLightValueScratchXYNN + renderblocks.aoLightValueScratchYZNP + renderblocks.aoLightValueYNeg) / 4.0F;
                var12 = (renderblocks.aoLightValueScratchYZNP + renderblocks.aoLightValueYNeg + renderblocks.aoLightValueScratchXYZPNP + renderblocks.aoLightValueScratchXYPN) / 4.0F;
                var11 = (renderblocks.aoLightValueYNeg + renderblocks.aoLightValueScratchYZNN + renderblocks.aoLightValueScratchXYPN + renderblocks.aoLightValueScratchXYZPNN) / 4.0F;
                var10 = (renderblocks.aoLightValueScratchXYNN + renderblocks.aoLightValueScratchXYZNNN + renderblocks.aoLightValueYNeg + renderblocks.aoLightValueScratchYZNN) / 4.0F;
                renderblocks.brightnessTopLeft = renderblocks.getAoBrightness(renderblocks.aoBrightnessXYZNNP, renderblocks.aoBrightnessXYNN, renderblocks.aoBrightnessYZNP, var21);
                renderblocks.brightnessTopRight = renderblocks.getAoBrightness(renderblocks.aoBrightnessYZNP, renderblocks.aoBrightnessXYZPNP, renderblocks.aoBrightnessXYPN, var21);
                renderblocks.brightnessBottomRight = renderblocks.getAoBrightness(renderblocks.aoBrightnessYZNN, renderblocks.aoBrightnessXYPN, renderblocks.aoBrightnessXYZPNN, var21);
                renderblocks.brightnessBottomLeft = renderblocks.getAoBrightness(renderblocks.aoBrightnessXYNN, renderblocks.aoBrightnessXYZNNN, renderblocks.aoBrightnessYZNN, var21);
            }
            else
            {
                var12 = renderblocks.aoLightValueYNeg;
                var11 = renderblocks.aoLightValueYNeg;
                var10 = renderblocks.aoLightValueYNeg;
                var9 = renderblocks.aoLightValueYNeg;
                renderblocks.brightnessTopLeft = renderblocks.brightnessBottomLeft = renderblocks.brightnessBottomRight = renderblocks.brightnessTopRight = renderblocks.aoBrightnessXYNN;
            }

            renderblocks.colorRedTopLeft = renderblocks.colorRedBottomLeft = renderblocks.colorRedBottomRight = renderblocks.colorRedTopRight = (var13 ? par5 : 1.0F) * 0.5F;
            renderblocks.colorGreenTopLeft = renderblocks.colorGreenBottomLeft = renderblocks.colorGreenBottomRight = renderblocks.colorGreenTopRight = (var13 ? par6 : 1.0F) * 0.5F;
            renderblocks.colorBlueTopLeft = renderblocks.colorBlueBottomLeft = renderblocks.colorBlueBottomRight = renderblocks.colorBlueTopRight = (var13 ? par7 : 1.0F) * 0.5F;
            renderblocks.colorRedTopLeft *= var9;
            renderblocks.colorGreenTopLeft *= var9;
            renderblocks.colorBlueTopLeft *= var9;
            renderblocks.colorRedBottomLeft *= var10;
            renderblocks.colorGreenBottomLeft *= var10;
            renderblocks.colorBlueBottomLeft *= var10;
            renderblocks.colorRedBottomRight *= var11;
            renderblocks.colorGreenBottomRight *= var11;
            renderblocks.colorBlueBottomRight *= var11;
            renderblocks.colorRedTopRight *= var12;
            renderblocks.colorGreenTopRight *= var12;
            renderblocks.colorBlueTopRight *= var12;
            renderblocks.renderBottomFace(block, (double)par2, (double)par3, (double)par4, block.getBlockTexture(renderblocks.blockAccess, par2, par3, par4, 0));
            var8 = true;
        }

        if (renderblocks.renderAllFaces || block.shouldSideBeRendered(renderblocks.blockAccess, par2, par3 + 1, par4, 1))
        {
            if (renderblocks.aoType > 0)
            {
                if (block.maxY >= 1.0D)
                {
                    ++par3;
                }

                renderblocks.aoBrightnessXYNP = block.getMixedBrightnessForBlock(renderblocks.blockAccess, par2 - 1, par3, par4);
                renderblocks.aoBrightnessXYPP = block.getMixedBrightnessForBlock(renderblocks.blockAccess, par2 + 1, par3, par4);
                renderblocks.aoBrightnessYZPN = block.getMixedBrightnessForBlock(renderblocks.blockAccess, par2, par3, par4 - 1);
                renderblocks.aoBrightnessYZPP = block.getMixedBrightnessForBlock(renderblocks.blockAccess, par2, par3, par4 + 1);
                renderblocks.aoLightValueScratchXYNP = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, par2 - 1, par3, par4);
                renderblocks.aoLightValueScratchXYPP = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, par2 + 1, par3, par4);
                renderblocks.aoLightValueScratchYZPN = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, par2, par3, par4 - 1);
                renderblocks.aoLightValueScratchYZPP = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, par2, par3, par4 + 1);

                if (!renderblocks.aoGrassXYZCPN && !renderblocks.aoGrassXYZNPC)
                {
                    renderblocks.aoLightValueScratchXYZNPN = renderblocks.aoLightValueScratchXYNP;
                    renderblocks.aoBrightnessXYZNPN = renderblocks.aoBrightnessXYNP;
                }
                else
                {
                    renderblocks.aoLightValueScratchXYZNPN = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, par2 - 1, par3, par4 - 1);
                    renderblocks.aoBrightnessXYZNPN = block.getMixedBrightnessForBlock(renderblocks.blockAccess, par2 - 1, par3, par4 - 1);
                }

                if (!renderblocks.aoGrassXYZCPN && !renderblocks.aoGrassXYZPPC)
                {
                    renderblocks.aoLightValueScratchXYZPPN = renderblocks.aoLightValueScratchXYPP;
                    renderblocks.aoBrightnessXYZPPN = renderblocks.aoBrightnessXYPP;
                }
                else
                {
                    renderblocks.aoLightValueScratchXYZPPN = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, par2 + 1, par3, par4 - 1);
                    renderblocks.aoBrightnessXYZPPN = block.getMixedBrightnessForBlock(renderblocks.blockAccess, par2 + 1, par3, par4 - 1);
                }

                if (!renderblocks.aoGrassXYZCPP && !renderblocks.aoGrassXYZNPC)
                {
                    renderblocks.aoLightValueScratchXYZNPP = renderblocks.aoLightValueScratchXYNP;
                    renderblocks.aoBrightnessXYZNPP = renderblocks.aoBrightnessXYNP;
                }
                else
                {
                    renderblocks.aoLightValueScratchXYZNPP = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, par2 - 1, par3, par4 + 1);
                    renderblocks.aoBrightnessXYZNPP = block.getMixedBrightnessForBlock(renderblocks.blockAccess, par2 - 1, par3, par4 + 1);
                }

                if (!renderblocks.aoGrassXYZCPP && !renderblocks.aoGrassXYZPPC)
                {
                    renderblocks.aoLightValueScratchXYZPPP = renderblocks.aoLightValueScratchXYPP;
                    renderblocks.aoBrightnessXYZPPP = renderblocks.aoBrightnessXYPP;
                }
                else
                {
                    renderblocks.aoLightValueScratchXYZPPP = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, par2 + 1, par3, par4 + 1);
                    renderblocks.aoBrightnessXYZPPP = block.getMixedBrightnessForBlock(renderblocks.blockAccess, par2 + 1, par3, par4 + 1);
                }

                if (block.maxY >= 1.0D)
                {
                    --par3;
                }

                var12 = (renderblocks.aoLightValueScratchXYZNPP + renderblocks.aoLightValueScratchXYNP + renderblocks.aoLightValueScratchYZPP + renderblocks.aoLightValueYPos) / 4.0F;
                var9 = (renderblocks.aoLightValueScratchYZPP + renderblocks.aoLightValueYPos + renderblocks.aoLightValueScratchXYZPPP + renderblocks.aoLightValueScratchXYPP) / 4.0F;
                var10 = (renderblocks.aoLightValueYPos + renderblocks.aoLightValueScratchYZPN + renderblocks.aoLightValueScratchXYPP + renderblocks.aoLightValueScratchXYZPPN) / 4.0F;
                var11 = (renderblocks.aoLightValueScratchXYNP + renderblocks.aoLightValueScratchXYZNPN + renderblocks.aoLightValueYPos + renderblocks.aoLightValueScratchYZPN) / 4.0F;
                renderblocks.brightnessTopRight = renderblocks.getAoBrightness(renderblocks.aoBrightnessXYZNPP, renderblocks.aoBrightnessXYNP, renderblocks.aoBrightnessYZPP, var24);
                renderblocks.brightnessTopLeft = renderblocks.getAoBrightness(renderblocks.aoBrightnessYZPP, renderblocks.aoBrightnessXYZPPP, renderblocks.aoBrightnessXYPP, var24);
                renderblocks.brightnessBottomLeft = renderblocks.getAoBrightness(renderblocks.aoBrightnessYZPN, renderblocks.aoBrightnessXYPP, renderblocks.aoBrightnessXYZPPN, var24);
                renderblocks.brightnessBottomRight = renderblocks.getAoBrightness(renderblocks.aoBrightnessXYNP, renderblocks.aoBrightnessXYZNPN, renderblocks.aoBrightnessYZPN, var24);
            }
            else
            {
                var12 = renderblocks.aoLightValueYPos;
                var11 = renderblocks.aoLightValueYPos;
                var10 = renderblocks.aoLightValueYPos;
                var9 = renderblocks.aoLightValueYPos;
                renderblocks.brightnessTopLeft = renderblocks.brightnessBottomLeft = renderblocks.brightnessBottomRight = renderblocks.brightnessTopRight = var24;
            }

            renderblocks.colorRedTopLeft = renderblocks.colorRedBottomLeft = renderblocks.colorRedBottomRight = renderblocks.colorRedTopRight = var14 ? par5 : 1.0F;
            renderblocks.colorGreenTopLeft = renderblocks.colorGreenBottomLeft = renderblocks.colorGreenBottomRight = renderblocks.colorGreenTopRight = var14 ? par6 : 1.0F;
            renderblocks.colorBlueTopLeft = renderblocks.colorBlueBottomLeft = renderblocks.colorBlueBottomRight = renderblocks.colorBlueTopRight = var14 ? par7 : 1.0F;
            renderblocks.colorRedTopLeft *= var9;
            renderblocks.colorGreenTopLeft *= var9;
            renderblocks.colorBlueTopLeft *= var9;
            renderblocks.colorRedBottomLeft *= var10;
            renderblocks.colorGreenBottomLeft *= var10;
            renderblocks.colorBlueBottomLeft *= var10;
            renderblocks.colorRedBottomRight *= var11;
            renderblocks.colorGreenBottomRight *= var11;
            renderblocks.colorBlueBottomRight *= var11;
            renderblocks.colorRedTopRight *= var12;
            renderblocks.colorGreenTopRight *= var12;
            renderblocks.colorBlueTopRight *= var12;
            renderblocks.renderTopFace(block, (double)par2, (double)par3, (double)par4, block.getBlockTexture(renderblocks.blockAccess, par2, par3, par4, 1));
            var8 = true;
        }

        int var27;

        if (renderblocks.renderAllFaces || block.shouldSideBeRendered(renderblocks.blockAccess, par2, par3, par4 - 1, 2))
        {
            if (renderblocks.aoType > 0)
            {
                if (block.minZ <= 0.0D)
                {
                    --par4;
                }

                renderblocks.aoLightValueScratchXZNN = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, par2 - 1, par3, par4);
                renderblocks.aoLightValueScratchYZNN = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, par2, par3 - 1, par4);
                renderblocks.aoLightValueScratchYZPN = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, par2, par3 + 1, par4);
                renderblocks.aoLightValueScratchXZPN = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, par2 + 1, par3, par4);
                renderblocks.aoBrightnessXZNN = block.getMixedBrightnessForBlock(renderblocks.blockAccess, par2 - 1, par3, par4);
                renderblocks.aoBrightnessYZNN = block.getMixedBrightnessForBlock(renderblocks.blockAccess, par2, par3 - 1, par4);
                renderblocks.aoBrightnessYZPN = block.getMixedBrightnessForBlock(renderblocks.blockAccess, par2, par3 + 1, par4);
                renderblocks.aoBrightnessXZPN = block.getMixedBrightnessForBlock(renderblocks.blockAccess, par2 + 1, par3, par4);

                if (!renderblocks.aoGrassXYZNCN && !renderblocks.aoGrassXYZCNN)
                {
                    renderblocks.aoLightValueScratchXYZNNN = renderblocks.aoLightValueScratchXZNN;
                    renderblocks.aoBrightnessXYZNNN = renderblocks.aoBrightnessXZNN;
                }
                else
                {
                    renderblocks.aoLightValueScratchXYZNNN = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, par2 - 1, par3 - 1, par4);
                    renderblocks.aoBrightnessXYZNNN = block.getMixedBrightnessForBlock(renderblocks.blockAccess, par2 - 1, par3 - 1, par4);
                }

                if (!renderblocks.aoGrassXYZNCN && !renderblocks.aoGrassXYZCPN)
                {
                    renderblocks.aoLightValueScratchXYZNPN = renderblocks.aoLightValueScratchXZNN;
                    renderblocks.aoBrightnessXYZNPN = renderblocks.aoBrightnessXZNN;
                }
                else
                {
                    renderblocks.aoLightValueScratchXYZNPN = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, par2 - 1, par3 + 1, par4);
                    renderblocks.aoBrightnessXYZNPN = block.getMixedBrightnessForBlock(renderblocks.blockAccess, par2 - 1, par3 + 1, par4);
                }

                if (!renderblocks.aoGrassXYZPCN && !renderblocks.aoGrassXYZCNN)
                {
                    renderblocks.aoLightValueScratchXYZPNN = renderblocks.aoLightValueScratchXZPN;
                    renderblocks.aoBrightnessXYZPNN = renderblocks.aoBrightnessXZPN;
                }
                else
                {
                    renderblocks.aoLightValueScratchXYZPNN = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, par2 + 1, par3 - 1, par4);
                    renderblocks.aoBrightnessXYZPNN = block.getMixedBrightnessForBlock(renderblocks.blockAccess, par2 + 1, par3 - 1, par4);
                }

                if (!renderblocks.aoGrassXYZPCN && !renderblocks.aoGrassXYZCPN)
                {
                    renderblocks.aoLightValueScratchXYZPPN = renderblocks.aoLightValueScratchXZPN;
                    renderblocks.aoBrightnessXYZPPN = renderblocks.aoBrightnessXZPN;
                }
                else
                {
                    renderblocks.aoLightValueScratchXYZPPN = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, par2 + 1, par3 + 1, par4);
                    renderblocks.aoBrightnessXYZPPN = block.getMixedBrightnessForBlock(renderblocks.blockAccess, par2 + 1, par3 + 1, par4);
                }

                if (block.minZ <= 0.0D)
                {
                    ++par4;
                }

                var9 = (renderblocks.aoLightValueScratchXZNN + renderblocks.aoLightValueScratchXYZNPN + renderblocks.aoLightValueZNeg + renderblocks.aoLightValueScratchYZPN) / 4.0F;
                var10 = (renderblocks.aoLightValueZNeg + renderblocks.aoLightValueScratchYZPN + renderblocks.aoLightValueScratchXZPN + renderblocks.aoLightValueScratchXYZPPN) / 4.0F;
                var11 = (renderblocks.aoLightValueScratchYZNN + renderblocks.aoLightValueZNeg + renderblocks.aoLightValueScratchXYZPNN + renderblocks.aoLightValueScratchXZPN) / 4.0F;
                var12 = (renderblocks.aoLightValueScratchXYZNNN + renderblocks.aoLightValueScratchXZNN + renderblocks.aoLightValueScratchYZNN + renderblocks.aoLightValueZNeg) / 4.0F;
                renderblocks.brightnessTopLeft = renderblocks.getAoBrightness(renderblocks.aoBrightnessXZNN, renderblocks.aoBrightnessXYZNPN, renderblocks.aoBrightnessYZPN, var22);
                renderblocks.brightnessBottomLeft = renderblocks.getAoBrightness(renderblocks.aoBrightnessYZPN, renderblocks.aoBrightnessXZPN, renderblocks.aoBrightnessXYZPPN, var22);
                renderblocks.brightnessBottomRight = renderblocks.getAoBrightness(renderblocks.aoBrightnessYZNN, renderblocks.aoBrightnessXYZPNN, renderblocks.aoBrightnessXZPN, var22);
                renderblocks.brightnessTopRight = renderblocks.getAoBrightness(renderblocks.aoBrightnessXYZNNN, renderblocks.aoBrightnessXZNN, renderblocks.aoBrightnessYZNN, var22);
            }
            else
            {
                var12 = renderblocks.aoLightValueZNeg;
                var11 = renderblocks.aoLightValueZNeg;
                var10 = renderblocks.aoLightValueZNeg;
                var9 = renderblocks.aoLightValueZNeg;
                renderblocks.brightnessTopLeft = renderblocks.brightnessBottomLeft = renderblocks.brightnessBottomRight = renderblocks.brightnessTopRight = var22;
            }

            renderblocks.colorRedTopLeft = renderblocks.colorRedBottomLeft = renderblocks.colorRedBottomRight = renderblocks.colorRedTopRight = (var15 ? par5 : 1.0F) * 0.8F;
            renderblocks.colorGreenTopLeft = renderblocks.colorGreenBottomLeft = renderblocks.colorGreenBottomRight = renderblocks.colorGreenTopRight = (var15 ? par6 : 1.0F) * 0.8F;
            renderblocks.colorBlueTopLeft = renderblocks.colorBlueBottomLeft = renderblocks.colorBlueBottomRight = renderblocks.colorBlueTopRight = (var15 ? par7 : 1.0F) * 0.8F;
            renderblocks.colorRedTopLeft *= var9;
            renderblocks.colorGreenTopLeft *= var9;
            renderblocks.colorBlueTopLeft *= var9;
            renderblocks.colorRedBottomLeft *= var10;
            renderblocks.colorGreenBottomLeft *= var10;
            renderblocks.colorBlueBottomLeft *= var10;
            renderblocks.colorRedBottomRight *= var11;
            renderblocks.colorGreenBottomRight *= var11;
            renderblocks.colorBlueBottomRight *= var11;
            renderblocks.colorRedTopRight *= var12;
            renderblocks.colorGreenTopRight *= var12;
            renderblocks.colorBlueTopRight *= var12;
            var27 = block.getBlockTexture(renderblocks.blockAccess, par2, par3, par4, 2);
            renderblocks.renderEastFace(block, (double)par2, (double)par3, (double)par4, var27);

            if (var27 != 255)
            {
                renderblocks.colorRedTopLeft *= par5;
                renderblocks.colorRedBottomLeft *= par5;
                renderblocks.colorRedBottomRight *= par5;
                renderblocks.colorRedTopRight *= par5;
                renderblocks.colorGreenTopLeft *= par6;
                renderblocks.colorGreenBottomLeft *= par6;
                renderblocks.colorGreenBottomRight *= par6;
                renderblocks.colorGreenTopRight *= par6;
                renderblocks.colorBlueTopLeft *= par7;
                renderblocks.colorBlueBottomLeft *= par7;
                renderblocks.colorBlueBottomRight *= par7;
                renderblocks.colorBlueTopRight *= par7;
                renderblocks.renderEastFace(block, (double)par2, (double)par3, (double)par4, 254);
            }
            else
            {
                if(BGTex == 255)
                {
                    renderblocks.colorRedTopLeft *= par5;
                    renderblocks.colorRedBottomLeft *= par5;
                    renderblocks.colorRedBottomRight *= par5;
                    renderblocks.colorRedTopRight *= par5;
                    renderblocks.colorGreenTopLeft *= par6;
                    renderblocks.colorGreenBottomLeft *= par6;
                    renderblocks.colorGreenBottomRight *= par6;
                    renderblocks.colorGreenTopRight *= par6;
                    renderblocks.colorBlueTopLeft *= par7;
                    renderblocks.colorBlueBottomLeft *= par7;
                    renderblocks.colorBlueBottomRight *= par7;
                    renderblocks.colorBlueTopRight *= par7;
                }
                renderblocks.renderEastFace(block, (double)par2, (double)par3, (double)par4, BGTex);
            }

            var8 = true;
        }

        if (renderblocks.renderAllFaces || block.shouldSideBeRendered(renderblocks.blockAccess, par2, par3, par4 + 1, 3))
        {
            if (renderblocks.aoType > 0)
            {
                if (block.maxZ >= 1.0D)
                {
                    ++par4;
                }

                renderblocks.aoLightValueScratchXZNP = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, par2 - 1, par3, par4);
                renderblocks.aoLightValueScratchXZPP = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, par2 + 1, par3, par4);
                renderblocks.aoLightValueScratchYZNP = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, par2, par3 - 1, par4);
                renderblocks.aoLightValueScratchYZPP = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, par2, par3 + 1, par4);
                renderblocks.aoBrightnessXZNP = block.getMixedBrightnessForBlock(renderblocks.blockAccess, par2 - 1, par3, par4);
                renderblocks.aoBrightnessXZPP = block.getMixedBrightnessForBlock(renderblocks.blockAccess, par2 + 1, par3, par4);
                renderblocks.aoBrightnessYZNP = block.getMixedBrightnessForBlock(renderblocks.blockAccess, par2, par3 - 1, par4);
                renderblocks.aoBrightnessYZPP = block.getMixedBrightnessForBlock(renderblocks.blockAccess, par2, par3 + 1, par4);

                if (!renderblocks.aoGrassXYZNCP && !renderblocks.aoGrassXYZCNP)
                {
                    renderblocks.aoLightValueScratchXYZNNP = renderblocks.aoLightValueScratchXZNP;
                    renderblocks.aoBrightnessXYZNNP = renderblocks.aoBrightnessXZNP;
                }
                else
                {
                    renderblocks.aoLightValueScratchXYZNNP = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, par2 - 1, par3 - 1, par4);
                    renderblocks.aoBrightnessXYZNNP = block.getMixedBrightnessForBlock(renderblocks.blockAccess, par2 - 1, par3 - 1, par4);
                }

                if (!renderblocks.aoGrassXYZNCP && !renderblocks.aoGrassXYZCPP)
                {
                    renderblocks.aoLightValueScratchXYZNPP = renderblocks.aoLightValueScratchXZNP;
                    renderblocks.aoBrightnessXYZNPP = renderblocks.aoBrightnessXZNP;
                }
                else
                {
                    renderblocks.aoLightValueScratchXYZNPP = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, par2 - 1, par3 + 1, par4);
                    renderblocks.aoBrightnessXYZNPP = block.getMixedBrightnessForBlock(renderblocks.blockAccess, par2 - 1, par3 + 1, par4);
                }

                if (!renderblocks.aoGrassXYZPCP && !renderblocks.aoGrassXYZCNP)
                {
                    renderblocks.aoLightValueScratchXYZPNP = renderblocks.aoLightValueScratchXZPP;
                    renderblocks.aoBrightnessXYZPNP = renderblocks.aoBrightnessXZPP;
                }
                else
                {
                    renderblocks.aoLightValueScratchXYZPNP = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, par2 + 1, par3 - 1, par4);
                    renderblocks.aoBrightnessXYZPNP = block.getMixedBrightnessForBlock(renderblocks.blockAccess, par2 + 1, par3 - 1, par4);
                }

                if (!renderblocks.aoGrassXYZPCP && !renderblocks.aoGrassXYZCPP)
                {
                    renderblocks.aoLightValueScratchXYZPPP = renderblocks.aoLightValueScratchXZPP;
                    renderblocks.aoBrightnessXYZPPP = renderblocks.aoBrightnessXZPP;
                }
                else
                {
                    renderblocks.aoLightValueScratchXYZPPP = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, par2 + 1, par3 + 1, par4);
                    renderblocks.aoBrightnessXYZPPP = block.getMixedBrightnessForBlock(renderblocks.blockAccess, par2 + 1, par3 + 1, par4);
                }

                if (block.maxZ >= 1.0D)
                {
                    --par4;
                }

                var9 = (renderblocks.aoLightValueScratchXZNP + renderblocks.aoLightValueScratchXYZNPP + renderblocks.aoLightValueZPos + renderblocks.aoLightValueScratchYZPP) / 4.0F;
                var12 = (renderblocks.aoLightValueZPos + renderblocks.aoLightValueScratchYZPP + renderblocks.aoLightValueScratchXZPP + renderblocks.aoLightValueScratchXYZPPP) / 4.0F;
                var11 = (renderblocks.aoLightValueScratchYZNP + renderblocks.aoLightValueZPos + renderblocks.aoLightValueScratchXYZPNP + renderblocks.aoLightValueScratchXZPP) / 4.0F;
                var10 = (renderblocks.aoLightValueScratchXYZNNP + renderblocks.aoLightValueScratchXZNP + renderblocks.aoLightValueScratchYZNP + renderblocks.aoLightValueZPos) / 4.0F;
                renderblocks.brightnessTopLeft = renderblocks.getAoBrightness(renderblocks.aoBrightnessXZNP, renderblocks.aoBrightnessXYZNPP, renderblocks.aoBrightnessYZPP, var25);
                renderblocks.brightnessTopRight = renderblocks.getAoBrightness(renderblocks.aoBrightnessYZPP, renderblocks.aoBrightnessXZPP, renderblocks.aoBrightnessXYZPPP, var25);
                renderblocks.brightnessBottomRight = renderblocks.getAoBrightness(renderblocks.aoBrightnessYZNP, renderblocks.aoBrightnessXYZPNP, renderblocks.aoBrightnessXZPP, var25);
                renderblocks.brightnessBottomLeft = renderblocks.getAoBrightness(renderblocks.aoBrightnessXYZNNP, renderblocks.aoBrightnessXZNP, renderblocks.aoBrightnessYZNP, var25);
            }
            else
            {
                var12 = renderblocks.aoLightValueZPos;
                var11 = renderblocks.aoLightValueZPos;
                var10 = renderblocks.aoLightValueZPos;
                var9 = renderblocks.aoLightValueZPos;
                renderblocks.brightnessTopLeft = renderblocks.brightnessBottomLeft = renderblocks.brightnessBottomRight = renderblocks.brightnessTopRight = var25;
            }

            renderblocks.colorRedTopLeft = renderblocks.colorRedBottomLeft = renderblocks.colorRedBottomRight = renderblocks.colorRedTopRight = (var16 ? par5 : 1.0F) * 0.8F;
            renderblocks.colorGreenTopLeft = renderblocks.colorGreenBottomLeft = renderblocks.colorGreenBottomRight = renderblocks.colorGreenTopRight = (var16 ? par6 : 1.0F) * 0.8F;
            renderblocks.colorBlueTopLeft = renderblocks.colorBlueBottomLeft = renderblocks.colorBlueBottomRight = renderblocks.colorBlueTopRight = (var16 ? par7 : 1.0F) * 0.8F;
            renderblocks.colorRedTopLeft *= var9;
            renderblocks.colorGreenTopLeft *= var9;
            renderblocks.colorBlueTopLeft *= var9;
            renderblocks.colorRedBottomLeft *= var10;
            renderblocks.colorGreenBottomLeft *= var10;
            renderblocks.colorBlueBottomLeft *= var10;
            renderblocks.colorRedBottomRight *= var11;
            renderblocks.colorGreenBottomRight *= var11;
            renderblocks.colorBlueBottomRight *= var11;
            renderblocks.colorRedTopRight *= var12;
            renderblocks.colorGreenTopRight *= var12;
            renderblocks.colorBlueTopRight *= var12;
            var27 = block.getBlockTexture(renderblocks.blockAccess, par2, par3, par4, 3);
            renderblocks.renderWestFace(block, (double)par2, (double)par3, (double)par4, block.getBlockTexture(renderblocks.blockAccess, par2, par3, par4, 3));

            if (var27 != 255)
            {
                renderblocks.colorRedTopLeft *= par5;
                renderblocks.colorRedBottomLeft *= par5;
                renderblocks.colorRedBottomRight *= par5;
                renderblocks.colorRedTopRight *= par5;
                renderblocks.colorGreenTopLeft *= par6;
                renderblocks.colorGreenBottomLeft *= par6;
                renderblocks.colorGreenBottomRight *= par6;
                renderblocks.colorGreenTopRight *= par6;
                renderblocks.colorBlueTopLeft *= par7;
                renderblocks.colorBlueBottomLeft *= par7;
                renderblocks.colorBlueBottomRight *= par7;
                renderblocks.colorBlueTopRight *= par7;
                renderblocks.renderWestFace(block, (double)par2, (double)par3, (double)par4, 254);
            }
            else
            {
                if(BGTex == 255)
                {
                    renderblocks.colorRedTopLeft *= par5;
                    renderblocks.colorRedBottomLeft *= par5;
                    renderblocks.colorRedBottomRight *= par5;
                    renderblocks.colorRedTopRight *= par5;
                    renderblocks.colorGreenTopLeft *= par6;
                    renderblocks.colorGreenBottomLeft *= par6;
                    renderblocks.colorGreenBottomRight *= par6;
                    renderblocks.colorGreenTopRight *= par6;
                    renderblocks.colorBlueTopLeft *= par7;
                    renderblocks.colorBlueBottomLeft *= par7;
                    renderblocks.colorBlueBottomRight *= par7;
                    renderblocks.colorBlueTopRight *= par7;
                }
                renderblocks.renderWestFace(block, (double)par2, (double)par3, (double)par4, BGTex);
            }

            var8 = true;
        }

        if (renderblocks.renderAllFaces || block.shouldSideBeRendered(renderblocks.blockAccess, par2 - 1, par3, par4, 4))
        {
            if (renderblocks.aoType > 0)
            {
                if (block.minX <= 0.0D)
                {
                    --par2;
                }

                renderblocks.aoLightValueScratchXYNN = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, par2, par3 - 1, par4);
                renderblocks.aoLightValueScratchXZNN = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, par2, par3, par4 - 1);
                renderblocks.aoLightValueScratchXZNP = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, par2, par3, par4 + 1);
                renderblocks.aoLightValueScratchXYNP = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, par2, par3 + 1, par4);
                renderblocks.aoBrightnessXYNN = block.getMixedBrightnessForBlock(renderblocks.blockAccess, par2, par3 - 1, par4);
                renderblocks.aoBrightnessXZNN = block.getMixedBrightnessForBlock(renderblocks.blockAccess, par2, par3, par4 - 1);
                renderblocks.aoBrightnessXZNP = block.getMixedBrightnessForBlock(renderblocks.blockAccess, par2, par3, par4 + 1);
                renderblocks.aoBrightnessXYNP = block.getMixedBrightnessForBlock(renderblocks.blockAccess, par2, par3 + 1, par4);

                if (!renderblocks.aoGrassXYZNCN && !renderblocks.aoGrassXYZNNC)
                {
                    renderblocks.aoLightValueScratchXYZNNN = renderblocks.aoLightValueScratchXZNN;
                    renderblocks.aoBrightnessXYZNNN = renderblocks.aoBrightnessXZNN;
                }
                else
                {
                    renderblocks.aoLightValueScratchXYZNNN = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, par2, par3 - 1, par4 - 1);
                    renderblocks.aoBrightnessXYZNNN = block.getMixedBrightnessForBlock(renderblocks.blockAccess, par2, par3 - 1, par4 - 1);
                }

                if (!renderblocks.aoGrassXYZNCP && !renderblocks.aoGrassXYZNNC)
                {
                    renderblocks.aoLightValueScratchXYZNNP = renderblocks.aoLightValueScratchXZNP;
                    renderblocks.aoBrightnessXYZNNP = renderblocks.aoBrightnessXZNP;
                }
                else
                {
                    renderblocks.aoLightValueScratchXYZNNP = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, par2, par3 - 1, par4 + 1);
                    renderblocks.aoBrightnessXYZNNP = block.getMixedBrightnessForBlock(renderblocks.blockAccess, par2, par3 - 1, par4 + 1);
                }

                if (!renderblocks.aoGrassXYZNCN && !renderblocks.aoGrassXYZNPC)
                {
                    renderblocks.aoLightValueScratchXYZNPN = renderblocks.aoLightValueScratchXZNN;
                    renderblocks.aoBrightnessXYZNPN = renderblocks.aoBrightnessXZNN;
                }
                else
                {
                    renderblocks.aoLightValueScratchXYZNPN = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, par2, par3 + 1, par4 - 1);
                    renderblocks.aoBrightnessXYZNPN = block.getMixedBrightnessForBlock(renderblocks.blockAccess, par2, par3 + 1, par4 - 1);
                }

                if (!renderblocks.aoGrassXYZNCP && !renderblocks.aoGrassXYZNPC)
                {
                    renderblocks.aoLightValueScratchXYZNPP = renderblocks.aoLightValueScratchXZNP;
                    renderblocks.aoBrightnessXYZNPP = renderblocks.aoBrightnessXZNP;
                }
                else
                {
                    renderblocks.aoLightValueScratchXYZNPP = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, par2, par3 + 1, par4 + 1);
                    renderblocks.aoBrightnessXYZNPP = block.getMixedBrightnessForBlock(renderblocks.blockAccess, par2, par3 + 1, par4 + 1);
                }

                if (block.minX <= 0.0D)
                {
                    ++par2;
                }

                var12 = (renderblocks.aoLightValueScratchXYNN + renderblocks.aoLightValueScratchXYZNNP + renderblocks.aoLightValueXNeg + renderblocks.aoLightValueScratchXZNP) / 4.0F;
                var9 = (renderblocks.aoLightValueXNeg + renderblocks.aoLightValueScratchXZNP + renderblocks.aoLightValueScratchXYNP + renderblocks.aoLightValueScratchXYZNPP) / 4.0F;
                var10 = (renderblocks.aoLightValueScratchXZNN + renderblocks.aoLightValueXNeg + renderblocks.aoLightValueScratchXYZNPN + renderblocks.aoLightValueScratchXYNP) / 4.0F;
                var11 = (renderblocks.aoLightValueScratchXYZNNN + renderblocks.aoLightValueScratchXYNN + renderblocks.aoLightValueScratchXZNN + renderblocks.aoLightValueXNeg) / 4.0F;
                renderblocks.brightnessTopRight = renderblocks.getAoBrightness(renderblocks.aoBrightnessXYNN, renderblocks.aoBrightnessXYZNNP, renderblocks.aoBrightnessXZNP, var20);
                renderblocks.brightnessTopLeft = renderblocks.getAoBrightness(renderblocks.aoBrightnessXZNP, renderblocks.aoBrightnessXYNP, renderblocks.aoBrightnessXYZNPP, var20);
                renderblocks.brightnessBottomLeft = renderblocks.getAoBrightness(renderblocks.aoBrightnessXZNN, renderblocks.aoBrightnessXYZNPN, renderblocks.aoBrightnessXYNP, var20);
                renderblocks.brightnessBottomRight = renderblocks.getAoBrightness(renderblocks.aoBrightnessXYZNNN, renderblocks.aoBrightnessXYNN, renderblocks.aoBrightnessXZNN, var20);
            }
            else
            {
                var12 = renderblocks.aoLightValueXNeg;
                var11 = renderblocks.aoLightValueXNeg;
                var10 = renderblocks.aoLightValueXNeg;
                var9 = renderblocks.aoLightValueXNeg;
                renderblocks.brightnessTopLeft = renderblocks.brightnessBottomLeft = renderblocks.brightnessBottomRight = renderblocks.brightnessTopRight = var20;
            }

            renderblocks.colorRedTopLeft = renderblocks.colorRedBottomLeft = renderblocks.colorRedBottomRight = renderblocks.colorRedTopRight = (var17 ? par5 : 1.0F) * 0.6F;
            renderblocks.colorGreenTopLeft = renderblocks.colorGreenBottomLeft = renderblocks.colorGreenBottomRight = renderblocks.colorGreenTopRight = (var17 ? par6 : 1.0F) * 0.6F;
            renderblocks.colorBlueTopLeft = renderblocks.colorBlueBottomLeft = renderblocks.colorBlueBottomRight = renderblocks.colorBlueTopRight = (var17 ? par7 : 1.0F) * 0.6F;
            renderblocks.colorRedTopLeft *= var9;
            renderblocks.colorGreenTopLeft *= var9;
            renderblocks.colorBlueTopLeft *= var9;
            renderblocks.colorRedBottomLeft *= var10;
            renderblocks.colorGreenBottomLeft *= var10;
            renderblocks.colorBlueBottomLeft *= var10;
            renderblocks.colorRedBottomRight *= var11;
            renderblocks.colorGreenBottomRight *= var11;
            renderblocks.colorBlueBottomRight *= var11;
            renderblocks.colorRedTopRight *= var12;
            renderblocks.colorGreenTopRight *= var12;
            renderblocks.colorBlueTopRight *= var12;
            var27 = block.getBlockTexture(renderblocks.blockAccess, par2, par3, par4, 4);
            renderblocks.renderNorthFace(block, (double)par2, (double)par3, (double)par4, var27);

            if (var27 != 255)
            {
                renderblocks.colorRedTopLeft *= par5;
                renderblocks.colorRedBottomLeft *= par5;
                renderblocks.colorRedBottomRight *= par5;
                renderblocks.colorRedTopRight *= par5;
                renderblocks.colorGreenTopLeft *= par6;
                renderblocks.colorGreenBottomLeft *= par6;
                renderblocks.colorGreenBottomRight *= par6;
                renderblocks.colorGreenTopRight *= par6;
                renderblocks.colorBlueTopLeft *= par7;
                renderblocks.colorBlueBottomLeft *= par7;
                renderblocks.colorBlueBottomRight *= par7;
                renderblocks.colorBlueTopRight *= par7;
                renderblocks.renderNorthFace(block, (double)par2, (double)par3, (double)par4, 254);
            }
            else
            {
                if(BGTex == 255)
                {
                    renderblocks.colorRedTopLeft *= par5;
                    renderblocks.colorRedBottomLeft *= par5;
                    renderblocks.colorRedBottomRight *= par5;
                    renderblocks.colorRedTopRight *= par5;
                    renderblocks.colorGreenTopLeft *= par6;
                    renderblocks.colorGreenBottomLeft *= par6;
                    renderblocks.colorGreenBottomRight *= par6;
                    renderblocks.colorGreenTopRight *= par6;
                    renderblocks.colorBlueTopLeft *= par7;
                    renderblocks.colorBlueBottomLeft *= par7;
                    renderblocks.colorBlueBottomRight *= par7;
                    renderblocks.colorBlueTopRight *= par7;
                }
                renderblocks.renderNorthFace(block, (double)par2, (double)par3, (double)par4, BGTex);
            }

            var8 = true;
        }

        if (renderblocks.renderAllFaces || block.shouldSideBeRendered(renderblocks.blockAccess, par2 + 1, par3, par4, 5))
        {
            if (renderblocks.aoType > 0)
            {
                if (block.maxX >= 1.0D)
                {
                    ++par2;
                }

                renderblocks.aoLightValueScratchXYPN = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, par2, par3 - 1, par4);
                renderblocks.aoLightValueScratchXZPN = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, par2, par3, par4 - 1);
                renderblocks.aoLightValueScratchXZPP = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, par2, par3, par4 + 1);
                renderblocks.aoLightValueScratchXYPP = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, par2, par3 + 1, par4);
                renderblocks.aoBrightnessXYPN = block.getMixedBrightnessForBlock(renderblocks.blockAccess, par2, par3 - 1, par4);
                renderblocks.aoBrightnessXZPN = block.getMixedBrightnessForBlock(renderblocks.blockAccess, par2, par3, par4 - 1);
                renderblocks.aoBrightnessXZPP = block.getMixedBrightnessForBlock(renderblocks.blockAccess, par2, par3, par4 + 1);
                renderblocks.aoBrightnessXYPP = block.getMixedBrightnessForBlock(renderblocks.blockAccess, par2, par3 + 1, par4);

                if (!renderblocks.aoGrassXYZPNC && !renderblocks.aoGrassXYZPCN)
                {
                    renderblocks.aoLightValueScratchXYZPNN = renderblocks.aoLightValueScratchXZPN;
                    renderblocks.aoBrightnessXYZPNN = renderblocks.aoBrightnessXZPN;
                }
                else
                {
                    renderblocks.aoLightValueScratchXYZPNN = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, par2, par3 - 1, par4 - 1);
                    renderblocks.aoBrightnessXYZPNN = block.getMixedBrightnessForBlock(renderblocks.blockAccess, par2, par3 - 1, par4 - 1);
                }

                if (!renderblocks.aoGrassXYZPNC && !renderblocks.aoGrassXYZPCP)
                {
                    renderblocks.aoLightValueScratchXYZPNP = renderblocks.aoLightValueScratchXZPP;
                    renderblocks.aoBrightnessXYZPNP = renderblocks.aoBrightnessXZPP;
                }
                else
                {
                    renderblocks.aoLightValueScratchXYZPNP = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, par2, par3 - 1, par4 + 1);
                    renderblocks.aoBrightnessXYZPNP = block.getMixedBrightnessForBlock(renderblocks.blockAccess, par2, par3 - 1, par4 + 1);
                }

                if (!renderblocks.aoGrassXYZPPC && !renderblocks.aoGrassXYZPCN)
                {
                    renderblocks.aoLightValueScratchXYZPPN = renderblocks.aoLightValueScratchXZPN;
                    renderblocks.aoBrightnessXYZPPN = renderblocks.aoBrightnessXZPN;
                }
                else
                {
                    renderblocks.aoLightValueScratchXYZPPN = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, par2, par3 + 1, par4 - 1);
                    renderblocks.aoBrightnessXYZPPN = block.getMixedBrightnessForBlock(renderblocks.blockAccess, par2, par3 + 1, par4 - 1);
                }

                if (!renderblocks.aoGrassXYZPPC && !renderblocks.aoGrassXYZPCP)
                {
                    renderblocks.aoLightValueScratchXYZPPP = renderblocks.aoLightValueScratchXZPP;
                    renderblocks.aoBrightnessXYZPPP = renderblocks.aoBrightnessXZPP;
                }
                else
                {
                    renderblocks.aoLightValueScratchXYZPPP = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, par2, par3 + 1, par4 + 1);
                    renderblocks.aoBrightnessXYZPPP = block.getMixedBrightnessForBlock(renderblocks.blockAccess, par2, par3 + 1, par4 + 1);
                }

                if (block.maxX >= 1.0D)
                {
                    --par2;
                }

                var9 = (renderblocks.aoLightValueScratchXYPN + renderblocks.aoLightValueScratchXYZPNP + renderblocks.aoLightValueXPos + renderblocks.aoLightValueScratchXZPP) / 4.0F;
                var12 = (renderblocks.aoLightValueXPos + renderblocks.aoLightValueScratchXZPP + renderblocks.aoLightValueScratchXYPP + renderblocks.aoLightValueScratchXYZPPP) / 4.0F;
                var11 = (renderblocks.aoLightValueScratchXZPN + renderblocks.aoLightValueXPos + renderblocks.aoLightValueScratchXYZPPN + renderblocks.aoLightValueScratchXYPP) / 4.0F;
                var10 = (renderblocks.aoLightValueScratchXYZPNN + renderblocks.aoLightValueScratchXYPN + renderblocks.aoLightValueScratchXZPN + renderblocks.aoLightValueXPos) / 4.0F;
                renderblocks.brightnessTopLeft = renderblocks.getAoBrightness(renderblocks.aoBrightnessXYPN, renderblocks.aoBrightnessXYZPNP, renderblocks.aoBrightnessXZPP, var23);
                renderblocks.brightnessTopRight = renderblocks.getAoBrightness(renderblocks.aoBrightnessXZPP, renderblocks.aoBrightnessXYPP, renderblocks.aoBrightnessXYZPPP, var23);
                renderblocks.brightnessBottomRight = renderblocks.getAoBrightness(renderblocks.aoBrightnessXZPN, renderblocks.aoBrightnessXYZPPN, renderblocks.aoBrightnessXYPP, var23);
                renderblocks.brightnessBottomLeft = renderblocks.getAoBrightness(renderblocks.aoBrightnessXYZPNN, renderblocks.aoBrightnessXYPN, renderblocks.aoBrightnessXZPN, var23);
            }
            else
            {
                var12 = renderblocks.aoLightValueXPos;
                var11 = renderblocks.aoLightValueXPos;
                var10 = renderblocks.aoLightValueXPos;
                var9 = renderblocks.aoLightValueXPos;
                renderblocks.brightnessTopLeft = renderblocks.brightnessBottomLeft = renderblocks.brightnessBottomRight = renderblocks.brightnessTopRight = var23;
            }

            renderblocks.colorRedTopLeft = renderblocks.colorRedBottomLeft = renderblocks.colorRedBottomRight = renderblocks.colorRedTopRight = (var18 ? par5 : 1.0F) * 0.6F;
            renderblocks.colorGreenTopLeft = renderblocks.colorGreenBottomLeft = renderblocks.colorGreenBottomRight = renderblocks.colorGreenTopRight = (var18 ? par6 : 1.0F) * 0.6F;
            renderblocks.colorBlueTopLeft = renderblocks.colorBlueBottomLeft = renderblocks.colorBlueBottomRight = renderblocks.colorBlueTopRight = (var18 ? par7 : 1.0F) * 0.6F;
            renderblocks.colorRedTopLeft *= var9;
            renderblocks.colorGreenTopLeft *= var9;
            renderblocks.colorBlueTopLeft *= var9;
            renderblocks.colorRedBottomLeft *= var10;
            renderblocks.colorGreenBottomLeft *= var10;
            renderblocks.colorBlueBottomLeft *= var10;
            renderblocks.colorRedBottomRight *= var11;
            renderblocks.colorGreenBottomRight *= var11;
            renderblocks.colorBlueBottomRight *= var11;
            renderblocks.colorRedTopRight *= var12;
            renderblocks.colorGreenTopRight *= var12;
            renderblocks.colorBlueTopRight *= var12;
            var27 = block.getBlockTexture(renderblocks.blockAccess, par2, par3, par4, 5);
            renderblocks.renderSouthFace(block, (double)par2, (double)par3, (double)par4, var27);

            if (var27 != 255)
            {
                renderblocks.colorRedTopLeft *= par5;
                renderblocks.colorRedBottomLeft *= par5;
                renderblocks.colorRedBottomRight *= par5;
                renderblocks.colorRedTopRight *= par5;
                renderblocks.colorGreenTopLeft *= par6;
                renderblocks.colorGreenBottomLeft *= par6;
                renderblocks.colorGreenBottomRight *= par6;
                renderblocks.colorGreenTopRight *= par6;
                renderblocks.colorBlueTopLeft *= par7;
                renderblocks.colorBlueBottomLeft *= par7;
                renderblocks.colorBlueBottomRight *= par7;
                renderblocks.colorBlueTopRight *= par7;
                renderblocks.renderSouthFace(block, (double)par2, (double)par3, (double)par4, 254);
            }
            else
            {
                if(BGTex == 255)
                {
                    renderblocks.colorRedTopLeft *= par5;
                    renderblocks.colorRedBottomLeft *= par5;
                    renderblocks.colorRedBottomRight *= par5;
                    renderblocks.colorRedTopRight *= par5;
                    renderblocks.colorGreenTopLeft *= par6;
                    renderblocks.colorGreenBottomLeft *= par6;
                    renderblocks.colorGreenBottomRight *= par6;
                    renderblocks.colorGreenTopRight *= par6;
                    renderblocks.colorBlueTopLeft *= par7;
                    renderblocks.colorBlueBottomLeft *= par7;
                    renderblocks.colorBlueBottomRight *= par7;
                    renderblocks.colorBlueTopRight *= par7;
                }
                renderblocks.renderSouthFace(block, (double)par2, (double)par3, (double)par4, BGTex);
            }

            var8 = true;
        }

        renderblocks.enableAO = false;
        return var8;
    }

    public static boolean RenderSulfur(Block block, int i, int j, int k, RenderBlocks renderblocks)
    {
        IBlockAccess blockAccess = renderblocks.blockAccess;
        if(blockAccess.isBlockNormalCube(i, j, k+1) && blockAccess.getBlockId(i, j, k+1) != block.blockID)
        {
            block.setBlockBounds(0.0F, 0.0F, 0.99F, 1.0F, 1.0F, 1.0F);
            renderblocks.renderStandardBlock(block, i, j, k);
        }
        if(blockAccess.isBlockNormalCube(i, j, k-1) && blockAccess.getBlockId(i, j, k-1) != block.blockID)
        {
            block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.01F);
            renderblocks.renderStandardBlock(block, i, j, k);
        }
        if(blockAccess.isBlockNormalCube(i+1, j, k) && blockAccess.getBlockId(i+1, j, k) != block.blockID)
        {
            block.setBlockBounds(0.99F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
            renderblocks.renderStandardBlock(block, i, j, k);
        }
        if(blockAccess.isBlockNormalCube(i-1, j, k) && blockAccess.getBlockId(i-1, j, k) != block.blockID)
        {
            block.setBlockBounds(0.0F, 0.0F, 0.0F, 0.01F, 1.0F, 1.0F);
            renderblocks.renderStandardBlock(block, i, j, k);
        }
        if(blockAccess.isBlockNormalCube(i, j+1, k) && blockAccess.getBlockId(i, j+1, k) != block.blockID)
        {
            block.setBlockBounds(0.0F, 0.99F, 0.0F, 1.0F, 1.0F, 1.0F);
            renderblocks.renderStandardBlock(block, i, j, k);
        }
        if(blockAccess.isBlockNormalCube(i, j-1, k) && blockAccess.getBlockId(i, j-1, k) != block.blockID)
        {
            block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.01F, 1.0F);
            renderblocks.renderStandardBlock(block, i, j, k);
        }

        return true;
    }

    public static boolean RenderSnow(Block block, int i, int j, int k, RenderBlocks renderblocks)
    {
        int meta = renderblocks.blockAccess.getBlockMetadata(i, j, k);

        float drift = 0.04F + (meta * 0.06F);

        block.setBlockBounds(0.0F, 0.0F, 0F, 1.0F, drift, 1.0F);
        renderblocks.renderStandardBlock(block, i, j, k);
        return true;
    }

    public static boolean RenderWoodTrunk(Block block, int i, int j, int k, RenderBlocks renderblocks)
    {
        IBlockAccess blockAccess = renderblocks.blockAccess;

        if(true/*blockAccess.getBlockMaterial(i, j+1, k) == Material.leaves || blockAccess.getBlockMaterial(i, j-1, k) == Material.leaves || 
                blockAccess.getBlockId(i, j+1, k) == mod_TFC_Core.fruitTreeWood.blockID || blockAccess.getBlockId(i, j-1, k) == mod_TFC_Core.fruitTreeWood.blockID*/)
        {
            if(blockAccess.getBlockTileEntity(i, j, k) != null && (blockAccess.getBlockId(i, j-1, k) == mod_TFC.fruitTreeWood.blockID || blockAccess.isBlockOpaqueCube(i, j-1, k)))
            {
                block.setBlockBounds(0.3F, 0.0F, 0.3F, 0.7F, 1.0F, 0.7F);
                renderblocks.renderStandardBlock(block, i, j, k);
            }
            if(blockAccess.getBlockMaterial(i-1, j, k) == Material.leaves || blockAccess.getBlockId(i-1, j, k) == mod_TFC.fruitTreeWood.blockID)
            {
                block.setBlockBounds(0.0F, 0.4F, 0.4F, 0.5F, 0.6F, 0.6F);
                renderblocks.renderStandardBlock(block, i, j, k);
            }
            if(blockAccess.getBlockMaterial(i+1, j, k) == Material.leaves || blockAccess.getBlockId(i+1, j, k) == mod_TFC.fruitTreeWood.blockID)
            {
                block.setBlockBounds(0.5F, 0.4F, 0.4F, 1.0F, 0.6F, 0.6F);
                renderblocks.renderStandardBlock(block, i, j, k);
            }
            if(blockAccess.getBlockMaterial(i, j, k-1) == Material.leaves || blockAccess.getBlockId(i, j, k-1) == mod_TFC.fruitTreeWood.blockID)
            {
                block.setBlockBounds(0.4F, 0.4F, 0.0F, 0.6F, 0.6F, 0.5F);
                renderblocks.renderStandardBlock(block, i, j, k);
            }
            if(blockAccess.getBlockMaterial(i, j, k+1) == Material.leaves || blockAccess.getBlockId(i, j, k+1) == mod_TFC.fruitTreeWood.blockID)
            {
                block.setBlockBounds(0.4F, 0.4F, 0.5F, 0.6F, 0.6F, 1.0F);
                renderblocks.renderStandardBlock(block, i, j, k);
            }
        }

        if(!((TileEntityFruitTreeWood)blockAccess.getBlockTileEntity(i, j, k)).isTrunk && blockAccess.getBlockId(i, j-1, k) != mod_TFC.fruitTreeWood.blockID && !blockAccess.isBlockOpaqueCube(i, j-1, k))
        {

            block.setBlockBounds(0.0F, 0.4F, 0.4F, 0.5F, 0.6F, 0.6F);
            renderblocks.renderStandardBlock(block, i, j, k);

            block.setBlockBounds(0.5F, 0.4F, 0.4F, 1.0F, 0.6F, 0.6F);
            renderblocks.renderStandardBlock(block, i, j, k);

            block.setBlockBounds(0.4F, 0.4F, 0.0F, 0.6F, 0.6F, 0.5F);
            renderblocks.renderStandardBlock(block, i, j, k);

            block.setBlockBounds(0.4F, 0.4F, 0.5F, 0.6F, 0.6F, 1.0F);
            renderblocks.renderStandardBlock(block, i, j, k);

        }

        block.setBlockBounds(0.0F, 0.0F, 0.0F, 1F, 1F, 1F);
        return true;
    }

    public static boolean RenderLooseRock(Block block, int i, int j, int k, RenderBlocks renderblocks)
    {
        BiomeGenBase biome = renderblocks.blockAccess.getBiomeGenForCoords(i, k);

        int meta = renderblocks.blockAccess.getBlockMetadata(i, j, k);
        Random R = new Random(meta+i*k);

        renderblocks.overrideBlockTexture = getRockTexture(j,(TFCBiome)biome);

        block.setBlockBounds(0.40F, 0.00F, 0.4F, 0.6F, 0.10F, 0.7F);
        renderblocks.renderStandardBlock(block, i, j, k);
        block.setBlockBounds(0.40F, 0.00F, 0.4F, 0.6F, 0.10F, 0.7F);


        renderblocks.overrideBlockTexture = -1;
        return false;
    }

    public static boolean RenderWoodSupportBeamH(Block block, int i, int j, int k, RenderBlocks renderblocks)
    {
        IBlockAccess blockAccess = renderblocks.blockAccess;

        int supportIDv = mod_TFC.terraWoodSupportV.blockID;
        int supportIDh = mod_TFC.terraWoodSupportH.blockID;

        Boolean hasVerticalBeam = false;
        Boolean hasHorizontalBeamX = false;
        Boolean hasHorizontalBeamZ = false;

        //if the block directly beneath is a Vertical Support
        if((blockAccess.getBlockId(i, j-1, k) == supportIDv))
        {	
            block.setBlockBounds(0.25F, 0.0F, 0.25F, 0.75F, 1.0F, 0.75F);
            renderblocks.renderStandardBlock(block, i, j, k);
            hasVerticalBeam = true;
        }

        //X
        if(/*TFC_Core.isBlockAboveSolid(blockAccess, i, j, k) &&*/ (blockAccess.getBlockId(i-1, j, k) == supportIDv || blockAccess.getBlockId(i-1, j, k) == supportIDh))//if the block above is solid and the block at -x is a support beam
        {
            if((blockAccess.getBlockId(i+1, j, k) == supportIDv || blockAccess.getBlockId(i+1, j, k) == supportIDh))//if the block above is solid and the block at +x is a support beam
            {
                if(hasVerticalBeam)//if the block does contain a vertical beam
                {
                    block.setBlockBounds(0.0F, 0.50F, 0.25F, 0.25F, 1.0F, 0.75F);
                    renderblocks.renderStandardBlock(block, i, j, k);
                    block.setBlockBounds(0.75F, 0.50F, 0.25F, 1.0F, 1.0F, 0.75F);
                    renderblocks.renderStandardBlock(block, i, j, k);
                }
                else if(!hasVerticalBeam)//if the block does not contain a vertical beam
                {
                    block.setBlockBounds(0.0F, 0.50F, 0.25F, 1.0F, 1.0F, 0.75F);
                    renderblocks.renderStandardBlock(block, i, j, k);
                }
                hasHorizontalBeamX = true;
            }
            else//if there is only a beam at the negative x and not the positive x
            {
                if(hasVerticalBeam)//if the block does contain a vertical beam
                {
                    block.setBlockBounds(0.0F, 0.50F, 0.25F, 0.25F, 1.0F, 0.75F);
                    renderblocks.renderStandardBlock(block, i, j, k);
                }
                else
                {
                    block.setBlockBounds(0.0F, 0.50F, 0.25F, 0.75F, 1.0F, 0.75F);// 3/4 block
                    renderblocks.renderStandardBlock(block, i, j, k);
                }
                hasHorizontalBeamX = true;
            }
        }
        else if(/*TFC_Core.isBlockAboveSolid(blockAccess, i, j, k) &&*/ (blockAccess.getBlockId(i+1, j, k) == supportIDv || blockAccess.getBlockId(i+1, j, k) == supportIDh))
        {
            if(hasVerticalBeam)//if the block does contain a vertical beam
            {
                block.setBlockBounds(0.75F, 0.50F, 0.25F, 1.0F, 1.0F, 0.75F);
                renderblocks.renderStandardBlock(block, i, j, k);
            }
            else
            {
                block.setBlockBounds(0.25F, 0.50F, 0.25F, 1.0F, 1.0F, 0.75F);// 3/4 block
                renderblocks.renderStandardBlock(block, i, j, k);
            }
            hasHorizontalBeamX = true;
        }
        //Z
        if(/*TFC_Core.isBlockAboveSolid(blockAccess, i, j, k) &&*/ (blockAccess.getBlockId(i, j, k-1) == supportIDv || blockAccess.getBlockId(i, j, k-1) == supportIDh))
        {
            if((blockAccess.getBlockId(i, j, k+1) == supportIDv || blockAccess.getBlockId(i, j, k+1) == supportIDh))//if the block above is solid and the block at +x is a support beam
            {
                if(hasVerticalBeam)//if the block does contain a vertical beam
                {
                    block.setBlockBounds(0.25F, 0.50F, 0.0F, 0.75F, 1.0F, 0.25F);
                    renderblocks.renderStandardBlock(block, i, j, k);
                    block.setBlockBounds(0.25F, 0.50F, 0.75F, 0.75F, 1.0F, 1.0F);
                    renderblocks.renderStandardBlock(block, i, j, k);
                }
                else if(!hasVerticalBeam)//if the block does not contain a vertical beam
                {
                    block.setBlockBounds(0.25F, 0.50F, 0.0F, 0.75F, 1.0F, 1.0F);
                    renderblocks.renderStandardBlock(block, i, j, k);
                }
                hasHorizontalBeamZ = true;
            }
            else//if there is only a beam at the negative x and not the positive x
            {
                if(hasVerticalBeam)//if the block does contain a vertical beam
                {
                    block.setBlockBounds(0.25F, 0.50F, 0.0F, 0.75F, 1.0F, 0.25F);
                    renderblocks.renderStandardBlock(block, i, j, k);
                }
                else
                {
                    block.setBlockBounds(0.25F, 0.50F, 0.0F, 0.75F, 1.0F, 0.75F);// 3/4 block
                    renderblocks.renderStandardBlock(block, i, j, k);
                }
                hasHorizontalBeamZ = true;
            }
        }
        else if(/*TFC_Core.isBlockAboveSolid(blockAccess, i, j, k) && */(blockAccess.getBlockId(i, j, k+1) == supportIDv || blockAccess.getBlockId(i, j, k+1) == supportIDh))//Top is solid and positive Z is support
        {
            if(hasVerticalBeam)//if the block does contain a vertical beam
            {
                block.setBlockBounds(0.25F, 0.50F, 0.75F, 0.75F, 1.0F, 1.0F);
                renderblocks.renderStandardBlock(block, i, j, k);
            }
            else
            {
                block.setBlockBounds(0.25F, 0.50F, 0.25F, 0.75F, 1.0F, 1.0F);
                renderblocks.renderStandardBlock(block, i, j, k);
            }
            hasHorizontalBeamZ = true;
        }

        float minX = -1;
        float minY = -1;
        float minZ = -1;

        float maxX = -1;
        float maxY = -1;
        float maxZ = -1;

        if(hasHorizontalBeamX)
        {
            minX = 0F;
            maxX = 1F;
            minZ = 0.25F;
            maxZ = 0.75F;
        }
        if(hasHorizontalBeamZ)
        {
            if(maxX == -1)
            {
                minX = 0.25F;
                maxX = 0.75F;
            }

            minZ = 0F;
            maxZ = 1F;

        }
        if(hasVerticalBeam)
        {
            minY = 0F;
            maxY = 1F;
            if(maxX == -1)
            {
                minX = 0.25F;
                maxX = 0.75F;
            }
            if(maxZ == -1)
            {
                minZ = 0.25F;
                maxZ = 0.75F;
            }
        }
        else
        {
            minY = 0.5F;
            maxY = 1F;
        }

        block.setBlockBounds(minX,minY, minZ, maxX, maxY, maxZ);

        return true;
    }

    public static boolean RenderWoodSupportBeamV(Block block, int i, int j, int k, RenderBlocks renderblocks)
    {
        IBlockAccess blockAccess = renderblocks.blockAccess;

        int supportIDv = mod_TFC.terraWoodSupportV.blockID;
        int supportIDh = mod_TFC.terraWoodSupportH.blockID;

        Boolean hasVerticalBeam = false;
        Boolean hasHorizontalBeamX = false;
        Boolean hasHorizontalBeamZ = false;

        //if the block directly beneath is a Vertical Support or a solid block
        if((blockAccess.isBlockOpaqueCube(i, j-1, k) || blockAccess.getBlockId(i, j-1, k) == supportIDv) && block.blockID == mod_TFC.terraWoodSupportV.blockID)
        {	
            block.setBlockBounds(0.25F, 0.0F, 0.25F, 0.75F, 1.0F, 0.75F);
            renderblocks.renderStandardBlock(block, i, j, k);
            hasVerticalBeam = true;
        }

        //X
        if(/*TFC_Core.isBlockAboveSolid(blockAccess, i, j, k) &&*/ (blockAccess.getBlockId(i-1, j, k) == supportIDv || blockAccess.getBlockId(i-1, j, k) == supportIDh))//if the block above is solid and the block at -x is a support beam
        {
            if((blockAccess.getBlockId(i+1, j, k) == supportIDv || blockAccess.getBlockId(i+1, j, k) == supportIDh))//if the block above is solid and the block at +x is a support beam
            {
                if(hasVerticalBeam)//if the block does contain a vertical beam
                {
                    block.setBlockBounds(0.0F, 0.50F, 0.25F, 0.25F, 1.0F, 0.75F);
                    renderblocks.renderStandardBlock(block, i, j, k);
                    block.setBlockBounds(0.75F, 0.50F, 0.25F, 1.0F, 1.0F, 0.75F);
                    renderblocks.renderStandardBlock(block, i, j, k);
                }
                else if(!hasVerticalBeam)//if the block does not contain a vertical beam
                {
                    block.setBlockBounds(0.0F, 0.50F, 0.25F, 1.0F, 1.0F, 0.75F);
                    renderblocks.renderStandardBlock(block, i, j, k);
                }
                hasHorizontalBeamX = true;
            }
            else//if there is only a beam at the negative x and not the positive x
            {
                if(hasVerticalBeam)//if the block does contain a vertical beam
                {
                    block.setBlockBounds(0.0F, 0.50F, 0.25F, 0.25F, 1.0F, 0.75F);
                    renderblocks.renderStandardBlock(block, i, j, k);
                }
                else
                {
                    block.setBlockBounds(0.0F, 0.50F, 0.25F, 0.75F, 1.0F, 0.75F);// 3/4 block
                    renderblocks.renderStandardBlock(block, i, j, k);
                }
                hasHorizontalBeamX = true;
            }
        }
        else if(/*TFC_Core.isBlockAboveSolid(blockAccess, i, j, k) && */(blockAccess.getBlockId(i+1, j, k) == supportIDv || blockAccess.getBlockId(i+1, j, k) == supportIDh))
        {
            if(hasVerticalBeam)//if the block does contain a vertical beam
            {
                block.setBlockBounds(0.75F, 0.50F, 0.25F, 1.0F, 1.0F, 0.75F);
                renderblocks.renderStandardBlock(block, i, j, k);
            }
            else
            {
                block.setBlockBounds(0.25F, 0.50F, 0.25F, 1.0F, 1.0F, 0.75F);// 3/4 block
                renderblocks.renderStandardBlock(block, i, j, k);
            }
            hasHorizontalBeamX = true;
        }
        //Z
        if(/*TFC_Core.isBlockAboveSolid(blockAccess, i, j, k) && */(blockAccess.getBlockId(i, j, k-1) == supportIDv || blockAccess.getBlockId(i, j, k-1) == supportIDh))
        {
            if((blockAccess.getBlockId(i, j, k+1) == supportIDv || blockAccess.getBlockId(i, j, k+1) == supportIDh))//if the block above is solid and the block at +x is a support beam
            {
                if(hasVerticalBeam)//if the block does contain a vertical beam
                {
                    block.setBlockBounds(0.25F, 0.50F, 0.0F, 0.75F, 1.0F, 0.25F);
                    renderblocks.renderStandardBlock(block, i, j, k);
                    block.setBlockBounds(0.25F, 0.50F, 0.75F, 0.75F, 1.0F, 1.0F);
                    renderblocks.renderStandardBlock(block, i, j, k);
                }
                else if(!hasVerticalBeam)//if the block does not contain a vertical beam
                {
                    block.setBlockBounds(0.25F, 0.50F, 0.0F, 0.75F, 1.0F, 1.0F);
                    renderblocks.renderStandardBlock(block, i, j, k);
                }
                hasHorizontalBeamZ = true;
            }
            else//if there is only a beam at the negative x and not the positive x
            {
                if(hasVerticalBeam)//if the block does contain a vertical beam
                {
                    block.setBlockBounds(0.25F, 0.50F, 0.0F, 0.75F, 1.0F, 0.25F);
                    renderblocks.renderStandardBlock(block, i, j, k);
                }
                else
                {
                    block.setBlockBounds(0.25F, 0.50F, 0.0F, 0.75F, 1.0F, 0.75F);// 3/4 block
                    renderblocks.renderStandardBlock(block, i, j, k);
                }
                hasHorizontalBeamZ = true;
            }
        }
        else if(/*TFC_Core.isBlockAboveSolid(blockAccess, i, j, k) && */(blockAccess.getBlockId(i, j, k+1) == supportIDv || blockAccess.getBlockId(i, j, k+1) == supportIDh))//Top is solid and positive Z is support
        {
            if(hasVerticalBeam)//if the block does contain a vertical beam
            {
                block.setBlockBounds(0.25F, 0.50F, 0.75F, 0.75F, 1.0F, 1.0F);
                renderblocks.renderStandardBlock(block, i, j, k);
            }
            else
            {
                block.setBlockBounds(0.25F, 0.50F, 0.25F, 0.75F, 1.0F, 1.0F);
                renderblocks.renderStandardBlock(block, i, j, k);
            }
            hasHorizontalBeamZ = true;
        }

        float minX = 0.25F;
        float minY = 0;
        float minZ = 0.25F;

        float maxX = 0.75F;
        float maxY = 1;
        float maxZ = 0.75F;


        block.setBlockBounds(minX,minY, minZ, maxX, maxY, maxZ);

        return true;
    }

    public static void renderBlockFallingSand(Block block,int meta, World world, int i, int j, int k)
    {
        float f = 0.5F;
        float f1 = 1.0F;
        float f2 = 0.8F;
        float f3 = 0.6F;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.setBrightness(block.getMixedBrightnessForBlock(world, i, j, k));
        float f4 = 1.0F;
        float f5 = 1.0F;
        if (f5 < f4)
        {
            f5 = f4;
        }
        tessellator.setColorOpaque_F(f * f5, f * f5, f * f5);
        renderBottomFace(block, -0.5D, -0.5D, -0.5D, block.getBlockTextureFromSideAndMetadata(0, meta));
        f5 = 1.0F;
        if (f5 < f4)
        {
            f5 = f4;
        }
        tessellator.setColorOpaque_F(f1 * f5, f1 * f5, f1 * f5);
        renderTopFace(block, -0.5D, -0.5D, -0.5D, block.getBlockTextureFromSideAndMetadata(1, meta));
        f5 = 1.0F;
        if (f5 < f4)
        {
            f5 = f4;
        }
        tessellator.setColorOpaque_F(f2 * f5, f2 * f5, f2 * f5);
        renderEastFace(block, -0.5D, -0.5D, -0.5D, block.getBlockTextureFromSideAndMetadata(2, meta));
        f5 = 1.0F;
        if (f5 < f4)
        {
            f5 = f4;
        }
        tessellator.setColorOpaque_F(f2 * f5, f2 * f5, f2 * f5);
        renderWestFace(block, -0.5D, -0.5D, -0.5D, block.getBlockTextureFromSideAndMetadata(3, meta));
        f5 = 1.0F;
        if (f5 < f4)
        {
            f5 = f4;
        }
        tessellator.setColorOpaque_F(f3 * f5, f3 * f5, f3 * f5);
        renderNorthFace(block, -0.5D, -0.5D, -0.5D, block.getBlockTextureFromSideAndMetadata(4, meta));
        f5 = 1.0F;
        if (f5 < f4)
        {
            f5 = f4;
        }
        tessellator.setColorOpaque_F(f3 * f5, f3 * f5, f3 * f5);
        renderSouthFace(block, -0.5D, -0.5D, -0.5D, block.getBlockTextureFromSideAndMetadata(5, meta));
        tessellator.draw();
    }

    public static void renderBottomFace(Block block, double d, double d1, double d2,
            int i)
    {
        Tessellator tessellator = Tessellator.instance;

        int j = (i & 0xf) << 4;
        int k = i & 0xf0;
        double d3 = ((double)j + block.minX * 16D) / 256D;
        double d4 = (((double)j + block.maxX * 16D) - 0.01D) / 256D;
        double d5 = ((double)k + block.minZ * 16D) / 256D;
        double d6 = (((double)k + block.maxZ * 16D) - 0.01D) / 256D;
        if (block.minX < 0.0D || block.maxX > 1.0D)
        {
            d3 = ((float)j + 0.0F) / 256F;
            d4 = ((float)j + 15.99F) / 256F;
        }
        if (block.minZ < 0.0D || block.maxZ > 1.0D)
        {
            d5 = ((float)k + 0.0F) / 256F;
            d6 = ((float)k + 15.99F) / 256F;
        }
        double d7 = d4;
        double d8 = d3;
        double d9 = d5;
        double d10 = d6;

        double d11 = d + block.minX;
        double d12 = d + block.maxX;
        double d13 = d1 + block.minY;
        double d14 = d2 + block.minZ;
        double d15 = d2 + block.maxZ;

        tessellator.addVertexWithUV(d11, d13, d15, d8, d10);
        tessellator.addVertexWithUV(d11, d13, d14, d3, d5);
        tessellator.addVertexWithUV(d12, d13, d14, d7, d9);
        tessellator.addVertexWithUV(d12, d13, d15, d4, d6);

    }

    public static void renderTopFace(Block block, double d, double d1, double d2,
            int i)
    {
        Tessellator tessellator = Tessellator.instance;

        int j = (i & 0xf) << 4;
        int k = i & 0xf0;
        double d3 = ((double)j + block.minX * 16D) / 256D;
        double d4 = (((double)j + block.maxX * 16D) - 0.01D) / 256D;
        double d5 = ((double)k + block.minZ * 16D) / 256D;
        double d6 = (((double)k + block.maxZ * 16D) - 0.01D) / 256D;
        if (block.minX < 0.0D || block.maxX > 1.0D)
        {
            d3 = ((float)j + 0.0F) / 256F;
            d4 = ((float)j + 15.99F) / 256F;
        }
        if (block.minZ < 0.0D || block.maxZ > 1.0D)
        {
            d5 = ((float)k + 0.0F) / 256F;
            d6 = ((float)k + 15.99F) / 256F;
        }
        double d7 = d4;
        double d8 = d3;
        double d9 = d5;
        double d10 = d6;

        double d11 = d + block.minX;
        double d12 = d + block.maxX;
        double d13 = d1 + block.maxY;
        double d14 = d2 + block.minZ;
        double d15 = d2 + block.maxZ;

        tessellator.addVertexWithUV(d12, d13, d15, d4, d6);
        tessellator.addVertexWithUV(d12, d13, d14, d7, d9);
        tessellator.addVertexWithUV(d11, d13, d14, d3, d5);
        tessellator.addVertexWithUV(d11, d13, d15, d8, d10);

    }

    public static void renderEastFace(Block block, double d, double d1, double d2,
            int i)
    {
        Tessellator tessellator = Tessellator.instance;

        int j = (i & 0xf) << 4;
        int k = i & 0xf0;
        double d3 = ((double)j + block.minX * 16D) / 256D;
        double d4 = (((double)j + block.maxX * 16D) - 0.01D) / 256D;
        double d5 = ((double)(k + 16) - block.maxY * 16D) / 256D;
        double d6 = ((double)(k + 16) - block.minY * 16D - 0.01D) / 256D;

        if (block.minX < 0.0D || block.maxX > 1.0D)
        {
            d3 = ((float)j + 0.0F) / 256F;
            d4 = ((float)j + 15.99F) / 256F;
        }
        if (block.minY < 0.0D || block.maxY > 1.0D)
        {
            d5 = ((float)k + 0.0F) / 256F;
            d6 = ((float)k + 15.99F) / 256F;
        }
        double d8 = d4;
        double d9 = d3;
        double d10 = d5;
        double d11 = d6;

        double d12 = d + block.minX;
        double d13 = d + block.maxX;
        double d14 = d1 + block.minY;
        double d15 = d1 + block.maxY;
        double d16 = d2 + block.minZ;

        tessellator.addVertexWithUV(d12, d15, d16, d8, d10);
        tessellator.addVertexWithUV(d13, d15, d16, d3, d5);
        tessellator.addVertexWithUV(d13, d14, d16, d9, d11);
        tessellator.addVertexWithUV(d12, d14, d16, d4, d6);

    }

    public static void renderWestFace(Block block, double d, double d1, double d2,
            int i)
    {
        Tessellator tessellator = Tessellator.instance;

        int j = (i & 0xf) << 4;
        int k = i & 0xf0;
        double d3 = ((double)j + block.minX * 16D) / 256D;
        double d4 = (((double)j + block.maxX * 16D) - 0.01D) / 256D;
        double d5 = ((double)(k + 16) - block.maxY * 16D) / 256D;
        double d6 = ((double)(k + 16) - block.minY * 16D - 0.01D) / 256D;

        if (block.minX < 0.0D || block.maxX > 1.0D)
        {
            d3 = ((float)j + 0.0F) / 256F;
            d4 = ((float)j + 15.99F) / 256F;
        }
        if (block.minY < 0.0D || block.maxY > 1.0D)
        {
            d5 = ((float)k + 0.0F) / 256F;
            d6 = ((float)k + 15.99F) / 256F;
        }
        double d8 = d4;
        double d9 = d3;
        double d10 = d5;
        double d11 = d6;

        double d12 = d + block.minX;
        double d13 = d + block.maxX;
        double d14 = d1 + block.minY;
        double d15 = d1 + block.maxY;
        double d16 = d2 + block.maxZ;

        tessellator.addVertexWithUV(d12, d15, d16, d3, d5);
        tessellator.addVertexWithUV(d12, d14, d16, d9, d11);
        tessellator.addVertexWithUV(d13, d14, d16, d4, d6);
        tessellator.addVertexWithUV(d13, d15, d16, d8, d10);

    }

    public static void renderNorthFace(Block block, double d, double d1, double d2,
            int i)
    {
        Tessellator tessellator = Tessellator.instance;

        int j = (i & 0xf) << 4;
        int k = i & 0xf0;
        double d3 = ((double)j + block.minZ * 16D) / 256D;
        double d4 = (((double)j + block.maxZ * 16D) - 0.01D) / 256D;
        double d5 = ((double)(k + 16) - block.maxY * 16D) / 256D;
        double d6 = ((double)(k + 16) - block.minY * 16D - 0.01D) / 256D;

        if (block.minZ < 0.0D || block.maxZ > 1.0D)
        {
            d3 = ((float)j + 0.0F) / 256F;
            d4 = ((float)j + 15.99F) / 256F;
        }
        if (block.minY < 0.0D || block.maxY > 1.0D)
        {
            d5 = ((float)k + 0.0F) / 256F;
            d6 = ((float)k + 15.99F) / 256F;
        }
        double d8 = d4;
        double d9 = d3;
        double d10 = d5;
        double d11 = d6;

        double d12 = d + block.minX;
        double d13 = d1 + block.minY;
        double d14 = d1 + block.maxY;
        double d15 = d2 + block.minZ;
        double d16 = d2 + block.maxZ;

        tessellator.addVertexWithUV(d12, d14, d16, d8, d10);
        tessellator.addVertexWithUV(d12, d14, d15, d3, d5);
        tessellator.addVertexWithUV(d12, d13, d15, d9, d11);
        tessellator.addVertexWithUV(d12, d13, d16, d4, d6);

    }

    public static void renderSouthFace(Block block, double d, double d1, double d2,
            int i)
    {
        Tessellator tessellator = Tessellator.instance;

        int j = (i & 0xf) << 4;
        int k = i & 0xf0;
        double d3 = ((double)j + block.minZ * 16D) / 256D;
        double d4 = (((double)j + block.maxZ * 16D) - 0.01D) / 256D;
        double d5 = ((double)(k + 16) - block.maxY * 16D) / 256D;
        double d6 = ((double)(k + 16) - block.minY * 16D - 0.01D) / 256D;

        if (block.minZ < 0.0D || block.maxZ > 1.0D)
        {
            d3 = ((float)j + 0.0F) / 256F;
            d4 = ((float)j + 15.99F) / 256F;
        }
        if (block.minY < 0.0D || block.maxY > 1.0D)
        {
            d5 = ((float)k + 0.0F) / 256F;
            d6 = ((float)k + 15.99F) / 256F;
        }
        double d8 = d4;
        double d9 = d3;
        double d10 = d5;
        double d11 = d6;

        double d12 = d + block.maxX;
        double d13 = d1 + block.minY;
        double d14 = d1 + block.maxY;
        double d15 = d2 + block.minZ;
        double d16 = d2 + block.maxZ;

        tessellator.addVertexWithUV(d12, d13, d16, d9, d11);
        tessellator.addVertexWithUV(d12, d13, d15, d4, d6);
        tessellator.addVertexWithUV(d12, d14, d15, d8, d10);
        tessellator.addVertexWithUV(d12, d14, d16, d3, d5);

    }

    public static boolean RenderOre(Block block, int xCoord, int yCoord, int zCoord,float par5, float par6, float par7, RenderBlocks renderblocks, IBlockAccess iblockaccess)
    {
        BiomeGenBase biome = iblockaccess.getBiomeGenForCoords(xCoord, zCoord);

        int over = renderblocks.overrideBlockTexture;

        if(over == -1)
        {
            renderblocks.overrideBlockTexture = getRockTexture(yCoord,(TFCBiome) biome);
            renderblocks.renderStandardBlock(block, xCoord, yCoord, zCoord);
            renderblocks.overrideBlockTexture = over;

            renderblocks.renderStandardBlock(block, xCoord, yCoord, zCoord);

            renderblocks.overrideBlockTexture = over;
        }

        renderblocks.renderStandardBlock(block, xCoord, yCoord, zCoord);

        return true;
    }

    public static int getRockTexture(int yCoord, TFCBiome biome) {
        int var27;
        if(yCoord <= biome.Layer3)
            var27 = Block.blocksList[biome.Layer3Type].getBlockTextureFromSideAndMetadata(5, biome.Layer3Meta);
        else if(yCoord > biome.Layer3 && yCoord <= biome.Layer2)
            var27 = Block.blocksList[biome.Layer2Type].getBlockTextureFromSideAndMetadata(5, biome.Layer2Meta);
        else if(yCoord > biome.Layer2 && yCoord <= biome.Layer1)
            var27 = Block.blocksList[biome.Layer1Type].getBlockTextureFromSideAndMetadata(5, biome.Layer1Meta);
        else
            var27 = Block.blocksList[biome.SurfaceType].getBlockTextureFromSideAndMetadata(5, biome.SurfaceMeta);
        return var27;
    }

    public static boolean RenderMolten(Block block, int i, int j, int k, RenderBlocks renderblocks)
    {
        block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        renderblocks.renderStandardBlock(block, i, j, k);

        //block.setBlockBounds(0.0F, 0.0F, 0.0F, 0.001F, 0.001F, 0.001F);
        return true;
    }

    public static boolean renderFirepit(Block block, int i, int j, int k, RenderBlocks renderblocks)
    {
        IBlockAccess blockAccess = renderblocks.blockAccess;

        block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.02F, 1.0F);
        renderblocks.renderStandardBlock(block, i, j, k);

        block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.02F, 1.0F);
        return true;
    }

    public static boolean renderForge(Block block, int i, int j, int k, RenderBlocks renderblocks)
    {
        IBlockAccess blockAccess = renderblocks.blockAccess;

        block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.9F, 1.0F);
        renderblocks.renderStandardBlock(block, i, j, k);


        block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.9F, 1.0F);
        return true;
    }

    public static boolean renderBellows(Block block, int i, int j, int k, RenderBlocks renderblocks)
    {
        IBlockAccess blockAccess = renderblocks.blockAccess;

        int meta = blockAccess.getBlockMetadata(i, j, k);
        int direction = ((BlockTerraBellows)block).getDirectionFromMetadata(meta);

        if(direction == 0)
        {
            //forward
            renderblocks.overrideBlockTexture = 86;
            block.setBlockBounds(0.0F, 0.0F, 0.9F, 1.0F, 1.0F, 1.0F);
            renderblocks.renderStandardBlock(block, i, j, k);
            //mid
            renderblocks.overrideBlockTexture = -1;
            block.setBlockBounds(0.1F, 0.1F, 0.05F, 0.9F, 0.9F, 0.95F);
            renderblocks.renderStandardBlock(block, i, j, k);
            //back
            renderblocks.overrideBlockTexture = 87;
            block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.1F);
            renderblocks.renderStandardBlock(block, i, j, k);
            renderblocks.overrideBlockTexture = -1;
        }
        else if(direction == 1)
        {
            //forward
            renderblocks.overrideBlockTexture = 86;
            block.setBlockBounds(0.0F, 0.0F, 0.0F, 0.1F, 1.0F, 1.0F);
            renderblocks.renderStandardBlock(block, i, j, k);

            //mid
            renderblocks.overrideBlockTexture = -1;
            block.setBlockBounds(0.1F, 0.1F, 0.1F, 0.9F, 0.9F, 0.9F);
            renderblocks.renderStandardBlock(block, i, j, k);

            //back
            renderblocks.overrideBlockTexture = 87;
            block.setBlockBounds(0.9F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
            renderblocks.renderStandardBlock(block, i, j, k);
            renderblocks.overrideBlockTexture = -1;

        }
        else if(direction == 2)
        {
            //forward
            renderblocks.overrideBlockTexture = 86;
            block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.1F);
            renderblocks.renderStandardBlock(block, i, j, k);
            //mid
            renderblocks.overrideBlockTexture = -1;
            block.setBlockBounds(0.1F, 0.1F, 0.05F, 0.9F, 0.9F, 0.95F);
            renderblocks.renderStandardBlock(block, i, j, k);
            //back
            renderblocks.overrideBlockTexture = 87;
            block.setBlockBounds(0.0F, 0.0F, 0.9F, 1.0F, 1.0F, 1.0F);
            renderblocks.renderStandardBlock(block, i, j, k);
            renderblocks.overrideBlockTexture = -1;
        }
        else if(direction == 3)
        {
            //forward
            renderblocks.overrideBlockTexture = 86;
            block.setBlockBounds(0.9F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
            renderblocks.renderStandardBlock(block, i, j, k);

            //mid
            renderblocks.overrideBlockTexture = -1;
            block.setBlockBounds(0.1F, 0.1F, 0.1F, 0.9F, 0.9F, 0.9F);
            renderblocks.renderStandardBlock(block, i, j, k);

            //back
            renderblocks.overrideBlockTexture = 87;
            block.setBlockBounds(0.0F, 0.0F, 0.0F, 0.1F, 1.0F, 1.0F);
            renderblocks.renderStandardBlock(block, i, j, k);
            renderblocks.overrideBlockTexture = -1;
        }

        block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        return true;
    }

    public static boolean renderAnvil(Block block, int i, int j, int k, RenderBlocks renderblocks)
    {
        IBlockAccess blockAccess = renderblocks.blockAccess;

        int meta = blockAccess.getBlockMetadata(i, j, k);
        int direction = ((BlockTerraAnvil)block).getDirectionFromMetadata(meta);

            if(direction == 0)//x
            {
                //top
                block.setBlockBounds(0.3F, 0.4F, 0.1F, 0.7F, 0.6F, 0.9F);
                renderblocks.renderStandardBlock(block, i, j, k);

                //core
                block.setBlockBounds(0.35F, 0.0F, 0.15F, 0.65F, 0.4F, 0.85F);
                renderblocks.renderStandardBlock(block, i, j, k);

                //feet
                block.setBlockBounds(0.25F, 0.0F, 0.1F, 0.75F, 0.2F, 0.90F);
                renderblocks.renderStandardBlock(block, i, j, k);
                block.setBlockBounds(0.20F, 0.0F, 0.0F, 0.80F, 0.1F, 1.0F);
                renderblocks.renderStandardBlock(block, i, j, k);

                block.setBlockBounds(0.2F, 0.0F, 0.0F, 0.80F, 0.6F, 1.0F);
            }
            else if(direction == 1)//z
            {
                //top
                block.setBlockBounds(0.1F, 0.4F, 0.3F, 0.9F, 0.6F, 0.7F);
                renderblocks.renderStandardBlock(block, i, j, k);

                //core
                block.setBlockBounds(0.15F, 0.0F, 0.35F, 0.85F, 0.4F, 0.65F);
                renderblocks.renderStandardBlock(block, i, j, k);

                //feet
                block.setBlockBounds(0.1F, 0.0F, 0.25F, 0.90F, 0.2F, 0.75F);
                renderblocks.renderStandardBlock(block, i, j, k);
                block.setBlockBounds(0.0F, 0.0F, 0.20F, 1.00F, 0.1F, 0.80F);
                renderblocks.renderStandardBlock(block, i, j, k);

                block.setBlockBounds(0.0F, 0.0F, 0.20F, 1.0F, 0.6F, 0.8F);

            }
            
            if(((TileEntityTerraAnvil)blockAccess.getBlockTileEntity(i, j, k)).AnvilTier == AnvilReq.STONE.Tier)
            {
                block.setBlockBounds(0.0F, 0.0F, 0.00F, 1.0F, 1.0F, 1.0F);
            }
        return true;
    }

    public static boolean renderAnvil2(Block block, int i, int j, int k, RenderBlocks renderblocks)
    {
        IBlockAccess blockAccess = renderblocks.blockAccess;

        int meta = blockAccess.getBlockMetadata(i, j, k);
        int direction = ((BlockTerraAnvil)block).getDirectionFromMetadata(meta);        

        if(direction == 0)//x
        {
            //top
            block.setBlockBounds(0.3F, 0.4F, 0.1F, 0.7F, 0.6F, 0.9F);
            renderblocks.renderStandardBlock(block, i, j, k);

            //core
            block.setBlockBounds(0.35F, 0.0F, 0.15F, 0.65F, 0.4F, 0.85F);
            renderblocks.renderStandardBlock(block, i, j, k);

            //feet
            block.setBlockBounds(0.25F, 0.0F, 0.1F, 0.75F, 0.2F, 0.90F);
            renderblocks.renderStandardBlock(block, i, j, k);
            block.setBlockBounds(0.20F, 0.0F, 0.0F, 0.80F, 0.1F, 1.0F);
            renderblocks.renderStandardBlock(block, i, j, k);

            block.setBlockBounds(0.2F, 0.0F, 0.0F, 0.80F, 0.6F, 1.0F);
        }
        else if(direction == 1)//z
        {
            //top
            block.setBlockBounds(0.1F, 0.4F, 0.3F, 0.9F, 0.6F, 0.7F);
            renderblocks.renderStandardBlock(block, i, j, k);

            //core
            block.setBlockBounds(0.15F, 0.0F, 0.35F, 0.85F, 0.4F, 0.65F);
            renderblocks.renderStandardBlock(block, i, j, k);

            //feet
            block.setBlockBounds(0.1F, 0.0F, 0.25F, 0.90F, 0.2F, 0.75F);
            renderblocks.renderStandardBlock(block, i, j, k);
            block.setBlockBounds(0.0F, 0.0F, 0.20F, 1.00F, 0.1F, 0.80F);
            renderblocks.renderStandardBlock(block, i, j, k);

            block.setBlockBounds(0.0F, 0.0F, 0.20F, 1.0F, 0.6F, 0.8F);

        }


        return true;
    }

    public static boolean RenderSluice(Block block, int i, int j, int k, RenderBlocks renderblocks)
    {
        IBlockAccess blockAccess = renderblocks.blockAccess;
        Tessellator tessellator = Tessellator.instance;
        int l = blockAccess.getBlockMetadata(i, j, k);
        int i1 = ((BlockTerraSluice)block).getDirectionFromMetadata(l);
        float f = 0.5F;
        float f1 = 1.0F;
        float f2 = 0.8F;
        float f3 = 0.6F;
        int j1 = block.getMixedBrightnessForBlock(blockAccess, i, j, k);
        tessellator.setBrightness(j1);
        tessellator.setColorOpaque_F(f, f, f);

        int k1 = block.getBlockTexture(blockAccess, i, j, k, 0);
        int l1 = (k1 & 0xf) << 4;
        int i2 = k1 & 0xf0;
        double d = (float)l1 / 256F;
        double d1 = ((double)(l1 + 16) - 0.01D) / 256D;
        double d2 = (float)i2 / 256F;
        double d3 = ((double)(i2 + 16) - 0.01D) / 256D;

        double minX = (double)i + block.minX;
        double maxX = (double)i + block.maxX;
        double minY = (double)j + block.minY;
        double minZ = (double)k + block.minZ;
        double maxZ = (double)k + block.maxZ;
        double maxY = (double)j + block.maxY;

        int var10 = blockAccess.getBiomeGenForCoords(i, k).waterColorMultiplier;
        int waterR = (var10 & 16711680) >> 16;
        int waterG = (var10 & 65280) >> 8;
        int waterB = var10 & 255;

        //render ramp
        if(!((BlockTerraSluice)block).isBlockFootOfBed(l))
        {
            if(i1 == 0)
            {
                //ribs
                block.setBlockBounds(0.0F, 0.0F, 0.75F, 1F, 0.75F, 0.8F);
                renderblocks.renderStandardBlock(block, i, j, k);
                block.setBlockBounds(0.0F, 0.0F, 0.45F, 1F, 0.9F, 0.5F);
                renderblocks.renderStandardBlock(block, i, j, k);

                tessellator.setColorOpaque_F(f * f1, f * f2, f * f3);
                tessellator.addVertexWithUV(minX, maxY, minZ, d1, d2);//d,d3
                tessellator.addVertexWithUV(minX, minY+0.5F, maxZ, d1, d3);//d,d2
                tessellator.addVertexWithUV(maxX, minY+0.5f, maxZ, d, d3);//d1,d2
                tessellator.addVertexWithUV(maxX, maxY, minZ, d, d2);//d1,d3
                if(((BlockTerraSluice)block).getIsRecievingWater(l))
                {
                    //get water texture
                    k1 = block.getBlockTextureFromSideAndMetadata(0, 4);
                    l1 = (k1 & 0xf) << 4;
                    i2 = k1 & 0xf0;
                    l = block.colorMultiplier(blockAccess, i, j, k);
                    //reassign the uv coords
                    d = (float)l1 / 256F;
                    d1 = ((double)(l1 + 16) - 0.01D) / 256D;
                    d2 = (float)i2 / 256F;
                    d3 = ((double)(i2 + 16) - 0.01D) / 256D;

                    //draw water plane
                    //tessellator.setColorOpaque_F(0.8F, 0.8F, 0.8F);
                    tessellator.setColorOpaque(waterR, waterG, waterB);
                    tessellator.addVertexWithUV(minX, maxY, minZ, d1, d2);//d,d3
                    tessellator.addVertexWithUV(minX, minY+0.6F, maxZ, d1, d3);//d,d2
                    tessellator.addVertexWithUV(maxX, minY+0.6f, maxZ, d, d3);//d1,d2
                    tessellator.addVertexWithUV(maxX, maxY, minZ, d, d2);//d1,d3
                }
            }
            else if(i1 == 1)
            {
                //ribs
                block.setBlockBounds(0.2F, 0.0F, 0.0F, 0.25F, 0.75F, 1.0F);
                renderblocks.renderStandardBlock(block, i, j, k);
                block.setBlockBounds(0.5F, 0.0F, 0.0F, 0.55F, 0.9F, 1.0F);
                renderblocks.renderStandardBlock(block, i, j, k);

                tessellator.setColorOpaque_F(f * f1, f * f2, f * f3);
                tessellator.addVertexWithUV(minX, minY+0.5F, maxZ, d, d3);
                tessellator.addVertexWithUV(maxX, maxY, maxZ, d, d2);
                tessellator.addVertexWithUV(maxX, maxY, minZ, d1, d2);
                tessellator.addVertexWithUV(minX, minY+0.5F, minZ, d1, d3);

                if(((BlockTerraSluice)block).getIsRecievingWater(l))
                {
                    //get water texture
                    k1 = block.getBlockTextureFromSideAndMetadata(0, 4);
                    l1 = (k1 & 0xf) << 4;
                    i2 = k1 & 0xf0;
                    l = block.colorMultiplier(blockAccess, i, j, k);
                    //reassign the uv coords
                    d = (float)l1 / 256F;
                    d1 = ((double)(l1 + 16) - 0.01D) / 256D;
                    d2 = (float)i2 / 256F;
                    d3 = ((double)(i2 + 16) - 0.01D) / 256D;

                    //draw water plane
                    tessellator.setColorOpaque(waterR, waterG, waterB);
                    tessellator.addVertexWithUV(minX, minY+0.6F, maxZ, d, d3);
                    tessellator.addVertexWithUV(maxX, maxY, maxZ, d, d2);
                    tessellator.addVertexWithUV(maxX, maxY, minZ, d1, d2);
                    tessellator.addVertexWithUV(minX, minY+0.6F, minZ, d1, d3);
                }
            }
            else if(i1 == 2)
            {
                //ribs
                block.setBlockBounds(0.0F, 0.0F, 0.2F, 1F, 0.75F, 0.25F);
                renderblocks.renderStandardBlock(block, i, j, k);
                block.setBlockBounds(0.0F, 0.0F, 0.5F, 1F, 0.9F, 0.55F);
                renderblocks.renderStandardBlock(block, i, j, k);

                tessellator.setColorOpaque_F(f * f1, f * f2, f * f3);
                tessellator.addVertexWithUV(minX, minY+0.5F, minZ, d, d3);
                tessellator.addVertexWithUV(minX, maxY, maxZ, d, d2);
                tessellator.addVertexWithUV(maxX, maxY, maxZ, d1, d2);
                tessellator.addVertexWithUV(maxX, minY+0.5F, minZ, d1, d3);

                if(((BlockTerraSluice)block).getIsRecievingWater(l))
                {
                    //get water texture
                    k1 = block.getBlockTextureFromSideAndMetadata(0, 4);
                    l1 = (k1 & 0xf) << 4;
                    i2 = k1 & 0xf0;
                    l = block.colorMultiplier(blockAccess, i, j, k);
                    //reassign the uv coords
                    d = (float)l1 / 256F;
                    d1 = ((double)(l1 + 16) - 0.01D) / 256D;
                    d2 = (float)i2 / 256F;
                    d3 = ((double)(i2 + 16) - 0.01D) / 256D;

                    //draw water plane
                    tessellator.setColorOpaque(waterR, waterG, waterB);
                    tessellator.addVertexWithUV(minX, minY+0.6F, minZ, d, d3);
                    tessellator.addVertexWithUV(minX, maxY, maxZ, d, d2);
                    tessellator.addVertexWithUV(maxX, maxY, maxZ, d1, d2);
                    tessellator.addVertexWithUV(maxX, minY+0.6F, minZ, d1, d3);
                }


            }
            else if(i1 == 3)
            {        
                //ribs
                block.setBlockBounds(0.75F, 0.0F, 0.0F, 0.8F, 0.75F, 1.0F);
                renderblocks.renderStandardBlock(block, i, j, k);
                block.setBlockBounds(0.45F, 0.0F, 0.0F, 0.5F, 0.9F, 1.0F);
                renderblocks.renderStandardBlock(block, i, j, k);

                tessellator.setColorOpaque_F(f * f1, f * f2, f * f3);
                tessellator.addVertexWithUV(maxX, minY+0.5f, minZ, d, d3);
                tessellator.addVertexWithUV(minX, maxY, minZ, d, d2);
                tessellator.addVertexWithUV(minX, maxY, maxZ, d1, d2);
                tessellator.addVertexWithUV(maxX, minY+0.5F, maxZ, d1, d3);

                if(((BlockTerraSluice)block).getIsRecievingWater(l))
                {
                    //get water texture
                    k1 = 223;
                    l1 = (k1 & 0xf) << 4;
                    i2 = k1 & 0xf0;
                    l = block.colorMultiplier(blockAccess, i, j, k);
                    //reassign the uv coords
                    d = (float)l1 / 256F;
                    d1 = ((double)(l1 + 16) - 0.01D) / 256D;
                    d2 = (float)i2 / 256F;
                    d3 = ((double)(i2 + 16) - 0.01D) / 256D;

                    tessellator.setColorOpaque(waterR, waterG, waterB);
                    tessellator.addVertexWithUV(maxX, minY+0.6f, minZ, d, d3);
                    tessellator.addVertexWithUV(minX, maxY, minZ, d, d2);
                    tessellator.addVertexWithUV(minX, maxY, maxZ, d1, d2);
                    tessellator.addVertexWithUV(maxX, minY+0.6F, maxZ, d1, d3);
                }
            }
        }
        else
        {
            if(i1 == 0)
            {
                //ribs
                block.setBlockBounds(0.0F, 0.0F, 0.70F, 1F, 0.3F, 0.75F);
                renderblocks.renderStandardBlock(block, i, j, k);
                block.setBlockBounds(0.0F, 0.0F, 0.4F, 1F, 0.45F, 0.45F);
                renderblocks.renderStandardBlock(block, i, j, k);
                block.setBlockBounds(0.0F, 0.0F, 0.1F, 1F, 0.6F, 0.15F);
                renderblocks.renderStandardBlock(block, i, j, k);

                tessellator.setColorOpaque_F(f * f1, f * f2, f * f3);
                tessellator.addVertexWithUV(minX, minY+0.5F, minZ, d1, d2);//d,d3
                tessellator.addVertexWithUV(minX, minY, maxZ, d1, d3);//d,d2
                tessellator.addVertexWithUV(maxX, minY, maxZ, d, d3);//d1,d2
                tessellator.addVertexWithUV(maxX, minY+0.5F, minZ, d, d2);//d1,d3

                if(((BlockTerraSluice)block).getIsRecievingWater(l))
                {
                    //get water texture
                    k1 = block.getBlockTextureFromSideAndMetadata(0, 4);
                    l1 = (k1 & 0xf) << 4;
                    i2 = k1 & 0xf0;
                    l = block.colorMultiplier(blockAccess, i, j, k);
                    //reassign the uv coords
                    d = (float)l1 / 256F;
                    d1 = ((double)(l1 + 16) - 0.01D) / 256D;
                    d2 = (float)i2 / 256F;
                    d3 = ((double)(i2 + 16) - 0.01D) / 256D;

                    //draw water plane
                    tessellator.setColorOpaque(waterR, waterG, waterB);
                    tessellator.addVertexWithUV(minX, minY+0.6F, minZ, d1, d2);//d,d3
                    tessellator.addVertexWithUV(minX, minY, maxZ, d1, d3);//d,d2
                    tessellator.addVertexWithUV(maxX, minY, maxZ, d, d3);//d1,d2
                    tessellator.addVertexWithUV(maxX, minY+0.6F, minZ, d, d2);//d1,d3
                }
            }
            if(i1 == 1)
            {
                //ribs
                block.setBlockBounds(0.9F, 0.0F, 0.0F, 0.95F, 0.6F, 1.0F);
                renderblocks.renderStandardBlock(block, i, j, k);
                block.setBlockBounds(0.6F, 0.0F, 0.0F, 0.65F, 0.45F, 1.0F);
                renderblocks.renderStandardBlock(block, i, j, k);
                block.setBlockBounds(0.3F, 0.0F, 0.0F, 0.35F, 0.3F, 1.0F);
                renderblocks.renderStandardBlock(block, i, j, k);

                tessellator.setColorOpaque_F(f * f1, f * f2, f * f3);
                tessellator.addVertexWithUV(minX, minY, maxZ, d, d3);
                tessellator.addVertexWithUV(maxX, minY+0.5F, maxZ, d, d2);
                tessellator.addVertexWithUV(maxX, minY+0.5F, minZ, d1, d2);
                tessellator.addVertexWithUV(minX, minY, minZ, d1, d3);

                if(((BlockTerraSluice)block).getIsRecievingWater(l))
                {
                    //get water texture
                    k1 = block.getBlockTextureFromSideAndMetadata(0, 4);
                    l1 = (k1 & 0xf) << 4;
                    i2 = k1 & 0xf0;
                    l = block.colorMultiplier(blockAccess, i, j, k);
                    //reassign the uv coords
                    d = (float)l1 / 256F;
                    d1 = ((double)(l1 + 16) - 0.01D) / 256D;
                    d2 = (float)i2 / 256F;
                    d3 = ((double)(i2 + 16) - 0.01D) / 256D;

                    //draw water plane
                    tessellator.setColorOpaque(waterR, waterG, waterB);
                    tessellator.addVertexWithUV(minX, minY, maxZ, d, d3);
                    tessellator.addVertexWithUV(maxX, minY+0.6F, maxZ, d, d2);
                    tessellator.addVertexWithUV(maxX, minY+0.6F, minZ, d1, d2);
                    tessellator.addVertexWithUV(minX, minY, minZ, d1, d3);
                }
            }
            if(i1 == 2)
            {               
                //ribs
                block.setBlockBounds(0.0F, 0.0F, 0.3F, 1F, 0.3F, 0.35F);
                renderblocks.renderStandardBlock(block, i, j, k);
                block.setBlockBounds(0.0F, 0.0F, 0.6F, 1F, 0.45F, 0.65F);
                renderblocks.renderStandardBlock(block, i, j, k);
                block.setBlockBounds(0.0F, 0.0F, 0.9F, 1F, 0.6F, 0.95F);
                renderblocks.renderStandardBlock(block, i, j, k);

                tessellator.setColorOpaque_F(f * f1, f * f2, f * f3);
                tessellator.addVertexWithUV(minX, minY, minZ, d, d3);
                tessellator.addVertexWithUV(minX, minY+0.5f, maxZ, d, d2);
                tessellator.addVertexWithUV(maxX, minY+0.5f, maxZ, d1, d2);
                tessellator.addVertexWithUV(maxX, minY, minZ, d1, d3);

                if(((BlockTerraSluice)block).getIsRecievingWater(l))
                {
                    //get water texture
                    k1 = 223;
                    l1 = (k1 & 0xf) << 4;
                    i2 = k1 & 0xf0;
                    l = block.colorMultiplier(blockAccess, i, j, k);

                    //reassign the uv coords
                    d = (float)l1 / 256F;
                    d1 = ((double)(l1 + 16) - 0.01D) / 256D;
                    d2 = (float)i2 / 256F;
                    d3 = ((double)(i2 + 16) - 0.01D) / 256D;

                    tessellator.setColorOpaque(waterR, waterG, waterB);
                    tessellator.addVertexWithUV(minX, minY, minZ, d, d3);
                    tessellator.addVertexWithUV(minX, minY+0.6f, maxZ, d, d2);
                    tessellator.addVertexWithUV(maxX, minY+0.6f, maxZ, d1, d2);
                    tessellator.addVertexWithUV(maxX, minY, minZ, d1, d3);
                }
            }
            if(i1 == 3)
            {               
                //ribs
                block.setBlockBounds(0.7F, 0.0F, 0.0F, 0.75F, 0.3F, 1.0F);
                renderblocks.renderStandardBlock(block, i, j, k);
                block.setBlockBounds(0.4F, 0.0F, 0.0F, 0.45F, 0.45F, 1.0F);
                renderblocks.renderStandardBlock(block, i, j, k);
                block.setBlockBounds(0.1F, 0.0F, 0.0F, 0.15F, 0.6F, 1.0F);
                renderblocks.renderStandardBlock(block, i, j, k);

                tessellator.setColorOpaque_F(f * f1, f * f2, f * f3);
                tessellator.addVertexWithUV(maxX, minY, minZ, d, d3);
                tessellator.addVertexWithUV(minX, minY+0.5f, minZ, d, d2);
                tessellator.addVertexWithUV(minX, minY+0.5f, maxZ, d1, d2);
                tessellator.addVertexWithUV(maxX, minY, maxZ, d1, d3);

                if(((BlockTerraSluice)block).getIsRecievingWater(l))
                {
                    //get water texture
                    k1 = 223;
                    l1 = (k1 & 0xf) << 4;
                    i2 = k1 & 0xf0;
                    l = block.colorMultiplier(blockAccess, i, j, k);
                    //reassign the uv coords
                    d = (float)l1 / 256F;
                    d1 = ((double)(l1 + 16) - 0.01D) / 256D;
                    d2 = (float)i2 / 256F;
                    d3 = ((double)(i2 + 16) - 0.01D) / 256D;
                    tessellator.setColorOpaque(waterR, waterG, waterB);
                    tessellator.addVertexWithUV(maxX, minY, minZ, d, d3);
                    tessellator.addVertexWithUV(minX, minY+0.6f, minZ, d, d2);
                    tessellator.addVertexWithUV(minX, minY+0.6f, maxZ, d1, d2);
                    tessellator.addVertexWithUV(maxX, minY, maxZ, d1, d3);
                }
            }
        }


        //set the block collision box
        block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);

        return true;
    }

    public static boolean RenderFruitLeaves(Block block, int xCoord, int yCoord, int zCoord,float par5, float par6, float par7, RenderBlocks renderblocks)
    {
        BiomeGenBase biome = renderblocks.blockAccess.getBiomeGenForCoords(xCoord, zCoord);
        int meta = renderblocks.blockAccess.getBlockMetadata(xCoord, yCoord, zCoord);
        if(meta >= 8)
            meta-=8;
        FloraManager manager = FloraManager.getInstance();
        FloraIndex index = manager.findMatchingIndex(BlockFruitLeaves.getType(block.blockID, meta));

        if(index == null)
            return false;
        renderblocks.enableAO = true;
        boolean var8 = false;
        float var9 = renderblocks.lightValueOwn;
        float var10 = renderblocks.lightValueOwn;
        float var11 = renderblocks.lightValueOwn;
        float var12 = renderblocks.lightValueOwn;
        boolean var13 = true;
        boolean var14 = true;
        boolean var15 = true;
        boolean var16 = true;
        boolean var17 = true;
        boolean var18 = true;
        renderblocks.lightValueOwn = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord, yCoord, zCoord);
        renderblocks.aoLightValueXNeg = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord - 1, yCoord, zCoord);
        renderblocks.aoLightValueYNeg = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord, yCoord - 1, zCoord);
        renderblocks.aoLightValueZNeg = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord, yCoord, zCoord - 1);
        renderblocks.aoLightValueXPos = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord + 1, yCoord, zCoord);
        renderblocks.aoLightValueYPos = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord, yCoord + 1, zCoord);
        renderblocks.aoLightValueZPos = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord, yCoord, zCoord + 1);
        int var19 = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord, yCoord, zCoord);
        int var20 = var19;
        int var21 = var19;
        int var22 = var19;
        int var23 = var19;
        int var24 = var19;
        int var25 = var19;

        if (block.minY <= 0.0D)
        {
            var21 = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord, yCoord - 1, zCoord);
        }

        if (block.maxY >= 1.0D)
        {
            var24 = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord, yCoord + 1, zCoord);
        }

        if (block.minX <= 0.0D)
        {
            var20 = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord - 1, yCoord, zCoord);
        }

        if (block.maxX >= 1.0D)
        {
            var23 = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord + 1, yCoord, zCoord);
        }

        if (block.minZ <= 0.0D)
        {
            var22 = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord, yCoord, zCoord - 1);
        }

        if (block.maxZ >= 1.0D)
        {
            var25 = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord, yCoord, zCoord + 1);
        }

        Tessellator tesselator = Tessellator.instance;
        tesselator.setBrightness(983055);
        renderblocks.aoGrassXYZPPC = Block.canBlockGrass[renderblocks.blockAccess.getBlockId(xCoord + 1, yCoord + 1, zCoord)];
        renderblocks.aoGrassXYZPNC = Block.canBlockGrass[renderblocks.blockAccess.getBlockId(xCoord + 1, yCoord - 1, zCoord)];
        renderblocks.aoGrassXYZPCP = Block.canBlockGrass[renderblocks.blockAccess.getBlockId(xCoord + 1, yCoord, zCoord + 1)];
        renderblocks.aoGrassXYZPCN = Block.canBlockGrass[renderblocks.blockAccess.getBlockId(xCoord + 1, yCoord, zCoord - 1)];
        renderblocks.aoGrassXYZNPC = Block.canBlockGrass[renderblocks.blockAccess.getBlockId(xCoord - 1, yCoord + 1, zCoord)];
        renderblocks.aoGrassXYZNNC = Block.canBlockGrass[renderblocks.blockAccess.getBlockId(xCoord - 1, yCoord - 1, zCoord)];
        renderblocks.aoGrassXYZNCN = Block.canBlockGrass[renderblocks.blockAccess.getBlockId(xCoord - 1, yCoord, zCoord - 1)];
        renderblocks.aoGrassXYZNCP = Block.canBlockGrass[renderblocks.blockAccess.getBlockId(xCoord - 1, yCoord, zCoord + 1)];
        renderblocks.aoGrassXYZCPP = Block.canBlockGrass[renderblocks.blockAccess.getBlockId(xCoord, yCoord + 1, zCoord + 1)];
        renderblocks.aoGrassXYZCPN = Block.canBlockGrass[renderblocks.blockAccess.getBlockId(xCoord, yCoord + 1, zCoord - 1)];
        renderblocks.aoGrassXYZCNP = Block.canBlockGrass[renderblocks.blockAccess.getBlockId(xCoord, yCoord - 1, zCoord + 1)];
        renderblocks.aoGrassXYZCNN = Block.canBlockGrass[renderblocks.blockAccess.getBlockId(xCoord, yCoord - 1, zCoord - 1)];

        int texIndex;
        float colorMult = 0.78F;

        if (renderblocks.renderAllFaces || block.shouldSideBeRendered(renderblocks.blockAccess, xCoord, yCoord - 1, zCoord, 0))
        {
            if (renderblocks.aoType > 0)
            {
                if (block.minY <= 0.0D)
                {
                    --yCoord;
                }

                renderblocks.aoBrightnessXYNN = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord - 1, yCoord, zCoord);
                renderblocks.aoBrightnessYZNN = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord, yCoord, zCoord - 1);
                renderblocks.aoBrightnessYZNP = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord, yCoord, zCoord + 1);
                renderblocks.aoBrightnessXYPN = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord + 1, yCoord, zCoord);
                renderblocks.aoLightValueScratchXYNN = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord - 1, yCoord, zCoord);
                renderblocks.aoLightValueScratchYZNN = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord, yCoord, zCoord - 1);
                renderblocks.aoLightValueScratchYZNP = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord, yCoord, zCoord + 1);
                renderblocks.aoLightValueScratchXYPN = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord + 1, yCoord, zCoord);

                if (!renderblocks.aoGrassXYZCNN && !renderblocks.aoGrassXYZNNC)
                {
                    renderblocks.aoLightValueScratchXYZNNN = renderblocks.aoLightValueScratchXYNN;
                    renderblocks.aoBrightnessXYZNNN = renderblocks.aoBrightnessXYNN;
                }
                else
                {
                    renderblocks.aoLightValueScratchXYZNNN = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord - 1, yCoord, zCoord - 1);
                    renderblocks.aoBrightnessXYZNNN = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord - 1, yCoord, zCoord - 1);
                }

                if (!renderblocks.aoGrassXYZCNP && !renderblocks.aoGrassXYZNNC)
                {
                    renderblocks.aoLightValueScratchXYZNNP = renderblocks.aoLightValueScratchXYNN;
                    renderblocks.aoBrightnessXYZNNP = renderblocks.aoBrightnessXYNN;
                }
                else
                {
                    renderblocks.aoLightValueScratchXYZNNP = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord - 1, yCoord, zCoord + 1);
                    renderblocks.aoBrightnessXYZNNP = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord - 1, yCoord, zCoord + 1);
                }

                if (!renderblocks.aoGrassXYZCNN && !renderblocks.aoGrassXYZPNC)
                {
                    renderblocks.aoLightValueScratchXYZPNN = renderblocks.aoLightValueScratchXYPN;
                    renderblocks.aoBrightnessXYZPNN = renderblocks.aoBrightnessXYPN;
                }
                else
                {
                    renderblocks.aoLightValueScratchXYZPNN = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord + 1, yCoord, zCoord - 1);
                    renderblocks.aoBrightnessXYZPNN = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord + 1, yCoord, zCoord - 1);
                }

                if (!renderblocks.aoGrassXYZCNP && !renderblocks.aoGrassXYZPNC)
                {
                    renderblocks.aoLightValueScratchXYZPNP = renderblocks.aoLightValueScratchXYPN;
                    renderblocks.aoBrightnessXYZPNP = renderblocks.aoBrightnessXYPN;
                }
                else
                {
                    renderblocks.aoLightValueScratchXYZPNP = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord + 1, yCoord, zCoord + 1);
                    renderblocks.aoBrightnessXYZPNP = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord + 1, yCoord, zCoord + 1);
                }

                if (block.minY <= 0.0D)
                {
                    ++yCoord;
                }

                var9 = (renderblocks.aoLightValueScratchXYZNNP + renderblocks.aoLightValueScratchXYNN + renderblocks.aoLightValueScratchYZNP + renderblocks.aoLightValueYNeg) / 4.0F;
                var12 = (renderblocks.aoLightValueScratchYZNP + renderblocks.aoLightValueYNeg + renderblocks.aoLightValueScratchXYZPNP + renderblocks.aoLightValueScratchXYPN) / 4.0F;
                var11 = (renderblocks.aoLightValueYNeg + renderblocks.aoLightValueScratchYZNN + renderblocks.aoLightValueScratchXYPN + renderblocks.aoLightValueScratchXYZPNN) / 4.0F;
                var10 = (renderblocks.aoLightValueScratchXYNN + renderblocks.aoLightValueScratchXYZNNN + renderblocks.aoLightValueYNeg + renderblocks.aoLightValueScratchYZNN) / 4.0F;
                renderblocks.brightnessTopLeft = renderblocks.getAoBrightness(renderblocks.aoBrightnessXYZNNP, renderblocks.aoBrightnessXYNN, renderblocks.aoBrightnessYZNP, var21);
                renderblocks.brightnessTopRight = renderblocks.getAoBrightness(renderblocks.aoBrightnessYZNP, renderblocks.aoBrightnessXYZPNP, renderblocks.aoBrightnessXYPN, var21);
                renderblocks.brightnessBottomRight = renderblocks.getAoBrightness(renderblocks.aoBrightnessYZNN, renderblocks.aoBrightnessXYPN, renderblocks.aoBrightnessXYZPNN, var21);
                renderblocks.brightnessBottomLeft = renderblocks.getAoBrightness(renderblocks.aoBrightnessXYNN, renderblocks.aoBrightnessXYZNNN, renderblocks.aoBrightnessYZNN, var21);
            }
            else
            {
                var12 = renderblocks.aoLightValueYNeg;
                var11 = renderblocks.aoLightValueYNeg;
                var10 = renderblocks.aoLightValueYNeg;
                var9 = renderblocks.aoLightValueYNeg;
                renderblocks.brightnessTopLeft = renderblocks.brightnessBottomLeft = renderblocks.brightnessBottomRight = renderblocks.brightnessTopRight = renderblocks.aoBrightnessXYNN;
            }

            renderblocks.colorRedTopLeft = renderblocks.colorRedBottomLeft = renderblocks.colorRedBottomRight = renderblocks.colorRedTopRight = (var18 ? par5 : 1.0F) * colorMult;
            renderblocks.colorGreenTopLeft = renderblocks.colorGreenBottomLeft = renderblocks.colorGreenBottomRight = renderblocks.colorGreenTopRight = (var18 ? par6 : 1.0F) * colorMult;
            renderblocks.colorBlueTopLeft = renderblocks.colorBlueBottomLeft = renderblocks.colorBlueBottomRight = renderblocks.colorBlueTopRight = (var18 ? par7 : 1.0F) * colorMult;

            renderblocks.renderBottomFace(block, (double)xCoord, (double)yCoord, (double)zCoord, block.getBlockTexture(renderblocks.blockAccess, xCoord, yCoord, zCoord, 0));

            if(index.inBloom(TFCSeasons.currentMonth))
            {
                texIndex = getFruitTreeOverlay(renderblocks.blockAccess,xCoord,yCoord,zCoord);
                if(texIndex!= -1)
                {
                    renderblocks.colorRedTopLeft = renderblocks.colorRedBottomLeft = renderblocks.colorRedBottomRight = renderblocks.colorRedTopRight = ( 1.0F) * colorMult;
                    renderblocks.colorGreenTopLeft = renderblocks.colorGreenBottomLeft = renderblocks.colorGreenBottomRight = renderblocks.colorGreenTopRight = ( 1.0F) * colorMult;
                    renderblocks.colorBlueTopLeft = renderblocks.colorBlueBottomLeft = renderblocks.colorBlueBottomRight = renderblocks.colorBlueTopRight = ( 1.0F) * colorMult;
                    renderblocks.renderBottomFace(block, (double)xCoord, (double)yCoord, (double)zCoord, texIndex);
                }
            }
            var8 = true;
        }

        if (renderblocks.renderAllFaces || block.shouldSideBeRendered(renderblocks.blockAccess, xCoord, yCoord + 1, zCoord, 1))
        {
            if (renderblocks.aoType > 0)
            {
                if (block.maxY >= 1.0D)
                {
                    ++yCoord;
                }

                renderblocks.aoBrightnessXYNP = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord - 1, yCoord, zCoord);
                renderblocks.aoBrightnessXYPP = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord + 1, yCoord, zCoord);
                renderblocks.aoBrightnessYZPN = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord, yCoord, zCoord - 1);
                renderblocks.aoBrightnessYZPP = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord, yCoord, zCoord + 1);
                renderblocks.aoLightValueScratchXYNP = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord - 1, yCoord, zCoord);
                renderblocks.aoLightValueScratchXYPP = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord + 1, yCoord, zCoord);
                renderblocks.aoLightValueScratchYZPN = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord, yCoord, zCoord - 1);
                renderblocks.aoLightValueScratchYZPP = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord, yCoord, zCoord + 1);

                if (!renderblocks.aoGrassXYZCPN && !renderblocks.aoGrassXYZNPC)
                {
                    renderblocks.aoLightValueScratchXYZNPN = renderblocks.aoLightValueScratchXYNP;
                    renderblocks.aoBrightnessXYZNPN = renderblocks.aoBrightnessXYNP;
                }
                else
                {
                    renderblocks.aoLightValueScratchXYZNPN = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord - 1, yCoord, zCoord - 1);
                    renderblocks.aoBrightnessXYZNPN = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord - 1, yCoord, zCoord - 1);
                }

                if (!renderblocks.aoGrassXYZCPN && !renderblocks.aoGrassXYZPPC)
                {
                    renderblocks.aoLightValueScratchXYZPPN = renderblocks.aoLightValueScratchXYPP;
                    renderblocks.aoBrightnessXYZPPN = renderblocks.aoBrightnessXYPP;
                }
                else
                {
                    renderblocks.aoLightValueScratchXYZPPN = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord + 1, yCoord, zCoord - 1);
                    renderblocks.aoBrightnessXYZPPN = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord + 1, yCoord, zCoord - 1);
                }

                if (!renderblocks.aoGrassXYZCPP && !renderblocks.aoGrassXYZNPC)
                {
                    renderblocks.aoLightValueScratchXYZNPP = renderblocks.aoLightValueScratchXYNP;
                    renderblocks.aoBrightnessXYZNPP = renderblocks.aoBrightnessXYNP;
                }
                else
                {
                    renderblocks.aoLightValueScratchXYZNPP = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord - 1, yCoord, zCoord + 1);
                    renderblocks.aoBrightnessXYZNPP = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord - 1, yCoord, zCoord + 1);
                }

                if (!renderblocks.aoGrassXYZCPP && !renderblocks.aoGrassXYZPPC)
                {
                    renderblocks.aoLightValueScratchXYZPPP = renderblocks.aoLightValueScratchXYPP;
                    renderblocks.aoBrightnessXYZPPP = renderblocks.aoBrightnessXYPP;
                }
                else
                {
                    renderblocks.aoLightValueScratchXYZPPP = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord + 1, yCoord, zCoord + 1);
                    renderblocks.aoBrightnessXYZPPP = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord + 1, yCoord, zCoord + 1);
                }

                if (block.maxY >= 1.0D)
                {
                    --yCoord;
                }

                var12 = (renderblocks.aoLightValueScratchXYZNPP + renderblocks.aoLightValueScratchXYNP + renderblocks.aoLightValueScratchYZPP + renderblocks.aoLightValueYPos) / 4.0F;
                var9 = (renderblocks.aoLightValueScratchYZPP + renderblocks.aoLightValueYPos + renderblocks.aoLightValueScratchXYZPPP + renderblocks.aoLightValueScratchXYPP) / 4.0F;
                var10 = (renderblocks.aoLightValueYPos + renderblocks.aoLightValueScratchYZPN + renderblocks.aoLightValueScratchXYPP + renderblocks.aoLightValueScratchXYZPPN) / 4.0F;
                var11 = (renderblocks.aoLightValueScratchXYNP + renderblocks.aoLightValueScratchXYZNPN + renderblocks.aoLightValueYPos + renderblocks.aoLightValueScratchYZPN) / 4.0F;
                renderblocks.brightnessTopRight = renderblocks.getAoBrightness(renderblocks.aoBrightnessXYZNPP, renderblocks.aoBrightnessXYNP, renderblocks.aoBrightnessYZPP, var24);
                renderblocks.brightnessTopLeft = renderblocks.getAoBrightness(renderblocks.aoBrightnessYZPP, renderblocks.aoBrightnessXYZPPP, renderblocks.aoBrightnessXYPP, var24);
                renderblocks.brightnessBottomLeft = renderblocks.getAoBrightness(renderblocks.aoBrightnessYZPN, renderblocks.aoBrightnessXYPP, renderblocks.aoBrightnessXYZPPN, var24);
                renderblocks.brightnessBottomRight = renderblocks.getAoBrightness(renderblocks.aoBrightnessXYNP, renderblocks.aoBrightnessXYZNPN, renderblocks.aoBrightnessYZPN, var24);
            }
            else
            {
                var12 = renderblocks.aoLightValueYPos;
                var11 = renderblocks.aoLightValueYPos;
                var10 = renderblocks.aoLightValueYPos;
                var9 = renderblocks.aoLightValueYPos;
                renderblocks.brightnessTopLeft = renderblocks.brightnessBottomLeft = renderblocks.brightnessBottomRight = renderblocks.brightnessTopRight = var24;
            }

            renderblocks.colorRedTopLeft = renderblocks.colorRedBottomLeft = renderblocks.colorRedBottomRight = renderblocks.colorRedTopRight = (var18 ? par5 : 1.0F) * colorMult;
            renderblocks.colorGreenTopLeft = renderblocks.colorGreenBottomLeft = renderblocks.colorGreenBottomRight = renderblocks.colorGreenTopRight = (var18 ? par6 : 1.0F) * colorMult;
            renderblocks.colorBlueTopLeft = renderblocks.colorBlueBottomLeft = renderblocks.colorBlueBottomRight = renderblocks.colorBlueTopRight = (var18 ? par7 : 1.0F) * colorMult;

            texIndex = block.getBlockTexture(renderblocks.blockAccess, xCoord, yCoord, zCoord, 1);
            renderblocks.renderTopFace(block, (double)xCoord, (double)yCoord, (double)zCoord, texIndex);

            if(index.inBloom(TFCSeasons.currentMonth))
            {
                texIndex = getFruitTreeOverlay(renderblocks.blockAccess,xCoord,yCoord,zCoord);
                if(texIndex!= -1)
                {
                    renderblocks.colorRedTopLeft = renderblocks.colorRedBottomLeft = renderblocks.colorRedBottomRight = renderblocks.colorRedTopRight = ( 1.0F) * colorMult;
                    renderblocks.colorGreenTopLeft = renderblocks.colorGreenBottomLeft = renderblocks.colorGreenBottomRight = renderblocks.colorGreenTopRight = ( 1.0F) * colorMult;
                    renderblocks.colorBlueTopLeft = renderblocks.colorBlueBottomLeft = renderblocks.colorBlueBottomRight = renderblocks.colorBlueTopRight = ( 1.0F) * colorMult;
                    renderblocks.renderTopFace(block, (double)xCoord, (double)yCoord, (double)zCoord, texIndex);
                }
            }
            var8 = true;
        }



        if (renderblocks.renderAllFaces || block.shouldSideBeRendered(renderblocks.blockAccess, xCoord, yCoord, zCoord - 1, 2))
        {
            if (renderblocks.aoType > 0)
            {
                if (block.minZ <= 0.0D)
                {
                    --zCoord;
                }

                renderblocks.aoLightValueScratchXZNN = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord - 1, yCoord, zCoord);
                renderblocks.aoLightValueScratchYZNN = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord, yCoord - 1, zCoord);
                renderblocks.aoLightValueScratchYZPN = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord, yCoord + 1, zCoord);
                renderblocks.aoLightValueScratchXZPN = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord + 1, yCoord, zCoord);
                renderblocks.aoBrightnessXZNN = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord - 1, yCoord, zCoord);
                renderblocks.aoBrightnessYZNN = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord, yCoord - 1, zCoord);
                renderblocks.aoBrightnessYZPN = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord, yCoord + 1, zCoord);
                renderblocks.aoBrightnessXZPN = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord + 1, yCoord, zCoord);

                if (!renderblocks.aoGrassXYZNCN && !renderblocks.aoGrassXYZCNN)
                {
                    renderblocks.aoLightValueScratchXYZNNN = renderblocks.aoLightValueScratchXZNN;
                    renderblocks.aoBrightnessXYZNNN = renderblocks.aoBrightnessXZNN;
                }
                else
                {
                    renderblocks.aoLightValueScratchXYZNNN = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord - 1, yCoord - 1, zCoord);
                    renderblocks.aoBrightnessXYZNNN = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord - 1, yCoord - 1, zCoord);
                }

                if (!renderblocks.aoGrassXYZNCN && !renderblocks.aoGrassXYZCPN)
                {
                    renderblocks.aoLightValueScratchXYZNPN = renderblocks.aoLightValueScratchXZNN;
                    renderblocks.aoBrightnessXYZNPN = renderblocks.aoBrightnessXZNN;
                }
                else
                {
                    renderblocks.aoLightValueScratchXYZNPN = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord - 1, yCoord + 1, zCoord);
                    renderblocks.aoBrightnessXYZNPN = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord - 1, yCoord + 1, zCoord);
                }

                if (!renderblocks.aoGrassXYZPCN && !renderblocks.aoGrassXYZCNN)
                {
                    renderblocks.aoLightValueScratchXYZPNN = renderblocks.aoLightValueScratchXZPN;
                    renderblocks.aoBrightnessXYZPNN = renderblocks.aoBrightnessXZPN;
                }
                else
                {
                    renderblocks.aoLightValueScratchXYZPNN = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord + 1, yCoord - 1, zCoord);
                    renderblocks.aoBrightnessXYZPNN = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord + 1, yCoord - 1, zCoord);
                }

                if (!renderblocks.aoGrassXYZPCN && !renderblocks.aoGrassXYZCPN)
                {
                    renderblocks.aoLightValueScratchXYZPPN = renderblocks.aoLightValueScratchXZPN;
                    renderblocks.aoBrightnessXYZPPN = renderblocks.aoBrightnessXZPN;
                }
                else
                {
                    renderblocks.aoLightValueScratchXYZPPN = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord + 1, yCoord + 1, zCoord);
                    renderblocks.aoBrightnessXYZPPN = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord + 1, yCoord + 1, zCoord);
                }

                if (block.minZ <= 0.0D)
                {
                    ++zCoord;
                }

                var9 = (renderblocks.aoLightValueScratchXZNN + renderblocks.aoLightValueScratchXYZNPN + renderblocks.aoLightValueZNeg + renderblocks.aoLightValueScratchYZPN) / 4.0F;
                var10 = (renderblocks.aoLightValueZNeg + renderblocks.aoLightValueScratchYZPN + renderblocks.aoLightValueScratchXZPN + renderblocks.aoLightValueScratchXYZPPN) / 4.0F;
                var11 = (renderblocks.aoLightValueScratchYZNN + renderblocks.aoLightValueZNeg + renderblocks.aoLightValueScratchXYZPNN + renderblocks.aoLightValueScratchXZPN) / 4.0F;
                var12 = (renderblocks.aoLightValueScratchXYZNNN + renderblocks.aoLightValueScratchXZNN + renderblocks.aoLightValueScratchYZNN + renderblocks.aoLightValueZNeg) / 4.0F;
                renderblocks.brightnessTopLeft = renderblocks.getAoBrightness(renderblocks.aoBrightnessXZNN, renderblocks.aoBrightnessXYZNPN, renderblocks.aoBrightnessYZPN, var22);
                renderblocks.brightnessBottomLeft = renderblocks.getAoBrightness(renderblocks.aoBrightnessYZPN, renderblocks.aoBrightnessXZPN, renderblocks.aoBrightnessXYZPPN, var22);
                renderblocks.brightnessBottomRight = renderblocks.getAoBrightness(renderblocks.aoBrightnessYZNN, renderblocks.aoBrightnessXYZPNN, renderblocks.aoBrightnessXZPN, var22);
                renderblocks.brightnessTopRight = renderblocks.getAoBrightness(renderblocks.aoBrightnessXYZNNN, renderblocks.aoBrightnessXZNN, renderblocks.aoBrightnessYZNN, var22);
            }
            else
            {
                var12 = renderblocks.aoLightValueZNeg;
                var11 = renderblocks.aoLightValueZNeg;
                var10 = renderblocks.aoLightValueZNeg;
                var9 = renderblocks.aoLightValueZNeg;
                renderblocks.brightnessTopLeft = renderblocks.brightnessBottomLeft = renderblocks.brightnessBottomRight = renderblocks.brightnessTopRight = var22;
            }



            renderblocks.colorRedTopLeft = renderblocks.colorRedBottomLeft = renderblocks.colorRedBottomRight = renderblocks.colorRedTopRight = (var18 ? par5 : 1.0F) * colorMult;
            renderblocks.colorGreenTopLeft = renderblocks.colorGreenBottomLeft = renderblocks.colorGreenBottomRight = renderblocks.colorGreenTopRight = (var18 ? par6 : 1.0F) * colorMult;
            renderblocks.colorBlueTopLeft = renderblocks.colorBlueBottomLeft = renderblocks.colorBlueBottomRight = renderblocks.colorBlueTopRight = (var18 ? par7 : 1.0F) * colorMult;

            texIndex = block.getBlockTexture(renderblocks.blockAccess, xCoord, yCoord, zCoord, 2);
            renderblocks.renderEastFace(block, (double)xCoord, (double)yCoord, (double)zCoord, texIndex);

            texIndex = getFruitTreeOverlay(renderblocks.blockAccess,xCoord,yCoord,zCoord);
            if(texIndex != -1)
            {
                renderblocks.colorRedTopLeft = renderblocks.colorRedBottomLeft = renderblocks.colorRedBottomRight = renderblocks.colorRedTopRight = ( 1.0F) * colorMult;
                renderblocks.colorGreenTopLeft = renderblocks.colorGreenBottomLeft = renderblocks.colorGreenBottomRight = renderblocks.colorGreenTopRight = ( 1.0F) * colorMult;
                renderblocks.colorBlueTopLeft = renderblocks.colorBlueBottomLeft = renderblocks.colorBlueBottomRight = renderblocks.colorBlueTopRight = ( 1.0F) * colorMult;
                renderblocks.renderEastFace(block, (double)xCoord, (double)yCoord, (double)zCoord, texIndex);
            }



            var8 = true;
        }

        if (renderblocks.renderAllFaces || block.shouldSideBeRendered(renderblocks.blockAccess, xCoord, yCoord, zCoord + 1, 3))
        {
            if (renderblocks.aoType > 0)
            {
                if (block.maxZ >= 1.0D)
                {
                    ++zCoord;
                }

                renderblocks.aoLightValueScratchXZNP = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord - 1, yCoord, zCoord);
                renderblocks.aoLightValueScratchXZPP = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord + 1, yCoord, zCoord);
                renderblocks.aoLightValueScratchYZNP = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord, yCoord - 1, zCoord);
                renderblocks.aoLightValueScratchYZPP = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord, yCoord + 1, zCoord);
                renderblocks.aoBrightnessXZNP = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord - 1, yCoord, zCoord);
                renderblocks.aoBrightnessXZPP = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord + 1, yCoord, zCoord);
                renderblocks.aoBrightnessYZNP = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord, yCoord - 1, zCoord);
                renderblocks.aoBrightnessYZPP = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord, yCoord + 1, zCoord);

                if (!renderblocks.aoGrassXYZNCP && !renderblocks.aoGrassXYZCNP)
                {
                    renderblocks.aoLightValueScratchXYZNNP = renderblocks.aoLightValueScratchXZNP;
                    renderblocks.aoBrightnessXYZNNP = renderblocks.aoBrightnessXZNP;
                }
                else
                {
                    renderblocks.aoLightValueScratchXYZNNP = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord - 1, yCoord - 1, zCoord);
                    renderblocks.aoBrightnessXYZNNP = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord - 1, yCoord - 1, zCoord);
                }

                if (!renderblocks.aoGrassXYZNCP && !renderblocks.aoGrassXYZCPP)
                {
                    renderblocks.aoLightValueScratchXYZNPP = renderblocks.aoLightValueScratchXZNP;
                    renderblocks.aoBrightnessXYZNPP = renderblocks.aoBrightnessXZNP;
                }
                else
                {
                    renderblocks.aoLightValueScratchXYZNPP = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord - 1, yCoord + 1, zCoord);
                    renderblocks.aoBrightnessXYZNPP = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord - 1, yCoord + 1, zCoord);
                }

                if (!renderblocks.aoGrassXYZPCP && !renderblocks.aoGrassXYZCNP)
                {
                    renderblocks.aoLightValueScratchXYZPNP = renderblocks.aoLightValueScratchXZPP;
                    renderblocks.aoBrightnessXYZPNP = renderblocks.aoBrightnessXZPP;
                }
                else
                {
                    renderblocks.aoLightValueScratchXYZPNP = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord + 1, yCoord - 1, zCoord);
                    renderblocks.aoBrightnessXYZPNP = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord + 1, yCoord - 1, zCoord);
                }

                if (!renderblocks.aoGrassXYZPCP && !renderblocks.aoGrassXYZCPP)
                {
                    renderblocks.aoLightValueScratchXYZPPP = renderblocks.aoLightValueScratchXZPP;
                    renderblocks.aoBrightnessXYZPPP = renderblocks.aoBrightnessXZPP;
                }
                else
                {
                    renderblocks.aoLightValueScratchXYZPPP = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord + 1, yCoord + 1, zCoord);
                    renderblocks.aoBrightnessXYZPPP = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord + 1, yCoord + 1, zCoord);
                }

                if (block.maxZ >= 1.0D)
                {
                    --zCoord;
                }

                var9 = (renderblocks.aoLightValueScratchXZNP + renderblocks.aoLightValueScratchXYZNPP + renderblocks.aoLightValueZPos + renderblocks.aoLightValueScratchYZPP) / 4.0F;
                var12 = (renderblocks.aoLightValueZPos + renderblocks.aoLightValueScratchYZPP + renderblocks.aoLightValueScratchXZPP + renderblocks.aoLightValueScratchXYZPPP) / 4.0F;
                var11 = (renderblocks.aoLightValueScratchYZNP + renderblocks.aoLightValueZPos + renderblocks.aoLightValueScratchXYZPNP + renderblocks.aoLightValueScratchXZPP) / 4.0F;
                var10 = (renderblocks.aoLightValueScratchXYZNNP + renderblocks.aoLightValueScratchXZNP + renderblocks.aoLightValueScratchYZNP + renderblocks.aoLightValueZPos) / 4.0F;
                renderblocks.brightnessTopLeft = renderblocks.getAoBrightness(renderblocks.aoBrightnessXZNP, renderblocks.aoBrightnessXYZNPP, renderblocks.aoBrightnessYZPP, var25);
                renderblocks.brightnessTopRight = renderblocks.getAoBrightness(renderblocks.aoBrightnessYZPP, renderblocks.aoBrightnessXZPP, renderblocks.aoBrightnessXYZPPP, var25);
                renderblocks.brightnessBottomRight = renderblocks.getAoBrightness(renderblocks.aoBrightnessYZNP, renderblocks.aoBrightnessXYZPNP, renderblocks.aoBrightnessXZPP, var25);
                renderblocks.brightnessBottomLeft = renderblocks.getAoBrightness(renderblocks.aoBrightnessXYZNNP, renderblocks.aoBrightnessXZNP, renderblocks.aoBrightnessYZNP, var25);
            }
            else
            {
                var12 = renderblocks.aoLightValueZPos;
                var11 = renderblocks.aoLightValueZPos;
                var10 = renderblocks.aoLightValueZPos;
                var9 = renderblocks.aoLightValueZPos;
                renderblocks.brightnessTopLeft = renderblocks.brightnessBottomLeft = renderblocks.brightnessBottomRight = renderblocks.brightnessTopRight = var25;
            }

            renderblocks.colorRedTopLeft = renderblocks.colorRedBottomLeft = renderblocks.colorRedBottomRight = renderblocks.colorRedTopRight = (var18 ? par5 : 1.0F) * colorMult;
            renderblocks.colorGreenTopLeft = renderblocks.colorGreenBottomLeft = renderblocks.colorGreenBottomRight = renderblocks.colorGreenTopRight = (var18 ? par6 : 1.0F) * colorMult;
            renderblocks.colorBlueTopLeft = renderblocks.colorBlueBottomLeft = renderblocks.colorBlueBottomRight = renderblocks.colorBlueTopRight = (var18 ? par7 : 1.0F) * colorMult;

            texIndex = block.getBlockTexture(renderblocks.blockAccess, xCoord, yCoord, zCoord, 3);
            renderblocks.renderWestFace(block, (double)xCoord, (double)yCoord, (double)zCoord, block.getBlockTexture(renderblocks.blockAccess, xCoord, yCoord, zCoord, 3));

            texIndex = getFruitTreeOverlay(renderblocks.blockAccess,xCoord,yCoord,zCoord);
            if(texIndex!= -1)
            {
                renderblocks.colorRedTopLeft = renderblocks.colorRedBottomLeft = renderblocks.colorRedBottomRight = renderblocks.colorRedTopRight = ( 1.0F) * colorMult;
                renderblocks.colorGreenTopLeft = renderblocks.colorGreenBottomLeft = renderblocks.colorGreenBottomRight = renderblocks.colorGreenTopRight = ( 1.0F) * colorMult;
                renderblocks.colorBlueTopLeft = renderblocks.colorBlueBottomLeft = renderblocks.colorBlueBottomRight = renderblocks.colorBlueTopRight = ( 1.0F) * colorMult;
                renderblocks.renderWestFace(block, (double)xCoord, (double)yCoord, (double)zCoord, texIndex);
            }

            var8 = true;
        }

        if (renderblocks.renderAllFaces || block.shouldSideBeRendered(renderblocks.blockAccess, xCoord - 1, yCoord, zCoord, 4))
        {
            if (renderblocks.aoType > 0)
            {
                if (block.minX <= 0.0D)
                {
                    --xCoord;
                }

                renderblocks.aoLightValueScratchXYNN = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord, yCoord - 1, zCoord);
                renderblocks.aoLightValueScratchXZNN = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord, yCoord, zCoord - 1);
                renderblocks.aoLightValueScratchXZNP = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord, yCoord, zCoord + 1);
                renderblocks.aoLightValueScratchXYNP = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord, yCoord + 1, zCoord);
                renderblocks.aoBrightnessXYNN = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord, yCoord - 1, zCoord);
                renderblocks.aoBrightnessXZNN = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord, yCoord, zCoord - 1);
                renderblocks.aoBrightnessXZNP = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord, yCoord, zCoord + 1);
                renderblocks.aoBrightnessXYNP = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord, yCoord + 1, zCoord);

                if (!renderblocks.aoGrassXYZNCN && !renderblocks.aoGrassXYZNNC)
                {
                    renderblocks.aoLightValueScratchXYZNNN = renderblocks.aoLightValueScratchXZNN;
                    renderblocks.aoBrightnessXYZNNN = renderblocks.aoBrightnessXZNN;
                }
                else
                {
                    renderblocks.aoLightValueScratchXYZNNN = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord, yCoord - 1, zCoord - 1);
                    renderblocks.aoBrightnessXYZNNN = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord, yCoord - 1, zCoord - 1);
                }

                if (!renderblocks.aoGrassXYZNCP && !renderblocks.aoGrassXYZNNC)
                {
                    renderblocks.aoLightValueScratchXYZNNP = renderblocks.aoLightValueScratchXZNP;
                    renderblocks.aoBrightnessXYZNNP = renderblocks.aoBrightnessXZNP;
                }
                else
                {
                    renderblocks.aoLightValueScratchXYZNNP = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord, yCoord - 1, zCoord + 1);
                    renderblocks.aoBrightnessXYZNNP = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord, yCoord - 1, zCoord + 1);
                }

                if (!renderblocks.aoGrassXYZNCN && !renderblocks.aoGrassXYZNPC)
                {
                    renderblocks.aoLightValueScratchXYZNPN = renderblocks.aoLightValueScratchXZNN;
                    renderblocks.aoBrightnessXYZNPN = renderblocks.aoBrightnessXZNN;
                }
                else
                {
                    renderblocks.aoLightValueScratchXYZNPN = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord, yCoord + 1, zCoord - 1);
                    renderblocks.aoBrightnessXYZNPN = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord, yCoord + 1, zCoord - 1);
                }

                if (!renderblocks.aoGrassXYZNCP && !renderblocks.aoGrassXYZNPC)
                {
                    renderblocks.aoLightValueScratchXYZNPP = renderblocks.aoLightValueScratchXZNP;
                    renderblocks.aoBrightnessXYZNPP = renderblocks.aoBrightnessXZNP;
                }
                else
                {
                    renderblocks.aoLightValueScratchXYZNPP = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord, yCoord + 1, zCoord + 1);
                    renderblocks.aoBrightnessXYZNPP = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord, yCoord + 1, zCoord + 1);
                }

                if (block.minX <= 0.0D)
                {
                    ++xCoord;
                }

                var12 = (renderblocks.aoLightValueScratchXYNN + renderblocks.aoLightValueScratchXYZNNP + renderblocks.aoLightValueXNeg + renderblocks.aoLightValueScratchXZNP) / 4.0F;
                var9 = (renderblocks.aoLightValueXNeg + renderblocks.aoLightValueScratchXZNP + renderblocks.aoLightValueScratchXYNP + renderblocks.aoLightValueScratchXYZNPP) / 4.0F;
                var10 = (renderblocks.aoLightValueScratchXZNN + renderblocks.aoLightValueXNeg + renderblocks.aoLightValueScratchXYZNPN + renderblocks.aoLightValueScratchXYNP) / 4.0F;
                var11 = (renderblocks.aoLightValueScratchXYZNNN + renderblocks.aoLightValueScratchXYNN + renderblocks.aoLightValueScratchXZNN + renderblocks.aoLightValueXNeg) / 4.0F;
                renderblocks.brightnessTopRight = renderblocks.getAoBrightness(renderblocks.aoBrightnessXYNN, renderblocks.aoBrightnessXYZNNP, renderblocks.aoBrightnessXZNP, var20);
                renderblocks.brightnessTopLeft = renderblocks.getAoBrightness(renderblocks.aoBrightnessXZNP, renderblocks.aoBrightnessXYNP, renderblocks.aoBrightnessXYZNPP, var20);
                renderblocks.brightnessBottomLeft = renderblocks.getAoBrightness(renderblocks.aoBrightnessXZNN, renderblocks.aoBrightnessXYZNPN, renderblocks.aoBrightnessXYNP, var20);
                renderblocks.brightnessBottomRight = renderblocks.getAoBrightness(renderblocks.aoBrightnessXYZNNN, renderblocks.aoBrightnessXYNN, renderblocks.aoBrightnessXZNN, var20);
            }
            else
            {
                var12 = renderblocks.aoLightValueXNeg;
                var11 = renderblocks.aoLightValueXNeg;
                var10 = renderblocks.aoLightValueXNeg;
                var9 = renderblocks.aoLightValueXNeg;
                renderblocks.brightnessTopLeft = renderblocks.brightnessBottomLeft = renderblocks.brightnessBottomRight = renderblocks.brightnessTopRight = var20;
            }


            renderblocks.colorRedTopLeft = renderblocks.colorRedBottomLeft = renderblocks.colorRedBottomRight = renderblocks.colorRedTopRight = (var18 ? par5 : 1.0F) * colorMult;
            renderblocks.colorGreenTopLeft = renderblocks.colorGreenBottomLeft = renderblocks.colorGreenBottomRight = renderblocks.colorGreenTopRight = (var18 ? par6 : 1.0F) * colorMult;
            renderblocks.colorBlueTopLeft = renderblocks.colorBlueBottomLeft = renderblocks.colorBlueBottomRight = renderblocks.colorBlueTopRight = (var18 ? par7 : 1.0F) * colorMult;

            texIndex = block.getBlockTexture(renderblocks.blockAccess, xCoord, yCoord, zCoord, 4);
            renderblocks.renderNorthFace(block, (double)xCoord, (double)yCoord, (double)zCoord, texIndex);

            texIndex = getFruitTreeOverlay(renderblocks.blockAccess,xCoord,yCoord,zCoord);
            if(texIndex!= -1)
            {
                renderblocks.colorRedTopLeft = renderblocks.colorRedBottomLeft = renderblocks.colorRedBottomRight = renderblocks.colorRedTopRight = ( 1.0F) * colorMult;
                renderblocks.colorGreenTopLeft = renderblocks.colorGreenBottomLeft = renderblocks.colorGreenBottomRight = renderblocks.colorGreenTopRight = ( 1.0F) * colorMult;
                renderblocks.colorBlueTopLeft = renderblocks.colorBlueBottomLeft = renderblocks.colorBlueBottomRight = renderblocks.colorBlueTopRight = ( 1.0F) * colorMult;
                renderblocks.renderNorthFace(block, (double)xCoord, (double)yCoord, (double)zCoord, texIndex);
            }

            var8 = true;
        }

        if (renderblocks.renderAllFaces || block.shouldSideBeRendered(renderblocks.blockAccess, xCoord + 1, yCoord, zCoord, 5))
        {
            if (renderblocks.aoType > 0)
            {
                if (block.maxX >= 1.0D)
                {
                    ++xCoord;
                }

                renderblocks.aoLightValueScratchXYPN = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord, yCoord - 1, zCoord);
                renderblocks.aoLightValueScratchXZPN = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord, yCoord, zCoord - 1);
                renderblocks.aoLightValueScratchXZPP = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord, yCoord, zCoord + 1);
                renderblocks.aoLightValueScratchXYPP = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord, yCoord + 1, zCoord);
                renderblocks.aoBrightnessXYPN = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord, yCoord - 1, zCoord);
                renderblocks.aoBrightnessXZPN = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord, yCoord, zCoord - 1);
                renderblocks.aoBrightnessXZPP = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord, yCoord, zCoord + 1);
                renderblocks.aoBrightnessXYPP = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord, yCoord + 1, zCoord);

                if (!renderblocks.aoGrassXYZPNC && !renderblocks.aoGrassXYZPCN)
                {
                    renderblocks.aoLightValueScratchXYZPNN = renderblocks.aoLightValueScratchXZPN;
                    renderblocks.aoBrightnessXYZPNN = renderblocks.aoBrightnessXZPN;
                }
                else
                {
                    renderblocks.aoLightValueScratchXYZPNN = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord, yCoord - 1, zCoord - 1);
                    renderblocks.aoBrightnessXYZPNN = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord, yCoord - 1, zCoord - 1);
                }

                if (!renderblocks.aoGrassXYZPNC && !renderblocks.aoGrassXYZPCP)
                {
                    renderblocks.aoLightValueScratchXYZPNP = renderblocks.aoLightValueScratchXZPP;
                    renderblocks.aoBrightnessXYZPNP = renderblocks.aoBrightnessXZPP;
                }
                else
                {
                    renderblocks.aoLightValueScratchXYZPNP = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord, yCoord - 1, zCoord + 1);
                    renderblocks.aoBrightnessXYZPNP = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord, yCoord - 1, zCoord + 1);
                }

                if (!renderblocks.aoGrassXYZPPC && !renderblocks.aoGrassXYZPCN)
                {
                    renderblocks.aoLightValueScratchXYZPPN = renderblocks.aoLightValueScratchXZPN;
                    renderblocks.aoBrightnessXYZPPN = renderblocks.aoBrightnessXZPN;
                }
                else
                {
                    renderblocks.aoLightValueScratchXYZPPN = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord, yCoord + 1, zCoord - 1);
                    renderblocks.aoBrightnessXYZPPN = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord, yCoord + 1, zCoord - 1);
                }

                if (!renderblocks.aoGrassXYZPPC && !renderblocks.aoGrassXYZPCP)
                {
                    renderblocks.aoLightValueScratchXYZPPP = renderblocks.aoLightValueScratchXZPP;
                    renderblocks.aoBrightnessXYZPPP = renderblocks.aoBrightnessXZPP;
                }
                else
                {
                    renderblocks.aoLightValueScratchXYZPPP = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord, yCoord + 1, zCoord + 1);
                    renderblocks.aoBrightnessXYZPPP = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord, yCoord + 1, zCoord + 1);
                }

                if (block.maxX >= 1.0D)
                {
                    --xCoord;
                }

                var9 = (renderblocks.aoLightValueScratchXYPN + renderblocks.aoLightValueScratchXYZPNP + renderblocks.aoLightValueXPos + renderblocks.aoLightValueScratchXZPP) / 4.0F;
                var12 = (renderblocks.aoLightValueXPos + renderblocks.aoLightValueScratchXZPP + renderblocks.aoLightValueScratchXYPP + renderblocks.aoLightValueScratchXYZPPP) / 4.0F;
                var11 = (renderblocks.aoLightValueScratchXZPN + renderblocks.aoLightValueXPos + renderblocks.aoLightValueScratchXYZPPN + renderblocks.aoLightValueScratchXYPP) / 4.0F;
                var10 = (renderblocks.aoLightValueScratchXYZPNN + renderblocks.aoLightValueScratchXYPN + renderblocks.aoLightValueScratchXZPN + renderblocks.aoLightValueXPos) / 4.0F;
                renderblocks.brightnessTopLeft = renderblocks.getAoBrightness(renderblocks.aoBrightnessXYPN, renderblocks.aoBrightnessXYZPNP, renderblocks.aoBrightnessXZPP, var23);
                renderblocks.brightnessTopRight = renderblocks.getAoBrightness(renderblocks.aoBrightnessXZPP, renderblocks.aoBrightnessXYPP, renderblocks.aoBrightnessXYZPPP, var23);
                renderblocks.brightnessBottomRight = renderblocks.getAoBrightness(renderblocks.aoBrightnessXZPN, renderblocks.aoBrightnessXYZPPN, renderblocks.aoBrightnessXYPP, var23);
                renderblocks.brightnessBottomLeft = renderblocks.getAoBrightness(renderblocks.aoBrightnessXYZPNN, renderblocks.aoBrightnessXYPN, renderblocks.aoBrightnessXZPN, var23);
            }
            else
            {
                var12 = renderblocks.aoLightValueXPos;
                var11 = renderblocks.aoLightValueXPos;
                var10 = renderblocks.aoLightValueXPos;
                var9 = renderblocks.aoLightValueXPos;
                renderblocks.brightnessTopLeft = renderblocks.brightnessBottomLeft = renderblocks.brightnessBottomRight = renderblocks.brightnessTopRight = var23;
            }

            renderblocks.colorRedTopLeft = renderblocks.colorRedBottomLeft = renderblocks.colorRedBottomRight = renderblocks.colorRedTopRight = (var18 ? par5 : 1.0F) * colorMult;
            renderblocks.colorGreenTopLeft = renderblocks.colorGreenBottomLeft = renderblocks.colorGreenBottomRight = renderblocks.colorGreenTopRight = (var18 ? par6 : 1.0F) * colorMult;
            renderblocks.colorBlueTopLeft = renderblocks.colorBlueBottomLeft = renderblocks.colorBlueBottomRight = renderblocks.colorBlueTopRight = (var18 ? par7 : 1.0F) * colorMult;

            texIndex = block.getBlockTexture(renderblocks.blockAccess, xCoord, yCoord, zCoord, 5);
            renderblocks.renderSouthFace(block, (double)xCoord, (double)yCoord, (double)zCoord, texIndex);

            texIndex = getFruitTreeOverlay(renderblocks.blockAccess,xCoord,yCoord,zCoord);
            if(texIndex!= -1)
            {
                renderblocks.colorRedTopLeft = renderblocks.colorRedBottomLeft = renderblocks.colorRedBottomRight = renderblocks.colorRedTopRight = ( 1.0F) * colorMult;
                renderblocks.colorGreenTopLeft = renderblocks.colorGreenBottomLeft = renderblocks.colorGreenBottomRight = renderblocks.colorGreenTopRight = ( 1.0F) * colorMult;
                renderblocks.colorBlueTopLeft = renderblocks.colorBlueBottomLeft = renderblocks.colorBlueBottomRight = renderblocks.colorBlueTopRight = ( 1.0F) * colorMult;
                renderblocks.renderSouthFace(block, (double)xCoord, (double)yCoord, (double)zCoord, texIndex);
            }

            var8 = true;
        }

        renderblocks.enableAO = false;
        return var8;
    }

    public static int getFruitTreeOverlay(IBlockAccess world, int x, int y, int z)
    {
        int out = -1;
        int meta = world.getBlockMetadata(x, y, z);

        FloraManager manager = FloraManager.getInstance();
        FloraIndex index = manager.findMatchingIndex(BlockFruitLeaves.getType(world.getBlockId(x, y, z), meta & 7));
        if(index != null)
        {
            if(index.inBloom(TFCSeasons.currentMonth))//blooming
            {
                out = 96+(meta & 7);
            }
            else if(index.inHarvest(TFCSeasons.currentMonth) && meta >= 8)//fruit
            {
                out = 80+(meta & 7);
            }
        }
        return out;
    }

    public static boolean RenderToolRack(Block block, int i, int j, int k, RenderBlocks renderblocks)
    {
        IBlockAccess blockAccess = renderblocks.blockAccess;


        block.setBlockBounds(0.0F, 0.0F, 0.0F, 1F, 1F, 1F);
        return true;
    }

    /**
     * Renders a block based on the BlockFluids class at the given coordinates
     */
    public static boolean RenderFiniteWater(Block par1Block, int par2, int par3, int par4, RenderBlocks renderblocks)
    {
        Tessellator var5 = Tessellator.instance;
        int var6 = par1Block.colorMultiplier(renderblocks.blockAccess, par2, par3, par4);
        float var7 = (float)(var6 >> 16 & 255) / 255.0F;
        float var8 = (float)(var6 >> 8 & 255) / 255.0F;
        float var9 = (float)(var6 & 255) / 255.0F;
        boolean var10 = par1Block.shouldSideBeRendered(renderblocks.blockAccess, par2, par3 + 1, par4, 1);
        boolean var11 = par1Block.shouldSideBeRendered(renderblocks.blockAccess, par2, par3 - 1, par4, 0);
        boolean[] var12 = new boolean[] {par1Block.shouldSideBeRendered(renderblocks.blockAccess, par2, par3, par4 - 1, 2), par1Block.shouldSideBeRendered(renderblocks.blockAccess, par2, par3, par4 + 1, 3), par1Block.shouldSideBeRendered(renderblocks.blockAccess, par2 - 1, par3, par4, 4), par1Block.shouldSideBeRendered(renderblocks.blockAccess, par2 + 1, par3, par4, 5)};

        if (!var10 && !var11 && !var12[0] && !var12[1] && !var12[2] && !var12[3])
        {
            return false;
        }
        else
        {
            boolean var13 = false;
            float var14 = 0.5F;
            float var15 = 1.0F;
            float var16 = 0.8F;
            float var17 = 0.6F;
            double var18 = 0.0D;
            double var20 = 1.0D;
            Material var22 = par1Block.blockMaterial;
            int var23 = renderblocks.blockAccess.getBlockMetadata(par2, par3, par4);
            double var24 = (double)renderblocks.getFluidHeight(par2, par3, par4, var22);
            double var26 = (double)renderblocks.getFluidHeight(par2, par3, par4 + 1, var22);
            double var28 = (double)renderblocks.getFluidHeight(par2 + 1, par3, par4 + 1, var22);
            double var30 = (double)renderblocks.getFluidHeight(par2 + 1, par3, par4, var22);
            double var32 = 0.0010000000474974513D;
            int var34;
            int var35;
            float var36;
            int var37;
            double var42;
            double var40;
            double var44;

            if (renderblocks.renderAllFaces || var10)
            {
                var13 = true;
                var34 = par1Block.getBlockTextureFromSideAndMetadata(1, var23);
                var36 = (float)BlockFiniteWater.func_293_a(renderblocks.blockAccess, par2, par3, par4, var22);

                if (var36 > -999.0F)
                {
                    var34 = par1Block.getBlockTextureFromSideAndMetadata(2, var23);
                }

                var24 -= var32;
                var26 -= var32;
                var28 -= var32;
                var30 -= var32;
                var37 = (var34 & 15) << 4;
                var35 = var34 & 240;
                double var38 = ((double)var37 + 8.0D) / 256.0D;
                var40 = ((double)var35 + 8.0D) / 256.0D;

                if (var36 < -999.0F)
                {
                    var36 = 0.0F;
                }
                else
                {
                    var38 = (double)((float)(var37 + 16) / 256.0F);
                    var40 = (double)((float)(var35 + 16) / 256.0F);
                }

                var42 = (double)(MathHelper.sin(var36) * 8.0F) / 256.0D;
                var44 = (double)(MathHelper.cos(var36) * 8.0F) / 256.0D;
                var5.setBrightness(par1Block.getMixedBrightnessForBlock(renderblocks.blockAccess, par2, par3, par4));
                float var46 = 1.0F;
                var5.setColorOpaque_F(var15 * var46 * var7, var15 * var46 * var8, var15 * var46 * var9);
                var5.addVertexWithUV((double)(par2 + 0), (double)par3 + var24, (double)(par4 + 0), var38 - var44 - var42, var40 - var44 + var42);
                var5.addVertexWithUV((double)(par2 + 0), (double)par3 + var26, (double)(par4 + 1), var38 - var44 + var42, var40 + var44 + var42);
                var5.addVertexWithUV((double)(par2 + 1), (double)par3 + var28, (double)(par4 + 1), var38 + var44 + var42, var40 + var44 - var42);
                var5.addVertexWithUV((double)(par2 + 1), (double)par3 + var30, (double)(par4 + 0), var38 + var44 - var42, var40 - var44 - var42);
            }

            if (renderblocks.renderAllFaces || var11)
            {
                var5.setBrightness(par1Block.getMixedBrightnessForBlock(renderblocks.blockAccess, par2, par3 - 1, par4));
                var36 = 1.0F;
                var5.setColorOpaque_F(var14 * var36, var14 * var36, var14 * var36);
                renderblocks.renderBottomFace(par1Block, (double)par2, (double)par3 + var32, (double)par4, par1Block.getBlockTextureFromSide(0));
                var13 = true;
            }

            for (var34 = 0; var34 < 4; ++var34)
            {
                int var64 = par2;
                var35 = par4;

                if (var34 == 0)
                {
                    var35 = par4 - 1;
                }

                if (var34 == 1)
                {
                    ++var35;
                }

                if (var34 == 2)
                {
                    var64 = par2 - 1;
                }

                if (var34 == 3)
                {
                    ++var64;
                }

                var37 = par1Block.getBlockTextureFromSideAndMetadata(var34 + 2, var23);
                int var63 = (var37 & 15) << 4;
                int var39 = var37 & 240;

                if (renderblocks.renderAllFaces || var12[var34])
                {
                    double var65;
                    double var50;
                    double var48;

                    if (var34 == 0)
                    {
                        var42 = var24;
                        var40 = var30;
                        var65 = (double)par2;
                        var50 = (double)(par2 + 1);
                        var44 = (double)par4 + var32;
                        var48 = (double)par4 + var32;
                    }
                    else if (var34 == 1)
                    {
                        var42 = var28;
                        var40 = var26;
                        var65 = (double)(par2 + 1);
                        var50 = (double)par2;
                        var44 = (double)(par4 + 1) - var32;
                        var48 = (double)(par4 + 1) - var32;
                    }
                    else if (var34 == 2)
                    {
                        var42 = var26;
                        var40 = var24;
                        var65 = (double)par2 + var32;
                        var50 = (double)par2 + var32;
                        var44 = (double)(par4 + 1);
                        var48 = (double)par4;
                    }
                    else
                    {
                        var42 = var30;
                        var40 = var28;
                        var65 = (double)(par2 + 1) - var32;
                        var50 = (double)(par2 + 1) - var32;
                        var44 = (double)par4;
                        var48 = (double)(par4 + 1);
                    }

                    var13 = true;
                    double var52 = (double)((float)(var63 + 0) / 256.0F);
                    double var54 = ((double)(var63 + 16) - 0.01D) / 256.0D;
                    double var56 = ((double)var39 + (1.0D - var42) * 16.0D) / 256.0D;
                    double var58 = ((double)var39 + (1.0D - var40) * 16.0D) / 256.0D;
                    double var60 = ((double)(var39 + 16) - 0.01D) / 256.0D;
                    var5.setBrightness(par1Block.getMixedBrightnessForBlock(renderblocks.blockAccess, var64, par3, var35));
                    float var62 = 1.0F;

                    if (var34 < 2)
                    {
                        var62 *= var16;
                    }
                    else
                    {
                        var62 *= var17;
                    }

                    var5.setColorOpaque_F(var15 * var62 * var7, var15 * var62 * var8, var15 * var62 * var9);
                    var5.addVertexWithUV(var65, (double)par3 + var42, var44, var52, var56);
                    var5.addVertexWithUV(var50, (double)par3 + var40, var48, var54, var58);
                    var5.addVertexWithUV(var50, (double)(par3 + 0), var48, var54, var60);
                    var5.addVertexWithUV(var65, (double)(par3 + 0), var44, var52, var60);
                }
            }

            par1Block.minY = var18;
            par1Block.maxY = var20;
            return var13;
        }
    }
  
    public static WavefrontObject[] TomatoPlant;
    
    public static boolean RenderCrop(Block block, int i, int j, int k, RenderBlocks renderblocks)
    {
        IBlockAccess blockaccess = renderblocks.blockAccess;
        TileEntityCrop te = (TileEntityCrop)blockaccess.getBlockTileEntity(i, j, k);
        CropIndex crop = CropManager.getInstance().getCropFromId(te.cropId);
        Minecraft mc = ModLoader.getMinecraftInstance();
        
        int stage = (int) Math.floor(te.growth);
        if(stage > crop.numGrowthStages)
            stage = crop.numGrowthStages;
        
        int meta = blockaccess.getBlockMetadata(i, j, k);
        
        float est = te.getEstimatedGrowth(crop);
        float mult = 0.85f + (0.15f * (te.growth / est));
        if(mult > 1.15f) {mult = 1.15f;}
        
        switch(te.cropId)
        {
            case 0://Wheat
            case 1://Wild Wheat
            {
                byte index = (byte) (16 + stage);
                
                renderBlockCropsImpl(block, (double)i, (double)j, (double)k,renderblocks, index, mult, 1.0);
                break;
            }
            case 2://Corn
            case 3://Wild Corn
            {
                byte index = (byte) (96 + stage);
                drawCrossedSquares(block, i, j, k, index, mult, 2.0);
                break;
            }
            case 4://Tomato
            {                
                byte index = (byte) (240 + stage);
                drawCrossedSquares(block, i, j, k, index, 1.0f, 2.0);
                break;
            }
            case 5://Barley
            case 6://Wild Barley
            {
                byte index = (byte) (0 + stage);
                
                renderBlockCropsImpl(block, (double)i, (double)j, (double)k,renderblocks, index, mult, 1.0);
                break;
            }
            case 7://Rye
            case 8://Wild Rye
            {
                byte index = (byte) (32 + stage);
                
                renderBlockCropsImpl(block, (double)i, (double)j, (double)k,renderblocks, index, mult, 1.0);
                break;
            }
            case 9://Oat
            case 10://Wild Oat
            {
                byte index = (byte) (48 + stage);
                
                renderBlockCropsImpl(block, (double)i, (double)j, (double)k,renderblocks, index, mult, 1.0);
                break;
            }
            case 11://Rice
            case 12://Wild Rice
            {
                byte index = (byte) (64 + stage);
                
                renderBlockCropsImpl(block, (double)i, (double)j, (double)k,renderblocks, index, mult, 1.0);
                break;
            }
            case 13://Potato
            case 14://Wild Potato
            {                
                byte index = (byte) (112 + stage);
                drawCrossedSquares(block, i, j, k, index, mult, 1.0);
                break;
            }
            case 15://Onion
            {                
                byte index = (byte) (128 + stage);
                drawCrossedSquares(block, i, j, k, index, mult, 1.0);
                break;
            }
            case 16://Cabbage
            {                
                byte index = (byte) (144 + stage);
                drawCrossedSquares(block, i, j, k, index, mult, 1.0);
                break;
            }
            case 17://Garlic
            {                
                byte index = (byte) (213 + stage);
                drawCrossedSquares(block, i, j, k, index, mult, 1.0);
                break;
            }
            case 18://Carrots
            {                
                byte index = (byte) (208 + stage);
                drawCrossedSquares(block, i, j, k, index, mult, 1.0);
                break;
            }
            case 19://Sugarcane
            {                
                byte index = (byte) (218 + stage);
                drawCrossedSquares(block, i, j, k, index, mult, 1.0);
                break;
            }
            case 20://Hemp
            {                
                byte index = (byte) (192 + stage);
                drawCrossedSquares(block, i, j, k, index, mult, 3.0);
                break;
            }
            default:
            {
                renderblocks.renderBlockCrops(block, i, j, k);
                break;
            }
        }

        return true;
    }
    
    public static void renderBlockCropsImpl(Block par1Block, double par3, double par5, double par7, RenderBlocks renderblocks, byte index, float heightMult, double height)
    {
        Tessellator var9 = Tessellator.instance;

        int texX = (index & 15) << 4;
        int texY = (index+(16-(int)(16*height))) & 240;
        
        double var13 = (double)((float)texX / 256.0F);
        double var15 = (double)(((float)texX + 15.99F) / 256.0F);
        double var17 = (double)((float)texY / 256.0F);
        double var19 = (double)(((float)texY + 15.99F) / 256.0F);
        double var21 = par3 + 0.5D - 0.25D;
        double var23 = par3 + 0.5D + 0.25D;
        double var25 = par7 + 0.5D - 0.5D;
        double var27 = par7 + 0.5D + 0.5D;
        
        double y = par5;        
        
        var9.addVertexWithUV(var21, y + (height*heightMult), var25, var13, var17);
        var9.addVertexWithUV(var21, y + 0.0D, var25, var13, var19);
        var9.addVertexWithUV(var21, y + 0.0D, var27, var15, var19);
        var9.addVertexWithUV(var21, y + (height*heightMult), var27, var15, var17);
        var9.addVertexWithUV(var21, y + (height*heightMult), var27, var13, var17);
        var9.addVertexWithUV(var21, y + 0.0D, var27, var13, var19);
        var9.addVertexWithUV(var21, y + 0.0D, var25, var15, var19);
        var9.addVertexWithUV(var21, y + (height*heightMult), var25, var15, var17);
        var9.addVertexWithUV(var23, y + (height*heightMult), var27, var13, var17);
        var9.addVertexWithUV(var23, y + 0.0D, var27, var13, var19);
        var9.addVertexWithUV(var23, y + 0.0D, var25, var15, var19);
        var9.addVertexWithUV(var23, y + (height*heightMult), var25, var15, var17);
        var9.addVertexWithUV(var23, y + (height*heightMult), var25, var13, var17);
        var9.addVertexWithUV(var23, y + 0.0D, var25, var13, var19);
        var9.addVertexWithUV(var23, y + 0.0D, var27, var15, var19);
        var9.addVertexWithUV(var23, y + (height*heightMult), var27, var15, var17);
        var21 = par3 + 0.5D - 0.5D;
        var23 = par3 + 0.5D + 0.5D;
        var25 = par7 + 0.5D - 0.25D;
        var27 = par7 + 0.5D + 0.25D;
        var9.addVertexWithUV(var21, y + (height*heightMult), var25, var13, var17);
        var9.addVertexWithUV(var21, y + 0.0D, var25, var13, var19);
        var9.addVertexWithUV(var23, y + 0.0D, var25, var15, var19);
        var9.addVertexWithUV(var23, y + (height*heightMult), var25, var15, var17);
        var9.addVertexWithUV(var23, y + (height*heightMult), var25, var13, var17);
        var9.addVertexWithUV(var23, y + 0.0D, var25, var13, var19);
        var9.addVertexWithUV(var21, y + 0.0D, var25, var15, var19);
        var9.addVertexWithUV(var21, y + (height*heightMult), var25, var15, var17);
        var9.addVertexWithUV(var23, y + (height*heightMult), var27, var13, var17);
        var9.addVertexWithUV(var23, y + 0.0D, var27, var13, var19);
        var9.addVertexWithUV(var21, y + 0.0D, var27, var15, var19);
        var9.addVertexWithUV(var21, y + (height*heightMult), var27, var15, var17);
        var9.addVertexWithUV(var21, y + (height*heightMult), var27, var13, var17);
        var9.addVertexWithUV(var21, y + 0.0D, var27, var13, var19);
        var9.addVertexWithUV(var23, y + 0.0D, var27, var15, var19);
        var9.addVertexWithUV(var23, y + (height*heightMult), var27, var15, var17);
    }

    public static void drawCrossedSquares(Block par1Block, double par3, double par5, double par7, byte index, float heightMult, double height)
    {
        Tessellator var9 = Tessellator.instance;

        var9.setColorOpaque_F(1.0f, 1.0f, 1.0f);
        int texX = (index & 15) << 4;
        int texY = index & 240;
        int texY2 = (index+(16-(int)(16*height))) & 240;
        
        double minX = (double)((float)texX / 256.0F);
        double maxX = (double)(((float)texX + 15.99F) / 256.0F);
        double minY = (double)((float)texY2 / 256.0F);
        double maxY = (double)(((float)texY + 15.99F) / 256.0F);
        
        double var21 = par3 + 0.5D - 0.45D;
        double var23 = par3 + 0.5D + 0.45D;
        double var25 = par7 + 0.5D - 0.45D;
        double var27 = par7 + 0.5D + 0.45D;
        
        double y = par5;    
        
        var9.addVertexWithUV(var21, y + (height*heightMult), var25, minX, minY);
        var9.addVertexWithUV(var21, y + 0.0D, var25, minX, maxY);
        var9.addVertexWithUV(var23, y + 0.0D, var27, maxX, maxY);
        var9.addVertexWithUV(var23, y + (height*heightMult), var27, maxX, minY);
        
        var9.addVertexWithUV(var23, y + (height*heightMult), var27, minX, minY);
        var9.addVertexWithUV(var23, y + 0.0D, var27, minX, maxY);
        var9.addVertexWithUV(var21, y + 0.0D, var25, maxX, maxY);
        var9.addVertexWithUV(var21, y + (height*heightMult), var25, maxX, minY);
        
        var9.addVertexWithUV(var21, y + (height*heightMult), var27, minX, minY);
        var9.addVertexWithUV(var21, y + 0.0D, var27, minX, maxY);
        var9.addVertexWithUV(var23, y + 0.0D, var25, maxX, maxY);
        var9.addVertexWithUV(var23, y + (height*heightMult), var25, maxX, minY);
        
        var9.addVertexWithUV(var23, y + (height*heightMult), var25, minX, minY);
        var9.addVertexWithUV(var23, y + 0.0D, var25, minX, maxY);
        var9.addVertexWithUV(var21, y + 0.0D, var27, maxX, maxY);
        var9.addVertexWithUV(var21, y + (height*heightMult), var27, maxX, minY);
    }
  
    public static boolean RenderNewLeaves(Block block, int xCoord, int yCoord, int zCoord,float par5, float par6, float par7, RenderBlocks renderblocks, boolean fancy, boolean firstTry)
    {
        BiomeGenBase biome = renderblocks.blockAccess.getBiomeGenForCoords(xCoord, zCoord);
        int meta = renderblocks.blockAccess.getBlockMetadata(xCoord, yCoord, zCoord);
        
        boolean renderAll = true;
        
        renderblocks.enableAO = true;
        boolean var8 = false;
        float var9 = renderblocks.lightValueOwn;
        float var10 = renderblocks.lightValueOwn;
        float var11 = renderblocks.lightValueOwn;
        float var12 = renderblocks.lightValueOwn;
        boolean var13 = true;
        boolean var14 = true;
        boolean var15 = true;
        boolean var16 = true;
        boolean var17 = true;
        boolean var18 = true;
        renderblocks.lightValueOwn = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord, yCoord, zCoord);
        renderblocks.aoLightValueXNeg = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord - 1, yCoord, zCoord);
        renderblocks.aoLightValueYNeg = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord, yCoord - 1, zCoord);
        renderblocks.aoLightValueZNeg = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord, yCoord, zCoord - 1);
        renderblocks.aoLightValueXPos = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord + 1, yCoord, zCoord);
        renderblocks.aoLightValueYPos = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord, yCoord + 1, zCoord);
        renderblocks.aoLightValueZPos = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord, yCoord, zCoord + 1);
        int var19 = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord, yCoord, zCoord);
        int var20 = var19;
        int var21 = var19;
        int var22 = var19;
        int var23 = var19;
        int var24 = var19;
        int var25 = var19;

        if (block.minY <= 0.0D)
        {
            var21 = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord, yCoord - 1, zCoord);
        }

        if (block.maxY >= 1.0D)
        {
            var24 = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord, yCoord + 1, zCoord);
        }

        if (block.minX <= 0.0D)
        {
            var20 = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord - 1, yCoord, zCoord);
        }

        if (block.maxX >= 1.0D)
        {
            var23 = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord + 1, yCoord, zCoord);
        }

        if (block.minZ <= 0.0D)
        {
            var22 = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord, yCoord, zCoord - 1);
        }

        if (block.maxZ >= 1.0D)
        {
            var25 = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord, yCoord, zCoord + 1);
        }

        Tessellator tesselator = Tessellator.instance;
        tesselator.setBrightness(983055);
        renderblocks.aoGrassXYZPPC = Block.canBlockGrass[renderblocks.blockAccess.getBlockId(xCoord + 1, yCoord + 1, zCoord)];
        renderblocks.aoGrassXYZPNC = Block.canBlockGrass[renderblocks.blockAccess.getBlockId(xCoord + 1, yCoord - 1, zCoord)];
        renderblocks.aoGrassXYZPCP = Block.canBlockGrass[renderblocks.blockAccess.getBlockId(xCoord + 1, yCoord, zCoord + 1)];
        renderblocks.aoGrassXYZPCN = Block.canBlockGrass[renderblocks.blockAccess.getBlockId(xCoord + 1, yCoord, zCoord - 1)];
        renderblocks.aoGrassXYZNPC = Block.canBlockGrass[renderblocks.blockAccess.getBlockId(xCoord - 1, yCoord + 1, zCoord)];
        renderblocks.aoGrassXYZNNC = Block.canBlockGrass[renderblocks.blockAccess.getBlockId(xCoord - 1, yCoord - 1, zCoord)];
        renderblocks.aoGrassXYZNCN = Block.canBlockGrass[renderblocks.blockAccess.getBlockId(xCoord - 1, yCoord, zCoord - 1)];
        renderblocks.aoGrassXYZNCP = Block.canBlockGrass[renderblocks.blockAccess.getBlockId(xCoord - 1, yCoord, zCoord + 1)];
        renderblocks.aoGrassXYZCPP = Block.canBlockGrass[renderblocks.blockAccess.getBlockId(xCoord, yCoord + 1, zCoord + 1)];
        renderblocks.aoGrassXYZCPN = Block.canBlockGrass[renderblocks.blockAccess.getBlockId(xCoord, yCoord + 1, zCoord - 1)];
        renderblocks.aoGrassXYZCNP = Block.canBlockGrass[renderblocks.blockAccess.getBlockId(xCoord, yCoord - 1, zCoord + 1)];
        renderblocks.aoGrassXYZCNN = Block.canBlockGrass[renderblocks.blockAccess.getBlockId(xCoord, yCoord - 1, zCoord - 1)];

        int texIndex;
        float colorMult = 0.78F;

        boolean xInRange = xCoord > ModLoader.getMinecraftInstance().renderViewEntity.posX-TFCSettings.leavesLOD && xCoord < ModLoader.getMinecraftInstance().renderViewEntity.posX+TFCSettings.leavesLOD;
        boolean yInRange = yCoord > ModLoader.getMinecraftInstance().renderViewEntity.posY-TFCSettings.leavesLOD && yCoord < ModLoader.getMinecraftInstance().renderViewEntity.posY+TFCSettings.leavesLOD;
        boolean zInRange = zCoord > ModLoader.getMinecraftInstance().renderViewEntity.posZ-TFCSettings.leavesLOD && zCoord < ModLoader.getMinecraftInstance().renderViewEntity.posZ+TFCSettings.leavesLOD;

        if (renderAll || (xInRange && yInRange && zInRange) || (!firstTry &&
                block.shouldSideBeRendered(renderblocks.blockAccess, xCoord, yCoord-1, zCoord, 0)))
        {
            if (renderblocks.aoType > 0)
            {
                if (block.minY <= 0.0D)
                {
                    --yCoord;
                }

                renderblocks.aoBrightnessXYNN = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord - 1, yCoord, zCoord);
                renderblocks.aoBrightnessYZNN = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord, yCoord, zCoord - 1);
                renderblocks.aoBrightnessYZNP = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord, yCoord, zCoord + 1);
                renderblocks.aoBrightnessXYPN = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord + 1, yCoord, zCoord);
                renderblocks.aoLightValueScratchXYNN = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord - 1, yCoord, zCoord);
                renderblocks.aoLightValueScratchYZNN = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord, yCoord, zCoord - 1);
                renderblocks.aoLightValueScratchYZNP = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord, yCoord, zCoord + 1);
                renderblocks.aoLightValueScratchXYPN = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord + 1, yCoord, zCoord);

                if (!renderblocks.aoGrassXYZCNN && !renderblocks.aoGrassXYZNNC)
                {
                    renderblocks.aoLightValueScratchXYZNNN = renderblocks.aoLightValueScratchXYNN;
                    renderblocks.aoBrightnessXYZNNN = renderblocks.aoBrightnessXYNN;
                }
                else
                {
                    renderblocks.aoLightValueScratchXYZNNN = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord - 1, yCoord, zCoord - 1);
                    renderblocks.aoBrightnessXYZNNN = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord - 1, yCoord, zCoord - 1);
                }

                if (!renderblocks.aoGrassXYZCNP && !renderblocks.aoGrassXYZNNC)
                {
                    renderblocks.aoLightValueScratchXYZNNP = renderblocks.aoLightValueScratchXYNN;
                    renderblocks.aoBrightnessXYZNNP = renderblocks.aoBrightnessXYNN;
                }
                else
                {
                    renderblocks.aoLightValueScratchXYZNNP = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord - 1, yCoord, zCoord + 1);
                    renderblocks.aoBrightnessXYZNNP = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord - 1, yCoord, zCoord + 1);
                }

                if (!renderblocks.aoGrassXYZCNN && !renderblocks.aoGrassXYZPNC)
                {
                    renderblocks.aoLightValueScratchXYZPNN = renderblocks.aoLightValueScratchXYPN;
                    renderblocks.aoBrightnessXYZPNN = renderblocks.aoBrightnessXYPN;
                }
                else
                {
                    renderblocks.aoLightValueScratchXYZPNN = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord + 1, yCoord, zCoord - 1);
                    renderblocks.aoBrightnessXYZPNN = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord + 1, yCoord, zCoord - 1);
                }

                if (!renderblocks.aoGrassXYZCNP && !renderblocks.aoGrassXYZPNC)
                {
                    renderblocks.aoLightValueScratchXYZPNP = renderblocks.aoLightValueScratchXYPN;
                    renderblocks.aoBrightnessXYZPNP = renderblocks.aoBrightnessXYPN;
                }
                else
                {
                    renderblocks.aoLightValueScratchXYZPNP = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord + 1, yCoord, zCoord + 1);
                    renderblocks.aoBrightnessXYZPNP = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord + 1, yCoord, zCoord + 1);
                }

                if (block.minY <= 0.0D)
                {
                    ++yCoord;
                }

                var9 = (renderblocks.aoLightValueScratchXYZNNP + renderblocks.aoLightValueScratchXYNN + renderblocks.aoLightValueScratchYZNP + renderblocks.aoLightValueYNeg) / 4.0F;
                var12 = (renderblocks.aoLightValueScratchYZNP + renderblocks.aoLightValueYNeg + renderblocks.aoLightValueScratchXYZPNP + renderblocks.aoLightValueScratchXYPN) / 4.0F;
                var11 = (renderblocks.aoLightValueYNeg + renderblocks.aoLightValueScratchYZNN + renderblocks.aoLightValueScratchXYPN + renderblocks.aoLightValueScratchXYZPNN) / 4.0F;
                var10 = (renderblocks.aoLightValueScratchXYNN + renderblocks.aoLightValueScratchXYZNNN + renderblocks.aoLightValueYNeg + renderblocks.aoLightValueScratchYZNN) / 4.0F;
                renderblocks.brightnessTopLeft = renderblocks.getAoBrightness(renderblocks.aoBrightnessXYZNNP, renderblocks.aoBrightnessXYNN, renderblocks.aoBrightnessYZNP, var21);
                renderblocks.brightnessTopRight = renderblocks.getAoBrightness(renderblocks.aoBrightnessYZNP, renderblocks.aoBrightnessXYZPNP, renderblocks.aoBrightnessXYPN, var21);
                renderblocks.brightnessBottomRight = renderblocks.getAoBrightness(renderblocks.aoBrightnessYZNN, renderblocks.aoBrightnessXYPN, renderblocks.aoBrightnessXYZPNN, var21);
                renderblocks.brightnessBottomLeft = renderblocks.getAoBrightness(renderblocks.aoBrightnessXYNN, renderblocks.aoBrightnessXYZNNN, renderblocks.aoBrightnessYZNN, var21);
            }
            else
            {
                var12 = renderblocks.aoLightValueYNeg;
                var11 = renderblocks.aoLightValueYNeg;
                var10 = renderblocks.aoLightValueYNeg;
                var9 = renderblocks.aoLightValueYNeg;
                renderblocks.brightnessTopLeft = renderblocks.brightnessBottomLeft = renderblocks.brightnessBottomRight = renderblocks.brightnessTopRight = renderblocks.aoBrightnessXYNN;
            }

            renderblocks.colorRedTopLeft = renderblocks.colorRedBottomLeft = renderblocks.colorRedBottomRight = renderblocks.colorRedTopRight = (var18 ? par5 : 1.0F) * colorMult;
            renderblocks.colorGreenTopLeft = renderblocks.colorGreenBottomLeft = renderblocks.colorGreenBottomRight = renderblocks.colorGreenTopRight = (var18 ? par6 : 1.0F) * colorMult;
            renderblocks.colorBlueTopLeft = renderblocks.colorBlueBottomLeft = renderblocks.colorBlueBottomRight = renderblocks.colorBlueTopRight = (var18 ? par7 : 1.0F) * colorMult;

            renderblocks.renderBottomFace(block, (double)xCoord, (double)yCoord, (double)zCoord, block.getBlockTexture(renderblocks.blockAccess, xCoord, yCoord, zCoord, 0));
            var8 = true;
        }

        if (renderAll || (xInRange && yInRange && zInRange) || (!firstTry&& 
                block.shouldSideBeRendered(renderblocks.blockAccess, xCoord, yCoord+1, zCoord, 1)))
        {
            if (renderblocks.aoType > 0)
            {
                if (block.maxY >= 1.0D)
                {
                    ++yCoord;
                }

                renderblocks.aoBrightnessXYNP = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord - 1, yCoord, zCoord);
                renderblocks.aoBrightnessXYPP = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord + 1, yCoord, zCoord);
                renderblocks.aoBrightnessYZPN = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord, yCoord, zCoord - 1);
                renderblocks.aoBrightnessYZPP = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord, yCoord, zCoord + 1);
                renderblocks.aoLightValueScratchXYNP = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord - 1, yCoord, zCoord);
                renderblocks.aoLightValueScratchXYPP = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord + 1, yCoord, zCoord);
                renderblocks.aoLightValueScratchYZPN = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord, yCoord, zCoord - 1);
                renderblocks.aoLightValueScratchYZPP = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord, yCoord, zCoord + 1);

                if (!renderblocks.aoGrassXYZCPN && !renderblocks.aoGrassXYZNPC)
                {
                    renderblocks.aoLightValueScratchXYZNPN = renderblocks.aoLightValueScratchXYNP;
                    renderblocks.aoBrightnessXYZNPN = renderblocks.aoBrightnessXYNP;
                }
                else
                {
                    renderblocks.aoLightValueScratchXYZNPN = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord - 1, yCoord, zCoord - 1);
                    renderblocks.aoBrightnessXYZNPN = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord - 1, yCoord, zCoord - 1);
                }

                if (!renderblocks.aoGrassXYZCPN && !renderblocks.aoGrassXYZPPC)
                {
                    renderblocks.aoLightValueScratchXYZPPN = renderblocks.aoLightValueScratchXYPP;
                    renderblocks.aoBrightnessXYZPPN = renderblocks.aoBrightnessXYPP;
                }
                else
                {
                    renderblocks.aoLightValueScratchXYZPPN = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord + 1, yCoord, zCoord - 1);
                    renderblocks.aoBrightnessXYZPPN = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord + 1, yCoord, zCoord - 1);
                }

                if (!renderblocks.aoGrassXYZCPP && !renderblocks.aoGrassXYZNPC)
                {
                    renderblocks.aoLightValueScratchXYZNPP = renderblocks.aoLightValueScratchXYNP;
                    renderblocks.aoBrightnessXYZNPP = renderblocks.aoBrightnessXYNP;
                }
                else
                {
                    renderblocks.aoLightValueScratchXYZNPP = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord - 1, yCoord, zCoord + 1);
                    renderblocks.aoBrightnessXYZNPP = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord - 1, yCoord, zCoord + 1);
                }

                if (!renderblocks.aoGrassXYZCPP && !renderblocks.aoGrassXYZPPC)
                {
                    renderblocks.aoLightValueScratchXYZPPP = renderblocks.aoLightValueScratchXYPP;
                    renderblocks.aoBrightnessXYZPPP = renderblocks.aoBrightnessXYPP;
                }
                else
                {
                    renderblocks.aoLightValueScratchXYZPPP = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord + 1, yCoord, zCoord + 1);
                    renderblocks.aoBrightnessXYZPPP = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord + 1, yCoord, zCoord + 1);
                }

                if (block.maxY >= 1.0D)
                {
                    --yCoord;
                }

                var12 = (renderblocks.aoLightValueScratchXYZNPP + renderblocks.aoLightValueScratchXYNP + renderblocks.aoLightValueScratchYZPP + renderblocks.aoLightValueYPos) / 4.0F;
                var9 = (renderblocks.aoLightValueScratchYZPP + renderblocks.aoLightValueYPos + renderblocks.aoLightValueScratchXYZPPP + renderblocks.aoLightValueScratchXYPP) / 4.0F;
                var10 = (renderblocks.aoLightValueYPos + renderblocks.aoLightValueScratchYZPN + renderblocks.aoLightValueScratchXYPP + renderblocks.aoLightValueScratchXYZPPN) / 4.0F;
                var11 = (renderblocks.aoLightValueScratchXYNP + renderblocks.aoLightValueScratchXYZNPN + renderblocks.aoLightValueYPos + renderblocks.aoLightValueScratchYZPN) / 4.0F;
                renderblocks.brightnessTopRight = renderblocks.getAoBrightness(renderblocks.aoBrightnessXYZNPP, renderblocks.aoBrightnessXYNP, renderblocks.aoBrightnessYZPP, var24);
                renderblocks.brightnessTopLeft = renderblocks.getAoBrightness(renderblocks.aoBrightnessYZPP, renderblocks.aoBrightnessXYZPPP, renderblocks.aoBrightnessXYPP, var24);
                renderblocks.brightnessBottomLeft = renderblocks.getAoBrightness(renderblocks.aoBrightnessYZPN, renderblocks.aoBrightnessXYPP, renderblocks.aoBrightnessXYZPPN, var24);
                renderblocks.brightnessBottomRight = renderblocks.getAoBrightness(renderblocks.aoBrightnessXYNP, renderblocks.aoBrightnessXYZNPN, renderblocks.aoBrightnessYZPN, var24);
            }
            else
            {
                var12 = renderblocks.aoLightValueYPos;
                var11 = renderblocks.aoLightValueYPos;
                var10 = renderblocks.aoLightValueYPos;
                var9 = renderblocks.aoLightValueYPos;
                renderblocks.brightnessTopLeft = renderblocks.brightnessBottomLeft = renderblocks.brightnessBottomRight = renderblocks.brightnessTopRight = var24;
            }

            renderblocks.colorRedTopLeft = renderblocks.colorRedBottomLeft = renderblocks.colorRedBottomRight = renderblocks.colorRedTopRight = (var18 ? par5 : 1.0F) * colorMult;
            renderblocks.colorGreenTopLeft = renderblocks.colorGreenBottomLeft = renderblocks.colorGreenBottomRight = renderblocks.colorGreenTopRight = (var18 ? par6 : 1.0F) * colorMult;
            renderblocks.colorBlueTopLeft = renderblocks.colorBlueBottomLeft = renderblocks.colorBlueBottomRight = renderblocks.colorBlueTopRight = (var18 ? par7 : 1.0F) * colorMult;

            texIndex = block.getBlockTexture(renderblocks.blockAccess, xCoord, yCoord, zCoord, 1);
            renderblocks.renderTopFace(block, (double)xCoord, (double)yCoord, (double)zCoord, texIndex);

            var8 = true;
        }



        if (renderAll || (xInRange && yInRange && zInRange) || (!firstTry && 
                block.shouldSideBeRendered(renderblocks.blockAccess, xCoord, yCoord, zCoord-1, 2)))
        {
            if (renderblocks.aoType > 0)
            {
                if (block.minZ <= 0.0D)
                {
                    --zCoord;
                }

                renderblocks.aoLightValueScratchXZNN = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord - 1, yCoord, zCoord);
                renderblocks.aoLightValueScratchYZNN = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord, yCoord - 1, zCoord);
                renderblocks.aoLightValueScratchYZPN = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord, yCoord + 1, zCoord);
                renderblocks.aoLightValueScratchXZPN = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord + 1, yCoord, zCoord);
                renderblocks.aoBrightnessXZNN = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord - 1, yCoord, zCoord);
                renderblocks.aoBrightnessYZNN = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord, yCoord - 1, zCoord);
                renderblocks.aoBrightnessYZPN = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord, yCoord + 1, zCoord);
                renderblocks.aoBrightnessXZPN = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord + 1, yCoord, zCoord);

                if (!renderblocks.aoGrassXYZNCN && !renderblocks.aoGrassXYZCNN)
                {
                    renderblocks.aoLightValueScratchXYZNNN = renderblocks.aoLightValueScratchXZNN;
                    renderblocks.aoBrightnessXYZNNN = renderblocks.aoBrightnessXZNN;
                }
                else
                {
                    renderblocks.aoLightValueScratchXYZNNN = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord - 1, yCoord - 1, zCoord);
                    renderblocks.aoBrightnessXYZNNN = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord - 1, yCoord - 1, zCoord);
                }

                if (!renderblocks.aoGrassXYZNCN && !renderblocks.aoGrassXYZCPN)
                {
                    renderblocks.aoLightValueScratchXYZNPN = renderblocks.aoLightValueScratchXZNN;
                    renderblocks.aoBrightnessXYZNPN = renderblocks.aoBrightnessXZNN;
                }
                else
                {
                    renderblocks.aoLightValueScratchXYZNPN = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord - 1, yCoord + 1, zCoord);
                    renderblocks.aoBrightnessXYZNPN = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord - 1, yCoord + 1, zCoord);
                }

                if (!renderblocks.aoGrassXYZPCN && !renderblocks.aoGrassXYZCNN)
                {
                    renderblocks.aoLightValueScratchXYZPNN = renderblocks.aoLightValueScratchXZPN;
                    renderblocks.aoBrightnessXYZPNN = renderblocks.aoBrightnessXZPN;
                }
                else
                {
                    renderblocks.aoLightValueScratchXYZPNN = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord + 1, yCoord - 1, zCoord);
                    renderblocks.aoBrightnessXYZPNN = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord + 1, yCoord - 1, zCoord);
                }

                if (!renderblocks.aoGrassXYZPCN && !renderblocks.aoGrassXYZCPN)
                {
                    renderblocks.aoLightValueScratchXYZPPN = renderblocks.aoLightValueScratchXZPN;
                    renderblocks.aoBrightnessXYZPPN = renderblocks.aoBrightnessXZPN;
                }
                else
                {
                    renderblocks.aoLightValueScratchXYZPPN = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord + 1, yCoord + 1, zCoord);
                    renderblocks.aoBrightnessXYZPPN = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord + 1, yCoord + 1, zCoord);
                }

                if (block.minZ <= 0.0D)
                {
                    ++zCoord;
                }

                var9 = (renderblocks.aoLightValueScratchXZNN + renderblocks.aoLightValueScratchXYZNPN + renderblocks.aoLightValueZNeg + renderblocks.aoLightValueScratchYZPN) / 4.0F;
                var10 = (renderblocks.aoLightValueZNeg + renderblocks.aoLightValueScratchYZPN + renderblocks.aoLightValueScratchXZPN + renderblocks.aoLightValueScratchXYZPPN) / 4.0F;
                var11 = (renderblocks.aoLightValueScratchYZNN + renderblocks.aoLightValueZNeg + renderblocks.aoLightValueScratchXYZPNN + renderblocks.aoLightValueScratchXZPN) / 4.0F;
                var12 = (renderblocks.aoLightValueScratchXYZNNN + renderblocks.aoLightValueScratchXZNN + renderblocks.aoLightValueScratchYZNN + renderblocks.aoLightValueZNeg) / 4.0F;
                renderblocks.brightnessTopLeft = renderblocks.getAoBrightness(renderblocks.aoBrightnessXZNN, renderblocks.aoBrightnessXYZNPN, renderblocks.aoBrightnessYZPN, var22);
                renderblocks.brightnessBottomLeft = renderblocks.getAoBrightness(renderblocks.aoBrightnessYZPN, renderblocks.aoBrightnessXZPN, renderblocks.aoBrightnessXYZPPN, var22);
                renderblocks.brightnessBottomRight = renderblocks.getAoBrightness(renderblocks.aoBrightnessYZNN, renderblocks.aoBrightnessXYZPNN, renderblocks.aoBrightnessXZPN, var22);
                renderblocks.brightnessTopRight = renderblocks.getAoBrightness(renderblocks.aoBrightnessXYZNNN, renderblocks.aoBrightnessXZNN, renderblocks.aoBrightnessYZNN, var22);
            }
            else
            {
                var12 = renderblocks.aoLightValueZNeg;
                var11 = renderblocks.aoLightValueZNeg;
                var10 = renderblocks.aoLightValueZNeg;
                var9 = renderblocks.aoLightValueZNeg;
                renderblocks.brightnessTopLeft = renderblocks.brightnessBottomLeft = renderblocks.brightnessBottomRight = renderblocks.brightnessTopRight = var22;
            }



            renderblocks.colorRedTopLeft = renderblocks.colorRedBottomLeft = renderblocks.colorRedBottomRight = renderblocks.colorRedTopRight = (var18 ? par5 : 1.0F) * colorMult;
            renderblocks.colorGreenTopLeft = renderblocks.colorGreenBottomLeft = renderblocks.colorGreenBottomRight = renderblocks.colorGreenTopRight = (var18 ? par6 : 1.0F) * colorMult;
            renderblocks.colorBlueTopLeft = renderblocks.colorBlueBottomLeft = renderblocks.colorBlueBottomRight = renderblocks.colorBlueTopRight = (var18 ? par7 : 1.0F) * colorMult;

            texIndex = block.getBlockTexture(renderblocks.blockAccess, xCoord, yCoord, zCoord, 2);
            renderblocks.renderEastFace(block, (double)xCoord, (double)yCoord, (double)zCoord, texIndex);

            var8 = true;
        }

        if (renderAll || (xInRange && yInRange && zInRange) || (!firstTry && 
                block.shouldSideBeRendered(renderblocks.blockAccess, xCoord, yCoord, zCoord+1, 3)))
        {
            if (renderblocks.aoType > 0)
            {
                if (block.maxZ >= 1.0D)
                {
                    ++zCoord;
                }

                renderblocks.aoLightValueScratchXZNP = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord - 1, yCoord, zCoord);
                renderblocks.aoLightValueScratchXZPP = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord + 1, yCoord, zCoord);
                renderblocks.aoLightValueScratchYZNP = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord, yCoord - 1, zCoord);
                renderblocks.aoLightValueScratchYZPP = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord, yCoord + 1, zCoord);
                renderblocks.aoBrightnessXZNP = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord - 1, yCoord, zCoord);
                renderblocks.aoBrightnessXZPP = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord + 1, yCoord, zCoord);
                renderblocks.aoBrightnessYZNP = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord, yCoord - 1, zCoord);
                renderblocks.aoBrightnessYZPP = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord, yCoord + 1, zCoord);

                if (!renderblocks.aoGrassXYZNCP && !renderblocks.aoGrassXYZCNP)
                {
                    renderblocks.aoLightValueScratchXYZNNP = renderblocks.aoLightValueScratchXZNP;
                    renderblocks.aoBrightnessXYZNNP = renderblocks.aoBrightnessXZNP;
                }
                else
                {
                    renderblocks.aoLightValueScratchXYZNNP = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord - 1, yCoord - 1, zCoord);
                    renderblocks.aoBrightnessXYZNNP = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord - 1, yCoord - 1, zCoord);
                }

                if (!renderblocks.aoGrassXYZNCP && !renderblocks.aoGrassXYZCPP)
                {
                    renderblocks.aoLightValueScratchXYZNPP = renderblocks.aoLightValueScratchXZNP;
                    renderblocks.aoBrightnessXYZNPP = renderblocks.aoBrightnessXZNP;
                }
                else
                {
                    renderblocks.aoLightValueScratchXYZNPP = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord - 1, yCoord + 1, zCoord);
                    renderblocks.aoBrightnessXYZNPP = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord - 1, yCoord + 1, zCoord);
                }

                if (!renderblocks.aoGrassXYZPCP && !renderblocks.aoGrassXYZCNP)
                {
                    renderblocks.aoLightValueScratchXYZPNP = renderblocks.aoLightValueScratchXZPP;
                    renderblocks.aoBrightnessXYZPNP = renderblocks.aoBrightnessXZPP;
                }
                else
                {
                    renderblocks.aoLightValueScratchXYZPNP = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord + 1, yCoord - 1, zCoord);
                    renderblocks.aoBrightnessXYZPNP = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord + 1, yCoord - 1, zCoord);
                }

                if (!renderblocks.aoGrassXYZPCP && !renderblocks.aoGrassXYZCPP)
                {
                    renderblocks.aoLightValueScratchXYZPPP = renderblocks.aoLightValueScratchXZPP;
                    renderblocks.aoBrightnessXYZPPP = renderblocks.aoBrightnessXZPP;
                }
                else
                {
                    renderblocks.aoLightValueScratchXYZPPP = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord + 1, yCoord + 1, zCoord);
                    renderblocks.aoBrightnessXYZPPP = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord + 1, yCoord + 1, zCoord);
                }

                if (block.maxZ >= 1.0D)
                {
                    --zCoord;
                }

                var9 = (renderblocks.aoLightValueScratchXZNP + renderblocks.aoLightValueScratchXYZNPP + renderblocks.aoLightValueZPos + renderblocks.aoLightValueScratchYZPP) / 4.0F;
                var12 = (renderblocks.aoLightValueZPos + renderblocks.aoLightValueScratchYZPP + renderblocks.aoLightValueScratchXZPP + renderblocks.aoLightValueScratchXYZPPP) / 4.0F;
                var11 = (renderblocks.aoLightValueScratchYZNP + renderblocks.aoLightValueZPos + renderblocks.aoLightValueScratchXYZPNP + renderblocks.aoLightValueScratchXZPP) / 4.0F;
                var10 = (renderblocks.aoLightValueScratchXYZNNP + renderblocks.aoLightValueScratchXZNP + renderblocks.aoLightValueScratchYZNP + renderblocks.aoLightValueZPos) / 4.0F;
                renderblocks.brightnessTopLeft = renderblocks.getAoBrightness(renderblocks.aoBrightnessXZNP, renderblocks.aoBrightnessXYZNPP, renderblocks.aoBrightnessYZPP, var25);
                renderblocks.brightnessTopRight = renderblocks.getAoBrightness(renderblocks.aoBrightnessYZPP, renderblocks.aoBrightnessXZPP, renderblocks.aoBrightnessXYZPPP, var25);
                renderblocks.brightnessBottomRight = renderblocks.getAoBrightness(renderblocks.aoBrightnessYZNP, renderblocks.aoBrightnessXYZPNP, renderblocks.aoBrightnessXZPP, var25);
                renderblocks.brightnessBottomLeft = renderblocks.getAoBrightness(renderblocks.aoBrightnessXYZNNP, renderblocks.aoBrightnessXZNP, renderblocks.aoBrightnessYZNP, var25);
            }
            else
            {
                var12 = renderblocks.aoLightValueZPos;
                var11 = renderblocks.aoLightValueZPos;
                var10 = renderblocks.aoLightValueZPos;
                var9 = renderblocks.aoLightValueZPos;
                renderblocks.brightnessTopLeft = renderblocks.brightnessBottomLeft = renderblocks.brightnessBottomRight = renderblocks.brightnessTopRight = var25;
            }

            renderblocks.colorRedTopLeft = renderblocks.colorRedBottomLeft = renderblocks.colorRedBottomRight = renderblocks.colorRedTopRight = (var18 ? par5 : 1.0F) * colorMult;
            renderblocks.colorGreenTopLeft = renderblocks.colorGreenBottomLeft = renderblocks.colorGreenBottomRight = renderblocks.colorGreenTopRight = (var18 ? par6 : 1.0F) * colorMult;
            renderblocks.colorBlueTopLeft = renderblocks.colorBlueBottomLeft = renderblocks.colorBlueBottomRight = renderblocks.colorBlueTopRight = (var18 ? par7 : 1.0F) * colorMult;

            texIndex = block.getBlockTexture(renderblocks.blockAccess, xCoord, yCoord, zCoord, 3);
            renderblocks.renderWestFace(block, (double)xCoord, (double)yCoord, (double)zCoord, block.getBlockTexture(renderblocks.blockAccess, xCoord, yCoord, zCoord, 3));

            var8 = true;
        }

        if (renderAll || (xInRange && yInRange && zInRange) || (!firstTry && 
                block.shouldSideBeRendered(renderblocks.blockAccess, xCoord-1, yCoord, zCoord, 4)))
        {
            if (renderblocks.aoType > 0)
            {
                if (block.minX <= 0.0D)
                {
                    --xCoord;
                }

                renderblocks.aoLightValueScratchXYNN = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord, yCoord - 1, zCoord);
                renderblocks.aoLightValueScratchXZNN = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord, yCoord, zCoord - 1);
                renderblocks.aoLightValueScratchXZNP = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord, yCoord, zCoord + 1);
                renderblocks.aoLightValueScratchXYNP = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord, yCoord + 1, zCoord);
                renderblocks.aoBrightnessXYNN = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord, yCoord - 1, zCoord);
                renderblocks.aoBrightnessXZNN = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord, yCoord, zCoord - 1);
                renderblocks.aoBrightnessXZNP = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord, yCoord, zCoord + 1);
                renderblocks.aoBrightnessXYNP = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord, yCoord + 1, zCoord);

                if (!renderblocks.aoGrassXYZNCN && !renderblocks.aoGrassXYZNNC)
                {
                    renderblocks.aoLightValueScratchXYZNNN = renderblocks.aoLightValueScratchXZNN;
                    renderblocks.aoBrightnessXYZNNN = renderblocks.aoBrightnessXZNN;
                }
                else
                {
                    renderblocks.aoLightValueScratchXYZNNN = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord, yCoord - 1, zCoord - 1);
                    renderblocks.aoBrightnessXYZNNN = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord, yCoord - 1, zCoord - 1);
                }

                if (!renderblocks.aoGrassXYZNCP && !renderblocks.aoGrassXYZNNC)
                {
                    renderblocks.aoLightValueScratchXYZNNP = renderblocks.aoLightValueScratchXZNP;
                    renderblocks.aoBrightnessXYZNNP = renderblocks.aoBrightnessXZNP;
                }
                else
                {
                    renderblocks.aoLightValueScratchXYZNNP = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord, yCoord - 1, zCoord + 1);
                    renderblocks.aoBrightnessXYZNNP = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord, yCoord - 1, zCoord + 1);
                }

                if (!renderblocks.aoGrassXYZNCN && !renderblocks.aoGrassXYZNPC)
                {
                    renderblocks.aoLightValueScratchXYZNPN = renderblocks.aoLightValueScratchXZNN;
                    renderblocks.aoBrightnessXYZNPN = renderblocks.aoBrightnessXZNN;
                }
                else
                {
                    renderblocks.aoLightValueScratchXYZNPN = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord, yCoord + 1, zCoord - 1);
                    renderblocks.aoBrightnessXYZNPN = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord, yCoord + 1, zCoord - 1);
                }

                if (!renderblocks.aoGrassXYZNCP && !renderblocks.aoGrassXYZNPC)
                {
                    renderblocks.aoLightValueScratchXYZNPP = renderblocks.aoLightValueScratchXZNP;
                    renderblocks.aoBrightnessXYZNPP = renderblocks.aoBrightnessXZNP;
                }
                else
                {
                    renderblocks.aoLightValueScratchXYZNPP = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord, yCoord + 1, zCoord + 1);
                    renderblocks.aoBrightnessXYZNPP = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord, yCoord + 1, zCoord + 1);
                }

                if (block.minX <= 0.0D)
                {
                    ++xCoord;
                }

                var12 = (renderblocks.aoLightValueScratchXYNN + renderblocks.aoLightValueScratchXYZNNP + renderblocks.aoLightValueXNeg + renderblocks.aoLightValueScratchXZNP) / 4.0F;
                var9 = (renderblocks.aoLightValueXNeg + renderblocks.aoLightValueScratchXZNP + renderblocks.aoLightValueScratchXYNP + renderblocks.aoLightValueScratchXYZNPP) / 4.0F;
                var10 = (renderblocks.aoLightValueScratchXZNN + renderblocks.aoLightValueXNeg + renderblocks.aoLightValueScratchXYZNPN + renderblocks.aoLightValueScratchXYNP) / 4.0F;
                var11 = (renderblocks.aoLightValueScratchXYZNNN + renderblocks.aoLightValueScratchXYNN + renderblocks.aoLightValueScratchXZNN + renderblocks.aoLightValueXNeg) / 4.0F;
                renderblocks.brightnessTopRight = renderblocks.getAoBrightness(renderblocks.aoBrightnessXYNN, renderblocks.aoBrightnessXYZNNP, renderblocks.aoBrightnessXZNP, var20);
                renderblocks.brightnessTopLeft = renderblocks.getAoBrightness(renderblocks.aoBrightnessXZNP, renderblocks.aoBrightnessXYNP, renderblocks.aoBrightnessXYZNPP, var20);
                renderblocks.brightnessBottomLeft = renderblocks.getAoBrightness(renderblocks.aoBrightnessXZNN, renderblocks.aoBrightnessXYZNPN, renderblocks.aoBrightnessXYNP, var20);
                renderblocks.brightnessBottomRight = renderblocks.getAoBrightness(renderblocks.aoBrightnessXYZNNN, renderblocks.aoBrightnessXYNN, renderblocks.aoBrightnessXZNN, var20);
            }
            else
            {
                var12 = renderblocks.aoLightValueXNeg;
                var11 = renderblocks.aoLightValueXNeg;
                var10 = renderblocks.aoLightValueXNeg;
                var9 = renderblocks.aoLightValueXNeg;
                renderblocks.brightnessTopLeft = renderblocks.brightnessBottomLeft = renderblocks.brightnessBottomRight = renderblocks.brightnessTopRight = var20;
            }


            renderblocks.colorRedTopLeft = renderblocks.colorRedBottomLeft = renderblocks.colorRedBottomRight = renderblocks.colorRedTopRight = (var18 ? par5 : 1.0F) * colorMult;
            renderblocks.colorGreenTopLeft = renderblocks.colorGreenBottomLeft = renderblocks.colorGreenBottomRight = renderblocks.colorGreenTopRight = (var18 ? par6 : 1.0F) * colorMult;
            renderblocks.colorBlueTopLeft = renderblocks.colorBlueBottomLeft = renderblocks.colorBlueBottomRight = renderblocks.colorBlueTopRight = (var18 ? par7 : 1.0F) * colorMult;

            texIndex = block.getBlockTexture(renderblocks.blockAccess, xCoord, yCoord, zCoord, 4);
            renderblocks.renderNorthFace(block, (double)xCoord, (double)yCoord, (double)zCoord, texIndex);


            var8 = true;
        }

        if (renderAll || (xInRange && yInRange && zInRange) || (!firstTry && 
                block.shouldSideBeRendered(renderblocks.blockAccess, xCoord+1, yCoord, zCoord, 5)))
        {
            if (renderblocks.aoType > 0)
            {
                if (block.maxX >= 1.0D)
                {
                    ++xCoord;
                }

                renderblocks.aoLightValueScratchXYPN = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord, yCoord - 1, zCoord);
                renderblocks.aoLightValueScratchXZPN = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord, yCoord, zCoord - 1);
                renderblocks.aoLightValueScratchXZPP = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord, yCoord, zCoord + 1);
                renderblocks.aoLightValueScratchXYPP = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord, yCoord + 1, zCoord);
                renderblocks.aoBrightnessXYPN = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord, yCoord - 1, zCoord);
                renderblocks.aoBrightnessXZPN = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord, yCoord, zCoord - 1);
                renderblocks.aoBrightnessXZPP = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord, yCoord, zCoord + 1);
                renderblocks.aoBrightnessXYPP = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord, yCoord + 1, zCoord);

                if (!renderblocks.aoGrassXYZPNC && !renderblocks.aoGrassXYZPCN)
                {
                    renderblocks.aoLightValueScratchXYZPNN = renderblocks.aoLightValueScratchXZPN;
                    renderblocks.aoBrightnessXYZPNN = renderblocks.aoBrightnessXZPN;
                }
                else
                {
                    renderblocks.aoLightValueScratchXYZPNN = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord, yCoord - 1, zCoord - 1);
                    renderblocks.aoBrightnessXYZPNN = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord, yCoord - 1, zCoord - 1);
                }

                if (!renderblocks.aoGrassXYZPNC && !renderblocks.aoGrassXYZPCP)
                {
                    renderblocks.aoLightValueScratchXYZPNP = renderblocks.aoLightValueScratchXZPP;
                    renderblocks.aoBrightnessXYZPNP = renderblocks.aoBrightnessXZPP;
                }
                else
                {
                    renderblocks.aoLightValueScratchXYZPNP = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord, yCoord - 1, zCoord + 1);
                    renderblocks.aoBrightnessXYZPNP = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord, yCoord - 1, zCoord + 1);
                }

                if (!renderblocks.aoGrassXYZPPC && !renderblocks.aoGrassXYZPCN)
                {
                    renderblocks.aoLightValueScratchXYZPPN = renderblocks.aoLightValueScratchXZPN;
                    renderblocks.aoBrightnessXYZPPN = renderblocks.aoBrightnessXZPN;
                }
                else
                {
                    renderblocks.aoLightValueScratchXYZPPN = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord, yCoord + 1, zCoord - 1);
                    renderblocks.aoBrightnessXYZPPN = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord, yCoord + 1, zCoord - 1);
                }

                if (!renderblocks.aoGrassXYZPPC && !renderblocks.aoGrassXYZPCP)
                {
                    renderblocks.aoLightValueScratchXYZPPP = renderblocks.aoLightValueScratchXZPP;
                    renderblocks.aoBrightnessXYZPPP = renderblocks.aoBrightnessXZPP;
                }
                else
                {
                    renderblocks.aoLightValueScratchXYZPPP = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord, yCoord + 1, zCoord + 1);
                    renderblocks.aoBrightnessXYZPPP = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord, yCoord + 1, zCoord + 1);
                }

                if (block.maxX >= 1.0D)
                {
                    --xCoord;
                }

                var9 = (renderblocks.aoLightValueScratchXYPN + renderblocks.aoLightValueScratchXYZPNP + renderblocks.aoLightValueXPos + renderblocks.aoLightValueScratchXZPP) / 4.0F;
                var12 = (renderblocks.aoLightValueXPos + renderblocks.aoLightValueScratchXZPP + renderblocks.aoLightValueScratchXYPP + renderblocks.aoLightValueScratchXYZPPP) / 4.0F;
                var11 = (renderblocks.aoLightValueScratchXZPN + renderblocks.aoLightValueXPos + renderblocks.aoLightValueScratchXYZPPN + renderblocks.aoLightValueScratchXYPP) / 4.0F;
                var10 = (renderblocks.aoLightValueScratchXYZPNN + renderblocks.aoLightValueScratchXYPN + renderblocks.aoLightValueScratchXZPN + renderblocks.aoLightValueXPos) / 4.0F;
                renderblocks.brightnessTopLeft = renderblocks.getAoBrightness(renderblocks.aoBrightnessXYPN, renderblocks.aoBrightnessXYZPNP, renderblocks.aoBrightnessXZPP, var23);
                renderblocks.brightnessTopRight = renderblocks.getAoBrightness(renderblocks.aoBrightnessXZPP, renderblocks.aoBrightnessXYPP, renderblocks.aoBrightnessXYZPPP, var23);
                renderblocks.brightnessBottomRight = renderblocks.getAoBrightness(renderblocks.aoBrightnessXZPN, renderblocks.aoBrightnessXYZPPN, renderblocks.aoBrightnessXYPP, var23);
                renderblocks.brightnessBottomLeft = renderblocks.getAoBrightness(renderblocks.aoBrightnessXYZPNN, renderblocks.aoBrightnessXYPN, renderblocks.aoBrightnessXZPN, var23);
            }
            else
            {
                var12 = renderblocks.aoLightValueXPos;
                var11 = renderblocks.aoLightValueXPos;
                var10 = renderblocks.aoLightValueXPos;
                var9 = renderblocks.aoLightValueXPos;
                renderblocks.brightnessTopLeft = renderblocks.brightnessBottomLeft = renderblocks.brightnessBottomRight = renderblocks.brightnessTopRight = var23;
            }

            renderblocks.colorRedTopLeft = renderblocks.colorRedBottomLeft = renderblocks.colorRedBottomRight = renderblocks.colorRedTopRight = (var18 ? par5 : 1.0F) * colorMult;
            renderblocks.colorGreenTopLeft = renderblocks.colorGreenBottomLeft = renderblocks.colorGreenBottomRight = renderblocks.colorGreenTopRight = (var18 ? par6 : 1.0F) * colorMult;
            renderblocks.colorBlueTopLeft = renderblocks.colorBlueBottomLeft = renderblocks.colorBlueBottomRight = renderblocks.colorBlueTopRight = (var18 ? par7 : 1.0F) * colorMult;

            texIndex = block.getBlockTexture(renderblocks.blockAccess, xCoord, yCoord, zCoord, 5);
            renderblocks.renderSouthFace(block, (double)xCoord, (double)yCoord, (double)zCoord, texIndex);

            var8 = true;
        }
        
        if(firstTry && !var8 && mod_TFC.proxy.getGraphicsLevel() && (!xInRange|| !yInRange || !zInRange))
        {
            renderblocks.overrideBlockTexture = block.getBlockTextureFromSideAndMetadata(0, meta)+16;
            RenderNewLeaves(block, xCoord, yCoord, zCoord,par5,par6,par7,renderblocks,!fancy,false);
            renderblocks.clearOverrideBlockTexture();
        }
        else if(firstTry && !var8 && !mod_TFC.proxy.getGraphicsLevel() && (!xInRange|| !yInRange || !zInRange))
        {
            RenderNewLeaves(block, xCoord, yCoord, zCoord,par5,par6,par7,renderblocks,!fancy,false);
        }

        renderblocks.enableAO = false;
        return var8;
    }
}
