package TFC.Blocks;

import java.util.Random;

import TFC.Core.Helper;
import TFC.Core.TFCItems;
import TFC.Items.ItemChisel;
import TFC.TileEntities.TileEntityPartial;

import net.minecraft.src.Block;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.ModLoader;
import net.minecraft.src.MovingObjectPosition;
import net.minecraft.src.StatList;
import net.minecraft.src.TFCBlocks;
import net.minecraft.src.World;
import net.minecraft.src.TerraFirmaCraft;

public class BlockOre extends BlockTerra
{
	public BlockOre(int i, Material material) {
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
		return TFCBlocks.oreRenderId;
	}

	/*
	 * Mapping from metadata value to damage value
	 */
	public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int l)
	{
	    if(entityplayer != null)
	    {
	        entityplayer.addStat(StatList.mineBlockStatArray[blockID], 1);
	        entityplayer.addExhaustion(0.075F);
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
		    //if(random.nextInt(4) == 0)
		        dropBlockAsItem_do(world, i, j, k, itemstack);
			
//			if(random.nextInt(100) != 0)
//			    world.setBlockAndMetadata(i, j, k, blockID, l);
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
    
    public static Item getDroppedItem(int meta)
    {
        if(meta == 14 || meta == 15) 
        {
            return Item.coal;
        } 
        else 
        {
            return TFCItems.SmallOreChunk;
        }
    }
}
