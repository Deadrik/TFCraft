package net.minecraft.src.TFC_Core.Blocks;

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

public class BlockTerraOre3 extends BlockTerraOre
{

	public BlockTerraOre3(int i, Material material) {
		super(i, material);
		this.blockIndexInTexture = 160;
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
        return j+32;
    }

	/*
	 * Mapping from metadata value to damage value
	 */
	@Override
	public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int l)
	{
	    if(entityplayer != null)
        {
            entityplayer.addStat(StatList.mineBlockStatArray[blockID], 1);
            entityplayer.addExhaustion(0.075F);
        }
		Random random = new Random();

		ItemStack itemstack = new ItemStack(TFCItems.OreChunk, 1, damageDropped(l));

		if (itemstack != null)
		{
			dropBlockAsItem_do(world, i, j, k, itemstack);
		}

	}
	
	@Override
    public String getTextureFile()
    {
        return "/bioxx/terraRock.png";
    }
	
	public static String[] blockNames = { "Borax", "Olivine", "LapisLazuli"};
    
    public static String getItemNameDamage(int d) 
    {
        String s = blockNames[d-32];
        return s;
    }
}
