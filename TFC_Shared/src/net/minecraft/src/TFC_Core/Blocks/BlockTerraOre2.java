package net.minecraft.src.TFC_Core.Blocks;

import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.MovingObjectPosition;
import net.minecraft.src.StatList;
import net.minecraft.src.TFCItems;
import net.minecraft.src.World;
import net.minecraft.src.mod_TFC_Core;
import net.minecraft.src.TFC_Core.TileEntityPartial;
import net.minecraft.src.TFC_Core.General.Helper;
import net.minecraft.src.TFC_Core.Items.ItemChisel;

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
		    if (!world.isRemote/* && (random.nextInt(4) == 0)*/)
	        {
	            float var6 = 0.7F;
	            double var7 = (double)(world.rand.nextFloat() * var6) + (double)(1.0F - var6) * 0.5D;
	            double var9 = (double)(world.rand.nextFloat() * var6) + (double)(1.0F - var6) * 0.5D;
	            double var11 = (double)(world.rand.nextFloat() * var6) + (double)(1.0F - var6) * 0.5D;
	            EntityItem var13 = new EntityItem(world, (double)i + var7, (double)j + var9, (double)k + var11, itemstack);
	            var13.delayBeforeCanPickup = 10;
	            world.spawnEntityInWorld(var13);
	        }
		    
//		    if(random.nextInt(100) != 0)
//                world.setBlockAndMetadata(i, j, k, blockID, l);
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
    
//    public boolean removeBlockByPlayer(World world, EntityPlayer player, int i, int j, int k) 
//    {
//        if(player != null)
//        {
//            player.addStat(StatList.mineBlockStatArray[blockID], 1);
//            player.addExhaustion(0.075F);
//        }
//
//        MovingObjectPosition objectMouseOver = Helper.getMouseOverObject(player, world);
//        if(objectMouseOver == null) {
//            return false;
//        }       
//        int side = objectMouseOver.sideHit;
//        int sub = objectMouseOver.subHit;
//
//
//        if(true)
//        {
//            
//            ItemChisel.CreateSlab(world, i, j, k, this.blockID, (byte) world.getBlockMetadata(i, j, k), side, mod_TFC_Core.stoneMinedSlabs.blockID);
//            TileEntityPartial te = (TileEntityPartial) world.getBlockTileEntity(i,j,k);
//            int id = te.TypeID;
//            int meta = te.MetaID;
//            ItemChisel.CreateSlab(world, i, j, k, te.TypeID, te.MetaID, side, mod_TFC_Core.stoneMinedSlabs.blockID);
//            te = (TileEntityPartial) world.getBlockTileEntity(i, j, k);
//            Block.blocksList[id].harvestBlock(world, player, i, j, k, meta);
//            if(te != null)
//            {
//                long extraX = (te.extraData) & 0xf;
//                long extraY = (te.extraData >> 4) & 0xf;
//                long extraZ = (te.extraData >> 8) & 0xf;
//                long extraX2 = (te.extraData >> 12) & 0xf;
//                long extraY2 = (te.extraData >> 16) & 0xf;
//                long extraZ2 = (te.extraData >> 20) & 0xf;
//
//                if(extraX+extraY+extraZ+extraX2+extraY2+extraZ2 > 8)
//                    return world.setBlockWithNotify(i, j, k, 0);
//            }
//            else
//                return world.setBlockWithNotify(i, j, k, 0);
//        }
//
//        return false;
//    }
}
