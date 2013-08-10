package TFC.Render;

import java.io.IOException;

import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.ResourceManager;
import net.minecraft.client.resources.ResourceManagerReloadListener;
import net.minecraft.util.ResourceLocation;
import TFC.Core.ColorizerFoliageTFC;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class FoliageColorReloadListener implements ResourceManagerReloadListener
{
	private static final ResourceLocation field_130079_a = new ResourceLocation("textures/colormap/foliage.png");

	public void func_110549_a(ResourceManager par1ResourceManager)
	{
		try
		{
			ColorizerFoliageTFC.setFoliageBiomeColorizer(TextureUtil.func_110986_a(par1ResourceManager, field_130079_a));
		}
		catch (IOException ioexception)
		{
			;
		}
	}
}
