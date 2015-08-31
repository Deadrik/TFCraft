package com.bioxx.tfc.GUI;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Textures;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.api.TFCOptions;

public class GuiCalendar extends GuiScreen
{
	private World world;
	private EntityPlayer player;

	private static final ResourceLocation TEXTURE = new ResourceLocation("terrafirmacraft:textures/gui/gui_calendar.png");

	/** The X size of the inventory window in pixels. */
	protected int xSize = 176;

	/** The Y size of the inventory window in pixels. */
	protected int ySize = 184;
	/**
	 * Starting X position for the Gui. Inconsistent use for Gui backgrounds.
	 */
	protected int guiLeft;

	/**
	 * Starting Y position for the Gui. Inconsistent use for Gui backgrounds.
	 */
	protected int guiTop;

	public GuiCalendar(EntityPlayer p)
	{
		this.world = p.worldObj;
		this.guiLeft = (this.width - this.xSize) / 2;
		this.guiTop = (this.height - this.ySize) / 2;
		player = p;
	}

	/*@Override
	public void onGuiClosed()
	{
		super.onGuiClosed();
	}*/

	@Override
	public void initGui()
	{
		super.initGui();
		buttonList.clear();
		guiLeft = (width - xSize) / 2;
		guiTop = (height - ySize) / 2;
		if(TFCOptions.enableDebugMode)
		{
			buttonList.add(new GuiButton(0, guiLeft+20, guiTop + 118, 66, 20, TFC_Core.translate("gui.Calendar.1Hour")));
			buttonList.add(new GuiButton(1, guiLeft+20, guiTop + 137, 66, 20, TFC_Core.translate("gui.Calendar.1Day")));
			buttonList.add(new GuiButton(2, guiLeft + 85, guiTop + 118, 66, 20, TFC_Core.translate("gui.Calendar.1Month")));
			buttonList.add(new GuiButton(3, guiLeft + 85, guiTop + 137, 66, 20, TFC_Core.translate("gui.Calendar.1Year")));
		}

		buttonList.add(new GuiInventoryButton(4, guiLeft + 176, guiTop + 9, 25, 20, 
				0, 86, 25, 20, TFC_Core.translate("gui.Inventory.Inventory"), TFC_Textures.guiInventory));
		buttonList.add(new GuiInventoryButton(5, guiLeft + 176, guiTop + 28, 25, 20, 
				0, 86, 25, 20, TFC_Core.translate("gui.Inventory.Skills"), TFC_Textures.guiSkills));
		buttonList.add(new GuiInventoryButton(6, guiLeft + 176, guiTop + 47, 25, 20, 
				0, 86, 25, 20, TFC_Core.translate("gui.Calendar.Calendar"), TFC_Textures.guiCalendar));
		buttonList.add(new GuiInventoryButton(7, guiLeft + 176, guiTop + 66, 25, 20, 
				0, 86, 25, 20, TFC_Core.translate("gui.Inventory.Health"), TFC_Textures.guiHealth));
	}

	@Override
	public void drawScreen(int par1, int par2, float par3)
	{

		drawWorldBackground(0);

		TFC_Core.bindTexture(TEXTURE);

		//int var4 = this.guiLeft;
		//int var5 = this.guiTop+6;

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		int l = (width - xSize) / 2;
		int i1 = (height - ySize) / 2;
		drawTexturedModalRect(l, i1+6, 0, 0, xSize, ySize);

		drawCenteredString(fontRendererObj,TFC_Core.translate("gui.Calendar.Calendar"), l+87, i1+16, 0xFFFFFF);
		drawCenteredString(fontRendererObj,TFC_Core.translate("gui.Calendar.Season") + " : " + TFC_Time.SEASONS[TFC_Time.getSeasonAdjustedMonth((int)(player.posZ))], l + 87, i1+26, 0x000000);

		int dom = TFC_Time.getDayOfMonth();
		int month = TFC_Time.currentMonth;
		String day = TFC_Time.DAYS[TFC_Time.getDayOfWeek()];

		if (month == 3 && dom == 18)
			day = TFC_Core.translate("gui.Calendar.DateKitty");
		else if(month == 4 && dom == 7)
			day = TFC_Core.translate("gui.Calendar.DateBioxx");
		else if(month == 8 && dom == 2)
			day = TFC_Core.translate("gui.Calendar.DateDunk");

		drawCenteredString(fontRendererObj, TFC_Core.translate("gui.Calendar.Day") + " : " + day, l + 87, i1 + 36, 0x000000);

		int year = 1000 + TFC_Time.getYear();
        // year changes at January, not March
		if (month >= TFC_Time.JANUARY) {
		    year += 1;
		}
        drawCenteredString(fontRendererObj, TFC_Core.translate("gui.Calendar.Date") + " : " + dom + " " + TFC_Time.MONTHS[month] + ", " + year, l + 87, i1 + 46, 0x000000);

		//float temp = Math.round((TFC_Climate.getHeightAdjustedTemp(player.worldObj, (int) player.posX, (int) player.posY, (int) player.posZ)));

		//drawCenteredString(fontRenderer,"Temperature : " + (int)temp + "C", l + 87, i1+56, 0x000000);
		//drawCenteredString(fontRenderer,"Month : " + , l + 87, i1+36, 0x000000);


		long h = TFC_Time.getHour();
		String hour = "";
		if(h == 0)
			hour = TFC_Core.translate("gui.Calendar.WitchHour");
		else
			hour+=h;
		drawCenteredString(fontRendererObj,TFC_Core.translate("gui.Calendar.Hour") + " : " + hour, l + 87, i1+56, 0x000000);
		//drawCenteredString(fontRenderer,"EVT : " + ((TFCWorldChunkManager)world.provider.worldChunkMgr).getEVTLayerAt((int) player.posX, (int) player.posZ).floatdata1, l + 87, i1+76, 0x000000);

		//int rain = (int) TFC_Climate.getRainfall((int) player.posX,(int) player.posY, (int) player.posZ);
		//drawCenteredString(fontRenderer,"Rain : " + rain, l + 87, i1+86, 0x000000);

		for (int var6 = 0; var6 < this.buttonList.size(); ++var6)
		{
			GuiButton var7 = (GuiButton)this.buttonList.get(var6);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			var7.drawButton(this.mc, par1, par2);
		}
	}

	@Override
	public boolean doesGuiPauseGame()
	{
		return false;
	}

	@Override
	protected void keyTyped(char par1, int par2)
	{
		if (par2 == 1 || par2 == this.mc.gameSettings.keyBindInventory.getKeyCode())
			this.mc.thePlayer.closeScreen();
	}

	@Override
	public void drawCenteredString(FontRenderer fontrenderer, String s, int i, int j, int k)
	{
		fontrenderer.drawString(s, i - fontrenderer.getStringWidth(s) / 2, j, k);
	}

	@Override
	protected void actionPerformed(GuiButton guibutton)
	{
		if(world.isRemote)
			if (guibutton.id == 0)
				this.mc.thePlayer.sendChatMessage("/time add " + (TFC_Time.DAY_LENGTH/24));
			else if (guibutton.id == 1)
				this.mc.thePlayer.sendChatMessage("/time add " + (TFC_Time.DAY_LENGTH));
			else if (guibutton.id == 2)
				this.mc.thePlayer.sendChatMessage("/time add " + (TFC_Time.DAY_LENGTH * TFC_Time.daysInMonth));
			else if (guibutton.id == 3)
				this.mc.thePlayer.sendChatMessage("/time add " + (TFC_Time.DAY_LENGTH * TFC_Time.daysInYear));
			else if (guibutton.id == 4)
				this.mc.displayGuiScreen(new GuiInventoryTFC(Minecraft.getMinecraft().thePlayer));
			else if (guibutton.id == 5)
				Minecraft.getMinecraft().displayGuiScreen(new GuiSkills(Minecraft.getMinecraft().thePlayer));
			else if (guibutton.id == 7)
				Minecraft.getMinecraft().displayGuiScreen(new GuiHealth(Minecraft.getMinecraft().thePlayer));
	}
}
