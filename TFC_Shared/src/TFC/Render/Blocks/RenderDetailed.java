package TFC.Render.Blocks;

import org.lwjgl.opengl.GL11;
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
import net.minecraftforge.client.ForgeHooksClient;
import TFC.TFCBlocks;
import TFC.Render.TFC_CoreRender;
import TFC.TileEntities.TileEntityDetailed;
import TFC.TileEntities.TileEntityPartial;

public class RenderDetailed 
{
	public static boolean renderBlockDetailed(Block block, int i, int j, int k, RenderBlocks renderblocks)
	{
		TileEntityDetailed te = (TileEntityDetailed) renderblocks.blockAccess.getBlockTileEntity(i, j, k);
		int md = renderblocks.blockAccess.getBlockMetadata(i, j, k);

		if(te.TypeID <= 0) return false;

		int type = te.TypeID;
		int meta = te.MetaID;

		boolean breaking = false;
		if(renderblocks.overrideBlockTexture >= 240)
		{
			breaking = true;
		}

		if(!breaking)
			ForgeHooksClient.bindTexture(Block.blocksList[type].getTextureFile(), ModLoader.getMinecraftInstance().renderEngine.getTexture(Block.blocksList[type].getTextureFile()));


		for(int subX = 0; subX < 2; subX++)
		{
			for(int subZ = 0; subZ < 2; subZ++)
			{
				for(int subY = 0; subY < 2; subY++)
				{
					if(!te.isQuadSolid(subX, subY, subZ))
						renderMiniBlock(i, j, k, subX, subY, subZ, renderblocks, te, type, meta);
					else
					{
						float minX = 0.5f * subX;
						float maxX = minX + 0.5f;
						float minY = 0.5f * subY;
						float maxY = minY + 0.5f;
						float minZ = 0.5f * subZ;
						float maxZ = minZ + 0.5f;

						renderblocks.setRenderBounds(minX, minY, minZ, maxX, maxY, maxZ);
						renderStandardBlockWithColorMultiplier(Block.blocksList[type], renderblocks, i, j, k, 0.95f, 0.95f, 0.95f, meta);
					}
				}
			}
		}

		return true;
	}

	private static void renderMiniBlock(int i, int j, int k, int x, int y, int z,
			RenderBlocks renderblocks, TileEntityDetailed te, int type, int meta) {
		for(int subX = x*4; subX < 4+x*4; subX++)
		{
			for(int subZ = z*4; subZ < 4+z*4; subZ++)
			{
				for(int subY = y*4; subY < 4+y*4; subY++)
				{
					boolean subExists = isOpaque(te,subX, subY, subZ);
					if(subExists)
					{
						float minX = 0.125f*subX;
						float maxX = minX + 0.125f;
						float minY = 0.125f*subY;
						float maxY = minY + 0.125f;
						float minZ = 0.125f*subZ;
						float maxZ = minZ + 0.125f;

						renderblocks.setRenderBounds(minX, minY, minZ, maxX, maxY, maxZ);
						renderStandardBlockWithColorMultiplier(Block.blocksList[type], renderblocks, i, j, k, 0.95f, 0.95f, 0.95f, meta);
					}
				}
			}
		}
	}

	public static boolean renderStandardBlockWithColorMultiplier(Block par1Block, RenderBlocks renderblocks, int par2, int par3, int par4, float par5, float par6, float par7, int meta)
	{
		renderblocks.enableAO = false;
		Tessellator var8 = Tessellator.instance;

		float var10 = 0.5F;
		float var11 = 1.0F;
		float var12 = 0.8F;
		float var13 = 0.6F;
		float var14 = var11 * par5;
		float var15 = var11 * par6;
		float var16 = var11 * par7;
		float var17 = var10;
		float var18 = var12;
		float var19 = var13;
		float var20 = var10;
		float var21 = var12;
		float var22 = var13;
		float var23 = var10;
		float var24 = var12;
		float var25 = var13;

		if (par1Block != Block.grass)
		{
			var17 = var10 * par5;
			var18 = var12 * par5;
			var19 = var13 * par5;
			var20 = var10 * par6;
			var21 = var12 * par6;
			var22 = var13 * par6;
			var23 = var10 * par7;
			var24 = var12 * par7;
			var25 = var13 * par7;
		}

		int var26 = par1Block.getMixedBrightnessForBlock(renderblocks.blockAccess, par2, par3, par4);

		var8.setBrightness(renderblocks.customMinY > 0.0D ? var26 : par1Block.getMixedBrightnessForBlock(renderblocks.blockAccess, par2, par3 - 1, par4));
		var8.setColorOpaque_F(var17, var20, var23);
		renderblocks.renderBottomFace(par1Block, (double)par2, (double)par3, (double)par4, par1Block.getBlockTextureFromSideAndMetadata(0, meta));


		var8.setBrightness(renderblocks.customMaxY < 1.0D ? var26 : par1Block.getMixedBrightnessForBlock(renderblocks.blockAccess, par2, par3 + 1, par4));
		var8.setColorOpaque_F(var14, var15, var16);
		renderblocks.renderTopFace(par1Block, (double)par2, (double)par3, (double)par4, par1Block.getBlockTextureFromSideAndMetadata(1, meta));


		int var28;

		var8.setBrightness(renderblocks.customMaxX > 0.0D ? var26 : par1Block.getMixedBrightnessForBlock(renderblocks.blockAccess, par2, par3, par4 - 1));
		var8.setColorOpaque_F(var18, var21, var24);
		var28 = par1Block.getBlockTextureFromSideAndMetadata(2, meta);

		renderblocks.renderEastFace(par1Block, (double)par2, (double)par3, (double)par4, var28);

		var8.setBrightness(renderblocks.customMinX < 1.0D ? var26 : par1Block.getMixedBrightnessForBlock(renderblocks.blockAccess, par2, par3, par4 + 1));
		var8.setColorOpaque_F(var18, var21, var24);
		var28 = par1Block.getBlockTextureFromSideAndMetadata(3, meta);

		renderblocks.renderWestFace(par1Block, (double)par2, (double)par3, (double)par4, var28);


		var8.setBrightness(renderblocks.customMinZ > 0.0D ? var26 : par1Block.getMixedBrightnessForBlock(renderblocks.blockAccess, par2 - 1, par3, par4));
		var8.setColorOpaque_F(var19, var22, var25);
		var28 = par1Block.getBlockTextureFromSideAndMetadata(4, meta);

		renderblocks.renderNorthFace(par1Block, (double)par2, (double)par3, (double)par4, var28);


		var8.setBrightness(renderblocks.customMaxZ < 1.0D ? var26 : par1Block.getMixedBrightnessForBlock(renderblocks.blockAccess, par2 + 1, par3, par4));
		var8.setColorOpaque_F(var19, var22, var25);
		var28 = par1Block.getBlockTextureFromSideAndMetadata(5, meta);

		renderblocks.renderSouthFace(par1Block, (double)par2, (double)par3, (double)par4, var28);

		return true;
	}

	public static boolean isOpaque(TileEntityDetailed te, int x, int y, int z)
	{
		return te.data.get((x * 8 + z)*8 + y);
	}

}
