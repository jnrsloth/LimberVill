package software.supernaturali.example.client.renderer.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory.Context;
import net.minecraft.client.render.model.json.ModelTransformation.Mode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3f;
import net.minecraft.village.VillagerDataContainer;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;
import software.supernaturali.example.client.model.entity.ReplacedVillagerModel;

@Environment(EnvType.CLIENT)
public class VillagerEntityRenderer<T extends MerchantEntity & IAnimatable & VillagerDataContainer> extends GeoEntityRenderer<T> {

    public VillagerEntityRenderer(Context ctx) {
        super(ctx, new ReplacedVillagerModel<>());
//        this.addFeature(new HeadFeatureRenderer(this, ctx.getModelLoader()));
        this.addLayer(new VillagerClothingFeatureRenderer<>(this, "villager"));
        this.addLayer(new VillagerHeldItemFeatureRenderer<>(this));
    }

    //TODO check what baby villagers look like, does this need to be used? if so where?
    protected void scale(VillagerEntity villagerEntity, MatrixStack matrixStack, float f) {
        float g = 0.9375F;
        if (villagerEntity.isBaby()) {
            g = (float)((double)g * 0.5D);
            this.shadowRadius = 0.25F;
        } else {
            this.shadowRadius = 0.5F;
        }
        matrixStack.scale(g, g, g);
    }
}
