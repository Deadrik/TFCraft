package com.bioxx.tfc.Render;

import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.util.ResourceLocation;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import org.apache.logging.log4j.LogManager;

@SideOnly(Side.CLIENT)
public class EntityRendererTFC extends EntityRenderer {

	private boolean allowShaderSwitching = true;
	private ResourceLocation currentShader;

	public EntityRendererTFC(Minecraft minecraft, IResourceManager irm) {
		super(minecraft, irm);
	}

	@Override
	public void deactivateShader()
	{
		if(allowShaderSwitching){
			super.activateNextShader();
		}
	}

	public void setManualShader(ResourceLocation shaderLocation){
		deactivateManualShader();
		try{
			Minecraft mc = Minecraft.getMinecraft();
			this.theShaderGroup = new ShaderGroup(mc.getTextureManager(), mc.getResourceManager(), mc.getFramebuffer(), shaderLocation);
			this.theShaderGroup.createBindFramebuffers(mc.displayWidth, mc.displayHeight);
			this.currentShader = shaderLocation;
		}
		catch (IOException ioexception)
		{
			LogManager.getLogger().warn("Failed to load shader: " + shaderLocation, ioexception);
		}
		allowShaderSwitching = false;
	}

	public void deactivateManualShader(){
		allowShaderSwitching = true;
		super.deactivateShader();
	}
	
	public ResourceLocation getCurrentShaderLocation(){
		return this.currentShader;
	}

	public boolean getManualShaderBeingUsed(){
		return !this.allowShaderSwitching;
	}

	@Override
	public void activateNextShader()
	{
		if(allowShaderSwitching){
			super.activateNextShader();
		}
	}
}
