package com.bioxx.tfc.Items.Tools;

import java.util.Random;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.bioxx.tfc.Blocks.BlockSlab;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.Player.PlayerInfo;
import com.bioxx.tfc.Core.Player.PlayerManagerTFC;
import com.bioxx.tfc.TileEntities.TEDetailed;
import com.bioxx.tfc.TileEntities.TEPartial;
import com.bioxx.tfc.api.TFCBlocks;
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
		int hit = 0;
		TEPartial te = null;
		if(id != TFCBlocks.stoneStairs)
		{
			world.setBlock(x, y, z, TFCBlocks.stoneStairs, 0, 0x3);
			te = (TEPartial)world.getTileEntity(x, y, z);
			te.TypeID = (short) Block.getIdFromBlock(id);
			te.MetaID = (byte) meta;
			te.extraData = hit;
			te.setMaterial(world.getBlock(x, y, z).getMaterial());
			te.validate();
		}
		else
		{
			te = (TEPartial)world.getTileEntity(x, y, z);
			world.notifyBlockChange(x, y, z, id);
		}
		if( hitY > 0.5F ) {
			if( hitX <= 0.5F && hitZ >= 0.5F && (te.extraData & 1) == 0) hit = 1;
			if( hitX >= 0.5F && hitZ <= 0.5F && (te.extraData & 2) == 0) hit = 2;
			if( hitX <= 0.5F && hitZ <= 0.5F && (te.extraData & 4) == 0) hit = 4;
			if( hitX >= 0.5F && hitZ >= 0.5F && (te.extraData & 8) == 0) hit = 8;
		}
		else
		{
			if( hitX <= 0.5F && hitZ >= 0.5F && (te.extraData & 16) == 0) hit = 16;
			if( hitX >= 0.5F && hitZ <= 0.5F && (te.extraData & 32) == 0) hit = 32;
			if( hitX <= 0.5F && hitZ <= 0.5F && (te.extraData & 64) == 0) hit = 64;
			if( hitX >= 0.5F && hitZ >= 0.5F && (te.extraData & 128) == 0) hit = 128;
		}

		te.extraData |= hit;
		if(te.extraData == 255)
			world.setBlock(x, y, z, Blocks.air);
		else
			te.broadcastPacketInRange();
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
				if (random.nextInt(4) == 0)
				{
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
