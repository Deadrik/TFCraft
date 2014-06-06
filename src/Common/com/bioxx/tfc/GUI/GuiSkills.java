package com.bioxx.tfc.GUI;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Containers.ContainerSkills;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Textures;
import com.bioxx.tfc.Core.Player.PlayerInventory;
import com.bioxx.tfc.Core.Player.SkillStats;
import com.bioxx.tfc.api.SkillsManager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public class GuiSkills extends GuiContainerTFC
{
	public static ResourceLocation GuiTex = new ResourceLocation(Reference.ModID, Reference.AssetPathGui + "gui_skills.png");
	protected EntityPlayer player;

	public GuiSkills(EntityPlayer player)
	{
		super(new ContainerSkills(player), 176, 166);
		this.setDrawInventory(false);
		this.player = player;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) 
	{
		SkillStats ss = TFC_Core.getSkillStats(player);
		int y = 10;
		for(String o : SkillsManager.instance.getSkillsArray())
		{
			fontRendererObj.drawString(StatCollector.translateToLocal(o) + ": " + EnumChatFormatting.DARK_BLUE + ss.getSkillRaw(o), 4, y, 0, false);
			y+=10;
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		this.drawGui(GuiTex);
	}
	
	@Override
	protected void drawGui(ResourceLocation rl)
	{
		if(rl != null)
		{
			bindTexture(rl);
			guiLeft = (width - xSize) / 2;
			guiTop = ((height - ySize) / 2)-3;
			drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		}
		if(drawInventory)
			PlayerInventory.drawInventory(this, width, height, ySize-PlayerInventory.invYSize);
	}

	@Override
	public void initGui()
	{
		super.initGui();
		this.guiLeft = (this.width - this.xSize) / 2;
		this.guiTop = (this.height - this.ySize) / 2;
		buttonList.clear();
		buttonList.add(new GuiInventoryButton(0, guiLeft+176, guiTop, 25, 20, 
				0, 86, 25, 20, StatCollector.translateToLocal("gui.Inventory.Inventory"), TFC_Textures.GuiInventory));
		buttonList.add(new GuiInventoryButton(1, guiLeft+176, guiTop + 19, 25, 20, 
				0, 86, 25, 20, StatCollector.translateToLocal("gui.Inventory.Skills"), TFC_Textures.GuiSkills));
		buttonList.add(new GuiInventoryButton(2, guiLeft+176, guiTop + 38, 25, 20, 
				0, 86, 25, 20, StatCollector.translateToLocal("gui.Calendar.Calendar"), TFC_Textures.GuiCalendar));
		buttonList.add(new GuiInventoryButton(3, guiLeft+176, guiTop + 57, 25, 20, 
				0, 86, 25, 20, StatCollector.translateToLocal("gui.Inventory.Health"), TFC_Textures.GuiHealth));
	}

	@Override
	protected void actionPerformed(GuiButton guibutton)
	{
		if (guibutton.id == 0)
			Minecraft.getMinecraft().displayGuiScreen(new GuiInventoryTFC(Minecraft.getMinecraft().thePlayer));
		else if (guibutton.id == 2)
			Minecraft.getMinecraft().displayGuiScreen(new GuiCalendar(Minecraft.getMinecraft().thePlayer));
		else if (guibutton.id == 3)
			Minecraft.getMinecraft().displayGuiScreen(new GuiHealth(Minecraft.getMinecraft().thePlayer));
	}
}
