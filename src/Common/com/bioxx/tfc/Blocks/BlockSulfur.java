package com.bioxx.tfc.Blocks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import net.minecraftforge.common.util.ForgeDirection;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Core.CollisionRayTraceStandard;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.Interfaces.ICustomCollision;

public class BlockSulfur extends BlockTerra implements ICustomCollision
{
	private int itemMeta = Arrays.asList(Global.POWDER).indexOf("Sulfur Powder");
	private IIcon[] icons = new IIcon[4];

	public BlockSulfur(Material material)
	{
		super(material);
	}

	@Override
	public IIcon getIcon(int i, int j) 
	{
		return icons[j];
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k)
	{
		return null;
	}

	@Override
	public int damageDropped(int dmg)
	{
		return itemMeta;
	}

	@Override
	public void registerBlockIcons(IIconRegister registerer)
	{
		for(int i = 0; i < 4; i++)
			icons[i] = registerer.registerIcon(Reference.MOD_ID + ":" + "ores/Sulfur"+i);
	}

	@Override
	public int getRenderType()
	{
		return TFCBlocks.sulfurRenderId;
	}

	@Override
	public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int l)
	{
		//super.harvestBlock(world, entityplayer, i, j, k, l);
		dropBlockAsItem(world, i, j, k, new ItemStack(TFCItems.powder, quantityDropped(new Random()), itemMeta));
	}

	@Override
	public Item getItemDropped(int par1, Random par2Random, int par3) {
		return TFCItems.powder;
	}

	@Override
	public boolean isBlockNormalCube()
	{
		return false;
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
	public void onNeighborBlockChange(World world, int i, int j, int k, Block l)
	{
		int num = 0;
		if (world.getBlock(i, j, k + 1).isSideSolid(world, i, j, k + 1, ForgeDirection.NORTH))
			num++;
		if (world.getBlock(i, j, k - 1).isSideSolid(world, i, j, k - 1, ForgeDirection.SOUTH))
			num++;
		if (world.getBlock(i + 1, j, k).isSideSolid(world, i + 1, j, k, ForgeDirection.WEST))
			num++;
		if (world.getBlock(i - 1, j, k).isSideSolid(world, i - 1, j, k, ForgeDirection.EAST))
			num++;
		if (world.getBlock(i, j + 1, k).isSideSolid(world, i, j + 1, k, ForgeDirection.DOWN))
			num++;
		if (world.getBlock(i, j - 1, k).isSideSolid(world, i, j - 1, k, ForgeDirection.UP))
			num++;
		if(num == 0)
		{
			world.setBlockToAir(i, j, k);
			dropBlockAsItem(world, i, j, k, new ItemStack(TFCItems.powder, quantityDropped(new Random()), itemMeta));
		}
	}

	@Override
	public int quantityDropped(Random random)
	{
		return 1 + random.nextInt(5);
	}

	/*@Override
	public void setBlockBoundsBasedOnState(IBlockAccess iblockaccess, int i, int j, int k)
	{
		int num = 0;
		if(iblockaccess.getBlock(i, j, k+1).isBlockNormalCube())
		{
			setBlockBounds(0.0F, 0.0F, 0.99F, 1.0F, 1.0F, 1.0F);
			num++;
		}
		if(iblockaccess.getBlock(i, j, k-1).isBlockNormalCube())
		{
			setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.01F);
			num++;
		}
		if(iblockaccess.getBlock(i+1, j, k).isBlockNormalCube())
		{
			setBlockBounds(0.99F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
			num++;
		}
		if(iblockaccess.getBlock(i-1, j, k).isBlockNormalCube())
		{
			setBlockBounds(0.0F, 0.0F, 0.0F, 0.01F, 1.0F, 1.0F);
			num++;
		}
		if(iblockaccess.getBlock(i, j+1, k).isBlockNormalCube())
		{
			setBlockBounds(0.0F, 0.99F, 0.0F, 1.0F, 1.0F, 1.0F);
			num++;
		}
		if(iblockaccess.getBlock(i, j-1, k).isBlockNormalCube())
		{
			setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.01F, 1.0F);
			num++;
		}
		if(num > 1)
		{
			setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		}
	}*/

	@Override
	public void addCollisionBoxesToList(World world, int i, int j, int k, AxisAlignedBB aabb, List list, Entity entity)
	{
		ArrayList<Object[]> alist = new ArrayList<Object[]>();
		addCollisionBoxesToList(world, i, j, k, alist);
		for(Object[] obj : alist)
		{
			AxisAlignedBB plankAABB = (AxisAlignedBB)obj[0];
			plankAABB.minX += i; plankAABB.maxX += i;
			plankAABB.minY += j; plankAABB.maxY += j;
			plankAABB.minZ += k; plankAABB.maxZ += k;
			if (aabb.intersectsWith(plankAABB))
			{
				list.add(plankAABB);
			}
		}
	}

	@Override
	public MovingObjectPosition collisionRayTrace(World world, int x, int y, int z, Vec3 player, Vec3 view)
	{
		return CollisionRayTraceStandard.collisionRayTrace(this, world, x, y, z, player, view);
	}

	@Override
	public void addCollisionBoxesToList(World world, int x, int y, int z, List list) 
	{
		if (world.getBlock(x, y, z + 1).isSideSolid(world, x, y, z + 1, ForgeDirection.NORTH))
		{
			list.add(new Object[]{AxisAlignedBB.getBoundingBox(0.0F, 0.0F, 0.99F, 1.0F, 1.0F, 1.0F)});
		}
		if (world.getBlock(x, y, z - 1).isSideSolid(world, x, y, z - 1, ForgeDirection.SOUTH))
		{
			list.add(new Object[]{AxisAlignedBB.getBoundingBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.01F)});
		}
		if (world.getBlock(x + 1, y, z).isSideSolid(world, x + 1, y, z, ForgeDirection.EAST))
		{
			list.add(new Object[]{AxisAlignedBB.getBoundingBox(0.99F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F)});
		}
		if (world.getBlock(x - 1, y, z).isSideSolid(world, x - 1, y, z, ForgeDirection.WEST))
		{
			list.add(new Object[]{AxisAlignedBB.getBoundingBox(0.0F, 0.0F, 0.0F, 0.01F, 1.0F, 1.0F)});
		}
		if (world.getBlock(x, y + 1, z).isSideSolid(world, x, y + 1, z, ForgeDirection.DOWN))
		{
			list.add(new Object[]{AxisAlignedBB.getBoundingBox(0.0F, 0.99F, 0.0F, 1.0F, 1.0F, 1.0F)});
		}
		if (world.getBlock(x, y - 1, z).isSideSolid(world, x, y - 1, z, ForgeDirection.UP))
		{
			list.add(new Object[]{AxisAlignedBB.getBoundingBox(0.0F, 0.0F, 0.0F, 1.0F, 0.01F, 1.0F)});
		}
	}
}
