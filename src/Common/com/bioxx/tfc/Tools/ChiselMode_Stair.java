package com.bioxx.tfc.Tools;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Reference;
import com.bioxx.tfc.TileEntities.TEPartial;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.Tools.ChiselMode;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

/**
 * Created by raymondbh on 08.07.2015.
 */
public class ChiselMode_Stair extends ChiselMode {

    private static String name;
    private static ResourceLocation resourcelocation = new ResourceLocation(Reference.ModID, Reference.AssetPathGui + "icons.png");
    private static int texture_u, texture_v, div;

    public ChiselMode_Stair(String n){
        name = n;
        texture_u = 20;
        texture_v = 58;
        div = 2;
    }

    public ResourceLocation getResourceLocation(){
        return resourcelocation;
    }

    public int getTexture_u(){
        return texture_u;
    }

    public int getTexture_v(){
        return texture_v;
    }

    public int getDivX(Block block){
        if(block == TFCBlocks.stoneStairs || isChiselable(block) ) {
            return div;
        }
        else{
            return 0;
        }
    }

    public int getDivY(Block block){
        if(block == TFCBlocks.stoneStairs || isChiselable(block) ) {
            return div;
        }
        else{
            return 0;
        }
    }

    public int getDivZ(Block block){
        if(block == TFCBlocks.stoneStairs || isChiselable(block) ) {
            return div;
        }
        else{
            return 0;
        }
    }

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

            player.inventory.mainInventory[hasChisel].damageItem(1, player);
        }

        return true;
    }
}
