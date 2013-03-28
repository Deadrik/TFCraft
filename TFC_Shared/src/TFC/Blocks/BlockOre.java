package TFC.Blocks;

import java.util.Random;

import TFC.TFCBlocks;
import TFC.TFCItems;
import TFC.TerraFirmaCraft;
import TFC.Core.Helper;
import TFC.Core.TFC_Textures;
import TFC.Items.ItemChisel;
import TFC.TileEntities.TileEntityPartial;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.crash.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.packet.*;
import net.minecraft.pathfinding.*;
import net.minecraft.potion.*;
import net.minecraft.server.*;
import net.minecraft.stats.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.village.*;
import net.minecraft.world.*;
import net.minecraft.world.biome.*;
import net.minecraft.world.chunk.*;
import net.minecraft.world.gen.feature.*;

public class BlockOre extends BlockTerra
{
	public String[] blockNames = {"Native Copper", "Native Gold", "Native Platinum", "Hematite", "Native Silver", "Cassiterite", "Galena", "Bismuthinite", "Garnierite", 
	        "Malachite", "Magnetite", "Limonite", "Sphalerite", "Tetrahedrite", 
	        "Bituminous Coal", "Lignite"};
	
	public BlockOre(int i, Material material) {
		super(i, material);
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
    public Icon getBlockTextureFromSideAndMetadata(int i, int j) 
    {
        return icons[j];
    }
	
	protected Icon[] icons = new Icon[16];
	
	@Override
	public void registerIcons(IconRegister iconRegisterer)
    {
		for(int i = 0; i < 16; i++)
		{
			icons[i] = iconRegisterer.registerIcon("ores/"+blockNames[i] + " Ore");
		}
    }

	@Override
	public int getRenderType()
	{
		return TFCBlocks.oreRenderId;
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
		return TFCItems.OreChunk.itemID;
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
