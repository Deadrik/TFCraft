package com.bioxx.tfc.GUI;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.TFCItems;
import com.bioxx.tfc.Containers.ContainerFoodPrep;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.Player.PlayerInventory;
import com.bioxx.tfc.TileEntities.TEFoodPrep;

public class GuiFoodPrep extends GuiContainerTFC
{
	private static final ResourceLocation texture = new ResourceLocation(Reference.ModID, Reference.AssetPathGui + "gui_foodprep.png");
	TEFoodPrep table;
	private int guiTab = 0;

	public GuiFoodPrep(InventoryPlayer inventoryplayer, TEFoodPrep wb, World world, int i, int j, int k, int tab)
	{
		super(new ContainerFoodPrep(inventoryplayer, wb,world, i, j, k, tab), 176, 85);
		table = wb;
		guiTab = tab;
	}

	@Override
	public void onGuiClosed()
	{
		super.onGuiClosed();
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		drawGui(texture);
	}

	@Override
	protected void drawGui(ResourceLocation rl)
	{
		if(rl != null)
		{
			bindTexture(rl);
			guiLeft = (width - xSize) / 2;
			guiTop = (height - ySize) / 2;
			if(guiTab == 0)
			{
				drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
			}
			else if(guiTab == 1)
			{
				drawTexturedModalRect(guiLeft, guiTop, 0, 86, xSize, ySize);
			}
		}
		if(drawInventory)
			PlayerInventory.drawInventory(this, width, height, ySize - PlayerInventory.invYSize);
	}

	@Override
	public void initGui()
	{
		super.initGui();
		buttonList.clear();
		if(guiTab == 0)
		{
			buttonList.add(new GuiButton(0, guiLeft + 101, guiTop + 33, 42, 20, StatCollector.translateToLocal("gui.FoodPrep.CreateMeal")));
			buttonList.add(new GuiFoodPrepTabButton(2, guiLeft+36, guiTop-21, 31, 21, this, new ItemStack(TFCItems.Salad), StatCollector.translateToLocal("gui.FoodPrep.Salad")).setButtonCoord(31, 172));
			buttonList.add(new GuiFoodPrepTabButton(1, guiLeft+5, guiTop-21, 31, 21, this, new ItemStack(TFCItems.Sandwich), StatCollector.translateToLocal("gui.FoodPrep.Sandwich")));
		}
		else if(guiTab == 1)
		{
			buttonList.add(new GuiButton(0, guiLeft + 101, guiTop + 33, 42, 20, StatCollector.translateToLocal("gui.FoodPrep.CreateMeal")));
			buttonList.add(new GuiFoodPrepTabButton(2, guiLeft+36, guiTop-21, 31, 21, this, new ItemStack(TFCItems.Salad), StatCollector.translateToLocal("gui.FoodPrep.Salad")));
			buttonList.add(new GuiFoodPrepTabButton(1, guiLeft+5, guiTop-21, 31, 21, this, new ItemStack(TFCItems.Sandwich), StatCollector.translateToLocal("gui.FoodPrep.Sandwich")).setButtonCoord(31, 172));
		}
	}

	@Override
	protected void actionPerformed(GuiButton guibutton)
	{
		if (guibutton.id == 0)
			table.actionCreate(Minecraft.getMinecraft().thePlayer);
		else if (guibutton.id == 1 && guiTab != 0)//Pressed Sandwich Tab
			table.actionSwitchTab(0, Minecraft.getMinecraft().thePlayer);
		else if (guibutton.id == 2 && guiTab != 1)//Pressed Salad Tab
			table.actionSwitchTab(1, Minecraft.getMinecraft().thePlayer);
	}

	@Override
	public void updateScreen()
	{
		super.updateScreen();
		if(guiTab == 0 && table.validateSandwich())
			((GuiButton)buttonList.get(0)).enabled = true;
		else if(guiTab == 1 && table.validateSalad())
			((GuiButton)buttonList.get(0)).enabled = true;
		else if(((GuiButton)buttonList.get(0)).enabled)
			((GuiButton)buttonList.get(0)).enabled = false;

	}

	public class GuiFoodPrepTabButton extends GuiButton 
	{
		GuiFoodPrep screen;
		ItemStack item;

		int xPos = 0;
		int yPos = 172;
		int xSize = 31;
		int ySize = 24;

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
				int k = this.getHoverState(this.field_146123_n)-1;

				TFC_Core.bindTexture(GuiFoodPrep.texture);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				RenderHelper.disableStandardItemLighting();
				GL11.glDisable(GL11.GL_LIGHTING);
				GL11.glDisable(GL11.GL_DEPTH_TEST);
				this.zLevel = 301f;
				this.drawTexturedModalRect(this.xPosition, this.yPosition, xPos, yPos, xSize, ySize);
				this.field_146123_n = x >= this.xPosition && y >= this.yPosition && x < this.xPosition + this.width && y < this.yPosition + this.height;

				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				GL11.glPushMatrix(); //start
				renderInventorySlot(item,this.xPosition+8, this.yPosition+5);
				GL11.glPopMatrix(); //end
				this.mouseDragged(mc, x, y);

				if(field_146123_n)
				{
					FontRenderer fontrenderer = Minecraft.getMinecraft().fontRenderer;
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
