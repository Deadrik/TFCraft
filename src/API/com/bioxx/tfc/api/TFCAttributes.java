package com.bioxx.tfc.api;

import java.util.UUID;

import net.minecraft.entity.ai.attributes.AttributeModifier;

public class TFCAttributes 
{
	public static final UUID overburdenedUUID = UUID.fromString("772A6B8D-DA3E-4C1C-8813-96EA6097278D");
	public static final AttributeModifier overburdened = new AttributeModifier(overburdenedUUID, "Overburdened speed penalty", -1.0D, 2).setSaved(false);
	public static final UUID thirstyUUID = UUID.fromString("772A6B8D-DA3E-4C1C-9999-96EA6097278D");
	public static final AttributeModifier thirsty = new AttributeModifier(thirstyUUID, "Thirsty speed penalty", -0.3D, 2).setSaved(false);
}
