package draylar.battletowers.config;


import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;
import me.sargunvohra.mcmods.autoconfig1u.shadowed.blue.endless.jankson.Comment;

@Config(name = "battletowers")
public class BattleTowersConfig implements ConfigData {

    public int requiredKeys = 10;
    public String requiredItem = "battletowers:boss_key";

    @Comment(value = "Starting health (generic.max_health) of spawned Tower Guardian entities.")
    public int bossHP = 150;

    @Comment(value = "Starting attack stat (generic.attack_damage) of spawned Tower Guardian entities.")
    public int bossAttack = 5;

    public int floorAmount = 10;
    public int floorRandomAddition = 5;
}