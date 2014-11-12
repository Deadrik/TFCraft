package com.bioxx.tfc.Items.Tools;

import java.util.Random;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.bioxx.tfc.TFCBlocks;
import com.bioxx.tfc.Blocks.BlockSlab;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.Player.PlayerInfo;
import com.bioxx.tfc.Core.Player.PlayerManagerTFC;
import com.bioxx.tfc.TileEntities.TEDetailed;
import com.bioxx.tfc.TileEntities.TEPartial;
import com.bioxx.tfc.api.TFCOptions;
import com.bioxx.tfc.api.Enums.EnumItemReach;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Tools.IToolChisel;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;

public class ItemChisel extends ItemTerraTool implements IToolChisel
{
	static Random random = new Random();
	private static final Set blocks = Sets.newHashSet( new Block[] {});

	public ItemChisel(ToolMaterial e)
	{
		super(0, e, blocks);
		this.setMaxDamage(e.getMaxUses() / 2);
	}

	@Override
	public EnumSize getSize(ItemStack is)
	{
		return EnumSize.VERYSMALL;
	}

	public static void CreateSmooth(World world, int x, int y, int z, Block id, int meta)
	{
		if(id == TFCBlocks.StoneIgIn) {
			world.setBlock(x, y, z, TFCBlocks.StoneIgInSmooth, meta, 0x2);
		} else if(id == TFCBlocks.StoneIgEx) {
			world.setBlock(x, y, z, TFCBlocks.StoneIgExSmooth, meta, 0x2);
		} else if(id == TFCBlocks.StoneSed) {
			world.setBlock(x, y, z, TFCBlocks.StoneSedSmooth, meta, 0x2);
		} else if(id == TFCBlocks.StoneMM) {
			world.setBlock(x, y, z, TFCBlocks.StoneMMSmooth, meta, 0x2);
		}
	}

	public static void CreateStairs(World world, int x, int y, int z, Block id, int meta, float hitX, float hitY, float hitZ)
	{
		int rot = 0;

		if( hitY > 0.5F ) {
			if( hitX < 0.5F && hitZ > 0.5F ) rot = 4;
			if( hitX > 0.5F && hitZ < 0.5F ) rot = 5;
			if( hitX < 0.5F && hitZ < 0.5F ) rot = 6;
			if( hitX > 0.5F && hitZ > 0.5F ) rot = 7;
		}
		else
		{
			if( hitX < 0.5F && hitZ > 0.5F ) rot = 12;
			if( hitX > 0.5F && hitZ < 0.5F ) rot = 13;
			if( hitX < 0.5F && hitZ < 0.5F ) rot = 14;
			if( hitX > 0.5F && hitZ > 0.5F ) rot = 15;
		}
		
		world.setBlock(x, y, z, TFCBlocks.stoneStairs, rot, 0x2);
		TEPartial te = (TEPartial)world.getTileEntity(x, y, z);
		te.TypeID = (short) Block.getIdFromBlock(id);
		te.MetaID = (byte) meta;
		te.extraData = 0;
		te.setMaterial(world.getBlock(x, y, z).getMaterial());
		te.validate();
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
		
		if( meta == newmeta )
			ItemChisel.StairToDetailed(world, x, y, z, meta, hitX, hitY, hitZ);
		else
			world.setBlockMetadataWithNotify(x, y, z, newmeta, 0x2);
	}

	public static void CreateSlab(World world, int x, int y, int z, Block id, int meta, int side, Block Slab)
	{
		TEPartial te;

		if(world.getBlock(x, y, z) != Slab)
		{
			world.setBlock(x, y, z, Slab, side, 0x2);
			te = (TEPartial)world.getTileEntity(x, y, z);
			te.TypeID = (short) Block.getIdFromBlock(id);
			te.MetaID = (byte) meta;
			te.setMaterial(world.getBlock(x, y, z).getMaterial());
		}
		else
		{
			te = (TEPartial)world.getTileEntity(x, y, z);
			world.notifyBlockChange(x, y, z, Slab);
		}

		if(TFCOptions.enableDebugMode)
			System.out.println(side);

		long extraX = (te.extraData) & 0xf;
		long extraY = (te.extraData >> 4) & 0xf;
		long extraZ = (te.extraData >> 8) & 0xf;
		long extraX2 = (te.extraData >> 12) & 0xf;
		long extraY2 = (te.extraData >> 16) & 0xf;
		long extraZ2 = (te.extraData >> 20) & 0xf;

		if(side == 0)
		{
			long e = extraY + 1; 
			long new1 = (extraY << 4);
			long new2 = (e << 4);
			long old2 = new2 | (te.extraData - new1);

			if(e + BlockSlab.getTopChiselLevel(te.extraData) >= 8) {
				world.setBlockToAir(x, y, z);
			} else {
				te.extraData =  old2;
			}
		}
		else if(side == 1)
		{
			long e = extraY2 + 1; 
			long new1 = (extraY2 << 16);
			long new2 = (e << 16);
			long old2 = new2 | (te.extraData - new1);

			if(e + BlockSlab.getBottomChiselLevel(te.extraData) >= 8) {
				world.setBlockToAir(x, y, z);
			} else {
				te.extraData =  old2;
			}
		}
		else if(side == 2)
		{
			long e = extraZ + 1; 
			long new1 = (extraZ << 8);
			long new2 = (e << 8);
			long old2 = new2 | (te.extraData - new1);

			if(e + BlockSlab.getSouthChiselLevel(te.extraData) >= 8) {
				world.setBlockToAir(x, y, z);
			} else {
				te.extraData =  old2;
			}
		}
		else if(side == 3)
		{
			long e = extraZ2 + 1; 
			long new1 = (extraZ2 << 20);
			long new2 = (e << 20);
			long old2 = new2 | (te.extraData - new1);

			if(e + BlockSlab.getNorthChiselLevel(te.extraData) >= 8) {
				world.setBlockToAir(x, y, z);
			} else {
				te.extraData =  old2;
			}
		}
		else if(side == 4)
		{
			long e = extraX + 1; 
			long new1 = (extraX);
			long new2 = (e);
			long old2 = new2 | (te.extraData - new1);

			if(e + BlockSlab.getEastChiselLevel(te.extraData) >= 8) {
				world.setBlockToAir(x, y, z);
			} else {
				te.extraData =  old2;
			}
		}
		else if(side == 5)
		{
			long e = extraX2 + 1; 
			long new1 = (extraX2 << 12);
			long new2 = (e << 12);
			long old2 = new2 | (te.extraData - new1);

			if(e + BlockSlab.getWestChiselLevel(te.extraData) >= 8) {
				world.setBlockToAir(x, y, z);
			} else {
				te.extraData =  old2;
			}
		}

		if(TFCOptions.enableDebugMode)
		{
			System.out.println("Extra ="+te.extraData);  
		}

		te = (TEPartial)world.getTileEntity(x, y, z);
		if(te != null)
		{
			world.markBlockForUpdate(te.xCoord, te.yCoord, te.zCoord);
			//te.broadcastPacketInRange(te.createUpdatePacket());
		}

		world.notifyBlocksOfNeighborChange(x, y, z, world.getBlock(x, y, z));
	}

	public static void CreateSlab(World world, int x, int y, int z, Block id, int meta, int side)
	{
		CreateSlab(world,x,y,z,id,meta,side, TFCBlocks.stoneSlabs);
	}

	public static void CreateDetailed(World world, int x, int y, int z, Block id, int meta, int side, float hitX, float hitY, float hitZ)
	{
		TEDetailed te;

		if(id == TFCBlocks.stoneSlabs)
		{
			TEPartial tep = (TEPartial)world.getTileEntity(x, y, z);
			int extraX = (int) ((tep.extraData) & 0xf);
			int extraY = (int) ((tep.extraData >> 4) & 0xf);
			int extraZ = (int) ((tep.extraData >> 8) & 0xf);
			int extraX2 = 8 - (int) ((tep.extraData >> 12) & 0xf);
			int extraY2 = 8 - (int) ((tep.extraData >> 16) & 0xf);
			int extraZ2 = 8 - (int) ((tep.extraData >> 20) & 0xf);
			world.setBlock(x, y, z, TFCBlocks.Detailed);
			te = (TEDetailed)world.getTileEntity(x, y, z);
			te.TypeID = tep.TypeID;
			te.MetaID = tep.MetaID;

			for(int subX = 0; subX < 8; subX++)
			{
				for(int subZ = 0; subZ < 8; subZ++)
				{
					for(int subY = 0; subY < 8; subY++)
					{
						if(subX >= extraX && subX < extraX2 && subY >= extraY && subY < extraY2 && subZ >= extraZ && subZ < extraZ2)
						{
							te.setBlock(subX, subY, subZ);
							te.setQuad(subX, subY, subZ);
						}
					}
				}
			}
			return;
		}
		else
		{
			Material m = world.getBlock(x, y, z).getMaterial();
			world.setBlock(x, y, z, TFCBlocks.Detailed);

			te = (TEDetailed)world.getTileEntity(x, y, z);
			te.TypeID = (short) Block.getIdFromBlock(id);
			te.MetaID = (byte) meta;

			for(int subX = 0; subX < 8; subX++)
			{
				for(int subZ = 0; subZ < 8; subZ++)
				{
					for(int subY = 0; subY < 8; subY++)
					{
						te.setBlock(subX, subY, subZ);
						te.setQuad(subX, subY, subZ);
					}
				}
			}
		}

		world.notifyBlocksOfNeighborChange(x, y, z, world.getBlock(x, y, z));
	}
	
	public static void StairToDetailed(World world, int x, int y, int z, int meta, float hitX, float hitY, float hitZ)
	{
		world.notifyBlocksOfNeighborChange(x, y, z, world.getBlock(x, y, z));
		
		boolean xmymzm = hitX < 0.5F && hitY < 0.5F && hitZ < 0.5F;
		boolean xpymzm = hitX > 0.5F && hitY < 0.5F && hitZ < 0.5F;
		boolean xpymzp = hitX > 0.5F && hitY < 0.5F && hitZ > 0.5F;
		boolean xmymzp = hitX < 0.5F && hitY < 0.5F && hitZ > 0.5F;
		boolean xmypzm = hitX < 0.5F && hitY > 0.5F && hitZ < 0.5F;
		boolean xpypzm = hitX > 0.5F && hitY > 0.5F && hitZ < 0.5F;
		boolean xpypzp = hitX > 0.5F && hitY > 0.5F && hitZ > 0.5F;
		boolean xmypzp = hitX < 0.5F && hitY > 0.5F && hitZ > 0.5F;
		
		if( ( meta ==  8 || meta == 10 || meta == 14 ) && xmymzm ) return;
		if( ( meta == 10 || meta ==  9 || meta == 13 ) && xpymzm ) return;
		if( ( meta ==  9 || meta == 11 || meta == 15 ) && xpymzp ) return;
		if( ( meta == 11 || meta ==  8 || meta == 12 ) && xmymzp ) return;
		if( ( meta ==  0 || meta ==  2 || meta ==  6 ) && xmypzm ) return;
		if( ( meta ==  2 || meta ==  1 || meta ==  5 ) && xpypzm ) return;
		if( ( meta ==  1 || meta ==  3 || meta ==  7 ) && xpypzp ) return;
		if( ( meta ==  3 || meta ==  0 || meta ==  4 ) && xmypzp ) return;
		
		TEPartial tep = (TEPartial)world.getTileEntity(x, y, z);
		world.setBlock(x, y, z, TFCBlocks.Detailed);
		
		TEDetailed te;
		te = (TEDetailed)world.getTileEntity(x, y, z);
		te.TypeID = tep.TypeID;
		te.MetaID = tep.MetaID;
		
		System.out.println(meta);
		
		System.out.println( Math.round(hitY) + " " + Math.round(hitX) + " " + Math.round(hitZ) );
		
		if( meta != 8 && meta != 10 && meta != 14 && !xmymzm )
			for(int subX = 0; subX < 4; subX++) for(int subZ = 0; subZ < 4; subZ++) for(int subY = 0; subY < 4; subY++) {
				te.setBlock(subX, subY, subZ);
				te.setQuad(subX, subY, subZ);
			}
		if( meta != 10 && meta != 9 && meta != 13 && !xpymzm )
			for(int subX = 4; subX < 8; subX++) for(int subZ = 0; subZ < 4; subZ++) for(int subY = 0; subY < 4; subY++) {
				te.setBlock(subX, subY, subZ);
				te.setQuad(subX, subY, subZ);
			}
		if( meta != 9 && meta != 11 && meta != 15 && !xpymzp )
			for(int subX = 4; subX < 8; subX++) for(int subZ = 4; subZ < 8; subZ++) for(int subY = 0; subY < 4; subY++) {
				te.setBlock(subX, subY, subZ);
				te.setQuad(subX, subY, subZ);
			}
		if( meta != 11 && meta != 8 && meta != 12 && !xmymzp )
			for(int subX = 0; subX < 4; subX++) for(int subZ = 4; subZ < 8; subZ++) for(int subY = 0; subY < 4; subY++) {
				te.setBlock(subX, subY, subZ);
				te.setQuad(subX, subY, subZ);
			}
		if( meta != 0 && meta != 2 && meta != 6 && !xmypzm )
			for(int subX = 0; subX < 4; subX++) for(int subZ = 0; subZ < 4; subZ++) for(int subY = 4; subY < 8; subY++) {
				te.setBlock(subX, subY, subZ);
				te.setQuad(subX, subY, subZ);
			}
		if( meta != 2 && meta != 1 && meta != 5 && !xpypzm )
			for(int subX = 4; subX < 8; subX++) for(int subZ = 0; subZ < 4; subZ++) for(int subY = 4; subY < 8; subY++) {
				te.setBlock(subX, subY, subZ);
				te.setQuad(subX, subY, subZ);
			}
		if( meta != 1 && meta != 3 && meta != 7 && !xpypzp )
			for(int subX = 4; subX < 8; subX++) for(int subZ = 4; subZ < 8; subZ++) for(int subY = 4; subY < 8; subY++) {
				te.setBlock(subX, subY, subZ);
				te.setQuad(subX, subY, subZ);
			}
		if( meta != 3 && meta != 0 && meta != 4 && !xmypzp )
			for(int subX = 0; subX < 4; subX++) for(int subZ = 4; subZ < 8; subZ++) for(int subY = 4; subY < 8; subY++) {
				te.setBlock(subX, subY, subZ);
				te.setQuad(subX, subY, subZ);
			}
		
		return;
	}

	@Override
	public boolean onUsed(World world, EntityPlayer player, int x, int y, int z, Block block, int meta, int side, float hitX, float hitY, float hitZ)
	{
		int mode = 0;
		PlayerInfo pi = null;

		int hasChisel = -1;
		int hasHammer = -1;
		
		for(int i = 0; i < 9;i++)
		{
			if(player.inventory.mainInventory[i] != null && player.inventory.mainInventory[i].getItem() instanceof ItemHammer) {
				hasHammer = i;
			}
		}

		if(player.inventory.mainInventory[player.inventory.currentItem] != null && player.inventory.mainInventory[player.inventory.currentItem].getItem() instanceof ItemChisel) {
			hasChisel = player.inventory.currentItem;
		}

		if(!world.isRemote)
		{
			pi = PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(player);

			if(pi != null) {
				mode = pi.ChiselMode;
			}
		}
		else
		{
			pi = PlayerManagerTFC.getInstance().getClientPlayer();
		}

		if(pi != null) {
			mode = pi.ChiselMode;
		}

		if(hasChisel >= 0)
		{
			if(mode == 0)
			{
				if(TFC_Core.isNaturalStone(world.getBlock(x, y+1, z)) && TFC_Core.isNaturalStone(world.getBlock(x, y+2, z))) {
					return false;
				}

				CreateSmooth(world, x, y, z, block, meta);

				player.inventory.mainInventory[hasChisel].damageItem(1, player);

				return true;
			}
			else if(mode == 1)
			{
				if (TFC_Core.isNaturalStone(block) && TFC_Core.isNaturalStone(world.getBlock(x, y+1, z)) && 
						TFC_Core.isNaturalStone(world.getBlock(x, y+2, z))) {
					return false;
				}
				
				ItemChisel.CreateStairs(world, x, y, z, block, meta, hitX, hitY, hitZ);

				player.inventory.mainInventory[hasChisel].damageItem(1, player);

				return true;
			}
			else if(mode == 2)
			{
				if (TFC_Core.isNaturalStone(block) && TFC_Core.isNaturalStone(world.getBlock(x, y+1, z)) && 
						TFC_Core.isNaturalStone(world.getBlock(x, y+2, z))) {
					return false;
				}

				ItemChisel.CreateSlab(world, x, y, z, block, meta, side);

				player.inventory.mainInventory[hasChisel].damageItem(1, player);

				return true;
			}
			else if(mode == 3 && pi.lockMatches(x, y, z))
			{
				ItemChisel.CreateDetailed(world, x, y, z, block, meta, side, hitX, hitY, hitZ);
				if (random.nextInt(4)==0){
					player.inventory.mainInventory[hasChisel].damageItem(1, player);
				}
				return true;
			}
		}
		return true;
	}

	@Override
	public boolean canChisel(EntityPlayer player, int x, int y, int z) 
	{
		return true;
	}

	@Override
	public Multimap getItemAttributeModifiers()
	{
		Multimap multimap = HashMultimap.create();
		return multimap;
	}

	@Override
	public EnumItemReach getReach(ItemStack is){
		return EnumItemReach.SHORT;
	}
}
