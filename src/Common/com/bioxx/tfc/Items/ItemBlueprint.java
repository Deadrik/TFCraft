package com.bioxx.tfc.Items;

import java.util.BitSet;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import com.bioxx.tfc.TFCBlocks;
import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Items.Tools.ItemChisel;
import com.bioxx.tfc.Items.Tools.ItemHammer;
import com.bioxx.tfc.TileEntities.TEDetailed;
import com.bioxx.tfc.api.Util.Helper;

public class ItemBlueprint extends ItemTerra
{
	public static final String suffix = "BR:";

	public static final String tag_completed = "Completed";
	public static final String tag_item_name = "ItemName";
	public static final String tag_data = "Data";
	public static final String tag_x_angle = "xAngle";
	public static final String tag_y_angle = "yAngle";
	public static final String tag_z_angle = "zAngle";

	public ItemBlueprint()
	{
		super();
		setMaxDamage(0);
		setHasSubtypes(true);
		setUnlocalizedName("Blueprint");
		setCreativeTab(TFCTabs.TFCTools);
		setFolder("tools/");
	}

	@Override
	public boolean getShareTag()
	{
		return true;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		MovingObjectPosition mo = Helper.getMouseOverObject(player, world);
		int x = (int)player.posX;
		int y = (int)player.posY;
		int z = (int)player.posZ;
		if (mo != null) {
			x = mo.blockX; y = mo.blockY; z = mo.blockZ;
		}

		if (mo == null || world.getBlock(mo.blockX, mo.blockY, mo.blockZ) != TFCBlocks.Detailed) {
			if (stack.hasTagCompound() && stack.stackTagCompound.getBoolean(tag_completed))
				player.openGui(TerraFirmaCraft.instance, 34, player.worldObj, x, y, z);
		}
		else if (!stack.hasTagCompound() || !stack.stackTagCompound.getBoolean(tag_completed))
			player.openGui(TerraFirmaCraft.instance, 34, player.worldObj, mo.blockX, mo.blockY, mo.blockZ);

		return stack;
	}

	@Override
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		if (world.getBlock(x, y, z) != TFCBlocks.Detailed)
			return false;

		if(!stack.hasTagCompound() || !stack.stackTagCompound.getBoolean(tag_completed))
		{
			TEDetailed te = (TEDetailed) world.getTileEntity(x, y, z);

			byte[] data = TEDetailed.toByteArray(te.data);

			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setByteArray(tag_data, data);

			stack.setTagCompound(nbt);
		}
		else if (stack.hasTagCompound() && stack.stackTagCompound.getBoolean(tag_completed))
		{
			int hasChisel = -1;
			int hasHammer = -1;

			if (!player.capabilities.isCreativeMode) {
				for (int i = 0; i < 9; i++) {
					if (player.inventory.mainInventory[i] != null && player.inventory.mainInventory[i].getItem() instanceof ItemHammer)
						hasHammer = i;
					if (player.inventory.mainInventory[i] != null && player.inventory.mainInventory[i].getItem() instanceof ItemChisel)
						hasChisel = i;
				}

				if (hasChisel == -1 || hasHammer == -1)
					return false;
			}

			TEDetailed te = (TEDetailed) world.getTileEntity(x, y, z);
			BitSet blueprintData = TEDetailed.turnCube(
							stack.stackTagCompound.getByteArray(tag_data),
							stack.stackTagCompound.getInteger(tag_x_angle),
							stack.stackTagCompound.getInteger(tag_y_angle),
							stack.stackTagCompound.getInteger(tag_z_angle)
			);

			for(int c = 0; c < 512; c++)
				if(te.data.get(c) && !blueprintData.get(c))
				{
					te.data.clear(c);

					if (!player.capabilities.isCreativeMode)
					{
						if(player.inventory.mainInventory[hasChisel] != null)
							player.inventory.mainInventory[hasChisel].damageItem(1, player);
						else
							break;

						if(player.inventory.mainInventory[hasHammer] != null)
							player.inventory.mainInventory[hasHammer].damageItem(1, player);
						else
							break;
					}
				}

			if(!world.isRemote)
			{
				world.markBlockForUpdate(x, y, z);
				if (!player.capabilities.isCreativeMode)
					stack.stackSize--;
			}
		}
		return false;
	}

	@Override
	public String getItemStackDisplayName(ItemStack is)
	{
		if(is.hasTagCompound() && is.stackTagCompound.hasKey(tag_completed))
			return suffix + is.stackTagCompound.getString(tag_item_name);
		else
			return StatCollector.translateToLocal(this.getUnlocalizedName());
	}
}
