package net.minecraft.src.TFC_Core.Items;

import net.minecraft.src.*;
import net.minecraft.src.ItemPickaxe;
import net.minecraft.src.forge.ITextureProvider;

public class ItemCustomPickaxe extends ItemPickaxe
implements ITextureProvider
{
	public ItemCustomPickaxe(int i, EnumToolMaterial e)
	{
		super(i, e);
	}

	public String getTextureFile() {
		return "/bioxx/terratools.png";
	}
}