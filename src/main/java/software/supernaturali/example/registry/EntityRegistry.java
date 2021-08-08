/*
 * Copyright (c) 2020.
 * Author: Bernie G. (Gecko)
 */

package software.supernaturali.example.registry;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import software.supernaturali.example.LimberVillMod;
import software.supernaturali.example.entity.BikeEntity;
import software.supernaturali.example.entity.GeoExampleEntity;
import software.bernie.geckolib3.GeckoLib;

public class EntityRegistry {
	public static final EntityType<GeoExampleEntity> GEO_EXAMPLE_ENTITY = buildEntity(GeoExampleEntity::new,
			GeoExampleEntity.class, .7F, 1.3F, SpawnGroup.CREATURE);
	public static final EntityType<BikeEntity> BIKE_ENTITY = buildEntity(BikeEntity::new, BikeEntity.class, 0.5f, 0.6F,
			SpawnGroup.CREATURE);

	public static <T extends Entity> EntityType<T> buildEntity(EntityType.EntityFactory<T> entity, Class<T> entityClass,
			float width, float height, SpawnGroup group) {
		if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
			String name = entityClass.getSimpleName().toLowerCase();
			return EntityRegistryBuilder.<T>createBuilder(new Identifier(LimberVillMod.ModID, name)).entity(entity)
					.category(group).dimensions(EntityDimensions.changing(width, height)).build();
		}
		return null;
	}

}
