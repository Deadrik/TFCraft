package vazkii.um;

import net.minecraft.client.Minecraft;
import net.minecraft.src.GuiButton;
import net.minecraft.src.ModLoader;
import net.minecraft.src.Tessellator;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

/**
 * Apologies for having rewritten the GuiSlot class, but if I had extended it it would have placed a
 * scroll bar in the middle of the GUI, so I had to do it like this.
 * @author Vazkii
 */
public class GuiSlotChangelog {

    private final Minecraft mc;
    private final int width;
    private final int height;
    protected final int top;
    protected final int bottom;
    private final int right;
    private final int left;
    protected final int slotHeight;
    private int scrollUpButtonID;
    private int scrollDownButtonID;
    protected int mouseX;
    protected int mouseY;
    private float initialClickY = -2.0F;
    private float scrollMultiplier;
    private float amountScrolled;
    private int selectedElement = -1;
    private long lastClicked = 0L;
    private boolean field_25123_p = true;
    private boolean field_27262_q;
    private int field_27261_r;

	
	GuiChangelog parentGui;
	
	public GuiSlotChangelog(GuiChangelog parent) {
		parentGui = parent;
		mc = ModLoader.getMinecraftInstance();
		width = parent.width;
		height = parent.height;
		top = 32;
		bottom = height-16;
		slotHeight = 12;
		left = 0;
		right = width;
	}

    public void func_27258_a(boolean par1)
    {
        this.field_25123_p = par1;
    }

    protected void func_27259_a(boolean par1, int par2)
    {
        this.field_27262_q = par1;
        this.field_27261_r = par2;

        if (!par1)
        {
            this.field_27261_r = 0;
        }
    }
    
    public int func_27256_c(int par1, int par2)
    {
        int var3 = this.width / 2 - 110;
        int var4 = this.width / 2 + 110;
        int var5 = par2 - this.top - this.field_27261_r + (int)this.amountScrolled - 4;
        int var6 = var5 / this.slotHeight;
        return par1 >= var3 && par1 <= var4 && var6 >= 0 && var5 >= 0 && var6 < this.getSize() ? var6 : -1;
    }
    
    private void bindAmountScrolled()
    {
        int var1 = this.getContentHeight() - (this.bottom - this.top - 4);

        if (var1 < 0)
        {
            var1 /= 2;
        }

        if (this.amountScrolled < 0.0F)
        {
            this.amountScrolled = 0.0F;
        }

        if (this.amountScrolled > (float)var1)
        {
            this.amountScrolled = (float)var1;
        }
    }

    public void actionPerformed(GuiButton par1GuiButton)
    {
        if (par1GuiButton.enabled)
        {
            if (par1GuiButton.id == this.scrollUpButtonID)
            {
                this.amountScrolled -= (float)(this.slotHeight * 2 / 3);
                this.initialClickY = -2.0F;
                this.bindAmountScrolled();
            }
            else if (par1GuiButton.id == this.scrollDownButtonID)
            {
                this.amountScrolled += (float)(this.slotHeight * 2 / 3);
                this.initialClickY = -2.0F;
                this.bindAmountScrolled();
            }
        }
    }
    
    public void drawScreen(int par1, int par2, float par3)
    {
        this.mouseX = par1;
        this.mouseY = par2;
        this.drawBackground();
        int var4 = this.getSize();
        int var5 = this.width-7;
        int var6 = var5 + 6;
        int var9;
        int var10;
        int var11;
        int var13;
        int var19;

        if (Mouse.isButtonDown(0))
        {
            if (this.initialClickY == -1.0F)
            {
                boolean var7 = true;
                if (par2 >= this.top && par2 <= this.bottom)
                {
                    int var8 = this.width / 2 - 110;
                    var9 = this.width / 2 + 110;
                    var10 = par2 - this.top - this.field_27261_r + (int)this.amountScrolled - 4;
                    var11 = var10 / this.slotHeight;
                    if (par1 >= var8 && par1 <= var9 && var11 >= 0 && var10 >= 0 && var11 < var4)
                    {
                        boolean var12 = var11 == this.selectedElement && System.currentTimeMillis() - this.lastClicked < 250L;
                        this.elementClicked(var11, var12);
                        this.selectedElement = var11;
                        this.lastClicked = System.currentTimeMillis();
                    }
                    else if (par1 >= var8 && par1 <= var9 && var10 < 0)
                        var7 = false;
                    if (par1 >= var5 && par1 <= var6)
                    {
                        this.scrollMultiplier = -1F;
                        var19 = this.getContentHeight() - (this.bottom - this.top - 4);
                        if (var19 < 1)
                            var19 = 1;
                        var13 = (int)((float)((this.bottom - this.top) * (this.bottom - this.top)) / (float)this.getContentHeight());
                        if (var13 < 32)
                            var13 = 32;
                        if (var13 > this.bottom - this.top - 8)
                            var13 = this.bottom - this.top - 8;
                        this.scrollMultiplier /= (float)(this.bottom - this.top - var13) / (float)var19;
                    }
                    else
                        this.scrollMultiplier = 1F;
                    if (var7)
                        this.initialClickY = (float)par2;
                    else
                        this.initialClickY = -2.0F;
                }
                else
                    this.initialClickY = -2.0F;
            }
            else if (this.initialClickY >= 0.0F)
            {
                this.amountScrolled -= ((float)par2 - this.initialClickY) * this.scrollMultiplier;
                this.initialClickY = (float)par2;
            }
        }
        else
        {
            while (Mouse.next())
            {
                int var16 = Mouse.getEventDWheel();

                if (var16 != 0)
                {
                    if (var16 > 0)
                        var16 = -3;
                    else if (var16 < 0)
                        var16 = 3;
                    this.amountScrolled += (float)(var16 * this.slotHeight / 2);
                }
            }
            this.initialClickY = -1.0F;
        }

        this.bindAmountScrolled();
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_FOG);
        Tessellator var18 = Tessellator.instance;
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mc.renderEngine.getTexture("/gui/background.png"));
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        float var17 = 32.0F;
        var18.startDrawingQuads();
        var18.setColorOpaque_I(2105376);
        var18.addVertexWithUV((double)this.left, (double)this.bottom, 0.0D, (double)((float)this.left / var17), (double)((float)(this.bottom + (int)this.amountScrolled) / var17));
        var18.addVertexWithUV((double)this.right, (double)this.bottom, 0.0D, (double)((float)this.right / var17), (double)((float)(this.bottom + (int)this.amountScrolled) / var17));
        var18.addVertexWithUV((double)this.right, (double)this.top, 0.0D, (double)((float)this.right / var17), (double)((float)(this.top + (int)this.amountScrolled) / var17));
        var18.addVertexWithUV((double)this.left, (double)this.top, 0.0D, (double)((float)this.left / var17), (double)((float)(this.top + (int)this.amountScrolled) / var17));
        var18.draw();
        var9 = this.width / 2 - 92 - 16;
        var10 = this.top + 4 - (int)this.amountScrolled;

        int var14;

        for (var11 = 0; var11 < var4; ++var11)
        {
            var19 = var10 + var11 * this.slotHeight + this.field_27261_r;
            var13 = this.slotHeight - 4;

            if (var19 <= this.bottom && var19 + var13 >= this.top)
            {
                if (this.field_25123_p && this.isSelected(var11))
                {
                    var14 = this.width / 2 - 110;
                    int var15 = this.width / 2 + 110;
                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                    GL11.glDisable(GL11.GL_TEXTURE_2D);
                    var18.startDrawingQuads();
                    var18.setColorOpaque_I(8421504);
                    var18.addVertexWithUV((double)var14, (double)(var19 + var13 + 2), 0.0D, 0.0D, 1.0D);
                    var18.addVertexWithUV((double)var15, (double)(var19 + var13 + 2), 0.0D, 1.0D, 1.0D);
                    var18.addVertexWithUV((double)var15, (double)(var19 - 2), 0.0D, 1.0D, 0.0D);
                    var18.addVertexWithUV((double)var14, (double)(var19 - 2), 0.0D, 0.0D, 0.0D);
                    var18.setColorOpaque_I(0);
                    var18.addVertexWithUV((double)(var14 + 1), (double)(var19 + var13 + 1), 0.0D, 0.0D, 1.0D);
                    var18.addVertexWithUV((double)(var15 - 1), (double)(var19 + var13 + 1), 0.0D, 1.0D, 1.0D);
                    var18.addVertexWithUV((double)(var15 - 1), (double)(var19 - 1), 0.0D, 1.0D, 0.0D);
                    var18.addVertexWithUV((double)(var14 + 1), (double)(var19 - 1), 0.0D, 0.0D, 0.0D);
                    var18.draw();
                    GL11.glEnable(GL11.GL_TEXTURE_2D);
                }

                this.drawSlot(var11, var9, var19, var13, var18);
            }
        }

        GL11.glDisable(GL11.GL_DEPTH_TEST);
        byte var20 = 4;
        this.overlayBackground(0, this.top, 255, 255);
        this.overlayBackground(this.bottom, this.height, 255, 255);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        var18.startDrawingQuads();
        var18.setColorRGBA_I(0, 0);
        var18.addVertexWithUV((double)this.left, (double)(this.top + var20), 0.0D, 0.0D, 1.0D);
        var18.addVertexWithUV((double)this.right, (double)(this.top + var20), 0.0D, 1.0D, 1.0D);
        var18.setColorRGBA_I(0, 255);
        var18.addVertexWithUV((double)this.right, (double)this.top, 0.0D, 1.0D, 0.0D);
        var18.addVertexWithUV((double)this.left, (double)this.top, 0.0D, 0.0D, 0.0D);
        var18.draw();
        var18.startDrawingQuads();
        var18.setColorRGBA_I(0, 255);
        var18.addVertexWithUV((double)this.left, (double)this.bottom, 0.0D, 0.0D, 1.0D);
        var18.addVertexWithUV((double)this.right, (double)this.bottom, 0.0D, 1.0D, 1.0D);
        var18.setColorRGBA_I(0, 0);
        var18.addVertexWithUV((double)this.right, (double)(this.bottom - var20), 0.0D, 1.0D, 0.0D);
        var18.addVertexWithUV((double)this.left, (double)(this.bottom - var20), 0.0D, 0.0D, 0.0D);
        var18.draw();
        var19 = this.getContentHeight() - (this.bottom - this.top - 4);

        if (var19 > 0)
        {
            var13 = (this.bottom - this.top) * (this.bottom - this.top) / this.getContentHeight();
            if (var13 < 32)
                var13 = 32;
            if (var13 > this.bottom - this.top - 8)
                var13 = this.bottom - this.top - 8;
            var14 = (int)this.amountScrolled * (this.bottom - this.top - var13) / var19 + this.top;
            if (var14 < this.top)
                var14 = this.top;
            var18.startDrawingQuads();
            var18.setColorRGBA_I(0, 255);
            var18.addVertexWithUV((double)var5, (double)this.bottom, 0.0D, 0.0D, 1.0D);
            var18.addVertexWithUV((double)var6, (double)this.bottom, 0.0D, 1.0D, 1.0D);
            var18.addVertexWithUV((double)var6, (double)this.top, 0.0D, 1.0D, 0.0D);
            var18.addVertexWithUV((double)var5, (double)this.top, 0.0D, 0.0D, 0.0D);
            var18.draw();
            var18.startDrawingQuads();
            var18.setColorRGBA_I(8421504, 255);
            var18.addVertexWithUV((double)var5, (double)(var14 + var13), 0.0D, 0.0D, 1.0D);
            var18.addVertexWithUV((double)var6, (double)(var14 + var13), 0.0D, 1.0D, 1.0D);
            var18.addVertexWithUV((double)var6, (double)var14, 0.0D, 1.0D, 0.0D);
            var18.addVertexWithUV((double)var5, (double)var14, 0.0D, 0.0D, 0.0D);
            var18.draw();
            var18.startDrawingQuads();
            var18.setColorRGBA_I(12632256, 255);
            var18.addVertexWithUV((double)var5, (double)(var14 + var13 - 1), 0.0D, 0.0D, 1.0D);
            var18.addVertexWithUV((double)(var6 - 1), (double)(var14 + var13 - 1), 0.0D, 1.0D, 1.0D);
            var18.addVertexWithUV((double)(var6 - 1), (double)var14, 0.0D, 1.0D, 0.0D);
            var18.addVertexWithUV((double)var5, (double)var14, 0.0D, 0.0D, 0.0D);
            var18.draw();
        }
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glShadeModel(GL11.GL_FLAT);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glDisable(GL11.GL_BLEND);
    }

    private void overlayBackground(int par1, int par2, int par3, int par4)
    {
        Tessellator var5 = Tessellator.instance;
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mc.renderEngine.getTexture("/gui/background.png"));
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        float var6 = 32.0F;
        var5.startDrawingQuads();
        var5.setColorRGBA_I(4210752, par4);
        var5.addVertexWithUV(0.0D, (double)par2, 0.0D, 0.0D, (double)((float)par2 / var6));
        var5.addVertexWithUV((double)this.width, (double)par2, 0.0D, (double)((float)this.width / var6), (double)((float)par2 / var6));
        var5.setColorRGBA_I(4210752, par3);
        var5.addVertexWithUV((double)this.width, (double)par1, 0.0D, (double)((float)this.width / var6), (double)((float)par1 / var6));
        var5.addVertexWithUV(0.0D, (double)par1, 0.0D, 0.0D, (double)((float)par1 / var6));
        var5.draw();
    }
	
	protected int getSize() {
		return parentGui.modChangelog.length;
	}
	
	protected void elementClicked(int var1, boolean var2) {}

	protected boolean isSelected(int var1) {
		return false;
	}
	
    protected int getContentHeight()
    {
        return getSize() * 12;
    }

	protected void drawBackground() {
		parentGui.drawDefaultBackground();
	}

	protected void drawSlot(int var1, int var2, int var3, int var4, Tessellator var5) {
		parentGui.drawString(parentGui.fontRenderer(), parentGui.modChangelog[var1].replace("	", "    "), 8, var3, 0xFFFFFF);
	}

}
