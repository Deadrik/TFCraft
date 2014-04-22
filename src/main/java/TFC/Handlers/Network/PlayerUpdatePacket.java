package TFC.Handlers.Network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.io.DataInputStream;

import net.minecraft.entity.player.EntityPlayer;
import TFC.Core.TFC_Core;
import TFC.Core.Player.FoodStatsTFC;
import TFC.Core.Player.PlayerInventory;
import TFC.Core.Player.SkillStats;
import cpw.mods.fml.common.network.ByteBufUtils;

public class PlayerUpdatePacket extends AbstractPacket
{
	private byte flag;
	private float stomachLevel;
	private float waterLevel;
	private float nutrFruit;
	private float nutrVeg;
	private float nutrGrain;
	private float nutrProtein;
	private float nutrDairy;
	private SkillStats playerSkills;
	private String skillName;
	private int skillLevel;
	private boolean craftingTable = false;
	private DataInputStream dis;

	public PlayerUpdatePacket() {}

	public PlayerUpdatePacket(EntityPlayer P, int f)
	{
		this.flag = (byte)f;
		if(this.flag == 0)
		{
			FoodStatsTFC fs = TFC_Core.getPlayerFoodStats(P);
			this.stomachLevel = fs.stomachLevel;
			this.waterLevel = fs.waterLevel;
			this.nutrFruit = fs.nutrFruit;
			this.nutrVeg = fs.nutrVeg;
			this.nutrGrain = fs.nutrGrain;
			this.nutrProtein = fs.nutrProtein;
			this.nutrDairy = fs.nutrDairy;
		}
		else if(this.flag == 2)
		{
			this.craftingTable = P.getEntityData().getBoolean("craftingTable");
		}
		else if(this.flag == 3)
		{
//			this.playerSkills = TFC_Core.getSkillStats(P);
		}
	}
	
	public PlayerUpdatePacket(EntityPlayer P, byte f, String name, int lvl)
	{
		this.flag = f;
		if(this.flag == 1)
		{
			this.skillName = name;
			this.skillLevel = lvl;
		}
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
	{
		buffer.writeByte(this.flag);
		if(this.flag == 0)
		{
			buffer.writeFloat(this.stomachLevel);
			buffer.writeFloat(this.waterLevel);
			buffer.writeFloat(this.nutrFruit);
			buffer.writeFloat(this.nutrVeg);
			buffer.writeFloat(this.nutrGrain);
			buffer.writeFloat(this.nutrProtein);
			buffer.writeFloat(this.nutrDairy);
		}
		else if(this.flag == 1)
		{
			ByteBufUtils.writeUTF8String(buffer, this.skillName);
			buffer.writeInt(this.skillLevel);
		}
		else if(this.flag == 2)
		{
			buffer.writeBoolean(this.craftingTable);
		}
		else if(this.flag == 3)
		{
//			this.playerSkills.toOutBuffer(buffer);
		}
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
	{
		this.flag = buffer.readByte();
		if(this.flag == 0)
		{
			this.stomachLevel = buffer.readFloat();
			this.waterLevel = buffer.readFloat();
			this.nutrFruit = buffer.readFloat();
			this.nutrVeg = buffer.readFloat();
			this.nutrGrain = buffer.readFloat();
			this.nutrProtein = buffer.readFloat();
			this.nutrDairy = buffer.readFloat();
		}
		else if(this.flag == 1)
		{
			this.skillName = ByteBufUtils.readUTF8String(buffer);
			this.skillLevel = buffer.readInt();
		}
		else if(this.flag == 2)
		{
			this.craftingTable = buffer.readBoolean();
		}
		else if(this.flag == 3)
		{
//			this.dis = new DataInputStream(new ByteArrayInputStream(buffer.array()));
		}
	}

	@Override
	public void handleClientSide(EntityPlayer player)
	{
		if(this.flag == 0)
		{
			/*
			System.out.println("-----------------------------Handle PlayerUpdatePacket flag:0");
			System.out.println("stomachLevel :"+ this.stomachLevel);
			System.out.println("waterLevel :"+ this.waterLevel);
			System.out.println("nutrFruit :"+ this.nutrFruit);
			System.out.println("nutrVeg :"+ this.nutrVeg);
			System.out.println("nutrProtein :"+ this.nutrProtein);
			System.out.println("nutrDairy :"+ this.nutrDairy);
			*/

			FoodStatsTFC fs = TFC_Core.getPlayerFoodStats(player);
			fs.stomachLevel = this.stomachLevel;
			fs.waterLevel = this.waterLevel;
			fs.nutrFruit = this.nutrFruit;
			fs.nutrVeg = this.nutrVeg;
			fs.nutrProtein = this.nutrProtein;
			fs.nutrDairy = this.nutrDairy;
			TFC_Core.setPlayerFoodStats(player, fs);
		}
		else if(this.flag == 1)
		{
			System.out.println("-----------------------------Handle PlayerUpdatePacket flag:1");
			System.out.println("Skill : "+this.skillName+" : "+this.skillLevel);

			playerSkills.setSkillSave(skillName, skillLevel);
		}
		else if(this.flag == 2)
		{
			//System.out.println("-----------------------------Handle PlayerUpdatePacket flag:2");
			System.out.println("craftingTable : "+this.craftingTable);
			
			if(this.craftingTable && !player.getEntityData().hasKey("craftingTable"))
			{
				player.getEntityData().setBoolean("craftingTable", this.craftingTable);
				PlayerInventory.upgradePlayerCrafting(player);
			}
		}
		else if(this.flag == 3)
		{
			System.out.println("-----------------------------Handle PlayerUpdatePacket flag:3");
			System.out.println("Skill : ");
			
//			try
//			{
//				while(dis.available() > 0)
//					playerSkills.setSkillSave(dis.readUTF(), dis.readInt());
//			}
//			catch (IOException e)
//			{
//				e.printStackTrace();
//			}
		}
	}

	@Override
	public void handleServerSide(EntityPlayer player)
	{
	}

}
