package TFC.GUI;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.src.ModLoader;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import TFC.API.TFCOptions;
import TFC.Core.TFC_Climate;
import TFC.Core.TFC_Core;
import TFC.Core.TFC_Time;
import TFC.Core.Util.StringUtil;

public class GuiCalendar extends GuiScreen
{
	World world;
	int x;
	int z;
	EntityPlayer player;

	private static final ResourceLocation texture = new ResourceLocation("terrafirmacraft:textures/gui/gui_calendar.png");

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

	public GuiCalendar(EntityPlayer p, World world, int i, int j, int k)
	{
		this.world = world;
		this.guiLeft = (this.width - this.xSize) / 2;
		this.guiTop = (this.height - this.ySize) / 2;
		x = i;
		z = k;
		player = p;
	}

	@Override
	public void onGuiClosed()
	{
		super.onGuiClosed();
	}

	@Override
	public void initGui()
	{
		super.initGui();

		if(TFCOptions.enableDebugMode)
		{
			buttonList.clear();
			int l = (width - xSize) / 2;
			int i1 = (height - ySize) / 2;
			buttonList.add(new GuiButton(0, l+20, i1 + 118, 66, 20, StringUtil.localize("gui.Calendar.1Hour")));
			buttonList.add(new GuiButton(1, l+20, i1 + 137, 66, 20, StringUtil.localize("gui.Calendar.1Day")));
			buttonList.add(new GuiButton(2, l+20, i1 + 156, 66, 20, StringUtil.localize("gui.Calendar.1Week")));
			buttonList.add(new GuiButton(3, l+85, i1 + 118, 66, 20, StringUtil.localize("gui.Calendar.1Month")));
			buttonList.add(new GuiButton(4, l+85, i1 + 137, 66, 20, StringUtil.localize("gui.Calendar.1Year")));
		}

	}

	@Override
	public void drawScreen(int par1, int par2, float par3)
	{
		TFC_Core.bindTexture(texture);

		int var4 = this.guiLeft;
		int var5 = this.guiTop;

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		int l = (width - xSize) / 2;
		int i1 = (height - ySize) / 2;
		drawTexturedModalRect(l, i1, 0, 0, xSize, ySize);

		drawCenteredString(fontRenderer,StringUtil.localize("gui.Calendar.Calendar"), l+87, i1+16, 0xFFFFFF);
		drawCenteredString(fontRenderer,StringUtil.localize("gui.Calendar.Season") + " : " + TFC_Time.seasons[TFC_Time.getMonth()], l + 87, i1+26, 0x000000);

		drawCenteredString(fontRenderer,StringUtil.localize("gui.Calendar.Day") + " : " + TFC_Time.Days[TFC_Time.getDayOfWeek()], l + 87, i1+36, 0x000000);
		int dom = TFC_Time.getDayOfMonth();
		int month = TFC_Time.currentMonth;

		if(dom == 7 && month == 4) {
			drawCenteredString(fontRenderer,StringUtil.localize("gui.Calendar.DateBioxx") + ", " +(1000+TFC_Time.getYear()), l + 87, i1+46, 0x000000);
		} else {
			drawCenteredString(fontRenderer,StringUtil.localize("gui.Calendar.Date") + " : " + dom + " " + TFC_Time.months[month] + ", " +(1000+TFC_Time.getYear()), l + 87, i1+46, 0x000000);
		}

		float temp = Math.round((TFC_Climate.getHeightAdjustedTemp((int) player.posX, (int) player.posY, (int) player.posZ)));

		//drawCenteredString(fontRenderer,"Temperature : " + (int)temp + "C", l + 87, i1+56, 0x000000);
		//drawCenteredString(fontRenderer,"Month : " + , l + 87, i1+36, 0x000000);


		long h = TFC_Time.getHour();
		String hour = "";
		if(h == 0) {
			hour = StringUtil.localize("gui.Calendar.WitchHour");
		} else {
			hour+=h;
		}
		drawCenteredString(fontRenderer,StringUtil.localize("gui.Calendar.Hour") + " : " + hour, l + 87, i1+56, 0x000000);
		//drawCenteredString(fontRenderer,"EVT : " + ((TFCWorldChunkManager)world.provider.worldChunkMgr).getEVTLayerAt((int) player.posX, (int) player.posZ).floatdata1, l + 87, i1+76, 0x000000);

		//int rain = (int) TFC_Climate.getRainfall((int) player.posX,(int) player.posY, (int) player.posZ);
		//drawCenteredString(fontRenderer,"Rain : " + rain, l + 87, i1+86, 0x000000);

		for (int var6 = 0; var6 < this.buttonList.size(); ++var6)
		{
			GuiButton var7 = (GuiButton)this.buttonList.get(var6);
			var7.drawButton(this.mc, par1, par2);
		}


	}

	@Override
	public boolean doesGuiPauseGame()
	{
		return false;
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
		{
			if (guibutton.id == 0)
			{
				ModLoader.getMinecraftInstance().thePlayer.sendChatMessage("/time add " + (TFC_Time.dayLength/24));
			}
			else if (guibutton.id == 1)
			{
				ModLoader.getMinecraftInstance().thePlayer.sendChatMessage("/time add " + (TFC_Time.dayLength));
			}
			else if (guibutton.id == 2)
			{
				ModLoader.getMinecraftInstance().thePlayer.sendChatMessage("/time add " + (TFC_Time.dayLength*7));
			}
			else if (guibutton.id == 3)
			{
				ModLoader.getMinecraftInstance().thePlayer.sendChatMessage("/time add " + (TFC_Time.dayLength * TFC_Time.daysInMonth));
			}
			else if (guibutton.id == 4)
			{
				ModLoader.getMinecraftInstance().thePlayer.sendChatMessage("/time add " + (TFC_Time.dayLength * TFC_Time.daysInYear));
			}

		}
	}
}
