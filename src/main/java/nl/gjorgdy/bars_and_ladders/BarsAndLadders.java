package nl.gjorgdy.bars_and_ladders;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.loader.api.FabricLoader;
import nl.gjorgdy.bars_and_ladders.listeners.PlayerBlockBreakListener;
import nl.gjorgdy.bars_and_ladders.listeners.UseBlockCallbackListener;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BarsAndLadders implements ModInitializer {

	public static final String MOD_NAME = "Bars and Ladders";
	public static final String MOD_ID = "bars_and_ladders";
	public static final Logger LOGGER = LogManager.getLogger(MOD_NAME);

	public static double targetSpeed = -0.75;
	public static double dragModifier = 0.85;

	@Override
	public void onInitialize() {
		UseBlockCallback.EVENT.register(new UseBlockCallbackListener());
		PlayerBlockBreakEvents.AFTER.register(new PlayerBlockBreakListener());

		if (FabricLoader.getInstance().isModLoaded("fzzy_config")) {
			FzzyConfig.load();
		} else {
			LOGGER.log(Level.INFO, "Fzzy Config not found, using default settings.");
		}
	}
}
