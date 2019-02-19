package com.github.draylar.battleTowers;

import com.github.draylar.battleTowers.blocks.spawner.DungeonSpawnerBlockEntity;
import com.github.draylar.battleTowers.common.Blocks;
import com.github.draylar.battleTowers.common.Entities;
import com.github.draylar.battleTowers.common.Items;
import com.github.draylar.battleTowers.common.Structures;
import com.github.draylar.battleTowers.config.BattleTowersConfig;
import net.fabricmc.api.ModInitializer;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.registry.Registry;

public class BattleTowers implements ModInitializer
{
	public static final BlockEntityType<DungeonSpawnerBlockEntity> DUNGEON_SPAWNER = Registry.register(Registry.BLOCK_ENTITY, "battle-towers:dungeon_spawner", BlockEntityType.Builder.create(DungeonSpawnerBlockEntity::new).build(null));

	@Override
	public void onInitialize() {
		new BattleTowersConfig().checkConfigFolder();

		Structures.registerStructures();
		Blocks.registerBlocks();
		Items.registerItems();
		Entities.registerEntities();
	}
}
