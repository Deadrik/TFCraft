package vazkii.um;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.src.FontRenderer;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.ModLoader;

/**
 * @author Vazkii
 */
public class GuiChangelog extends GuiScreen {

	GuiModList parentGui;
	UpdateManagerMod linkedMod;
	protected String[] modChangelog;
	GuiSlotChangelog changelogSlot;

	public GuiChangelog(GuiModList parent, UpdateManagerMod linkedMod) {
		super();
		parentGui = parent;
		this.linkedMod = linkedMod;
		modChangelog = readChangelog(linkedMod.getChangelogURL());
	}

	protected void actionPerformed(GuiButton button) {
		if (button.id == 0)
			UpdateManager.openWebpage(linkedMod.getChangelogURL());
		else {
			ModLoader.openGUI(mc.thePlayer, parentGui);
			parentGui.selectModIndex(parentGui.getSelected());
		}
		changelogSlot.actionPerformed(button);
		super.actionPerformed(button);
	}

	public void initGui() {
		super.initGui();
		controlList.clear();
		controlList.add(new GuiButton(0, 5, 5, fontRenderer
				.getStringWidth("View Raw") + 8, 20, "View Raw"));
		controlList.add(new GuiButton(1, width - 13
				- fontRenderer.getStringWidth("Back"), 5, fontRenderer
				.getStringWidth("Back") + 8, 20, "Back"));
		changelogSlot = new GuiSlotChangelog(this);
	}

	public void drawScreen(int par1, int par2, float par3) {

		changelogSlot.drawScreen(par1, par2, par3);
		drawCenteredString(this.fontRenderer, "Latest Changelog for "
				+ linkedMod.getModName() + ":", this.width / 2, 16, 0xFFFFFF);
		super.drawScreen(par1, par2, par3);
	}

	protected FontRenderer fontRenderer() {
		return fontRenderer;
	}

	String[] readChangelog(String URL) {
		try {
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(new URL(URL).openStream()));
			List<String> changeList = new ArrayList<String>();
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				List<String> extraLines = new ArrayList();
				while (line.length() > 100) {
					extraLines.add(line.substring(0, 100));
					line = line.substring(100, line.length());
				}
				for (String s : extraLines)
					changeList.add(s);
				changeList.add(line);
			}

			bufferedReader.close();
			return changeList.toArray(new String[changeList.size()]);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
