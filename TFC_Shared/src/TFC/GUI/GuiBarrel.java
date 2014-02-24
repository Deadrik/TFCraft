package TFC.GUI;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import TFC.Reference;
import TFC.Containers.ContainerBarrel;
import TFC.Core.TFC_Core;
import TFC.Core.Player.PlayerInventory;
import TFC.Core.Util.StringUtil;
import TFC.TileEntities.TileEntityBarrel;

public class GuiBarrel extends GuiContainer
{
	private TileEntityBarrel barrel;
	private EntityPlayer player;

	public GuiBarrel(InventoryPlayer inventoryplayer, TileEntityBarrel tileentitybarrel, World world, int x, int y, int z)
	{
		super(new ContainerBarrel(inventoryplayer,tileentitybarrel, world, x, y, z) );
		barrel = tileentitybarrel;
		player = inventoryplayer.player;
		guiLeft = (width - 208) / 2;
		guiTop = (height - 198) / 2;
		xSize = 176;
		ySize = 85+PlayerInventory.invYSize;

		//controlList.clear();

		//controlList.add(new GuiButton(0, guiLeft+86, guiTop + 74, 36, 20, "\2474Seal"));
	}

	@Override
	public void initGui()
	{
		super.initGui();
		//guiLeft = (width - 208) / 2;
		//guiTop = (height - 198) / 2;

		buttonList.clear();

		buttonList.add(new GuiButton(0, guiLeft+38, guiTop + 50, 50, 20, StringUtil.localize("gui.Barrel.Seal")));
		buttonList.add(new GuiButton(1, guiLeft+88, guiTop + 50, 50, 20, StringUtil.localize("gui.Barrel.Empty")));
		GuiButton buttonMode = new GuiButtonMode(2, guiLeft+140, guiTop + 5, 30,20, barrel.mode==0?StringUtil.localize("gui.Barrel.ToggleOn"):StringUtil.localize("gui.Barrel.ToggleOff"));
		buttonList.add(buttonMode);
	}
	
	public class GuiButtonMode extends GuiButton{

		public GuiButtonMode(int par1, int par2, int par3, String par4Str)
	    {
	        super(par1, par2, par3, 200, 20, par4Str);
	    }

	    public GuiButtonMode(int par1, int par2, int par3, int par4, int par5, String par6Str)
	    {
	        super(par1,par2,par3,par4,par5,par6Str);
	    }
		
	    @Override
	    public void drawButton(Minecraft par1Minecraft, int par2, int par3)
	    {
	        if (this.drawButton)
	        {
	            FontRenderer fontrenderer = par1Minecraft.fontRenderer;
	            par1Minecraft.getTextureManager().bindTexture(buttonTextures);
	            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	            this.field_82253_i = par2 >= this.xPosition && par3 >= this.yPosition && par2 < this.xPosition + this.width && par3 < this.yPosition + this.height;
	            int k = this.getHoverState(this.field_82253_i);
	            this.drawTexturedModalRect(this.xPosition, this.yPosition, 0, 46 + k * 20, this.width / 2, this.height);
	            this.drawTexturedModalRect(this.xPosition + this.width / 2, this.yPosition, 200 - this.width / 2, 46 + k * 20, this.width / 2, this.height);
	            this.mouseDragged(par1Minecraft, par2, par3);
	            int l = 14737632;

	            if (!this.enabled)
	            {
	                l = -6250336;
	            }
	            else if (this.field_82253_i)
	            {
	                l = 16777120;
	            }

	            this.drawCenteredString(fontrenderer,  barrel.mode==0?StringUtil.localize("gui.Barrel.ToggleOn"):StringUtil.localize("gui.Barrel.ToggleOff"), this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, l);
	        }
	    }
	}

	@Override
	protected void actionPerformed(GuiButton guibutton)
	{
		if (guibutton.id == 0)
		{
			if(barrel.actionSeal()){
				player.closeScreen();
			}
		}
		if (guibutton.id == 1)
		{
			barrel.actionEmpty();
		}
		if (guibutton.id == 2)
		{
			barrel.actionMode();
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		TFC_Core.bindTexture(new ResourceLocation(Reference.ModID, Reference.AssetPathGui + "gui_barrel.png"));
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.5F);
		int w = (width - xSize) / 2;
		int h = (height - ySize) / 2;
		drawTexturedModalRect(w, h, 0, 0, xSize, ySize);

		int scale = 0;
		if(barrel!=null){
			scale = barrel.getLiquidScaled(49);
			drawTexturedModalRect(w + 8, h + 65 - scale, 185, 31, 15, 6);
		}
		
		PlayerInventory.drawInventory(this, width, height, ySize-PlayerInventory.invYSize);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j)
	{
		drawCenteredString(this.fontRenderer,barrel.getType(barrel.Type),88,7,0x555555);
	}

	@Override
	public void drawCenteredString(FontRenderer fontrenderer, String s, int i, int j, int k)
	{
		fontrenderer.drawString(s, i - fontrenderer.getStringWidth(s) / 2, j, k);
	}


}
