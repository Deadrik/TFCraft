package com.bioxx.tfc.Handlers.Network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.util.LinkedList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;

import com.bioxx.tfc.Core.TFC_Core;

public class ItemNBTPacket extends AbstractPacket {
	private NBTTagCompound tags;
	private LinkedList<String> tagNames;
	private LinkedList<String> removeNames;

	public ItemNBTPacket() {
		tags = new NBTTagCompound();
		tagNames = new LinkedList<String>();
		removeNames = new LinkedList<String>();
	}

	public ItemNBTPacket(NBTTagCompound _tags) {
		this();
		tags = _tags;
	}

	public ItemNBTPacket(NBTTagCompound _tags, LinkedList<String> acceptedTagNames) {
		this();
		tagNames = acceptedTagNames;
		for (String tagName : tagNames)
			tags.setTag(tagName, _tags.getTag(tagName));
	}

	public ItemNBTPacket(NBTTagCompound _tags, LinkedList<String> acceptedTagNames, LinkedList<String> removeTagNames) {
		this();
		tagNames = acceptedTagNames;
		for (String tagName : tagNames)
			tags.setTag(tagName, _tags.getTag(tagName));
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

	public ItemNBTPacket RemoveRemoveTag(String name) {
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
			e.printStackTrace();
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
			e.printStackTrace();
		}
	}

	@Override
	public void handleClientSide(EntityPlayer player)
	{
		NBTTagCompound stackNBT;
	    ItemStack stack = player.inventory.getCurrentItem();
	    
	    if (stack.hasTagCompound())
	    {
			stackNBT = stack.stackTagCompound;
	    }
	    else
	    {
			System.out.println(TFC_Core.translate("error.error") + " " + stack.getUnlocalizedName() + " " + 
					TFC_Core.translate("error.NBT") + " " + TFC_Core.translate("error.Contact"));
			stackNBT = new NBTTagCompound();
	    }

		for (String tagName : tagNames)
			stackNBT.setTag(tagName, tags.getTag(tagName));
		for (String tagName : removeNames)
			stackNBT.removeTag(tagName);
		player.inventory.getCurrentItem().setTagCompound(stackNBT);
	}

	@Override
	public void handleServerSide(EntityPlayer player)
	{
		NBTTagCompound stackNBT;
		ItemStack stack = player.inventory.getCurrentItem();

		if (stack.hasTagCompound())
		{
			stackNBT = stack.stackTagCompound;
		}
		else
		{
			System.out.println(TFC_Core.translate("error.error") + " " + stack.getUnlocalizedName() + " " +
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
