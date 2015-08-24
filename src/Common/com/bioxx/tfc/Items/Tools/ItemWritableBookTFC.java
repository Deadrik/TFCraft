package com.bioxx.tfc.Items.Tools;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import com.bioxx.tfc.Items.ItemTerra;

public class ItemWritableBookTFC extends ItemTerra
{
	public ItemWritableBookTFC()
	{
		super();
		stackable = false;
	}
	public ItemWritableBookTFC(String tex){
		super();
		stackable = false;
		this.setCreativeTab(null);
	}

	/**
	 * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
	 */
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer entityplayer)
	{
		/*if(entityplayer.worldObj.isRemote)
		{
			//TerraFirmaCraft.log.info(par1ItemStack.hasTagCompound());
			//TerraFirmaCraft.log.info(par1ItemStack);
			//Can't do this
			//Minecraft.getMinecraft().displayGuiScreen(new GuiScreenBookTFC(entityplayer, par1ItemStack, false));
		}*/
		return par1ItemStack;
	}

	public static boolean validBookTagContents(NBTTagCompound par0NBTTagCompound)
	{
		if (!ItemWritableBookTFC.validBookTagPages(par0NBTTagCompound))
		{
			return false;
		}
		else if (!par0NBTTagCompound.hasKey("title"))
		{
			return false;
		}
		else
		{
			String var1 = par0NBTTagCompound.getString("title");
			return var1 != null && var1.length() <= 16 ? par0NBTTagCompound.hasKey("author") : false;
		}
	}

	/**
	 * If this function returns true (or the item is damageable), the ItemStack's NBT tag will be sent to the client.
	 */
	@Override
	public boolean getShareTag()
	{
		return true;
	}

	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack)
	{
		if (par1ItemStack.hasTagCompound())
		{
			NBTTagCompound var2 = par1ItemStack.getTagCompound();
			NBTTagString var3 = (NBTTagString)var2.getTag("title");
			//NBTTagString var4 = (NBTTagString)var2.getTag("author");
			if (var3 != null)
			{
				return var3.toString();
			}
		}

		return super.getItemStackDisplayName(par1ItemStack);
	}
	@Override
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
	{
		if (par1ItemStack.hasTagCompound())
		{
			NBTTagCompound var5 = par1ItemStack.getTagCompound();
			NBTTagString var6 = (NBTTagString)var5.getTag("author");

			if (var6 != null)
			{
				par3List.add("\u00a77" + String.format(StatCollector.translateToLocalFormatted("book.byAuthor", new Object[] {var6.func_150285_a_()}), new Object[0]));
			}
		}
		super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
	}

	public static boolean validBookTagPages(NBTTagCompound par0NBTTagCompound)
	{
		if (par0NBTTagCompound == null)
		{
			return false;
		}
		else if (!par0NBTTagCompound.hasKey("pages"))
		{
			return false;
		}
		else
		{
			NBTTagList var1 = (NBTTagList)par0NBTTagCompound.getTag("pages");
			for (int var2 = 0; var2 < var1.tagCount(); ++var2)
			{
				String var3 = var1.getStringTagAt(var2);
				if (var3.isEmpty())
					return false;
				if (var3.length() > 256)
					return false;
			}
			return true;
		}
	}
}
