package TFC.Core;

import java.util.LinkedList;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.ChunkCoordinates;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.EnumGameType;
import net.minecraft.src.ItemInWorldManager;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ServerPlayerAPI;
import net.minecraft.src.StringTranslate;
import net.minecraft.src.World;

public class TFC_PlayerMP extends EntityPlayerMP
{
	public TFC_PlayerMP(MinecraftServer par1MinecraftServer, World par2World,
			String par3Str, ItemInWorldManager par4ItemInWorldManager) {
		super(par1MinecraftServer, par2World, par3Str, par4ItemInWorldManager);
	}
	
	public TFC_PlayerMP(EntityPlayerMP entity) 
	{
		super(entity.mcServer, entity.worldObj, entity.username, entity.theItemInWorldManager);     
		this.playerNetServerHandler = entity.playerNetServerHandler;
	}

	@Override
	public int getMaxHealth()
    {
        return 100;
    }
}
