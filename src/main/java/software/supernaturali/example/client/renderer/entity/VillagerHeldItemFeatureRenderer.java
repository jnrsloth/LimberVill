package software.supernaturali.example.client.renderer.entity;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.model.json.ModelTransformation.Mode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3f;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

//THIS CLASS IS NO LONGER IN USE
public class VillagerHeldItemFeatureRenderer <T extends LivingEntity & IAnimatable, M extends AnimatedGeoModel<T>> extends GeoLayerRenderer<T> {

    final IGeoRenderer ctx;

    public VillagerHeldItemFeatureRenderer(IGeoRenderer<T> context) {
        super(context);
        this.ctx = context;
    }

    public void render(MatrixStack stack, VertexConsumerProvider vertexConsumerProvider, int packedLightIn, T livingEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        stack.push();
        stack.translate(0.0D, 1.2D, -0.4000000059604645D); //-0.4000000059604645D
        stack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(0));
        stack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(0));
        stack.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(0));
        stack.scale(0.7F, 0.7F, 0.7F);
        ItemStack itemStack = livingEntity.getEquippedStack(EquipmentSlot.MAINHAND);
        MinecraftClient.getInstance().getHeldItemRenderer().renderItem(livingEntity, itemStack, Mode.THIRD_PERSON_RIGHT_HAND, false, stack, vertexConsumerProvider, packedLightIn);
        stack.pop();
    }
}