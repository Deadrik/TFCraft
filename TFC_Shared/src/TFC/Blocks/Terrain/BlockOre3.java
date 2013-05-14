package TFC.Blocks.Terrain;

import java.util.Random;

import TFC.TFCItems;
import TFC.TerraFirmaCraft;
import TFC.Core.Helper;
import TFC.Core.TFC_Textures;
import TFC.Items.Tools.ItemChisel;
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
public class BlockOre3 extends BlockOre
{
	public static String[] blockNames = { "Borax", "Olivine", "Lapis Lazuli"};
	
	public BlockOre3(int i, Material material) {
		super(i, material);
	}

	public void addCreativeItems(java.util.ArrayList list)
    {
        for(int i = 0; i < 3; i++) {
            list.add(new ItemStack(this,1,i));
        }
    }
	
	@Override
	public void registerIcons(IconRegister iconRegisterer)
    {
		for(int i = 0; i < 3; i++)
		{
			icons[i] = iconRegisterer.registerIcon("ores/"+blockNames[i] + " Ore");
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
//                    return world.setBlock(i, j, k, 0);
//            }
//            else
//                return world.setBlock(i, j, k, 0);
//        }
//
//        return false;
//    }
	
	@Override
	public void onBlockExploded(World par1World, int par2, int par3, int par4, Explosion par5Explosion) {
		Random random = new Random();
		ItemStack itemstack;
		int meta = par1World.getBlockMetadata(par2, par3, par4);
		itemstack  = new ItemStack(TFCItems.OreChunk, 1, meta + 32);
		
		if (itemstack != null) {
			dropBlockAsItem_do(par1World, par2, par3, par4, itemstack);
		}
		onBlockDestroyedByExplosion(par1World, par2, par3, par4, par5Explosion);
	}
}
