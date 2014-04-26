package TFC;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.particle.EntityRainFX;
import net.minecraft.client.particle.EntitySmokeFX;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.world.biome.BiomeGenBase;
import TFC.API.Util.Helper;
import TFC.Core.WeatherManager;

public class ClientOverrides 
{
	public static void loadRenderers()
	{
		RenderGlobal renderG = Minecraft.getMinecraft().renderGlobal;
		int k = 0, j = 0;
		int renderChunksWide = Helper.getInteger(renderG, "field_72766_m", "renderChunksWide", TFCASMLoadingPlugin.runtimeDeobf);
		int renderChunksTall = Helper.getInteger(renderG, "field_72763_n", "renderChunksTall", TFCASMLoadingPlugin.runtimeDeobf);
		int renderChunksDeep = Helper.getInteger(renderG, "field_72764_o", "renderChunksDeep", TFCASMLoadingPlugin.runtimeDeobf);
		int glRenderListBase = Helper.getInteger(renderG, "field_72778_p", "glRenderListBase", TFCASMLoadingPlugin.runtimeDeobf);
		WorldRenderer[] worldRenderers = (WorldRenderer[])(Helper.getObject(renderG, "field_72765_l", "worldRenderers", TFCASMLoadingPlugin.runtimeDeobf));
		WorldRenderer[] sortedWorldRenderers = (WorldRenderer[])(Helper.getObject(renderG, "field_72768_k", "sortedWorldRenderers", TFCASMLoadingPlugin.runtimeDeobf));
		List worldRenderersToUpdate = (ArrayList)(Helper.getObject(renderG, "field_72767_j", "worldRenderersToUpdate", TFCASMLoadingPlugin.runtimeDeobf));

		worldRenderersToUpdate.clear();
		renderG.tileEntities.clear();

		for (int l = 0; l < renderChunksWide; ++l)
		{
			for (int i1 = renderChunksTall-1; i1 >= 0; --i1)
			{
				for (int j1 = 0; j1 < renderChunksDeep; ++j1)
				{
					worldRenderers[(j1 * renderChunksTall + i1) * renderChunksWide + l] = new WorldRenderer(renderG.theWorld, renderG.tileEntities, l * 16, i1 * 16, j1 * 16, glRenderListBase + j);

					if (Helper.getBoolean(renderG, "field_72774_t", "occlusionEnabled", TFCASMLoadingPlugin.runtimeDeobf))
					{
						worldRenderers[(j1 * renderChunksTall + i1) * renderChunksWide + l].glOcclusionQuery = ((IntBuffer)(Helper.getObject(renderG, "field_72775_s", "glOcclusionQueryBase", TFCASMLoadingPlugin.runtimeDeobf))).get(k);
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
				int k1 = i + random.nextInt(b0) - random.nextInt(b0);
				int l1 = k + random.nextInt(b0) - random.nextInt(b0);
				int i2 = worldclient.getPrecipitationHeight(k1, l1);
				int j2 = worldclient.getBlockId(k1, i2 - 1, l1);
				BiomeGenBase biomegenbase = worldclient.getBiomeGenForCoords(k1, l1);
				if(!WeatherManager.canSnow(k1, i2, l1))
					if (i2 <= j + b0 && i2 >= j - b0 && biomegenbase.canSpawnLightningBolt() && biomegenbase.getFloatTemperature() >= 0.2F)
					{
						float f1 = random.nextFloat();
						float f2 = random.nextFloat();

						if (j2 > 0)
						{
							if (Block.blocksList[j2].blockMaterial == Material.lava)
							{
								Minecraft.getMinecraft().effectRenderer.addEffect(new EntitySmokeFX(worldclient, k1 + f1, i2 + 0.1F - Block.blocksList[j2].getBlockBoundsMinY(), l1 + f2, 0.0D, 0.0D, 0.0D));
							}
							else
							{
								++l;

								if (random.nextInt(l) == 0)
								{
									d0 = k1 + f1;
									d1 = i2 + 0.1F - Block.blocksList[j2].getBlockBoundsMinY();
									d2 = l1 + f2;
								}

								Minecraft.getMinecraft().effectRenderer.addEffect(new EntityRainFX(worldclient, k1 + f1, i2 + 0.1F - Block.blocksList[j2].getBlockBoundsMinY(), l1 + f2));
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
}
