package TFC.TileEntities;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import TFC.TFCBlocks;
import TFC.TFCItems;
import TFC.TerraFirmaCraft;
import TFC.Blocks.BlockBloomery;
import TFC.Core.HeatIndex;
import TFC.Core.HeatManager;
import TFC.Core.TFC_Climate;
import TFC.Core.TFC_ItemHeat;
import TFC.Core.TFC_Time;
import TFC.Core.Vector3f;
import TFC.Handlers.PacketHandler;
import TFC.Items.ItemOre;
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

public class TileEntityBarrel extends NetworkTileEntity implements IInventory
{

	public int liquidLevel;
	public int Type;
	public int barrelType;
	private ItemStack itemstack;
	private ItemStack output;
	private boolean sealed;
	private int sealtimecounter;
	private static int[] barrels ={(TFCBlocks.BarrelOak.blockID),(TFCBlocks.BarrelAspen.blockID),(TFCBlocks.BarrelBirch.blockID),(TFCBlocks.BarrelChestnut.blockID),
			(TFCBlocks.BarrelDouglasFir.blockID),(TFCBlocks.BarrelHickory.blockID),(TFCBlocks.BarrelMaple.blockID),(TFCBlocks.BarrelAsh.blockID),
			(TFCBlocks.BarrelPine.blockID),(TFCBlocks.BarrelSequoia.blockID),(TFCBlocks.BarrelSpruce.blockID),(TFCBlocks.BarrelSycamore.blockID),
			(TFCBlocks.BarrelWhiteCedar.blockID),(TFCBlocks.BarrelWhiteElm.blockID),(TFCBlocks.BarrelWillow.blockID),(TFCBlocks.BarrelKapok.blockID)};
	public final int SEALTIME = (int) ((TFC_Time.hourLength*6)/100);//default 80

	public TileEntityBarrel()
	{
		liquidLevel = 0;
		shouldSendInitData = true;
		sealed = false;
		//itemstack = new ItemStack(1,0,0);
		sealtimecounter = 0;
	}

	public void careForInventorySlot(int i, float startTemp)
	{

	}
	public static int[] getBarrels(){
		return barrels;
	}

	public boolean getSealed(){
		return sealed;
	}
	
	public int getBarrelType(){
		for(int i = 0; i < barrels.length;i++){
			if(worldObj.getBlockId(xCoord,yCoord,zCoord)==barrels[i]){
				return i;
			}
		}
		return 0;
	}

	public void externalFireCheck()
	{
		Random R = new Random();
		if(sealed)
		{

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
	}

	private void ProcessItems(){
		ItemStack itemstack2;
		System.out.println(liquidLevel +", "+ Type);
		if (Type== 1&&itemstack.getItem() == TFCItems.ScrapedHide){
			itemstack2 = new ItemStack(TFCItems.PrepHide,0,0);
			while(liquidLevel >= 5 && itemstack.stackSize >0){
				liquidLevel-=5;
				itemstack2.stackSize++;
				itemstack.stackSize--;
			}
			if(itemstack2.stackSize > 0){
				output = itemstack2;
			}
		}
		if(Type== 1&&itemstack.getItem() == TFCItems.Logs){
			itemstack.stackSize--;
			if(itemstack.stackSize ==0){
				itemstack=null;
			}
			Type = 3;
		}
		if(Type==2&&itemstack.getItem() == TFCItems.Hide){
			itemstack2 = new ItemStack(TFCItems.SoakedHide,0,0);
			while(liquidLevel >= 5 && itemstack.stackSize >0){
				System.out.println(liquidLevel);
				liquidLevel-=5;
				itemstack2.stackSize++;
				itemstack.stackSize--;
			}
			if(itemstack2.stackSize > 0){
				output = itemstack2;
			}
		}
		if(itemstack!=null &&Type ==3&&itemstack.getItem()==TFCItems.PrepHide){
			itemstack2 = new ItemStack(TFCItems.TerraLeather,0,0);
			while(liquidLevel >= 5 && itemstack.stackSize >0){
				liquidLevel-=5;
				itemstack2.stackSize++;
				itemstack.stackSize--;
			}
			if(itemstack2.stackSize > 0){
				output = itemstack2;
			}
		}
		if (liquidLevel == 0){
			Type = 0;
		}
		if (itemstack!=null&&itemstack.stackSize==0){
			itemstack = null;
		}
	}

	public String getType(){
		switch (Type){
		case 0:
			return "";
		case 1:
			return "Water";
		case 2:
			return "Limewater";
		case 3:
			return "Tannin";
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
				entityitem = new EntityItem(worldObj, (float)xCoord + f, (float)yCoord + f1, (float)zCoord + f2, 
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


	public void updateEntity()
	{
		if(!worldObj.isRemote)
		{
			if(sealed){
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
			if(itemstack==null){
				if(output != null){
					itemstack = output;
					output = null;
				}
			}
			if (itemstack != null){
				if ((Type ==0||Type == 2) && itemstack.getItem() == TFCItems.Limewater && liquidLevel < 64){
					liquidLevel = (int)Math.min(liquidLevel + 8, 64);
					Type = 2;
					itemstack.itemID = TFCItems.WoodenBucketEmpty.shiftedIndex;
				}
				if ((Type == 0||Type == 1) && itemstack.getItem() == TFCItems.WoodenBucketWater && liquidLevel < 64){
					liquidLevel = (int)Math.min(liquidLevel + 8, 64);
					Type = 1;
					itemstack.itemID = TFCItems.WoodenBucketEmpty.shiftedIndex;
				}
			}
			//Do the funky math to find how many molten blocks should be placed


			//get the direction that the bloomery is facing so that we know where the stack should be
			int meta = worldObj.getBlockMetadata(xCoord, yCoord, zCoord) & 3;


		}
		validate();
	}

	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setInteger("liqLev", liquidLevel);
		nbttagcompound.setInteger("Type", Type);
		NBTTagList nbttaglist = new NBTTagList();

		NBTTagCompound nbttagcompound1 = new NBTTagCompound();
		nbttagcompound1.setByte("Slot", (byte)1);
		if(itemstack != null){
			itemstack.writeToNBT(nbttagcompound1);
		}
		nbttaglist.appendTag(nbttagcompound1);

		nbttagcompound.setTag("Items", nbttaglist);
	}

	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		liquidLevel = nbttagcompound.getInteger("liqLev");
		Type = nbttagcompound.getInteger("Type");
		NBTTagList nbttaglist = nbttagcompound.getTagList("Items");

		NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.tagAt(0);
		byte byte0 = nbttagcompound1.getByte("Slot");
		itemstack = ItemStack.loadItemStackFromNBT(nbttagcompound1);
	}

	public void updateGui()
	{
		if(!worldObj.isRemote)
			TerraFirmaCraft.proxy.sendCustomPacketToPlayersInRange(xCoord, yCoord, zCoord, createUpdatePacket(), 5);
	}

	@Override
	public void handleDataPacket(DataInputStream inStream) throws IOException {
		/*
		oreCount = inStream.readInt();
		charcoalCount = inStream.readInt();
		oreDamage = inStream.readInt();
		outCount = inStream.readInt();

		if(oreDamage == -1)
			this.OreType = "";
		else
			this.OreType = ItemOre.getItemNameDamage(oreDamage);
		 */
		Type = inStream.readInt();
		liquidLevel = inStream.readInt();
		sealed = inStream.readBoolean();
		sealtimecounter = inStream.readInt();
		//handleInitPacket(inStream);
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
		//outStream.writeInt(itemstack != null ? itemstack.itemID : -1);
		//outStream.writeInt(itemstack != null ? itemstack.stackSize : 0);
		//outStream.writeInt(output != null ? output.itemID : -1);
		//outStream.writeInt(output != null ? output.stackSize : 0);
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
		//itemstack.itemID = inStream.readInt();
		//itemstack.stackSize = inStream.readInt();
		//output.itemID = inStream.readInt();
		//output.stackSize = inStream.readInt();
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
		return (int)(((float)liquidLevel/64f)*i);
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
		if(itemstack == null){
			return false;
		}
		else{
			int id = itemstack.getItem().shiftedIndex;
			if(Type == 1){
				if(id == TFCItems.ScrapedHide.shiftedIndex){
					return true;
				}
				if(id == TFCItems.Logs.shiftedIndex){
					return true;
				}
			}
			if(id == TFCItems.Hide.shiftedIndex && Type == 2){
				return true;
			}
			if(id == TFCItems.PrepHide.shiftedIndex && Type == 3){
				return true;
			}
		}
		return false;
	}

	public void actionEmpty() {
		if(liquidLevel >0){
			liquidLevel =0;
			TerraFirmaCraft.proxy.sendCustomPacket(createSealPacket());
		}
		
	}
}
