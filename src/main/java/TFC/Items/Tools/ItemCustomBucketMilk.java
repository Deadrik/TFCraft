package TFC.Items.Tools;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;
import TFC.TFCItems;
import TFC.API.IFood;
import TFC.API.Enums.EnumFoodGroup;
import TFC.Core.TFCTabs;
import TFC.Core.TFC_Core;
import TFC.Core.Player.FoodStatsTFC;
import TFC.Items.ItemTerra;
import TFC.TileEntities.TileEntityBarrel;

public class ItemCustomBucketMilk extends ItemTerra implements IFood
{
	public ItemCustomBucketMilk()
	{
		super();
		this.setMaxStackSize(1);
		setCreativeTab(TFCTabs.TFCTools);
		this.setFolder("tools/");
	}
	
	@Override
	public boolean canStack()
	{
		return false;
	}

	public ItemStack onEaten(ItemStack is, World world, EntityPlayer player)
	{
		FoodStatsTFC foodstats = TFC_Core.getPlayerFoodStats(player);
		if(!world.isRemote && foodstats.needFood())
		{
			world.playSoundAtEntity(player, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);

			foodstats.eatFood(is);
			foodstats.restoreWater(player, 8000);

			TFC_Core.setPlayerFoodStats(player, foodstats);

			is = new ItemStack(TFCItems.WoodenBucketEmpty);
		}
		return is;
	}

	/**
	 * How long it takes to use or consume an item
	 */
	public int getMaxItemUseDuration(ItemStack par1ItemStack)
	{
		return 32;
	}

	/**
	 * returns the action that specifies what animation to play when the items is being used
	 */
	public EnumAction getItemUseAction(ItemStack par1ItemStack)
	{
		return EnumAction.drink;
	}

	/**
	 * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
	 */
	public ItemStack onItemRightClick(ItemStack is, World world, EntityPlayer entity)
	{
		MovingObjectPosition mop = this.getMovingObjectPositionFromPlayer(world, entity, true);
		FoodStatsTFC fs = TFC_Core.getPlayerFoodStats(entity);
		Boolean canDrink = fs.getMaxWater(entity) - 500 > fs.waterLevel;

		if(mop == null)
		{
			if(is.getItemDamage() > 1 && canDrink)
				entity.setItemInUse(is, this.getMaxItemUseDuration(is));
		}
		else
		{
			if (mop.typeOfHit == MovingObjectType.BLOCK)
			{
				int i = mop.blockX;
				int j = mop.blockY;
				int k = mop.blockZ;
				if(world.getTileEntity(i,j,k) != null && world.getTileEntity(i,j,k) instanceof TileEntityBarrel)
				{
					TileEntityBarrel TE = (TileEntityBarrel) world.getTileEntity(i,j,k);
					if(TE.checkValidAddition(13))
						return new ItemStack(TFCItems.WoodenBucketEmpty);
				}
			}
		}
		entity.setItemInUse(is, this.getMaxItemUseDuration(is));
		return is;
	}

	@Override
	public EnumFoodGroup getFoodGroup()
	{
		return EnumFoodGroup.Dairy;
	}

	@Override
	public int getFoodID()
	{
		return -1;
	}

}
