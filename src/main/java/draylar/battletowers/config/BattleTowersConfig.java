package draylar.battletowers.config;


import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;

@Config(name = "battletowers")
public class BattleTowersConfig implements ConfigData {

    public int requiredKeys = 10;
    public String requiredItem = "battletowers:boss_key";

    public String bossLootTable = "battletowers:boss_loot";
    public String blacksmithLootTable = "battletowers:blacksmith_loot";
    public String entranceLootTable = "battletowers:base_loot";
    public String jungleLootTable = "battletowers:jungle_loot";
    public String layerLootTable = "battletowers:layer_loot";
    public String libraryLootTable = "battletowers:library_loot";
    public String mineLootTable = "battletowers:mine_loot";

    public int bossHP = 150;
    public int bossDamageScale = 5;

    public int floorAmount = 10;
    public int floorRandomAddition = 5;
}