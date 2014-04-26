package TFC.Items;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import TFC.TFCBlocks;
import TFC.API.Enums.EnumSize;
import TFC.API.Enums.EnumWeight;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemCoal extends ItemTerra {

	public ItemCoal() {
		super();
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
		this.setCreativeTab(CreativeTabs.tabMaterials);
		this.MetaNames = new String[] {"coal", "charcoal"};
		this.setWeight(EnumWeight.LIGHT);
		this.setSize(EnumSize.TINY);
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
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		par3List.add(new ItemStack(par1, 1, 0));
		par3List.add(new ItemStack(par1, 1, 1));
	}

	@Override
	public boolean onItemUse(ItemStack is, EntityPlayer player, World world, int i, int j, int k, int side, float hitX, float hitY, float hitZ)
	{
		if(is.getItemDamage() == 1 && !world.isRemote)
		{
			if(world.getBlock(i, j, k) == TFCBlocks.Charcoal)
			{
				int meta = world.getBlockMetadata(i, j, k);
				if(meta < 8)
				{
					world.setBlockMetadataWithNotify(i, j, k, meta+1,3);
					is.stackSize--;
					return true;
				}
				else if(side == 1 && world.isAirBlock(i + map[side][0], j + map[side][1], k + map[side][2]))
				{
					world.setBlock(i + map[side][0], j + map[side][1], k + map[side][2], TFCBlocks.Charcoal, 1, 0x2);
					is.stackSize--;
					return true;
				}
			}

			if(world.getBlock(i + map[side][0], j + map[side][1], k + map[side][2]) == TFCBlocks.Charcoal)
			{
				int meta = world.getBlockMetadata(i + map[side][0], j + map[side][1], k + map[side][2]);
				if(meta < 8)
				{
					world.setBlockMetadataWithNotify(i + map[side][0], j + map[side][1], k + map[side][2], meta+1,3);
					is.stackSize--;
					return true;
				}
			}

			if(world.isAirBlock(i + map[side][0], j + map[side][1], k + map[side][2]))
			{
				world.setBlock(i + map[side][0], j + map[side][1], k + map[side][2], TFCBlocks.Charcoal, 1, 0x2);
				is.stackSize--;
				TFCBlocks.Charcoal.onNeighborBlockChange(world, i + map[side][0], j + map[side][1], k + map[side][2], world.getBlock(i + map[side][0], j + map[side][1], k + map[side][2]));
			}
			return true;
		}
		return false;
	}
}
