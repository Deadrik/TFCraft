package TFC.Blocks;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import TFC.Reference;
import TFC.TFCItems;
import TFC.TerraFirmaCraft;
import TFC.Items.Tools.ItemFirestarter;
import TFC.Items.Tools.ItemFlintSteel;
import TFC.TileEntities.TileEntityLogPile;

public class BlockLogPile extends BlockTerraContainer
{
	Icon[] icons = new Icon[3];

	public BlockLogPile(int i)
	{
		super(i, Material.wood);
		this.setBurnProperties(blockID, 5, 30);
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
		else if(entityplayer.inventory.getCurrentItem() == null || (!(entityplayer.inventory.getCurrentItem().getItem() instanceof ItemFirestarter) && 
				!(entityplayer.inventory.getCurrentItem().getItem() instanceof ItemFlintSteel)))
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
		else return false;
	}

	@Override
	public Icon getIcon(int i, int j)
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
		icons[0] = iconRegisterer.registerIcon(Reference.ModID + ":" + "devices/Log Pile Side 0");
		icons[1] = iconRegisterer.registerIcon(Reference.ModID + ":" + "devices/Log Pile Side 1");
		icons[2] = iconRegisterer.registerIcon(Reference.ModID + ":" + "devices/Log Pile End");
    }
	
	public void Eject(World par1World, int par2, int par3, int par4)
    {
        if(!par1World.isRemote && (TileEntityLogPile)par1World.getBlockTileEntity(par2, par3, par4)!=null)
        {
            TileEntityLogPile tileentitylogpile;
            tileentitylogpile = (TileEntityLogPile)par1World.getBlockTileEntity(par2, par3, par4);
            tileentitylogpile.ejectContents();
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
		return par1World.setBlockToAir(par2,par3,par4);
	}

	@Override
	public TileEntity createNewTileEntity(World var1) 
	{
		return new TileEntityLogPile();
	}
	
	@Override
	public void onNeighborBlockChange(World par1World, int x, int y, int z, int blockId)
	{	
		if(!par1World.isRemote)
		{
			TileEntityLogPile teLogPile = (TileEntityLogPile)par1World.getBlockTileEntity(x, y, z);
			if(teLogPile != null)
				teLogPile.neighborChanged();
		}
	}
}
