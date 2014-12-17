package com.bioxx.tfc.Items;

import java.util.Random;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidContainerRegistry;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.TFCItems;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.Core.Player.FoodStatsTFC;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemAlcohol extends ItemTerra
{
	public ItemAlcohol()
	{
		super();
		this.setFolder("food/");
		this.setContainerItem(TFCItems.GlassBottle);
	}

	@Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack)
	{
		return EnumAction.drink;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
		return par1ItemStack;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack)
	{
		return 32;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister registerer)
	{
		this.itemIcon = registerer.registerIcon(Reference.ModID + ":Glass Bottle Overlay");		
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(ItemStack stack, int pass)
	{
		return pass == 1 ? this.itemIcon : this.getContainerItem().getIcon(stack, pass);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack is, int pass)
	{
		return pass == 1 ? FluidContainerRegistry.getFluidForFilledItem(is).getFluid().getColor() : super.getColorFromItemStack(is, pass);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses()
	{
		return true;
	}


	@Override
	public ItemStack onEaten(ItemStack par1ItemStack, World par2World, EntityPlayer player)
	{
		if (!player.capabilities.isCreativeMode)
		{
			--par1ItemStack.stackSize;
		}

		if (!par2World.isRemote)
		{

			Random rand = new Random();
			FoodStatsTFC fs = TFC_Core.getPlayerFoodStats(player);
			fs.restoreWater(player, 800);
			int time = 400+rand.nextInt(1000);
			fs.consumeAlcohol();
			if(rand.nextInt(100)==0){
				player.addPotionEffect(new PotionEffect(8,time,4));
			}
			if(rand.nextInt(100)==0){
				player.addPotionEffect(new PotionEffect(5,time,4));
			}
			if(rand.nextInt(100)==0){
				player.addPotionEffect(new PotionEffect(8,time,4));
			}
			if(rand.nextInt(200)==0){
				player.addPotionEffect(new PotionEffect(10,time,4));
			}
			if(rand.nextInt(150)==0){
				player.addPotionEffect(new PotionEffect(12,time,4));
			}
			if(rand.nextInt(180)==0){
				player.addPotionEffect(new PotionEffect(13,time,4));
			}
			int levelMod = 250*player.experienceLevel;
			if(fs.soberTime >TFC_Time.getTotalTicks()+3000+levelMod){
				if(rand.nextInt(4)==0){
					//player.addPotionEffect(new PotionEffect(9,time,4));
				}
				if(fs.soberTime >TFC_Time.getTotalTicks()+5000+levelMod){
					if(rand.nextInt(4)==0){
						player.addPotionEffect(new PotionEffect(18,time,4));
					}
					if(fs.soberTime >TFC_Time.getTotalTicks()+7000+levelMod){
						if(rand.nextInt(2)==0){
							player.addPotionEffect(new PotionEffect(15,time,4));
						}
						if(fs.soberTime >TFC_Time.getTotalTicks()+8000+levelMod){
							if(rand.nextInt(10)==0){
								player.addPotionEffect(new PotionEffect(20,time,4));
							}
						}
						if(fs.soberTime > TFC_Time.getTotalTicks()+10000+levelMod && !player.capabilities.isCreativeMode){
							fs.soberTime = 0;

							player.attackEntityFrom((new DamageSource("alcohol")).setDamageBypassesArmor().setDamageIsAbsolute(), player.getMaxHealth());
						}
					}

				}
			}
			TFC_Core.setPlayerFoodStats(player, fs);
		}

		if (!player.capabilities.isCreativeMode)
		{
			if (par1ItemStack.stackSize <= 0)
			{
				return new ItemStack(TFCItems.GlassBottle);
			}

			player.inventory.addItemStackToInventory(new ItemStack(TFCItems.GlassBottle));
		}

		return par1ItemStack;
	}

}
