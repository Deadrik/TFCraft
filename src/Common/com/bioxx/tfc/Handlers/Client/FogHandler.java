package com.bioxx.tfc.Handlers.Client;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.EntityViewRenderEvent.RenderFogEvent;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.Core.TFC_Climate;
import com.bioxx.tfc.Core.WeatherManager;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class FogHandler 
{
	double lerpTime = 14f;
	double lerpTimer = 0f;
	float mult = 0.0f;
	boolean rainLast = false;
	boolean snowLast = false;
	boolean shouldLerp = false;
	float fogEnd = 0;
	float fogStart = 0;	
	float fogDensity = 0.1f;
	float fogEndBegin = 0;
	float fogStartBegin = 0;	
	float fogDensityBegin = 0.1f;
	float fogEndFinish = 0;
	float fogStartFinish = 0;	
	float fogDensityFinish = 0.1f;
	float localWorldFog = 0;
	float snowStrength = 0;
	int renderRange = 17;

	@SubscribeEvent
	public void RenderFogHandler(RenderFogEvent event)
	{

		if(event.fogMode >= 0)
		{
			//If the user changes their render range, we need to rerun all of this
			if(renderRange != Minecraft.getMinecraft().gameSettings.renderDistanceChunks)
			{
				renderRange = Minecraft.getMinecraft().gameSettings.renderDistanceChunks;
				boolean rainLast = false;
				boolean snowLast = false;
				fogStart = event.farPlaneDistance * 0.75f;
				fogEnd = event.farPlaneDistance;
			}

			//Detect if the weather is changing.
			if(shouldLerp)
			{
				lerpTimer += event.renderPartialTicks;
				//System.out.println(event.renderPartialTicks + " | " +(lerpTime - lerpTimer)/20 + ": " + fogStartBegin + " of " + fogStartFinish + " | " + fogEndBegin + " of " + fogEndFinish);
				if(lerpTimer >= lerpTime)
					shouldLerp = false;

				double lerpMult = lerpTimer/lerpTime;

				fogStart = lerp(fogStartBegin, fogStartFinish, lerpTime, lerpTimer);
				fogEnd = lerp(fogEndBegin, fogEndFinish, lerpTime, lerpTimer);
				fogDensity = lerp(fogDensityBegin, fogDensityFinish, lerpTime, lerpTimer);
			}

			calcFog(event);

			if((int)event.entity.posY > 128)
			{
				GL11.glFogf(GL11.GL_FOG_DENSITY, fogDensity);
				GL11.glFogf(GL11.GL_FOG_START, fogStart);
				GL11.glFogf(GL11.GL_FOG_END, fogEnd);
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
			if(rainLast || (localWorldFog != 0 && localFog == 0)) 
			{
				startLerp(600);
				localWorldFog = 0;
				rainLast = false; 
				snowLast = false;
				fogStartFinish = event.farPlaneDistance*0.75f;
				fogEndFinish = event.farPlaneDistance;
				fogDensityFinish = 0.1f;
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
