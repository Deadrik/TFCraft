package TFC.Items;

import java.util.List;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;
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
    public boolean onItemUseFirst(ItemStack is, EntityPlayer player, World world, int i, int j, int k, int side, float hitX, float hitY, float hitZ)
    {
    	if(!world.isRemote)
    	{
    		if(world.getBlockId(i, j, k) == TFCBlocks.Charcoal.blockID)
    		{
    			int meta = world.getBlockMetadata(i, j, k);
    			if(meta < 8)
    				world.setBlockMetadata(i, j, k, meta+1);
    			else if(side == 1)
    			{
    				world.setBlockAndMetadata(i + map[side][0], j + map[side][1], k + map[side][2], TFCBlocks.Charcoal.blockID, 1);
    				is.stackSize--;
    				return true;
    			}
    		}
    		else
    		{
    			world.setBlockAndMetadata(i + map[side][0], j + map[side][1], k + map[side][2], TFCBlocks.Charcoal.blockID, 1);
    			is.stackSize--;
    			return true;
    		}
    	}
        return false;
    }
}
