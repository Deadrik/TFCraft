package TFC.Handlers.Client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.event.ForgeSubscribe;

import org.lwjgl.opengl.GL11;

import TFC.Reference;
import TFC.API.TFCOptions;
import TFC.Core.TFC_Climate;
import TFC.Core.TFC_Core;
import TFC.Core.Player.PlayerInfo;
import TFC.Core.Player.PlayerManagerTFC;
import TFC.Food.FoodStatsTFC;
import TFC.Items.Tools.ItemChisel;
import TFC.Items.Tools.ItemCustomHoe;

public class RenderOverlayHandler 
{
	public static ResourceLocation tfcicons = new ResourceLocation(Reference.ModID, Reference.AssetPathGui + "icons.png");
	private FontRenderer fontrenderer = null;

	@ForgeSubscribe
	public void render(RenderGameOverlayEvent.Pre event)
	{
		GuiIngameForge.renderFood = false;

		// We check for crosshairs just because it's always drawn and is before air bar
		if(event.type != ElementType.CROSSHAIRS) {
			return;
		}

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

		PlayerInfo playerclient = PlayerManagerTFC.getInstance().getClientPlayer();
		if(playerclient != null && mc.playerController.func_78763_f())
		{
			//Draw Health

			this.drawTexturedModalRect(sr.getScaledWidth() / 2-91, healthRowHeight, 0, 0, 90, 10);
			float maxHealth = mc.thePlayer.getMaxHealth();
			float percentHealth = mc.thePlayer.getHealth()/maxHealth;
			this.drawTexturedModalRect(sr.getScaledWidth() / 2-91, healthRowHeight, 0, 9, (int) (90*percentHealth), 9);

			//Draw Food and Water
			FoodStatsTFC foodstats = TFC_Core.getPlayerFoodStats(mc.thePlayer);
			int foodLevel = foodstats.getFoodLevel();
			int preFoodLevel = foodstats.getPrevFoodLevel();

			float waterLevel = foodstats.waterLevel;

			float percentFood = foodLevel/100f;
			float percentWater = waterLevel/foodstats.getMaxWater(mc.thePlayer);

			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			this.drawTexturedModalRect(sr.getScaledWidth() / 2, healthRowHeight, 0, 18, 90, 5);
			if(playerclient.guishowFoodRestoreAmount)
			{
				float percentFood2 = Math.min(percentFood + playerclient.guiFoodRestoreAmount/100f, 1);
				GL11.glColor4f(0.0F, 0.6F, 0.0F, 0.3F);
				this.drawTexturedModalRect(sr.getScaledWidth() / 2, healthRowHeight, 0, 23, (int) (90*(percentFood2)), 5);
			}
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			this.drawTexturedModalRect(sr.getScaledWidth() / 2, healthRowHeight, 0, 23, (int) (90*percentFood), 5);

			this.drawTexturedModalRect(sr.getScaledWidth() / 2, healthRowHeight+5, 0, 28, 90, 5);
			this.drawTexturedModalRect(sr.getScaledWidth() / 2, healthRowHeight+5, 0, 33, (int) (90*percentWater), 5);

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
					{
						drawTexturedModalRect(left, top, 0, 69, filled, 5);
					}
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

	@ForgeSubscribe
	public void renderText(RenderGameOverlayEvent.Text event)
	{
		Minecraft mc = Minecraft.getMinecraft();
		if(mc.gameSettings.showDebugInfo || TFCOptions.enableDebugMode)
		{
			EntityPlayer player = mc.thePlayer;
			int xCoord = (int)player.posX;
			int yCoord = (int)player.posY;
			int zCoord = (int)player.posZ;
			event.left.add(String.format("rain: %.0f, temp: %.2f, evt: %.3f", new Object[] {
					TFC_Climate.getRainfall(xCoord, yCoord, zCoord), 
					TFC_Climate.getHeightAdjustedTemp(xCoord, yCoord, zCoord), 
					TFC_Climate.manager.getEVTLayerAt(xCoord, zCoord).floatdata1}));

			event.left.add("Health: " + player.getHealth());
		}
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
