package TFC.Handlers;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetServerHandler;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet3Chat;
import cpw.mods.fml.common.network.IChatListener;

public class ChatListenerTFC implements IChatListener
{

	@Override
	public Packet3Chat serverChat(NetHandler handler, Packet3Chat message) {
		EntityPlayerMP player = ((NetServerHandler)handler).playerEntity;
		long soberTime = player.getEntityData().hasKey("soberTime") ? player.getEntityData().getLong("soberTime"):0;
		if(soberTime > 0){

			String s = "aeiouywrsflzvbnmAEIOUYWRSFLZVBNM";
			Random rand = new Random();
			for(int i = 0; i < message.message.length()-1;i++){
				String start = message.message.substring(0,i);
				String s2 = message.message.substring(i, i+1);
				String end = message.message.substring(i+1);
				if(s.indexOf(s2)!=-1 && rand.nextInt(11-(int)(soberTime/1000))==0){
					int n = rand.nextInt(2);
					int m = 0;
					message.message = start + s2;
					for(int j = 0; j < n; j++){
						message.message = message.message + (end.substring(0, 1).toUpperCase().equals(end.substring(0, 1))?s2:s2.toLowerCase());
					}
					if((s2.equals("S")||s2.equals("s"))&&!end.substring(0, 1).equals("S")&&
							!end.substring(0, 1).equals("s")){
						message.message+=(s2.toUpperCase().equals(s2)?(end.substring(0, 1).toUpperCase().equals(end.substring(0, 1))?"H":"h".toLowerCase()):"h");
						n++;
					}
					message.message = message.message + end;
					i+=n;
				}
			}

		}
		return message;
	}

	@Override
	public Packet3Chat clientChat(NetHandler handler, Packet3Chat message) {
		return message;
	}



}
