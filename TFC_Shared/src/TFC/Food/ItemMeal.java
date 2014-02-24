package TFC.Food;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import TFC.API.Enums.EnumFoodGroup;
import TFC.API.Enums.EnumSize;
import TFC.API.Enums.EnumWeight;
import TFC.Core.TFC_Core;
import TFC.Core.TFC_ItemHeat;
import TFC.Core.Player.FoodStatsTFC;
import TFC.Items.ItemTerra;

public class ItemMeal extends ItemTerra
{
	PotionEffect foodEffect;
	private boolean alwaysEdible = false;

	public ItemMeal(int id) 
	{
		super(id);
		this.hasSubtypes = true;
		this.MetaIcons = new Icon[11];
		this.setFolder("food/");
	}

	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag) 
	{

		ItemFoodTFC.addHeatInformation(is, arraylist);

		if (is.hasTagCompound())
		{
			NBTTagCompound nbt = is.getTagCompound();
			String fg = "";
			if(nbt.hasKey("FG0"))
				fg += ItemFoodTFC.getFoodGroupName(EnumFoodGroup.values()[nbt.getByte("FG0")]);
			if(nbt.hasKey("FG1"))
				fg += " / " + ItemFoodTFC.getFoodGroupName(EnumFoodGroup.values()[nbt.getByte("FG1")]);
			if(nbt.hasKey("FG2"))
				fg += " / " + ItemFoodTFC.getFoodGroupName(EnumFoodGroup.values()[nbt.getByte("FG2")]);
			if(nbt.hasKey("FG3"))
				fg += " / " + ItemFoodTFC.getFoodGroupName(EnumFoodGroup.values()[nbt.getByte("FG3")]);

			if(nbt.hasKey("foodWeight"))
			{
				float ounces = nbt.getFloat("foodWeight");
				if(ounces > 0)
					arraylist.add("Amount " + ounces+" oz / 80.0 oz");
				float decay = nbt.getFloat("foodDecay");
				if(decay > 0)
					arraylist.add(EnumChatFormatting.DARK_GRAY + "Decay " + decay/ounces*100+"%");
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
				TFC_ItemHeat.HandleItemHeat(is);
		}
	}

	@Override
	public ItemStack onEaten(ItemStack is, World world, EntityPlayer player)
	{
		world.playSoundAtEntity(player, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
		if(!world.isRemote)
		{
			FoodStatsTFC foodstats = TFC_Core.getPlayerFoodStats(player);
			foodstats.eatFood(is);
			TFC_Core.setPlayerFoodStats(player, foodstats);
			player.inventory.addItemStackToInventory(new ItemStack(Item.bowlEmpty,1));
		}
		is.stackSize--;
		return is;
	}

	public static boolean isWarm(ItemStack is)
	{
		if(TFC_ItemHeat.GetTemperature(is) > TFC_ItemHeat.getMeltingPoint(is) * 0.1)
			return true;
		else
			return false;
	}

	public float getFoodWeight(ItemStack is)
	{
		if(is.hasTagCompound() && is.getTagCompound().hasKey("foodWeight"))
		{
			NBTTagCompound nbt = is.getTagCompound();
			return nbt.getFloat("foodWeight");
		}
		return 0f;
	}

	public float getFoodDecay(ItemStack is)
	{
		if(is.hasTagCompound() && is.getTagCompound().hasKey("foodDecay"))
		{
			NBTTagCompound nbt = is.getTagCompound();
			return nbt.getFloat("foodDecay");
		}
		return 0f;
	}

	/**
	 * How long it takes to use or consume an item
	 */
	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack)
	{
		return 32;
	}

	/**
	 * returns the action that specifies what animation to play when the items is being used
	 */
	@Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack)
	{
		return EnumAction.eat;
	}

	/**
	 * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
	 */
	@Override
	public ItemStack onItemRightClick(ItemStack is, World world, EntityPlayer player)
	{
		FoodStatsTFC foodstats = TFC_Core.getPlayerFoodStats(player);

		//The player needs to be able to fit the food into his stomach
		if(foodstats.needFood())
			player.setItemInUse(is, this.getMaxItemUseDuration(is));

		return is;
	}

	@Override
	public EnumSize getSize(ItemStack is) 
	{
		return EnumSize.SMALL;
	}
	@Override
	public EnumWeight getWeight(ItemStack is) 
	{
		return EnumWeight.MEDIUM;
	}

}
