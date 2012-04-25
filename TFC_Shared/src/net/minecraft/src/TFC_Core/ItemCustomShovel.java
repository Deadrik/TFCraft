package net.minecraft.src.TFC_Core;

import net.minecraft.src.*;
import net.minecraft.src.forge.ITextureProvider;

public class ItemCustomShovel extends ItemSpade
implements ITextureProvider
{
	public ItemCustomShovel(int i, EnumToolMaterial e)
	{
		super(i, e);
	}

	public String getTextureFile() {
		return "/bioxx/terratools.png";
	}
}