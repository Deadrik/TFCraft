package TFC.Blocks;

import java.util.Random;

import TFC.Items.ItemTerraLogs;
import TFC.TileEntities.TileEntityTerraFirepit;
import TFC.TileEntities.TileEntityTerraLogPile;

import net.minecraft.src.*;
import net.minecraft.src.forge.ITextureProvider;

public class BlockTerraFirepit extends BlockContainer implements ITextureProvider
{
	
	private int meta;
	private int xCoord;
	private int yCoord;

	private int zCoord;

	private Class EntityClass;

	public BlockTerraFirepit(int i, Class tClass, int tex)
	{
		super(i, Material.ground);
		EntityClass = tClass;
		this.blockIndexInTexture = tex;
		this.setBlockBounds(0, 0, 0, 1, 0.1f, 1);
	}

	public boolean blockActivated(World world, int i, int j, int k, EntityPlayer entityplayer)
	{
		meta = world.getBlockMetadata(i, j, k);
		xCoord = i;
		yCoord = j;
		zCoord = k;
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
		else if(itemid == TFCItems.terraFireStarter.shiftedIndex)
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
					world.setBlockMetadata(i, j, k, 2);
					world.markBlockAsNeedsUpdate(i, j, k);
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

				entityplayer.openGui(mod_TFC_Core.instance, mod_TFC_Core.terraFirepitGuiId, world, i, j, k);
				//ModLoader.openGUI(entityplayer, new GuiTerraFirepit(entityplayer.inventory, tileentityfirepit));
			}
			return true;
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
		return blockIndexInTexture;
	}

	public boolean getIsFireLit(int i)
	{
		return (i & 1) != 0;
	}
	
	public void onEntityCollidedWithBlock(World world, int i, int j, int k, Entity entity)
    {
        if(entity instanceof EntityItem && ((EntityItem)entity).item.getItem() instanceof ItemTerraLogs)
        {
            if((TileEntityTerraFirepit)world.getBlockTileEntity(i, j, k)!=null)
            {
                ItemStack is = ((EntityItem)entity).item;
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

	public String getItemNameIS(ItemStack itemstack) 
	{
		String s = "terraFirepit";
		return s;
	}

	public int getRenderType()
	{
		return mod_TFC_Core.terraFirepitRenderId;
	}

	@Override
	public String getTextureFile() {

		return "/bioxx/terrablocks.png";
	}

	public boolean isOpaqueCube()
	{
		return false;
	}

	public void onNeighborBlockChange(World world, int i, int j, int k, int l)
	{
		if(!world.isBlockOpaqueCube(i, j-1, k))
		{
			((TileEntityTerraFirepit)world.getBlockTileEntity(i, j, k)).ejectContents();
			world.setBlock(i, j, k, 0);
			return;
		}
		int numAirBlocks = 0;
		for (int x = -1; x < 2; x++)
		{
			for (int y = 0; y < 2; y++)
			{
				for (int z = -1; z < 2; z++)
				{
					if(world.getBlockId(xCoord+x, yCoord+y, zCoord+z) == 0) {
						numAirBlocks++;
					}
				}
			}
		}
		if(world.getBlockTileEntity(i, j, k) != null) {
			((TileEntityTerraFirepit)world.getBlockTileEntity(i, j, k)).setNumAirBlocks(numAirBlocks);
		}
	}


	public void randomDisplayTick(World world, int i, int j, int k, Random random)
	{
		if (this.blockID == mod_TFC_Core.terraFirepit.blockID)
		{
			return;
		}
		else
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
					world.spawnParticle("smoke", f+f4 - 0.3F-1, f1+2.5,  f2 + f5 + 0.3F+1, 0.0D, 0.0D, 0.0D);
					world.spawnParticle("smoke", f+f4 - 0.3F-2, f1+1,  f2 + f5 + 0.3F, 0.0D, 0.0D, 0.0D);
					world.spawnParticle("smoke", f+f4 - 0.3F+2, f1+1.5,  f2 + f5 + 0.3F+2, 0.0D, 0.0D, 0.0D);
					world.spawnParticle("smoke", f+f4 - 0.3F+1, f1+1.5,  f2 + f5 + 0.3F-2, 0.0D, 0.0D, 0.0D);
				}
			}
		}
	}
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	private void setBurnRate(int i, int j, int k)
	{
		Block.setBurnProperties(i, j, k);
	}
	
	public static void updateFurnaceBlockState(boolean par0, World par1World, int par2, int par3, int par4)
    {
        int var5 = par1World.getBlockMetadata(par2, par3, par4);
        
        TileEntity var6 = par1World.getBlockTileEntity(par2, par3, par4);

        if (par0)
        {
            par1World.setBlockWithNotify(par2, par3, par4, mod_TFC_Core.terraFirepitOn.blockID);
            par1World.markBlockAsNeedsUpdate(par2, par3, par4);
        }
        else
        {
            par1World.setBlockWithNotify(par2, par3, par4, mod_TFC_Core.terraFirepit.blockID);
            par1World.markBlockAsNeedsUpdate(par2, par3, par4);
        }

        par1World.setBlockMetadataWithNotify(par2, par3, par4, var5);

        if (var6 != null)
        {
            var6.validate();
            par1World.setBlockTileEntity(par2, par3, par4, var6);
        }
    }
	
	/**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
        return null;
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
}
