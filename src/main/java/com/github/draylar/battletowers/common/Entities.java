package com.github.draylar.battletowers.common;

import com.github.draylar.battletowers.common.entity.TowerGuardEntity;
import net.fabricmc.fabric.api.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityCategory;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Entities {
    public static final EntityType<TowerGuardEntity> TOWER_GUARD = register("tower_guard", FabricEntityTypeBuilder.create(EntityCategory.CREATURE, TowerGuardEntity::new).size(EntityDimensions.changing(3, 6)).build());

    public static void init() {

    }

    private static <T extends LivingEntity> EntityType<T> register(String name, EntityType<T> type) {
        return Registry.register(Registry.ENTITY_TYPE, new Identifier("battle-towers", name), type);
    }
}
