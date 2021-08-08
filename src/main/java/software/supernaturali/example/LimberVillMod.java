/*
 * Copyright (c) 2020.
 * Author: Bernie G. (Gecko)
 */

package software.supernaturali.example;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.loader.api.FabricLoader;
import software.bernie.example.GeckoLibMod;
import software.bernie.geckolib3.GeckoLib;
import software.supernaturali.example.registry.*;

public class LimberVillMod implements ModInitializer {
	public static boolean DISABLE_IN_DEV = false;
    public static String ModID = "limbervill";
    boolean isDevelopmentEnvironment = FabricLoader.getInstance().isDevelopmentEnvironment();

	@Override
	public void onInitialize() {
		GeckoLib.initialize();
		if (isDevelopmentEnvironment && !GeckoLibMod.DISABLE_IN_DEV) {
			new EntityRegistry();
			FabricDefaultAttributeRegistry.register(EntityRegistry.GEO_EXAMPLE_ENTITY,
					EntityUtils.createGenericEntityAttributes());
			FabricDefaultAttributeRegistry.register(EntityRegistry.BIKE_ENTITY,
					EntityUtils.createGenericEntityAttributes());
		}
	}
}