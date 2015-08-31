package com.bioxx.tfc.GUI;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Handlers.Network.AbstractPacket;
import com.bioxx.tfc.Handlers.Network.ItemRenamePacket;

public class GuiCustomNametag extends GuiScreen
{
	private GuiTextField theGuiTextField;
	private World world;
	/*private int x;
	private int z;*/
	private EntityPlayer player;

	/** The X size of the inventory window in pixels. */
	protected int xSize = 220;

	/** The Y size of the inventory window in pixels. */
	protected int ySize = 104;
	/**
	 * Starting X position for the Gui. Inconsistent use for Gui backgrounds.
	 */
	protected int guiLeft;

	/**
	 * Starting Y position for the Gui. Inconsistent use for Gui backgrounds.
	 */
	protected int guiTop;

	public GuiCustomNametag(EntityPlayer p, World world, int i, int j, int k)
	{
		this.world = world;
		this.guiLeft = (this.width - this.xSize) / 2;
		this.guiTop = (this.height - this.ySize) / 2;
		/*x = i;
		z = k;*/
		player = p;
	}

	@Override
	public void updateScreen()
	{
		this.theGuiTextField.updateCursorCounter();
	}

	@Override
	public void onGuiClosed()
	{
		Keyboard.enableRepeatEvents(false);
	}

	@Override
	public void initGui()
	{
		super.initGui();

		this.guiTop = (this.height - this.ySize) / 2;

		Keyboard.enableRepeatEvents(true);
		this.buttonList.clear();
		this.buttonList.add(new GuiButton(0, this.width / 2 - 100, guiTop + 57, TFC_Core.translate("gui.done")));
		//this.controlList.add(new GuiButton(1, this.width / 2 - 100, guiTop + 210, var1.translateKey("gui.cancel")));

		this.theGuiTextField = new GuiTextField(this.fontRendererObj, this.width / 2 - 90, guiTop + 27, 180, 20);
		this.theGuiTextField.setFocused(true);
		this.theGuiTextField.setText("");
	}

	public static RenderItem itemRenderer = new RenderItem();

	@Override
	protected void mouseClicked(int par1, int par2, int par3)
	{
		super.mouseClicked(par1, par2, par3);
		this.theGuiTextField.mouseClicked(par1, par2, par3);
	}

	@Override
	protected void keyTyped(char par1, int par2)
	{
		this.theGuiTextField.textboxKeyTyped(par1, par2);
		((GuiButton)this.buttonList.get(0)).enabled = this.theGuiTextField.getText().trim().length() > 0;
		if (par1 == 13)
			this.actionPerformed((GuiButton)this.buttonList.get(0));
	}

	@Override
	public boolean doesGuiPauseGame()
	{
		return false;
	}

	@Override
	public void drawCenteredString(FontRenderer fontrenderer, String s, int i, int j, int k)
	{
		fontrenderer.drawString(s, i - fontrenderer.getStringWidth(s) / 2, j, k);
	}

	@Override
	protected void actionPerformed(GuiButton guibutton)
	{
		if (guibutton.id == 0 && world.isRemote)
		{
			ItemStack stack = player.inventory.getCurrentItem();
			stack.stackTagCompound.setString("Name", theGuiTextField.getText());

			AbstractPacket pkt = new ItemRenamePacket(theGuiTextField.getText());
			//TerraFirmaCraft.packetPipeline.sendToAll(pkt);
			TerraFirmaCraft.PACKET_PIPELINE.sendToServer(pkt);
			
			Minecraft.getMinecraft().displayGuiScreen(null);//player.closeScreen();
		}
		else if (guibutton.id == 1 && world.isRemote)
		{
			Minecraft.getMinecraft().displayGuiScreen(null);//player.closeScreen();
		}
	}

	@Override
	public void drawScreen(int par1, int par2, float par3)
	{
		TFC_Core.bindTexture(new ResourceLocation(Reference.MOD_ID, Reference.ASSET_PATH_GUI + "gui_nametag.png"));

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		int l = (width - xSize) / 2;
		int i1 = (height - ySize) / 2;
		drawTexturedModalRect(l, i1, 0, 0, xSize, ySize);

		drawCenteredString(fontRendererObj,TFC_Core.translate("gui.Nametag"), this.width / 2, i1+8, 0x000000);
		this.theGuiTextField.drawTextBox();

		super.drawScreen(par1, par2, par3);
	}

}
