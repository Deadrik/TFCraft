package com.bioxx.tfc.Handlers.Client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Core.TFC_Core;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class FMLClientEventHandler 
{
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void renderTick(TickEvent.RenderTickEvent event)
	{
		/*Minecraft mc = FMLClientHandler.instance().getClient();
		World world = mc.theWorld;
		if (event.phase != TickEvent.Phase.START)
		{
			GuiScreen gui = mc.currentScreen;
			if (gui instanceof GuiContainer && GuiScreen.isShiftKeyDown() && !Mouse.isGrabbed())
			{

				renderAspectsInGui((GuiContainer)gui, mc.thePlayer);
			}
		}*/
	}

	public void renderImageInGui(GuiContainer gui, EntityPlayer player)
	{
		Minecraft mc = FMLClientHandler.instance().getClient();
		ScaledResolution var13 = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
		int scaledWidth = var13.getScaledWidth();
		int scaledHeight = var13.getScaledHeight();
		int mouseX = Mouse.getX() * scaledWidth / mc.displayWidth;
		int mouseY = scaledHeight - Mouse.getY() * scaledHeight / mc.displayHeight - 1;

		GL11.glPushMatrix();
		GL11.glPushAttrib(1048575);
		GL11.glDisable(2896);
		int xs = gui.width;
		int ys = gui.height;
		int shift = 0;
		int shift2 = 0;
		int shiftx = -8;
		int shifty = -8;

		Slot slot = gui.getSlotAtPosition(mouseX, mouseY);
		if (slot != null)
		{
			if (slot.getStack() != null)
			{
				int x = mouseX + 17;
				int y = mouseY + 7 - 33;
				GL11.glDisable(2929);

				x = mouseX + 17;
				y = mouseY + 7 - 33;

				TFC_Core.bindTexture(new ResourceLocation(Reference.ModID+":textures/wip.png"));
				GL11.glPushMatrix();
				GL11.glEnable(3042);
				GL11.glBlendFunc(770, 771);
				GL11.glTranslated(x + shiftx - 2, y + shifty - 2, 0.0D);
				GL11.glScaled(1.25D, 1.25D, 0.0D);
				drawTexturedQuadFull(0, 0, 200);
				GL11.glDisable(3042);
				GL11.glPopMatrix();
			}
			GL11.glEnable(2929);
		}

		GL11.glPopAttrib();

		GL11.glPopMatrix();
	}

	public static void drawTexturedQuadFull(int par1, int par2, double zLevel)
	{
		Tessellator var9 = Tessellator.instance;
		var9.startDrawingQuads();
		var9.addVertexWithUV(par1 + 0, par2 + 16, zLevel, 0.0D, 1.0D);
		var9.addVertexWithUV(par1 + 16, par2 + 16, zLevel, 1.0D, 1.0D);
		var9.addVertexWithUV(par1 + 16, par2 + 0, zLevel, 1.0D, 0.0D);
		var9.addVertexWithUV(par1 + 0, par2 + 0, zLevel, 0.0D, 0.0D);
		var9.draw();
	}

	private boolean isMouseOverSlot(Slot slot, int x, int y)
	{
		return (x >= slot.xDisplayPosition - 1) && (x < slot.xDisplayPosition + 16 + 1) && (y >= slot.yDisplayPosition - 1) && (y < slot.yDisplayPosition + 16 + 1);
	}
}
