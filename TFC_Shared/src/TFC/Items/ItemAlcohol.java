package TFC.Items;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import TFC.Core.TFC_Core;
import TFC.Core.Util.StringUtil;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemAlcohol extends ItemTerra
{

	@SideOnly(Side.CLIENT)
	private Icon field_94591_c;
	@SideOnly(Side.CLIENT)
	private Icon field_94590_d;
	@SideOnly(Side.CLIENT)
	private Icon field_94592_ct;

	public ItemAlcohol(int par1)
	{
		super(par1);
		this.setFolder("food/");
	}

	@Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack)
	{
		return EnumAction.drink;
	}

	@Override
	public String getItemDisplayName(ItemStack itemstack) 
	{
		return StringUtil.localize(getUnlocalizedName(itemstack).replace(" ", ""));
	}

	/*	
	@Override
	public Icon getIconFromDamage(int par1)
	{
		return this.field_94590_d;
	}

	 */
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
	/*
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister)
	{
		this.field_94590_d = par1IconRegister.registerIcon("potion_bottle_drinkable");
		this.field_94592_ct = par1IconRegister.registerIcon("potion_overlay");
	}

	@Override
	@SideOnly(Side.CLIENT)

	/**
	 * Gets an icon index based on an item's damage value and the given render pass

	public Icon getIconFromDamageForRenderPass(int par1, int par2)
	{
		return par2 == 0 ? this.field_94592_ct : super.getIconFromDamageForRenderPass(par1, par2);
	}

	@SideOnly(Side.CLIENT)
	public int getColorFromDamage(int par1)
	{
		return PotionHelper.func_77915_a(14981690, false);
	}


	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses()
	{
		return true;
	}
	 */

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

			TFC_Core.getPlayerFoodStats(player).restoreWater(player, 800);
			long soberTime = player.getEntityData().hasKey("soberTime") ? player.getEntityData().getLong("soberTime") : 0;
			int time = rand.nextInt(1000) + 400;
			System.out.println(soberTime);
			soberTime +=time;
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
			if(soberTime >3000+levelMod){
				if(rand.nextInt(4)==0){
					player.addPotionEffect(new PotionEffect(9,time,4));
				}
				if(soberTime >5000+levelMod){
					if(rand.nextInt(4)==0){
						player.addPotionEffect(new PotionEffect(18,time,4));
					}
					if(soberTime >7000+levelMod){
						if(rand.nextInt(2)==0){
							player.addPotionEffect(new PotionEffect(15,time,4));
						}
						if(soberTime >8000+levelMod){
							if(rand.nextInt(10)==0){
								player.addPotionEffect(new PotionEffect(20,time,4));
							}
						}
						if(soberTime > 10000+levelMod){
							soberTime = 0;
							//((EntityPlayerMP)player).mcServer.getConfigurationManager().sendChatMsg(player.username+" died of alcohol poisoning.");
							player.inventory.dropAllItems();
							player.setHealth(0);
						}
					}

				}
			}
			player.getEntityData().setLong("soberTime",soberTime);
		}

		if (!player.capabilities.isCreativeMode)
		{
			if (par1ItemStack.stackSize <= 0)
			{
				return new ItemStack(Item.glassBottle);
			}

			player.inventory.addItemStackToInventory(new ItemStack(Item.glassBottle));
		}

		return par1ItemStack;
	}

}
