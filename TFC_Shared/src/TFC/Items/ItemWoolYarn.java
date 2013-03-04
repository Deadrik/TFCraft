package TFC.Items;

import java.util.List;

import TFC.TFCBlocks;
import TFC.Blocks.BlockCollapsable;
import TFC.Core.Helper;
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
import net.minecraft.world.gen.*;
import net.minecraft.world.gen.feature.*;

public class ItemWoolYarn extends ItemTerra
{
	public ItemWoolYarn(int id) 
	{
		super(id);
		this.hasSubtypes = false;
		this.setMaxDamage(0);
		this.setCreativeTab(CreativeTabs.tabMaterials);
	}
	public ItemWoolYarn(int id, String tex) 
	{
		super(id);
		texture = tex;
	}

	int[][] map = 
		{   {0,-1,0},
			{0,1,0},
			{0,0,-1},
			{0,0,1},
			{-1,0,0},
			{1,0,0},
		};

	

	

	public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5) 
	{

	}

	
}
