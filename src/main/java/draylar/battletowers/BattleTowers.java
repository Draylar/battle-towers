package draylar.battletowers;

import draylar.battletowers.config.BattleTowersConfig;
import draylar.battletowers.group.BattleTowersItemGroup;
import draylar.battletowers.registry.*;
import draylar.omegaconfig.OmegaConfig;
import net.fabricmc.api.ModInitializer;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BattleTowers implements ModInitializer {

    public static final String MODID = "battletowers";
    public static final BattleTowersConfig CONFIG = OmegaConfig.register(BattleTowersConfig.class);
    public static final ItemGroup GROUP = new BattleTowersItemGroup(BattleTowers.id("group"));
    public static final Logger LOGGER = LogManager.getLogger();

    public static Identifier id(String name) {
        return new Identifier(MODID, name);
    }

    @Override
    public void onInitialize() {
        BattleTowerBlocks.init();
        BattleTowerItems.init();
        BattleTowerEntities.init();
        BattleTowerWorld.init();
        BattleTowerTags.init();
        BattleTowerStatusEffects.init();
        validateConfig();
    }

    public void validateConfig() {
        if (CONFIG.towerSpacing == 0) {
            LOGGER.warn("Tower Spacing config value was set to 0, but it needs to be at least 1. Temporarily replacing value with 32.");
            CONFIG.towerSpacing = 32;
        }

        if (CONFIG.towerSeparation >= CONFIG.towerSpacing) {
            LOGGER.warn(String.format("Tower Separation must be lower than Tower Spacing. Temporarily replacing Tower Separation value with %d (half of configured Tower Spacing).", CONFIG.towerSpacing / 2));
            CONFIG.towerSeparation = CONFIG.towerSeparation / 2;
        }
    }
}
