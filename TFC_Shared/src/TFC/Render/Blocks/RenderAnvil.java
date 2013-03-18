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
import net.minecraftforge.client.ForgeHooksClient;
import TFC.Blocks.BlockAnvil;
import TFC.Core.AnvilReq;
import TFC.Core.TFC_Climate;
import TFC.Core.TFC_Textures;
import TFC.TileEntities.TileEntityTerraAnvil;
import TFC.WorldGen.DataLayer;

public class RenderAnvil {

	public static boolean renderAnvil(Block block, int i, int j, int k, RenderBlocks renderblocks)
	{
		IBlockAccess blockAccess = renderblocks.blockAccess;

		int meta = blockAccess.getBlockMetadata(i, j, k);
		int direction = ((BlockAnvil)block).getDirectionFromMetadata(meta);
		renderblocks.renderAllFaces = true;

		boolean breaking = false;
		if(renderblocks.overrideBlockTexture >= 240)
		{
			breaking = true;
		}

		TileEntityTerraAnvil te = (TileEntityTerraAnvil)blockAccess.getBlockTileEntity(i, j, k);

		if(te.AnvilTier != AnvilReq.STONE.Tier)
		{
			if(direction == 0)//x
			{
				//top
				renderblocks.setRenderBounds(0.3F, 0.4F, 0.1F, 0.7F, 0.6F, 0.9F);
				renderblocks.renderStandardBlock(block, i, j, k);

				//core
				renderblocks.setRenderBounds(0.35F, 0.0F, 0.15F, 0.65F, 0.4F, 0.85F);
				renderblocks.renderStandardBlock(block, i, j, k);

				//feet
				renderblocks.setRenderBounds(0.25F, 0.0F, 0.1F, 0.75F, 0.2F, 0.90F);
				renderblocks.renderStandardBlock(block, i, j, k);
				renderblocks.setRenderBounds(0.20F, 0.0F, 0.0F, 0.80F, 0.1F, 1.0F);
				renderblocks.renderStandardBlock(block, i, j, k);

				//block.setBlockBounds(0.2F, 0.0F, 0.0F, 0.80F, 0.6F, 1.0F);
			}
			else if(direction == 1)//z
			{
				//top
				renderblocks.setRenderBounds(0.1F, 0.4F, 0.3F, 0.9F, 0.6F, 0.7F);
				renderblocks.renderStandardBlock(block, i, j, k);

				//core
				renderblocks.setRenderBounds(0.15F, 0.0F, 0.35F, 0.85F, 0.4F, 0.65F);
				renderblocks.renderStandardBlock(block, i, j, k);

				//feet
				renderblocks.setRenderBounds(0.1F, 0.0F, 0.25F, 0.90F, 0.2F, 0.75F);
				renderblocks.renderStandardBlock(block, i, j, k);
				renderblocks.setRenderBounds(0.0F, 0.0F, 0.20F, 1.00F, 0.1F, 0.80F);
				renderblocks.renderStandardBlock(block, i, j, k);

				//block.setBlockBounds(0.0F, 0.0F, 0.20F, 1.0F, 0.6F, 0.8F);
			}	
		}
		else
		{
			if(Block.blocksList[te.stonePair[0]] != null)
			{
				if(!breaking)
				{
					ForgeHooksClient.bindTexture(TFC_Textures.RockSheet, ModLoader.getMinecraftInstance().renderEngine.getTexture(TFC_Textures.RockSheet));
					renderblocks.overrideBlockTexture = Block.blocksList[te.stonePair[0]].getBlockTextureFromSideAndMetadata(0, te.stonePair[1]);
				}
				renderblocks.setRenderBounds(0.0F, 0.0F, 0.00F, 1.0F, 0.9F, 1.0F);
				renderblocks.renderStandardBlock(block, i, j, k);
				if(te.anvilItemStacks[0] != null && !breaking)
				{
					ForgeHooksClient.bindTexture("/bioxx/terratools.png", ModLoader.getMinecraftInstance().renderEngine.getTexture("/bioxx/terratools.png"));
					renderblocks.overrideBlockTexture = Item.itemsList[te.anvilItemStacks[0].itemID].getIconIndex(te.anvilItemStacks[0]);
					//					block.setBlockBounds(0.0F, 0.9F, 0.0F, 1F, 0.901F, 1F);
					//					renderblocks.renderStandardBlock(block, i, j, k);
					Tessellator tessellator = Tessellator.instance;
					tessellator.setBrightness(block.getMixedBrightnessForBlock(blockAccess, i, j, k));
					tessellator.setColorRGBA_F(1, 1, 1, 1);

					int x = (renderblocks.overrideBlockTexture & 0xf) << 4;
					int z = renderblocks.overrideBlockTexture & 0xf0;

					double minX = ((double)x + 0	) / 256D;
					double maxX = ((double)x + 16	) / 256D;
					double minZ = ((double)z + 0	) / 256D;
					double maxZ = ((double)z + 16	) / 256D;

					tessellator.addTranslation(0.5f, 0f, 0.5f);

					tessellator.addVertexWithUV(i, j + 0.901, k + 0.4, minX, maxZ);
					tessellator.addVertexWithUV(i + 0.4, j + 0.901, k + 0.4, maxX, maxZ);
					tessellator.addVertexWithUV(i + 0.4, j + 0.901, k, maxX, minZ);
					tessellator.addVertexWithUV(i, j + 0.901, k, minX, minZ);

					tessellator.addTranslation(-0.5f, 0f, -0.5f);


				}
				//block.setBlockBounds(0.0F, 0.0F, 0.00F, 1.0F, 0.9F, 1.0F);
				renderblocks.clearOverrideBlockTexture();
			}
		}
		return true;
	}
}

