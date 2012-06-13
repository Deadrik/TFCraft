package net.minecraft.src.TFC_Core.GUI;

import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import net.minecraft.src.*;
import net.minecraft.src.TFC_Core.TFCSeasons;
import net.minecraft.src.TFC_Core.General.TFCSettings;

public class GuiCalendar extends GuiScreen
{
    World world;

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

    public GuiCalendar(World world, int i, int j, int k)
    {
        this.world = world;
        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;
    }

    public void onGuiClosed()
    {
        super.onGuiClosed();
    }

    public void initGui()
    {
        super.initGui();

        if(TFCSettings.enableDebugMode)
        {
            controlList.clear();
            int l = (width - xSize) / 2;
            int i1 = (height - ySize) / 2;
            controlList.add(new GuiButton(0, l+20, i1 + 80, 66, 20, "1 Day"));
            controlList.add(new GuiButton(1, l+20, i1 + 99, 66, 20, "1 Week"));
            controlList.add(new GuiButton(2, l+20, i1 + 118, 66, 20, "1 Month"));
            controlList.add(new GuiButton(3, l+20, i1 + 137, 66, 20, "1 Year"));
        }

    }

    @Override
    public void drawScreen(int par1, int par2, float par3)
    {
        int k = mc.renderEngine.getTexture("/bioxx/CalendarGui.png");

        int var4 = this.guiLeft;
        int var5 = this.guiTop;

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(k);
        int l = (width - xSize) / 2;
        int i1 = (height - ySize) / 2;
        drawTexturedModalRect(l, i1, 0, 0, xSize, ySize);

        drawCenteredString(fontRenderer,"Calendar", l+87, i1+16, 0xFFFFFF);
        drawCenteredString(fontRenderer,"Season : " + TFCSeasons.seasons[TFCSeasons.getMonth()], l + 87, i1+26, 0x000000);
        drawCenteredString(fontRenderer,"Day : " + TFCSeasons.Days[TFCSeasons.getDayOfWeek()], l + 87, i1+36, 0x000000);
        drawCenteredString(fontRenderer,"Date : " + TFCSeasons.getDayOfMonth() + " " + TFCSeasons.months[TFCSeasons.getMonth()] + ", " +(1000+TFCSeasons.getYear()), l + 87, i1+46, 0x000000);
        //drawCenteredString(fontRenderer,"Month : " + , l + 87, i1+36, 0x000000);


        long h = TFCSeasons.getHour();
        String hour = "";
        if(h == 0)
            hour = "The Witching Hour";
        else
            hour+=h;
        drawCenteredString(fontRenderer,"Hour : " + hour, l + 87, i1+66, 0x000000);
        
        for (int var6 = 0; var6 < this.controlList.size(); ++var6)
        {
            GuiButton var7 = (GuiButton)this.controlList.get(var6);
            var7.drawButton(this.mc, par1, par2);
        }


    }

    public boolean doesGuiPauseGame()
    {
        return false;
    }

    public void drawCenteredString(FontRenderer fontrenderer, String s, int i, int j, int k)
    {
        fontrenderer.drawString(s, i - fontrenderer.getStringWidth(s) / 2, j, k);
    }

    protected void actionPerformed(GuiButton guibutton)
    {
        if(!world.isRemote)
        {
            if (guibutton.id == 0)
            {
                this.world.getWorldInfo().setWorldTime(TFCSeasons.getTotalTicks() + 36000);
            }
            else if (guibutton.id == 1)
            {
                this.world.getWorldInfo().setWorldTime(TFCSeasons.getTotalTicks() + 252000);
            }
            else if (guibutton.id == 2)
            {
                this.world.getWorldInfo().setWorldTime(TFCSeasons.getTotalTicks() + 1080000);
            }
            else if (guibutton.id == 3)
            {
                this.world.getWorldInfo().setWorldTime(TFCSeasons.getTotalTicks() + 12960000);
            }

        }
    }
}
