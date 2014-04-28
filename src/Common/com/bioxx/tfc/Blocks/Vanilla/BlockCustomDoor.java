package com.bioxx.tfc.Blocks.Vanilla;

import java.util.Random;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Blocks.BlockTerra;
import com.bioxx.tfc.Core.Recipes;
import com.bioxx.tfc.api.Constant.Global;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.renderer.IconFlipped;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockCustomDoor extends BlockTerra
{
	int woodType;
	String[] WoodNames = {"Oak Door Lower","Oak Door Upper","Aspen Door Lower","Aspen Door Upper","Birch Door Lower","Birch Door Upper",
			"Chestnut Door Lower","Chestnut Door Upper","Douglas Fir Door Lower","Douglas Fir Door Upper","Hickory Door Lower","Hickory Door Upper",
			"Maple Door Lower","Maple Door Upper","Ash Door Lower","Ash Door Upper","Pine Door Lower","Pine Door Upper",
			"Sequoia Door Lower","Sequoia Door Upper","Spruce Door Lower","Spruce Door Upper","Sycamore Door Lower","Sycamore Door Upper",
			"White Cedar Door Lower","White Cedar Door Upper","White Elm Door Lower","White Elm Door Upper","Willow Door Lower","Willow Door Upper",
			"Kapok Door Lower","Kapok Door Upper","Acacia Door Lower","Acacia Door Upper"};

	IIcon[] icons = new IIcon[Global.WOOD_ALL.length*2];
	public BlockCustomDoor(int woodId)
	{
		super(Material.wood);
		this.setHardness(3);

		float var3 = 0.5F;
		float var4 = 1.0F;
		this.setBlockBounds(0.5F - var3, 0.0F, 0.5F - var3, 0.5F + var3, var4, 0.5F + var3);
		woodType = woodId;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int par1, int par2)
	{
		return this.icons[woodType];
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
	{
		if (par5 != 1 && par5 != 0)
		{
			int meta = this.getFullMetadata(par1IBlockAccess, par2, par3, par4);
			int rotation = meta & 3;
			boolean flag = (meta & 4) != 0;
			boolean flag1 = false;
			boolean flag2 = (meta & 8) != 0;

			if (flag)
			{
				if (rotation == 0 && par5 == 2)
					flag1 = !flag1;
				else if (rotation == 1 && par5 == 5)
					flag1 = !flag1;
				else if (rotation == 2 && par5 == 3)
					flag1 = !flag1;
				else if (rotation == 3 && par5 == 4)
					flag1 = !flag1;
			}
			else
			{
				if (rotation == 0 && par5 == 5)
					flag1 = !flag1;
				else if (rotation == 1 && par5 == 3)
					flag1 = !flag1;
				else if (rotation == 2 && par5 == 4)
					flag1 = !flag1;
				else if (rotation == 3 && par5 == 2)
					flag1 = !flag1;

				if ((meta & 16) != 0)
					flag1 = !flag1;
			}

			return icons[woodType + (flag1 ? WoodNames.length : 0) + (flag2 ? 1 : 0)];
		}
		else
		{
			return icons[woodType];
		}
	}

	@Override
	public void registerBlockIcons(IIconRegister registerer)
	{
		this.icons = new IIcon[WoodNames.length * 2];
		for(int i = 0; i < WoodNames.length; i++)
		{
			icons[i] = registerer.registerIcon(Reference.ModID + ":" + "wood/doors/"+WoodNames[i]);
			this.icons[i + WoodNames.length] = new IconFlipped(this.icons[i], true, false);
		}
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public boolean getBlocksMovement(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		int var5 = this.getFullMetadata(par1IBlockAccess, par2, par3, par4);
		return (var5 & 4) != 0;
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	public int getRenderType()
	{
		return 7;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	{
		this.setBlockBoundsBasedOnState(par1World, par2, par3, par4);
		return super.getSelectedBoundingBoxFromPool(par1World, par2, par3, par4);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	{
		this.setBlockBoundsBasedOnState(par1World, par2, par3, par4);
		return super.getCollisionBoundingBoxFromPool(par1World, par2, par3, par4);
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		this.setDoorRotation(this.getFullMetadata(par1IBlockAccess, par2, par3, par4));
	}

	/**
	 * Returns 0, 1, 2 or 3 depending on where the hinge is.
	 */
	public int getDoorOrientation(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		return this.getFullMetadata(par1IBlockAccess, par2, par3, par4) & 3;
	}

	public boolean isDoorOpen(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		return (this.getFullMetadata(par1IBlockAccess, par2, par3, par4) & 4) != 0;
	}

	private void setDoorRotation(int par1)
	{
		float var2 = 0.1875F;
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F);
		int var3 = par1 & 3;
		boolean var4 = (par1 & 4) != 0;
		boolean var5 = (par1 & 16) != 0;

		if (var3 == 0)
		{
			if (var4)
			{
				if (!var5)
					this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, var2);
				else
					this.setBlockBounds(0.0F, 0.0F, 1.0F - var2, 1.0F, 1.0F, 1.0F);
			}
			else
			{
				this.setBlockBounds(0.0F, 0.0F, 0.0F, var2, 1.0F, 1.0F);
			}
		}
		else if (var3 == 1)
		{
			if (var4)
			{
				if (!var5)
					this.setBlockBounds(1.0F - var2, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
				else
					this.setBlockBounds(0.0F, 0.0F, 0.0F, var2, 1.0F, 1.0F);
			}
			else
			{
				this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, var2);
			}
		}
		else if (var3 == 2)
		{
			if (var4)
			{
				if (!var5)
					this.setBlockBounds(0.0F, 0.0F, 1.0F - var2, 1.0F, 1.0F, 1.0F);
				else
					this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, var2);
			}
			else
			{
				this.setBlockBounds(1.0F - var2, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
			}
		}
		else if (var3 == 3)
		{
			if (var4)
			{
				if (!var5)
					this.setBlockBounds(0.0F, 0.0F, 0.0F, var2, 1.0F, 1.0F);
				else
					this.setBlockBounds(1.0F - var2, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
			}
			else
			{
				this.setBlockBounds(0.0F, 0.0F, 1.0F - var2, 1.0F, 1.0F, 1.0F);
			}
		}
	}

	@Override
	public void onBlockClicked(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer) {}

	@Override
	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
	{
		if (this.blockMaterial == Material.iron)
		{
			return false; //Allow items to interact with the door
		}
		else
		{
			int var10 = this.getFullMetadata(par1World, par2, par3, par4);
			int var11 = var10 & 7;
			var11 ^= 4;

			if ((var10 & 8) == 0)
			{
				par1World.setBlockMetadataWithNotify(par2, par3, par4, var11, 3);
				par1World.markBlockRangeForRenderUpdate(par2, par3, par4, par2, par3, par4);
			}
			else
			{
				par1World.setBlockMetadataWithNotify(par2, par3 - 1, par4, var11, 3);
				par1World.markBlockRangeForRenderUpdate(par2, par3 - 1, par4, par2, par3, par4);
			}

			par1World.playAuxSFXAtEntity(par5EntityPlayer, 1003, par2, par3, par4, 0);
			return true;
		}
	}

	public void onPoweredBlockChange(World par1World, int par2, int par3, int par4, boolean par5)
	{
		int var6 = this.getFullMetadata(par1World, par2, par3, par4);
		boolean var7 = (var6 & 4) != 0;

		if (var7 != par5)
		{
			int var8 = var6 & 7;
			var8 ^= 4;

			if ((var6 & 8) == 0)
			{
				par1World.setBlockMetadataWithNotify(par2, par3, par4, var8, 3);
				par1World.markBlockRangeForRenderUpdate(par2, par3, par4, par2, par3, par4);
			}
			else
			{
				par1World.setBlockMetadataWithNotify(par2, par3 - 1, par4, var8, 3);
				par1World.markBlockRangeForRenderUpdate(par2, par3 - 1, par4, par2, par3, par4);
			}

			par1World.playAuxSFXAtEntity((EntityPlayer)null, 1003, par2, par3, par4, 0);
		}
	}

	/**
	 * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
	 * their own) Args: x, y, z, neighbor Block
	 */
	@Override
	public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, Block par5)
	{
		int var6 = par1World.getBlockMetadata(par2, par3, par4);

		if ((var6 & 8) == 0)
		{
			boolean var7 = false;

			if (par1World.getBlock(par2, par3 + 1, par4) != this)
			{
				par1World.setBlockToAir(par2, par3, par4);
				var7 = true;
			}

			if (!World.doesBlockHaveSolidTopSurface(par1World, par2, par3 - 1, par4))
			{
				par1World.setBlockToAir(par2, par3, par4);
				var7 = true;

				if (par1World.getBlock(par2, par3 + 1, par4) == this)
					par1World.setBlockToAir(par2, par3 + 1, par4);
			}

			if (var7)
			{
				if (!par1World.isRemote)
					this.dropBlockAsItem(par1World, par2, par3, par4, var6, 0);
			}
			else
			{
				boolean var8 = par1World.isBlockIndirectlyGettingPowered(par2, par3, par4) || par1World.isBlockIndirectlyGettingPowered(par2, par3 + 1, par4);
				if ((var8 || par5.canProvidePower()) && par5 != this)
					this.onPoweredBlockChange(par1World, par2, par3, par4, var8);
			}
		}
		else
		{
			if (par1World.getBlock(par2, par3 - 1, par4) != this)
				par1World.setBlockToAir(par2, par3, par4);

			if (par5 != this)
				this.onNeighborBlockChange(par1World, par2, par3 - 1, par4, par5);
		}
	}

	/**
	 * Returns the Item to drop on destruction.
	 */
	@Override
	public Item getItemDropped(int par1, Random par2Random, int par3)
	{
		return (par1 & 8) != 0 ? null : Recipes.Doors[woodType/2];
	}

	/**
	 * Ray traces through the blocks collision from start vector to end vector returning a ray trace hit. Args: world,
	 * x, y, z, startVec, endVec
	 */
	@Override
	public MovingObjectPosition collisionRayTrace(World par1World, int par2, int par3, int par4, Vec3 par5Vec3, Vec3 par6Vec3)
	{
		this.setBlockBoundsBasedOnState(par1World, par2, par3, par4);
		return super.collisionRayTrace(par1World, par2, par3, par4, par5Vec3, par6Vec3);
	}

	/**
	 * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
	 */
	@Override
	public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
	{
		return par3 >= 255 ? false : World.doesBlockHaveSolidTopSurface(par1World, par2, par3 - 1, par4) && super.canPlaceBlockAt(par1World, par2, par3, par4) && super.canPlaceBlockAt(par1World, par2, par3 + 1, par4);
	}

	/**
	 * Returns the mobility information of the block, 0 = free, 1 = can't push but can move over, 2 = total immobility
	 * and stop pistons
	 */
	@Override
	public int getMobilityFlag()
	{
		return 1;
	}

	/**
	 * Returns the full metadata value created by combining the metadata of both blocks the door takes up.
	 */
	public int getFullMetadata(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		int var5 = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
		boolean var6 = (var5 & 8) != 0;
		int var7;
		int var8;

		if (var6)
		{
			var7 = par1IBlockAccess.getBlockMetadata(par2, par3 - 1, par4);
			var8 = var5;
		}
		else
		{
			var7 = var5;
			var8 = par1IBlockAccess.getBlockMetadata(par2, par3 + 1, par4);
		}

		boolean var9 = (var8 & 1) != 0;
		return var7 & 7 | (var6 ? 8 : 0) | (var9 ? 16 : 0);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Item getItem(World par1World, int par2, int par3, int par4)
	{
		return this.blockMaterial == Material.iron ? Items.iron_door : Items.wooden_door;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean addDestroyEffects(World world, int x, int y, int z, int meta, EffectRenderer effectRenderer)
	{
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean addHitEffects(World worldObj, MovingObjectPosition target, EffectRenderer effectRenderer)
	{
		return true;
	}
}
