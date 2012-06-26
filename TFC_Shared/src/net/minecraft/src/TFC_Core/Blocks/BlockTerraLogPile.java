package net.minecraft.src.TFC_Core.Blocks;

import java.util.Random;

import net.minecraft.src.*;
import net.minecraft.src.TFC_Core.TileEntityTerraLogPile;
import net.minecraft.src.forge.ITextureProvider;

public class BlockTerraLogPile extends BlockContainer implements ITextureProvider
{
	private Class EntityClass;

	public BlockTerraLogPile(int i, Class tClass)
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
	public boolean blockActivated(World world, int i, int j, int k, EntityPlayer entityplayer)
	{
		if (mod_TFC_Core.proxy.isRemote())
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
					entityplayer.openGui(mod_TFC_Core.instance, mod_TFC_Core.logPileGuiId, world, i, j, k);
				}
				return true;
			} else {
				return false;
			}

		}
	}

	

	@Override
	public TileEntity getBlockEntity() 
	{
		try
		{
			return (TileEntity) EntityClass.newInstance();

		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
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

	public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int l)
	{		
		Eject(world,i,j,k);
	}

	public void onBlockDestroyedByExplosion(World par1World, int par2, int par3, int par4) {
		Eject(par1World,par2,par3,par4);
	}

	public void onBlockDestroyedByPlayer(World par1World, int par2, int par3, int par4, int par5) {
		Eject(par1World,par2,par3,par4);
	}

	public void onBlockRemoval(World par1World, int par2, int par3, int par4) {Eject(par1World,par2,par3,par4);}
}
