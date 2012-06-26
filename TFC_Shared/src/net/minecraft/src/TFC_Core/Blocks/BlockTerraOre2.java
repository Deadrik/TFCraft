package net.minecraft.src.TFC_Core.Blocks;

import java.util.Random;

import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.StatList;
import net.minecraft.src.TFCItems;
import net.minecraft.src.World;
import net.minecraft.src.mod_TFC_Core;

public class BlockTerraOre2 extends BlockTerraOre
{

	public BlockTerraOre2(int i, Material material) {
		super(i, material);
		this.blockIndexInTexture = 144;
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
        return j+16;
    }

	@Override
	public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int l)
	{
	    if(entityplayer != null)
        {
            entityplayer.addStat(StatList.mineBlockStatArray[blockID], 1);
            entityplayer.addExhaustion(0.075F);
        }
		Random random = new Random();

		ItemStack itemstack = new ItemStack(TFCItems.OreChunk, 1 , damageDropped(l));

		if(l == 5)
		{
			//Drop diamonds
			itemstack = KimberliteGemSpawn();
		}
		else if(l == 13)
		{
			itemstack = new ItemStack(TFCItems.terraSaltpeterPowder, 1 + random.nextInt(3));
		}                      
		if (itemstack != null)
		{
		    if (!world.isRemote)
	        {
	            float var6 = 0.7F;
	            double var7 = (double)(world.rand.nextFloat() * var6) + (double)(1.0F - var6) * 0.5D;
	            double var9 = (double)(world.rand.nextFloat() * var6) + (double)(1.0F - var6) * 0.5D;
	            double var11 = (double)(world.rand.nextFloat() * var6) + (double)(1.0F - var6) * 0.5D;
	            EntityItem var13 = new EntityItem(world, (double)i + var7, (double)j + var9, (double)k + var11, itemstack);
	            var13.delayBeforeCanPickup = 10;
	            world.spawnEntityInWorld(var13);
	        }
		}

	}

	public ItemStack KimberliteGemSpawn()
	{
		Random random = new Random();
		if(random.nextInt(25) == 0)
		{
			return new ItemStack(TFCItems.terraGemDiamond,1,0);
		}
		if(random.nextInt(50) == 0)
		{
			return new ItemStack(TFCItems.terraGemDiamond,1,1);
		}
		if(random.nextInt(75) == 0)
		{
			return new ItemStack(TFCItems.terraGemDiamond,1,2);
		}
		if(random.nextInt(150) == 0)
		{
			return new ItemStack(TFCItems.terraGemDiamond,1,3);
		}
		if(random.nextInt(300) == 0)
		{
			return new ItemStack(TFCItems.terraGemDiamond,1,4);
		} else {
			return null;
		}
	}
	
	@Override
    public String getTextureFile()
    {
        return "/bioxx/terraRock.png";
    }
	
	public static String[] blockNames = { "Kaolinite", "Gypsum", "Satinspar", "Selenite", "Graphite", "Kimberlite", 
        "Petrified Wood", "Sulfur", "Jet", "Microcline", "Pitchblende", "Cinnabar", "Cryolite", "Saltpeter", "Serpentine", "Sylvite"};
    
    public static String getItemNameDamage(int d) 
    {
        String s = blockNames[d-16];
        return s;
    }
}
