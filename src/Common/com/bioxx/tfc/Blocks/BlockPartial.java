package com.bioxx.tfc.Blocks;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import com.bioxx.tfc.Core.CollisionRayTraceDetailed;
import com.bioxx.tfc.Core.Player.PlayerInfo;
import com.bioxx.tfc.Core.Player.PlayerManagerTFC;
import com.bioxx.tfc.Items.Tools.ItemChisel;
import com.bioxx.tfc.Items.Tools.ItemHammer;
import com.bioxx.tfc.TileEntities.TEPartial;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockPartial extends BlockTerraContainer
{
	public BlockPartial(Material m)
	{
		super(m);
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

	public int xSelected = -10, ySelected = -10, zSelected = -10, chiselmode = 0;

	public MovingObjectPosition collisionRayTrace(World world, int x, int y, int z, Vec3 player, Vec3 view)
	{
		// player
		EntityPlayer entityplayer = getPlayer(world);
		if( entityplayer == null ) {
			setBlockBounds(0f, 0f, 0f, 1f, 1f, 1f);
			return super.collisionRayTrace(world, x, y, z, player, view);
		}

		// tool mode
		chiselmode = getChiselMode(entityplayer);
		if( chiselmode != 1 && chiselmode != 3 ) {
			setBlockBounds(0f, 0f, 0f, 1f, 1f, 1f);
			return super.collisionRayTrace(world, x, y, z, player, view);
		}
		
		//data
		BitSet data = getData(world, x, y, z);
		if( data == null )  {
			setBlockBounds(0f, 0f, 0f, 1f, 1f, 1f);
			return super.collisionRayTrace(world, x, y, z, player, view);
		}
		
		int d = 0;
		if( chiselmode == 1 ) d = 2;
		else if ( chiselmode == 3 ) d = 8;
		
		Vec3 relplayer = player.addVector(-x, -y, -z);
		Vec3 relview = view.addVector(-x, -y, -z);

		List<Object[]> returns = new ArrayList<Object[]>();

		//Creates all possible collisions and returns any that collide
		returns = CollisionRayTraceDetailed.rayTraceSubBlocks( relplayer, relview, x, y, z, returns, data, d);

		//Check if the block itself is being collided with
		Object[] min = null;
		if (!returns.isEmpty()) {
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
		}
		
		if( min == null ) {
			xSelected = -10;
			ySelected = -10;
			zSelected = -10;
			setBlockBounds(0f, 0f, 0f, 0f, 0f, 0f);
			return super.collisionRayTrace(world, x, y, z, player, view);
		}
		else
		{
			xSelected = (Integer) min[3];
			ySelected = (Integer) min[4];
			zSelected = (Integer) min[5];
			setBlockBoundsBasedOnSelection(world, x, y, z);
			return new MovingObjectPosition(x,y,z,
					(Byte) min[1],
					((Vec3) min[0]).addVector(x, y, z));
		}
	}
	
	public void setBlockBoundsBasedOnSelection(World world, int x, int y, int z) 
	{
		if( chiselmode == 1 )
		{
			int d = 2;
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
		else if( chiselmode == 3 )
		{
			int d = 8;
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
		else
		{
			setBlockBounds(0,0,0,1,1,1);
		}
	}

	public EntityPlayer getPlayer(World world)
	{
		UUID uuid = PlayerManagerTFC.getInstance().getClientPlayer().PlayerUUID;
		EntityPlayer entityplayer = null;
		Iterator iterator = world.playerEntities.iterator();
		while (iterator.hasNext())
		{
			EntityPlayer ep = (EntityPlayer)iterator.next();
			if (ep.getPersistentID().equals(uuid)) entityplayer = ep;
		}
		
		return entityplayer;
	}

	public BitSet getData(World world, int x, int y, int z)
	{
		return null;
	}

	public int getChiselMode( EntityPlayer entityplayer ) {
		
		chiselmode = -1;
		boolean hasHammer = false;
		for(int i = 0; i < 9;i++)
			if(entityplayer.inventory.mainInventory[i] != null && entityplayer.inventory.mainInventory[i].getItem() instanceof ItemHammer)
				hasHammer = true;

		boolean hasChisel = entityplayer.getCurrentEquippedItem() != null && entityplayer.getCurrentEquippedItem().getItem() instanceof ItemChisel;
		
		if( !hasChisel || !hasHammer ) return 0;
		
		PlayerInfo pi = PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(entityplayer);
		if(pi!=null) chiselmode = pi.ChiselMode;
		
		return chiselmode;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean addDestroyEffects(World world, int x, int y, int z, int meta, EffectRenderer effectRenderer)
	{
		// TODO Include particle spawning logic, or replace this with a functional getBlockTextureFromSideAndMetadata 
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean addHitEffects(World worldObj, MovingObjectPosition target, EffectRenderer effectRenderer)
	{
		// TODO Include particle spawning logic, or replace this with a functional getBlockTextureFromSideAndMetadata 
		return true;
	}

	@Override
	public void onBlockAdded(World world, int par2, int par3, int par4)
	{
		super.onBlockAdded(world, par2, par3, par4);
		world.markBlockForUpdate(par2, par3, par4);
	}

	@Override
	public void onBlockDestroyedByExplosion(World world, int i, int j, int k, Explosion ex)
	{
		if(!world.isRemote)
		{
		}
	}

	@Override
	public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int l)
	{
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2)
	{
		return new TEPartial();
	}

	@Override
	public int getFlammability(IBlockAccess world, int x, int y, int z, ForgeDirection face)
	{
		TEPartial te = (TEPartial) world.getTileEntity(x, y, z);
		if(te.TypeID >= 0)
			return Blocks.fire.getFlammability(Block.getBlockById(te.TypeID));
		else return 0;
	}

	@Override
	public int getFireSpreadSpeed(IBlockAccess world, int x, int y, int z, ForgeDirection face)
	{
		TEPartial te = (TEPartial) world.getTileEntity(x, y, z);
		if(te.TypeID >= 0)
			return Blocks.fire.getEncouragement(Block.getBlockById(te.TypeID));
		else return 0;
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z)
	{
		return null;
	}
}
