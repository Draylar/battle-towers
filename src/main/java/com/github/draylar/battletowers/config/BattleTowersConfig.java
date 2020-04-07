package com.github.draylar.battletowers.config;

import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;

@Config(name = "battletowers")
public class BattleTowersConfig implements ConfigData {
    public int requiredKeys = 10;
    public String requiredItem = "battle-towers:boss_key";

    public String bossLootTable = "battle-towers:boss_loot";
    public String blacksmithLootTable = "battle-towers:blacksmith_loot";
    public String entranceLootTable = "battle-towers:base_loot";
    public String jungleLootTable = "battle-towers:jungle_loot";
    public String layerLootTable = "battle-towers:layer_loot";
    public String libraryLootTable = "battle-towers:library_loot";
    public String mineLootTable = "battle-towers:mine_loot";

    public int bossHP = 150;
    public int bossDamageScale = 5;

    public int floorAmount = 10;
    public int floorRandomAddition = 5;
    public int structureRarity = 2500;
}
