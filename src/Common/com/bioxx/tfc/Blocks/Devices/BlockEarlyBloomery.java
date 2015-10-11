package com.bioxx.tfc.Blocks.Devices;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Blocks.BlockTerraContainer;
import com.bioxx.tfc.Core.CollisionRayTraceStandard;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.TileEntities.TEBloomery;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.Interfaces.ICustomCollision;

public class BlockEarlyBloomery extends BlockTerraContainer implements ICustomCollision
{
	private IIcon textureOn;
	private IIcon textureOff;

	public static final int BLOOMERY_TO_STACK_MAP[][] = { { 0, 1 }, { -1, 0 }, { 0, -1 }, { 1, 0 } };
	public static final int SIDES_MAP[][] = { { 1, 0 }, { 0, 1 }, { 1, 0 }, { 0, 1 } };
	//North = 2 = -z
	//South = 0 = +z
	//West = 1 = -x
	//East = 3 = = +x

	public BlockEarlyBloomery()
	{
		super(Material.rock);
		this.setCreativeTab(TFCTabs.TFC_DEVICES);
		setBlockBounds(0.0F, 0.0F, 0.0f, 0.0f, 0.0F, 0.0F);
	}

	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z)
	{
		int meta = world.getBlockMetadata(x, y, z) & 4;
		if (meta == 0)
			return 0;
		else
			return 15;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityplayer, int par6, float par7, float par8, float par9)
	{
		if (!canBlockStay(world, x, y, z))
		{
			world.setBlockToAir(x, y, z);
			world.spawnEntityInWorld(new EntityItem(world, x, y, z, new ItemStack(this, 1)));
		}
		else if ((TEBloomery) world.getTileEntity(x, y, z) != null)
		{
			TEBloomery te = (TEBloomery) world.getTileEntity(x, y, z);
			ItemStack is = entityplayer.getCurrentEquippedItem();

			if (is != null && (is.getItem() == TFCItems.fireStarter || is.getItem() == TFCItems.flintSteel))
			{
				if (te.canLight())
					entityplayer.getCurrentEquippedItem().damageItem(1, entityplayer);
			}
			else
			{
				world.playAuxSFXAtEntity(entityplayer, 1003, x, y, z, 0);
				if (isOpen(world.getBlockMetadata(x, y, z)))
					world.setBlockMetadataWithNotify(x, y, z, world.getBlockMetadata(x, y, z) - 8, 3);
				else
					world.setBlockMetadataWithNotify(x, y, z, world.getBlockMetadata(x, y, z) + 8, 3);
			}
		}
		return true;
	}

	@Override
	public boolean canBlockStay(World world, int x, int y, int z)
	{
		if(world.isAirBlock(x, y, z))
			return true;

		if (world.getTileEntity(x, y, z) instanceof TEBloomery) // Prevent ClassCastException
		{
			boolean flipped = false;
			int dir = world.getBlockMetadata(x, y, z) & 3;
			TEBloomery te = (TEBloomery) world.getTileEntity(x, y, z);

			if (te != null)
				flipped = te.isFlipped;

			if (checkStack(world, x, y, z, dir))
			{
				if (checkVertical(world, x, y, z, flipped))
				{
					if(checkHorizontal(world, x, y, z, flipped))
						return true;
				}
				else if (te != null && !flipped)
				{
					this.tryFlip(world, x, y, z);
					flipped = te.isFlipped;
					if (checkVertical(world, x, y, z, flipped))
					{
						if (checkHorizontal(world, x, y, z, flipped))
							return true;
					}
				}
			}
		}
		return false;
	}

	public boolean checkStack(World world, int x, int y, int z, int dir)
	{
		int[] map = BLOOMERY_TO_STACK_MAP[dir];
		int centerX = x + map[0];
		int centerZ = z + map[1];
		if (isNorthStackValid(world, centerX, y, centerZ - 1) || centerX == x && centerZ - 1 == z)
		{
			if (isSouthStackValid(world, centerX, y, centerZ + 1) || centerX == x && centerZ + 1 == z)
			{
				if (isEastStackValid(world, centerX - 1, y, centerZ) || centerX - 1 == x && centerZ == z)
				{
					if (isWestStackValid(world, centerX + 1, y, centerZ) || centerX + 1 == x && centerZ == z)
					{
						return true;
					}
				}
			}
		}
		return false;
	}

	private boolean isNorthStackValid(World world, int x, int y, int z)
	{
		return (world.getBlock(x, y, z).getMaterial() == Material.rock ||
				world.getBlock(x, y, z).getMaterial() == Material.iron) &&
				world.getBlock(x, y, z).isNormalCube() ||
				TFC_Core.isSouthFaceSolid(world, x, y, z); //Since its the North Block, we need to make sure the South side facing the stack is solid
	}

	private boolean isSouthStackValid(World world, int x, int y, int z)
	{
		return (world.getBlock(x, y, z).getMaterial() == Material.rock ||
				world.getBlock(x, y, z).getMaterial() == Material.iron) &&
				world.getBlock(x, y, z).isNormalCube() ||
				TFC_Core.isNorthFaceSolid(world, x, y, z);//Since its the South Block, we need to make sure the North side facing the stack is solid
	}

	private boolean isEastStackValid(World world, int x, int y, int z)
	{
		return (world.getBlock(x, y, z).getMaterial() == Material.rock ||
				world.getBlock(x, y, z).getMaterial() == Material.iron) &&
				world.getBlock(x, y, z).isNormalCube() ||
				TFC_Core.isWestFaceSolid(world, x, y, z);//Since its the East Block, we need to make sure the West side facing the stack is solid
	}

	private boolean isWestStackValid(World world, int x, int y, int z)
	{
		return (world.getBlock(x, y, z).getMaterial() == Material.rock ||
				world.getBlock(x, y, z).getMaterial() == Material.iron) &&
				world.getBlock(x, y, z).isNormalCube() ||
				TFC_Core.isEastFaceSolid(world, x, y, z); //Since its the West Block, we need to make sure the East side facing the stack is solid
	}

	private boolean checkHorizontal(World world, int x, int y, int z, boolean flip)
	{
		int dir = world.getBlockMetadata(x, y, z) & 3;

		if(flip)
			dir = flipDir(dir);

		int[] map = SIDES_MAP[dir];

		boolean l = false;
		boolean r = false;
		if((world.getBlock(x - map[0], y, z - map[1]).getMaterial() == Material.rock || world.getBlock(x - map[0], y, z - map[1]).getMaterial() == Material.iron) && world.getBlock(x - map[0], y, z - map[1]).isOpaqueCube())
			l = true;

		if (!l && world.getBlock(x - map[0], y, z - map[1]) == TFCBlocks.detailed || world.getBlock(x - map[0], y, z - map[1]) == TFCBlocks.stoneSlabs)
		{
			switch(dir)
			{
			case 0:
				if(TFC_Core.isNorthFaceSolid(world, x - map[0], y, z - map[1]) && TFC_Core.isEastFaceSolid(world, x - map[0], y, z - map[1]))
					l=true;
				break;
			case 1:
				if(TFC_Core.isEastFaceSolid(world, x - map[0], y, z - map[1]) && TFC_Core.isSouthFaceSolid(world, x - map[0], y, z - map[1]))
					l=true;
				break;
			case 2:
				if(TFC_Core.isSouthFaceSolid(world, x - map[0], y, z - map[1]) && TFC_Core.isEastFaceSolid(world, x - map[0], y, z - map[1]))
					l=true;
				break;
			case 3:
				if(TFC_Core.isWestFaceSolid(world, x - map[0], y, z - map[1]) && TFC_Core.isSouthFaceSolid(world, x - map[0], y, z - map[1]))
					l=true;
				break;
			}

			if(!TFC_Core.isBottomFaceSolid(world, x - map[0], y, z - map[1]))
				l = false;
			if(!TFC_Core.isTopFaceSolid(world, x - map[0], y, z - map[1]))
				l = false;
		}

		if((world.getBlock(x + map[0], y, z + map[1]).getMaterial() == Material.rock || world.getBlock(x + map[0], y, z + map[1]).getMaterial() == Material.iron) && world.getBlock(x + map[0], y, z + map[1]).isOpaqueCube())
			r = true;

		if (!r && world.getBlock(x + map[0], y, z + map[1]) == TFCBlocks.detailed || world.getBlock(x + map[0], y, z + map[1]) == TFCBlocks.stoneSlabs)
		{
			switch(dir)
			{
			case 0:
				if(TFC_Core.isNorthFaceSolid(world, x+map[0], y, z + map[1]) && TFC_Core.isWestFaceSolid(world, x+map[0], y, z + map[1]))
					r=true;
				break;
			case 1:
				if(TFC_Core.isEastFaceSolid(world, x+map[0], y, z + map[1]) && TFC_Core.isNorthFaceSolid(world, x+map[0], y, z + map[1]))
					r=true;
				break;
			case 2:
				if(TFC_Core.isSouthFaceSolid(world, x+map[0], y, z + map[1]) && TFC_Core.isWestFaceSolid(world, x+map[0], y, z + map[1]))
					r=true;
				break;
			case 3:
				if(TFC_Core.isWestFaceSolid(world, x+map[0], y, z + map[1]) && TFC_Core.isNorthFaceSolid(world, x+map[0], y, z + map[1]))
					r=true;
				break;
			}
		}

		if(!TFC_Core.isBottomFaceSolid(world, x + map[0], y, z + map[1]))
			r = false;
		if(!TFC_Core.isTopFaceSolid(world, x + map[0], y, z + map[1]))
			r = false;

		return l && r;

	}

	private boolean checkVertical(World world, int x, int y, int z, boolean flip)
	{
		int dir = world.getBlockMetadata(x, y, z) & 3;

		if(flip)
			dir = flipDir(dir);

		boolean b = false;
		boolean t = false;
		if((world.getBlock(x, y - 1, z).getMaterial() == Material.rock || world.getBlock(x, y - 1, z).getMaterial() == Material.iron) && world.getBlock(x, y - 1, z).isOpaqueCube())
			b = true;

		if (!b && world.getBlock(x, y - 1, z) == TFCBlocks.detailed || world.getBlock(x, y - 1, z) == TFCBlocks.stoneSlabs)
		{
			switch(dir)
			{
			case 0:
				if(TFC_Core.isNorthFaceSolid(world, x, y - 1, z) && TFC_Core.isEastFaceSolid(world, x, y - 1, z) && TFC_Core.isWestFaceSolid(world, x, y - 1, z))
					b=true;
				break;
			case 1:
				if(TFC_Core.isEastFaceSolid(world, x, y - 1, z) && TFC_Core.isNorthFaceSolid(world, x, y - 1, z) && TFC_Core.isSouthFaceSolid(world, x, y - 1, z))
					b=true;
				break;
			case 2:
				if(TFC_Core.isSouthFaceSolid(world, x, y - 1, z) && TFC_Core.isEastFaceSolid(world, x, y - 1, z) && TFC_Core.isWestFaceSolid(world, x, y - 1, z))
					b=true;
				break;
			case 3:
				if(TFC_Core.isWestFaceSolid(world, x,  y- 1, z) && TFC_Core.isNorthFaceSolid(world, x, y - 1, z) && TFC_Core.isSouthFaceSolid(world, x, y - 1, z))
					b=true;
				break;
			}

			if(!TFC_Core.isTopFaceSolid(world, x, y - 1, z))
				b = false;
		}

		if((world.getBlock(x, y + 1, z).getMaterial() == Material.rock || world.getBlock(x, y + 1, z).getMaterial() == Material.iron) && world.getBlock(x, y + 1, z).isOpaqueCube())
			t = true;

		if (!t && world.getBlock(x, y + 1, z) == TFCBlocks.detailed || world.getBlock(x, y + 1, z) == TFCBlocks.stoneSlabs)
		{
			switch(dir)
			{
			case 0:
				if(TFC_Core.isNorthFaceSolid(world, x, y + 1, z) && TFC_Core.isEastFaceSolid(world, x, y + 1, z) && TFC_Core.isWestFaceSolid(world, x, y + 1, z))
					t=true;
				break;
			case 1:
				if(TFC_Core.isEastFaceSolid(world, x, y + 1, z) && TFC_Core.isNorthFaceSolid(world, x, y + 1, z) && TFC_Core.isSouthFaceSolid(world, x, y + 1, z))
					t=true;
				break;
			case 2:
				if(TFC_Core.isSouthFaceSolid(world, x, y + 1, z) && TFC_Core.isEastFaceSolid(world, x, y + 1, z) && TFC_Core.isWestFaceSolid(world, x, y + 1, z))
					t=true;
				break;
			case 3:
				if(TFC_Core.isWestFaceSolid(world, x, y + 1, z) && TFC_Core.isNorthFaceSolid(world, x, y + 1, z) && TFC_Core.isSouthFaceSolid(world, x, y + 1, z))
					t=true;
				break;
			}

			if(!TFC_Core.isBottomFaceSolid(world, x, y + 1, z) || !TFC_Core.isTopFaceSolid(world, x, y + 1, z))
				t = false;
		}

		return b && t;
	}

	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int  z)
	{
		return canBlockStay(world, x, y, z);
	}

	@Override
	public IIcon getIcon(int i, int j)
	{
		int lit = (j & 4) == 4 ? 1 : 0;
		if (!isOpen(j))
		{
			if (lit == 1)
				return textureOn;
		}
		return textureOff;
	}

	@Override
	public void registerBlockIcons(IIconRegister iconRegisterer)
	{
		textureOn = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "devices/Bloomery On");
		textureOff = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "devices/Bloomery Off");
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityliving, ItemStack is)
	{
		if (!world.isRemote)
		{
			int dir = MathHelper.floor_double(entityliving.rotationYaw * 4F / 360F + 0.5D) & 3;
			//l = flipDir(l);
			world.setBlockMetadataWithNotify(x, y, z, dir, 0x2);
			if (!canBlockStay(world, x, y, z))
			{
				dropBlockAsItem(world, x, y, z, new ItemStack(this, 1));
			}
		}
	}

	@Override
	public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z)
	{
		clearStack(world, x, y, z);
		return true;
	}

	public void clearStack(World world, int x, int y, int z)
	{
		if (!world.isRemote)
		{
			world.setBlockToAir(x, y, z);
			int meta = world.getBlockMetadata(x, y, z);
			int[] dir = BLOOMERY_TO_STACK_MAP[meta & 3];
			if (world.getBlock(x + dir[0], y, z + dir[1]) == TFCBlocks.molten)
				world.setBlockToAir(x + dir[0], y, z + dir[1]);
			if (world.getBlock(x + dir[0], y + 1, z + dir[1]) == TFCBlocks.molten)
				world.setBlockToAir(x + dir[0], y + 1, z + dir[1]);
			if (world.getBlock(x + dir[0], y + 2, z + dir[1]) == TFCBlocks.molten)
				world.setBlockToAir(x + dir[0], y + 2, z + dir[1]);
			if (world.getBlock(x + dir[0], y + 3, z + dir[1]) == TFCBlocks.molten)
				world.setBlockToAir(x + dir[0], y + 3, z + dir[1]);
		}
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
	{
		if (!canBlockStay(world, x, y, z))
		{
			if (!tryFlip(world, x, y, z))
			{
				world.setBlockToAir(x, y, z);
				dropBlockAsItem(world, x, y, z, new ItemStack(this, 1));
			}
			else if (!canBlockStay(world, x, y, z))
			{
				world.setBlockToAir(x, y, z);
				dropBlockAsItem(world, x, y, z, new ItemStack(this, 1));
			}
		}
	}
	public static int flipDir(int dir)
	{
		int out = 0;
		if (dir - 2 >= 0)
			out = dir - 2;
		else if (dir + 2 < 4)
			out = dir + 2;
		return out;
	}

	private boolean tryFlip(World world, int x, int y, int z)
	{
		TEBloomery te = (TEBloomery)world.getTileEntity(x, y, z);
		te.swapFlipped();
		return canBlockStay(world, x, y, z);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TEBloomery();
	}

	/*@Override
	public void setBlockBoundsBasedOnState(IBlockAccess access, int i, int j, int k)
	{
		int meta = access.getBlockMetadata(i, j, k) & 3;
		float f = 0.125F;
		if (!isOpen(access.getBlockMetadata(i, j, k)))
		{
			if ((meta & 3) == 0)
			{
				setBlockBounds(0.0F, 0.0F, 0.0f, 1.0f, 1.0F, f);
			}

			if ((meta & 3) == 1)
			{
				setBlockBounds(1.0f - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0f);
			}

			if ((meta & 3) == 2)
			{
				setBlockBounds(0.0f, 0.0F, 1.0f - f, 1.0F, 1.0F, 1.0F);
			}

			if ((meta & 3) == 3)
			{
				setBlockBounds(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
			}
		}
		else
			setBlockBounds(0.0F, 0.0F, 0.0f, 0.0f, 0.0F, 0.0F);
	}*/

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
	{
		return null;
	}

	public static boolean isOpen(int par0)
	{
		return (par0 & 8) != 0;
	}

	@Override
	public int getRenderType()
	{
		return TFCBlocks.bloomeryRenderId;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	public void addCollisionBoxesToList(World world, int x, int y, int z, List list)
	{
		int meta = world.getBlockMetadata(x, y, z);
		int dir = meta & 3;
		if (world.getTileEntity(x, y, z) instanceof TEBloomery)
		{
			TEBloomery te = (TEBloomery) world.getTileEntity(x, y, z);
			if (te.isFlipped)
				dir = flipDir(dir);
		}
		float f = 0.125F;

		if (!BlockEarlyBloomery.isOpen(meta))
		{
			if (dir == 0)
				list.add(new Object[]{AxisAlignedBB.getBoundingBox(0.0F, 0.0F, 0.0f, 1.0f, 1.0F, f)});
			else if (dir == 1)
				list.add(new Object[]{AxisAlignedBB.getBoundingBox(1.0f - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0f)});
			else if (dir == 2)
				list.add(new Object[]{AxisAlignedBB.getBoundingBox(0.0f, 0.0F, 1.0f - f, 1.0F, 1.0F, 1.0F)});
			else if (dir == 3)
				list.add(new Object[]{AxisAlignedBB.getBoundingBox(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F)});
		}
		else
		{
			if (dir == 0)
			{
				list.add(new Object[]{AxisAlignedBB.getBoundingBox(0.0F, 0.0F, 0.0f, f, 1.0F, 0.5F)});
				list.add(new Object[]{AxisAlignedBB.getBoundingBox(1 - f, 0.0F, 0.0f, 1, 1.0F, 0.5F)});
			}
			else if (dir == 1)
			{
				list.add(new Object[]{AxisAlignedBB.getBoundingBox(0.5F, 0.0F, 0, 1.0F, 1.0F, f)});
				list.add(new Object[]{AxisAlignedBB.getBoundingBox(0.5F, 0.0F, 1-f, 1.0F, 1.0F, 1)});
			}
			else if (dir == 2)
			{
				list.add(new Object[]{AxisAlignedBB.getBoundingBox(0.0F, 0.0F, 0.5f, f, 1.0F, 1.0F)});
				list.add(new Object[]{AxisAlignedBB.getBoundingBox(1 - f, 0.0F, 0.5f, 1, 1.0F, 1.0F)});
			}
			else if (dir == 3)
			{
				list.add(new Object[]{AxisAlignedBB.getBoundingBox(0.0F, 0.0F, 0.0F, 0.5F, 1.0F, f)});
				list.add(new Object[]{AxisAlignedBB.getBoundingBox(0.0F, 0.0F, 1-f, 0.5F, 1.0F, 1.0)});
			}
		}
	}

	@Override
	public MovingObjectPosition collisionRayTrace(World world, int x, int y, int z, Vec3 player, Vec3 view)
	{
		return CollisionRayTraceStandard.collisionRayTrace(this, world, x, y, z, player, view);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess bAccess, int x, int y, int z, int side)
	{
		return true;
	}

	@Override
	protected void dropBlockAsItem(World world, int x, int y, int z, ItemStack is)
	{
		if (!world.isRemote && world.getGameRules().getGameRuleBooleanValue("doTileDrops"))
		{
			clearStack(world, x, y, z);
			EntityItem ei = new EntityItem(world, x+0.5, y+0.5, z+0.5, is);
			ei.motionX = 0;
			ei.motionY = 0;
			ei.motionZ = 0;
			ei.delayBeforeCanPickup = 10;
			world.spawnEntityInWorld(ei);
		}
	}
}
