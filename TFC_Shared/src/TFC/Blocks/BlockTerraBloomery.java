package TFC.Blocks;

import java.util.Random;

import TFC.TileEntities.TileEntityTerraBloomery;
import net.minecraft.src.BlockContainer;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.MathHelper;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.mod_TFC_Core;
import net.minecraft.src.forge.ITextureProvider;

public class BlockTerraBloomery extends BlockContainer implements ITextureProvider
{
    private Class EntityClass;
    private int meta;
    private int xCoord;
    private int yCoord;
    private int zCoord;
    public static final int headBlockToFootBlockMap[][] = {
        {
            0, 1
        }, {
            -1, 0
        }, {
            0, -1
        }, {
            1, 0
        }
    };
    
    public BlockTerraBloomery(int i, Class tClass, int tex)
    {
        super(i, Material.rock);
        this.blockIndexInTexture = tex;
        EntityClass = tClass;
    }
    
	public static void DoValidCheck(World world, int i, int j, int k, int meta)
	{
		if(meta == 0)
		{
			if(!world.isBlockOpaqueCube(i-1, j, k) || !world.isBlockOpaqueCube(i+1, j, k))
			{
				((TileEntityTerraBloomery)world.getBlockTileEntity(i, j, k)).ejectContents();
				world.setBlock(i, j, k, 0);
				world.markBlockNeedsUpdate(i, j, k);
			}
		}
		else if(meta == 1)
		{
			if(!world.isBlockOpaqueCube(i, j, k-1) || !world.isBlockOpaqueCube(i, j, k+1))
			{
				((TileEntityTerraBloomery)world.getBlockTileEntity(i, j, k)).ejectContents();
				world.setBlock(i, j, k, 0);
				world.markBlockNeedsUpdate(i, j, k);
			}
		}
		else if(meta == 2)
		{
			if(!world.isBlockOpaqueCube(i-1, j, k) || !world.isBlockOpaqueCube(i+1, j, k))
			{
				((TileEntityTerraBloomery)world.getBlockTileEntity(i, j, k)).ejectContents();
				world.setBlock(i, j, k, 0);
				world.markBlockNeedsUpdate(i, j, k);
			}
		}
		else if(meta == 3)
		{
			if(!world.isBlockOpaqueCube(i, j, k-1) || !world.isBlockOpaqueCube(i, j, k+1))
			{
				((TileEntityTerraBloomery)world.getBlockTileEntity(i, j, k)).ejectContents();
				world.setBlock(i, j, k, 0);
				world.markBlockNeedsUpdate(i, j, k);
			}
		}
	}
	public static void updateFurnaceBlockState(boolean par0, World par1World, int par2, int par3, int par4)
	{
		int var5 = par1World.getBlockMetadata(par2, par3, par4);
		TileEntity var6 = par1World.getBlockTileEntity(par2, par3, par4);

		if (par0)
		{
			par1World.setBlockWithNotify(par2, par3, par4, mod_TFC_Core.terraBloomeryOn.blockID);
			par1World.markBlockNeedsUpdate(par2, par3, par4);
		}
		else
		{
			par1World.setBlockWithNotify(par2, par3, par4, mod_TFC_Core.terraBloomery.blockID);
			par1World.markBlockNeedsUpdate(par2, par3, par4);
		}

		par1World.setBlockMetadataWithNotify(par2, par3, par4, var5);

		if (var6 != null)
		{
			var6.validate();
			par1World.setBlockTileEntity(par2, par3, par4, var6);
		}
	}
	public void addCreativeItems(java.util.ArrayList list)
	{
		if(this.blockID != mod_TFC_Core.terraBloomeryOn.blockID) {
			list.add(new ItemStack(this,1,0));
		}
	}
	public boolean blockActivated(World world, int i, int j, int k, EntityPlayer entityplayer)
	{
		meta = world.getBlockMetadata(i, j, k);
		xCoord = i;
		yCoord = j;
		zCoord = k;
		ItemStack equippedItem = entityplayer.getCurrentEquippedItem();
		int itemid;

		if((TileEntityTerraBloomery)world.getBlockTileEntity(i, j, k)!=null)
		{
			TileEntityTerraBloomery tileentityforge;
			tileentityforge = (TileEntityTerraBloomery)world.getBlockTileEntity(i, j, k);
			ItemStack is =entityplayer.getCurrentEquippedItem();

			if(tileentityforge.isValid)
			{
				entityplayer.openGui(mod_TFC_Core.instance, mod_TFC_Core.terraBloomeryGuiId, world, i, j, k);
			}
		}
		return true;
	}

	public boolean canPlaceBlockAt(World world, int i, int j, int k)
	{
		return world.isBlockOpaqueCube(i, j-1, k) && world.isBlockOpaqueCube(i, j+1, k);
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
		if(i == 0 || i == 1) {
			return 64;
		}

		if(j == 0 && i == 2	) {
			return blockIndexInTexture;
		}
		if(j == 1 && i == 5) {
			return blockIndexInTexture;
		}
		if(j == 2 && i == 3) {
			return blockIndexInTexture;
		}
		if(j == 3 && i == 4) {
			return blockIndexInTexture;
		}
		return 64;
	}

	@Override
	public String getTextureFile() {

		return "/bioxx/terrablocks.png";
	}

	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLiving entityliving)
	{
		xCoord = i;
		yCoord = j;
		zCoord = k;
		int l = MathHelper.floor_double((double)(entityliving.rotationYaw * 4F / 360F) + 0.5D) & 3;
		byte byte0 = 0;
		if(l == 0)//+z
		{
			byte0 = 0;
		}
		if(l == 1)//-x
		{
			byte0 = 1;
		}
		if(l == 2)//-z
		{
			byte0 = 2;
		}
		if(l == 3)//+x
		{
			byte0 = 3;
		}

		world.setBlockMetadataWithNotify(i, j, k, byte0);

	}

	public void onBlockRemoval(World world, int par2, int par3, int par4) 
	{
		meta = world.getBlockMetadata(par2, par3, par4);
		int[] dir = headBlockToFootBlockMap[meta & 3];
		if(world.getBlockId(par2+dir[0], par3, par4+dir[1]) == mod_TFC_Core.terraMolten.blockID)
		{
			world.setBlock(par2+dir[0], par3, par4+dir[1], 0);
			world.markBlockNeedsUpdate(par2+dir[0], par3, par4+dir[1]);
		}
		if(world.getBlockId(par2+dir[0], par3+1, par4+dir[1]) == mod_TFC_Core.terraMolten.blockID)
		{
			world.setBlock(par2+dir[0], par3+1, par4+dir[1], 0);
			world.markBlockNeedsUpdate(par2+dir[0], par3+1, par4+dir[1]);
		}
		if(world.getBlockId(par2+dir[0], par3+2, par4+dir[1]) == mod_TFC_Core.terraMolten.blockID)
		{
			world.setBlock(par2+dir[0], par3+2, par4+dir[1], 0);
			world.markBlockNeedsUpdate(par2+dir[0], par3+2, par4+dir[1]);
		}
		if(world.getBlockId(par2+dir[0], par3+3, par4+dir[1]) == mod_TFC_Core.terraMolten.blockID)
		{
			world.setBlock(par2+dir[0], par3+3, par4+dir[1], 0);
			world.markBlockNeedsUpdate(par2+dir[0], par3+3, par4+dir[1]);
		}
		if(world.getBlockId(par2+dir[0], par3+4, par4+dir[1]) == mod_TFC_Core.terraMolten.blockID)
		{
			world.setBlock(par2+dir[0], par3+4, par4+dir[1], 0);
			world.markBlockNeedsUpdate(par2+dir[0], par3+4, par4+dir[1]);
		}
	}

	public void onNeighborBlockChange(World world, int i, int j, int k, int l)
	{
		meta = world.getBlockMetadata(i, j, k) & 3;
		int[] dir = headBlockToFootBlockMap[meta];


		if(!world.isBlockOpaqueCube(i, j-1, k) || !world.isBlockOpaqueCube(i, j+1, k))
		{
			((TileEntityTerraBloomery)world.getBlockTileEntity(i, j, k)).ejectContents();
			world.setBlock(i, j, k, 0);
			world.markBlockNeedsUpdate(i,j,k);
		}
	}

	public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int l)
    {       
        //dropBlockAsItem_do(world, i, j, k, new ItemStack(mod_TFC_Core.terraBloomery, 1));
    }
}
