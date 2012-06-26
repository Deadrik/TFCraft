package net.minecraft.src.vazkii.updatemanager;

public class UMUtils {

	protected static boolean isModAdvanced(IUpdateManager mod){
		try{
			for (Class c : mod.getClass().getInterfaces()) {
		if (c.isInterface() && c.equals(IUMAdvanced.class)) 
			return true;
			}
			return false;
		}catch(Exception e){
			return false;
		}
	}

	protected static int parseHex(String str){
		return Integer.parseInt(str, 16);
	}
	
}
