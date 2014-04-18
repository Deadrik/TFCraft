package TFC.Items.ItemBlocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import TFC.API.HeatIndex;
import TFC.API.HeatRegistry;
import TFC.API.ISize;
import TFC.API.Enums.EnumItemReach;
import TFC.API.Enums.EnumSize;
import TFC.API.Enums.EnumWeight;
import TFC.Core.TFC_ItemHeat;
import TFC.Items.ItemTerra;

public class ItemTerraBlock extends ItemBlock implements ISize
{
	public String[] MetaNames;
	public IIcon[] icons;
	public String folder;

	public ItemTerraBlock(Block par1)
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
				return getUnlocalizedName().concat("." + MetaNames[itemstack.getItemDamage()] + ".name");
		}
		catch(Exception ex)
		{
			System.out.println(ex.getLocalizedMessage());
		}
		return super.getUnlocalizedName(itemstack);
	}

//	@Override
//	public String getItemStackDisplayName(ItemStack itemstack)
//	{
//		return StringUtil.localize(getUnlocalizedName(itemstack).replace(" ", ""));
//	}

	@Override
	public void onUpdate(ItemStack is, World world, Entity entity, int i, boolean isSelected) 
	{
		/*if (!world.isRemote && is.hasTagCompound())
		{
			NBTTagCompound stackTagCompound = is.getTagCompound();

			if(stackTagCompound.hasKey("temperature"))
				TFC_ItemHeat.HandleItemHeat(is, (int)entity.posX, (int)entity.posY, (int)entity.posZ);
		}*/
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

			if(stackTagCompound.hasKey("temperature"))
			{
				float temp = stackTagCompound.getFloat("temperature");
				float meltTemp = -1;
				HeatIndex hi = HeatRegistry.getInstance().findMatchingIndex(is);
				if(hi != null)
					meltTemp = hi.meltTemp;

				if(meltTemp != -1)
				{
					if(is.getItem() == Items.stick)
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
			return this.getSize(null).stackSize * getWeight(null).multiplier;
		else
			return 1;
	}

	@Override
	public EnumSize getSize(ItemStack is)
	{
		return EnumSize.VERYSMALL;
	}

	@Override
	public boolean canStack()
	{
		return true;
	}

	@Override
	public EnumWeight getWeight(ItemStack is)
	{
		return EnumWeight.HEAVY;
	}

	@Override
	public void registerIcons(IIconRegister registerer)
	{
		/*if(MetaNames != null)
		{
			icons = new IIcon[MetaNames.length];
			for(int i = 0; i < MetaNames.length; i++)
			{
				icons[i] = registerer.registerIcon(folder+MetaNames[i]);
			}
		}*/
	}

	@Override
	public EnumItemReach getReach(ItemStack is)
	{
		return EnumItemReach.SHORT;
	}

}