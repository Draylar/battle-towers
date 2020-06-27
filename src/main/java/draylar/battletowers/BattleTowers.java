package draylar.battletowers;

import draylar.battletowers.api.tower.TowerDataLoader;
import draylar.battletowers.config.BattleTowersConfig;
import draylar.battletowers.entity.TowerGuardianEntity;
import draylar.battletowers.group.BattleTowersItemGroup;
import draylar.battletowers.registry.*;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;

public class BattleTowers implements ModInitializer {

    public static final String MODID = "battletowers";

    public static BattleTowersConfig CONFIG = AutoConfig.register(BattleTowersConfig.class, GsonConfigSerializer::new).getConfig();
    public static final ItemGroup GROUP = new BattleTowersItemGroup(BattleTowers.id("group"));
    public static final TowerDataLoader TOWER_DATA = new TowerDataLoader();

    // red = center, green = ladder

    @Override
    public void onInitialize() {
        BattleTowerBlocks.init();
        BattleTowerItems.init();
        BattleTowerEntities.init();
        BattleTowerStructures.init();
        BattleTowerTags.init();

        FabricDefaultAttributeRegistry.register(BattleTowerEntities.TOWER_GUARD, TowerGuardianEntity.createGuardianAttributes());
    }

    public static Identifier id(String name) {
        return new Identifier(MODID, name);
    }
}
