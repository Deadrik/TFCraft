package TFC.Items;

import java.util.HashMap;
import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumChatFormatting;
import TFC.API.Metal;
import TFC.API.Constant.Global;
import TFC.API.Enums.EnumSize;
import TFC.API.Enums.EnumWeight;
import TFC.Core.Metal.MetalRegistry;
import TFC.Items.ItemBlocks.ItemTerraBlock;
import TFC.TileEntities.TileEntityBarrel;

public class ItemBarrels extends ItemTerraBlock
{
	private int type = 0;
	public ItemBarrels(int i) 
	{
		super(i);
		setMaxDamage(0);
		setHasSubtypes(true);
		this.setCreativeTab(CreativeTabs.tabMaterials);
		this.MetaNames = Global.WOOD_ALL.clone();
	}

	@Override
	public EnumSize getSize(ItemStack is) {
		return EnumSize.LARGE;
	}

	@Override
	public EnumWeight getWeight(ItemStack is) {
		return EnumWeight.HEAVY;
	}

	public void readFromItemNBT(NBTTagCompound nbt, List arraylist)
	{
		if(nbt != null && nbt.hasKey("type")){
			type = nbt.getInteger("type");
			arraylist.add(EnumChatFormatting.YELLOW + TileEntityBarrel.getType(type));
		}
	}

	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag) 
	{
		ItemTerra.addSizeInformation(is, arraylist);

		readFromItemNBT(is.getTagCompound(), arraylist);
	}

	@Override
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List list)
	{
		for(int i = 0; i < Global.WOOD_ALL.length; i++) {
			list.add(new ItemStack(this,1,i));
		}
	}
}
