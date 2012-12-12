package TFC.Items;

import java.util.List;

import TFC.Core.HeatIndex;
import TFC.Core.HeatManager;
import TFC.Core.TFC_ItemHeat;
import TFC.Core.TFC_Textures;
import net.minecraft.src.Block;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Entity;
import net.minecraft.src.Item;
import net.minecraft.src.ItemBlock;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.World;

public class ItemDirt extends ItemTerraBlock
{
	public ItemDirt(int i) 
	{
		super(i);
	}

	@Override
	public String getTextureFile()
	{
		return TFC_Textures.BlockSheet2;
	}
	
	public String getItemNameIS(ItemStack par1ItemStack)
    {
        return Block.blocksList[this.getBlockID()].getBlockName();
    }
}