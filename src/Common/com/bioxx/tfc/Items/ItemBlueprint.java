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
		MovingObjectPosition objectMouseOver = Helper.getMouseOverObject(player, world);
		if(objectMouseOver == null) 
			return stack;

		int side = objectMouseOver.sideHit;
		int x = objectMouseOver.blockX;
		int y = objectMouseOver.blockY;
		int z = objectMouseOver.blockZ;

		if(stack.stackTagCompound != null && !stack.stackTagCompound.hasKey("Name") &&
				(world.getBlock(x, y, z) == TFCBlocks.Detailed))
		{
			player.openGui(TerraFirmaCraft.instance, 34, player.worldObj, x, y, z);
		}

		return stack;
	}

	@Override
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		if(stack.stackTagCompound == null &&
				(world.getBlock(x, y, z) == TFCBlocks.Detailed))
		{
			TEDetailed te = (TEDetailed) world.getTileEntity(x, y, z);

			byte[] data = TEDetailed.toByteArray(te.data);

			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setByteArray("data", data);

			stack.setTagCompound(nbt);
		}
		else if(stack.stackTagCompound != null && (world.getBlock(x, y, z) == TFCBlocks.Detailed))
		{
			int hasChisel = -1;
			int hasHammer = -1;

			for(int i = 0; i < 9;i++)
			{
				if(player.inventory.mainInventory[i] != null && player.inventory.mainInventory[i].getItem() instanceof ItemHammer)
					hasHammer = i;
				if(player.inventory.mainInventory[i] != null && player.inventory.mainInventory[i].getItem() instanceof ItemChisel)
					hasChisel = i;
			}

			if(hasChisel >= 0 && hasHammer >= 0)
			{
				
				TEDetailed te = (TEDetailed) world.getTileEntity(x, y, z);
				byte[] data = stack.stackTagCompound.getByteArray("data");
				BitSet blueprintData = TEDetailed.fromByteArray(data, 512);
				for(int c = 0; c < 512; c++)
				{
					if(te.data.get(c) && !blueprintData.get(c))
					{
						te.data.clear(c);

						if(player.inventory.mainInventory[hasChisel] != null)
							player.inventory.mainInventory[hasChisel].damageItem(1, player);

						if(player.inventory.mainInventory[hasHammer] != null)
							player.inventory.mainInventory[hasHammer].damageItem(1, player);
					}
				}
				//te.data.and(blueprintData);
				if(!world.isRemote)
				{
					//TODO TerraFirmaCraft.proxy.sendCustomPacketToPlayersInRange(x, y, z, te.createFullPacket(), 200);
					stack.stackSize--;
				}
			}
		}
		return false;
	}

	@Override
	public String getItemStackDisplayName(ItemStack is)
	{
		if(is.stackTagCompound != null)
			return is.stackTagCompound.getString("Name");
		else return StatCollector.translateToLocal("gui.Blueprint");
	}
}
