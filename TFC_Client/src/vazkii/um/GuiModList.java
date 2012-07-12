package vazkii.um;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.LinkedList;

import net.minecraft.client.Minecraft;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.ModLoader;
import net.minecraft.src.StringTranslate;

import org.lwjgl.Sys;
import org.lwjgl.opengl.GL11;

/**
 * @author Vazkii, cpw
 */
public class GuiModList extends GuiScreen {

	private GuiUMModSlot modList;
	private int listWidth;
	private int selected = -1;
	private GuiScreen parentGui;

	String motwWeek = null;

	protected LinkedList<UpdateManagerMod> mods;

	public GuiModList(GuiScreen parentGui) {
		super();
		this.parentGui = parentGui;
	}

	public void initGui() {
		mods = UpdateManager.sortMods(UpdateManager.loadedModsSet,
				UpdateManager.loadedModsMap);
		for (UpdateManagerMod mod : mods)
			listWidth = Math.max(listWidth,
					fontRenderer.getStringWidth(mod.getModName()) + 20);
		StringTranslate translations = StringTranslate.getInstance();
		int shiftx = 10;
		int shifty = 40;

		controlList.add(new GuiButton(0, shiftx, height - 30, fontRenderer
				.getStringWidth("Back") + 8, 20, "Back"));
		shiftx += fontRenderer.getStringWidth("Back") + 12;
		controlList.add(new GuiButton(1, shiftx, height - 30, fontRenderer
				.getStringWidth("Update Manager Forum Topic") + 8, 20,
				"Update Manager Forum Topic"));
		shiftx += fontRenderer.getStringWidth("Update Manager Forum Topic") + 12;
		controlList.add(new GuiButton(2, shiftx, height - 30, fontRenderer
				.getStringWidth("MotW") + 8, 20, "MotW"));
		shiftx += fontRenderer.getStringWidth("MotW") + 12;
		controlList.add(new GuiButton(3, shiftx, height - 30, fontRenderer
				.getStringWidth("Settings") + 8, 20, "Settings"));
		shiftx += fontRenderer.getStringWidth("Settings") + 12;
		controlList.add(new GuiButton(4, width
				- fontRenderer.getStringWidth("Website") - 12, shifty,
				fontRenderer.getStringWidth("Website") + 8, 20, "Website"));
		shifty += 22;
		controlList.add(new GuiButton(5, width
				- fontRenderer.getStringWidth("Changelog") - 12, shifty,
				fontRenderer.getStringWidth("Changelog") + 8, 20, "Changelog"));
		shifty += 22;
		controlList.add(new GuiButton(6, width
				- fontRenderer.getStringWidth("Download") - 12, shifty,
				fontRenderer.getStringWidth("Download") + 8, 20, "Download"));
		controlList.add(new GuiButton(7, shiftx, height - 30, fontRenderer
				.getStringWidth("View Downloaded Mods") + 8, 20,
				"View Downloaded Mods"));
		for (int i = UpdateManager.online ? 4 : 1; i <= 6; i++)
			((GuiButton) controlList.get(i)).enabled = i == 3;

		if (selected >= 0)
			selectModIndex(selected);

		this.modList = new GuiUMModSlot(this, listWidth);
		this.modList.registerScrollButtons(this.controlList, 7, 8);
	}

	public void drawScreen(int par1, int par2, float par3) {
		this.modList.drawScreen(par1, par2, par3);
		int d = updateDownloads();
		this.drawCenteredString(
				this.fontRenderer,
				UpdateManager.online ? ("Mod Update Manager by Vazkii: "
						+ UpdateManager.getQuantEntries(Boolean.valueOf(true),
								UpdateManager.loadedModsMap) + "/"
						+ UpdateManager.loadedModsSet.size() + (d > 0 ? (" ("
						+ d + " ongoing Downloads)") : (".")))
						: "Mod Update Manager §c(OFFLINE)", this.width / 2, 16,
				0xFFFFFF);
		if (UpdateManager.online)
			fontRenderer
					.drawStringWithShadow(
							motwWeek == null ? motwWeek = getMotwWeek("https://dl.dropbox.com/u/34938401/Update%20Manager/MotW_Week.txt")
									: motwWeek, 180, height - 9, 0xFFFFFF);
		int offset = this.listWidth + 20;
		UpdateManagerMod selectedMod;
		if (getSelected() >= 0
				&& (selectedMod = mods.get(getSelected())) != null) {
			int shifty = 35;
			GL11.glPushMatrix();
			GL11.glScalef(2.0F, 2.0F, 2.0F);
			fontRenderer.drawStringWithShadow(selectedMod.getModName(),
					offset / 2, shifty / 2, selectedMod.getModType().getHex());
			GL11.glScalef(1.0F, 1.0F, 1.0F);
			GL11.glPopMatrix();
			shifty += 25;
			shifty = typeLine(
					selectedMod.getModType() != ModType.UNDEFINED ? "Mod Type: "
							+ selectedMod.getModType().getName()
							: null, offset, shifty);
			shifty = typeLine(
					selectedMod.getReleaseType() != ModReleaseType.RELEASED ? selectedMod
							.getReleaseType().getName() : null, offset, shifty,
					selectedMod.getReleaseType().getHex());
			shifty += 5;
			shifty = typeLine("Client Version: " + selectedMod.getUMVersion(),
					offset, shifty);
			shifty = typeLine(
					"Latest Version: "
							+ (selectedMod.getObjectToCheck() instanceof Integer ? "Release "
									+ UpdateManager
											.getWebVersionFor(selectedMod)
									: UpdateManager
											.getWebVersionFor(selectedMod)),
					offset, shifty);
			shifty += 15;
			if (selectedMod.addNotes() != null) {
				shifty = typeLine("Notes:", offset + 10, shifty);
				for (String s : selectedMod.addNotes())
					shifty = typeLine(s, offset + 10, shifty);
			}
			if (ThreadDownloadMod.downloadings.contains(selectedMod
					.getModName()))
				fontRenderer.drawStringWithShadow("Downloading...", width - 6
						- fontRenderer.getStringWidth("Downloading..."), 110,
						0x00FF00);
		}
		super.drawScreen(par1, par2, par3);
	}

	protected void actionPerformed(GuiButton button) {
		UpdateManagerMod mod = null;

		if (selected >= 0)
			mod = mods.get(getSelected());

		if (button.enabled)
			switch (button.id) {
			case 0: {
				ModLoader.openGUI(ModLoader.getMinecraftInstance().thePlayer,
						new GuiModListWithUMButton(parentGui));
				break;
			}
			case 1: {
				UpdateManager.openWebpage(UpdateManager.umWebpage);
				break;
			}
			case 2: {
				try {
					URL url = new URL(
							"https://dl.dropbox.com/u/34938401/Update%20Manager/MotW.txt");
					BufferedReader r = new BufferedReader(
							new InputStreamReader(url.openStream()));
					UpdateManager.openWebpage(r.readLine());
				} catch (IOException e) {
					e.printStackTrace();
				}

				break;
			}
			case 3: {
				ModLoader.openGUI(ModLoader.getMinecraftInstance().thePlayer,
						new GuiSettings(this));
				break;
			}
			case 4: {
				UpdateManager.openWebpage(mod.getModURL());
				break;
			}
			case 5: {
				ModLoader.openGUI(ModLoader.getMinecraftInstance().thePlayer,
						new GuiChangelog(this, mod));
				break;
			}
			case 6: {
				if(mod.getDisclaimerURL() == null){
					if (!ThreadDownloadMod.downloadings.contains(mod.getModName())) {
						new ThreadDownloadMod(mod.getDirectDownloadURL(), mod);
						button.enabled = false;
					}
				} else ModLoader.openGUI(ModLoader.getMinecraftInstance().thePlayer, new GuiDisclaimer(this, mod));

				break;
			}
			case 7: {
				Sys.openURL("file://"
						+ (new File(Minecraft.getMinecraftDir(),
								"downloadedMods")).getAbsolutePath());
				break;
			}
			}

		super.actionPerformed(button);
	}

	String getMotwWeek(String urlString) {
		try {
			URL url = new URL(urlString);
			BufferedReader r = new BufferedReader(new InputStreamReader(
					url.openStream()));
			return r.readLine();
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}

	public void selectModIndex(int var1) {
		selected = var1;
		UpdateManagerMod mod = mods.get(var1);
		GuiButton bWebsite = (GuiButton) controlList.get(4);
		GuiButton bChangelog = (GuiButton) controlList.get(5);
		GuiButton bDownload = (GuiButton) controlList.get(6);

		if (UpdateManager.online) {
			bWebsite.enabled = true;
			bChangelog.enabled = mod.getChangelogURL() != null;
			bDownload.enabled = mod.getDirectDownloadURL() != null
					&& !ThreadDownloadMod.downloadings.contains(mod
							.getModName());
		}
	}

	int updateDownloads() {
		if (selected < 0)
			return 0;
		int i = ThreadDownloadMod.downloadings.size();
		GuiButton bDownload = (GuiButton) controlList.get(6);
		if (mods.get(selected).getDirectDownloadURL() != null
				&& !ThreadDownloadMod.downloadings.contains(mods.get(selected)
						.getModName()))
			bDownload.enabled = true;

		return i;
	}

	public boolean isIndexSelected(int var1) {
		return var1 == getSelected();
	}

	public int typeLine(String line, int offset, int shifty) {
		return typeLine(line, offset, shifty, 0xd7edea);
	}

	public int typeLine(String line, int offset, int shifty, int hex) {
		if (line != null) {
			fontRenderer.drawString(line, offset, shifty, hex);
			return shifty + 10;
		}
		return shifty;
	}

	public int getSelected() {
		return selected;
	}
}
