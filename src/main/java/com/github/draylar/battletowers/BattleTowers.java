package com.github.draylar.battletowers;

import com.github.draylar.battletowers.registry.BattleTowerBlocks;
import com.github.draylar.battletowers.registry.BattleTowerEntities;
import com.github.draylar.battletowers.registry.BattleTowerItems;
import com.github.draylar.battletowers.registry.BattleTowerStructures;
import com.github.draylar.battletowers.config.BattleTowersConfig;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

public class BattleTowers implements ModInitializer {

    public static final String MODID = "battletowers";

    public static BattleTowersConfig CONFIG = AutoConfig.register(BattleTowersConfig.class, GsonConfigSerializer::new).getConfig();

    @Override
    public void onInitialize() {
        BattleTowerBlocks.init();
        BattleTowerItems.init();
        BattleTowerEntities.init();
        BattleTowerStructures.init();
    }

    public static Identifier id(String name) {
        return new Identifier(MODID, name);
    }
}
