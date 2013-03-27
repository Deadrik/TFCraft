package TFC.Core.Player;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import TFC.TFCBlocks;
import TFC.Chunkdata.ChunkData;
import TFC.Chunkdata.ChunkDataManager;
import TFC.Core.TFC_Climate;
import TFC.Core.TFC_Time;
import TFC.Entities.EntityArrowTFC;
import TFC.Entities.EntityTerraJavelin;
import TFC.Food.FoodStatsTFC;
import TFC.Handlers.PacketHandler;
import TFC.TileEntities.TileEntityFireEntity;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.crash.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.packet.*;
import net.minecraft.pathfinding.*;
import net.minecraft.potion.*;
import net.minecraft.server.*;
import net.minecraft.stats.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.village.*;
import net.minecraft.world.*;
import net.minecraft.world.biome.*;
import net.minecraft.world.chunk.*;
import net.minecraft.world.gen.feature.*;
import net.minecraftforge.common.ForgeHooks;
import net.minecraft.src.*;

public class TFC_PlayerServer extends ServerPlayerBase
{
	//Last Visited Chunk
	public int lastChunkX;
	public int lastChunkY;
	public int lastChunkZ;
	public double bodyTemp;
	
	//Last time the spawn protection was updated
	private long spawnProtectionTimer = -1;
	
	private FoodStatsTFC foodstats;

	public TFC_PlayerServer(ServerPlayerAPI var1) {
		super(var1);
		foodstats = new FoodStatsTFC();
		bodyTemp = 37;
	}
	
	@Override
	public boolean attackEntityFrom(DamageSource source, int dam)
    {
        if (this.player.getInitialInvulnerabilityField() > 0)
        {
            return false;
        }
        else
        {
        	int damage = dam;
            if (!this.player.mcServer.isPVPEnabled() && source instanceof EntityDamageSource)
            {
                Entity var3 = source.getEntity();

                if (var3 instanceof EntityPlayer)
                {
                    return false;
                }

                if (var3 instanceof EntityArrowTFC)
                {
                	EntityArrowTFC var4 = (EntityArrowTFC)var3;

                    if (var4.shootingEntity instanceof EntityPlayer)
                    {
                        return false;
                    }
                }
                else if (var3 instanceof EntityTerraJavelin)
                {
                	EntityTerraJavelin var4 = (EntityTerraJavelin)var3;

                    if (var4.shootingEntity instanceof EntityPlayer)
                    {
                        return false;
                    }
                }
            }
            return super.attackEntityFrom(source, damage);
        }
    }

	@Override
	public void beforeMoveEntity(double x, double y, double z) 
	{
		if(!this.player.worldObj.isRemote)
		{
			lastChunkX = (int)player.posX >> 4;
			lastChunkY = (int)player.posY >> 4;
			lastChunkZ = (int)player.posZ >> 4;
		}
	}

	@Override
	public void afterMoveEntity(double x, double y, double z) 
	{
		if(!this.player.worldObj.isRemote)
		{
			int currentChunkX = (int)player.posX >> 4;
			int currentChunkY = (int)player.posY >> 4;
			int currentChunkZ = (int)player.posZ >> 4;

			if(currentChunkX != lastChunkX || currentChunkZ != lastChunkZ)
			{
				ChunkDataManager.setLastVisted(lastChunkX, lastChunkZ);
				//Reset the timer since we've entered a new chunkww
				spawnProtectionTimer = TFC_Time.getTotalTicks() + TFC_Time.hourLength;

			}
		}
	}
	
	@Override
	public void afterLocalConstructing(MinecraftServer var1, World var2, String var3, ItemInWorldManager var4) 
	{
		/*This exists to make sure that if the player spawns into the world with default health, that
		 * he will instead get the new TFC default health. An issue can occur where the player can force themselves to full
		 * health if they manage to get exactly 20 health and they relog. There is not currently a lifetime clock to compare against. 
		*/
		if(this.player.getHealth() == 20 && this.player.ticksExisted == 0)
			this.player.setHealthField(this.getStartingMaxHealth());
	}
	
	@Override
	public void beforeOnLivingUpdate() 
	{
		if((float)foodstats.waterLevel / (float)foodstats.getMaxWater(this.player) <= 0.25f && player.worldObj.difficultySetting >= 1)
		{
			player.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id,1,1));
		}
		else if((float)foodstats.waterLevel / (float)foodstats.getMaxWater(this.player) <= 0.5f)
		{
			if(this.player.isSprinting())
			{
				this.player.setSprinting(false);
			}
		}
		TileEntityFireEntity te = null;
		for (int i = -10;i<10;i++){
			for(int j = -2; j < 3;j++){
				for(int k = -10;k<10;k++){
					if(player.worldObj.getBlockId((int)player.posX+i,(int)player.posY+j,(int)player.posZ+k)==TFCBlocks.Firepit.blockID){
						te = (TileEntityFireEntity)player.worldObj.getBlockTileEntity((int)player.posX+i, (int)player.posY+j, (int)player.posZ+k);
					}
				}
			}
		}
		double netBodyTemp = 0;
		double distanceTE = 0;
		if (te!=null)distanceTE = Math.sqrt(Math.pow(player.posX-te.xCoord,2)+Math.pow(player.posY-te.yCoord,2)+Math.pow(player.posZ-te.zCoord,2));
		float temp =TFC_Climate.getHeightAdjustedTemp((int)player.posX, (int)player.posY, (int)player.posZ);
		if(temp<25)netBodyTemp-= (12*(te!=null&&te.fireTemperature>100?Math.pow(1d/(11-distanceTE),3):1))/(60*20*10*(Math.pow(5, (double)temp/10)));
		else if(temp>=30)netBodyTemp+=((temp+7)/bodyTemp)/(60*3*20);
		netBodyTemp+=0.000017889*(player.isSprinting()?12:1)*(player.inventory.armorInventory[3]!=null &&player.inventory.armorInventory[3].getItem() ==Item.helmetLeather?2.24:1)*(player.inventory.armorInventory[2]!=null &&player.inventory.armorInventory[2].getItem() ==Item.plateLeather?2.24:1)
				*(player.inventory.armorInventory[1]!=null &&player.inventory.armorInventory[1].getItem() ==Item.legsLeather?2.24:1)*(player.inventory.armorInventory[0]!=null &&player.inventory.armorInventory[0].getItem() ==Item.bootsLeather?2.24:1);
		bodyTemp+=netBodyTemp;
		if(temp<25)bodyTemp=Math.max(temp, bodyTemp);
	}

	@Override
	public void afterOnLivingUpdate() 
	{
		this.player.getFoodStats().addStats(10, 0);
		if(!this.player.worldObj.isRemote)
		{
			if(spawnProtectionTimer == -1)
				spawnProtectionTimer = TFC_Time.getTotalTicks() + TFC_Time.hourLength;
			
			if(spawnProtectionTimer < TFC_Time.getTotalTicks())
			{
				//Add protection time to the chunks
				for(int i = -1; i < 2; i++)
				{
					for(int k = -1; k < 2; k++)
					{
						ChunkDataManager.addProtection(lastChunkX + i, lastChunkZ + k, 6);
					}
				}
				
				spawnProtectionTimer += TFC_Time.hourLength;
			}
			
			this.foodstats.onUpdate(player);
			this.player.playerNetServerHandler.sendPacketToPlayer(getStatusPacket());
		}
	}
	
	public boolean shouldHeal()
    {
		int health = player.getHealth();
        return health > 0 && health < getMaxHealth();
    }
	
	@Override
	public void heal(int var1)
    {
		int health = this.player.getHealthField();
		if (health > 0)
        {
            this.player.setHealthField(health + var1);

            if (this.player.getHealthField() > this.getMaxHealth())
            {
            	this.player.setHealthField(getMaxHealth());
            }

            this.player.hurtResistantTime = this.player.maxHurtResistantTime / 2;
        }
    }
	
	public int getMaxHealth()
    {
        return 1000+(this.player.experienceLevel*25);
    }
	
	public static int getStartingMaxHealth()
    {
        return 1000;
    }
	
	public FoodStatsTFC getFoodStatsTFC()
	{
		return this.foodstats;
	}
	
	public EntityPlayerMP getPlayer()
	{
		return this.player;
	}
	
	public static TFC_PlayerServer getFromEntityPlayer(EntityPlayer p)
	{
		EntityPlayerMP pmp = (EntityPlayerMP) p;
		return (TFC_PlayerServer) pmp.getServerPlayerBase("TFC Player Server");
	}
	
	@Override
	public void fall(float fallDistance)
    {
		fallDistance = ForgeHooks.onLivingFall(this.player, fallDistance);
        if (fallDistance <= 0)
        {
            return;
        }

        int var2 = MathHelper.ceiling_float_int(fallDistance - 3.0F);

        //Play the falling sounds
        if (var2 > 0)
        {
            if (var2 > 4)
            {
                this.player.worldObj.playSoundAtEntity(this.player, "damage.fallbig", 1.0F, 1.0F);
            }
            else
            {
            	this.player.worldObj.playSoundAtEntity(this.player, "damage.fallsmall", 1.0F, 1.0F);
            }

            this.attackEntityFrom(DamageSource.fall, var2);
            int var3 = this.player.worldObj.getBlockId(MathHelper.floor_double(this.player.posX), MathHelper.floor_double(this.player.posY - 0.20000000298023224D - (double)this.player.yOffset), MathHelper.floor_double(this.player.posZ));

            if (var3 > 0)
            {
                StepSound var4 = Block.blocksList[var3].stepSound;
                this.player.worldObj.playSoundAtEntity(this.player, var4.getStepSound(), var4.getVolume() * 0.5F, var4.getPitch() * 0.75F);
            }
        }
        
        //Deal the damage and apply status effects
        
    }
	
	@Override
	public void beforeReadEntityFromNBT(NBTTagCompound var1) 
	{
		if (!var1.hasKey("Health"))
        {
			this.player.setHealthField(getStartingMaxHealth());
        }
		this.foodstats.readNBT(var1);
	}
	
	@Override
	public void beforeWriteEntityToNBT(NBTTagCompound var1) 
	{
		this.foodstats.writeNBT(var1);
	}
	
	private Packet getStatusPacket()
	{
		ByteArrayOutputStream bos=new ByteArrayOutputStream(140);
		DataOutputStream dos=new DataOutputStream(bos);
		Packet250CustomPayload pkt=new Packet250CustomPayload();
		try 
		{
			//The packet type sent determines who is expected to process this packet, the client or the server.
			dos.writeByte(PacketHandler.Packet_Player_Status);
			dos.writeFloat(this.foodstats.foodLevel);
			dos.writeFloat(this.foodstats.waterLevel);
			
			pkt.channel="TerraFirmaCraft";
			pkt.data = bos.toByteArray();
			pkt.length= pkt.data.length;
			pkt.isChunkDataPacket=false;
		} 
		catch (IOException e) 
		{

		}
		return pkt;
	}
}
