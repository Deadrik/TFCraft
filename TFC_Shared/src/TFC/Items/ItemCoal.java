package TFC.Items;

import java.util.List;
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
import TFC.TFCBlocks;
import TFC.Enums.EnumSize;
import TFC.Enums.EnumWeight;

public class ItemCoal extends ItemTerra {

	public ItemCoal(int id) {
		super(id, "/gui/items.png");
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
		this.setCreativeTab(CreativeTabs.tabMaterials);
	}

	int[][] map = 
		{   {0,-1,0},
			{0,1,0},
			{0,0,-1},
			{0,0,1},
			{-1,0,0},
			{1,0,0},
		};

	@Override
	public EnumSize getSize() 
	{
		return EnumSize.TINY;
	}

	@Override
	public EnumWeight getWeight() 
	{
		return EnumWeight.LIGHT;
	}

	public String getItemNameIS(ItemStack par1ItemStack)
	{
		return par1ItemStack.getItemDamage() == 1 ? "item.charcoal" : "item.coal";
	}

	@SideOnly(Side.CLIENT)

	/**
	 * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
	 */
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		par3List.add(new ItemStack(par1, 1, 0));
		par3List.add(new ItemStack(par1, 1, 1));
	}

	@Override
	public boolean onItemUse(ItemStack is, EntityPlayer player, World world, int i, int j, int k, int side, float hitX, float hitY, float hitZ)
	{
		if(is.getItemDamage() == 1 && !world.isRemote)
		{
			if(world.getBlockId(i, j, k) == TFCBlocks.Charcoal.blockID)
			{
				int meta = world.getBlockMetadata(i, j, k);
				if(meta < 8)
				{
					world.setBlockMetadataWithNotify(i, j, k, meta+1,3);
					is.stackSize--;
					return true;
				}
				else if(side == 1)
				{
					world.setBlockAndMetadataWithNotify(i + map[side][0], j + map[side][1], k + map[side][2], TFCBlocks.Charcoal.blockID, 1,3);
					is.stackSize--;
					return true;
				}
			}


			if(world.getBlockId(i + map[side][0], j + map[side][1], k + map[side][2]) == TFCBlocks.Charcoal.blockID)
			{
				int meta = world.getBlockMetadata(i + map[side][0], j + map[side][1], k + map[side][2]);
				if(meta < 8)
				{
					world.setBlockMetadataWithNotify(i + map[side][0], j + map[side][1], k + map[side][2], meta+1,3);
					is.stackSize--;
					return true;
				}
			}


			world.setBlockAndMetadataWithNotify(i + map[side][0], j + map[side][1], k + map[side][2], TFCBlocks.Charcoal.blockID, 1,3);
			is.stackSize--;
			TFCBlocks.Charcoal.onNeighborBlockChange(world, i + map[side][0], j + map[side][1], k + map[side][2], 0);
			return true;


		}
		return false;
	}
}
