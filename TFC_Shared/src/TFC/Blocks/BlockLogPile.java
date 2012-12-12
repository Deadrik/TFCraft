package TFC.Blocks;

import java.util.Random;

import TFC.*;
import TFC.TileEntities.*;

import net.minecraft.src.BlockContainer;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public class BlockLogPile extends BlockContainer
{
	private Class EntityClass;

	public BlockLogPile(int i, Class tClass)
	{
		super(i, Material.wood);
		blockIndexInTexture = 92;
		EntityClass = tClass;
	}
	
	public static int getDirectionFromMetadata(int i)
    {
        return i & 3;
    }

	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int par6, float par7, float par8, float par9)
	{
		if (world.isRemote)
		{
			return true;
		}
		else
		{
			if((TileEntityTerraLogPile)world.getBlockTileEntity(i, j, k)!=null)
			{
				TileEntityTerraLogPile te;
				te = (TileEntityTerraLogPile)world.getBlockTileEntity(i, j, k);
				ItemStack is = entityplayer.getCurrentEquippedItem();

				if(is != null && is.getItem().shiftedIndex == TFCItems.Logs.shiftedIndex)
				{
					return false;
				}
				else
				{
					entityplayer.openGui(TerraFirmaCraft.instance, 0, world, i, j, k);
				}
				return true;
			} else {
				return false;
			}

		}
	}

	public int getBlockTextureFromSideAndMetadata(int i, int j)
	{
		if(j == 0 || j == 2)//+z
		{
			if(i == 0) {
				return 93;
			} else if(i == 1) {
				return 93;
			} else if(i == 2) {
				return 94;
			} else if(i == 3) {
				return 94;
			} else if(i == 4) {
				return 92;
			} else if(i == 5) {
				return 92;
			}
		}
		else if(j == 1 || j == 3)//-x
		{
			if(i == 0) {
				return 92;
			} else if(i == 1) {
				return 92;
			} else if(i == 2) {
				return 92;
			} else if(i == 3) {
				return 92;
			} else if(i == 4) {
				return 94;
			} else if(i == 5) {
				return 94;
			}
		}

		return 92;

	}

	@Override
	public String getTextureFile() 
	{
		return "/bioxx/terrablocks.png";
	}
	
	public void Eject(World par1World, int par2, int par3, int par4)
    {
        if((TileEntityTerraLogPile)par1World.getBlockTileEntity(par2, par3, par4)!=null)
        {
            TileEntityTerraLogPile tileentityanvil;
            tileentityanvil = (TileEntityTerraLogPile)par1World.getBlockTileEntity(par2, par3, par4);
            tileentityanvil.ejectContents();
            par1World.removeBlockTileEntity(par2, par3, par4);
        }
    }
	
	@Override
    public int idDropped(int i, Random random, int j)
    {
        return 0;
    }
	@Override
	public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int l)
	{		
		Eject(world,i,j,k);
	}
	@Override
	public void onBlockDestroyedByExplosion(World par1World, int par2, int par3, int par4) {
		Eject(par1World,par2,par3,par4);
	}
	@Override
	public void onBlockDestroyedByPlayer(World par1World, int par2, int par3, int par4, int par5) {
		Eject(par1World,par2,par3,par4);
	}

	@Override
	public boolean removeBlockByPlayer(World par1World, EntityPlayer player, int par2, int par3, int par4) 
	{
		Eject(par1World,par2,par3,par4);
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World var1) {
		// TODO Auto-generated method stub
		return new TileEntityTerraLogPile();
	}
}
