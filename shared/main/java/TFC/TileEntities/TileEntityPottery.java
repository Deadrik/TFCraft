package TFC.TileEntities;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.packet.Packet;
import net.minecraftforge.common.ForgeDirection;
import TFC.TFCItems;
import TFC.API.TFCOptions;
import TFC.API.Crafting.KilnCraftingManager;
import TFC.API.Crafting.KilnRecipe;
import TFC.Core.TFC_Core;
import TFC.Core.TFC_Time;
import TFC.Core.Metal.Alloy;
import TFC.Handlers.PacketHandler;
import TFC.Items.Pottery.ItemPotteryBase;

public class TileEntityPottery extends NetworkTileEntity implements IInventory
{
	public ItemStack inventory[];
	public boolean hasRack;
	public int logsForBurn;
	public long burnStart;

	public TileEntityPottery()
	{
		inventory = new ItemStack[4];
		hasRack = false;
		logsForBurn = 0;
	}

	@Override
	public void updateEntity()
	{        
		//If there are no logs for burning then we dont need to tick at all
		if(!worldObj.isRemote && logsForBurn > 0)
		{			

			int blockAboveID = worldObj.getBlockId(xCoord, yCoord+1, zCoord);
			//Make sure to keep the fire going throughout the length of the burn
			if(blockAboveID != Block.fire.blockID && TFC_Time.getTotalTicks() - burnStart < TFC_Time.hourLength * TFCOptions.pitKilnBurnTime)
			{
				if((blockAboveID == 0 || worldObj.getBlockMaterial(xCoord, yCoord+1, zCoord).getCanBurn()) && isValid()) 
				{
					worldObj.setBlock(xCoord, yCoord+1, zCoord, Block.fire.blockID);
				} 
				else 
				{
					logsForBurn = 0;
				}
			}

			//If the total time passes then we complete the burn and turn the clay into ceramic
			if(logsForBurn > 0 && blockAboveID == Block.fire.blockID && 
					TFC_Time.getTotalTicks() > burnStart + (TFCOptions.pitKilnBurnTime * TFC_Time.hourLength))
			{
				worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 0, 3);
				worldObj.setBlock(xCoord, yCoord+1, zCoord, 0);
				if(inventory[0] != null)
				{
					inventory[0] = KilnCraftingManager.getInstance().findCompleteRecipe(new KilnRecipe(inventory[0], 0)).copy();
					if(inventory[0].getItem() instanceof ItemPotteryBase)
					{
						((ItemPotteryBase)inventory[0].getItem()).onDoneCooking(worldObj, inventory[0], Alloy.EnumTier.TierI);
					}
				}
				if(inventory[1] != null)
				{
					inventory[1] = KilnCraftingManager.getInstance().findCompleteRecipe(new KilnRecipe(inventory[1], 0)).copy();
					if(inventory[1].getItem() instanceof ItemPotteryBase)
					{
						((ItemPotteryBase)inventory[1].getItem()).onDoneCooking(worldObj, inventory[1], Alloy.EnumTier.TierI);
					}
				}
				if(inventory[2] != null)
				{
					inventory[2] = KilnCraftingManager.getInstance().findCompleteRecipe(new KilnRecipe(inventory[2], 0)).copy();
					if(inventory[2].getItem() instanceof ItemPotteryBase)
					{
						((ItemPotteryBase)inventory[2].getItem()).onDoneCooking(worldObj, inventory[2], Alloy.EnumTier.TierI);
					}
				}
				if(inventory[3] != null)
				{
					inventory[3] = KilnCraftingManager.getInstance().findCompleteRecipe(new KilnRecipe(inventory[3], 0)).copy();
					if(inventory[3].getItem() instanceof ItemPotteryBase)
					{
						((ItemPotteryBase)inventory[3].getItem()).onDoneCooking(worldObj, inventory[3], Alloy.EnumTier.TierI);
					}
				}

				logsForBurn = 0;

				broadcastPacketInRange(createUpdatePacket());
			}


		}
	}	

	public void StartPitFire()
	{
		int meta = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
		TileEntityLogPile telp = (TileEntityLogPile) worldObj.getBlockTileEntity(xCoord, yCoord+1, zCoord);
		if(meta == 15 && telp.getNumberOfLogs() == 16)
		{
			logsForBurn = telp.getNumberOfLogs();
			telp.clearContents();
			worldObj.setBlock(xCoord, yCoord+1, zCoord, Block.fire.blockID);

			burnStart = TFC_Time.getTotalTicks();
		}
	}

	public boolean isValid()
	{
		boolean surroundSolids = TFC_Core.isNorthSolid(worldObj, xCoord, yCoord, zCoord) && TFC_Core.isSouthSolid(worldObj, xCoord, yCoord, zCoord) && 
				TFC_Core.isEastSolid(worldObj, xCoord, yCoord, zCoord) && TFC_Core.isWestSolid(worldObj, xCoord, yCoord, zCoord);
		boolean surroundSolidsUp1 = TFC_Core.isNorthSolid(worldObj, xCoord, yCoord+1, zCoord) && TFC_Core.isSouthSolid(worldObj, xCoord, yCoord+1, zCoord) && 
				TFC_Core.isEastSolid(worldObj, xCoord, yCoord+1, zCoord) && TFC_Core.isWestSolid(worldObj, xCoord, yCoord+1, zCoord);
		return surroundSolids && surroundSolidsUp1 && worldObj.isBlockSolidOnSide(xCoord, yCoord-1, zCoord, ForgeDirection.UP);
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack)
	{
		inventory[i] = itemstack;
		if(itemstack != null && itemstack.stackSize > getInventoryStackLimit())
		{
			itemstack.stackSize = getInventoryStackLimit();
		}
	}

	public void ejectContents()
	{
		float f3 = 0.05F;
		EntityItem entityitem;
		Random rand = new Random();
		float f = rand.nextFloat() * 0.8F + 0.1F;
		float f1 = rand.nextFloat() * 2.0F + 0.4F;
		float f2 = rand.nextFloat() * 0.8F + 0.1F;

		for (int i = 0; i < getSizeInventory(); i++)
		{
			if(inventory[i]!= null)
			{
				entityitem = new EntityItem(worldObj, xCoord + f, yCoord + f1, zCoord + f2, 
						inventory[i]);
				entityitem.motionX = (float)rand.nextGaussian() * f3;
				entityitem.motionY = (float)rand.nextGaussian() * f3 + 0.2F;
				entityitem.motionZ = (float)rand.nextGaussian() * f3;
				worldObj.spawnEntityInWorld(entityitem);
				inventory[i] = null;
			}
		}
	}
	
	public void ejectItem(int index)
	{
		float f3 = 0.01F;
		EntityItem entityitem;
		Random rand = new Random();

		if(inventory[index] != null)
		{
			entityitem = new EntityItem(worldObj, xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, inventory[index]);
			entityitem.lifespan = 48000;
			worldObj.spawnEntityInWorld(entityitem);
			inventory[index] = null;
		}

		if(inventory[0] == null && inventory[1] == null && inventory[2] == null && inventory[3] == null)
		{
			// eject straw before destroying block
			int m = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
			if(m > 0)
			{
				entityitem = new EntityItem(
						worldObj, xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, new ItemStack(TFCItems.Straw, m));
				entityitem.lifespan = 48000;
				worldObj.spawnEntityInWorld(entityitem);
			}
			worldObj.setBlock(xCoord, yCoord, zCoord, 0);
		}
	}

	public Packet createUpdatePacket()
	{
		ByteArrayOutputStream bos=new ByteArrayOutputStream(140);
		DataOutputStream dos=new DataOutputStream(bos);

		try {
			dos.writeByte(PacketHandler.Packet_Data_Block_Client);
			dos.writeInt(xCoord);
			dos.writeInt(yCoord);
			dos.writeInt(zCoord);
			if(inventory[0] != null)
			{
				dos.writeInt(inventory[0].itemID);
				dos.writeInt(inventory[0].getItemDamage());
			}
			else
			{
				dos.writeInt(0);
				dos.writeInt(0);
			}
			if(inventory[1] != null)
			{
				dos.writeInt(inventory[1].itemID);
				dos.writeInt(inventory[1].getItemDamage());
			}
			else
			{
				dos.writeInt(0);
				dos.writeInt(0);
			}
			if(inventory[2] != null)
			{
				dos.writeInt(inventory[2].itemID);
				dos.writeInt(inventory[2].getItemDamage());
			}
			else
			{
				dos.writeInt(0);
				dos.writeInt(0);
			}
			if(inventory[3] != null)
			{
				dos.writeInt(inventory[3].itemID);
				dos.writeInt(inventory[3].getItemDamage());
			}
			else
			{
				dos.writeInt(0);
				dos.writeInt(0);
			}
			dos.writeBoolean(hasRack);
		} catch (IOException e) {
		}

		return this.setupCustomPacketData(bos.toByteArray(), bos.size());
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

	@Override
	public int getSizeInventory()
	{
		return inventory.length;
	}

	@Override
	public ItemStack getStackInSlot(int i)
	{
		// TODO Auto-generated method stub  
		return inventory[i];
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int var1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);

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

		nbttagcompound.setTag("Items", nbttaglist);

		nbttagcompound.setLong("burnStart", burnStart);
		nbttagcompound.setBoolean("hasRack", hasRack);
		nbttagcompound.setInteger("logsForBurn", logsForBurn);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);

		NBTTagList nbttaglist = nbt.getTagList("Items");
		inventory = new ItemStack[getSizeInventory()];
		for(int i = 0; i < nbttaglist.tagCount(); i++)
		{
			NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.tagAt(i);
			byte byte0 = nbttagcompound1.getByte("Slot");
			if(byte0 >= 0 && byte0 < inventory.length)
			{
				inventory[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
			}
		}
		burnStart = nbt.getLong("burnStart");
		logsForBurn = nbt.getInteger("logsForBurn");
		hasRack = nbt.getBoolean("hasRack");
	}

	@Override
	public void createInitPacket(DataOutputStream outStream) throws IOException  
	{
		if(inventory[0] != null)
		{
			outStream.writeInt(inventory[0].itemID);
			outStream.writeInt(inventory[0].getItemDamage());
		}
		else
		{
			outStream.writeInt(0);
			outStream.writeInt(0);
		}
		if(inventory[1] != null)
		{
			outStream.writeInt(inventory[1].itemID);
			outStream.writeInt(inventory[1].getItemDamage());
		}
		else
		{
			outStream.writeInt(0);
			outStream.writeInt(0);
		}
		if(inventory[2] != null)
		{
			outStream.writeInt(inventory[2].itemID);
			outStream.writeInt(inventory[2].getItemDamage());
		}
		else
		{
			outStream.writeInt(0);
			outStream.writeInt(0);
		}
		if(inventory[3] != null)
		{
			outStream.writeInt(inventory[3].itemID);
			outStream.writeInt(inventory[3].getItemDamage());
		}
		else
		{
			outStream.writeInt(0);
			outStream.writeInt(0);
		}
		outStream.writeBoolean(hasRack);
	}

	@Override
	public void handleInitPacket(DataInputStream inStream) throws IOException 
	{
		int inv0 = inStream.readInt();
		int inv0d = inStream.readInt();
		int inv1 = inStream.readInt();
		int inv1d = inStream.readInt();
		int inv2 = inStream.readInt();
		int inv2d = inStream.readInt();
		int inv3 = inStream.readInt();
		int inv3d = inStream.readInt();

		hasRack = inStream.readBoolean();

		inventory[0] = inv0 != 0 ? new ItemStack(inv0, 1, inv0d) : null;
		inventory[1] = inv1 != 0 ? new ItemStack(inv1, 1, inv1d) : null;
		inventory[2] = inv2 != 0 ? new ItemStack(inv2, 1, inv2d) : null;
		inventory[3] = inv3 != 0 ? new ItemStack(inv3, 1, inv3d) : null;
		this.worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
	}

	@Override
	public void handleDataPacketServer(DataInputStream inStream)throws IOException 
	{

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

	@Override
	public ItemStack decrStackSize(int i, int j) {
		return null;
	}

	@Override
	public String getInvName() {
		return "Pottery";
	}

	@Override
	public int getInventoryStackLimit() {
		return 0;
	}

	@Override
	public void closeChest() 
	{		
	}

	@Override
	public void handleDataPacket(DataInputStream inStream) throws IOException 
	{
		int inv0 = inStream.readInt();
		int inv0d = inStream.readInt();
		int inv1 = inStream.readInt();
		int inv1d = inStream.readInt();
		int inv2 = inStream.readInt();
		int inv2d = inStream.readInt();
		int inv3 = inStream.readInt();
		int inv3d = inStream.readInt();

		hasRack = inStream.readBoolean();

		inventory[0] = inv0 != 0 ? new ItemStack(inv0, 1, inv0d) : null;
		inventory[1] = inv1 != 0 ? new ItemStack(inv1, 1, inv1d) : null;
		inventory[2] = inv2 != 0 ? new ItemStack(inv2, 1, inv2d) : null;
		inventory[3] = inv3 != 0 ? new ItemStack(inv3, 1, inv3d) : null;
		this.worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
	}
}
