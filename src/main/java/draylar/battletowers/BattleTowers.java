package draylar.battletowers;

import draylar.battletowers.config.BattleTowersConfig;
import draylar.battletowers.registry.*;
import draylar.omegaconfig.OmegaConfig;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.example.GeckoLibMod;

public class BattleTowers implements ModInitializer {

    public static final String MODID = "battletowers";
    public static final BattleTowersConfig CONFIG = OmegaConfig.register(BattleTowersConfig.class);
    public static final ItemGroup GROUP = FabricItemGroupBuilder.build(id("group"), () -> new ItemStack(BattleTowerItems.BOSS_KEY));
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
        GeckoLibMod.DISABLE_IN_DEV = true;
    }
}
