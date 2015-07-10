package com.bioxx.tfc.Tools;

import com.bioxx.tfc.Blocks.Terrain.BlockStone;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Reference;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.Tools.ChiselMode;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

/**
 * Created by raymondbh on 08.07.2015.
 */
public class ChiselMode_Smooth extends ChiselMode {
    private static String name;
    private static ResourceLocation resourcelocation = new ResourceLocation(Reference.ModID, Reference.AssetPathGui + "icons.png");
    private static int texture_u, texture_v, div;

    public ChiselMode_Smooth(String n){
        name = n;
        texture_u = 0;
        texture_v = 58;
        div = 1;
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
        if(block instanceof BlockStone){
            return div;
        }
        else {
            return 0;
        }
    }

    public int getDivY(Block block){
        if(block instanceof BlockStone){
            return div;
        }
        else {
            return 0;
        }
    }

    public int getDivZ(Block block){
        if(block instanceof BlockStone){
            return div;
        }
        else {
            return 0;
        }
    }

    public boolean onUsedHandler(World world, EntityPlayer player, int x, int y, int z, Block id, int meta, int side, float hitX, float hitY, float hitZ){

        if(TFC_Core.isNaturalStone(world.getBlock(x, y + 1, z)) && TFC_Core.isNaturalStone(world.getBlock(x, y+2, z))) {
            return false;
        }

        int hasChisel = hasChisel(player);
        if( hasChisel >= 0 ){
            if(id == TFCBlocks.StoneIgIn) {
                world.setBlock(x, y, z, TFCBlocks.StoneIgInSmooth, meta, 0x2);
            } else if(id == TFCBlocks.StoneIgEx) {
                world.setBlock(x, y, z, TFCBlocks.StoneIgExSmooth, meta, 0x2);
            } else if(id == TFCBlocks.StoneSed) {
                world.setBlock(x, y, z, TFCBlocks.StoneSedSmooth, meta, 0x2);
            } else if(id == TFCBlocks.StoneMM) {
                world.setBlock(x, y, z, TFCBlocks.StoneMMSmooth, meta, 0x2);
            }

            player.inventory.mainInventory[hasChisel].damageItem(1, player);
        }

        return true;
    }
}
