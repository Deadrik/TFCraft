package com.bioxx.tfc.Commands;
import java.util.Arrays;
import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;

public class CommandTransferTamed extends CommandBase
{
	@Override
	public List getCommandAliases()
	{
		return Arrays.asList(new String[] {"transfer"});
	}

	@Override
	public String getCommandName()
	{
		return "transferTamed";
	}

	/**
	 * Return the required permission level for this command.
	 */
	@Override
	public int getRequiredPermissionLevel()
	{
		return 0;
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender p_71519_1_)
	{
		return true;
	}

	@Override
	public String getCommandUsage(ICommandSender p_71518_1_)
	{
		return "commands.transferTamed.usage";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] chars)
	{
		if(sender instanceof EntityPlayer){
			EntityPlayerMP entityplayermp = null;
			if(chars.length > 0){
				entityplayermp = getPlayer(sender, chars[0]);
			}

			EntityTameable theEntity = null;
			List<EntityTameable> entitiesInRange =	((EntityPlayer)sender).worldObj.getEntitiesWithinAABB(EntityTameable.class, ((EntityPlayer)sender).boundingBox.expand(3, 1, 3));

			if(entitiesInRange.size() == 0){
				throw new WrongUsageException("commands.transferTamed.noTamed");
			}
			else if(entitiesInRange.size() > 1){
				throw new WrongUsageException("commands.transferTamed.tooMany");
			}
			else if(entitiesInRange.size() == 1){
				theEntity = entitiesInRange.get(0);
				if(theEntity.getOwner() == null || !theEntity.getOwner().equals(sender)){
					throw new WrongUsageException("commands.transferTamed.wrongOwner");
				}
			}

			if (entityplayermp == null)
			{
				if(theEntity != null && chars.length == 0){
					theEntity.setTamed(false);
					theEntity.func_152115_b("");
				}
				else{
					throw new PlayerNotFoundException();
				}
			}
			else if (entityplayermp == sender)
			{
				throw new PlayerNotFoundException("commands.transferTamed.sameTarget", new Object[0]);
			}
			else
			{
				theEntity.func_152115_b(entityplayermp.getUniqueID().toString());

				ChatComponentTranslation chatcomponenttranslation = new ChatComponentTranslation("commands.transferTamed.display.incoming", new Object[] {sender.func_145748_c_()});
				ChatComponentTranslation chatcomponenttranslation1 = new ChatComponentTranslation("commands.transferTamed.display.outgoing", new Object[] {entityplayermp.func_145748_c_()});
				chatcomponenttranslation.getChatStyle().setColor(EnumChatFormatting.GRAY).setItalic(Boolean.valueOf(true));
				chatcomponenttranslation1.getChatStyle().setColor(EnumChatFormatting.GRAY).setItalic(Boolean.valueOf(true));
				entityplayermp.addChatMessage(chatcomponenttranslation);
				sender.addChatMessage(chatcomponenttranslation1);
			}
		}
		else{
			throw new WrongUsageException("commands.transferTamed.wrongSender");
		}
	}

	/**
	 * Adds the strings available in this command to the given list of tab completion options.
	 */
	@Override
	public List addTabCompletionOptions(ICommandSender p_71516_1_, String[] p_71516_2_)
	{
		/**
		 * Returns a List of strings (chosen from the given strings) which the last word in the given string array is a
		 * beginning-match for. (Tab completion).
		 */
		return getListOfStringsMatchingLastWord(p_71516_2_, MinecraftServer.getServer().getAllUsernames());
	}

	/**
	 * Return whether the specified command parameter index is a username parameter.
	 */
	@Override
	public boolean isUsernameIndex(String[] p_82358_1_, int p_82358_2_)
	{
		return p_82358_2_ == 0;
	}

}
