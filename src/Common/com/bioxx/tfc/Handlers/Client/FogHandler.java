package com.bioxx.tfc.Handlers.Client;

import net.minecraft.client.Minecraft;

import net.minecraftforge.client.event.EntityViewRenderEvent.RenderFogEvent;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.Core.TFC_Climate;
import com.bioxx.tfc.Core.WeatherManager;

public class FogHandler 
{
	private double lerpTime = 14f;
	private double lerpTimer;
	//private float mult;
	private boolean rainLast;
	private boolean snowLast;
	private boolean shouldLerp;
	private float fogEnd;
	private float fogStart;
	private float fogDensity;
	private float fogEndBegin;
	private float fogStartBegin;
	private float fogDensityBegin;
	private float fogEndFinish;
	private float fogStartFinish;
	private float fogDensityFinish;
	private float localWorldFog;
	private float snowStrength;
	private int renderRange = 17;

	public FogHandler()
	{
		// PMD Optimization - Initialize floats in constructors.
		this.fogDensity = 0.1f;
		this.fogDensityBegin = 0.1f;
		this.fogDensityFinish = 0.1f;
	}

	@SubscribeEvent
	public void renderFogHandler(RenderFogEvent event)
	{

		if(event.fogMode >= 0)
		{
			//If the user changes their render range, we need to rerun all of this
			if(renderRange != Minecraft.getMinecraft().gameSettings.renderDistanceChunks || fogEnd < 16)
			{
				renderRange = Minecraft.getMinecraft().gameSettings.renderDistanceChunks;
				//boolean rainLast = false;
				//boolean snowLast = false;
				fogStart = event.farPlaneDistance * 0.75f;
				fogEnd = event.farPlaneDistance;
			}

			//Detect if the weather is changing.
			if(shouldLerp)
			{
				lerpTimer += event.renderPartialTicks;
				//TerraFirmaCraft.log.info(event.renderPartialTicks + " | " +(lerpTime - lerpTimer)/20 + ": " + fogStartBegin + " of " + fogStartFinish + " | " + fogEndBegin + " of " + fogEndFinish);
				if(lerpTimer >= lerpTime)
					shouldLerp = false;

				//double lerpMult = lerpTimer/lerpTime;

				fogStart = lerp(fogStartBegin, fogStartFinish, lerpTime, lerpTimer);
				fogEnd = lerp(fogEndBegin, fogEndFinish, lerpTime, lerpTimer);
				fogDensity = lerp(fogDensityBegin, fogDensityFinish, lerpTime, lerpTimer);
			}

			calcFog(event);

			if((int)event.entity.posY > 128)
			{
				GL11.glFogf(GL11.GL_FOG_DENSITY, Math.min(fogDensity, 0.3f));
				GL11.glFogf(GL11.GL_FOG_START, Math.max(fogStart, 8f));
				GL11.glFogf(GL11.GL_FOG_END, Math.max(fogEnd, 16f));
			}
		}
	}

	private void calcFog(RenderFogEvent event)
	{
		if(renderRange < 6)
			return;
		boolean raining = event.entity.worldObj.isRaining();
		float temp = TFC_Climate.getHeightAdjustedTemp(event.entity.worldObj, (int)event.entity.posX, (int)event.entity.posY, (int)event.entity.posZ);
		float localFog = WeatherManager.getInstance().getLocalFog(event.entity.worldObj, (int)event.entity.posX, (int)event.entity.posY, (int)event.entity.posZ);
		if(localFog != localWorldFog)
			localFog = WeatherManager.getInstance().getLocalFog(event.entity.worldObj, (int)event.entity.posX, (int)event.entity.posY, (int)event.entity.posZ);

		if(raining && temp > 0)
		{
			if(!rainLast || snowLast) 
			{
				startLerp(600); 
				rainLast = true; 
				snowLast = false;
				fogStartFinish = event.farPlaneDistance*0.5f;
				fogEndFinish = event.farPlaneDistance*0.75f;
				fogDensityFinish = 0.2f;
			}
		}
		else if(raining && temp <= 0)
		{
			if(!snowLast)  
			{
				startLerp(600); 
				rainLast = true; 
				snowLast = true;
				snowStrength = WeatherManager.getInstance().getSnowStrength();
				fogStartFinish = event.farPlaneDistance*0.1f;
				fogEndFinish = event.farPlaneDistance*(0.2f+(0.1f*snowStrength));
				fogDensityFinish = 0.3f;
			}
			else if(snowLast && WeatherManager.getInstance().getSnowStrength() != snowStrength)  
			{
				startLerp(300); 
				snowStrength = WeatherManager.getInstance().getSnowStrength();
				fogStartFinish = event.farPlaneDistance*0.1f;
				fogEndFinish = event.farPlaneDistance*(0.2f+(0.1f*snowStrength));
				fogDensityFinish = 0.3f;
			}
		}
		else if(localFog > 0 && localFog != localWorldFog)
		{
			startLerp(600);
			localWorldFog = localFog;
			fogStartFinish = event.farPlaneDistance*0.1f;
			fogEndFinish = event.farPlaneDistance*(0.6f-(0.3f*localFog));
			fogDensityFinish = 0.2f-(0.08f*localFog);
		}
		else if(!raining)
		{
			if (rainLast || localWorldFog != 0 && localFog == 0)
			{
				startLerp(600);
				localWorldFog = 0;
				rainLast = false; 
				snowLast = false;
				fogStartFinish = event.farPlaneDistance*0.75f;
				fogEndFinish = event.farPlaneDistance;
				fogDensityFinish = 0.1f;
			}
			else if(!shouldLerp && localWorldFog==0)
			{
				fogStart = event.farPlaneDistance*0.75f;
				fogEnd = event.farPlaneDistance;
				fogDensity = 0.1f;
			}

		}
	}

	public float lerp(float start, float target, double duration, double timeSinceStart)
	{
		float value = start;
		if (timeSinceStart > 0.0f && timeSinceStart < duration)
		{
			final float range = target - start;
			final float percent = (float)(timeSinceStart / duration);
			value = start + (range * percent);
		}
		else if (timeSinceStart >= duration)
		{
			value = target;
		}
		return value;
	}

	private void startLerp(double ticks)
	{
		if(!shouldLerp)
		{
			shouldLerp = true;
			lerpTimer = 0;
			lerpTime = ticks;
			fogStartBegin = fogStart;
			fogEndBegin = fogEnd;
			fogDensityBegin = fogDensity;
		}
	}
}
