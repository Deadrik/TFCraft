package TFC.Blocks.Flora;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import TFC.TFCBlocks;
import TFC.Blocks.Vanilla.BlockCustomIce;
import TFC.TileEntities.TESeaWeed;
import TFC.WorldGen.TFCProvider;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockFrozenSeaGrass extends BlockCustomIce implements ITileEntityProvider
{
	public BlockFrozenSeaGrass()
	{
		super();
	}

	/**
	 * Called when the player destroys a block with an item that can harvest it. (i, j, k) are the coordinates of the
	 * block and l is the block's subtype/damage.
	 */
	@Override
	public void harvestBlock(World par1World, EntityPlayer par2EntityPlayer, int par3, int par4, int par5, int par6)
	{
		super.harvestBlock(par1World, par2EntityPlayer, par3, par4, par5, par6);
		Material var7 = par1World.getBlock(par3, par4 - 1, par5).getMaterial();

		if (var7.blocksMovement() || var7.isLiquid())
			par1World.setBlock(par3, par4, par5, getBlockMelt(par1World,par3,par4,par5,true), 0, 2);
	}

	@Override
	public void onBlockAdded(World world,int i,int j,int k){
		super.onBlockAdded(world, i, j, k);
		TESeaWeed te = (TESeaWeed)(world.getTileEntity(i,j,k));
		if(te != null)
			te.setType(world.getBlockMetadata(i,j,k));
	}

	@Override
	public int getRenderType()
	{
		return TFCBlocks.seaWeedRenderId;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int par1, int par2)
	{
		return Blocks.ice.getIcon(par1,par2);
	}

	@Override
	protected Block getBlockMelt(World world, int i, int j, int k, boolean moving){
		int meta = world.getBlockMetadata(i,j,k);
		switch(meta){
		case 0: return moving ? TFCBlocks.SeaGrassStill : TFCBlocks.SeaGrassStill;
		case 1: return moving ? TFCBlocks.SeaGrassStill : TFCBlocks.SeaGrassStill;
		case 2: return moving ? TFCBlocks.SeaGrassStill : TFCBlocks.SeaGrassStill;
		default: return TFCBlocks.SeaGrassStill;
		}
	}

	/**
	 * Ticks the block if it's been scheduled
	 */
	@Override
	public void updateTick(World world, int i, int j, int k, Random rand)
	{
		Block block = world.getBlock(i, j, k);
		if((world.provider) instanceof TFCProvider && !world.isRemote && block == this)
		{
			if (!((TFCProvider)(world.provider)).canBlockFreezeTFC(i, j, k, false))
			{
				TESeaWeed te;
				int type = -1;
				int id;
				if (world.getBlock(i, j+1, k) == Blocks.snow)
				{
					int meta = world.getBlockMetadata(i, j+1, k);
					if (meta > 0)
						world.setBlockMetadataWithNotify(i, j+1, k, meta-1, 2);
					else
						world.setBlockToAir(i, j+1, k);
				} else {
					this.dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k), 0);
					int meta = world.getBlockMetadata(i,j,k);
					te = (TESeaWeed)(world.getTileEntity(i,j,k));
					if(te!=null)
						type = te.getType();
					world.setBlock(i, j, k, getBlockMelt(world,i,j,k,false), 0, 2);
					te = (TESeaWeed)(world.getTileEntity(i,j,k));
					if(te != null){
						te.setType(type);
						//redundancy, if this.type is set correctly, setType(meta) will do nothing
						te.setType(meta);
					}
				}
				this.dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k), 0);

				if(j > 143){
					int meta = world.getBlockMetadata(i,j,k);
					te = (TESeaWeed)(world.getTileEntity(i,j,k));
					if(te!=null)
						type = te.getType();
					world.setBlock(i, j, k, getBlockMelt(world,i,j,k,true), 0, 2);
					te = (TESeaWeed)(world.getTileEntity(i,j,k));
					if(te != null){
						te.setType(type);
						te.setType(meta);
					}
				} else {
					int meta = world.getBlockMetadata(i,j,k);
					te = (TESeaWeed)(world.getTileEntity(i,j,k));
					if(te!=null)
						type = te.getType();
					world.setBlock(i, j, k, getBlockMelt(world,i,j,k,false), 0, 2);
					te = (TESeaWeed)(world.getTileEntity(i,j,k));
					if(te != null){
						te.setType(type);
						te.setType(meta);	
					}
				}
				world.scheduleBlockUpdate(i, j, k, block, this.tickRate(world));
			}
		}
	}

	@Override
	public TileEntity createNewTileEntity(World world, int var2)
	{
		return new TESeaWeed();
	}
}
