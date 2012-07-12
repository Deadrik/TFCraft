package vazkii.um;

import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.GuiTextField;
import net.minecraft.src.ModLoader;

import org.lwjgl.opengl.GL11;

/**
 * @author Vazkii
 */
public class GuiSettings extends GuiScreen {

	GuiTextField timeField;
	GuiButton buttonSMPCheck;
	GuiButton buttonAutoDownload;
	
	GuiScreen parentGui;
	
	public GuiSettings(GuiScreen parent){
		super();
		parentGui = parent;
	}
	
	public void initGui(){
		timeField = new GuiRestrictedTextField(fontRenderer, 10, 90, 50, 16, "1234567890");
		timeField.setMaxStringLength(6);
		timeField.setText(""+Settings.getInt("checkDelay"));
		controlList.clear();
		controlList.add(new GuiButton(0, 10, 30, fontRenderer.getStringWidth("Back") + 8, 20, "Back"));
		controlList.add(new GuiButton(1, 65, 88, fontRenderer.getStringWidth("Set") + 8, 20 ,"Set"));
		buttonSMPCheck = new GuiButton(2, 10, 115, fontRenderer.getStringWidth("Check for Updates On SMP: Yes") + 8, 20, "Check for Updates On SMP: " + (Settings.getBoolean("smpEnable") ? "Yes" : "No" ));
		controlList.add(buttonSMPCheck);
		buttonAutoDownload = new GuiButton(3, 10, 140, fontRenderer.getStringWidth("Automatically Download outdated Mods: Yes") + 8, 20, "Automatically Download outdated Mods: " + (Settings.getBoolean("autoDownload") ? "Yes" : "No" ));
		controlList.add(buttonAutoDownload);
		super.initGui();
	}
	
    protected void keyTyped(char par1, int par2)
    {
    	if(timeField.getIsFocused())
    		timeField.textboxKeyTyped(par1, par2);
    	super.keyTyped(par1, par2);
    }
    
    protected void mouseClicked(int par1, int par2, int par3)
    {
        timeField.mouseClicked(par1, par2, par3);
        super.mouseClicked(par1, par2, par3);
    }
	
    public void updateScreen()
    {
        timeField.updateCursorCounter();
    }
    
    protected void actionPerformed(GuiButton button) {
    	switch(button.id){
    	case 0 : {
    		ModLoader.openGUI(ModLoader.getMinecraftInstance().thePlayer, parentGui);
    		break;
    	}
    	case 1 : {
    		Settings.setInt("checkDelay", Integer.parseInt(timeField.getText()));
    		break;
    	}
    	case 2 : {
    		Settings.setBoolean("smpEnable", !Settings.getBoolean("smpEnable"));
    		buttonSMPCheck.displayString = "Check for Updates On SMP: " + (Settings.getBoolean("smpEnable") ? "Yes" : "No");
    		break;
    	}
    	case 3 : {
    		Settings.setBoolean("autoDownload", !Settings.getBoolean("autoDownload"));
    		buttonAutoDownload.displayString = "Automatically Download outdated Mods: " + (Settings.getBoolean("autoDownload") ? "Yes" : "No");
    		break;
    	}
    	}
    	
    	super.actionPerformed(button);
    }
	
	public void drawScreen(int par1, int par2, float par3)
	{
		drawWorldBackground(0);
		GL11.glPushMatrix();
		GL11.glScaled(2.0F, 2.0F, 2.0F);
		fontRenderer.drawStringWithShadow("Mod Update Manager Settings", 5, 5, 0xFFFFFF);
		GL11.glScaled(1.0F, 1.0F, 1.0F);
		GL11.glPopMatrix();
		fontRenderer.drawString("Time to wait between checks, in seconds (Set to 0 to disable checks):", 10, 78, 0xd7edea);
		timeField.drawTextBox();
		int shifty = 180;
		shifty = typeLine("Note: A Mod will only get downloaded automatically if the mod's", 20, shifty);
		shifty = typeLine("author(s) endorses that option.", 20, shifty);
		
		super.drawScreen(par1, par2, par3);
    }
	
	public int typeLine(String line, int offset, int shifty)
	{
		if(line != null){
		    fontRenderer.drawString(line, offset, shifty, 0xd7edea);
		    return shifty + 10;
		}
		return shifty;
	}
	
    public void drawWorldBackground(int par1) {
    	drawBackground(0);
	}
	
    public void drawBackground(int i)
    {
        super.drawBackground(0);
    }
	
}
