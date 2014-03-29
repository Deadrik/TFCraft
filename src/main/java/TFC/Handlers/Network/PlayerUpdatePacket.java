package TFC.Handlers.Network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import TFC.Core.TFC_Core;
import TFC.Core.Player.FoodStatsTFC;
import TFC.Core.Player.PlayerInventory;
import TFC.Core.Player.SkillStats;
import cpw.mods.fml.common.network.ByteBufUtils;

public class PlayerUpdatePacket extends AbstractPacket
{
	private byte flag;
	private FoodStatsTFC foodstats;
	private SkillStats playerSkills;
	private String skillName;
	private int skillLevel;
	private boolean craftingTable = false;
	private DataInputStream dis;
	
	public PlayerUpdatePacket(EntityPlayer P, int f)
	{
		flag = (byte)f;
		if(flag == 0)
		{
			foodstats = TFC_Core.getPlayerFoodStats(P);
		}
		else if(flag == 2)
		{
			craftingTable = P.getEntityData().getBoolean("craftingTable");
		}
		else if(flag == 3)
		{
			playerSkills = TFC_Core.getSkillStats(P);
		}
	}
	
	public PlayerUpdatePacket(EntityPlayer P, byte f, String s, int i)
	{
		flag = f;
		if(flag == 1)
		{
			skillName = s;
			skillLevel = i;
		}
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
	{
		buffer.writeByte(flag);
		if(flag == 0)
		{
			buffer.writeFloat(foodstats.foodLevel);
			buffer.writeFloat(foodstats.waterLevel);
		}
		else if(flag == 1)
		{
			ByteBufUtils.writeUTF8String(buffer, skillName);
			buffer.writeInt(skillLevel);
		}
		else if(flag == 2)
		{
			buffer.writeBoolean(craftingTable);
		}
		else if(flag == 3)
		{
			playerSkills.toOutBuffer(buffer);
		}
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
	{
		flag = buffer.readByte();
		if(flag == 0)
		{
			foodstats.foodLevel = buffer.readFloat();
			foodstats.waterLevel = buffer.readFloat();
		}
		else if(flag == 1)
		{
			skillName = ByteBufUtils.readUTF8String(buffer);
			skillLevel = buffer.readInt();
		}
		else if(flag == 2)
		{
			craftingTable = buffer.readBoolean();
		}
		else if(flag == 3)
		{
			dis = new DataInputStream(new ByteArrayInputStream(buffer.array()));
		}
	}

	@Override
	public void handleClientSide(EntityPlayer player)
	{
		if(flag == 0)
		{
			FoodStatsTFC fs = TFC_Core.getPlayerFoodStats(player);
			fs.foodLevel = this.foodstats.foodLevel;
			fs.waterLevel = this.foodstats.waterLevel;
			TFC_Core.setPlayerFoodStats(player, foodstats);
		}
		else if(flag == 1)
		{
			playerSkills.setSkillSave(skillName, skillLevel);
		}
		else if(flag == 2)
		{
			if(craftingTable && !player.getEntityData().hasKey("craftingTable"))
			{
				player.getEntityData().setBoolean("craftingTable", craftingTable);
				PlayerInventory.upgradePlayerCrafting(player);
			}
		}
		else if(flag == 3)
		{
			try
			{
				while(dis.available() > 0)
					playerSkills.setSkillSave(dis.readUTF(), dis.readInt());
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	@Override
	public void handleServerSide(EntityPlayer player)
	{
	}

}
