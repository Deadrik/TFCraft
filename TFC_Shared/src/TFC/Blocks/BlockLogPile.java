package TFC.Blocks;

import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import TFC.TFCItems;
import TFC.TerraFirmaCraft;
import TFC.TileEntities.TileEntityLogPile;

public class BlockLogPile extends BlockContainer
{
	Icon[] icons = new Icon[3];

	public BlockLogPile(int i)
	{
		super(i, Material.wood);
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
			if((TileEntityLogPile)world.getBlockTileEntity(i, j, k)!=null)
			{
				TileEntityLogPile te;
				te = (TileEntityLogPile)world.getBlockTileEntity(i, j, k);
				ItemStack is = entityplayer.getCurrentEquippedItem();

				if(is != null && is.getItem().itemID == TFCItems.Logs.itemID)
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

	@Override
	public Icon getBlockTextureFromSideAndMetadata(int i, int j)
	{
		if(j == 0 || j == 2)//+z
		{
			if(i == 0) {
				return icons[1];
			} else if(i == 1) {
				return icons[1];
			} else if(i == 2) {
				return icons[2];
			} else if(i == 3) {
				return icons[2];
			} else if(i == 4) {
				return icons[0];
			} else if(i == 5) {
				return icons[0];
			}
		}
		else if(j == 1 || j == 3)//-x
		{
			if(i == 0) {
				return icons[0];
			} else if(i == 1) {
				return icons[0];
			} else if(i == 2) {
				return icons[0];
			} else if(i == 3) {
				return icons[0];
			} else if(i == 4) {
				return icons[2];
			} else if(i == 5) {
				return icons[2];
			}
		}

		return icons[0];

	}
	
	@Override
	public void registerIcons(IconRegister iconRegisterer)
    {
		icons[0] = iconRegisterer.registerIcon("devices/Log Pile Side 0");
		icons[1] = iconRegisterer.registerIcon("devices/Log Pile Side 1");
		icons[2] = iconRegisterer.registerIcon("devices/Log Pile End");
    }
	
	public void Eject(World par1World, int par2, int par3, int par4)
    {
        if(!par1World.isRemote && (TileEntityLogPile)par1World.getBlockTileEntity(par2, par3, par4)!=null)
        {
            TileEntityLogPile tileentityanvil;
            tileentityanvil = (TileEntityLogPile)par1World.getBlockTileEntity(par2, par3, par4);
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
	public void onBlockDestroyedByExplosion(World par1World, int par2, int par3, int par4, Explosion ex) {
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
		return new TileEntityLogPile();
	}
}
