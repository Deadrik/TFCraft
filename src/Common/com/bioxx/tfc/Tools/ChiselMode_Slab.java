package com.bioxx.tfc.Tools;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Blocks.BlockSlab;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.TileEntities.TEPartial;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.TFCOptions;
import com.bioxx.tfc.api.Tools.ChiselMode;

/**
 * Created by raymondbh on 08.07.2015.
 */
public class ChiselMode_Slab extends ChiselMode {

    private static String name;
    private static ResourceLocation resourcelocation = new ResourceLocation(Reference.ModID, Reference.AssetPathGui + "icons.png");
    private static int texture_u, texture_v, divX, divY, divZ;

    public ChiselMode_Slab(String n){
        name = n;
        texture_u = 40;
        texture_v = 58;
    }

	@Override
	public ResourceLocation getResourceLocation()
	{
        return resourcelocation;
    }

	@Override
	public int getTexture_u()
	{
        return texture_u;
    }

	@Override
	public int getTexture_v()
	{
        return texture_v;
    }

	@Override
	public int getDivX(Block block)
	{
        if(block == TFCBlocks.stoneSlabs || isChiselable(block) ) {
            return divX;
        }
        else{
            return 0;
        }
    }

	@Override
	public int getDivY(Block block)
	{
        if(block == TFCBlocks.stoneSlabs || isChiselable(block) ) {
            return divY;
        }
        else{
            return 0;
        }
    }

	@Override
	public int getDivZ(Block block)
	{
        if(block == TFCBlocks.stoneSlabs || isChiselable(block) ) {
            return divZ;
        }
        else{
            return 0;
        }
    }

	@Override
	public void setDivision(int sideHit)
	{
        if (sideHit == 5 || sideHit == 4)
        {
            divY = divZ = 1;
            divX = 8;
        }
        else if (sideHit == 1 || sideHit == 0)
        {
            divX = divZ = 1;
            divY = 8;
        }
        else if (sideHit == 3 || sideHit == 2)
        {
            divY = divX = 1;
            divZ = 8;
        }
    }

	@Override
	public boolean onUsedHandler(World world, EntityPlayer player, int x, int y, int z, Block id, int meta, int side, float hitX, float hitY, float hitZ)
    {
        if (TFC_Core.isNaturalStone(id) && TFC_Core.isNaturalStone(world.getBlock(x, y+1, z)) &&
                TFC_Core.isNaturalStone(world.getBlock(x, y + 2, z)) || id == TFCBlocks.stoneStairs) {
            return false;
        }

        int hasChisel = hasChisel(player);

        if( hasChisel >= 0 ) {
            Block Slab = TFCBlocks.stoneSlabs;
            TEPartial te;

            if (world.getBlock(x, y, z) != Slab) {
                world.setBlock(x, y, z, Slab, side, 0x2);
                te = (TEPartial) world.getTileEntity(x, y, z);
                te.TypeID = (short) Block.getIdFromBlock(id);
                te.MetaID = (byte) meta;
                te.setMaterial(world.getBlock(x, y, z).getMaterial());
            } else {
                te = (TEPartial) world.getTileEntity(x, y, z);
                world.notifyBlockChange(x, y, z, Slab);
            }

            if (TFCOptions.enableDebugMode)
                TerraFirmaCraft.log.info(side);

            long extraX = (te.extraData) & 0xf;
            long extraY = (te.extraData >> 4) & 0xf;
            long extraZ = (te.extraData >> 8) & 0xf;
            long extraX2 = (te.extraData >> 12) & 0xf;
            long extraY2 = (te.extraData >> 16) & 0xf;
            long extraZ2 = (te.extraData >> 20) & 0xf;

            if (side == 0) {
                long e = extraY + 1;
                long new1 = (extraY << 4);
                long new2 = (e << 4);
                long old2 = new2 | (te.extraData - new1);

                if (e + BlockSlab.getTopChiselLevel(te.extraData) >= 8) {
                    world.setBlockToAir(x, y, z);
                } else {
                    te.extraData = old2;
                }
            } else if (side == 1) {
                long e = extraY2 + 1;
                long new1 = (extraY2 << 16);
                long new2 = (e << 16);
                long old2 = new2 | (te.extraData - new1);

                if (e + BlockSlab.getBottomChiselLevel(te.extraData) >= 8) {
                    world.setBlockToAir(x, y, z);
                } else {
                    te.extraData = old2;
                }
            } else if (side == 2) {
                long e = extraZ + 1;
                long new1 = (extraZ << 8);
                long new2 = (e << 8);
                long old2 = new2 | (te.extraData - new1);

                if (e + BlockSlab.getSouthChiselLevel(te.extraData) >= 8) {
                    world.setBlockToAir(x, y, z);
                } else {
                    te.extraData = old2;
                }
            } else if (side == 3) {
                long e = extraZ2 + 1;
                long new1 = (extraZ2 << 20);
                long new2 = (e << 20);
                long old2 = new2 | (te.extraData - new1);

                if (e + BlockSlab.getNorthChiselLevel(te.extraData) >= 8) {
                    world.setBlockToAir(x, y, z);
                } else {
                    te.extraData = old2;
                }
            } else if (side == 4) {
                long e = extraX + 1;
                long new1 = (extraX);
                long new2 = (e);
                long old2 = new2 | (te.extraData - new1);

                if (e + BlockSlab.getEastChiselLevel(te.extraData) >= 8) {
                    world.setBlockToAir(x, y, z);
                } else {
                    te.extraData = old2;
                }
            } else if (side == 5) {
                long e = extraX2 + 1;
                long new1 = (extraX2 << 12);
                long new2 = (e << 12);
                long old2 = new2 | (te.extraData - new1);

                if (e + BlockSlab.getWestChiselLevel(te.extraData) >= 8) {
                    world.setBlockToAir(x, y, z);
                } else {
                    te.extraData = old2;
                }
            }

            if (TFCOptions.enableDebugMode) {
                TerraFirmaCraft.log.info("Extra =" + te.extraData);
            }

            te = (TEPartial) world.getTileEntity(x, y, z);
            if (te != null) {
                world.markBlockForUpdate(te.xCoord, te.yCoord, te.zCoord);
                //te.broadcastPacketInRange(te.createUpdatePacket());
            }

            world.notifyBlocksOfNeighborChange(x, y, z, world.getBlock(x, y, z));

            player.inventory.mainInventory[hasChisel].damageItem(1, player);
        }

        return true;
    }
}
