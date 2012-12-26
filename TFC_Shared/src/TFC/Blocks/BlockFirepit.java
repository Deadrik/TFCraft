package TFC.Blocks;

import java.util.Random;

import TFC.*;
import TFC.Items.ItemLogs;
import TFC.TileEntities.TileEntityTerraFirepit;
import TFC.TileEntities.TileEntityTerraLogPile;
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

public class BlockFirepit extends BlockTerraContainer
{

	public BlockFirepit(int i, int tex)
	{
		super(i, Material.ground);
		this.blockIndexInTexture = tex;
		this.setBlockBounds(0, 0, 0, 1, 0.1f, 1);
	}

	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float hitX, float hitY, float hitZ)
	{
		int meta = world.getBlockMetadata(i, j, k);
		int xCoord = i;
		int yCoord = j;
		int zCoord = k;
		ItemStack equippedItem = entityplayer.getCurrentEquippedItem();
		int itemid;
		if(equippedItem != null)
		{
			itemid = entityplayer.getCurrentEquippedItem().itemID;
		} else {
			itemid = 0;
		}

		if(world.isRemote)
		{
			return true;
		} 
		else if(itemid == TFCItems.FireStarter.shiftedIndex)
		{
			if((TileEntityTerraFirepit)world.getBlockTileEntity(i, j, k) != null)
			{
				TileEntityTerraFirepit tileentityfirepit;
				tileentityfirepit = (TileEntityTerraFirepit)world.getBlockTileEntity(i, j, k);
				if(tileentityfirepit.fireTemperature < 210 && tileentityfirepit.fireItemStacks[5] != null)
				{
					tileentityfirepit.fireTemperature = 300;
					int ss = entityplayer.inventory.getCurrentItem().stackSize;
					int dam = entityplayer.inventory.getCurrentItem().getItemDamage();
					entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, 
							new ItemStack(entityplayer.getCurrentEquippedItem().getItem(),ss,dam));
					world.setBlockMetadata(i, j, k, 1);
					world.markBlockForUpdate(i, j, k);
				}
			}
			return true;
		}
		else
		{
			if((TileEntityTerraFirepit)world.getBlockTileEntity(i, j, k)!=null)
			{
				TileEntityTerraFirepit tileentityfirepit;
				tileentityfirepit = (TileEntityTerraFirepit)world.getBlockTileEntity(i, j, k);
				ItemStack is =entityplayer.getCurrentEquippedItem();

				entityplayer.openGui(TerraFirmaCraft.instance, 20, world, i, j, k);
			}
			return true;
		}
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(int i, int j)
	{
		if(j > 0)
			return blockIndexInTexture+1;
		
		return blockIndexInTexture;
	}
	
	@Override
	public void onEntityCollidedWithBlock(World world, int i, int j, int k, Entity entity)
    {
        if(entity instanceof EntityItem && ((EntityItem)entity).func_92014_d().getItem() instanceof ItemLogs)
        {
            if((TileEntityTerraFirepit)world.getBlockTileEntity(i, j, k)!=null)
            {
                ItemStack is = ((EntityItem)entity).func_92014_d();
                TileEntityTerraFirepit te;
                te = (TileEntityTerraFirepit)world.getBlockTileEntity(i, j, k);
                if(te.fireItemStacks[0] == null)
                {
                    if(is.stackSize == 1)
                    {
                        te.fireItemStacks[0] = is;
                        entity.setDead();
                    }
                }
            }   
        }
    }

	@Override
	public int getRenderType()
	{
		return TFCBlocks.FirepitRenderId;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override
	public int quantityDropped(Random par1Random)
    {
        return 0;
    }

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, int l)
	{
		if(!world.isBlockOpaqueCube(i, j-1, k))
		{
			((TileEntityTerraFirepit)world.getBlockTileEntity(i, j, k)).ejectContents();
			world.setBlock(i, j, k, 0);
			return;
		}
	}

	@Override
	public void randomDisplayTick(World world, int i, int j, int k, Random random)
	{
		int meta = world.getBlockMetadata(i, j, k);
		if (meta >= 1)
		{
		    if (random.nextInt(24) == 0)
	        {
		        world.playSoundEffect(i,j,k, "fire.fire", 0.4F + (random.nextFloat()/2), 0.7F + random.nextFloat());
	        }
		    
			float f = (float)i + 0.5F;
			float f1 = (float)j + 0.1F + random.nextFloat() * 6F / 16F;
			float f2 = (float)k + 0.5F;
			float f3 = 0.52F;
			float f4 = random.nextFloat() * 0.6F;
			float f5 = random.nextFloat() * -0.6F;
			float f6 = random.nextFloat() * -0.6F;
			world.spawnParticle("smoke", f+f4 - 0.3F, f1,  f2 + f5 + 0.3F, 0.0D, 0.0D, 0.0D);
			world.spawnParticle("flame", f+f4 - 0.3F, f1,  f2 + f5 + 0.3F, 0.0D, 0.0D, 0.0D);
			world.spawnParticle("smoke", f+f5 + 0.3F , f1, f2 + f4 - 0.3F, 0.0D, 0.0D, 0.0D);
			world.spawnParticle("flame", f+f5 + 0.3F , f1, f2 + f4 - 0.3F, 0.0D, 0.0D, 0.0D);
			if (((TileEntityTerraFirepit)world.getBlockTileEntity(i, j, k)).fireTemperature > 550)
			{
				world.spawnParticle("flame", f+f5 + 0.3F , f1, f2 + f6 + 0.2F, 0.0D, 0.0D, 0.0D);
				world.spawnParticle("flame", f+f4 - 0.3F , f1, f2 + f6 + 0.1F, 0.0D, 0.0D, 0.0D);
			}
			if(world.getBlockTileEntity(i, j, k) != null)
			{
				if(((TileEntityTerraFirepit)world.getBlockTileEntity(i, j, k)).charcoalCounter != 0)
				{
					world.spawnParticle("smoke", f+f4 - 0.3F, f1+2.5,  f2 + f5 + 0.3F, 0.0D, 0.0D, 0.0D);
					world.spawnParticle("smoke", f+f4 - 0.1F, f1+2.5,  f2 + f5 + 0.1F, 0.0D, 0.0D, 0.0D);
				}
			}
		}
	}
	
	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z) 
    {
		int meta = world.getBlockMetadata(x, y, z);
		if(meta == 0)
			return 0;
		else if(meta == 1)
			return 10;
		else
			return 15;
    }
	
	/**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
	@Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
        return null;
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
    public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
    	Eject(par1World,par2,par3,par4);
    }

    //public void onBlockRemoval(World par1World, int par2, int par3, int par4) {Eject(par1World,par2,par3,par4);}
    
    public void Eject(World par1World, int par2, int par3, int par4)
    {
        if((TileEntityTerraFirepit)par1World.getBlockTileEntity(par2, par3, par4)!=null)
        {
            TileEntityTerraFirepit tileentityanvil;
            tileentityanvil = (TileEntityTerraFirepit)par1World.getBlockTileEntity(par2, par3, par4);
            tileentityanvil.ejectContents();
            par1World.removeBlockTileEntity(par2, par3, par4);
        }
    }
    
    @Override
    public boolean getBlocksMovement(IBlockAccess par1IBlockAccess, int i, int j, int k)
    {
        return true;
    }

	@Override
	public TileEntity createNewTileEntity(World var1) 
	{
		return new TileEntityTerraFirepit();
	}
}
