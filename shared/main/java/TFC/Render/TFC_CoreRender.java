package TFC.Render;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.src.ModLoader;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

import org.lwjgl.opengl.GL11;

import TFC.TFCBlocks;
import TFC.API.TFCOptions;
import TFC.Blocks.BlockFruitLeaves;
import TFC.Blocks.Devices.BlockSluice;
import TFC.Core.TFC_Time;
import TFC.Food.FloraIndex;
import TFC.Food.FloraManager;
import TFC.TileEntities.TileEntityFruitTreeWood;
import TFC.TileEntities.TileEntityPartial;
import TFC.WorldGen.DataLayer;
import TFC.WorldGen.TFCWorldChunkManager;

public class TFC_CoreRender 
{
	public static boolean renderBlockSlab(Block par1Block, int par2, int par3, int par4, RenderBlocks renderblocks)
	{
		TileEntityPartial te = (TileEntityPartial) renderblocks.blockAccess.getBlockTileEntity(par2, par3, par4);
		int md = renderblocks.blockAccess.getBlockMetadata(par2, par3, par4);

		boolean breaking = false;
		/*if(renderblocks.overrideBlockTexture >= 240)
        {
        	breaking = true;
        }*/

		if(te.TypeID <= 0)
		{
			return false;
		}

		int type = te.TypeID;
		int meta = te.MetaID;
		Icon tex = Block.blocksList[type].getIcon(0, meta);

		//if(!breaking)
		//	ForgeHooksClient.bindTexture(Block.blocksList[type].getTextureFile(), ModLoader.getMinecraftInstance().renderEngine.getTexture(Block.blocksList[type].getTextureFile()));

		long extraX = (te.extraData) & 0xf;
		long extraY = (te.extraData >> 4) & 0xf;
		long extraZ = (te.extraData >> 8) & 0xf;
		long extraX2 = (te.extraData >> 12) & 0xf;
		long extraY2 = (te.extraData >> 16) & 0xf;
		long extraZ2 = (te.extraData >> 20) & 0xf;

		float div = 1f / 8;

		renderblocks.setRenderBounds(0.0F+ (div * extraX), 0.0F+ (div * extraY), 0.0F+ (div * extraZ), 1.0F-(div * extraX2), 1-(div * extraY2), 1.0F-(div * extraZ2));

		//This is the old ore code that I experimented with
		Icon over = renderblocks.overrideBlockTexture;
		if(!breaking && (type == TFCBlocks.Ore.blockID || type == TFCBlocks.Ore2.blockID || type == TFCBlocks.Ore3.blockID))
		{
			BiomeGenBase biome = renderblocks.blockAccess.getBiomeGenForCoords(par2, par4);
			renderblocks.overrideBlockTexture = getRockTexture(ModLoader.getMinecraftInstance().theWorld, par2, par3, par4);
			renderblocks.renderStandardBlock(par1Block, par2, par3, par4);
			renderblocks.overrideBlockTexture = over;
		}

		if(!breaking)
		{
			renderblocks.overrideBlockTexture = tex;
		}

		renderblocks.renderStandardBlock(par1Block, par2, par3, par4);

		renderblocks.overrideBlockTexture = over;



		return true;
	}

	public static boolean renderBlockStairs(Block par1Block, int par2, int par3, int par4, RenderBlocks renderblocks)
	{
		boolean breaking = false;
		/*if(renderblocks.overrideBlockTexture >= 240)
        {
        	breaking = true;
        }*/

		int var5 = renderblocks.blockAccess.getBlockMetadata(par2, par3, par4);
		renderblocks.renderAllFaces = true;
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
		if(te.TypeID <= 0)
		{
			return false;
		}

		int type = te.TypeID;
		int meta = te.MetaID;
		Icon tex = Block.blocksList[type].getIcon(0, meta);
		if(!breaking)
		{
			//ForgeHooksClient.bindTexture(Block.blocksList[type].getTextureFile(), ModLoader.getMinecraftInstance().renderEngine.getTexture(Block.blocksList[type].getTextureFile()));
			renderblocks.overrideBlockTexture = tex;
		}
		renderblocks.setRenderBounds(0.0F, var7, 0.0F, 1.0F, var8, 1.0F);
		renderblocks.renderStandardBlock(par1Block, par2, par3, par4);

		if (var6 == 0)
		{
			renderblocks.setRenderBounds(0.5F, var9, 0.0F, 1.0F, var10, 1.0F);
			renderblocks.renderStandardBlock(par1Block, par2, par3, par4);
		}
		else if (var6 == 1)
		{
			renderblocks.setRenderBounds(0.0F, var9, 0.0F, 0.5F, var10, 1.0F);
			renderblocks.renderStandardBlock(par1Block, par2, par3, par4);
		}
		else if (var6 == 2)
		{
			renderblocks.setRenderBounds(0.0F, var9, 0.5F, 1.0F, var10, 1.0F);
			renderblocks.renderStandardBlock(par1Block, par2, par3, par4);
		}
		else if (var6 == 3)
		{
			renderblocks.setRenderBounds(0.0F, var9, 0.0F, 1.0F, var10, 0.5F);
			renderblocks.renderStandardBlock(par1Block, par2, par3, par4);
		}
		renderblocks.clearOverrideBlockTexture();
		renderblocks.setRenderBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		return true;
	}

	public static boolean RenderSulfur(Block block, int i, int j, int k, RenderBlocks renderblocks)
	{
		IBlockAccess blockAccess = renderblocks.blockAccess;
		if(blockAccess.isBlockNormalCube(i, j, k+1) && blockAccess.getBlockId(i, j, k+1) != block.blockID)
		{
			renderblocks.setRenderBounds(0.0F, 0.0F, 0.99F, 1.0F, 1.0F, 1.0F);
			renderblocks.renderStandardBlock(block, i, j, k);
		}
		if(blockAccess.isBlockNormalCube(i, j, k-1) && blockAccess.getBlockId(i, j, k-1) != block.blockID)
		{
			renderblocks.setRenderBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.01F);
			renderblocks.renderStandardBlock(block, i, j, k);
		}
		if(blockAccess.isBlockNormalCube(i+1, j, k) && blockAccess.getBlockId(i+1, j, k) != block.blockID)
		{
			renderblocks.setRenderBounds(0.99F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
			renderblocks.renderStandardBlock(block, i, j, k);
		}
		if(blockAccess.isBlockNormalCube(i-1, j, k) && blockAccess.getBlockId(i-1, j, k) != block.blockID)
		{
			renderblocks.setRenderBounds(0.0F, 0.0F, 0.0F, 0.01F, 1.0F, 1.0F);
			renderblocks.renderStandardBlock(block, i, j, k);
		}
		if(blockAccess.isBlockNormalCube(i, j+1, k) && blockAccess.getBlockId(i, j+1, k) != block.blockID)
		{
			renderblocks.setRenderBounds(0.0F, 0.99F, 0.0F, 1.0F, 1.0F, 1.0F);
			renderblocks.renderStandardBlock(block, i, j, k);
		}
		if(blockAccess.isBlockNormalCube(i, j-1, k) && blockAccess.getBlockId(i, j-1, k) != block.blockID)
		{
			renderblocks.setRenderBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.01F, 1.0F);
			renderblocks.renderStandardBlock(block, i, j, k);
		}

		return true;
	}

	public static boolean RenderSnow(Block block, int i, int j, int k, RenderBlocks renderblocks)
	{
		int meta = renderblocks.blockAccess.getBlockMetadata(i, j, k);

		float drift = 0.04F + (meta * 0.06F);

		renderblocks.setRenderBounds(0.0F, 0.0F, 0F, 1.0F, drift, 1.0F);
		renderblocks.renderStandardBlock(block, i, j, k);
		return true;
	}

	public static boolean RenderWoodTrunk(Block block, int i, int j, int k, RenderBlocks renderblocks)
	{
		IBlockAccess blockAccess = renderblocks.blockAccess;

		if(true/*blockAccess.getBlockMaterial(i, j+1, k) == Material.leaves || blockAccess.getBlockMaterial(i, j-1, k) == Material.leaves || 
                blockAccess.getBlockId(i, j+1, k) == mod_TFC_Core.fruitTreeWood.blockID || blockAccess.getBlockId(i, j-1, k) == mod_TFC_Core.fruitTreeWood.blockID*/)
		{
			if(blockAccess.getBlockTileEntity(i, j, k) != null && (blockAccess.getBlockId(i, j-1, k) == TFCBlocks.fruitTreeWood.blockID || blockAccess.isBlockOpaqueCube(i, j-1, k)))
			{
				renderblocks.setRenderBounds(0.3F, 0.0F, 0.3F, 0.7F, 1.0F, 0.7F);
				renderblocks.renderStandardBlock(block, i, j, k);
			}
			if(blockAccess.getBlockMaterial(i-1, j, k) == Material.leaves || blockAccess.getBlockId(i-1, j, k) == TFCBlocks.fruitTreeWood.blockID)
			{
				renderblocks.setRenderBounds(0.0F, 0.4F, 0.4F, 0.5F, 0.6F, 0.6F);
				renderblocks.renderStandardBlock(block, i, j, k);
			}
			if(blockAccess.getBlockMaterial(i+1, j, k) == Material.leaves || blockAccess.getBlockId(i+1, j, k) == TFCBlocks.fruitTreeWood.blockID)
			{
				renderblocks.setRenderBounds(0.5F, 0.4F, 0.4F, 1.0F, 0.6F, 0.6F);
				renderblocks.renderStandardBlock(block, i, j, k);
			}
			if(blockAccess.getBlockMaterial(i, j, k-1) == Material.leaves || blockAccess.getBlockId(i, j, k-1) == TFCBlocks.fruitTreeWood.blockID)
			{
				renderblocks.setRenderBounds(0.4F, 0.4F, 0.0F, 0.6F, 0.6F, 0.5F);
				renderblocks.renderStandardBlock(block, i, j, k);
			}
			if(blockAccess.getBlockMaterial(i, j, k+1) == Material.leaves || blockAccess.getBlockId(i, j, k+1) == TFCBlocks.fruitTreeWood.blockID)
			{
				renderblocks.setRenderBounds(0.4F, 0.4F, 0.5F, 0.6F, 0.6F, 1.0F);
				renderblocks.renderStandardBlock(block, i, j, k);
			}
		}

		if(!((TileEntityFruitTreeWood)blockAccess.getBlockTileEntity(i, j, k)).isTrunk && blockAccess.getBlockId(i, j-1, k) != TFCBlocks.fruitTreeWood.blockID && !blockAccess.isBlockOpaqueCube(i, j-1, k))
		{

			renderblocks.setRenderBounds(0.0F, 0.4F, 0.4F, 0.5F, 0.6F, 0.6F);
			renderblocks.renderStandardBlock(block, i, j, k);

			renderblocks.setRenderBounds(0.5F, 0.4F, 0.4F, 1.0F, 0.6F, 0.6F);
			renderblocks.renderStandardBlock(block, i, j, k);

			renderblocks.setRenderBounds(0.4F, 0.4F, 0.0F, 0.6F, 0.6F, 0.5F);
			renderblocks.renderStandardBlock(block, i, j, k);

			renderblocks.setRenderBounds(0.4F, 0.4F, 0.5F, 0.6F, 0.6F, 1.0F);
			renderblocks.renderStandardBlock(block, i, j, k);

		}

		//renderblocks.func_83020_a(0.0F, 0.0F, 0.0F, 1F, 1F, 1F);
		return true;
	}

	public static boolean RenderLooseRock(Block block, int i, int j, int k, RenderBlocks renderblocks)	
	{
		boolean breaking = false;
		/*if(renderblocks.overrideBlockTexture >= 240)
        {
        	breaking = true;
        }*/

		int meta = renderblocks.blockAccess.getBlockMetadata(i, j, k);
		World w = ModLoader.getMinecraftInstance().theWorld;
		TFCWorldChunkManager wcm = ((TFCWorldChunkManager)w.getWorldChunkManager());
		renderblocks.renderAllFaces = true;

		DataLayer rockLayer1 = ((TFCWorldChunkManager)w.getWorldChunkManager()).getRockLayerAt(i, k, 0);
		if(rockLayer1 != null && Block.blocksList[rockLayer1.data1] != null && !breaking)
		{
			renderblocks.overrideBlockTexture = Block.blocksList[rockLayer1.data1].getIcon(0, rockLayer1.data2);
		}

		int seed = i*k+j;
		Random R = new Random(seed);

		float xOffset = (R.nextInt(5) - 2) * 0.05f;
		float zOffset = (R.nextInt(5) - 2) * 0.05f;

		float xOffset2 = (R.nextInt(5) - 2) * 0.05f;
		float yOffset2 = (R.nextInt(5) - 2) * 0.05f;
		float zOffset2 = (R.nextInt(5) - 2) * 0.05f;

		renderblocks.setRenderBounds(0.35F + xOffset, 0.00F, 0.35F + zOffset, 0.65F + xOffset2, 0.15F + yOffset2, 0.65F + zOffset2);
		renderblocks.renderStandardBlock(block, i, j, k);
		//renderblocks.func_83020_a(0.20F, 0.00F, 0.2F, 0.8F, 0.25F, 0.8F);
		renderblocks.clearOverrideBlockTexture();

		return true;
	}



	public static boolean RenderOre(Block block, int xCoord, int yCoord, int zCoord,float par5, float par6, float par7, RenderBlocks renderblocks, IBlockAccess iblockaccess)
	{
		/*boolean breaking = false;
        if(renderblocks.overrideBlockTexture >= 240)
        {
        	breaking = true;
        }

        if(!breaking)
        {
        	//render the background rock
            renderblocks.overrideBlockTexture = getRockTexture(ModLoader.getMinecraftInstance().theWorld, xCoord, yCoord, zCoord);
            renderblocks.renderStandardBlock(block, xCoord, yCoord, zCoord);
            renderblocks.clearOverrideBlockTexture();

            //render the ore overlay
            renderblocks.renderStandardBlock(block, xCoord, yCoord, zCoord);
        }

        //renderblocks.renderStandardBlock(block, xCoord, yCoord, zCoord);
		 */
		return true;
	}

	public static Icon getRockTexture(World worldObj, int xCoord, int yCoord, int zCoord) 
	{
		Icon var27;
		DataLayer rockLayer1 = ((TFCWorldChunkManager)worldObj.getWorldChunkManager()).getRockLayerAt(xCoord, zCoord, 0);
		DataLayer rockLayer2 = ((TFCWorldChunkManager)worldObj.getWorldChunkManager()).getRockLayerAt(xCoord, zCoord, 1);
		DataLayer rockLayer3 = ((TFCWorldChunkManager)worldObj.getWorldChunkManager()).getRockLayerAt(xCoord, zCoord, 2);

		if(yCoord <= TFCOptions.RockLayer3Height)
		{
			var27 = Block.blocksList[rockLayer3.data1].getIcon(5, rockLayer3.data2);
		} else if(yCoord <= TFCOptions.RockLayer2Height)
		{
			var27 = Block.blocksList[rockLayer2.data1].getIcon(5, rockLayer2.data2);
		} else
		{
			var27 = Block.blocksList[rockLayer1.data1].getIcon(5, rockLayer1.data2);
		}
		return var27;
	}

	public static boolean RenderMolten(Block block, int i, int j, int k, RenderBlocks renderblocks)
	{
		renderblocks.setRenderBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		renderblocks.renderStandardBlock(block, i, j, k);

		//renderblocks.func_83020_a(0.0F, 0.0F, 0.0F, 0.001F, 0.001F, 0.001F);
		return true;
	}

	public static boolean renderFirepit(Block block, int i, int j, int k, RenderBlocks renderblocks)
	{
		IBlockAccess blockAccess = renderblocks.blockAccess;

		renderblocks.setRenderBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.02F, 1.0F);
		renderblocks.renderStandardBlock(block, i, j, k);

		renderblocks.setRenderBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.02F, 1.0F);
		return true;
	}

	public static boolean renderForge(Block block, int i, int j, int k, RenderBlocks renderblocks)
	{
		IBlockAccess blockAccess = renderblocks.blockAccess;

		renderblocks.setRenderBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.9F, 1.0F);
		renderblocks.renderStandardBlock(block, i, j, k);


		renderblocks.setRenderBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.9F, 1.0F);
		return true;
	}

	public static boolean RenderSluice(Block block, int i, int j, int k, RenderBlocks renderblocks)
	{

		double blockMinX = block.getBlockBoundsMinX();
		double blockMaxX = block.getBlockBoundsMaxX();
		double blockMinY = block.getBlockBoundsMinY();
		double blockMaxY = block.getBlockBoundsMaxY();
		double blockMinZ = block.getBlockBoundsMinZ();
		double blockMaxZ = block.getBlockBoundsMaxZ();
		IBlockAccess blockAccess = renderblocks.blockAccess;
		Tessellator tessellator = Tessellator.instance;
		int l = blockAccess.getBlockMetadata(i, j, k);
		int i1 = ((BlockSluice)block).getDirectionFromMetadata(l);
		float f = 0.5F;
		float f1 = 1.0F;
		float f2 = 0.8F;
		float f3 = 0.6F;
		int j1 = block.getMixedBrightnessForBlock(blockAccess, i, j, k);
		tessellator.setBrightness(j1);
		tessellator.setColorOpaque_F(f, f, f);

		Icon texture = block.getBlockTexture(blockAccess, i, j, k, 0);
		double texMinX = texture.getMinU();
		double texMaxX = texture.getMaxU();
		double texMinY = texture.getMinV();
		double texMaxY = texture.getMaxV();

		double minX = i + blockMinX;
		double maxX = i + blockMaxX;
		double minY = j + blockMinY;
		double minZ = k + blockMinZ;
		double maxZ = k + blockMaxZ;
		double maxY = j + blockMaxY;

		int var10 = blockAccess.getBiomeGenForCoords(i, k).waterColorMultiplier;
		int waterR = (var10 & 16711680) >> 16;
		int waterG = (var10 & 65280) >> 8;
		int waterB = var10 & 255;

		//render ramp
		if(!((BlockSluice)block).isBlockFootOfBed(l))
		{
			if(i1 == 0)
			{
				//ribs
				renderblocks.setRenderBounds(0.0F, 0.0F, 0.75F, 1F, 0.75F, 0.8F);
				renderblocks.renderStandardBlock(block, i, j, k);
				renderblocks.setRenderBounds(0.0F, 0.0F, 0.45F, 1F, 0.9F, 0.5F);
				renderblocks.renderStandardBlock(block, i, j, k);

				tessellator.setColorOpaque_F(f * f1, f * f2, f * f3);
				tessellator.addVertexWithUV(minX, maxY, minZ, texMaxX, texMinY);//d,d3
				tessellator.addVertexWithUV(minX, minY+0.5F, maxZ, texMaxX, texMaxY);//d,d2
				tessellator.addVertexWithUV(maxX, minY+0.5f, maxZ, texMinX, texMaxY);//d1,d2
				tessellator.addVertexWithUV(maxX, maxY, minZ, texMinX, texMinY);//d1,d3
				if(((BlockSluice)block).getIsRecievingWater(l))
				{
					//get water texture
					texture = Block.blocksList[Block.waterStill.blockID].getIcon(0, 4);
					l = block.colorMultiplier(blockAccess, i, j, k);
					//reassign the uv coords
					texMinX = texture.getMinU();
					texMaxX = texture.getMaxU();
					texMinY = texture.getMinV();
					texMaxY = texture.getMaxV();

					//draw water plane
					//tessellator.setColorOpaque_F(0.8F, 0.8F, 0.8F);
					tessellator.setColorOpaque(waterR, waterG, waterB);
					tessellator.addVertexWithUV(minX, maxY, minZ, texMaxX, texMinY);//d,d3
					tessellator.addVertexWithUV(minX, minY+0.6F, maxZ, texMaxX, texMaxY);//d,d2
					tessellator.addVertexWithUV(maxX, minY+0.6f, maxZ, texMinX, texMaxY);//d1,d2
					tessellator.addVertexWithUV(maxX, maxY, minZ, texMinX, texMinY);//d1,d3
				}
			}
			else if(i1 == 1)
			{
				//ribs
				renderblocks.setRenderBounds(0.2F, 0.0F, 0.0F, 0.25F, 0.75F, 1.0F);
				renderblocks.renderStandardBlock(block, i, j, k);
				renderblocks.setRenderBounds(0.5F, 0.0F, 0.0F, 0.55F, 0.9F, 1.0F);
				renderblocks.renderStandardBlock(block, i, j, k);

				tessellator.setColorOpaque_F(f * f1, f * f2, f * f3);
				tessellator.addVertexWithUV(minX, minY+0.5F, maxZ, texMinX, texMaxY);
				tessellator.addVertexWithUV(maxX, maxY, maxZ, texMinX, texMinY);
				tessellator.addVertexWithUV(maxX, maxY, minZ, texMaxX, texMinY);
				tessellator.addVertexWithUV(minX, minY+0.5F, minZ, texMaxX, texMaxY);

				if(((BlockSluice)block).getIsRecievingWater(l))
				{
					//get water texture
					texture = Block.blocksList[Block.waterStill.blockID].getIcon(0, 4);
					l = block.colorMultiplier(blockAccess, i, j, k);
					//reassign the uv coords
					texMinX = texture.getMinU();
					texMaxX = texture.getMaxU();
					texMinY = texture.getMinV();
					texMaxY = texture.getMaxV();

					//draw water plane
					tessellator.setColorOpaque(waterR, waterG, waterB);
					tessellator.addVertexWithUV(minX, minY+0.6F, maxZ, texMinX, texMaxY);
					tessellator.addVertexWithUV(maxX, maxY, maxZ, texMinX, texMinY);
					tessellator.addVertexWithUV(maxX, maxY, minZ, texMaxX, texMinY);
					tessellator.addVertexWithUV(minX, minY+0.6F, minZ, texMaxX, texMaxY);
				}
			}
			else if(i1 == 2)
			{
				//ribs
				renderblocks.setRenderBounds(0.0F, 0.0F, 0.2F, 1F, 0.75F, 0.25F);
				renderblocks.renderStandardBlock(block, i, j, k);
				renderblocks.setRenderBounds(0.0F, 0.0F, 0.5F, 1F, 0.9F, 0.55F);
				renderblocks.renderStandardBlock(block, i, j, k);

				tessellator.setColorOpaque_F(f * f1, f * f2, f * f3);
				tessellator.addVertexWithUV(minX, minY+0.5F, minZ, texMinX, texMaxY);
				tessellator.addVertexWithUV(minX, maxY, maxZ, texMinX, texMinY);
				tessellator.addVertexWithUV(maxX, maxY, maxZ, texMaxX, texMinY);
				tessellator.addVertexWithUV(maxX, minY+0.5F, minZ, texMaxX, texMaxY);

				if(((BlockSluice)block).getIsRecievingWater(l))
				{
					//get water texture
					texture = Block.blocksList[Block.waterStill.blockID].getIcon(0, 4);
					l = block.colorMultiplier(blockAccess, i, j, k);
					//reassign the uv coords
					texMinX = texture.getMinU();
					texMaxX = texture.getMaxU();
					texMinY = texture.getMinV();
					texMaxY = texture.getMaxV();

					//draw water plane
					tessellator.setColorOpaque(waterR, waterG, waterB);
					tessellator.addVertexWithUV(minX, minY+0.6F, minZ, texMinX, texMaxY);
					tessellator.addVertexWithUV(minX, maxY, maxZ, texMinX, texMinY);
					tessellator.addVertexWithUV(maxX, maxY, maxZ, texMaxX, texMinY);
					tessellator.addVertexWithUV(maxX, minY+0.6F, minZ, texMaxX, texMaxY);
				}


			}
			else if(i1 == 3)
			{        
				//ribs
				renderblocks.setRenderBounds(0.75F, 0.0F, 0.0F, 0.8F, 0.75F, 1.0F);
				renderblocks.renderStandardBlock(block, i, j, k);
				renderblocks.setRenderBounds(0.45F, 0.0F, 0.0F, 0.5F, 0.9F, 1.0F);
				renderblocks.renderStandardBlock(block, i, j, k);

				tessellator.setColorOpaque_F(f * f1, f * f2, f * f3);
				tessellator.addVertexWithUV(maxX, minY+0.5f, minZ, texMinX, texMaxY);
				tessellator.addVertexWithUV(minX, maxY, minZ, texMinX, texMinY);
				tessellator.addVertexWithUV(minX, maxY, maxZ, texMaxX, texMinY);
				tessellator.addVertexWithUV(maxX, minY+0.5F, maxZ, texMaxX, texMaxY);

				if(((BlockSluice)block).getIsRecievingWater(l))
				{
					//get water texture
					texture = Block.blocksList[Block.waterStill.blockID].getBlockTextureFromSide(0);
					l = block.colorMultiplier(blockAccess, i, j, k);
					//reassign the uv coords
					texMinX = texture.getMinU();
					texMaxX = texture.getMaxU();
					texMinY = texture.getMinV();
					texMaxY = texture.getMaxV();

					tessellator.setColorOpaque(waterR, waterG, waterB);
					tessellator.addVertexWithUV(maxX, minY+0.6f, minZ, texMinX, texMaxY);
					tessellator.addVertexWithUV(minX, maxY, minZ, texMinX, texMinY);
					tessellator.addVertexWithUV(minX, maxY, maxZ, texMaxX, texMinY);
					tessellator.addVertexWithUV(maxX, minY+0.6F, maxZ, texMaxX, texMaxY);
				}
			}
		}
		else
		{
			if(i1 == 0)
			{
				//ribs
				renderblocks.setRenderBounds(0.0F, 0.0F, 0.70F, 1F, 0.3F, 0.75F);
				renderblocks.renderStandardBlock(block, i, j, k);
				renderblocks.setRenderBounds(0.0F, 0.0F, 0.4F, 1F, 0.45F, 0.45F);
				renderblocks.renderStandardBlock(block, i, j, k);
				renderblocks.setRenderBounds(0.0F, 0.0F, 0.1F, 1F, 0.6F, 0.15F);
				renderblocks.renderStandardBlock(block, i, j, k);

				tessellator.setColorOpaque_F(f * f1, f * f2, f * f3);
				tessellator.addVertexWithUV(minX, minY+0.5F, minZ, texMaxX, texMinY);//d,d3
				tessellator.addVertexWithUV(minX, minY, maxZ, texMaxX, texMaxY);//d,d2
				tessellator.addVertexWithUV(maxX, minY, maxZ, texMinX, texMaxY);//d1,d2
				tessellator.addVertexWithUV(maxX, minY+0.5F, minZ, texMinX, texMinY);//d1,d3

				if(((BlockSluice)block).getIsRecievingWater(l))
				{
					//get water texture
					texture = Block.blocksList[Block.waterStill.blockID].getIcon(0, 4);
					l = block.colorMultiplier(blockAccess, i, j, k);
					//reassign the uv coords
					texMinX = texture.getMinU();
					texMaxX = texture.getMaxU();
					texMinY = texture.getMinV();
					texMaxY = texture.getMaxV();

					//draw water plane
					tessellator.setColorOpaque(waterR, waterG, waterB);
					tessellator.addVertexWithUV(minX, minY+0.6F, minZ, texMaxX, texMinY);//d,d3
					tessellator.addVertexWithUV(minX, minY, maxZ, texMaxX, texMaxY);//d,d2
					tessellator.addVertexWithUV(maxX, minY, maxZ, texMinX, texMaxY);//d1,d2
					tessellator.addVertexWithUV(maxX, minY+0.6F, minZ, texMinX, texMinY);//d1,d3
				}
			}
			if(i1 == 1)
			{
				//ribs
				renderblocks.setRenderBounds(0.9F, 0.0F, 0.0F, 0.95F, 0.6F, 1.0F);
				renderblocks.renderStandardBlock(block, i, j, k);
				renderblocks.setRenderBounds(0.6F, 0.0F, 0.0F, 0.65F, 0.45F, 1.0F);
				renderblocks.renderStandardBlock(block, i, j, k);
				renderblocks.setRenderBounds(0.3F, 0.0F, 0.0F, 0.35F, 0.3F, 1.0F);
				renderblocks.renderStandardBlock(block, i, j, k);

				tessellator.setColorOpaque_F(f * f1, f * f2, f * f3);
				tessellator.addVertexWithUV(minX, minY, maxZ, texMinX, texMaxY);
				tessellator.addVertexWithUV(maxX, minY+0.5F, maxZ, texMinX, texMinY);
				tessellator.addVertexWithUV(maxX, minY+0.5F, minZ, texMaxX, texMinY);
				tessellator.addVertexWithUV(minX, minY, minZ, texMaxX, texMaxY);

				if(((BlockSluice)block).getIsRecievingWater(l))
				{
					//get water texture
					texture = Block.blocksList[Block.waterStill.blockID].getIcon(0, 4);
					l = block.colorMultiplier(blockAccess, i, j, k);
					//reassign the uv coords
					texMinX = texture.getMinU();
					texMaxX = texture.getMaxU();
					texMinY = texture.getMinV();
					texMaxY = texture.getMaxV();

					//draw water plane
					tessellator.setColorOpaque(waterR, waterG, waterB);
					tessellator.addVertexWithUV(minX, minY, maxZ, texMinX, texMaxY);
					tessellator.addVertexWithUV(maxX, minY+0.6F, maxZ, texMinX, texMinY);
					tessellator.addVertexWithUV(maxX, minY+0.6F, minZ, texMaxX, texMinY);
					tessellator.addVertexWithUV(minX, minY, minZ, texMaxX, texMaxY);
				}
			}
			if(i1 == 2)
			{               
				//ribs
				renderblocks.setRenderBounds(0.0F, 0.0F, 0.3F, 1F, 0.3F, 0.35F);
				renderblocks.renderStandardBlock(block, i, j, k);
				renderblocks.setRenderBounds(0.0F, 0.0F, 0.6F, 1F, 0.45F, 0.65F);
				renderblocks.renderStandardBlock(block, i, j, k);
				renderblocks.setRenderBounds(0.0F, 0.0F, 0.9F, 1F, 0.6F, 0.95F);
				renderblocks.renderStandardBlock(block, i, j, k);

				tessellator.setColorOpaque_F(f * f1, f * f2, f * f3);
				tessellator.addVertexWithUV(minX, minY, minZ, texMinX, texMaxY);
				tessellator.addVertexWithUV(minX, minY+0.5f, maxZ, texMinX, texMinY);
				tessellator.addVertexWithUV(maxX, minY+0.5f, maxZ, texMaxX, texMinY);
				tessellator.addVertexWithUV(maxX, minY, minZ, texMaxX, texMaxY);

				if(((BlockSluice)block).getIsRecievingWater(l))
				{
					//get water texture
					texture = Block.blocksList[Block.waterStill.blockID].getBlockTextureFromSide(0);
					l = block.colorMultiplier(blockAccess, i, j, k);

					//reassign the uv coords
					texMinX = texture.getMinU();
					texMaxX = texture.getMaxU();
					texMinY = texture.getMinV();
					texMaxY = texture.getMaxV();

					tessellator.setColorOpaque(waterR, waterG, waterB);
					tessellator.addVertexWithUV(minX, minY, minZ, texMinX, texMaxY);
					tessellator.addVertexWithUV(minX, minY+0.6f, maxZ, texMinX, texMinY);
					tessellator.addVertexWithUV(maxX, minY+0.6f, maxZ, texMaxX, texMinY);
					tessellator.addVertexWithUV(maxX, minY, minZ, texMaxX, texMaxY);
				}
			}
			if(i1 == 3)
			{               
				//ribs
				renderblocks.setRenderBounds(0.7F, 0.0F, 0.0F, 0.75F, 0.3F, 1.0F);
				renderblocks.renderStandardBlock(block, i, j, k);
				renderblocks.setRenderBounds(0.4F, 0.0F, 0.0F, 0.45F, 0.45F, 1.0F);
				renderblocks.renderStandardBlock(block, i, j, k);
				renderblocks.setRenderBounds(0.1F, 0.0F, 0.0F, 0.15F, 0.6F, 1.0F);
				renderblocks.renderStandardBlock(block, i, j, k);

				tessellator.setColorOpaque_F(f * f1, f * f2, f * f3);
				tessellator.addVertexWithUV(maxX, minY, minZ, texMinX, texMaxY);
				tessellator.addVertexWithUV(minX, minY+0.5f, minZ, texMinX, texMinY);
				tessellator.addVertexWithUV(minX, minY+0.5f, maxZ, texMaxX, texMinY);
				tessellator.addVertexWithUV(maxX, minY, maxZ, texMaxX, texMaxY);

				if(((BlockSluice)block).getIsRecievingWater(l))
				{
					//get water texture
					texture = Block.blocksList[Block.waterStill.blockID].getBlockTextureFromSide(0);
					l = block.colorMultiplier(blockAccess, i, j, k);
					//reassign the uv coords
					texMinX = texture.getMinU();
					texMaxX = texture.getMaxU();
					texMinY = texture.getMinV();
					texMaxY = texture.getMaxV();
					tessellator.setColorOpaque(waterR, waterG, waterB);
					tessellator.addVertexWithUV(maxX, minY, minZ, texMinX, texMaxY);
					tessellator.addVertexWithUV(minX, minY+0.6f, minZ, texMinX, texMinY);
					tessellator.addVertexWithUV(minX, minY+0.6f, maxZ, texMaxX, texMinY);
					tessellator.addVertexWithUV(maxX, minY, maxZ, texMaxX, texMaxY);
				}
			}
		}


		//set the block collision box
		renderblocks.setRenderBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);

		return true;
	}

	public static boolean RenderBlockWithCustomColorMultiplier(Block block, RenderBlocks renderBlocks, int xCoord, int yCoord, int zCoord, int colorMultiplier)
	{
		int l = colorMultiplier;
		float f = (l >> 16 & 255) / 255.0F;
		float f1 = (l >> 8 & 255) / 255.0F;
		float f2 = (l & 255) / 255.0F;

		if (EntityRenderer.anaglyphEnable)
		{
			float f3 = (f * 30.0F + f1 * 59.0F + f2 * 11.0F) / 100.0F;
			float f4 = (f * 30.0F + f1 * 70.0F) / 100.0F;
			float f5 = (f * 30.0F + f2 * 70.0F) / 100.0F;
			f = f3;
			f1 = f4;
			f2 = f5;
		}

		return Minecraft.isAmbientOcclusionEnabled() && Block.lightValue[block.blockID] == 0 ? 
				(renderBlocks.partialRenderBounds ? 
						renderBlocks.renderStandardBlockWithAmbientOcclusion(block, xCoord, yCoord, zCoord, f, f1, f2) : 
							renderBlocks.renderStandardBlockWithAmbientOcclusion(block, xCoord, yCoord, zCoord, f, f1, f2)) : 
								renderBlocks.renderStandardBlockWithColorMultiplier(block, xCoord, yCoord, zCoord, f, f1, f2);
	}

	public static boolean RenderFruitLeaves(Block block, int xCoord, int yCoord, int zCoord, RenderBlocks renderblocks)
	{
		int meta = renderblocks.blockAccess.getBlockMetadata(xCoord, yCoord, zCoord);
		if(meta >= 8)
		{
			meta-=8;
		}
		FloraManager manager = FloraManager.getInstance();
		FloraIndex index = manager.findMatchingIndex(BlockFruitLeaves.getType(block.blockID, meta));

		renderblocks.renderStandardBlock(block, xCoord, yCoord, zCoord);
		if(index.inBloom(TFC_Time.currentMonth) || index.inHarvest(TFC_Time.currentMonth))
		{
			renderblocks.overrideBlockTexture = getFruitTreeOverlay(renderblocks.blockAccess,xCoord,yCoord,zCoord);
			if(renderblocks.overrideBlockTexture != null)
			{
				RenderBlockWithCustomColorMultiplier(block, renderblocks, xCoord, yCoord, zCoord, 16777215);
			}
			renderblocks.clearOverrideBlockTexture();
		}

		/*
		renderblocks.renderBottomFace(block, (double)xCoord, (double)yCoord, (double)zCoord, block.getBlockTexture(renderblocks.blockAccess, xCoord, yCoord, zCoord, 0));

		if(index.inBloom(TFC_Time.currentMonth))
		{
			texIndex = getFruitTreeOverlay(renderblocks.blockAccess,xCoord,yCoord,zCoord);
			if(texIndex != null)
			{
				renderblocks.colorRedTopLeft = renderblocks.colorRedBottomLeft = renderblocks.colorRedBottomRight = renderblocks.colorRedTopRight = ( 1.0F) * colorMult;
				renderblocks.colorGreenTopLeft = renderblocks.colorGreenBottomLeft = renderblocks.colorGreenBottomRight = renderblocks.colorGreenTopRight = ( 1.0F) * colorMult;
				renderblocks.colorBlueTopLeft = renderblocks.colorBlueBottomLeft = renderblocks.colorBlueBottomRight = renderblocks.colorBlueTopRight = ( 1.0F) * colorMult;
				renderblocks.renderBottomFace(block, (double)xCoord, (double)yCoord, (double)zCoord, texIndex);
			}
		}
		 */
		return true;
	}

	public static Icon getFruitTreeOverlay(IBlockAccess world, int x, int y, int z)
	{
		Icon out = null;
		int meta = world.getBlockMetadata(x, y, z);
		int id = world.getBlockId(x, y, z);
		int offset = id == TFCBlocks.fruitTreeLeaves.blockID ? 0 : 8;

		FloraManager manager = FloraManager.getInstance();
		FloraIndex index = manager.findMatchingIndex(BlockFruitLeaves.getType(id, meta & 7));
		if(index != null)
		{
			if(index.inBloom(TFC_Time.currentMonth))//blooming
			{
				out = BlockFruitLeaves.iconsFlowers[(meta & 7)+offset];
			}
			else if(meta >= 8)//fruit
			{
				out = BlockFruitLeaves.iconsFruit[(meta & 7)+offset];
			}
		}
		return out;
	}

	/**
	 * Renders a block based on the BlockFluids class at the given coordinates
	 */
	public static boolean RenderFiniteWater(Block block, int par2, int par3, int par4, RenderBlocks renderblocks)
	{
		/*double blockMinX = block.getBlockBoundsMinX();
		double blockMaxX = block.getBlockBoundsMaxX();
		double blockMinY = block.getBlockBoundsMinY();
		double blockMaxY = block.getBlockBoundsMaxY();
		double blockMinZ = block.getBlockBoundsMinZ();
		double blockMaxZ = block.getBlockBoundsMaxZ();
        Tessellator var5 = Tessellator.instance;
        int var6 = block.colorMultiplier(renderblocks.blockAccess, par2, par3, par4);
        float var7 = (float)(var6 >> 16 & 255) / 255.0F;
        float var8 = (float)(var6 >> 8 & 255) / 255.0F;
        float var9 = (float)(var6 & 255) / 255.0F;
        boolean var10 = block.shouldSideBeRendered(renderblocks.blockAccess, par2, par3 + 1, par4, 1);
        boolean var11 = block.shouldSideBeRendered(renderblocks.blockAccess, par2, par3 - 1, par4, 0);
        boolean[] var12 = new boolean[] {block.shouldSideBeRendered(renderblocks.blockAccess, par2, par3, par4 - 1, 2), block.shouldSideBeRendered(renderblocks.blockAccess, par2, par3, par4 + 1, 3), block.shouldSideBeRendered(renderblocks.blockAccess, par2 - 1, par3, par4, 4), block.shouldSideBeRendered(renderblocks.blockAccess, par2 + 1, par3, par4, 5)};

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
            Material var22 = block.blockMaterial;
            int var23 = renderblocks.blockAccess.getBlockMetadata(par2, par3, par4);
            double var24 = (double)renderblocks.getFluidHeight(par2, par3, par4, var22);
            double var26 = (double)renderblocks.getFluidHeight(par2, par3, par4 + 1, var22);
            double var28 = (double)renderblocks.getFluidHeight(par2 + 1, par3, par4 + 1, var22);
            double var30 = (double)renderblocks.getFluidHeight(par2 + 1, par3, par4, var22);
            double var32 = 0.0010000000474974513D;
            Icon var34;
            int var35;
            float var36;
            int var37;
            double var42;
            double var40;
            double var44;

            if (renderblocks.renderAllFaces || var10)
            {
                var13 = true;
                var34 = block.getBlockTextureFromSideAndMetadata(1, var23);
                var36 = (float)BlockFiniteWater.func_293_a(renderblocks.blockAccess, par2, par3, par4, var22);

                if (var36 > -999.0F)
                {
                    var34 = block.getBlockTextureFromSideAndMetadata(2, var23);
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
                var5.setBrightness(block.getMixedBrightnessForBlock(renderblocks.blockAccess, par2, par3, par4));
                float var46 = 1.0F;
                var5.setColorOpaque_F(var15 * var46 * var7, var15 * var46 * var8, var15 * var46 * var9);
                var5.addVertexWithUV((double)(par2 + 0), (double)par3 + var24, (double)(par4 + 0), var38 - var44 - var42, var40 - var44 + var42);
                var5.addVertexWithUV((double)(par2 + 0), (double)par3 + var26, (double)(par4 + 1), var38 - var44 + var42, var40 + var44 + var42);
                var5.addVertexWithUV((double)(par2 + 1), (double)par3 + var28, (double)(par4 + 1), var38 + var44 + var42, var40 + var44 - var42);
                var5.addVertexWithUV((double)(par2 + 1), (double)par3 + var30, (double)(par4 + 0), var38 + var44 - var42, var40 - var44 - var42);
            }

            if (renderblocks.renderAllFaces || var11)
            {
                var5.setBrightness(block.getMixedBrightnessForBlock(renderblocks.blockAccess, par2, par3 - 1, par4));
                var36 = 1.0F;
                var5.setColorOpaque_F(var14 * var36, var14 * var36, var14 * var36);
                renderblocks.renderBottomFace(block, (double)par2, (double)par3 + var32, (double)par4, block.getBlockTextureFromSide(0));
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

                var37 = block.getBlockTextureFromSideAndMetadata(var34 + 2, var23);
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
                    var5.setBrightness(block.getMixedBrightnessForBlock(renderblocks.blockAccess, var64, par3, var35));
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

            blockMinY = var18;
            blockMaxY = var20;
            return var13;
        }*/

		return true;
	}

	private static void drawCrossedSquares(Block block, int x, int y, int z, RenderBlocks renderblocks)
	{
		Tessellator var9 = Tessellator.instance;

		var9.setColorOpaque_F(1.0f, 1.0f, 1.0f);
		GL11.glColor3f(1, 1, 1);

		Icon index = block.getBlockTexture(renderblocks.blockAccess, x, y, z, 0);


		double minX = index.getMinU();
		double maxX = index.getMaxU();
		double minY = index.getMinV();
		double maxY = index.getMaxV();

		double xMin = x + 0.5D - 0.45D;
		double xMax = x + 0.5D + 0.45D;
		double zMin = z + 0.5D - 0.45D;
		double zMax = z + 0.5D + 0.45D;

		var9.addVertexWithUV(xMin, y + 0, zMin, minX, minY);
		var9.addVertexWithUV(xMin, y + 0.0D, zMin, minX, maxY);
		var9.addVertexWithUV(xMax, y + 0.0D, zMax, maxX, maxY);
		var9.addVertexWithUV(xMax, y + 0, zMax, maxX, minY);

		var9.addVertexWithUV(xMax, y + 0, zMax, minX, minY);
		var9.addVertexWithUV(xMax, y + 0.0D, zMax, minX, maxY);
		var9.addVertexWithUV(xMin, y + 0.0D, zMin, maxX, maxY);
		var9.addVertexWithUV(xMin, y + 0, zMin, maxX, minY);

		var9.addVertexWithUV(xMin, y + 0, zMax, minX, minY);
		var9.addVertexWithUV(xMin, y + 0.0D, zMax, minX, maxY);
		var9.addVertexWithUV(xMax, y + 0.0D, zMin, maxX, maxY);
		var9.addVertexWithUV(xMax, y + 0, zMin, maxX, minY);

		var9.addVertexWithUV(xMax, y + 0, zMin, minX, minY);
		var9.addVertexWithUV(xMax, y + 0.0D, zMin, minX, maxY);
		var9.addVertexWithUV(xMin, y + 0.0D, zMax, maxX, maxY);
		var9.addVertexWithUV(xMin, y + 0, zMax, maxX, minY);
	}
}
