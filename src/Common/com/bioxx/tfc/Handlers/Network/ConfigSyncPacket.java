package com.bioxx.tfc.Handlers.Network;

import java.util.HashMap;
import java.util.Map;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import net.minecraft.entity.player.EntityPlayer;

import cpw.mods.fml.common.network.ByteBufUtils;

import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Core.Config.SyncingOption;

import static com.bioxx.tfc.Core.Config.TFC_ConfigFiles.SYNCING_OPTION_MAP;

/**
 * @author Dries007
 */
public class ConfigSyncPacket extends AbstractPacket
{
	private Map<String, Boolean> map;

	public ConfigSyncPacket()
	{

	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
	{
		buffer.writeInt(SYNCING_OPTION_MAP.size());
		for (SyncingOption option : SYNCING_OPTION_MAP.values())
		{
			ByteBufUtils.writeUTF8String(buffer, option.name);
			buffer.writeBoolean(option.inConfig());
		}
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
	{
		final int size = buffer.readInt();
		map = new HashMap<String, Boolean>(size);
		for (int i = 0; i < size; i++)
		{
			map.put(ByteBufUtils.readUTF8String(buffer), buffer.readBoolean());
		}
	}

	@Override
	public void handleClientSide(EntityPlayer player)
	{
		if (map == null) throw new IllegalStateException("Packet was not decoded");
		TerraFirmaCraft.LOG.info("Applying server TFCCrafting settings");
		try
		{
			for (Map.Entry<String, Boolean> entry : map.entrySet())
			{
				SYNCING_OPTION_MAP.get(entry.getKey()).apply(entry.getValue());
			}
		}
		catch (IllegalAccessException e)
		{
			TerraFirmaCraft.LOG.fatal("Error loading TFCCrafting settings from server!", e);
		}
	}

	@Override
	public void handleServerSide(EntityPlayer player)
	{
		// This packet could be used to update the config on the client on an op's command.
	}
}
