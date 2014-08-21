package com.bioxx.tfc;

import java.nio.IntBuffer;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.particle.EntityRainFX;
import net.minecraft.client.particle.EntitySmokeFX;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.Core.WeatherManager;
import com.bioxx.tfc.api.Interfaces.IFood;
import com.bioxx.tfc.api.Util.Helper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientOverrides 
{
	public static void loadRenderers()
	{
		RenderGlobal renderG = Minecraft.getMinecraft().renderGlobal;
		int k = 0, j = 0;
		int renderChunksWide = Helper.getInteger(renderG, "w","field_72766_m", "renderChunksWide", TFCASMLoadingPlugin.runtimeDeobf);
		int renderChunksTall = Helper.getInteger(renderG, "x","field_72763_n", "renderChunksTall", TFCASMLoadingPlugin.runtimeDeobf);
		int renderChunksDeep = Helper.getInteger(renderG, "y","field_72764_o", "renderChunksDeep", TFCASMLoadingPlugin.runtimeDeobf);
		int glRenderListBase = Helper.getInteger(renderG, "z","field_72778_p", "glRenderListBase", TFCASMLoadingPlugin.runtimeDeobf);
		WorldRenderer[] worldRenderers = (WorldRenderer[])(Helper.getObject(renderG,"v", "field_72765_l", "worldRenderers", TFCASMLoadingPlugin.runtimeDeobf));
		WorldRenderer[] sortedWorldRenderers = (WorldRenderer[])(Helper.getObject(renderG, "u","field_72768_k", "sortedWorldRenderers", TFCASMLoadingPlugin.runtimeDeobf));
		Object obj = Helper.getObject(renderG, "t", "field_72767_j", "worldRenderersToUpdate", TFCASMLoadingPlugin.runtimeDeobf);
		if(!(obj instanceof List))
			return;
		List worldRenderersToUpdate = (List)(Helper.getObject(renderG, "t", "field_72767_j", "worldRenderersToUpdate", TFCASMLoadingPlugin.runtimeDeobf));
		World world = (World)(Helper.getObject(renderG, "r", "field_72769_h", "theWorld", TFCASMLoadingPlugin.runtimeDeobf));

		worldRenderersToUpdate.clear();
		renderG.tileEntities.clear();

		for (int l = 0; l < renderChunksWide; ++l)
		{
			for (int i1 = renderChunksTall-1; i1 >= 0; --i1)
			{
				for (int j1 = 0; j1 < renderChunksDeep; ++j1)
				{
					worldRenderers[(j1 * renderChunksTall + i1) * renderChunksWide + l] = new WorldRenderer(world, renderG.tileEntities, l * 16, i1 * 16, j1 * 16, glRenderListBase + j);

					if (Helper.getBoolean(renderG, "D","field_72774_t", "occlusionEnabled", TFCASMLoadingPlugin.runtimeDeobf))
					{
						worldRenderers[(j1 * renderChunksTall + i1) * renderChunksWide + l].glOcclusionQuery = ((IntBuffer)(Helper.getObject(renderG, "C", "field_72775_s", "glOcclusionQueryBase", TFCASMLoadingPlugin.runtimeDeobf))).get(k);
					}

					worldRenderers[(j1 * renderChunksTall + i1) * renderChunksWide + l].isWaitingOnOcclusionQuery = false;
					worldRenderers[(j1 * renderChunksTall + i1) * renderChunksWide + l].isVisible = true;
					worldRenderers[(j1 * renderChunksTall + i1) * renderChunksWide + l].isInFrustum = true;
					worldRenderers[(j1 * renderChunksTall + i1) * renderChunksWide + l].chunkIndex = k++;
					worldRenderers[(j1 * renderChunksTall + i1) * renderChunksWide + l].markDirty();
					sortedWorldRenderers[(j1 * renderChunksTall + i1) * renderChunksWide + l] = worldRenderers[(j1 * renderChunksTall + i1) * renderChunksWide + l];
					worldRenderersToUpdate.add(worldRenderers[(j1 * renderChunksTall + i1) * renderChunksWide + l]);
					j += 3;
				}
			}
		}
	}

	private static int rainSoundCounter = 0;

	public static void doRainClient(Random random, int rendererUpdateCount)
	{
		float f = Minecraft.getMinecraft().theWorld.getRainStrength(1.0F);

		if (!Minecraft.getMinecraft().gameSettings.fancyGraphics)
		{
			f /= 2.0F;
		}

		if (f != 0.0F)
		{
			random.setSeed(rendererUpdateCount * 312987231L);
			EntityLivingBase entitylivingbase = Minecraft.getMinecraft().renderViewEntity;
			WorldClient worldclient = Minecraft.getMinecraft().theWorld;
			int i = MathHelper.floor_double(entitylivingbase.posX);
			int j = MathHelper.floor_double(entitylivingbase.posY);
			int k = MathHelper.floor_double(entitylivingbase.posZ);
			byte b0 = 10;
			double d0 = 0.0D;
			double d1 = 0.0D;
			double d2 = 0.0D;
			int l = 0;
			int i1 = (int)(100.0F * f * f);

			if (Minecraft.getMinecraft().gameSettings.particleSetting == 1)
			{
				i1 >>= 1;
			}
			else if (Minecraft.getMinecraft().gameSettings.particleSetting == 2)
			{
				i1 = 0;
			}

			for (int j1 = 0; j1 < i1; ++j1)
			{
				int x = i + random.nextInt(b0) - random.nextInt(b0);
				int z = k + random.nextInt(b0) - random.nextInt(b0);
				int y = worldclient.getPrecipitationHeight(x, z);
				Block b = worldclient.getBlock(x, y - 1, z);
				if(!WeatherManager.canSnow(Minecraft.getMinecraft().theWorld, x, y, z))
				{
					if (y <= j + b0 && y >= j - b0)
					{
						float f1 = random.nextFloat();
						float f2 = random.nextFloat();

						if (b != Blocks.air)
						{
							if (b.getMaterial() == Material.lava)
							{
								Minecraft.getMinecraft().effectRenderer.addEffect(new EntitySmokeFX(worldclient, x + f1, y + 0.1F - b.getBlockBoundsMinY(), z + f2, 0.0D, 0.0D, 0.0D));
							}
							else
							{
								++l;

								if (random.nextInt(l) == 0)
								{
									d0 = x + f1;
									d1 = y + 0.1F - b.getBlockBoundsMinY();
									d2 = z + f2;
								}

								Minecraft.getMinecraft().effectRenderer.addEffect(new EntityRainFX(worldclient, x + f1, y + 0.1F - b.getBlockBoundsMinY(), z + f2));
							}
						}
					}
				}
			}

			if (l > 0 && random.nextInt(3) < rainSoundCounter++)
			{
				rainSoundCounter = 0;

				if (d1 > entitylivingbase.posY + 1.0D && worldclient.getPrecipitationHeight(MathHelper.floor_double(entitylivingbase.posX), MathHelper.floor_double(entitylivingbase.posZ)) > MathHelper.floor_double(entitylivingbase.posY))
				{
					Minecraft.getMinecraft().theWorld.playSound(d0, d1, d2, "ambient.weather.rain", 0.1F, 0.5F, false);
				}
				else
				{
					Minecraft.getMinecraft().theWorld.playSound(d0, d1, d2, "ambient.weather.rain", 0.2F, 1.0F, false);
				}
			}
		}
	}  

	public static void renderIcon(int x, int y, ItemStack is, int xSize, int ySize)
	{
		//Disabled until I can come up with a better way for showing all 6 ingredients
		/*if(is.getItem() instanceof ItemMeal)
		{
			Item i;
			int offset = 0;
			if(is.hasTagCompound() && is.getTagCompound().hasKey("FG"))
			{
				int[] fg = is.getTagCompound().getIntArray("FG");
				for(int j = 1; j < fg.length; j++)
				{
					i = FoodRegistry.getInstance().getFood(is.getTagCompound().getIntArray("FG")[j]);
					if(i != null)
					{
						renderIcon(x+offset, y, i.getIcon(new ItemStack(i), 0), 4, 4); 
						offset+=4;
					}
				}
				if(fg[0] != -1)
				{

				}
			}
		}*/

		if(is.getItem() instanceof IFood)
		{
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glDisable(GL11.GL_DEPTH_TEST);
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glDisable(GL11.GL_ALPHA_TEST);
			GL11.glDisable(GL11.GL_BLEND);
			float decayPerc = Math.max(((IFood)is.getItem()).getFoodDecay(is) / ((IFood)is.getItem()).getFoodWeight(is), 0);

			renderQuad(x +1, y + 13, 13, 1, 0);
			float decayTop = decayPerc * 13.0F;

			if(((IFood)is.getItem()).renderDecay())
			{
				if(decayPerc < 0.10)
				{
					decayTop = (decayTop*10);
					renderQuad(x+1, y+13, 13-decayTop, 1, 0x00ff00);
				}
				else
					renderQuad(x+1, y+13, 13-decayTop, 1, 0xff0000);
			}
			if(((IFood)is.getItem()).renderWeight())
			{
				renderQuad(x + 1, y + 14, 13, 1, 0);
				float weightPerc = ((IFood)is.getItem()).getFoodWeight(is) / ((IFood)is.getItem()).getFoodMaxWeight(is);
				float weightTop = weightPerc * 13.0F;

				renderQuad(x+1, y+14, weightTop, 1, 0xffffff);
			}
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glEnable(GL11.GL_DEPTH_TEST);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		}
	}

	public static void renderIcon(int x, int y, IIcon icon, int sizeX, int sizeY)
	{
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV((double)(x + 0), (double)(y + sizeY), (double)200, (double)icon.getMinU(), (double)icon.getMaxV());
		tessellator.addVertexWithUV((double)(x + sizeX), (double)(y + sizeY), (double)200, (double)icon.getMaxU(), (double)icon.getMaxV());
		tessellator.addVertexWithUV((double)(x + sizeX), (double)(y + 0), (double)200, (double)icon.getMaxU(), (double)icon.getMinV());
		tessellator.addVertexWithUV((double)(x + 0), (double)(y + 0), (double)200, (double)icon.getMinU(), (double)icon.getMinV());
		tessellator.draw();
	}

	private static void renderQuad(double x, double y, double sizeX, double sizeY, int color)
	{
		Tessellator tess = Tessellator.instance;
		tess.startDrawingQuads();
		tess.setColorOpaque_I(color);
		tess.addVertex((double)(x + 0), (double)(y + 0), 0.0D);
		tess.addVertex((double)(x + 0), (double)(y + sizeY), 0.0D);
		tess.addVertex((double)(x + sizeX), (double)(y + sizeY), 0.0D);
		tess.addVertex((double)(x + sizeX), (double)(y + 0), 0.0D);
		tess.draw();
	}
}
