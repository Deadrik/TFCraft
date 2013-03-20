package TFC.Render.Blocks;

import org.lwjgl.opengl.GL11;

import TFC.TFCBlocks;
import TFC.Blocks.BlockFoodPrep;
import TFC.Blocks.BlockSlab;
import TFC.Blocks.BlockToolRack;
import TFC.Core.TFC_Textures;
import TFC.TileEntities.TileEntityFoodPrep;
import TFC.TileEntities.TileEntityPartial;
import TFC.TileEntities.TileEntityTerraAnvil;
import TFC.TileEntities.TileEntityToolRack;
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

public class RenderFoodPrep 
{
	static double[][] spritesOffsets = {{}, {}, {0.5, 0.3, 0.8, 0.06}, {}};

	public static boolean render(Block block, int i, int j, int k, RenderBlocks renderblocks)
	{
		IBlockAccess blockAccess = renderblocks.blockAccess;
		TileEntityFoodPrep te = (TileEntityFoodPrep)blockAccess.getBlockTileEntity(i, j, k);
		int dir = blockAccess.getBlockMetadata(i, j, k);
		if(te != null)
		{
			double height = 0.005;
			String tex = TFC_Textures.FoodSheet;
			
			ModLoader.getMinecraftInstance().renderEngine.func_98187_b("/gui/items.png");
			Tessellator tessellator = Tessellator.instance;
			tessellator.setBrightness(block.getMixedBrightnessForBlock(blockAccess, i, j, k));
			tessellator.setColorRGBA_F(1, 1, 1, 1);
			if(te.storage[0] != null)
			{
				renderblocks.overrideBlockTexture = te.storage[0].getIconIndex();

				double minX = renderblocks.overrideBlockTexture.func_94209_e();
				double maxX = renderblocks.overrideBlockTexture.func_94212_f();
				double minZ = renderblocks.overrideBlockTexture.func_94206_g();
				double maxZ = renderblocks.overrideBlockTexture.func_94210_h();

				tessellator.addTranslation(0f, 0.0f, 0.0f);

				tessellator.addVertexWithUV(i + 0.05, 	j + height, 	k + 0.05, 	minX, minZ);
				tessellator.addVertexWithUV(i + 0.05, 	j + height, 	k + 0.45, 	maxX, minZ);
				tessellator.addVertexWithUV(i + 0.45, 	j + height, 	k + 0.45, 	maxX, maxZ);
				tessellator.addVertexWithUV(i + 0.45, 	j + height, 	k + 0.05, 	minX, maxZ);

				tessellator.addTranslation(0f, 0.0f, 0f);
			}
			if(te.storage[1] != null)
			{
				renderblocks.overrideBlockTexture = te.storage[1].getIconIndex();

				double minX = renderblocks.overrideBlockTexture.func_94209_e();
				double maxX = renderblocks.overrideBlockTexture.func_94212_f();
				double minZ = renderblocks.overrideBlockTexture.func_94206_g();
				double maxZ = renderblocks.overrideBlockTexture.func_94210_h();

				tessellator.addTranslation(0f, 0.0f, 0.5f);

				tessellator.addVertexWithUV(i + 0.05, 	j + height, 	k + 0.05, 	minX, minZ);
				tessellator.addVertexWithUV(i + 0.05, 	j + height, 	k + 0.45, 	maxX, minZ);
				tessellator.addVertexWithUV(i + 0.45, 	j + height, 	k + 0.45, 	maxX, maxZ);
				tessellator.addVertexWithUV(i + 0.45, 	j + height, 	k + 0.05, 	minX, maxZ);

				tessellator.addTranslation(0f, 0.0f, -0.5f);;
			}
			if(te.storage[2] != null)
			{
				renderblocks.overrideBlockTexture = te.storage[2].getIconIndex();

				double minX = renderblocks.overrideBlockTexture.func_94209_e();
				double maxX = renderblocks.overrideBlockTexture.func_94212_f();
				double minZ = renderblocks.overrideBlockTexture.func_94206_g();
				double maxZ = renderblocks.overrideBlockTexture.func_94210_h();

				tessellator.addTranslation(0.5f, 0.0f, 0.0f);

				tessellator.addVertexWithUV(i + 0.05, 	j + height, 	k + 0.05, 	minX, minZ);
				tessellator.addVertexWithUV(i + 0.05, 	j + height, 	k + 0.45, 	maxX, minZ);
				tessellator.addVertexWithUV(i + 0.45, 	j + height, 	k + 0.45, 	maxX, maxZ);
				tessellator.addVertexWithUV(i + 0.45, 	j + height, 	k + 0.05, 	minX, maxZ);

				tessellator.addTranslation(-0.5f, 0.0f, 0f);
			}
			if(te.storage[3] != null)
			{
				renderblocks.overrideBlockTexture = te.storage[3].getIconIndex();

				double minX = renderblocks.overrideBlockTexture.func_94209_e();
				double maxX = renderblocks.overrideBlockTexture.func_94212_f();
				double minZ = renderblocks.overrideBlockTexture.func_94206_g();
				double maxZ = renderblocks.overrideBlockTexture.func_94210_h();

				tessellator.addTranslation(0.5f, 0.0f, 0.5f);

				tessellator.addVertexWithUV(i + 0.05, 	j + height, 	k + 0.05, 	minX, minZ);
				tessellator.addVertexWithUV(i + 0.05, 	j + height, 	k + 0.45, 	maxX, minZ);
				tessellator.addVertexWithUV(i + 0.45, 	j + height, 	k + 0.45, 	maxX, maxZ);
				tessellator.addVertexWithUV(i + 0.45, 	j + height, 	k + 0.05, 	minX, maxZ);

				tessellator.addTranslation(-0.5f, 0.0f, -0.5f);
			}
		}

		renderblocks.clearOverrideBlockTexture();
		return true;
	}
}
