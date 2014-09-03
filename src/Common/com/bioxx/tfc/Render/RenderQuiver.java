package com.bioxx.tfc.Render;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Render.Models.ModelQuiver;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

public class RenderQuiver {

	private ModelQuiver quiver = new ModelQuiver();
	private static final ResourceLocation QuiverTexture = new ResourceLocation(Reference.ModID, "textures/models/armor/leatherquiver_1.png");
	
	public RenderQuiver(){
		
	}
	
	public void render(EntityLivingBase entity, int numArrows) {
        this.doRender(entity, numArrows);
    }
	
	public void doRender(EntityLivingBase entity, int numArrows){
		GL11.glPushMatrix();
        Minecraft.getMinecraft().renderEngine.bindTexture(QuiverTexture);
        if (!entity.isSneaking()){ GL11.glTranslatef(0F, 0.0F/*0.65F*/, 0.1F);
        }
        else{ GL11.glTranslatef(0F, 0.1F/*0.55F*/, 0.1F);
        GL11.glRotatef(20F, 1F, 0F, 0F);}
        GL11.glScalef(0.8F, 0.8F, 0.8F);
        this.quiver.render(entity, numArrows);
        GL11.glPopMatrix();
	}
	
	
}
