package TFC.Render;

import java.io.IOException;

import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.ResourceManager;
import net.minecraft.client.resources.ResourceManagerReloadListener;
import net.minecraft.util.ResourceLocation;
import TFC.Core.ColorizerGrassTFC;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GrassColorReloadListener implements ResourceManagerReloadListener
{
	private static final ResourceLocation field_130078_a = new ResourceLocation("textures/colormap/grass.png");

	@Override
	public void onResourceManagerReload(ResourceManager par1ResourceManager)
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
