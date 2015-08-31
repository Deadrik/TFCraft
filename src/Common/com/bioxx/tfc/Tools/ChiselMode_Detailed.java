package com.bioxx.tfc.Tools;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.Player.PlayerInfo;
import com.bioxx.tfc.TileEntities.TEDetailed;
import com.bioxx.tfc.TileEntities.TEPartial;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.Tools.ChiselMode;

/**
 * Created by raymondbh on 08.07.2015.
 */
public class ChiselMode_Detailed extends ChiselMode {

	//private static String name;
    private static ResourceLocation resourcelocation = new ResourceLocation(Reference.MOD_ID, Reference.ASSET_PATH_GUI + "icons.png");
    private static int textureU, textureV, div;
    private static Random random = new Random();

    public ChiselMode_Detailed(String n){
		//name = n;
        textureU = 60;
        textureV = 58;
        div = 8;
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
        return div;
    }

	@Override
	public int getDivY(Block block)
	{
        return div;
    }

	@Override
	public int getDivZ(Block block)
	{
        return div;
    }

	@Override
	public boolean onUsedHandler(World world, EntityPlayer player, int x, int y, int z, Block id, int meta, int side, float hitX, float hitY, float hitZ)
    {
        if(TFC_Core.isNaturalStone(id) && TFC_Core.isNaturalStone(world.getBlock(x, y+1, z)) &&
                TFC_Core.isNaturalStone(world.getBlock(x, y + 2, z)) || id == TFCBlocks.stoneStairs ) {
            return false;
        }

        int hasChisel = hasChisel(player);
        PlayerInfo pi = playerInfo(world, player);


        if(hasChisel >=0 && pi.lockMatches(x, y, z)) {
            TEDetailed te;

            if (id == TFCBlocks.stoneSlabs) {
                TEPartial tep = (TEPartial) world.getTileEntity(x, y, z);
                int extraX = (int) ((tep.extraData) & 0xf);
                int extraY = (int) ((tep.extraData >> 4) & 0xf);
                int extraZ = (int) ((tep.extraData >> 8) & 0xf);
                int extraX2 = 8 - (int) ((tep.extraData >> 12) & 0xf);
                int extraY2 = 8 - (int) ((tep.extraData >> 16) & 0xf);
                int extraZ2 = 8 - (int) ((tep.extraData >> 20) & 0xf);
                world.setBlock(x, y, z, TFCBlocks.detailed);
                te = (TEDetailed) world.getTileEntity(x, y, z);
                te.typeID = tep.typeID;
                te.metaID = tep.metaID;

                for (int subX = 0; subX < 8; subX++) {
                    for (int subZ = 0; subZ < 8; subZ++) {
                        for (int subY = 0; subY < 8; subY++) {
                            if (subX >= extraX && subX < extraX2 && subY >= extraY && subY < extraY2 && subZ >= extraZ && subZ < extraZ2) {
                                te.setBlock(subX, subY, subZ);
                                te.setQuad(subX, subY, subZ);
                            }
                        }
                    }
                }
                return true;
            } else {
                //Material m = world.getBlock(x, y, z).getMaterial();
                world.setBlock(x, y, z, TFCBlocks.detailed);

                te = (TEDetailed) world.getTileEntity(x, y, z);
                te.typeID = (short) Block.getIdFromBlock(id);
                te.metaID = (byte) meta;

                for (int subX = 0; subX < 8; subX++) {
                    for (int subZ = 0; subZ < 8; subZ++) {
                        for (int subY = 0; subY < 8; subY++) {
                            te.setBlock(subX, subY, subZ);
                            te.setQuad(subX, subY, subZ);
                        }
                    }
                }
            }

            world.notifyBlocksOfNeighborChange(x, y, z, world.getBlock(x, y, z));

            if (random.nextInt(4) == 0)
            {
                player.inventory.mainInventory[hasChisel].damageItem(1, player);
            }
        }

        return true;
    }
}
