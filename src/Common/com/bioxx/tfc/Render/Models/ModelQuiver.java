package com.bioxx.tfc.Render.Models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class ModelQuiver extends ModelBase {

	private ModelRenderer quiver;
	private ModelRenderer[] arrows = new ModelRenderer[16];
	
	public ModelQuiver(){
		super();
		//Quiver
				quiver = new ModelRenderer(this,38,0);
				quiver.addBox(-2.5f,-6,-1.5f,5,12,3);
				quiver.setRotationPoint(0,4,4);
				quiver.rotateAngleZ = (float)(Math.PI/4) + (float)(Math.PI);
				quiver.setTextureSize(64, 32);

				for(int i = 0; i < arrows.length; i++)
				{
					arrows[i] = new ModelRenderer(this,59,0);
					arrows[i].addBox(-1,-8,0,2,14,0);
					arrows[i].setRotationPoint(0,0,0f);
					arrows[i].setTextureSize(64,32);
					arrows[i].rotateAngleZ = (float)(Math.PI) + (float)(Math.PI/36)*(i%3)*(i%2==0?1f:-1f);
					arrows[i].rotateAngleX = (float)(Math.PI/36)*(i%3)*(i%2==(i%3)?1f:-1f);
					quiver.addChild(arrows[i]);
				}

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

    public void render(EntityLivingBase theEntity, int numArrows) {
    	for(int i = 0; i < numArrows; i++){
    		arrows[i].isHidden = false;
    	}
    	this.quiver.render(0.0625F);
    	for(int i = 0; i < arrows.length; i++){
    		arrows[i].isHidden = true;
    	}
    }
    
    @Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        if (entity instanceof EntityPlayer) this.quiver.render(0.0625F);
    }
}
