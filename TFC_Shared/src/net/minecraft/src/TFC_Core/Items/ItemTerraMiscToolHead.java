package net.minecraft.src.TFC_Core.Items;

import net.minecraft.src.Item;
import net.minecraft.src.forge.ITextureProvider;

public class ItemTerraMiscToolHead extends ItemTerra implements ITextureProvider
{

	public ItemTerraMiscToolHead(int i) 
	{
		super(i);
		this.setMaxDamage(100);
	}

	@Override
	public String getTextureFile()
	{
		return "/bioxx/terratoolheads.png";
	}	
}
