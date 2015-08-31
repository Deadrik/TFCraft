package com.bioxx.tfc.Render;

import java.io.IOException;

import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.util.ResourceLocation;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import com.bioxx.tfc.Core.ColorizerFoliageTFC;

@SideOnly(Side.CLIENT)
public class FoliageColorReloadListener implements IResourceManagerReloadListener
{
	private static final ResourceLocation TEXTURE = new ResourceLocation("textures/colormap/foliage.png");

	@Override
	public void onResourceManagerReload(IResourceManager par1ResourceManager)
	{
		try
		{
			ColorizerFoliageTFC.setFoliageBiomeColorizer(TextureUtil.readImageData(par1ResourceManager, TEXTURE));
		}
		catch (IOException ioexception)
		{
		}
	}
}
