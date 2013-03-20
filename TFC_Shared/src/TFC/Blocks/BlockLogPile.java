package TFC.Blocks;

import java.util.Random;

import TFC.*;
import TFC.TileEntities.*;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.*;
import net.minecraft.client.gui.inventory.*;
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

public class BlockLogPile extends BlockContainer
{
	private Class EntityClass;

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
			if((TileEntityTerraLogPile)world.getBlockTileEntity(i, j, k)!=null)
			{
				TileEntityTerraLogPile te;
				te = (TileEntityTerraLogPile)world.getBlockTileEntity(i, j, k);
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

	public Icon getBlockTextureFromSideAndMetadata(int i, int j)
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
		return new TileEntityTerraLogPile();
	}
}
