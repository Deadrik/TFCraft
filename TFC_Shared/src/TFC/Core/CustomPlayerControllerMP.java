package TFC.Core;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.NetClientHandler;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import TFC.API.Util.Helper;

public class CustomPlayerControllerMP extends PlayerControllerMP {

	public CustomPlayerControllerMP(Minecraft par1Minecraft,
			NetClientHandler par2NetClientHandler) {
		super(par1Minecraft, par2NetClientHandler);
	}

	@Override
	/**
     * player reach distance = 4F
     */
    public float getBlockReachDistance()
    {
        float result = super.getBlockReachDistance();
        result *= Helper.getReachDistance(Minecraft.getMinecraft().theWorld, Minecraft.getMinecraft().thePlayer, true);
        return result;
    }
}
