package com.github.draylar.battleTowers;

import com.github.draylar.battleTowers.common.Blocks;
import com.github.draylar.battleTowers.common.Entities;
import com.github.draylar.battleTowers.common.Items;
import com.github.draylar.battleTowers.common.Structures;
import com.github.draylar.battleTowers.config.BattleTowersConfig;
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
