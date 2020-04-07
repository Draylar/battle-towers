package com.github.draylar.battletowers;

import com.github.draylar.battletowers.common.Blocks;
import com.github.draylar.battletowers.common.Entities;
import com.github.draylar.battletowers.common.Items;
import com.github.draylar.battletowers.common.Structures;
import com.github.draylar.battletowers.config.BattleTowersConfig;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;

public class BattleTowers implements ModInitializer {
    public static BattleTowersConfig CONFIG;

    @Override
    public void onInitialize() {

        CONFIG = AutoConfig.register(BattleTowersConfig.class, GsonConfigSerializer::new).getConfig();

        Blocks.init();
        Items.init();
        Entities.init();
        Structures.init();
    }
}
