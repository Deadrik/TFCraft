package TFC.Render;

import net.minecraft.client.model.ModelBase;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelIngotPile extends ModelBase
{
	public ModelRendererTFC[][] ingots = new ModelRendererTFC[21][64];

	public ModelIngotPile()
	{
		for (int a = 0; a < 21; a++){
			for (int n = 0; n < 64; n++){
				this.ingots[a][n] = new ModelRendererTFC(this,0,0);
				int m = (n+8)/8;
				float x = (n %4)*8f;
				float y = (m -1)*4f;
				float z = 0;
				
				int ingotHeight = 4;
				int ingotWidth = 7;
				int ingotDepth = 15;
				
				if (n%8 >=4){
					z = 16f;
				}
				if (m %2 == 1)
				{
			        
					this.ingots[a][n].setRotationPoint(0,0,0);

					this.ingots[a][n].cubeList.add(
							new ModelIngot(this.ingots[a][n],this.ingots[a][n].textureOffsetX, this.ingots[a][n].textureOffsetY, 0.5F + x, y, z + 0.5f, ingotWidth, ingotHeight, ingotDepth, 0f));
					
				}
				else
				{
					this.ingots[a][n].setRotationPoint(0,0,0);

					this.ingots[a][n].cubeList.add(
							new ModelIngot(this.ingots[a][n],this.ingots[a][n].textureOffsetX, this.ingots[a][n].textureOffsetY, z + 0.5f, y, 0.5f + x, ingotDepth, ingotHeight, ingotWidth, 0f));

				}
			}
		}
	}
	
	/**
	 * This method renders out all parts of the chest model.
	 */
	public void renderIngots(int i,int a)
	{
		for (int n = 0; n < i; n++){
			this.ingots[a][n].render(0.0625F / 2F);
		}
	}
}
