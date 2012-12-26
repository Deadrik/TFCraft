package TFC.Blocks;

import TFC.*;
import TFC.Enums.EnumSize;
import TFC.Items.ISize;
import TFC.TileEntities.TileEntityTerraWorkbench;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.crash.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.packet.*;
import net.minecraft.pathfinding.*;
import net.minecraft.potion.*;
import net.minecraft.server.*;
import net.minecraft.stats.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.village.*;
import net.minecraft.world.*;
import net.minecraft.world.biome.*;
import net.minecraft.world.chunk.*;
import net.minecraft.world.gen.feature.*;
public class BlockWorkbench extends BlockContainer
{
	public BlockWorkbench(int i)
	{
		super(i, Material.wood);
		blockIndexInTexture = 59;
		this.setCreativeTab(CreativeTabs.tabRedstone);
	}

	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int par6, float par7, float par8, float par9)
	{
		if (!world.isRemote)
		{
			entityplayer.openGui(TerraFirmaCraft.instance, 1, world, i, j, k);
		}
		return true;
	}

	public int getBlockTextureFromSide(int i)
	{
		if (i == 1)
		{
			return blockIndexInTexture - 16;
		}
		if (i == 0)
		{
			return Block.planks.getBlockTextureFromSide(0);
		}
		if (i == 2 || i == 4)
		{
			return blockIndexInTexture + 1;
		}
		else
		{
			return blockIndexInTexture;
		}
	}

	@Override
	public TileEntity createNewTileEntity(World var1) {
		// TODO Auto-generated method stub
		return new TileEntityTerraWorkbench();
	}
}
