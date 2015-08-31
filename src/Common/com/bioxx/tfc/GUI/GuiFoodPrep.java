package com.bioxx.tfc.GUI;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Containers.ContainerFoodPrep;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.Player.PlayerInventory;
import com.bioxx.tfc.TileEntities.TEFoodPrep;
import com.bioxx.tfc.api.TFCItems;

public class GuiFoodPrep extends GuiContainerTFC
{
	private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MOD_ID, Reference.ASSET_PATH_GUI + "gui_foodprep.png");
	private TEFoodPrep foodPrepTE;
	private int guiTab;

	public GuiFoodPrep(InventoryPlayer inventoryplayer, TEFoodPrep te, World world, int i, int j, int k, int tab)
	{
		super(new ContainerFoodPrep(inventoryplayer, te, world, i, j, k, tab), 176, 85);
		foodPrepTE = te;
		guiTab = tab;
	}

	/*
	 * Edited Copy/Paste of drawGui() code since the background texture changes depending on the tab selected.
	 */
	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		bindTexture(TEXTURE);
		guiLeft = (width - xSize) / 2;
		guiTop = (height - ySize) / 2;
		if (guiTab == 0)
		{
			drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, this.getShiftedYSize());
		}
		else if (guiTab == 1)
		{
			drawTexturedModalRect(guiLeft, guiTop, 0, 86, xSize, this.getShiftedYSize());
		}

		PlayerInventory.drawInventory(this, width, height, this.getShiftedYSize());
	}

	@Override
	public void initGui()
	{
		super.initGui();
		buttonList.clear();
		if (guiTab == 0)
		{
			buttonList.add(new GuiButton(0, guiLeft + 74, guiTop + 44, 50, 20, TFC_Core.translate("gui.FoodPrep.CreateMeal")));
			buttonList.add(new GuiFoodPrepTabButton(2, guiLeft + 36, guiTop - 21, 31, 21, this, new ItemStack(TFCItems.salad), TFC_Core.translate("gui.FoodPrep.Salad")).setButtonCoord(31, 172));
			buttonList.add(new GuiFoodPrepTabButton(1, guiLeft + 5, guiTop - 21, 31, 21, this, new ItemStack(TFCItems.sandwich), TFC_Core.translate("gui.FoodPrep.Sandwich")));
		}
		else if (guiTab == 1)
		{
			buttonList.add(new GuiButton(0, guiLeft + 74, guiTop + 44, 50, 20, TFC_Core.translate("gui.FoodPrep.CreateMeal")));
			buttonList.add(new GuiFoodPrepTabButton(2, guiLeft + 36, guiTop - 21, 31, 21, this, new ItemStack(TFCItems.salad), TFC_Core.translate("gui.FoodPrep.Salad")));
			buttonList.add(new GuiFoodPrepTabButton(1, guiLeft + 5, guiTop - 21, 31, 21, this, new ItemStack(TFCItems.sandwich), TFC_Core.translate("gui.FoodPrep.Sandwich")).setButtonCoord(31, 172));
		}
	}

	@Override
	protected void actionPerformed(GuiButton guibutton)
	{
		if (guibutton.id == 0)
			foodPrepTE.actionCreate(Minecraft.getMinecraft().thePlayer);
		else if (guibutton.id == 1 && guiTab != 0)//Pressed Sandwich Tab
			foodPrepTE.actionSwitchTab(0, Minecraft.getMinecraft().thePlayer);
		else if (guibutton.id == 2 && guiTab != 1)//Pressed Salad Tab
			foodPrepTE.actionSwitchTab(1, Minecraft.getMinecraft().thePlayer);
	}

	@Override
	public void updateScreen()
	{
		super.updateScreen();
		if (guiTab == 0 && foodPrepTE.validateSandwich())
			((GuiButton) buttonList.get(0)).enabled = true;
		else if (guiTab == 1 && foodPrepTE.validateSalad())
			((GuiButton) buttonList.get(0)).enabled = true;
		else if (((GuiButton) buttonList.get(0)).enabled)
			((GuiButton) buttonList.get(0)).enabled = false;

	}

	public class GuiFoodPrepTabButton extends GuiButton
	{
		private GuiFoodPrep screen;
		private ItemStack item;

		private int xPos;
		private int yPos = 172;
		private int xSize = 31;
		private int ySize = 24;

		public GuiFoodPrepTabButton(int index, int xPos, int yPos, int width, int height, GuiFoodPrep gui, ItemStack is, String s)
		{
			super(index, xPos, yPos, width, height, s);
			screen = gui;
			item = is;
		}

		public GuiFoodPrepTabButton(int index, int xPos, int yPos, int width, int height, GuiFoodPrep gui, String s, int xp, int yp, int xs, int ys)
		{
			super(index, xPos, yPos, width, height, s);
			screen = gui;
			this.xPos = xp;
			this.yPos = yp;
			xSize = xs;
			ySize = ys;
		}

		public GuiFoodPrepTabButton setButtonCoord(int x, int y)
		{
			xPos = x;
			yPos = y;
			return this;
		}

		@Override
		public void drawButton(Minecraft mc, int x, int y)
		{
			if (this.visible)
			{
				//int k = this.getHoverState(this.field_146123_n) - 1;

				TFC_Core.bindTexture(GuiFoodPrep.TEXTURE);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				RenderHelper.disableStandardItemLighting();
				GL11.glDisable(GL11.GL_LIGHTING);
				GL11.glDisable(GL11.GL_DEPTH_TEST);
				this.zLevel = 301f;
				this.drawTexturedModalRect(this.xPosition, this.yPosition, xPos, yPos, xSize, ySize);
				this.field_146123_n = x >= this.xPosition && y >= this.yPosition && x < this.xPosition + this.width && y < this.yPosition + this.height;

				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				GL11.glPushMatrix(); //start
				renderInventorySlot(item, this.xPosition + 8, this.yPosition + 5);
				GL11.glPopMatrix(); //end
				this.mouseDragged(mc, x, y);

				if (field_146123_n)
				{
					screen.drawTooltip(x, y, this.displayString);
					GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				}
			}
		}

		protected void renderInventorySlot(ItemStack par1, int par2, int par3)
		{
			if (par1 != null)
			{
				RenderItem.getInstance().renderItemAndEffectIntoGUI(Minecraft.getMinecraft().fontRenderer, Minecraft.getMinecraft().getTextureManager(), par1, par2, par3);
			}
		}
	}
}
