package com.bioxx.tfc.Handlers.Network;

import java.util.LinkedList;
import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;

import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Core.TFC_Core;

public class ItemNBTPacket extends AbstractPacket {
	private NBTTagCompound tags;
	private List<String> tagNames;
	private List<String> removeNames;

	public ItemNBTPacket() {
		tags = new NBTTagCompound();
		tagNames = new LinkedList<String>();
		removeNames = new LinkedList<String>();
	}

	public ItemNBTPacket(NBTTagCompound nbt) {
		this();
		tags = nbt;
	}

	public ItemNBTPacket(NBTTagCompound nbt, List<String> acceptedTagNames)
	{
		this();
		tagNames = acceptedTagNames;
		for (String tagName : tagNames)
			tags.setTag(tagName, nbt.getTag(tagName));
	}

	public ItemNBTPacket(NBTTagCompound nbt, List<String> acceptedTagNames, List<String> removeTagNames)
	{
		this();
		tagNames = acceptedTagNames;
		for (String tagName : tagNames)
			tags.setTag(tagName, nbt.getTag(tagName));
		removeNames = removeTagNames;
	}

	public ItemNBTPacket addAcceptedTag(String name) {
		if (!removeNames.contains(name) && !tagNames.contains(name))
			tagNames.add(name);
		return this;
	}

	public ItemNBTPacket removeAcceptedTag(String name) {
		tagNames.remove(name);
		return this;
	}

	public ItemNBTPacket addRemoveTag(String name) {
		if (!removeNames.contains(name))
			removeNames.add(name);
		return this;
	}

	public ItemNBTPacket removeRemoveTag(String name) {
		removeNames.remove(name);
		return this;
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		PacketBuffer pb = new PacketBuffer(buffer);
		NBTTagCompound tags = this.tags;

		for (String tagName : removeNames)
			tags.removeTag(tagName);

		try {
			pb.writeNBTTagCompoundToBuffer(tags);

			pb.writeInt(tagNames.size());
			for (String tagName : tagNames)
				pb.writeStringToBuffer(tagName);

			pb.writeInt(removeNames.size());
			for (String tagName : removeNames)
				pb.writeStringToBuffer(tagName);
		} catch (Exception e) {
			TerraFirmaCraft.LOG.catching(e);
		}
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		PacketBuffer pb = new PacketBuffer(buffer);

		try {
			tags = pb.readNBTTagCompoundFromBuffer();

			int size = pb.readInt();
			for (int i = 0; i < size; ++i)
				tagNames.add(pb.readStringFromBuffer(256));

			size = pb.readInt();
			for (int i = 0; i < size; ++i)
				removeNames.add(pb.readStringFromBuffer(256));
		} catch (Exception e) {
			TerraFirmaCraft.LOG.catching(e);
		}
	}

	@Override
	public void handleClientSide(EntityPlayer player)
	{
		NBTTagCompound stackNBT;
	    ItemStack stack = player.inventory.getCurrentItem();
	    
		if (stack != null)
	    {
			if (stack.hasTagCompound())
			{
				stackNBT = stack.stackTagCompound;
			}
			else
			{
				TerraFirmaCraft.LOG.error(TFC_Core.translate("error.error") +" " + stack.getUnlocalizedName() + " " +
											TFC_Core.translate("error.NBT") + " " + TFC_Core.translate("error.Contact"));
				stackNBT = new NBTTagCompound();
			}

			for (String tagName : tagNames)
				stackNBT.setTag(tagName, tags.getTag(tagName));
			for (String tagName : removeNames)
				stackNBT.removeTag(tagName);
			player.inventory.getCurrentItem().setTagCompound(stackNBT);
	    }
	}

	@Override
	public void handleServerSide(EntityPlayer player)
	{
		NBTTagCompound stackNBT;
		ItemStack stack = player.inventory.getCurrentItem();

		if (stack != null)
		{
			if (stack.hasTagCompound())
			{
				stackNBT = stack.stackTagCompound;
			}
			else
			{
				TerraFirmaCraft.LOG.error(TFC_Core.translate("error.error") +" " + stack.getUnlocalizedName() + " " +
											TFC_Core.translate("error.NBT") + " " + TFC_Core.translate("error.Contact"));
				stackNBT = new NBTTagCompound();
			}
			for (String tagName : tagNames)
				stackNBT.setTag(tagName, tags.getTag(tagName));
			for (String tagName : removeNames)
				stackNBT.removeTag(tagName);
			player.inventory.getCurrentItem().setTagCompound(stackNBT);
		}
	}
}
