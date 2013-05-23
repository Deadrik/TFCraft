package TFC.Blocks.Terrain;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityFallingSand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import TFC.API.BlockTypes;
import TFC.Blocks.BlockTerra;
import TFC.Core.Helper;
import TFC.Core.TFC_Sounds;
import TFC.Items.Tools.ItemChisel;
import TFC.Items.Tools.ItemHammer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockIgExCobble extends BlockCobble
{

	public static boolean fallInstantly = false;

	public BlockIgExCobble(int i, Material material) 
	{
        super(i, material);
    	
        names = BlockTypes.STONE_IGEX;
    	icons = new Icon[names.length];
    }
	
	@Override
	public int tickRate(World world)
	{
		return 3;
	}
}
