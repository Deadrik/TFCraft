package TFC.Render;

import java.io.IOException;

import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.util.ResourceLocation;
import TFC.Core.ColorizerGrassTFC;
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
