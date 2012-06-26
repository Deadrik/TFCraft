package net.minecraft.src.vazkii.updatemanager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import net.minecraft.src.BaseMod;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.mod_UpdateManager;

public class ModListGui extends GuiScreen {

	public ModListGui() {
		super();
	}
	
	String week;
	
	public void initGui(){
		super.initGui();
		if(!mod_UpdateManager.isOnline) return;
		week = readMOTW_Week();
		controlList.clear();
		controlList.add(new GuiButton(0, width - (fontRenderer.getStringWidth("Mod Manager Forum Topic") + 4) - width/30, height - 30, fontRenderer.getStringWidth("Mod Manager Forum Topic") + 8, 20, "Mod Manager Forum Topic"));
		controlList.add(new GuiButton(1, width - (fontRenderer.getStringWidth("Mod Manager Forum Topic") + 4) - width/30 - fontRenderer.getStringWidth("MotW") - 16, height - 30, fontRenderer.getStringWidth("MotW") + 8, 20, "MotW"));
		if(!UMCore.outdatedMods.isEmpty()){
			Iterator it = UMCore.outdatedMods.iterator();
			int index = 0;
			while(it.hasNext() && index++*15+55 < height - 1){
				controlList.add(new GuiButton(index+2, 20, 46 + (index*15), fontRenderer.getStringWidth("Update") + 8, 14, "Update"));
				it.next();
			}
		}
	}
	
	protected void keyTyped(char par1, int par2)
    {
		if(par2 == mod_UpdateManager.key.keyCode || par2 == 1)
			mc.displayGuiScreen(null);
    }
	
	protected void actionPerformed(GuiButton par1GuiButton)
    {
		if(par1GuiButton.id == 0){
			try { 
				Runtime.getRuntime().exec("cmd /c start http://www.minecraftforum.net/topic/1243564-any-version-mod-update-manager-last-updated-22512/"); 
				} 
				catch(IOException e) {
				}
			return;
		}else if(par1GuiButton.id == 1){
			try {
				Runtime.getRuntime().exec("cmd /c start " + readMOTW());
			} catch (IOException e) {
			} 
			return;
		}
		
		IUpdateManager mod = UMCore.outdatedMods.get(par1GuiButton.id-3);
		
		try { 
			if(UMUtils.isModAdvanced(mod) && ((IUMAdvanced)mod).getChangelogURL() != "nil")
				mc.displayGuiScreen(new ChangelogGui((IUMAdvanced)mod));
			else
		Runtime.getRuntime().exec("cmd /c start " + mod.getModURL()); 
		} 
		catch(IOException e) {
			e.printStackTrace();
		}
    }
	
	public void drawScreen(int par1, int par2, float par3){
        drawDefaultBackground();
		drawGradientRect(5, 15, 7, height-15, -0xFFFFFF, UMUtils.parseHex(mod_UpdateManager.barHex));
		drawGradientRect(width-17, 15, width-15, height-15, -0xFFFFFF, UMUtils.parseHex(mod_UpdateManager.barHex));
		drawGradientRect(0, 0, width, height, -0xFFFFFF, UMUtils.parseHex(mod_UpdateManager.mainHex));
		drawCenteredString(fontRenderer, "Update Manager: Outdated Mods: " + (UMCore.outdatedMods.size()), width / 2, 4, 0xffffff);
		drawString(fontRenderer, week, width - (fontRenderer.getStringWidth("Mod Manager Forum Topic") + 4) - width/30 - fontRenderer.getStringWidth("MotW") - fontRenderer.getStringWidth(week)/2 - 12, height - 9, 0xffffff);
		if(UMCore.outdatedMods.isEmpty()){
			drawCenteredString(fontRenderer, "§2All your mods are up to date!", width / 2, 32, 0xffffff);
		}else if(!mod_UpdateManager.isOnline){
			drawCenteredString(fontRenderer, "§4You are Offline!", width / 2, 32, 0xffffff);
		}else
		{
			Iterator it = UMCore.outdatedMods.iterator();
			int index = 0;

			while(it.hasNext() && index++*15+55 < height - 1){
				IUpdateManager mod = (IUpdateManager)it.next();
				drawString(fontRenderer, mod.getModType().displayName + getModName(mod), fontRenderer.getStringWidth("Update") + 35, 50+(index*15), mod.getModType().hexColor);		
			}
		}
		if(UMCore.outdatedMods.size() * 15 + 55 > height - 1)
			drawCenteredString(fontRenderer, "§4You have too many outated mods to display, focus on updating these first.", width / 2, 32, 0xffffff);
		
		super.drawScreen(par1, par2, par3);
	}
	
	private String readMOTW(){
		URL motwPage;
		try {
			motwPage = new URL("http://dl.dropbox.com/u/34938401/Update%20Manager/MotW.txt");
			BufferedReader motwReader = new BufferedReader(new InputStreamReader(motwPage.openStream()));
			String line = motwReader.readLine();
			motwReader.close();
			return line;
		} catch (MalformedURLException e) {
			return "";
		} catch (IOException e1){
			return "";
		}
	}
	
	private String readMOTW_Week(){
		URL motwPage;
		try {
			motwPage = new URL("http://dl.dropbox.com/u/34938401/Update%20Manager/MotW_Week.txt");
			BufferedReader motwReader = new BufferedReader(new InputStreamReader(motwPage.openStream()));
			String line = motwReader.readLine();
			motwReader.close();
			return line;
		} catch (MalformedURLException e) {
			return "";
		} catch (IOException e1){
			return "";
		}
	}
	
	private String getModName(IUpdateManager mod){
		if(!UMUtils.isModAdvanced(mod)) return ((BaseMod)mod).getName();
		else if(((IUMAdvanced)mod).getModName() == "nil") return ((BaseMod)mod).getName();
		else return ((IUMAdvanced)mod).getModName();
	}
	
}