package software.supernaturali.example.client.renderer.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory.Context;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.village.VillagerDataContainer;
import net.minecraft.village.VillagerProfession;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;
import software.supernaturali.example.client.model.entity.ReplacedVillagerModel;

@Environment(EnvType.CLIENT)
public class VillagerEntityRenderer<T extends MerchantEntity & IAnimatable & VillagerDataContainer> extends GeoEntityRenderer<T> {

    private String profession;

    public VillagerEntityRenderer(Context ctx) {
        super(ctx, new ReplacedVillagerModel<>());
//        this.addFeature(new HeadFeatureRenderer(this, ctx.getModelLoader()));
        this.addLayer(new VillagerClothingFeatureRenderer<>(this, "villager"));
        this.addLayer(new VillagerHeldItemFeatureRenderer<>(this));
    }

    @Override
    public void renderEarly(T entity, MatrixStack stackIn, float ticks, VertexConsumerProvider renderTypeBuffer, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float partialTicks) {
        //store the Render Type Buffer and current texture, we'll need them later.
        super.renderEarly(entity, stackIn, ticks, renderTypeBuffer, bufferIn, packedLightIn, packedOverlayIn, red,
                green, blue, partialTicks);
        this.profession = entity.getVillagerData().getProfession().getId();
    }

    @Override
    public void renderRecursively(GeoBone bone, MatrixStack stack, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        System.out.println(profession);
        if (profession.equals("weaponsmith") && bone.getName().equals("leftEye")) {
            bone.setHidden(true);
        }
        super.renderRecursively(bone, stack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }

    @Override
    public void render(T entity, float entityYaw, float partialTicks, MatrixStack stack,
                 VertexConsumerProvider bufferIn, int packedLightIn){
        this.scale(entity, stack, 0);
        super.render(entity, entityYaw, partialTicks, stack, bufferIn, packedLightIn);

    }

    //TODO check what baby villagers look like, does this need to be used? if so where?
    protected void scale(T villagerEntity, MatrixStack matrixStack, float f) {
        float g = 0.9375F;
        if (villagerEntity.isBaby()) {
            g = g * 0.5F;
            this.shadowRadius = 0.4F;
        } else {
            this.shadowRadius = 0.5F;
        }
        matrixStack.scale(g, g, g);
    }
}
