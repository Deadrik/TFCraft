package com.bioxx.tfc.Render.Models;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.TileEntities.TELoom;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.PositionTextureVertex;
import net.minecraft.client.model.TexturedQuad;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class ModelLoom extends ModelBase {

	ModelRenderer loomPole1;
	ModelRenderer loomPole2;
	ModelRenderer loomPoleBase;

	TexturedQuad[] initialString;
	TexturedQuad[] finalString;

	TexturedQuad clothRender;

	PositionTextureVertex vert0;
	PositionTextureVertex vert1;
	PositionTextureVertex vert2;
	PositionTextureVertex vert3;

	float[] stringLength = new float[16];

	public int cloth = 0;
	float pole1String_y = 14.5F;
	float pole2String_y = 14.5F;

	int lastIncreaseTick_1;
	int lastIncreaseTick_2;
	
	public int tempNum = 0;
	public long tempTime = 0;
	public boolean clothIncrease = false;
	public int mod = 40;
	public int lastClothIncrease = 0;
	public boolean stillWeaving = false;

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

		float string1_z,string2_z;

		float stringStart_z = 11.9F;

		float stringWidth = 9.5F/numMaxStrings;
		float stringStart_x = 3.25F;

		float pole2_height = 2.15F;
		float pole1_height = 5.35F;
		
		if(cloth == 0){
			pole1String_y = 14.5F;
			pole2String_y = 14.5F;
		}
		
		if(shouldClothIncrease){
			cloth++;
		}

		if(shouldClothIncrease && tick >= ((tickMod/4)-1) && tick < (((3*tickMod)/4)-1)){
			pole1String_y = pole2String_y - stringWidth;
		}
		else if(shouldClothIncrease && (tick >= (((3*tickMod)/4)-1) || tick < ((tickMod/4)-1))){
			pole2String_y = pole1String_y - stringWidth;
		}
		
		if(tick >=(tickMod/2) &&(isWeaving || (stillWeaving && renderOffsetPole1 != 9) ) ){
			renderOffsetPole1 = 7f + (float)(2f * Math.cos(((tick)/(float)(tickMod/4))* Math.PI));
		}
		else if(isWeaving || (stillWeaving && renderOffsetPole2 != 9)){
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

		string1_z = Math.min(renderOffsetPole1+4.25F,12F);
		string2_z = Math.min(renderOffsetPole2+4.25F,12F);

		float string1_i_length = (float)Math.sqrt(Math.pow(stringStart_z - string1_z, 2) + Math.pow(pole1String_y - pole1_height, 1));
		float string2_i_length = (float)Math.sqrt(Math.pow(stringStart_z - string2_z, 2) + Math.pow(pole2String_y - pole2_height, 1));

		float string1_f_length = (float)Math.sqrt(Math.pow(stringStart_z - string1_z, 2) + Math.pow(pole1_height, 1));
		float string2_f_length = (float)Math.sqrt(Math.pow(stringStart_z - string2_z, 2) + Math.pow(pole2_height, 1));
		
		for(int i = 0; i < numStrings; i+=2){

			//pole2 strings

			//initial
			vert0 = new PositionTextureVertex(
					stringStart_x + (i*stringWidth),
					pole2String_y,
					stringStart_z,
					0,0);
			vert1 = new PositionTextureVertex(
					stringStart_x + stringWidth + (i*stringWidth),
					pole2String_y,
					stringStart_z,
					0,8);
			vert2 = new PositionTextureVertex(
					stringStart_x +(i*stringWidth),
					pole2_height,
					string2_z,
					8,0);
			vert3 = new PositionTextureVertex(
					stringStart_x + stringWidth + (i*stringWidth),
					pole2_height,
					string2_z,
					8,8);

			initialString[i] = new TexturedQuad(new PositionTextureVertex[] {vert2, vert3, vert1, vert0}, 
					0, 0, 1, (int)string2_i_length, 16, 16);

			//final
			vert0 = new PositionTextureVertex(
					stringStart_x + (i*stringWidth),
					pole2_height,
					string2_z,
					0,0);
			vert1 = new PositionTextureVertex(
					stringStart_x + stringWidth + (i*stringWidth),
					pole2_height,
					string2_z,
					0,8);
			vert2 = new PositionTextureVertex(
					stringStart_x +(i*stringWidth),
					0,
					stringStart_z,
					8,0);
			vert3 = new PositionTextureVertex(
					stringStart_x + stringWidth + (i*stringWidth),
					0,
					stringStart_z,
					8,8);

			finalString[i] = new TexturedQuad(new PositionTextureVertex[] {vert2, vert3, vert1, vert0}, 
					0, 0, 1, (int)string2_f_length, 16, 16);



			//pole1 strings
			//initial
			vert0 = new PositionTextureVertex(
					stringStart_x + ((i+1)*stringWidth),
					pole1String_y,
					stringStart_z,
					0,0);
			vert1 = new PositionTextureVertex(
					stringStart_x + stringWidth + ((i+1)*stringWidth),
					pole1String_y,
					stringStart_z,
					0,8);
			vert2 = new PositionTextureVertex(
					stringStart_x + ((i+1)*stringWidth),
					pole1_height,
					string1_z,
					8,0);
			vert3 = new PositionTextureVertex(
					stringStart_x + stringWidth + ((i+1)*stringWidth),
					pole1_height,
					string1_z,
					8,8);
			if(i+1 < numStrings){
				initialString[i+1] = new TexturedQuad(new PositionTextureVertex[] {vert2, vert3, vert1, vert0}, 
						0, 0, 1, (int)string1_i_length, 16, 16);
				//final
				vert0 = new PositionTextureVertex(
						stringStart_x + ((i+1)*stringWidth),
						pole1_height,
						string1_z,
						0,0);
				vert1 = new PositionTextureVertex(
						stringStart_x + stringWidth + ((i+1)*stringWidth),
						pole1_height,
						string1_z,
						0,8);
				vert2 = new PositionTextureVertex(
						stringStart_x + ((i+1)*stringWidth),
						0,
						stringStart_z,
						8,0);
				vert3 = new PositionTextureVertex(
						stringStart_x + stringWidth + ((i+1)*stringWidth),
						0,
						stringStart_z,
						8,8);

				finalString[i+1] = new TexturedQuad(new PositionTextureVertex[] {vert2, vert3, vert1, vert0}, 
						0, 0, 1, (int)string1_f_length, 16, 16);
			}
		}
		vert0 = new PositionTextureVertex(
				stringStart_x,
				14.5F,
				stringStart_z,
				0,0);
		vert1 = new PositionTextureVertex(
				stringStart_x + (numStrings*stringWidth),
				14.5F,
				stringStart_z,
				0,8);
		vert2 = new PositionTextureVertex(
				stringStart_x,
				14.5F - (cloth*stringWidth),
				stringStart_z,
				8,0);
		vert3 = new PositionTextureVertex(
				stringStart_x + (numStrings*stringWidth),
				14.5F - (cloth*stringWidth),
				stringStart_z,
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
