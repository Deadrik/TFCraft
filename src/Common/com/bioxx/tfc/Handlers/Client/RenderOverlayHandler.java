package com.bioxx.tfc.Handlers.Client;

import java.awt.Color;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.TFCItems;
import com.bioxx.tfc.Core.TFC_Climate;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.Player.BodyTempStats;
import com.bioxx.tfc.Core.Player.FoodStatsTFC;
import com.bioxx.tfc.Core.Player.InventoryPlayerTFC;
import com.bioxx.tfc.Core.Player.PlayerInfo;
import com.bioxx.tfc.Core.Player.PlayerManagerTFC;
import com.bioxx.tfc.Items.ItemQuiver;
import com.bioxx.tfc.Items.Tools.ItemChisel;
import com.bioxx.tfc.Items.Tools.ItemCustomHoe;
import com.bioxx.tfc.WorldGen.DataLayer;
import com.bioxx.tfc.api.TFCAttributes;
import com.bioxx.tfc.api.TFCOptions;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class RenderOverlayHandler
{
	public static ResourceLocation tfcicons = new ResourceLocation(Reference.ModID, Reference.AssetPathGui + "icons.png");
	private FontRenderer fontrenderer = null;

	@SubscribeEvent
	public void renderText(RenderGameOverlayEvent.Chat event)
	{
		// Render chat window above health bar.
		if(Minecraft.getMinecraft().playerController.isInCreativeMode())
			event.posY += 4;
		else
			event.posY -= 12;
	}

	@SubscribeEvent
	public void render(RenderGameOverlayEvent.Pre event)
	{
		GuiIngameForge.renderFood = false;

		// We check for crosshairs just because it's always drawn and is before air bar
		if(event.type != ElementType.CROSSHAIRS)
			return;

		// This is for air to be drawn above our bars
		GuiIngameForge.right_height += 10;

		ScaledResolution sr = event.resolution;
		Minecraft mc = Minecraft.getMinecraft();

		int healthRowHeight = sr.getScaledHeight() - 39;
		int armorRowHeight = healthRowHeight - 10;

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		TFC_Core.bindTexture(tfcicons);

		//Render Tool Mode
		if(mc.thePlayer.inventory.getCurrentItem() != null && 
				mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemCustomHoe)
		{
			int mode = PlayerManagerTFC.getInstance().getClientPlayer().hoeMode;
			this.drawTexturedModalRect(sr.getScaledWidth() / 2 + 95, sr.getScaledHeight() - 21, 0+(20*mode), 38, 20, 20);
		}
		else if(mc.thePlayer.inventory.getCurrentItem() != null && 
				mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemChisel)
		{
			int mode = PlayerManagerTFC.getInstance().getClientPlayer().ChiselMode;
			this.drawTexturedModalRect(sr.getScaledWidth() / 2 + 95, sr.getScaledHeight() - 21, 0+(20*mode), 58, 20, 20);
		}

		//Render Arrow and Javelin for Quiver
		if(getQuiver()!=null && getQuiver().getItem() instanceof ItemQuiver){
			fontrenderer = mc.fontRenderer;

			int xPos = 1;
			int yPos = sr.getScaledHeight() - 34;
			boolean leftSide = true;

			String pos = TFCOptions.quiverHUDPosition;

			if (pos.equalsIgnoreCase("topright"))
			{
				xPos = sr.getScaledWidth() - 19;
				yPos = 1;
				leftSide = false;
			}
			else if (pos.equalsIgnoreCase("right"))
			{
				xPos = sr.getScaledWidth() - 19;
				yPos = (sr.getScaledHeight() - 34) / 2;
				leftSide = false;
			}
			else if (pos.equalsIgnoreCase("bottomright"))
			{
				xPos = sr.getScaledWidth() - 19;
				yPos = sr.getScaledHeight() - 34;
				leftSide = false;
			}
			else if (pos.equalsIgnoreCase("topleft"))
			{
				xPos = 1;
				yPos = 1;
				leftSide = true;
			}
			else if (pos.equalsIgnoreCase("left"))
			{
				xPos = 1;
				yPos = (sr.getScaledHeight() - 34) / 2;
				leftSide = true;
			}

			this.drawTexturedModalRect(xPos, yPos, 0, 78, 16, 16);
			this.drawTexturedModalRect(xPos, yPos + 17, 0, 94, 16, 16);

			if (leftSide)
			{
				fontrenderer.drawString("" + getQuiverArrows(), xPos + 19, yPos + 4, Color.white.getRGB());
				fontrenderer.drawString("" + getQuiverJavelins(), xPos + 19, yPos + 21, Color.white.getRGB());
			}
			else
			{
				int arrowOffset = fontrenderer.getStringWidth(String.valueOf(getQuiverArrows())) + 1;
				int javOffset = fontrenderer.getStringWidth(String.valueOf(getQuiverJavelins())) + 1;

				fontrenderer.drawString("" + getQuiverArrows(), xPos - arrowOffset, yPos + 4, Color.white.getRGB());
				fontrenderer.drawString("" + getQuiverJavelins(), xPos - javOffset, yPos + 21, Color.white.getRGB());
			}

			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			TFC_Core.bindTexture(tfcicons);
		}

		PlayerInfo playerclient = PlayerManagerTFC.getInstance().getClientPlayer();
		if(playerclient != null && mc.playerController.gameIsSurvivalOrAdventure())
		{
			//Draw Health
			int mid = sr.getScaledWidth() / 2;
			this.drawTexturedModalRect(mid-91, healthRowHeight, 0, 0, 90, 10);
			float maxHealth = mc.thePlayer.getMaxHealth();
			float percentHealth = Math.min(mc.thePlayer.getHealth()/maxHealth, 1.0f);
			this.drawTexturedModalRect(mid-91, healthRowHeight, 0, 9, (int) (90*percentHealth), 9);

			//Draw Food and Water
			FoodStatsTFC foodstats = TFC_Core.getPlayerFoodStats(mc.thePlayer);
			float foodLevel = foodstats.getFoodLevel();
			float preFoodLevel = foodstats.getPrevFoodLevel();

			float waterLevel = foodstats.waterLevel;

			float percentFood = foodLevel/foodstats.getMaxStomach(mc.thePlayer);
			float percentWater = waterLevel/foodstats.getMaxWater(mc.thePlayer);

			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			this.drawTexturedModalRect(sr.getScaledWidth() / 2, healthRowHeight, 0, 18, 90, 5);
			if(playerclient.guishowFoodRestoreAmount)
			{
				float percentFood2 = Math.min(percentFood + playerclient.guiFoodRestoreAmount/foodstats.getMaxStomach(mc.thePlayer), 1);
				GL11.glColor4f(0.0F, 0.6F, 0.0F, 0.3F);
				this.drawTexturedModalRect(sr.getScaledWidth() / 2, healthRowHeight, 0, 23, (int) (90*(percentFood2)), 5);
			}
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			this.drawTexturedModalRect(sr.getScaledWidth() / 2, healthRowHeight, 0, 23, (int) (90*percentFood), 5);

			this.drawTexturedModalRect(sr.getScaledWidth() / 2, healthRowHeight+5, 0, 28, 90, 5);
			this.drawTexturedModalRect(sr.getScaledWidth() / 2, healthRowHeight+5, 0, 33, (int) (90*percentWater), 5);

			//Draw Notifications
			String healthString = (int)mc.thePlayer.getHealth()+"/"+(int)maxHealth;
			mc.fontRenderer.drawString(healthString, mid-45-(mc.fontRenderer.getStringWidth(healthString)/2), healthRowHeight+1, Color.white.getRGB());
			if(mc.thePlayer.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getModifier(TFCAttributes.overburdenedUUID) != null)
				mc.fontRenderer.drawString(StatCollector.translateToLocal("gui.overburdened"), mid-(mc.fontRenderer.getStringWidth(StatCollector.translateToLocal("gui.overburdened"))/2), healthRowHeight-20, Color.red.getRGB());

			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			TFC_Core.bindTexture(new ResourceLocation("minecraft:textures/gui/icons.png"));
			//Draw experience bar
			int cap = 0;
			if(mc.thePlayer.ridingEntity == null)
			{
				cap = mc.thePlayer.xpBarCap();
				int left = sr.getScaledWidth() / 2 - 91;

				if (cap > 0)
				{
					short barWidth = 182;
					int filled = (int)(mc.thePlayer.experience * (barWidth + 1));
					int top = sr.getScaledHeight() - 28;
					drawTexturedModalRect(left, top, 0, 64, barWidth, 5);
					if (filled > 0)
						drawTexturedModalRect(left, top, 0, 69, filled, 5);
				}

				if (mc.thePlayer.experienceLevel > 0)
				{
					fontrenderer = mc.fontRenderer;
					boolean flag1 = false;
					int color = flag1 ? 16777215 : 8453920;
					String text = "" + mc.thePlayer.experienceLevel;
					int x = (sr.getScaledWidth() - fontrenderer.getStringWidth(text)) / 2;
					int y = sr.getScaledHeight() - 29;
					fontrenderer.drawString(text, x + 1, y, 0);
					fontrenderer.drawString(text, x - 1, y, 0);
					fontrenderer.drawString(text, x, y + 1, 0);
					fontrenderer.drawString(text, x, y - 1, 0);
					fontrenderer.drawString(text, x, y, color);
				}

				// We have to reset the color back to white
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			}
		}
	}

	@SubscribeEvent
	public void renderText(RenderGameOverlayEvent.Text event)
	{
		Minecraft mc = Minecraft.getMinecraft();
		if(mc.gameSettings.showDebugInfo || TFCOptions.enableDebugMode)
		{
			EntityPlayer player = mc.thePlayer;
			int xCoord = (int)player.posX;
			int yCoord = (int)player.posY;
			int zCoord = (int)player.posZ;
			DataLayer evt = TFC_Climate.getManager(mc.theWorld).getEVTLayerAt(xCoord, zCoord);
			event.left.add(String.format("rain: %.0f, temp: %.2f, average bio temp: %.2f, apparent temp: %.2f, evt: %.3f", new Object[] {
					TFC_Climate.getRainfall(mc.theWorld, xCoord, yCoord, zCoord), 
					TFC_Climate.getHeightAdjustedTemp(mc.theWorld, xCoord, yCoord, zCoord),
					TFC_Climate.getBioTemperatureHeight(mc.theWorld, xCoord, yCoord, zCoord),
					TFC_Climate.getHeightAdjustedTemp(mc.theWorld, xCoord, yCoord, zCoord)+BodyTempStats.applyTemperatureFromHeatSources(player),
					evt.floatdata1}));

			if(TFCOptions.enableDebugMode)
			{
				event.left.add("Stability: " + TFC_Climate.getStability(mc.theWorld, xCoord, zCoord) + 
						", Drainage: " +TFC_Climate.getManager(mc.theWorld).getDrainageLayerAt(xCoord, zCoord).name +
						", pH: " +TFC_Climate.getManager(mc.theWorld).getPHLayerAt(xCoord, zCoord).name);
				event.left.add("Rock Layers: " + TFC_Climate.getManager(mc.theWorld).getRockLayerAt(xCoord, zCoord, 0).name+", "+
						TFC_Climate.getManager(mc.theWorld).getRockLayerAt(xCoord, zCoord, 1).name+", "+
						TFC_Climate.getManager(mc.theWorld).getRockLayerAt(xCoord, zCoord, 2).name);
			}
		}
	}

	private ItemStack getQuiver()
	{
		Minecraft mc = Minecraft.getMinecraft();
		EntityPlayer player = mc.thePlayer;
		ItemStack quiver = null;
		if(player.inventory instanceof InventoryPlayerTFC){
			quiver = ((InventoryPlayerTFC)player.inventory).extraEquipInventory[0];
		}
		return quiver;
	}

	private int getQuiverArrows()
	{
		return ((ItemQuiver)(TFCItems.Quiver)).getQuiverArrows(getQuiver());
	}

	private int getQuiverJavelins()
	{
		return ((ItemQuiver)(TFCItems.Quiver)).getQuiverJavelins(getQuiver());
	}

	public void drawTexturedModalRect(int par1, int par2, int par3, int par4, int par5, int par6)
	{
		float f = 0.00390625F;
		float f1 = 0.00390625F;
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(par1 + 0, par2 + par6, 0.0, (par3 + 0) * f, (par4 + par6) * f1);
		tessellator.addVertexWithUV(par1 + par5, par2 + par6, 0.0, (par3 + par5) * f, (par4 + par6) * f1);
		tessellator.addVertexWithUV(par1 + par5, par2 + 0, 0.0, (par3 + par5) * f, (par4 + 0) * f1);
		tessellator.addVertexWithUV(par1 + 0, par2 + 0, 0.0, (par3 + 0) * f, (par4 + 0) * f1);
		tessellator.draw();
	}
}
