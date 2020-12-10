package draylar.battletowers;

import draylar.battletowers.config.BattleTowersConfig;
import draylar.battletowers.group.BattleTowersItemGroup;
import draylar.battletowers.registry.*;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BattleTowers implements ModInitializer {

    public static final String MODID = "battletowers";
    public static final BattleTowersConfig CONFIG = AutoConfig.register(BattleTowersConfig.class, GsonConfigSerializer::new).getConfig();
    public static final ItemGroup GROUP = new BattleTowersItemGroup(BattleTowers.id("group"));
    public static final Logger LOGGER = LogManager.getLogger();;

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
        if(CONFIG.towerSpacing == 0) {
            LOGGER.warn("Tower Spacing config value was set to 0, but it needs to be at least 1. Temporarily replacing value with 32.");
            CONFIG.towerSpacing = 32;
        }

        if(CONFIG.towerSeparation >= CONFIG.towerSpacing) {
            LOGGER.warn(String.format("Tower Separation must be lower than Tower Spacing. Temporarily replacing Tower Separation value with %d (half of configured Tower Spacing).", CONFIG.towerSpacing / 2));
            CONFIG.towerSeparation = CONFIG.towerSeparation / 2;
        }
    }

    public static Identifier id(String name) {
        return new Identifier(MODID, name);
    }
}
