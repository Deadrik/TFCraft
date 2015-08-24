package com.bioxx.tfc;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;

import com.bioxx.tfc.Core.TFC_Core;

public class ServerOverrides
{
	static public boolean canPlayerMove( EntityLivingBase entity )
	{
		double ref = entity.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue();
		if ( ref > 0.001 )
				return true;
		
		// no jumping, no swimming up, just falling
		entity.motionY = -0.15D;
		return false;
	}

	@SuppressWarnings("null")
	public static int isValidSurface(Block b)
	{
		if (TFC_Core.isFence(b) || b != null && b.getRenderType() == 11)
			return 11;
		return b.getRenderType();
	}
}
