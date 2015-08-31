package com.bioxx.tfc.GUI;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Containers.ContainerSkills;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Textures;
import com.bioxx.tfc.Core.Player.FoodStatsTFC;
import com.bioxx.tfc.api.TFCOptions;

public class GuiHealth extends GuiContainerTFC
{
	public static ResourceLocation texture = new ResourceLocation(Reference.MOD_ID, Reference.ASSET_PATH_GUI + "gui_health.png");
	protected EntityPlayer player;

	public GuiHealth(EntityPlayer player)
	{
		super(new ContainerSkills(player), 176, 104);
		this.setDrawInventory(false);
		this.player = player;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		fontRendererObj.drawString(TFC_Core.translate("gui.food.fruit"), 5, 13, 0, false);
		fontRendererObj.drawString(TFC_Core.translate("gui.food.vegetable"), 5, 23, 0, false);
		fontRendererObj.drawString(TFC_Core.translate("gui.food.grain"), 5, 33, 0, false);
		fontRendererObj.drawString(TFC_Core.translate("gui.food.protein"), 5, 43, 0, false);
		fontRendererObj.drawString(TFC_Core.translate("gui.food.dairy"), 5, 53, 0, false);
		if (TFCOptions.enableDebugMode)
		{
			FoodStatsTFC food = TFC_Core.getPlayerFoodStats(player);
			fontRendererObj.drawString(Float.toString(food.nutrFruit), 85, 13, 0, false);
			fontRendererObj.drawString(Float.toString(food.nutrVeg), 85, 23, 0, false);
			fontRendererObj.drawString(Float.toString(food.nutrGrain), 85, 33, 0, false);
			fontRendererObj.drawString(Float.toString(food.nutrProtein), 85, 43, 0, false);
			fontRendererObj.drawString(Float.toString(food.nutrDairy), 85, 53, 0, false);
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		this.drawGui(texture);
	}

	@Override
	protected void drawGui(ResourceLocation rl)
	{
		bindTexture(rl);
		guiLeft = (width - xSize) / 2;
		guiTop = (height - ySize) / 2 - 34; //Shifted 34 pixels up to match other inventory tabs
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize); //No inventory drawn, so shifted ySize is not necessary
		drawForeground(guiLeft, guiTop);
	}

	@Override
	protected void drawForeground(int guiLeft, int guiTop)
	{
		FoodStatsTFC food = TFC_Core.getPlayerFoodStats(player);

		drawTexturedModalRect(guiLeft + 55, guiTop + 14, 0, 106, (int) (food.nutrFruit * 24), 6);
		drawTexturedModalRect(guiLeft + 55, guiTop + 24, 0, 106, (int) (food.nutrVeg * 24), 6);
		drawTexturedModalRect(guiLeft + 55, guiTop + 34, 0, 106, (int) (food.nutrGrain * 24), 6);
		drawTexturedModalRect(guiLeft + 55, guiTop + 44, 0, 106, (int) (food.nutrProtein * 24), 6);
		drawTexturedModalRect(guiLeft + 55, guiTop + 54, 0, 106, (int) (food.nutrDairy * 24), 6);
	}

	@Override
	public void initGui()
	{
		super.initGui();
		this.guiLeft = (this.width - this.xSize) / 2;
		this.guiTop = (this.height - this.ySize) / 2;
		buttonList.clear();
		buttonList.add(new GuiInventoryButton(0, guiLeft + 176, guiTop - 31, 25, 20, 0, 86, 25, 20, TFC_Core.translate("gui.Inventory.Inventory"), TFC_Textures.guiInventory));
		buttonList.add(new GuiInventoryButton(1, guiLeft + 176, guiTop - 12, 25, 20, 0, 86, 25, 20, TFC_Core.translate("gui.Inventory.Skills"), TFC_Textures.guiSkills));
		buttonList.add(new GuiInventoryButton(2, guiLeft + 176, guiTop + 7, 25, 20, 0, 86, 25, 20, TFC_Core.translate("gui.Calendar.Calendar"), TFC_Textures.guiCalendar));
		buttonList.add(new GuiInventoryButton(3, guiLeft + 176, guiTop + 26, 25, 20, 0, 86, 25, 20, TFC_Core.translate("gui.Inventory.Health"), TFC_Textures.guiHealth));
	}

	@Override
	protected void actionPerformed(GuiButton guibutton)
	{
		if (guibutton.id == 0)
			Minecraft.getMinecraft().displayGuiScreen(new GuiInventoryTFC(Minecraft.getMinecraft().thePlayer));
		else if (guibutton.id == 1)
			Minecraft.getMinecraft().displayGuiScreen(new GuiSkills(Minecraft.getMinecraft().thePlayer));
		else if (guibutton.id == 2)
			Minecraft.getMinecraft().displayGuiScreen(new GuiCalendar(Minecraft.getMinecraft().thePlayer));
	}
}
