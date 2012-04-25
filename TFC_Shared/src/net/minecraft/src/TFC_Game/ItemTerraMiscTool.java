package net.minecraft.src.TFC_Game;

import net.minecraft.src.Item;
import net.minecraft.src.forge.ITextureProvider;

public class ItemTerraMiscTool extends Item implements ITextureProvider
{

	public ItemTerraMiscTool(int i) 
	{
		super(i);
	}

	@Override
	public String getTextureFile()
	{
		return "/bioxx/terratools.png";
	}	
}
