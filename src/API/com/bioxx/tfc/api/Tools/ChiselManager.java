package com.bioxx.tfc.api.Tools;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import com.bioxx.tfc.Core.Player.PlayerInfo;

/**
 * Created by raymondbh on 08.07.2015.
 */
public class ChiselManager {

    private static final ChiselManager INSTANCE = new ChiselManager();
    public static final ChiselManager getInstance(){return INSTANCE;}

    private List<ChiselMode> chiselModes;

    private ChiselManager(){

        chiselModes = new ArrayList<ChiselMode>();

    }

    public void addChiselMode(ChiselMode mode){
        chiselModes.add(mode);
    }

    public ResourceLocation getResourceLocation(int mode){

        return chiselModes.get(mode).getResourceLocation();
    }

    public int getTextureU(int mode){
        return chiselModes.get(mode).getTextureU();
    }

    public int getTextureV(int mode){
        return chiselModes.get(mode).getTextureV();
    }

    public int getDivX(int mode, Block block) { return chiselModes.get(mode).getDivX(block); }

    public int getDivY(int mode, Block block) { return chiselModes.get(mode).getDivY(block); }

    public int getDivZ(int mode, Block block) { return chiselModes.get(mode).getDivZ(block); }

    public int getSize() { return chiselModes.size();}

    public void setDivision(int mode, int sideHit) { chiselModes.get(mode).setDivision(sideHit);}

    public boolean onUsedHandler(World world, EntityPlayer player, int x, int y, int z, Block id, int meta, int side, float hitX, float hitY, float hitZ) {
        int mode = -1;
        PlayerInfo pi = null;
        pi = ChiselMode.playerInfo(world, player);
        if(pi != null){ mode = pi.chiselMode; }
        return chiselModes.get(mode).onUsedHandler(world, player, x, y, z, id, meta, side, hitX, hitY, hitZ);
    }
}
