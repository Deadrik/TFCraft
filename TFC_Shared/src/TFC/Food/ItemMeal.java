package TFC.Food;

import java.util.List;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.crash.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.packet.*;
import net.minecraft.pathfinding.*;
import net.minecraft.potion.*;
import net.minecraft.server.*;
import net.minecraft.stats.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.village.*;
import net.minecraft.world.*;
import net.minecraft.world.biome.*;
import net.minecraft.world.chunk.*;
import net.minecraft.world.gen.feature.*;
import TFC.*;
import TFC.Core.TFC_ItemHeat;
import TFC.Core.Player.TFC_PlayerClient;
import TFC.Core.Player.TFC_PlayerServer;
import TFC.Enums.EnumSize;
import TFC.Enums.EnumWeight;
import TFC.Items.ItemTerra;

public class ItemMeal extends ItemTerra
{
	PotionEffect foodEffect;

	String texture;

	private boolean alwaysEdible = false;

	public ItemMeal(int id, String tex) 
	{
		super(id);
		texture = tex;
	}

	@Override
	public String getTextureFile()
	{
		return texture;
	}

	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag) 
	{
		ItemTerraFood.addFoodTempInformation(is, arraylist);
		
		if (is.hasTagCompound())
		{
			NBTTagCompound stackTagCompound = is.getTagCompound();

			

			if(foodEffect != null)
				arraylist.add("Effect: " + StatCollector.translateToLocal(foodEffect.getEffectName()));

			int energy = getMealEnergy(is)/10;
			int power = getMealPower(is)/10;
			int filling = getMealFilling(is)/10;
			
			if(!isWarm(is))
			{
				energy /= 2;
				power /= 2;
				filling /= 2;
			}

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

				arraylist.add("Energy: " + stars);
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

				arraylist.add("Power: " + stars);
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

				arraylist.add("Filling: " + stars);
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
	public ItemStack onFoodEaten(ItemStack is, World world, EntityPlayer player)
	{
		
		if(!world.isRemote)
		{
			--is.stackSize;
			int energy = getMealEnergy(is);
			int filling = getMealFilling(is);
			if(!isWarm(is))
			{
				energy /= 2;
				filling /= 2;
			}
			TFC_PlayerServer playerServer = (TFC_PlayerServer) ((EntityPlayerMP)player).getServerPlayerBase("TFC Player Server");
			playerServer.getFoodStatsTFC().addStats(getMealFilling(is), (float)getMealEnergy(is)/100f);
			world.playSoundAtEntity(player, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
			this.addFoodEffect(is, world, player);
			player.inventory.addItemStackToInventory(new ItemStack(Item.bowlEmpty,1));
		}
		return is;
	}
	
	public boolean isWarm(ItemStack is)
	{
		if(TFC_ItemHeat.GetTemperature(is) > TFC_ItemHeat.getMeltingPoint(is) * 0.1)
			return true;
		else return false;
	}

	public static int getMealPower(ItemStack is)
	{
		if (is.hasTagCompound())
		{
			NBTTagCompound stackTagCompound = is.getTagCompound();

			if(stackTagCompound.hasKey("effectpower"))
			{
				return stackTagCompound.getByte("effectpower");
			}
			else return -1;
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
				return stackTagCompound.getByte("filling");
			}
			else return -1;
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
				return stackTagCompound.getByte("energy");
			}
			else return -1;
		}
		return -1;
	}

	public void addFoodEffect(ItemStack is, World world, EntityPlayer player)
	{
		if (!world.isRemote && this.foodEffect != null)
		{
			float Power = ((float)getMealPower(is)/100f);
			if(!isWarm(is))
			{
				Power /= 2;
			}

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
		if(!world.isRemote)
		{
			TFC_PlayerServer playerserver = ((TFC.Core.Player.TFC_PlayerServer)((EntityPlayerMP)player).getServerPlayerBase("TFC Player Server"));

			int energy = getMealEnergy(is)/100;
			int filling = getMealFilling(is);

			if (playerserver.getFoodStatsTFC().needFood() && filling+(filling / 3 * energy * 2.0F) <= 140)
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
