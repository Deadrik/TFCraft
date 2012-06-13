package net.minecraft.src.TFC_Core.Items;

import net.minecraft.src.*;
import net.minecraft.src.forge.ITextureProvider;

public class ItemHammer extends ItemTool
implements ITextureProvider
{
	public ItemHammer(int i, EnumToolMaterial e)
	{
		super(i, 0, e, new Block[] {});
	}

	public String getTextureFile() {
		return "/bioxx/terratools.png";
	}
}