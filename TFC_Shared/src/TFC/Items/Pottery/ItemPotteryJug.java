package TFC.Items.Pottery;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.Icon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import TFC.Reference;
import TFC.API.Enums.EnumSize;
import TFC.API.Enums.EnumWeight;
import TFC.Core.TFC_Core;
import TFC.Core.TFC_Sounds;
import TFC.Food.FoodStatsTFC;

public class ItemPotteryJug extends ItemPotteryBase
{
	Icon WaterIcon;

	public ItemPotteryJug(int id) 
	{
		super(id);
		this.MetaNames = new String[]{"Clay Jug", "Ceramic Jug", "Water Jug"};
	}

	@Override
	public ItemStack onEaten(ItemStack is, World world, EntityPlayer player)
	{
		if (!world.isRemote)
		{
			if(is.getItemDamage() == 2) {
				TFC_Core.getPlayerFoodStats(player).restoreWater(player, 8000);
			}

			if (is.getItemDamage() > 1 && !player.capabilities.isCreativeMode)
			{
				if(world.rand.nextInt(25) == 0)
				{
					is.stackSize--;
					player.playSound(TFC_Sounds.CERAMICBREAK, 0.7f, player.worldObj.rand.nextFloat() * 0.2F + 0.8F);
				}
				else
				{
					is.setItemDamage(1);
				}
			}
		}
		return is;
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
		return EnumAction.drink;
	}

	/**
	 * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
	 */
	@Override
	public ItemStack onItemRightClick(ItemStack is, World world, EntityPlayer entity)
	{
		MovingObjectPosition mop = this.getMovingObjectPositionFromPlayer(world, entity, true);
		FoodStatsTFC fs = TFC_Core.getPlayerFoodStats(entity);
		Boolean canDrink = fs.getMaxWater(entity) - 500 > fs.waterLevel;

		if(mop == null)
		{
			if(is.getItemDamage() > 1 && canDrink) {
				entity.setItemInUse(is, this.getMaxItemUseDuration(is));
			}
		}
		else
		{
			if(mop.typeOfHit == EnumMovingObjectType.TILE)
			{
				int x = mop.blockX;
				int y = mop.blockY;
				int z = mop.blockZ;

				if(!world.canMineBlock(entity, x, y, z))
				{
					return is;
				}

				if(!entity.canPlayerEdit(x, y, z, mop.sideHit, is))
				{
					return is;
				}

				if(!world.isRemote && world.getBlockMaterial(x, y, z) == Material.water && !entity.isSneaking())
				{
					if(is.getItemDamage() == 1)
					{
						is.setItemDamage(2);
					}
					else
					{
						if(canDrink) {
							entity.setItemInUse(is, this.getMaxItemUseDuration(is));
						}
					}
				}
				else
				{
					if(is.getItemDamage() == 2 && canDrink)
					{
						entity.setItemInUse(is, this.getMaxItemUseDuration(is));
					}
				}
			}
		}
		return is;
	}

	@Override
	public Icon getIconFromDamage(int damage)
	{
		if(damage == 0) {
			return this.ClayIcon;
		} else if(damage == 1) {
			return this.CeramicIcon;
		} else if(damage == 2) {
			return this.WaterIcon;
		} 

		return this.WaterIcon; 
	}

	@Override
	public void registerIcons(IconRegister registerer)
	{
		super.registerIcons(registerer);
		this.WaterIcon = registerer.registerIcon(Reference.ModID + ":" + textureFolder + "Water Jug");
	}

	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag) 
	{
		super.addInformation(is, player, arraylist, flag);
		if(is.hasTagCompound() && is.stackTagCompound.hasKey("LiquidType"))
		{
			arraylist.add(is.stackTagCompound.getString("LiquidType"));
		}
	}

	@Override
	public EnumSize getSize() 
	{
		return EnumSize.SMALL;
	}
	@Override
	public EnumWeight getWeight() 
	{
		return EnumWeight.LIGHT;
	}

}
