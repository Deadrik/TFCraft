package com.bioxx.tfc.Render;

import java.util.EnumSet;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.util.IIcon;

import net.minecraftforge.common.util.ForgeDirection;

public class RenderBlocksLightCache extends RenderBlocksFixUV
{
	private EnumSet<ForgeDirection> facesToDraw = EnumSet.allOf( ForgeDirection.class );
	
	private RenderFaceData sides[]  = new RenderFaceData[6];
	
	private static class RenderPointData
	{
		private int brightness;
		private float r, g, b;
	};
	
	private static class RenderFaceData
	{
		
		private int cacheBrightnessTopLeft;
		private int cacheBrightnessBottomLeft;
		private int cacheBrightnessBottomRight;
		private int cacheBrightnessTopRight;
		
		private float cacheColorRedTopLeft;
		private float cacheColorRedBottomLeft;
		private float cacheColorRedBottomRight;
		private float cacheColorRedTopRight;
		
		private float cacheColorGreenTopLeft;
		private float cacheColorGreenBottomLeft;
		private float cacheColorGreenBottomRight;
		private float cacheColorGreenTopRight;
		
		private float cacheColorBlueTopLeft;
		private float cacheColorBlueBottomLeft;
		private float cacheColorBlueBottomRight;
		private float cacheColorBlueTopRight;
		
		public RenderFaceData(RenderBlocks rb)
		{
			cacheBrightnessTopLeft = rb.brightnessTopLeft;
			cacheBrightnessBottomLeft = rb.brightnessBottomLeft;
			cacheBrightnessBottomRight = rb.brightnessBottomRight;
			cacheBrightnessTopRight = rb.brightnessTopRight;
			
			cacheColorRedTopLeft = rb.colorRedTopLeft;
			cacheColorRedBottomLeft = rb.colorRedBottomLeft;
			cacheColorRedBottomRight = rb.colorRedBottomRight;
			cacheColorRedTopRight = rb.colorRedTopRight;
			
			cacheColorGreenTopLeft = rb.colorGreenTopLeft;
			cacheColorGreenBottomLeft = rb.colorGreenBottomLeft;
			cacheColorGreenBottomRight = rb.colorGreenBottomRight;
			cacheColorGreenTopRight = rb.colorGreenTopRight;
			
			cacheColorBlueTopLeft = rb.colorBlueTopLeft;
			cacheColorBlueBottomLeft = rb.colorBlueBottomLeft;
			cacheColorBlueBottomRight = rb.colorBlueBottomRight;
			cacheColorBlueTopRight = rb.colorBlueTopRight;
		}
		
		public RenderPointData getCachedValue( double leftRight, double topBottom)
		{
			RenderPointData o = new RenderPointData();
			
			// interpolate colors
			double rTop = cacheColorRedTopLeft * leftRight + (1.0d - leftRight) * cacheColorRedTopRight;
			double rBottom = cacheColorRedBottomLeft * leftRight + (1.0d - leftRight) * cacheColorRedBottomRight;
			o.r = (float) (rTop * topBottom + rBottom * (1.0d - topBottom));
			
			double gTop = cacheColorGreenTopLeft * leftRight + (1.0d - leftRight) * cacheColorGreenTopRight;
			double gBottom = cacheColorGreenBottomLeft * leftRight + (1.0d - leftRight) * cacheColorGreenBottomRight;
			o.g = (float) (gTop * topBottom + gBottom * (1.0d - topBottom));
			
			double bTop = cacheColorBlueTopLeft * leftRight + (1.0d - leftRight) * cacheColorBlueTopRight;
			double bBottom = cacheColorBlueBottomLeft * leftRight + (1.0d - leftRight) * cacheColorBlueBottomRight;
			o.b = (float) (bTop * topBottom + bBottom * (1.0d - topBottom));
			
			// interpolate lighting, do sky and block light separately
			double highTop = (cacheBrightnessTopLeft >> 16 & 0xff) * leftRight + (1.0d - leftRight) * (cacheBrightnessTopRight >> 16 & 0xff);
			double highBottom = (cacheBrightnessBottomLeft >> 16 & 0xff) * leftRight + (1.0d - leftRight) * (cacheBrightnessBottomRight >> 16 & 0xff);
			int high = ((int) (highTop * topBottom + highBottom * (1.0d - topBottom))) & 0xff;
			
			double lowTop = (cacheBrightnessTopLeft & 0xff) * leftRight + (1.0d - leftRight) * (cacheBrightnessTopRight & 0xff);
			double lowBottom = (cacheBrightnessBottomLeft & 0xff) * leftRight + (1.0d - leftRight) * (cacheBrightnessBottomRight & 0xff);
			int low = ((int) (lowTop * topBottom + lowBottom * (1.0d - topBottom))) & 0xff;
			
			// merge sky and block light.
			o.brightness = (high << 16) | low;
			
			return o;
		}
	
	};
	
	public RenderBlocksLightCache(RenderBlocks old)
	{
		super( old );
	}
	
	/**
	* enable or disable caching
	*/
	public void disableRender()
	{
		facesToDraw = EnumSet.noneOf( ForgeDirection.class );
	}
	
	public void enableRender()
	{
		facesToDraw = EnumSet.allOf( ForgeDirection.class );
	}
	
	/**
	* the following are just to enable and disable the actual drawing.
	*/
	
	@Override
	public void renderFaceZNeg(Block a, double b, double c, double d, IIcon e)
	{
		if ( ! facesToDraw.contains( ForgeDirection.NORTH ) )
		{
			saveData( ForgeDirection.NORTH );
			return;
		}
		
		super.renderFaceZNeg(a, b, c, d, e);
	}
	
	@Override
	public void renderFaceXPos(Block a, double b, double c, double d, IIcon e)
	{
		if ( ! facesToDraw.contains( ForgeDirection.EAST ) )
		{
			saveData( ForgeDirection.EAST );
			return;
		}
		
		super.renderFaceXPos(a, b, c, d, e);
	}
	
	
	@Override
	public void renderFaceXNeg(Block a, double b, double c, double d, IIcon e)
	{
		if ( ! facesToDraw.contains( ForgeDirection.WEST ) )
		{
			saveData( ForgeDirection.WEST );
			return;
		}
		
		super.renderFaceXNeg(a, b, c, d, e);
	}
	
	@Override
	public void renderFaceYNeg(Block a, double b, double c, double d, IIcon e)
	{
		if ( ! facesToDraw.contains( ForgeDirection.DOWN ) )
		{
			saveData( ForgeDirection.DOWN );
			return;
		}
		
		super.renderFaceYNeg(a, b, c, d, e);
	}
	
	@Override
	public void renderFaceYPos(Block a, double b, double c, double d, IIcon e)
	{
		if ( ! facesToDraw.contains( ForgeDirection.UP ) )
		{
			saveData( ForgeDirection.UP );
			return;
		}
		
		super.renderFaceYPos(a, b, c, d, e);
	}
	
	@Override
	public void renderFaceZPos(Block a, double b, double c, double d, IIcon e)
	{
		if ( ! facesToDraw.contains( ForgeDirection.SOUTH ) )
		{
			saveData( ForgeDirection.SOUTH );
			return;
		}
		
		super.renderFaceZPos(a, b, c, d, e);
	}
	
	private void saveData(ForgeDirection side)
	{
		sides[ side.ordinal() ] = new RenderFaceData( this );
	}
	
	public void renderCachedBlock(Block block, int i, int j, int k, IIcon myTexture)
	{
		this.enableAO = Minecraft.isAmbientOcclusionEnabled();
		RenderPointData rpd;
		
		
		rpd = sides[ ForgeDirection.EAST.ordinal() ].getCachedValue( 1.0d - renderMaxY, renderMaxZ );
		colorRedTopLeft 	= rpd.r;
		colorGreenTopLeft	= rpd.g;
		colorBlueTopLeft	= rpd.b;
		brightnessTopLeft	= rpd.brightness;
		
		rpd = sides[ ForgeDirection.EAST.ordinal() ].getCachedValue( 1.0d - renderMinY, renderMaxZ );
		colorRedTopRight	= rpd.r;
		colorGreenTopRight	= rpd.g;
		colorBlueTopRight	= rpd.b;
		brightnessTopRight	= rpd.brightness;
		
		rpd = sides[ ForgeDirection.EAST.ordinal() ].getCachedValue( 1.0d - renderMinY, renderMinZ );
		colorRedBottomRight		= rpd.r;
		colorGreenBottomRight	= rpd.g;
		colorBlueBottomRight	= rpd.b;
		brightnessBottomRight	= rpd.brightness;
		
		rpd = sides[ ForgeDirection.EAST.ordinal() ].getCachedValue( 1.0d - renderMaxY, renderMinZ );
		colorRedBottomLeft		= rpd.r;
		colorGreenBottomLeft	= rpd.g;
		colorBlueBottomLeft		= rpd.b;
		brightnessBottomLeft	= rpd.brightness;
		
		renderFaceXPos(block, i, j, k, myTexture);
		
		
		rpd = sides[ ForgeDirection.WEST.ordinal() ].getCachedValue( renderMaxY, renderMaxZ );
		colorRedTopLeft 	= rpd.r;
		colorGreenTopLeft	= rpd.g;
		colorBlueTopLeft	= rpd.b;
		brightnessTopLeft	= rpd.brightness;
		
		rpd = sides[ ForgeDirection.WEST.ordinal() ].getCachedValue( renderMaxY, renderMinZ );
		colorRedBottomLeft		= rpd.r;
		colorGreenBottomLeft	= rpd.g;
		colorBlueBottomLeft		= rpd.b;
		brightnessBottomLeft	= rpd.brightness;
		
		rpd = sides[ ForgeDirection.WEST.ordinal() ].getCachedValue( renderMinY, renderMinZ );
		colorRedBottomRight		= rpd.r;
		colorGreenBottomRight	= rpd.g;
		colorBlueBottomRight	= rpd.b;
		brightnessBottomRight	= rpd.brightness;
		
		rpd = sides[ ForgeDirection.WEST.ordinal() ].getCachedValue( renderMinY, renderMaxZ );
		colorRedTopRight	= rpd.r;
		colorGreenTopRight	= rpd.g;
		colorBlueTopRight	= rpd.b;
		brightnessTopRight	= rpd.brightness;
		
		renderFaceXNeg(block, i, j, k, myTexture);
		
		
		rpd = sides[ ForgeDirection.SOUTH.ordinal() ].getCachedValue( 1.0d - renderMinX, renderMaxY );
		colorRedTopLeft 	= rpd.r;
		colorGreenTopLeft	= rpd.g;
		colorBlueTopLeft	= rpd.b;
		brightnessTopLeft	= rpd.brightness;
		
		rpd = sides[ ForgeDirection.SOUTH.ordinal() ].getCachedValue( 1.0d - renderMinX, renderMinY );
		colorRedBottomLeft		= rpd.r;
		colorGreenBottomLeft	= rpd.g;
		colorBlueBottomLeft		= rpd.b;
		brightnessBottomLeft	= rpd.brightness;
		
		rpd = sides[ ForgeDirection.SOUTH.ordinal() ].getCachedValue( 1.0d - renderMaxX, renderMinY );
		colorRedBottomRight		= rpd.r;
		colorGreenBottomRight	= rpd.g;
		colorBlueBottomRight	= rpd.b;
		brightnessBottomRight	= rpd.brightness;
		
		rpd = sides[ ForgeDirection.SOUTH.ordinal() ].getCachedValue( 1.0d - renderMaxX, renderMaxY );
		colorRedTopRight	= rpd.r;
		colorGreenTopRight	= rpd.g;
		colorBlueTopRight	= rpd.b;
		brightnessTopRight	= rpd.brightness;
		
		renderFaceZPos(block, i, j, k, myTexture);
		
		
		rpd = sides[ ForgeDirection.NORTH.ordinal() ].getCachedValue( renderMaxY, 1.0d - renderMinX );
		colorRedTopLeft 	= rpd.r;
		colorGreenTopLeft	= rpd.g;
		colorBlueTopLeft	= rpd.b;
		brightnessTopLeft	= rpd.brightness;
		
		rpd = sides[ ForgeDirection.NORTH.ordinal() ].getCachedValue( renderMaxY, 1.0d - renderMaxX );
		colorRedTopRight	= rpd.r;
		colorGreenTopRight	= rpd.g;
		colorBlueTopRight	= rpd.b;
		brightnessTopRight	= rpd.brightness;
		
		rpd = sides[ ForgeDirection.NORTH.ordinal() ].getCachedValue( renderMinY, 1.0d - renderMaxX );
		colorRedBottomRight		= rpd.r;
		colorGreenBottomRight	= rpd.g;
		colorBlueBottomRight	= rpd.b;
		brightnessBottomRight	= rpd.brightness;
		
		rpd = sides[ ForgeDirection.NORTH.ordinal() ].getCachedValue( renderMinY, 1.0d - renderMinX );
		colorRedBottomLeft		= rpd.r;
		colorGreenBottomLeft	= rpd.g;
		colorBlueBottomLeft		= rpd.b;
		brightnessBottomLeft	= rpd.brightness;
		
		renderFaceZNeg(block, i, j, k, myTexture);
		
		
		rpd = sides[ ForgeDirection.UP.ordinal() ].getCachedValue( renderMaxX, renderMaxZ );
		colorRedTopLeft 	= rpd.r;
		colorGreenTopLeft	= rpd.g;
		colorBlueTopLeft	= rpd.b;
		brightnessTopLeft	= rpd.brightness;
		
		rpd = sides[ ForgeDirection.UP.ordinal() ].getCachedValue( renderMaxX, renderMinZ );
		colorRedBottomLeft		= rpd.r;
		colorGreenBottomLeft	= rpd.g;
		colorBlueBottomLeft		= rpd.b;
		brightnessBottomLeft	= rpd.brightness;
		
		rpd = sides[ ForgeDirection.UP.ordinal() ].getCachedValue( renderMinX, renderMinZ );
		colorRedBottomRight		= rpd.r;
		colorGreenBottomRight	= rpd.g;
		colorBlueBottomRight	= rpd.b;
		brightnessBottomRight	= rpd.brightness;
		
		rpd = sides[ ForgeDirection.UP.ordinal() ].getCachedValue( renderMinX, renderMaxZ );
		colorRedTopRight	= rpd.r;
		colorGreenTopRight	= rpd.g;
		colorBlueTopRight	= rpd.b;
		brightnessTopRight	= rpd.brightness;
		
		renderFaceYPos(block, i, j, k, myTexture);
		
		
		rpd = sides[ ForgeDirection.DOWN.ordinal() ].getCachedValue( 1.0d - renderMinX, renderMaxZ );
		colorRedTopLeft 	= rpd.r;
		colorGreenTopLeft	= rpd.g;
		colorBlueTopLeft	= rpd.b;
		brightnessTopLeft	= rpd.brightness;
		
		rpd = sides[ ForgeDirection.DOWN.ordinal() ].getCachedValue( 1.0d - renderMinX, renderMinZ );
		colorRedBottomLeft		= rpd.r;
		colorGreenBottomLeft	= rpd.g;
		colorBlueBottomLeft		= rpd.b;
		brightnessBottomLeft	= rpd.brightness;
		
		rpd = sides[ ForgeDirection.DOWN.ordinal() ].getCachedValue( 1.0d - renderMaxX, renderMinZ );
		colorRedBottomRight		= rpd.r;
		colorGreenBottomRight	= rpd.g;
		colorBlueBottomRight	= rpd.b;
		brightnessBottomRight	= rpd.brightness;
		
		rpd = sides[ ForgeDirection.DOWN.ordinal() ].getCachedValue( 1.0d - renderMaxX, renderMaxZ );
		colorRedTopRight	= rpd.r;
		colorGreenTopRight	= rpd.g;
		colorBlueTopRight	= rpd.b;
		brightnessTopRight	= rpd.brightness;
		
		renderFaceYNeg(block, i, j, k, myTexture);
		
		
		enableAO = false;
	}

}