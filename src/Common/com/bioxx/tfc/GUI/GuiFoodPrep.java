package com.bioxx.tfc.GUI;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Containers.ContainerFoodPrep;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Textures;
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
	protected void drawGuiContainerForegroundLayer(int i, int j)
	{
		if(table.getMealWeight() < 14 || !table.areComponentsCorrect())
			((GuiButton)buttonList.get(0)).enabled = false;
		else ((GuiButton)buttonList.get(0)).enabled = true;
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
			drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		}
		if(drawInventory)
			PlayerInventory.drawInventory(this, width, height, ySize - PlayerInventory.invYSize);
	}

	@Override
	public void drawCenteredString(FontRenderer fontrenderer, String s, int i, int j, int k)
	{
		fontrenderer.drawString(s, i - fontrenderer.getStringWidth(s) / 2, j, k);
	}

	@Override
	public void initGui()
	{
		super.initGui();
		buttonList.clear();
		if(guiTab == 0)
		{
			buttonList.add(new GuiButton(0, guiLeft + 101, guiTop + 33, 40, 20, StatCollector.translateToLocal("gui.FoodPrep.CreateMeal")));
		}
		buttonList.add(new GuiFoodPrepTabButton(1, guiLeft+5, guiTop-12, 31, 15, this, TFC_Textures.GuiSolidStorage, StatCollector.translateToLocal("gui.FoodPrep.Sandwich")));
	}

	@Override
	protected void actionPerformed(GuiButton guibutton)
	{
		if (guibutton.id == 0)
			table.actionCreate(Minecraft.getMinecraft().thePlayer);
	}

	public class GuiFoodPrepTabButton extends GuiButton 
	{
		GuiFoodPrep screen;
		IIcon buttonicon = null;

		int xPos = 0;
		int yPos = 172;
		int xSize = 31;
		int ySize = 15;

		public GuiFoodPrepTabButton(int index, int xPos, int yPos, int width, int height, GuiFoodPrep gui, IIcon icon, String s)
		{
			super(index, xPos, yPos, width, height, s);
			screen = gui;
			buttonicon = icon;
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

		@Override
		public void drawButton(Minecraft mc, int x, int y)
		{
			if (this.visible)
			{
				int k = this.getHoverState(this.field_146123_n)-1;

				TFC_Core.bindTexture(GuiFoodPrep.texture);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				this.zLevel = 301f;
				this.drawTexturedModalRect(this.xPosition, this.yPosition, xPos, yPos, xSize, ySize);
				this.field_146123_n = x >= this.xPosition && y >= this.yPosition && x < this.xPosition + this.width && y < this.yPosition + this.height;

				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				TFC_Core.bindTexture(TextureMap.locationBlocksTexture);
				if(buttonicon != null)
					this.drawTexturedModelRectFromIcon(this.xPosition+12, this.yPosition+4, buttonicon, 8, 8);

				this.zLevel = 0;
				this.mouseDragged(mc, x, y);

				if(field_146123_n)
				{
					FontRenderer fontrenderer = Minecraft.getMinecraft().fontRenderer;
					screen.drawTooltip(x, y, this.displayString);
					GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				}
			}
		}
	}
}
