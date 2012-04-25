package net.minecraft.src.TFC_Core;

import net.minecraft.src.*;
import net.minecraft.src.forge.ITextureProvider;

public abstract class BlockTerra2 extends Block implements ITextureProvider
{
	protected BlockTerra2(int par1) 
	{
		super(par1, Material.rock);
	}
	protected BlockTerra2(int par1,int par2, Material material) 
	{
		super(par1,par2, material);
	}

	protected BlockTerra2(int par1, Material material) 
	{
		super(par1, material);
	}

	@Override
	public String getTextureFile()
	{
		return "/bioxx/terrablocks2.png";
	}
}
