package com.bioxx.tfc.Handlers.Client;

import java.awt.Color;
import java.lang.reflect.Field;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.ReflectionHelper;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Core.TFC_Climate;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.Player.FoodStatsTFC;
import com.bioxx.tfc.Core.Player.InventoryPlayerTFC;
import com.bioxx.tfc.Core.Player.PlayerInfo;
import com.bioxx.tfc.Core.Player.PlayerManagerTFC;
import com.bioxx.tfc.Entities.Mobs.EntityPigTFC;
import com.bioxx.tfc.GUI.GuiScreenHorseInventoryTFC;
import com.bioxx.tfc.Items.ItemQuiver;
import com.bioxx.tfc.Items.Tools.ItemChisel;
import com.bioxx.tfc.Items.Tools.ItemCustomHoe;
import com.bioxx.tfc.Items.Tools.ItemHammer;
import com.bioxx.tfc.WorldGen.DataLayer;
import com.bioxx.tfc.api.TFCAttributes;
import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.TFCOptions;
import com.bioxx.tfc.api.Tools.ChiselManager;

public class RenderOverlayHandler
{
	public static ResourceLocation tfcicons = new ResourceLocation(Reference.MOD_ID, Reference.ASSET_PATH_GUI + "icons.png");
	private FontRenderer fontrenderer;
	public int recordTimer;
	private final Field _recordPlayingUpFor = ReflectionHelper.findField(GuiIngame.class, "recordPlayingUpFor", "field_73845_h");
	private final Field _recordPlaying = ReflectionHelper.findField(GuiIngame.class, "recordPlaying", "field_73838_g");

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
		EntityPlayer player = mc.thePlayer;
		InventoryPlayer playerInventory = player.inventory;
		PlayerInfo playerInfo = PlayerManagerTFC.getInstance().getClientPlayer();

		int healthRowHeight = sr.getScaledHeight() - 40;
		int armorRowHeight = healthRowHeight - 10;
		int mid = sr.getScaledWidth() / 2;

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		TFC_Core.bindTexture(tfcicons);

		//Render Tool Mode
		if (TFCOptions.enableToolModeIndicator && playerInventory.getCurrentItem() != null && playerInfo != null)
		{
			Item currentItem = playerInventory.getCurrentItem().getItem();

			if (currentItem instanceof ItemCustomHoe)
			{
				int mode = playerInfo.hoeMode;
				this.drawTexturedModalRect(mid + 95, sr.getScaledHeight() - 21, 0 + (20 * mode), 38, 20, 20);
			}
			else if (currentItem instanceof ItemChisel)
			{
				boolean hasHammer = false;

				for (int i = 0; i < 9; i++)
				{
					if (playerInventory.mainInventory[i] != null && playerInventory.mainInventory[i].getItem() instanceof ItemHammer)
					{
						hasHammer = true;
						break;
					}
				}
				if (hasHammer)
				{
					int mode = playerInfo.chiselMode;
					TFC_Core.bindTexture(ChiselManager.getInstance().getResourceLocation(mode));
					this.drawTexturedModalRect(mid + 95, sr.getScaledHeight() - 21, ChiselManager.getInstance().getTextureU(mode), ChiselManager.getInstance().getTextureV(mode), 20, 20);
					TFC_Core.bindTexture(tfcicons);
				}
			}
		}

		//Render Arrow and Javelin for Quiver
		ItemStack quiverStack = getQuiver();
		Item quiver = quiverStack != null ? quiverStack.getItem() : null;
		if (quiver instanceof ItemQuiver)
		{
			fontrenderer = mc.fontRenderer;

			int xPos = 1;
			int yPos = sr.getScaledHeight() - 34;
			boolean leftSide = true;

			String pos = TFCOptions.quiverHUDPosition;

			if ("topright".equalsIgnoreCase(pos))
			{
				xPos = sr.getScaledWidth() - 19;
				yPos = 1;
				leftSide = false;
			}
			else if ("right".equalsIgnoreCase(pos))
			{
				xPos = sr.getScaledWidth() - 19;
				yPos = (sr.getScaledHeight() - 34) / 2;
				leftSide = false;
			}
			else if ("bottomright".equalsIgnoreCase(pos))
			{
				xPos = sr.getScaledWidth() - 19;
				yPos = sr.getScaledHeight() - 34;
				leftSide = false;
			}
			else if ("topleft".equalsIgnoreCase(pos))
			{
				xPos = 1;
				yPos = 1;
				leftSide = true;
			}
			else if ("left".equalsIgnoreCase(pos))
			{
				xPos = 1;
				yPos = (sr.getScaledHeight() - 34) / 2;
				leftSide = true;
			}

			this.drawTexturedModalRect(xPos, yPos, 0, 78, 16, 16);
			this.drawTexturedModalRect(xPos, yPos + 17, 0, 94, 16, 16);

			if (leftSide)
			{
				fontrenderer.drawString(Integer.toString(getQuiverArrows()), xPos + 19, yPos + 4, Color.white.getRGB());
				fontrenderer.drawString(Integer.toString(getQuiverJavelins()), xPos + 19, yPos + 21, Color.white.getRGB());
			}
			else
			{
				int arrowOffset = fontrenderer.getStringWidth(String.valueOf(getQuiverArrows())) + 1;
				int javOffset = fontrenderer.getStringWidth(String.valueOf(getQuiverJavelins())) + 1;

				fontrenderer.drawString(Integer.toString(getQuiverArrows()), xPos - arrowOffset, yPos + 4, Color.white.getRGB());
				fontrenderer.drawString(Integer.toString(getQuiverJavelins()), xPos - javOffset, yPos + 21, Color.white.getRGB());
			}

			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			TFC_Core.bindTexture(tfcicons);
		}

		if(mc.playerController.gameIsSurvivalOrAdventure())
		{
			//Draw Health
			this.drawTexturedModalRect(mid-91, healthRowHeight, 0, 0, 90, 10);
			float maxHealth = player.getMaxHealth();
			float percentHealth = Math.min(player.getHealth() / maxHealth, 1.0f);
			this.drawTexturedModalRect(mid-91, healthRowHeight, 0, 10, (int) (90*percentHealth), 10);

			//Draw Food and Water
			FoodStatsTFC foodstats = TFC_Core.getPlayerFoodStats(player);
			float foodLevel = foodstats.getFoodLevel();
			//float preFoodLevel = foodstats.getPrevFoodLevel();

			float waterLevel = foodstats.waterLevel;

			float percentFood = Math.min(foodLevel / foodstats.getMaxStomach(player), 1);
			float percentWater = Math.min(waterLevel / foodstats.getMaxWater(player), 1);

			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			this.drawTexturedModalRect(mid+1, healthRowHeight, 0, 20, 90, 5);
			if (playerInfo != null && playerInfo.guishowFoodRestoreAmount)
			{
				float percentFood2 = Math.min(percentFood + playerInfo.guiFoodRestoreAmount / foodstats.getMaxStomach(player), 1);
				GL11.glColor4f(0.0F, 0.6F, 0.0F, 0.3F);
				this.drawTexturedModalRect(mid+1, healthRowHeight, 0, 25, (int) (90*(percentFood2)), 5);
			}
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

			this.drawTexturedModalRect(mid+1, healthRowHeight, 0, 25, (int) (90*percentFood), 5);

			this.drawTexturedModalRect(mid+1, healthRowHeight+5, 90, 20, 90, 5);
			this.drawTexturedModalRect(mid+1, healthRowHeight+5, 90, 25, (int) (90*percentWater), 5);

			//Draw Notifications
			String healthString = (int) Math.min(player.getHealth(), maxHealth) + "/" + (int) maxHealth;
			mc.fontRenderer.drawString(healthString, mid-45-(mc.fontRenderer.getStringWidth(healthString)/2), healthRowHeight+2, Color.white.getRGB());
			if (player.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getModifier(TFCAttributes.OVERBURDENED_UUID) != null)
				mc.fontRenderer.drawString(TFC_Core.translate("gui.overburdened"), mid-(mc.fontRenderer.getStringWidth(TFC_Core.translate("gui.overburdened"))/2), healthRowHeight-20, Color.red.getRGB());

			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			TFC_Core.bindTexture(new ResourceLocation("minecraft:textures/gui/icons.png"));

			//Draw experience bar when not riding anything, riding a non-living entity such as a boat/minecart, or riding a pig.
			if (!(player.ridingEntity instanceof EntityLiving) || player.ridingEntity instanceof EntityPigTFC)
			{
				int cap = 0;
				cap = player.xpBarCap();
				int left = mid - 91;

				if (cap > 0)
				{
					short barWidth = 182;
					int filled = (int) (player.experience * (barWidth + 1));
					int top = sr.getScaledHeight() - 29;
					drawTexturedModalRect(left, top, 0, 64, barWidth, 5);
					if (filled > 0)
						drawTexturedModalRect(left, top, 0, 69, filled, 5);
				}

				if (player.experienceLevel > 0)
				{
					fontrenderer = mc.fontRenderer;
					boolean flag1 = false;
					int color = flag1 ? 16777215 : 8453920;
					String text = Integer.toString(player.experienceLevel);
					int x = (sr.getScaledWidth() - fontrenderer.getStringWidth(text)) / 2;
					int y = sr.getScaledHeight() - 30;
					fontrenderer.drawString(text, x + 1, y, 0);
					fontrenderer.drawString(text, x - 1, y, 0);
					fontrenderer.drawString(text, x, y + 1, 0);
					fontrenderer.drawString(text, x, y - 1, 0);
					fontrenderer.drawString(text, x, y, color);
				}

				// We have to reset the color back to white
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			}

			// Don't show the dismount message if it was triggered by the "mounting" from opening a horse inventory.
			if (mc.currentScreen instanceof GuiScreenHorseInventoryTFC)
			{
				recordTimer = 0;
				try
				{
					_recordPlayingUpFor.setInt(mc.ingameGUI, 0);
				} catch (Exception e)
				{
					throw new RuntimeException(e);
				}
			}

			// Draw mount's health bar
			if (player.ridingEntity instanceof EntityLivingBase)
			{
				GuiIngameForge.renderHealthMount = false;
				TFC_Core.bindTexture(tfcicons);
				EntityLivingBase mount = ((EntityLivingBase) player.ridingEntity);
				this.drawTexturedModalRect(mid+1, armorRowHeight, 90, 0, 90, 10);
				double mountMaxHealth = mount.getEntityAttribute(SharedMonsterAttributes.maxHealth).getBaseValue();
				double mountCurrentHealth = mount.getHealth();
				float mountPercentHealth = (float)Math.min(mountCurrentHealth/mountMaxHealth, 1.0f);
				this.drawTexturedModalRect(mid+1, armorRowHeight, 90, 10, (int) (90*mountPercentHealth), 10);

				String mountHealthString = (int) Math.min(mountCurrentHealth, mountMaxHealth) + "/" + (int) mountMaxHealth;
				mc.fontRenderer.drawString(mountHealthString, mid + 47 - (mc.fontRenderer.getStringWidth(mountHealthString) / 2), armorRowHeight + 2, Color.white.getRGB());
				renderDismountOverlay(mc, mid, sr.getScaledHeight(), event.partialTicks);
			}

			TFC_Core.bindTexture(new ResourceLocation("minecraft:textures/gui/icons.png"));
		}
	}

	/*
	 * Based on net.minecraftforge.client.GuiIngameForge.renderRecordOverlay
	 * which is used to render both the pop-up from playing a record,
	 * and the "Press 'sneak' to dismount" message.
	 * We only care about dismounting, and not record playing for this method.
	 */
	protected void renderDismountOverlay(Minecraft mc, int midpoint, int height, float partialTicks)
	{
		if (recordTimer == 0)
		{
			try
			{
				recordTimer = _recordPlayingUpFor.getInt(mc.ingameGUI); // Copy the vanilla timer
				_recordPlayingUpFor.setInt(mc.ingameGUI, 0); // Reset the vanilla timer so the vanilla overlay doesn't appear

			} catch (Exception e)
			{
				throw new RuntimeException(e);
			}
		}

		if (recordTimer > 0)
		{
			float hue = recordTimer - partialTicks;
			int opacity = (int) (hue * 256.0F / 20.0F);
			if (opacity > 255)
				opacity = 255;

			if (opacity > 0)
			{
				try
				{
					String recordPlaying = (String) _recordPlaying.get(mc.ingameGUI);

					GL11.glPushMatrix();
					GL11.glTranslatef(midpoint, height - 48, 0.0F);
					GL11.glEnable(GL11.GL_BLEND);
					OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
					mc.fontRenderer.drawString(recordPlaying, -mc.fontRenderer.getStringWidth(recordPlaying) / 2, -12, 0xFFFFFF | (opacity << 24));
					GL11.glDisable(GL11.GL_BLEND);
					GL11.glPopMatrix();

				} catch (Exception e)
				{
					throw new RuntimeException(e);
				}
			}

			recordTimer--;
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
			DataLayer evt = TFC_Climate.getCacheManager(mc.theWorld).getEVTLayerAt(xCoord, zCoord);
			event.left.add(String.format("rain: %.0f, temp: %.2f, average bio temp: %.2f, evt: %.3f", new Object[] {
					TFC_Climate.getRainfall(mc.theWorld, xCoord, yCoord, zCoord), 
					TFC_Climate.getHeightAdjustedTemp(mc.theWorld, xCoord, yCoord, zCoord),
					TFC_Climate.getBioTemperatureHeight(mc.theWorld, xCoord, yCoord, zCoord),
					evt.floatdata1}));

			if(TFCOptions.enableDebugMode)
			{
				event.left.add("Stability: " + TFC_Climate.getStability(mc.theWorld, xCoord, zCoord) + 
								", Drainage: " + TFC_Climate.getCacheManager(mc.theWorld).getDrainageLayerAt(xCoord, zCoord).getName() +
								", pH: " + TFC_Climate.getCacheManager(mc.theWorld).getPHLayerAt(xCoord, zCoord).getName());
				event.left.add("Rock Layers: " + TFC_Climate.getCacheManager(mc.theWorld).getRockLayerAt(xCoord, zCoord, 0).getName() + ", " +
								TFC_Climate.getCacheManager(mc.theWorld).getRockLayerAt(xCoord, zCoord, 1).getName() + ", " +
								TFC_Climate.getCacheManager(mc.theWorld).getRockLayerAt(xCoord, zCoord, 2).getName());
				event.left.add("Tree Layers: " + TFC_Climate.getCacheManager(mc.theWorld).getTreeLayerAt(xCoord, zCoord, 0).getName() + ", " +
								TFC_Climate.getCacheManager(mc.theWorld).getTreeLayerAt(xCoord, zCoord, 1).getName() + ", " +
								TFC_Climate.getCacheManager(mc.theWorld).getTreeLayerAt(xCoord, zCoord, 2).getName());
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
		return ((ItemQuiver)(TFCItems.quiver)).getQuiverArrows(getQuiver());
	}

	private int getQuiverJavelins()
	{
		return ((ItemQuiver)(TFCItems.quiver)).getQuiverJavelins(getQuiver());
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
