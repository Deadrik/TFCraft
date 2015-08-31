package com.bioxx.tfc.GUI;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.achievement.GuiAchievements;
import net.minecraft.client.gui.achievement.GuiStats;
import net.minecraft.client.gui.inventory.CreativeCrafting;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.bioxx.tfc.Containers.ContainerCreativeTFC;

@SideOnly(Side.CLIENT)
public class GuiContainerCreativeTFC extends InventoryEffectRenderer
{
	private static final ResourceLocation TEXTURE = new ResourceLocation("textures/gui/container/creative_inventory/tabs.png");
	private static InventoryBasic inventory = new InventoryBasic("tmp", true, 51);

	/** Currently selected creative inventory tab index. */
	private static int selectedTabIndex = CreativeTabs.tabBlock.getTabIndex();

	/** Amount scrolled in Creative mode inventory (0 = top, 1 = bottom) */
	private float currentScroll;

	/** True if the scrollbar is being dragged */
	private boolean isScrolling;

	/**
	 * True if the left mouse button was held down last time drawScreen was called.
	 */
	private boolean wasClicking;
	private GuiTextField searchField;

	/**
	 * Used to back up the ContainerCreativeTFC's inventory slots before filling it with the player's inventory slots for
	 * the inventory tab.
	 */
	private List<Slot> backupContainerSlots;
	private Slot zeroSlot;
	private boolean eventTriggered;
	private CreativeCrafting crafting;
	private static int tabPage;
	private int maxPages;

	public GuiContainerCreativeTFC(EntityPlayer par1EntityPlayer)
	{
		super(new ContainerCreativeTFC(par1EntityPlayer));
		//this.inventorySlots = new ContainerCreativeTFC(par1EntityPlayer);
		par1EntityPlayer.openContainer = this.inventorySlots;
		this.allowUserInput = true;
		par1EntityPlayer.addStat(AchievementList.openInventory, 1);
		this.ySize = 136;
		this.xSize = 195;
	}

	@Override
	/**
	 * Called from the main game loop to update the screen.
	 */
	public void updateScreen()
	{
		if (!this.mc.playerController.isInCreativeMode())
			this.mc.displayGuiScreen(new GuiInventoryTFC(this.mc.thePlayer));
	}

	@Override
	protected void handleMouseClick(Slot par1Slot, int par2, int par3, int par4)
	{
		this.eventTriggered = true;
		boolean flag = par4 == 1;
		par4 = par2 == -999 && par4 == 0 ? 4 : par4;
		ItemStack itemstack;
		InventoryPlayer inventoryplayer;

		if (par1Slot == null && selectedTabIndex != CreativeTabs.tabInventory.getTabIndex() && par4 != 5)
		{
			inventoryplayer = this.mc.thePlayer.inventory;

			if (inventoryplayer.getItemStack() != null)
			{
				if (par3 == 0)
				{
					this.mc.thePlayer.entityDropItem(inventoryplayer.getItemStack(), 1.5F);
					this.mc.playerController.sendPacketDropItem/*func_78752_a*/(inventoryplayer.getItemStack());
					inventoryplayer.setItemStack((ItemStack)null);
				}

				if (par3 == 1)
				{
					itemstack = inventoryplayer.getItemStack().splitStack(1);
					this.mc.thePlayer.entityDropItem(itemstack, 1.5F);
					this.mc.playerController.sendPacketDropItem/*func_78752_a*/(itemstack);

					if (inventoryplayer.getItemStack().stackSize == 0)
						inventoryplayer.setItemStack((ItemStack)null);
				}
			}
		}
		else
		{
			int l;

			if (par1Slot == this.zeroSlot && flag)
			{
				for (l = 0; l < this.mc.thePlayer.inventoryContainer.getInventory().size(); ++l)
				{
					this.mc.playerController.sendSlotPacket((ItemStack)null, l);
				}
			}
			else
			{
				ItemStack itemstack1;

				if (selectedTabIndex == CreativeTabs.tabInventory.getTabIndex())
				{
					if (par1Slot == this.zeroSlot)
					{
						this.mc.thePlayer.inventory.setItemStack((ItemStack)null);
					}
					else if (par4 == 4 && par1Slot != null && par1Slot.getHasStack())
					{
						itemstack1 = par1Slot.decrStackSize(par3 == 0 ? 1 : par1Slot.getStack().getMaxStackSize());
						this.mc.thePlayer.entityDropItem(itemstack1, 1.5F);
						this.mc.playerController.sendPacketDropItem/*func_78752_a*/(itemstack1);
					}
					else if (par4 == 4 && this.mc.thePlayer.inventory.getItemStack() != null)
					{
						this.mc.thePlayer.entityDropItem(this.mc.thePlayer.inventory.getItemStack(), 1.5F);
						this.mc.playerController.sendPacketDropItem/*func_78752_a*/(this.mc.thePlayer.inventory.getItemStack());
						this.mc.thePlayer.inventory.setItemStack((ItemStack)null);
					}
					else
					{
						this.mc.thePlayer.inventoryContainer.slotClick(par1Slot == null ? par2 : SlotCreativeInventoryTFC.getSlot((SlotCreativeInventoryTFC)par1Slot).slotNumber, par3, par4, this.mc.thePlayer);
						this.mc.thePlayer.inventoryContainer.detectAndSendChanges();
					}
				}
				else if (par4 != 5 && par1Slot != null && par1Slot.inventory == inventory)
				{
					inventoryplayer = this.mc.thePlayer.inventory;
					itemstack = inventoryplayer.getItemStack();
					ItemStack itemstack2 = par1Slot.getStack();
					ItemStack itemstack3;

					if (par4 == 2)
					{
						if (itemstack2 != null && par3 >= 0 && par3 < 9)
						{
							itemstack3 = itemstack2.copy();
							itemstack3.stackSize = itemstack3.getMaxStackSize();
							this.mc.thePlayer.inventory.setInventorySlotContents(par3, itemstack3);
							this.mc.thePlayer.inventoryContainer.detectAndSendChanges();
						}

						return;
					}

					if (par4 == 3)
					{
						if (inventoryplayer.getItemStack() == null && par1Slot.getHasStack())
						{
							itemstack3 = par1Slot.getStack().copy();
							itemstack3.stackSize = itemstack3.getMaxStackSize();
							inventoryplayer.setItemStack(itemstack3);
						}

						return;
					}

					if (par4 == 4)
					{
						if (itemstack2 != null)
						{
							itemstack3 = itemstack2.copy();
							itemstack3.stackSize = par3 == 0 ? 1 : itemstack3.getMaxStackSize();
							this.mc.thePlayer.entityDropItem(itemstack3, 1.5F);
							this.mc.playerController.sendPacketDropItem/*func_78752_a*/(itemstack3);
						}

						return;
					}

					if (itemstack != null && itemstack2 != null && itemstack.isItemEqual(itemstack2) && ItemStack.areItemStackTagsEqual(itemstack, itemstack2)) //Forge: Bugfix, Compare NBT data, allow for deletion of enchanted books, MC-12770
					{
						if (par3 == 0)
						{
							if (flag)
								itemstack.stackSize = itemstack.getMaxStackSize();
							else if (itemstack.stackSize < itemstack.getMaxStackSize())
								++itemstack.stackSize;
						}
						else if (itemstack.stackSize <= 1)
						{
							inventoryplayer.setItemStack((ItemStack)null);
						}
						else
						{
							--itemstack.stackSize;
						}
					}
					else if (itemstack2 != null && itemstack == null)
					{
						inventoryplayer.setItemStack(ItemStack.copyItemStack(itemstack2));
						itemstack = inventoryplayer.getItemStack();

						if (flag)
							itemstack.stackSize = itemstack.getMaxStackSize();
					}
					else
					{
						inventoryplayer.setItemStack((ItemStack)null);
					}
				}
				else
				{
					this.inventorySlots.slotClick(par1Slot == null ? par2 : par1Slot.slotNumber, par3, par4, this.mc.thePlayer);

					if (Container.func_94532_c(par3) == 2)
					{
						for (l = 0; l < 9; ++l)
							this.mc.playerController.sendSlotPacket(this.inventorySlots.getSlot(45 + l).getStack(), 36 + l);
					}
					else if (par1Slot != null)
					{
						itemstack1 = this.inventorySlots.getSlot(par1Slot.slotNumber).getStack();
						this.mc.playerController.sendSlotPacket(itemstack1, par1Slot.slotNumber - this.inventorySlots.inventorySlots.size() + 9 + 36);
					}
				}
			}
		}
	}

	/**
	 * Adds the buttons (and other controls) to the screen in question.
	 */
	@Override
	public void initGui()
	{
		if (this.mc.playerController.isInCreativeMode())
		{
			super.initGui();
			this.buttonList.clear();
			Keyboard.enableRepeatEvents(true);
			this.searchField = new GuiTextField(this.fontRendererObj, this.guiLeft + 82, this.guiTop + 6, 89, this.fontRendererObj.FONT_HEIGHT);
			this.searchField.setMaxStringLength(15);
			this.searchField.setEnableBackgroundDrawing(false);
			this.searchField.setVisible(false);
			this.searchField.setTextColor(16777215);
			int i = selectedTabIndex;
			selectedTabIndex = -1;
			this.setCurrentCreativeTab(CreativeTabs.creativeTabArray[i]);
			this.crafting = new CreativeCrafting(this.mc);
			this.mc.thePlayer.inventoryContainer.addCraftingToCrafters(this.crafting);
			int tabCount = CreativeTabs.creativeTabArray.length;
			if (tabCount > 12)
			{
				buttonList.add(new GuiButton(101, guiLeft,              guiTop - 50, 20, 20, "<"));
				buttonList.add(new GuiButton(102, guiLeft + xSize - 20, guiTop - 50, 20, 20, ">"));
				maxPages = (tabCount - 12) / 10 + 1;
			}
		}
		else
		{
			this.mc.displayGuiScreen(new GuiInventoryTFC(this.mc.thePlayer));
		}
	}

	/**
	 * Called when the screen is unloaded. Used to disable keyboard repeat events
	 */
	@Override
	public void onGuiClosed()
	{
		super.onGuiClosed();

		if (this.mc.thePlayer != null && this.mc.thePlayer.inventory != null)
			this.mc.thePlayer.inventoryContainer.removeCraftingFromCrafters(this.crafting);

		Keyboard.enableRepeatEvents(false);
	}

	/**
	 * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
	 */
	@Override
	protected void keyTyped(char par1, int par2)
	{
		if (!CreativeTabs.creativeTabArray[selectedTabIndex].hasSearchBar())
		{
			if (GameSettings.isKeyDown(this.mc.gameSettings.keyBindChat))
				this.setCurrentCreativeTab(CreativeTabs.tabAllSearch);
			else
				super.keyTyped(par1, par2);
		}
		else
		{
			if (this.eventTriggered)
			{
				this.eventTriggered = false;
				this.searchField.setText("");
			}

			if (!this.checkHotbarKeys(par2))
			{
				if (this.searchField.textboxKeyTyped(par1, par2))
					this.updateCreativeSearch();
				else
					super.keyTyped(par1, par2);
			}
		}
	}

	private void updateCreativeSearch()
	{
		ContainerCreativeTFC containercreative = (ContainerCreativeTFC)this.inventorySlots;
		containercreative.itemList.clear();

		CreativeTabs tab = CreativeTabs.creativeTabArray[selectedTabIndex];
		if (tab.hasSearchBar() && tab != CreativeTabs.tabAllSearch)
		{
			tab.displayAllReleventItems(containercreative.itemList);
			updateFilteredItems(containercreative);
			return;
		}

		Iterator iItems = Item.itemRegistry.iterator();
		while(iItems.hasNext())
		{
			Item item = (Item)iItems.next();
			if (item != null && item.getCreativeTab() != null)
				item.getSubItems(item, (CreativeTabs)null, containercreative.itemList);
		}

		Enchantment[] aenchantment = Enchantment.enchantmentsList;
		int i = aenchantment.length;
		for (int j = 0; j < i; ++j)
		{
			Enchantment enchantment = aenchantment[j];
			if (enchantment != null && enchantment.type != null)
				Items.enchanted_book.func_92113_a(enchantment, containercreative.itemList);
		}
		updateFilteredItems(containercreative);
	}

	//split from above for custom search tabs
	private void updateFilteredItems(ContainerCreativeTFC containercreative)
	{
		Iterator iterator = containercreative.itemList.iterator();
		String s = this.searchField.getText().toLowerCase();

		while (iterator.hasNext())
		{
			ItemStack itemstack = (ItemStack)iterator.next();
			boolean flag = false;
			Iterator iterator1 = itemstack.getTooltip(this.mc.thePlayer, this.mc.gameSettings.advancedItemTooltips).iterator();
			boolean loop = true;

			while (loop)
			{
				if (iterator1.hasNext())
				{
					String s1 = (String)iterator1.next();
					if (!s1.toLowerCase().contains(s))
						continue;
					flag = true;
				}

				if (!flag)
					iterator.remove();
				loop = false;
			}
		}

		this.currentScroll = 0.0F;
		containercreative.scrollTo(0.0F);
	}

	/**
	 * Draw the foreground layer for the GuiContainer (everything in front of the items)
	 */
	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		CreativeTabs creativetabs = CreativeTabs.creativeTabArray[selectedTabIndex];
		if (creativetabs != null && creativetabs.drawInForegroundOfTab())
			this.fontRendererObj.drawString(I18n.format(creativetabs.getTranslatedTabLabel()), 8, 6, 4210752);
	}

	/**
	 * Called when the mouse is clicked.
	 */
	@Override
	protected void mouseClicked(int par1, int par2, int par3)
	{
		if (par3 == 0)
		{
			int l = par1 - this.guiLeft;
			int i1 = par2 - this.guiTop;
			CreativeTabs[] acreativetabs = CreativeTabs.creativeTabArray;
			int j1 = acreativetabs.length;

			for (int k1 = 0; k1 < j1; ++k1)
			{
				CreativeTabs creativetabs = acreativetabs[k1];
				if (this.switchTab(creativetabs, l, i1))
					return;
			}
		}
		super.mouseClicked(par1, par2, par3);
	}

	/**
	 * Called when the mouse is moved or a mouse button is released.  Signature: (mouseX, mouseY, which) which==-1 is
	 * mouseMove, which==0 or which==1 is mouseUp
	 */
	@Override
	protected void mouseMovedOrUp(int par1, int par2, int par3)
	{
		if (par3 == 0)
		{
			int l = par1 - this.guiLeft;
			int i1 = par2 - this.guiTop;
			CreativeTabs[] acreativetabs = CreativeTabs.creativeTabArray;
			int j1 = acreativetabs.length;

			for (int k1 = 0; k1 < j1; ++k1)
			{
				CreativeTabs creativetabs = acreativetabs[k1];
				if (creativetabs != null && switchTab(creativetabs, l, i1))
				{
					this.setCurrentCreativeTab(creativetabs);
					return;
				}
			}
		}

		super.mouseMovedOrUp(par1, par2, par3);
	}

	/**
	 * returns (if you are not on the inventoryTab) and (the flag isn't set) and( you have more than 1 page of items)
	 */
	private boolean needsScrollBars()
	{
		if (CreativeTabs.creativeTabArray[selectedTabIndex] == null) return false;
		return selectedTabIndex != CreativeTabs.tabInventory.getTabIndex() && CreativeTabs.creativeTabArray[selectedTabIndex].shouldHidePlayerInventory() && ((ContainerCreativeTFC)this.inventorySlots).hasMoreThan1PageOfItemsInList();
	}

	private void setCurrentCreativeTab(CreativeTabs par1CreativeTabs)
	{
		if (par1CreativeTabs == null)
			return;

		int i = selectedTabIndex;
		selectedTabIndex = par1CreativeTabs.getTabIndex();
		ContainerCreativeTFC containercreative = (ContainerCreativeTFC)this.inventorySlots;
		this.field_147008_s/*field_94077_p*/.clear();
		containercreative.itemList.clear();
		par1CreativeTabs.displayAllReleventItems(containercreative.itemList);

		if (par1CreativeTabs == CreativeTabs.tabInventory)
		{
			Container container = this.mc.thePlayer.inventoryContainer;
			if (this.backupContainerSlots == null)
				this.backupContainerSlots = containercreative.inventorySlots;

			containercreative.inventorySlots = new ArrayList<Slot>();
			for (int j = 0; j < container.inventorySlots.size(); ++j)
			{
				SlotCreativeInventoryTFC slotcreativeinventory = new SlotCreativeInventoryTFC(this, (Slot)container.inventorySlots.get(j), j);
				containercreative.inventorySlots.add(slotcreativeinventory);
				int k;
				int l;
				int i1;

				if (j >= 5 && j < 9)
				{
					k = j - 5;
					l = k / 2;
					i1 = k % 2;
					slotcreativeinventory.xDisplayPosition = 9 + l * 54;
					slotcreativeinventory.yDisplayPosition = 6 + i1 * 27;
				}
				else if(j == 50)//50 should be the back slot for armor
				{
					k = 2;
					l = k / 2;
					i1 = k % 2;
					slotcreativeinventory.xDisplayPosition = 18+9 + l * 54;
					slotcreativeinventory.yDisplayPosition = 6 + i1 * 27;
				}
				else if (j >= 0 && j < 5)
				{
					slotcreativeinventory.yDisplayPosition = -2000;
					slotcreativeinventory.xDisplayPosition = -2000;
				}
				else if (j < container.inventorySlots.size())
				{
					k = j - 9;
					l = k % 9;
					i1 = k / 9;
					slotcreativeinventory.xDisplayPosition = 9 + l * 18;

					if (j >= 36)
						slotcreativeinventory.yDisplayPosition = 112;
					else
						slotcreativeinventory.yDisplayPosition = 54 + i1 * 18;
				}
			}

			this.zeroSlot = new Slot(inventory, 0, 173, 112);
			containercreative.inventorySlots.add(this.zeroSlot);
		}
		else if (i == CreativeTabs.tabInventory.getTabIndex())
		{
			containercreative.inventorySlots = this.backupContainerSlots;
			this.backupContainerSlots = null;
		}

		if (this.searchField != null)
		{
			if (par1CreativeTabs.hasSearchBar())
			{
				this.searchField.setVisible(true);
				this.searchField.setCanLoseFocus(false);
				this.searchField.setFocused(true);
				this.searchField.setText("");
				this.updateCreativeSearch();
			}
			else
			{
				this.searchField.setVisible(false);
				this.searchField.setCanLoseFocus(true);
				this.searchField.setFocused(false);
			}
		}

		this.currentScroll = 0.0F;
		containercreative.scrollTo(0.0F);
	}

	/**
	 * Handles mouse input.
	 */
	@Override
	public void handleMouseInput()
	{
		super.handleMouseInput();
		int i = Mouse.getEventDWheel();

		if (i != 0 && this.needsScrollBars())
		{
			int j = ((ContainerCreativeTFC)this.inventorySlots).itemList.size() / 9 - 5 + 1;

			if (i > 0)
				i = 1;
			if (i < 0)
				i = -1;

			this.currentScroll = (float) (this.currentScroll - (double) i / (double) j);

			if (this.currentScroll < 0.0F)
				this.currentScroll = 0.0F;

			if (this.currentScroll > 1.0F)
				this.currentScroll = 1.0F;

			((ContainerCreativeTFC)this.inventorySlots).scrollTo(this.currentScroll);
		}
	}

	/**
	 * Draws the screen and all the components in it.
	 */
	@Override
	public void drawScreen(int par1, int par2, float par3)
	{
		boolean flag = Mouse.isButtonDown(0);
		int k = this.guiLeft;
		int l = this.guiTop;
		int i1 = k + 175;
		int j1 = l + 18;
		int k1 = i1 + 14;
		int l1 = j1 + 112;

		if (!this.wasClicking && flag && par1 >= i1 && par2 >= j1 && par1 < k1 && par2 < l1)
			this.isScrolling = this.needsScrollBars();

		if (!flag)
			this.isScrolling = false;

		this.wasClicking = flag;

		if (this.isScrolling)
		{
			this.currentScroll = (par2 - j1 - 7.5F) / (l1 - j1 - 15.0F);

			if (this.currentScroll < 0.0F)
				this.currentScroll = 0.0F;

			if (this.currentScroll > 1.0F)
				this.currentScroll = 1.0F;

			((ContainerCreativeTFC)this.inventorySlots).scrollTo(this.currentScroll);
		}

		super.drawScreen(par1, par2, par3);
		CreativeTabs[] acreativetabs = CreativeTabs.creativeTabArray;
		int start = tabPage * 10;
		int i2 = Math.min(acreativetabs.length, (tabPage + 1) * 10 + 2);
		if (tabPage != 0) start += 2;
		boolean rendered = false;

		for (int j2 = start; j2 < i2; ++j2)
		{
			CreativeTabs creativetabs = acreativetabs[j2];

			if (creativetabs != null && this.renderCreativeInventoryHoveringText(creativetabs, par1, par2))
			{
				rendered = true;
				break;
			}
		}

		if (!rendered && !renderCreativeInventoryHoveringText(CreativeTabs.tabAllSearch, par1, par2))
			renderCreativeInventoryHoveringText(CreativeTabs.tabInventory, par1, par2);

		if (this.zeroSlot != null && selectedTabIndex == CreativeTabs.tabInventory.getTabIndex() &&
				this.func_146978_c/*isPointInRegion*/(this.zeroSlot.xDisplayPosition, this.zeroSlot.yDisplayPosition, 16, 16, par1, par2))
		{
			this.drawCreativeTabHoveringText(I18n.format("inventory.binSlot"), par1, par2);
		}

		if (maxPages != 0)
		{
			String page = String.format("%d / %d", tabPage + 1, maxPages + 1);
			int width = fontRendererObj.getStringWidth(page);
			GL11.glDisable(GL11.GL_LIGHTING);
			this.zLevel = 300.0F;
			itemRender.zLevel = 300.0F;
			fontRendererObj.drawString(page, guiLeft + (xSize / 2) - (width / 2), guiTop - 44, -1);
			this.zLevel = 0.0F;
			itemRender.zLevel = 0.0F;
		}

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(GL11.GL_LIGHTING);
	}

	@Override
	protected void renderToolTip(ItemStack par1ItemStack, int par2, int par3)
	{
		if (selectedTabIndex == CreativeTabs.tabAllSearch.getTabIndex())
		{
			List list = par1ItemStack.getTooltip(this.mc.thePlayer, this.mc.gameSettings.advancedItemTooltips);
			CreativeTabs creativetabs = par1ItemStack.getItem().getCreativeTab();

			if (creativetabs == null && par1ItemStack.getItem() == Items.enchanted_book)
			{
				Map map = EnchantmentHelper.getEnchantments(par1ItemStack);

				if (map.size() == 1)
				{
					Enchantment enchantment = Enchantment.enchantmentsList[((Integer)map.keySet().iterator().next()).intValue()];
					CreativeTabs[] acreativetabs = CreativeTabs.creativeTabArray;
					int k = acreativetabs.length;

					for (int l = 0; l < k; ++l)
					{
						CreativeTabs creativetabs1 = acreativetabs[l];
						if (creativetabs1.func_111226_a(enchantment.type))
						{
							creativetabs = creativetabs1;
							break;
						}
					}
				}
			}

			if (creativetabs != null)
				list.add(1, EnumChatFormatting.BOLD.toString() + EnumChatFormatting.BLUE + I18n.format(creativetabs.getTranslatedTabLabel()));

			for (int i1 = 0; i1 < list.size(); ++i1)
			{
				if (i1 == 0)
					list.set(i1, par1ItemStack.getRarity().rarityColor + (String)list.get(i1));
				else
					list.set(i1, EnumChatFormatting.GRAY + (String)list.get(i1));
			}

			this.func_146283_a(list, par2, par3);
		}
		else
		{
			super.renderToolTip(par1ItemStack, par2, par3);
		}
	}

	/**
	 * Draw the background layer for the GuiContainer (everything behind the items)
	 */
	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		RenderHelper.enableGUIStandardItemLighting();
		CreativeTabs creativetabs = CreativeTabs.creativeTabArray[selectedTabIndex];
		CreativeTabs[] acreativetabs = CreativeTabs.creativeTabArray;
		int k = acreativetabs.length;
		int l;

		int start = tabPage * 10;
		k = Math.min(acreativetabs.length, ((tabPage + 1) * 10 + 2));
		if (tabPage != 0) start += 2;
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		for (l = start; l < k; ++l)
		{
			CreativeTabs creativetabs1 = acreativetabs[l];
			this.mc.getTextureManager().bindTexture(TEXTURE);
			if (creativetabs1 != null && creativetabs1.getTabIndex() != selectedTabIndex)
				this.renderCreativeTab(creativetabs1);
		}

		if (tabPage != 0)
		{
			if (creativetabs != CreativeTabs.tabAllSearch)
			{
				this.mc.getTextureManager().bindTexture(TEXTURE);
				renderCreativeTab(CreativeTabs.tabAllSearch);
			}
			if (creativetabs != CreativeTabs.tabInventory)
			{
				this.mc.getTextureManager().bindTexture(TEXTURE);
				renderCreativeTab(CreativeTabs.tabInventory);
			}
		}
		this.mc.getTextureManager().bindTexture(new ResourceLocation("textures/gui/container/creative_inventory/tab_" + creativetabs.getBackgroundImageName()));
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		this.searchField.drawTextBox();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		int i1 = this.guiLeft + 175;
		k = this.guiTop + 18;
		l = k + 112;
		this.mc.getTextureManager().bindTexture(TEXTURE);

		if (creativetabs.shouldHidePlayerInventory())
			this.drawTexturedModalRect(i1, k + (int) ((l - k - 17) * this.currentScroll), 232 + (this.needsScrollBars() ? 0 : 12), 0, 12, 15);

		if (creativetabs.getTabPage() != tabPage)
			if (creativetabs != CreativeTabs.tabAllSearch && creativetabs != CreativeTabs.tabInventory)
				return;

		this.renderCreativeTab(creativetabs);

		if (creativetabs == CreativeTabs.tabInventory)
			GuiInventoryTFC.drawPlayerModel(this.guiLeft + 43, this.guiTop + 45, 20, this.guiLeft + 43 - par2, this.guiTop + 45 - 30 - par3, this.mc.thePlayer);
	}

	protected boolean switchTab(CreativeTabs par1CreativeTabs, int par2, int par3)
	{
		if (par1CreativeTabs.getTabPage() != tabPage)
			if (par1CreativeTabs != CreativeTabs.tabAllSearch && par1CreativeTabs != CreativeTabs.tabInventory)
				return false;

		int k = par1CreativeTabs.getTabColumn();
		int l = 28 * k;
		byte b0 = 0;

		if (k == 5)
			l = this.xSize - 28 + 2;
		else if (k > 0)
			l += k;

		int i1;

		if (par1CreativeTabs.isTabInFirstRow())
			i1 = b0 - 32;
		else
			i1 = b0 + this.ySize;

		return par2 >= l && par2 <= l + 28 && par3 >= i1 && par3 <= i1 + 32;
	}

	/**
	 * Renders the creative inventory hovering text if mouse is over it. Returns true if did render or false otherwise.
	 * Params: current creative tab to be checked, current mouse x position, current mouse y position.
	 */
	protected boolean renderCreativeInventoryHoveringText(CreativeTabs par1CreativeTabs, int par2, int par3)
	{
		int k = par1CreativeTabs.getTabColumn();
		int l = 28 * k;
		byte b0 = 0;

		if (k == 5)
			l = this.xSize - 28 + 2;
		else if (k > 0)
			l += k;

		int i1;

		if (par1CreativeTabs.isTabInFirstRow())
			i1 = b0 - 32;
		else
			i1 = b0 + this.ySize;

		if (this.func_146978_c/*isPointInRegion*/(l + 3, i1 + 3, 23, 27, par2, par3))
		{
			this.drawCreativeTabHoveringText(I18n.format(par1CreativeTabs.getTranslatedTabLabel()), par2, par3);
			return true;
		}
		else
		{
			return false;
		}
	}

	/**
	 * Renders passed creative inventory tab into the screen.
	 */
	protected void renderCreativeTab(CreativeTabs par1CreativeTabs)
	{
		boolean flag = par1CreativeTabs.getTabIndex() == selectedTabIndex;
		boolean flag1 = par1CreativeTabs.isTabInFirstRow();
		int i = par1CreativeTabs.getTabColumn();
		int j = i * 28;
		int k = 0;
		int l = this.guiLeft + 28 * i;
		int i1 = this.guiTop;
		byte b0 = 32;

		if (flag)
			k += 32;

		if (i == 5)
			l = this.guiLeft + this.xSize - 28;
		else if (i > 0)
			l += i;

		if (flag1)
		{
			i1 -= 28;
		}
		else
		{
			k += 64;
			i1 += this.ySize - 4;
		}

		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glColor3f(1F, 1F, 1F); //Forge: Reset color in case Items change it.
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		this.drawTexturedModalRect(l, i1, j, k, 28, b0);
		this.zLevel = 100.0F;
		itemRender.zLevel = 100.0F;
		l += 6;
		i1 += 8 + (flag1 ? 1 : -1);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		ItemStack itemstack = par1CreativeTabs.getIconItemStack();
		if (itemstack != null)
		{
			itemRender.renderItemAndEffectIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), itemstack, l, i1);
			itemRender.renderItemOverlayIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), itemstack, l, i1);
		}
		GL11.glDisable(GL11.GL_LIGHTING);
		itemRender.zLevel = 0.0F;
		this.zLevel = 0.0F;
	}

	/**
	 * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
	 */
	@Override
	protected void actionPerformed(GuiButton par1GuiButton)
	{
		if (par1GuiButton.id == 0)
			this.mc.displayGuiScreen(new GuiAchievements(this, this.mc.thePlayer.getStatFileWriter()));

		if (par1GuiButton.id == 1)
			this.mc.displayGuiScreen(new GuiStats(this, this.mc.thePlayer.getStatFileWriter()));

		if (par1GuiButton.id == 101)
			tabPage = Math.max(tabPage - 1, 0);
		else if (par1GuiButton.id == 102)
			tabPage = Math.min(tabPage + 1, maxPages);
	}

	/**
	 * Returns the current creative tab index.
	 */
	public int getCurrentTabIndex()
	{
		return selectedTabIndex;
	}

	/**
	 * Returns the creative inventory
	 */
	public static InventoryBasic getInventory()
	{
		return inventory;
	}
}
