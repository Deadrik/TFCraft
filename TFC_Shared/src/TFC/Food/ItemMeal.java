package TFC.Food;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.Icon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import TFC.Reference;
import TFC.API.Enums.EnumSize;
import TFC.API.Enums.EnumWeight;
import TFC.Core.TFC_Core;
import TFC.Core.TFC_ItemHeat;
import TFC.Core.Util.StringUtil;

public class ItemMeal extends ItemTerraFood
{
	PotionEffect foodEffect;

	private boolean alwaysEdible = false;
	private int iconid;

	public ItemMeal(int id, int icon) 
	{
		super(id, 0);
		this.hasSubtypes = true;
		iconid = icon;
	}

	@Override
	public Icon getIconFromDamage(int meta)
	{        
		return itemIcon;
	}

	@Override
	public void registerIcons(IconRegister registerer)
	{
		this.itemIcon = registerer.registerIcon(Reference.ModID + ":" + "food/Meal"+iconid);
	}

	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag) 
	{
		ItemTerraFood.addFoodTempInformation(is, arraylist);

		if (is.hasTagCompound())
		{
			NBTTagCompound stackTagCompound = is.getTagCompound();



			if(foodEffect != null) {
				arraylist.add(StringUtil.localize("gui.FoodPrep.Effect") + ": " + StatCollector.translateToLocal(foodEffect.getEffectName()));
			}

			int energy = getMealEnergy(is)/10;
			int power = getMealPower(is)/10;
			int filling = getMealFilling(is)/10;

			if(energy > 0)
			{
				String stars = "";
				int whitestars = 10-energy;
				int blackstars = energy;

				for(int i = 0; i < blackstars; i++)
				{
					stars += "\u272e";
				}
				for(int i = 0; i < whitestars; i++)
				{
					stars += "\u2729";
				}

				arraylist.add(StringUtil.localize("gui.FoodPrep.Energy") + ": " + stars);
			}

			if(power > 0)
			{
				String stars = "";
				int whitestars = 10-power;
				int blackstars = power;

				for(int i = 0; i < blackstars; i++)
				{
					stars += "\u272e";
				}
				for(int i = 0; i < whitestars; i++)
				{
					stars += "\u2729";
				}

				arraylist.add(StringUtil.localize("gui.FoodPrep.Power") + ": " + stars);
			}

			if(filling > 0)
			{
				String stars = "";
				int whitestars = 10-filling;
				int blackstars = filling;

				for(int i = 0; i < blackstars; i++)
				{
					stars += "\u272e";
				}
				for(int i = 0; i < whitestars; i++)
				{
					stars += "\u2729";
				}

				arraylist.add(StringUtil.localize("gui.FoodPrep.Filling") + ": " + stars);
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
			{
				TFC_ItemHeat.HandleItemHeat(is, (int)entity.posX, (int)entity.posY, (int)entity.posZ);
			}
		}
	}

	@Override
	public ItemStack onEaten(ItemStack is, World world, EntityPlayer player)
	{
		world.playSoundAtEntity(player, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
		this.addFoodEffect(is, world, player);
		if(!world.isRemote)
		{

			int energy = getMealEnergy(is);
			int filling = getMealFilling(is);
			FoodStatsTFC foodstats = TFC_Core.getPlayerFoodStats(player);
			foodstats.addStats(filling, energy/100f);
			TFC_Core.setPlayerFoodStats(player, foodstats);
			player.inventory.addItemStackToInventory(new ItemStack(Item.bowlEmpty,1));
		}
		is.stackSize--;
		return is;
	}

	public static boolean isWarm(ItemStack is)
	{
		if(TFC_ItemHeat.GetTemperature(is) > TFC_ItemHeat.getMeltingPoint(is) * 0.1) {
			return true;
		} else {
			return false;
		}
	}

	public static int getMealPower(ItemStack is)
	{
		if (is.hasTagCompound())
		{
			NBTTagCompound stackTagCompound = is.getTagCompound();

			if(stackTagCompound.hasKey("effectpower"))
			{
				int power = stackTagCompound.getByte("effectpower");
				if(!isWarm(is))
				{
					power /= 2;
				}
				return power;
			} else {
				return -1;
			}
		}
		return -1;
	}

	public static int getMealFilling(ItemStack is)
	{
		if (is.hasTagCompound())
		{
			NBTTagCompound stackTagCompound = is.getTagCompound();

			if(stackTagCompound.hasKey("filling"))
			{
				int filling = stackTagCompound.getByte("filling");
				if(!isWarm(is))
				{
					filling /= 2;
				}
				return filling;
			} else {
				return -1;
			}
		}
		return -1;
	}

	/**
	 * Energy is divided by 100 when it is sent to food stats to give a 0.0 - 1.0 float
	 * */
	public static int getMealEnergy(ItemStack is)
	{
		if (is.hasTagCompound())
		{
			NBTTagCompound stackTagCompound = is.getTagCompound();

			if(stackTagCompound.hasKey("energy"))
			{
				int energy = stackTagCompound.getByte("energy");
				if(!isWarm(is))
				{
					energy /= 2;
				}
				return energy;
			} else {
				return -1;
			}
		}
		return -1;
	}

	public void addFoodEffect(ItemStack is, World world, EntityPlayer player)
	{
		if (!world.isRemote && this.foodEffect != null)
		{
			float Power = (getMealPower(is)/100f);

			player.addPotionEffect(new PotionEffect(foodEffect.getPotionID(), (int)(foodEffect.getDuration()*Power), (int)(foodEffect.getAmplifier()*Power)));
		}
	}
	@Override
	public boolean getShareTag()
	{
		return true;
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
		if(!world.isRemote)
		{
			int energy = getMealEnergy(is)/100;
			int filling = getMealFilling(is);

			if (foodstats.needFood() && filling+(filling / 3 * energy * 2.0F) <= 140)
			{
				player.setItemInUse(is, this.getMaxItemUseDuration(is));
			}
		}
		else if(world.isRemote)
		{

			int energy = getMealEnergy(is)/100;
			int filling = getMealFilling(is);

			if (foodstats.needFood() && filling+(filling / 3 * energy * 2.0F) <= 140)
			{
				player.setItemInUse(is, this.getMaxItemUseDuration(is));
			}
		}

		return is;
	}

	/**
	 * sets a potion effect on the item. Args: int potionId, int duration (will be multiplied by 20), int amplifier,
	 * float probability of effect happening
	 */
	public ItemMeal setPotionEffect(PotionEffect potioneffect)
	{
		foodEffect = potioneffect;
		return this;
	}

	/**
	 * Set the field 'alwaysEdible' to true, and make the food edible even if the player don't need to eat.
	 */
	@Override
	public ItemMeal setAlwaysEdible()
	{
		this.alwaysEdible = true;
		return this;
	}

	@Override
	public EnumSize getSize() 
	{
		return EnumSize.SMALL;
	}
	@Override
	public EnumWeight getWeight() 
	{
		return EnumWeight.MEDIUM;
	}

}
