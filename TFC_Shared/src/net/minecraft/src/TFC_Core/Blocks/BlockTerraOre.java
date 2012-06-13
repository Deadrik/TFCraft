package net.minecraft.src.TFC_Core.Blocks;

import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.ModLoader;
import net.minecraft.src.StatList;
import net.minecraft.src.TFCItems;
import net.minecraft.src.World;
import net.minecraft.src.mod_TFC_Core;
import net.minecraft.src.mod_TFC_Core;
import net.minecraft.src.forge.ITextureProvider;

public class BlockTerraOre extends BlockTerra
{
	public BlockTerraOre(int i, Material material) {
		super(i,128, material);
		this.blockIndexInTexture = 128;
	}

	public void addCreativeItems(java.util.ArrayList list)
	{
		for(int i = 0; i <16; i++) {
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
	public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int l)
	{
	    if(entityplayer != null)
	    {
	        entityplayer.addStat(StatList.mineBlockStatArray[blockID], 1);
	        entityplayer.addExhaustion(0.025F);
	    }
		Random random = new Random();
		ItemStack itemstack;
		if(l == 14 || l == 15) 
		{
		    itemstack  = new ItemStack(Item.coal,1+random.nextInt(2));
			
		} 
		else 
		{
		    itemstack  = new ItemStack(TFCItems.OreChunk, 1, damageDropped(l));
		}

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
	
	public static String[] blockNames = {"Native Copper", "Native Gold", "Native Platinum", "Hematite", "Native Silver", "Cassiterite", "Galena", "Bismuthinite", "Garnierite", 
        "Malachite", "Magnetite", "Limonite", "Sphalerite", "Tetrahedrite", 
        "Bituminous Coal", "Lignite"};
    
    public static String getItemNameDamage(int d) 
    {
        String s = blockNames[d];
        return s;
    }
}
