package com.bioxx.tfc.Items;

import java.util.BitSet;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Items.Tools.ItemChisel;
import com.bioxx.tfc.Items.Tools.ItemHammer;
import com.bioxx.tfc.TileEntities.TEDetailed;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.Util.Helper;

public class ItemBlueprint extends ItemTerra
{
	public static final String SUFFIX = "BR:";

	public static final String TAG_COMPLETED = "Completed";
	public static final String TAG_ITEM_NAME = "ItemName";
	public static final String TAG_DATA = "Data";
	public static final String TAG_X_ANGLE = "xAngle";
	public static final String TAG_Y_ANGLE = "yAngle";
	public static final String TAG_Z_ANGLE = "zAngle";

	public ItemBlueprint()
	{
		super();
		setMaxDamage(0);
		setHasSubtypes(true);
		setUnlocalizedName("Blueprint");
		setCreativeTab(TFCTabs.TFC_TOOLS);
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

		if (mo == null || world.getBlock(mo.blockX, mo.blockY, mo.blockZ) != TFCBlocks.detailed) {
			if (stack.hasTagCompound() && stack.stackTagCompound.getBoolean(TAG_COMPLETED))
				player.openGui(TerraFirmaCraft.instance, 34, player.worldObj, x, y, z);
		}
		else if (!stack.hasTagCompound() || !stack.stackTagCompound.getBoolean(TAG_COMPLETED))
			player.openGui(TerraFirmaCraft.instance, 34, player.worldObj, mo.blockX, mo.blockY, mo.blockZ);

		return stack;
	}

	@Override
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		if (world.getBlock(x, y, z) != TFCBlocks.detailed)
			return false;

		if(!stack.hasTagCompound() || !stack.stackTagCompound.getBoolean(TAG_COMPLETED))
		{
			TEDetailed te = (TEDetailed) world.getTileEntity(x, y, z);

			byte[] data = TEDetailed.toByteArray(te.data);

			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setByteArray(TAG_DATA, data);

			stack.setTagCompound(nbt);
		}
		else if (stack.hasTagCompound() && stack.stackTagCompound.getBoolean(TAG_COMPLETED))
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
				{
					if (!world.isRemote)
					{
						TFC_Core.sendInfoMessage(player, new ChatComponentTranslation("gui.Blueprint.missingTool"));
					}
					return false;
				}
			}

			TEDetailed te = (TEDetailed) world.getTileEntity(x, y, z);
			BitSet blueprintData = TEDetailed.turnCube(
					stack.stackTagCompound.getByteArray(TAG_DATA),
					stack.stackTagCompound.getInteger(TAG_X_ANGLE),
					stack.stackTagCompound.getInteger(TAG_Y_ANGLE),
					stack.stackTagCompound.getInteger(TAG_Z_ANGLE)
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
					}
				}

			if(!world.isRemote)
			{
				world.markBlockForUpdate(x, y, z);
				if (!player.capabilities.isCreativeMode)
				{
					stack.stackSize--;

					if (stack.stackSize <= 0)
						stack = null;
				}
			}
		}
		return false;
	}

	@Override
	public String getItemStackDisplayName(ItemStack is)
	{
		if(is.hasTagCompound() && is.stackTagCompound.hasKey(TAG_COMPLETED))
			return is.stackTagCompound.getString(TAG_ITEM_NAME);
		else
			return TFC_Core.translate(this.getUnlocalizedName());
	}

	@Override
	public void addExtraInformation(ItemStack is, EntityPlayer player, List<String> arraylist)
	{
		if (TFC_Core.showShiftInformation())
		{
			arraylist.add(TFC_Core.translate("gui.Help"));
			arraylist.add(TFC_Core.translate("gui.Blueprint.Inst0"));
			if (is.hasTagCompound() && !is.stackTagCompound.getString(ItemBlueprint.TAG_ITEM_NAME).isEmpty())
				arraylist.add(TFC_Core.translate("gui.Blueprint.Inst1"));
		}
		else
		{
			arraylist.add(TFC_Core.translate("gui.ShowHelp"));
		}
	}
}
