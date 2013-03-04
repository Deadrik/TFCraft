package TFC.Blocks;

import java.util.Iterator;
import java.util.Random;

import TFC.*;
import TFC.TileEntities.NetworkTileEntity;
import TFC.TileEntities.TileEntityBarrel;
import TFC.TileEntities.TileEntityChestTFC;
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
import net.minecraft.entity.passive.EntityOcelot;
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
import net.minecraftforge.common.ForgeDirection;

public class BlockBarrel extends BlockTerraContainer
{
	private Random random = new Random();

	public BlockBarrel(int par1)
	{
		super(par1, Material.wood);
		this.blockIndexInTexture = 5;
	}

	/**
	 * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
	 * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
	 */
	public boolean isOpaqueCube()
	{
		return false;
	}

	/**
	 * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
	 */
	public boolean renderAsNormalBlock()
	{
		return true;//false;
	}

	/**
	 * The type of render function that is called for this block
	 */

	/**
	 * Called whenever the block is added into the world. Args: world, x, y, z
	 */
	@Override
	public void onBlockAdded(World par1World, int par2, int par3, int par4)
	{
		super.onBlockAdded(par1World, par2, par3, par4);
	}

	/**
	 * Called when the block is placed in the world.
	 */
	@Override
	public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLiving par5EntityLiving)
	{

	}


	/**
	 * Retrieves the block texture to use based on the display side. Args: iBlockAccess, x, y, z, side
	 */
	@Override
	public int getBlockTexture(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
	{
		if (par5 == 1)
		{
			return this.blockIndexInTexture - 1;
		}
		else if (par5 == 0)
		{
			return this.blockIndexInTexture - 1;
		}
		else
		{
			int var6 = par1IBlockAccess.getBlockId(par2, par3, par4 - 1);
			int var7 = par1IBlockAccess.getBlockId(par2, par3, par4 + 1);
			int var8 = par1IBlockAccess.getBlockId(par2 - 1, par3, par4);
			int var9 = par1IBlockAccess.getBlockId(par2 + 1, par3, par4);
			int var10;
			int var11;
			int var12;
			byte var13;

			if (var6 != this.blockID && var7 != this.blockID)
			{
				if (var8 != this.blockID && var9 != this.blockID)
				{
					byte var14 = 3;

					if (Block.opaqueCubeLookup[var6] && !Block.opaqueCubeLookup[var7])
					{
						var14 = 3;
					}

					if (Block.opaqueCubeLookup[var7] && !Block.opaqueCubeLookup[var6])
					{
						var14 = 2;
					}

					if (Block.opaqueCubeLookup[var8] && !Block.opaqueCubeLookup[var9])
					{
						var14 = 5;
					}

					if (Block.opaqueCubeLookup[var9] && !Block.opaqueCubeLookup[var8])
					{
						var14 = 4;
					}

					return par5 == var14 ? this.blockIndexInTexture + 1 : this.blockIndexInTexture;
				}
				else if (par5 != 4 && par5 != 5)
				{
					var10 = 0;

					if (var8 == this.blockID)
					{
						var10 = -1;
					}

					var11 = par1IBlockAccess.getBlockId(var8 == this.blockID ? par2 - 1 : par2 + 1, par3, par4 - 1);
					var12 = par1IBlockAccess.getBlockId(var8 == this.blockID ? par2 - 1 : par2 + 1, par3, par4 + 1);

					if (par5 == 3)
					{
						var10 = -1 - var10;
					}

					var13 = 3;

					if ((Block.opaqueCubeLookup[var6] || Block.opaqueCubeLookup[var11]) && !Block.opaqueCubeLookup[var7] && !Block.opaqueCubeLookup[var12])
					{
						var13 = 3;
					}

					if ((Block.opaqueCubeLookup[var7] || Block.opaqueCubeLookup[var12]) && !Block.opaqueCubeLookup[var6] && !Block.opaqueCubeLookup[var11])
					{
						var13 = 2;
					}

					return (par5 == var13 ? this.blockIndexInTexture + 16 : this.blockIndexInTexture + 32) + var10;
				}
				else
				{
					return this.blockIndexInTexture;
				}
			}
			else if (par5 != 2 && par5 != 3)
			{
				var10 = 0;

				if (var6 == this.blockID)
				{
					var10 = -1;
				}

				var11 = par1IBlockAccess.getBlockId(par2 - 1, par3, var6 == this.blockID ? par4 - 1 : par4 + 1);
				var12 = par1IBlockAccess.getBlockId(par2 + 1, par3, var6 == this.blockID ? par4 - 1 : par4 + 1);

				if (par5 == 4)
				{
					var10 = -1 - var10;
				}

				var13 = 5;

				if ((Block.opaqueCubeLookup[var8] || Block.opaqueCubeLookup[var11]) && !Block.opaqueCubeLookup[var9] && !Block.opaqueCubeLookup[var12])
				{
					var13 = 5;
				}

				if ((Block.opaqueCubeLookup[var9] || Block.opaqueCubeLookup[var12]) && !Block.opaqueCubeLookup[var8] && !Block.opaqueCubeLookup[var11])
				{
					var13 = 4;
				}

				return (par5 == var13 ? this.blockIndexInTexture + 16 : this.blockIndexInTexture + 32) + var10;
			}
			else
			{
				return this.blockIndexInTexture;
			}
		}
	}

	/**
	 * Returns the block texture based on the side being looked at.  Args: side
	 */
	@Override
	public int getBlockTextureFromSide(int par1)
	{
		return par1 == 1 ? this.blockIndexInTexture - 1 : (par1 == 0 ? this.blockIndexInTexture - 1 : (par1 == 3 ? this.blockIndexInTexture + 1 : this.blockIndexInTexture));
	}

	/**
	 * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
	 */
	@Override
	public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
	{
		return true;
	}

	/**
	 * Called whenever the block is removed.
	 */
	@Override
	public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
	{
		TileEntityBarrel var5 = (TileEntityBarrel)par1World.getBlockTileEntity(par2, par3, par4);

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
							var12.func_92014_d().setTagCompound((NBTTagCompound)var7.getTagCompound().copy());
						}
					}
				}
			}
		}

		super.breakBlock(par1World, par2, par3, par4, par5, par6);
	}

	/**
	 * Called upon block activation (left or right click on the block.). The three integers represent x,y,z of the
	 * block.
	 */
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityplayer, int side, float hitX, float hitY, float hitZ)
	{
		if (world.isRemote)
		{
			((NetworkTileEntity)world.getBlockTileEntity(x,y,z)).validate();
			return true;
		}
		else
		{
			if(world.getBlockTileEntity(x, y, z) != null){
				TileEntityBarrel TeBarrel = (TileEntityBarrel)(world.getBlockTileEntity(x, y, z));
				if (TeBarrel.getSealed()){
					return false;
				}
				entityplayer.openGui(TerraFirmaCraft.instance, 35, world, x, y, z);
				return true;
			}
		}
		return false;

	}

	/**
	 * Returns the TileEntity used by this block.
	 */
	public TileEntity getBlockEntity()
	{
		return new TileEntityBarrel();
	}

	/**
	 * Looks for a sitting ocelot within certain bounds. Such an ocelot is considered to be blocking access to the
	 * chest.
	 */

	@Override
	public TileEntity createNewTileEntity(World var1) {
		// TODO Auto-generated method stub
		return new TileEntityBarrel();
	}
}
