package TFC.GUI;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import TFC.Reference;
import TFC.API.TFCOptions;
import TFC.API.Enums.CraftingRuleEnum;
import TFC.Containers.ContainerAnvil;
import TFC.Core.TFC_Core;
import TFC.Core.TFC_Textures;
import TFC.Core.Util.StringUtil;
import TFC.TileEntities.TileEntityAnvil;


public class GuiAnvil extends GuiContainer
{
	TileEntityAnvil AnvilEntity;
	public static ResourceLocation texture = new ResourceLocation(Reference.ModID, Reference.AssetPathGui + "gui_anvil.png");
	public GuiAnvil(InventoryPlayer inventoryplayer, TileEntityAnvil te, World world, int x, int y, int z)
	{
		super(new ContainerAnvil(inventoryplayer,te, world, x, y, z) );
		AnvilEntity = te;
		this.xSize = 208;
		this.ySize = 198;

	}

	@Override
	public void initGui()
	{
		super.initGui();

		buttonList.clear();
		buttonList.add(new GuiAnvilButton(7, guiLeft+123, guiTop + 82, 16, 16, TFC_Textures.AnvilShrink, 	208, 17, 16, 16, this, StringUtil.localize("gui.Anvil.Shrink")));
		buttonList.add(new GuiAnvilButton(6, guiLeft+105, guiTop + 82, 16, 16, TFC_Textures.AnvilUpset, 	208, 17, 16, 16, this, StringUtil.localize("gui.Anvil.Upset")));
		buttonList.add(new GuiAnvilButton(5, guiLeft+123, guiTop + 64, 16, 16, TFC_Textures.AnvilBend, 		208, 17, 16, 16, this, StringUtil.localize("gui.Anvil.Bend")));
		buttonList.add(new GuiAnvilButton(4, guiLeft+105, guiTop + 64, 16, 16, TFC_Textures.AnvilPunch, 	208, 17, 16, 16, this, StringUtil.localize("gui.Anvil.Punch")));		
		buttonList.add(new GuiAnvilButton(3, guiLeft+87, guiTop + 82, 16, 16, TFC_Textures.AnvilDraw, 		208, 33, 16, 16, this, StringUtil.localize("gui.Anvil.Draw")));
		buttonList.add(new GuiAnvilButton(2, guiLeft+69, guiTop + 82, 16, 16, TFC_Textures.AnvilHitHeavy, 	208, 33, 16, 16, this, StringUtil.localize("gui.Anvil.HeavyHit")));
		buttonList.add(new GuiAnvilButton(1, guiLeft+87, guiTop + 64, 16, 16, TFC_Textures.AnvilHitMedium, 	208, 33, 16, 16, this, StringUtil.localize("gui.Anvil.MediumHit")));
		buttonList.add(new GuiAnvilButton(0, guiLeft+69, guiTop + 64, 16, 16, TFC_Textures.AnvilHitLight, 	208, 33, 16, 16, this, StringUtil.localize("gui.Anvil.LightHit")));		
		buttonList.add(new GuiButton(8, guiLeft + 13, guiTop + 53, 36, 20, StringUtil.localize("gui.Anvil.Weld")));
		buttonList.add(new GuiAnvilButton(9, guiLeft+113, guiTop + 7, 19, 21,	208, 49, 19, 21, this, 2, TFCOptions.anvilRuleColor2[0], TFCOptions.anvilRuleColor2[1], TFCOptions.anvilRuleColor2[2]));
		buttonList.add(new GuiAnvilButton(10, guiLeft+94, guiTop + 7, 19, 21,	208, 49, 19, 21, this, 1, TFCOptions.anvilRuleColor1[0], TFCOptions.anvilRuleColor1[1], TFCOptions.anvilRuleColor1[2]));
		buttonList.add(new GuiAnvilButton(11, guiLeft+75, guiTop + 7, 19, 21,	208, 49, 19, 21, this, 0, TFCOptions.anvilRuleColor0[0], TFCOptions.anvilRuleColor0[1], TFCOptions.anvilRuleColor0[2]));
	}

	@Override
	protected void actionPerformed(GuiButton guibutton)
	{
		if (guibutton.id == 0)
		{
			AnvilEntity.actionLightHammer();
		}
		else if (guibutton.id == 2)
		{
			AnvilEntity.actionHeavyHammer();
		}
		else if (guibutton.id == 1)
		{
			AnvilEntity.actionHammer();
		}
		else if (guibutton.id == 3)
		{
			AnvilEntity.actionDraw();
		}
		else if (guibutton.id == 4)
		{
			AnvilEntity.actionPunch();
		}
		else if (guibutton.id == 5)
		{
			AnvilEntity.actionBend();
		}
		else if (guibutton.id == 6)
		{
			AnvilEntity.actionUpset();
		}
		else if (guibutton.id == 7)
		{
			AnvilEntity.actionShrink();
		}
		else if (guibutton.id == 8)
		{
			AnvilEntity.actionWeld();
		}
		this.inventorySlots.detectAndSendChanges();
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		this.mc.getTextureManager().bindTexture(texture);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.5F);
		int w = (width - this.xSize) / 2;
		int h = (height - this.ySize) / 2;
		drawTexturedModalRect(w, h, 0, 0, this.xSize, this.ySize);

		if(AnvilEntity != null)
		{
			int i1 = AnvilEntity.getCraftingValue();
			drawTexturedModalRect(w + 73 + i1, h + 103, 219, 9, 11, 6);

			i1 = AnvilEntity.getItemCraftingValue();
			drawTexturedModalRect(w + 76 + i1, h + 108, 208, 10, 5, 6);

			drawItemRulesImages(w, h);
			drawRulesImages(w,h);
		}

	}

	public void drawTooltip(int mx, int my, String text) {
		List list = new ArrayList();
		list.add(text);
		this.zLevel = 1;
		this.drawHoveringText(list, mx, my+15, this.fontRenderer);
		RenderHelper.disableStandardItemLighting();
		GL11.glDisable(GL11.GL_LIGHTING);
	}

	public void drawItemRulesImages(int w, int h)
	{		
		if(AnvilEntity != null && AnvilEntity.itemCraftingRules != null)
		{
			CraftingRuleEnum[] Rules = AnvilEntity.workRecipe != null ? AnvilEntity.workRecipe.getRules() : null;
			int[] ItemRules = AnvilEntity.getItemRules();

			this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
			this.drawTexturedModelRectFromIcon(w + 80, h + 31, getIconFromRule(ItemRules[0]), 10, 10);
			this.drawTexturedModelRectFromIcon(w + 99, h + 31, getIconFromRule(ItemRules[1]), 10, 10);
			this.drawTexturedModelRectFromIcon(w + 118, h + 31, getIconFromRule(ItemRules[2]), 10, 10);

			this.mc.getTextureManager().bindTexture(texture);

			if(Rules != null && Rules[0].matches(ItemRules, 0)) {
				GL11.glColor4f(0.0F, 1.0F, 0.0F, 1.0F);
			}
			drawTexturedModalRect(w + 77, h + 28, 210, 115, 15, 15);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

			if(Rules != null && Rules[1].matches(ItemRules, 1)) {
				GL11.glColor4f(0.0F, 1.0F, 0.0F, 1.0F);
			}
			drawTexturedModalRect(w + 96, h + 28, 210, 115, 15, 15);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

			if(Rules != null && Rules[2].matches(ItemRules, 2)) {
				GL11.glColor4f(0.0F, 1.0F, 0.0F, 1.0F);
			}
			drawTexturedModalRect(w + 115, h + 28, 210, 115, 15, 15);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		}

		//GL11.glColor4f(1.0F, 0.0F, 0.0F, 1.0F);
		//drawTexturedModalRect(w + 75, h + 7, 208, 49, 19, 21);
	}

	public void drawRulesImages(int w, int h)
	{		
		if(AnvilEntity.workRecipe != null)
		{
			CraftingRuleEnum[] Rules = AnvilEntity.workRecipe.getRules();
			int[] ItemRules = AnvilEntity.getItemRules();

			TFC_Core.bindTexture(TextureMap.locationBlocksTexture);
			this.drawTexturedModelRectFromIcon(w + 80, h + 10, getIconFromRule(Rules[0].Action), 10, 10);
			this.drawTexturedModelRectFromIcon(w + 99, h + 10, getIconFromRule(Rules[1].Action), 10, 10);
			this.drawTexturedModelRectFromIcon(w + 118, h + 10, getIconFromRule(Rules[2].Action), 10, 10);

			TFC_Core.bindTexture(texture);
			//Bottom Row
			GL11.glColor4ub(TFCOptions.anvilRuleColor0[0], TFCOptions.anvilRuleColor0[1], TFCOptions.anvilRuleColor0[2], (byte)255);
			if(Rules[0].Min == 0) {
				drawTexturedModalRect(w + 75, h + 26, 228, 68, 19, 3);
			}
			if(Rules[0].Max > 0 && (Rules[0].Min <= 1 || Rules[0].Max == 1)) {
				drawTexturedModalRect(w + 94, h + 26, 228, 68, 19, 3);
			}
			if(Rules[0].Max > 1 && (Rules[0].Min <= 2 || Rules[0].Max == 2)) {
				drawTexturedModalRect(w + 113, h + 26, 228, 68, 19, 3);
			}
			//Middle Row
			GL11.glColor4ub(TFCOptions.anvilRuleColor1[0], TFCOptions.anvilRuleColor1[1], TFCOptions.anvilRuleColor1[2], (byte)255);
			if(Rules[1].Min == 0) {
				drawTexturedModalRect(w + 75, h + 24, 228, 68, 19, 3);
			}
			if(Rules[1].Max > 0 && (Rules[1].Min <= 1 || Rules[1].Max == 1)) {
				drawTexturedModalRect(w + 94, h + 24, 228, 68, 19, 3);
			}
			if(Rules[1].Max > 1 && (Rules[1].Min <= 2 || Rules[1].Max == 2)) {
				drawTexturedModalRect(w + 113, h + 24, 228, 68, 19, 3);
			}
			//Top Row
			GL11.glColor4ub(TFCOptions.anvilRuleColor2[0], TFCOptions.anvilRuleColor2[1], TFCOptions.anvilRuleColor2[2], (byte)255);
			if(Rules[2].Min == 0) {
				drawTexturedModalRect(w + 75, h + 22, 228, 68, 19, 3);
			}
			if(Rules[2].Max > 0 && (Rules[2].Min <= 1 || Rules[2].Max == 1)) {
				drawTexturedModalRect(w + 94, h + 22, 228, 68, 19, 3);
			}
			if(Rules[2].Max > 1 && (Rules[2].Min <= 2 || Rules[2].Max == 2)) {
				drawTexturedModalRect(w + 113, h + 22, 228, 68, 19, 3);
			}
		}

		//GL11.glColor4f(1.0F, 0.0F, 0.0F, 1.0F);
		//drawTexturedModalRect(w + 75, h + 7, 208, 49, 19, 21);
	}

	public Icon getIconFromRule(int Action)
	{
		switch(Action)
		{
		case 0: return TFC_Textures.AnvilHit;
		case 1: return TFC_Textures.AnvilDraw;
		case 3: return TFC_Textures.AnvilPunch;
		case 4: return TFC_Textures.AnvilBend;
		case 5: return TFC_Textures.AnvilUpset;
		case 6: return TFC_Textures.AnvilShrink;
		default:return TFC_Textures.InvisibleTexture;
		}
	}

	public int getGuiLeft()
	{
		return this.guiLeft;
	}

	public int getGuiTop()
	{
		return this.guiTop;
	}

	@Override
	protected boolean isPointInRegion(int slotX, int slotY, int sizeX, int sizeY, int clickX, int clickY)
	{
		int k1 = this.guiLeft;
		int l1 = this.guiTop;
		clickX -= k1;
		clickY -= l1;
		return clickX >= slotX - 1 && clickX < slotX + sizeX + 1 && clickY >= slotY - 1 && clickY < slotY + sizeY + 1;
	}

	@Override
	public void drawCenteredString(FontRenderer fontrenderer, String s, int i, int j, int k)
	{
		fontrenderer.drawString(s, i - fontrenderer.getStringWidth(s) / 2, j, k);
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
	public void drawTexturedModelRectFromIcon(int x, int y, Icon par3Icon, int width, int height)
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
