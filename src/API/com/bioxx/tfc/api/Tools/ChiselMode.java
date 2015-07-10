package com.bioxx.tfc.api.Tools;

import com.bioxx.tfc.Blocks.Terrain.BlockCobble;
import com.bioxx.tfc.Blocks.Terrain.BlockSmooth;
import com.bioxx.tfc.Blocks.Terrain.BlockStone;
import com.bioxx.tfc.Core.Player.PlayerInfo;
import com.bioxx.tfc.Core.Player.PlayerManagerTFC;
import com.bioxx.tfc.Items.Tools.ItemChisel;
import com.bioxx.tfc.api.TFCBlocks;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;


/**
 * Created by raymondbh on 08.07.2015.
 */
public abstract class ChiselMode {

    private String name;
    private ResourceLocation resourcelocation;
    private int texture_u, texture_v, divX = 0, divY = 0, divZ = 0;

    public ChiselMode(){}

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
        return divX;
    }

    public int getDivY(Block block){
        return divY;
    }

    public int getDivZ(Block block){
        return divZ;
    }

    public void setDivision(int hitSide){}

    public boolean onUsedHandler(World world, EntityPlayer player, int x, int y, int z, Block id, int meta, int side, float hitX, float hitY, float hitZ){ return false; }

    public boolean isChiselable(Block block){
        boolean isChiselable = block == TFCBlocks.Planks
                || block instanceof BlockCobble
                || block instanceof BlockStone
                || block instanceof BlockSmooth;
        return isChiselable;
    }

    public int hasChisel(EntityPlayer player){
        int hasChisel = -1;
        if(player.inventory.mainInventory[player.inventory.currentItem] != null && player.inventory.mainInventory[player.inventory.currentItem].getItem() instanceof ItemChisel) {
            hasChisel = player.inventory.currentItem;
        }
        return hasChisel;
    }

    public static PlayerInfo playerInfo(World world, EntityPlayer player){
        PlayerInfo pi;
        if(!world.isRemote)
        {
            pi = PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(player);
        }
        else
        {
            pi = PlayerManagerTFC.getInstance().getClientPlayer();
        }
        return pi;
    }
}
