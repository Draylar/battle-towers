package draylar.battletowers;

import draylar.battletowers.api.Towers;
import draylar.battletowers.entity.TowerGuardEntity;
import draylar.battletowers.registry.BattleTowerBlocks;
import draylar.battletowers.registry.BattleTowerEntities;
import draylar.battletowers.registry.BattleTowerItems;
import draylar.battletowers.registry.BattleTowerStructures;
import draylar.battletowers.config.BattleTowersConfig;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class BattleTowers implements ModInitializer {

    public static final String MODID = "battletowers";

    public static BattleTowersConfig CONFIG = AutoConfig.register(BattleTowersConfig.class, GsonConfigSerializer::new).getConfig();
    public static final ItemGroup GROUP = FabricItemGroupBuilder.build(id("group"), () -> {
        return new ItemStack(BattleTowerItems.KEY);
    });

    @Override
    public void onInitialize() {
        BattleTowerBlocks.init();
        BattleTowerItems.init();
        BattleTowerEntities.init();
        BattleTowerStructures.init();
        Towers.init();

        FabricDefaultAttributeRegistry.register(BattleTowerEntities.TOWER_GUARD, TowerGuardEntity.createGuardianAttributes());
    }

    public static Identifier id(String name) {
        return new Identifier(MODID, name);
    }
}
