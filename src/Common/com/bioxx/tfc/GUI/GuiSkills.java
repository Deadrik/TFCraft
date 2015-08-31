package com.bioxx.tfc.GUI;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Containers.ContainerSkills;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Textures;
import com.bioxx.tfc.Core.Player.SkillStats;
import com.bioxx.tfc.api.SkillsManager;
import com.bioxx.tfc.api.SkillsManager.Skill;

public class GuiSkills extends GuiContainerTFC
{
	public static ResourceLocation texture = new ResourceLocation(Reference.MOD_ID, Reference.ASSET_PATH_GUI + "gui_skills.png");
	protected EntityPlayer player;
	private int skillsPage;
	private static final int SKILLS_PER_PAGE = 9;

	public GuiSkills(EntityPlayer player)
	{
		super(new ContainerSkills(player), 176, 166);
		this.setDrawInventory(false);
		this.player = player;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		fontRendererObj.drawString(TFC_Core.translate("gui.skillpage"), this.xSize / 2 - fontRendererObj.getStringWidth(TFC_Core.translate("gui.skillpage")) / 2, 4, 4210752, false);
		SkillStats ss = TFC_Core.getSkillStats(player);
		int y = 10;
		int count = -1;
		for (Skill o : SkillsManager.instance.getSkillsArray())
		{
			count++;
			if (count < (SKILLS_PER_PAGE * skillsPage) + SKILLS_PER_PAGE && count >= (SKILLS_PER_PAGE * skillsPage))
			{
				bindTexture(texture);
				drawTexturedModalRect(4, y, 4, 208, 168, 16);
				y += 12;
				float perc = ss.getPercToNextRank(o.skillName);
				drawTexturedModalRect(4, y, 4, 168, 168, 4);
				drawTexturedModalRect(4, y, 4, 172, (int) Math.floor(168 * perc), 4);

				fontRendererObj.drawString(TFC_Core.translate(o.skillName) + ": " + EnumChatFormatting.DARK_BLUE + ss.getSkillRank(o.skillName).getLocalizedName(), 6, y - 9, 0, false);
				y += 3;
			}
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
		guiTop = (height - ySize) / 2 - 3; //Shifted 3 pixels up to match other inventory tabs
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize); //No inventory drawn, so shifted ySize is not necessary
	}

	@Override
	public void initGui()
	{
		super.initGui();
		createButtons();
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
		else if (guibutton.id == 4)
		{
			if (skillsPage > 0)
				skillsPage--;
		}
		else if (guibutton.id == 5)
		{
			if (9 + (skillsPage * SKILLS_PER_PAGE) < SkillsManager.instance.getSkillsArray().size())
				skillsPage++;
		}
	}

	@Override
	public void updateScreen()
	{
		super.updateScreen();
		if (skillsPage == 0)
			((GuiButton) buttonList.get(4)).enabled = false;
		else
			((GuiButton) buttonList.get(4)).enabled = true;

		if (9 + (skillsPage * SKILLS_PER_PAGE) < SkillsManager.instance.getSkillsArray().size())
			((GuiButton) buttonList.get(5)).enabled = true;
		else
			((GuiButton) buttonList.get(5)).enabled = false;
	}

	public void createButtons()
	{
		this.guiLeft = (this.width - this.xSize) / 2;
		this.guiTop = (this.height - this.ySize) / 2;
		buttonList.clear();
		buttonList.add(new GuiInventoryButton(0, guiLeft + 176, guiTop, 25, 20, 0, 86, 25, 20, TFC_Core.translate("gui.Inventory.Inventory"), TFC_Textures.guiInventory));
		buttonList.add(new GuiInventoryButton(1, guiLeft + 176, guiTop + 19, 25, 20, 0, 86, 25, 20, TFC_Core.translate("gui.Inventory.Skills"), TFC_Textures.guiSkills));
		buttonList.add(new GuiInventoryButton(2, guiLeft + 176, guiTop + 38, 25, 20, 0, 86, 25, 20, TFC_Core.translate("gui.Calendar.Calendar"), TFC_Textures.guiCalendar));
		buttonList.add(new GuiInventoryButton(3, guiLeft + 176, guiTop + 57, 25, 20, 0, 86, 25, 20, TFC_Core.translate("gui.Inventory.Health"), TFC_Textures.guiHealth));
		buttonList.add(new GuiButtonPage(4, guiLeft + 4, guiTop + 144, 30, 15, 0, 177));
		buttonList.add(new GuiButtonPage(5, guiLeft + 142, guiTop + 144, 30, 15, 0, 192));
	}

	public class GuiButtonPage extends GuiButton
	{
		private int u, v;

		public GuiButtonPage(int id, int xPos, int yPos, int xSize, int ySize, int u, int v)
		{
			super(id, xPos, yPos, xSize, ySize, "");
			this.u = u;
			this.v = v;
		}

		@Override
		public void drawButton(Minecraft par1Minecraft, int xPos, int yPos)
		{
			if (this.visible)
			{
				bindTexture(texture);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				this.field_146123_n = xPos >= this.xPosition && yPos >= this.yPosition && xPos < this.xPosition + this.width && yPos < this.yPosition + this.height;
				int k = this.getHoverState(this.field_146123_n) - 1;
				this.drawTexturedModalRect(this.xPosition, this.yPosition, u + 30 * k, v, this.width, this.height);
				//this.drawTexturedModalRect(this.xPosition + this.width / 2, this.yPosition, 200 - this.width / 2, 46 + k * 20, this.width / 2, this.height);
				this.mouseDragged(par1Minecraft, xPos, yPos);

				//this.drawCenteredString(fontrenderer,  barrel.mode==0?TFC_Core.translate("gui.Barrel.ToggleOn"):TFC_Core.translate("gui.Barrel.ToggleOff"), this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, l);
			}
		}
	}
}
