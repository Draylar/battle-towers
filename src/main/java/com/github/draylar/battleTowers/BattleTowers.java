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
	public static BossLockBlock bossLockBlock;
	public static BossKeyItem bossKeyItem;
	public static BlockItem bossLockItem;
	public static final EntityType<TowerGuardEntity> TOWER_GUARD = FabricEntityTypeBuilder.<TowerGuardEntity>create(EntityCategory.CREATURE, TowerGuardEntity::new).size(EntitySize.resizeable(3, 6)).build();

	public static final BattleTowersConfig CONFIG = AutoConfig.getConfigHolder(BattleTowersConfig.class).getConfig();

	@Override
	public void onInitialize()
	{
		AutoConfig.register(BattleTowersConfig.class, GsonConfigSerializer::new);

		Structures.registerStructures();
		bossLockBlock = Registry.register(Registry.BLOCK, "battle-towers:boss_lock", new BossLockBlock());
		bossLockItem = Registry.register(Registry.ITEM, "battle-towers:boss_lock", new BlockItem(bossLockBlock, new Item.Settings().itemGroup(ItemGroup.MISC)));
		bossKeyItem = Registry.register(Registry.ITEM, "battle-towers:boss_key", new BossKeyItem(new Item.Settings().itemGroup(ItemGroup.MISC)));
		Registry.register(Registry.ENTITY_TYPE, "battle-towers:tower_guard", TOWER_GUARD);
	}
}
