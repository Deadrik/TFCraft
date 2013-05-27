package TFC.Handlers.Client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.event.ForgeSubscribe;

import org.lwjgl.opengl.GL11;

import TFC.Core.TFC_Climate;
import TFC.Core.TFC_Settings;
import TFC.Core.Player.PlayerManagerTFC;
import TFC.Core.Player.TFC_PlayerClient;
import TFC.Food.FoodStatsTFC;
import TFC.Items.Tools.ItemChisel;
import TFC.Items.Tools.ItemCustomHoe;

public class RenderOverlayHandler 
{
	@ForgeSubscribe
	public void render(RenderGameOverlayEvent.Pre event)
	{
		if(event.type == ElementType.HEALTH || event.type == ElementType.FOOD)
		{
			event.setCanceled(true);
		}
	}

	@ForgeSubscribe
	public void render(RenderGameOverlayEvent.Post event)
	{
		if(event.type == ElementType.HEALTH || event.type == ElementType.FOOD)
		{
			event.setCanceled(true);
		}

		ScaledResolution sr = event.resolution;

		int healthRowHeight = sr.getScaledHeight() - 39;
		int armorRowHeight = healthRowHeight - 10;

		TFC_PlayerClient playerclient = ((TFC.Core.Player.TFC_PlayerClient)Minecraft.getMinecraft().thePlayer.getPlayerBase("TFC Player Client"));
		if(playerclient != null)
		{
			//Draw Health
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			
			//GL11.glBindTexture(GL11.GL_TEXTURE_2D, Minecraft.getMinecraft().renderEngine.getTexture("/bioxx/icons.png"));
			Minecraft.getMinecraft().renderEngine.bindTexture("/bioxx/icons.png");
			this.drawTexturedModalRect(sr.getScaledWidth() / 2-91, healthRowHeight, 0, 0, 90, 10);
			float maxHealth = playerclient.getMaxHealth();
			float percentHealth = Minecraft.getMinecraft().thePlayer.getHealth()/maxHealth;
			this.drawTexturedModalRect(sr.getScaledWidth() / 2-91, healthRowHeight, 0, 9, (int) (90*percentHealth), 9);

			//Draw Food and Water
			FoodStatsTFC foodstats = playerclient.getFoodStatsTFC();
			int foodLevel = foodstats.getFoodLevel();
			int preFoodLevel = foodstats.getPrevFoodLevel();

			float waterLevel = foodstats.waterLevel;

			float percentFood = foodLevel/100f;
			float percentWater = waterLevel/foodstats.getMaxWater(Minecraft.getMinecraft().thePlayer);

			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			//GL11.glBindTexture(GL11.GL_TEXTURE_2D, Minecraft.getMinecraft().renderEngine.getTexture("/bioxx/icons.png"));
			this.drawTexturedModalRect(sr.getScaledWidth() / 2, healthRowHeight, 0, 18, 90, 5);
			if(playerclient.guishowFoodRestoreAmount)
			{
				float percentFood2 = Math.min(percentFood + playerclient.guiFoodRestoreAmount/100f, 1);
				GL11.glColor4f(0.0F, 0.6F, 0.0F, 0.3F);
				//GL11.glBindTexture(GL11.GL_TEXTURE_2D, Minecraft.getMinecraft().renderEngine.getTexture("/bioxx/icons.png"));
				this.drawTexturedModalRect(sr.getScaledWidth() / 2, healthRowHeight, 0, 23, (int) (90*(percentFood2)), 5);
			}
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			//GL11.glBindTexture(GL11.GL_TEXTURE_2D, Minecraft.getMinecraft().renderEngine.getTexture("/bioxx/icons.png"));
			this.drawTexturedModalRect(sr.getScaledWidth() / 2, healthRowHeight, 0, 23, (int) (90*percentFood), 5);

			this.drawTexturedModalRect(sr.getScaledWidth() / 2, healthRowHeight+5, 0, 28, 90, 5);
			this.drawTexturedModalRect(sr.getScaledWidth() / 2, healthRowHeight+5, 0, 33, (int) (90*percentWater), 5);

			//Render Tool Mode
			if(Minecraft.getMinecraft().thePlayer.inventory.getCurrentItem() != null && 
					Minecraft.getMinecraft().thePlayer.inventory.getCurrentItem().getItem() instanceof ItemCustomHoe)
			{
				int mode = PlayerManagerTFC.getInstance().getClientPlayer().hoeMode;
				this.drawTexturedModalRect(sr.getScaledWidth() / 2 + 95, sr.getScaledHeight() - 21, 0+(20*mode), 38, 20, 20);
			}
			else if(Minecraft.getMinecraft().thePlayer.inventory.getCurrentItem() != null && 
					Minecraft.getMinecraft().thePlayer.inventory.getCurrentItem().getItem() instanceof ItemChisel)
			{
				int mode = PlayerManagerTFC.getInstance().getClientPlayer().ChiselMode;
				this.drawTexturedModalRect(sr.getScaledWidth() / 2 + 95, sr.getScaledHeight() - 21, 0+(20*mode), 58, 20, 20);
			}
		}
		Minecraft.getMinecraft().renderEngine.resetBoundTexture();
	}

	@ForgeSubscribe
	public void renderText(RenderGameOverlayEvent.Text event)
	{
		if(Minecraft.getMinecraft().gameSettings.showDebugInfo || TFC_Settings.enableDebugMode)
		{
			EntityPlayer player = Minecraft.getMinecraft().thePlayer;
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
