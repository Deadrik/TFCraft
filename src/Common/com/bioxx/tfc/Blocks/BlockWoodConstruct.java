package com.bioxx.tfc.Blocks;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import com.bioxx.tfc.Core.CollisionRayTraceStandard;
import com.bioxx.tfc.TileEntities.TEWoodConstruct;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.Interfaces.ICustomCollision;

public class BlockWoodConstruct extends BlockTerraContainer implements ICustomCollision
{
	public BlockWoodConstruct()
	{
		super(Material.wood);
		setBlockBounds(0.0F, 0.0F, 0.0f, 0.0f, 0.0F, 0.0F);
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2)
	{
		return new TEWoodConstruct();
	}

	@Override
	public int getRenderType()
	{
		return TFCBlocks.woodConstructRenderId;
	}

	@Override
	public boolean getBlocksMovement(IBlockAccess par1IBlockAccess, int i, int j, int k)
	{
		return true;
	}

	@Override
	public void registerBlockIcons(IIconRegister iconRegisterer)
	{
		//Empty On Purpose
	}

	@Override
	public IIcon getIcon(int i, int j)
	{
		return TFCBlocks.planks.getIcon(i, j);
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
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune)
	{
		List<ItemStack> ret = new ArrayList<ItemStack>();

		if(!world.isRemote && (TEWoodConstruct)world.getTileEntity(x, y, z)!=null)
		{
			TEWoodConstruct te = (TEWoodConstruct)world.getTileEntity(x, y, z);
			ret = te.getDrops();
		}
		return (ArrayList<ItemStack>) ret;
	}

	@Override
	public void onBlockHarvested(World world, int x, int y, int z, int meta, EntityPlayer player) 
	{
		ArrayList<ItemStack> out = this.getDrops(world, x, y, z, meta, 0);
		for(ItemStack is : out)
		{
			world.spawnEntityInWorld(new EntityItem(world, x+0.5, y+0.5, z+0.5, is));
		}
	}

	@Override
	public boolean canDropFromExplosion(Explosion ex)
	{
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
	{
		return true;
	}

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
	public void addCollisionBoxesToList(World world, int i, int j, int k, List list) 
	{
		TEWoodConstruct te = (TEWoodConstruct) world.getTileEntity(i, j, k);

		int d = TEWoodConstruct.plankDetailLevel;
		int dd = TEWoodConstruct.plankDetailLevel * TEWoodConstruct.plankDetailLevel;

		float div = 1f / d;

		for(int x = 0; x < dd; x++)
		{
			if(te.data.get(x))
			{
				float minX = 0;
				float maxX = 1;
				float minY = div * (x & 7);
				float maxY = minY + div;
				float minZ = div * (x >> 3);
				float maxZ = minZ + div;
				list.add(new Object[]{AxisAlignedBB.getBoundingBox(minX, minY, minZ, maxX, maxY, maxZ)});
			}
		}

		for(int y = 0; y < dd; y++)
		{
			if(te.data.get(y+dd))
			{
				float minX = div * (y & 7);
				float maxX = minX + div;
				float minY = 0;
				float maxY = 1;
				float minZ = div * (y >> 3);
				float maxZ = minZ + div;
				list.add(new Object[]{AxisAlignedBB.getBoundingBox(minX, minY, minZ, maxX, maxY, maxZ)});
			}
		}

		for(int z = 0; z < dd; z++)
		{
			if(te.data.get(z+(dd*2)))
			{
				float minX = div * (z & 7);
				float maxX = minX + div;
				float minY = div * (z >> 3);
				float maxY = minY + div;
				float minZ = 0;
				float maxZ = 1;
				list.add(new Object[]{AxisAlignedBB.getBoundingBox(minX, minY, minZ, maxX, maxY, maxZ)});
			}
		}
	}

}
