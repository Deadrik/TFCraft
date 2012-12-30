package TFC.Items;

import java.util.BitSet;

import TFC.*;
import TFC.Blocks.BlockSlab;
import TFC.Core.Helper;
import TFC.Core.TFC_Core;
import TFC.Core.TFC_Settings;
import TFC.Core.Player.PlayerInfo;
import TFC.Core.Player.PlayerManagerTFC;
import TFC.Enums.EnumSize;
import TFC.TileEntities.TileEntityDetailed;
import TFC.TileEntities.TileEntityPartial;
import TFC.TileEntities.TileEntitySuperDetailed;
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

public class ItemChisel extends ItemTerraTool
{
	public ItemChisel(int i, EnumToolMaterial e)
	{
		super(i, 0, e, new Block[] {});
		this.setMaxDamage(e.getMaxUses()/2);
	}

	public String getTextureFile() {
		return "/bioxx/terratools.png";
	}

	@Override
	public EnumSize getSize() {
		return EnumSize.VERYSMALL;
	}

	public static boolean handleActivation(World world, EntityPlayer player, int x, int y, int z, int blockID, int meta, int side, float hitX, float hitY, float hitZ)
	{
		byte newMeta = 0;
		if (side == 0)
		{
			newMeta = (byte) (newMeta | 4);
		}

		int rot = MathHelper.floor_double((double)(player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
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
			if(player.inventory.mainInventory[i] != null && player.inventory.mainInventory[i].getItem() instanceof ItemHammer)
				hasHammer = i;
		}

		if(player.inventory.mainInventory[player.inventory.currentItem] != null && player.inventory.mainInventory[player.inventory.currentItem].getItem() instanceof ItemChisel)
			hasChisel = player.inventory.currentItem;

		if(!world.isRemote)
		{
			pi = PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(player);

			if(pi != null) 
				mode = pi.ChiselMode;
		}
		else
		{
			pi = PlayerManagerTFC.getInstance().getClientPlayer();
		}

		if(pi != null) 
			mode = pi.ChiselMode;

		if(hasChisel >= 0)
		{
			if(mode == 0)
			{
				if(side == 0 && world.getBlockId(x, y+1, z) == blockID)
					return false;

				CreateSmooth(world, x, y, z, blockID, meta);
				
				player.inventory.mainInventory[hasChisel].damageItem(1, player);
				
				return true;
			}
			else if(mode == 1)
			{
				if(side == 0 && world.getBlockId(x, y+1, z) == blockID && blockID != Block.planks.blockID)
					return false;

				ItemChisel.CreateStairs(world, x, y, z, blockID, meta, rotation);

				player.inventory.mainInventory[hasChisel].damageItem(1, player);
				
				return true;
			}
			else if(mode == 2)
			{
				if(side == 0 && world.getBlockId(x, y+1, z) == blockID && blockID != Block.planks.blockID)
					return false;

				ItemChisel.CreateSlab(world, x, y, z, blockID, meta, side);

				player.inventory.mainInventory[hasChisel].damageItem(1, player);
				
				return true;
			}
			else if(mode == 3 && pi.lockMatches(x, y, z))
			{
				ItemChisel.CreateDetailed(world, x, y, z, blockID, meta, side, hitX, hitY, hitZ);

				player.inventory.mainInventory[hasChisel].damageItem(1, player);
				
				return true;
			}
			else if(mode == 4 && pi.lockMatches(x, y, z))
			{
				ItemChisel.CreateSuperDetailed(world, x, y, z, blockID, meta, side, hitX, hitY, hitZ);

				player.inventory.mainInventory[hasChisel].damageItem(1, player);
				
				return true;
			}
		}
		return true;
	}

	public static void CreateSmooth(World world, int x, int y, int z, int id, int meta)
	{
		if(id == TFCBlocks.StoneIgIn.blockID)
			world.setBlockAndMetadataWithNotify(x, y, z, TFCBlocks.StoneIgInSmooth.blockID, meta);
		else if(id == TFCBlocks.StoneIgEx.blockID)
			world.setBlockAndMetadataWithNotify(x, y, z, TFCBlocks.StoneIgExSmooth.blockID, meta);
		else if(id == TFCBlocks.StoneSed.blockID)
			world.setBlockAndMetadataWithNotify(x, y, z, TFCBlocks.StoneSedSmooth.blockID, meta);
		else if(id == TFCBlocks.StoneMM.blockID)
			world.setBlockAndMetadataWithNotify(x, y, z, TFCBlocks.StoneMMSmooth.blockID, meta);
	}


	public static void CreateStairs(World world, int x, int y, int z, int id, int meta, byte m)
	{
		world.setBlockAndMetadataWithNotify(x, y, z, TFCBlocks.stoneStairs.blockID, m);
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
				world.setBlockAndMetadataWithNotify(x, y, z, SlabID, side);

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

			if(TFC_Settings.enableDebugMode)
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

				if(e + BlockSlab.getTopChiselLevel(te.extraData) >= 8)
					world.setBlockWithNotify(x, y, z, 0);
				else
					te.extraData =  old2;
			}
			else if(side == 1)
			{
				long e = extraY2 + 1; 
				long new1 = (extraY2 << 16);
				long new2 = (e << 16);
				long old2 = new2 | (te.extraData - new1);

				if(e + BlockSlab.getBottomChiselLevel(te.extraData) >= 8)
					world.setBlockWithNotify(x, y, z, 0);
				else
					te.extraData =  old2;
			}
			else if(side == 2)
			{
				long e = extraZ + 1; 
				long new1 = (extraZ << 8);
				long new2 = (e << 8);
				long old2 = new2 | (te.extraData - new1);

				if(e + BlockSlab.getSouthChiselLevel(te.extraData) >= 8)
					world.setBlockWithNotify(x, y, z, 0);
				else
					te.extraData =  old2;
			}
			else if(side == 3)
			{
				long e = extraZ2 + 1; 
				long new1 = (extraZ2 << 20);
				long new2 = (e << 20);
				long old2 = new2 | (te.extraData - new1);

				if(e + BlockSlab.getNorthChiselLevel(te.extraData) >= 8)
					world.setBlockWithNotify(x, y, z, 0);
				else
					te.extraData =  old2;
			}
			else if(side == 4)
			{
				long e = extraX + 1; 
				long new1 = (extraX);
				long new2 = (e);
				long old2 = new2 | (te.extraData - new1);

				if(e + BlockSlab.getEastChiselLevel(te.extraData) >= 8)
					world.setBlockWithNotify(x, y, z, 0);
				else
					te.extraData =  old2;
			}
			else if(side == 5)
			{
				long e = extraX2 + 1; 
				long new1 = (extraX2 << 12);
				long new2 = (e << 12);
				long old2 = new2 | (te.extraData - new1);

				if(e + BlockSlab.getWestChiselLevel(te.extraData) >= 8)
					world.setBlockWithNotify(x, y, z, 0);
				else
					te.extraData =  old2;
			}

			if(TFC_Settings.enableDebugMode)
			{
				System.out.println("Extra ="+te.extraData);  
			}
		}

		te = (TileEntityPartial)world.getBlockTileEntity(x, y, z);
		if(te != null)
			te.broadcastPacketInRange(te.createUpdatePacket());

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
						}
					}
				}
			}
			return;
		}
		else
		{
			Material m = world.getBlockMaterial(x, y, z);
			world.setBlockWithNotify(x, y, z, TFCBlocks.Detailed.blockID);

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
					}
				}
			}
		}
	}

	public static void CreateSuperDetailed(World world, int x, int y, int z, int id, int meta, int side, float hitX, float hitY, float hitZ)
	{
		TileEntitySuperDetailed te;
		if(id == TFCBlocks.stoneSlabs.blockID)
		{
			TileEntityPartial tep = (TileEntityPartial)world.getBlockTileEntity(x, y, z);
			int extraX = (int) ((tep.extraData) & 0xf);
			int extraY = (int) ((tep.extraData >> 4) & 0xf);
			int extraZ = (int) ((tep.extraData >> 8) & 0xf);
			int extraX2 = 8 - (int) ((tep.extraData >> 12) & 0xf);
			int extraY2 = 8 - (int) ((tep.extraData >> 16) & 0xf);
			int extraZ2 = 8 - (int) ((tep.extraData >> 20) & 0xf);

			world.setBlock(x, y, z, TFCBlocks.SuperDetailed.blockID);

			te = (TileEntitySuperDetailed)world.getBlockTileEntity(x, y, z);
			int index = te.setIdAndMeta(tep.TypeID, tep.MetaID);
			te.blockIndex[0] = index;

			for(int subX = 0; subX < 8; subX++)
			{
				for(int subZ = 0; subZ < 8; subZ++)
				{
					for(int subY = 0; subY < 8; subY++)
					{
						if(subX >= extraX && subX < extraX2 && subY >= extraY && subY < extraY2 && subZ >= extraZ && subZ < extraZ2)
						{
							te.setBlock(subX, subY, subZ);
						}
					}
				}
			}			
		}
		else if(id == TFCBlocks.Detailed.blockID)
		{
			TileEntityDetailed ted = (TileEntityDetailed)world.getBlockTileEntity(x, y, z);
			world.setBlock(x, y, z, TFCBlocks.SuperDetailed.blockID);

			te = (TileEntitySuperDetailed)world.getBlockTileEntity(x, y, z);
			int index = te.setIdAndMeta(ted.TypeID, ted.MetaID);
			te.blockIndex[0] = index;
		}
		else
		{
			Material m = world.getBlockMaterial(x, y, z);
			world.setBlockWithNotify(x, y, z, TFCBlocks.SuperDetailed.blockID);

			te = (TileEntitySuperDetailed)world.getBlockTileEntity(x, y, z);
			int index = te.setIdAndMeta(id, meta);
			te.blockIndex[0] = index;

			for(int subX = 0; subX < 8; subX++)
			{
				for(int subZ = 0; subZ < 8; subZ++)
				{
					for(int subY = 0; subY < 8; subY++)
					{
						te.setBlock(subX, subY, subZ);
						byte ind = te.createIndex(x, y, z, (short) te.setIdAndMeta(id, meta));
						te.setIndex(subX, subY, subZ, ind);
					}
				}
			}
		}
	}
}