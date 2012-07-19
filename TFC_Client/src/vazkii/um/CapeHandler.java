package vazkii.um;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.MathHelper;
import net.minecraft.src.ModLoader;
import net.minecraft.src.ModelBiped;
import net.minecraft.src.Render;
import net.minecraft.src.RenderBiped;
import net.minecraft.src.RenderEngine;
import net.minecraft.src.RenderManager;
import net.minecraft.src.RenderPlayer;
import net.minecraft.src.Timer;
import net.minecraft.src.World;

public class CapeHandler {
	
	public static final String ironCapeURL = "https://dl.dropbox.com/u/34938401/Update%20Manager/capeModder.png";
	public static final String goldCapeURL = "https://dl.dropbox.com/u/34938401/Update%20Manager/capeHelper.png";
	public static final String diamondCapeURL = "https://dl.dropbox.com/u/34938401/Update%20Manager/capeCreator.png";

	public static List<String> ironPlayers = new LinkedList();
	public static List<String> goldPlayers = new LinkedList();
	
	private static Thread linkedThread;
	
	public static void initCapes(){
		loadCapes();
		
		if(linkedThread == null)
		linkedThread = new CapeUpdateThread();
	}
	
	public static void loadCapes(){
		ironPlayers.clear();
		fillModders();
		goldPlayers.clear();
		fillHelpers();
		System.out.println("");
	}
	
	static void fillModders(){
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new URL("http://dl.dropbox.com/u/34938401/Update%20Manager/Capes%20for%20Modders.txt").openStream()));
			String line;
			while ((line = reader.readLine()) != null) 
				ironPlayers.add(line.toLowerCase());
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	static void fillHelpers(){
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new URL("http://dl.dropbox.com/u/34938401/Update%20Manager/Capes%20for%20Contributers.txt").openStream()));
			String line;
			while ((line = reader.readLine()) != null) 
				goldPlayers.add(line.toLowerCase());
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void updateCapes(World world){
		List<EntityPlayer> players = world.playerEntities;
		for(EntityPlayer p : players){
			if(ironPlayers.contains(p.username.toLowerCase())){
				p.cloakUrl = ironCapeURL;
				p.playerCloakUrl = ironCapeURL;
			}
			else if(goldPlayers.contains(p.username.toLowerCase())){
				p.cloakUrl = goldCapeURL;
				p.playerCloakUrl = goldCapeURL;
			}
			else if(p.username.equalsIgnoreCase("vazkii")){
				p.cloakUrl = diamondCapeURL;
				p.playerCloakUrl = diamondCapeURL;
			}
		}
	}
}
