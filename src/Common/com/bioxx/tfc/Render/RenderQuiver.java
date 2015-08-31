package com.bioxx.tfc.Render;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Entities.Mobs.EntitySkeletonTFC;
import com.bioxx.tfc.Items.ItemQuiver;
import com.bioxx.tfc.Render.Models.ModelQuiver;
import com.bioxx.tfc.api.Interfaces.IEquipable;

public class RenderQuiver {

	private ModelQuiver quiver = new ModelQuiver();
	private static final ResourceLocation QUIVER_TEXTURE = new ResourceLocation(Reference.MOD_ID, "textures/models/armor/leatherquiver_1.png");

	public RenderQuiver(){

	}

	public void render(EntityLivingBase entity, ItemStack item) {
		this.doRender(entity, item);
	}

	public void doRender(EntityLivingBase entity, ItemStack item){
		float entityTranslateY = entity instanceof EntityPlayer ? 0F : -1.5F;
		GL11.glPushMatrix();
		Minecraft.getMinecraft().renderEngine.bindTexture(QUIVER_TEXTURE);
		if (!entity.isSneaking()){ GL11.glTranslatef(0F,  entityTranslateY + 0.0F/*0.65F*/, 0.1F);
		}
		else{ GL11.glTranslatef(0F,  entityTranslateY + 0.1F/*0.55F*/, 0.1F);
		GL11.glRotatef(20F, 1F, 0F, 0F);}
		if(item != null){
			if(item.getItem() instanceof IEquipable){
				((IEquipable)(item.getItem())).onEquippedRender();
			}
			if(entity instanceof EntitySkeletonTFC)
				this.quiver.render(entity,16);
			else
				this.quiver.render(entity,(((ItemQuiver)item.getItem()).getQuiverArrows(item))/8);
		}
		GL11.glPopMatrix();
	}


}
