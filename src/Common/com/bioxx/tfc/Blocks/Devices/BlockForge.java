package com.bioxx.tfc.Blocks.Devices;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.TFCBlocks;
import com.bioxx.tfc.TFCItems;
import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Blocks.BlockTerraContainer;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Items.ItemStick;
import com.bioxx.tfc.Items.Tools.ItemFirestarter;
import com.bioxx.tfc.Items.Tools.ItemFlintSteel;
import com.bioxx.tfc.TileEntities.TEForge;

public class BlockForge extends BlockTerraContainer
{
	IIcon textureOn;
	IIcon textureOff;

	public BlockForge()
	{
		super(Material.sand);
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1F, 0.9F, 1F);
	}

	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float hitX, float hitY, float hitZ)
	{
		int meta = world.getBlockMetadata(i, j, k);
		int xCoord = i;
		int yCoord = j;
		int zCoord = k;
		ItemStack equippedItem = entityplayer.getCurrentEquippedItem();
		TEForge tef = (TEForge)world.getTileEntity(i, j, k);

		if(world.isRemote)
		{
			return true;
		}
		else if(equippedItem != null && (equippedItem.getItem() instanceof ItemFirestarter || equippedItem.getItem() instanceof ItemFlintSteel))
		{
			if(tef != null)
			{
				if(tef.fireTemp <= 0 && tef.fireItemStacks[7] != null && tef.isSmokeStackValid)
				{
					tef.fireTemp = 10;
					tef.fuelBurnTemp = 20;
					tef.fuelTimeLeft = 10;
					int ss = entityplayer.inventory.getCurrentItem().stackSize;
					int dam = entityplayer.inventory.getCurrentItem().getItemDamage()+1;

					if(dam >= entityplayer.getCurrentEquippedItem().getItem().getMaxDamage())
						entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
					else
						entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, new ItemStack(entityplayer.getCurrentEquippedItem().getItem(),ss,dam));

					world.setBlockMetadataWithNotify(i, j, k, 2, 3);
				}
			}
			return true;
		}
		else if(tef.fireTemp > 0 && equippedItem != null && equippedItem.getItem() instanceof ItemStick )
		{
			entityplayer.inventory.consumeInventoryItem(TFCItems.Stick);
			TFC_Core.giveItemToPlayer(new ItemStack(TFCBlocks.Torch), entityplayer);
			return true;
		}
		else
		{
			if(tef !=null)
			{
				if(tef.isSmokeStackValid)
				{
					entityplayer.openGui(TerraFirmaCraft.instance, 23, world, i, j, k);
					//ModLoader.openGUI(entityplayer, new GuiTerraForge(entityplayer.inventory, TEForge));
				}
			}
			return true;
		}
	}

	@Override
	public IIcon getIcon(int i, int j)
	{
		if(j > 0)
			return textureOn;
		return textureOff;
	}

	@Override
	public void registerBlockIcons(IIconRegister iconRegisterer)
	{
		textureOn = iconRegisterer.registerIcon(Reference.ModID + ":" + "devices/Forge On");
		textureOff = iconRegisterer.registerIcon(Reference.ModID + ":" + "devices/Forge Off");
	}

	public boolean getIsFireLit(int i)
	{
		return (i & 1) != 0;
	}

	@Override
	public int getRenderType()
	{
		return TFCBlocks.ForgeRenderId;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
	{
		if(!world.isRemote)
		{
			boolean surroundSolids = (world.getBlock(x+1, y, z).getMaterial() == Material.rock && world.getBlock(x-1, y, z).getMaterial() == Material.rock && 
					world.getBlock(x, y, z+1).getMaterial() == Material.rock && world.getBlock(x, y, z-1).getMaterial() == Material.rock &&
					world.getBlock(x, y-1, z).isNormalCube() && (world.getBlock(x+1, y, z).isNormalCube() && world.getBlock(x-1, y, z).isNormalCube() && 
							world.getBlock(x, y, z+1).isNormalCube() && world.getBlock(x, y, z-1).isNormalCube()));

			boolean rockXP = world.getBlock(x+1, y, z) == TFCBlocks.stoneSlabs || 
					(world.getBlock(x+1, y, z).getMaterial() == Material.rock && world.getBlock(x+1, y, z).isNormalCube());
			boolean rockXN = world.getBlock(x-1, y, z) == TFCBlocks.stoneSlabs || 
					(world.getBlock(x-1, y, z).getMaterial() == Material.rock && world.getBlock(x-1, y, z).isNormalCube());
			boolean rockZP = world.getBlock(x, y, z+1) == TFCBlocks.stoneSlabs || 
					(world.getBlock(x, y, z+1).getMaterial() == Material.rock && world.getBlock(x, y, z+1).isNormalCube());
			boolean rockZN = world.getBlock(x, y, z-1) == TFCBlocks.stoneSlabs || 
					(world.getBlock(x, y, z-1).getMaterial() == Material.rock && world.getBlock(x, y, z-1).isNormalCube());
			boolean rockYN = world.getBlock(x, y-1,  z ) == TFCBlocks.stoneSlabs ||
					(world.getBlock(x, y-1, z).getMaterial() == Material.rock && world.getBlock(x, y-1,z).isNormalCube());

			boolean validSlabs = world.isSideSolid(x, y, z + 1, ForgeDirection.NORTH) && world.isSideSolid(x, y, z - 1, ForgeDirection.SOUTH) &&
					world.isSideSolid(x - 1, y, z, ForgeDirection.EAST) && world.isSideSolid(x + 1, y, z, ForgeDirection.WEST);

			if (!(rockXP && rockXN && rockZP && rockZN && rockYN) || !validSlabs)
			{
				((TEForge)world.getTileEntity(x, y, z)).ejectContents();
				world.setBlockToAir(x, y, z);
			}
			else
			{
				if(world.getTileEntity(x, y, z) != null)
				{
					//((TEForge)world.getBlockTileEntity(x, y, z)).isValid = false;
				}
			}
		}
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune)
	{
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		return ret;
	}

	@Override
	public void randomDisplayTick(World world, int i, int j, int k, Random random)
	{
		if (this == TFCBlocks.Forge)
		{
			return;
		}
		else
		{
			float f = i + 0.5F;
			float f1 = j + 0.9F + random.nextFloat() * 6F / 16F;
			float f2 = k + 0.5F;
			float f3 = 0.52F;
			float f4 = random.nextFloat() * 0.6F;
			float f5 = random.nextFloat() * -0.6F;
			float f6 = random.nextFloat() * -0.6F;
			world.spawnParticle("smoke", f+f4 - 0.3F, f1,  f2 + f5 + 0.3F, 0.0D, 0.0D, 0.0D);
			world.spawnParticle("flame", f+f4 - 0.3F, f1,  f2 + f5 + 0.3F, 0.0D, 0.0D, 0.0D);
			world.spawnParticle("smoke", f+f5 + 0.3F , f1, f2 + f4 - 0.3F, 0.0D, 0.0D, 0.0D);
			world.spawnParticle("flame", f+f5 + 0.3F , f1, f2 + f4 - 0.3F, 0.0D, 0.0D, 0.0D);

			if (((TEForge)world.getTileEntity(i, j, k)).fireTemp > 550)
			{
				world.spawnParticle("flame", f+f5 + 0.3F , f1, f2 + f6 + 0.2F, 0.0D, 0.0D, 0.0D);
				world.spawnParticle("flame", f+f4 - 0.3F , f1, f2 + f6 + 0.1F, 0.0D, 0.0D, 0.0D);
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
			return 15;
		else
			return 10;
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta)
	{
		if(!world.isRemote)
		{
			TEForge te = (TEForge)world.getTileEntity(x, y, z);

			if (te != null)
			{
				te.removeSmoke();
			}
		}
	}

	/**
	 * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
	 * cleared to be reused)
	 */
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	{
		return AxisAlignedBB.getBoundingBox(par2 + this.minX, par3 + this.minY, par4 + this.minZ, par2 + this.maxX, par3 + this.maxY, par4 + this.maxZ);
	}

	/**
	 * Returns the bounding box of the wired rectangular prism to render.
	 */
	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	{
		return AxisAlignedBB.getBoundingBox(par2 + this.minX, par3 + this.minY, par4 + this.minZ, par2 + this.maxX, par3 + this.maxY, par4 + this.maxZ);
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2)
	{
		return new TEForge();
	}
}
