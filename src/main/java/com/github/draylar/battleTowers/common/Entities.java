package com.github.draylar.battleTowers.common;

import com.github.draylar.battleTowers.common.entity.tower_guard.TowerGuardEntity;
import net.fabricmc.fabric.api.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityCategory;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.util.registry.Registry;

public class Entities
{
    public static final EntityType<TowerGuardEntity> towerGuard = FabricEntityTypeBuilder.<TowerGuardEntity>create(EntityCategory.CREATURE, TowerGuardEntity::new).size(EntitySize.resizeable(3, 6)).build();

    //  public static final EntityType<?> towerGuard = Registry.register(Registry.ENTITY_TYPE, "battle-towers:tower_guard", EntityType.Builder.<TowerGuardEntity>create(TowerGuardEntity::new, EntityCategory.CREATURE).build("tower_guard"));

    public static void registerEntities()
    {
        Registry.register(Registry.ENTITY_TYPE, "battle-towers:tower_guard", towerGuard);
    }
}
