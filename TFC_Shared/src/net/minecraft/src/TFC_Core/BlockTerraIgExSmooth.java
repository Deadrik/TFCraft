package net.minecraft.src.TFC_Core;

import java.util.Random;

import net.minecraft.src.BiomeGenBase;
import net.minecraft.src.Block;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.ModLoader;
import net.minecraft.src.World;
import net.minecraft.src.mod_TFC_Core;
import net.minecraft.src.forge.ITextureProvider;

public class BlockTerraIgExSmooth extends Block implements ITextureProvider
{
	public BlockTerraIgExSmooth(int i, int tex) 
	{
		super(i, Material.rock);
		this.blockIndexInTexture = tex;
	}


	public void addCreativeItems(java.util.ArrayList list)
	{
		for(int i = 13; i < 17; i++) {
			list.add(new ItemStack(this,1,i));
		}
	}

	/*
	 * Mapping from metadata value to damage value
	 */
	@Override
	protected int damageDropped(int i) {
		switch (i) {
		case 0:
			return 16;
		default:
			return i;
		}
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(int i, int j) 
	{
		switch (j) {
		case 0:
			return blockIndexInTexture+16;
		default:
			return blockIndexInTexture+j;
		}
	}
	@Override
	public String getTextureFile()
	{
		return "/bioxx/terrablocks2.png";
	}
}
