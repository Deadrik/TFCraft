package TFC.GUI;

import java.awt.Color;
import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import TFC.*;
import TFC.Core.TFC_Climate;
import TFC.Core.TFC_Time;
import TFC.Items.*;
import TFC.Core.Player.PlayerManagerTFC;
import TFC.Core.Player.TFC_PlayerClient;
import TFC.Core.Player.TFC_PlayerServer;
import TFC.Food.FoodStatsTFC;
import TFC.WorldGen.DataLayer;
import TFC.WorldGen.TFCWorldChunkManager;

import net.minecraft.client.Minecraft;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.*;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.gui.GuiPlayerInfo;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.client.model.*;
import net.minecraft.client.multiplayer.NetClientHandler;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerSelector;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.crash.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.packet.*;
import net.minecraft.pathfinding.*;
import net.minecraft.potion.*;
import net.minecraft.server.*;
import net.minecraft.stats.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.village.*;
import net.minecraft.world.*;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.ForgeHooks;

public class GuiHUD extends GuiIngame
{

	private static final RenderItem itemRenderer = new RenderItem();
	private final Random rand = new Random();
	private final Minecraft mc;

	/** ChatGUI instance that retains all previous chat data */
	private final GuiNewChat persistantChatGUI;
	private int updateCounter = 0;

	/** The string specifying which record music is playing */
	private String recordPlaying = "";

	/** How many ticks the record playing message will be displayed */
	private int recordPlayingUpFor = 0;
	private boolean recordIsPlaying = false;

	/** Previous frame vignette brightness (slowly changes by 1% each frame) */
	public float prevVignetteBrightness = 1.0F;

	public GuiHUD(Minecraft par1Minecraft)
	{
		super(par1Minecraft);
		this.mc = par1Minecraft;
		this.persistantChatGUI = new GuiNewChat(par1Minecraft);
	}

	/**
	 * Render the ingame overlay with quick icon bar, ...
	 */
	public void renderGameOverlay(float par1, boolean par2, int par3, int par4)
	{
		ScaledResolution var5 = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
		int scaledWidth = var5.getScaledWidth();
		int scaledHeight = var5.getScaledHeight();
		FontRenderer fontRenderer = this.mc.fontRenderer;
		this.mc.entityRenderer.setupOverlayRendering();
		GL11.glEnable(GL11.GL_BLEND);

		if (Minecraft.isFancyGraphicsEnabled())
		{
			this.renderVignette(this.mc.thePlayer.getBrightness(par1), scaledWidth, scaledHeight);
		}
		else
		{
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		}

		ItemStack equippedHelm = this.mc.thePlayer.inventory.armorItemInSlot(3);

		if (this.mc.gameSettings.thirdPersonView == 0 && equippedHelm != null && equippedHelm.itemID == Block.pumpkin.blockID)
		{
			this.renderPumpkinBlur(scaledWidth, scaledHeight);
		}

		if (!this.mc.thePlayer.isPotionActive(Potion.confusion))
		{
			float var10 = this.mc.thePlayer.prevTimeInPortal + (this.mc.thePlayer.timeInPortal - this.mc.thePlayer.prevTimeInPortal) * par1;

			if (var10 > 0.0F)
			{
				this.renderPortalOverlay(var10, scaledWidth, scaledHeight);
			}
		}

		boolean var11;
		int var12;
		int var13;
		int preFoodLevel;
		int foodLevel;
		int var19;
		int var20;
		int var23;
		int armorRowHeight;
		int healthRowHeight;

		if (!this.mc.playerController.enableEverythingIsScrewedUpMode())
		{
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mc.renderEngine.getTexture("/gui/gui.png"));
			InventoryPlayer var31 = this.mc.thePlayer.inventory;
			this.zLevel = -90.0F;
			this.drawTexturedModalRect(scaledWidth / 2 - 91, scaledHeight - 22, 0, 0, 182, 22);
			this.drawTexturedModalRect(scaledWidth / 2 - 91 - 1 + var31.currentItem * 20, scaledHeight - 22 - 1, 0, 22, 24, 22);

			//Start Drawing the Reticule
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mc.renderEngine.getTexture("/gui/icons.png"));
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_ONE_MINUS_DST_COLOR, GL11.GL_ONE_MINUS_SRC_COLOR);
			this.drawTexturedModalRect(scaledWidth / 2 - 7, scaledHeight / 2 - 7, 0, 0, 16, 16);
			GL11.glDisable(GL11.GL_BLEND);
			//End Drawing Reticule

			var11 = this.mc.thePlayer.hurtResistantTime / 3 % 2 == 1;

			if (this.mc.thePlayer.hurtResistantTime < 10)
			{
				var11 = false;
			}

			healthRowHeight = scaledHeight - 39;
			armorRowHeight = healthRowHeight - 10;

			TFC_PlayerClient playerclient = ((TFC.Core.Player.TFC_PlayerClient)this.mc.thePlayer.getPlayerBase("TFC Player Client"));
			if(playerclient != null)
			{
				//Draw Health
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mc.renderEngine.getTexture("/bioxx/icons.png"));
				this.drawTexturedModalRect(scaledWidth / 2-91, healthRowHeight, 0, 0, 90, 10);
				float maxHealth = playerclient.getMaxHealth();
				float percentHealth = (float)this.mc.thePlayer.getHealth()/maxHealth;
				this.drawTexturedModalRect(scaledWidth / 2-91, healthRowHeight, 0, 9, (int) (90*percentHealth), 9);

				//String h = String.valueOf(this.mc.thePlayer.getHealth());
				//this.drawCenteredString(fontRenderer, h, scaledWidth / 2 + 45, healthRowHeight+1, 0xFFFFFF);

				//Draw Food and Water
				FoodStatsTFC foodstats = playerclient.getFoodStatsTFC();
				foodLevel = foodstats.getFoodLevel();
				preFoodLevel = foodstats.getPrevFoodLevel();

				float waterLevel = foodstats.waterLevel;

				float percentFood = (float)foodLevel/100f;
				float percentWater = (float)waterLevel/(float)foodstats.getMaxWater(this.mc.thePlayer);

				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mc.renderEngine.getTexture("/bioxx/icons.png"));
				this.drawTexturedModalRect(scaledWidth / 2, healthRowHeight, 0, 18, 90, 5);
				if(playerclient.guishowFoodRestoreAmount)
				{
					float percentFood2 = Math.min(percentFood + (float)playerclient.guiFoodRestoreAmount/100f, 1);
					GL11.glColor4f(0.0F, 0.6F, 0.0F, 0.3F);
					GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mc.renderEngine.getTexture("/bioxx/icons.png"));
					this.drawTexturedModalRect(scaledWidth / 2, healthRowHeight, 0, 23, (int) (90*(percentFood2)), 5);
				}
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mc.renderEngine.getTexture("/bioxx/icons.png"));
				this.drawTexturedModalRect(scaledWidth / 2, healthRowHeight, 0, 23, (int) (90*percentFood), 5);
				
				this.drawTexturedModalRect(scaledWidth / 2, healthRowHeight+5, 0, 28, 90, 5);
				this.drawTexturedModalRect(scaledWidth / 2, healthRowHeight+5, 0, 33, (int) (90*percentWater), 5);
				
				//Render Tool Mode
				//this.drawTexturedModalRect(scaledWidth / 2 + 95, scaledHeight - 21, 0, 38, 20, 20);
				if(mc.thePlayer.inventory.getCurrentItem() != null && 
						mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemCustomHoe)
				{
					int mode = PlayerManagerTFC.getInstance().getClientPlayer().hoeMode;
					this.drawTexturedModalRect(scaledWidth / 2 + 95, scaledHeight - 21, 0+(20*mode), 38, 20, 20);
				}
				else if(mc.thePlayer.inventory.getCurrentItem() != null && 
						mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemChisel)
				{
					int mode = PlayerManagerTFC.getInstance().getClientPlayer().ChiselMode;
					this.drawTexturedModalRect(scaledWidth / 2 + 95, scaledHeight - 21, 0+(20*mode), 58, 20, 20);
				}
			}



			//            this.mc.mcProfiler.startSection("bossHealth");
			//            this.renderBossHealth();
			//            this.mc.mcProfiler.endSection();




			int var18;
			var12 = this.mc.thePlayer.getHealth();
			var13 = this.mc.thePlayer.prevHealth;
			this.rand.setSeed((long)(this.updateCounter * 312871));
			boolean var14 = false;
			if (this.mc.playerController.shouldDrawHUD())
			{         
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mc.renderEngine.getTexture("/gui/icons.png"));
				var18 = scaledWidth / 2 - 91;
				var19 = scaledWidth / 2 + 91;
				this.mc.mcProfiler.startSection("expBar");
				var20 = this.mc.thePlayer.xpBarCap();

				if (var20 > 0)
				{
					short var21 = 182;
					int xp = (int)(this.mc.thePlayer.experience * (float)(var21 + 1));
					int yOffset = scaledHeight - 32 + 3;
					this.drawTexturedModalRect(var18, yOffset, 0, 64, var21, 5);

					if (xp > 0)
					{
						this.drawTexturedModalRect(var18, yOffset, 0, 69, xp, 5);
					}
				}


				int yOffset = scaledHeight - 49;
				int armorValue = ForgeHooks.getTotalArmorValue(mc.thePlayer);
				int var24 = -1;

				if (this.mc.thePlayer.isPotionActive(Potion.regeneration))
				{
					var24 = this.updateCounter % 25;
				}

				this.mc.mcProfiler.endStartSection("healthArmor");
				int var25;
				int var26;
				int var29;
				int var28;

				for (var25 = 0; var25 < 10; ++var25)
				{
					if (armorValue > 0)
					{
						
						var26 = var18 + var25 * 8;

						if (var25 * 2 + 1 < armorValue)
						{
							this.drawTexturedModalRect(var26, scaledHeight, 34, 9, 9, 9);
						}

						if (var25 * 2 + 1 == armorValue)
						{
							this.drawTexturedModalRect(var26, scaledHeight, 25, 9, 9, 9);
						}

						if (var25 * 2 + 1 > armorValue)
						{
							this.drawTexturedModalRect(var26, scaledHeight, 16, 9, 9, 9);
						}
					}

					var26 = 16;

					if (this.mc.thePlayer.isPotionActive(Potion.poison))
					{
						var26 += 36;
					}

					byte var27 = 0;

					if (var11)
					{
						var27 = 1;
					}

					var28 = var18 + var25 * 8;
					var29 = healthRowHeight;

					if (var12 <= 4)
					{
						var29 = healthRowHeight + this.rand.nextInt(2);
					}

					if (var25 == var24)
					{
						var29 -= 2;
					}

					byte var30 = 0;

					if (this.mc.theWorld.getWorldInfo().isHardcoreModeEnabled())
					{
						var30 = 5;
					}

					//					                    this.drawTexturedModalRect(var28, var29, 16 + var27 * 9, 9 * var30, 9, 9);
					//					
					//					                    if (var11)
						//						                    {
						//						                        if (var25 * 2 + 1 < var13)
							//							                        {
							//							                            this.drawTexturedModalRect(var28, var29, var26 + 54, 9 * var30, 9, 9);
					//					                        }
					//					
					//					                        if (var25 * 2 + 1 == var13)
					//					                        {
					//					                            this.drawTexturedModalRect(var28, var29, var26 + 63, 9 * var30, 9, 9);
					//					                        }
					//					                    }
					//					
					//					                    if (var25 * 2 + 1 < var12)
					//					                    {
					//					                        this.drawTexturedModalRect(var28, var29, var26 + 36, 9 * var30, 9, 9);
					//					                    }
					//					
					//					                    if (var25 * 2 + 1 == var12)
					//					                    {
					//					                        this.drawTexturedModalRect(var28, var29, var26 + 45, 9 * var30, 9, 9);
					//					                    }
				}

				this.mc.mcProfiler.endStartSection("food");
				int var52;
				this.mc.mcProfiler.endStartSection("air");

				if (this.mc.thePlayer.isInsideOfMaterial(Material.water))
				{
					var25 = this.mc.thePlayer.getAir();
					var26 = MathHelper.ceiling_double_int((double)(var25 - 2) * 10.0D / 300.0D);
					var52 = MathHelper.ceiling_double_int((double)var25 * 10.0D / 300.0D) - var26;

					GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mc.renderEngine.getTexture("/gui/icons.png"));
					for (var28 = 0; var28 < var26 + var52; ++var28)
					{
						if (var28 < var26)
						{
							this.drawTexturedModalRect(var19 - var28 * 8 - 9, armorRowHeight, 16, 18, 9, 9);
						}
						else
						{
							this.drawTexturedModalRect(var19 - var28 * 8 - 9, armorRowHeight, 25, 18, 9, 9);
						}
					}
				}

				this.mc.mcProfiler.endSection();
			}

			GL11.glDisable(GL11.GL_BLEND);
			this.mc.mcProfiler.startSection("actionBar");
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			RenderHelper.enableGUIStandardItemLighting();

			for (var18 = 0; var18 < 9; ++var18)
			{
				var19 = scaledWidth / 2 - 90 + var18 * 20 + 2;
				var20 = scaledHeight - 16 - 3;
				this.renderInventorySlot(var18, var19, var20, par1);
			}

			RenderHelper.disableStandardItemLighting();
			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
			this.mc.mcProfiler.endSection();
		}

		float var33;

		if (this.mc.thePlayer.getSleepTimer() > 0)
		{
			this.mc.mcProfiler.startSection("sleep");
			GL11.glDisable(GL11.GL_DEPTH_TEST);
			GL11.glDisable(GL11.GL_ALPHA_TEST);
			int var32 = this.mc.thePlayer.getSleepTimer();
			var33 = (float)var32 / 100.0F;

			if (var33 > 1.0F)
			{
				var33 = 1.0F - (float)(var32 - 100) / 10.0F;
			}

			var12 = (int)(220.0F * var33) << 24 | 1052704;
			drawRect(0, 0, scaledWidth, scaledHeight, var12);
			GL11.glEnable(GL11.GL_ALPHA_TEST);
			GL11.glEnable(GL11.GL_DEPTH_TEST);
			this.mc.mcProfiler.endSection();
		}

		int var38;
		int var40;

		if (this.mc.playerController.func_78763_f() && this.mc.thePlayer.experienceLevel > 0)
		{
			this.mc.mcProfiler.startSection("expLevel");
			var11 = false;
			var12 = var11 ? 16777215 : 8453920;
			String var35 = "" + this.mc.thePlayer.experienceLevel;
			var40 = (scaledWidth - fontRenderer.getStringWidth(var35)) / 2;
			var38 = scaledHeight - 31 - 4;
			fontRenderer.drawString(var35, var40 + 1, var38, 0);
			fontRenderer.drawString(var35, var40 - 1, var38, 0);
			fontRenderer.drawString(var35, var40, var38 + 1, 0);
			fontRenderer.drawString(var35, var40, var38 - 1, 0);
			fontRenderer.drawString(var35, var40, var38, var12);
			this.mc.mcProfiler.endSection();
		}

		if (this.mc.gameSettings.showDebugInfo)
		{
			this.mc.mcProfiler.startSection("debug");
			GL11.glPushMatrix();
			fontRenderer.drawStringWithShadow("Minecraft (" + this.mc.debug + ")", 2, 2, 16777215);
			fontRenderer.drawStringWithShadow(this.mc.debugInfoRenders(), 2, 12, 16777215);
			fontRenderer.drawStringWithShadow(this.mc.getEntityDebug(), 2, 22, 16777215);
			fontRenderer.drawStringWithShadow(this.mc.debugInfoEntities(), 2, 32, 16777215);
			fontRenderer.drawStringWithShadow(this.mc.getWorldProviderName(), 2, 42, 16777215);
			long var41 = Runtime.getRuntime().maxMemory();
			long var34 = Runtime.getRuntime().totalMemory();
			long var42 = Runtime.getRuntime().freeMemory();
			long var43 = var34 - var42;
			String var45 = "Used memory: " + var43 * 100L / var41 + "% (" + var43 / 1024L / 1024L + "MB) of " + var41 / 1024L / 1024L + "MB";
			this.drawString(fontRenderer, var45, scaledWidth - fontRenderer.getStringWidth(var45) - 2, 2, 14737632);
			var45 = "Allocated memory: " + var34 * 100L / var41 + "% (" + var34 / 1024L / 1024L + "MB)";
			this.drawString(fontRenderer, var45, scaledWidth - fontRenderer.getStringWidth(var45) - 2, 12, 14737632);
			
			int xCoord = MathHelper.floor_double(this.mc.thePlayer.posX);
			int yCoord = MathHelper.floor_double(this.mc.thePlayer.posY);
			int zCoord = MathHelper.floor_double(this.mc.thePlayer.posZ);
			
			this.drawString(fontRenderer, String.format("x: %.5f (%d) // c: %d (%d)", new Object[] {Double.valueOf(this.mc.thePlayer.posX), Integer.valueOf(xCoord), Integer.valueOf(xCoord >> 4), Integer.valueOf(xCoord & 15)}), 2, 64, 14737632);
            this.drawString(fontRenderer, String.format("y: %.3f (feet pos, %.3f eyes pos)", new Object[] {Double.valueOf(this.mc.thePlayer.boundingBox.minY), Double.valueOf(this.mc.thePlayer.posY)}), 2, 72, 14737632);
            this.drawString(fontRenderer, String.format("z: %.5f (%d) // c: %d (%d)", new Object[] {Double.valueOf(this.mc.thePlayer.posZ), Integer.valueOf(zCoord), Integer.valueOf(zCoord >> 4), Integer.valueOf(zCoord & 15)}), 2, 80, 14737632);
			this.drawString(fontRenderer, "f: " + (MathHelper.floor_double((double)(this.mc.thePlayer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3), 2, 88, 14737632);
			healthRowHeight = MathHelper.floor_double(this.mc.thePlayer.posX);
			armorRowHeight = MathHelper.floor_double(this.mc.thePlayer.posY);
			var23 = MathHelper.floor_double(this.mc.thePlayer.posZ);

			if (this.mc.theWorld != null && this.mc.theWorld.blockExists(healthRowHeight, armorRowHeight, var23))
			{
				Chunk var48 = this.mc.theWorld.getChunkFromBlockCoords(healthRowHeight, var23);
				this.drawString(fontRenderer, "lc: " + (var48.getTopFilledSegment() + 15) + " b: " + var48.getBiomeGenForWorldCoords(healthRowHeight & 15, var23 & 15, this.mc.theWorld.getWorldChunkManager()).biomeName + " bl: " + var48.getSavedLightValue(EnumSkyBlock.Block, healthRowHeight & 15, armorRowHeight, var23 & 15) + " sl: " + var48.getSavedLightValue(EnumSkyBlock.Sky, healthRowHeight & 15, armorRowHeight, var23 & 15) + " rl: " + var48.getBlockLightValue(healthRowHeight & 15, armorRowHeight, var23 & 15, 0), 2, 96, 14737632);
			}

			this.drawString(fontRenderer, String.format("ws: %.3f, fs: %.3f, g: %b", new Object[] {Float.valueOf(this.mc.thePlayer.capabilities.getWalkSpeed()), Float.valueOf(this.mc.thePlayer.capabilities.getFlySpeed()), Boolean.valueOf(this.mc.thePlayer.onGround)}), 2, 104, 14737632);
			
			//Render climate data
			this.drawString(fontRenderer, String.format("rain: %.0f, temp: %.2f, evt: %.3f", new Object[] {
					TFC_Climate.getRainfall(xCoord, yCoord, zCoord), 
					TFC_Climate.getHeightAdjustedTemp(xCoord, yCoord, zCoord), 
					TFC_Climate.manager.getEVTLayerAt(xCoord, zCoord).floatdata1}), 2, 120, 14737632);
			
			GL11.glPopMatrix();
			this.mc.mcProfiler.endSection();
		}

		if (this.recordPlayingUpFor > 0)
		{
			this.mc.mcProfiler.startSection("overlayMessage");
			var33 = (float)this.recordPlayingUpFor - par1;
			var12 = (int)(var33 * 256.0F / 20.0F);

			if (var12 > 255)
			{
				var12 = 255;
			}

			if (var12 > 0)
			{
				GL11.glPushMatrix();
				GL11.glTranslatef((float)(scaledWidth / 2), (float)(scaledHeight - 48), 0.0F);
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
				var13 = 16777215;

				if (this.recordIsPlaying)
				{
					var13 = Color.HSBtoRGB(var33 / 50.0F, 0.7F, 0.6F) & 16777215;
				}

				fontRenderer.drawString(this.recordPlaying, -fontRenderer.getStringWidth(this.recordPlaying) / 2, -4, var13 + (var12 << 24));
				GL11.glDisable(GL11.GL_BLEND);
				GL11.glPopMatrix();
			}

			this.mc.mcProfiler.endSection();
		}

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glPushMatrix();
		GL11.glTranslatef(0.0F, (float)(scaledHeight - 48), 0.0F);
		this.mc.mcProfiler.startSection("chat");
		this.persistantChatGUI.drawChat(this.updateCounter);
		this.mc.mcProfiler.endSection();
		GL11.glPopMatrix();

		if (this.mc.gameSettings.keyBindPlayerList.pressed && (!this.mc.isIntegratedServerRunning() || this.mc.thePlayer.sendQueue.playerInfoList.size() > 1))
		{
			this.mc.mcProfiler.startSection("playerList");
			NetClientHandler var37 = this.mc.thePlayer.sendQueue;
			List var39 = var37.playerInfoList;
			var13 = var37.currentServerMaxPlayers;
			var40 = var13;

			for (var38 = 1; var40 > 20; var40 = (var13 + var38 - 1) / var38)
			{
				++var38;
			}

			foodLevel = 300 / var38;

			if (foodLevel > 150)
			{
				foodLevel = 150;
			}

			preFoodLevel = (scaledWidth - var38 * foodLevel) / 2;
			byte var44 = 10;
			drawRect(preFoodLevel - 1, var44 - 1, preFoodLevel + foodLevel * var38, var44 + 9 * var40, Integer.MIN_VALUE);

			for (var19 = 0; var19 < var13; ++var19)
			{
				var20 = preFoodLevel + var19 % var38 * foodLevel;
				healthRowHeight = var44 + var19 / var38 * 9;
				drawRect(var20, healthRowHeight, var20 + foodLevel - 1, healthRowHeight + 8, 553648127);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				GL11.glEnable(GL11.GL_ALPHA_TEST);

				if (var19 < var39.size())
				{
					GuiPlayerInfo var46 = (GuiPlayerInfo)var39.get(var19);
					fontRenderer.drawStringWithShadow(var46.name, var20, healthRowHeight, 16777215);
					this.mc.renderEngine.bindTexture("/gui/icons.png");
					byte var51 = 0;
					boolean var49 = false;
					byte var50;

					if (var46.responseTime < 0)
					{
						var50 = 5;
					}
					else if (var46.responseTime < 150)
					{
						var50 = 0;
					}
					else if (var46.responseTime < 300)
					{
						var50 = 1;
					}
					else if (var46.responseTime < 600)
					{
						var50 = 2;
					}
					else if (var46.responseTime < 1000)
					{
						var50 = 3;
					}
					else
					{
						var50 = 4;
					}

					this.zLevel += 100.0F;
					this.drawTexturedModalRect(var20 + foodLevel - 12, healthRowHeight, 0 + var51 * 10, 176 + var50 * 8, 10, 8);
					this.zLevel -= 100.0F;
				}
			}
		}

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
	}

	/**
	 * Renders dragon's (boss) health on the HUD
	 */
	//	private void renderBossHealth()
	//	{
	//		if (RenderDragon.entityDragon != null)
	//		{
	//			EntityDragon var1 = RenderDragon.entityDragon;
	//			RenderDragon.entityDragon = null;
	//			FontRenderer var2 = this.mc.fontRenderer;
	//			ScaledResolution var3 = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
	//			int var4 = var3.getScaledWidth();
	//			short var5 = 182;
	//			int var6 = var4 / 2 - var5 / 2;
	//			int var7 = (int)((float)var1.getDragonHealth() / (float)var1.getMaxHealth() * (float)(var5 + 1));
	//			byte var8 = 12;
	//			this.drawTexturedModalRect(var6, var8, 0, 74, var5, 5);
	//			this.drawTexturedModalRect(var6, var8, 0, 74, var5, 5);
	//
	//			if (var7 > 0)
	//			{
	//				this.drawTexturedModalRect(var6, var8, 0, 79, var7, 5);
	//			}
	//
	//			String var9 = "Boss health";
	//			var2.drawStringWithShadow(var9, var4 / 2 - var2.getStringWidth(var9) / 2, var8 - 10, 16711935);
	//			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	//			GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mc.renderEngine.getTexture("/gui/icons.png"));
	//		}
	//	}

	private void renderPumpkinBlur(int par1, int par2)
	{
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mc.renderEngine.getTexture("%blur%/misc/pumpkinblur.png"));
		Tessellator var3 = Tessellator.instance;
		var3.startDrawingQuads();
		var3.addVertexWithUV(0.0D, (double)par2, -90.0D, 0.0D, 1.0D);
		var3.addVertexWithUV((double)par1, (double)par2, -90.0D, 1.0D, 1.0D);
		var3.addVertexWithUV((double)par1, 0.0D, -90.0D, 1.0D, 0.0D);
		var3.addVertexWithUV(0.0D, 0.0D, -90.0D, 0.0D, 0.0D);
		var3.draw();
		GL11.glDepthMask(true);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

	/**
	 * Renders the vignette. Args: vignetteBrightness, width, height
	 */
	private void renderVignette(float par1, int par2, int par3)
	{
		par1 = 1.0F - par1;

		if (par1 < 0.0F)
		{
			par1 = 0.0F;
		}

		if (par1 > 1.0F)
		{
			par1 = 1.0F;
		}

		this.prevVignetteBrightness = (float)((double)this.prevVignetteBrightness + (double)(par1 - this.prevVignetteBrightness) * 0.01D);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		GL11.glBlendFunc(GL11.GL_ZERO, GL11.GL_ONE_MINUS_SRC_COLOR);
		GL11.glColor4f(this.prevVignetteBrightness, this.prevVignetteBrightness, this.prevVignetteBrightness, 1.0F);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mc.renderEngine.getTexture("%blur%/misc/vignette.png"));
		Tessellator var4 = Tessellator.instance;
		var4.startDrawingQuads();
		var4.addVertexWithUV(0.0D, (double)par3, -90.0D, 0.0D, 1.0D);
		var4.addVertexWithUV((double)par2, (double)par3, -90.0D, 1.0D, 1.0D);
		var4.addVertexWithUV((double)par2, 0.0D, -90.0D, 1.0D, 0.0D);
		var4.addVertexWithUV(0.0D, 0.0D, -90.0D, 0.0D, 0.0D);
		var4.draw();
		GL11.glDepthMask(true);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	}

	/**
	 * Renders the portal overlay. Args: portalStrength, width, height
	 */
	private void renderPortalOverlay(float par1, int par2, int par3)
	{
		if (par1 < 1.0F)
		{
			par1 *= par1;
			par1 *= par1;
			par1 = par1 * 0.8F + 0.2F;
		}

		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, par1);
		this.mc.renderEngine.bindTexture("/terrain.png");
		Icon icon = Block.portal.getBlockTextureFromSide(1);
        float var4 = icon.getMinU();
        float var5 = icon.getMaxU();
        float var6 = icon.getMinV();
        float var7 = icon.getMaxV();
		Tessellator var8 = Tessellator.instance;
		var8.startDrawingQuads();
		var8.addVertexWithUV(0.0D, (double)par3, -90.0D, (double)var4, (double)var7);
		var8.addVertexWithUV((double)par2, (double)par3, -90.0D, (double)var6, (double)var7);
		var8.addVertexWithUV((double)par2, 0.0D, -90.0D, (double)var6, (double)var5);
		var8.addVertexWithUV(0.0D, 0.0D, -90.0D, (double)var4, (double)var5);
		var8.draw();
		GL11.glDepthMask(true);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

	/**
	 * Renders the specified item of the inventory slot at the specified location. Args: slot, x, y, partialTick
	 */
	private void renderInventorySlot(int par1, int par2, int par3, float par4)
	{
		ItemStack var5 = this.mc.thePlayer.inventory.mainInventory[par1];

		if (var5 != null)
		{
			float var6 = (float)var5.animationsToGo - par4;

			if (var6 > 0.0F)
			{
				GL11.glPushMatrix();
				float var7 = 1.0F + var6 / 5.0F;
				GL11.glTranslatef((float)(par2 + 8), (float)(par3 + 12), 0.0F);
				GL11.glScalef(1.0F / var7, (var7 + 1.0F) / 2.0F, 1.0F);
				GL11.glTranslatef((float)(-(par2 + 8)), (float)(-(par3 + 12)), 0.0F);
			}

			itemRenderer.renderItemIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, var5, par2, par3);

			if (var6 > 0.0F)
			{
				GL11.glPopMatrix();
			}

			itemRenderer.renderItemOverlayIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, var5, par2, par3);
		}
	}

	/**
	 * The update tick for the ingame UI
	 */
	public void updateTick()
	{
		if (this.recordPlayingUpFor > 0)
		{
			--this.recordPlayingUpFor;
		}

		++this.updateCounter;
	}

	public void setRecordPlayingMessage(String par1Str)
	{
		this.recordPlaying = "Now playing: " + par1Str;
		this.recordPlayingUpFor = 60;
		this.recordIsPlaying = true;
	}

	/**
	 * returns a pointer to the persistant Chat GUI, containing all previous chat messages and such
	 */
	public GuiNewChat getChatGUI()
	{
		return this.persistantChatGUI;
	}

	public int getUpdateCounter()
	{
		return this.updateCounter;
	}
}
