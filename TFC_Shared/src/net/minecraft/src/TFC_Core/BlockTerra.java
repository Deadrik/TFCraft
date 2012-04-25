package net.minecraft.src.TFC_Core;

import net.minecraft.src.*;
import net.minecraft.src.forge.ITextureProvider;

public abstract class BlockTerra extends Block implements ITextureProvider
{

	protected BlockTerra(int par1) 
	{
		super(par1, Material.rock);
	}
	protected BlockTerra(int par1,int par2, Material material) 
	{
		super(par1,par2, material);
	}

	protected BlockTerra(int par1, Material material) 
	{
		super(par1, material);
	}


	@Override
	public String getTextureFile()
	{
		return "/bioxx/terrablocks.png";
	}


	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLiving entityliving) 
	{
		//TODO: Debug Message should go here if debug is toggled on

		//mc.ingameGUI.addChatMessage("Meta="+(new StringBuilder()).append(getBlockName()).append(":").append(metadata).toString());  
	}
}
