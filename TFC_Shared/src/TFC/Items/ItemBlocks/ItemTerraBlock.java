package TFC.Items.ItemBlocks;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import TFC.API.ISize;
import TFC.API.TFC_ItemHeat;
import TFC.API.Enums.EnumItemReach;
import TFC.API.Enums.EnumSize;
import TFC.API.Enums.EnumWeight;
import TFC.Core.Util.StringUtil;
import TFC.Items.ItemTerra;

public class ItemTerraBlock extends ItemBlock implements ISize
{
	public String[] MetaNames;
	public Icon[] icons;
	public String folder;

	public ItemTerraBlock(int par1) 
	{
		super(par1);
		setHasSubtypes(true);
		this.setCreativeTab(CreativeTabs.tabBlock);
		folder = "";
	}

	public ItemTerraBlock setFolder(String f)
	{
		folder = f;
		return this;
	}

	@Override
	public String getUnlocalizedName(ItemStack itemstack)
	{
		try
		{
			if(MetaNames != null)
				return getUnlocalizedName().concat("."+ MetaNames[itemstack.getItemDamage()]);
		}
		catch(Exception ex)
		{
			System.out.println(ex.getLocalizedMessage());
		}
		return super.getUnlocalizedName(itemstack);
	}

	@Override
	public String getItemDisplayName(ItemStack itemstack) 
	{
		return StringUtil.localize(getUnlocalizedName(itemstack).replace(" ", ""));
	}

	/**
	 * This is called by inventories in the world to tick things such as temperature and food decay. Override this and 
	 * return true if you want the item to be handled differently than the standard code. True will stop he standard TFC code from running.
	 */
	public boolean onUpdate(ItemStack is, World world, int x, int y, int z)
	{
		return false;
	}

	@Override
	public int getMetadata(int i) 
	{		
		return i;
	}

	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag) 
	{
		ItemTerra.addSizeInformation(is, arraylist);

		if (is.hasTagCompound())
		{
			NBTTagCompound stackTagCompound = is.getTagCompound();

			if(TFC_ItemHeat.HasTemp(is))
			{
				int temp = TFC_ItemHeat.GetTemp(is);
				int meltTemp = TFC_ItemHeat.IsCookable(is);

				if(meltTemp != -1)
					if(is.itemID == Item.stick.itemID)
						arraylist.add(TFC_ItemHeat.getHeatColorTorch(temp, meltTemp));
					else
						arraylist.add(TFC_ItemHeat.getHeatColor(temp, meltTemp));
			}
		}
	}

	@Override
	public boolean getShareTag()
	{
		return true;
	}

	@Override
	public int getItemStackLimit()
	{
		if(canStack())
			return this.getSize(null).stackSize * getWeight(null).multiplier;
		else
			return 1;
	}

	@Override
	public EnumSize getSize(ItemStack is) {
		// TODO Auto-generated method stub
		return EnumSize.VERYSMALL;
	}

	@Override
	public boolean canStack() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public EnumWeight getWeight(ItemStack is) {
		// TODO Auto-generated method stub
		return EnumWeight.HEAVY;
	}

	@Override
	public void registerIcons(IconRegister registerer)
	{
		/*if(MetaNames != null)
		{
			icons = new Icon[MetaNames.length];
			for(int i = 0; i < MetaNames.length; i++)
			{
				icons[i] = registerer.registerIcon(folder+MetaNames[i]);
			}
		}*/
	}

	@Override
	public EnumItemReach getReach(ItemStack is) {
		return EnumItemReach.SHORT;
	}

}