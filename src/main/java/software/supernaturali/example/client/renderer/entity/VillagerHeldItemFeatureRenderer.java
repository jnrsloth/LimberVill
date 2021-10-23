package software.supernaturali.example.client.renderer.entity;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.render.model.json.ModelTransformation.Mode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3f;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;
import software.bernie.geckolib3.util.RenderUtils;
import software.supernaturali.example.LimberVillMod;

public class VillagerHeldItemFeatureRenderer <T extends LivingEntity & IAnimatable, M extends AnimatedGeoModel<T>> extends GeoLayerRenderer<T> {

    private static final Identifier VILLAGER_MODEL = new Identifier(LimberVillMod.ModID, "geo/villager.geo.json");
    private ItemStack mainHand;

    public VillagerHeldItemFeatureRenderer(IGeoRenderer<T> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(MatrixStack matrix, VertexConsumerProvider renderBuffer, int packedLight, T entity,
                       float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        this.mainHand = entity.getEquippedStack(EquipmentSlot.MAINHAND);
        GeoModel model = this.getEntityModel().getModel(VILLAGER_MODEL);
        //Start a manual renderRecursively.
        for (GeoBone group : model.topLevelBones) {
            renderRecursively(group, matrix, renderBuffer, packedLight, GeoEntityRenderer.getPackedOverlay(entity, 0));
        }
    }

    //Copied from IGeoRenderer but removed the actual rendering of cubes and only render items to prevent overriding
    //the original renderRecursively and affecting other layers that might need different RenderTypes.
    //While it might seem like a niche use, it is quite important for example with entities that have both glowing parts
    //and hold/equip items/armour like endermen. //Taken verbatim from McpeCommander, this is his fix for the issue. Thanks!
    private void renderRecursively(GeoBone bone, MatrixStack stack, VertexConsumerProvider renderBuffer, int packedLightIn, int packedOverlayIn) {
        stack.push();
        RenderUtils.translate(bone, stack);
        RenderUtils.moveToPivot(bone, stack);
        RenderUtils.rotate(bone, stack);
        RenderUtils.scale(bone, stack);
        RenderUtils.moveBackFromPivot(bone, stack);
        if (bone.getName().equals("handRight")) {
            stack.push();
            //You'll need to play around with these to get item to render in the correct orientation
            stack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(180));
            stack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(0));
            stack.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(85));
            //You'll need to play around with this to render the item in the correct spot.
            stack.translate(-0.4D, -0.5D, 0.08D);//(-0.4D, -0.5D, 0.2D)
            //Sets the scaling of the item.
            stack.scale(0.9f, 0.9f, 0.9f);//(0.9f, 0.9f, 0.9f)
            // Change mainHand to predefined Itemstack and TransformType to what transform you would want to use.
            MinecraftClient.getInstance().getItemRenderer().renderItem(mainHand, ModelTransformation.Mode.THIRD_PERSON_RIGHT_HAND,
                    packedLightIn, packedOverlayIn, stack, renderBuffer, 0);
            //Two pops to empty out the MatrixStack.
            stack.pop();
            stack.pop();
            //Stops unnecessary further recursive method calling. Only works if I am rendering one thing per layer.
            return;
        }
        //Continues the renderRecursively with the child bone, has to be done manually as this is not an overridden
        // method and so can't rely on super methods.
        if (!bone.isHidden) {
            for (GeoBone childBone : bone.childBones) {
                renderRecursively(childBone, stack, renderBuffer, packedLightIn, packedOverlayIn);
            }
        }
        stack.pop();
    }
}
