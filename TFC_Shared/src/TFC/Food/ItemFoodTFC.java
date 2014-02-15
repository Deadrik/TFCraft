package TFC.Food;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import TFC.API.IFood;
import TFC.API.ISize;
import TFC.API.Enums.EnumFoodGroup;
import TFC.API.Enums.EnumSize;
import TFC.API.Enums.EnumWeight;
import TFC.Core.TFC_Core;
import TFC.Core.TFC_ItemHeat;
import TFC.Core.Player.FoodStatsTFC;
import TFC.Core.Util.StringUtil;
import TFC.Items.ItemTerra;

public class ItemFoodTFC extends ItemTerra implements ISize, IFood
{
	/**
	 * Food can contain multiple NBT Tags including
	 * temperature:
	 * foodWeight:
	 * foodDecay
	 */

	public int foodID;

	private EnumFoodGroup foodgroup;

	public ItemFoodTFC(int id, int foodid, EnumFoodGroup fg)
	{
		super(id);
		this.setCreativeTab(CreativeTabs.tabFood);
		foodID = foodid;
		foodgroup = fg;
	}

	public static void addHeatInformation(ItemStack is, List arraylist)
	{
		if (is.hasTagCompound())
		{
			NBTTagCompound stackTagCompound = is.getTagCompound();

			if(stackTagCompound.hasKey("temperature"))
			{
				float temp = stackTagCompound.getFloat("temperature");
				float meltTemp = TFC_ItemHeat.getMeltingPoint(is);

				if(meltTemp != -1)
					arraylist.add(TFC_ItemHeat.getHeatColorFood(temp, meltTemp));
			}
		}
	}

	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag) 
	{
		ItemTerra.addSizeInformation(is, arraylist);

		if(this.getFoodGroup() == EnumFoodGroup.Dairy)
			arraylist.add(EnumChatFormatting.WHITE + StringUtil.localize("gui.food.dairy"));
		else if(this.getFoodGroup() == EnumFoodGroup.Fruit)
			arraylist.add(EnumChatFormatting.DARK_PURPLE + StringUtil.localize("gui.food.fruit"));
		else if(this.getFoodGroup() == EnumFoodGroup.Vegetable)
			arraylist.add(EnumChatFormatting.DARK_GREEN + StringUtil.localize("gui.food.vegetable"));
		else if(this.getFoodGroup() == EnumFoodGroup.Protein)
			arraylist.add(EnumChatFormatting.DARK_RED + StringUtil.localize("gui.food.protein"));
		else if(this.getFoodGroup() == EnumFoodGroup.Grain)
			arraylist.add(EnumChatFormatting.YELLOW + StringUtil.localize("gui.food.grain"));

		addHeatInformation(is, arraylist);

		//int filling = this.getHealAmount() / 10;
		int filling = 10;
		if(filling > 0)
		{
			String stars = "";
			int whitestars = 5-filling;
			int blackstars = filling;

			for(int i = 0; i < blackstars; i++)
				stars += "\u272e";
			for(int i = 0; i < whitestars; i++)
				stars += "\u2729";

			arraylist.add(StringUtil.localize("gui.FoodPrep.Filling") + ": " + stars);
		}
		if (is.hasTagCompound())
		{
			NBTTagCompound stackTagCompound = is.getTagCompound();
			if(stackTagCompound.hasKey("foodWeight"))
			{
				float ounces = stackTagCompound.getFloat("foodWeight");
				arraylist.add(ounces+"oz/10.0oz");
			}
		}
	}

	@Override
	public void onUpdate(ItemStack is, World world, Entity entity, int i, boolean isSelected) 
	{
		if (!world.isRemote && is.hasTagCompound())
		{
			NBTTagCompound stackTagCompound = is.getTagCompound();

			if(stackTagCompound.hasKey("temperature"))
				TFC_ItemHeat.HandleItemHeat(is, (int)entity.posX, (int)entity.posY, (int)entity.posZ);
		}
		if (!world.isRemote && is.stackSize > 1)
		{

		}
	}

	@Override
	public ItemStack onItemRightClick(ItemStack is, World world, EntityPlayer player)
	{
		FoodStatsTFC foodstats = TFC_Core.getPlayerFoodStats(player);
		if(!world.isRemote)
		{
			if (foodstats.needFood())
				player.setItemInUse(is, this.getMaxItemUseDuration(is));
		} else if (foodstats.needFood())
			player.setItemInUse(is, this.getMaxItemUseDuration(is));

		return is;
	}

	@Override
	public ItemStack onEaten(ItemStack is, World world, EntityPlayer player)
	{
		is.stackSize--;
		FoodStatsTFC foodstats = TFC_Core.getPlayerFoodStats(player);

		world.playSoundAtEntity(player, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
		if(!world.isRemote)
			foodstats.addStats(is);
		TFC_Core.setPlayerFoodStats(player, foodstats);
		return is;
	}

	@Override
	public boolean getShareTag()
	{
		return true;
	}
	public int getItemStackLimit()
	{
		if(canStack())
			return this.getSize(null).stackSize * getWeight(null).multiplier <= 64 ? this.getSize(null).stackSize * getWeight(null).multiplier : 64;
			else
				return 1;
	}

	public boolean isHot(ItemStack is)
	{
		if(TFC_ItemHeat.GetTemperature(is) > TFC_ItemHeat.getMeltingPoint(is) *0.8)
			return true;
		else
			return false;
	}

	public void createTag(ItemStack is, float weight)
	{
		if(!is.hasTagCompound())
		{
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setFloat("foodWeight", weight);
			nbt.setFloat("foodDecay", 0);
		}
	}

	public float getFoodWeight(ItemStack is)
	{
		if(is.hasTagCompound() && is.getTagCompound().hasKey("foodWeight"))
		{
			NBTTagCompound nbt = new NBTTagCompound();
			return nbt.getFloat("foodWeight");
		}
		return 0f;
	}

	public float getFoodDecay(ItemStack is)
	{
		if(is.hasTagCompound() && is.getTagCompound().hasKey("foodDecay"))
		{
			NBTTagCompound nbt = new NBTTagCompound();
			return nbt.getFloat("foodDecay");
		}
		return 0f;
	}

	@Override
	public EnumSize getSize(ItemStack is) 
	{
		return size;
	}
	@Override
	public EnumWeight getWeight(ItemStack is) 
	{
		if(is!= null && is.getTagCompound() != null && is.getTagCompound().hasKey("foodWeight"))
			return EnumWeight.HEAVY;
		return weight;
	}
	@Override
	public boolean canStack() 
	{
		return true;
	}

	@Override
	public EnumFoodGroup getFoodGroup() {
		// TODO Auto-generated method stub
		return foodgroup;
	}
}
