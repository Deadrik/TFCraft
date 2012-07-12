package vazkii.um;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.MathHelper;
import net.minecraft.src.ModLoader;

public class GuiDisclaimer extends GuiScreen {

	String header;
	String[] disclaimer;
	GuiScreen parentGui;
	UpdateManagerMod mod;
	
	GuiButton noDown;
	
	public GuiDisclaimer(GuiScreen parent, UpdateManagerMod mod){
		super();
		this.mod = mod;
		parentGui = parent;
		disclaimer = readDisclaimer(mod);
	}
	
	public void initGui(){
		controlList.clear();
		controlList.add(new GuiButton(0, width/2-50, height-60, 100, 20, "Download Anyway"));
		noDown = new GuiButton(1, width/2-50, height-90, 100, 20, "Don't Download");

		controlList.add(noDown);
		if(disclaimer.length < 1 || MathHelper.stringNullOrLengthZero(header = disclaimer[0]))
			actionPerformed(noDown);
		super.initGui();
	}
	
	protected void actionPerformed(GuiButton button) {
		if(button.id == 0){
			if (!ThreadDownloadMod.downloadings.contains(mod.getModName()))
				new ThreadDownloadMod(mod.getDirectDownloadURL(), mod);
		}
		ModLoader.openGUI(ModLoader.getMinecraftInstance().thePlayer, parentGui);
    	super.actionPerformed(button);
    }
	
	public void drawScreen(int par1, int par2, float par3)
	{
		drawWorldBackground(0);
		GL11.glPushMatrix();
		GL11.glScaled(2.0F, 2.0F, 2.0F);
		this.drawCenteredString(fontRenderer, header, width/4, 5, 0xFF0000);
		GL11.glScaled(1.0F, 1.0F, 1.0F);
		GL11.glPopMatrix();
		for(int i=0; i<disclaimer.length-1; i++)
			drawCenteredString(fontRenderer, disclaimer[i+1], width/2, i*12+30, 0xFFFFFF);
		super.drawScreen(par1, par2, par3);
    }
	
	String[] readDisclaimer(UpdateManagerMod mod){
		try {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new URL(mod.getDisclaimerURL()).openStream()));
			List<String> lineList = new ArrayList<String>();
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				List<String> extraLines = new ArrayList();
				while (line.length() > 100) {
					extraLines.add(line.substring(0, 100));
					line = line.substring(100, line.length());
				}
				for (String s : extraLines)
					lineList.add(s);
				lineList.add(line);
			}
			bufferedReader.close();
			return lineList.toArray(new String[lineList.size()]);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
 	
}
