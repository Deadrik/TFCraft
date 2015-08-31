package com.bioxx.tfc.Entities.Mobs;

import net.minecraft.entity.IEntityLivingData;

public class EntityHorseGroupData implements IEntityLivingData
{
    public int horseType;
    public int horseVariant;

    public EntityHorseGroupData(int par1, int par2)
    {
        this.horseType = par1;
        this.horseVariant = par2;
    }
}
