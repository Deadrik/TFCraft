package TFC.Render.Blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockGrass;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.Icon;
import TFC.TFCBlocks;

public class RenderGrass
{
	public static boolean Render(Block block, int x, int y, int z, RenderBlocks renderer)
	{
		renderer.enableAO = false;
        Tessellator tessellator = Tessellator.instance;
        
        int var5 = block.colorMultiplier(renderer.blockAccess, x, y, z);
        float var6 = (var5 >> 16 & 255) / 255.0F;
        float var7 = (var5 >> 8 & 255) / 255.0F;
        float var8 = (var5 & 255) / 255.0F;
        
        boolean flag = false;
        float f3 = 0.5F;
        float f4 = 1.0F;
        float f5 = 0.8F;
        float f6 = 0.6F;
        float f7 = f4 * var6;
        float f8 = f4 * var7;
        float f9 = f4 * var8;
        float f10 = f3;
        float f11 = f5;
        float f12 = f6;
        float f13 = f3;
        float f14 = f5;
        float f15 = f6;
        float f16 = f3;
        float f17 = f5;
        float f18 = f6;

        /*if (block != Block.grass)
        {
            f10 = f3 * var6;
            f11 = f5 * var6;
            f12 = f6 * var6;
            f13 = f3 * var7;
            f14 = f5 * var7;
            f15 = f6 * var7;
            f16 = f3 * var8;
            f17 = f5 * var8;
            f18 = f6 * var8;
        }*/

        int l = block.getMixedBrightnessForBlock(renderer.blockAccess, x, y, z);

        if (block.shouldSideBeRendered(renderer.blockAccess, x, y - 1, z, 0))
        {
            tessellator.setBrightness(renderer.renderMinY > 0.0D ? l : block.getMixedBrightnessForBlock(renderer.blockAccess, x, y - 1, z));
            tessellator.setColorOpaque_F(f10, f13, f16);
            renderer.renderBottomFace(block, x, y, z, renderer.getBlockIcon(block, renderer.blockAccess, x, y, z, 0));
            flag = true;
        }

        if (block.shouldSideBeRendered(renderer.blockAccess, x, y + 1, z, 1))
        {
            tessellator.setBrightness(renderer.renderMaxY < 1.0D ? l : block.getMixedBrightnessForBlock(renderer.blockAccess, x, y + 1, z));
            tessellator.setColorOpaque_F(f7, f8, f9);
            renderer.renderTopFace(block, x, y, z, renderer.getBlockIcon(block, renderer.blockAccess, x, y, z, 1));
            flag = true;
        }

        Icon icon;

        if (block.shouldSideBeRendered(renderer.blockAccess, x, y, z - 1, 2))
        {
            tessellator.setBrightness(renderer.renderMinZ > 0.0D ? l : block.getMixedBrightnessForBlock(renderer.blockAccess, x, y, z - 1));
            tessellator.setColorOpaque_F(f11, f14, f17);
            icon = renderer.getBlockIcon(block, renderer.blockAccess, x, y, z, 2);
            
            if(icon.getIconName().equals("GrassTop"))
            	tessellator.setColorOpaque_F(f11 * var6, f14 * var7, f17 * var8);
            
            renderer.renderEastFace(block, x, y, z, icon);

            if (icon.getIconName().contains("soil/Dirt") && !renderer.hasOverrideBlockTexture())
            {
            	icon = TFCBlocks.Grass.getIconSideOverlay();
                tessellator.setColorOpaque_F(f11 * var6, f14 * var7, f17 * var8);
                renderer.renderEastFace(block, x, y, z, BlockGrass.getIconSideOverlay());
            }

            flag = true;
        }

        if (block.shouldSideBeRendered(renderer.blockAccess, x, y, z + 1, 3))
        {
            tessellator.setBrightness(renderer.renderMaxZ < 1.0D ? l : block.getMixedBrightnessForBlock(renderer.blockAccess, x, y, z + 1));
            tessellator.setColorOpaque_F(f11, f14, f17);
            icon = renderer.getBlockIcon(block, renderer.blockAccess, x, y, z, 3);
            
            if(icon.getIconName().equals("GrassTop"))
            	tessellator.setColorOpaque_F(f11 * var6, f14 * var7, f17 * var8);
            
            renderer.renderWestFace(block, x, y, z, icon);

            if (icon.getIconName().contains("soil/Dirt") && !renderer.hasOverrideBlockTexture())
            {
            	icon = TFCBlocks.Grass.getIconSideOverlay();
                tessellator.setColorOpaque_F(f11 * var6, f14 * var7, f17 * var8);
                renderer.renderWestFace(block, x, y, z, BlockGrass.getIconSideOverlay());
            }

            flag = true;
        }

        if (block.shouldSideBeRendered(renderer.blockAccess, x - 1, y, z, 4))
        {
            tessellator.setBrightness(renderer.renderMinX > 0.0D ? l : block.getMixedBrightnessForBlock(renderer.blockAccess, x - 1, y, z));
            tessellator.setColorOpaque_F(f12, f15, f18);
            icon = renderer.getBlockIcon(block, renderer.blockAccess, x, y, z, 4);
            
            if(icon.getIconName().equals("GrassTop"))
            	tessellator.setColorOpaque_F(f12 * var6, f15 * var7, f18 * var8);
            
            renderer.renderNorthFace(block, x, y, z, icon);

            if (icon.getIconName().contains("soil/Dirt") && !renderer.hasOverrideBlockTexture())
            {
            	icon = TFCBlocks.Grass.getIconSideOverlay();
                tessellator.setColorOpaque_F(f12 * var6, f15 * var7, f18 * var8);
                renderer.renderNorthFace(block, x, y, z, BlockGrass.getIconSideOverlay());
            }

            flag = true;
        }

        if (renderer.renderAllFaces || block.shouldSideBeRendered(renderer.blockAccess, x + 1, y, z, 5))
        {
            tessellator.setBrightness(renderer.renderMaxX < 1.0D ? l : block.getMixedBrightnessForBlock(renderer.blockAccess, x + 1, y, z));
            tessellator.setColorOpaque_F(f12, f15, f18);
            icon = renderer.getBlockIcon(block, renderer.blockAccess, x, y, z, 5);
            
            if(icon.getIconName().equals("GrassTop"))
                tessellator.setColorOpaque_F(f12 * var6, f15 * var7, f18 * var8);
            
            renderer.renderSouthFace(block, x, y, z, icon);

            if (icon.getIconName().contains("soil/Dirt") && !renderer.hasOverrideBlockTexture())
            {
            	icon = TFCBlocks.Grass.getIconSideOverlay();
                tessellator.setColorOpaque_F(f12 * var6, f15 * var7, f18 * var8);
                renderer.renderSouthFace(block, x, y, z, BlockGrass.getIconSideOverlay());
            }

            flag = true;
        }

        return flag;
	}
	
	public static boolean render(Block block, int x, int y, int z, RenderBlocks renderer)
	{
		int var5 = block.colorMultiplier(renderer.blockAccess, x, y, z);
        float red = (var5 >> 16 & 255) / 255.0F;
        float green = (var5 >> 8 & 255) / 255.0F;
        float blue = (var5 & 255) / 255.0F;
        
		renderer.enableAO = true;
        boolean flag1 = true;
        int l = block.getMixedBrightnessForBlock(renderer.blockAccess, x, y, z);
        Tessellator tessellator = Tessellator.instance;
        tessellator.setBrightness(983055);

        if (renderer.getBlockIcon(block).getIconName().equals("GrassTop"))
        {
            flag1 = false;
        }
        else if (renderer.hasOverrideBlockTexture())
        {
            flag1 = false;
        }

        renderBottom(block, x, y, z, renderer, red, green, blue, flag1, l);

        renderTop(block, x, y, z, renderer, red, green, blue, l);

        renderEast(block, x, y, z, renderer, red, green, blue, flag1, l);

        renderWest(block, x, y, z, renderer, red, green, blue, flag1, l);

        renderNorth(block, x, y, z, renderer, red, green, blue, flag1, l);

        renderSouth(block, x, y, z, renderer, red, green, blue, flag1, l);

        renderer.enableAO = false;
        return true;
	}

	private static void renderSouth(Block block, int x, int y, int z,
			RenderBlocks renderer, float red, float green, float blue,
			boolean flag1, int l) {
		float f3;
		float f4;
		float f5;
		float f6;
		boolean flag2;
		boolean flag3;
		boolean flag4;
		boolean flag5;
		float f7;
		int i1;
		if (renderer.renderAllFaces || block.shouldSideBeRendered(renderer.blockAccess, x + 1, y, z, 5))
        {

            renderer.aoLightValueScratchXYPN = block.getAmbientOcclusionLightValue(renderer.blockAccess, x, y - 1, z);
            renderer.aoLightValueScratchXZPN = block.getAmbientOcclusionLightValue(renderer.blockAccess, x, y, z - 1);
            renderer.aoLightValueScratchXZPP = block.getAmbientOcclusionLightValue(renderer.blockAccess, x, y, z + 1);
            renderer.aoLightValueScratchXYPP = block.getAmbientOcclusionLightValue(renderer.blockAccess, x, y + 1, z);
            renderer.aoBrightnessXYPN = block.getMixedBrightnessForBlock(renderer.blockAccess, x, y - 1, z);
            renderer.aoBrightnessXZPN = block.getMixedBrightnessForBlock(renderer.blockAccess, x, y, z - 1);
            renderer.aoBrightnessXZPP = block.getMixedBrightnessForBlock(renderer.blockAccess, x, y, z + 1);
            renderer.aoBrightnessXYPP = block.getMixedBrightnessForBlock(renderer.blockAccess, x, y + 1, z);
            flag3 = Block.canBlockGrass[renderer.blockAccess.getBlockId(x + 1, y + 1, z)];
            flag2 = Block.canBlockGrass[renderer.blockAccess.getBlockId(x + 1, y - 1, z)];
            flag5 = Block.canBlockGrass[renderer.blockAccess.getBlockId(x + 1, y, z + 1)];
            flag4 = Block.canBlockGrass[renderer.blockAccess.getBlockId(x + 1, y, z - 1)];

            if (!flag2 && !flag4)
            {
                renderer.aoLightValueScratchXYZPNN = renderer.aoLightValueScratchXZPN;
                renderer.aoBrightnessXYZPNN = renderer.aoBrightnessXZPN;
            }
            else
            {
                renderer.aoLightValueScratchXYZPNN = block.getAmbientOcclusionLightValue(renderer.blockAccess, x, y - 1, z - 1);
                renderer.aoBrightnessXYZPNN = block.getMixedBrightnessForBlock(renderer.blockAccess, x, y - 1, z - 1);
            }

            if (!flag2 && !flag5)
            {
                renderer.aoLightValueScratchXYZPNP = renderer.aoLightValueScratchXZPP;
                renderer.aoBrightnessXYZPNP = renderer.aoBrightnessXZPP;
            }
            else
            {
                renderer.aoLightValueScratchXYZPNP = block.getAmbientOcclusionLightValue(renderer.blockAccess, x, y - 1, z + 1);
                renderer.aoBrightnessXYZPNP = block.getMixedBrightnessForBlock(renderer.blockAccess, x, y - 1, z + 1);
            }

            if (!flag3 && !flag4)
            {
                renderer.aoLightValueScratchXYZPPN = renderer.aoLightValueScratchXZPN;
                renderer.aoBrightnessXYZPPN = renderer.aoBrightnessXZPN;
            }
            else
            {
                renderer.aoLightValueScratchXYZPPN = block.getAmbientOcclusionLightValue(renderer.blockAccess, x, y + 1, z - 1);
                renderer.aoBrightnessXYZPPN = block.getMixedBrightnessForBlock(renderer.blockAccess, x, y + 1, z - 1);
            }

            if (!flag3 && !flag5)
            {
                renderer.aoLightValueScratchXYZPPP = renderer.aoLightValueScratchXZPP;
                renderer.aoBrightnessXYZPPP = renderer.aoBrightnessXZPP;
            }
            else
            {
                renderer.aoLightValueScratchXYZPPP = block.getAmbientOcclusionLightValue(renderer.blockAccess, x, y + 1, z + 1);
                renderer.aoBrightnessXYZPPP = block.getMixedBrightnessForBlock(renderer.blockAccess, x, y + 1, z + 1);
            }

            i1 = l;

            if (renderer.renderMaxX >= 1.0D || !renderer.blockAccess.isBlockOpaqueCube(x + 1, y, z))
            {
                i1 = block.getMixedBrightnessForBlock(renderer.blockAccess, x + 1, y, z);
            }

            f7 = block.getAmbientOcclusionLightValue(renderer.blockAccess, x + 1, y, z);
            f3 = (renderer.aoLightValueScratchXYPN + renderer.aoLightValueScratchXYZPNP + f7 + renderer.aoLightValueScratchXZPP) / 4.0F;
            f4 = (renderer.aoLightValueScratchXYZPNN + renderer.aoLightValueScratchXYPN + renderer.aoLightValueScratchXZPN + f7) / 4.0F;
            f5 = (renderer.aoLightValueScratchXZPN + f7 + renderer.aoLightValueScratchXYZPPN + renderer.aoLightValueScratchXYPP) / 4.0F;
            f6 = (f7 + renderer.aoLightValueScratchXZPP + renderer.aoLightValueScratchXYPP + renderer.aoLightValueScratchXYZPPP) / 4.0F;
            renderer.brightnessTopLeft = renderer.getAoBrightness(renderer.aoBrightnessXYPN, renderer.aoBrightnessXYZPNP, renderer.aoBrightnessXZPP, i1);
            renderer.brightnessTopRight = renderer.getAoBrightness(renderer.aoBrightnessXZPP, renderer.aoBrightnessXYPP, renderer.aoBrightnessXYZPPP, i1);
            renderer.brightnessBottomRight = renderer.getAoBrightness(renderer.aoBrightnessXZPN, renderer.aoBrightnessXYZPPN, renderer.aoBrightnessXYPP, i1);
            renderer.brightnessBottomLeft = renderer.getAoBrightness(renderer.aoBrightnessXYZPNN, renderer.aoBrightnessXYPN, renderer.aoBrightnessXZPN, i1);

            if (flag1)
            {
                renderer.colorRedTopLeft = renderer.colorRedBottomLeft = renderer.colorRedBottomRight = renderer.colorRedTopRight = red * 0.6F;
                renderer.colorGreenTopLeft = renderer.colorGreenBottomLeft = renderer.colorGreenBottomRight = renderer.colorGreenTopRight = green * 0.6F;
                renderer.colorBlueTopLeft = renderer.colorBlueBottomLeft = renderer.colorBlueBottomRight = renderer.colorBlueTopRight = blue * 0.6F;
            }
            else
            {
                renderer.colorRedTopLeft = renderer.colorRedBottomLeft = renderer.colorRedBottomRight = renderer.colorRedTopRight = 0.6F;
                renderer.colorGreenTopLeft = renderer.colorGreenBottomLeft = renderer.colorGreenBottomRight = renderer.colorGreenTopRight = 0.6F;
                renderer.colorBlueTopLeft = renderer.colorBlueBottomLeft = renderer.colorBlueBottomRight = renderer.colorBlueTopRight = 0.6F;
            }

            renderer.colorRedTopLeft *= f3;
            renderer.colorGreenTopLeft *= f3;
            renderer.colorBlueTopLeft *= f3;
            renderer.colorRedBottomLeft *= f4;
            renderer.colorGreenBottomLeft *= f4;
            renderer.colorBlueBottomLeft *= f4;
            renderer.colorRedBottomRight *= f5;
            renderer.colorGreenBottomRight *= f5;
            renderer.colorBlueBottomRight *= f5;
            renderer.colorRedTopRight *= f6;
            renderer.colorGreenTopRight *= f6;
            renderer.colorBlueTopRight *= f6;
            Icon icon = renderer.getBlockIcon(block, renderer.blockAccess, x, y, z, 5);
            renderer.renderSouthFace(block, x, y, z, icon);

            if (icon.getIconName().contains("soil/dirt") && !renderer.hasOverrideBlockTexture())
            {
                renderer.colorRedTopLeft *= red;
                renderer.colorRedBottomLeft *= red;
                renderer.colorRedBottomRight *= red;
                renderer.colorRedTopRight *= red;
                renderer.colorGreenTopLeft *= green;
                renderer.colorGreenBottomLeft *= green;
                renderer.colorGreenBottomRight *= green;
                renderer.colorGreenTopRight *= green;
                renderer.colorBlueTopLeft *= blue;
                renderer.colorBlueBottomLeft *= blue;
                renderer.colorBlueBottomRight *= blue;
                renderer.colorBlueTopRight *= blue;
                renderer.renderSouthFace(block, x, y, z, BlockGrass.getIconSideOverlay());
            }
        }
	}

	private static void renderNorth(Block block, int x, int y, int z,
			RenderBlocks renderer, float red, float green, float blue,
			boolean flag1, int l) {
		float f3;
		float f4;
		float f5;
		float f6;
		boolean flag2;
		boolean flag3;
		boolean flag4;
		boolean flag5;
		float f7;
		int i1;
		if (renderer.renderAllFaces || block.shouldSideBeRendered(renderer.blockAccess, x - 1, y, z, 4))
        {

            renderer.aoLightValueScratchXYNN = block.getAmbientOcclusionLightValue(renderer.blockAccess, x, y - 1, z);
            renderer.aoLightValueScratchXZNN = block.getAmbientOcclusionLightValue(renderer.blockAccess, x, y, z - 1);
            renderer.aoLightValueScratchXZNP = block.getAmbientOcclusionLightValue(renderer.blockAccess, x, y, z + 1);
            renderer.aoLightValueScratchXYNP = block.getAmbientOcclusionLightValue(renderer.blockAccess, x, y + 1, z);
            renderer.aoBrightnessXYNN = block.getMixedBrightnessForBlock(renderer.blockAccess, x, y - 1, z);
            renderer.aoBrightnessXZNN = block.getMixedBrightnessForBlock(renderer.blockAccess, x, y, z - 1);
            renderer.aoBrightnessXZNP = block.getMixedBrightnessForBlock(renderer.blockAccess, x, y, z + 1);
            renderer.aoBrightnessXYNP = block.getMixedBrightnessForBlock(renderer.blockAccess, x, y + 1, z);
            flag3 = Block.canBlockGrass[renderer.blockAccess.getBlockId(x - 1, y + 1, z)];
            flag2 = Block.canBlockGrass[renderer.blockAccess.getBlockId(x - 1, y - 1, z)];
            flag5 = Block.canBlockGrass[renderer.blockAccess.getBlockId(x - 1, y, z - 1)];
            flag4 = Block.canBlockGrass[renderer.blockAccess.getBlockId(x - 1, y, z + 1)];

            if (!flag5 && !flag2)
            {
                renderer.aoLightValueScratchXYZNNN = renderer.aoLightValueScratchXZNN;
                renderer.aoBrightnessXYZNNN = renderer.aoBrightnessXZNN;
            }
            else
            {
                renderer.aoLightValueScratchXYZNNN = block.getAmbientOcclusionLightValue(renderer.blockAccess, x, y - 1, z - 1);
                renderer.aoBrightnessXYZNNN = block.getMixedBrightnessForBlock(renderer.blockAccess, x, y - 1, z - 1);
            }

            if (!flag4 && !flag2)
            {
                renderer.aoLightValueScratchXYZNNP = renderer.aoLightValueScratchXZNP;
                renderer.aoBrightnessXYZNNP = renderer.aoBrightnessXZNP;
            }
            else
            {
                renderer.aoLightValueScratchXYZNNP = block.getAmbientOcclusionLightValue(renderer.blockAccess, x, y - 1, z + 1);
                renderer.aoBrightnessXYZNNP = block.getMixedBrightnessForBlock(renderer.blockAccess, x, y - 1, z + 1);
            }

            if (!flag5 && !flag3)
            {
                renderer.aoLightValueScratchXYZNPN = renderer.aoLightValueScratchXZNN;
                renderer.aoBrightnessXYZNPN = renderer.aoBrightnessXZNN;
            }
            else
            {
                renderer.aoLightValueScratchXYZNPN = block.getAmbientOcclusionLightValue(renderer.blockAccess, x, y + 1, z - 1);
                renderer.aoBrightnessXYZNPN = block.getMixedBrightnessForBlock(renderer.blockAccess, x, y + 1, z - 1);
            }

            if (!flag4 && !flag3)
            {
                renderer.aoLightValueScratchXYZNPP = renderer.aoLightValueScratchXZNP;
                renderer.aoBrightnessXYZNPP = renderer.aoBrightnessXZNP;
            }
            else
            {
                renderer.aoLightValueScratchXYZNPP = block.getAmbientOcclusionLightValue(renderer.blockAccess, x, y + 1, z + 1);
                renderer.aoBrightnessXYZNPP = block.getMixedBrightnessForBlock(renderer.blockAccess, x, y + 1, z + 1);
            }

            i1 = l;

            if (renderer.renderMinX <= 0.0D || !renderer.blockAccess.isBlockOpaqueCube(x - 1, y, z))
            {
                i1 = block.getMixedBrightnessForBlock(renderer.blockAccess, x - 1, y, z);
            }

            f7 = block.getAmbientOcclusionLightValue(renderer.blockAccess, x - 1, y, z);
            f6 = (renderer.aoLightValueScratchXYNN + renderer.aoLightValueScratchXYZNNP + f7 + renderer.aoLightValueScratchXZNP) / 4.0F;
            f3 = (f7 + renderer.aoLightValueScratchXZNP + renderer.aoLightValueScratchXYNP + renderer.aoLightValueScratchXYZNPP) / 4.0F;
            f4 = (renderer.aoLightValueScratchXZNN + f7 + renderer.aoLightValueScratchXYZNPN + renderer.aoLightValueScratchXYNP) / 4.0F;
            f5 = (renderer.aoLightValueScratchXYZNNN + renderer.aoLightValueScratchXYNN + renderer.aoLightValueScratchXZNN + f7) / 4.0F;
            renderer.brightnessTopRight = renderer.getAoBrightness(renderer.aoBrightnessXYNN, renderer.aoBrightnessXYZNNP, renderer.aoBrightnessXZNP, i1);
            renderer.brightnessTopLeft = renderer.getAoBrightness(renderer.aoBrightnessXZNP, renderer.aoBrightnessXYNP, renderer.aoBrightnessXYZNPP, i1);
            renderer.brightnessBottomLeft = renderer.getAoBrightness(renderer.aoBrightnessXZNN, renderer.aoBrightnessXYZNPN, renderer.aoBrightnessXYNP, i1);
            renderer.brightnessBottomRight = renderer.getAoBrightness(renderer.aoBrightnessXYZNNN, renderer.aoBrightnessXYNN, renderer.aoBrightnessXZNN, i1);

            if (flag1)
            {
                renderer.colorRedTopLeft = renderer.colorRedBottomLeft = renderer.colorRedBottomRight = renderer.colorRedTopRight = red * 0.6F;
                renderer.colorGreenTopLeft = renderer.colorGreenBottomLeft = renderer.colorGreenBottomRight = renderer.colorGreenTopRight = green * 0.6F;
                renderer.colorBlueTopLeft = renderer.colorBlueBottomLeft = renderer.colorBlueBottomRight = renderer.colorBlueTopRight = blue * 0.6F;
            }
            else
            {
                renderer.colorRedTopLeft = renderer.colorRedBottomLeft = renderer.colorRedBottomRight = renderer.colorRedTopRight = 0.6F;
                renderer.colorGreenTopLeft = renderer.colorGreenBottomLeft = renderer.colorGreenBottomRight = renderer.colorGreenTopRight = 0.6F;
                renderer.colorBlueTopLeft = renderer.colorBlueBottomLeft = renderer.colorBlueBottomRight = renderer.colorBlueTopRight = 0.6F;
            }

            renderer.colorRedTopLeft *= f3;
            renderer.colorGreenTopLeft *= f3;
            renderer.colorBlueTopLeft *= f3;
            renderer.colorRedBottomLeft *= f4;
            renderer.colorGreenBottomLeft *= f4;
            renderer.colorBlueBottomLeft *= f4;
            renderer.colorRedBottomRight *= f5;
            renderer.colorGreenBottomRight *= f5;
            renderer.colorBlueBottomRight *= f5;
            renderer.colorRedTopRight *= f6;
            renderer.colorGreenTopRight *= f6;
            renderer.colorBlueTopRight *= f6;
            Icon  icon = renderer.getBlockIcon(block, renderer.blockAccess, x, y, z, 4);
            renderer.renderNorthFace(block, x, y, z, icon);

            if (icon.getIconName().contains("soil/dirt") && !renderer.hasOverrideBlockTexture())
            {
                renderer.colorRedTopLeft *= red;
                renderer.colorRedBottomLeft *= red;
                renderer.colorRedBottomRight *= red;
                renderer.colorRedTopRight *= red;
                renderer.colorGreenTopLeft *= green;
                renderer.colorGreenBottomLeft *= green;
                renderer.colorGreenBottomRight *= green;
                renderer.colorGreenTopRight *= green;
                renderer.colorBlueTopLeft *= blue;
                renderer.colorBlueBottomLeft *= blue;
                renderer.colorBlueBottomRight *= blue;
                renderer.colorBlueTopRight *= blue;
                renderer.renderNorthFace(block, x, y, z, BlockGrass.getIconSideOverlay());
            }
        }
	}

	private static void renderWest(Block block, int x, int y, int z,
			RenderBlocks renderer, float red, float green, float blue,
			boolean flag1, int l) {
		float f3;
		float f4;
		float f5;
		float f6;
		boolean flag2;
		boolean flag3;
		boolean flag4;
		boolean flag5;
		float f7;
		int i1;
		if (renderer.renderAllFaces || block.shouldSideBeRendered(renderer.blockAccess, x, y, z + 1, 3))
        {

            renderer.aoLightValueScratchXZNP = block.getAmbientOcclusionLightValue(renderer.blockAccess, x - 1, y, z);
            renderer.aoLightValueScratchXZPP = block.getAmbientOcclusionLightValue(renderer.blockAccess, x + 1, y, z);
            renderer.aoLightValueScratchYZNP = block.getAmbientOcclusionLightValue(renderer.blockAccess, x, y - 1, z);
            renderer.aoLightValueScratchYZPP = block.getAmbientOcclusionLightValue(renderer.blockAccess, x, y + 1, z);
            renderer.aoBrightnessXZNP = block.getMixedBrightnessForBlock(renderer.blockAccess, x - 1, y, z);
            renderer.aoBrightnessXZPP = block.getMixedBrightnessForBlock(renderer.blockAccess, x + 1, y, z);
            renderer.aoBrightnessYZNP = block.getMixedBrightnessForBlock(renderer.blockAccess, x, y - 1, z);
            renderer.aoBrightnessYZPP = block.getMixedBrightnessForBlock(renderer.blockAccess, x, y + 1, z);
            flag3 = Block.canBlockGrass[renderer.blockAccess.getBlockId(x + 1, y, z + 1)];
            flag2 = Block.canBlockGrass[renderer.blockAccess.getBlockId(x - 1, y, z + 1)];
            flag5 = Block.canBlockGrass[renderer.blockAccess.getBlockId(x, y + 1, z + 1)];
            flag4 = Block.canBlockGrass[renderer.blockAccess.getBlockId(x, y - 1, z + 1)];

            if (!flag2 && !flag4)
            {
                renderer.aoLightValueScratchXYZNNP = renderer.aoLightValueScratchXZNP;
                renderer.aoBrightnessXYZNNP = renderer.aoBrightnessXZNP;
            }
            else
            {
                renderer.aoLightValueScratchXYZNNP = block.getAmbientOcclusionLightValue(renderer.blockAccess, x - 1, y - 1, z);
                renderer.aoBrightnessXYZNNP = block.getMixedBrightnessForBlock(renderer.blockAccess, x - 1, y - 1, z);
            }

            if (!flag2 && !flag5)
            {
                renderer.aoLightValueScratchXYZNPP = renderer.aoLightValueScratchXZNP;
                renderer.aoBrightnessXYZNPP = renderer.aoBrightnessXZNP;
            }
            else
            {
                renderer.aoLightValueScratchXYZNPP = block.getAmbientOcclusionLightValue(renderer.blockAccess, x - 1, y + 1, z);
                renderer.aoBrightnessXYZNPP = block.getMixedBrightnessForBlock(renderer.blockAccess, x - 1, y + 1, z);
            }

            if (!flag3 && !flag4)
            {
                renderer.aoLightValueScratchXYZPNP = renderer.aoLightValueScratchXZPP;
                renderer.aoBrightnessXYZPNP = renderer.aoBrightnessXZPP;
            }
            else
            {
                renderer.aoLightValueScratchXYZPNP = block.getAmbientOcclusionLightValue(renderer.blockAccess, x + 1, y - 1, z);
                renderer.aoBrightnessXYZPNP = block.getMixedBrightnessForBlock(renderer.blockAccess, x + 1, y - 1, z);
            }

            if (!flag3 && !flag5)
            {
                renderer.aoLightValueScratchXYZPPP = renderer.aoLightValueScratchXZPP;
                renderer.aoBrightnessXYZPPP = renderer.aoBrightnessXZPP;
            }
            else
            {
                renderer.aoLightValueScratchXYZPPP = block.getAmbientOcclusionLightValue(renderer.blockAccess, x + 1, y + 1, z);
                renderer.aoBrightnessXYZPPP = block.getMixedBrightnessForBlock(renderer.blockAccess, x + 1, y + 1, z);
            }
            i1 = l;

            if (renderer.renderMaxZ >= 1.0D || !renderer.blockAccess.isBlockOpaqueCube(x, y, z + 1))
            {
                i1 = block.getMixedBrightnessForBlock(renderer.blockAccess, x, y, z + 1);
            }

            f7 = block.getAmbientOcclusionLightValue(renderer.blockAccess, x, y, z + 1);
            f3 = (renderer.aoLightValueScratchXZNP + renderer.aoLightValueScratchXYZNPP + f7 + renderer.aoLightValueScratchYZPP) / 4.0F;
            f6 = (f7 + renderer.aoLightValueScratchYZPP + renderer.aoLightValueScratchXZPP + renderer.aoLightValueScratchXYZPPP) / 4.0F;
            f5 = (renderer.aoLightValueScratchYZNP + f7 + renderer.aoLightValueScratchXYZPNP + renderer.aoLightValueScratchXZPP) / 4.0F;
            f4 = (renderer.aoLightValueScratchXYZNNP + renderer.aoLightValueScratchXZNP + renderer.aoLightValueScratchYZNP + f7) / 4.0F;
            renderer.brightnessTopLeft = renderer.getAoBrightness(renderer.aoBrightnessXZNP, renderer.aoBrightnessXYZNPP, renderer.aoBrightnessYZPP, i1);
            renderer.brightnessTopRight = renderer.getAoBrightness(renderer.aoBrightnessYZPP, renderer.aoBrightnessXZPP, renderer.aoBrightnessXYZPPP, i1);
            renderer.brightnessBottomRight = renderer.getAoBrightness(renderer.aoBrightnessYZNP, renderer.aoBrightnessXYZPNP, renderer.aoBrightnessXZPP, i1);
            renderer.brightnessBottomLeft = renderer.getAoBrightness(renderer.aoBrightnessXYZNNP, renderer.aoBrightnessXZNP, renderer.aoBrightnessYZNP, i1);

            if (flag1)
            {
                renderer.colorRedTopLeft = renderer.colorRedBottomLeft = renderer.colorRedBottomRight = renderer.colorRedTopRight = red * 0.8F;
                renderer.colorGreenTopLeft = renderer.colorGreenBottomLeft = renderer.colorGreenBottomRight = renderer.colorGreenTopRight = green * 0.8F;
                renderer.colorBlueTopLeft = renderer.colorBlueBottomLeft = renderer.colorBlueBottomRight = renderer.colorBlueTopRight = blue * 0.8F;
            }
            else
            {
                renderer.colorRedTopLeft = renderer.colorRedBottomLeft = renderer.colorRedBottomRight = renderer.colorRedTopRight = 0.8F;
                renderer.colorGreenTopLeft = renderer.colorGreenBottomLeft = renderer.colorGreenBottomRight = renderer.colorGreenTopRight = 0.8F;
                renderer.colorBlueTopLeft = renderer.colorBlueBottomLeft = renderer.colorBlueBottomRight = renderer.colorBlueTopRight = 0.8F;
            }

            renderer.colorRedTopLeft *= f3;
            renderer.colorGreenTopLeft *= f3;
            renderer.colorBlueTopLeft *= f3;
            renderer.colorRedBottomLeft *= f4;
            renderer.colorGreenBottomLeft *= f4;
            renderer.colorBlueBottomLeft *= f4;
            renderer.colorRedBottomRight *= f5;
            renderer.colorGreenBottomRight *= f5;
            renderer.colorBlueBottomRight *= f5;
            renderer.colorRedTopRight *= f6;
            renderer.colorGreenTopRight *= f6;
            renderer.colorBlueTopRight *= f6;
            Icon icon = renderer.getBlockIcon(block, renderer.blockAccess, x, y, z, 3);
            renderer.renderWestFace(block, x, y, z, renderer.getBlockIcon(block, renderer.blockAccess, x, y, z, 3));

            if (icon.getIconName().contains("soil/dirt") && !renderer.hasOverrideBlockTexture())
            {
                renderer.colorRedTopLeft *= red;
                renderer.colorRedBottomLeft *= red;
                renderer.colorRedBottomRight *= red;
                renderer.colorRedTopRight *= red;
                renderer.colorGreenTopLeft *= green;
                renderer.colorGreenBottomLeft *= green;
                renderer.colorGreenBottomRight *= green;
                renderer.colorGreenTopRight *= green;
                renderer.colorBlueTopLeft *= blue;
                renderer.colorBlueBottomLeft *= blue;
                renderer.colorBlueBottomRight *= blue;
                renderer.colorBlueTopRight *= blue;
                renderer.renderWestFace(block, x, y, z, BlockGrass.getIconSideOverlay());
            }
        }
	}

	private static void renderEast(Block block, int x, int y, int z,
			RenderBlocks renderer, float red, float green, float blue,
			boolean flag1, int l) {
		float f3;
		float f4;
		float f5;
		float f6;
		boolean flag2;
		boolean flag3;
		boolean flag4;
		boolean flag5;
		float f7;
		int i1;
		if (renderer.renderAllFaces || block.shouldSideBeRendered(renderer.blockAccess, x, y, z - 1, 2))
        {
            renderer.aoLightValueScratchXZNN = block.getAmbientOcclusionLightValue(renderer.blockAccess, x - 1, y, z);
            renderer.aoLightValueScratchYZNN = block.getAmbientOcclusionLightValue(renderer.blockAccess, x, y - 1, z);
            renderer.aoLightValueScratchYZPN = block.getAmbientOcclusionLightValue(renderer.blockAccess, x, y + 1, z);
            renderer.aoLightValueScratchXZPN = block.getAmbientOcclusionLightValue(renderer.blockAccess, x + 1, y, z);
            renderer.aoBrightnessXZNN = block.getMixedBrightnessForBlock(renderer.blockAccess, x - 1, y, z);
            renderer.aoBrightnessYZNN = block.getMixedBrightnessForBlock(renderer.blockAccess, x, y - 1, z);
            renderer.aoBrightnessYZPN = block.getMixedBrightnessForBlock(renderer.blockAccess, x, y + 1, z);
            renderer.aoBrightnessXZPN = block.getMixedBrightnessForBlock(renderer.blockAccess, x + 1, y, z);
            flag3 = Block.canBlockGrass[renderer.blockAccess.getBlockId(x + 1, y, z - 1)];
            flag2 = Block.canBlockGrass[renderer.blockAccess.getBlockId(x - 1, y, z - 1)];
            flag5 = Block.canBlockGrass[renderer.blockAccess.getBlockId(x, y + 1, z - 1)];
            flag4 = Block.canBlockGrass[renderer.blockAccess.getBlockId(x, y - 1, z - 1)];

            if (!flag2 && !flag4)
            {
                renderer.aoLightValueScratchXYZNNN = renderer.aoLightValueScratchXZNN;
                renderer.aoBrightnessXYZNNN = renderer.aoBrightnessXZNN;
            }
            else
            {
                renderer.aoLightValueScratchXYZNNN = block.getAmbientOcclusionLightValue(renderer.blockAccess, x - 1, y - 1, z);
                renderer.aoBrightnessXYZNNN = block.getMixedBrightnessForBlock(renderer.blockAccess, x - 1, y - 1, z);
            }

            if (!flag2 && !flag5)
            {
                renderer.aoLightValueScratchXYZNPN = renderer.aoLightValueScratchXZNN;
                renderer.aoBrightnessXYZNPN = renderer.aoBrightnessXZNN;
            }
            else
            {
                renderer.aoLightValueScratchXYZNPN = block.getAmbientOcclusionLightValue(renderer.blockAccess, x - 1, y + 1, z);
                renderer.aoBrightnessXYZNPN = block.getMixedBrightnessForBlock(renderer.blockAccess, x - 1, y + 1, z);
            }

            if (!flag3 && !flag4)
            {
                renderer.aoLightValueScratchXYZPNN = renderer.aoLightValueScratchXZPN;
                renderer.aoBrightnessXYZPNN = renderer.aoBrightnessXZPN;
            }
            else
            {
                renderer.aoLightValueScratchXYZPNN = block.getAmbientOcclusionLightValue(renderer.blockAccess, x + 1, y - 1, z);
                renderer.aoBrightnessXYZPNN = block.getMixedBrightnessForBlock(renderer.blockAccess, x + 1, y - 1, z);
            }

            if (!flag3 && !flag5)
            {
                renderer.aoLightValueScratchXYZPPN = renderer.aoLightValueScratchXZPN;
                renderer.aoBrightnessXYZPPN = renderer.aoBrightnessXZPN;
            }
            else
            {
                renderer.aoLightValueScratchXYZPPN = block.getAmbientOcclusionLightValue(renderer.blockAccess, x + 1, y + 1, z);
                renderer.aoBrightnessXYZPPN = block.getMixedBrightnessForBlock(renderer.blockAccess, x + 1, y + 1, z);
            }
            
            i1 = l;

            if (renderer.renderMinZ <= 0.0D || !renderer.blockAccess.isBlockOpaqueCube(x, y, z - 1))
            {
                i1 = block.getMixedBrightnessForBlock(renderer.blockAccess, x, y, z - 1);
            }

            f7 = block.getAmbientOcclusionLightValue(renderer.blockAccess, x, y, z - 1);
            f3 = (renderer.aoLightValueScratchXZNN + renderer.aoLightValueScratchXYZNPN + f7 + renderer.aoLightValueScratchYZPN) / 4.0F;
            f4 = (f7 + renderer.aoLightValueScratchYZPN + renderer.aoLightValueScratchXZPN + renderer.aoLightValueScratchXYZPPN) / 4.0F;
            f5 = (renderer.aoLightValueScratchYZNN + f7 + renderer.aoLightValueScratchXYZPNN + renderer.aoLightValueScratchXZPN) / 4.0F;
            f6 = (renderer.aoLightValueScratchXYZNNN + renderer.aoLightValueScratchXZNN + renderer.aoLightValueScratchYZNN + f7) / 4.0F;
            renderer.brightnessTopLeft = renderer.getAoBrightness(renderer.aoBrightnessXZNN, renderer.aoBrightnessXYZNPN, renderer.aoBrightnessYZPN, i1);
            renderer.brightnessBottomLeft = renderer.getAoBrightness(renderer.aoBrightnessYZPN, renderer.aoBrightnessXZPN, renderer.aoBrightnessXYZPPN, i1);
            renderer.brightnessBottomRight = renderer.getAoBrightness(renderer.aoBrightnessYZNN, renderer.aoBrightnessXYZPNN, renderer.aoBrightnessXZPN, i1);
            renderer.brightnessTopRight = renderer.getAoBrightness(renderer.aoBrightnessXYZNNN, renderer.aoBrightnessXZNN, renderer.aoBrightnessYZNN, i1);

            if (flag1)
            {
                renderer.colorRedTopLeft = renderer.colorRedBottomLeft = renderer.colorRedBottomRight = renderer.colorRedTopRight = red * 0.8F;
                renderer.colorGreenTopLeft = renderer.colorGreenBottomLeft = renderer.colorGreenBottomRight = renderer.colorGreenTopRight = green * 0.8F;
                renderer.colorBlueTopLeft = renderer.colorBlueBottomLeft = renderer.colorBlueBottomRight = renderer.colorBlueTopRight = blue * 0.8F;
            }
            else
            {
                renderer.colorRedTopLeft = renderer.colorRedBottomLeft = renderer.colorRedBottomRight = renderer.colorRedTopRight = 0.8F;
                renderer.colorGreenTopLeft = renderer.colorGreenBottomLeft = renderer.colorGreenBottomRight = renderer.colorGreenTopRight = 0.8F;
                renderer.colorBlueTopLeft = renderer.colorBlueBottomLeft = renderer.colorBlueBottomRight = renderer.colorBlueTopRight = 0.8F;
            }

            renderer.colorRedTopLeft *= f3;
            renderer.colorGreenTopLeft *= f3;
            renderer.colorBlueTopLeft *= f3;
            renderer.colorRedBottomLeft *= f4;
            renderer.colorGreenBottomLeft *= f4;
            renderer.colorBlueBottomLeft *= f4;
            renderer.colorRedBottomRight *= f5;
            renderer.colorGreenBottomRight *= f5;
            renderer.colorBlueBottomRight *= f5;
            renderer.colorRedTopRight *= f6;
            renderer.colorGreenTopRight *= f6;
            renderer.colorBlueTopRight *= f6;
            Icon icon = renderer.getBlockIcon(block, renderer.blockAccess, x, y, z, 2);
            renderer.renderEastFace(block, x, y, z, icon);

            if (icon.getIconName().contains("soil/dirt") && !renderer.hasOverrideBlockTexture())
            {
                renderer.colorRedTopLeft *= red;
                renderer.colorRedBottomLeft *= red;
                renderer.colorRedBottomRight *= red;
                renderer.colorRedTopRight *= red;
                renderer.colorGreenTopLeft *= green;
                renderer.colorGreenBottomLeft *= green;
                renderer.colorGreenBottomRight *= green;
                renderer.colorGreenTopRight *= green;
                renderer.colorBlueTopLeft *= blue;
                renderer.colorBlueBottomLeft *= blue;
                renderer.colorBlueBottomRight *= blue;
                renderer.colorBlueTopRight *= blue;
                renderer.renderEastFace(block, x, y, z, BlockGrass.getIconSideOverlay());
            }
        }
	}

	private static void renderTop(Block block, int x, int y, int z,
			RenderBlocks renderer, float red, float green, float blue, int l) {
		float f3;
		float f4;
		float f5;
		float f6;
		boolean flag2;
		boolean flag3;
		boolean flag4;
		boolean flag5;
		float f7;
		int i1;
		if (renderer.renderAllFaces || block.shouldSideBeRendered(renderer.blockAccess, x, y + 1, z, 1))
        {

            renderer.aoBrightnessXYNP = block.getMixedBrightnessForBlock(renderer.blockAccess, x - 1, y, z);
            renderer.aoBrightnessXYPP = block.getMixedBrightnessForBlock(renderer.blockAccess, x + 1, y, z);
            renderer.aoBrightnessYZPN = block.getMixedBrightnessForBlock(renderer.blockAccess, x, y, z - 1);
            renderer.aoBrightnessYZPP = block.getMixedBrightnessForBlock(renderer.blockAccess, x, y, z + 1);
            renderer.aoLightValueScratchXYNP = block.getAmbientOcclusionLightValue(renderer.blockAccess, x - 1, y, z);
            renderer.aoLightValueScratchXYPP = block.getAmbientOcclusionLightValue(renderer.blockAccess, x + 1, y, z);
            renderer.aoLightValueScratchYZPN = block.getAmbientOcclusionLightValue(renderer.blockAccess, x, y, z - 1);
            renderer.aoLightValueScratchYZPP = block.getAmbientOcclusionLightValue(renderer.blockAccess, x, y, z + 1);
            flag3 = Block.canBlockGrass[renderer.blockAccess.getBlockId(x + 1, y + 1, z)];
            flag2 = Block.canBlockGrass[renderer.blockAccess.getBlockId(x - 1, y + 1, z)];
            flag5 = Block.canBlockGrass[renderer.blockAccess.getBlockId(x, y + 1, z + 1)];
            flag4 = Block.canBlockGrass[renderer.blockAccess.getBlockId(x, y + 1, z - 1)];

            if (!flag4 && !flag2)
            {
                renderer.aoLightValueScratchXYZNPN = renderer.aoLightValueScratchXYNP;
                renderer.aoBrightnessXYZNPN = renderer.aoBrightnessXYNP;
            }
            else
            {
                renderer.aoLightValueScratchXYZNPN = block.getAmbientOcclusionLightValue(renderer.blockAccess, x - 1, y, z - 1);
                renderer.aoBrightnessXYZNPN = block.getMixedBrightnessForBlock(renderer.blockAccess, x - 1, y, z - 1);
            }

            if (!flag4 && !flag3)
            {
                renderer.aoLightValueScratchXYZPPN = renderer.aoLightValueScratchXYPP;
                renderer.aoBrightnessXYZPPN = renderer.aoBrightnessXYPP;
            }
            else
            {
                renderer.aoLightValueScratchXYZPPN = block.getAmbientOcclusionLightValue(renderer.blockAccess, x + 1, y, z - 1);
                renderer.aoBrightnessXYZPPN = block.getMixedBrightnessForBlock(renderer.blockAccess, x + 1, y, z - 1);
            }

            if (!flag5 && !flag2)
            {
                renderer.aoLightValueScratchXYZNPP = renderer.aoLightValueScratchXYNP;
                renderer.aoBrightnessXYZNPP = renderer.aoBrightnessXYNP;
            }
            else
            {
                renderer.aoLightValueScratchXYZNPP = block.getAmbientOcclusionLightValue(renderer.blockAccess, x - 1, y, z + 1);
                renderer.aoBrightnessXYZNPP = block.getMixedBrightnessForBlock(renderer.blockAccess, x - 1, y, z + 1);
            }

            if (!flag5 && !flag3)
            {
                renderer.aoLightValueScratchXYZPPP = renderer.aoLightValueScratchXYPP;
                renderer.aoBrightnessXYZPPP = renderer.aoBrightnessXYPP;
            }
            else
            {
                renderer.aoLightValueScratchXYZPPP = block.getAmbientOcclusionLightValue(renderer.blockAccess, x + 1, y, z + 1);
                renderer.aoBrightnessXYZPPP = block.getMixedBrightnessForBlock(renderer.blockAccess, x + 1, y, z + 1);
            }

            i1 = l;

            if (renderer.renderMaxY >= 1.0D || !renderer.blockAccess.isBlockOpaqueCube(x, y + 1, z))
            {
                i1 = block.getMixedBrightnessForBlock(renderer.blockAccess, x, y + 1, z);
            }

            f7 = block.getAmbientOcclusionLightValue(renderer.blockAccess, x, y + 1, z);
            f6 = (renderer.aoLightValueScratchXYZNPP + renderer.aoLightValueScratchXYNP + renderer.aoLightValueScratchYZPP + f7) / 4.0F;
            f3 = (renderer.aoLightValueScratchYZPP + f7 + renderer.aoLightValueScratchXYZPPP + renderer.aoLightValueScratchXYPP) / 4.0F;
            f4 = (f7 + renderer.aoLightValueScratchYZPN + renderer.aoLightValueScratchXYPP + renderer.aoLightValueScratchXYZPPN) / 4.0F;
            f5 = (renderer.aoLightValueScratchXYNP + renderer.aoLightValueScratchXYZNPN + f7 + renderer.aoLightValueScratchYZPN) / 4.0F;
            renderer.brightnessTopRight = renderer.getAoBrightness(renderer.aoBrightnessXYZNPP, renderer.aoBrightnessXYNP, renderer.aoBrightnessYZPP, i1);
            renderer.brightnessTopLeft = renderer.getAoBrightness(renderer.aoBrightnessYZPP, renderer.aoBrightnessXYZPPP, renderer.aoBrightnessXYPP, i1);
            renderer.brightnessBottomLeft = renderer.getAoBrightness(renderer.aoBrightnessYZPN, renderer.aoBrightnessXYPP, renderer.aoBrightnessXYZPPN, i1);
            renderer.brightnessBottomRight = renderer.getAoBrightness(renderer.aoBrightnessXYNP, renderer.aoBrightnessXYZNPN, renderer.aoBrightnessYZPN, i1);
            renderer.colorRedTopLeft = renderer.colorRedBottomLeft = renderer.colorRedBottomRight = renderer.colorRedTopRight = red;
            renderer.colorGreenTopLeft = renderer.colorGreenBottomLeft = renderer.colorGreenBottomRight = renderer.colorGreenTopRight = green;
            renderer.colorBlueTopLeft = renderer.colorBlueBottomLeft = renderer.colorBlueBottomRight = renderer.colorBlueTopRight = blue;
            renderer.colorRedTopLeft *= f3;
            renderer.colorGreenTopLeft *= f3;
            renderer.colorBlueTopLeft *= f3;
            renderer.colorRedBottomLeft *= f4;
            renderer.colorGreenBottomLeft *= f4;
            renderer.colorBlueBottomLeft *= f4;
            renderer.colorRedBottomRight *= f5;
            renderer.colorGreenBottomRight *= f5;
            renderer.colorBlueBottomRight *= f5;
            renderer.colorRedTopRight *= f6;
            renderer.colorGreenTopRight *= f6;
            renderer.colorBlueTopRight *= f6;
            renderer.renderTopFace(block, x, y, z, renderer.getBlockIcon(block, renderer.blockAccess, x, y, z, 1));
        }
	}

	private static void renderBottom(Block block, int x, int y, int z,
			RenderBlocks renderer, float red, float green, float blue,
			boolean flag1, int l) {
		float f3;
		float f4;
		float f5;
		float f6;
		boolean flag2;
		boolean flag3;
		boolean flag4;
		boolean flag5;
		float f7;
		int i1;
		if (renderer.renderAllFaces || block.shouldSideBeRendered(renderer.blockAccess, x, y - 1, z, 0))
        {
            renderer.aoBrightnessXYNN = block.getMixedBrightnessForBlock(renderer.blockAccess, x - 1, y, z);
            renderer.aoBrightnessYZNN = block.getMixedBrightnessForBlock(renderer.blockAccess, x, y, z - 1);
            renderer.aoBrightnessYZNP = block.getMixedBrightnessForBlock(renderer.blockAccess, x, y, z + 1);
            renderer.aoBrightnessXYPN = block.getMixedBrightnessForBlock(renderer.blockAccess, x + 1, y, z);
            renderer.aoLightValueScratchXYNN = block.getAmbientOcclusionLightValue(renderer.blockAccess, x - 1, y, z);
            renderer.aoLightValueScratchYZNN = block.getAmbientOcclusionLightValue(renderer.blockAccess, x, y, z - 1);
            renderer.aoLightValueScratchYZNP = block.getAmbientOcclusionLightValue(renderer.blockAccess, x, y, z + 1);
            renderer.aoLightValueScratchXYPN = block.getAmbientOcclusionLightValue(renderer.blockAccess, x + 1, y, z);
            flag3 = Block.canBlockGrass[renderer.blockAccess.getBlockId(x + 1, y - 1, z)];
            flag2 = Block.canBlockGrass[renderer.blockAccess.getBlockId(x - 1, y - 1, z)];
            flag5 = Block.canBlockGrass[renderer.blockAccess.getBlockId(x, y - 1, z + 1)];
            flag4 = Block.canBlockGrass[renderer.blockAccess.getBlockId(x, y - 1, z - 1)];

            if (!flag4 && !flag2)
            {
                renderer.aoLightValueScratchXYZNNN = renderer.aoLightValueScratchXYNN;
                renderer.aoBrightnessXYZNNN = renderer.aoBrightnessXYNN;
            }
            else
            {
                renderer.aoLightValueScratchXYZNNN = block.getAmbientOcclusionLightValue(renderer.blockAccess, x - 1, y, z - 1);
                renderer.aoBrightnessXYZNNN = block.getMixedBrightnessForBlock(renderer.blockAccess, x - 1, y, z - 1);
            }

            if (!flag5 && !flag2)
            {
                renderer.aoLightValueScratchXYZNNP = renderer.aoLightValueScratchXYNN;
                renderer.aoBrightnessXYZNNP = renderer.aoBrightnessXYNN;
            }
            else
            {
                renderer.aoLightValueScratchXYZNNP = block.getAmbientOcclusionLightValue(renderer.blockAccess, x - 1, y, z + 1);
                renderer.aoBrightnessXYZNNP = block.getMixedBrightnessForBlock(renderer.blockAccess, x - 1, y, z + 1);
            }

            if (!flag4 && !flag3)
            {
                renderer.aoLightValueScratchXYZPNN = renderer.aoLightValueScratchXYPN;
                renderer.aoBrightnessXYZPNN = renderer.aoBrightnessXYPN;
            }
            else
            {
                renderer.aoLightValueScratchXYZPNN = block.getAmbientOcclusionLightValue(renderer.blockAccess, x + 1, y, z - 1);
                renderer.aoBrightnessXYZPNN = block.getMixedBrightnessForBlock(renderer.blockAccess, x + 1, y, z - 1);
            }

            if (!flag5 && !flag3)
            {
                renderer.aoLightValueScratchXYZPNP = renderer.aoLightValueScratchXYPN;
                renderer.aoBrightnessXYZPNP = renderer.aoBrightnessXYPN;
            }
            else
            {
                renderer.aoLightValueScratchXYZPNP = block.getAmbientOcclusionLightValue(renderer.blockAccess, x + 1, y, z + 1);
                renderer.aoBrightnessXYZPNP = block.getMixedBrightnessForBlock(renderer.blockAccess, x + 1, y, z + 1);
            }

            i1 = l;

            if (renderer.renderMinY <= 0.0D || !renderer.blockAccess.isBlockOpaqueCube(x, y - 1, z))
            {
                i1 = block.getMixedBrightnessForBlock(renderer.blockAccess, x, y - 1, z);
            }

            f7 = block.getAmbientOcclusionLightValue(renderer.blockAccess, x, y - 1, z);
            f3 = (renderer.aoLightValueScratchXYZNNP + renderer.aoLightValueScratchXYNN + renderer.aoLightValueScratchYZNP + f7) / 4.0F;
            f6 = (renderer.aoLightValueScratchYZNP + f7 + renderer.aoLightValueScratchXYZPNP + renderer.aoLightValueScratchXYPN) / 4.0F;
            f5 = (f7 + renderer.aoLightValueScratchYZNN + renderer.aoLightValueScratchXYPN + renderer.aoLightValueScratchXYZPNN) / 4.0F;
            f4 = (renderer.aoLightValueScratchXYNN + renderer.aoLightValueScratchXYZNNN + f7 + renderer.aoLightValueScratchYZNN) / 4.0F;
            renderer.brightnessTopLeft = renderer.getAoBrightness(renderer.aoBrightnessXYZNNP, renderer.aoBrightnessXYNN, renderer.aoBrightnessYZNP, i1);
            renderer.brightnessTopRight = renderer.getAoBrightness(renderer.aoBrightnessYZNP, renderer.aoBrightnessXYZPNP, renderer.aoBrightnessXYPN, i1);
            renderer.brightnessBottomRight = renderer.getAoBrightness(renderer.aoBrightnessYZNN, renderer.aoBrightnessXYPN, renderer.aoBrightnessXYZPNN, i1);
            renderer.brightnessBottomLeft = renderer.getAoBrightness(renderer.aoBrightnessXYNN, renderer.aoBrightnessXYZNNN, renderer.aoBrightnessYZNN, i1);

            if (flag1)
            {
                renderer.colorRedTopLeft = renderer.colorRedBottomLeft = renderer.colorRedBottomRight = renderer.colorRedTopRight = red * 0.5F;
                renderer.colorGreenTopLeft = renderer.colorGreenBottomLeft = renderer.colorGreenBottomRight = renderer.colorGreenTopRight = green * 0.5F;
                renderer.colorBlueTopLeft = renderer.colorBlueBottomLeft = renderer.colorBlueBottomRight = renderer.colorBlueTopRight = blue * 0.5F;
            }
            else
            {
                renderer.colorRedTopLeft = renderer.colorRedBottomLeft = renderer.colorRedBottomRight = renderer.colorRedTopRight = 0.5F;
                renderer.colorGreenTopLeft = renderer.colorGreenBottomLeft = renderer.colorGreenBottomRight = renderer.colorGreenTopRight = 0.5F;
                renderer.colorBlueTopLeft = renderer.colorBlueBottomLeft = renderer.colorBlueBottomRight = renderer.colorBlueTopRight = 0.5F;
            }

            renderer.colorRedTopLeft *= f3;
            renderer.colorGreenTopLeft *= f3;
            renderer.colorBlueTopLeft *= f3;
            renderer.colorRedBottomLeft *= f4;
            renderer.colorGreenBottomLeft *= f4;
            renderer.colorBlueBottomLeft *= f4;
            renderer.colorRedBottomRight *= f5;
            renderer.colorGreenBottomRight *= f5;
            renderer.colorBlueBottomRight *= f5;
            renderer.colorRedTopRight *= f6;
            renderer.colorGreenTopRight *= f6;
            renderer.colorBlueTopRight *= f6;
            renderer.renderBottomFace(block, x, y, z, renderer.getBlockIcon(block, renderer.blockAccess, x, y, z, 0));
        }
	}
}
