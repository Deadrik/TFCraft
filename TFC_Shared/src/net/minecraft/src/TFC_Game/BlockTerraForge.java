package net.minecraft.src.TFC_Game;

import java.util.Random;

import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.BlockContainer;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.mod_TFC_Core;
import net.minecraft.src.mod_TFC_Game;
import net.minecraft.src.forge.ITextureProvider;

public class BlockTerraForge extends BlockContainer implements ITextureProvider
{
	
	private int meta;
	private int xCoord;
	private int yCoord;

	private int zCoord;

	private Class EntityClass;

	public BlockTerraForge(int i, Class tClass, int tex)
	{
		super(i, Material.fire);
		this.blockIndexInTexture = tex;
		EntityClass = tClass;
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1F, 0.9F, 1F);
	}

	public void addCreativeItems(java.util.ArrayList list)
	{

		//list.add(new ItemStack(this,1,0));
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
		else if(itemid == mod_TFC_Game.terraFireStarter.shiftedIndex)
		{
			if((TileEntityTerraForge)world.getBlockTileEntity(i, j, k) != null)
			{
				TileEntityTerraForge tileentityforge;
				tileentityforge = (TileEntityTerraForge)world.getBlockTileEntity(i, j, k);
				if(tileentityforge.fireTemperature < 210 && tileentityforge.fireItemStacks[7] != null && tileentityforge.isValid)
				{
					tileentityforge.fireTemperature = 300;
					int ss = entityplayer.inventory.getCurrentItem().stackSize;
					int dam = entityplayer.inventory.getCurrentItem().getItemDamage();
					entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, 
							new ItemStack(entityplayer.getCurrentEquippedItem().getItem(),ss,dam));
					world.setBlockMetadata(i, j, k, 2);
				}
			}
			return true;
		}
		else
		{
			if((TileEntityTerraForge)world.getBlockTileEntity(i, j, k)!=null)
			{
				TileEntityTerraForge tileentityforge;
				tileentityforge = (TileEntityTerraForge)world.getBlockTileEntity(i, j, k);
				ItemStack is =entityplayer.getCurrentEquippedItem();

				if(tileentityforge.isValid)
				{
					entityplayer.openGui(mod_TFC_Game.instance, mod_TFC_Game.terraForgeGuiId, world, i, j, k);
					//ModLoader.openGUI(entityplayer, new GuiTerraForge(entityplayer.inventory, tileentityforge));
				}
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

	public String getItemNameIS(ItemStack itemstack) 
	{
		String s = "terraForge";
		return s;
	}

	public int getRenderType()
	{
		return mod_TFC_Core.terraForgeRenderId;
	}

	@Override
	public String getTextureFile() {

		return "/bioxx/terrablocks.png";
	}


	public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int l)
	{		
		if((TileEntityTerraForge)world.getBlockTileEntity(i, j, k)!=null)
		{
			TileEntityTerraForge tileentityanvil;
			tileentityanvil = (TileEntityTerraForge)world.getBlockTileEntity(i, j, k);
			tileentityanvil.ejectContents();
		}	
	}

	public boolean isOpaqueCube()
	{
		return false;
	}


	public void onNeighborBlockChange(World world, int i, int j, int k, int l)
	{
		if(!world.isBlockOpaqueCube(i, j-1, k) || world.getBlockMaterial(i, j-1, k) != Material.rock)
		{
			((TileEntityTerraForge)world.getBlockTileEntity(i, j, k)).ejectContents();
			world.setBlock(i, j, k, 0);
		}
		else if(!world.isBlockOpaqueCube(i+1, j, k) || world.getBlockMaterial(i+1, j, k) != Material.rock)
		{
			((TileEntityTerraForge)world.getBlockTileEntity(i, j, k)).ejectContents();
			world.setBlock(i, j, k, 0);
		}
		else if(!world.isBlockOpaqueCube(i-1, j, k) || world.getBlockMaterial(i-1, j, k) != Material.rock)
		{
			((TileEntityTerraForge)world.getBlockTileEntity(i, j, k)).ejectContents();
			world.setBlock(i, j, k, 0);
		}
		else if(!world.isBlockOpaqueCube(i, j, k+1) || world.getBlockMaterial(i, j, k+1) != Material.rock)
		{
			((TileEntityTerraForge)world.getBlockTileEntity(i, j, k)).ejectContents();
			world.setBlock(i, j, k, 0);
		}
		else if(!world.isBlockOpaqueCube(i, j, k-1) || world.getBlockMaterial(i, j, k-1) != Material.rock)
		{
			((TileEntityTerraForge)world.getBlockTileEntity(i, j, k)).ejectContents();
			world.setBlock(i, j, k, 0);
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
		if(world.getBlockTileEntity(i, j, k) != null)
		{
			((TileEntityTerraForge)world.getBlockTileEntity(i, j, k)).setNumAirBlocks(numAirBlocks);
			((TileEntityTerraForge)world.getBlockTileEntity(i, j, k)).isValid = false;
		}
	}
	public void randomDisplayTick(World world, int i, int j, int k, Random random)
	{
		if (this.blockID == mod_TFC_Game.terraForge.blockID)
		{
			return;
		}
		else
		{
			float f = (float)i + 0.5F;
			float f1 = (float)j + 0.9F + random.nextFloat() * 6F / 16F;
			float f2 = (float)k + 0.5F;
			float f3 = 0.52F;
			float f4 = random.nextFloat() * 0.6F;
			float f5 = random.nextFloat() * -0.6F;
			float f6 = random.nextFloat() * -0.6F;
			world.spawnParticle("smoke", f+f4 - 0.3F, f1,  f2 + f5 + 0.3F, 0.0D, 0.0D, 0.0D);
			world.spawnParticle("flame", f+f4 - 0.3F, f1,  f2 + f5 + 0.3F, 0.0D, 0.0D, 0.0D);
			world.spawnParticle("smoke", f+f5 + 0.3F , f1, f2 + f4 - 0.3F, 0.0D, 0.0D, 0.0D);
			world.spawnParticle("flame", f+f5 + 0.3F , f1, f2 + f4 - 0.3F, 0.0D, 0.0D, 0.0D);
			if (((TileEntityTerraForge)world.getBlockTileEntity(i, j, k)).fireTemperature > 550)
			{
				world.spawnParticle("flame", f+f5 + 0.3F , f1, f2 + f6 + 0.2F, 0.0D, 0.0D, 0.0D);
				world.spawnParticle("flame", f+f4 - 0.3F , f1, f2 + f6 + 0.1F, 0.0D, 0.0D, 0.0D);
			}
		}
	}

	public boolean renderAsNormalBlock()
	{
		return false;
	}

	public void setBlockBoundsForItemRender() 
	{
		setBlockBounds(0.0F, 0.0F, 0.0F, 1F, 0.9F, 1F);
	}


	public int tickRate()
	{
		return 20;
	}
	
	public static void updateFurnaceBlockState(boolean par0, World par1World, int par2, int par3, int par4)
    {
        int var5 = par1World.getBlockMetadata(par2, par3, par4);
        TileEntity var6 = par1World.getBlockTileEntity(par2, par3, par4);

        if (par0)
        {
            par1World.setBlockWithNotify(par2, par3, par4, mod_TFC_Game.terraForgeOn.blockID);
            par1World.markBlockAsNeedsUpdate(par2, par3, par4);
        }
        else
        {
            par1World.setBlockWithNotify(par2, par3, par4, mod_TFC_Game.terraForge.blockID);
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
        return AxisAlignedBB.getBoundingBoxFromPool((double)par2 + this.minX, (double)par3 + this.minY, (double)par4 + this.minZ, (double)par2 + this.maxX, (double)par3 + this.maxY, (double)par4 + this.maxZ);
    }

//	public void updateTick(World world, int i, int j, int k, Random random)
//	{
//		TileEntityTerraForge tileentityfirepit;
//		tileentityfirepit = (TileEntityTerraForge)world.getBlockTileEntity(i, j, k);
//		setLightValue(0.8F);
//		if(tileentityfirepit.fireTemperature < 100)
//		{
//			world.setBlockMetadata(i, j, k, 0);
//			setLightValue(0.0F);
//		}
//
//		if(tileentityfirepit.fireTemperature > 210)
//		{
//			world.setBlockMetadata(i, j, k, 1);
//		}
//	}

}
