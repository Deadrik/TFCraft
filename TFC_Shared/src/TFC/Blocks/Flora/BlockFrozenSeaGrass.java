package TFC.Blocks.Flora;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import TFC.TFCBlocks;
import TFC.Blocks.Vanilla.BlockCustomIce;
import TFC.TileEntities.TESeaWeed;
import net.minecraft.block.Block;
import net.minecraft.block.BlockIce;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class BlockFrozenSeaGrass extends BlockCustomIce implements ITileEntityProvider
{
	public BlockFrozenSeaGrass(int par1)
	{
		super(par1);
	}

	/**
	 * Called when the player destroys a block with an item that can harvest it. (i, j, k) are the coordinates of the
	 * block and l is the block's subtype/damage.
	 */
	@Override
	public void harvestBlock(World par1World, EntityPlayer par2EntityPlayer, int par3, int par4, int par5, int par6)
	{
		super.harvestBlock(par1World, par2EntityPlayer, par3, par4, par5, par6);
		Material var7 = par1World.getBlockMaterial(par3, par4 - 1, par5);

		if (var7.blocksMovement() || var7.isLiquid())
		{
			par1World.setBlock(par3, par4, par5, getBlockMeltId(par1World,par3,par4,par5,true), 0, 2);
		}
	}
	
	@Override
	public void onBlockAdded(World world,int i,int j,int k){
		super.onBlockAdded(world, i, j, k);
		TESeaWeed te = (TESeaWeed)(world.getBlockTileEntity(i,j,k));
		if(te != null){
			te.setType(world.getBlockMetadata(i,j,k));
		}
	}
	
	@Override
	public int getRenderType()
	{
		return TFCBlocks.seaWeedRenderId;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public Icon getIcon(int par1, int par2)
	{
		return Block.blocksList[Block.ice.blockID].getIcon(par1,par2);
	}

	public int getBlockMeltId(World world, int i, int j, int k, boolean moving){
		int meta = world.getBlockMetadata(i,j,k);
		switch(meta){
		case 0: return moving? TFCBlocks.SeaGrassStill.blockID : TFCBlocks.SeaGrassStill.blockID;
		case 1: return moving? TFCBlocks.SeaGrassStill.blockID : TFCBlocks.SeaGrassStill.blockID;
		case 2: return moving? TFCBlocks.SeaGrassStill.blockID : TFCBlocks.SeaGrassStill.blockID;
		default: return 0;
		}
	}

	/**
	 * Ticks the block if it's been scheduled
	 */
	@Override
	public void updateTick(World world, int i, int j, int k, Random rand)
	{
		if (!world.canBlockFreeze(i, j, k, false))
		{
			TESeaWeed te;
			int type = -1;
			int id;
			if (world.getBlockId(i, j+1, k) == Block.snow.blockID)
			{
				int meta = world.getBlockMetadata(i, j+1, k);
				if (meta > 0) {
					world.setBlockMetadataWithNotify(i, j+1, k, meta-1, 2);
				} else {
					world.setBlockToAir(i, j+1, k);
				}
			} else {
				this.dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k), 0);
				int meta = world.getBlockMetadata(i,j,k);
				te = (TESeaWeed)(world.getBlockTileEntity(i,j,k));
				if(te!=null){
					type = te.getType();
				}
				id =  getBlockMeltId(world,i,j,k,false);
				world.setBlock(i, j, k,id, 0, 2);
				te = (TESeaWeed)(world.getBlockTileEntity(i,j,k));
				if(te != null){
					te.setType(type);
					//redundancy, if this.type is set correctly, setType(meta) will do nothing
					te.setType(meta);
				}
			}
			this.dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k), 0);
			if(j > 143){
				int meta = world.getBlockMetadata(i,j,k);
				te = (TESeaWeed)(world.getBlockTileEntity(i,j,k));
				if(te!=null){
					type = te.getType();
				}
				id =  getBlockMeltId(world,i,j,k,true);
				world.setBlock(i, j, k,id, 0, 2);
				te = (TESeaWeed)(world.getBlockTileEntity(i,j,k));
				if(te != null){
					te.setType(type);
					te.setType(meta);
				}
			} else {
				int meta = world.getBlockMetadata(i,j,k);
				te = (TESeaWeed)(world.getBlockTileEntity(i,j,k));
				if(te!=null){
					type = te.getType();
				}
				id = getBlockMeltId(world,i,j,k,false);
				world.setBlock(i, j, k, id, 0, 2);
				te = (TESeaWeed)(world.getBlockTileEntity(i,j,k));
				if(te != null){
					te.setType(type);
					te.setType(meta);	
				}
			}
			world.scheduleBlockUpdate(i, j, k, id, this.tickRate(world));
		}
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		// TODO Auto-generated method stub
		return new TESeaWeed();
	}
}
