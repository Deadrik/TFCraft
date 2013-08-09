package TFC.TileEntities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import TFC.TerraFirmaCraft;
import TFC.Core.TFC_ItemHeat;
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

public class TileEntityMetallurgy extends TileEntity implements IInventory
{
	public ItemStack metalItemStacks[];
	public Boolean AllMelted = false;

	static
	{

	}

	public TileEntityMetallurgy()
	{
		metalItemStacks = new ItemStack[0];
	}

	public float checkTemps(IInventory inv)
	{
		float temp = 0;
		float[] temp1 = new float[inv.getSizeInventory()];
		for (int i = 0; i < inv.getSizeInventory(); i++)
		{
			ItemStack is = inv.getStackInSlot(i);
			if(is != null && is.hasTagCompound() && !is.getItem().getUnlocalizedName(is).contains("Clay"))
			{
				if(is.getTagCompound().hasKey("temperature"))
				{
					temp1[i] = is.getTagCompound().getFloat("temperature");
					if(temp1[i] < TFC_ItemHeat.getMeltingPoint(is))
					{
						return (float) -1;
					}
				} else {
					return (float) -1;
				}
			}
			else if(is == null)
			{
				temp1[i] = -1;
			}
		}
		int  temp2 = 0;
		for (int i = 0; i < inv.getSizeInventory(); i++)
		{
			if (temp1[i] >= 0)
			{
				temp += temp1[i];
				temp2++;
			}

		}
		if (temp2 > 0)
		{
			temp /= temp2;
		} 
		return temp;
	}

	@Override
	public void closeChest() {


	}

	@Override
	public ItemStack decrStackSize(int i, int j)
	{
		if(metalItemStacks[i] != null)
		{
			if(metalItemStacks[i].stackSize <= j)
			{
				ItemStack itemstack = metalItemStacks[i];
				metalItemStacks[i] = null;
				return itemstack;
			}
			ItemStack itemstack1 = metalItemStacks[i].splitStack(j);
			if(metalItemStacks[i].stackSize == 0)
			{
				metalItemStacks[i] = null;
			}
			return itemstack1;
		} else
		{
			return null;
		}

	}

	@Override
	public int getInventoryStackLimit()
	{
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public String getInvName()
	{
		return "MetallurgyTable";
	}

	public int getSizeInventory()
	{
		return metalItemStacks.length;
	}

	@Override
	public ItemStack getStackInSlot(int i)
	{
		// TODO Auto-generated method stub
		return metalItemStacks[i];
	}



	@Override
	public ItemStack getStackInSlotOnClosing(int var1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void openChest() {
		// TODO Auto-generated method stub

	}

	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		//timeleft = nbttagcompound.getInteger("timeleft");

		NBTTagList nbttaglist = nbttagcompound.getTagList("Items");
		metalItemStacks = new ItemStack[getSizeInventory()];
		for(int i = 0; i < nbttaglist.tagCount(); i++)
		{
			NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.tagAt(i);
			byte byte0 = nbttagcompound1.getByte("Slot");
			if(byte0 >= 0 && byte0 < metalItemStacks.length)
			{
				metalItemStacks[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
			}
		}
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack)
	{
		metalItemStacks[i] = itemstack;
		if(itemstack != null && itemstack.stackSize > getInventoryStackLimit())
		{
			itemstack.stackSize = getInventoryStackLimit();
		}
	}

	public void updateEntity()
	{
		//AllMelted = checkTemps();
	}

	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
		//nbttagcompound.setInteger("timeleft", timeleft);

		NBTTagList nbttaglist = new NBTTagList();
		for(int i = 0; i < metalItemStacks.length; i++)
		{
			if(metalItemStacks[i] != null)
			{
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte)i);
				metalItemStacks[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}

		nbttagcompound.setTag("Items", nbttaglist);


	}

	@Override
	public boolean isInvNameLocalized() 
	{
		return false;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) 
	{
		return false;
	}
}
