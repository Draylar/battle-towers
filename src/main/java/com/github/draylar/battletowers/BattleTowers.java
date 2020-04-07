package com.github.draylar.battletowers;

import com.github.draylar.battletowers.registry.Blocks;
import com.github.draylar.battletowers.registry.Entities;
import com.github.draylar.battletowers.registry.Items;
import com.github.draylar.battletowers.registry.Structures;
import com.github.draylar.battletowers.config.BattleTowersConfig;
import me.sargunvohra.mcmods.autoconfig1.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;

public class BattleTowers implements ModInitializer {

    public static BattleTowersConfig CONFIG = AutoConfig.register(BattleTowersConfig.class, GsonConfigSerializer::new).getConfig();;

    @Override
    public void onInitialize() {
        Blocks.init();
        Items.init();
        Entities.init();
        Structures.init();
    }
}
