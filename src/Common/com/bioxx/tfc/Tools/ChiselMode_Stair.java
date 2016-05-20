package com.bioxx.tfc.Tools;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.TileEntities.TEPartial;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.Tools.ChiselMode;

/**
 * Created by raymondbh on 08.07.2015.
 */
public class ChiselMode_Stair extends ChiselMode {

	//private static String name;
    private static ResourceLocation resourcelocation = new ResourceLocation(Reference.MOD_ID, Reference.ASSET_PATH_GUI + "icons.png");
    private static int textureU, textureV, div;

    public ChiselMode_Stair(String n){
		// name = n;
        textureU = 20;
        textureV = 58;
        div = 2;
    }

	@Override
	public ResourceLocation getResourceLocation()
	{
        return resourcelocation;
    }

	@Override
	public int getTextureU()
	{
        return textureU;
    }

	@Override
	public int getTextureV()
	{
        return textureV;
    }

	@Override
	public int getDivX(Block block)
	{
        if(block == TFCBlocks.stoneStairs || isChiselable(block) ) {
            return div;
        }
        else{
            return 0;
        }
    }

	@Override
	public int getDivY(Block block)
	{
        if(block == TFCBlocks.stoneStairs || isChiselable(block) ) {
            return div;
        }
        else{
            return 0;
        }
    }

	@Override
	public int getDivZ(Block block)
	{
        if(block == TFCBlocks.stoneStairs || isChiselable(block) ) {
            return div;
        }
        else{
            return 0;
        }
    }

	@Override
	public boolean onUsedHandler(World world, EntityPlayer player, int x, int y, int z, Block id, int meta, int side, float hitX, float hitY, float hitZ)
    {
        if (TFC_Core.isNaturalStone(id) && TFC_Core.isNaturalStone(world.getBlock(x, y+1, z)) &&
                TFC_Core.isNaturalStone(world.getBlock(x, y + 2, z)) || id == TFCBlocks.stoneSlabs) {
            return false;
        }

        int hasChisel = hasChisel(player);

        if( hasChisel >= 0 ){
            int hit = 0;
            TEPartial te = null;
            if(id != TFCBlocks.stoneStairs)
            {
                world.setBlock(x, y, z, TFCBlocks.stoneStairs, 0, 0x3);
                te = (TEPartial)world.getTileEntity(x, y, z);
                te.typeID = (short) Block.getIdFromBlock(id);
                te.metaID = (byte) meta;
                te.extraData = hit;
                te.setMaterial(world.getBlock(x, y, z).getMaterial());
                te.validate();
            }
            else
            {
                te = (TEPartial)world.getTileEntity(x, y, z);
                world.notifyBlockChange(x, y, z, id);
            }
			if (hitY > 0.5F)
			{ // Top Half
				if (hitX <= 0.5F && hitZ >= 0.5F && (te.extraData & 1) == 0)
					hit = 1; // South West
				if (hitX >= 0.5F && hitZ <= 0.5F && (te.extraData & 2) == 0)
					hit = 2; // North East
				if (hitX <= 0.5F && hitZ <= 0.5F && (te.extraData & 4) == 0)
					hit = 4; // North West
				if (hitX >= 0.5F && hitZ >= 0.5F && (te.extraData & 8) == 0)
					hit = 8; // South East
            }
			else // Bottom Half
            {
				if (hitX <= 0.5F && hitZ >= 0.5F && (te.extraData & 16) == 0)
					hit = 16; // South West
				if (hitX >= 0.5F && hitZ <= 0.5F && (te.extraData & 32) == 0)
					hit = 32; // North East
				if (hitX <= 0.5F && hitZ <= 0.5F && (te.extraData & 64) == 0)
					hit = 64; // North West
				if (hitX >= 0.5F && hitZ >= 0.5F && (te.extraData & 128) == 0)
					hit = 128; // South East
            }

            te.extraData |= hit;
            if(te.extraData == 255)
                world.setBlock(x, y, z, Blocks.air);
            else
                te.broadcastPacketInRange();

            player.inventory.mainInventory[hasChisel].damageItem(1, player);
        }

        return true;
    }
}
