package com.bioxx.tfc.Blocks.Devices;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Blocks.BlockTerraContainer;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.TileEntities.TEBlastFurnace;
import com.bioxx.tfc.TileEntities.TEMetalSheet;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.TFCItems;

public class BlockBlastFurnace extends BlockTerraContainer
{
	private IIcon[] textureSide;
	private IIcon textureOn;
	private IIcon textureOff;

	public BlockBlastFurnace()
	{
		super(Material.rock);
		this.setCreativeTab(TFCTabs.TFC_DEVICES);
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
		ItemStack equippedItem = entityplayer.getCurrentEquippedItem();
		if(!world.isRemote)
		{
		if(!canBlockStay(world,i,j,k))
		{
			world.setBlockToAir(i, j, k);
			world.spawnEntityInWorld(new EntityItem(world,i,j,k, new ItemStack(this, 1)));
		}
		else if(world.getTileEntity(i, j, k) != null)
		{
			TEBlastFurnace te = (TEBlastFurnace)world.getTileEntity(i, j, k);

			if(te.isValid)
			{
				if(equippedItem != null && (equippedItem.getItem() == TFCItems.fireStarter || equippedItem.getItem() == TFCItems.flintSteel))
					if(te.canLight())
						entityplayer.getCurrentEquippedItem().damageItem(1,entityplayer);

				entityplayer.openGui(TerraFirmaCraft.instance, 26, world, i, j, k);
			}
		}
		}
		return true;
	}

	@Override
	public boolean canBlockStay(World world, int i, int j, int k)
	{
		return checkStackAt(world,i,j+1,k);
	}
	
	public boolean checkStackAt(World world, int x, int y, int z)
	{
		return checkBlock(world, x + 1, y, z, x, z) &&checkBlock(world, x - 1, y, z, x, z) && checkBlock(world, x, y, z + 1, x, z) &&
				checkBlock(world, x, y, z - 1, x, z) && (world.isAirBlock(x, y, z) || world.getBlock(x, y, z) == TFCBlocks.molten);
	}

	public boolean checkBlock(World world, int x, int y, int z, int stackX, int stackZ)
	{
		if (!world.blockExists(x, y, z) || world.getBlock(x, y, z) != TFCBlocks.fireBrick)
			return false;

		int count = 0;
		int xCoord = x-1;
		int zCoord = z;
		if (world.blockExists(xCoord, y, zCoord) &&!(xCoord == stackX && zCoord == stackZ) &&
				world.getBlock(xCoord, y, zCoord) == TFCBlocks.metalSheet && world.getTileEntity(xCoord, y, zCoord) instanceof TEMetalSheet)
		{
			TEMetalSheet te = (TEMetalSheet)world.getTileEntity(xCoord, y, zCoord);
			if(!te.westExists() || !isValidMetalSheet(te))
				return false;
			count++;
		}
		xCoord = x+1;
		zCoord = z;
		if (world.blockExists(xCoord, y, zCoord) &&!(xCoord == stackX && zCoord == stackZ) &&
				world.getBlock(xCoord, y, zCoord) == TFCBlocks.metalSheet && world.getTileEntity(xCoord, y, zCoord) instanceof TEMetalSheet)
		{
			TEMetalSheet te = (TEMetalSheet)world.getTileEntity(xCoord, y, zCoord);
			if(!te.eastExists() || !isValidMetalSheet(te))
				return false;
			count++;
		}
		xCoord = x;
		zCoord = z-1;
		if (world.blockExists(xCoord, y, zCoord) &&!(xCoord == stackX && zCoord == stackZ) &&
				world.getBlock(xCoord, y, zCoord) == TFCBlocks.metalSheet && world.getTileEntity(xCoord, y, zCoord) instanceof TEMetalSheet)
		{
			TEMetalSheet te = (TEMetalSheet)world.getTileEntity(xCoord, y, zCoord);
			if(!te.southExists() || !isValidMetalSheet(te))
				return false;
			count++;
		}
		xCoord = x;
		zCoord = z+1;
		if (world.blockExists(xCoord, y, zCoord) &&!(xCoord == stackX && zCoord == stackZ) &&
				world.getBlock(xCoord, y, zCoord) == TFCBlocks.metalSheet && world.getTileEntity(xCoord, y, zCoord) instanceof TEMetalSheet)
		{
			TEMetalSheet te = (TEMetalSheet)world.getTileEntity(xCoord, y, zCoord);
			if(!te.northExists() || !isValidMetalSheet(te))
				return false;
			count++;
		}
		return count >= 3;
	}
	
	public boolean isValidMetalSheet(TEMetalSheet te)
	{
		if(te != null)
		{
			ItemStack sheet = te.sheetStack;
			if(sheet != null && (
					sheet.getItem() == TFCItems.wroughtIronSheet ||
					sheet.getItem() == TFCItems.steelSheet ||
					sheet.getItem() == TFCItems.blackSteelSheet ||
					sheet.getItem() == TFCItems.blueSteelSheet ||
					sheet.getItem() == TFCItems.redSteelSheet))
				return true;
		}
		return false;
	}

	@Override
	public boolean canPlaceBlockAt(World world, int i, int j, int k)
	{
		return true;
	}

	@Override
	public IIcon getIcon(int i, int j)
	{
		int lit = (j & 4) == 4 ? 1 : 0;
		j = j & 3;

		if(i == 0 || i == 1)
		{
			if(lit == 1)
				return textureSide[1];
			else
				return textureSide[0];
		}
		else
		{
			if(lit == 1)
				return textureOn;
			else
				return textureOff;
		}
	}

	@Override
	public void registerBlockIcons(IIconRegister iconRegisterer)
	{
		textureSide = new IIcon[2];
		textureSide[0] = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "devices/Blast Furnace Bottom Off");
		textureSide[1] = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "devices/Blast Furnace Bottom On");
		textureOn = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "devices/Blast Furnace On");
		textureOff = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "devices/Blast Furnace Off");
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
			else
			{
				((TEBlastFurnace)world.getTileEntity(i, j, k)).slowCounter = 101;
			}
		}
	}

	@Override
	public boolean removedByPlayer(World world, EntityPlayer player, int i, int j, int k) 
	{
		if(!world.isRemote)
		{
			world.getBlockMetadata(i, j, k);

			if(world.getBlock(i, j, k) == TFCBlocks.molten)
				world.setBlockToAir(i, j, k);
			if(world.getBlock(i, j+1, k) == TFCBlocks.molten)
				world.setBlockToAir(i, j+1, k);
			if(world.getBlock(i, j+2, k) == TFCBlocks.molten)
				world.setBlockToAir(i, j+2, k);
			if(world.getBlock(i, j+3, k) == TFCBlocks.molten)
				world.setBlockToAir(i, j+3, k);
			if(world.getBlock(i, j+4, k) == TFCBlocks.molten)
				world.setBlockToAir(i, j+4, k);
			world.setBlockToAir(i, j, k);
		}
		return true;
	}

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, Block block)
	{
		/*if(!world.isBlockOpaqueCube(i, j-1, k) || !world.isBlockOpaqueCube(i, j+1, k))
		{
			((TEBlastFurnace)world.getTileEntity(i, j, k)).ejectContents();
			world.setBlockToAir(i, j, k);
			world.spawnEntityInWorld(new EntityItem(world,i,j,k, new ItemStack(this, 1)));
		}*/
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2)
	{
		return new TEBlastFurnace();
	}
}
