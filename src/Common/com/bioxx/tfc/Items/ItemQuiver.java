package com.bioxx.tfc.Items;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Items.Tools.ItemJavelin;
import com.bioxx.tfc.api.Enums.EnumAmmo;
import com.bioxx.tfc.api.Interfaces.IEquipable;
import com.bioxx.tfc.api.Interfaces.IQuiverAmmo;

public class ItemQuiver extends ItemTerra implements IEquipable
{
	public ItemQuiver()
	{
		super();
		this.setCreativeTab(TFCTabs.TFC_ARMOR);
	}
	/**
	 * Gets an icon index based on an item's damage value and the given render pass
	 */
	@Override
	public IIcon getIconFromDamageForRenderPass(int par1, int par2)
	{
		return this.itemIcon;
	}

	/*@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
	{
		String m = ArmorType.metaltype.replace(" ", "").toLowerCase();
		return Reference.ModID+String.format(":textures/models/armor/%s_%d%s.png",
				m, (slot == 2 ? 2 : 1), type == null ? "" : String.format("_%s", type));
	}*/

	@Override
	public void onUpdate(ItemStack is, World world, Entity entity, int i, boolean isSelected) 
	{
		super.onUpdate(is, world, entity, i, isSelected);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
	{
		entityplayer.openGui(TerraFirmaCraft.instance, 40, entityplayer.worldObj, (int)entityplayer.posX, (int)entityplayer.posY, (int)entityplayer.posZ);
		return itemstack;
	}

	@Override
	public void registerIcons(IIconRegister registerer)
	{
		this.itemIcon = registerer.registerIcon(Reference.MOD_ID + ":quiver");
	}

	public int getQuiverArrows(ItemStack item){
		int n = 0;
		ItemStack[] inventory = loadInventory(item);
		for(ItemStack i : inventory)
			if(i!=null && i.getItem() instanceof ItemArrow)
				n += i.stackSize;

		return n;
	}

	public int getQuiverJavelins(ItemStack item)
	{
		int n = 0;
		ItemStack[] inventory = loadInventory(item);
		for(ItemStack i : inventory)
			if(i!=null && i.getItem() instanceof ItemJavelin)
				n += i.stackSize;

		return n;
	}

	@SuppressWarnings("rawtypes")
	// Storing both Strings and Integers in the same ArrayList
	public List[] getQuiverJavelinTypes(ItemStack item)
	{
		ArrayList[] pair = new ArrayList[2];
		ArrayList<String> list = new ArrayList<String>();
		ArrayList<Integer> listNum = new ArrayList<Integer>();
		ItemStack[] inventory = loadInventory(item);
		for(ItemStack i : inventory)
		{
			if(i!=null && i.getItem() instanceof ItemJavelin)
			{
				String s = i.getItem().getItemStackDisplayName(i);
				if(!list.contains(s))
					list.add(s);
				int n = list.indexOf(s);
				if(listNum.size() == n)
					listNum.add(0);
				listNum.set(n, listNum.get(n)+1);
			}
		}
		pair[0] = list;
		pair[1] = listNum;
		return pair;
	}

	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag)
	{
		ItemTerra.addSizeInformation(is, arraylist);

		if (TFC_Core.showShiftInformation())
		{
			//arraylist.add(EnumChatFormatting.WHITE + TFC_Core.translate("gui.Advanced") + ":");
			//arraylist.add(EnumChatFormatting.ITALIC + TFC_Core.translate("gui.Armor.Pierce") + ": " + EnumChatFormatting.AQUA + ArmorType.getPiercingAR());
			//arraylist.add(EnumChatFormatting.ITALIC + TFC_Core.translate("gui.Armor.Slash") + ": " + EnumChatFormatting.AQUA + ArmorType.getSlashingAR());
			//arraylist.add(EnumChatFormatting.ITALIC + TFC_Core.translate("gui.Armor.Crush") + ": " + EnumChatFormatting.AQUA + ArmorType.getCrushingAR());
			//arraylist.add("");
			arraylist.add(EnumChatFormatting.WHITE + TFC_Core.translate("gui.Advanced") + ":");
			arraylist.add(EnumChatFormatting.ITALIC + TFC_Core.translate("gui.Bow.Arrows") + ": " + EnumChatFormatting.YELLOW + getQuiverArrows(is));
			arraylist.add(EnumChatFormatting.ITALIC + TFC_Core.translate("gui.Bow.Javelins") + ": " + EnumChatFormatting.YELLOW + getQuiverJavelins(is));
			List[] javData = getQuiverJavelinTypes(is);
			for(int i = 0; i < javData[0].size();i++)
			{
				String s = (String)(javData[0].get(i));
				int n = (Integer)(javData[1].get(i));
				arraylist.add(EnumChatFormatting.ITALIC + "  -" + s + ": "+EnumChatFormatting.YELLOW+n);
			}
			if (is.hasTagCompound())
			{
				NBTTagCompound stackTagCompound = is.getTagCompound();
				if(stackTagCompound.hasKey("creator"))
					arraylist.add(EnumChatFormatting.ITALIC + TFC_Core.translate("gui.Armor.ForgedBy") + " " + stackTagCompound.getString("creator"));
			}
		}
		else
			arraylist.add(EnumChatFormatting.DARK_GRAY + TFC_Core.translate("gui.Advanced") + ": (" + TFC_Core.translate("gui.Hold") + " " + EnumChatFormatting.GRAY + TFC_Core.translate("gui.Shift") +
					EnumChatFormatting.DARK_GRAY + ")");

	}

	public ItemStack addItem(ItemStack quiver, ItemStack ammo)
	{
		ItemStack[] inventory = loadInventory(quiver);
		for(int i = 0; i < inventory.length && ammo != null; i++)
		{
			if(inventory[i] != null && inventory[i].isItemEqual(ammo))
			{
				if(ammo.stackSize + inventory[i].stackSize <= ammo.getMaxStackSize())
				{
					inventory[i].stackSize += ammo.stackSize;
					ammo = null;
				}
				else if(ammo.stackSize + inventory[i].stackSize > ammo.getMaxStackSize())
				{
					int diff = ammo.getMaxStackSize() - inventory[i].stackSize;
					inventory[i].stackSize = ammo.getMaxStackSize();
					ammo.stackSize -= diff;
				}
			}
			else if(inventory[i] == null)
			{
				inventory[i] = ammo.copy();
				ammo = null;
			}
		}
		saveInventory(quiver, inventory);
		return ammo;
	}

	public ItemStack consumeAmmo(ItemStack quiver, EnumAmmo ammoType, boolean shouldConsume)
	{
		ItemStack[] inventory = loadInventory(quiver);
		for(int i = 0; i < inventory.length; i++)
		{
			if(inventory[i] != null && inventory[i].getItem() instanceof IQuiverAmmo && ((IQuiverAmmo)inventory[i].getItem()).getAmmoType() == ammoType)
			{
				ItemStack out = inventory[i].copy();
				out.stackSize = 1;
				if(shouldConsume)
					inventory[i].stackSize--;
				if(inventory[i].stackSize <= 0)
					inventory[i] = null;
				saveInventory(quiver, inventory);
				return out;
			}
		}
		return null;
	}

	public ItemStack[] loadInventory(ItemStack quiver)
	{
		ItemStack[] inventory = new ItemStack[8];
		NBTTagCompound nbt = quiver.getTagCompound();
		if(nbt != null && nbt.hasKey("Items"))
		{
			NBTTagList nbttaglist = nbt.getTagList("Items", 10);

			for(int i = 0; i < nbttaglist.tagCount(); i++)
			{
				NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
				byte byte0 = nbttagcompound1.getByte("Slot");
				if(byte0 >= 0 && byte0 < 8)
					inventory[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
			}
		}
		return inventory;
	}

	public void saveInventory(ItemStack quiver, ItemStack[] inventory)
	{
		NBTTagList nbttaglist = new NBTTagList();
		for(int i = 0; i < inventory.length; i++)
		{
			if(inventory[i] != null)
			{
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte)i);
				inventory[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}
		if(quiver != null)
		{
			if(!quiver.hasTagCompound()) 
				quiver.setTagCompound(new NBTTagCompound());
			quiver.getTagCompound().setTag("Items", nbttaglist);
		}
	}

	@Override
	public EquipType getEquipType(ItemStack is) 
	{
		return EquipType.BACK;
	}
	@Override
	public void onEquippedRender() {
		//GL11.glScalef(0.8F, 0.8F, 0.8F);
	}

	@Override
	public boolean getTooHeavyToCarry(ItemStack is)
	{
		return false;
	}

	@Override
	public int getItemStackLimit()
	{
		return 1;
	}

	@Override
	public boolean canStack()
	{
		return false;
	}
}
