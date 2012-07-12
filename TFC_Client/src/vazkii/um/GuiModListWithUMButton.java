package vazkii.um;

import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.ModLoader;

/**
 * @author Vazkii, cpw
 */
public class GuiModListWithUMButton extends cpw.mods.fml.client.GuiModList {

	private GuiScreen mainMenu;

	public GuiModListWithUMButton(GuiScreen mainMenu) {
		super(mainMenu);
	}

	public void initGui() {
		super.initGui();
		controlList.add(new GuiButton(1337, 10, height - 30, fontRenderer
				.getStringWidth("Update Manager") + 6, 20, "Update Manager"));
	}

	// Bugfix for messed up render if there's a world behind.
	public void drawWorldBackground(int par1) {
		drawBackground(par1);
	}

	protected void actionPerformed(GuiButton button) {
		if (button.id == 1337)
			ModLoader.openGUI(ModLoader.getMinecraftInstance().thePlayer,
					new vazkii.um.GuiModList(mainMenu));

		super.actionPerformed(button);
	}

}
