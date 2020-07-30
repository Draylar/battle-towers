package draylar.battletowers;

import draylar.battletowers.api.data.TowerDataLoader;
import draylar.battletowers.config.BattleTowersConfig;
import draylar.battletowers.group.BattleTowersItemGroup;
import draylar.battletowers.registry.*;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;

public class BattleTowers implements ModInitializer {

    public static final String MODID = "battletowers";
    public static BattleTowersConfig CONFIG = AutoConfig.register(BattleTowersConfig.class, GsonConfigSerializer::new).getConfig();
    public static final ItemGroup GROUP = new BattleTowersItemGroup(BattleTowers.id("group"));
    public static final TowerDataLoader TOWER_DATA = new TowerDataLoader();

    @Override
    public void onInitialize() {
        BattleTowerBlocks.init();
        BattleTowerItems.init();
        BattleTowerEntities.init();
        BattleTowerStructures.init();
        BattleTowerTags.init();
    }

    public static Identifier id(String name) {
        return new Identifier(MODID, name);
    }
}
