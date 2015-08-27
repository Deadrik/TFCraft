package com.bioxx.tfc.Render.Models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.AxisAlignedBB;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class ModelBarrel extends ModelBase
{
	/** The chest lid in the chest's model. */
	//public ModelRenderer chestLid = (new ModelRenderer(this, 0, 0)).setTextureSize(64, 64);

	public ModelRenderer barrel;
	public ModelRenderer barrel2;
	public ModelRenderer barrel3;

	//double d1;
	//double d2;
	//double d3;
	/** The model of the bottom of the chest. */
	public ModelRenderer chestBelow;

	/** The chest's knob in the chest model. */
	public ModelRenderer chestKnob;

	public ModelBarrel(int type)
	{

		this.barrel = new ModelRenderer(this,0+(type*56),0).setTextureSize(952,76);
		this.barrel2 = new ModelRenderer(this,0+(type*56),44).setTextureSize(952,76);
		this.barrel3 = new ModelRenderer(this,0+(type*56),44).setTextureSize(952,76);

		this.barrel.setRotationPoint(16,16,0);
		this.barrel2.setRotationPoint(0,0,0);
		this.barrel3.setRotationPoint(0,0,0);
		//drawBox(AxisAlignedBB.getAABBPool().addOrModifyAABBInPool(d1,d2,d3,d1+1+x,d2+1+y,d3+1+z));
		//TerraFirmaCraft.log.info("COORDS: "+d1+", "+d2+", "+d3);
		this.barrel.addBox(1F,0f,1f, 14, 16, 14,0f);
		barrel.rotateAngleZ =-(float)Math.PI;
		this.barrel2.addBox(2, 0f, 2, 12, 16, 0,0f);
		this.barrel2.addBox(2, 0f, 2, 0, 16, 12,0f);
		this.barrel2.addBox(2, 0f, 14, 12, 16, 0,0f);
		this.barrel2.addBox(14, 0f, 2, 0, 16, 12,0f);
		this.barrel3.addBox(2, 2,2, 12, 12, 12,0f);


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


	public void drawBox(AxisAlignedBB par1AxisAlignedBB)
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
	public void renderBarrel()
	{

		this.barrel.render(0.0625f);
		this.barrel2.render(0.0625f);
		this.barrel3.render(0.0625f);
	}
}
