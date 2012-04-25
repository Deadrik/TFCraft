package net.minecraft.src.TFC_Core;

import net.minecraft.src.ModLoader;
import net.minecraft.src.forge.MinecraftForge;

public enum ServerClientProxy {
	CLIENT("net.minecraft.src.TFC_Core.ClientProxy"),
	SERVER("net.minecraft.src.TFC_Core.ServerProxy");

	public static IProxy getProxy() {
		if (MinecraftForge.isClient()) {
			return CLIENT.buildProxy();
		} else {
			return SERVER.buildProxy();
		}
	}
	private String className;

	private ServerClientProxy(String proxyClassName) {
		className=proxyClassName;
	}
	private IProxy buildProxy() {
		try {
			return (IProxy) Class.forName(className).newInstance();
		} catch (Exception e) {
			ModLoader.getLogger().severe("A fatal error has occured initializing TerraFirmaCraft");
			e.printStackTrace(System.err);
			throw new RuntimeException(e);
		}
	}
}
