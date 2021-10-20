package software.supernaturali.example;

import net.fabricmc.api.ModInitializer;
import software.bernie.geckolib3.GeckoLib;

public class LimberVillMod implements ModInitializer {
    public static final String ModID = "limbervill";

	@Override
	public void onInitialize() {
		GeckoLib.initialize();
	}
}