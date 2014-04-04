package TFC.Blocks;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import TFC.Reference;
import TFC.TFCBlocks;
import TFC.TFCItems;
import TFC.TileEntities.TileEntityBloom;

public class BlockBloom extends BlockTerraContainer
{
	public BlockBloom()
	{
		super(Material.iron);
		//this.setCreativeTab(CreativeTabs.tabDecorations);
		//this.setLightValue(1F);
	}

	@Override
	public boolean isOpaqueCube()
	{
		return true;
	}

	@Override
	public int damageDropped(int j) 
	{
		return j;
	}

	@Override
	public void registerBlockIcons(IIconRegister iconRegisterer)
	{
		this.blockIcon = iconRegisterer.registerIcon(Reference.ModID + ":" + "devices/Iron Bloom");
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2)
	{
		return new TileEntityBloom();
	}

	@Override
	public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int l)
	{
		//Eject(world,i,j,k);
	}

	@Override
	public void onBlockDestroyedByExplosion(World par1World, int par2, int par3, int par4, Explosion par5Explosion) {
		Eject(par1World,par2,par3,par4);
	}

	@Override
	public void onBlockDestroyedByPlayer(World par1World, int par2, int par3, int par4, int par5) {
		Eject(par1World,par2,par3,par4);
	}

	@Override
	public boolean removedByPlayer(World world, EntityPlayer player, int i, int j, int k)
	{
		if(!world.isRemote)
			Eject(world,i,j,k);

		return super.removedByPlayer(world, player, i, j, k);
	}

	//public void onBlockRemoval(World par1World, int par2, int par3, int par4) {Eject(par1World,par2,par3,par4);}

	public void Eject(World world, int i, int j, int k)
	{
		TileEntityBloom te = (TileEntityBloom)world.getTileEntity(i, j, k);
		if(te != null)
		{
			int[] b = getBloomery(world, i, j, k);
			te = (TileEntityBloom)world.getTileEntity(i, j, k);
			dropBlockAsItem(world, i+b[0], j, k+b[1], new ItemStack(TFCItems.RawBloom, 1, te.size));
			world.removeTileEntity(i, j, k);
		}
	}

	public int[] getBloomery(World world, int i, int j, int k)
	{
		if(world.getBlock(i+1, j, k) == TFCBlocks.EarlyBloomery)
			return new int[]{1,0};
		if(world.getBlock(i-1, j, k) == TFCBlocks.EarlyBloomery)
			return new int[]{-1,0};
		if(world.getBlock(i, j, k+1) == TFCBlocks.EarlyBloomery)
			return new int[]{0,1};
		if(world.getBlock(i, j, k-1) == TFCBlocks.EarlyBloomery)
			return new int[]{0,-1};

		return new int[]{0,0};
	}
}
