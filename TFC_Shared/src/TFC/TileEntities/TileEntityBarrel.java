package TFC.TileEntities;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Random;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.packet.Packet;
import TFC.TFCItems;
import TFC.TerraFirmaCraft;
import TFC.API.TFCOptions;
import TFC.Core.TFC_ItemHeat;
import TFC.Core.TFC_Time;
import TFC.Core.Util.StringUtil;
import TFC.Handlers.PacketHandler;
import TFC.Items.ItemTerra;

public class TileEntityBarrel extends NetworkTileEntity implements IInventory
{

	public int liquidLevel;
	public int Type;
	public int barrelType;
	private ItemStack itemstack;
	private ItemStack output;
	private boolean sealed;
	private int sealtimecounter;
	public int[] alcohols;
	public final int SEALTIME = TFCOptions.enableDebugMode?0:(int)((TFC_Time.hourLength*12)/100);//default 80

	public TileEntityBarrel()
	{
		liquidLevel = 0;
		shouldSendInitData = true;
		sealed = false;
		//itemstack = new ItemStack(1,0,0);
		sealtimecounter = 0;
		alcohols = new int[]{TFCItems.Beer.itemID,TFCItems.Cider.itemID,TFCItems.Vodka.itemID,TFCItems.Whiskey.itemID,
				TFCItems.RyeWhiskey.itemID,TFCItems.Sake.itemID,TFCItems.Rum.itemID};
	}

	public void careForInventorySlot()
	{
		if(Type ==1 && itemstack!=null&&  itemstack.getItem() instanceof ItemTerra )
		{
			if(itemstack.hasTagCompound() && itemstack.getTagCompound().hasKey("temperature"))
			{
				NBTTagCompound comp = itemstack.getTagCompound();
				float temp = comp.getFloat("temperature");
				if(liquidLevel >= 12 && temp >20)
				{
					temp-=100;
					liquidLevel-=12;

					comp.setFloat("temperature",temp);
					itemstack.setTagCompound(comp);

					TFC_ItemHeat.HandleItemHeat(itemstack, xCoord, yCoord, zCoord);
				}
			}
		}
	}

	public boolean getSealed()
	{
		return sealed;
	}

	private void ProcessItems()
	{
		ItemStack itemstack2;
		if(itemstack != null && Type == 1)
		{
			if (itemstack.getItem() == TFCItems.ScrapedHide){
				itemstack2 = new ItemStack(TFCItems.PrepHide,0,0);
				while(liquidLevel >= 20 && itemstack.stackSize >0){
					liquidLevel-=20;
					itemstack2.stackSize++;
					itemstack.stackSize--;
				}
				if(itemstack2.stackSize > 0){
					output = itemstack2;
				}
			}
			else if(itemstack.getItem() == TFCItems.Logs){
				itemstack.stackSize--;
				if(itemstack.stackSize ==0){
					itemstack=null;
				}
				Type = 3;
			}
			else if(itemstack.getItem() == TFCItems.BarleyGrain){
				itemstack.stackSize--;
				if(itemstack.stackSize ==0){
					itemstack=null;
				}
				Type = 5;
			}
			else if((itemstack.getItem() == TFCItems.RedApple||itemstack.getItem()==TFCItems.GreenApple)){
				itemstack.stackSize--;
				if(itemstack.stackSize ==0){
					itemstack=null;
				}
				Type = 6;
			}
			else if(itemstack.getItem() == TFCItems.Potato){
				itemstack.stackSize--;
				if(itemstack.stackSize ==0){
					itemstack=null;
				}
				Type = 7;
			}
			else if(itemstack.getItem() == TFCItems.WheatGrain){
				itemstack.stackSize--;
				if(itemstack.stackSize ==0){
					itemstack=null;
				}
				Type = 8;
			}
			else if(itemstack.getItem() == TFCItems.RyeGrain){
				itemstack.stackSize--;
				if(itemstack.stackSize ==0){
					itemstack=null;
				}
				Type = 9;
			}
			else if(itemstack.getItem() == TFCItems.RiceGrain){
				itemstack.stackSize--;
				if(itemstack.stackSize ==0){
					itemstack=null;
				}
				Type = 10;
			}
			else if(itemstack.getItem() == Item.sugar){
				itemstack.stackSize--;
				if(itemstack.stackSize ==0){
					itemstack=null;
				}
				Type = 11;
			}
			
		}
		else if(itemstack != null && Type == 2)
		{
			if(itemstack.getItem() == TFCItems.Hide)
			{
				itemstack2 = new ItemStack(TFCItems.SoakedHide,0,0);
				while(liquidLevel >= 20 && itemstack.stackSize >0)
				{
					System.out.println(liquidLevel);
					liquidLevel-=20;
					itemstack2.stackSize++;
					itemstack.stackSize--;
				}
				if(itemstack2.stackSize > 0)
				{
					output = itemstack2;
				}
			}
		}
		else if(itemstack != null && Type == 3)
		{
			if(itemstack.getItem() == TFCItems.PrepHide)
			{
				itemstack2 = new ItemStack(TFCItems.TerraLeather,0,0);
				while(liquidLevel >= 20 && itemstack.stackSize >0)
				{
					liquidLevel-=20;
					itemstack2.stackSize++;
					itemstack.stackSize--;
				}
				if(itemstack2.stackSize > 0)
				{
					output = itemstack2;
				}
			}
		}
		else if(itemstack == null && Type >= 5 && Type <= 11)
		{
			Type = 12;
		}
		else if(itemstack == null && Type == 14)
		{
			itemstack2 = new ItemStack(TFCItems.Cheese,0,0);
			while(liquidLevel >= 32)
			{
				liquidLevel-=32;
				itemstack2.stackSize++;
			}
			if(itemstack2.stackSize > 0)
			{
				output = itemstack2;
			}
		}
		if (liquidLevel == 0)
		{
			Type = 0;
		}
		if (itemstack!=null && itemstack.stackSize==0)
		{
			itemstack = null;
		}
		
	}

	public void setSealed(){
		sealed = true;
	}
	
	public void setUnsealed(String reason){
		if(reason.equals("killing fuse")){
		sealed = false;
		}
	}

	public String getType()
	{
		switch (Type)
		{
		case 0:
			return "";
		case 1:
			return StringUtil.localize("gui.Barrel.Water");
		case 2:
			return StringUtil.localize("gui.Barrel.Limewater");
		case 3:
			return StringUtil.localize("gui.Barrel.Tannin");
		case 4:
			return StringUtil.localize("gui.Barrel.Gunpowder");
		case 5:
			return StringUtil.localize("gui.Barrel.Beer");
		case 6:
			return StringUtil.localize("gui.Barrel.Cider");
		case 7:
			return StringUtil.localize("gui.Barrel.Vodka");
		case 8:
			return StringUtil.localize("gui.Barrel.Whiskey");
		case 9:
			return StringUtil.localize("gui.Barrel.RyeWhiskey");
		case 10:
			return StringUtil.localize("gui.Barrel.Sake");
		case 11:
			return StringUtil.localize("gui.Barrel.Rum");
		case 12:
			return StringUtil.localize("gui.Barrel.Vinegar");
		case 13:
			return StringUtil.localize("gui.Barrel.Milk");
		case 14:
			return StringUtil.localize("gui.Barrel.CurdledMilk");
		default:
			return "";
		}
	}

	@Override
	public void closeChest() {
		// TODO Auto-generated method stub

	}

	@Override
	public ItemStack decrStackSize(int i, int j)
	{
		if(itemstack != null)
		{
			if(itemstack.stackSize <= j)
			{
				ItemStack itemstack2 = itemstack;
				itemstack = null;
				return itemstack2;
			}
			ItemStack itemstack1 = itemstack.splitStack(j);
			if(itemstack.stackSize == 0)
			{
				itemstack = null;
			}
			return itemstack1;
		} else
		{
			return null;
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
			if(itemstack != null)
			{
				entityitem = new EntityItem(worldObj, xCoord + f, yCoord + f1, zCoord + f2, 
						itemstack);
				entityitem.motionX = (float)rand.nextGaussian() * f3;
				entityitem.motionY = (float)rand.nextGaussian() * f3 + 0.2F;
				entityitem.motionZ = (float)rand.nextGaussian() * f3;
				worldObj.spawnEntityInWorld(entityitem);
			}
		}
	}

	@Override
	public int getInventoryStackLimit()
	{
		// TODO Auto-generated method stub
		return 64;
	}

	@Override
	public String getInvName()
	{
		return "Barrel";
	}

	@Override
	public int getSizeInventory()
	{
		return 1;
	}

	@Override
	public ItemStack getStackInSlot(int i)
	{
		// TODO Auto-generated method stub
		return itemstack;
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

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack)
	{
		this.itemstack = itemstack;
		if(itemstack != null && itemstack.stackSize > getInventoryStackLimit())
		{
			itemstack.stackSize = getInventoryStackLimit();
		}


	}

	public boolean checkValidAddition(int i){
		if(i == Type && !sealed && liquidLevel < 256)
		{
			liquidLevel = Math.min(liquidLevel+32, 256);
			if(i == 2)
			{
				Type = 2;
			}
			return true;
		}
		return false;
	}

	@Override
	public void updateEntity()
	{
		if(!worldObj.isRemote)
		{
			careForInventorySlot();
			if(sealed)
			{
				//entityplayer.closeScreen();
				//This is where we handle the counter for producing charcoal. Once it reaches 24hours, we add charcoal to the fire and remove the wood.
				if(sealtimecounter == 0)
				{
					sealtimecounter = (int) TFC_Time.getTotalTicks();
				}

				if(sealtimecounter > 0 && sealtimecounter + (SEALTIME*100) < TFC_Time.getTotalTicks() )
				{
					sealtimecounter = 0;
					sealed = false;
					ProcessItems();                
				}
			}


			if(liquidLevel == 0)
			{
				Type = 0; 
			}
			if(itemstack == null)
			{
				if(output != null)
				{
					itemstack = output;
					output = null;
				}
			}
			if (itemstack != null)
			{
				if ((Type ==0||Type == 2) && itemstack.getItem() == TFCItems.Limewater && liquidLevel < 256)
				{
					liquidLevel = Math.min(liquidLevel + 32, 256);
					Type = 2;
					itemstack.itemID = TFCItems.WoodenBucketEmpty.itemID;
					updateGui();
				}
				else if ((Type == 0||Type == 1) && (itemstack.getItem() == TFCItems.WoodenBucketWater) && liquidLevel < 256)
				{
					liquidLevel = Math.min(liquidLevel + 32, 256);
					Type = 1;
					itemstack.itemID = TFCItems.WoodenBucketEmpty.itemID;
					updateGui();
				}
				else if ((Type == 0||Type == 1) && (itemstack.getItem() == TFCItems.RedSteelBucketWater) && liquidLevel < 256)
				{
					liquidLevel = Math.min(liquidLevel + 32, 256);
					Type = 1;
					itemstack.itemID = TFCItems.RedSteelBucketEmpty.itemID;
					updateGui();
				}
				else if ((Type == 0||Type == 4) && itemstack.getItem() == Item.gunpowder && liquidLevel < 256)
				{
					liquidLevel = Math.min(liquidLevel + 1, 256);
					Type = 4;
					itemstack.stackSize-=1;
					if(itemstack.stackSize==0) {
						itemstack=null;
					}
					updateGui();
				}
				else if((Type>=5&&Type<=11 )&& itemstack.getItem() == Item.glassBottle && liquidLevel >9*itemstack.stackSize)
				{
					liquidLevel = Math.max(0, liquidLevel-9*itemstack.stackSize);
					itemstack.itemID = alcohols[Type-5];
					updateGui();
				}
				/*Fill pottery jug*/
				else if (Type == 1 && (itemstack.getItem() == TFCItems.PotteryJug && itemstack.getItemDamage() == 1) && liquidLevel >= 16)
				{
					liquidLevel = Math.max(liquidLevel - 16, 0);
					Type = 1;
					itemstack.setItemDamage(2);
					updateGui();
				}
				/*Fill water bottle*/
				else if(Type == 1 && itemstack.getItem() == Item.glassBottle && liquidLevel >9*itemstack.stackSize)
				{
					liquidLevel = Math.max(0, liquidLevel-9*itemstack.stackSize);
					itemstack.itemID = Item.potion.itemID;
					updateGui();
				}
				else if((Type == 0||Type == 13 || Type == 14) && itemstack.getItem() == TFCItems.WoodenBucketMilk && liquidLevel < 256){
					liquidLevel = Math.min(liquidLevel + 32, 256);
					Type = Math.max(13, Type);
					itemstack.itemID = TFCItems.WoodenBucketEmpty.itemID;
					updateGui();
				}
				else if(Type == 12 && itemstack.getItem() == TFCItems.WoodenBucketEmpty && liquidLevel >=32){
					liquidLevel = Math.max(liquidLevel - 32, 0);
					Type = liquidLevel>0?Type:0;
					itemstack.itemID = TFCItems.Vinegar.itemID;
					updateGui();
				}
				else if(Type == 13 && itemstack.getItem() == TFCItems.Vinegar && liquidLevel < 256){
					liquidLevel = Math.min(liquidLevel + 32, 256);
					Type = 14;
					itemstack.itemID = TFCItems.WoodenBucketEmpty.itemID;
					updateGui();
				}
			}
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setInteger("liqLev", liquidLevel);
		nbttagcompound.setInteger("Type", Type);
		nbttagcompound.setBoolean("Sealed", sealed);
		nbttagcompound.setInteger("SealTime", sealtimecounter);
		NBTTagList nbttaglist = new NBTTagList();

		NBTTagCompound nbttagcompound1 = new NBTTagCompound();
		nbttagcompound1.setByte("Slot", (byte)1);
		if(itemstack != null){
			itemstack.writeToNBT(nbttagcompound1);
		}
		nbttaglist.appendTag(nbttagcompound1);

		nbttagcompound.setTag("Items", nbttaglist);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		liquidLevel = nbttagcompound.getInteger("liqLev");
		Type = nbttagcompound.getInteger("Type");
		sealed = nbttagcompound.getBoolean("Sealed");
		sealtimecounter = nbttagcompound.getInteger("SealTime");
		NBTTagList nbttaglist = nbttagcompound.getTagList("Items");

		NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.tagAt(0);
		byte byte0 = nbttagcompound1.getByte("Slot");
		itemstack = ItemStack.loadItemStackFromNBT(nbttagcompound1);
	}

	public void updateGui()
	{
		if(!worldObj.isRemote) {
			TerraFirmaCraft.proxy.sendCustomPacketToPlayersInRange(xCoord, yCoord, zCoord, createUpdatePacket(), 5);
		}
	}

	@Override
	public void handleDataPacket(DataInputStream inStream) throws IOException {
		Type = inStream.readInt();
		liquidLevel = inStream.readInt();
		sealed = inStream.readBoolean();
		sealtimecounter = inStream.readInt();
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
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
			dos.writeInt(Type);
			dos.writeInt(liquidLevel);
			dos.writeBoolean(sealed);
			dos.writeInt(sealtimecounter);
		} catch (IOException e) {
		}
		return this.setupCustomPacketData(bos.toByteArray(), bos.size());
	}

	@Override
	public void createInitPacket(DataOutputStream outStream) throws IOException {
		outStream.writeInt(Type);
		outStream.writeInt(liquidLevel);
		outStream.writeBoolean(sealed);
		outStream.writeInt(sealtimecounter);
	}

	public Packet createSealPacket()
	{
		ByteArrayOutputStream bos=new ByteArrayOutputStream(140);
		DataOutputStream dos=new DataOutputStream(bos);

		try {
			dos.writeByte(PacketHandler.Packet_Data_Block_Server);
			dos.writeInt(xCoord);
			dos.writeInt(yCoord);
			dos.writeInt(zCoord);
			dos.writeBoolean(sealed);
			dos.writeInt(liquidLevel);
		} catch (IOException e) {
		}

		return this.setupCustomPacketData(bos.toByteArray(), bos.size());
	}

	@Override
	public void handleInitPacket(DataInputStream inStream) throws IOException {
		Type = inStream.readInt();
		liquidLevel = inStream.readInt();
		sealed = inStream.readBoolean();
		sealtimecounter = inStream.readInt();
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	@Override
	public void handleDataPacketServer(DataInputStream inStream)
			throws IOException {
		sealed = inStream.readBoolean();
		liquidLevel = inStream.readInt();
	}

	public int getLiquidScaled(int i) {
		return (int)((liquidLevel/256f)*i);
	}

	public boolean actionSeal() {
		if(output==null && liquidLevel > 0 && isItemValid()){
			sealed = true;
			TerraFirmaCraft.proxy.sendCustomPacket(createSealPacket());
			return true;
		}
		return false;
	}

	private boolean isItemValid() {
		if(itemstack == null && ((Type >=5&& Type <=11)||Type == 14)){
			return true;
		}
		else if(itemstack == null){
			return false;
		}
		else{
			int id = itemstack.getItem().itemID;
			if(Type == 1){
				if(id == TFCItems.ScrapedHide.itemID){
					return true;
				}
				if(id == TFCItems.Logs.itemID){
					return true;
				}
				if(id == TFCItems.WheatGrain.itemID||id == TFCItems.BarleyGrain.itemID||id == TFCItems.RyeGrain.itemID||id == TFCItems.RiceGrain.itemID||
						id == TFCItems.Potato.itemID||id == TFCItems.RedApple.itemID||id == TFCItems.GreenApple.itemID||id == Item.sugar.itemID){
					return true;
				}
			}
			if(id == TFCItems.Hide.itemID && Type == 2){
				return true;
			}
			if(id == TFCItems.PrepHide.itemID && Type == 3){
				return true;
			}

		}
		return false;
	}

	public void actionEmpty() {
		if(itemstack!=null && itemstack.getItem()==TFCItems.WoodenBucketEmpty && liquidLevel >= 32){
			if(Type == 1){
				itemstack.itemID = TFCItems.WoodenBucketWater.itemID;
			}
			else if(Type == 2){
				itemstack.itemID = TFCItems.Limewater.itemID;
			}
			else if(Type == 12){
				itemstack.itemID = TFCItems.Vinegar.itemID;
			}
			else if(Type == 13){
				itemstack.itemID = TFCItems.WoodenBucketMilk.itemID;
			}
		}
		else if(liquidLevel >0){
			liquidLevel =0;
			Type = 0;
			updateGui();
			TerraFirmaCraft.proxy.sendCustomPacket(createSealPacket());
		}

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
