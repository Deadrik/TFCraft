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
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
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

public class GuiLargeVessel extends GuiContainerTFC
{
	public static final ResourceLocation texture = new ResourceLocation(Reference.ModID, Reference.AssetPathGui + "gui_largevessel.png");
	private TEVessel barrel;
	private EntityPlayer player;
	private int guiTab = 0;

	public GuiLargeVessel(InventoryPlayer inventoryplayer, TEVessel te, World world, int x, int y, int z, int tab)
	{
		super(new ContainerLargeVessel(inventoryplayer,te, world, x, y, z, tab), 176, 85);
		barrel = te;
		player = inventoryplayer.player;
		guiLeft = (width - 208) / 2;
		guiTop = (height - 198) / 2;
		guiTab = tab;
	}

	@Override
	public void updateScreen()
	{
		super.updateScreen();
		if(barrel.getInvCount() > 0)
		{
			if(guiTab == 0)
				((GuiButton)buttonList.get(4)).visible = false;//Turn off Liquid
			else if(guiTab == 1)
				((GuiButton)buttonList.get(1)).visible = false;//Turn off Liquid
		}
		else
		{
			if(guiTab == 0)
				((GuiButton)buttonList.get(4)).visible = true;//Turn on Liquid
			else if(guiTab == 1)
				((GuiButton)buttonList.get(1)).visible = true;//Turn on Liquid
		}
		if(barrel.getFluidLevel() > 0)
		{
			if(guiTab == 0)
				((GuiButton)buttonList.get(3)).visible = false;//Turn off Solid
			else if(guiTab == 1)
				((GuiButton)buttonList.get(0)).visible = false;//Turn off Solid
		}
		else
		{
			if(guiTab == 0)
				((GuiButton)buttonList.get(3)).visible = true;//Turn on Solid
			else if(guiTab == 1)
				((GuiButton)buttonList.get(0)).visible = true;//Turn on Solid
		}

		if(barrel.getSealed() && guiTab == 0)
		{
			((GuiButton)buttonList.get(0)).displayString = StatCollector.translateToLocal("gui.Barrel.Unseal");
			((GuiButton)buttonList.get(1)).enabled = false;
			((GuiButton)buttonList.get(2)).enabled = false;
		}
		else if(!barrel.getSealed() && guiTab == 0)
		{
			((GuiButton)buttonList.get(0)).displayString = StatCollector.translateToLocal("gui.Barrel.Seal");
			((GuiButton)buttonList.get(1)).enabled = true;
			((GuiButton)buttonList.get(2)).enabled = true;
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
		if(guiTab == 0)
		{
			if(!barrel.getSealed())
				buttonList.add(new GuiButton(0, guiLeft+38, guiTop + 50, 50, 20, StatCollector.translateToLocal("gui.Barrel.Seal")));
			else
				buttonList.add(new GuiButton(0, guiLeft+38, guiTop + 50, 50, 20, StatCollector.translateToLocal("gui.Barrel.Unseal")));
			buttonList.add(new GuiButton(1, guiLeft+88, guiTop + 50, 50, 20, StatCollector.translateToLocal("gui.Barrel.Empty")));
			if(barrel.mode == TEBarrel.MODE_IN)
				buttonList.add(new GuiBarrelTabButton(2, guiLeft+39, guiTop + 29, 16, 16,this, StatCollector.translateToLocal("gui.Barrel.ToggleOn"), 0, 204, 16, 16));
			else if(barrel.mode == TEBarrel.MODE_OUT)
				buttonList.add(new GuiBarrelTabButton(2, guiLeft+39, guiTop + 29, 16, 16,this, StatCollector.translateToLocal("gui.Barrel.ToggleOff"), 0, 188, 16, 16));
			buttonList.add(new GuiBarrelTabButton(3, guiLeft+36, guiTop-12, 31, 15, this, TFC_Textures.GuiSolidStorage, StatCollector.translateToLocal("gui.Barrel.Solid")));
			buttonList.add(new GuiBarrelTabButton(4, guiLeft+5, guiTop-12, 31, 15, this, TFC_Textures.GuiLiquidStorage, StatCollector.translateToLocal("gui.Barrel.Liquid")));

		}
		else if(guiTab == 1)
		{
			buttonList.add(new GuiBarrelTabButton(0, guiLeft+36, guiTop-12, 31, 15, this, TFC_Textures.GuiSolidStorage, StatCollector.translateToLocal("gui.Barrel.Solid")));
			buttonList.add(new GuiBarrelTabButton(1, guiLeft+5, guiTop-12, 31, 15, this, TFC_Textures.GuiLiquidStorage, StatCollector.translateToLocal("gui.Barrel.Liquid")));

			if(!barrel.getSealed())
				buttonList.add(new GuiButton(2, guiLeft+6, guiTop + 33, 44, 20, StatCollector.translateToLocal("gui.Barrel.Seal")));
			else
				buttonList.add(new GuiButton(2, guiLeft+6, guiTop + 33, 44, 20, StatCollector.translateToLocal("gui.Barrel.Unseal")));
		}
	}

	@Override
	public void drawTooltip(int mx, int my, String text) {
		List list = new ArrayList();
		list.add(text);
		this.drawHoveringText(list, mx, my+15, this.fontRendererObj);
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

				this.drawCenteredString(fontrenderer,  barrel.mode==0?StatCollector.translateToLocal("gui.Barrel.ToggleOn"):StatCollector.translateToLocal("gui.Barrel.ToggleOff"), this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, l);
			}
		}
	}

	public class GuiBarrelTabButton extends GuiButton 
	{
		GuiLargeVessel screen;
		IIcon buttonicon = null;

		int xPos = 0;
		int yPos = 172;
		int xSize = 31;
		int ySize = 15;

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
				int k = this.getHoverState(this.field_146123_n)-1;

				TFC_Core.bindTexture(GuiLargeVessel.texture);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				this.zLevel = 301f;
				this.drawTexturedModalRect(this.xPosition, this.yPosition, xPos, yPos, xSize, ySize);
				this.field_146123_n = x >= this.xPosition && y >= this.yPosition && x < this.xPosition + this.width && y < this.yPosition + this.height;

				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				TFC_Core.bindTexture(TextureMap.locationBlocksTexture);
				if(buttonicon != null)
					this.drawTexturedModelRectFromIcon(this.xPosition+12, this.yPosition+4, buttonicon, 8, 8);

				this.zLevel = 0;
				this.mouseDragged(mc, x, y);

				if(field_146123_n)
				{
					FontRenderer fontrenderer = Minecraft.getMinecraft().fontRenderer;
					screen.drawTooltip(x, y, this.displayString);
					GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				}
			}
		}
	}

	@Override
	protected void actionPerformed(GuiButton guibutton)
	{
		if(guiTab == 0)
		{
			if (guibutton.id == 0)
			{
				if(!barrel.getSealed())
					barrel.actionSeal(0, player);
				else
					barrel.actionUnSeal(0, player);
			}
			else if (guibutton.id == 1)
				barrel.actionEmpty();
			else if (guibutton.id == 2)
			{
				barrel.actionMode();
				createButtons();
			}
			else if (guibutton.id == 3 && barrel.getFluidLevel() == 0 && barrel.getInvCount() == 0)
				barrel.actionSwitchTab(1, player);
		}
		else if(guiTab == 1)
		{
			if (guibutton.id == 1 && barrel.getInvCount() == 0)
				barrel.actionSwitchTab(0, player);
			else if (guibutton.id == 2)
			{
				if(!barrel.getSealed())
					barrel.actionSeal(1, player);
				else
					barrel.actionUnSeal(1, player);
				createButtons();
			}
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int mouseX, int mouseY)
	{
		TFC_Core.bindTexture(texture);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.5F);
		int w = (width - xSize) / 2;
		int h = (height - ySize) / 2;
		if(guiTab == 0)
		{
			drawTexturedModalRect(w, h, 0, 0, xSize, ySize);

			int scale = 0;
			if(barrel!=null && barrel.fluid != null)
			{
				scale = barrel.getLiquidScaled(50);
				//drawTexturedModalRect(w + 8, h + 65 - scale, 185, 31, 15, 6);
				IIcon liquidIcon = barrel.fluid.getFluid().getIcon(barrel.fluid);
				TFC_Core.bindTexture(TextureMap.locationBlocksTexture);
				int color = barrel.fluid.getFluid().getColor(barrel.fluid);
				GL11.glColor4ub((byte)((color >> 16)&255), (byte)((color >> 8)&255), (byte)(color & 255), (byte)((0xaa)&255));
				int div = (int)Math.floor(scale/8);
				int rem = scale-(div*8);
				this.drawTexturedModelRectFromIcon(w + 12, h + 65-scale, liquidIcon, 8, div > 0 ? 8 : rem);
				for(int c = 0; div > 0 && c < div; c++)
				{
					this.drawTexturedModelRectFromIcon(w + 12, h + 65-(8+(c*8)), liquidIcon, 8, 8);
				}
				GL11.glColor3f(0, 0, 0);
			}

			if(barrel.getFluidStack() != null)
				drawCenteredString(this.fontRendererObj, barrel.fluid.getFluid().getLocalizedName(), w+88, h+7, 0x555555);
			if(barrel.sealtime != 0)
			{
				drawCenteredString(this.fontRendererObj, TFC_Time.getDateStringFromHours(barrel.sealtime), w+88, h+17, 0x555555);
			}
			if(barrel.recipe != null)
			{
				drawCenteredString(this.fontRendererObj, StatCollector.translateToLocal("gui.Bloomery.Output")+": "+barrel.recipe.getRecipeName(), w+88, h+72, 0x555555);
			}
			else
			{
				drawCenteredString(this.fontRendererObj, StatCollector.translateToLocal("gui.Bloomery.Output")+": N/A", w+88, h+72, 0x555555);
			}
		}
		else if(guiTab == 1)
		{
			drawTexturedModalRect(w, h, 0, 86, xSize, ySize);
		}

		PlayerInventory.drawInventory(this, width, height, ySize - PlayerInventory.invYSize);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		if(guiTab == 0 && this.mouseInRegion(12, 15, 9, 50, mouseX, mouseY))
		{
			ArrayList list = new ArrayList();
			list.add(barrel.getFluidLevel()+"mB");
			this.drawHoveringText(list, mouseX-guiLeft, mouseY-guiTop+8, this.fontRendererObj);
		}
	}

	@Override
	public void drawCenteredString(FontRenderer fontrenderer, String s, int i, int j, int k)
	{
		fontrenderer.drawString(s, i - fontrenderer.getStringWidth(s) / 2, j, k);
	}
}
