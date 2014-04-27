package TFC.GUI;

import TFC.Containers.ContainerHorseInventoryTFC;
import TFC.Core.Util.StringUtil;
import TFC.GUI.GuiBarrel.GuiButtonMode;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.inventory.ContainerHorseInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GuiScreenHorseInventoryTFC extends GuiContainer
{
    private static final ResourceLocation horseGuiTextures = new ResourceLocation("textures/gui/container/horse.png");
    private IInventory field_110413_u;
    private IInventory field_110412_v;
    private EntityHorse horse;
    private float field_110416_x;
    private float field_110415_y;

    public GuiScreenHorseInventoryTFC(IInventory par1IInventory, IInventory par2IInventory, EntityHorse par3EntityHorse)
    {
        super(new ContainerHorseInventoryTFC(par1IInventory, par2IInventory, par3EntityHorse));
        this.field_110413_u = par1IInventory;
        this.field_110412_v = par2IInventory;
        this.horse = par3EntityHorse;
        this.allowUserInput = false;
    }
    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        this.fontRenderer.drawString(this.field_110412_v.isInvNameLocalized() ? this.field_110412_v.getInvName() : I18n.getString(this.field_110412_v.getInvName()), 8, 6, 4210752);
        this.fontRenderer.drawString(this.field_110413_u.isInvNameLocalized() ? this.field_110413_u.getInvName() : I18n.getString(this.field_110413_u.getInvName()), 8, this.ySize - 96 + 2, 4210752);
    }

    /**
     * Draw the background layer for the GuiContainer (everything behind the items)
     */
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(horseGuiTextures);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);

        if (this.horse.isChested())
        {
            this.drawTexturedModalRect(k + 79, l + 17, 0, this.ySize, 90, 54);
        }

        if (this.horse.func_110259_cr())
        {
            this.drawTexturedModalRect(k + 7, l + 35, 0, this.ySize + 54, 18, 18);
        }

        GuiInventory.func_110423_a(k + 51, l + 60, 17, (float)(k + 51) - this.field_110416_x, (float)(l + 75 - 50) - this.field_110415_y, this.horse);
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        this.field_110416_x = (float)par1;
        this.field_110415_y = (float)par2;
        super.drawScreen(par1, par2, par3);
    }
}
