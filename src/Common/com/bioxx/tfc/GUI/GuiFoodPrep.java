package com.bioxx.tfc.GUI;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Containers.ContainerFoodPrep;
import com.bioxx.tfc.Core.Player.PlayerInventory;
import com.bioxx.tfc.TileEntities.TEFoodPrep;

public class GuiFoodPrep extends GuiContainerTFC
{
	private static final ResourceLocation texture = new ResourceLocation(Reference.ModID, Reference.AssetPathGui + "gui_foodprep.png");
	TEFoodPrep table;

	public GuiFoodPrep(InventoryPlayer inventoryplayer, TEFoodPrep wb, World world, int i, int j, int k)
	{
		super(new ContainerFoodPrep(inventoryplayer, wb,world, i, j, k), 176, 85);
		table = wb;
	}

	@Override
	public void onGuiClosed()
	{
		super.onGuiClosed();
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j)
	{
		fontRendererObj.drawString("10oz", 43, 12, 0x404040);
		fontRendererObj.drawString("4oz", 49, 30, 0x404040);
		fontRendererObj.drawString("4oz", 49, 48, 0x404040);
		fontRendererObj.drawString("2oz", 49, 66, 0x404040);

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
			drawTexturedModalRect(guiLeft + 0, guiTop, 0, 0, xSize, ySize);
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
		buttonList.add(new GuiButton(0, guiLeft + 92, guiTop + 59, 40, 20, StatCollector.translateToLocal("gui.FoodPrep.CreateMeal")));
	}

	@Override
	protected void actionPerformed(GuiButton guibutton)
	{
		if (guibutton.id == 0)
			table.actionCreate(Minecraft.getMinecraft().thePlayer);
	}
}
