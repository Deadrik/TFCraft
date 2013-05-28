package TFC.Blocks.Terrain;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.Icon;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import TFC.TFCBlocks;
import TFC.TFCItems;
import TFC.API.Constant.Global;
import TFC.Blocks.BlockTerra;

public class BlockOre extends BlockTerra
{
	public String[] blockNames = Global.ORE_METAL;
	
	public BlockOre(int i, Material material) {
		super(i, material);
	}

	@Override
	public void addCreativeItems(java.util.ArrayList list)
	{
		for(int i = 0; i < blockNames.length; i++) {
			list.add(new ItemStack(this,1,i));
		}
	}

	@Override
	public int damageDropped(int j) 
	{
		return j;
	}

	@Override
    public Icon getIcon(int i, int j) 
    {
        return icons[j];
    }
	
	protected Icon[] icons = new Icon[blockNames.length];
	
	@Override
	public void registerIcons(IconRegister iconRegisterer)
    {
		for(int i = 0; i < blockNames.length; i++)
		{
			icons[i] = iconRegisterer.registerIcon("ores/"+ blockNames[i] + " Ore");
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

	@Override
	public boolean canDropFromExplosion(Explosion par1Explosion) {
		return false;
	}
	
	@Override
	public void onBlockDestroyedByExplosion(World par1World, int par2, int par3, int par4, Explosion par5Explosion) {
		par1World.setBlockToAir(par2, par3, par4);
	}

	@Override
	public void onBlockExploded(World par1World, int par2, int par3, int par4, Explosion par5Explosion) {
		Random random = new Random();
		ItemStack itemstack;
		int meta = par1World.getBlockMetadata(par2, par3, par4);
		
		if(meta == 14 || meta == 15) {
			itemstack  = new ItemStack(Item.coal,1+random.nextInt(2));
		} else {
			itemstack  = new ItemStack(TFCItems.OreChunk, 1, meta);
		}
		if (itemstack != null) {
			dropBlockAsItem_do(par1World, par2, par3, par4, itemstack);
		}
		onBlockDestroyedByExplosion(par1World, par2, par3, par4, par5Explosion);
	}

}
