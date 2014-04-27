package com.bioxx.tfc.Render;

import java.io.IOException;

import com.bioxx.tfc.Core.ColorizerGrassTFC;

import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GrassColorReloadListener implements IResourceManagerReloadListener
{
	private static final ResourceLocation field_130078_a = new ResourceLocation("textures/colormap/grass.png");

	@Override
	public void onResourceManagerReload(IResourceManager par1ResourceManager)
	{
		try
		{
			ColorizerGrassTFC.setGrassBiomeColorizer(TextureUtil.readImageData(par1ResourceManager, field_130078_a));
		}
		catch (IOException ioexception)
		{
			;
		}
	}
}
