package TFC.Render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderChicken;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import TFC.Reference;
import TFC.API.Entities.IAnimal;
import TFC.API.Entities.IAnimal.GenderEnum;
public class RenderChickenTFC extends RenderChicken
{
	private static final ResourceLocation ChickenTexture = new ResourceLocation("textures/entity/chicken.png");
	private static final ResourceLocation RoosterTexture = new ResourceLocation(Reference.ModID, "mob/rooster.png");
	private static final ResourceLocation ChickTexture = new ResourceLocation(Reference.ModID, "mob/chick.png");

	public RenderChickenTFC(ModelBase par1ModelBase, float par2)
	{
		super(par1ModelBase, par2);
	}

	protected ResourceLocation getTexture(IAnimal entity)
	{
		if(entity.isAdult()){
			return ChickTexture;
		}
		else if(entity.getGender() == GenderEnum.MALE){
			return RoosterTexture;
		}
		else{
			return ChickenTexture;
		}
	}

	@Override
	protected ResourceLocation func_110775_a(Entity entity) {
		// TODO Auto-generated method stub
		return getTexture((IAnimal)entity);
	}
}
