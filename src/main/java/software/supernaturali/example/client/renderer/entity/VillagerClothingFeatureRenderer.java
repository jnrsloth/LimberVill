package software.supernaturali.example.client.renderer.entity;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.village.VillagerData;
import net.minecraft.village.VillagerDataContainer;
import net.minecraft.village.VillagerProfession;
import net.minecraft.village.VillagerType;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.model.provider.GeoModelProvider;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;
import software.supernaturali.example.LimberVillMod;

//public class VillagerClothingFeatureRenderer<T extends LivingEntity & VillagerDataContainer & IAnimatable, M extends AnimatedGeoModel<T> & ModelWithHat> extends GeoLayerRenderer<T> {
public class VillagerClothingFeatureRenderer<T extends MerchantEntity & VillagerDataContainer & IAnimatable> extends GeoLayerRenderer<T> {
    private static final Int2ObjectMap<Identifier> LEVEL_TO_ID = Util.make(new Int2ObjectOpenHashMap<>(), (levelToId) -> {
        levelToId.put(1, new Identifier("stone"));
        levelToId.put(2, new Identifier("iron"));
        levelToId.put(3, new Identifier("gold"));
        levelToId.put(4, new Identifier("emerald"));
        levelToId.put(5, new Identifier("diamond"));
    });
    private final String entityType;

//    public VillagerClothingFeatureRenderer(IGeoRenderer<T> context, ResourceManager resourceManager, String entityType) {
    public VillagerClothingFeatureRenderer(IGeoRenderer<T> context, String entityType) {
        super(context);
        // private final Object2ObjectMap<VillagerType, HatType> villagerTypeToHat = new Object2ObjectOpenHashMap();
        // private final Object2ObjectMap<VillagerProfession, HatType> professionToHat = new Object2ObjectOpenHashMap();
        this.entityType = entityType;
    }

    public void render(MatrixStack matrixStackIn, VertexConsumerProvider bufferIn, int packedLightIn, T livingEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!livingEntity.isInvisible()) {
            VillagerData villagerData = livingEntity.getVillagerData();
            VillagerType villagerType = villagerData.getType();
            VillagerProfession villagerProfession = villagerData.getProfession();
//            HatType hatType = this.getHatType(this.villagerTypeToHat, "type", Registry.VILLAGER_TYPE, villagerType);
//            HatType hatType2 = this.getHatType(this.professionToHat, "profession", Registry.VILLAGER_PROFESSION, villagerProfession);
            GeoModelProvider<T> entityModel = (GeoModelProvider<T>) this.getEntityModel();
//            entityModel.setHatVisible(hatType2 == HatType.NONE || hatType2 == HatType.PARTIAL && hatType != HatType.FULL);
            Identifier identifier = this.findTexture("type", Registry.VILLAGER_TYPE.getId(villagerType));
            renderModel(entityModel, identifier, matrixStackIn, bufferIn, packedLightIn, livingEntity, 1.0F, 1.0F, 1.0F, 1.0F);
//            entityModel.setHatVisible(true);
            if (villagerProfession != VillagerProfession.NONE && !livingEntity.isBaby()) {
                Identifier identifier2 = this.findTexture("profession", Registry.VILLAGER_PROFESSION.getId(villagerProfession));
                renderModel(entityModel, identifier2, matrixStackIn, bufferIn, packedLightIn, livingEntity, 1.0F, 1.0F, 1.0F, 1.0F);
                if (villagerProfession != VillagerProfession.NITWIT) {
                    Identifier identifier3 = this.findTexture("profession_level", LEVEL_TO_ID.get(MathHelper.clamp(villagerData.getLevel(), 1, LEVEL_TO_ID.size())));
                    renderModel(entityModel, identifier3, matrixStackIn, bufferIn, packedLightIn, livingEntity, 1.0F, 1.0F, 1.0F, 1.0F);
                }
            }

        }
    }
    private Identifier findTexture(String keyType, Identifier keyId) {
        return new Identifier(LimberVillMod.ModID, "textures/model/entity/" + this.entityType + "/" + keyType + "/" + keyId.getPath() + ".png");
    }

      //TODO what is a villager hat?? aren't they always wearing their hats?
//    public <K> HatType getHatType(Object2ObjectMap<K, HatType> hatLookUp, String keyType, DefaultedRegistry<K> registry, K key) {
//        return hatLookUp.computeIfAbsent(key, (k) -> {
//            try {
//                Resource resource = this.resourceManager.getResource(this.findTexture(keyType, registry.getId(key)));
//
//                HatType var7;
//                label50: {
//                    try {
//                        VillagerResourceMetadata villagerResourceMetadata = resource.getMetadata(VillagerResourceMetadata.READER);
//                        if (villagerResourceMetadata != null) {
//                            var7 = villagerResourceMetadata.getHatType();
//                            break label50;
//                        }
//                    } catch (Throwable var9) {
//                        if (resource != null) {
//                            try {
//                                resource.close();
//                            } catch (Throwable var8) {
//                                var9.addSuppressed(var8);
//                            }
//                        }
//
//                        throw var9;
//                    }
//
//                    if (resource != null) {
//                        resource.close();
//                    }
//
//                    return HatType.NONE;
//                }
//
//                if (resource != null) {
//                    resource.close();
//                }
//
//                return var7;
//            } catch (IOException var10) {
//                return HatType.NONE;
//            }
//        });
//    }


}
