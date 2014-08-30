package com.bioxx.tfc.Items.Tools;

import java.util.HashMap;
import java.util.Random;
import java.util.regex.Pattern;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.TFCBlocks;
import com.bioxx.tfc.TFCItems;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Core.TFC_Textures;
import com.bioxx.tfc.Items.ItemTerra;
import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.Enums.EnumItemReach;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Enums.EnumWeight;

public class ItemProPick extends ItemTerra
{
	HashMap<String, ProspectResult> results = new HashMap<String, ProspectResult>();
	Random random = null;

	public ItemProPick()
	{
		super();
		maxStackSize = 1;
		setCreativeTab(TFCTabs.TFCTools);
		this.setWeight(EnumWeight.LIGHT);
		this.setSize(EnumSize.SMALL);
	}

	@Override
	public void registerIcons(IIconRegister registerer)
	{
		this.itemIcon = registerer.registerIcon(Reference.ModID + ":" + "tools/" + this.getUnlocalizedName().replace("item.", ""));
	}

	@Override
	public IIcon getIcon(ItemStack stack, int pass)
	{
		NBTTagCompound nbt = stack.getTagCompound();
		if(pass == 1 && nbt != null && nbt.hasKey("broken"))
			return TFC_Textures.BrokenItem;
		else
			return getIconFromDamageForRenderPass(stack.getItemDamage(), pass);
	}

	@Override
	public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		Block block = world.getBlock(x, y, z);

		// Negated the old condition and exiting the method here instead.
		if (block == TFCBlocks.ToolRack || block == TFCBlocks.ToolRack2)
			return true;

		// Getting the meta data only when we actually need it.
		int meta = world.getBlockMetadata(x, y, z);

		// Damage the item on prospecting use.
		if (!world.isRemote)
		{
			itemStack.damageItem(1, player);
			if (itemStack.getItemDamage() >= itemStack.getMaxDamage())
				player.destroyCurrentEquippedItem();
			return true;
		}

		// If an ore block is targeted directly, it'll tell you what it is.
		if (block == TFCBlocks.Ore ||
			block == TFCBlocks.Ore2 ||
			block == TFCBlocks.Ore3)
		{
			if (block == TFCBlocks.Ore2) meta = meta + Global.ORE_METAL.length;
			if (block == TFCBlocks.Ore3) meta = meta + Global.ORE_METAL.length + Global.ORE_MINERAL.length;
			TellResult(player, new ItemStack(TFCItems.OreChunk, 1, meta));
			return true;
		}

		random = new Random(x * z + y);

		// If random(100) is less than 60, it used to succeed. we don't need to
		// gather the blocks in a 25x25 area if it doesn't.
		if (random.nextInt(100) >= 60)
		{
			player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("gui.ProPick.FoundNothing")));
			return true;
		}

		// Check all blocks in the 25x25 area, centered on the targeted block.
		for (int i = -12; i < 12; i++)
		{
			for (int j = -12; j < 12; j++)
			{
				for(int k = -12; k < 12; k++)
				{
					int blockX = x + i;
					int blockY = y + j;
					int blockZ = z + k;

					block = world.getBlock(blockX, blockY, blockZ);
					meta = world.getBlockMetadata(blockX, blockY, blockZ);
					ItemStack ore;
					if (block == TFCBlocks.Ore)
						ore = new ItemStack(TFCItems.OreChunk, 1, meta);
					else if (block == TFCBlocks.Ore2)
						ore = new ItemStack(TFCItems.OreChunk, 1, meta + Global.ORE_METAL.length);
					else if (block == TFCBlocks.Ore3)
						ore = new ItemStack(TFCItems.OreChunk, 1, meta + Global.ORE_METAL.length + Global.ORE_MINERAL.length);
					else
						continue;

					String oreName = ore.getDisplayName();

					if (results.containsKey(oreName))
						results.get(oreName).Count++;
					else
						results.put(oreName, new ProspectResult(ore, 1));

					ore = null;
					oreName = null;
				}
			}
		}

		// Tell the player what was found.
		TellResult(player);

		results.clear();
		random = null;

		return true;
	}

	/*
	 * Tells the player what block of ore he found, when directly targeting an ore block.
	 */
	private void TellResult(EntityPlayer player, ItemStack ore)
	{
		String message = StatCollector.translateToLocal("gui.ProPick.Found").replaceAll(Pattern.quote("$ORE"), ore.getItem().getItemStackDisplayName(ore));
		player.addChatMessage(new ChatComponentText(message));
	}

	/*
	 * Tells the player what ore has been found, randomly picked off the HashMap.
	 */
	private void TellResult(EntityPlayer player)
	{
		if (results == null || results.size() == 0)
		{
			player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("gui.ProPick.FoundNothing")));
			return;
		}

		int index = random.nextInt(results.size());
		ProspectResult result = results.values().toArray(new ProspectResult[0])[index];
		String oreName = result.ItemStack.getItem().getItemStackDisplayName(result.ItemStack);

		String message = "";
		if (result.Count < 10)
			message = StatCollector.translateToLocal("gui.ProPick.FoundTraces").replaceAll(Pattern.quote("$ORE"), oreName);
		else if(result.Count < 20)
			message = StatCollector.translateToLocal("gui.ProPick.FoundSmall").replaceAll(Pattern.quote("$ORE"), oreName);
		else if (result.Count < 40)
			message = StatCollector.translateToLocal("gui.ProPick.FoundMedium").replaceAll(Pattern.quote("$ORE"), oreName);
		else if (result.Count < 80)
			message = StatCollector.translateToLocal("gui.ProPick.FoundLarge").replaceAll(Pattern.quote("$ORE"), oreName);
		else
			message = StatCollector.translateToLocal("gui.ProPick.FoundVeryLarge").replaceAll(Pattern.quote("$ORE"), oreName);

		player.addChatMessage(new ChatComponentText(message));

		oreName = null;
		result = null;
	}

	@Override
	public boolean canStack()
	{
		return false;
	}

	private class ProspectResult
	{
		public ItemStack ItemStack;
		public int Count;

		public ProspectResult(ItemStack itemStack, int count)
		{
			ItemStack = itemStack;
			Count = count;
		}
	}
	
	@Override
	public EnumItemReach getReach(ItemStack is)
	{
		return EnumItemReach.SHORT;
	}
}
