package TFC.Blocks;

import java.util.Random;

import TFC.Core.Helper;
import TFC.Items.ItemChisel;
import TFC.TileEntities.TileEntityPartial;

import net.minecraft.src.Block;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.ModLoader;
import net.minecraft.src.MovingObjectPosition;
import net.minecraft.src.StatList;
import net.minecraft.src.TFCItems;
import net.minecraft.src.World;
import net.minecraft.src.mod_TFC;

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
		   // if(random.nextInt(4) == 0)
		        dropBlockAsItem_do(world, i, j, k, itemstack);
			
//			if(random.nextInt(100) != 0)
//                world.setBlockAndMetadata(i, j, k, blockID, l);
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
