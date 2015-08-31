package com.bioxx.tfc.GUI;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Containers.ContainerAnvil;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Textures;
import com.bioxx.tfc.TileEntities.TEAnvil;
import com.bioxx.tfc.api.TFCOptions;
import com.bioxx.tfc.api.Crafting.AnvilManager;
import com.bioxx.tfc.api.Crafting.PlanRecipe;
import com.bioxx.tfc.api.Enums.RuleEnum;

public class GuiAnvil extends GuiContainerTFC
{
	public TEAnvil anvilTE;
	public static ResourceLocation texture = new ResourceLocation(Reference.MOD_ID, Reference.ASSET_PATH_GUI + "gui_anvil.png");
	private EntityPlayer player;
	private int x, y, z;
	private String plan = "";
	private ItemStack input;
	private ItemStack input2;

	public GuiAnvil(InventoryPlayer inventoryplayer, TEAnvil te, World world, int x, int y, int z)
	{
		super(new ContainerAnvil(inventoryplayer, te, world, x, y, z), 208, 117);
		anvilTE = te;
		player = inventoryplayer.player;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public void initGui()
	{
		super.initGui();

		buttonList.clear();
		buttonList.add(new GuiAnvilButton(7, guiLeft + 123, guiTop + 82, 16, 16, TFC_Textures.anvilShrink, 208, 17, 16, 16, this, TFC_Core.translate("gui.Anvil.Shrink")));
		buttonList.add(new GuiAnvilButton(6, guiLeft + 105, guiTop + 82, 16, 16, TFC_Textures.anvilUpset, 208, 17, 16, 16, this, TFC_Core.translate("gui.Anvil.Upset")));
		buttonList.add(new GuiAnvilButton(5, guiLeft + 123, guiTop + 64, 16, 16, TFC_Textures.anvilBend, 208, 17, 16, 16, this, TFC_Core.translate("gui.Anvil.Bend")));
		buttonList.add(new GuiAnvilButton(4, guiLeft + 105, guiTop + 64, 16, 16, TFC_Textures.anvilPunch, 208, 17, 16, 16, this, TFC_Core.translate("gui.Anvil.Punch")));
		buttonList.add(new GuiAnvilButton(3, guiLeft + 87, guiTop + 82, 16, 16, TFC_Textures.anvilDraw, 208, 33, 16, 16, this, TFC_Core.translate("gui.Anvil.Draw")));
		buttonList.add(new GuiAnvilButton(2, guiLeft + 69, guiTop + 82, 16, 16, TFC_Textures.anvilHitHeavy, 208, 33, 16, 16, this, TFC_Core.translate("gui.Anvil.HeavyHit")));
		buttonList.add(new GuiAnvilButton(1, guiLeft + 87, guiTop + 64, 16, 16, TFC_Textures.anvilHitMedium, 208, 33, 16, 16, this, TFC_Core.translate("gui.Anvil.MediumHit")));
		buttonList.add(new GuiAnvilButton(0, guiLeft + 69, guiTop + 64, 16, 16, TFC_Textures.anvilHitLight, 208, 33, 16, 16, this, TFC_Core.translate("gui.Anvil.LightHit")));
		buttonList.add(new GuiButton(8, guiLeft + 13, guiTop + 53, 36, 20, TFC_Core.translate("gui.Anvil.Weld")));
		buttonList.add(new GuiAnvilButton(9, guiLeft + 113, guiTop + 7, 19, 21, 208, 49, 19, 21, this, 2, TFCOptions.anvilRuleColor2[0], TFCOptions.anvilRuleColor2[1], TFCOptions.anvilRuleColor2[2]));
		buttonList.add(new GuiAnvilButton(10, guiLeft + 94, guiTop + 7, 19, 21, 208, 49, 19, 21, this, 1, TFCOptions.anvilRuleColor1[0], TFCOptions.anvilRuleColor1[1], TFCOptions.anvilRuleColor1[2]));
		buttonList.add(new GuiAnvilButton(11, guiLeft + 75, guiTop + 7, 19, 21, 208, 49, 19, 21, this, 0, TFCOptions.anvilRuleColor0[0], TFCOptions.anvilRuleColor0[1], TFCOptions.anvilRuleColor0[2]));
		buttonList.add(new GuiAnvilPlanButton(12, guiLeft + 122, guiTop + 45, 18, 18, this, TFC_Core.translate("gui.Anvil.Plans")));
	}

	@Override
	public void updateScreen()
	{
		super.updateScreen();
		if (anvilTE != null) // Fixes NPE
		{
			String craftingPlan = this.anvilTE.craftingPlan;
			ItemStack stack1 = this.anvilTE.anvilItemStacks[TEAnvil.INPUT1_SLOT];
			ItemStack stack2 = this.anvilTE.anvilItemStacks[TEAnvil.INPUT2_SLOT];

			if (craftingPlan != null && craftingPlan != plan || 
					stack1 != null && stack1 != input || 
					stack2 != null && stack2 != input2) // Fixes NPE
			{
				plan = this.anvilTE.craftingPlan;
				this.anvilTE.updateRecipe();
				input = this.anvilTE.anvilItemStacks[TEAnvil.INPUT1_SLOT];
				input2 = this.anvilTE.anvilItemStacks[TEAnvil.INPUT2_SLOT];
			}
		}
	}

	@Override
	protected void actionPerformed(GuiButton guibutton)
	{
		if (guibutton.id == 0)
			anvilTE.actionLightHammer();
		else if (guibutton.id == 2)
			anvilTE.actionHeavyHammer();
		else if (guibutton.id == 1)
			anvilTE.actionHammer();
		else if (guibutton.id == 3)
			anvilTE.actionDraw();
		else if (guibutton.id == 4)
			anvilTE.actionPunch();
		else if (guibutton.id == 5)
			anvilTE.actionBend();
		else if (guibutton.id == 6)
			anvilTE.actionUpset();
		else if (guibutton.id == 7)
			anvilTE.actionShrink();
		else if (guibutton.id == 8)
			anvilTE.actionWeld();
		else if (guibutton.id == 12 && this.anvilTE.anvilItemStacks[TEAnvil.INPUT1_SLOT] != null)
			player.openGui(TerraFirmaCraft.instance, 24, player.worldObj, x, y, z);
		this.inventorySlots.detectAndSendChanges();
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		this.drawGui(texture);
	}

	@Override
	protected void drawForeground(int guiLeft, int guiTop)
	{
		if (anvilTE != null)
		{
			int i1 = anvilTE.getCraftingValue();
			drawTexturedModalRect(guiLeft + 27 + i1, guiTop + 103, 213, 10, 5, 5);

			i1 = anvilTE.getItemCraftingValue();
			drawTexturedModalRect(guiLeft + 27 + i1, guiTop + 108, 208, 10, 5, 6);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.5F);
			//Round to 1 decimal place XX.X%
			if (anvilTE.workRecipe != null)
			{
				int s0 = (int) (anvilTE.workRecipe.getSkillMult(player) * 1000);
				float s1 = s0 / 10f;
				fontRendererObj.drawString("Skill: " + s1 + "%", guiLeft + 150, guiTop + 8, 0xff6000);
			}

			drawItemRulesImages(guiLeft, guiTop);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.5F);
			drawRulesImages(guiLeft, guiTop);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.5F);
		}
	}

	@Override
	public void drawTooltip(int mx, int my, String text)
	{
		List<String> list = new ArrayList<String>();
		list.add(text);
		this.drawHoveringText(list, mx, my + 15, this.fontRendererObj);
		RenderHelper.disableStandardItemLighting();
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
	}

	public void drawItemRulesImages(int w, int h)
	{
		if (anvilTE != null && anvilTE.itemCraftingRules != null)
		{
			PlanRecipe p = AnvilManager.getInstance().getPlan(anvilTE.craftingPlan);
			if (p == null)
				return;
			RuleEnum[] rules = anvilTE.workRecipe != null ? p.rules : null;
			int[] itemRules = anvilTE.getItemRules();

			this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
			this.drawTexturedModelRectFromIcon(w + 80, h + 31, getIconFromRule(itemRules[0]), 10, 10);
			this.drawTexturedModelRectFromIcon(w + 99, h + 31, getIconFromRule(itemRules[1]), 10, 10);
			this.drawTexturedModelRectFromIcon(w + 118, h + 31, getIconFromRule(itemRules[2]), 10, 10);

			this.mc.getTextureManager().bindTexture(texture);

			if (rules != null && rules[0].matches(itemRules, 0))
				GL11.glColor4f(0.0F, 1.0F, 0.0F, 1.0F);
			drawTexturedModalRect(w + 77, h + 28, 210, 115, 15, 15);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

			if (rules != null && rules[1].matches(itemRules, 1))
				GL11.glColor4f(0.0F, 1.0F, 0.0F, 1.0F);
			drawTexturedModalRect(w + 96, h + 28, 210, 115, 15, 15);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

			if (rules != null && rules[2].matches(itemRules, 2))
				GL11.glColor4f(0.0F, 1.0F, 0.0F, 1.0F);
			drawTexturedModalRect(w + 115, h + 28, 210, 115, 15, 15);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		}

		//GL11.glColor4f(1.0F, 0.0F, 0.0F, 1.0F);
		//drawTexturedModalRect(w + 75, h + 7, 208, 49, 19, 21);
	}

	public void drawRulesImages(int w, int h)
	{
		if (anvilTE.workRecipe != null)
		{
			PlanRecipe p = AnvilManager.getInstance().getPlan(anvilTE.craftingPlan);
			if (p == null)
				return;
			RuleEnum[] rules = p.rules;
			//int[] itemRules = anvilTE.getItemRules();

			TFC_Core.bindTexture(TextureMap.locationBlocksTexture);
			this.drawTexturedModelRectFromIcon(w + 80, h + 10, getIconFromRule(rules[0].Action), 10, 10);
			this.drawTexturedModelRectFromIcon(w + 99, h + 10, getIconFromRule(rules[1].Action), 10, 10);
			this.drawTexturedModelRectFromIcon(w + 118, h + 10, getIconFromRule(rules[2].Action), 10, 10);

			TFC_Core.bindTexture(texture);
			//Bottom Row
			GL11.glColor4ub(TFCOptions.anvilRuleColor0[0], TFCOptions.anvilRuleColor0[1], TFCOptions.anvilRuleColor0[2], (byte) 255);
			if (rules[0].Min == 0)
				drawTexturedModalRect(w + 75, h + 26, 228, 68, 19, 3);
			if (rules[0].Max > 0 && (rules[0].Min <= 1 || rules[0].Max == 1))
				drawTexturedModalRect(w + 94, h + 26, 228, 68, 19, 3);
			if (rules[0].Max > 1 && (rules[0].Min <= 2 || rules[0].Max == 2))
				drawTexturedModalRect(w + 113, h + 26, 228, 68, 19, 3);
			//Middle Row
			GL11.glColor4ub(TFCOptions.anvilRuleColor1[0], TFCOptions.anvilRuleColor1[1], TFCOptions.anvilRuleColor1[2], (byte) 255);
			if (rules[1].Min == 0)
				drawTexturedModalRect(w + 75, h + 24, 228, 68, 19, 3);
			if (rules[1].Max > 0 && (rules[1].Min <= 1 || rules[1].Max == 1))
				drawTexturedModalRect(w + 94, h + 24, 228, 68, 19, 3);
			if (rules[1].Max > 1 && (rules[1].Min <= 2 || rules[1].Max == 2))
				drawTexturedModalRect(w + 113, h + 24, 228, 68, 19, 3);
			//Top Row
			GL11.glColor4ub(TFCOptions.anvilRuleColor2[0], TFCOptions.anvilRuleColor2[1], TFCOptions.anvilRuleColor2[2], (byte) 255);
			if (rules[2].Min == 0)
				drawTexturedModalRect(w + 75, h + 22, 228, 68, 19, 3);
			if (rules[2].Max > 0 && (rules[2].Min <= 1 || rules[2].Max == 1))
				drawTexturedModalRect(w + 94, h + 22, 228, 68, 19, 3);
			if (rules[2].Max > 1 && (rules[2].Min <= 2 || rules[2].Max == 2))
				drawTexturedModalRect(w + 113, h + 22, 228, 68, 19, 3);
		}

		//GL11.glColor4f(1.0F, 0.0F, 0.0F, 1.0F);
		//drawTexturedModalRect(w + 75, h + 7, 208, 49, 19, 21);
	}

	public IIcon getIconFromRule(int action)
	{
		switch (action)
		{
		case 0:
			return TFC_Textures.anvilHit;
		case 1:
			return TFC_Textures.anvilDraw;
		case 3:
			return TFC_Textures.anvilPunch;
		case 4:
			return TFC_Textures.anvilBend;
		case 5:
			return TFC_Textures.anvilUpset;
		case 6:
			return TFC_Textures.anvilShrink;
		default:
			return TFC_Textures.invisibleTexture;
		}
	}

	@Override
	protected boolean func_146978_c/*isPointInRegion*/(int slotX, int slotY, int sizeX, int sizeY, int clickX, int clickY)
	{
		int k1 = this.guiLeft;
		int l1 = this.guiTop;
		clickX -= k1;
		clickY -= l1;
		return clickX >= slotX - 1 && clickX < slotX + sizeX + 1 && clickY >= slotY - 1 && clickY < slotY + sizeY + 1;
	}

	/**
	 * Draws a textured rectangle at the stored z-value. Args: x, y, u, v, width, height
	 */
	public void drawTexturedModalRect(int drawX, int drawY, int drawWidth, int drawHeight, int u, int v, int width, int height)
	{
		float f = 0.00390625F;
		float f1 = 0.00390625F;
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(drawX + 0, drawY + drawHeight, this.zLevel, (u + 0) * f, (v + height) * f1);
		tessellator.addVertexWithUV(drawX + drawWidth, drawY + drawHeight, this.zLevel, (u + width) * f, (v + height) * f1);
		tessellator.addVertexWithUV(drawX + drawWidth, drawY + 0, this.zLevel, (u + width) * f, (v + 0) * f1);
		tessellator.addVertexWithUV(drawX + 0, drawY + 0, this.zLevel, (u + 0) * f, (v + 0) * f1);
		tessellator.draw();
	}

	@Override
	public void drawTexturedModelRectFromIcon(int x, int y, IIcon par3Icon, int width, int height)
	{
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(x + 0, y + height, this.zLevel, par3Icon.getMinU(), par3Icon.getMaxV());
		tessellator.addVertexWithUV(x + width, y + height, this.zLevel, par3Icon.getMaxU(), par3Icon.getMaxV());
		tessellator.addVertexWithUV(x + width, y + 0, this.zLevel, par3Icon.getMaxU(), par3Icon.getMinV());
		tessellator.addVertexWithUV(x + 0, y + 0, this.zLevel, par3Icon.getMinU(), par3Icon.getMinV());
		tessellator.draw();
	}
}
