package TFC.Blocks.Terrain;

import java.util.List;
import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import TFC.Reference;
import TFC.TFCItems;
import TFC.API.Constant.Global;

public class BlockOre3 extends BlockOre
{
	public static String[] blockNames = Global.ORE_MINERAL2;

	public BlockOre3(Material material)
	{
		super(material);
	}

	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list)
	{
		for(int i = 0; i < blockNames.length; i++)
			list.add(new ItemStack(this,1,i));
	}

	@Override
	public void registerBlockIcons(IIconRegister iconRegisterer)
	{
		for(int i = 0; i < blockNames.length; i++)
			icons[i] = iconRegisterer.registerIcon(Reference.ModID + ":" + "ores/"+blockNames[i] + " Ore");
	}

	@Override
	public int damageDropped(int j) 
	{
		return j + Global.ORE_METAL.length+Global.ORE_MINERAL.length;
	}

	@Override
	public boolean removedByPlayer(World world, EntityPlayer player, int i, int j, int k)
	{
		if(!world.isRemote)
		{
			int meta = world.getBlockMetadata(i, j, k);
			if(player != null)
			{
				player.addStat(StatList.mineBlockStatArray[getIdFromBlock(this)], 1);
				player.addExhaustion(0.075F);
			}
			Random random = new Random();
			ItemStack itemstack = new ItemStack(TFCItems.OreChunk, 1, damageDropped(meta));
			if (itemstack != null)
				dropBlockAsItem(world, i, j, k, itemstack);
		}
		return world.setBlockToAir(i, j, k);
	}

	/*
	 * Mapping from metadata value to damage value
	 */
	@Override
	public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int l)
	{
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
	//            TileEntityPartial te = (TileEntityPartial) world.getTileEntity(i,j,k);
	//            int id = te.TypeID;
	//            int meta = te.MetaID;
	//            ItemChisel.CreateSlab(world, i, j, k, te.TypeID, te.MetaID, side, mod_TFC_Core.stoneMinedSlabs.blockID);
	//            te = (TileEntityPartial) world.getTileEntity(i, j, k);
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

		if (itemstack != null)
			dropBlockAsItem(par1World, par2, par3, par4, itemstack);

		onBlockDestroyedByExplosion(par1World, par2, par3, par4, par5Explosion);
	}
}
