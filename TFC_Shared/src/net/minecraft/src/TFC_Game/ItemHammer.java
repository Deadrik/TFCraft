package net.minecraft.src.TFC_Game;

import net.minecraft.src.*;
import net.minecraft.src.forge.ITextureProvider;

public class ItemHammer extends ItemTool
implements ITextureProvider
{
	public ItemHammer(int i, EnumToolMaterial e)
	{
		super(i, i, e, new Block[] {});
	}

	public String getTextureFile() {
		return "/bioxx/terratools.png";
	}
}