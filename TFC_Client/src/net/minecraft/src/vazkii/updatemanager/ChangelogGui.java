package net.minecraft.src.vazkii.updatemanager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.src.BaseMod;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.mod_UpdateManager;

public class ChangelogGui extends GuiScreen {

	IUMAdvanced mod;
	String[] changelog; 
	
	public ChangelogGui(IUMAdvanced mod) {
		super();
		this.mod = mod;
		changelog = readChangelog(mod.getChangelogURL());
	}
	
	public void initGui(){
		super.initGui();
		controlList.clear();
		controlList.add(new GuiButton(0, width/2 - (fontRenderer.getStringWidth("Update")/2 + fontRenderer.getStringWidth("Back")/2 + 30) - fontRenderer.getStringWidth("Raw")/2, 16, fontRenderer.getStringWidth("Update") + 8, 20, "Update"));
		controlList.add(new GuiButton(1, width/2 - fontRenderer.getStringWidth("Update")/2 - 4, 16, fontRenderer.getStringWidth("Back") + 8, 20, "Back"));
		controlList.add(new GuiButton(2, width/2 + (fontRenderer.getStringWidth("Update")/2 - fontRenderer.getStringWidth("Back")/2 - 12) + fontRenderer.getStringWidth("Raw"), 16, fontRenderer.getStringWidth("Raw") + 8, 20, "Raw"));
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
			Runtime.getRuntime().exec("cmd /c start " + ((IUpdateManager)mod).getModURL()); 
			} 
			catch(IOException e) {
			}
		return;
		}else if(par1GuiButton.id == 1){
			mc.displayGuiScreen(new ModListGui());
		}else{
			try { 
				Runtime.getRuntime().exec("cmd /c start " + mod.getChangelogURL()); 
				} 
				catch(IOException e) {
				}
		}

    }
		
	public void drawScreen(int par1, int par2, float par3){
        drawDefaultBackground();
		drawGradientRect(5, 15, 7, height-15, -0xFFFFFF, UMUtils.parseHex(mod_UpdateManager.barHex));
		drawGradientRect(width-17, 15, width-15, height-15, -0xFFFFFF, UMUtils.parseHex(mod_UpdateManager.barHex));
		drawGradientRect(0, 0, width, height, -0xFFFFFF, UMUtils.parseHex(mod_UpdateManager.mainHex));
		drawCenteredString(fontRenderer, getModName((IUpdateManager)mod) + " Changelog:", width / 2, 4, 0xffffff);

		for(int i=0; changelog != null && i<changelog.length; i++){
			if(changelog[i].contains("	"))
			drawString(fontRenderer, changelog[i].replace("	", "     "), 8, 50+i*12, 0xFFFFFF);
			else drawString(fontRenderer, changelog[i], 8, 50+i*12, 0xFFFFFF);
		}
		
		super.drawScreen(par1, par2, par3);
	}
	
	private String getModName(IUpdateManager mod){
		if(((IUMAdvanced)mod).getModName() == "nil") return ((BaseMod)mod).getName();
		else return ((IUMAdvanced)mod).getModName();
	}
	
	private String[] readChangelog(String URL){
		try {
	        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new URL(URL).openStream()));
	        List<String> changeList = new ArrayList<String>();
	        String line = null;
	        while ((line = bufferedReader.readLine()) != null)
	            changeList.add(line);
	        bufferedReader.close();
	        return changeList.toArray(new String[changeList.size()]);
		} catch (MalformedURLException e) {
		} catch (IOException e) {
		}
		return null;
	
	}

}
