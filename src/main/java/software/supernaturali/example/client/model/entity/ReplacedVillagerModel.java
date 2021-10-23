package software.supernaturali.example.client.model.entity;

import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;
import software.supernaturali.example.LimberVillMod;

public class ReplacedVillagerModel<V extends MerchantEntity & IAnimatable> extends AnimatedGeoModel<V> {
	@Override
	public Identifier getModelLocation(V object) {
		return new Identifier(LimberVillMod.ModID, "geo/villager.geo.json");
	}

	@Override
	public Identifier getTextureLocation(V object) {
		return new Identifier(LimberVillMod.ModID, "textures/model/entity/villager/villager.png");
	}

	@Override
	public Identifier getAnimationFileLocation(V animatable) {
		return new Identifier(LimberVillMod.ModID, "animations/villager.animation.json");
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public void setLivingAnimations(V entity, Integer uniqueID, AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);
		IBone head = this.getAnimationProcessor().getBone("head");
		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		if (head != null) {
			head.setRotationX(extraData.headPitch * ((float) Math.PI / 180F));
			head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
		}
	}
}