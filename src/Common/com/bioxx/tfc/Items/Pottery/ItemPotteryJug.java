package com.bioxx.tfc.Items.Pottery;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Sounds;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.Core.Player.FoodStatsTFC;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Enums.EnumWeight;

public class ItemPotteryJug extends ItemPotteryBase
{
	IIcon WaterIcon;

	public ItemPotteryJug()
	{
		super();
		this.MetaNames = new String[]{"Clay Jug", "Ceramic Jug", "Water Jug"};
		this.stackable = false;

		this.setWeight(EnumWeight.LIGHT);
		this.setSize(EnumSize.SMALL);
	}

	@Override
	public ItemStack onEaten(ItemStack is, World world, EntityPlayer player)
	{
		if (!world.isRemote)
		{
			if(is.getItemDamage() == 2)
			{
				TFC_Core.getPlayerFoodStats(player).restoreWater(player, 24000);
			}

			if (is.getItemDamage() > 1 && !player.capabilities.isCreativeMode)
			{
				if(world.rand.nextInt(50) == 0)
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
	public int getMaxItemUseDuration(ItemStack is)
	{
		return 32;
	}

	/**
	 * returns the action that specifies what animation to play when the items is being used
	 */
	@Override
	public EnumAction getItemUseAction(ItemStack is)
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

		boolean playNote = false;
		float lookAngle = 0;
		if(mop == null)
		{
			if(is.getItemDamage() > 1 && canDrink)
			{
				entity.setItemInUse(is, this.getMaxItemUseDuration(is));
			}
			else if(is.getItemDamage() == 1){
				Vec3 lookVec = entity.getLookVec();
				lookAngle = (float)(lookVec.yCoord/2d);
				if(!is.hasTagCompound()){
					playNote = true;
					is.stackTagCompound = new NBTTagCompound();
					is.stackTagCompound.setLong("blowTime", TFC_Time.getTotalTicks());
				}
				else if(is.stackTagCompound.hasKey("blowTime") &&	
						(is.stackTagCompound.getLong("blowTime") + 10 < TFC_Time.getTotalTicks())){
					playNote = true;
					is.stackTagCompound.setLong("blowTime", TFC_Time.getTotalTicks());
				}
				else if(!is.stackTagCompound.hasKey("blowTime")){
					playNote = true;
					is.stackTagCompound.setLong("blowTime", TFC_Time.getTotalTicks());
				}
			}
		}
		else
		{
			if(mop.typeOfHit == MovingObjectType.BLOCK)
			{
				int x = mop.blockX;
				int y = mop.blockY;
				int z = mop.blockZ;

				if(!world.isRemote && TFC_Core.isFreshWater(world.getBlock(x, y, z)) && !entity.isSneaking())
				{
					if(is.getItemDamage() == 1)
					{
						is.setItemDamage(2);
						playNote = false;
					}
					else
					{
						if(canDrink)
						{
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
					else if(is.getItemDamage() == 1){
						Vec3 lookVec = entity.getLookVec();
						lookAngle = (float)(lookVec.yCoord/2d);
						if(!is.hasTagCompound()){
							is.stackTagCompound = new NBTTagCompound();
							is.stackTagCompound.setLong("blowTime", TFC_Time.getTotalTicks());
						}
						else if(is.stackTagCompound.hasKey("blowTime") &&	
								(is.stackTagCompound.getLong("blowTime") + 10 < TFC_Time.getTotalTicks())){
							is.stackTagCompound.setLong("blowTime", TFC_Time.getTotalTicks());
						}
						else if(!is.stackTagCompound.hasKey("blowTime")){
							playNote = true;
							is.stackTagCompound.setLong("blowTime", TFC_Time.getTotalTicks());
						}
					}
				}

				if(!world.canMineBlock(entity, x, y, z))
				{
					return is;
				}

				if(!entity.canPlayerEdit(x, y, z, mop.sideHit, is))
				{
					return is;
				}
			}
		}
		if(playNote){
			entity.playSound(TFC_Sounds.JUGBLOW, 1.0f, 1.0f + lookAngle);
		}
		return is;
	}

	@Override
	public IIcon getIconFromDamage(int damage)
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
	public void registerIcons(IIconRegister registerer)
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
}
