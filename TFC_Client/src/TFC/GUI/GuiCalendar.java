package TFC.GUI;

import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import TFC.Core.TFCHeat;
import TFC.Core.TFCSeasons;
import TFC.Core.TFCSettings;

import net.minecraft.src.*;

public class GuiCalendar extends GuiScreen
{
    World world;
    int x;
    int z;
    EntityPlayer player;

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
            controlList.add(new GuiButton(0, l+20, i1 + 80, 66, 20, "1 Hour"));
            controlList.add(new GuiButton(1, l+20, i1 + 99, 66, 20, "1 Day"));
            controlList.add(new GuiButton(2, l+20, i1 + 118, 66, 20, "1 Week"));
            controlList.add(new GuiButton(3, l+20, i1 + 137, 66, 20, "1 Month"));
            controlList.add(new GuiButton(4, l+20, i1 + 156, 66, 20, "1 Year"));
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
        int dom = TFCSeasons.getDayOfMonth();
        int month = TFCSeasons.currentMonth;
        if(dom == 6 && month == 6)
            drawCenteredString(fontRenderer,"Date : Bioxx's Birthday!, " +(1000+TFCSeasons.getYear()), l + 87, i1+46, 0x000000);
        else
            drawCenteredString(fontRenderer,"Date : " + dom + " " + TFCSeasons.months[month] + ", " +(1000+TFCSeasons.getYear()), l + 87, i1+46, 0x000000);
        drawCenteredString(fontRenderer,"Temperature : " + ((int)TFCHeat.getNormalizedTemp(world.getBiomeGenForCoords((int)player.posX, (int)player.posZ).getHeightAdjustedTemperature((int)player.posY))) + "C", l + 87, i1+56, 0x000000);
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
                this.world.getWorldInfo().setWorldTime(TFCSeasons.getTotalTicks() + 1000);
            }
            else if (guibutton.id == 1)
            {
                this.world.getWorldInfo().setWorldTime(TFCSeasons.getTotalTicks() + 24000);
            }
            else if (guibutton.id == 2)
            {
                this.world.getWorldInfo().setWorldTime(TFCSeasons.getTotalTicks() + 168000);
            }
            else if (guibutton.id == 3)
            {
                this.world.getWorldInfo().setWorldTime(TFCSeasons.getTotalTicks() + 720000);
            }
            else if (guibutton.id == 4)
            {
                this.world.getWorldInfo().setWorldTime(TFCSeasons.getTotalTicks() + 8640000);
            }

        }
    }
}
