package TFC.Items;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import TFC.Reference;
import TFC.TerraFirmaCraft;
import TFC.API.IQuiverAmmo;
import TFC.API.Enums.EnumAmmo;

public class ItemQuiver extends ItemTerra
{

	public ItemQuiver() 
	{
		super();
	}

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
		this.itemIcon = registerer.registerIcon(Reference.ModID + ":quiver");
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
				if(shouldConsume) {
					inventory[i].stackSize--;
				}
				if(inventory[i].stackSize <= 0) {
					inventory[i] = null;
				}
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
			NBTTagList nbttaglist = nbt.getTagList("Items", nbt.getTag("Items").getId()); // or is it 10?

			for(int i = 0; i < nbttaglist.tagCount(); i++)
			{
				NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
				byte byte0 = nbttagcompound1.getByte("Slot");
				if(byte0 >= 0 && byte0 < 8)
				{
					inventory[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
				}
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
			{
				quiver.setTagCompound(new NBTTagCompound());
			}
			quiver.getTagCompound().setTag("Items", nbttaglist);
		}
	}

}
