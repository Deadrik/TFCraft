package TFC.Items.ItemBlocks;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import TFC.API.HeatIndex;
import TFC.API.HeatRegistry;
import TFC.API.ISize;
import TFC.API.Enums.EnumSize;
import TFC.API.Enums.EnumWeight;
import TFC.Core.TFC_ItemHeat;
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
    	if(MetaNames != null)
    		return getUnlocalizedName().concat("."+ MetaNames[itemstack.getItemDamage()]);
    	return super.getUnlocalizedName(itemstack);
    }
	
	@Override
	public String getItemDisplayName(ItemStack itemstack) 
	{
		return StringUtil.localize(getUnlocalizedName(itemstack).replace(" ", ""));
	}

	@Override
	public void onUpdate(ItemStack is, World world, Entity entity, int i, boolean isSelected) 
	{
		if (!world.isRemote && is.hasTagCompound())
		{
			NBTTagCompound stackTagCompound = is.getTagCompound();

			if(stackTagCompound.hasKey("temperature"))
			{
				TFC_ItemHeat.HandleItemHeat(is, (int)entity.posX, (int)entity.posY, (int)entity.posZ);
			}
		}
	}

	@Override
	public int getMetadata(int i) 
	{		
		return i;
	}

	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag) 
	{
		ItemTerra.addSizeInformation(this, arraylist);
		
		if (is.hasTagCompound())
		{
			NBTTagCompound stackTagCompound = is.getTagCompound();

			if(stackTagCompound.hasKey("temperature"))
			{
				float temp = stackTagCompound.getFloat("temperature");
				float meltTemp = -1;
				HeatIndex hi = HeatRegistry.getInstance().findMatchingIndex(is);
				if(hi != null)
				{
					meltTemp = hi.meltTemp;
				}

				if(meltTemp != -1)
				{
					if(is.itemID == Item.stick.itemID)
						arraylist.add(TFC_ItemHeat.getHeatColorTorch(temp, meltTemp));
					else
						arraylist.add(TFC_ItemHeat.getHeatColor(temp, meltTemp));
				}
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
			return this.getSize().stackSize * getWeight().multiplier;
		else
			return 1;
	}

	@Override
	public EnumSize getSize() {
		// TODO Auto-generated method stub
		return EnumSize.VERYSMALL;
	}

	@Override
	public boolean canStack() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public EnumWeight getWeight() {
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

}