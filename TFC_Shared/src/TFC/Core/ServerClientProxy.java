package TFC.Core;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.ModLoader;
import net.minecraft.src.forge.MinecraftForge;

public enum ServerClientProxy {
	CLIENT("ClientProxy"),
	SERVER("ServerProxy");

	public static IProxy getProxy() 
	{
		if (TFC_Core.isClient()) 
		{
			return CLIENT.buildProxy();
		} 
		else 
		{
			return SERVER.buildProxy();
		}
	}
	private String className;

	private ServerClientProxy(String proxyClassName) {
		className=proxyClassName;
	}
	private IProxy buildProxy() {
		try 
		{
			return (IProxy) Class.forName(className).newInstance();
		} 
		catch (Exception e) 
		{
		    try
            {
                return (IProxy) Class.forName("net.minecraft.src." + className).newInstance();
            } catch (Exception e1)
            {
                e1.printStackTrace();
                throw new RuntimeException(e);
            }
		}
	}
}
