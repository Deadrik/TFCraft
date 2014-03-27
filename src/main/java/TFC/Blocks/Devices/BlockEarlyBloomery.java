package TFC.Blocks.Devices;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import TFC.Reference;
import TFC.TFCBlocks;
import TFC.TFCItems;
import TFC.Blocks.BlockTerraContainer;
import TFC.TileEntities.TileEntityEarlyBloomery;

public class BlockEarlyBloomery extends BlockTerraContainer
{
	IIcon textureSide;
	IIcon textureOn;
	IIcon textureOff;

	public static final int headBlockToFootBlockMap[][] = { {0, 1}, {-1, 0}, {0, -1}, {1, 0} };

	public BlockEarlyBloomery()
	{
		super(Material.rock);
		this.setCreativeTab(CreativeTabs.tabRedstone);
	}

	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z) 
	{
		int meta = world.getBlockMetadata(x, y, z) & 4;
		if(meta == 0)
			return 0;
		else
			return 15;
	}

	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int par6, float par7, float par8, float par9)
	{
		if(!canBlockStay(world,i,j,k))
		{
			world.setBlockToAir(i, j, k);
			world.spawnEntityInWorld(new EntityItem(world,i,j,k, new ItemStack(this, 1)));
		}
		else if((TileEntityEarlyBloomery)world.getTileEntity(i, j, k)!=null)
		{
			TileEntityEarlyBloomery te = (TileEntityEarlyBloomery)world.getTileEntity(i, j, k);
			ItemStack is = entityplayer.getCurrentEquippedItem();

			if(te.isValid && is != null && (is.getItem() == TFCItems.FireStarter || is.getItem() == TFCItems.FlintSteel))
				if(te.canLight())
					entityplayer.getCurrentEquippedItem().damageItem(1,entityplayer);
		}
		return true;
	}

	@Override
	public boolean canBlockStay(World world, int i, int j, int k)
	{
		int meta = world.getBlockMetadata(i, j, k) & 3;
		int[] dir = headBlockToFootBlockMap[meta];

		if(world.getBlock(i, j-1, k).isOpaqueCube() && world.getBlock(i, j+1, k).isOpaqueCube())
		{
			int centerX = i + dir[0];
			int centerY = j;
			int centerZ = k + dir[1];
			if((world.getBlock(centerX+1, centerY, centerZ).getMaterial() == Material.rock || world.getBlock(centerX+1, centerY, centerZ).getMaterial() == Material.iron || (centerX== i && centerZ == k)) 
				&& (world.getBlock(centerX+1, centerY, centerZ) != this || (centerX+1 == i && centerZ == k)))
			{
				if((world.getBlock(centerX-1, centerY, centerZ).getMaterial() == Material.rock || world.getBlock(centerX-1, centerY, centerZ).getMaterial() == Material.iron || (centerX== i && centerZ == k)) 
					&& (world.getBlock(centerX-1, centerY, centerZ) != this || (centerX-1 == i && centerZ == k)))
				{
					if((world.getBlock(centerX, centerY, centerZ+1).getMaterial() == Material.rock || world.getBlock(centerX, centerY, centerZ+1).getMaterial() == Material.iron || (centerX== i && centerZ == k)) 
						&& (world.getBlock(centerX, centerY, centerZ+1) != this || (centerX == i && centerZ+1 == k)))
					{
						if((world.getBlock(centerX, centerY, centerZ-1).getMaterial() == Material.rock || world.getBlock(centerX, centerY, centerZ-1).getMaterial() == Material.iron || (centerX== i && centerZ == k)) 
							&& (world.getBlock(centerX, centerY, centerZ-1) != this || (centerX == i && centerZ-1 == k)))
						{
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	@Override
	public boolean canPlaceBlockAt(World world, int i, int j, int k)
	{
		return  ((world.getBlock(i, j+1, k).getMaterial() == Material.rock || world.getBlock(i, j+1, k).getMaterial() == Material.iron) && world.getBlock(i, j+1, k) != this) &&
				((world.getBlock(i, j-1, k).getMaterial() == Material.rock || world.getBlock(i, j-1, k).getMaterial() == Material.iron) && world.getBlock(i, j-1, k) != this);
	}

	@Override
	public IIcon getIcon(int i, int j)
	{
		int lit = (j & 4) == 4 ? 1 : 0;
		j = j & 3;

		if(j == 0 && i == 2)
		{
			if(lit == 1)
				return textureOn;
			return textureOff;
		}
		else if(j == 1 && i == 5)
		{
			if(lit == 1)
				return textureOn;
			return textureOff;
		}
		else if(j == 2 && i == 3)
		{
			if(lit == 1)
				return textureOn;
			return textureOff;
		}
		else if(j == 3 && i == 4)
		{
			if(lit == 1)
				return textureOn;
			return textureOff;
		}
		return textureSide;
	}

	@Override
	public void registerBlockIcons(IIconRegister iconRegisterer)
	{
		textureSide = iconRegisterer.registerIcon(Reference.ModID + ":" + "devices/Bloomery Side");
		textureOn = iconRegisterer.registerIcon(Reference.ModID + ":" + "devices/Bloomery On");
		textureOff = iconRegisterer.registerIcon(Reference.ModID + ":" + "devices/Bloomery Off");
	}

	@Override
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLivingBase entityliving, ItemStack par6ItemStack)
	{
		if(!world.isRemote)
		{
			int l = MathHelper.floor_double(entityliving.rotationYaw * 4F / 360F + 0.5D) & 3;
			world.setBlockMetadataWithNotify(i, j, k, l, 0x2);
			if(!canBlockStay(world,i,j,k))
			{
				world.setBlockToAir(i, j, k);
				world.spawnEntityInWorld(new EntityItem(world,i,j,k, new ItemStack(this, 1)));
			}
		}
	}

	@Override
	public boolean removedByPlayer(World world, EntityPlayer player, int i, int j, int k)
	{
		if(!world.isRemote)
		{
			int meta = world.getBlockMetadata(i, j, k);
			int[] dir = headBlockToFootBlockMap[meta & 3];
			if(world.getBlock(i+dir[0], j, k+dir[1]) == TFCBlocks.Molten)
				world.setBlock(i+dir[0], j, k+dir[1], Blocks.air, 0, 0x2);
			if(world.getBlock(i+dir[0], j+1, k+dir[1]) == TFCBlocks.Molten)
				world.setBlock(i+dir[0], j+1, k+dir[1], Blocks.air, 0, 0x2);
			if(world.getBlock(i+dir[0], j+2, k+dir[1]) == TFCBlocks.Molten)
				world.setBlock(i+dir[0], j+2, k+dir[1], Blocks.air, 0, 0x2);
			if(world.getBlock(i+dir[0], j+3, k+dir[1]) == TFCBlocks.Molten)
				world.setBlock(i+dir[0], j+3, k+dir[1], Blocks.air, 0, 0x2);
			if(world.getBlock(i+dir[0], j+4, k+dir[1]) == TFCBlocks.Molten)
				world.setBlock(i+dir[0], j+4, k+dir[1], Blocks.air, 0, 0x2);
			world.setBlockToAir(i, j, k);
		}
		return true;
	}

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, Block block)
	{
		if(!world.getBlock(i, j-1, k).isOpaqueCube() || !world.getBlock(i, j+1, k).isOpaqueCube())
		{
			world.setBlockToAir(i, j, k);
			world.spawnEntityInWorld(new EntityItem(world,i,j,k, new ItemStack(this, 1)));
		}
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2)
	{
		return new TileEntityEarlyBloomery();
	}
}
