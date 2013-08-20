package TFC.Blocks;

import java.util.ArrayList;
import java.util.List;
import TFC.*;
import TFC.Core.*;
import TFC.Core.Player.PlayerInfo;
import TFC.Core.Player.PlayerManagerTFC;
import TFC.Items.Tools.ItemChisel;
import TFC.Items.Tools.ItemHammer;
import TFC.TileEntities.TileEntityDetailed;
import TFC.TileEntities.TileEntityPartial;
import TFC.TileEntities.TileEntityWoodConstruct;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.renderer.texture.IconRegister;
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

public class BlockDetailed extends BlockPartial
{
	public static int lockX = 0;
	public static int lockY = 0;
	public static int lockZ = 0;
	
	public BlockDetailed(int par1)
	{
		super(par1, Material.rock);
	}

	@Override
	public int getRenderType()
	{
		return TFCBlocks.detailedRenderId;
	}

    @SideOnly(Side.CLIENT)
    public boolean addBlockDestroyEffects(World world, int x, int y, int z, int meta, EffectRenderer effectRenderer)
    { 
        return true;
    }
	
    @SideOnly(Side.CLIENT)
    public boolean addBlockHitEffects(World worldObj, MovingObjectPosition target, EffectRenderer effectRenderer)
    { 
        return true;
    }
	@Override
	public boolean getBlocksMovement(IBlockAccess par1IBlockAccess, int i, int j, int k)
	{
		return true;
	}
	
	@Override
    public void registerIcons(IconRegister iconRegisterer)
    {
    }

	@Override
	public TileEntity createNewTileEntity(World var1) 
	{
		return new TileEntityDetailed();
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
	@SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
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
		if(entityplayer.getCurrentEquippedItem() != null && entityplayer.getCurrentEquippedItem().getItem() instanceof ItemChisel && hasHammer && world.isRemote &&
				pi.lockMatches(x, y, z))
		{
			TileEntityDetailed te = (TileEntityDetailed) world.getBlockTileEntity(x, y, z);
			
			lockX = x; lockY = y; lockZ = z;
			
			TerraFirmaCraft.proxy.sendCustomPacket(te.createActivatePacket(xSelected, ySelected, zSelected));
		}
		return false;
	}
	
	public boolean onBlockActivatedServer(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) 
	{
			int id = world.getBlockId(x, y, z);
			byte meta = (byte) world.getBlockMetadata(x, y, z);

			int mode = 0;
			PlayerInfo pi = PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(player);
			if(pi!=null) mode = pi.ChiselMode;
			
			int hasChisel = -1;
			int hasHammer = -1;

			for(int i = 0; i < 9;i++)
			{
				if(player.inventory.mainInventory[i] != null && player.inventory.mainInventory[i].getItem() instanceof ItemHammer)
					hasHammer = i;
				if(player.inventory.mainInventory[i] != null && player.inventory.mainInventory[i].getItem() instanceof ItemChisel)
					hasChisel = i;
			}

			if(mode == 3 && xSelected != -10)
			{
				//ItemChisel.CreateDetailed(world, x, y, z, id, meta, side, hitX, hitY, hitZ);
				TileEntityDetailed te = (TileEntityDetailed) world.getBlockTileEntity(x, y, z);
				int index = (xSelected * 8 + zSelected)*8 + ySelected;

				if(index >= 0)
				{
					System.out.println("xSelected: " +xSelected + " ySelected: " + ySelected + " zSelected: " + zSelected + " index: " + index);
					te.data.clear(index);
					te.clearQuad(xSelected, ySelected, zSelected);
					
					if(player.inventory.mainInventory[hasChisel] != null)
						player.inventory.mainInventory[hasChisel].damageItem(1, player);

					if(player.inventory.mainInventory[hasHammer] != null)
						player.inventory.mainInventory[hasHammer].damageItem(1, player);
					
					te.broadcastPacketInRange(te.createUpdatePacket(index));
				}
				return true;
			}
		return false;
	}

	@Override
	public void addCollisionBoxesToList(World world, int i, int j, int k, AxisAlignedBB aabb, List list, Entity entity)
	{
		TileEntityDetailed te = (TileEntityDetailed) world.getBlockTileEntity(i, j, k);
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
	public MovingObjectPosition collisionRayTrace(World world, int x, int y, int z, Vec3 player, Vec3 view) {

		TileEntityDetailed te = (TileEntityDetailed) world.getBlockTileEntity(x, y, z);

		player = player.addVector(-x, -y, -z);
		view = view.addVector(-x, -y, -z);
		if (te == null) {
			return null;
		}

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
		/*
		 * With this enabled, the chisel can't chisel past the outer layer
		 * */
//			returns = CollisionRayTraceDetailed.collisionRayTracer(
//					this,
//					world,
//					player,
//					view,
//					x,
//					y,
//					z,
//					returns);

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
				xSelected = (Integer) min[3];
				ySelected = (Integer) min[4];
				zSelected = (Integer) min[5];

				int index = (xSelected * 8 + zSelected)*8 + ySelected;

				if (index >= 0 && te.data.get(index)) 
				{
					int d = TileEntityWoodConstruct.PlankDetailLevel;
					int dd = d*d;
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
			TileEntityDetailed te = (TileEntityDetailed) access.getBlockTileEntity(x, y, z);
			int index = (xSelected * 8 + zSelected)*8 + ySelected;

			if(index >= 0 && te.data.get(index))
			{
				int d = 8;
				int dd = d * d;
				int dd2 = dd*2;

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
