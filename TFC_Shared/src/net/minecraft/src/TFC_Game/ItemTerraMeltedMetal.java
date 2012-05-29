package net.minecraft.src.TFC_Game;

import net.minecraft.src.Item;
import net.minecraft.src.forge.ITextureProvider;

public class ItemTerraMeltedMetal extends Item implements ITextureProvider
{
	public ItemTerraMeltedMetal(int i) 
	{
		super(i);
		setMaxDamage(100);
		this.setMaxStackSize(1);
	}

	@Override
	public String getTextureFile()
	{
		return "/bioxx/terrasprites.png";
	}	
}
