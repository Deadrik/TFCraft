package com.bioxx.tfc.GUI;

import com.bioxx.tfc.Handlers.Network.BlueprintTurnCubePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Handlers.Network.AbstractPacket;
import com.bioxx.tfc.Handlers.Network.ItemRenamePacket;

public class GuiBlueprint extends GuiScreen
{
	World world;
	int x;
	int z;
	EntityPlayer player;

	private GuiTextField nameTextField;
	private static final int x_m_BT_ind = 0;
	private static final int x_p_BT_ind = 1;
	private static final int y_m_BT_ind = 2;
	private static final int y_p_BT_ind = 3;
	private static final int z_m_BT_ind = 4;
	private static final int z_p_BT_ind = 5;
	private static final int done_BT_ind = 6;

	private int x_angle;
	private int y_angle;
	private int z_angle;

	/** The X size of the inventory window in pixels. */
	protected int xSize = 200;

	/** The Y size of the inventory window in pixels. */
	protected int ySize = 136;
	/**
	 * Starting X position for the Gui. Inconsistent use for Gui backgrounds.
	 */
	protected int guiLeft() { return (this.width - this.xSize) / 2; }

	/**
	 * Starting Y position for the Gui. Inconsistent use for Gui backgrounds.
	 */
	protected int guiTop() { return (this.height - this.ySize) / 2; }

	public GuiBlueprint(EntityPlayer p, World world, int i, int j, int k)
	{
		//super(new ContainerBlueprint(p, world, i, j, k));
		this.world = world;
		x = i;
		z = k;
		player = p;
		x_angle = 0;
		y_angle = 0;
		z_angle = 0;
	}

	@Override
	public void updateScreen()
	{
		this.nameTextField.updateCursorCounter();
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

		int nameTop = guiTop() + 10 + this.fontRendererObj.FONT_HEIGHT + 4;
		this.nameTextField = new GuiTextField(this.fontRendererObj, guiLeft() + 10, nameTop, 180, 20);
		this.nameTextField.setFocused(true);
		this.nameTextField.setText("BP.");

		Keyboard.enableRepeatEvents(true);
		int buttonsA = 20;
		int buttonsTop = nameTop + 20 + 4;
		int buttonsLeftF = guiLeft() + 10 + this.fontRendererObj.getStringWidth("X:") + 4;
		int buttonsLeftS = buttonsLeftF + buttonsA + 4 + this.fontRendererObj.getStringWidth("360") + 4;

		this.buttonList.clear();
		this.labelList.clear();

		// X:
		this.buttonList.add(new GuiButton(x_m_BT_ind, buttonsLeftF, buttonsTop, buttonsA, buttonsA, "<"));
		this.buttonList.add(new GuiButton(x_p_BT_ind, buttonsLeftS, buttonsTop, buttonsA, buttonsA, ">"));

		// Y:
		buttonsTop += buttonsA + 4;
		this.buttonList.add(new GuiButton(y_m_BT_ind, buttonsLeftF, buttonsTop, buttonsA, buttonsA, "<"));
		this.buttonList.add(new GuiButton(y_p_BT_ind, buttonsLeftS, buttonsTop, buttonsA, buttonsA, ">"));

		// Z:
		buttonsTop += buttonsA + 4;
		this.buttonList.add(new GuiButton(z_m_BT_ind, buttonsLeftF, buttonsTop, buttonsA, buttonsA, "<"));
		this.buttonList.add(new GuiButton(z_p_BT_ind, buttonsLeftS, buttonsTop, buttonsA, buttonsA, ">"));

		//this.controlList.add(new GuiButton(1, this.width / 2 - 100, guiTop + 210, var1.translateKey("gui.cancel")));
		// done
		int doneWidth = fontRendererObj.getStringWidth(StatCollector.translateToLocal("gui.done")) + 20;
		this.buttonList.add(new GuiButton(
						done_BT_ind,
						(width + xSize) / 2 - 10 - doneWidth, (height + ySize) / 2 - 10 - buttonsA,
						doneWidth, buttonsA,
						StatCollector.translateToLocal("gui.done")
		));
	}

	public static RenderItem itemRenderer = new RenderItem();

	@Override
	protected void mouseClicked(int par1, int par2, int par3)
	{
		super.mouseClicked(par1, par2, par3);
		this.nameTextField.mouseClicked(par1, par2, par3);
	}

	@Override
	protected void keyTyped(char par1, int par2)
	{
		this.nameTextField.textboxKeyTyped(par1, par2);
		((GuiButton)this.buttonList.get(done_BT_ind)).enabled = this.nameTextField.getText().trim().length() > 0;
		if (par1 == 13)
			this.actionPerformed((GuiButton)this.buttonList.get(done_BT_ind));
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
		if (!world.isRemote)
			return;

		if (guibutton.id == x_m_BT_ind)
			x_angle = (x_angle == 0 ? 270 : x_angle - 90);
		else if (guibutton.id == x_p_BT_ind)
			x_angle = (x_angle == 270 ? 0 : x_angle + 90);
		else if (guibutton.id == y_m_BT_ind)
			y_angle = (y_angle == 0 ? 270 : y_angle - 90);
		else if (guibutton.id == y_p_BT_ind)
			y_angle = (y_angle == 270 ? 0 : y_angle + 90);
		else if (guibutton.id == z_m_BT_ind)
			z_angle = (z_angle == 0 ? 270 : z_angle - 90);
		else if (guibutton.id == z_p_BT_ind)
			z_angle = (z_angle == 270 ? 0 : z_angle + 90);
		else if (guibutton.id == done_BT_ind)
		{
			ItemStack stack = player.inventory.getCurrentItem();
			stack.stackTagCompound.setString("Name", nameTextField.getText());

			AbstractPacket pkt;

			pkt = new ItemRenamePacket(nameTextField.getText());
			//TerraFirmaCraft.packetPipeline.sendToAll(pkt);
			TerraFirmaCraft.packetPipeline.sendToServer(pkt);

			if (x_angle != 0 || y_angle != 0 || z_angle != 0)
			{
				pkt = new BlueprintTurnCubePacket(x_angle, y_angle, z_angle);
				TerraFirmaCraft.packetPipeline.sendToServer(pkt);
			}

			Minecraft.getMinecraft().displayGuiScreen(null);//player.closeScreen();
		}
	}

	@Override
	public void drawScreen(int par1, int par2, float par3)
	{
		TFC_Core.bindTexture(new ResourceLocation(Reference.ModID, Reference.AssetPathGui + "gui_blueprint.png"));

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		int left = guiLeft();
		int top = guiTop();
		drawTexturedModalRect(left, top, 0, 0, xSize, ySize);

		drawCenteredString(fontRendererObj, StatCollector.translateToLocal("gui.Blueprint"), this.width / 2, top + 10, 0x000000);
		this.nameTextField.drawTextBox();

		int axesNameLeft = left + 10;
		int axesAngleLeft = axesNameLeft + fontRendererObj.getStringWidth("X: ") + 4 + 20 + fontRendererObj.getStringWidth("360") / 2;
		int topShift = (20 - this.fontRendererObj.FONT_HEIGHT) / 2;

		// X:
		top += 10 + this.fontRendererObj.FONT_HEIGHT + 4 + 20 + 4;
		fontRendererObj.drawString("X:", axesNameLeft, top + topShift, 0x000000);
		drawCenteredString(fontRendererObj, String.valueOf(x_angle), axesAngleLeft, top + topShift, 0x000000);

		// Y:
		top += 20 + 4;
		fontRendererObj.drawString("Y:", axesNameLeft, top + topShift, 0x000000);
		drawCenteredString(fontRendererObj, String.valueOf(y_angle), axesAngleLeft, top + topShift, 0x000000);

		// Z:
		top += 20 + 4;
		fontRendererObj.drawString("Z:", axesNameLeft, top + topShift, 0x000000);
		drawCenteredString(fontRendererObj, String.valueOf(z_angle), axesAngleLeft, top + topShift, 0x000000);

		super.drawScreen(par1, par2, par3);
	}

}
