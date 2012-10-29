package TFC.Core.Player;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import TFC.Chunkdata.ChunkData;
import TFC.Chunkdata.ChunkDataManager;
import TFC.Core.TFC_Time;
import TFC.Food.FoodStatsTFC;
import TFC.Handlers.PacketHandler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.Block;
import net.minecraft.src.DamageSource;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityArrow;
import net.minecraft.src.EntityDamageSource;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.FoodStats;
import net.minecraft.src.ItemInWorldManager;
import net.minecraft.src.MathHelper;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.Packet;
import net.minecraft.src.Packet250CustomPayload;
import net.minecraft.src.Packet8UpdateHealth;
import net.minecraft.src.Potion;
import net.minecraft.src.PotionEffect;
import net.minecraft.src.ServerPlayerAPI;
import net.minecraft.src.ServerPlayerBase;
import net.minecraft.src.StepSound;
import net.minecraft.src.World;
import net.minecraftforge.common.ForgeHooks;

public class TFC_PlayerServer extends ServerPlayerBase
{
	//Last Visited Chunk
	public int lastChunkX;
	public int lastChunkY;
	public int lastChunkZ;
	
	//Last time the spawn protection was updated
	private long spawnProtectionTimer = -1;
	
	private FoodStatsTFC foodstats;
	private FoodStats oldFood;

	public TFC_PlayerServer(ServerPlayerAPI var1) {
		super(var1);
		foodstats = new FoodStatsTFC();
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

                if (var3 instanceof EntityArrow)
                {
                    EntityArrow var4 = (EntityArrow)var3;

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
				//Reset the timer since we've entered a new chunk
				spawnProtectionTimer = TFC_Time.getTotalTicks() + TFC_Time.hourLength;

			}
		}
	}
	
	@Override
	public void afterLocalConstructing(MinecraftServer var1, World var2, String var3, ItemInWorldManager var4) 
	{
		//this.player.setFoodStatsField(new FoodStatsTFC());
		if(this.player.getHealth() == 20 && this.player.ticksExisted == 0)
			this.player.setHealthField(this.getStartingMaxHealth());
		
		this.player.capabilities = new PlayerCapabilitiesTFC();
	}
	
	@Override
	public void beforeOnLivingUpdate() 
	{
		oldFood = this.player.getFoodStats();

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
	}

	@Override
	public void afterOnLivingUpdate() 
	{
		this.player.setFoodStatsField(oldFood);
		if(!this.player.worldObj.isRemote)
		{
			if(spawnProtectionTimer == -1)
				spawnProtectionTimer = TFC_Time.getTotalTicks() + TFC_Time.hourLength;
			
			if (this.player.worldObj.difficultySetting == 0 && shouldHeal() && this.player.ticksExisted % 20 * 12 == 0)
	        {
	            this.heal((int) ((float)getMaxHealth()*0.01f));
	        }
			
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
			dos.writeInt(this.foodstats.foodLevel);
			dos.writeInt(this.foodstats.waterLevel);
			
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
