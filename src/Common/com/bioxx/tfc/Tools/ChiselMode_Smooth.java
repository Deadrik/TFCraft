package com.bioxx.tfc.Tools;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Blocks.Terrain.BlockStone;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.Tools.ChiselMode;

/**
 * Created by raymondbh on 08.07.2015.
 */
public class ChiselMode_Smooth extends ChiselMode {
	//private static String name;
    private static ResourceLocation resourcelocation = new ResourceLocation(Reference.MOD_ID, Reference.ASSET_PATH_GUI + "icons.png");
    private static int textureU, textureV, div;

    public ChiselMode_Smooth(String n){
		//name = n;
        textureU = 0;
        textureV = 58;
        div = 1;
    }

    @Override
	public ResourceLocation getResourceLocation(){
        return resourcelocation;
    }

    @Override
	public int getTextureU(){
        return textureU;
    }

    @Override
	public int getTextureV(){
        return textureV;
    }

	@Override
	public int getDivX(Block block)
	{
        if(block instanceof BlockStone){
            return div;
        }
        else {
            return 0;
        }
    }

    @Override
	public int getDivY(Block block){
        if(block instanceof BlockStone){
            return div;
        }
        else {
            return 0;
        }
    }

    @Override
	public int getDivZ(Block block){
        if(block instanceof BlockStone){
            return div;
        }
        else {
            return 0;
        }
    }

    @Override
	public boolean onUsedHandler(World world, EntityPlayer player, int x, int y, int z, Block id, int meta, int side, float hitX, float hitY, float hitZ){

        if(TFC_Core.isNaturalStone(world.getBlock(x, y + 1, z)) && TFC_Core.isNaturalStone(world.getBlock(x, y+2, z))) {
            return false;
        }

		if (TFC_Core.isRawStone(id))
		{
            int hasChisel = hasChisel(player);
            if( hasChisel >= 0 ){
                if(id == TFCBlocks.stoneIgIn) {
                    world.setBlock(x, y, z, TFCBlocks.stoneIgInSmooth, meta, 0x2);
                } else if(id == TFCBlocks.stoneIgEx) {
                    world.setBlock(x, y, z, TFCBlocks.stoneIgExSmooth, meta, 0x2);
                } else if(id == TFCBlocks.stoneSed) {
                    world.setBlock(x, y, z, TFCBlocks.stoneSedSmooth, meta, 0x2);
                } else if(id == TFCBlocks.stoneMM) {
                    world.setBlock(x, y, z, TFCBlocks.stoneMMSmooth, meta, 0x2);
                }

                player.inventory.mainInventory[hasChisel].damageItem(1, player);
				return true;
            }
		}

		return false;
    }
}
