package TFC.Blocks.Terrain;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import TFC.Reference;
import TFC.TFCItems;
import TFC.API.Constant.Global;

public class BlockOre2 extends BlockOre
{
	public static String[] blockNames = Global.ORE_MINERAL;
	
	public BlockOre2(int i, Material material) 
	{
		super(i, material);
	}
	
	@Override
	public void registerIcons(IconRegister iconRegisterer)
    {
		for(int i = 0; i < blockNames.length; i++)
		{
			icons[i] = iconRegisterer.registerIcon(Reference.ModID + ":" + "ores/"+blockNames[i] + " Ore");
		}
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
        return j + Global.ORE_METAL.length;
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
		    itemstack = new ItemStack(TFCItems.Powder, 1 + random.nextInt(3), 4);
		} 
		
		if (itemstack != null)
		{
		    if (!world.isRemote/* && (random.nextInt(4) == 0)*/)
	        {
	            float var6 = 0.7F;
	            double var7 = world.rand.nextFloat() * var6 + (1.0F - var6) * 0.5D;
	            double var9 = world.rand.nextFloat() * var6 + (1.0F - var6) * 0.5D;
	            double var11 = world.rand.nextFloat() * var6 + (1.0F - var6) * 0.5D;
	            EntityItem var13 = new EntityItem(world, i + var7, j + var9, k + var11, itemstack);
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
			return new ItemStack(TFCItems.GemDiamond,1,0);
		}
		if(random.nextInt(50) == 0)
		{
			return new ItemStack(TFCItems.GemDiamond,1,1);
		}
		if(random.nextInt(75) == 0)
		{
			return new ItemStack(TFCItems.GemDiamond,1,2);
		}
		if(random.nextInt(150) == 0)
		{
			return new ItemStack(TFCItems.GemDiamond,1,3);
		}
		if(random.nextInt(300) == 0)
		{
			return new ItemStack(TFCItems.GemDiamond,1,4);
		} else {
			return null;
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
		itemstack  = new ItemStack(TFCItems.OreChunk, 1, meta + 16);
		
		if(meta == 5) {
			itemstack = KimberliteGemSpawn();
		} else if (meta == 13) {
			itemstack = new ItemStack(TFCItems.Powder, 1 + random.nextInt(3), 4);
		}
		if (itemstack != null) {
			dropBlockAsItem_do(par1World, par2, par3, par4, itemstack);
		}
		onBlockDestroyedByExplosion(par1World, par2, par3, par4, par5Explosion);
	}
}
