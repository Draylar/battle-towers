package draylar.battletowers.registry;

import draylar.battletowers.BattleTowers;
import draylar.battletowers.entity.TowerGuardEntity;
import net.fabricmc.fabric.api.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityCategory;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.registry.Registry;

public class BattleTowerEntities {

    public static final EntityType<TowerGuardEntity> TOWER_GUARD = register("tower_guard", FabricEntityTypeBuilder.create(EntityCategory.CREATURE, TowerGuardEntity::new).size(EntityDimensions.changing(3, 6)).build());

    private static <T extends LivingEntity> EntityType<T> register(String name, EntityType<T> type) {
        return Registry.register(Registry.ENTITY_TYPE, BattleTowers.id(name), type);
    }

    public static void init() {

    }

    private BattleTowerEntities() {
        // NO-OP
    }
}
