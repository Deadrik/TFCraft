package TFC.Items.Tools;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import TFC.TFCBlocks;
import TFC.API.TFCOptions;
import TFC.API.Enums.EnumSize;
import TFC.API.Tools.IToolChisel;
import TFC.Blocks.BlockSlab;
import TFC.Core.TFC_Core;
import TFC.Core.Player.PlayerInfo;
import TFC.Core.Player.PlayerManagerTFC;
import TFC.TileEntities.TileEntityDetailed;
import TFC.TileEntities.TileEntityPartial;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

public class ItemChisel extends ItemTerraTool implements IToolChisel
{
	static Random random = new Random();
	public ItemChisel(int i, EnumToolMaterial e)
	{
		super(i, 0, e, new Block[] {});
		this.setMaxDamage(e.getMaxUses()/2);
	}

	@Override
	public EnumSize getSize() {
		return EnumSize.VERYSMALL;
	}

	public static void CreateSmooth(World world, int x, int y, int z, int id, int meta)
	{
		if(id == TFCBlocks.StoneIgIn.blockID) {
			world.setBlock(x, y, z, TFCBlocks.StoneIgInSmooth.blockID, meta, 0x2);
		} else if(id == TFCBlocks.StoneIgEx.blockID) {
			world.setBlock(x, y, z, TFCBlocks.StoneIgExSmooth.blockID, meta, 0x2);
		} else if(id == TFCBlocks.StoneSed.blockID) {
			world.setBlock(x, y, z, TFCBlocks.StoneSedSmooth.blockID, meta, 0x2);
		} else if(id == TFCBlocks.StoneMM.blockID) {
			world.setBlock(x, y, z, TFCBlocks.StoneMMSmooth.blockID, meta, 0x2);
		}
	}
	public static void CreateStairs(World world, int x, int y, int z, int id, int meta, byte m)
	{
		world.setBlock(x, y, z, TFCBlocks.stoneStairs.blockID, m, 0x2);
		TileEntityPartial te = (TileEntityPartial)world.getBlockTileEntity(x, y, z);
		te.TypeID = (short) id;
		te.MetaID = (byte) meta;
		te.extraData = 0;
		te.setMaterial(world.getBlockMaterial(x, y, z));
		te.validate();
		world.markBlockForUpdate(x, y, z);

	}
	public static void CreateSlab(World world, int x, int y, int z, int id, int meta, int side, int SlabID)
	{
		TileEntityPartial te;
		if(true)
		{
			if(world.getBlockId(x, y, z) != SlabID)
			{
				world.setBlock(x, y, z, SlabID, side, 0x2);

				te = (TileEntityPartial)world.getBlockTileEntity(x, y, z);
				te.TypeID = (short) id;
				te.MetaID = (byte) meta;
				te.setMaterial(world.getBlockMaterial(x, y, z));
			}
			else
			{
				te = (TileEntityPartial)world.getBlockTileEntity(x, y, z);
				world.notifyBlockChange(x, y, z, SlabID);
			}

			if(TFCOptions.enableDebugMode) {
				System.out.println(side);
			}

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
					world.setBlock(x, y, z, 0);
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
					world.setBlock(x, y, z, 0);
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

				if(e + BlockSlab.getNorthChiselLevel(te.extraData) >= 8) {
					world.setBlock(x, y, z, 0);
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

				if(e + BlockSlab.getSouthChiselLevel(te.extraData) >= 8) {
					world.setBlock(x, y, z, 0);
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
					world.setBlock(x, y, z, 0);
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
					world.setBlock(x, y, z, 0);
				} else {
					te.extraData =  old2;
				}
			}

			if(TFCOptions.enableDebugMode)
			{
				System.out.println("Extra ="+te.extraData);  
			}
		}

		te = (TileEntityPartial)world.getBlockTileEntity(x, y, z);
		if(te != null) {
			te.broadcastPacketInRange(te.createUpdatePacket());
		}

		world.markBlockForUpdate(x, y, z);
	}
	public static void CreateSlab(World world, int x, int y, int z, int id, int meta, int side)
	{
		CreateSlab(world,x,y,z,id,meta,side, TFCBlocks.stoneSlabs.blockID);
	}
	public static void CreateDetailed(World world, int x, int y, int z, int id, int meta, int side, float hitX, float hitY, float hitZ)
	{
		TileEntityDetailed te;

		if(id == TFCBlocks.stoneSlabs.blockID)
		{
			TileEntityPartial tep = (TileEntityPartial)world.getBlockTileEntity(x, y, z);
			int extraX = (int) ((tep.extraData) & 0xf);
			int extraY = (int) ((tep.extraData >> 4) & 0xf);
			int extraZ = (int) ((tep.extraData >> 8) & 0xf);
			int extraX2 = 8 - (int) ((tep.extraData >> 12) & 0xf);
			int extraY2 = 8 - (int) ((tep.extraData >> 16) & 0xf);
			int extraZ2 = 8 - (int) ((tep.extraData >> 20) & 0xf);
			world.setBlock(x, y, z, TFCBlocks.Detailed.blockID);
			te = (TileEntityDetailed)world.getBlockTileEntity(x, y, z);
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
			Material m = world.getBlockMaterial(x, y, z);
			world.setBlock(x, y, z, TFCBlocks.Detailed.blockID);

			te = (TileEntityDetailed)world.getBlockTileEntity(x, y, z);
			te.TypeID = (short) id;
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
	}

	@Override
	public boolean onUsed(World world, EntityPlayer player, int x, int y, int z,
			int blockID, int meta, int side, float hitX, float hitY, float hitZ) 
	{
		byte newMeta = 0;
		if (side == 0)
		{
			newMeta = (byte) (newMeta | 4);
		}

		int rot = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
		byte flip = (byte) (newMeta & 4);
		byte rotation = 0;

		if (rot == 0)
		{
			rotation = (byte) ( 2 | flip);
		}
		else if (rot == 1)
		{
			rotation = (byte) ( 1 | flip);
		}
		else if (rot == 2)
		{
			rotation = (byte) ( 3 | flip);
		}
		else if (rot == 3)
		{
			rotation = (byte) ( 0 | flip);
		}

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
				if(side == 0 && world.getBlockId(x, y+1, z) == blockID) {
					return false;
				}

				CreateSmooth(world, x, y, z, blockID, meta);

				player.inventory.mainInventory[hasChisel].damageItem(1, player);

				return true;
			}
			else if(mode == 1)
			{
				if ((side == 0) && TFC_Core.isRawStone(blockID) && TFC_Core.isRawStone(world.getBlockId(x, y+1, z))) {
					return false;
				}

				ItemChisel.CreateStairs(world, x, y, z, blockID, meta, rotation);

				player.inventory.mainInventory[hasChisel].damageItem(1, player);

				return true;
			}
			else if(mode == 2)
			{
				if ((side == 0) && TFC_Core.isRawStone(blockID) && TFC_Core.isRawStone(world.getBlockId(x, y+1, z))) {
					return false;
				}

				ItemChisel.CreateSlab(world, x, y, z, blockID, meta, side);

				player.inventory.mainInventory[hasChisel].damageItem(1, player);

				return true;
			}
			else if(mode == 3 && pi.lockMatches(x, y, z))
			{
				ItemChisel.CreateDetailed(world, x, y, z, blockID, meta, side, hitX, hitY, hitZ);
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
}
