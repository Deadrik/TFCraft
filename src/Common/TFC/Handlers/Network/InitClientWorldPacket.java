package TFC.Handlers.Network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.io.DataInputStream;

import net.minecraft.entity.player.EntityPlayer;
import TFC.API.TFCOptions;
import TFC.Core.TFC_Core;
import TFC.Core.TFC_Time;
import TFC.Core.Player.FoodStatsTFC;
import TFC.Core.Player.PlayerInventory;
import TFC.Core.Player.SkillStats;

public class InitClientWorldPacket extends AbstractPacket
{
	private long seed;
	private float stomachLevel;
	private float waterLevel;
	private float nutrFruit;
	private float nutrVeg;
	private float nutrGrain;
	private float nutrProtein;
	private float nutrDairy;
	private boolean craftingTable = false;
	private DataInputStream dis;
	private SkillStats playerSkills;
	private int daysInYear, HGRate, HGCap;

	public InitClientWorldPacket() {}

	public InitClientWorldPacket(EntityPlayer P)
	{
		this.seed = P.worldObj.getSeed();
		FoodStatsTFC fs = TFC_Core.getPlayerFoodStats(P);
		fs.resetTimers();
		fs.writeNBT(P.getEntityData());
		this.stomachLevel = fs.stomachLevel;
		this.waterLevel = fs.waterLevel;
		this.nutrFruit = fs.nutrFruit;
		this.nutrVeg = fs.nutrVeg;
		this.nutrGrain = fs.nutrGrain;
		this.nutrProtein = fs.nutrProtein;
		this.nutrDairy = fs.nutrDairy;
		this.daysInYear = TFC_Time.daysInYear;
		this.HGRate = TFCOptions.HealthGainRate;
		this.HGCap = TFCOptions.HealthGainCap;
		if(P.getEntityData().hasKey("craftingTable"))
			this.craftingTable = true;
	}
	
	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
	{
		buffer.writeLong(this.seed);
		buffer.writeInt(this.daysInYear);
		buffer.writeFloat(this.stomachLevel);
		buffer.writeFloat(this.waterLevel);
		buffer.writeFloat(this.nutrFruit);
		buffer.writeFloat(this.nutrVeg);
		buffer.writeFloat(this.nutrGrain);
		buffer.writeFloat(this.nutrProtein);
		buffer.writeFloat(this.nutrDairy);
		buffer.writeInt(this.HGRate);
		buffer.writeInt(this.HGCap);
		buffer.writeBoolean(this.craftingTable);
//		TFC_Core.getSkillStats(player).toOutBuffer(buffer);
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
	{
		this.seed = buffer.readLong();
		this.daysInYear = buffer.readInt();
		this.stomachLevel = buffer.readFloat();
		this.waterLevel = buffer.readFloat();
		this.nutrFruit = buffer.readFloat();
		this.nutrVeg = buffer.readFloat();
		this.nutrGrain = buffer.readFloat();
		this.nutrProtein = buffer.readFloat();
		this.nutrDairy = buffer.readFloat();
		this.HGRate = buffer.readInt();
		this.HGCap = buffer.readInt();
		this.craftingTable = buffer.readBoolean();
//		dis = new DataInputStream(new ByteArrayInputStream(buffer.array()));
	}

	@Override
	public void handleClientSide(EntityPlayer player)
	{
//		System.out.println("-----------------------------Handle InitClientWorldPacket");
//		System.out.println("stomachLevel :"+ this.stomachLevel);
//		System.out.println("waterLevel :"+ this.waterLevel);
//		System.out.println("nutrFruit :"+ this.nutrFruit);
//		System.out.println("nutrVeg :"+ this.nutrVeg);
//		System.out.println("nutrProtein :"+ this.nutrProtein);
//		System.out.println("nutrDairy :"+ this.nutrDairy);
//		System.out.println("seed :"+ this.seed);
//		System.out.println("daysInYear :"+ this.daysInYear);
//		System.out.println("HealthGainRate :"+ this.HGRate);
//		System.out.println("HealthGainCap :"+ this.HGCap);
//		System.out.println("craftingTable :"+ this.craftingTable);
		
		FoodStatsTFC fs = TFC_Core.getPlayerFoodStats(player);
		fs.stomachLevel = this.stomachLevel;
		fs.waterLevel = this.waterLevel;
		fs.nutrFruit = this.nutrFruit;
		fs.nutrVeg = this.nutrVeg;
		fs.nutrProtein = this.nutrProtein;
		fs.nutrDairy = this.nutrDairy;
		TFC_Core.setPlayerFoodStats(player, fs);

		TFC_Time.daysInYear = this.daysInYear;
		TFCOptions.HealthGainRate = this.HGRate;
		TFCOptions.HealthGainCap = this.HGCap;
		if(this.craftingTable)
		{
			player.getEntityData().setBoolean("craftingTable", this.craftingTable);
			PlayerInventory.upgradePlayerCrafting(player);
		}
		TFC_Core.SetupWorld(player.worldObj, this.seed);
//		try
//		{
//			while(dis.available() > 0)
//				playerSkills.setSkillSave(dis.readUTF(), dis.readInt());
//		}
//		catch (IOException e)
//		{
//			e.printStackTrace();
//		}
	}

	@Override
	public void handleServerSide(EntityPlayer player)
	{
	}

}
