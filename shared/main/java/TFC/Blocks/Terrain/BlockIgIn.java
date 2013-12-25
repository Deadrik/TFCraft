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
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import TFC.TFCBlocks;
import TFC.TFCItems;
import TFC.API.Constant.Global;
import TFC.Core.TFC_Core;
import TFC.Items.Tools.ItemChisel;
import TFC.Items.Tools.ItemHammer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockIgIn extends BlockStone
{
	public BlockIgIn(int i, Material material,int id) {
		super(i, material, id);
		this.dropBlock = TFCBlocks.StoneIgInCobble.blockID;

        names = Global.STONE_IGIN;
        icons = new Icon[names.length];
        looseStart = Global.STONE_IGIN_START;
        gemChance = 2;
	}
}