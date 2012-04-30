package net.minecraft.src.TFC_Game;

import java.util.Random;

import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.BlockContainer;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.MathHelper;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.mod_TFC_Core;
import net.minecraft.src.mod_TFC_Game;
import net.minecraft.src.forge.ITextureProvider;

public class BlockTerraAnvil extends BlockContainer implements ITextureProvider
{
	private int meta;
	private int xCoord;
	private int yCoord;

	private int zCoord;


	private int anvilId;

	private Class EntityClass;

	private Random random = new Random();
	public BlockTerraAnvil(int i, Class tClass)
	{
		super(i, Material.iron);
		EntityClass = tClass;
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
		else
		{
			if((TileEntityTerraAnvil)world.getBlockTileEntity(i, j, k)!=null)
			{
				TileEntityTerraAnvil tileentityanvil;
				tileentityanvil = (TileEntityTerraAnvil)world.getBlockTileEntity(i, j, k);
				ItemStack is = entityplayer.getCurrentEquippedItem();

				entityplayer.openGui(mod_TFC_Game.instance, mod_TFC_Game.terraAnvilGuiId, world, i, j, k);
			}
			return true;
		}
	}
	
	/**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
        int meta = par1World.getBlockMetadata(par2, par3, par4);
        int direction = getDirectionFromMetadata(meta);
        if(direction == 0)
            return AxisAlignedBB.getBoundingBoxFromPool((double)par2 + 0.2, (double)par3 + 0, (double)par4 + 0, (double)par2 + 0.8, (double)par3 + 0.6, (double)par4 + 1);
        else
            return AxisAlignedBB.getBoundingBoxFromPool((double)par2 + 0, (double)par3 + 0, (double)par4 + 0.2, (double)par2 + 1, (double)par3 + 0.6, (double)par4 + 0.8);
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
		int meta = getAnvilTypeFromMeta(j);
		int offset = meta * 2;

		if(i == 0) {
			return 193 + offset;
		} else if(i == 1) {
			return 193 + offset;
		} else if(i == 2) {
			return 192 + offset;
		} else if(i == 3) {
			return 192 + offset;
		} else if(i == 4) {
			return 192 + offset;
		} else {
			return 192 + offset;
		}
	}

	public String getItemNameIS(ItemStack itemstack) 
	{
		String s = "terraAnvil";
		return s;
	}

	public int getRenderType()
	{
		return mod_TFC_Core.terraAnvilRenderId;
	}

	@Override
	public String getTextureFile() {

		return "/bioxx/terrablocks.png";
	}
	public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int l)
	{		
		int type = BlockTerraAnvil.getAnvilTypeFromMeta(l);

		switch (type)
		{
		case 1:
			dropBlockAsItem_do(world, i, j, k, new ItemStack(mod_TFC_Game.terraCopperAnvilItem, 1));
			break;
		case 2:
			dropBlockAsItem_do(world, i, j, k, new ItemStack(mod_TFC_Game.terraBronzeAnvilItem, 1));
			break;
		case 3:
			dropBlockAsItem_do(world, i, j, k, new ItemStack(mod_TFC_Game.terraWroughtIronAnvilItem, 1));
			break;
		case 4:
			dropBlockAsItem_do(world, i, j, k, new ItemStack(mod_TFC_Game.terraSteelAnvilItem, 1));
			break;
		case 5:
			dropBlockAsItem_do(world, i, j, k, new ItemStack(mod_TFC_Game.terraBlackSteelAnvilItem, 1));
			break;
		case 6:
			dropBlockAsItem_do(world, i, j, k, new ItemStack(mod_TFC_Game.terraRedSteelAnvilItem, 1));
			break;
		case 7:
			dropBlockAsItem_do(world, i, j, k, new ItemStack(mod_TFC_Game.terraBlueSteelAnvilItem, 1));
			break;
		default:
			dropBlockAsItem_do(world, i, j, k, new ItemStack(mod_TFC_Game.terraStoneAnvilItem, 1));
		}
	}

	public boolean isOpaqueCube()
	{
		return false;
	}

	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLiving entityliving)
	{
		meta = world.getBlockMetadata(i, j, k);
		xCoord = i;
		yCoord = j;
		zCoord = k;
		int l = MathHelper.floor_double((double)(entityliving.rotationYaw * 4F / 360F) + 0.5D) & 3;
		byte byte0 = 0;
		if(l == 0)//+z
		{
			byte0 = 8;
		}
		if(l == 1)//-x
		{
			byte0 = 0;
		}
		if(l == 2)//-z
		{
			byte0 = 8;
		}
		if(l == 3)//+x
		{
			byte0 = 0;
		}
		byte0 += meta;
		world.setBlockMetadataWithNotify(i, j, k, byte0);

	}
	public void onBlockRemoval(World par1World, int par2, int par3, int par4)
	{
		TileEntityTerraAnvil var5 = (TileEntityTerraAnvil)par1World.getBlockTileEntity(par2, par3, par4);

		if (var5 != null)
		{
			for (int var6 = 0; var6 < var5.getSizeInventory(); ++var6)
			{
				ItemStack var7 = var5.getStackInSlot(var6);

				if (var7 != null)
				{
					float var8 = this.random.nextFloat() * 0.8F + 0.1F;
					float var9 = this.random.nextFloat() * 0.8F + 0.1F;
					EntityItem var12;

					for (float var10 = this.random.nextFloat() * 0.8F + 0.1F; var7.stackSize > 0; par1World.spawnEntityInWorld(var12))
					{
						int var11 = this.random.nextInt(21) + 10;

						if (var11 > var7.stackSize)
						{
							var11 = var7.stackSize;
						}

						var7.stackSize -= var11;
						var12 = new EntityItem(par1World, (double)((float)par2 + var8), (double)((float)par3 + var9), (double)((float)par4 + var10), new ItemStack(var7.itemID, var11, var7.getItemDamage()));
						float var13 = 0.05F;
						var12.motionX = (double)((float)this.random.nextGaussian() * var13);
						var12.motionY = (double)((float)this.random.nextGaussian() * var13 + 0.2F);
						var12.motionZ = (double)((float)this.random.nextGaussian() * var13);

						if (var7.hasTagCompound())
						{
							var12.item.setTagCompound((NBTTagCompound)var7.getTagCompound().copy());
						}
					}
				}
			}
		}

		super.onBlockRemoval(par1World, par2, par3, par4);
	}
	public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	public static int getAnvilTypeFromMeta(int j)
    {
        int l = 7;
        int k = j & l;
        return k;
    }
    public static int getDirectionFromMetadata(int i)
    {
        int d = i >> 3;

        if (d == 1) {
            return 1;
        } else {
            return 0;
        }
    }
}
