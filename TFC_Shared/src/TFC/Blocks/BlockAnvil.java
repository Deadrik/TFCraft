package TFC.Blocks;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import TFC.TFCBlocks;
import TFC.TFCItems;
import TFC.TerraFirmaCraft;
import TFC.Core.AnvilReq;
import TFC.TileEntities.TileEntityTerraAnvil;

public class BlockAnvil extends BlockTerraContainer
{
	private int anvilId = 0;


	private Random random = new Random();
	
	public BlockAnvil(int i)
	{
		super(i, Material.iron);
	}
	
	public BlockAnvil(int i, int offset)
	{
		this(i);
		anvilId = offset;
	}
	
	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float hitX, float hitY, float hitZ)
	{
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

				entityplayer.openGui(TerraFirmaCraft.instance, 21, world, i, j, k);
			}
			return true;
		}
	}
	
	/**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
	@Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
        int meta = par1World.getBlockMetadata(par2, par3, par4);
        int direction = getDirectionFromMetadata(meta);
        TileEntityTerraAnvil te = (TileEntityTerraAnvil)par1World.getBlockTileEntity(par2, par3, par4);

		if(te.AnvilTier != AnvilReq.STONE.Tier)
		{
        if(direction == 0)
            return AxisAlignedBB.getBoundingBox(par2 + 0.2, (double)par3 + 0, (double)par4 + 0, par2 + 0.8, par3 + 0.6, (double)par4 + 1);
        else
            return AxisAlignedBB.getBoundingBox((double)par2 + 0, (double)par3 + 0, par4 + 0.2, (double)par2 + 1, par3 + 0.6, par4 + 0.8);
		}
		else
		{
			return AxisAlignedBB.getBoundingBox((double)par2 + 0, (double)par3 + 0, (double)par4 + 0, (double)par2 + 1, (double)par3 + 1, (double)par4 + 1);
		}
    }
	
	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
        int meta = par1World.getBlockMetadata(par2, par3, par4);
        int direction = getDirectionFromMetadata(meta);
        TileEntityTerraAnvil te = (TileEntityTerraAnvil)par1World.getBlockTileEntity(par2, par3, par4);

		if(te.AnvilTier != AnvilReq.STONE.Tier)
		{
        if(direction == 0)
            return AxisAlignedBB.getBoundingBox(par2 + 0.2, (double)par3 + 0, (double)par4 + 0, par2 + 0.8, par3 + 0.6, (double)par4 + 1);
        else
            return AxisAlignedBB.getBoundingBox((double)par2 + 0, (double)par3 + 0, par4 + 0.2, (double)par2 + 1, par3 + 0.6, par4 + 0.8);
		}
		else
		{
			return AxisAlignedBB.getBoundingBox((double)par2 + 0, (double)par3 + 0, (double)par4 + 0, (double)par2 + 1, (double)par3 + 1, (double)par4 + 1);
		}
    }
    @Override
	public Icon getBlockTextureFromSideAndMetadata(int i, int j)
	{
		int meta = getAnvilTypeFromMeta(j);

		if(i == 0) 
		{
			return textureMapTop[meta];
		} 
		else if(i == 1) 
		{
			return textureMapTop[meta];
		} 
		else 
		{
			return textureMapSide[meta];
		}
	}

	@Override
	public int getRenderType()
	{
		return TFCBlocks.AnvilRenderId;
	}

	@Override
	public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int l)
	{		
		int type = BlockAnvil.getAnvilTypeFromMeta(l);

		if(blockID == TFCBlocks.Anvil.blockID)
		{
		switch (type)
		{
		case 1:
			dropBlockAsItem_do(world, i, j, k, new ItemStack(TFCItems.CopperAnvilItem, 1));
			break;
		case 2:
			dropBlockAsItem_do(world, i, j, k, new ItemStack(TFCItems.BronzeAnvilItem, 1));
			break;
		case 3:
			dropBlockAsItem_do(world, i, j, k, new ItemStack(TFCItems.WroughtIronAnvilItem, 1));
			break;
		case 4:
			dropBlockAsItem_do(world, i, j, k, new ItemStack(TFCItems.SteelAnvilItem, 1));
			break;
		case 5:
			dropBlockAsItem_do(world, i, j, k, new ItemStack(TFCItems.BlackSteelAnvilItem, 1));
			break;
		case 6:
			dropBlockAsItem_do(world, i, j, k, new ItemStack(TFCItems.RedSteelAnvilItem, 1));
			break;
		case 7:
			dropBlockAsItem_do(world, i, j, k, new ItemStack(TFCItems.BlueSteelAnvilItem, 1));
			break;
		default:
			break;
		}
		}
		else if(blockID == TFCBlocks.Anvil2.blockID)
        {
        switch (type)
        {
        case 1:
            dropBlockAsItem_do(world, i, j, k, new ItemStack(TFCItems.BlackBronzeAnvilItem, 1));
            break;
        case 2:
            dropBlockAsItem_do(world, i, j, k, new ItemStack(TFCItems.RoseGoldAnvilItem, 1));
            break;
        default:
            dropBlockAsItem_do(world, i, j, k, new ItemStack(TFCItems.BismuthBronzeAnvilItem, 1));
        }
        }
	}
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}
	@Override
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLiving entityliving, ItemStack is)
	{
		int meta = world.getBlockMetadata(i, j, k);
		int l = MathHelper.floor_double(entityliving.rotationYaw * 4F / 360F + 0.5D) & 3;
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
		
		world.setBlockMetadataWithNotify(i, j, k, byte0, 3);

	}
	@Override
	public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
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
						var12 = new EntityItem(par1World, par2 + var8, par3 + var9, par4 + var10, new ItemStack(var7.itemID, var11, var7.getItemDamage()));
						float var13 = 0.05F;
						var12.motionX = (float)this.random.nextGaussian() * var13;
						var12.motionY = (float)this.random.nextGaussian() * var13 + 0.2F;
						var12.motionZ = (float)this.random.nextGaussian() * var13;

						if (var7.hasTagCompound())
						{
							var12.getEntityItem().setTagCompound((NBTTagCompound)var7.getTagCompound().copy());
						}
					}
				}
			}
		}
		
		

		super.breakBlock(par1World, par2, par3, par4, par5, par6);
	}
	@Override
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

	@Override
	public TileEntity createNewTileEntity(World var1) {
		// TODO Auto-generated method stub
		return new TileEntityTerraAnvil();
	}
	
	Icon[] textureMapTop = new Icon[8];
	Icon[] textureMapSide = new Icon[8];

	@Override
    public void registerIcons(IconRegister registerer)
    {
		for(int i = 1; i < (anvilId == 0 ? 8 : 3); i++)
		{
			textureMapTop[i] = registerer.registerIcon("devices/Anvil_" + (i+anvilId) + "_Top");
			textureMapSide[i] = registerer.registerIcon("devices/Anvil_" + (i+anvilId) + "_Side");
		}
    }
}
