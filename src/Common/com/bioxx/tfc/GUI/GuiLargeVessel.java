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
import com.bioxx.tfc.Containers.ContainerLargeVessel;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Textures;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.Core.Player.PlayerInventory;
import com.bioxx.tfc.TileEntities.TEBarrel;
import com.bioxx.tfc.TileEntities.TEVessel;
import com.bioxx.tfc.api.Food;
import com.bioxx.tfc.api.TFCFluids;
import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.Crafting.BarrelBriningRecipe;
import com.bioxx.tfc.api.Crafting.BarrelManager;
import com.bioxx.tfc.api.Crafting.BarrelPreservativeRecipe;
import com.bioxx.tfc.api.Enums.EnumFoodGroup;
import com.bioxx.tfc.api.Interfaces.IFood;

public class GuiLargeVessel extends GuiContainerTFC
{
	public static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MOD_ID, Reference.ASSET_PATH_GUI + "gui_largevessel.png");
	private TEVessel vesselTE;
	private EntityPlayer player;
	private int guiTab;

	public GuiLargeVessel(InventoryPlayer inventoryplayer, TEVessel te, World world, int x, int y, int z, int tab)
	{
		super(new ContainerLargeVessel(inventoryplayer, te, world, x, y, z, tab), 176, 85);
		vesselTE = te;
		player = inventoryplayer.player;
		guiLeft = (width - 208) / 2;
		guiTop = (height - 198) / 2;
		guiTab = tab;
	}

	@Override
	public void updateScreen()
	{
		super.updateScreen();
		if (vesselTE.getInvCount() > 0)
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
		if (vesselTE.getFluidLevel() > 0)
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

		if (vesselTE.getSealed() && guiTab == 0)
		{
			((GuiButton) buttonList.get(0)).displayString = TFC_Core.translate("gui.Barrel.Unseal");
			((GuiButton) buttonList.get(1)).enabled = false;
			((GuiButton) buttonList.get(2)).enabled = false;
		}
		else if (!vesselTE.getSealed() && guiTab == 0)
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
			if (!vesselTE.getSealed())
				buttonList.add(new GuiButton(0, guiLeft + 38, guiTop + 50, 50, 20, TFC_Core.translate("gui.Barrel.Seal")));
			else
				buttonList.add(new GuiButton(0, guiLeft + 38, guiTop + 50, 50, 20, TFC_Core.translate("gui.Barrel.Unseal")));
			buttonList.add(new GuiButton(1, guiLeft + 88, guiTop + 50, 50, 20, TFC_Core.translate("gui.Barrel.Empty")));
			if (vesselTE.mode == TEBarrel.MODE_IN)
				buttonList.add(new GuiBarrelTabButton(2, guiLeft + 39, guiTop + 29, 16, 16, this, TFC_Core.translate("gui.Barrel.ToggleOn"), 0, 204, 16, 16));
			else if (vesselTE.mode == TEBarrel.MODE_OUT)
				buttonList.add(new GuiBarrelTabButton(2, guiLeft + 39, guiTop + 29, 16, 16, this, TFC_Core.translate("gui.Barrel.ToggleOff"), 0, 188, 16, 16));
			buttonList.add(new GuiBarrelTabButton(3, guiLeft + 36, guiTop - 12, 31, 15, this, TFC_Textures.guiSolidStorage, TFC_Core.translate("gui.Barrel.Solid")));
			buttonList.add(new GuiBarrelTabButton(4, guiLeft + 5, guiTop - 12, 31, 15, this, TFC_Textures.guiLiquidStorage, TFC_Core.translate("gui.Barrel.Liquid")));

		}
		else if (guiTab == 1)
		{
			buttonList.add(new GuiBarrelTabButton(0, guiLeft + 36, guiTop - 12, 31, 15, this, TFC_Textures.guiSolidStorage, TFC_Core.translate("gui.Barrel.Solid")));
			buttonList.add(new GuiBarrelTabButton(1, guiLeft + 5, guiTop - 12, 31, 15, this, TFC_Textures.guiLiquidStorage, TFC_Core.translate("gui.Barrel.Liquid")));

			if (!vesselTE.getSealed())
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

				this.drawCenteredString(fontrenderer, vesselTE.mode == 0 ? TFC_Core.translate("gui.Barrel.ToggleOn") : TFC_Core.translate("gui.Barrel.ToggleOff"), this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, l);
			}
		}
	}

	public class GuiBarrelTabButton extends GuiButton
	{
		private GuiLargeVessel screen;
		private IIcon buttonicon;

		private int xPos;
		private int yPos = 172;
		private int xSize = 31;
		private int ySize = 15;

		public GuiBarrelTabButton(int index, int xPos, int yPos, int width, int height, GuiLargeVessel gui, IIcon icon, String s)
		{
			super(index, xPos, yPos, width, height, s);
			screen = gui;
			buttonicon = icon;
		}

		public GuiBarrelTabButton(int index, int xPos, int yPos, int width, int height, GuiLargeVessel gui, String s, int xp, int yp, int xs, int ys)
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
				TFC_Core.bindTexture(GuiLargeVessel.TEXTURE);
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
				if (!vesselTE.getSealed())
					vesselTE.actionSeal(0, player);
				else
					vesselTE.actionUnSeal(0, player);
			}
			else if (guibutton.id == 1)
				vesselTE.actionEmpty();
			else if (guibutton.id == 2)
			{
				vesselTE.actionMode();
				createButtons();
			}
			else if (guibutton.id == 3 && vesselTE.getFluidLevel() == 0 && vesselTE.getInvCount() == 0)
				vesselTE.actionSwitchTab(1, player);
		}
		else if (guiTab == 1)
		{
			if (guibutton.id == 1 && vesselTE.getInvCount() == 0)
				vesselTE.actionSwitchTab(0, player);
			else if (guibutton.id == 2)
			{
				if (!vesselTE.getSealed())
					vesselTE.actionSeal(1, player);
				else
					vesselTE.actionUnSeal(1, player);
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
		TFC_Core.bindTexture(TEXTURE);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.5F);
		int w = (width - xSize) / 2;
		int h = (height - ySize) / 2;
		if (guiTab == 0)
		{
			drawTexturedModalRect(w, h, 0, 0, xSize, this.getShiftedYSize());

			int scale = 0;
			if (vesselTE != null && vesselTE.fluid != null)
			{
				scale = vesselTE.getLiquidScaled(50);
				//drawTexturedModalRect(w + 8, h + 65 - scale, 185, 31, 15, 6);
				IIcon liquidIcon = vesselTE.fluid.getFluid().getIcon(vesselTE.fluid);
				TFC_Core.bindTexture(TextureMap.locationBlocksTexture);
				int color = vesselTE.fluid.getFluid().getColor(vesselTE.fluid);
				GL11.glColor4ub((byte) ((color >> 16) & 255), (byte) ((color >> 8) & 255), (byte) (color & 255), (byte) ((0xaa) & 255));
				int div = (int) Math.floor(scale / 8);
				int rem = scale - (div * 8);
				this.drawTexturedModelRectFromIcon(w + 12, h + 65 - scale, liquidIcon, 8, div > 0 ? 8 : rem);
				for (int c = 0; div > 0 && c < div; c++)
				{
					this.drawTexturedModelRectFromIcon(w + 12, h + 65 - (8 + (c * 8)), liquidIcon, 8, 8);
				}
				GL11.glColor3f(0, 0, 0);
			}
			ItemStack inStack = vesselTE.getStackInSlot(0);

			// Draw Fluid Name
			if (vesselTE.getFluidStack() != null)
				drawCenteredString(this.fontRendererObj, vesselTE.fluid.getFluid().getLocalizedName(vesselTE.getFluidStack()), guiLeft + 88, guiTop + 7, 0x555555);

			// Draw Seal Date
			if (vesselTE.sealtime != 0)
			{
				drawCenteredString(this.fontRendererObj, TFC_Time.getDateStringFromHours(vesselTE.sealtime), guiLeft + 88, guiTop + 17, 0x555555);
			}

			// Draw Output
			if (vesselTE.recipe != null)
			{
				if (!(vesselTE.recipe instanceof BarrelBriningRecipe))
				{
					drawCenteredString(this.fontRendererObj, TFC_Core.translate("gui.Output") + ": " + vesselTE.recipe.getRecipeName(), guiLeft + 88, guiTop + 72, 0x555555);
				}
				else if (vesselTE.getSealed() && vesselTE.getFluidStack() != null && vesselTE.getFluidStack().getFluid() == TFCFluids.BRINE)
				{
					if (inStack != null && inStack.getItem() instanceof IFood && (((IFood) inStack.getItem()).getFoodGroup() == EnumFoodGroup.Fruit ||
							((IFood) inStack.getItem()).getFoodGroup() == EnumFoodGroup.Vegetable || ((IFood) inStack.getItem()).getFoodGroup() == EnumFoodGroup.Protein ||
							((IFood) inStack.getItem()) == TFCItems.cheese) && !Food.isBrined(inStack))
					{
						drawCenteredString(this.fontRendererObj, TFC_Core.translate("gui.barrel.brining"), guiLeft + 88, guiTop + 72, 0x555555);
					}
				}
			}
			else if (vesselTE.recipe == null && vesselTE.getSealed() && vesselTE.getFluidStack() != null && inStack != null && inStack.getItem() instanceof IFood &&
					vesselTE.getFluidStack().getFluid() == TFCFluids.VINEGAR && !Food.isPickled(inStack) && Food.getWeight(inStack) / vesselTE.getFluidStack().amount <= Global.FOOD_MAX_WEIGHT / vesselTE.getMaxLiquid())
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
				BarrelPreservativeRecipe preservative = BarrelManager.getInstance().findMatchingPreservativeRepice(vesselTE, inStack, vesselTE.getFluidStack(), vesselTE.getSealed());
				if(preservative!=null){
					drawCenteredString(this.fontRendererObj, TFC_Core.translate(preservative.getPreservingString()), guiLeft+88, guiTop+72, 0x555555);
				}
			}
		}
		else if (guiTab == 1)
		{
			drawTexturedModalRect(w, h, 0, 86, xSize, this.getShiftedYSize());
		}

		PlayerInventory.drawInventory(this, width, height, this.getShiftedYSize());
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		if (guiTab == 0 && this.mouseInRegion(12, 15, 9, 50, mouseX, mouseY))
		{
			ArrayList<String> list = new ArrayList<String>();
			list.add(vesselTE.getFluidLevel() + "mB");
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
		if (vesselTE.getSealed())
		{
			GL11.glPushMatrix();
			if (guiTab == 0)
			{
				Slot inputSlot = this.inventorySlots.getSlot(TEBarrel.INPUT_SLOT);
				drawSlotOverlay(inputSlot);
			}
			else if (guiTab == 1)
			{
				for (int i = 0; i < vesselTE.storage.length; ++i)
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
