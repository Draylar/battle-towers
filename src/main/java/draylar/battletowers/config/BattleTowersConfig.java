package draylar.battletowers.config;


import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;
import me.sargunvohra.mcmods.autoconfig1u.shadowed.blue.endless.jankson.Comment;

@Config(name = "battletowers")
public class BattleTowersConfig implements ConfigData {

    @Comment(value = "Number of \"keys\" required to unlock the Boss Lock at the top of a Battle Tower.")
    public int requiredKeys = 10;

    @Comment(value = "The item required to unlock the Boss Lock at the top of a Battle Tower.")
    public String requiredItem = "battletowers:boss_key";

    @Comment(value = "Starting health (generic.max_health) of spawned Tower Guardian entities.")
    public int bossHP = 250;

    @Comment(value = "Starting attack stat (generic.attack_damage) of spawned Tower Guardian entities.")
    public int bossAttack = 5;

    @Comment(value = "Maximum distance, in chunks, between Battle Tower spawn attempts.")
    public int towerSpacing = 32;

    @Comment(value = "Minimum distance, in chunks, between Battle Tower spawn attempts.")
    public int towerSeparation = 24;
}