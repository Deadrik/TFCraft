package TFC.Items;

import java.util.BitSet;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import TFC.TFCBlocks;
import TFC.TerraFirmaCraft;
import TFC.API.Util.Helper;
import TFC.Core.TFCTabs;
import TFC.Core.Util.StringUtil;
import TFC.Items.Tools.ItemChisel;
import TFC.Items.Tools.ItemHammer;
import TFC.TileEntities.TileEntityDetailed;

public class ItemBlueprint extends ItemTerra
{
	public ItemBlueprint(int i)
	{
		super(i);
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
		{
			return stack;
		}

		int side = objectMouseOver.sideHit;
		int x = objectMouseOver.blockX;
		int y = objectMouseOver.blockY;
		int z = objectMouseOver.blockZ;

		if(stack.stackTagCompound != null && !stack.stackTagCompound.hasKey("Name") &&
				(world.getBlockId(x, y, z) == TFCBlocks.Detailed.blockID))
		{
			player.openGui(TerraFirmaCraft.instance, 34, player.worldObj, x, y, z);
		}

		return stack;
	}

	@Override
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) 
	{
		if(stack.stackTagCompound == null &&
				(world.getBlockId(x, y, z) == TFCBlocks.Detailed.blockID))
		{
			TileEntityDetailed te = (TileEntityDetailed) world.getBlockTileEntity(x, y, z);

			byte[] data = TileEntityDetailed.toByteArray(te.data);

			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setByteArray("data", data);

			stack.setTagCompound(nbt);		
		}
		else if(stack.stackTagCompound != null &&
				(world.getBlockId(x, y, z) == TFCBlocks.Detailed.blockID))
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

	@Override
	public String getItemDisplayName(ItemStack par1ItemStack)
	{
		if(par1ItemStack.stackTagCompound != null)
			return par1ItemStack.stackTagCompound.getString("Name");
		else return StringUtil.localize("gui.Blueprint");
	}
}
