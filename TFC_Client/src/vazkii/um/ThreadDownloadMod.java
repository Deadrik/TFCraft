package vazkii.um;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import net.minecraft.src.ModLoader;

/**
 * @author Vazkii
 */
public class ThreadDownloadMod extends Thread {

	public static int amountOfDownloadResourceThreads = 0;
	public static List<String> downloadings = new LinkedList();
	
	URL downloadUrl;
	String modName;
	
	long downloadStartTime;
	long downloadFinishTime;
	
	byte[] buffer = new byte[10240];
	
	int totalBytesDownloaded;
	int bytesJustDownloaded;
	
	InputStream webReader;
	
	public ThreadDownloadMod(String urlToFind, UpdateManagerMod mod){
		if(urlToFind == null) return;
		
		setName("Update Manager Download File Thread " + ++amountOfDownloadResourceThreads);
		
		modName = mod.getModName() + " " + UpdateManager.getWebVersionFor(mod);
		try {
			downloadUrl = getUrlFromFile(urlToFind);
			downloadUrl.openConnection();
			webReader = downloadUrl.openStream();
			downloadings.add(modName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		setDaemon(true);
		start();
	}
	
	URL getUrlFromFile(String file){
    	URL url;
		try {
			url = new URL(file);
			BufferedReader r = new BufferedReader(new InputStreamReader(url.openStream()));
			String s = r.readLine();
			r.close();
			return new URL(s);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void run(){
		try{
			File f = new File(ModLoader.getMinecraftInstance().getAppDir("minecraft/downloadedMods"), modName + ".zip");
			f.createNewFile();
			System.out.println("[Mod Update Manager] Starting to download file: " + modName + ".zip to " + f.getAbsolutePath() + ".");
			FileOutputStream outputStream = new FileOutputStream(f.getAbsolutePath());
			
			downloadStartTime = System.currentTimeMillis();
			
			while((bytesJustDownloaded = webReader.read(buffer)) > 0){
				outputStream.write(buffer, 0, bytesJustDownloaded);
				buffer = new byte[10240];
				totalBytesDownloaded += bytesJustDownloaded;
			}
			
			downloadFinishTime = System.currentTimeMillis();
			
			if(ModLoader.getMinecraftInstance().thePlayer != null)
			ModLoader.getMinecraftInstance().thePlayer.addChatMessage("Finished downloading file: " + modName + ".zip, took " + ((downloadFinishTime - downloadStartTime) / 1000) + " seconds.");
			
			outputStream.close();
			webReader.close();
			--amountOfDownloadResourceThreads;
			downloadings.remove(modName);
			finalize();
		}catch(Throwable e){
			e.printStackTrace();
			--amountOfDownloadResourceThreads;
			downloadings.remove(modName);
			try {
				finalize();
			} catch (Throwable e1) {
				e1.printStackTrace();
			}
		}
	}
}
