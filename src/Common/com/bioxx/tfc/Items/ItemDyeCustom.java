package com.bioxx.tfc.Items;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockColored;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.BonemealEvent;

import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import com.bioxx.tfc.Core.TFCTabs;

public class ItemDyeCustom extends ItemTerra
{
	/** List of dye color names */
	public static final String[] DYE_COLOR_NAMES = new String[] {"black", "red", "green", "brown", "blue", "purple", "cyan", "silver", "gray", "pink", "lime", "yellow", "light_blue", "magenta", "orange", "white"};
	public static final int[] DYE_COLORS = new int[] {1973019, 11743532, 3887386, 5320730, 2437522, 8073150, 2651799, 2651799, 4408131, 14188952, 4312372, 14602026, 6719955, 12801229, 15435844, 15790320};
	@SideOnly(Side.CLIENT)
	private IIcon[] icons;

	public ItemDyeCustom()
	{
		super();
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
		this.setCreativeTab(TFCTabs.TFC_MATERIALS);
	}

	@Override
	@SideOnly(Side.CLIENT)

	/**
	 * Gets an icon index based on an item's damage value
	 */
	public IIcon getIconFromDamage(int par1)
	{
		int j = MathHelper.clamp_int(par1, 0, 15);
		return this.icons[j];
	}

	/**
	 * Returns the unlocalized name of this item. This version accepts an ItemStack so different stacks can have
	 * different names based on their damage or NBT.
	 */
	@Override
	public String getUnlocalizedName(ItemStack par1ItemStack)
	{
		int i = MathHelper.clamp_int(par1ItemStack.getItemDamage(), 0, 15);
		return super.getUnlocalizedName() + "." + DYE_COLOR_NAMES[i];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister par1IconRegister)
	{
		this.icons = new IIcon[DYE_COLOR_NAMES.length];

		for (int i = 0; i < DYE_COLOR_NAMES.length; ++i)
		{
			this.icons[i] = par1IconRegister.registerIcon(this.getIconString() + "_" + DYE_COLOR_NAMES[i]);
		}
	}

	@Override
	public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
	{
		if (!par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack))
		{
			return false;
		}
		else
		{
			Block var11;
			//int var12;
			//int var13;

			if (par1ItemStack.getItemDamage() == 15)
			{
				var11 = par3World.getBlock(par4, par5, par6);

				BonemealEvent event = new BonemealEvent(par2EntityPlayer, par3World, var11, par4, par5, par6);
				if (MinecraftForge.EVENT_BUS.post(event))
				{
					return false;
				}

				if (event.getResult() == Result.ALLOW)
				{
					if (!par3World.isRemote)
					{
						par1ItemStack.stackSize--;
					}
					return true;
				}
			}
			return false;
		}
	}

	@Override
	public boolean itemInteractionForEntity(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, EntityLivingBase par3EntityLivingBase)
	{
		if (par3EntityLivingBase instanceof EntitySheep)
		{
			EntitySheep entitysheep = (EntitySheep)par3EntityLivingBase;
			int var4 = BlockColored.func_150032_b(par1ItemStack.getItemDamage());

			if (!entitysheep.getSheared() && entitysheep.getFleeceColor() != var4)
			{
				entitysheep.setFleeceColor(var4);
				--par1ItemStack.stackSize;
			}

			return true;
		}
		else
		{
			return false;
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		for (int var4 = 0; var4 < 16; ++var4)
		{
			par3List.add(new ItemStack(par1, 1, var4));
		}
	}
}
