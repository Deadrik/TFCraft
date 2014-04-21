package TFC.Render.Models;

import net.minecraft.client.model.ModelBase;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelLogPile extends ModelBase
{
	public ModelRendererTFC[] renderer = new ModelRendererTFC[16];
	int logHeight = 7;
	int logWidth = 7;
	int logDepth = 31;

	public ModelLogPile()
	{
		for (int n = 0; n < 16; n++){
			this.renderer[n] = new ModelRendererTFC(this,0,0);
			int m = (n+4)/4;	// layer
			float x = (n %4)*8f;
			float y = (m -1)*8f;	// layer height
			float z = 0;

			if (m%2 == 1)
			{
				renderer[n].cubeList.add(
						new ModelLog(renderer[n],renderer[n].textureOffsetX, renderer[n].textureOffsetY, 0.5f + x, y, z + 0.5f, logWidth, logHeight, logDepth, 0f));
			} else {
				renderer[n].cubeList.add(
						new ModelLog(renderer[n],renderer[n].textureOffsetX, renderer[n].textureOffsetY, z + 0.5f, y, 0.5f + x, logDepth, logHeight, logWidth, 0f));
			}
		}
	}

	public void renderLogs(int i)
	{
		for (int n = 0; n < i; n++)
		{
			renderer[n].render(0.0625F / 2F);
		}
	}
}
