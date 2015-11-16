package com.bioxx.tfc.Render;

import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderHorse;
import net.minecraft.client.renderer.texture.LayeredTexture;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.util.ResourceLocation;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.Entities.Mobs.EntityHorseTFC;
import com.google.common.collect.Maps;

@SideOnly(Side.CLIENT)
public class RenderHorseTFC extends RenderHorse
{
	private static final Map TEXTURE_MAP = Maps.newHashMap();
	private static final ResourceLocation WHITE_TEXTURE = new ResourceLocation("textures/entity/horse/horse_white.png");
	private static final ResourceLocation MULE_TEXTURE = new ResourceLocation("textures/entity/horse/mule.png");
	private static final ResourceLocation DONKEY_TEXTURE = new ResourceLocation("textures/entity/horse/donkey.png");
	private static final ResourceLocation ZOMBIE_TEXTURE = new ResourceLocation("textures/entity/horse/horse_zombie.png");
	private static final ResourceLocation SKELETON_TEXTURE = new ResourceLocation("textures/entity/horse/horse_skeleton.png");

	public RenderHorseTFC(ModelBase par1ModelBase, float par2)
	{
		super(par1ModelBase, par2);
	}

	protected void renderHorse(EntityHorse par1EntityHorse, float par2, float par3, float par4, float par5, float par6, float par7)
	{
		if (par1EntityHorse.isInvisible())
		{
			this.mainModel.setRotationAngles(par2, par3, par4, par5, par6, par7, par1EntityHorse);
		}
		else
		{
			this.bindEntityTexture(par1EntityHorse);
			this.mainModel.render(par1EntityHorse, par2, par3, par4, par5, par6, par7);
		}
	}

	protected ResourceLocation getTextureLocation(EntityHorse par1EntityHorse)
	{
		if (!par1EntityHorse.func_110239_cn())
		{
			switch (par1EntityHorse.getHorseType())
			{
			case 0:
			default:
				return WHITE_TEXTURE;
			case 1:
				return DONKEY_TEXTURE;
			case 2:
				return MULE_TEXTURE;
			case 3:
				return ZOMBIE_TEXTURE;
			case 4:
				return SKELETON_TEXTURE;
			}
		}
		else
		{
			return this.getTexture(par1EntityHorse);
		}
	}

	private ResourceLocation getTexture(EntityHorse par1EntityHorse)
	{
		String s = par1EntityHorse.getHorseTexture();
		ResourceLocation resourcelocation = (ResourceLocation)TEXTURE_MAP.get(s);

		if (resourcelocation == null)
		{
			resourcelocation = new ResourceLocation(s);
			Minecraft.getMinecraft().getTextureManager().loadTexture(resourcelocation, new LayeredTexture(par1EntityHorse.getVariantTexturePaths()));
			TEXTURE_MAP.put(s, resourcelocation);
		}

		return resourcelocation;
	}

	/**
	 * Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args:
	 * entityLiving, partialTickTime
	 */
	@Override
	protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float par2)
	{
		float scale = ((EntityHorseTFC) par1EntityLivingBase).getSizeMod();
		GL11.glScalef(scale, scale, scale);
		this.preRenderCallback((EntityHorse)par1EntityLivingBase, par2);
	}

	/**
	 * Renders the model in RenderLiving
	 */
	@Override
	protected void renderModel(EntityLivingBase par1EntityLivingBase, float par2, float par3, float par4, float par5, float par6, float par7)
	{
		this.renderHorse((EntityHorse)par1EntityLivingBase, par2, par3, par4, par5, par6, par7);
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
	 */
	@Override
	protected ResourceLocation getEntityTexture(Entity par1Entity)
	{
		return this.getTextureLocation((EntityHorse)par1Entity);
	}
}
