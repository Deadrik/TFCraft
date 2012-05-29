package net.minecraft.src.TFC_Core;

import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.ModLoader;
import net.minecraft.src.StatList;
import net.minecraft.src.TFCItems;
import net.minecraft.src.World;
import net.minecraft.src.mod_TFC_Core;
import net.minecraft.src.forge.ITextureProvider;

public class BlockTerraOre3 extends BlockTerra
{

	public BlockTerraOre3(int i, Material material) {
		super(i,160, material);
	}

	public void addCreativeItems(java.util.ArrayList list)
    {
        for(int i = 0; i < 3; i++) {
            list.add(new ItemStack(this,1,i));
        }
    }

    @Override
    public int damageDropped(int j) 
    {
        return j;
    }

    @Override
    public int getBlockTextureFromSideAndMetadata(int i, int j) 
    {
        return blockIndexInTexture + j;
    }

	public int getRenderType()
	{
		return mod_TFC_Core.oreRenderId;
	}

	/*
	 * Mapping from metadata value to damage value
	 */
	@Override
	public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int l)
	{
		entityplayer.addStat(StatList.mineBlockStatArray[blockID], 1);
		entityplayer.addExhaustion(0.025F);
		Random random = new Random();

		ItemStack itemstack = new ItemStack(TFCItems.OreChunk, 1, damageDropped(l+32));

		if (itemstack != null)
		{
			dropBlockAsItem_do(world, i, j, k, itemstack);
		}

	}

	@Override
	public int idDropped(int i, Random random, int j)
	{
		return TFCItems.OreChunk.shiftedIndex;
	}
	
	@Override
    public String getTextureFile()
    {
        return "/bioxx/terraRock.png";
    }
	
	public static String[] blockNames = { "Borax", "Olivine", "LapisLazuli"};
    
    public static String getItemNameDamage(int d) 
    {
        String s = blockNames[d];
        return s;
    }
}
