package TFC.Items;

import java.util.BitSet;

import TFC.TFCBlocks;
import TFC.TerraFirmaCraft;
import TFC.Core.Helper;
import TFC.Core.StructureBlueprint;
import TFC.Core.TFCTabs;
import TFC.Core.TFC_Core;
import TFC.TileEntities.TileEntityDetailed;
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
import net.minecraft.world.gen.*;
import net.minecraft.world.gen.feature.*;

public class ItemBlueprint extends ItemTerra
{
	public ItemBlueprint(int i)
	{
		super(i);
		setMaxDamage(0);
		setHasSubtypes(true);
		setItemName("Blueprint");
		setCreativeTab(TFCTabs.TFCTools);
		setIconIndex(0);

	}

	@Override
	public boolean getShareTag()
	{
		return true;
	}

	@Override
	public String getTextureFile()
	{
		return "/bioxx/terratools.png";
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		MovingObjectPosition objectMouseOver = Helper.getMouseOverObject(player, world);
		if(objectMouseOver == null) 
		{
			return stack;
		}

		int side = objectMouseOver.sideHit;
		int x = objectMouseOver.blockX;
		int y = objectMouseOver.blockY;
		int z = objectMouseOver.blockZ;

		if(stack.stackTagCompound != null && !stack.stackTagCompound.hasKey("Name") &&
				(world.getBlockId(x, y, z) == TFCBlocks.Detailed.blockID || world.getBlockId(x, y, z) == TFCBlocks.SuperDetailed.blockID))
		{
			player.openGui(TerraFirmaCraft.instance, 34, player.worldObj, x, y, z);
		}

		return stack;
	}

	@Override
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) 
	{
		if(stack.stackTagCompound == null &&
				(world.getBlockId(x, y, z) == TFCBlocks.Detailed.blockID || world.getBlockId(x, y, z) == TFCBlocks.SuperDetailed.blockID))
		{
			TileEntityDetailed te = (TileEntityDetailed) world.getBlockTileEntity(x, y, z);

			byte[] data = TileEntityDetailed.toByteArray(te.data);

			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setByteArray("data", data);

			stack.setTagCompound(nbt);		
		}
		else if(stack.stackTagCompound != null &&
				(world.getBlockId(x, y, z) == TFCBlocks.Detailed.blockID || world.getBlockId(x, y, z) == TFCBlocks.SuperDetailed.blockID))
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
				
				TileEntityDetailed te = (TileEntityDetailed) world.getBlockTileEntity(x, y, z);
				byte[] data = stack.stackTagCompound.getByteArray("data");
				BitSet blueprintData = te.fromByteArray(data, 512);
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
					TerraFirmaCraft.proxy.sendCustomPacketToPlayersInRange(x, y, z, te.createFullPacket(), 200);
					stack.stackSize--;
				}
			}
		}
		return false;
	}

	public String getItemDisplayName(ItemStack par1ItemStack)
	{
		if(par1ItemStack.stackTagCompound != null)
			return par1ItemStack.stackTagCompound.getString("Name");
		else return "Blueprint";
	}
}
