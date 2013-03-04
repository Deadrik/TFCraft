package TFC.Render;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.AxisAlignedBB;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelIngotPile extends ModelBase
{
	/** The chest lid in the chest's model. */
	public ModelRenderer chestLid = (new ModelRenderer(this, 0, 0)).setTextureSize(64, 64);

	public ModelRenderer[][] ingots = new ModelRenderer[21][32];

	//double d1;
	//double d2;
	//double d3;
	/** The model of the bottom of the chest. */
	public ModelRenderer chestBelow;

	/** The chest's knob in the chest model. */
	public ModelRenderer chestKnob;

	public ModelIngotPile()
	{
		for (int a = 0; a < 21; a++){
			for (int n = 0; n < 32; n++){
				this.ingots[a][n] = new ModelRenderer(this,64*a,932+((a/16)*64)).setTextureSize(1024,1024);
				int m = (n+8)/8;
				float x = (n %4)*8f;
				float y = (m -1)*8f;
				float z = 0;
				if (n%8 >=4){
					z = 16f;
				}
				if (m %2 == 1){

					this.ingots[a][n].setRotationPoint(0,0,0);
					//drawBox(AxisAlignedBB.getAABBPool().addOrModifyAABBInPool(d1,d2,d3,d1+1+x,d2+1+y,d3+1+z));
					//System.out.println("COORDS: "+d1+", "+d2+", "+d3);
					this.ingots[a][n].addBox(0.5F + x,y,z + 0.5f, 7, 4, 15,0f);
					this.ingots[a][n].addBox(1 + x, 4 + y, 1 + z, 6, 4, 14,0f);
					this.ingots[a][n].addBox(1.5f + x, 6 + y, 2 + z, 5, 2, 12,0f);
				}
				else{

					this.ingots[a][n].setRotationPoint(0,0,0);
					this.ingots[a][n].addBox(z+0.5f,y,0.5f+x,15,4,7,0f);
					this.ingots[a][n].addBox(1 + z, 4 + y,  1 + x, 14 , 4,6,0f);
					this.ingots[a][n].addBox(2f + z, 6 + y,  1.5f + x, 12 , 2,5,0f);

				}
			}
		}
		/*
        this.chestLid.addBox(0.0F, -5.0F, -14.0F, 14, 5, 14, 0.0F);
        this.chestLid.rotationPointX = 1.0F;
        this.chestLid.rotationPointY = 7.0F;
        this.chestLid.rotationPointZ = 15.0F;
        this.chestKnob = (new ModelRenderer(this, 0, 0)).setTextureSize(64, 64);
        this.chestKnob.addBox(-1.0F, -2.0F, -15.0F, 2, 4, 1, 0.0F);
        this.chestKnob.rotationPointX = 8.0F;
        this.chestKnob.rotationPointY = 7.0F;
        this.chestKnob.rotationPointZ = 15.0F;
        this.chestBelow = (new ModelRenderer(this, 0, 19)).setTextureSize(64, 64);
        this.chestBelow.addBox(0.0F, 0.0F, 0.0F, 14, 10, 14, 0.0F);
        this.chestBelow.rotationPointX = 1.0F;
        this.chestBelow.rotationPointY = 6.0F;
        this.chestBelow.rotationPointZ = 1.0F;*/
	}

	void drawBox(AxisAlignedBB par1AxisAlignedBB)
	{
		Tessellator var2 = Tessellator.instance;

		//Top
		var2.startDrawing(GL11.GL_QUADS);
		var2.addVertex(par1AxisAlignedBB.minX+0.1, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ+0.1);
		var2.addVertex(par1AxisAlignedBB.maxX-0.1, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ+0.1);
		var2.addVertex(par1AxisAlignedBB.maxX-0.1, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ-0.1);
		var2.addVertex(par1AxisAlignedBB.minX+0.1, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ-0.1);
		var2.draw();
		
		//Bottom
				var2.startDrawing(GL11.GL_QUADS);
				var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
				var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
				var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
				var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
				var2.draw();

		//-x
		var2.startDrawing(GL11.GL_QUADS);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
		var2.addVertex(par1AxisAlignedBB.minX+0.1, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ+0.1);
		var2.addVertex(par1AxisAlignedBB.minX+0.1, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ-0.1);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
		var2.draw();

		//+x
		var2.startDrawing(GL11.GL_QUADS);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
		var2.addVertex(par1AxisAlignedBB.maxX-0.1, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ-0.1);
		var2.addVertex(par1AxisAlignedBB.maxX-0.1, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ+0.1);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
		var2.draw();

		//-z
		var2.startDrawing(GL11.GL_QUADS);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
		var2.addVertex(par1AxisAlignedBB.minX+0.1, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ+0.1);
		var2.addVertex(par1AxisAlignedBB.maxX-0.1, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ+0.1);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
		var2.draw();
		
		//+z
		var2.startDrawing(GL11.GL_QUADS);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
		var2.addVertex(par1AxisAlignedBB.maxX-0.1, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ-0.1);
		var2.addVertex(par1AxisAlignedBB.minX+0.1, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ-0.1);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
		var2.draw();
	}

	
	/**
	 * This method renders out all parts of the chest model.
	 */
	public void renderIngots(int i,int a)
	{
		//this.chestKnob.rotateAngleX = this.chestLid.rotateAngleX;
		//this.chestLid.render(0.0625F);
		//this.chestKnob.render(0.0625F);
		//this.chestBelow.render(0.0625F);
		for (int n = 0; n < i; n++){
			this.ingots[a][n].render(0.0625F / 2F);
		}
	}
}
