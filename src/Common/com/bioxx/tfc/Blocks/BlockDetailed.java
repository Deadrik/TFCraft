package com.bioxx.tfc.Blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import net.minecraftforge.common.util.ForgeDirection;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import com.bioxx.tfc.Core.CollisionRayTraceDetailed;
import com.bioxx.tfc.Core.Player.PlayerInfo;
import com.bioxx.tfc.Core.Player.PlayerManagerTFC;
import com.bioxx.tfc.Items.Tools.ItemChisel;
import com.bioxx.tfc.Items.Tools.ItemHammer;
import com.bioxx.tfc.TileEntities.TEDetailed;
import com.bioxx.tfc.TileEntities.TEWoodConstruct;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.TFCOptions;

public class BlockDetailed extends BlockPartial
{
	public static int lockX;
	public static int lockY;
	public static int lockZ;

	public BlockDetailed()
	{
		super(Material.rock);
	}

	@Override
	public int getRenderType()
	{
		return TFCBlocks.detailedRenderId;
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

	@Override
	public boolean getBlocksMovement(IBlockAccess bAccess, int i, int j, int k)
	{
		return true;
	}

	@Override
	public void registerBlockIcons(IIconRegister iconRegisterer)
	{
	}

	@Override
	public IIcon getIcon(IBlockAccess bAccess, int x, int y, int z, int side)
	{
		TEDetailed te = ((TEDetailed)bAccess.getTileEntity(x, y, z));
		return te.getBlockType().getIcon(side, te.metaID);
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2)
	{
		return new TEDetailed();
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side)
	{
		if (!TFCOptions.enableSolidDetailed)
			return false;
		if (side == ForgeDirection.UNKNOWN)
			return false;

		int transpCount = TFCOptions.maxRemovedSolidDetailed;
		if (transpCount < 0 || transpCount >= 64)
			return false;

		TEDetailed te = (TEDetailed) world.getTileEntity(x, y, z);

		int startX = (side == ForgeDirection.EAST ? 7 : 0);
		int endX = (side == ForgeDirection.WEST ? 1 : 8);

		int startY = (side == ForgeDirection.UP ? 7 : 0);
		int endY = (side == ForgeDirection.DOWN ? 1 : 8);

		int startZ = (side == ForgeDirection.SOUTH ? 7 : 0);
		int endZ = (side == ForgeDirection.NORTH ? 1 : 8);

		for (int subX = startX; subX < endX; ++subX)
			for (int subY = startY; subY < endY; ++subY)
				for (int subZ = startZ; subZ < endZ; ++subZ)
					if (!te.getBlockExists(subX, subY, subZ) && --transpCount < 0)
						return false;

		return true;
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess bAccess, int x, int y, int z, int side)
	{
		return true;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityplayer, int side, float hitX, float hitY, float hitZ) 
	{
		boolean hasHammer = false;
		PlayerInfo pi = PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(entityplayer);
		for(int i = 0; i < 9;i++)
		{
			if(entityplayer.inventory.mainInventory[i] != null && entityplayer.inventory.mainInventory[i].getItem() instanceof ItemHammer)
				hasHammer = true;
		}

		if(entityplayer.getCurrentEquippedItem() != null && entityplayer.getCurrentEquippedItem().getItem() instanceof ItemChisel && hasHammer && world.isRemote && pi.lockMatches(x, y, z))
		{
			TEDetailed te = (TEDetailed) world.getTileEntity(x, y, z);
			lockX = x; lockY = y; lockZ = z;
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setByte("packetType", TEDetailed.PACKET_ACTIVATE);
			nbt.setInteger("xSelected", xSelected);
			nbt.setInteger("ySelected", ySelected);
			nbt.setInteger("zSelected", zSelected);
			te.createDataNBT(nbt);
			te.broadcastPacketInRange(te.createDataPacket(nbt));
		}
		return false;
	}

	public boolean onBlockActivatedServer(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) 
	{
		int mode = 0;
		PlayerInfo pi = PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(player);
		if(pi!=null)
			mode = pi.chiselMode;

		TEDetailed te = (TEDetailed) world.getTileEntity(x, y, z);

		int hasChisel = -1;
		int hasHammer = -1;

		for(int i = 0; i < 9;i++)
		{
			if(player.inventory.mainInventory[i] != null && player.inventory.mainInventory[i].getItem() instanceof ItemHammer)
				hasHammer = i;
			if(player.inventory.mainInventory[i] != null && player.inventory.mainInventory[i].getItem() instanceof ItemChisel)
				hasChisel = i;
		}

		if(mode == 1)
		{
			int index = -10;

			if( xSelected < 4 && ySelected < 4 && zSelected < 4 )
				for(int subX = 0; subX < 4; subX++) for(int subZ = 0; subZ < 4; subZ++) for(int subY = 0; subY < 4; subY++) {
					index = (subX * 8 + subZ) * 8 + subY;
					deleteBox(world, x, y, z, player, te, index, hasChisel, hasHammer);
				}
			if( xSelected > 3 && ySelected < 4 && zSelected < 4 )
				for(int subX = 4; subX < 8; subX++) for(int subZ = 0; subZ < 4; subZ++) for(int subY = 0; subY < 4; subY++) {
					index = (subX * 8 + subZ) * 8 + subY;
					deleteBox(world, x, y, z, player, te, index, hasChisel, hasHammer);
				}
			if( xSelected > 3 && ySelected < 4 && zSelected > 3 )
				for(int subX = 4; subX < 8; subX++) for(int subZ = 4; subZ < 8; subZ++) for(int subY = 0; subY < 4; subY++) {
					index = (subX * 8 + subZ) * 8 + subY;
					deleteBox(world, x, y, z, player, te, index, hasChisel, hasHammer);
				}
			if( xSelected < 4 && ySelected < 4 && zSelected > 3 )
				for(int subX = 0; subX < 4; subX++) for(int subZ = 4; subZ < 8; subZ++) for(int subY = 0; subY < 4; subY++) {
					index = (subX * 8 + subZ) * 8 + subY;
					deleteBox(world, x, y, z, player, te, index, hasChisel, hasHammer);
				}
			if( xSelected < 4 && ySelected > 3 && zSelected < 4 )
				for(int subX = 0; subX < 4; subX++) for(int subZ = 0; subZ < 4; subZ++) for(int subY = 4; subY < 8; subY++) {
					index = (subX * 8 + subZ) * 8 + subY;
					deleteBox(world, x, y, z, player, te, index, hasChisel, hasHammer);
				}
			if( xSelected > 3 && ySelected > 3 && zSelected < 4 )
				for(int subX = 4; subX < 8; subX++) for(int subZ = 0; subZ < 4; subZ++) for(int subY = 4; subY < 8; subY++) {
					index = (subX * 8 + subZ) * 8 + subY;
					deleteBox(world, x, y, z, player, te, index, hasChisel, hasHammer);
				}
			if( xSelected > 3 && ySelected > 3 && zSelected > 3 )
				for(int subX = 4; subX < 8; subX++) for(int subZ = 4; subZ < 8; subZ++) for(int subY = 4; subY < 8; subY++) {
					index = (subX * 8 + subZ) * 8 + subY;
					deleteBox(world, x, y, z, player, te, index, hasChisel, hasHammer);
				}
			if( xSelected < 4 && ySelected > 3 && zSelected > 3 )
				for(int subX = 0; subX < 4; subX++) for(int subZ = 4; subZ < 8; subZ++) for(int subY = 4; subY < 8; subY++) {
					index = (subX * 8 + subZ) * 8 + subY;
					deleteBox(world, x, y, z, player, te, index, hasChisel, hasHammer);
				}

			return true;
		}
		else if(mode == 3 && xSelected != -10)
		{
			int index = (xSelected * 8 + zSelected) * 8 + ySelected;

			if(index >= 0)
			{
				deleteBox(world, x, y, z, player, te, index, hasChisel, hasHammer);
			}
			return true;
		}
		return false;
	}

	public void deleteBox(World world, int x, int y, int z, EntityPlayer player, TEDetailed te, int index, int hasChisel, int hasHammer)
	{
		te.data.clear(index);
		te.clearQuad(xSelected, ySelected, zSelected);
		if(te.isBlockEmpty())
		{
			world.setBlockToAir(x, y, z);
		}
		if(player.inventory.mainInventory[hasChisel] != null)
			player.inventory.mainInventory[hasChisel].damageItem(1, player);

		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setByte("packetType", TEDetailed.PACKET_UPDATE);
		nbt.setInteger("index", index);
		te.createDataNBT(nbt);
		te.broadcastPacketInRange(te.createDataPacket(nbt));
	}

	@Override
	public void addCollisionBoxesToList(World world, int i, int j, int k, AxisAlignedBB aabb, List list, Entity entity)
	{
		TEDetailed te = (TEDetailed) world.getTileEntity(i, j, k);
		float div = 1f / 8;

		for(int subX = 0; subX < 8; subX++)
		{
			for(int subZ = 0; subZ < 8; subZ++)
			{
				for(int subY = 0; subY < 8; subY++)
				{
					if (te.data.get((subX * 8 + subZ)*8 + subY))
					{
						float minX = subX * div;
						float maxX = minX + div;
						float minY = subY * div;
						float maxY = minY + div;
						float minZ = subZ * div;
						float maxZ = minZ + div;

						this.setBlockBounds(minX, minY, minZ, maxX, maxY, maxZ);
						super.addCollisionBoxesToList(world, i, j, k, aabb, list, entity);
					}
				}
			}
		}
		setBlockBoundsBasedOnSelection(world, i, j, k);
	}


	public int xSelected = -10, ySelected = -10, zSelected = -10, side = -1;

	@Override
	public MovingObjectPosition collisionRayTrace(World world, int x, int y, int z, Vec3 player, Vec3 view)
	{
		TEDetailed te = (TEDetailed) world.getTileEntity(x, y, z);

		player = player.addVector(-x, -y, -z);
		view = view.addVector(-x, -y, -z);
		if (te == null)
			return null;

		List<Object[]> returns = new ArrayList<Object[]>();

		//Creates all possible collisions and returns any that collide
		returns = CollisionRayTraceDetailed.rayTraceSubBlocks(
				this,
				player,
				view,
				x,
				y,
				z,
				returns,
				te.data,
				te);

		//Check if the block itself is being collided with
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
			if (min != null)
			{
				side = (Byte) min[1];
				xSelected = (Integer) min[3];
				ySelected = (Integer) min[4];
				zSelected = (Integer) min[5];

				int index = (xSelected * 8 + zSelected)*8 + ySelected;

				if (index >= 0 && te.data.get(index)) 
				{
					int d = TEWoodConstruct.plankDetailLevel;
					//int dd = d*d;
					float div = 1f / d;

					float minX = x + xSelected * div;
					float maxX = minX + div;
					float minY = y + ySelected * div;
					float maxY = minY + div;
					float minZ = z + zSelected * div;
					float maxZ = minZ + div;

					this.setBlockBounds(minX, minY, minZ, maxX, maxY, maxZ);
					rayTraceBound(AxisAlignedBB.getBoundingBox(minX, minY, minZ, maxX, maxY, maxZ), x, y, z, player, view);
				}
				setBlockBoundsBasedOnSelection(world, x, y, z);

				lockX = x; lockY = y; lockZ = z;

				return new MovingObjectPosition(x,y,z,
						(Byte) min[1],
						((Vec3) min[0]).addVector(x, y, z));
			}
		}
		xSelected = -10;
		ySelected = -10;
		zSelected = -10;
		side = -1;
		setBlockBoundsBasedOnSelection(world, x, y, z);

		return null;
	}

	public void setBlockBoundsBasedOnSelection(IBlockAccess access, int x, int y, int z) 
	{
		if(xSelected == -10)
		{
			setBlockBounds(0f, 0f, 0f, 0f, 0f, 0f);
		}
		else
		{
			TEDetailed te = (TEDetailed) access.getTileEntity(x, y, z);
			int index = (xSelected * 8 + zSelected) * 8 + ySelected;

			if(index >= 0 && te.data.get(index))
			{
				int d = 8;
				//int dd = d * d;
				//int dd2 = dd*2;

				float div = 1f / d;

				float minX = xSelected * div;
				float maxX = minX + div;
				float minY = ySelected * div;
				float maxY = minY + div;
				float minZ = zSelected * div;
				float maxZ = minZ + div;

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

	@SuppressWarnings("null")
	public Object[] rayTraceBound(AxisAlignedBB bound, int i, int j, int k, Vec3 player, Vec3 view) {
		Vec3 minX = player.getIntermediateWithXValue(view, bound.minX);
		Vec3 maxX = player.getIntermediateWithXValue(view, bound.maxX);
		Vec3 minY = player.getIntermediateWithYValue(view, bound.minY);
		Vec3 maxY = player.getIntermediateWithYValue(view, bound.maxY);
		Vec3 minZ = player.getIntermediateWithZValue(view, bound.minZ);
		Vec3 maxZ = player.getIntermediateWithZValue(view, bound.maxZ);
		if (!isVecInsideYZBounds(bound, minX))
			minX = null;
		if (!isVecInsideYZBounds(bound, maxX))
			maxX = null;
		if (!isVecInsideXZBounds(bound, minY))
			minY = null;
		if (!isVecInsideXZBounds(bound, maxY))
			maxY = null;
		if (!isVecInsideXYBounds(bound, minZ))
			minZ = null;
		if (!isVecInsideXYBounds(bound, maxZ))
			maxZ = null;

		Vec3 tracedBound = null;
		if (minX != null && (tracedBound == null || player.distanceTo(minX) < player.distanceTo(tracedBound)))
			tracedBound = minX;
		if (maxX != null && (tracedBound == null || player.distanceTo(maxX) < player.distanceTo(tracedBound)))
			tracedBound = maxX;
		if (minY != null && (tracedBound == null || player.distanceTo(minY) < player.distanceTo(tracedBound)))
			tracedBound = minY;
		if (maxY != null && (tracedBound == null || player.distanceTo(maxY) < player.distanceTo(tracedBound)))
			tracedBound = maxY;
		if (minZ != null && (tracedBound == null || player.distanceTo(minZ) < player.distanceTo(tracedBound)))
			tracedBound = minZ;
		if (maxZ != null && (tracedBound == null || player.distanceTo(maxZ) < player.distanceTo(tracedBound)))
			tracedBound = maxZ;
		if (tracedBound == null)
			return null;

		byte side = -1;
		if (tracedBound == minX)
			side = 4;
		if (tracedBound == maxX)
			side = 5;
		if (tracedBound == minY)
			side = 0;
		if (tracedBound == maxY)
			side = 1;
		if (tracedBound == minZ)
			side = 2;
		if (tracedBound == maxZ)
			side = 3;

		return new Object[] { tracedBound, side, player.distanceTo(tracedBound) };
	}

	private boolean isVecInsideYZBounds(AxisAlignedBB bound, Vec3 vec3) {
		if (vec3 == null)
			return false;
		else
			return vec3.yCoord >= bound.minY && vec3.yCoord <= bound.maxY && vec3.zCoord >= bound.minZ && vec3.zCoord <= bound.maxZ;
	}

	private boolean isVecInsideXZBounds(AxisAlignedBB bound, Vec3 vec3) {
		if (vec3 == null)
			return false;
		else
			return vec3.xCoord >= bound.minX && vec3.xCoord <= bound.maxX && vec3.zCoord >= bound.minZ && vec3.zCoord <= bound.maxZ;
	}

	private boolean isVecInsideXYBounds(AxisAlignedBB bound, Vec3 vec3) {
		if (vec3 == null)
			return false;
		else
			return vec3.xCoord >= bound.minX && vec3.xCoord <= bound.maxX && vec3.yCoord >= bound.minY && vec3.yCoord <= bound.maxY;
	}

	@Override
	public int getFlammability(IBlockAccess world, int x, int y, int z, ForgeDirection face)
	{
		TEDetailed te = (TEDetailed) world.getTileEntity(x, y, z);
		if(te.typeID >= 0)
			return Blocks.fire.getFlammability(Block.getBlockById(te.typeID));
		else return 0;
	}

	@Override
	public int getFireSpreadSpeed(IBlockAccess world, int x, int y, int z, ForgeDirection face)
	{
		TEDetailed te = (TEDetailed) world.getTileEntity(x, y, z);
		if(te.typeID >= 0)
			return Blocks.fire.getEncouragement(Block.getBlockById(te.typeID));
		else return 0;
	}
	
	@Override
	public Item getItemDropped(int metadata, Random rand, int fortune)
	{
		return null;
	}
}
