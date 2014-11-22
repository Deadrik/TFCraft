package com.bioxx.tfc.Blocks;

import java.util.BitSet;
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
		float var9 = 0;
		if ((meta & 8) != 0) var9 = .5F;

			addCollisionBoxes(world, x, y, z, AABB, list, entity, 0  ,   0+var9, 0  , 1  , .5F+var9, 1);

		if (rvmeta == 0 || rvmeta == 4)
			addCollisionBoxes(world, x, y, z, AABB, list, entity, .5F, .5F-var9, 0  , 1  ,   1-var9, 1);
		else if (rvmeta == 1 || rvmeta == 5)
			addCollisionBoxes(world, x, y, z, AABB, list, entity, 0  , .5F-var9, 0  , .5F,   1-var9, 1);
		else if (rvmeta == 2 || rvmeta == 6)
			addCollisionBoxes(world, x, y, z, AABB, list, entity, 0  , .5F-var9, .5F, 1  ,   1-var9, 1);
		else if (rvmeta == 3 || rvmeta == 7)
			addCollisionBoxes(world, x, y, z, AABB, list, entity, 0  , .5F-var9, 0  , 1  ,   1-var9, .5F);
		
		if (rvmeta == 4)
			addCollisionBoxes(world, x, y, z, AABB, list, entity, 0  , .5F-var9, 0  , .5F,   1-var9, .5F);
		else if (rvmeta == 5)
			addCollisionBoxes(world, x, y, z, AABB, list, entity, .5F, .5F-var9, .5F, 1  ,   1-var9, 1);
		else if (rvmeta == 6)
			addCollisionBoxes(world, x, y, z, AABB, list, entity, .5F, .5F-var9, 0  , 1  ,   1-var9, .5F);
		else if (rvmeta == 7)
			addCollisionBoxes(world, x, y, z, AABB, list, entity, 0  , .5F-var9, .5F, .5F,   1-var9, 1);

		setBlockBoundsBasedOnSelection(world, x, y, z);
	}
	
	public void addCollisionBoxes(World world, int x, int y, int z, AxisAlignedBB AABB, List list, Entity entity, float bx, float by, float bz, float tx, float ty, float tz)
	{
		this.setBlockBounds(bx, by, bz, tx, ty, tz);
		super.addCollisionBoxesToList(world, x, y, z, AABB, list, entity);
	}

	@Override
	public BitSet getData( World world, int x, int y, int z ) {
		
		BitSet data = new BitSet(8);
		int meta = world.getBlockMetadata(x, y, z);
		
		if( chiselmode == 1 ) {
			if( meta !=  8 && meta != 10 && meta != 14 ) data.set((0 * 2 + 0)*2 + 0);
			if( meta != 10 && meta !=  9 && meta != 13 ) data.set((1 * 2 + 0)*2 + 0);
			if( meta !=  0 && meta !=  2 && meta !=  6 ) data.set((0 * 2 + 0)*2 + 1);
			if( meta !=  2 && meta !=  1 && meta !=  5 ) data.set((1 * 2 + 0)*2 + 1);
			if( meta != 11 && meta !=  8 && meta != 12 ) data.set((0 * 2 + 1)*2 + 0);
			if( meta !=  9 && meta != 11 && meta != 15 ) data.set((1 * 2 + 1)*2 + 0);
			if( meta !=  3 && meta !=  0 && meta !=  4 ) data.set((0 * 2 + 1)*2 + 1);
			if( meta !=  1 && meta !=  3 && meta !=  7 ) data.set((1 * 2 + 1)*2 + 1);
		} else if ( chiselmode == 3 ) {
			data = metaToDetailed(meta);
		}
		
		return data;
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
	public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int l)
	{
	}

	@Override
	public boolean isBlockSolid(IBlockAccess world, int x, int y, int z, int side)
	{
		boolean solidSides[][] = {
				{ true, false, false, false, false, true  },
				{ true, false, false, false, true , false },
				{ true, false, false, true , false, false },
				{ true, false, true , false, false, false },
				{ true, false, true , false, false, true  },//4
				{ true, false, false, true , true , false },//5
				{ true, false, false, true , false, true  },//6
				{ true, false, true , false, true , false },//7
				{ false, true, false, false, false, true  },
				{ false, true, false, false, true , false },
				{ false, true, false, true , false, false },
				{ false, true, true , false, false, false },
				{ false, true, true , false, false, true  },//12
				{ false, true, false, true , true , false },//13
				{ false, true, false, true , false, true  },//14
				{ false, true, true , false, true , false } //15
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
			else if(mode == 3)
			{
				int meta = world.getBlockMetadata(x, y, z);
				BitSet data = metaToDetailed(meta);
				data = BlockDetailed.EmptyDetailedInt(xSelected, ySelected, zSelected, data);
				BlockDetailed.BitSetToDetailled(world, x, y, z, data );
				data = null;
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
			if( ( meta == 4 && hitX <= 0.5F && hitZ <= 0.5F )
			 || ( meta == 6 && hitX <= 0.5F && hitZ >= 0.5F ) ) newmeta = 0;
			if( ( meta == 7 && hitX >= 0.5F && hitZ <= 0.5F )
			 || ( meta == 5 && hitX >= 0.5F && hitZ >= 0.5F ) ) newmeta = 1;
			if( ( meta == 6 && hitX >= 0.5F && hitZ <= 0.5F )
			 || ( meta == 5 && hitX <= 0.5F && hitZ <= 0.5F ) ) newmeta = 2;
			if( ( meta == 7 && hitX <= 0.5F && hitZ >= 0.5F )
			 || ( meta == 4 && hitX >= 0.5F && hitZ >= 0.5F ) ) newmeta = 3;
		}
		else
		{
			if( ( meta == 12 && hitX <= 0.5F && hitZ <= 0.5F )
			 || ( meta == 14 && hitX <= 0.5F && hitZ >= 0.5F ) ) newmeta = 8;
			if( ( meta == 15 && hitX >= 0.5F && hitZ <= 0.5F )
			 || ( meta == 13 && hitX >= 0.5F && hitZ >= 0.5F ) ) newmeta = 9;
			if( ( meta == 14 && hitX >= 0.5F && hitZ <= 0.5F )
			 || ( meta == 13 && hitX <= 0.5F && hitZ <= 0.5F ) ) newmeta = 10;
			if( ( meta == 15 && hitX <= 0.5F && hitZ >= 0.5F )
			 || ( meta == 12 && hitX >= 0.5F && hitZ >= 0.5F ) ) newmeta = 11;
		}
		
		if( meta == newmeta ) {
			BitSet data = metaToDetailed(meta);
			data.andNot(BlockDetailed.getEmptyQuad(hitX, hitY, hitZ));
			BitSet quaddata = metaToQuad(meta);
			BlockDetailed.BitSetToDetailled(world, x, y, z, data, quaddata );
			data = null;
			System.gc();
		} else
			world.setBlockMetadataWithNotify(x, y, z, newmeta, 0x2);
	}

	public static BitSet metaToDetailed(int meta)
	{
		BitSet data = new BitSet(512);
		data.set(0, 512);
		
		if( meta ==  8 || meta == 10 || meta == 14 ) data.andNot(BlockDetailed.quad_0_0_0);
		if( meta == 10 || meta ==  9 || meta == 13 ) data.andNot(BlockDetailed.quad_1_0_0);
		if( meta ==  0 || meta ==  2 || meta ==  6 ) data.andNot(BlockDetailed.quad_0_1_0);
		if( meta ==  2 || meta ==  1 || meta ==  5 ) data.andNot(BlockDetailed.quad_1_1_0);
		if( meta == 11 || meta ==  8 || meta == 12 ) data.andNot(BlockDetailed.quad_0_0_1);
		if( meta ==  9 || meta == 11 || meta == 15 ) data.andNot(BlockDetailed.quad_1_0_1);
		if( meta ==  3 || meta ==  0 || meta ==  4 ) data.andNot(BlockDetailed.quad_0_1_1);
		if( meta ==  1 || meta ==  3 || meta ==  7 ) data.andNot(BlockDetailed.quad_1_1_1);
		
		return data;
	}

	private static BitSet metaToQuad(int meta) {
		BitSet data = new BitSet(8);
		
		if( meta ==  8 || meta == 10 || meta == 14 ) data.set((0*2+0)*2+0);
		if( meta == 10 || meta ==  9 || meta == 13 ) data.set((1*2+0)*2+0);
		if( meta ==  0 || meta ==  2 || meta ==  6 ) data.set((0*2+0)*2+1);
		if( meta ==  2 || meta ==  1 || meta ==  5 ) data.set((1*2+0)*2+1);
		if( meta == 11 || meta ==  8 || meta == 12 ) data.set((0*2+1)*2+0);
		if( meta ==  9 || meta == 11 || meta == 15 ) data.set((1*2+1)*2+0);
		if( meta ==  3 || meta ==  0 || meta ==  4 ) data.set((0*2+1)*2+1);
		if( meta ==  1 || meta ==  3 || meta ==  7 ) data.set((1*2+1)*2+1);
		
		return data;
	}
}
