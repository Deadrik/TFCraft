package TFC.Blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import TFC.TFCBlocks;
import TFC.Core.CollisionRayTracePlanks;
import TFC.TileEntities.TileEntityTerraLogPile;
import TFC.TileEntities.TileEntityWoodConstruct;
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

public class BlockWoodConstruct extends BlockTerraContainer
{	
	public BlockWoodConstruct(int par1) 
	{
		super(par1, Material.wood);
	}

	@Override
	public TileEntity createNewTileEntity(World var1) {
		return new TileEntityWoodConstruct();
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
	public int getBlockTextureFromSideAndMetadata(int i, int j) 
	{
		return j+176;
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
	
	public void Eject(World par1World, int par2, int par3, int par4)
    {
        if(!par1World.isRemote && (TileEntityWoodConstruct)par1World.getBlockTileEntity(par2, par3, par4)!=null)
        {
        	TileEntityWoodConstruct te = (TileEntityWoodConstruct)par1World.getBlockTileEntity(par2, par3, par4);
            te.ejectContents();
            par1World.setBlock(par2, par3, par4, 0);
        }
    }
	@Override
	public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int l)
	{		
		Eject(world,i,j,k);
	}
	@Override
	public void onBlockDestroyedByExplosion(World par1World, int par2, int par3, int par4) 
	{
		Eject(par1World,par2,par3,par4);
	}
	@Override
	public void onBlockDestroyedByPlayer(World par1World, int par2, int par3, int par4, int par5) 
	{
		Eject(par1World,par2,par3,par4);
	}
	@Override
	public boolean removeBlockByPlayer(World par1World, EntityPlayer player, int par2, int par3, int par4) 
	{
		Eject(par1World,par2,par3,par4);
		return true;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
        return true;
    }

	@Override
	public void addCollidingBlockToList(World world, int i, int j, int k, AxisAlignedBB aabb, List list, Entity entity)
	{
		TileEntityWoodConstruct te = (TileEntityWoodConstruct) world.getBlockTileEntity(i, j, k);

		int d = te.PlankDetailLevel;
		int dd = te.PlankDetailLevel * te.PlankDetailLevel;

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

				this.setBlockBounds(minX, minY, minZ, maxX, maxY, maxZ);
				super.addCollidingBlockToList(world, i, j, k, aabb, list, entity);
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

				this.setBlockBounds(minX, minY, minZ, maxX, maxY, maxZ);
				super.addCollidingBlockToList(world, i, j, k, aabb, list, entity);
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

				this.setBlockBounds(minX, minY, minZ, maxX, maxY, maxZ);
				super.addCollidingBlockToList(world, i, j, k, aabb, list, entity);
			}
		}
		setBlockBoundsBasedOnSelection(world, i, j, k);
	}

	public static int index = -10, side = -1;

	@Override
	public MovingObjectPosition collisionRayTrace(World world, int x, int y, int z, Vec3 player, Vec3 view) {

		TileEntityWoodConstruct te = (TileEntityWoodConstruct) world.getBlockTileEntity(x, y, z);

		player = player.addVector(-x, -y, -z);
		view = view.addVector(-x, -y, -z);
		if (te == null) {
			return null;
		}

		List<Object[]> returns = new ArrayList<Object[]>();

		//Creates all possible collisions and returns any that collide
		returns = CollisionRayTracePlanks.rayTracePlanks(
				this,
				player,
				view,
				x,
				y,
				z,
				returns,
				te.data,
				te);

		//Check if the block itself is beign collided with
//		returns = CollisionRayTracePlanks.collisionRayTracer(
//				this,
//				world,
//				player,
//				view,
//				x,
//				y,
//				z,
//				returns);

		if (!returns.isEmpty()) {
			Object[] min = null;
			double distMin = 0;
			for (Object[] ret : returns) 
			{
				double dist = (Double) ret[2];
				if (min == null || dist < distMin) 
				{
					distMin = dist;
					min = ret;
				}
			}
			if (min != null) {
				side = (Byte) min[1];
				index = (Integer) min[3];
				if (te.data.get(index)) 
				{
					int d = TileEntityWoodConstruct.PlankDetailLevel;
					int dd = d*d;
					float div = 1f / d;
					
					if(index < dd)
					{
						float minX = x + 0;
						float maxX = x + 1;
						float minY = y + div * (index & 7);
						float maxY = minX + div;
						float minZ = z + div * (index >> 3);
						float maxZ = minZ + div;
						
						this.setBlockBounds(minX, minY, minZ, maxX, maxY, maxZ);
						rayTraceBound(AxisAlignedBB.getBoundingBox(minX, minY, minZ, maxX, maxY, maxZ), x, y, z, player, view);
					}
					else if(index < dd*2)
					{
						float minX = x + div * (index & 7);
						float maxX = minX + div;
						float minY = y + 0;
						float maxY = y + 1;
						float minZ = z + div * (index >> 3);
						float maxZ = minZ + div;
						
						this.setBlockBounds(minX, minY, minZ, maxX, maxY, maxZ);
						rayTraceBound(AxisAlignedBB.getBoundingBox(minX, minY, minZ, maxX, maxY, maxZ), x, y, z, player, view);
					}
					else
					{
						float minX = x + div * (index & 7);
						float maxX = minX + div;
						float minY = y + div * (index >> 3);
						float maxY = minY + div;
						float minZ = z + 0;
						float maxZ = z + 1;
						
						this.setBlockBounds(minX, minY, minZ, maxX, maxY, maxZ);
						rayTraceBound(AxisAlignedBB.getBoundingBox(minX, minY, minZ, maxX, maxY, maxZ), x, y, z, player, view);
					}
				}
				setBlockBoundsBasedOnSelection(world, x, y, z);

				return new MovingObjectPosition(x,y,z,
						(Byte) min[1],
						((Vec3) min[0]).addVector(x, y, z));
			}
		}
		index = -10;
		side = -1;
		//setBlockBoundsBasedOnSelection(world, x, y, z);

		return null;
	}

	public void setBlockBoundsBasedOnSelection(IBlockAccess access, int x, int y, int z) 
	{
		if(index < 0)
		{
			setBlockBounds(0f, 0f, 0f, 0f, 0f, 0f);
		}
		else
		{
			TileEntityWoodConstruct te = (TileEntityWoodConstruct) access.getBlockTileEntity(x, y, z);
			if(te.data.get(index))
			{
				int d = te.PlankDetailLevel;
				int dd = te.PlankDetailLevel * te.PlankDetailLevel;
				int dd2 = dd*2;

				float div = 1 / d;

				float minX = 0;
				float maxX = 1;
				float minY = 0;
				float maxY = 1;
				float minZ = 0;
				float maxZ = 1;

				if(index < dd)
				{
					minX = 0;
					maxX = 1;
					minY = div * (x & 7);
					maxY = minX + div;
					minZ = div * (x >> 3);
					maxZ = minZ + div;
				}
				else if(index < dd2)
				{
					minX = div * (y & 7);
					maxX = minX + div;
					minY = 0;
					maxY = 1;
					minZ = div * (y >> 3);
					maxZ = minZ + div;
				}
				else
				{
					minX = div * (z & 7);
					maxX = minX + div;
					minY = div * (z >> 3);
					maxY = minY + div;
					minZ = 0;
					maxZ = 1;
				}

				AxisAlignedBB bound = AxisAlignedBB.getBoundingBox(minX, minY, minZ, maxX, maxY, maxZ);
				setBlockBounds(
						(float) bound.minX,
						(float) bound.minY,
						(float) bound.minZ,
						(float) bound.maxX,
						(float) bound.maxY,
						(float) bound.maxZ);
			}
		}
	}

	public Object[] rayTraceBound(AxisAlignedBB bound, int i, int j, int k, Vec3 player, Vec3 view) {
		Vec3 minX = player.getIntermediateWithXValue(view, bound.minX);
		Vec3 maxX = player.getIntermediateWithXValue(view, bound.maxX);
		Vec3 minY = player.getIntermediateWithYValue(view, bound.minY);
		Vec3 maxY = player.getIntermediateWithYValue(view, bound.maxY);
		Vec3 minZ = player.getIntermediateWithZValue(view, bound.minZ);
		Vec3 maxZ = player.getIntermediateWithZValue(view, bound.maxZ);
		if (!isVecInsideYZBounds(bound, minX)) {
			minX = null;
		}
		if (!isVecInsideYZBounds(bound, maxX)) {
			maxX = null;
		}
		if (!isVecInsideXZBounds(bound, minY)) {
			minY = null;
		}
		if (!isVecInsideXZBounds(bound, maxY)) {
			maxY = null;
		}
		if (!isVecInsideXYBounds(bound, minZ)) {
			minZ = null;
		}
		if (!isVecInsideXYBounds(bound, maxZ)) {
			maxZ = null;
		}
		Vec3 tracedBound = null;
		if (minX != null && (tracedBound == null || player.distanceTo(minX) < player
				.distanceTo(tracedBound))) {
			tracedBound = minX;
		}
		if (maxX != null && (tracedBound == null || player.distanceTo(maxX) < player
				.distanceTo(tracedBound))) {
			tracedBound = maxX;
		}
		if (minY != null && (tracedBound == null || player.distanceTo(minY) < player
				.distanceTo(tracedBound))) {
			tracedBound = minY;
		}
		if (maxY != null && (tracedBound == null || player.distanceTo(maxY) < player
				.distanceTo(tracedBound))) {
			tracedBound = maxY;
		}
		if (minZ != null && (tracedBound == null || player.distanceTo(minZ) < player
				.distanceTo(tracedBound))) {
			tracedBound = minZ;
		}
		if (maxZ != null && (tracedBound == null || player.distanceTo(maxZ) < player
				.distanceTo(tracedBound))) {
			tracedBound = maxZ;
		}
		if (tracedBound == null) {
			return null;
		}
		byte side = -1;
		if (tracedBound == minX) {
			side = 4;
		}
		if (tracedBound == maxX) {
			side = 5;
		}
		if (tracedBound == minY) {
			side = 0;
		}
		if (tracedBound == maxY) {
			side = 1;
		}
		if (tracedBound == minZ) {
			side = 2;
		}
		if (tracedBound == maxZ) {
			side = 3;
		}
		return new Object[] { tracedBound, side, player.distanceTo(tracedBound) };
	}

	private boolean isVecInsideYZBounds(AxisAlignedBB bound, Vec3 Vec3) {
		if (Vec3 == null) {
			return false;
		} else {
			return Vec3.yCoord >= bound.minY && Vec3.yCoord <= bound.maxY && Vec3.zCoord >= bound.minZ && Vec3.zCoord <= bound.maxZ;
		}
	}

	private boolean isVecInsideXZBounds(AxisAlignedBB bound, Vec3 Vec3) {
		if (Vec3 == null) {
			return false;
		} else {
			return Vec3.xCoord >= bound.minX && Vec3.xCoord <= bound.maxX && Vec3.zCoord >= bound.minZ && Vec3.zCoord <= bound.maxZ;
		}
	}

	private boolean isVecInsideXYBounds(AxisAlignedBB bound, Vec3 Vec3) {
		if (Vec3 == null) {
			return false;
		} else {
			return Vec3.xCoord >= bound.minX && Vec3.xCoord <= bound.maxX && Vec3.yCoord >= bound.minY && Vec3.yCoord <= bound.maxY;
		}
	}
}
