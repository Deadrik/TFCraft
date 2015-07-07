package com.bioxx.tfc.Entities.Mobs;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;

final class EntityHorseBredSelector implements IEntitySelector
{
    /**
     * Return whether the specified entity is applicable to this filter.
     */
    @Override
	public boolean isEntityApplicable(Entity par1Entity)
    {
        return par1Entity instanceof EntityHorseTFC && ((EntityHorseTFC)par1Entity).func_110205_ce();
    }
}
