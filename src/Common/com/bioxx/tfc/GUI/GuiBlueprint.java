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
import net.minecraft.util.StatCollector;
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
	World world;
	EntityPlayer player;
	ItemStack stack;

	private GuiTextField nameTextField;
	private static final int x_m_bt_ind = 0;
	private static final int x_p_bt_ind = 1;
	private static final int y_m_bt_ind = 2;
	private static final int y_p_bt_ind = 3;
	private static final int z_m_bt_ind = 4;
	private static final int z_p_bt_ind = 5;
	private static final int done_bt_ind = 6;
	private static final int cancel_bt_ind = 7;

	private static final String done_bt_name = "gui.done";
	private static final String cancel_bt_name = "gui.cancel";

	private int x_angle;
	private int y_angle;
	private int z_angle;

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
		x_angle = stack.stackTagCompound.getInteger(ItemBlueprint.tag_x_angle);
		y_angle = stack.stackTagCompound.getInteger(ItemBlueprint.tag_y_angle);
		z_angle = stack.stackTagCompound.getInteger(ItemBlueprint.tag_z_angle);
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
		int namePrefixWidth = fontRendererObj.getStringWidth(ItemBlueprint.suffix) + 4;
		this.nameTextField = new GuiTextField(fontRendererObj, guiLeft() + 10 + namePrefixWidth, nameTop, 180 - namePrefixWidth, 20);
		this.nameTextField.setFocused(true);
		this.nameTextField.setCanLoseFocus(false);
		if (!stack.hasTagCompound()
						|| stack.stackTagCompound.getString(ItemBlueprint.tag_item_name).isEmpty())
		{
			this.nameTextField.setText("name_it");
			this.nameTextField.setCursorPosition(0);
			this.nameTextField.setSelectionPos(this.nameTextField.getText().length());
		}
		else
		{
			this.nameTextField.setEnabled(false);
			this.nameTextField.setText(stack.stackTagCompound.getString(ItemBlueprint.tag_item_name));
		}

		Keyboard.enableRepeatEvents(true);
		int buttonsA = 20;
		int buttonsTop = nameTop + 20 + 4;
		int buttonsLeftF = guiLeft() + 10 + this.fontRendererObj.getStringWidth("X:") + 4;
		int buttonsLeftS = buttonsLeftF + buttonsA + 4 + this.fontRendererObj.getStringWidth("360") + 4;

		this.buttonList.clear();
		this.labelList.clear();

		// X:
		this.buttonList.add(new GuiButton(x_m_bt_ind, buttonsLeftF, buttonsTop, buttonsA, buttonsA, "<"));
		this.buttonList.add(new GuiButton(x_p_bt_ind, buttonsLeftS, buttonsTop, buttonsA, buttonsA, ">"));

		// Y:
		buttonsTop += buttonsA + 4;
		this.buttonList.add(new GuiButton(y_m_bt_ind, buttonsLeftF, buttonsTop, buttonsA, buttonsA, "<"));
		this.buttonList.add(new GuiButton(y_p_bt_ind, buttonsLeftS, buttonsTop, buttonsA, buttonsA, ">"));

		// Z:
		buttonsTop += buttonsA + 4;
		this.buttonList.add(new GuiButton(z_m_bt_ind, buttonsLeftF, buttonsTop, buttonsA, buttonsA, "<"));
		this.buttonList.add(new GuiButton(z_p_bt_ind, buttonsLeftS, buttonsTop, buttonsA, buttonsA, ">"));

		// done
		int doneWidth = fontRendererObj.getStringWidth(StatCollector.translateToLocal(done_bt_name)) + 20;
		this.buttonList.add(new GuiButton(
						done_bt_ind,
						(width + xSize) / 2 - 10 - doneWidth, (height + ySize) / 2 - 10 - buttonsA,
						doneWidth, buttonsA,
						StatCollector.translateToLocal(done_bt_name)
		));

		// cancel
		int cancelWidth = fontRendererObj.getStringWidth(StatCollector.translateToLocal(cancel_bt_name)) + 20;
		this.buttonList.add(new GuiButton(
						cancel_bt_ind,
						(width + xSize) / 2 - 10 - doneWidth - cancelWidth - 4, (height + ySize) / 2 - 10 - buttonsA,
						cancelWidth, buttonsA,
						StatCollector.translateToLocal(cancel_bt_name)
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
		((GuiButton)this.buttonList.get(done_bt_ind)).enabled = this.nameTextField.getText().trim().length() > 0;
		if (par1 == 13)
			this.actionPerformed((GuiButton)this.buttonList.get(done_bt_ind));
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

		if (guibutton.id == x_m_bt_ind)
			x_angle = (x_angle == 0 ? 270 : x_angle - 90);
		else if (guibutton.id == x_p_bt_ind)
			x_angle = (x_angle == 270 ? 0 : x_angle + 90);
		else if (guibutton.id == y_m_bt_ind)
			y_angle = (y_angle == 0 ? 270 : y_angle - 90);
		else if (guibutton.id == y_p_bt_ind)
			y_angle = (y_angle == 270 ? 0 : y_angle + 90);
		else if (guibutton.id == z_m_bt_ind)
			z_angle = (z_angle == 0 ? 270 : z_angle - 90);
		else if (guibutton.id == z_p_bt_ind)
			z_angle = (z_angle == 270 ? 0 : z_angle + 90);
		else if (guibutton.id == done_bt_ind) {
			stack.stackTagCompound.setBoolean(ItemBlueprint.tag_completed, true);
			stack.stackTagCompound.setString(ItemBlueprint.tag_item_name, nameTextField.getText());
			stack.stackTagCompound.setInteger(ItemBlueprint.tag_x_angle, x_angle);
			stack.stackTagCompound.setInteger(ItemBlueprint.tag_y_angle, y_angle);
			stack.stackTagCompound.setInteger(ItemBlueprint.tag_z_angle, z_angle);

			AbstractPacket	pkt = new ItemNBTPacket(stack.stackTagCompound);
			((ItemNBTPacket)pkt)
							.addAcceptedTag(ItemBlueprint.tag_completed)
							.addAcceptedTag(ItemBlueprint.tag_item_name)
							.addAcceptedTag(ItemBlueprint.tag_x_angle)
							.addAcceptedTag(ItemBlueprint.tag_y_angle)
							.addAcceptedTag(ItemBlueprint.tag_z_angle);

			TerraFirmaCraft.packetPipeline.sendToServer(pkt);

			Minecraft.getMinecraft().displayGuiScreen(null);
		}
		else if (guibutton.id == cancel_bt_ind) {
			if (!stack.stackTagCompound.getBoolean(ItemBlueprint.tag_completed)) {
				stack.setTagCompound(new NBTTagCompound());
				AbstractPacket pkt = new ItemNBTPacket(stack.stackTagCompound);
				((ItemNBTPacket)pkt)
								.addRemoveTag(ItemBlueprint.tag_completed)
								.addRemoveTag(ItemBlueprint.tag_item_name)
								.addRemoveTag(ItemBlueprint.tag_data)
								.addRemoveTag(ItemBlueprint.tag_x_angle)
								.addRemoveTag(ItemBlueprint.tag_y_angle)
								.addRemoveTag(ItemBlueprint.tag_z_angle);
				TerraFirmaCraft.packetPipeline.sendToServer(pkt);
			}

			Minecraft.getMinecraft().displayGuiScreen(null);
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

		int axesNameLeft = left + 10;
		int axesAngleLeft = axesNameLeft + fontRendererObj.getStringWidth("X: ") + 4 + 20 + fontRendererObj.getStringWidth("360") / 2;
		int topShift = (20 - this.fontRendererObj.FONT_HEIGHT) / 2;

		top += 10 + this.fontRendererObj.FONT_HEIGHT + 4;
		fontRendererObj.drawString(ItemBlueprint.suffix, axesNameLeft, top + topShift, 0x000000);
		this.nameTextField.drawTextBox();

		// X:
		top += 20 + 4;
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
