package com.bioxx.tfc.Render.Models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.PositionTextureVertex;
import net.minecraft.client.model.TexturedQuad;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.TileEntities.TELoom;

public class ModelLoom extends ModelBase {

	private ModelRenderer loomPole1;
	private ModelRenderer loomPole2;
	//private ModelRenderer loomPoleBase;

	private TexturedQuad[] initialString;
	private TexturedQuad[] finalString;

	private TexturedQuad clothRender;

	private PositionTextureVertex vert0;
	private PositionTextureVertex vert1;
	private PositionTextureVertex vert2;
	private PositionTextureVertex vert3;

	//private float[] stringLength = new float[16];

	public int cloth;
	private float pole1StringY = 14.5F;
	private float pole2StringY = 14.5F;

	/*private int lastIncreaseTick_1;
	private int lastIncreaseTick_2;*/
	
	public int tempNum;
	public long tempTime;
	public boolean clothIncrease;
	public int mod = 40;
	public int lastClothIncrease;
	public boolean stillWeaving;

	public ModelLoom(){
		super();
		loomPole2 = new ModelRenderer(this,38,0);
		loomPole2.addBox(0f,0f,0f,14,1,1);
		loomPole2.setRotationPoint(1F, 1.65F, 10F);

		loomPole1 = new ModelRenderer(this,38,0);
		loomPole1.addBox(0f,0f,0f,14,1,1);
		loomPole1.setRotationPoint(1F, 4.85F, 10F);
	}


	/**
	 * Sets the rotation on a model where the provided params are in radians
	 * @param model The model
	 * @param x The x angle
	 * @param y The y angle
	 * @param z The z angle
	 */
	protected void setRotationRadians(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	/**
	 * Sets the rotation on a model where the provided params are in degrees
	 * @param model The model
	 * @param x The x angle
	 * @param y The y angle
	 * @param z The z angle
	 */
	protected void setRotationDegrees(ModelRenderer model, float x, float y, float z) {
		this.setRotationRadians(model, (float) Math.toRadians(x), (float) Math.toRadians(y), (float) Math.toRadians(z));
	}

	public void render(int numStrings, int numMaxStrings, int tick, boolean shouldClothIncrease, int tickMod, ResourceLocation stringTex, boolean isWeaving, boolean stillWeaving, TELoom te) {
		float renderOffsetPole2 = 9f;
		float renderOffsetPole1 = 9f;
		
		initialString = new TexturedQuad[numMaxStrings];
		finalString = new TexturedQuad[numMaxStrings];

		float string1Z,string2Z;

		float stringStartZ = 11.9F;

		float stringWidth = 9.5F/numMaxStrings;
		float stringStartX = 3.25F;

		float pole2Height = 2.15F;
		float pole1Height = 5.35F;
		
		if(cloth == 0){
			pole1StringY = 14.5F;
			pole2StringY = 14.5F;
		}
		
		if(shouldClothIncrease){
			cloth++;
		}

		if (shouldClothIncrease && tick >= (tickMod / 4) - 1 && tick < ((3 * tickMod) / 4) - 1)
		{
			pole1StringY = pole2StringY - stringWidth;
		}
		else if (shouldClothIncrease && (tick >= ((3 * tickMod) / 4) - 1 || tick < (tickMod / 4) - 1))
		{
			pole2StringY = pole1StringY - stringWidth;
		}
		
		if (tick >= tickMod / 2 && (isWeaving || stillWeaving && renderOffsetPole1 != 9))
		{
			renderOffsetPole1 = 7f + (float)(2f * Math.cos(((tick)/(float)(tickMod/4))* Math.PI));
		}
		else if (isWeaving || stillWeaving && renderOffsetPole2 != 9)
		{
			renderOffsetPole2 = 7f + (float)(2f * Math.cos(((tick)/(float)(tickMod/4))* Math.PI));
		}
		
		if(cloth >= numMaxStrings && shouldClothIncrease){
			resetCloth(te);
		}

		loomPole2.setRotationPoint(1F, 1.65F, renderOffsetPole2);
		loomPole1.setRotationPoint(1F, 4.85F, renderOffsetPole1);
		
		GL11.glPushMatrix();
		GL11.glRotatef(te.rotation * -90F, 0, 1, 0);
		switch(te.rotation){
		case 0: break;
		case 1: GL11.glTranslatef(0, 0, -1);break;
		case 2: GL11.glTranslatef(-1, 0, -1);break;
		case 3: GL11.glTranslatef(-1, 0, 0);break;
		default:break;
		}
		this.loomPole2.render(0.0625F);
		this.loomPole1.render(0.0625F);

		TFC_Core.bindTexture(stringTex); //texture

		string1Z = Math.min(renderOffsetPole1+4.25F,12F);
		string2Z = Math.min(renderOffsetPole2+4.25F,12F);

		float string1Length = (float)Math.sqrt(Math.pow(stringStartZ - string1Z, 2) + Math.pow(pole1StringY - pole1Height, 1));
		float string2Length = (float)Math.sqrt(Math.pow(stringStartZ - string2Z, 2) + Math.pow(pole2StringY - pole2Height, 1));

		float string1FinalLength = (float)Math.sqrt(Math.pow(stringStartZ - string1Z, 2) + Math.pow(pole1Height, 1));
		float string2FinalLength = (float)Math.sqrt(Math.pow(stringStartZ - string2Z, 2) + Math.pow(pole2Height, 1));
		
		for(int i = 0; i < numStrings; i+=2){

			//pole2 strings

			//initial
			vert0 = new PositionTextureVertex(
					stringStartX + (i*stringWidth),
					pole2StringY,
					stringStartZ,
					0,0);
			vert1 = new PositionTextureVertex(
					stringStartX + stringWidth + (i*stringWidth),
					pole2StringY,
					stringStartZ,
					0,8);
			vert2 = new PositionTextureVertex(
					stringStartX +(i*stringWidth),
					pole2Height,
					string2Z,
					8,0);
			vert3 = new PositionTextureVertex(
					stringStartX + stringWidth + (i*stringWidth),
					pole2Height,
					string2Z,
					8,8);

			initialString[i] = new TexturedQuad(new PositionTextureVertex[] {vert2, vert3, vert1, vert0}, 
					0, 0, 1, (int)string2Length, 16, 16);

			//final
			vert0 = new PositionTextureVertex(
					stringStartX + (i*stringWidth),
					pole2Height,
					string2Z,
					0,0);
			vert1 = new PositionTextureVertex(
					stringStartX + stringWidth + (i*stringWidth),
					pole2Height,
					string2Z,
					0,8);
			vert2 = new PositionTextureVertex(
					stringStartX +(i*stringWidth),
					0,
					stringStartZ,
					8,0);
			vert3 = new PositionTextureVertex(
					stringStartX + stringWidth + (i*stringWidth),
					0,
					stringStartZ,
					8,8);

			finalString[i] = new TexturedQuad(new PositionTextureVertex[] {vert2, vert3, vert1, vert0}, 
					0, 0, 1, (int)string2FinalLength, 16, 16);



			//pole1 strings
			//initial
			vert0 = new PositionTextureVertex(
					stringStartX + ((i+1)*stringWidth),
					pole1StringY,
					stringStartZ,
					0,0);
			vert1 = new PositionTextureVertex(
					stringStartX + stringWidth + ((i+1)*stringWidth),
					pole1StringY,
					stringStartZ,
					0,8);
			vert2 = new PositionTextureVertex(
					stringStartX + ((i+1)*stringWidth),
					pole1Height,
					string1Z,
					8,0);
			vert3 = new PositionTextureVertex(
					stringStartX + stringWidth + ((i+1)*stringWidth),
					pole1Height,
					string1Z,
					8,8);
			if(i+1 < numStrings){
				initialString[i+1] = new TexturedQuad(new PositionTextureVertex[] {vert2, vert3, vert1, vert0}, 
						0, 0, 1, (int)string1Length, 16, 16);
				//final
				vert0 = new PositionTextureVertex(
						stringStartX + ((i+1)*stringWidth),
						pole1Height,
						string1Z,
						0,0);
				vert1 = new PositionTextureVertex(
						stringStartX + stringWidth + ((i+1)*stringWidth),
						pole1Height,
						string1Z,
						0,8);
				vert2 = new PositionTextureVertex(
						stringStartX + ((i+1)*stringWidth),
						0,
						stringStartZ,
						8,0);
				vert3 = new PositionTextureVertex(
						stringStartX + stringWidth + ((i+1)*stringWidth),
						0,
						stringStartZ,
						8,8);

				finalString[i+1] = new TexturedQuad(new PositionTextureVertex[] {vert2, vert3, vert1, vert0}, 
						0, 0, 1, (int)string1FinalLength, 16, 16);
			}
		}
		vert0 = new PositionTextureVertex(
				stringStartX,
				14.5F,
				stringStartZ,
				0,0);
		vert1 = new PositionTextureVertex(
				stringStartX + (numStrings*stringWidth),
				14.5F,
				stringStartZ,
				0,8);
		vert2 = new PositionTextureVertex(
				stringStartX,
				14.5F - (cloth*stringWidth),
				stringStartZ,
				8,0);
		vert3 = new PositionTextureVertex(
				stringStartX + (numStrings*stringWidth),
				14.5F - (cloth*stringWidth),
				stringStartZ,
				8,8);

		clothRender = new TexturedQuad(new PositionTextureVertex[] {vert0, vert1, vert3, vert2}, 
				0, 0, 16, cloth, 16, 16);
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_CULL_FACE);
		for(int i = 0; i < numStrings; i++){
			initialString[i].draw(Tessellator.instance, 0.0625F);
			finalString[i].draw(Tessellator.instance, 0.0625F);
		}
		clothRender.draw(Tessellator.instance, 0.0625F);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glPopMatrix();
		GL11.glPopMatrix();
	}

	public void updateCloth(int newCloth){
		cloth = newCloth;
	}
	
	public void resetCloth(TELoom te){
		
		te.finishCloth();
	}
}
