package com.bioxx.tfc.GUI;

import java.util.Collection;
import java.util.Iterator;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Textures;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.Core.Player.PlayerInventory;
import com.bioxx.tfc.Food.ItemMeal;
import com.bioxx.tfc.Food.TFCPotion;
import com.bioxx.tfc.api.Food;
import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.Interfaces.IFood;
import com.bioxx.tfc.api.Tools.IKnife;

public class GuiInventoryTFC extends InventoryEffectRenderer
{
	private float xSizeLow;
	private float ySizeLow;
	private boolean hasEffect;
	protected static final ResourceLocation UPPER_TEXTURE = new ResourceLocation(Reference.MOD_ID+":textures/gui/inventory.png");
	protected static final ResourceLocation UPPER_TEXTURE_2X2 = new ResourceLocation(Reference.MOD_ID+":textures/gui/gui_inventory2x2.png");
	protected static final ResourceLocation EFFECTS_TEXTURE = new ResourceLocation(Reference.MOD_ID+":textures/gui/inv_effects.png");
	protected EntityPlayer player;
	protected Slot activeSlot;

	public GuiInventoryTFC(EntityPlayer player)
	{
		super(player.inventoryContainer);
		this.allowUserInput = true;
		player.addStat(AchievementList.openInventory, 1);
		xSize = 176;
		ySize = 85 + PlayerInventory.invYSize;
		this.player = player;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		if(player.getEntityData().hasKey("craftingTable"))
			TFC_Core.bindTexture(UPPER_TEXTURE);
		else
			TFC_Core.bindTexture(UPPER_TEXTURE_2X2);
		int k = this.guiLeft;
		int l = this.guiTop;
		this.drawTexturedModalRect(k, l, 0, 0, this.xSize, 86);
		//Draw the player avatar
		drawPlayerModel(k + 51, l + 75, 30, k + 51 - this.xSizeLow, l + 75 - 50 - this.ySizeLow, this.mc.thePlayer);

		PlayerInventory.drawInventory(this, width, height, ySize - PlayerInventory.invYSize);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

	@Override
	public void drawTexturedModelRectFromIcon(int i, int j, IIcon icon, int w, int h)
	{
		Tessellator tessellator = Tessellator.instance;
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(i + 0, j + h, this.zLevel, icon.getMinU(), icon.getMaxV());
		tessellator.addVertexWithUV(i + w, j + h, this.zLevel, icon.getMaxU(), icon.getMaxV());
		tessellator.addVertexWithUV(i + w, j + 0, this.zLevel, icon.getMaxU(), icon.getMinV());
		tessellator.addVertexWithUV(i + 0, j + 0, this.zLevel, icon.getMinU(), icon.getMinV());
		tessellator.draw();
		GL11.glDisable(GL11.GL_BLEND);
	}

	public static void drawPlayerModel(int par0, int par1, int par2, float par3, float par4, EntityLivingBase par5EntityLivingBase)
	{
		GL11.glEnable(GL11.GL_COLOR_MATERIAL);
		GL11.glPushMatrix();
		GL11.glTranslatef(par0, par1, 50.0F);
		GL11.glScalef(-par2, par2, par2);
		GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
		float f2 = par5EntityLivingBase.renderYawOffset;
		float f3 = par5EntityLivingBase.rotationYaw;
		float f4 = par5EntityLivingBase.rotationPitch;
		float f5 = par5EntityLivingBase.prevRotationYawHead;
		float f6 = par5EntityLivingBase.rotationYawHead;
		GL11.glRotatef(135.0F, 0.0F, 1.0F, 0.0F);
		RenderHelper.enableStandardItemLighting();
		GL11.glRotatef(-135.0F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(-((float)Math.atan(par4 / 40.0F)) * 20.0F, 1.0F, 0.0F, 0.0F);
		par5EntityLivingBase.renderYawOffset = (float)Math.atan(par3 / 40.0F) * 20.0F;
		par5EntityLivingBase.rotationYaw = (float)Math.atan(par3 / 40.0F) * 40.0F;
		par5EntityLivingBase.rotationPitch = -((float)Math.atan(par4 / 40.0F)) * 20.0F;
		par5EntityLivingBase.rotationYawHead = par5EntityLivingBase.rotationYaw;
		par5EntityLivingBase.prevRotationYawHead = par5EntityLivingBase.rotationYaw;
		GL11.glTranslatef(0.0F, par5EntityLivingBase.yOffset, 0.0F);
		RenderManager.instance.playerViewY = 180.0F;
		RenderManager.instance.renderEntityWithPosYaw(par5EntityLivingBase, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
		par5EntityLivingBase.renderYawOffset = f2;
		par5EntityLivingBase.rotationYaw = f3;
		par5EntityLivingBase.rotationPitch = f4;
		par5EntityLivingBase.prevRotationYawHead = f5;
		par5EntityLivingBase.rotationYawHead = f6;
		GL11.glPopMatrix();
		RenderHelper.disableStandardItemLighting();
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		//this.fontRendererObj.drawString(I18n.format("container.crafting", new Object[0]), 86, 7, 4210752);
	}

	@Override
	/**
	 * Called from the main game loop to update the screen.
	 */
	public void updateScreen()
	{
		if (this.mc.playerController.isInCreativeMode())
			this.mc.displayGuiScreen(new GuiContainerCreativeTFC(player));
	}

	@Override
	public void initGui()
	{
		super.buttonList.clear();

		if (this.mc.playerController.isInCreativeMode())
			this.mc.displayGuiScreen(new GuiContainerCreativeTFC(this.mc.thePlayer));
		else
			super.initGui();

		if (!this.mc.thePlayer.getActivePotionEffects().isEmpty())
		{
			//this.guiLeft = 160 + (this.width - this.xSize - 200) / 2;
			this.guiLeft = (this.width - this.xSize) / 2;
			this.hasEffect = true;
		}

		buttonList.clear();
		buttonList.add(new GuiInventoryButton(0, guiLeft+176, guiTop + 3, 25, 20, 
				0, 86, 25, 20, TFC_Core.translate("gui.Inventory.Inventory"), TFC_Textures.guiInventory));
		buttonList.add(new GuiInventoryButton(1, guiLeft+176, guiTop + 22, 25, 20, 
				0, 86, 25, 20, TFC_Core.translate("gui.Inventory.Skills"), TFC_Textures.guiSkills));
		buttonList.add(new GuiInventoryButton(2, guiLeft+176, guiTop + 41, 25, 20, 
				0, 86, 25, 20, TFC_Core.translate("gui.Calendar.Calendar"), TFC_Textures.guiCalendar));
		buttonList.add(new GuiInventoryButton(3, guiLeft+176, guiTop + 60, 25, 20, 
				0, 86, 25, 20, TFC_Core.translate("gui.Inventory.Health"), TFC_Textures.guiHealth));
	}

	@Override
	protected void actionPerformed(GuiButton guibutton)
	{
		if (guibutton.id == 1)
			Minecraft.getMinecraft().displayGuiScreen(new GuiSkills(player));
		else if (guibutton.id == 2)
			Minecraft.getMinecraft().displayGuiScreen(new GuiCalendar(player));
		else if (guibutton.id == 3)
			Minecraft.getMinecraft().displayGuiScreen(new GuiHealth(player));
	}

	@Override
	public void drawScreen(int par1, int par2, float par3)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		super.drawScreen(par1, par2, par3);
		this.xSizeLow = par1;
		this.ySizeLow = par2;
		if(hasEffect)
			displayDebuffEffects();

		for (int j1 = 0; j1 < this.inventorySlots.inventorySlots.size(); ++j1)
		{
			Slot slot = (Slot)this.inventorySlots.inventorySlots.get(j1);
			if (this.isMouseOverSlot(slot, par1, par2) && slot.func_111238_b())
				this.activeSlot = slot;
		}
	}

	protected boolean isMouseOverSlot(Slot par1Slot, int par2, int par3)
	{
		return this.func_146978_c/*isPointInRegion*/(par1Slot.xDisplayPosition, par1Slot.yDisplayPosition, 16, 16, par2, par3);
	}

	/**
	 * Displays debuff/potion effects that are currently being applied to the player
	 */
	private void displayDebuffEffects()
	{
		int var1 = this.guiLeft - 124;
		int var2 = this.guiTop;
		Collection var4 = this.mc.thePlayer.getActivePotionEffects();

		if (!var4.isEmpty())
		{
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glDisable(GL11.GL_LIGHTING);
			int var6 = 33;

			if (var4.size() > 5)
				var6 = 132 / (var4.size() - 1);

			for (Iterator var7 = this.mc.thePlayer.getActivePotionEffects().iterator(); var7.hasNext(); var2 += var6)
			{
				PotionEffect var8 = (PotionEffect)var7.next();
				Potion var9 = Potion.potionTypes[var8.getPotionID()] instanceof TFCPotion ? 
						((TFCPotion) Potion.potionTypes[var8.getPotionID()]) : 
							Potion.potionTypes[var8.getPotionID()];
						GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
						TFC_Core.bindTexture(EFFECTS_TEXTURE);
						this.drawTexturedModalRect(var1, var2, 0, 166, 140, 32);

						if (var9.hasStatusIcon())
						{
							int var10 = var9.getStatusIconIndex();
							this.drawTexturedModalRect(var1 + 6, var2 + 7, 0 + var10 % 8 * 18, 198 + var10 / 8 * 18, 18, 18);
						}

						String var12 = TFC_Core.translate(var9.getName());

						if (var8.getAmplifier() == 1)
							var12 = var12 + " II";
						else if (var8.getAmplifier() == 2)
							var12 = var12 + " III";
						else if (var8.getAmplifier() == 3)
							var12 = var12 + " IV";

						this.fontRendererObj.drawStringWithShadow(var12, var1 + 10 + 18, var2 + 6, 16777215);
						String var11 = Potion.getDurationString(var8);
						this.fontRendererObj.drawStringWithShadow(var11, var1 + 10 + 18, var2 + 6 + 10, 8355711);
			}
		}
	}

	private long spamTimer;
	@Override
	protected boolean checkHotbarKeys(int keycode)
	{
		if(this.activeSlot != null && this.activeSlot.slotNumber == 0 && this.activeSlot.getHasStack() &&
				this.activeSlot.getStack().getItem() instanceof IFood)
			return false;
		//Here is the code for quick stacking food
		if(keycode == 31 && activeSlot != null && activeSlot.canTakeStack(player) && activeSlot.getHasStack() && 
				activeSlot.getStack() != null && activeSlot.getStack().getItem() instanceof IFood && TFC_Time.getTotalTicks() > spamTimer+5)
		{
			spamTimer = TFC_Time.getTotalTicks();
			Item iType = activeSlot.getStack().getItem();
			ItemStack activeIS = activeSlot.getStack();
			for(int i = 9; i < 45 && getEmptyCraftSlot() != -1; i++)
			{
				ItemStack is = this.inventorySlots.getSlot(i).getStack();
				if (is != null && is.getItem() == iType && Food.areEqual(activeIS, is) && Food.getWeight(is) < Global.FOOD_MAX_WEIGHT)
					this.handleMouseClick(this.inventorySlots.getSlot(i), i, getEmptyCraftSlot(), 7);
			}

			if(this.inventorySlots.getSlot(0).getStack() != null)
			{
				this.handleMouseClick(this.inventorySlots.getSlot(0), 0, 0, 1);
			}
			return true;
		}
		else if(keycode == 32 && TFC_Time.getTotalTicks() > spamTimer+5)
		{
			spamTimer = TFC_Time.getTotalTicks();
			int knifeSlot = -1;
			if(!getCraftingHasKnife())
			{
				for(int i = 9; i < 45 && getEmptyCraftSlot() != -1; i++)
				{
					ItemStack is = this.inventorySlots.getSlot(i).getStack();
					if(is != null && is.getItem() instanceof IKnife)
					{
						knifeSlot = i;
						break;
					}
				}
			}
			for(int i = 9; i < 45 && getEmptyCraftSlot() != -1 && knifeSlot != -1 && inventorySlots.getSlot(knifeSlot).getStack() != null; i++)
			{
				ItemStack is = this.inventorySlots.getSlot(i).getStack();
				int knifeDamage = inventorySlots.getSlot(knifeSlot).getStack().getItemDamage();
				if(knifeDamage >= inventorySlots.getSlot(knifeSlot).getStack().getMaxDamage())
					break;
				if (is != null && !(is.getItem() instanceof ItemMeal) && is.getItem() instanceof IFood && Food.getDecay(is) > 0 &&
						Food.getDecayTimer(is) >= TFC_Time.getTotalHours())
				{
					this.handleMouseClick(this.inventorySlots.getSlot(i), i, getEmptyCraftSlot(), 7);
					this.handleMouseClick(this.inventorySlots.getSlot(0), 0, 0, 1);
				}
			}
			return true;
		}
		else return super.checkHotbarKeys(keycode);
	}

	private int getEmptyCraftSlot()
	{
		if(this.inventorySlots.getSlot(4).getStack() == null)
			return 4;
		if(this.inventorySlots.getSlot(1).getStack() == null)
			return 1;
		if(this.inventorySlots.getSlot(2).getStack() == null)
			return 2;
		if(this.inventorySlots.getSlot(3).getStack() == null)
			return 3;
		if(player.getEntityData().hasKey("craftingTable"))
		{
			if(this.inventorySlots.getSlot(45).getStack() == null)
				return 45;
			if(this.inventorySlots.getSlot(46).getStack() == null)
				return 46;
			if(this.inventorySlots.getSlot(47).getStack() == null)
				return 47;
			if(this.inventorySlots.getSlot(48).getStack() == null)
				return 48;
			if(this.inventorySlots.getSlot(49).getStack() == null)
				return 49;
		}

		return -1;
	}

	private boolean getCraftingHasKnife()
	{
		if(this.inventorySlots.getSlot(4).getStack() != null && this.inventorySlots.getSlot(4).getStack().getItem() instanceof IKnife)
			return true;
		if(this.inventorySlots.getSlot(1).getStack() != null && this.inventorySlots.getSlot(1).getStack().getItem() instanceof IKnife)
			return true;
		if(this.inventorySlots.getSlot(2).getStack() != null && this.inventorySlots.getSlot(2).getStack().getItem() instanceof IKnife)
			return true;
		if(this.inventorySlots.getSlot(3).getStack() != null && this.inventorySlots.getSlot(3).getStack().getItem() instanceof IKnife)
			return true;
		if(player.getEntityData().hasKey("craftingTable"))
		{
			if(this.inventorySlots.getSlot(45).getStack() != null && this.inventorySlots.getSlot(45).getStack().getItem() instanceof IKnife)
				return true;
			if(this.inventorySlots.getSlot(46).getStack() != null && this.inventorySlots.getSlot(46).getStack().getItem() instanceof IKnife)
				return true;
			if(this.inventorySlots.getSlot(47).getStack() != null && this.inventorySlots.getSlot(47).getStack().getItem() instanceof IKnife)
				return true;
			if(this.inventorySlots.getSlot(48).getStack() != null && this.inventorySlots.getSlot(48).getStack().getItem() instanceof IKnife)
				return true;
			if(this.inventorySlots.getSlot(49).getStack() != null && this.inventorySlots.getSlot(49).getStack().getItem() instanceof IKnife)
				return true;
		}

		return false;
	}
}
