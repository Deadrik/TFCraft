package com.bioxx.tfc.Handlers.Client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Food.ItemMeal;
import com.bioxx.tfc.api.Food;
import com.bioxx.tfc.api.FoodRegistry;

public class FMLClientEventHandler 
{
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void renderTick(TickEvent.RenderTickEvent event)
	{
		Minecraft mc = FMLClientHandler.instance().getClient();
		//World world = mc.theWorld;
		if (event.phase != TickEvent.Phase.START)
		{
			GuiScreen gui = mc.currentScreen;
			if (gui instanceof GuiContainer && !GuiScreen.isShiftKeyDown() && !Mouse.isGrabbed())
			{
				if(mc.thePlayer.inventory.getItemStack() == null)
					renderMealIngredInGui((GuiContainer)gui, mc.thePlayer);
			}
		}
	}

	public void renderMealIngredInGui(GuiContainer gui, EntityPlayer player)
	{
		Minecraft mc = FMLClientHandler.instance().getClient();
		ScaledResolution var13 = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
		int scaledWidth = var13.getScaledWidth();
		int scaledHeight = var13.getScaledHeight();
		int mouseX = Mouse.getX() * scaledWidth / mc.displayWidth;
		int mouseY = scaledHeight - Mouse.getY() * scaledHeight / mc.displayHeight - 1;

		GL11.glPushMatrix();
		GL11.glPushAttrib(1048575);
		//GL11.glDisable(GL11.GL_LIGHTING);
		//int xs = gui.width;
		//int ys = gui.height;
		//int shift = 0;
		//int shift2 = 0;
		int shiftx = -8;
		int shifty = 0;

		Slot slot = gui.getSlotAtPosition(mouseX, mouseY);
		if (slot != null && slot.func_111238_b())
		{
			if (slot.getHasStack() && slot.getStack().getItem() instanceof ItemMeal && slot.getStack().hasTagCompound())
			{
				int[] fg = Food.getFoodGroups(slot.getStack());
				TFC_Core.bindTexture(TextureMap.locationItemsTexture);
				GL11.glColor4f(1, 1, 1, 1.0F);
				for(int i = 0; i < fg.length; i++)
				{
					Item food = FoodRegistry.getInstance().getFood(fg[i]);
					if(food == null)
						continue; // We need to continue the loop for when a middle slot was left empty
					int x = mouseX + 19;
					int y = mouseY + 11;
					GL11.glDisable(GL11.GL_DEPTH_TEST);
					GL11.glPushMatrix();
					GL11.glEnable(GL11.GL_BLEND);
					GL11.glBlendFunc(770, 771);
					GL11.glTranslated(x + shiftx - 2, y + shifty, 0.0D);
					GL11.glScaled(1.0D, 1.0D, 0.0D);
					drawQuad(0, 0, 8, 8, 200, food.getIconFromDamage(0));
					GL11.glDisable(GL11.GL_BLEND);
					GL11.glPopMatrix();

					shiftx+=8;
				}
			}
			GL11.glEnable(GL11.GL_DEPTH_TEST);
		}

		GL11.glPopAttrib();

		GL11.glPopMatrix();
	}

	public static void drawQuad(int x, int y, int xSize, int ySize, double zLevel, IIcon ico)
	{
		Tessellator var9 = Tessellator.instance;
		var9.startDrawingQuads();
		var9.addVertexWithUV(x + 0, y + ySize, zLevel, ico.getMinU(), ico.getMaxV());
		var9.addVertexWithUV(x + xSize, y + ySize, zLevel, ico.getMaxU(), ico.getMaxV());
		var9.addVertexWithUV(x + xSize, y + 0, zLevel, ico.getMaxU(), ico.getMinV());
		var9.addVertexWithUV(x + 0, y + 0, zLevel, ico.getMinU(), ico.getMinV());
		var9.draw();
	}

	/*private boolean isMouseOverSlot(Slot slot, int x, int y)
	{
		return (x >= slot.xDisplayPosition - 1) && (x < slot.xDisplayPosition + 16 + 1) && (y >= slot.yDisplayPosition - 1) && (y < slot.yDisplayPosition + 16 + 1);
	}*/
}
