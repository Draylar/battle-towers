package com.github.draylar.battleTowers;

import com.github.draylar.battleTowers.common.Structures;
import com.github.draylar.battleTowers.common.blocks.boss_lock.BossLockBlock;
import com.github.draylar.battleTowers.common.entity.tower_guard.TowerGuardEntity;
import com.github.draylar.battleTowers.common.items.BossKeyItem;
import com.github.draylar.battleTowers.config.BattleTowersConfig;
import me.sargunvohra.mcmods.autoconfig1.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityCategory;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.registry.Registry;

public class BattleTowers implements ModInitializer
{
	public static BossLockBlock BOSS_LOCK;
	public static BossKeyItem KEY;
	public static BlockItem BOSS_LOCK_ITEM;
	public static final EntityType<TowerGuardEntity> TOWER_GUARD = FabricEntityTypeBuilder.<TowerGuardEntity>create(EntityCategory.CREATURE, TowerGuardEntity::new).size(EntitySize.resizeable(3, 6)).build();

	public static BattleTowersConfig CONFIG;

	@Override
	public void onInitialize()
	{
		CONFIG = AutoConfig.register(BattleTowersConfig.class, GsonConfigSerializer::new).getConfig();

		Structures.registerStructures();
		BOSS_LOCK = Registry.register(Registry.BLOCK, "battle-towers:boss_lock", new BossLockBlock());
		BOSS_LOCK_ITEM = Registry.register(Registry.ITEM, "battle-towers:boss_lock", new BlockItem(BOSS_LOCK, new Item.Settings().itemGroup(ItemGroup.MISC)));
		KEY = Registry.register(Registry.ITEM, "battle-towers:boss_key", new BossKeyItem(new Item.Settings().itemGroup(ItemGroup.MISC)));
		Registry.register(Registry.ENTITY_TYPE, "battle-towers:tower_guard", TOWER_GUARD);
	}
}
