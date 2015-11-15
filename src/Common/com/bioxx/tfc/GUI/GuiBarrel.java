package com.bioxx.tfc.GUI;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Containers.ContainerBarrel;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Textures;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.Core.Player.PlayerInventory;
import com.bioxx.tfc.TileEntities.TEBarrel;
import com.bioxx.tfc.api.Food;
import com.bioxx.tfc.api.TFCFluids;
import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.Crafting.BarrelBriningRecipe;
import com.bioxx.tfc.api.Crafting.BarrelManager;
import com.bioxx.tfc.api.Crafting.BarrelPreservativeRecipe;
import com.bioxx.tfc.api.Enums.EnumFoodGroup;
import com.bioxx.tfc.api.Interfaces.IFood;

public class GuiBarrel extends GuiContainerTFC
{
	public static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MOD_ID, Reference.ASSET_PATH_GUI + "gui_barrel.png");
	protected TEBarrel barrelTE;
	protected EntityPlayer player;
	protected int guiTab;

	public GuiBarrel(InventoryPlayer inventoryplayer, TEBarrel te, World world, int x, int y, int z, int tab)
	{
		super(new ContainerBarrel(inventoryplayer, te, world, x, y, z, tab), 176, 85);
		barrelTE = te;
		player = inventoryplayer.player;
		guiLeft = (width - 208) / 2;
		guiTop = (height - 198) / 2;
		guiTab = tab;
	}

	@Override
	public void updateScreen()
	{
		super.updateScreen();
		if (barrelTE.getInvCount() > 0)
		{
			if (guiTab == 0)
				((GuiButton) buttonList.get(4)).visible = false;//Turn off Liquid
			else if (guiTab == 1)
				((GuiButton) buttonList.get(1)).visible = false;//Turn off Liquid
		}
		else
		{
			if (guiTab == 0)
				((GuiButton) buttonList.get(4)).visible = true;//Turn on Liquid
			else if (guiTab == 1)
				((GuiButton) buttonList.get(1)).visible = true;//Turn on Liquid
		}
		if (barrelTE.getFluidLevel() > 0)
		{
			if (guiTab == 0)
				((GuiButton) buttonList.get(3)).visible = false;//Turn off Solid
			else if (guiTab == 1)
				((GuiButton) buttonList.get(0)).visible = false;//Turn off Solid
		}
		else
		{
			if (guiTab == 0)
				((GuiButton) buttonList.get(3)).visible = true;//Turn on Solid
			else if (guiTab == 1)
				((GuiButton) buttonList.get(0)).visible = true;//Turn on Solid
		}

		if (barrelTE.getSealed() && guiTab == 0)
		{
			((GuiButton) buttonList.get(0)).displayString = TFC_Core.translate("gui.Barrel.Unseal");
			((GuiButton) buttonList.get(1)).enabled = false;
			((GuiButton) buttonList.get(2)).enabled = false;
		}
		else if (!barrelTE.getSealed() && guiTab == 0)
		{
			((GuiButton) buttonList.get(0)).displayString = TFC_Core.translate("gui.Barrel.Seal");
			((GuiButton) buttonList.get(1)).enabled = true;
			((GuiButton) buttonList.get(2)).enabled = true;
		}
	}

	@Override
	public void initGui()
	{
		super.initGui();
		createButtons();
	}

	public void createButtons()
	{
		buttonList.clear();
		if (guiTab == 0)
		{
			if (!barrelTE.getSealed())
				buttonList.add(new GuiButton(0, guiLeft + 38, guiTop + 50, 50, 20, TFC_Core.translate("gui.Barrel.Seal")));
			else
				buttonList.add(new GuiButton(0, guiLeft + 38, guiTop + 50, 50, 20, TFC_Core.translate("gui.Barrel.Unseal")));
			buttonList.add(new GuiButton(1, guiLeft + 88, guiTop + 50, 50, 20, TFC_Core.translate("gui.Barrel.Empty")));
			if (barrelTE.mode == TEBarrel.MODE_IN)
				buttonList.add(new GuiBarrelTabButton(2, guiLeft + 39, guiTop + 29, 16, 16, this, TFC_Core.translate("gui.Barrel.ToggleOn"), 0, 204, 16, 16));
			else if (barrelTE.mode == TEBarrel.MODE_OUT)
				buttonList.add(new GuiBarrelTabButton(2, guiLeft + 39, guiTop + 29, 16, 16, this, TFC_Core.translate("gui.Barrel.ToggleOff"), 0, 188, 16, 16));
			buttonList.add(new GuiBarrelTabButton(3, guiLeft + 36, guiTop - 12, 31, 15, this, TFC_Textures.guiSolidStorage, TFC_Core.translate("gui.Barrel.Solid")));
			buttonList.add(new GuiBarrelTabButton(4, guiLeft + 5, guiTop - 12, 31, 15, this, TFC_Textures.guiLiquidStorage, TFC_Core.translate("gui.Barrel.Liquid")));

		}
		else if (guiTab == 1)
		{
			buttonList.add(new GuiBarrelTabButton(0, guiLeft + 36, guiTop - 12, 31, 15, this, TFC_Textures.guiSolidStorage, TFC_Core.translate("gui.Barrel.Solid")));
			buttonList.add(new GuiBarrelTabButton(1, guiLeft + 5, guiTop - 12, 31, 15, this, TFC_Textures.guiLiquidStorage, TFC_Core.translate("gui.Barrel.Liquid")));

			if (!barrelTE.getSealed())
				buttonList.add(new GuiButton(2, guiLeft + 6, guiTop + 33, 44, 20, TFC_Core.translate("gui.Barrel.Seal")));
			else
				buttonList.add(new GuiButton(2, guiLeft + 6, guiTop + 33, 44, 20, TFC_Core.translate("gui.Barrel.Unseal")));
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
	}

	public class GuiButtonMode extends GuiButton
	{
		public GuiButtonMode(int par1, int par2, int par3, String par4Str)
		{
			super(par1, par2, par3, 200, 20, par4Str);
		}

		public GuiButtonMode(int par1, int par2, int par3, int par4, int par5, String par6Str)
		{
			super(par1, par2, par3, par4, par5, par6Str);
		}

		@Override
		public void drawButton(Minecraft par1Minecraft, int xPos, int yPos)
		{
			if (this.visible)
			{
				FontRenderer fontrenderer = par1Minecraft.fontRenderer;
				par1Minecraft.getTextureManager().bindTexture(buttonTextures);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				this.field_146123_n = xPos >= this.xPosition && yPos >= this.yPosition && xPos < this.xPosition + this.width && yPos < this.yPosition + this.height;
				int k = this.getHoverState(this.field_146123_n);
				this.drawTexturedModalRect(this.xPosition, this.yPosition, 0, 46 + k * 20, this.width / 2, this.height);
				this.drawTexturedModalRect(this.xPosition + this.width / 2, this.yPosition, 200 - this.width / 2, 46 + k * 20, this.width / 2, this.height);
				this.mouseDragged(par1Minecraft, xPos, yPos);
				int l = 14737632;

				if (!this.enabled)
				{
					l = -6250336;
				}
				else if (this.field_146123_n)
				{
					l = 16777120;
				}

				this.drawCenteredString(fontrenderer, barrelTE.mode == 0 ? TFC_Core.translate("gui.Barrel.ToggleOn") : TFC_Core.translate("gui.Barrel.ToggleOff"), this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, l);
			}
		}
	}

	public class GuiBarrelTabButton extends GuiButton
	{
		private GuiBarrel screen;
		private IIcon buttonicon;

		private int xPos;
		private int yPos = 172;
		private int xSize = 31;
		private int ySize = 15;

		public GuiBarrelTabButton(int index, int xPos, int yPos, int width, int height, GuiBarrel gui, IIcon icon, String s)
		{
			super(index, xPos, yPos, width, height, s);
			screen = gui;
			buttonicon = icon;
		}

		public GuiBarrelTabButton(int index, int xPos, int yPos, int width, int height, GuiBarrel gui, String s, int xp, int yp, int xs, int ys)
		{
			super(index, xPos, yPos, width, height, s);
			screen = gui;
			this.xPos = xp;
			this.yPos = yp;
			xSize = xs;
			ySize = ys;
		}

		@Override
		public void drawButton(Minecraft mc, int x, int y)
		{
			if (this.visible)
			{
				TFC_Core.bindTexture(GuiBarrel.TEXTURE);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				this.zLevel = 301f;
				this.drawTexturedModalRect(this.xPosition, this.yPosition, xPos, yPos, xSize, ySize);
				this.field_146123_n = x >= this.xPosition && y >= this.yPosition && x < this.xPosition + this.width && y < this.yPosition + this.height;

				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				TFC_Core.bindTexture(TextureMap.locationBlocksTexture);
				if (buttonicon != null)
					this.drawTexturedModelRectFromIcon(this.xPosition + 12, this.yPosition + 4, buttonicon, 8, 8);

				this.zLevel = 0;
				this.mouseDragged(mc, x, y);

				if (field_146123_n)
				{
					screen.drawTooltip(x, y, this.displayString);
					GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				}
			}
		}
	}

	@Override
	protected void actionPerformed(GuiButton guibutton)
	{
		if (guiTab == 0)
		{
			if (guibutton.id == 0)
			{
				if (!barrelTE.getSealed())
					barrelTE.actionSeal(0, player);
				else
					barrelTE.actionUnSeal(0, player);
			}
			else if (guibutton.id == 1)
				barrelTE.actionEmpty();
			else if (guibutton.id == 2)
			{
				barrelTE.actionMode();
				createButtons();
			}
			else if (guibutton.id == 3 && barrelTE.getFluidLevel() == 0 && barrelTE.getInvCount() == 0)
				barrelTE.actionSwitchTab(1, player);
		}
		else if (guiTab == 1)
		{
			if (guibutton.id == 1 && barrelTE.getInvCount() == 0)
				barrelTE.actionSwitchTab(0, player);
			else if (guibutton.id == 2)
			{
				if (!barrelTE.getSealed())
					barrelTE.actionSeal(1, player);
				else
					barrelTE.actionUnSeal(1, player);
				createButtons();
			}
		}
	}

	/*
	 * Edited Copy/Paste of drawGui() code since the background texture changes depending on the tab selected.
	 */
	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int mouseX, int mouseY)
	{
		bindTexture(TEXTURE);
		guiLeft = (width - xSize) / 2;
		guiTop = (height - ySize) / 2;

		if (guiTab == 0)
		{
			drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, this.getShiftedYSize());

			int scale = 0;
			if (barrelTE != null && barrelTE.fluid != null)
			{
				scale = barrelTE.getLiquidScaled(50);
				//drawTexturedModalRect(w + 8, h + 65 - scale, 185, 31, 15, 6);
				IIcon liquidIcon = barrelTE.fluid.getFluid().getIcon(barrelTE.fluid);
				TFC_Core.bindTexture(TextureMap.locationBlocksTexture);
				int color = barrelTE.fluid.getFluid().getColor(barrelTE.fluid);
				GL11.glColor4ub((byte) ((color >> 16) & 255), (byte) ((color >> 8) & 255), (byte) (color & 255), (byte) ((0xaa) & 255));
				int div = (int) Math.floor(scale / 8);
				int rem = scale - (div * 8);
				this.drawTexturedModelRectFromIcon(guiLeft + 12, guiTop + 65 - scale, liquidIcon, 8, div > 0 ? 8 : rem);
				for (int c = 0; div > 0 && c < div; c++)
				{
					this.drawTexturedModelRectFromIcon(guiLeft + 12, guiTop + 65 - (8 + (c * 8)), liquidIcon, 8, 8);
				}
				GL11.glColor3f(0, 0, 0);
			}
			ItemStack inStack = barrelTE.getStackInSlot(0);

			// Draw Fluid Name
			if (barrelTE.getFluidStack() != null)
				drawCenteredString(this.fontRendererObj, barrelTE.fluid.getFluid().getLocalizedName(barrelTE.getFluidStack()), guiLeft + 88, guiTop + 7, 0x555555);

			// Draw Seal Date
			if (barrelTE.sealtime != 0)
			{
				drawCenteredString(this.fontRendererObj, TFC_Time.getDateStringFromHours(barrelTE.sealtime), guiLeft + 88, guiTop + 17, 0x555555);
			}

			// Draw Output
			if (barrelTE.recipe != null)
			{
				if (!(barrelTE.recipe instanceof BarrelBriningRecipe))
				{
					drawCenteredString(this.fontRendererObj, TFC_Core.translate("gui.Output") + ": " + barrelTE.recipe.getRecipeName(), guiLeft + 88, guiTop + 72, 0x555555);
				}
				else if (barrelTE.getSealed() && barrelTE.getFluidStack() != null && barrelTE.getFluidStack().getFluid() == TFCFluids.BRINE)
				{
					if (inStack != null && inStack.getItem() instanceof IFood && (((IFood) inStack.getItem()).getFoodGroup() == EnumFoodGroup.Fruit ||
							((IFood) inStack.getItem()).getFoodGroup() == EnumFoodGroup.Vegetable || ((IFood) inStack.getItem()).getFoodGroup() == EnumFoodGroup.Protein ||
							((IFood) inStack.getItem()) == TFCItems.cheese) && !Food.isBrined(inStack))
					{
						drawCenteredString(this.fontRendererObj, TFC_Core.translate("gui.barrel.brining"), guiLeft + 88, guiTop + 72, 0x555555);
					}
				}
			}
			else if (barrelTE.recipe == null && barrelTE.getSealed() && barrelTE.getFluidStack() != null && inStack != null && inStack.getItem() instanceof IFood &&
					barrelTE.getFluidStack().getFluid() == TFCFluids.VINEGAR && !Food.isPickled(inStack) && Food.getWeight(inStack) / barrelTE.getFluidStack().amount <= Global.FOOD_MAX_WEIGHT / barrelTE.getMaxLiquid())
			{
				if ((((IFood) inStack.getItem()).getFoodGroup() == EnumFoodGroup.Fruit || ((IFood) inStack.getItem()).getFoodGroup() == EnumFoodGroup.Vegetable ||
						((IFood) inStack.getItem()).getFoodGroup() == EnumFoodGroup.Protein || ((IFood) inStack.getItem()) == TFCItems.cheese) &&
						Food.isBrined(inStack))
				{
					drawCenteredString(this.fontRendererObj, TFC_Core.translate("gui.barrel.pickling"), guiLeft + 88, guiTop + 72, 0x555555);
				}
			}
			else
			{
				BarrelPreservativeRecipe preservative = BarrelManager.getInstance().findMatchingPreservativeRepice(barrelTE, inStack, barrelTE.getFluidStack(), barrelTE.getSealed());
				if(preservative!=null){
					drawCenteredString(this.fontRendererObj, TFC_Core.translate(preservative.getPreservingString()), guiLeft+88, guiTop+72, 0x555555);
				}
			}

		}
		else if (guiTab == 1)
		{
			drawTexturedModalRect(guiLeft, guiTop, 0, 86, xSize, this.getShiftedYSize());
		}

		PlayerInventory.drawInventory(this, width, height, this.getShiftedYSize());
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		if (guiTab == 0 && this.mouseInRegion(12, 15, 9, 50, mouseX, mouseY))
		{
			ArrayList<String> list = new ArrayList<String>();
			list.add(barrelTE.getFluidLevel() + "mB");
			this.drawHoveringText(list, mouseX - guiLeft, mouseY - guiTop + 8, this.fontRendererObj);
		}
	}

	@Override
	public void drawCenteredString(FontRenderer fontrenderer, String s, int i, int j, int k)
	{
		fontrenderer.drawString(s, i - fontrenderer.getStringWidth(s) / 2, j, k);
	}

	@Override
	public void drawScreen(int x, int y, float par3)
	{
		super.drawScreen(x, y, par3);
		if (barrelTE.getSealed())
		{
			GL11.glPushMatrix();
			if (guiTab == 0)
			{
				Slot inputSlot = this.inventorySlots.getSlot(TEBarrel.INPUT_SLOT);
				drawSlotOverlay(inputSlot);
			}
			else if (guiTab == 1)
			{
				for (int i = 0; i < barrelTE.storage.length; ++i)
				{
					Slot slot = this.inventorySlots.getSlot(i);
					drawSlotOverlay(slot);
				}
			}

			GL11.glPopMatrix();
		}
	}

	private void drawSlotOverlay(Slot slot)
	{
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		int xPos = slot.xDisplayPosition + guiLeft - 1;
		int yPos = slot.yDisplayPosition + guiTop - 1;
		GL11.glColorMask(true, true, true, false);
		this.drawGradientRect(xPos, yPos, xPos + 18, yPos + 18, 0x75FFFFFF, 0x75FFFFFF);
		GL11.glColorMask(true, true, true, true);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
	}
}
