package com.bioxx.tfc.GUI;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Handlers.Network.AbstractPacket;
import com.bioxx.tfc.Handlers.Network.ItemNBTPacket;
import com.bioxx.tfc.Items.ItemBlueprint;

public class GuiBlueprint extends GuiScreen
{
	private World world;
	private EntityPlayer player;
	private ItemStack stack;

	private GuiTextField nameTextField;
	private static final int X_MINUS_BUTTON = 0;
	private static final int X_PLUS_BUTTON = 1;
	private static final int Y_MINUS_BUTTON = 2;
	private static final int Y_PLUS_BUTTON = 3;
	private static final int Z_MINUS_BUTTON = 4;
	private static final int Z_PLUS_BUTTON = 5;
	private static final int DONE_BUTTON = 6;
	private static final int CANCEL_BUTTON = 7;

	private static final String DONE_NAME = "gui.done";
	private static final String CANCEL_NAME = "gui.cancel";

	private int xAngle;
	private int yAngle;
	private int zAngle;

	/** The X size of the inventory window in pixels. */
	protected int xSize = 200;

	/** The Y size of the inventory window in pixels. */
	protected int ySize = 172;
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
		player = p;
		stack = player.inventory.getCurrentItem();
		if (stack.hasTagCompound())
		{
			xAngle = stack.stackTagCompound.getInteger(ItemBlueprint.TAG_X_ANGLE);
			yAngle = stack.stackTagCompound.getInteger(ItemBlueprint.TAG_Y_ANGLE);
			zAngle = stack.stackTagCompound.getInteger(ItemBlueprint.TAG_Z_ANGLE);
		}
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
		this.nameTextField = new GuiTextField(fontRendererObj, guiLeft() + 14, nameTop, 176, 20);
		this.nameTextField.setFocused(true);
		this.nameTextField.setCanLoseFocus(false);
		if (!stack.hasTagCompound()
						|| stack.stackTagCompound.getString(ItemBlueprint.TAG_ITEM_NAME).isEmpty())
		{
			this.nameTextField.setText("name_it");
			this.nameTextField.setCursorPosition(0);
			this.nameTextField.setSelectionPos(this.nameTextField.getText().length());
		}
		else
		{
			this.nameTextField.setEnabled(false);
			this.nameTextField.setFocused(false);
			this.nameTextField.setText(stack.stackTagCompound.getString(ItemBlueprint.TAG_ITEM_NAME));
		}

		Keyboard.enableRepeatEvents(true);
		int buttonsA = 20;
		int buttonsTop = nameTop + 20 + 4;
		int buttonsLeftF = guiLeft() + 10 + this.fontRendererObj.getStringWidth("X:") + 4;
		int buttonsLeftS = buttonsLeftF + buttonsA + 4 + this.fontRendererObj.getStringWidth("360") + 4;

		this.buttonList.clear();
		this.labelList.clear();

		// X:
		this.buttonList.add(new GuiButton(X_MINUS_BUTTON, buttonsLeftF, buttonsTop, buttonsA, buttonsA, "<"));
		this.buttonList.add(new GuiButton(X_PLUS_BUTTON, buttonsLeftS, buttonsTop, buttonsA, buttonsA, ">"));

		// Y:
		buttonsTop += buttonsA + 4;
		this.buttonList.add(new GuiButton(Y_MINUS_BUTTON, buttonsLeftF, buttonsTop, buttonsA, buttonsA, "<"));
		this.buttonList.add(new GuiButton(Y_PLUS_BUTTON, buttonsLeftS, buttonsTop, buttonsA, buttonsA, ">"));

		// Z:
		buttonsTop += buttonsA + 4;
		this.buttonList.add(new GuiButton(Z_MINUS_BUTTON, buttonsLeftF, buttonsTop, buttonsA, buttonsA, "<"));
		this.buttonList.add(new GuiButton(Z_PLUS_BUTTON, buttonsLeftS, buttonsTop, buttonsA, buttonsA, ">"));

		// done
		int doneWidth = fontRendererObj.getStringWidth(TFC_Core.translate(DONE_NAME)) + 20;
		this.buttonList.add(new GuiButton(
						DONE_BUTTON,
						(width + xSize) / 2 - 10 - doneWidth, (height + ySize) / 2 - 10 - buttonsA,
						doneWidth, buttonsA,
						TFC_Core.translate(DONE_NAME)
		));

		// cancel
		int cancelWidth = fontRendererObj.getStringWidth(TFC_Core.translate(CANCEL_NAME)) + 20;
		this.buttonList.add(new GuiButton(
						CANCEL_BUTTON,
						(width + xSize) / 2 - 10 - doneWidth - cancelWidth - 4, (height + ySize) / 2 - 10 - buttonsA,
						cancelWidth, buttonsA,
						TFC_Core.translate(CANCEL_NAME)
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
		((GuiButton)this.buttonList.get(DONE_BUTTON)).enabled = this.nameTextField.getText().trim().length() > 0;
		if (par1 == 13)
			this.actionPerformed((GuiButton)this.buttonList.get(DONE_BUTTON));
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

		if (guibutton.id == X_MINUS_BUTTON)
			xAngle = (xAngle == 0 ? 270 : xAngle - 90);
		else if (guibutton.id == X_PLUS_BUTTON)
			xAngle = (xAngle == 270 ? 0 : xAngle + 90);
		else if (guibutton.id == Y_MINUS_BUTTON)
			yAngle = (yAngle == 0 ? 270 : yAngle - 90);
		else if (guibutton.id == Y_PLUS_BUTTON)
			yAngle = (yAngle == 270 ? 0 : yAngle + 90);
		else if (guibutton.id == Z_MINUS_BUTTON)
			zAngle = (zAngle == 0 ? 270 : zAngle - 90);
		else if (guibutton.id == Z_PLUS_BUTTON)
			zAngle = (zAngle == 270 ? 0 : zAngle + 90);
		else if (guibutton.id == DONE_BUTTON) {
			stack.stackTagCompound.setBoolean(ItemBlueprint.TAG_COMPLETED, true);
			stack.stackTagCompound.setString(ItemBlueprint.TAG_ITEM_NAME, nameTextField.getText());
			stack.stackTagCompound.setInteger(ItemBlueprint.TAG_X_ANGLE, xAngle);
			stack.stackTagCompound.setInteger(ItemBlueprint.TAG_Y_ANGLE, yAngle);
			stack.stackTagCompound.setInteger(ItemBlueprint.TAG_Z_ANGLE, zAngle);

			AbstractPacket	pkt = new ItemNBTPacket(stack.stackTagCompound);
			((ItemNBTPacket)pkt)
							.addAcceptedTag(ItemBlueprint.TAG_COMPLETED)
							.addAcceptedTag(ItemBlueprint.TAG_ITEM_NAME)
							.addAcceptedTag(ItemBlueprint.TAG_X_ANGLE)
							.addAcceptedTag(ItemBlueprint.TAG_Y_ANGLE)
							.addAcceptedTag(ItemBlueprint.TAG_Z_ANGLE);

			TerraFirmaCraft.PACKET_PIPELINE.sendToServer(pkt);

			Minecraft.getMinecraft().displayGuiScreen(null);
		}
		else if (guibutton.id == CANCEL_BUTTON) {
			if (!stack.stackTagCompound.getBoolean(ItemBlueprint.TAG_COMPLETED)) {
				stack.setTagCompound(new NBTTagCompound());
				AbstractPacket pkt = new ItemNBTPacket(stack.stackTagCompound);
				((ItemNBTPacket)pkt)
								.addRemoveTag(ItemBlueprint.TAG_COMPLETED)
								.addRemoveTag(ItemBlueprint.TAG_ITEM_NAME)
								.addRemoveTag(ItemBlueprint.TAG_DATA)
								.addRemoveTag(ItemBlueprint.TAG_X_ANGLE)
								.addRemoveTag(ItemBlueprint.TAG_Y_ANGLE)
								.addRemoveTag(ItemBlueprint.TAG_Z_ANGLE);
				TerraFirmaCraft.PACKET_PIPELINE.sendToServer(pkt);
			}

			Minecraft.getMinecraft().displayGuiScreen(null);
		}
	}

	@Override
	public void drawScreen(int par1, int par2, float par3)
	{
		TFC_Core.bindTexture(new ResourceLocation(Reference.MOD_ID, Reference.ASSET_PATH_GUI + "gui_blueprint.png"));

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		int left = guiLeft();
		int top = guiTop();
		drawTexturedModalRect(left, top, 0, 0, xSize, ySize);

		if (!stack.hasTagCompound() || stack.stackTagCompound.getString(ItemBlueprint.TAG_ITEM_NAME).isEmpty())
			drawCenteredString(fontRendererObj, TFC_Core.translate("gui.Blueprint"), this.width / 2, top + 10, 0x000000);
		else
			drawCenteredString(fontRendererObj, TFC_Core.translate("gui.Rotate"), this.width / 2, top + 10, 0x000000);

		int axesNameLeft = left + 10;
		int axesAngleLeft = axesNameLeft + fontRendererObj.getStringWidth("X: ") + 4 + 20 + fontRendererObj.getStringWidth("360") / 2;
		int topShift = (20 - this.fontRendererObj.FONT_HEIGHT) / 2;

		top += 10 + this.fontRendererObj.FONT_HEIGHT + 4;
		this.nameTextField.drawTextBox();

		// X:
		top += 20 + 4;
		fontRendererObj.drawString("X:", axesNameLeft, top + topShift, 0x000000);
		drawCenteredString(fontRendererObj, String.valueOf(xAngle), axesAngleLeft, top + topShift, 0x000000);

		// Y:
		top += 20 + 4;
		fontRendererObj.drawString("Y:", axesNameLeft, top + topShift, 0x000000);
		drawCenteredString(fontRendererObj, String.valueOf(yAngle), axesAngleLeft, top + topShift, 0x000000);

		// Z:
		top += 20 + 4;
		fontRendererObj.drawString("Z:", axesNameLeft, top + topShift, 0x000000);
		drawCenteredString(fontRendererObj, String.valueOf(zAngle), axesAngleLeft, top + topShift, 0x000000);

		super.drawScreen(par1, par2, par3);
	}

}
