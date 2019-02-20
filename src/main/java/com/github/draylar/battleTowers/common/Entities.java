package com.github.draylar.battleTowers.common;

import com.github.draylar.battleTowers.common.entity.tower_guard.TowerGuardEntity;
import net.fabricmc.fabric.api.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityCategory;
import net.minecraft.entity.EntityType;
import net.minecraft.util.registry.Registry;

public class Entities
{
    public static final EntityType<TowerGuardEntity> towerGuard = FabricEntityTypeBuilder.create(EntityCategory.CREATURE, TowerGuardEntity::new).build();

    public static void registerEntities()
    {
        Registry.register(Registry.ENTITY_TYPE, "battle-towers:tower_guard", towerGuard);
    }
}
