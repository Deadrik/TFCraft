package com.bioxx.tfc.Blocks;

import java.util.List;
import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.bioxx.tfc.TFCBlocks;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Core.Player.PlayerInfo;
import com.bioxx.tfc.Core.Player.PlayerManagerTFC;
import com.bioxx.tfc.Items.Tools.ItemChisel;
import com.bioxx.tfc.Items.Tools.ItemHammer;
import com.bioxx.tfc.TileEntities.TEDetailed;
import com.bioxx.tfc.TileEntities.TEPartial;

public class BlockStair extends BlockPartial
{

	public BlockStair(Material m)
	{
		super(m);
		this.setCreativeTab(TFCTabs.TFCBuilding);
	}

	@Override
	public int getRenderType()
	{
		return TFCBlocks.stairRenderId;
	}

	@Override
	public void registerBlockIcons(IIconRegister iconRegisterer)
	{
	}

	@Override
	public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB AABB, List list, Entity entity)
	{
		int meta = world.getBlockMetadata(x, y, z);
		int rvmeta = meta & 7;
		float var9 = 0.0F;
		float var10 = 0.5F;
		float var11 = 0.5F;
		float var12 = 1.0F;

		if ((meta & 8) != 0)
		{
			var9 = 0.5F;
			var10 = 1.0F;
			var11 = 0.0F;
			var12 = 0.5F;
		}

		this.setBlockBounds(0.0F, var9, 0.0F, 1.0F, var10, 1.0F);
		super.addCollisionBoxesToList(world, x, y, z, AABB, list, entity);

		if (rvmeta == 0 || rvmeta == 4)
		{
			this.setBlockBounds(0.5F, var11, 0.0F, 1.0F, var12, 1.0F);
			super.addCollisionBoxesToList(world, x, y, z, AABB, list, entity);
		}
		else if (rvmeta == 1 || rvmeta == 5)
		{
			this.setBlockBounds(0.0F, var11, 0.0F, 0.5F, var12, 1.0F);
			super.addCollisionBoxesToList(world, x, y, z, AABB, list, entity);
		}
		else if (rvmeta == 2 || rvmeta == 6)
		{
			this.setBlockBounds(0.0F, var11, 0.5F, 1.0F, var12, 1.0F);
			super.addCollisionBoxesToList(world, x, y, z, AABB, list, entity);
		}
		else if (rvmeta == 3 || rvmeta == 7)
		{
			this.setBlockBounds(0.0F, var11, 0.0F, 1.0F, var12, 0.5F);
			super.addCollisionBoxesToList(world, x, y, z, AABB, list, entity);
		}
		
		if (rvmeta == 4)
		{
			this.setBlockBounds(0.0F, var11, 0.0F, 0.5F, var12, 0.5F);
			super.addCollisionBoxesToList(world, x, y, z, AABB, list, entity);
		}
		else if (rvmeta == 5)
		{
			this.setBlockBounds(0.5F, var11, 0.5F, 1.0F, var12, 1.0F);
			super.addCollisionBoxesToList(world, x, y, z, AABB, list, entity);
		}
		else if (rvmeta == 6)
		{
			this.setBlockBounds(0.5F, var11, 0.0F, 1.0F, var12, 0.5F);
			super.addCollisionBoxesToList(world, x, y, z, AABB, list, entity);
		}
		else if (rvmeta == 7)
		{
			this.setBlockBounds(0.0F, var11, 0.5F, 0.5F, var12, 1.0F);
			super.addCollisionBoxesToList(world, x, y, z, AABB, list, entity);
		}

		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
	{
		return super.getCollisionBoundingBoxFromPool(world, x, y, z);
	}

	@Override
	public boolean canDropFromExplosion(Explosion ex)
	{
		return false;
	}

	@Override
	public Item getItemDropped(int metadata, Random rand, int fortune)
	{
		return null;
	}

	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int i, int j, int k)
	{
		return AxisAlignedBB.getBoundingBox(i, j, k, i + 1, j + 1, k + 1);
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess bAccess, int i, int j, int k)
	{
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	}
	@Override
	public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int l)
	{
	}

	@Override
	public boolean isBlockSolid(IBlockAccess world, int x, int y, int z, int side)
	{
		boolean solidSides[][] = {
				{ true, false, false, false, false, true },
				{ true, false, false, false, true, false },
				{ true, false, false, true, false, false },
				{ true, false, true, false, false, false },
				{ false, true, false, false, false, true },
				{ false, true, false, false, true, false },
				{ false, true, false, true, false, false },
				{ false, true, true, false, false, false }
		};

		int meta = world.getBlockMetadata(x, y, z);
		return solidSides[meta][side];
	}

	/**
	 * Called when the block is clicked by a player. Args: x, y, z, entityPlayer
	 */
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityplayer, int side, float hitX, float hitY, float hitZ)  
	{
		boolean hasHammer = false;
		for(int i = 0; i < 9;i++)
		{
			if(entityplayer.inventory.mainInventory[i] != null && entityplayer.inventory.mainInventory[i].getItem() instanceof ItemHammer) {
				hasHammer = true;
			}
		}
		if(entityplayer.getCurrentEquippedItem() != null && entityplayer.getCurrentEquippedItem().getItem() instanceof ItemChisel && hasHammer && !world.isRemote)
		{

			int mode = 0;
			if(!world.isRemote)
			{
				PlayerInfo pi = PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(entityplayer);

				if(pi!=null) {
					mode = pi.ChiselMode;
				}
			} else {
				mode = PlayerManagerTFC.getInstance().getClientPlayer().ChiselMode;
			}

			if(mode == 1)
			{
				ChangeStairs(world, x, y, z, hitX, hitY, hitZ);
				entityplayer.getCurrentEquippedItem().damageItem(1, entityplayer);
				return true;
			}
			else if(mode == 2)
			{
				int[][][] list = StairToDetailledList( world.getBlockMetadata(x, y, z) );
				list = BlockSlab.EmptySlab(side, hitX, hitY, hitZ, list);
				BlockDetailed.ListToDetailled(world, x, y, z, list );
				list = null;
				System.gc();
				
				entityplayer.getCurrentEquippedItem().damageItem(1, entityplayer);
				return true;
			}
		}
		return false;
	}

	public static void ChangeStairs(World world, int x, int y, int z, float hitX, float hitY, float hitZ)
	{
		int meta = world.getBlockMetadata(x, y, z);
		int newmeta = meta;
		
		if( hitY > 0.5F ) {
			if( ( meta == 4 && hitX < 0.5F && hitZ < 0.5F )
			 || ( meta == 6 && hitX < 0.5F && hitZ > 0.5F ) ) newmeta = 0;
			if( ( meta == 7 && hitX > 0.5F && hitZ < 0.5F )
			 || ( meta == 5 && hitX > 0.5F && hitZ > 0.5F ) ) newmeta = 1;
			if( ( meta == 6 && hitX > 0.5F && hitZ < 0.5F )
			 || ( meta == 5 && hitX < 0.5F && hitZ < 0.5F ) ) newmeta = 2;
			if( ( meta == 7 && hitX < 0.5F && hitZ > 0.5F )
			 || ( meta == 4 && hitX > 0.5F && hitZ > 0.5F ) ) newmeta = 3;
		}
		else
		{
			if( ( meta == 12 && hitX < 0.5F && hitZ < 0.5F )
			 || ( meta == 14 && hitX < 0.5F && hitZ > 0.5F ) ) newmeta = 8;
			if( ( meta == 15 && hitX > 0.5F && hitZ < 0.5F )
			 || ( meta == 13 && hitX > 0.5F && hitZ > 0.5F ) ) newmeta = 9;
			if( ( meta == 14 && hitX > 0.5F && hitZ < 0.5F )
			 || ( meta == 13 && hitX < 0.5F && hitZ < 0.5F ) ) newmeta = 10;
			if( ( meta == 15 && hitX < 0.5F && hitZ > 0.5F )
			 || ( meta == 12 && hitX > 0.5F && hitZ > 0.5F ) ) newmeta = 11;
		}
		
		if( meta == newmeta ) {
			int[][][] list = StairToDetailledList(meta);
			list = EmptyLastStair(hitX, hitY, hitZ, list);
			BlockDetailed.ListToDetailled(world, x, y, z, list );
			list = null;
			System.gc();
		} else
			world.setBlockMetadataWithNotify(x, y, z, newmeta, 0x2);
	}

	public static int[][][] StairToDetailledList(int meta)
	{
		int[][][] list = new int[8][8][8];
		
		if( meta != 8 && meta != 10 && meta != 14 )
			for(int subX = 0; subX < 4; subX++) for(int subZ = 0; subZ < 4; subZ++) for(int subY = 0; subY < 4; subY++)
				list[subX][subY][subZ] = 1;
		if( meta != 10 && meta != 9 && meta != 13 )
			for(int subX = 4; subX < 8; subX++) for(int subZ = 0; subZ < 4; subZ++) for(int subY = 0; subY < 4; subY++)
				list[subX][subY][subZ] = 1;
		if( meta != 9 && meta != 11 && meta != 15 )
			for(int subX = 4; subX < 8; subX++) for(int subZ = 4; subZ < 8; subZ++) for(int subY = 0; subY < 4; subY++)
				list[subX][subY][subZ] = 1;
		if( meta != 11 && meta != 8 && meta != 12 )
			for(int subX = 0; subX < 4; subX++) for(int subZ = 4; subZ < 8; subZ++) for(int subY = 0; subY < 4; subY++)
				list[subX][subY][subZ] = 1;
		if( meta != 0 && meta != 2 && meta != 6)
			for(int subX = 0; subX < 4; subX++) for(int subZ = 0; subZ < 4; subZ++) for(int subY = 4; subY < 8; subY++)
				list[subX][subY][subZ] = 1;
		if( meta != 2 && meta != 1 && meta != 5 )
			for(int subX = 4; subX < 8; subX++) for(int subZ = 0; subZ < 4; subZ++) for(int subY = 4; subY < 8; subY++)
				list[subX][subY][subZ] = 1;
		if( meta != 1 && meta != 3 && meta != 7 )
			for(int subX = 4; subX < 8; subX++) for(int subZ = 4; subZ < 8; subZ++) for(int subY = 4; subY < 8; subY++)
				list[subX][subY][subZ] = 1;
		if( meta != 3 && meta != 0 && meta != 4 )
			for(int subX = 0; subX < 4; subX++) for(int subZ = 4; subZ < 8; subZ++) for(int subY = 4; subY < 8; subY++)
				list[subX][subY][subZ] = 1;
		
		return list;
	}
	
	private static int[][][] EmptyLastStair(float hitX, float hitY, float hitZ, int[][][] list) {

		boolean xmymzm = hitX < 0.5F && hitY < 0.5F && hitZ < 0.5F;
		boolean xpymzm = hitX > 0.5F && hitY < 0.5F && hitZ < 0.5F;
		boolean xpymzp = hitX > 0.5F && hitY < 0.5F && hitZ > 0.5F;
		boolean xmymzp = hitX < 0.5F && hitY < 0.5F && hitZ > 0.5F;
		boolean xmypzm = hitX < 0.5F && hitY > 0.5F && hitZ < 0.5F;
		boolean xpypzm = hitX > 0.5F && hitY > 0.5F && hitZ < 0.5F;
		boolean xpypzp = hitX > 0.5F && hitY > 0.5F && hitZ > 0.5F;
		boolean xmypzp = hitX < 0.5F && hitY > 0.5F && hitZ > 0.5F;

		if( xmymzm )
			for(int subX = 0; subX < 4; subX++) for(int subZ = 0; subZ < 4; subZ++) for(int subY = 0; subY < 4; subY++)
				list[subX][subY][subZ] = 0;
		else if( xpymzm )
			for(int subX = 4; subX < 8; subX++) for(int subZ = 0; subZ < 4; subZ++) for(int subY = 0; subY < 4; subY++)
				list[subX][subY][subZ] = 0;
		else if( xpymzp )
			for(int subX = 4; subX < 8; subX++) for(int subZ = 4; subZ < 8; subZ++) for(int subY = 0; subY < 4; subY++)
				list[subX][subY][subZ] = 0;
		else if( xmymzp )
			for(int subX = 0; subX < 4; subX++) for(int subZ = 4; subZ < 8; subZ++) for(int subY = 0; subY < 4; subY++)
				list[subX][subY][subZ] = 0;
		else if( xmypzm )
			for(int subX = 0; subX < 4; subX++) for(int subZ = 0; subZ < 4; subZ++) for(int subY = 4; subY < 8; subY++)
				list[subX][subY][subZ] = 0;
		else if( xpypzm )
			for(int subX = 4; subX < 8; subX++) for(int subZ = 0; subZ < 4; subZ++) for(int subY = 4; subY < 8; subY++)
				list[subX][subY][subZ] = 0;
		else if( xpypzp )
			for(int subX = 4; subX < 8; subX++) for(int subZ = 4; subZ < 8; subZ++) for(int subY = 4; subY < 8; subY++)
				list[subX][subY][subZ] = 0;
		else if( xmypzp )
			for(int subX = 0; subX < 4; subX++) for(int subZ = 4; subZ < 8; subZ++) for(int subY = 4; subY < 8; subY++)
				list[subX][subY][subZ] = 0;
		
		return list;
	}
}
