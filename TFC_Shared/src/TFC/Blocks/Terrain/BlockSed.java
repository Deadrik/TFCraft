package TFC.Blocks.Terrain;

import java.util.List;
import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import TFC.TFCBlocks;
import TFC.TFCItems;
import TFC.API.Constant.Global;
import TFC.Core.Helper;
import TFC.Core.TFC_Core;
import TFC.Items.Tools.ItemChisel;
import TFC.Items.Tools.ItemHammer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


public class BlockSed extends BlockStone
{
    public BlockSed(int i, Material material, int id) {
        super(i, material, id);
        this.dropBlock = TFCBlocks.StoneSedCobble.blockID;

        names = Global.STONE_SED;
        icons = new Icon[names.length];
    }

    @Override
	public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int l)
    {			
        Random R = new Random();
        //if(R.nextBoolean())
            dropBlockAsItem_do(world, i, j, k, new ItemStack(TFCItems.LooseRock, R.nextInt(4), l+3));

        super.harvestBlock(world, entityplayer, i, j, k, l);
    }

    @Override
	public void onBlockDestroyedByPlayer(World world, int i, int j, int k, int l)
    {
        if(!world.isRemote)
        {
            Random random = new Random();
            ItemStack is = null;

            is = TFC_Core.RandomGem(random,1);

            if(is != null)
            {
                EntityItem item = new EntityItem(world, i, j, k, is);
                world.spawnEntityInWorld(item);
            }

        }
    }
}