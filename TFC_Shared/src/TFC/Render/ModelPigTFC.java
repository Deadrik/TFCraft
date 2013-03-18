package TFC.Render;

public class ModelPigTFC extends ModelQuadrupedTFC
{
    public ModelPigTFC()
    {
        this(0.0F);
    }

    public ModelPigTFC(float par1)
    {
        super(6, par1);
        this.head.setTextureOffset(16, 16).addBox(-2.0F, 0.0F, -9.0F, 4, 3, 1, par1);
        this.field_40331_g = 4.0F;
    }
}
