package TFC.Render.Blocks;

import TFC.Core.TFC_Climate;
import TFC.Core.TFC_Core;
import net.minecraft.src.Block;
import net.minecraft.src.RenderBlocks;
import net.minecraft.src.Tessellator;

public class RenderGrass 
{
	public static boolean Render(Block block, int xCoord, int yCoord, int zCoord, 
            float par5, float par6, float par7, RenderBlocks renderblocks)
    {
		double blockMinX = block.func_83009_v();
		double blockMaxX = block.func_83007_w();
		double blockMinY = block.func_83008_x();
		double blockMaxY = block.func_83010_y();
		double blockMinZ = block.func_83005_z();
		double blockMaxZ = block.func_83006_A();
		
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

        int BGTex = renderblocks.blockAccess.getBlockId(xCoord, yCoord+1, zCoord) == Block.snow.blockID ? 253 : block.getBlockTexture(renderblocks.blockAccess, xCoord, yCoord, zCoord, 1);

        if (blockMinY <= 0.0D)//minY
        {
            var21 = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord, yCoord - 1, zCoord);
        }

        if (blockMaxY >= 1.0D)//maxY
        {
            var24 = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord, yCoord + 1, zCoord);
        }

        if (blockMinX <= 0.0D)//minX
        {
            var20 = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord - 1, yCoord, zCoord);
        }

        if (blockMaxX >= 1.0D)//maxX
        {
            var23 = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord + 1, yCoord, zCoord);
        }

        if (blockMinZ <= 0.0D)//minZ
        {
            var22 = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord, yCoord, zCoord - 1);
        }

        if (blockMaxZ >= 1.0D)//maxZ
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

        if (renderblocks.renderAllFaces || block.shouldSideBeRendered(renderblocks.blockAccess, xCoord, yCoord - 1, zCoord, 0))
        {
            if (renderblocks.aoType > 0)
            {
                if (blockMinY <= 0.0D)
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

                if (blockMinY <= 0.0D)
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
            renderblocks.renderBottomFace(block, (double)xCoord, (double)yCoord, (double)zCoord, block.getBlockTexture(renderblocks.blockAccess, xCoord, yCoord, zCoord, 0));
            
            var8 = true;
        }

        if (renderblocks.renderAllFaces || block.shouldSideBeRendered(renderblocks.blockAccess, xCoord, yCoord + 1, zCoord, 1))
        {
            if (renderblocks.aoType > 0)
            {
                if (blockMaxY >= 1.0D)
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

                if (blockMaxY >= 1.0D)
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
            
            renderblocks.colorRedTopLeft = renderblocks.colorRedBottomLeft = renderblocks.colorRedBottomRight = renderblocks.colorRedTopRight = 1.0F;
            renderblocks.colorGreenTopLeft = renderblocks.colorGreenBottomLeft = renderblocks.colorGreenBottomRight = renderblocks.colorGreenTopRight = 1.0F;
            renderblocks.colorBlueTopLeft = renderblocks.colorBlueBottomLeft = renderblocks.colorBlueBottomRight = renderblocks.colorBlueTopRight = 1.0F;
            
            renderblocks.renderTopFace(block, (double)xCoord, (double)yCoord, (double)zCoord, block.getBlockTexture(renderblocks.blockAccess, xCoord, yCoord, zCoord, 0));
            
            
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
            
            renderblocks.renderTopFace(block, (double)xCoord, (double)yCoord, (double)zCoord, block.getBlockTexture(renderblocks.blockAccess, xCoord, yCoord, zCoord, 1));
            var8 = true;
        }

        int var27;

        if (renderblocks.renderAllFaces || block.shouldSideBeRendered(renderblocks.blockAccess, xCoord, yCoord, zCoord - 1, 2))
        {
            if (renderblocks.aoType > 0)
            {
                if (blockMinZ <= 0.0D)
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

                if (blockMinZ <= 0.0D)
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
            var27 = block.getBlockTexture(renderblocks.blockAccess, xCoord, yCoord, zCoord, 2);
            renderblocks.renderEastFace(block, (double)xCoord, (double)yCoord, (double)zCoord, var27);

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
                renderblocks.renderEastFace(block, (double)xCoord, (double)yCoord, (double)zCoord, 254);
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
                renderblocks.renderEastFace(block, (double)xCoord, (double)yCoord, (double)zCoord, BGTex);
            }

            var8 = true;
        }

        if (renderblocks.renderAllFaces || block.shouldSideBeRendered(renderblocks.blockAccess, xCoord, yCoord, zCoord + 1, 3))
        {
            if (renderblocks.aoType > 0)
            {
                if (blockMaxZ >= 1.0D)
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

                if (blockMaxZ >= 1.0D)
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
            var27 = block.getBlockTexture(renderblocks.blockAccess, xCoord, yCoord, zCoord, 3);
            renderblocks.renderWestFace(block, (double)xCoord, (double)yCoord, (double)zCoord, block.getBlockTexture(renderblocks.blockAccess, xCoord, yCoord, zCoord, 3));

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
                renderblocks.renderWestFace(block, (double)xCoord, (double)yCoord, (double)zCoord, 254);
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
                renderblocks.renderWestFace(block, (double)xCoord, (double)yCoord, (double)zCoord, BGTex);
            }

            var8 = true;
        }

        if (renderblocks.renderAllFaces || block.shouldSideBeRendered(renderblocks.blockAccess, xCoord - 1, yCoord, zCoord, 4))
        {
            if (renderblocks.aoType > 0)
            {
                if (blockMinX <= 0.0D)
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

                if (blockMinX <= 0.0D)
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
            var27 = block.getBlockTexture(renderblocks.blockAccess, xCoord, yCoord, zCoord, 4);
            renderblocks.renderNorthFace(block, (double)xCoord, (double)yCoord, (double)zCoord, var27);

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
                renderblocks.renderNorthFace(block, (double)xCoord, (double)yCoord, (double)zCoord, 254);
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
                renderblocks.renderNorthFace(block, (double)xCoord, (double)yCoord, (double)zCoord, BGTex);
            }

            var8 = true;
        }

        if (renderblocks.renderAllFaces || block.shouldSideBeRendered(renderblocks.blockAccess, xCoord + 1, yCoord, zCoord, 5))
        {
            if (renderblocks.aoType > 0)
            {
                if (blockMaxX >= 1.0D)
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

                if (blockMaxX >= 1.0D)
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
            var27 = block.getBlockTexture(renderblocks.blockAccess, xCoord, yCoord, zCoord, 5);
            renderblocks.renderSouthFace(block, (double)xCoord, (double)yCoord, (double)zCoord, var27);

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
                renderblocks.renderSouthFace(block, (double)xCoord, (double)yCoord, (double)zCoord, 254);
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
                renderblocks.renderSouthFace(block, (double)xCoord, (double)yCoord, (double)zCoord, BGTex);
            }

            var8 = true;
        }

        renderblocks.enableAO = false;
        return var8;
    }
}
