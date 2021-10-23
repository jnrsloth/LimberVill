package software.supernaturali.example.mixins;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.InteractionObserver;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.village.VillagerDataContainer;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

//This is where I store the methods needed to extend IAnimatable to make the entity compatible with geckolib
@Mixin(VillagerEntity.class)
public abstract class VillagerEntityMixin extends MerchantEntity
        implements InteractionObserver, VillagerDataContainer, IAnimatable {

    final AnimationFactory factory = new AnimationFactory(this);

    public VillagerEntityMixin(EntityType<? extends VillagerEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 1, this::predicate));
        data.addAnimationController(new AnimationController<>(this, "headController", 1, this::headPredicate));
    }

    private <P extends IAnimatable> PlayState headPredicate(AnimationEvent<P> event) {
        if (this.isSleeping()){
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.model.sleep", true));
        } else if (this.getHeadRollingTimeLeft() > 0) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.model.headShake", false));
        } else if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.model.headWalk", true));
        } else {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.model.headIdle", true));
        }
        return PlayState.CONTINUE;
    }

    @SuppressWarnings("SameReturnValue")
    private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event) {
        if (this.isSleeping()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.model.sleep", true));
        } else if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.model.walk", true));
        } else {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.model.idle", true));
        }
        return PlayState.CONTINUE;
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
}