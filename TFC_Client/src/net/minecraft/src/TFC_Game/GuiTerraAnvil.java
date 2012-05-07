package net.minecraft.src.TFC_Game;

import net.minecraft.src.*;
import net.minecraft.src.TFC_Core.GuiContainerTFC;
import net.minecraft.src.TFC_Core.General.CraftingRule;
import net.minecraft.src.TFC_Game.*;

import org.lwjgl.opengl.GL11;


public class GuiTerraAnvil extends GuiContainerTFC
{
    private TileEntityTerraAnvil AnvilEntity;


    public GuiTerraAnvil(InventoryPlayer inventoryplayer, TileEntityTerraAnvil tileentityanvil)
    {
        super(new ContainerTerraAnvil(inventoryplayer,tileentityanvil) );
        AnvilEntity = tileentityanvil;

    }

    public void initGui()
    {
        super.initGui();
        guiLeft = (width - 208) / 2;
        guiTop = (height - 198) / 2;

        controlList.clear();

        controlList.add(new GuiButton(0, guiLeft+5, guiTop + 5, 66, 20, "\2474Light Hit"));
        controlList.add(new GuiButton(1, guiLeft+5, guiTop + 24, 66, 20, "\2474Heavy Hit"));
        controlList.add(new GuiButton(2, guiLeft+5, guiTop + 43, 66, 20, "\2474Draw"));
        controlList.add(new GuiButton(3, guiLeft+5, guiTop + 62, 66, 20, "\2474Quench"));

        controlList.add(new GuiButton(4, guiLeft+137, guiTop + 5, 66, 20, "\2472Punch"));
        controlList.add(new GuiButton(5, guiLeft+137, guiTop + 24, 66, 20, "\2472Bend"));
        controlList.add(new GuiButton(6, guiLeft+137, guiTop + 43, 66, 20, "\2472Upset"));
        controlList.add(new GuiButton(7, guiLeft+137, guiTop + 62, 66, 20, "\2472Shrink"));

        controlList.add(new GuiButton(8, guiLeft+86, guiTop + 74, 36, 20, "\2474Weld"));

    }

    protected void actionPerformed(GuiButton guibutton)
    {
        if(true)
        {
            if (guibutton.id == 0)
            {
                AnvilEntity.actionLightHammer();
            }
            else if (guibutton.id == 1)
            {
                AnvilEntity.actionHeavyHammer();
            }
            else if (guibutton.id == 2)
            {
                AnvilEntity.actionDraw();
            }
            else if (guibutton.id == 3)
            {
                AnvilEntity.actionQuench();
            }
            else if (guibutton.id == 4)
            {
                AnvilEntity.actionPunch();
            }
            else if (guibutton.id == 5)
            {
                AnvilEntity.actionBend();
            }
            else if (guibutton.id == 6)
            {
                AnvilEntity.actionUpset();
            }
            else if (guibutton.id == 7)
            {
                AnvilEntity.actionShrink();
            }
            else if (guibutton.id == 8)
            {
                AnvilEntity.actionWeld();
            }
            this.inventorySlots.updateCraftingResults();
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
    {
        int r = mc.renderEngine.getTexture("/bioxx/anvilgui.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.5F);
        mc.renderEngine.bindTexture(r);
        int w = (width - 208) / 2;
        int h = (height - 198) / 2;
        drawTexturedModalRect(w, h, 0, 0, 208, 198);

        int i1 = AnvilEntity.workRecipe!= null ? AnvilEntity.workRecipe.getCraftingValue() : 0;
        //(guiX,guiY,sourceMinX,sourceMinY,sourceMaxX,sourceMaxY
        drawTexturedModalRect(w + 76 + i1, h + 103, 213, 10, 5, 5);

        i1 = AnvilEntity.getItemCraftingValue();
        drawTexturedModalRect(w + 76 + i1, h + 108, 208, 10, 5, 6);

        drawRules(w,h);

    }

    public void drawRules(int w, int h)
    {
        fontRenderer.drawString("Rules:", w + 209, h+30-8, 0x404040);
        if(AnvilEntity.workRecipe != null)
        {
            CraftingRule[] Rules = AnvilEntity.workRecipe.getRules();
            for(int i = 0; i < 3; i++)
            {
                int yOffset = 8 * i;
                String s = "\u2022";			

                if(i == 0)
                {
                    if(Rules[0] != null)
                        s += "\2472";
                    else
                        s += "\2474";

                    s += Rules[0].Name;

                }
                else if(i == 1)
                {
                    if(Rules[1] != null)
                        s += "\2472";
                    else
                        s += "\2474";

                    s += Rules[1].Name;
                }
                else if(i == 2)
                {
                    if(Rules[2] != null)
                        s += "\2472";
                    else
                        s += "\2474";

                    s += Rules[2].Name;
                }
                fontRenderer.drawString(s, w + 209, h+30+yOffset, 0x404040);
            }
        }
    }

    protected void drawGuiContainerForegroundLayer()
    {
        ((GuiButton)controlList.get(3)).enabled = false;
    }

    private boolean getIsMouseOverSlot(Slot slot, int i, int j)
    {
        int k = guiLeft;
        int l = guiTop;
        i -= k;
        j -= l;
        return i >= slot.xDisplayPosition - 1 && i < slot.xDisplayPosition + 16 + 1 && j >= slot.yDisplayPosition - 1 && j < slot.yDisplayPosition + 16 + 1;
    }

    public void drawCenteredString(FontRenderer fontrenderer, String s, int i, int j, int k)
    {
        fontrenderer.drawString(s, i - fontrenderer.getStringWidth(s) / 2, j, k);
    }


}
