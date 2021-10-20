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
    private VertexConsumerProvider rtb;
    private Identifier whTexture;
    private ItemStack itemStack;

    public VillagerEntityRenderer(Context ctx) {
        super(ctx, new ReplacedVillagerModel<>());
//        this.addFeature(new HeadFeatureRenderer(this, ctx.getModelLoader()));
        this.addLayer(new VillagerClothingFeatureRenderer<>(this, "villager"));
//        this.addLayer(new VillagerHeldItemFeatureRenderer(this)); //replaced by renderRecursively in this class.
    }

    @Override
    public void renderEarly(T entity, MatrixStack stackIn, float ticks, VertexConsumerProvider renderTypeBuffer, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float partialTicks) {
        //store the Render Type Buffer and current texture, we'll need them later.
        this.rtb = renderTypeBuffer;
        this.whTexture = this.getTextureLocation(entity);
        this.itemStack = entity.getEquippedStack(EquipmentSlot.MAINHAND);
        super.renderEarly(entity, stackIn, ticks, renderTypeBuffer, bufferIn, packedLightIn, packedOverlayIn, red,
                          green, blue, partialTicks);
    }

    @Override
    //This will help render any items in the villagers' hands which will appear when they want to trade.
    public void renderRecursively(GeoBone bone, MatrixStack stack, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        if (bone.getName().equals("handRight")) { // handRight is the name of the bone you to set the item to attach too. Please see Note
            stack.push(); //FIXME not sure of the purpose of this and stack.pop, seems to work fine without it.
            //You'll need to play around with these to get item to render in the correct orientation
            stack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(180));
            stack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(0));
            stack.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(85));
            //You'll need to play around with this to render the item in the correct spot.
            stack.translate(-0.525D, -0.5D, 0.11D);//(-0.4D, -0.5D, 0.2D)
            //Sets the scaling of the item.
            stack.scale(0.9f, 0.9f, 0.9f);//(0.9f, 0.9f, 0.9f)
            // Change mainHand to predefined Itemstack and Mode to what transform you would want to use.
            MinecraftClient.getInstance().getItemRenderer().renderItem(this.itemStack, Mode.GROUND, packedLightIn, packedOverlayIn, stack, this.rtb, 0);
            stack.pop();
            bufferIn = rtb.getBuffer(RenderLayer.getEntityTranslucent(whTexture));
        }
        super.renderRecursively(bone, stack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
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
