package net.minecraft.src.TFC_Core;

import net.minecraft.src.Item;
import net.minecraft.src.forge.ITextureProvider;

public class ItemTerra extends Item  implements ITextureProvider
{
	String texture;

	public ItemTerra(int id) 
	{
		super(id);
	}
	public ItemTerra(int id, String tex) 
	{
		super(id);
		texture = tex;
	}

	@Override
	public String getTextureFile()
	{
		return texture;
	}

	public ItemTerra setTexturePath(String t)
	{
		texture = t;
		return this;
	}

}
